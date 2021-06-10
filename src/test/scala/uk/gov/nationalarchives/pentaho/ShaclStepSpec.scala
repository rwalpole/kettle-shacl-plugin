package uk.gov.nationalarchives.pentaho

import org.pentaho.di.core.{KettleEnvironment, RowMetaAndData}
import org.pentaho.di.core.variables.Variables
import org.pentaho.di.trans.TransTestFactory
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

import java.util


@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements","org.wartremover.warts.Any", "org.wartremover.warts.Null"))
class ShaclStepSpec extends AnyWordSpec with Matchers with MockitoSugar {

  private val STEP_NAME = "Test Shacl Step"
  //private val mockStepMeta1 = mock[StepMeta]
  //private val mockStepMeta2 = mock[StepMeta]
  //private val mockTransMeta = mock[TransMeta]
  //private val mockTrans = mock[Trans]
  private val meta = ShaclStepMeta()
  //private val data = ShaclStepData()
  //private val mockStepPartitioningMeta = mock[StepPartitioningMeta]



  "dsda" must {
    "dasda" in {
      KettleEnvironment.init(false)
      //when(mockStepMeta1.getName).thenReturn(STEP_NAME)
      val tm = TransTestFactory.generateTestTransformation(new Variables(), meta, STEP_NAME)
      val result = TransTestFactory.executeTestTransformation(tm,TransTestFactory.INJECTOR_STEPNAME, STEP_NAME, TransTestFactory.DUMMY_STEPNAME, new util.ArrayList[RowMetaAndData]())
      result must contain(null)
      //when(mockTransMeta.findStep(STEP_NAME)).thenReturn(mockStepMeta2)
      //when(mockStepMeta2.getTargetStepPartitioningMeta).thenReturn(mockStepPartitioningMeta)
      //val shaclStep = ShaclStep(mockStepMeta1, data, 0, mockTransMeta, mockTrans)
      //shaclStep.processRow(meta, data) mustBe true
    }
  }

}
