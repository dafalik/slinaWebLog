/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package slina.mb.work;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import slina.mb.domain.LogEvent;
import slina.mb.domain.TableInfo;
import slina.mb.domain.TableRows;
import slina.mb.domain.db.DbLogEventImpl;
import slina.mb.domain.db.DbLogFileImpl;
import slina.mb.domain.db.SlinaRepositoryService;
import slina.mb.io.LogFilePath;
import slina.mb.io.LogFilePathImpl;
import slina.mb.menu.Node;

/**
 * @author AnilAmarakoon
 *
 */

public class DatabaseLogServiceImpl {
	
	private static final Logger LOGGER = Logger.getLogger(DatabaseLogServiceImpl.class);
	
	@Autowired
	private SlinaRepositoryService slinaRepositoryService;
	
	private Map<Integer, List<LogFilePathImpl>> logFilesMap = new HashMap<Integer, List<LogFilePathImpl>>();
	
	private List<Node> nodeList = new ArrayList<Node>();
	
	private List<LogEvent> getLogEventsFromDataBase(int id) throws IOException {
		
		List<LogEvent> exportEventsList  = new ArrayList<LogEvent>();
		
		List<LogFilePathImpl> logFilePathList = this.logFilesMap.get(id);
		
		List<LogEvent> eventlogList = new ArrayList<LogEvent>();
		
		if(logFilePathList == null) {
			return eventlogList;
		}
		
		for(LogFilePath logPath: logFilePathList) {
			
			DbLogFileImpl file = this.slinaRepositoryService.findOne(logPath.getFileId());
			
			LOGGER.info("getting infor from "+logPath.getFileName());

			List<DbLogEventImpl> dbLogEventsList = file.getLogEvents();
			
			for(DbLogEventImpl dbEvent: dbLogEventsList) {
				exportEventsList.add(dbEvent);
			}
			return exportEventsList;

		}	
		return exportEventsList;
	}
	
	public TableInfo getLogEventListings(int uniqueId)  {
	   		   
		   if(uniqueId==0) {
			   
			   return this.getDefaultTableInfo();
		   }   		   
		   LOGGER.info("getLogListings id:  "+uniqueId);
		   
		try {
			   List<LogEvent> eventList = this.getLogEventsFromDataBase(uniqueId);
			   TableInfo tableInfo = this.createTableInfoFromLogEventList(eventList, false);
			   return tableInfo;
			   
			   
		} catch (IOException e) {
			
				e.printStackTrace();
			   return this.getDefaultTableInfo();						
		}

	  }	
 
	
	
	public List<Node> getNodeList() {
		return this.nodeList;
	}

	/**
	 * @return empty log listing
	 */
	private TableInfo getDefaultTableInfo() {
		TableInfo info = new TableInfo();
		   info.setsEcho(1);
		   info.setiTotalDisplayRecords(0);
		   info.setiTotalRecords(0);
		   return info;
	}	   
   
	
	/**
	 * Converts List<LogEvent> eventList to TableRows objects for presentation. 
	 * Creates TableInfo objects that carries TableRows and other information (like totals) back to screen
	 * @param eventList
	 * @param setserver
	 * @return log listing as TableInfo object, which wrraps LogEvents converted to TableRows
	 */
	private TableInfo createTableInfoFromLogEventList(List<LogEvent> eventList, boolean setserver) {
		
		   TableInfo info = new TableInfo();
		   info.setsEcho(1);			   
		   
		   for(LogEvent logEvent:eventList) {
			   
			   if(logEvent == null)
				   continue;
			   				   
		
			   
			   
			   TableRows row = new TableRows(logEvent.getCal().toString(), logEvent.getLogLevel().getName(), logEvent.getLogClass(), 
					   logEvent.getLogMessage());
			   
			   
			   if(setserver) {
				   row.setServer(logEvent.getServer());
			   }
			   
			   row.setTimestamp(logEvent.getCal().getTime());
			   
			   if(logEvent.getLogDetails().size() == 0 ) {
				   row.setDetailsLink("");					   

			   } else {					   
				   List<String> logDetails = logEvent.getLogDetails();					   
				   row.setLogDetails(logDetails);
			   }				   
			   info.getAaData().add(row);				   
		   }
		   
		   info.setiTotalDisplayRecords(eventList.size());
		   info.setiTotalRecords(eventList.size());
		   return info;
	}
	

	public void setLogService(LogServiceImpl logService) {
		this.logFilesMap = logService.getLogFilesMap();
		this.nodeList = logService.getProdNodeList();
	}
	
	
	
	
	
}
