/*
 * Copyright (C) 2007 SQL Explorer Development Team
 * http://sourceforge.net/projects/eclipsesql
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.safi.workshop.sqlexplorer.plugin.editors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.safi.workshop.SafiNavigator;
import com.safi.workshop.part.AsteriskDiagramEditorPlugin;
import com.safi.workshop.part.SafiWorkshopEditorUtil;
import com.safi.workshop.sqlexplorer.IConstants;
import com.safi.workshop.sqlexplorer.Messages;
import com.safi.workshop.sqlexplorer.connections.SessionEstablishedAdapter;
import com.safi.workshop.sqlexplorer.dbproduct.Alias;
import com.safi.workshop.sqlexplorer.dbproduct.Session;
import com.safi.workshop.sqlexplorer.dbproduct.User;
import com.safi.workshop.sqlexplorer.plugin.SQLExplorerPlugin;
import com.safi.workshop.sqlexplorer.plugin.views.DatabaseStructureView;
import com.safi.workshop.sqlexplorer.sessiontree.model.utility.Dictionary;
import com.safi.workshop.sqlexplorer.sqleditor.actions.SQLEditorToolBar;
import com.safi.workshop.sqlexplorer.sqlpanel.AbstractSQLExecution;
import com.safi.workshop.sqlexplorer.util.PartAdapter2;

/**
 * SQLEditor is the top-level Editor component which is registered with Eclipse as the
 * Editor for each new SQL connection.
 * 
 * NOTE RE PREVIOUS VERSIONS: The previous version of this class was derived from
 * TextEditor and "only" provided facilities for editing and executing SQL text; the
 * results were displayed in a separate view, where the results from different editors
 * would intermingle. Also, any messages output from the server (ie which was not a result
 * set) were lost.
 * 
 * This version includes a splitter pane where the top half is the text editor and the
 * bottom half is a tabbed control containing one tab per result set and a further tab for
 * messages. The code in SQLEditor was becoming very large and so the code for the text
 * editor part has been split off into SQLTextEditor; it is largely unchanged except for
 * the refactoring.
 * 
 * Another change is that the editor no longer provides a status bar; because each query
 * tab provides a status bar with a message specific to the resultset, it was redundant to
 * provide the same info (well, "most recent event" info) in a status bar just 2 pixels
 * below. However, the setMessage method is still supported and now updates the main
 * Eclipse status bar instead. This means that the "limit rows" feature moves to the
 * Editor toolbar.
 * 
 * Behavoural Changes The editors now prompt to save when they're closed; this is part of
 * the slight paradigm shift of SQLExplorer - instead of the SQL window being a scratch
 * pad for executing ad-hoc SQL, it's becoming part of a development environment for
 * stored procedures and queries.
 * 
 * @modified John Spackman
 * 
 */
public class SQLEditor extends EditorPart implements SwitchableSessionEditor {

  // The color of the sash
  private static final Color SASH_COLOR = IConstants.TAB_BORDER_COLOR;

  // Typical file extensions
  public static final String[] SUPPORTED_FILETYPES = new String[] { "*.sql", "*.txt", "*.*" };

  // The Session node from the Connections view
  private Session session;

  private User user;

  // Toolbar
  private SQLEditorToolBar toolBar;

  // The actual text editor for the top half of our composite editor
  private SQLTextEditor textEditor;

  // TabFolder for results and messages; note that we use CTabFolder and not TabFolder
  // because CTabFolder adds support for an "X" close button on each tab
  private CTabFolder tabFolder;

  // The messages tab
  private CTabItem messagesTab;

  // The Table in the Messages tab
  private Table messagesTable;

  // Our own dirty flag - used for new files
  private boolean isDirty;

