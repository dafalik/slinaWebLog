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
public class ParserConfig9Tests {
	
	@Autowired
    @Qualifier("stdLogParser")
	Log4jParser stdLogParser;	
	

	
	String[] samples = { 
			"2012-11-02 10:14:05,407 [ERROR] com.drfxtry.Logger R1062143	-	-	Authenticator.getProfile - There was an issue getting user profile from external service.",
			"at java.lang.J9VMInternals.newInstanceImpl(Native Method)",
			"2012-11-02 09:29:15,752 [INFO] com.comtx.Logger R1044340	-	-	NpoServlet.login - Setting the session timeout to user specified value of 30",
			"2012-11-02 09:41:18,195 [ERROR] com.comtx.Logger.2e5f2e5f R1048749	242333	ggg@hh.bb	Launch.acquireEngine - Error: You have tried to acquire a second Launch engine: ",
			"2012-11-02 10:14:05,407 [ERROR] com.comtx.Logger R1062143	-	-	BaseAuthenticator.getProfile - There was an issue getting user profile from external service."
			  };


	
	 String[] singleSamples = {
			 "2012-11-02 10:14:05,407 [ERROR] com.drfxtry.Logger R1062143	-	-	Authenticator.getProfile - There was an issue getting user profile from external service.",
				"at java.lang.J9VMInternals.newInstanceImpl(Native Method)"
	 };		
	 
		@Test
		public void testString() {
			
			List<String> stringList = Arrays.asList(singleSamples);
			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(9, stringList);
			 assertEquals(1,resultaArray.size());
			 this.printMathes(resultaArray);
		}
	 
	 
		@Test
		 public void ibmParserParserTest() {
			
			 List<String> stringList = Arrays.asList(samples);			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(9, stringList);
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
			 assertEquals(2,parserId);
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
			 assertEquals(9,parserId);
			// this.printMathes(resultaArray);
		}	
	
	 protected void printMathes( List<LogEvent> resultaArray) {

			for(LogEvent event: resultaArray) {
				System.out.println(event);
			}
	 }	

}
