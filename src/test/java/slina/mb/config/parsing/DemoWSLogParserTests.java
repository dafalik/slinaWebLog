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
@ContextConfiguration(locations={"/config/logParsers.xml","/config/demo-ws-prod-logs.xml"})
public class DemoWSLogParserTests {

	@Autowired
    @Qualifier("logFileReader")
	LogFileReader logFileReader;	
			
	@Resource
    @Qualifier("logParserMap")
	Map<Integer, Log4jParser> logParserMap;

	@Autowired
    @Qualifier("prod1WSSysoutLog")
	LogFilePath prod1WSSysoutLog;		

	@Autowired
    @Qualifier("prod1WSApp2Log")
	LogFilePath prod1WSApp2Log;
	
	@Autowired
    @Qualifier("prod1WSApp4Log")
	LogFilePath prod1WSApp4Log;

	@Autowired
    @Qualifier("prod1WSApp7Log")
	LogFilePath prod1WSApp7Log;		

	@Autowired
    @Qualifier("prod2WSApp2Log")
	LogFilePath prod2WSApp2Log;
	
	@Autowired
    @Qualifier("prod2WSApp4Log")
	LogFilePath prod2WSApp4Log;
			
	@Autowired
    @Qualifier("prod2WSApp7Log")
	LogFilePath prod2WSApp7Log;		

	@Autowired
    @Qualifier("prod2WSSysoutLog")
	LogFilePath prod2WSSysoutLog;	

	@Test
	public void testProd1WSSysout() throws IOException {
		
		String filePath = this.prod1WSSysoutLog.getfullFilePath();
		Integer parserConfigId = this.prod1WSSysoutLog.getParserConfigId();
		Integer parserId = this.prod1WSSysoutLog.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test WS SystemOut Log Events --> " + logList.size());						
	}


	@Test
	public void testprod1WSApp2LogLog() throws IOException {
		
		String filePath = this.prod1WSApp2Log.getfullFilePath();
		Integer parserConfigId = this.prod1WSApp2Log.getParserConfigId();
		Integer parserId = this.prod1WSApp2Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test WS prod1 App2 Log Events --> " + logList.size());		
	}
	

	@Test
	public void testprod1WSApp4LogLog() throws IOException {
		
		String filePath = this.prod1WSApp4Log.getfullFilePath();
		Integer parserConfigId = this.prod1WSApp4Log.getParserConfigId();
		Integer parserId = this.prod1WSApp4Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test WS prod1 App4 Log Events --> " + logList.size());		
	}	
	
	@Test
	public void testprod1WSApp7LogLog() throws IOException {
		
		String filePath = this.prod1WSApp7Log.getfullFilePath();
		Integer parserConfigId = this.prod1WSApp7Log.getParserConfigId();
		Integer parserId = this.prod1WSApp7Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
			
		assertTrue(logList.size()>0);
		System.out.println("test prod1 App9 Log Events --> " + logList.size());		
	}	
	
	

	
/** prod 2 logs */
	@Test
	public void testprod2WSSysoutLog() throws IOException {
		
		String filePath = this.prod2WSSysoutLog.getfullFilePath();
		Integer parserConfigId = this.prod2WSSysoutLog.getParserConfigId();
		Integer parserId = this.prod2WSSysoutLog.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test SystemOut Log Events --> " + logList.size());						
	}


	@Test
	public void testprod2WSApp2Log() throws IOException {
		
		String filePath = this.prod2WSApp2Log.getfullFilePath();
		Integer parserConfigId = this.prod2WSApp2Log.getParserConfigId();
		Integer parserId = this.prod2WSApp2Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test prod2 App1 Log Events --> " + logList.size());		
	}
	

	@Test
	public void testprod2WSApp4Log() throws IOException {
		
		String filePath = this.prod2WSApp4Log.getfullFilePath();
		Integer parserConfigId = this.prod2WSApp4Log.getParserConfigId();
		Integer parserId = this.prod2WSApp4Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test prod2 App8 Log Events --> " + logList.size());		
	}	
	
	@Test
	public void testprod2WSApp7Log() throws IOException {
		
		String filePath = this.prod2WSApp7Log.getfullFilePath();
		Integer parserConfigId = this.prod2WSApp7Log.getParserConfigId();
		Integer parserId = this.prod2WSApp7Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
			
		assertTrue(logList.size()>0);
		System.out.println("test prod2 App9 Log Events --> " + logList.size());		
	}	
	
	
	
}
