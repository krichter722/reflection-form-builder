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
package de.richtercloud.reflection.form.builder.fieldhandler;

import de.richtercloud.message.handler.IssueHandler;
import de.richtercloud.message.handler.MessageHandler;
import de.richtercloud.reflection.form.builder.ReflectionFormBuilder;
import de.richtercloud.reflection.form.builder.panels.AbstractListPanel;
import de.richtercloud.reflection.form.builder.typehandler.IntegerListTypeHandler;
import de.richtercloud.reflection.form.builder.typehandler.TypeHandler;
import java.util.List;

/**
 *
 * @author richter
 */
public class IntegerListFieldHandler extends AbstractListFieldHandler<List<Integer>, FieldUpdateEvent<List<Integer>>, ReflectionFormBuilder> implements FieldHandler<List<Integer>, FieldUpdateEvent<List<Integer>>, ReflectionFormBuilder, AbstractListPanel>{

    public IntegerListFieldHandler(IssueHandler issueHandler) {
        super(issueHandler,
                new IntegerListTypeHandler(issueHandler));
    }

    public IntegerListFieldHandler(MessageHandler messageHandler,
            TypeHandler<List<Integer>, FieldUpdateEvent<List<Integer>>,ReflectionFormBuilder, AbstractListPanel> typeHandler) {
        super(messageHandler, typeHandler);
    }
}