  // True if the editor does not have a filename yet
  private boolean isUntitled;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.IEditorPart#init(org.eclipse.ui.IEditorSite,
   * org.eclipse.ui.IEditorInput)
   */
  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {

    // Configure the editor
    setSite(site);
    setInput(input);

    // Create the text editor
    textEditor = new SQLTextEditor(this);
    textEditor.init(site, input);

    // Make sure we get notification that our editor is closing because
    // we may need to stop running queries
    getSite().getPage().addPartListener(new PartAdapter2() {

      /*
       * (non-JavaDoc)
       * 
       * @seecom.safi.workshop.sqlexplorer.util.PartAdapter2#partClosed(org.eclipse.ui.
       * IWorkbenchPartReference)
       */
      @Override
      public void partClosed(IWorkbenchPartReference partRef) {
        if (partRef.getPart(false) == SQLEditor.this) {
          onCloseEditor();
        }
      }

    });

    // If we havn't got a view, then try for the current session in the ConnectionsView
    if (getSession() == null) {
      SafiNavigator nav = SafiWorkshopEditorUtil.getSafiNavigator();
      if (nav != null) {
        User user = nav.getDefaultUser();

        if (user != null)
          user.queueForNewSession(new SessionEstablishedAdapter() {
            @Override
            public void sessionEstablished(Session session) {
              setSession(session);
              // openDBStructureView();
            }
          });
      }

      // ConnectionsView view = SQLExplorerPlugin.getDefault().getConnectionsView();
      // if (view != null) {
      // User user = view.getDefaultUser();
      // if (user != null) user.queueForNewSession(new SessionEstablishedAdapter() {
      // @Override
      // public void sessionEstablished(Session session) {
      // setSession(session);
      // }
      // });
      // }
    } else {
      // openDBStructureView();
    }
  }

  private void openDBStructureView() {
    Session session = getSession();
    if (session == null) {
      AsteriskDiagramEditorPlugin.getInstance().logError(
          "Couldn't open DB Structure view.  No current session in SQLEditor");
      return;
    }
    DatabaseStructureView dsView = SQLExplorerPlugin.getDefault().getDatabaseStructureView();
    Alias alias = session.getUser().getAlias();
    if (alias != null && !alias.getDefaultUser().isOfflineMode()) {
      // && !alias.getDefaultUser().hasAuthenticated()) {
      try {
        dsView.addUser(alias.getDefaultUser());
        // } catch (SQLCannotConnectException e) {
        // MessageDialog.openError(Display.getDefault().getActiveShell(),
        // "Cannot connect", e
        // .getMessage());
      } catch (Throwable e) {
        SQLExplorerPlugin.error("Error creating sql editor", e);
      }
    }
  }

  @Override
  public void dispose() {
    setSession(null);
    super.dispose();
  }

  /*
   * (non-JavaDoc)
   * 
   * @see
   * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite
   * )
   */
  @Override
  public void createPartControl(Composite parent) {
    try {
      parent.setLayout(new FillLayout());

      // Create a wrapper for our stuff
      final Composite myParent = new Composite(parent, SWT.NONE);
      FormLayout layout = new FormLayout();
      myParent.setLayout(layout);
      FormData data;

      // Create sash and attach it to 75% of the way down
      final Sash sash = createSash(myParent);
      data = new FormData();
      data.top = new FormAttachment(75, 0);
      data.left = new FormAttachment(0, 0);
      data.right = new FormAttachment(100, 0);
      sash.setLayoutData(data);

      // Create status bar and attach it to the bottom
      /*
       * Composite statusBar = createStatusBar(myParent); data = new FormData(); data.left
       * = new FormAttachment(0, 0); data.right = new FormAttachment(100, 0); data.bottom
       * = new FormAttachment(100, 0); statusBar.setLayoutData(data);
       */

      // Create the toolbar and attach it to the top of the composite
      createToolbar(myParent);
      data = new FormData();
      data.top = new FormAttachment(0, 0);
      data.left = new FormAttachment(0, 0);
      data.right = new FormAttachment(100, 0);
      toolBar.getToolbarControl().setLayoutData(data);

      // Attach the editor to the toolbar and the top of the sash
      final Composite editor = createEditor(myParent);
      data = new FormData();
      data.top = new FormAttachment(toolBar.getToolbarControl(), 0);
      data.bottom = new FormAttachment(sash, 0);
      data.left = new FormAttachment(0, 0);
      data.right = new FormAttachment(100, 0);
      editor.setLayoutData(data);

      // Attach the tabs to the bottom of the sash and the bottom of the composite
      CTabFolder tabFolder = createResultTabs(myParent);
      data = new FormData();
      data.top = new FormAttachment(sash, 0);
      data.left = new FormAttachment(0, 0);
      data.right = new FormAttachment(100, 0);
      data.bottom = new FormAttachment(100, 0);
      tabFolder.setLayoutData(data);

      if (session != null)
        toolBar.onEditorSessionChanged(session);

    } catch (Exception e) {
      SQLExplorerPlugin.error("Couldn't create text editor", e);
      MessageDialog.openError(getSite().getShell(), Messages
          .getString("SQLEditor.Init.CreateTextEditor"), e.getClass().getCanonicalName() + ": "
          + e.getMessage());
    }
  }

  /**
   * Creates the toolbar
   * 
   * @param parent
   */
  private void createToolbar(final Composite parent) {
    toolBar = new SQLEditorToolBar(parent, this);

    toolBar.addResizeListener(new ControlListener() {
      public void controlMoved(ControlEvent e) {
      }

      public void controlResized(ControlEvent e) {
        parent.getParent().layout(true);
        parent.layout(true);
      }
    });
  }

  /**
   * Creates the sash (the draggable splitter) between the editor and the results tab
   * 
   * @param parent
   * @return
   */
  private Sash createSash(Composite parent) {
    // Create the sash and put it in the middle
    final Sash sash = new Sash(parent, SWT.HORIZONTAL);
    sash.setBackground(SASH_COLOR);

    sash.addSelectionListener(new SelectionListener() {

      /*
       * (non-JavaDoc)
       * 
       * @see
       * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt
       * .events.SelectionEvent)
       */
      public void widgetDefaultSelected(SelectionEvent e) {
        widgetSelected(e);
      }

      /*
       * (non-JavaDoc)
       * 
       * @see
       * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events
       * .SelectionEvent)
       */
      public void widgetSelected(SelectionEvent e) {
        FormData data = (FormData) sash.getLayoutData();
        Rectangle rect = sash.getParent().getBounds();
        data.top = new FormAttachment(e.y, rect.height, 0);
        sash.getParent().layout();
        sash.getParent().getParent().layout();
      }
    });

    return sash;
  }

  /**
   * Creates the text editor in the top half
   * 
   * @param parent
   * @return
   */
  private Composite createEditor(Composite parent) {
    // Attach the editor to the top of the composite and the top of the sash
    final Composite editorParent = new Composite(parent, SWT.NONE);
    editorParent.setLayout(new FillLayout());
    textEditor.createPartControl(editorParent);
    textEditor.addPropertyListener(new IPropertyListener() {
      public void propertyChanged(Object source, int propertyId) {
        SQLEditor.this.firePropertyChange(propertyId);
      }
    });

    return editorParent;
  }

  /**
   * Creates the results tabs in the bottom half
   * 
   * @param parent
   * @return
   */
  private CTabFolder createResultTabs(Composite parent) {
    tabFolder = new CTabFolder(parent, SWT.TOP | SWT.CLOSE);
    tabFolder.setBorderVisible(true);
    tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

    // Set up a gradient background for the selected tab
    Display display = getSite().getShell().getDisplay();
    tabFolder.setSelectionBackground(
        new Color[] { display.getSystemColor(SWT.COLOR_WHITE), new Color(null, 211, 225, 250),
            new Color(null, 175, 201, 246), IConstants.TAB_BORDER_COLOR },
        new int[] { 25, 50, 75 }, true);

    messagesTab = new CTabItem(tabFolder, SWT.NONE);
    messagesTab.setText(Messages.getString("SQLEditor.Results.Messages.Caption"));

    messagesTable = new Table(tabFolder, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
    messagesTab.setControl(messagesTable);
    messagesTable.setLinesVisible(true);
    messagesTable.setHeaderVisible(true);
    messagesTable.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        super.widgetSelected(e);
        Message message = (Message) ((TableItem) e.item).getData();
        setCursorPosition(message.getLineNo(), message.getCharNo());
      }
    });

    TableColumn col = new TableColumn(messagesTable, SWT.NONE);
    col.setText(Messages.getString("SQLEditor.Results.Messages.Status"));
    col.pack();

    col = new TableColumn(messagesTable, SWT.NONE);
    col.setText(Messages.getString("SQLEditor.Results.Messages.Location"));
    col.pack();

    col = new TableColumn(messagesTable, SWT.NONE);
    col.setText(Messages.getString("SQLEditor.Results.Messages.SQL"));
    col.pack();

    col = new TableColumn(messagesTable, SWT.NONE);
    col.setText(Messages.getString("SQLEditor.Results.Messages.Text"));
    col.pack();

    tabFolder.setSelection(messagesTab);

    // Add a listener to get the close button on each tab
    tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {

      /*
       * (non-JavaDoc)
       * 
       * @seeorg.eclipse.swt.custom.CTabFolder2Adapter#close(org.eclipse.swt.custom.
       * CTabFolderEvent)
       */
      @Override
      public void close(CTabFolderEvent event) {
        super.close(event);
        CTabItem tabItem = (CTabItem) event.item;
        event.doit = onCloseTab(tabItem);
      }

    });

    return tabFolder;
  }

  /*
   * (non-JavaDoc)
   * 
   * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
   */
  @Override
  protected void setInput(IEditorInput input) {
    super.setInput(input);
    if (textEditor != null)
      textEditor.setInput(input);

    // Handle our own form of input
    if (input instanceof SQLEditorInput) {
      SQLEditorInput sqlInput = (SQLEditorInput) input;
      if (input != null) {
        user = sqlInput.getUser();

        if (user != null && !user.isOfflineMode()) {
          user.queueForNewSession(new SessionEstablishedAdapter() {
            @Override
            public void sessionEstablished(Session session) {
              setSession(session);
            }
          });
        }
        if (sqlInput.getQuery() == null) {
          isUntitled = true;
        }
      }
    }

    setPartName(input.getName());
  }

  /*
   * (non-JavaDoc)
   * 
   * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void doSave(IProgressMonitor monitor) {
    if (monitor == null)
      monitor = textEditor.getProgressMonitor();

    if (isUntitled) {
      if (!doSave(false, monitor))
        monitor.setCanceled(true);
      else
        setIsDirty(textEditor.isDirty());
      return;
    }

    // If it's a SQLEditorInput then we have to handle saving ourselves; once the file
    // has been saved into a project (via SaveAs) then the input becomes IFileEditorInput
    // and Eclipse knows how to deal with it
    IEditorInput input = getEditorInput();
    if (input instanceof SQLEditorInput) {
      try {
        SQLEditorInput sqlInput = (SQLEditorInput) input;
        if (sqlInput.getQuery() != null) {
          if (isDirty()) {
            textEditor.doSave(monitor);
            // Query qry = sqlInput.getQuery();
            // qry.setQuerySql(textEditor.sqlTextViewer.getDocument().get());
            //
            // qry.getParameters().clear();
            // BasicQueryParser.updateParameters(qry);
            // if (!qry.getParameters().isEmpty()) {
            // NewQueryDialog dialog = new
            // NewQueryDialog(Display.getCurrent().getActiveShell(),
            // qry, NewQueryDialog.Mode.MODIFY);
            // dialog.open();
            // }
            // setIsDirty(false);
            // SQLExplorerPlugin.getDefault().getAliasManager().modelChanged();
          }
        } else {
          saveToFile(sqlInput.getFile());
        }
      } catch (IOException e) {
        SQLExplorerPlugin.error(e);
        monitor.setCanceled(true);
        return;
      }
    } else
      textEditor.doSave(monitor);

    setIsDirty(textEditor.isDirty());
  }

  /**
   * Save editor content to file.
   * 
   * @see org.eclipse.ui.ISaveablePart#doSaveAs()
   */
  @Override
  public void doSaveAs() {
    doSave(true, null);
  }

  /**
   * Implementation for save-as; returns true if successfull, false if not (i.e. the user
   * cancelled the dialog)
   * 
   * @return true if saved, false if cancelled
   */
  public boolean doSave(boolean saveAs, IProgressMonitor monitor) {

    IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
    boolean haveProjects = projects != null && projects.length > 0;
    IEditorInput input = getEditorInput();

    boolean saveInsideProject = true;
    File file = null;
    if (input instanceof SQLEditorInput) {
      SQLEditorInput seInput = (SQLEditorInput) input;
      file = seInput.getFile();
    }

    // If we have a file, then we already have a filename outside of the project;
    // but if we're doing a save-as then recheck with the user
    if (file != null && !saveAs)
      saveInsideProject = false;

    // Either we're doing a save on a file outside a project or we're doing a save-as
    else if (input instanceof SQLEditorInput) {
      IConstants.Confirm confirm = SQLExplorerPlugin
          .getConfirm(IConstants.CONFIRM_YNA_SAVING_INSIDE_PROJECT);

      // If we're supposed to ask the user...
      if (confirm == IConstants.Confirm.ASK) {
        // Build up the message to ask
        String msg = Messages.getString("Confirm.SaveInsideProject.Intro") + "\n\n";
        if (!haveProjects)
          msg = msg + Messages.getString("Confirm.SaveInsideProject.NoProjectsConfigured") + "\n\n";
        msg = msg + Messages.getString("Confirm.SaveInsideProject.SaveInProject");

        // Ask them
        MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoCancelQuestion(getSite()
            .getShell(), Messages.getString("SQLEditor.SaveAsDialog.Title"), msg, Messages
            .getString("Confirm.SaveInsideProject.Toggle"), false, null, null);
        if (dialog.getReturnCode() == IDialogConstants.CANCEL_ID)
          return false;

        // If they turned on the toggle ("Use this answer in the future"), update the
        // preferences
        if (dialog.getToggleState()) {
          confirm = dialog.getReturnCode() == IDialogConstants.YES_ID ? IConstants.Confirm.YES
              : IConstants.Confirm.NO;
          SQLExplorerPlugin.getDefault().getPreferenceStore().setValue(
              IConstants.CONFIRM_YNA_SAVING_INSIDE_PROJECT, confirm.toString());
        }

        // Whether to save inside or outside
        saveInsideProject = dialog.getReturnCode() == IDialogConstants.YES_ID;
      } else
        saveInsideProject = confirm == IConstants.Confirm.YES;
    }

    // Saving inside a project - convert SQLEditorInput into a Resource by letting
    // TextEditor do the work for us
    if (saveInsideProject) {
      if (!haveProjects) {
        MessageDialog.openError(getSite().getShell(), Messages
            .getString("Confirm.SaveInsideProject.Title"), Messages
            .getString("Confirm.SaveInsideProject.CreateAProject"));
        return false;
      }
      if (input instanceof SQLEditorInput)
        saveAs = true;

      // Save it and use their EditorInput
      if (saveAs)
        textEditor.doSaveAs();
      else {
        if (monitor == null)
          monitor = textEditor.getProgressMonitor();

        textEditor.doSave(monitor);
      }
      if (input.equals(textEditor.getEditorInput()))
        return false;
      input = textEditor.getEditorInput();
      setInput(input);

      // Update the display
      setPartName(input.getName());
      setTitleToolTip(input.getToolTipText());

    } else {
      try {
        if (file == null || saveAs) {
          FileDialog dialog = new FileDialog(getSite().getShell(), SWT.SAVE);
          dialog.setText(Messages.getString("SQLEditor.SaveAsDialog.Title"));
          dialog.setFilterExtensions(SUPPORTED_FILETYPES);
          dialog.setFilterNames(SUPPORTED_FILETYPES);
          dialog.setFileName("*.sql");

          String path = dialog.open();
          if (path == null)
            return false;
          file = new File(path);
        }

        // Save it
        saveToFile(file);

        // Update the editor input
        input = new SQLEditorInput(file);
        setInput(input);
        setPartName(input.getName());
        setTitleToolTip(input.getToolTipText());

      } catch (IOException e) {
        SQLExplorerPlugin.error("Couldn't save sql", e);
        MessageDialog.openError(getSite().getShell(), Messages
            .getString("SQLEditor.SaveAsDialog.Error"), e.getMessage());
        return false;
      }

    }

    setIsDirty(textEditor.isDirty());
    return true;
  }

  /**
   * Loads a file into the editor
   * 
   * @param file
   *          file to load
   */
  /*
   * private void loadFromFile(File file) throws IOException { BufferedReader reader =
   * null;
   * 
   * StringBuffer all = new StringBuffer((int)file.length()); String str = null; //String
   * delimiter = _editor.getSqlTextViewer().getTextWidget().getLineDelimiter(); // Note: I
   * have changed the delimiter to a hardcoded \n because this a) allows the // interface
   * to SQLEditor to be cleaner (see SQLEditor for refactoring description) // and I can
   * find several other places where text will be passed to the same text // editor and \n
   * is hard coded. If there is an issue with how the view encodes // line delimiters, it
   * is likely to be a global problem and we should handle it in // SQLEditor.setText()
   * instead. // reader = new BufferedReader(new FileReader(file)); try { while ((str =
   * reader.readLine()) != null) { all.append(str); all.append('\n'); }
   * 
   * setText(all.toString()); isDirty = false; } finally { reader.close(); } }
   */

  /**
   * Saves the text to a file on the filing system - IE outside of any projects
   */
  private void saveToFile(File file) throws IOException {
    if (file.exists())
      file.delete();

    file.createNewFile();

    String content = textEditor.sqlTextViewer.getDocument().get();

    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.write(content, 0, content.length());
    writer.close();
  }

  /**
   * Adds a message to the message window
   * 
   * @param message
   */
  public void addMessage(Message message) {
    if (isClosed())
      return;

    // Don't log success messages unless we're supposed to
    if (message.getStatus() == Message.Status.SUCCESS
        && !SQLExplorerPlugin.getDefault().getPreferenceStore().getBoolean(
            IConstants.LOG_SUCCESS_MESSAGES))
      return;

    TableItem tableRow = new TableItem(messagesTable, SWT.NONE);
    tableRow.setText(message.getTableText());
    tableRow.setData(message);

    TableColumn[] cols = messagesTable.getColumns();
    for (TableColumn col : cols)
      col.pack();
  }

  /**
   * Allocates a new tab for an AbstractSQLExecution; the tab and it's contents are
   * completely undefined
   * 
   * @param sqlExec
   * @return
   */
  public CTabItem createResultsTab(AbstractSQLExecution job) {
    if (tabFolder.isDisposed())
      return null;

    // Create the new tab, make it second to last (IE keep the messages tab
    // always at the end) and set the new tab's title to the 1-based index
    CTabItem tabItem = new CTabItem(tabFolder, SWT.CLOSE, tabFolder.getItems().length - 1);
    tabItem.setText(Integer.toString(tabFolder.getItems().length - 1));
    tabFolder.setSelection(tabItem);

    // Make sure we can track the execution
    tabItem.setData(job);

    // Make sure we're visible
    getSite().getPage().bringToTop(this);

    return tabItem;
  }

  public boolean isClosed() {
    return tabFolder.isDisposed();
  }

  /**
   * Called internally when the user tries to close a tab
   * 
   * @param tabItem
   * @return true if the tab should be closed
   */
  private boolean onCloseTab(CTabItem tabItem) {
    // Cannot close the messages tab
    if (tabItem == messagesTab)
      return false;

    // The SQL query runs in a background thread and if the tab item gets disposed of
    // BEFORE the query has finished we need to notify the thread that there is no UI
    // left and that the thread should terminate as soon as possible.
    synchronized (this) {
      // Get the SQL execution
      AbstractSQLExecution job = getSqlExecution(tabItem);

      // Stop the statement - but allow it to happen asynchronously
      if (job != null) {
        tabItem.setData(null);
        job.cancel();
      }
    }

    return true;
  }

  /**
   * Called internally when the user tries to close the editor
   */
  private void onCloseEditor() {
    textEditor.getDocumentProvider().disconnect(getEditorInput());
    textEditor.setInput(null);
    clearResults();
  }

  /**
   * Closes all result tabs and signals all associated AbstractSQLExecutions to stop (if
   * they're still running)
   */
  public void clearResults() {
    if (tabFolder.isDisposed())
      return;
    synchronized (this) {
      CTabItem[] tabItems = tabFolder.getItems();
      for (CTabItem tabItem : tabItems)
        if (tabItem != messagesTab)
          closeTab(tabItem);
      messagesTable.removeAll();
    }
  }

  /**
   * Removes a tab from the results tabs, stopping any pending executions etc
   * 
   * @param tab
   */
  private void closeTab(CTabItem tab) {
    tab.dispose();
  }

  /**
   * Converts a TabItem into the AbstractSQLExecution for that tab
   * 
   * @param tab
   * @return
   */
  public AbstractSQLExecution getSqlExecution(CTabItem tab) {
    return (AbstractSQLExecution) tab.getData();
  }

  /**
   * Converts an AbstractSQLExecution into its TabItem
   * 
   * @param sqlExec
   * @return
   */
  public CTabItem getResultsTab(AbstractSQLExecution sqlExec) {
    CTabItem[] items = tabFolder.getItems();
    for (CTabItem item : items)
      if (item.getData() == sqlExec)
        return item;
    return null;
  }

  /*
   * (non-JavaDoc)
   * 
   * @see org.eclipse.ui.part.EditorPart#isDirty()
   */
  @Override
  public boolean isDirty() {
    IEditorInput input = getEditorInput();
    if (input instanceof SQLEditorInput && ((SQLEditorInput) input).getQuery() != null) {
      return isDirty || textEditor.isDirty();
    }
    boolean saveOnClose = SQLExplorerPlugin.getDefault().getPreferenceStore().getBoolean(
        IConstants.REQUIRE_SAVE_ON_CLOSE_EDITOR);
    return saveOnClose && (isDirty || textEditor.isDirty());
  }

  public void setIsDirty(boolean isDirty) {
    if (this.isDirty != isDirty) {
      this.isDirty = isDirty;
      firePropertyChange(PROP_DIRTY);
    }
    if (!isDirty)
      isUntitled = false;
  }

  /*
   * (non-JavaDoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    textEditor.setFocus();
  }

  /*
   * (non-JavaDoc)
   * 
   * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
   */
  @Override
  public boolean isSaveAsAllowed() {
    return textEditor.isSaveAsAllowed();
  }

  /**
   * Sets the message displayed on the Eclipse main status bar
   * 
   * @param s
   */
  public void setMessage(String s) {
    IStatusLineManager manager = getEditorSite().getActionBars().getStatusLineManager();
    manager.setMessage(s);
    // statusMgr.setMessage(s);
  }

  /**
   * Sets the dictionary used by the text editor
   * 
   * @param dictionary
   */
  public void setNewDictionary(final Dictionary dictionary) {
    textEditor.setNewDictionary(dictionary);
  }

  /**
   * Sets the session to use for executing queries
   * 
   * @param session
   *          The new Session
   */
  public void setSession(Session session) {
    if (session == this.session)
      return;

    // If we already have a session and we're changing to a different one, close the
    // current one
    if (getSession() != null && session != this.session)
      this.session.close();
    this.session = session;
    if (textEditor != null)
      textEditor.onEditorSessionChanged(session);
    if (toolBar != null)
      toolBar.onEditorSessionChanged(session);
  }

  /**
   * @return the session
   */
  public Session getSession() {
    // In theory, if our session is somehow closed by something else then we will have
    // already
    // had our session changed or reset; however, just in case this doesn't happen we can
    // detect it because the session has it's user set to null when it is detached. If
    // that
    // happened, then we reset the session to null
    if (session != null && session.getUser() == null)
      session = null;
    return session;
  }

  /**
   * Sets the text of the editor
   * 
   * @param txt
   */
  public void setText(String txt) {
    IDocument dc = textEditor.sqlTextViewer.getDocument();
    textEditor.setTextListenerEnabled(false);
    if (dc == null) {
      dc = textEditor.getDocumentProvider().getDocument(txt == null ? "" : txt);

      // IDocument dc = new Document(txt == null ? "" : txt);
      textEditor.sqlTextViewer.setDocument(dc);
    } else
      dc.set(txt == null ? "" : txt);
    textEditor.setTextListenerEnabled(true);
    textEditor.sqlTextViewer.refresh();
  }

  /**
   * insert the text at the current text cursor position of the editor
   * 
   * @param txt
   */
  public void insertText(String pText) {
    textEditor.sqlTextViewer.insertText(pText);
  }

  /**
   * Returns the text to be executed; this is the entire text if there is no selection,
   * else just the selected text
   * 
   * @return
   */
  public String getSQLToBeExecuted() {

    String sql = textEditor.sqlTextViewer.getTextWidget().getSelectionText();
    if (sql == null || sql.trim().length() == 0)
      sql = textEditor.sqlTextViewer.getTextWidget().getText();

    // Normalise this to have standard \n in strings. \r confuses Oracle and
    // isn't normally needed internally anyway
    StringBuffer sb = new StringBuffer(sql);
    for (int i = 0; i < sb.length(); i++) {
      if (sb.charAt(i) == '\r') {
        sb.deleteCharAt(i);
        i--;
      }
    }
    sql = sb.toString();

    return sql != null ? sql : "";
  }

  /**
   * returns the line number that the SQL starts on
   * 
   * @return
   */
  public int getSQLLineNumber() {
    String sql = textEditor.sqlTextViewer.getTextWidget().getSelectionText();
    if (sql == null || sql.trim().length() == 0)
      return 1;
    Point pt = textEditor.sqlTextViewer.getTextWidget().getSelection();
    if (pt == null)
      return 1;
    StyledText text = (StyledText) textEditor.getAdapter(org.eclipse.swt.widgets.Control.class);
    int offset = pt.x;
    int lineNo = text.getLineAtOffset(offset);
    return lineNo + 1;
  }

  /**
   * Clears the text of the editor
   */
  public void clearText() {
    textEditor.sqlTextViewer.clearText();
  }

  /**
   * Returns the toolbar
   * 
   * @return
   */
  public SQLEditorToolBar getEditorToolBar() {
    return toolBar;
  }

  /**
   * Returns whether to limit the results and if so by how much.
   * 
   * @return the maximum number of rows to retrieve, 0 for unlimited, or null if it cannot
   *         be interpretted
   */
  public Integer getLimitResults() {
    return toolBar.getLimitResults();
  }

  /**
   * Updates the cursor position info in the status bar
   */
  public void updateCursorPosition() {
    Object adapter = textEditor.getAdapter(org.eclipse.swt.widgets.Control.class);
    if (adapter instanceof StyledText) {
      StyledText text = (StyledText) adapter;
      int offset = text.getCaretOffset();
      int lineNo = text.getLineAtOffset(offset);
      int lineOffset = text.getOffsetAtLine(lineNo);
      int charNo = offset - lineOffset;

      IStatusLineManager manager = getEditorSite().getActionBars().getStatusLineManager();
      IContributionItem items[] = manager.getItems();
      for (IContributionItem item : items) {
        if (item instanceof CursorPositionContrib) {
          CursorPositionContrib contrib = (CursorPositionContrib) item;
          contrib.setPosition(lineNo + 1, charNo + 1);
          break;
        }
      }
    }
  }

  /**
   * Moves the text cursor to a given line and column
   * 
   * @param lineNo
   * @param charNo
   */
  public void setCursorPosition(int lineNo, int charNo) {
    if (lineNo < 1)
      return;
    if (charNo < 1)
      charNo = 1;
    Object adapter = textEditor.getAdapter(org.eclipse.swt.widgets.Control.class);
    if (adapter instanceof StyledText) {
      StyledText text = (StyledText) adapter;
      int lineOffset = text.getOffsetAtLine(lineNo - 1);
      text.setCaretOffset(lineOffset + charNo - 1);
      updateCursorPosition();
      text.setFocus();
      text.showSelection();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.safi.workshop.sqlexplorer.plugin.editors.SwitchableSessionEditor#refreshToolbars
   * ()
   */
  public void refreshToolbars() {
    getEditorToolBar().refresh();
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
