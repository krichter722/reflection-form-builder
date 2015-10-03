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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Type;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author richter
 */
public class StringFieldHandler implements FieldHandler<String, StringFieldUpdateEvent> {
    private final static StringFieldHandler INSTANCE = new StringFieldHandler();

    public static StringFieldHandler getInstance() {
        return INSTANCE;
    }

    protected StringFieldHandler() {
    }

    @Override
    public JComponent handle(Type type, String fieldValue, final FieldUpdateListener<StringFieldUpdateEvent> updateListener, ReflectionFormBuilder reflectionFormBuilder) {
        final JTextField retValue = new JTextField(fieldValue);
        retValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateListener.onUpdate(new StringFieldUpdateEvent(retValue.getText()));
            }
        });
        return  retValue;
    }

}
