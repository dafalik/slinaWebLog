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

import slina.mb.domain.LogEvent;
import slina.mb.domain.LogEventImpl;
import slina.mb.domain.Loglevel;

/**
 * @author amaraa1
 *
 */
public class StdinParserConfigImpl extends StandardParserConfigImpl {

//	private static final Logger LOGGER = Logger.getLogger(StdinParserConfigImpl.class);
	
	private String secondayRegex = "(\\.)";
	private  Pattern p1;
	
	public StdinParserConfigImpl(String regex, LogLevelMap logLevelLookup) {
		super(regex, logLevelLookup);
		 this.p1 = Pattern.compile(secondayRegex);
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
		
		this.hasLogClass(le);
				
		//LOGGER.info("Created log event ==> "+le.toString());
		
		return le;
		
	}
	
	private void hasLogClass(LogEvent le) {
		
	    String logClass = le.getLogClass().trim();
	    Matcher m1 = this.p1.matcher(logClass);
	    
	    if(!m1.find()) {
	    	this.reAlaignLogEvent(le, logClass);
	    }

	}
	
	private void reAlaignLogEvent(LogEvent le, String logClass) {
		
		String logMessage = le.getLogMessage();
		le.setLogClass(" ");
		le.setLogMessage(logClass+" "+logMessage);
	}
	
	
	
}
