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

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

/*
internal implementation notes:
- There's no sense in providing a generic id panel for numeric ids because
type of SpinnerNumberModel is determined at initialization.
- There's no sense to specify a type of NumberPanelUpdateListener because it
can't be initialized
- Since horizontal and vertical group of layout need to be exposed in order to
allow adding components in subclasses this class can't be designed in the
NetBeans GUI builder because it shouldn't influence the programming style
- in order to avoid intialValue overwriting the value represented by components
and because it doesn't have a disadvantage there's no simple getter for the
value property by a retrieval method retrieveValue which evaluates component
state -> value isn't changed after component update (only the UI state)
- Since the value is made of multiple components, don't expose them publicly,
but provide methods to retrieve and set the value only.
*/
/**
 * A component to generate ids for entity instances. Ids are only generated for
 * valid instances (i.e. after validation with Java Validation API). This
 * restriction isn't necessary, but generation of intermediate ids on invalid
 * instances doesn't make sense.
 *
 * @author richter
 * @param <N> the type of {@link Number}s to manage
 */
@SuppressWarnings("PMD.AccessorMethodGeneration")
public abstract class NumberPanel<N extends Number> extends JPanel {
    private static final long serialVersionUID = 1L;
    private final SpinnerNumberModel idSpinnerModel = new SpinnerNumberModel((Long)0L, (Long)0L, (Long)Long.MAX_VALUE, (Long)1L); //the cast to Long is necessary otherwise Doubles are retrieved from component later
    private final Set<NumberPanelUpdateListener<N>> updateListeners = new HashSet<>();
    private Group layoutHorizontalGroup;
    private Group layoutVerticalGroup;
    private JSpinner valueSpinner;
    private JCheckBox nullCheckBox;
    private final Number initialValue;

    /**
     * Creates a {@code NumberPanel}.
     * @param initialValue the initial value
     * @param readOnly if {@code true} there'll be no possibility to set the
     *     value in the GUI, but programmatically and the value will be
     *     displayed and updated
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public NumberPanel(N initialValue,
            boolean readOnly) {
        super();
        initComponents();
        this.valueSpinner.addChangeListener((ChangeEvent e) -> {
            for(NumberPanelUpdateListener<N> updateListener : updateListeners) {
                updateListener.onUpdate(new NumberPanelUpdateEvent<>(getValue()));
            }
        });
        this.nullCheckBox.addActionListener((ActionEvent e) -> {
            for(NumberPanelUpdateListener<N> updateListener : updateListeners) {
                updateListener.onUpdate(new NumberPanelUpdateEvent<>(getValue()));
            }
        });
        this.initialValue = initialValue;
        if(readOnly) {
            this.valueSpinner.setEnabled(false);
        }
        reset0();
    }

    public void addUpdateListener(NumberPanelUpdateListener<N> updateListener) {
        this.updateListeners.add(updateListener);
    }

    public void removeUpdateListener(NumberPanelUpdateListener<N> updateListener) {
        this.updateListeners.remove(updateListener);
    }

    public Set<NumberPanelUpdateListener<N>> getUpdateListeners() {
        return Collections.unmodifiableSet(updateListeners);
    }

    public N getValue() {
        return (N) (nullCheckBox.isSelected() ? null : valueSpinner.getValue());
    }

    /**
     * Sets the value no matter whether the panel is read-only or not.
     * @param value the value to set
     */
    public void setValue(N value) {
        if(value == null) {
            nullCheckBox.setSelected(true);
            valueSpinner.setEnabled(false);
            //don't set value to 0 because it will trigger more than one update
            //event
        }else {
            nullCheckBox.setSelected(false);
            valueSpinner.setEnabled(true);
            valueSpinner.setValue(value);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        valueSpinner = new JSpinner();
        nullCheckBox = new JCheckBox();

        valueSpinner.setModel(this.idSpinnerModel);

        nullCheckBox.setText("null");
        nullCheckBox.addActionListener((ActionEvent evt) -> {
            nullCheckBoxActionPerformed(evt);
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        this.layoutHorizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        this.layoutVerticalGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        layout.setHorizontalGroup(this.layoutHorizontalGroup
            .addGroup(layout.createSequentialGroup()
                .addComponent(nullCheckBox)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(valueSpinner,
                        0,
                        GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE))
        );
        layout.setVerticalGroup(this.layoutVerticalGroup
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(valueSpinner,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addComponent(nullCheckBox))
        );
    }

    protected Group getLayoutHorizontalGroup() {
        return layoutHorizontalGroup;
    }

    protected Group getLayoutVerticalGroup() {
        return layoutVerticalGroup;
    }

    @Override
    public GroupLayout getLayout() {
        return (GroupLayout) super.getLayout();
    }

    @SuppressWarnings({"PMD.UnusedFormalParameter",
        "PMD.AvoidInstantiatingObjectsInLoops"
    })
    private void nullCheckBoxActionPerformed(ActionEvent evt) {
        if(nullCheckBox.isSelected()) {
            this.valueSpinner.setEnabled(false);
        }else {
            this.valueSpinner.setEnabled(true);
        }
        for(NumberPanelUpdateListener<N> updateListener : this.updateListeners) {
            updateListener.onUpdate(new NumberPanelUpdateEvent<>(getValue()));
        }
    }

    public void reset() {
        reset0();
    }

    private void reset0() {
        if(initialValue == null) {
            this.nullCheckBox.setSelected(true);
            this.valueSpinner.setEnabled(false);
        }else {
            this.valueSpinner.setValue(initialValue);
            this.nullCheckBox.setSelected(false); //might not be necessary, but is clearer
        }
    }
}
