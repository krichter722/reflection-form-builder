/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.richtercloud.reflection.form.builder.panels;

import de.richtercloud.message.handler.MessageHandler;
import de.richtercloud.reflection.form.builder.ReflectionFormBuilder;
import java.util.List;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * An {@link AbstractListPanel} which displays only editable components in one
 * column.
 *
 * @author richter
 * @param <T> the type of the managed values
 * @param <L> the type of the event listener to use
 * @param <M> the type of table model
 * @param <R> the type of reflection form builder to use
 */
public abstract class AbstractSingleColumnListPanel<T, L extends ListPanelItemListener<T>, M extends SingleColumnListPanelTableModel<T>, R extends ReflectionFormBuilder> extends AbstractListPanel<T, L, M, R> {
    private static final long serialVersionUID = 1L;

    private static DefaultTableColumnModel createMainListColumnModel(ListPanelTableCellEditor mainListCellEditor, ListPanelTableCellRenderer mainListCellRenderer) {
        DefaultTableColumnModel mainListColumnModel = new DefaultTableColumnModel();
        mainListColumnModel.addColumn(new TableColumn(0,
                100,
                mainListCellRenderer,
                mainListCellEditor));
        return mainListColumnModel;
    }

    protected static <X> SingleColumnListPanelTableModel<X> createMainListModel(Class<? extends X> columnClass) {
        SingleColumnListPanelTableModel<X> mainListModel = new SingleColumnListPanelTableModel<>(columnClass);
        mainListModel.addColumn("");
        return mainListModel;
    }

    public  AbstractSingleColumnListPanel(R reflectionFormBuilder,
            ListPanelTableCellEditor mainListCellEditor,
            ListPanelTableCellRenderer mainListCellRenderer,
            M mainListModel,
            List<T> initialValues,
            MessageHandler messageHandler) {
        super(reflectionFormBuilder,
                mainListCellEditor,
                mainListCellRenderer,
                mainListModel,
                initialValues,
                messageHandler,
                new RightHeightTableHeader(createMainListColumnModel(mainListCellEditor, mainListCellRenderer), 0));
        updateRowHeights(); //necessary after adding initial values
    }

    @Override
    protected void editRow() {
        this.getMainList().editCellAt(this.getMainList().getSelectedRow(),
                this.getMainList().getSelectedColumn());
    }

}
