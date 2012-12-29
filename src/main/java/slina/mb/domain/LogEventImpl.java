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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class LogEventImpl implements LogEvent {
	
	private static final long serialVersionUID = 7635711682538927715L;
	private int year, month, day, hour,minute, seconds, miliseconds;
	private Loglevel logLevel;
	private String server;
	private String logClass;
	private String logMessage;
	private List<String> logDetails = new ArrayList<String>();
	private Calendar cal= GregorianCalendar.getInstance();


	public LogEventImpl() {
		super();
	}



	public LogEventImpl(String year, String month, String day, String hour, String minute,
			String seconds, String miliseconds, String logClass,
			String logMessage) {
		super();
		this.setYear(year.trim());
		this.setMonth(month.trim());
		this.setDay(day.trim());
		this.setHour(hour.trim());
		this.setMinute(minute.trim());
		this.setSeconds(seconds.trim());
		this.setMiliseconds(miliseconds.trim());
		this.logClass = logClass;
		this.logMessage = logMessage;
		this.cal.set(this.getYear(), this.getMonth(), this.getDay(), this.getHour(), this.getMinute(), this.getSeconds());
		this.cal.set(Calendar.MILLISECOND, this.getMiliseconds());
	}




	@Override
	public int getYear() {
		return year;
	}
	

	@Override
	public void setYear(String year) {
		this.year = Integer.parseInt(year);
	}
	

	@Override
	public int getMonth() {
		return month;
	}
	

	@Override
	public void setMonth(String month) {
		this.month = Integer.parseInt(month)-1;
	}

	@Override
	public int getDay() {
		return day;
	}
	

	@Override
	public void setDay(String day) {
		this.day = Integer.parseInt(day);
	}
	

	@Override
	public int getHour() {
		return hour;
	}

	@Override
	public void setHour(String hour) {
		this.hour = Integer.parseInt(hour);
	}
	

	@Override
	public int getMinute() {
		return minute;
	}
	

	@Override
	public void setMinute(String minute) {
		this.minute = Integer.parseInt(minute);
	}
	
	@Override
	public int getSeconds() {
		return seconds;
	}
	
	@Override
	public void setSeconds(String seconds) {
		this.seconds = Integer.parseInt(seconds);
	}
	
	@Override
	public int getMiliseconds() {
		return miliseconds;
	}
	
	@Override
	public void setMiliseconds(String miliseconds) {
		this.miliseconds = Integer.parseInt(miliseconds);
	}
	
	@Override
	public Loglevel getLogLevel() {
		return this.logLevel;
	}
	
	@Override
	public void setLogLevel(Loglevel logLevel) {

		this.logLevel = logLevel;
	}
	
	@Override
	public String getLogClass() {
		return logClass;
	}
	
	@Override
	public void setLogClass(String logClass) {
		this.logClass = logClass;
	}
	
	@Override
	public String getLogMessage() {
		return logMessage;
	}
	
	@Override
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	
	@Override
	public List<String> getLogDetails() {
		return this.logDetails;
	}
	
	@Override
	public void addLogDetails(String logDetails) {
		this.logDetails.add(logDetails);
	}
	 
	
	
	public Date getCal() {
		return cal.getTime();
	}



	public String toString() {
		
		StringBuilder sb = new StringBuilder();
	//	sb.append(this.year);
	//	sb.append("-");
	//	sb.append(this.month);
	//	sb.append("-");
	//	sb.append(this.day);
	//	sb.append(" ");
	//	sb.append(this.hour);
	//	sb.append(":");
	//	sb.append(this.minute);
	//	sb.append(":");
	//	sb.append(this.seconds);
	//	sb.append(":");
	//	sb.append(this.miliseconds);
		sb.append("\n");
		sb.append(this.getCal());
		sb.append("\n");
		sb.append(this.logLevel.getName());
		sb.append("\n");
		sb.append(this.logClass);
		sb.append("\n");
		sb.append(logMessage);
		sb.append("\n");
		sb.append("**********\n");
		for(String value:this.logDetails) {
			sb.append(value);
			sb.append("\n");
		}
		sb.append("**********\n");
		sb.append("\n");
		return sb.toString();
	}



	@Override
	public long getLongDate() {
		
		return this.getCal().getTime();
		
		
	}



	/**
	 * @return the server
	 */
	@Override
	public String getServer() {
		return server;
	}



	/**
	 * @param server the server to set
	 */
	@Override
	public void setServer(String server) {
		this.server = server;
	}



	

}
