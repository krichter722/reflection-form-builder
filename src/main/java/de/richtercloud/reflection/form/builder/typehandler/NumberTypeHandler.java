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
import java.lang.reflect.Type;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author richter
 */
public class NumberTypeHandler implements TypeHandler<Number, FieldUpdateEvent<Number>,ReflectionFormBuilder, JSpinner> {
    private final static NumberTypeHandler INSTANCE = new NumberTypeHandler();

    public static NumberTypeHandler getInstance() {
        return INSTANCE;
    }

    protected NumberTypeHandler() {
    }

    @Override
    public Pair<JComponent, ComponentHandler<?>> handle(Type type,
            Number fieldValue,
            String fieldName,
            Class<?> declaringClass,
            final FieldUpdateListener<FieldUpdateEvent<Number>> updateListener,
            ReflectionFormBuilder reflectionFormBuilder) throws FieldHandlingException {
        //@TODO: handle validaton annotations (should cover all cases, so no
        // need to develop own annotations
        JSpinner retValue = new JSpinner(new SpinnerNumberModel(fieldValue, null, null, 1));
        retValue.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                updateListener.onUpdate(new FieldUpdateEvent<>((Number) ((JSpinner)e.getSource()).getValue()));
            }
        });
        return new ImmutablePair<JComponent, ComponentHandler<?>>(retValue, this);
    }

    @Override
    public void reset(JSpinner component) {
        component.setValue(0);
    }
}
