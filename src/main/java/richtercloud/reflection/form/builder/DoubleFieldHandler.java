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
package richtercloud.reflection.form.builder;

import java.lang.reflect.Type;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author richter
 */
public class DoubleFieldHandler implements FieldHandler<Double, DoubleFieldUpdateEvent> {
    private final static DoubleFieldHandler INSTANCE = new DoubleFieldHandler();

    public static DoubleFieldHandler getInstance() {
        return INSTANCE;
    }

    protected DoubleFieldHandler() {
    }

    @Override
    public JComponent handle(Type type,
            Double fieldValue,
            final FieldUpdateListener<DoubleFieldUpdateEvent> updateListener,
            ReflectionFormBuilder reflectionFormBuilder) {
        //@TODO: handle validaton annotations (should cover all cases, so no
        // need to develop own annotations
        JSpinner retValue = new JSpinner(new SpinnerNumberModel((double) fieldValue, Double.MIN_VALUE, Double.MAX_VALUE, 0.1));
        retValue.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                updateListener.onUpdate(new DoubleFieldUpdateEvent((Double) ((JSpinner)e.getSource()).getValue()));
            }
        });
        return retValue;
    }

}
