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
package slina.mb.config.parsing;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.domain.LogEvent;
import slina.mb.io.LogFilePath;
import slina.mb.parsing.Log4jParser;
import slina.mb.utils.LogFileReader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/config/logParsers.xml","/config/demo-app-prod-logs.xml"})
public class DemoAppLogParserTests {

	@Autowired
    @Qualifier("logFileReader")
	LogFileReader logFileReader;	
		
	
	@Autowired
    @Qualifier("filteringLogParser")
	Log4jParser filteringLogParser;
	
	@Resource
    @Qualifier("logParserMap")
	Map<Integer, Log4jParser> logParserMap;

	@Autowired
    @Qualifier("prod1SysoutLog")
	LogFilePath prod1SysoutLog;		


	@Autowired
    @Qualifier("prod1App1Log")
	LogFilePath prod1App1Log;
	
	@Autowired
    @Qualifier("prod1App8Log")
	LogFilePath prod1App8Log;
			
	@Autowired
    @Qualifier("prod1App9Log")
	LogFilePath prod1App9Log;		

	@Autowired
    @Qualifier("prod1Application1Log")
	LogFilePath prod1Application1Log;
	
	@Autowired
    @Qualifier("prod1Application2Log")
	LogFilePath prod1Application2Log;
			



	@Autowired
    @Qualifier("prod2App1Log")
	LogFilePath prod2App1Log;
	
	@Autowired
    @Qualifier("prod2App8Log")
	LogFilePath prod2App8Log;
			
	@Autowired
    @Qualifier("prod2App9Log")
	LogFilePath prod2App9Log;		

	@Autowired
    @Qualifier("prod2Application1Log")
	LogFilePath prod2Application1Log;
	
	@Autowired
    @Qualifier("prod2Application2Log")
	LogFilePath prod2Application2Log;

	@Autowired
    @Qualifier("prod2SysoutLog")
	LogFilePath prod2SysoutLog;	

	@Test
	public void testProd1Sysout() throws IOException {
		
		String filePath = this.prod1SysoutLog.getfullFilePath();
		Integer parserConfigId = this.prod1SysoutLog.getParserConfigId();
		Integer parserId = this.prod1SysoutLog.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test SystemOut Log Events --> " + logList.size());						
	}


	@Test
	public void testProd1App1Log() throws IOException {
		
		String filePath = this.prod1App1Log.getfullFilePath();
		Integer parserConfigId = this.prod1App1Log.getParserConfigId();
		Integer parserId = this.prod1App1Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test prod1 App1 Log Events --> " + logList.size());		
	}
	

	@Test
	public void testProd1App8Log() throws IOException {
		
		String filePath = this.prod1App8Log.getfullFilePath();
		Integer parserConfigId = this.prod1App8Log.getParserConfigId();
		Integer parserId = this.prod1App8Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test prod1 App8 Log Events --> " + logList.size());		
	}	
	
	@Test
	public void testProd1App9Log() throws IOException {
		
		String filePath = this.prod1App9Log.getfullFilePath();
		Integer parserConfigId = this.prod1App9Log.getParserConfigId();
		Integer parserId = this.prod1App9Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
			
		assertTrue(logList.size()>0);
		System.out.println("test prod1 App9 Log Events --> " + logList.size());		
	}	
	
	
	@Test
	public void testProd1Application1Log() throws IOException {
		
		String filePath = this.prod1Application1Log.getfullFilePath();
		Integer parserConfigId = this.prod1Application1Log.getParserConfigId();		
		Integer parserId = this.prod1Application1Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test prod1 Application1 Log Events --> " + logList.size());		
	}		
	
	@Test
	public void testProd1Application2Log() throws IOException {
		
		String filePath = this.prod1Application2Log.getfullFilePath();
		Integer parserConfigId = this.prod1Application2Log.getParserConfigId();		
		Integer parserId = this.prod1Application2Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test prod1 Application2 Log Events --> " + logList.size());		
	}		
	
/** prod 2 logs */
	@Test
	public void testProd2Sysout() throws IOException {
		
		String filePath = this.prod2SysoutLog.getfullFilePath();
		Integer parserConfigId = this.prod2SysoutLog.getParserConfigId();
		Integer parserId = this.prod2SysoutLog.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test SystemOut Log Events --> " + logList.size());						
	}


	@Test
	public void testProd2App1Log() throws IOException {
		
		String filePath = this.prod2App1Log.getfullFilePath();
		Integer parserConfigId = this.prod2App1Log.getParserConfigId();
		Integer parserId = this.prod2App1Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test prod2 App1 Log Events --> " + logList.size());		
	}
	

	@Test
	public void testProd2App8Log() throws IOException {
		
		String filePath = this.prod2App8Log.getfullFilePath();
		Integer parserConfigId = this.prod2App8Log.getParserConfigId();
		Integer parserId = this.prod2App8Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test prod2 App8 Log Events --> " + logList.size());		
	}	
	
	@Test
	public void testProd2App9Log() throws IOException {
		
		String filePath = this.prod2App9Log.getfullFilePath();
		Integer parserConfigId = this.prod2App9Log.getParserConfigId();
		Integer parserId = this.prod2App9Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
			
		assertTrue(logList.size()>0);
		System.out.println("test prod2 App9 Log Events --> " + logList.size());		
	}	
	
	
	@Test
	public void testProd2Application1Log() throws IOException {
		
		String filePath = this.prod2Application1Log.getfullFilePath();
		Integer parserConfigId = this.prod2Application1Log.getParserConfigId();		
		Integer parserId = this.prod2Application1Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test prod2 Application1 Log Events --> " + logList.size());		
	}		
	
	@Test
	public void testProd2Application2Log() throws IOException {
		
		String filePath = this.prod2Application2Log.getfullFilePath();
		Integer parserConfigId = this.prod2Application2Log.getParserConfigId();		
		Integer parserId = this.prod2Application2Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test prod2 Application2 Log Events --> " + logList.size());		
	}		
	


	
	
	
	
	
}
