package com.safi.workshop.timeBasedRouting.pak.factory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.figures.DiagramColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.ShapeStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;

import com.safi.workshop.part.SafletVisualIDRegistry;
import com.safi.workshop.timeBasedRouting.pak.editpart.TimeItemEditPart;
import com.safi.workshop.timeBasedRouting.pak.editpart.TimeItemLabelTextEditPart;

/**
 * @generated
 */
public class TimeItemViewFactory extends AbstractShapeViewFactory {

  /**
   * @generated
   */
  protected List createStyles(View view) {
    List styles = new ArrayList();
    styles.add(NotationFactory.eINSTANCE.createShapeStyle());
    return styles;
  }

  /**
   * @generated NOT
   */
  protected void decorateView(View containerView, View view, IAdaptable semanticAdapter,
      String semanticHint, int index, boolean persisted) {
    if (semanticHint == null) {
      semanticHint = SafletVisualIDRegistry.getType(TimeItemEditPart.VISUAL_ID);
      view.setType(semanticHint);
    }
    super.decorateView(containerView, view, semanticAdapter, semanticHint, index, persisted);
    IAdaptable eObjectAdapter = null;
    EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
    if (eObject != null) {
      eObjectAdapter = new EObjectAdapter(eObject);
    }
    getViewService().createNode(eObjectAdapter, view,
        SafletVisualIDRegistry.getType(TimeItemLabelTextEditPart.VISUAL_ID), ViewUtil.APPEND,
        true, getPreferencesHint());

    NodeImpl n = (NodeImpl) view;
    ShapeStyle fillstyle = (ShapeStyle) n.getStyle(NotationPackage.Literals.FILL_STYLE);
    //fillstyle.setLineColor(FigureUtilities.colorToInteger(DiagramColorConstants.red));
    //    EList<Style> styles =  view.getStyles();
    fillstyle.setFillColor(FigureUtilities.colorToInteger(DiagramColorConstants.blue));

  }
}
