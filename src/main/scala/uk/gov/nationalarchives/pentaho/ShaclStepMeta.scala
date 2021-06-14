package uk.gov.nationalarchives.pentaho

import org.pentaho.di.core.annotations.Step
import org.pentaho.di.trans.{ Trans, TransMeta }
import org.pentaho.di.trans.step.{ BaseStepMeta, StepDataInterface, StepInterface, StepMeta, StepMetaInterface }

@Step(id = "ShaclStep", name = "ShaclStep.name")
case class ShaclStepMeta() extends BaseStepMeta with StepMetaInterface {

  override def getStep(
    stepMeta: StepMeta,
    stepDataInterface: StepDataInterface,
    copyNr: Int,
    transMeta: TransMeta,
    trans: Trans): StepInterface =
    ShaclStep(stepMeta, stepDataInterface, copyNr, transMeta, trans)

  override def getStepData: StepDataInterface = ShaclStepData()

  /**
    * This method is called every time a new step is created and allocates or sets the step configuration to sensible defaults.
    * The values set here are used by the PDI client (Spoon) when a new step is created. This is a good place to ensure that
    * the step settings are initialized to non-null values. Null values can be cumbersome to deal with in serialization and
    * dialog population, so most PDI step implementations stick to non-null values for all step settings.
    */
  override def setDefault(): Unit = ???

  /**
    * This method is called when a step is duplicated in the PDI client. It returns a deep copy of the step meta object.
    * It is essential that the implementing class creates proper deep copies if the step configuration is stored in modifiable
    * objects, such as lists or custom helper objects.
    * @return
    */
  override def clone(): AnyRef = super.clone()

  override def supportsErrorHandling(): Boolean = true

  /**
    * Getter for the name of the field added by this step
    *
    * @return the name of the field added
    */
  //def getOutputField: String = outputField

  //def setJenaModelField()

}
