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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.domain.LogEvent;

/**
 * @author AnilAmarakoon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/config/logParsers.xml"})
public class ParserConfig10Tests {
	
	@Autowired
    @Qualifier("stdLogParser")
	Log4jParser stdLogParser;	
	

	
	String[] samples = { 
			"[2012-11-02 09:03:41,133] TP-Processor33 ERROR actions.DispatchAction - Action[/SendEmail] does not contain method named view",
			"at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:237)",
			"[2012-09-20 12:48:02,178] TP-Processor3 DEBUG util.ModuleUtils - Module name found: default",
			"[2012-09-20 12:48:03,350] TP-Processor3 DEBUG util.PropertyMessageResources - getMessage(en,header.link.alternate.reviewer)",
			"[2012-09-20 12:48:02,147] TP-Processor3 DEBUG tiles.TilesRequestProcessor - processForwardConfig(/Home.do?dispatch=ty, false)"
			  };


	
	 String[] singleSamples = {
			 "[2012-11-02 09:03:41,133] TP-Processor33 ERROR actions.DispatchAction - Action[/SendEmail] does not contain method named view",
				"at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:237)"
	 };		
	 
		@Test
		public void testString() {
			
			List<String> stringList = Arrays.asList(singleSamples);
			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(10, stringList);
			 assertEquals(1,resultaArray.size());
			 this.printMathes(resultaArray);
		}
	 
	 
		@Test
		 public void ibmParserParserTest() {
			
			 List<String> stringList = Arrays.asList(samples);			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(10, stringList);
			 assertEquals(4,resultaArray.size());
		}
	 
		@Test
		 public void tryAllParsersForwardTest() {
			
			 List<String> stringList = Arrays.asList(samples);
			 List<LogEvent> resultaArray = null;
			 int parserId = -1;
			
			 for(int i=0; i<12; i++) {
				 
				 resultaArray = stdLogParser.createLogEvents(i, stringList);
				 if(resultaArray.size() == 4) {
					 System.out.println("Forward list matching parser is: "+i);
					 parserId = i;
					 break;
				 }
			 }
			 
			 assertNotNull(resultaArray);
			 assertEquals(4,resultaArray.size());
			 assertEquals(10,parserId);
			// this.printMathes(resultaArray);
		}	 

		/**
		 * Parser id 2 & 6 & 9 get the same results 
		 */
		@Test
		 public void tryAllParsersReverseTest() {
			
			 List<String> stringList = Arrays.asList(samples);
			 List<LogEvent> resultaArray = null;
			 int parserId = -1;
			
			 for(int i=11; i >-1; i--) {
				 
				 resultaArray = stdLogParser.createLogEvents(i, stringList);
				 if(resultaArray.size() == 4) {
					 System.out.println("Reverse list matching parser is: "+i);
					 parserId = i;
					 break;
				 }
			 }
			 
			 
			 assertNotNull(resultaArray);
			 assertEquals(4,resultaArray.size());
			 assertEquals(10,parserId);
			// this.printMathes(resultaArray);
		}	
	
	 protected void printMathes( List<LogEvent> resultaArray) {

			for(LogEvent event: resultaArray) {
				System.out.println(event);
			}
	 }	

}
