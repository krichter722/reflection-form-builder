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

/**
 * A listener to track adding and removing from selection lists in different
 * list panels.
 *
 * @author richter
 * @param <T> the type of items to handle
 */
public interface ListPanelItemListener<T> {

    void onItemAdded(ListPanelItemEvent<T> event) throws ListPanelItemEventVetoException;

    void onItemRemoved(ListPanelItemEvent<T> event) throws ListPanelItemEventVetoException;
}
