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


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import slina.mb.domain.Loglevel;

/**
 * @author amaraa1
 *
 */
public class LogLevelMapImp implements LogLevelMap, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2046633858624179788L;
	private Map<String, Loglevel> logLevelLokup = new HashMap<String, Loglevel>();
	

	
	public LogLevelMapImp() {
		super();	
		this.createLog4jLevellogLevelLokup();
		this.createLogIBMLevellogLevelLokup();
	}


	public Loglevel get(String key) {
		
		return this.logLevelLokup.get(key);
		
	}
	
	
	
	
	private void createLog4jLevellogLevelLokup() {
		
		logLevelLokup.put("SEVERE", Loglevel.SEVERE);
		logLevelLokup.put("WARN", Loglevel.WARN);
		logLevelLokup.put("INFO", Loglevel.INFO);
		logLevelLokup.put("ERROR", Loglevel.ERROR);
		logLevelLokup.put("FATAL", Loglevel.FATAL);
		logLevelLokup.put("DEBUG", Loglevel.DEBUG);
		logLevelLokup.put("TRACE", Loglevel.TRACE);
	}
	
	private void createLogIBMLevellogLevelLokup() {
		
		logLevelLokup.put("S", Loglevel.SEVERE);
		logLevelLokup.put("W", Loglevel.WARN);
		logLevelLokup.put("I", Loglevel.INFO);
		logLevelLokup.put("E", Loglevel.ERROR);
		logLevelLokup.put("F", Loglevel.FATAL);
		logLevelLokup.put("D", Loglevel.DEBUG);
		logLevelLokup.put("T", Loglevel.TRACE);
		logLevelLokup.put("O", Loglevel.APP_LOG);
		logLevelLokup.put("A", Loglevel.AUDIT);
		logLevelLokup.put("R", Loglevel.APP_ERROR);
		logLevelLokup.put("C", Loglevel.CONFIG);
	}
	

}
