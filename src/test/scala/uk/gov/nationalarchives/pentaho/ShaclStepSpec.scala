package uk.gov.nationalarchives.pentaho

import org.apache.jena.riot.RDFDataMgr
import org.pentaho.di.core.row.RowMeta
import org.pentaho.di.core.row.value.ValueMetaString
import org.pentaho.di.core.variables.Variables
import org.pentaho.di.core.{ KettleEnvironment, RowMetaAndData }
import org.pentaho.di.trans.TransTestFactory
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

import scala.jdk.CollectionConverters._
//import java.util.{ArrayList, List, UUID}
@SuppressWarnings(
  Array("org.wartremover.warts.NonUnitStatements", "org.wartremover.warts.Any", "org.wartremover.warts.Null"))
class ShaclStepSpec extends AnyWordSpec with Matchers with MockitoSugar {

  private val STEP_NAME = "Test Shacl Step"
  //private val mockStepMeta1 = mock[StepMeta]
  //private val mockStepMeta2 = mock[StepMeta]
  //private val mockTransMeta = mock[TransMeta]
  //private val mockTrans = mock[Trans]

  //private val data = ShaclStepData()
  //private val mockStepPartitioningMeta = mock[StepPartitioningMeta]

  "ShaclStep " must {
    "successfully process a row" in {
      val meta = ShaclStepMeta()
      KettleEnvironment.init(false)
      //when(mockStepMeta1.getName).thenReturn(STEP_NAME)
      val tm = TransTestFactory.generateTestTransformation(new Variables(), meta, STEP_NAME)
      val result = TransTestFactory.executeTestTransformation(
        tm,
        TransTestFactory.INJECTOR_STEPNAME,
        STEP_NAME,
        TransTestFactory.DUMMY_STEPNAME,
        generateInputData().asJava)
      result.size must equal(1)
    }
  }

  def generateInputData(): List[RowMetaAndData] = {
    val rowMeta = new RowMeta
    rowMeta.addValueMeta(new ValueMetaString("jena_model"))
    val url = getClass.getResource("/" + "FO_371_190180_1-policy.ttl")
    val model = RDFDataMgr.loadModel(url.toString)
    val data = Array(model)
    List(new RowMetaAndData(rowMeta, data))
  }

}
