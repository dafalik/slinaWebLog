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


import java.util.List;
import java.util.Map;

import slina.mb.domain.LogEvent;
import slina.mb.domain.Loglevel;

public interface CustomLogParser {

	public abstract List<LogEvent> createLogEvents(List<String> filelist);

	public abstract void setRegex(String regex);

	public abstract void setLogLevelLokup(Map<String, Loglevel> logLevelLokup);

	public abstract Map<String, Loglevel> getLogLevelLokup();

}