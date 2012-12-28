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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import slina.mb.domain.LogEvent;
import slina.mb.domain.LogEventImpl;
import slina.mb.domain.Loglevel;

/**
 * @author anil
 *
 */


@Deprecated
public class IBMParserImpl implements CustomLogParser {

	private static final Logger LOGGER = Logger.getLogger(IBMParserImpl.class);
	
	String regex ="(\\s*\\[\\s*)(\\d{1,2})\\/(\\d{1,2})\\/(\\d{2,4}) (\\d{1}):(\\d{2}):(\\d{2}):(\\d{3}) (\\w{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$";

	private Map<String, Loglevel> logLevelLokup = new HashMap<String, Loglevel>();
	Pattern p ;
	
	
	
	public IBMParserImpl() {
		super();
		this.p = Pattern.compile(this.regex);
	}

	/**
	 * @see slina.mb.utils.CustomLogParser#createLogEvents(java.util.List)
	 */
	@Override
	public List<LogEvent> createLogEvents(List<String> filelist) {
		
		List<Integer> pointer = new ArrayList<Integer>();
		List<LogEvent> logEvents = new ArrayList<LogEvent>();
		LogEvent bf =null;
		
		int matchCount=0, unmatchCount=0,infocount=0;
		
		for(String queryString:filelist) {
			
			Matcher m = this.getMatcher(queryString);
			
			if(m.matches()) {
				
				if(bf != null) {
					logEvents.add(bf);
				} 

				bf = new LogEventImpl(m.group(4),m.group(2),m.group(3),m.group(5),m.group(6),m.group(7),m.group(8),
							m.group(14).trim(), m.group(15).trim());

				String logLevelString = m.group(13).trim();
				Loglevel loglevel = this.findLogLevel(logLevelString);
				bf.setLogLevel(loglevel);
				
				
				matchCount++;
				pointer.add(matchCount);
		//		System.out.println(queryString);
			} else {
				
				if(bf == null) {
					unmatchCount++;
					continue;
				}
				
				if(queryString !=null && queryString.length() >1) {
					bf.addLogDetails(queryString);
					infocount++;
				}
			}

		}
		LOGGER.info("Matched lines > "+matchCount+"  unmatchCount > "+unmatchCount+" infoCount "+infocount+"  total lines > "+filelist.size());
		logEvents.add(bf);
		return logEvents;
		

	}

	
	
	private Matcher getMatcher(String queryString) {
		
		return this.p.matcher(queryString);
	}
	/**
	 * @see slina.mb.utils.CustomLogParser#setRegex(java.lang.String)
	 */
	@Override
	public void setRegex(String regex) {
		this.regex = regex;

	}

	/**
	 * @see slina.mb.utils.CustomLogParser#setLogLevelLokup(java.util.Map)
	 */
	@Override
	public void setLogLevelLokup(Map<String, Loglevel> logLevelLokup) {
		this.logLevelLokup = logLevelLokup;
	}

	/**
	 * @see slina.mb.utils.CustomLogParser#getLogLevelLokup()
	 */
	@Override
	public Map<String, Loglevel> getLogLevelLokup() {
		return this.logLevelLokup;
	}
	
	private Loglevel findLogLevel(String logLevel) {
		
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

}
