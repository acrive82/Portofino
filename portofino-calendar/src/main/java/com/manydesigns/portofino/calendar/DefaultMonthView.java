/*
 * Copyright (C) 2005-2016 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package com.manydesigns.portofino.calendar;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

/**
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
public class DefaultMonthView extends AbstractMonthView<DefaultWeek> {
    public static final String copyright =
            "Copyright (C) 2005-2016, ManyDesigns srl";

    public DefaultMonthView(DateTime referenceDateTime) {
        super(referenceDateTime);
    }

    public DefaultMonthView(DateTime referenceDateTime, int firstDayOfWeek) {
        super(referenceDateTime, firstDayOfWeek);
    }

    @Override
    protected DefaultWeek[] createWeeksArray(int size) {
        return new DefaultWeek[size];
    }

    @Override
    protected DefaultWeek createWeek(DateMidnight weekStart, DateMidnight weekEnd) {
        return new DefaultWeek(weekStart, weekEnd);
    }

}
