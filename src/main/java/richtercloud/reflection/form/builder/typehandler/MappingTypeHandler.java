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
package richtercloud.reflection.form.builder.typehandler;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import org.apache.commons.lang3.tuple.Pair;
import richtercloud.reflection.form.builder.ComponentHandler;
import richtercloud.reflection.form.builder.ReflectionFormBuilder;
import richtercloud.reflection.form.builder.fieldhandler.FieldHandlingException;
import richtercloud.reflection.form.builder.fieldhandler.FieldUpdateEvent;
import richtercloud.reflection.form.builder.fieldhandler.FieldUpdateListener;
import richtercloud.validation.tools.FieldRetrievalException;

/**
 *
 * @author richter
 */
public class MappingTypeHandler<T, E extends FieldUpdateEvent<T>, R extends ReflectionFormBuilder> implements TypeHandler<T,E,R, Component>{
    private Map<Type, TypeHandler<T,E,R, Component>> classMapping = new HashMap<>();
    /**
     * Since the type handler delegates to mapped type handlers it's sufficient
     * to track the created components internally.
     */
    private final Map<JComponent, ComponentHandler<?>> componentMapping = new HashMap<>();

    @Override
    @SuppressWarnings("FinalMethod") //enforce everything being handled in handle0
    public final Pair<JComponent, ComponentHandler<?>> handle(Type type,
            T fieldValue,
            String fieldName,
            Class<?> declaringClass,
            FieldUpdateListener<E> updateListener,
            R reflectionFormBuilder) throws IllegalArgumentException, IllegalAccessException, FieldHandlingException, InstantiationException, InvocationTargetException, FieldRetrievalException {
        Pair<JComponent, ComponentHandler<?>> retValueEntry = handle0(type, fieldValue, fieldName, declaringClass, updateListener, reflectionFormBuilder);
        if(retValueEntry == null) {
            throw new IllegalArgumentException("handle0 mustn't return null");
        }
        ComponentHandler<?> componentResettable = retValueEntry.getValue();
        if(componentResettable == null) {
            throw new IllegalArgumentException("ComponentResettable in Pair returned by handle0 mustn't be null");
        }
        JComponent retValue = retValueEntry.getKey();
        this.componentMapping.put(retValue, componentResettable);
        return retValueEntry;
    }

    protected Pair<JComponent, ComponentHandler<?>> handle0(Type type,
            T fieldValue,
            String fieldName,
            Class<?> declaringClass,
            FieldUpdateListener<E> updateListener,
            R reflectionFormBuilder) throws IllegalArgumentException, IllegalAccessException, FieldHandlingException, InstantiationException, InvocationTargetException, FieldRetrievalException {
        TypeHandler<T,E,R, Component> typeHandler = classMapping.get(type);
        if(typeHandler == null) {
            throw new IllegalArgumentException(String.format("Type '%s' isn't mapped.", type));
        }
        Pair<JComponent, ComponentHandler<?>> retValue = typeHandler.handle(type,
                fieldValue,
                fieldName,
                declaringClass,
                updateListener,
                reflectionFormBuilder);
        return retValue;
    }

    @Override
    public void reset(Component component) throws FieldRetrievalException {
        ComponentHandler componentResettable = this.componentMapping.get(component);
        componentResettable.reset(component);
    }
}
