package com.safi.workshop.sheet;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.safi.core.actionstep.DBQueryId;

public class DBQueryParamPropertyDescriptor extends PropertyDescriptor {

  private EditingDomain editingDomain;

  public DBQueryParamPropertyDescriptor(Object object,
      IItemPropertyDescriptor itemPropertyDescriptor, EditingDomain editingDomain) {
    super(object, itemPropertyDescriptor);
    this.editingDomain = editingDomain;
  }

  @Override
  protected CellEditor createEDataTypeCellEditor(EDataType dataType, Composite composite) {
    return super.createEDataTypeCellEditor(dataType, composite);
  }

  @Override
  public CellEditor createPropertyEditor(Composite composite) {
    EStructuralFeature structuralFeature = ((EStructuralFeature) itemPropertyDescriptor
        .getFeature(object));
    EClassifier eType = structuralFeature.getEType();
    EAnnotation annotation = structuralFeature.getEAnnotation("Directionality");
    if (annotation != null) {
      EMap<String, String> map = annotation.getDetails();
      String output = map.get("output");
      if (Boolean.valueOf(output)) {
        return new QueryParameterSelectorCellEditor(composite, getLabelProvider());
      }
    }
    return super.createPropertyEditor(composite);
  }

  @Override
  public ILabelProvider getLabelProvider() {
    // TODO Auto-generated method stub
    return super.getLabelProvider();
  }

  class QueryParameterSelectorCellEditor extends ExtendedDialogCellEditor {

    public QueryParameterSelectorCellEditor(Composite composite, ILabelProvider labelProvider) {
      super(composite, labelProvider);
    }

    @Override
    protected Object openDialogBox(Control cellEditorWindow) {
      DBResourceChooser chooser = new DBResourceChooser(cellEditorWindow.getShell());
      Object val = getValue();
      chooser.setSelectedId(val);
      chooser.setMode(DBResourceChooser.Mode.QUERY_PARAM);
      EObject obj = (EObject) object;
      EStructuralFeature feature = obj.eClass().getEStructuralFeature("query");
      if (feature != null) {
        Object cobj = obj.eGet(feature, true);
        if (cobj instanceof DBQueryId) {
          String id = ((DBQueryId) cobj).getId();
          chooser.setParentId(id);
          chooser.setMessageText("Select a query parameter");
        } else {
          chooser.setTitleText("No resources available");
          chooser.setMessageText("You must first select a query");
          chooser.setDisabled(true);
        }
      }
      int result = chooser.open();
      if (result == Window.OK) {
        Object newVal = chooser.getSelectedId();
        if (newVal != val) {
          // editingDomain.getChildren(editingDomain.getParent(val)).remove(val);
        }
        return newVal;
      }
      return val;
    }
  }
}