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


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.domain.LogEvent;
import slina.mb.domain.Loglevel;
import slina.mb.utils.IBMParserImpl;
import slina.mb.utils.LogFileReader;
import slina.mb.utils.LogFileReaderImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/config/logParsers.xml"})
public class LogParserTests {
	

	@Autowired
    @Qualifier("stdLogParser")
	Log4jParserImpl stdLogParser;

	 String poolLog = "logfiles/app/prod1/connectionPool.log";
	 String ibmFile = "logfiles/app/prod1/SystemOut.log";
	 

	@Test
	public void testLog4jFile() throws IOException {
		
		LogFileReader reader = new LogFileReaderImpl();
		
		Resource res = new ClassPathResource(this.poolLog);  
		File file = res.getFile();		
		String var = file.getAbsolutePath();
		
		List<String> filelist = reader.readFile(var);
		assertTrue(filelist.size()>0);
		System.out.println(filelist.size());
	
		List<LogEvent> eventsList = this.stdLogParser.createLogEvents(2, filelist);

		assertTrue(eventsList.size()>0);
		System.out.println(eventsList.size());
		System.out.println("\n\n");


	}

	@Test
	public void testIBMFile() throws IOException {
		
		 Resource res = new ClassPathResource(this.ibmFile);  
		 File file = res.getFile();		
		String var = file.getAbsolutePath();
		
		LogFileReader reader = new LogFileReaderImpl();
		List<String> filelist = reader.readFile(var);
		assertTrue(filelist.size()>0);
		System.out.println(filelist.size());
		
		IBMParserImpl parser = new IBMParserImpl();
		this.createLogIBMLevelMap(parser.getLogLevelLokup());
		
		List<LogEvent> eventsList = this.stdLogParser.createLogEvents(0, filelist);
		assertTrue(eventsList.size()>0);
		System.out.println(eventsList.size());

	}

	
	
	private void createLogIBMLevelMap(Map<String, Loglevel> map) {
		
		map.put("S", Loglevel.SEVERE);
		map.put("W", Loglevel.WARN);
		map.put("I", Loglevel.INFO);
		map.put("E", Loglevel.ERROR);
		map.put("F", Loglevel.FATAL);
		map.put("D", Loglevel.DEBUG);
		map.put("T", Loglevel.TRACE);
	}
	

}
