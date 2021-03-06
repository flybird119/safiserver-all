/*
 * Copyright (C) 2006 SQL Explorer Development Team
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
package com.safi.workshop.sqlexplorer.sqlpanel;

import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.custom.CTabItem;

import com.safi.workshop.part.AsteriskDiagramEditorPlugin;
import com.safi.workshop.sqlexplorer.IConstants;
import com.safi.workshop.sqlexplorer.Messages;
import com.safi.workshop.sqlexplorer.dataset.DataSet;
import com.safi.workshop.sqlexplorer.dbproduct.DatabaseProduct;
import com.safi.workshop.sqlexplorer.parsers.ExecutionContext;
import com.safi.workshop.sqlexplorer.parsers.Query;
import com.safi.workshop.sqlexplorer.parsers.QueryParser;
import com.safi.workshop.sqlexplorer.plugin.SQLExplorerPlugin;
import com.safi.workshop.sqlexplorer.plugin.editors.Message;
import com.safi.workshop.sqlexplorer.plugin.editors.SQLEditor;
import com.safi.workshop.sqlexplorer.sqleditor.results.DataSetResultsTab;
import com.safi.workshop.sqlexplorer.sqleditor.results.EditorResultsTab;
import com.safi.workshop.sqlexplorer.sqleditor.results.GenericAction;
import com.safi.workshop.sqlexplorer.sqleditor.results.GenericActionGroup;
import com.safi.workshop.sqlexplorer.sqleditor.results.ResultsTableAction;

/**
 * Executes one or more SQL statements in sequence, displaying the results in tabs and/or
 * in the Messages tab
 * 
 * @modified John Spackman
 * 
 */
public class SQLExecution extends AbstractSQLExecution {

  // Whether the editor has any messages
  private boolean hasMessages;

  // Maximum number of rows to return
  protected int _maxRows;

  // The Statement being used to execute the current query
  protected Statement _stmt;

  /**
   * Constructor
   * 
   * @param _editor
   * @param queryParser
   * @param maxRows
   * @param _session
   */
  public SQLExecution(SQLEditor _editor, QueryParser queryParser, int maxRows) {
    super(_editor, queryParser);
    _maxRows = maxRows;
  }

