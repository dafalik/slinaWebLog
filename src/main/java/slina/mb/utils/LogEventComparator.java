/*
 *	 This file is part of Slina web log.
 *
 *   Slina web log is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Slina web log is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package slina.mb.utils;


import java.util.Comparator;

import slina.mb.domain.LogEvent;

public class LogEventComparator implements Comparator<LogEvent> {

	@Override
	public int compare(LogEvent arg0, LogEvent arg1) {
		
		if(arg0 == null)
			return 1;
		
		if(arg1==null)
			return -1;
		
		
		return arg0.getCal().compareTo(arg1.getCal());
		
		
	}

}
