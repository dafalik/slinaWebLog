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

import slina.mb.domain.LogEvent;
import slina.mb.domain.LogEventImpl;
import slina.mb.domain.Loglevel;

/**
 * @author amaraa1
 *
 */
public class CustomParserConfigImpl extends StandardParserConfigImpl {

//	private static final Logger LOGGER = Logger.getLogger(CustomParserConfigImpl.class);
	
	private final String YEAR = "YEAR";
	private final String MONTH = "MONTH";
	private final String DAY = "DAY";
	private final String HOUR = "HOUR";
	private final String MINUTE = "MINUTE";
	private final String SECOND = "SECOND";
	private final String MILI_SECOND = "MILI_SECOND";
	private final String LOG_CLASS = "LOG_CLASS";
	private final String LOG_MESSAGE = "LOG_MESSAGE";
	private final String LOG_LEVEL = "LOG_LEVEL";
	
	protected Map<String, String> replacementMap = new HashMap<String, String>();
	
	
	
	public CustomParserConfigImpl(String regex, LogLevelMap logLevelLookup) {
		super(regex, logLevelLookup);
	}
	
	@Override
	public LogEvent createLogEvent(Matcher matcher) {
		
		
		
		String yearValue = this.getValue(matcher, this.year, YEAR);
		String monthValue = this.getValue(matcher, this.month, MONTH);
		String dayValue = this.getValue(matcher, this.day, DAY);
		String hourValue = this.getValue(matcher, this.hour, HOUR);
		String minuteValue = this.getValue(matcher, this.minute, MINUTE);
		String secondValue = this.getValue(matcher, this.seconds, SECOND);
		String miliSecondValue = this.getValue(matcher, this.miliseconds, MILI_SECOND);
		String logClassValue = this.getValue(matcher, this.logClass, LOG_CLASS);
		String logMessageValue = this.getValue(matcher, this.logMessage, LOG_MESSAGE);
		String logLevelValue = this.getValue(matcher, this.logLevel, LOG_LEVEL);
		
		LogEvent le = new LogEventImpl(
				
				yearValue,
				monthValue,
				dayValue,
				hourValue,
				minuteValue,
				secondValue,
				miliSecondValue,
				logClassValue.trim(), 
				logMessageValue.trim()
				);
		
		String logLevelString = logLevelValue.trim();
		Loglevel loglevel = this.findLogLevel(logLevelString);
		le.setLogLevel(loglevel);
		
		return le;
		
	}


	private String getValue(Matcher matcher, int index, String replacementLookup) {
		
		String value;
		
		if(index == -1) {
			value = this.replacementMap.get(replacementLookup);
		}
		else {
			value = matcher.group(index);
		}		
		return value;
	}

	public void setReplacementMap(Map<String, String> replacementMap) {
		this.replacementMap = replacementMap;
	}
	
	
	
	
}
