package com.safi.workshop.sheet.actionstep;

import java.util.List;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import com.safi.asterisk.actionstep.GetDigits;
import com.safi.workshop.sheet.DynamicValueEditorUtils;

public class GetDigitsOutputEditorPage extends AbstractActionstepEditorPage {

  private DynamicValueEditorWidget variableDVEWidget;

  public GetDigitsOutputEditorPage(ActionstepEditorDialog parent) {
    super(parent);
    final GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    setLayout(gridLayout);

    // ----------- ActionStep Stuff
    GetDigits getditits = (GetDigits) parent.getEditPart().getActionStep();

    // ----------- say Text Field
    final Label variableLabel = new Label(this, SWT.NONE);
    variableLabel.setText("Output to Variable:");

    TransactionalEditingDomain editingDomain = parent.getEditPart().getEditingDomain();

    variableDVEWidget = new DynamicValueEditorWidget(this, SWT.NONE);
    variableDVEWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    variableDVEWidget.setDynamicValue(DynamicValueEditorUtils.copyDynamicValue(getditits
        .getVariableName()));
    variableDVEWidget.setEditingDomain(editingDomain);
    variableDVEWidget.setObject(getditits);

    EStructuralFeature textFeature = getditits.eClass().getEStructuralFeature("variableName");
    variableDVEWidget.setFeature(textFeature);

    IObservableValue ob = ActionstepEditObservables.observeValue(editingDomain, getditits,
        textFeature);
    DynamicValueWidgetObservableValue textVal = new DynamicValueWidgetObservableValue(
        variableDVEWidget, SWT.Modify);

    bindingContext.bindValue(textVal, ob, null, null);

  }

  @Override
  public String getTabText() {
    // TODO Auto-generated method stub
    return "Output";
  }

  @Override
  public boolean validate() {
    IObservableList list = bindingContext.getBindings();
    for (Binding b : (List<Binding>) list) {
      b.validateTargetToModel();
    }
    return true;
  }

}
