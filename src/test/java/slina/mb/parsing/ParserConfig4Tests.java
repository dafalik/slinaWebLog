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
public class ParserConfig4Tests {
	
	@Autowired
    @Qualifier("stdLogParser")
	Log4jParser stdLogParser;	
	

	
	String[] samples = { "12-11-01 08:52:48,763 ERROR org.apache.jk.server.JkCoyoteHandler Class:org.apache.jk.server.JkCoyoteHandler Method:action: Line:506 - Error in action code java.net.SocketException: Connection reset by peer: socket write error",
			"at java.net.SocketOutputStream.socketWrite0(Native Method)",
			"12-11-02 10:52:53,626 DEBUG org.apache.jk.server.JkCoyoteHandler Class:org.apache.jk.server.JkCoyoteHandler Method:doWrite: Line:250 - doWrite 0 8184 8",
			"12-11-02 10:52:53,626 DEBUG org.apache.jk.common.ChannelSocket Class:org.apache.jk.common.ChannelSocket Method:send: Line:503 - send() 16 3",
			"12-11-02 10:52:56,782 DEBUG org.apache.coyote.tomcat5.CoyoteAdapter Class:org.apache.coyote.tomcat5.CoyoteAdapter Method:parseSessionCookiesId: Line:384 -  Requested cookie session id is 68C57B6E00C982C510ACFE2848C5F4DE"
			  };


	
	 String[] singleSamples = {
			 "12-11-01 08:52:48,763 ERROR org.apache.jk.server.JkCoyoteHandler Class:org.apache.jk.server.JkCoyoteHandler Method:action: Line:506 - Error in action code java.net.SocketException: Connection reset by peer: socket write error",
				"at java.net.SocketOutputStream.socketWrite0(Native Method)"
	 };		
	 
		@Test
		public void testString() {
			
			List<String> stringList = Arrays.asList(singleSamples);
			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(4, stringList);
			 assertEquals(1,resultaArray.size());
			 this.printMathes(resultaArray);
		}
	 
	 
		@Test
		 public void ibmParserParserTest() {
			
			 List<String> stringList = Arrays.asList(samples);			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(4, stringList);
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
			 assertEquals(3,parserId);
			// this.printMathes(resultaArray);
		}	 

		/**
		 * Parser id 3 & 4 get the same results 
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
			 assertEquals(4,parserId);
			// this.printMathes(resultaArray);
		}	
	
	 protected void printMathes( List<LogEvent> resultaArray) {

			for(LogEvent event: resultaArray) {
				System.out.println(event);
			}
	 }	

}
