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

import de.richtercloud.reflection.form.builder.ComponentHandler;
import de.richtercloud.reflection.form.builder.ReflectionFormBuilder;
import de.richtercloud.reflection.form.builder.fieldhandler.FieldHandlingException;
import de.richtercloud.reflection.form.builder.fieldhandler.FieldUpdateEvent;
import de.richtercloud.reflection.form.builder.fieldhandler.FieldUpdateListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Type;
import javax.swing.JComponent;
import javax.swing.JTextField;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author richter
 */
public class StringTypeHandler implements TypeHandler<String, FieldUpdateEvent<String>,ReflectionFormBuilder, JTextField> {
    private final static StringTypeHandler INSTANCE = new StringTypeHandler();

    public static StringTypeHandler getInstance() {
        return INSTANCE;
    }

    protected StringTypeHandler() {
    }

    @Override
    public Pair<JComponent, ComponentHandler<?>> handle(Type type,
            String fieldValue,
            String fieldName,
            Class<?> declaringClass,
            final FieldUpdateListener<FieldUpdateEvent<String>> updateListener,
            ReflectionFormBuilder reflectionFormBuilder) throws FieldHandlingException {
        final JTextField retValue = new JTextField(fieldValue);
        retValue.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                updateListener.onUpdate(new FieldUpdateEvent<>(retValue.getText()));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                updateListener.onUpdate(new FieldUpdateEvent<>(retValue.getText()));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateListener.onUpdate(new FieldUpdateEvent<>(retValue.getText()));
            }
        }); //action listener doesn't register text change events with keyboard
        return  new ImmutablePair<JComponent, ComponentHandler<?>>(retValue, this);
    }

    @Override
    public void reset(JTextField component) {
        component.setText("");
    }
}
