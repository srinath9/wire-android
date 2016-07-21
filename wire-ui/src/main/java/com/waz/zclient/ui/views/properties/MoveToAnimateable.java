/**
 * Wire
 * Copyright (C) 2016 Wire Swiss GmbH
 *
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
package com.waz.zclient.ui.views.properties;

import android.util.Property;

public interface MoveToAnimateable {

    Property<MoveToAnimateable, Float> MOVE_TO = new Property<MoveToAnimateable, Float>(Float.class, "moveTo") {
        @Override
        public Float get(MoveToAnimateable object) {
            return object.getMoveTo();
        }

        @Override
        public void set(MoveToAnimateable object, Float value) {
            object.setMoveTo(value);
        }
    };

    float getMoveTo();

    void setMoveTo(float value);
}
