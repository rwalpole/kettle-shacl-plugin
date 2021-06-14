package uk.gov.nationalarchives.pentaho

import org.apache.jena.graph.Graph
import org.apache.jena.rdf.model.Model
import org.apache.jena.riot.RDFDataMgr
import org.apache.jena.shacl.ShaclValidator
import org.pentaho.di.core.exception.KettleException
import org.pentaho.di.core.row.RowMeta
import org.pentaho.di.trans.step._
import org.pentaho.di.trans.{ Trans, TransMeta }

import scala.jdk.CollectionConverters._

@SuppressWarnings(
  Array(
    "org.wartremover.warts.AsInstanceOf",
    "org.wartremover.warts.StringPlusAny",
    "org.wartremover.warts.IsInstanceOf",
    "org.wartremover.warts.Var",
    "org.wartremover.warts.Throw",
    "org.wartremover.warts.Null"
  ))
case class ShaclStep(s: StepMeta, stepDataInterface: StepDataInterface, c: Int, t: TransMeta, dis: Trans)
    extends BaseStep(s, stepDataInterface, c, t, dis) with StepInterface {

  //private val PKG = classOf[ShaclStep] // for i18n purposes

  /* Load the shapes file */
  override def init(smi: StepMetaInterface, sdi: StepDataInterface): Boolean = {
    val meta = smi.asInstanceOf[ShaclStepMeta]
    val data = sdi.asInstanceOf[ShaclStepData]
    if (!super.init(meta, data)) {
      false
    } else {
      data.shaclValidator = Some(ShaclValidator.get())
      data.shapesGraph = Some(RDFDataMgr.loadGraph("ODRL-shape.ttl"))
      true
    }
  }

  override def processRow(smi: StepMetaInterface, sdi: StepDataInterface): Boolean = {

    // safely cast the step settings (meta) and runtime info (data) to specific implementations
    val meta = smi.asInstanceOf[ShaclStepMeta]
    val data = sdi.asInstanceOf[ShaclStepData]

    if (first) {
      first = false
      // clone the input row structure and place it in our data object
      //val irm: RowMetaInterface = getInputRowMeta.copy()
      //data.outputRowMeta = Some(irm.clone())
      // use meta.getFields() to change it, so it reflects the output row structure
      //meta.getFields(data.outputRowMeta.get, getStepname, null, null, this, null, null)
      // Locate the row index for this step's field
      // If less than 0, the field was not found.
//      data.outputFieldIndex = data.outputRowMeta.get.indexOfValue(meta.getOutputField)
//      if (data.outputFieldIndex < 0) {
//        log.logError(BaseMessages.getString(PKG, "DemoStep.Error.NoOutputField"))
//        setErrors(1L)
//        setOutputDone()
//        return false
//      }
    }

    val row: Array[AnyRef] = getRow
    var rowInError = false
    val errMsgBuilder = new StringBuilder
    var errCnt: Long = 0

    if (row == null) {
      setOutputDone()
      false
    } else {
      val inputRowMeta = getInputRowMeta
      // use meta.getFields() to change it, so it reflects the output row structure
      meta.getFields(data.outputRowMeta.getOrElse(new RowMeta), getStepname, null, null, null, null, null)
      val jenaModelFieldIdx = inputRowMeta.indexOfValue("jena_model")
      val jenaModelField: AnyRef = row(jenaModelFieldIdx)
      try {
        if (jenaModelField.isInstanceOf[Model]) {
          val dataModel = jenaModelField.asInstanceOf[Model]
          val dataGraph = dataModel.getGraph
          val shapesGraph = getShapesGraph(data)
          data.shaclValidator match {
            case Some(validator) =>
              if (!validator.conforms(shapesGraph, dataGraph)) {
                val report = validator.validate(shapesGraph, dataGraph)
                report.getEntries.asScala.foreach(entry => errMsgBuilder.append(entry.message()))
                rowInError = true
                errCnt = errCnt + 1
              }
            case _ => throw new KettleException("No ShaclValidator instance found")
          }
        } else {
          throw new KettleException(
            "Expected field " + jenaModelField + " to contain a Jena Model, but found " + jenaModelField.getClass)
        }

      } catch {
        case ex: ClassCastException => throw new KettleException(ex.getMessage)
      }

      if (!rowInError) putRow(data.outputRowMeta.getOrElse(new RowMeta), row)
      else
        putError(data.outputRowMeta.getOrElse(new RowMeta), row, errCnt, errMsgBuilder.toString(), "data", "ERROR_01")
      true
    }

  }

  override def dispose(smi: StepMetaInterface, sdi: StepDataInterface): Unit = super.dispose(smi, sdi)

  private def getShapesGraph(data: ShaclStepData): Graph =
    data.shapesGraph match {
      case Some(shapes) => shapes
      case _            => throw new KettleException("No SHACL shapes graph found")
    }

}
