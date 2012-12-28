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
@ContextConfiguration(locations={"/config/logParsers.xml","/config/demo-tomcat-prod.xml"})
public class DemoTomcatLogParserTests {

	@Autowired
    @Qualifier("logFileReader")
	LogFileReader logFileReader;	

	@Resource
    @Qualifier("logParserMap")
	Map<Integer, Log4jParser> logParserMap;
	
	
	
	@Autowired
    @Qualifier("prodTomcatApp10Log")
	LogFilePath prodTomcatApp10Log;		


	@Autowired
    @Qualifier("prodTomcatApp11Log")
	LogFilePath prodTomcatApp11Log;
	
	@Autowired
    @Qualifier("prodTomcatApp3Log")
	LogFilePath prodTomcatApp3Log;

	@Autowired
    @Qualifier("prodTomcatApp5Log")
	LogFilePath prodTomcatApp5Log;		

	@Autowired
    @Qualifier("prodTomcatApp6Log")
	LogFilePath prodTomcatApp6Log;
	
	@Autowired
    @Qualifier("prodTomcatDefaultLog")
	LogFilePath prodTomcatDefaultLog;
			

	@Autowired
    @Qualifier("prodTomcatStdoutLog")
	LogFilePath prodTomcatStdoutLog;
	


	@Test
	public void testTomcatApp10() throws IOException {
		
		String filePath = this.prodTomcatApp10Log.getfullFilePath();
		Integer parserConfigId = this.prodTomcatApp10Log.getParserConfigId();
		Integer parserId = this.prodTomcatApp10Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test SystemOut Log Events --> " + logList.size());						
	}


	@Test
	public void testTomcat1App11() throws IOException {
		
		String filePath = this.prodTomcatApp11Log.getfullFilePath();
		Integer parserConfigId = this.prodTomcatApp11Log.getParserConfigId();
		Integer parserId = this.prodTomcatApp11Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test tomcat App1 Log Events --> " + logList.size());		
	}


	@Test
	public void testTomcatApp5() throws IOException {
		
		String filePath = this.prodTomcatApp5Log.getfullFilePath();
		Integer parserConfigId = this.prodTomcatApp5Log.getParserConfigId();
		Integer parserId = this.prodTomcatApp5Log.getParserId();
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test tomcat App8 Log Events --> " + logList.size());		
	}	
	
	@Test
	public void testTomcatApp6() throws IOException {
		
		String filePath = this.prodTomcatApp6Log.getfullFilePath();
		Integer parserConfigId = this.prodTomcatApp6Log.getParserConfigId();
		Integer parserId = this.prodTomcatApp6Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
			
		assertTrue(logList.size()>0);
		System.out.println("test tomcat App9 Log Events --> " + logList.size());		
	}	
	
	
	@Test
	public void testTomcatDefaultLog() throws IOException {
		
		String filePath = this.prodTomcatDefaultLog.getfullFilePath();
		Integer parserConfigId = this.prodTomcatDefaultLog.getParserConfigId();		
		Integer parserId = this.prodTomcatDefaultLog.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test tomcat Default Log Events --> " + logList.size());		
	}		
	
	@Test
	public void testTomcatStdoutLog() throws IOException {
		
		String filePath = this.prodTomcatStdoutLog.getfullFilePath();
		Integer parserConfigId = this.prodTomcatStdoutLog.getParserConfigId();		
		Integer parserId = this.prodTomcatStdoutLog.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);
		
		assertTrue(logList.size()>0);
		System.out.println("test tomcat stdout Log Events --> " + logList.size());		
	}		
	
/** prod 2 logs */
	@Test
	public void testTomcatApp3() throws IOException {
		
		String filePath = this.prodTomcatApp3Log.getfullFilePath();
		Integer parserConfigId = this.prodTomcatApp3Log.getParserConfigId();
		Integer parserId = this.prodTomcatApp3Log.getParserId();
		
		Log4jParser parser = this.logParserMap.get(parserId);
		
		List<String> filelist = this.logFileReader.readFile(filePath);
		
		List<LogEvent> logList = parser.createLogEvents(parserConfigId,filelist);

		assertTrue(logList.size()>0);
		System.out.println("test tomcat app3 Log Events --> " + logList.size());						
	}

	
	
}
