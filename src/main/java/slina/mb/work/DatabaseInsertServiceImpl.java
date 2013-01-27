package slina.mb.work;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import slina.mb.domain.LogEvent;
import slina.mb.domain.db.DbLogEventImpl;
import slina.mb.domain.db.DbLogFileImpl;
import slina.mb.domain.db.SlinaRepositoryService;
import slina.mb.io.LogFilePath;
import slina.mb.io.LogFilePathImpl;
import slina.mb.parsing.Log4jParser;
import slina.mb.utils.LogEventComparator;
import slina.mb.utils.LogFileReader;

public class DatabaseInsertServiceImpl {
	
	private static final Logger LOGGER = Logger.getLogger(DatabaseLogServiceImpl.class);
	
	@Autowired
	private SlinaRepositoryService slinaRepositoryService;
	
	private Map<Integer, List<LogFilePathImpl>> logFilesMap = new HashMap<Integer, List<LogFilePathImpl>>();
	
	private Map<Integer, Log4jParser> logParserMap = new HashMap<Integer, Log4jParser>();	
	private LogFileReader logFileReader;
	
	
	/**
	 * 
	 * @throws IOException
	 */
	public void insertLogRecordTodatabase() throws IOException {
				
			Iterator<Integer> keyIterator = this.logFilesMap.keySet().iterator();
		
			while(keyIterator.hasNext()) {
				
				Integer id = keyIterator.next();
				List<LogFilePathImpl> filePathList = this.logFilesMap.get(id);
				
				for(LogFilePathImpl logFilePath: filePathList) {
					
					List<LogEvent> eventList = this.getLogEventsFromFile(id);
					
					DbLogFileImpl dbFile = this.createLogRecord(logFilePath, eventList);
					slinaRepositoryService.save(dbFile);
				}
			}			
	}
	
	
	/**
	 * Converts LogFilePathImpl and logEventList into DbLogFileImpl object
	 * @param logFilePath
	 * @param eventList
	 * @return
	 */
	private DbLogFileImpl createLogRecord(LogFilePathImpl logFilePath, List<LogEvent> eventList ) {
		
		DbLogFileImpl dbFile = new DbLogFileImpl(logFilePath);
		
		for(LogEvent event: eventList) {
			
			DbLogEventImpl dbEvent = new DbLogEventImpl(event, dbFile);
			dbFile.addLogEvent(dbEvent);
		}		
		return dbFile;
	}
	
	
	
	public List<LogEvent> getLogEventsFromFile(int id) throws IOException {
		
		List<LogFilePathImpl> logFilePathList = this.logFilesMap.get(id);
		
		
		List<LogEvent> eventlogList = new ArrayList<LogEvent>();
		
		if(logFilePathList == null) {
			return eventlogList;
		}
		

		for(LogFilePath logPath: logFilePathList) {
			
			//String filepath = logPath.getfullFilePath();
			Resource resource = logPath.getResource();
			int paserId = logPath.getParserId();
			int paserConfigId = logPath.getParserConfigId();
			LOGGER.info("Process file for display : "+resource.getFilename());
			
			Log4jParser logParser = logParserMap.get(paserId);
			
			List<String> filelist = this.logFileReader.readFile(resource);					
			List<LogEvent> logList = logParser.createLogEvents(paserConfigId,filelist);
			
			eventlogList.addAll(logList);
		}
		
		if(eventlogList.size() > 2 ) {
			Collections.sort(eventlogList, new LogEventComparator());		
		}		
		return eventlogList;
	}
	
	public void setLogParserMap(Map<Integer, Log4jParser> logParserMap) {
		this.logParserMap = logParserMap;
	}

	public void setLogFileReader(LogFileReader logFileReader) {
		this.logFileReader = logFileReader;
	}

	public void setLogService(LogServiceImpl logService) {
		this.logFilesMap = logService.getLogFilesMap();
	}
	
	

}
