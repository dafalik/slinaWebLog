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
package slina.mb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface LogEvent extends Serializable {

	public abstract int getYear();

	public abstract void setYear(String year);

	public abstract int getMonth();

	public abstract void setMonth(String month);

	public abstract int getDay();

	public abstract void setDay(String day);

	public abstract int getHour();

	public abstract void setHour(String hour);

	public abstract int getMinute();

	public abstract void setMinute(String minute);

	public abstract int getSeconds();

	public abstract void setSeconds(String seconds);

	public abstract int getMiliseconds();

	public abstract void setMiliseconds(String miliseconds);

	public abstract Loglevel getLogLevel();

	public abstract void setLogLevel(Loglevel logLevel);

	public abstract String getLogClass();

	public abstract void setLogClass(String logClass);

	public abstract String getLogMessage();

	public abstract void setLogMessage(String logMessage);

	public abstract List<String> getLogDetails();

	public abstract void addLogDetails(String logDetails);
	
	public Date getCal();
	
	public long getLongDate();

	public abstract void setServer(String server);

	public abstract String getServer();

}