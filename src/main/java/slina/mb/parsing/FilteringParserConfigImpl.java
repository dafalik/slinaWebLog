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


import java.util.LinkedList;
import java.util.List;
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
public class FilteringParserConfigImpl  implements ParserConfig {
	
	
	private static final Logger LOGGER = Logger.getLogger(FilteringParserConfigImpl.class);

	protected  String regex;
	protected  String dateRegex;
	protected LogLevelMap logLevelLookup ;
	protected Pattern pattern, datePattern;
	protected int id, year, month, day, hour,minute, seconds, miliseconds, logClass, logLevel, logMessage;
	List<String> searchList = new LinkedList<String>();

	
	public FilteringParserConfigImpl(String regex, String dateRegex, LogLevelMap logLevelLookup, String[] keywordsList) {
		this.regex = regex;
		this.dateRegex = dateRegex;
		this.logLevelLookup = logLevelLookup;
		this.createRegexPatterns();		
		this.createSearchList(keywordsList);
		
	}


	private void createRegexPatterns() {
		this.pattern = Pattern.compile(this.regex);
		this.datePattern = Pattern.compile(this.dateRegex);
	}

	private void createSearchList(String[] keywordsList) {
		
		for(String keyWords: keywordsList) {
			searchList.add(keyWords);
		}
	}

	@Override
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

	@Override
	public boolean searchForKeyWords(String searchString) {
		
		for(String keyWord:this.searchList) {
			if(searchString.contains(keyWord))
				return true;
		}		
		return false;
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

	@Override
	public Pattern getPattern() {		return this.pattern;	}

	public void setLogMessage(int logMessage) {   	this.logMessage = logMessage;   }

	public void setLogLevelLookup(LogLevelMap logLevelLookup) {		this.logLevelLookup = logLevelLookup;	}

	@Override
	public Pattern getFilterPattern() {		return this.datePattern;	}


	
	
	
}
