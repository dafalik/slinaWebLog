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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;

import slina.mb.domain.LogEvent;

/**
 * @author anil
 *
 */
public class LookupLog4jParserImpl implements Log4jParser  {
	
	private static final Logger LOGGER = Logger.getLogger(LookupLog4jParserImpl.class);	
	protected Map<Integer,ParserConfig> parserConfigMap = new HashMap<Integer,ParserConfig>();
	private int parserId;	
	
	
	
	
	public LookupLog4jParserImpl(int parserId , Map<Integer,ParserConfig> parserConfigMap) {
		super();
		this.parserConfigMap = parserConfigMap;
		this.parserId = parserId;
	}


	private Matcher getMatcher(ParserConfig config, String queryString) {		
				
		return config.getPattern().matcher(queryString);
	}
	
	
	@Override
	public List<LogEvent> createLogEvents(Integer configId, List<String> filelist) {
		
		List<Integer> pointer = new ArrayList<Integer>();
		List<LogEvent> logEvents = new ArrayList<LogEvent>();
		LogEvent logEvent = null;
		
		ParserConfig config = this.parserConfigMap.get(configId);
				
		
		int matchCount=0, unmatchCount=0, infocount=0;
		
		for(String queryString:filelist) {
			
			Matcher matcher = this.getMatcher(config, queryString);
			
			
			if(matcher.matches()) {
				
				if(logEvent != null) {
					logEvents.add(logEvent);
				} 
				
				logEvent = config.createLogEvent(matcher);
							
				matchCount++;
				pointer.add(matchCount);
				
			} else {
				
				if(logEvent == null) {
					unmatchCount++;
					continue;
				}
				
				if(queryString !=null && queryString.length() >1) {
					
					logEvent.addLogDetails(queryString);
					infocount++;
				}
			}

		}
		if(logEvent !=null) {
			logEvents.add(logEvent);
		}
		
		LOGGER.info("Matched lines > "+matchCount+"  unmatchCount > "+unmatchCount+" infoCount "+infocount+"  total lines > "+filelist.size());
		
		return logEvents;
	}


	public int getParserId() {
		return this.parserId;
	}


	public void setParserId(int parserId) {
		this.parserId = parserId;
	}
	
	

}
