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
public class StandardParserConfigImpl  implements ParserConfig {
	
	
	private static final Logger LOGGER = Logger.getLogger(StandardParserConfigImpl.class);

	protected  String regex;
	protected LogLevelMap logLevelLookup ;
	protected Pattern pattern;
	protected int id, year, month, day, hour,minute, seconds, miliseconds, logClass, logLevel, logMessage;
	

	
	public StandardParserConfigImpl(String regex, LogLevelMap logLevelLookup) {
		this.regex = regex;
		this.logLevelLookup = logLevelLookup;
		this.pattern = Pattern.compile(this.regex);
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
		
		//LOGGER.info("Created log event ==> "+le.toString());
		
		return le;
		
	}


	protected Loglevel findLogLevel(String logLevel) {
		
		if(logLevel==null || logLevel.length()==0) {
			LOGGER.info("empty log level ");
			return Loglevel.UNDEFINED;
		}
		
		Loglevel level = this.logLevelLookup.get(logLevel.trim().toUpperCase());
		
		if(level == null) {
			LOGGER.info("undefined log level "+logLevel);
			return Loglevel.UNDEFINED;
		}		
		return level;
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

	public Pattern getPattern() {		return this.pattern;	}

	public void setLogMessage(int logMessage) {   	this.logMessage = logMessage;   }

	public void setLogLevelLookup(LogLevelMap logLevelLookup) {		this.logLevelLookup = logLevelLookup;	}

	@Override
	public Pattern getFilterPattern() {
		throw new RuntimeException("getFilterPattern: Should not call this method");	
	}

	@Override
	public boolean searchForKeyWords(String searchString) {
				throw new RuntimeException("searchForKeyWords: Should not call this method");		
	}
	
}
