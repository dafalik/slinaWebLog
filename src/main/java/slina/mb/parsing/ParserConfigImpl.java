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
package slina.mb.parsing;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import slina.mb.domain.LogEvent;
import slina.mb.domain.LogEventImpl;
import slina.mb.domain.Loglevel;

/**
 * @author amaraa1
 *
 */
public class ParserConfigImpl implements ParserConfig {
	
	
	private static final Logger LOGGER = Logger.getLogger(Log4jParserImpl.class);
	private  String regex;
	private Map<String, Loglevel> logLevelLokup = new HashMap<String, Loglevel>();
	private Pattern pattern;
	private int id, year, month, day, hour,minute, seconds, miliseconds, logClass, logLevel, logMessage;
	private Map<Integer, String> cutomStringsMap = new HashMap<Integer,String>();
	protected boolean custom = false;
	
	
	public ParserConfigImpl(String regex, Map<String, Loglevel> logLevelLokup) {
		super();
		this.regex = regex;
		this.logLevelLokup = logLevelLokup;
		this.pattern = Pattern.compile(this.regex);
	}
	

	
	public Loglevel findLogLevel(String logLevel) {
		
		if(logLevel==null || logLevel.length()==0) {
			LOGGER.info("empty log level ");
			return Loglevel.UNDEFINED;
		}
		
		Loglevel level = this.logLevelLokup.get(logLevel.trim().toUpperCase());
		
		if(level == null) {
			LOGGER.info("undefined log level "+logLevel);
			return Loglevel.UNDEFINED;
		}		
		return level;
	}

	
	public LogEvent makeLogEvent(Matcher matcher) {
		
		if(this.custom) {
			return this.createCustomLogEvent(matcher);
		}
			
		return this.createLogEvent(matcher);
		
	}
	
	

	public LogEvent createLogEvent(Matcher matcher) {
		
		
		LogEvent le = new LogEventImpl(
				matcher.group(this.year),
				matcher.group(this.month),
				matcher.group(this.day),
				matcher.group(this.hour),
				matcher.group(this.minute),
				matcher.group(this.seconds),
				matcher.group(this.miliseconds),
				matcher.group(this.logClass).trim(), 
				matcher.group(this.logMessage).trim()
				);
		
		String logLevelString = matcher.group(this.logLevel).trim();
		Loglevel loglevel = this.findLogLevel(logLevelString);
		le.setLogLevel(loglevel);
		
		return le;
		
	}
	
	
	private LogEvent createCustomLogEvent(Matcher matcher) {
		
		
		String year = this.getValue(0, this.year, matcher);
		String month = this.getValue(1, this.month, matcher);
		String day = this.getValue(2, this.day, matcher);
		String hour = this.getValue(3, this.hour, matcher);
		String minute = this.getValue(4, this.minute, matcher);
		String second = this.getValue(5, this.seconds, matcher);
		String miliSeconds = this.getValue(6, this.miliseconds, matcher);
		String logClass = this.getValue(7, this.logClass, matcher).trim();
		String logMessage = this.getValue(8, this.logMessage, matcher).trim();
		String logLevelString = this.getValue(9, this.logLevel, matcher);

		LogEvent le = new LogEventImpl(year, month, day, hour, minute, second, miliSeconds, logClass, logMessage);
		
		Loglevel loglevel = this.findLogLevel(logLevelString);
		le.setLogLevel(loglevel);		
		return le;
		
	}
	
	private String getValue( int customIndex, int index, Matcher matcher ) {

		
		if(index == -1) {
			return this.cutomStringsMap.get(customIndex);
		} 

		return matcher.group(index);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setYear(int year) { 	this.year = year;  }

	public void setMonth(int month) {    this.month = month;   }

	public void setDay(int day) {		this.day = day;   	}

	public void setHour(int hour) {		this.hour = hour;	}

	public void setMinute(int minute) {		this.minute = minute; 	}

	public void setSeconds(int seconds) {		this.seconds = seconds;	}

	public void setMiliseconds(int miliseconds) {		this.miliseconds = miliseconds; 	}
	
	public void setLogClass(int logClass) {		this.logClass = logClass; 	}

	public void setLogLevel(int logLevel) {		this.logLevel = logLevel;	}

	@Override
	public Pattern getPattern() {		return this.pattern;	}

	public void setLogLevelLokup(Map<String, Loglevel> logLevelLokup) {		this.logLevelLokup = logLevelLokup; 	}

	@Override
	public Pattern getFilterPattern() {
		throw new RuntimeException("getFilterPattern: Should not call this method");	
	}

	@Override
	public boolean searchForKeyWords(String searchString) {
				throw new RuntimeException("searchForKeyWords: Should not call this method");		
	}
	
}
