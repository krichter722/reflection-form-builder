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
package de.richtercloud.reflection.form.builder.typehandler;

import de.richtercloud.message.handler.IssueHandler;
import de.richtercloud.reflection.form.builder.ComponentHandler;
import de.richtercloud.reflection.form.builder.ReflectionFormBuilder;
import de.richtercloud.reflection.form.builder.fieldhandler.FieldUpdateEvent;
import de.richtercloud.reflection.form.builder.fieldhandler.FieldUpdateListener;
import de.richtercloud.reflection.form.builder.panels.EditableListPanelItemListener;
import de.richtercloud.reflection.form.builder.panels.ListPanelItemEvent;
import de.richtercloud.reflection.form.builder.panels.StringListPanel;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author richter
 */
public class StringListTypeHandler extends AbstractListTypeHandler<List<String>, FieldUpdateEvent<List<String>>,ReflectionFormBuilder>{

    public StringListTypeHandler(IssueHandler issueHandler) {
        super(issueHandler);
    }

    @Override
    public Pair<JComponent, ComponentHandler<?>> handle0(Type type,
            List<String> fieldValue,
            final FieldUpdateListener<FieldUpdateEvent<List<String>>> updateListener,
            ReflectionFormBuilder reflectionFormBuilder) {
        StringListPanel retValue = new StringListPanel(reflectionFormBuilder,
                fieldValue,
                getIssueHandler());
        retValue.addItemListener(new EditableListPanelItemListener<String>() {

            @Override
            public void onItemChanged(ListPanelItemEvent<String> event) {
                updateListener.onUpdate(new FieldUpdateEvent<List<String>>(new LinkedList<>(event.getItem())));
            }

            @Override
            public void onItemAdded(ListPanelItemEvent<String> event) {
                updateListener.onUpdate(new FieldUpdateEvent<List<String>>(new LinkedList<>(event.getItem())));
            }

            @Override
            public void onItemRemoved(ListPanelItemEvent<String> event) {
                updateListener.onUpdate(new FieldUpdateEvent<List<String>>(new LinkedList<>(event.getItem())));
            }
        });
        return new ImmutablePair<JComponent, ComponentHandler<?>>(retValue, this);
    }
}
