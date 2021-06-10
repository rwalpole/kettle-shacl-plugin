package uk.gov.nationalarchives.pentaho

import org.eclipse.swt.widgets.Shell
import org.pentaho.di.trans.TransMeta
import org.pentaho.di.trans.step.{ BaseStepMeta, StepDialogInterface }
import org.pentaho.di.ui.trans.step.BaseStepDialog

@SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
class ShaclStepDialog(parent: Shell, in: Object, transMeta: TransMeta, sname: String)
    extends BaseStepDialog(parent, in.asInstanceOf[BaseStepMeta], transMeta, sname) with StepDialogInterface {
  override def open(): String = ???
}
