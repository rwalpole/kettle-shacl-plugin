package uk.gov.nationalarchives.pentaho

import org.apache.jena.graph.Graph
import org.apache.jena.rdf.model.Model
import org.apache.jena.shacl.ShaclValidator
import org.pentaho.di.core.row.RowMetaInterface
import org.pentaho.di.trans.step.{ BaseStepData, StepDataInterface }

@SuppressWarnings(Array("org.wartremover.warts.Var"))
case class ShaclStepData() extends BaseStepData with StepDataInterface {

  var outputRowMeta: Option[RowMetaInterface] = None

  var shaclValidator: Option[ShaclValidator] = None

  var shapesGraph: Option[Graph] = None

  var outputFieldIndex: Int = -1

  var model: Option[Model] = None

}
