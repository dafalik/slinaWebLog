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
package slina.mb.cron;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import slina.mb.domain.LogEvent;
import slina.mb.domain.Loglevel;
import slina.mb.io.FileCopyWorker;
import slina.mb.io.LogFilePath;
import slina.mb.io.SourceInfo;
import slina.mb.parsing.Log4jParser;
import slina.mb.utils.LogFileReader;

/**
 * @author anilAmarakoon
 *
 */
public class AdvanceWarningImpl implements AdvanceWarning {

	private static final Logger LOGGER = Logger.getLogger(AdvanceWarningImpl.class);	
	private List<FileCopyWorker> fileWorkersList;
	private LogFileReader logFileReader;
	private Map<Integer, Log4jParser> logParserMap = new HashMap<Integer, Log4jParser>();	
	private Map<Loglevel, Boolean> filtermap ;
	
	private List<LogEvent> filteredServList = new ArrayList<LogEvent>();
	
	
	
	
	
	
	public AdvanceWarningImpl() {
		super();
		this.filtermap = new HashMap<Loglevel, Boolean>();
		this.filtermap.put(Loglevel.APP_ERROR, true);
		this.filtermap.put(Loglevel.ERROR, true);
		this.filtermap.put(Loglevel.WARN, true);
		this.filtermap.put(Loglevel.FATAL, true);
		
	}

	public void work() throws IOException {
		
		List<LogEvent> eventlogList = new ArrayList<LogEvent>();
		
		LOGGER.info("work started time-> "+new Date());
		LOGGER.info("work started errorlog size-> "+filteredServList.size());
		
		for(FileCopyWorker fileWorker:fileWorkersList) {
		
			SourceInfo source = fileWorker.getSourceInfo();
			
					
					List<LogFilePath> logFilePathList =  source.getLogFileList();
					
					for(LogFilePath logPath: logFilePathList) {
						
						String filepath = logPath.getfullFilePath();
						int paserId = logPath.getParserId();
						int paserConfigId = logPath.getParserConfigId();
						LOGGER.info("processing -> "+filepath);
						
						Log4jParser logParser = logParserMap.get(paserId);
						
						List<String> filelist = this.logFileReader.readFile(filepath);					
						List<LogEvent> logEventList = logParser.createLogEvents(paserConfigId,filelist);
						
						this.setServerName(logEventList, fileWorker.getServerName());
						
						eventlogList.addAll(logEventList);
		
					}		

		}
		
	
		
		List<LogEvent> filteredList = this.getFilteredLogEvents(eventlogList);

		synchronized(filteredServList) {
			LOGGER.info("filteredServList list size -> "+filteredList.size());
			filteredServList = filteredList;
        }
		
	}
	
	private void setServerName(List<LogEvent> logEventList,String serverName)  {
		

		LOGGER.info("server name: "+serverName);
		
		int sNameIndex = serverName.indexOf('.', 0);
		String sName = null;
		
		if(sNameIndex==-1) {
				sName = serverName;
		}else {
				sName = serverName.substring(0, sNameIndex);
		}
		
		
		if(sName==null) {
			LOGGER.error("******************* Null server name *******************");
			sName = serverName;
		}
		
		
		for(LogEvent source:logEventList) {
			
			if(source==null) {
				LOGGER.error("******************* Null LogEvent:source--> setServerName.source *******************");
				continue;
			}
			
			source.setServer(sName);
		}
		
	}
	
	
	private List<LogEvent> getFilteredLogEvents(List<LogEvent> eventsList) {
		
		LOGGER.info("filtering logs started size -> "+eventsList.size());
		
		List<LogEvent> filteredList =  new ArrayList<LogEvent>();
		
		for(LogEvent source:eventsList) {
			
			if(source==null) {
				LOGGER.error("******************* Null LogEvent:source--> getFilteredLogEvents.source *******************");
				continue;
			}
			
			Loglevel level = source.getLogLevel();

			if(level==null) {
				LOGGER.error("******************* Null LogEvent:Loglevel--> getFilteredLogEvents.source *******************");
				continue;
			}
			
			
			if(filtermap.containsKey(level)) {
				filteredList.add(source);
			}
		}
		
		LOGGER.info("filtering logs end size -> "+filteredList.size());
		return filteredList;
	}

	/* (non-Javadoc)
	 * @see ig.mips.anil.cron.AdvanceWarning#getFilteredServList()
	 */
	@Override
	public List<LogEvent> getFilteredServList() {
		return filteredServList;
	}

	/**
	 * @param fileWorkersList the fileWorkersList to set
	 */
	public void setFileWorkersList(List<FileCopyWorker> fileWorkersList) {
		this.fileWorkersList = fileWorkersList;
	}

	/**
	 * @param logFileReader the logFileReader to set
	 */
	public void setLogFileReader(LogFileReader logFileReader) {
		this.logFileReader = logFileReader;
	}

	/**
	 * @param logParserMap the logParserMap to set
	 */
	public void setLogParserMap(Map<Integer, Log4jParser> logParserMap) {
		this.logParserMap = logParserMap;
	}

	/**
	 * @param filtermap the filtermap to set
	 */
	public void setFiltermap(Map<Loglevel, Boolean> filtermap) {
		this.filtermap = filtermap;
	}
	
	

}
