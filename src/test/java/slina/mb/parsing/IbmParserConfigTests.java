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
public class IbmParserConfigTests {
	
	@Autowired
    @Qualifier("stdLogParser")
	Log4jParser stdLogParser;	
	
	
	 String[] samples = {
		        "[11/2/12 8:23:59:400 CDT] 00000065 SpnegoHandler W com.ibm.ws.security.spnego.SpnegoHandler handleRequest CWSPN0021E: No delegated credentials were found for user: Ytrc@bzmbn.c0m.",
		        "More exception detailsgoes here",
		        "[11/2/12 8:24:07:258 CDT] 00000069 ClientSideUse I   UserCredentialsClientInterceptor handleMessage invoked",
		        "[11/2/12 8:57:58:221 CDT] 00000007 TimeoutManage I   WTRN0124I: When the timeout occurred the thread with which the transaction is, or was most recently, associated was Thread[WebContainer : 33,5,main]. The stack trace of this thread when the timeout occurred was: ",
		        "[11/2/12 7:59:31:820 CDT] 00000014 HttpConnectio W org.apache.commons.httpclient.HttpConnection releaseConnection HttpConnectionManager is null.  Connection cannot be released.",
		        "[11/2/12 12:56:36:534 CST] 00002b37 ExchangeSyncI W   [sa <ExchangeFolder:OID:1:V32:BC85AD2FDCC14917B03B50160F9A8243>] Synchronization completed, but some instances could not be synchronized.",
		        "XX YYY more detailsgoes here"
	 };	
	
	 String[] singleSamples = {
		        "[11/2/12 8:23:59:400 CDT] 00000065 SpnegoHandler W com.ibm.ws.security.spnego.SpnegoHandler handleRequest CWSPN0021E: No delegated credentials were found for user: Ytrc@bzmbn.c0m.",
		        "More exception detailsgoes here"
	 };		
	 
		@Test
		public void testString() {
			
			List<String> stringList = Arrays.asList(singleSamples);
			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(0, stringList);
			 assertEquals(1,resultaArray.size());
			 this.printMathes(resultaArray);
		}
	 
	 
		@Test
		 public void ibmParserParserTest() {
			
			 List<String> stringList = Arrays.asList(samples);			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(0, stringList);
			 assertEquals(5,resultaArray.size());
		}
	 
		@Test
		 public void tryAllParsersForwardTest() {
			
			 List<String> stringList = Arrays.asList(samples);
			 List<LogEvent> resultaArray = null;
			
			 for(int i=0; i<12; i++) {
				 
				 resultaArray = stdLogParser.createLogEvents(i, stringList);
				 if(resultaArray.size() == 5) {
					 System.out.println("Forward list matching parser is: "+i);
					 assertEquals(0,i);
					 break;
				 }
			 }
			 
			 assertNotNull(resultaArray);
			 assertEquals(5,resultaArray.size());

		}	 

		
		@Test
		 public void tryAllParsersReverseTest() {
			
			 List<String> stringList = Arrays.asList(samples);
			 List<LogEvent> resultaArray = null;
			
			 for(int i=11; i >-1; i--) {
				 
				 resultaArray = stdLogParser.createLogEvents(i, stringList);
				 if(resultaArray.size() == 5) {
					 System.out.println("Reverse list matching parser is: "+i);
					 assertEquals(0,i);
					 break;
				 }
			 }
			 
			 assertNotNull(resultaArray);
			 assertEquals(5,resultaArray.size());

		}	
	
	 protected void printMathes( List<LogEvent> resultaArray) {

			for(LogEvent event: resultaArray) {
				System.out.println(event);
			}
	 }	

}
