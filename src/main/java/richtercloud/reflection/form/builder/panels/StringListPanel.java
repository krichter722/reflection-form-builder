/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package richtercloud.reflection.form.builder.panels;

import java.util.List;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import richtercloud.reflection.form.builder.ReflectionFormBuilder;

/**
 *
 * @author richter
 */
public class StringListPanel extends AbstractSingleColumnListPanel<String, EditableListPanelItemListener<String>, SingleColumnListPanelTableModel<String>> {

    private static final long serialVersionUID = 1L;

    public StringListPanel(ReflectionFormBuilder reflectionFormBuilder, List<String> initialValues) {
        super(reflectionFormBuilder,
                new StringListPanelCellEditor(),
                new StringListPanelCellRenderer(),
                AbstractSingleColumnListPanel.<String>createMainListModel(String.class));
        getMainListCellEditor().addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent e) {
                int row = StringListPanel.this.getMainList().getSelectedRow();
                if(row > -1) {
                    StringListPanel.this.getMainListModel().setValueAt((String) ((StringListPanelCellEditor) e.getSource()).getCellEditorValue(),
                            row,
                            0 //column
                    );
                    for(EditableListPanelItemListener<String> itemListener : StringListPanel.this.getItemListeners()) {
                        itemListener.onItemChanged(new ListPanelItemEvent<>(ListPanelItemEvent.EVENT_TYPE_CHANGED, row, StringListPanel.this.getMainListModel().getData()));
                    }
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                //do nothing because there're no changes to the model
            }
        });
        if(initialValues != null) {
            for(String initialValue : initialValues) {
                this.getMainListModel().setValueAt(initialValue, this.getMainListModel().getRowCount(), 0);
            }
        }
    }

    @Override
    protected String createNewElement() {
        return new String();
    }

}