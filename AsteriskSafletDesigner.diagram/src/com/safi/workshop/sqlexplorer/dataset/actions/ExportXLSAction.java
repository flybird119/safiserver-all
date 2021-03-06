/*
 * Copyright (C) 2006 Davy Vanherbergen
 * dvanherbergen@users.sourceforge.net
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
package com.safi.workshop.sqlexplorer.dataset.actions;

import java.io.File;
import java.io.PrintStream;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;

import com.safi.workshop.sqlexplorer.Messages;
import com.safi.workshop.sqlexplorer.dataset.DataSet;
import com.safi.workshop.sqlexplorer.dataset.DataSetRow;
import com.safi.workshop.sqlexplorer.dialogs.XlsExportOptionsDlg;
import com.safi.workshop.sqlexplorer.plugin.SQLExplorerPlugin;
import com.safi.workshop.sqlexplorer.sqleditor.results.CellRange.Column;
import com.safi.workshop.sqlexplorer.util.ImageUtil;
import com.safi.workshop.sqlexplorer.util.TextUtil;

/**
 * Copy an entire datasettable to the clipboard.
 * 
 * @author Davy Vanherbergen
 */
public class ExportXLSAction extends AbstractDataSetTableContextAction {

  private static final ImageDescriptor _image = ImageUtil.getDescriptor("Images.ExportIcon");

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.action.IAction#getText()
   */
  @Override
  public String getText() {
    return Messages.getString("DataSetTable.Actions.Export.XLS");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.action.IAction#getImageDescriptor()
   */
  @Override
  public ImageDescriptor getImageDescriptor() {
    return _image;
  }

  /**
   * Copy all table data to clipboard
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {

    final XlsExportOptionsDlg dlg = new XlsExportOptionsDlg(_table.getShell());
    if (dlg.open() != Window.OK)
      return;

    BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {

      public void run() {

        try {

          File file = new File(dlg.getFilename());

          if (file.exists()) {
            // overwrite existing files
            file.delete();
          }

          file.createNewFile();
          PrintStream writer = new PrintStream(file, dlg.getCharacterSet());
          StringBuffer buffer = new StringBuffer("");

          // get preferences
          boolean includeColumnNames = dlg.includeHeaders();
          boolean rtrim = dlg.trimSpaces();
          boolean quote = dlg.quoteText();
          String nullValue = dlg.getNullValue();

          DataSet dataSet = (DataSet) _table.getData();

          if (dataSet == null) {
            return;
          }

          writer.println("<table>");

          // export column names
          if (includeColumnNames) {

            buffer.append("<tr>");
            DataSet.Column[] columns = dataSet.getColumns();
            for (Column column : columns) {
              buffer.append("<th>");
              buffer.append(TextUtil.htmlEscape(column.getCaption()));
              buffer.append("</th>");
            }
            buffer.append("</tr>");
            writer.println(buffer.toString());
          }

          // export column data
          int columnCount = _table.getColumnCount();
          for (int i = 0; i < dataSet.getRows().length; i++) {

            buffer = new StringBuffer("<tr>");
            DataSetRow row = dataSet.getRow(i);

            for (int j = 0; j < columnCount; j++) {
              buffer.append("<td>");
              Object o = row.getCellValue(j);
              String t = o == null ? nullValue : o.toString();
              if (rtrim)
                t = TextUtil.rtrim(t);
              if (quote && o instanceof String) {
                buffer.append("\"");
                buffer.append(TextUtil.htmlEscape(t));
                buffer.append("\"");
              } else
                buffer.append(TextUtil.htmlEscape(t));
              buffer.append("</td>");
            }

            buffer.append("</tr>");

            writer.println(buffer.toString());
          }

          writer.println("</table>");

          writer.close();

        } catch (final Exception e) {
          _table.getShell().getDisplay().asyncExec(new Runnable() {

            public void run() {
              MessageDialog.openError(_table.getShell(), Messages
                  .getString("SQLResultsView.Error.Export.Title"), e.getMessage());
              SQLExplorerPlugin.error(Messages.getString("SQLResultsView.Error.Export.Title"), e);
            }
          });
        }
      }
    });

  }

}