  /**
   * Display SQL Results in result pane
   * 
   * @param sqlResult
   *          the results of the query
   */
  protected void displayResults(final DataSet dataSet) {

    // Switch to the UI thread to execute this
    getEditor().getSite().getShell().getDisplay().asyncExec(new Runnable() {

      public void run() {

        int resultCount = dataSet.hasData() ? dataSet.getRows().length : dataSet.getUpdateCount();
        String statusMessageSmall = Messages.getString("SQLResultsView.Time.Prefix") + " "
            + dataSet.getExecutionTime() + " " + Messages.getString("SQLResultsView.Time.Postfix");
        String statusMessageLarge = statusMessageSmall;
        if (resultCount >= 0) {
          statusMessageLarge = statusMessageLarge
              + "  "
              + (dataSet.hasData() ? Messages.getString("SQLResultsView.Count.Prefix") : Messages
                  .getString("SQLResultsView.Update.Prefix")) + " " + resultCount;
        }
        if (dataSet.hasData()) {
          CTabItem tabItem = allocateResultsTab(dataSet.getQuery());
          if (tabItem == null)
            return;

          final DataSetResultsTab table = new DataSetResultsTab(dataSet);
          table.setHasStatusBar(true);
          table.setStatusMessage(statusMessageLarge);
          EditorResultsTab resultsTab = new EditorResultsTab(tabItem, table);
          String caption = dataSet.getCaption();
          if (caption != null)
            resultsTab.setTabTitle(caption);

          // add context menu to table & cursor
          final GenericActionGroup actionGroup = new GenericActionGroup(
              "dataSetTableContextAction", getEditor().getSite().getShell()) {
            @Override
            public void initialiseAction(GenericAction action) {
              super.initialiseAction(action);
              ResultsTableAction dsAction = (ResultsTableAction) action;
              dsAction.setResultsTable(table);
            }
          };
          table.getMenuManager().addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager manager) {
              actionGroup.fillContextMenu(manager);
            }
          });
        }
        try {
          // set initial message
          setProgressMessage(Messages.getString("SQLResultsView.ConnectionWait"));

          getEditor().setMessage(statusMessageSmall);

          Query sql = dataSet.getQuery();
          int lineNo = sql.getLineNo();
          lineNo = getQueryParser().adjustLineNo(lineNo);

          if (getQueryParser().getContext().isOn(ExecutionContext.LOG_SUCCESS)) {
            getEditor().addMessage(
                new Message(Message.Status.SUCCESS, lineNo, 0, sql.getQuerySql(),
                    statusMessageLarge));
          }

          // reset to start message in case F5 will be used
          setProgressMessage(Messages.getString("SQLResultsView.ConnectionWait"));

        } catch (Exception e) {
          MessageDialog.openError(getEditor().getSite().getShell(), "Error creating result tab", e
              .getMessage());
          SQLExplorerPlugin.error("Error creating result tab", e);
        }
      };
    });
  }

  private void closeStatement() {

    if (_stmt == null) {
      return;
    }
    if (_stmt != null) {
      try {
        _stmt.close();
      } catch (Exception e) {
        SQLExplorerPlugin.error("Error closing statement.", e);
      }
    }
    _stmt = null;

  }

  @Override
  protected void doExecution(IProgressMonitor monitor) throws Exception {
    int numErrors = 0;
    SQLException lastSQLException = null;

    try {
      long overallUpdateCount = 0;
      long overallStartTime = System.currentTimeMillis();
      for (Query query : getQueryParser()) {
        if (monitor.isCanceled())
          break;
        if (getEditor().isClosed())
          break;

        // Get the next bit of SQL to run and store it as "current"
        if (query == null)
          break;
        String querySQL = query.getQuerySql().toString();
        if (querySQL == null)
          continue;

        // Initialise
        setProgressMessage(Messages.getString("SQLResultsView.Executing"));
        final long startTime = System.currentTimeMillis();

        // Run it
        DatabaseProduct.ExecutionResults results = null;
        try {
          DatabaseProduct product = getEditor().getSession().getDatabaseProduct();
          boolean offlineMode = getEditor().getSession().getUser().isOfflineMode();
          if (!offlineMode) {
            try {
              results = product.executeQuery(_connection, query, _maxRows);
            } catch (RuntimeException e) {
              e.printStackTrace();
              throw new SQLException(e.getMessage());
            }
            final long endTime = System.currentTimeMillis();
            DataSet dataSet;
            boolean checkedForMessages = false;
            // IEditorInput input = getEditor().getEditorInput();
            // if (input != null && input instanceof SQLEditorInput &&
            // ((SQLEditorInput)input).getQuery() != null)
            // SQLExplorerPlugin.getDefault().getSQLHistory().addSQL(querySQL,
            // ((SQLEditorInput)input).getQuery());
            // else
            SQLExplorerPlugin.getDefault().getSQLHistory().addSQL(querySQL, _session);
            if (results == null) // cancelled
              return;
            while ((dataSet = results.nextDataSet()) != null) {

              // update sql result
              dataSet.setQuery(query);
              dataSet.setExecutionTime(endTime - startTime);

              // Save successfull query
              if (getQueryParser().getContext().isOn(ExecutionContext.LOG_SQL)) {
                SQLExplorerPlugin.getDefault().getSQLHistory().addSQL(querySQL, _session);
              }

              if (monitor.isCanceled())
                return;

              checkForMessages(query);
              checkedForMessages = true;

              // show results..
              displayResults(dataSet);
            }
            overallUpdateCount += results.getUpdateCount();

            if (!checkedForMessages)
              checkForMessages(query);
            debugLogQuery(query, null);
          } else {
            // IEditorInput input = getEditor().getEditorInput();
            // if (input != null && input instanceof SQLEditorInput &&
            // ((SQLEditorInput)input).getQuery() != null)
            // SQLExplorerPlugin.getDefault().getSQLHistory().addSQL(querySQL,
            // ((SQLEditorInput)input).getQuery());
            // else
            SQLExplorerPlugin.getDefault().getSQLHistory().addSQL(querySQL, _session);
            overallUpdateCount++;
          }
        } catch (final SQLException e) {
          e.printStackTrace();
          debugLogQuery(query, e);
          boolean stopOnError = SQLExplorerPlugin.getDefault().getPreferenceStore().getBoolean(
              IConstants.STOP_ON_ERROR);
          logException(e, query, stopOnError);
          closeStatement();
          hasMessages = true;
          if (stopOnError) {
            errorDialog(Messages.getString("SQLResultsView.Error.Title"), e.getMessage());
            return;
          }
          numErrors++;
          lastSQLException = e;

        } finally {
          try {
            if (results != null) {
              results.close();
              results = null;
            }
          } catch (SQLException e) {
            // Nothing
          }
        }
      }
      if (!hasMessages) {
        long overallTime = System.currentTimeMillis() - overallStartTime;
        String message = Messages.getString("SQLEditor.Overall.Prefix") + " "
            + Long.toString(overallTime) + " " + Messages.getString("SQLEditor.Update.Postfix");

        addMessage(new Message(Message.Status.STATUS, -1, 0, "", message));
      }
    } catch (Exception e) {
      closeStatement();
      throw e;
    }
    if (numErrors == 1)
      throw lastSQLException;
    else if (numErrors > 1
        && SQLExplorerPlugin.getDefault().getPreferenceStore().getBoolean(
            IConstants.CONFIRM_BOOL_SHOW_DIALOG_ON_QUERY_ERROR))
      getEditor().getSite().getShell().getDisplay().asyncExec(new Runnable() {
        public void run() {
          MessageDialogWithToggle dialog = MessageDialogWithToggle.openInformation(getEditor()
              .getSite().getShell(), Messages.getString("SQLExecution.Error.Title"), Messages
              .getString("SQLExecution.Error.Message"), Messages
              .getString("SQLExecution.Error.Toggle"), false, null, null);

          if (dialog.getToggleState() && dialog.getReturnCode() == IDialogConstants.OK_ID)
            AsteriskDiagramEditorPlugin.getDefault().getPluginPreferences().setValue(
                IConstants.CONFIRM_BOOL_SHOW_DIALOG_ON_QUERY_ERROR, false);
        }
      });
  }

  /**
   * Cancel sql execution and close execution tab.
   */
  @Override
  public void doStop() {

    if (_stmt != null) {
      try {
        _stmt.cancel();
      } catch (Exception e) {
        SQLExplorerPlugin.error("Error cancelling statement.", e);
      }
      try {
        closeStatement();
      } catch (Exception e) {
        SQLExplorerPlugin.error("Error closing statement.", e);
      }
    }

  }

}
