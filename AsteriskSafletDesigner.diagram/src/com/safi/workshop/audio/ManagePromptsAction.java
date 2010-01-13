package com.safi.workshop.audio;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import com.safi.workshop.part.AsteriskDiagramEditorUtil;

public class ManagePromptsAction implements IWorkbenchWindowActionDelegate {

  @Override
  public void dispose() {
    // TODO Auto-generated method stub

  }

  @Override
  public void init(IWorkbenchWindow window) {

  }

  @Override
  public void run(IAction action) {
    new ManagePromptsDialog(AsteriskDiagramEditorUtil.getActiveShell()).open();
  }

  @Override
  public void selectionChanged(IAction action, ISelection selection) {
    // TODO Auto-generated method stub

  }

}
