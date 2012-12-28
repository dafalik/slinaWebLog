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
public class ParserConfig3Tests {
	
	@Autowired
    @Qualifier("stdLogParser")
	Log4jParser stdLogParser;	
	

	
	String[] samples = { "12-10-30 15:48:07,934 ERROR InsertTag - ServletException in '/WEB-INF/pages/toc.jsp': Cannot find bean: \"SESSION_ITEMS\" in scope: \"session\"javax.servlet.ServletException: Cannot find bean: \"SESSION_ITEMS\" in scope: \"session\" ",
			"at org.apache.jasper.runtime.PageContextImpl.doHandlePageException(PageContextImpl.java:82",
			"12-10-19 12:54:23,627 ERROR DispatchAction - Request[/Client] does not contain handler parameter named 'dispatch'.  This may be caused by whitespace in the label text.",
			"12-10-19 12:54:23,642 FATAL CRUDExceptionHandler - [CoyotePrincipal[pescid1]] javax.servlet.ServletException: Request[/PotentialClient] does not contain handler parameter named 'tab'.  This may be caused by whitespace in the label text."
			  };


	
	 String[] singleSamples = {
			 "12-10-30 15:48:07,934 ERROR InsertTag - ServletException in '/WEB-INF/pages/toc.jsp': Cannot find bean: \"SESSION_ITEMS\" in scope: \"session\"javax.servlet.ServletException: Cannot find bean: \"SESSION_ITEMS\" in scope: \"session\" ",
		     "at org.apache.jasper.runtime.PageContextImpl.doHandlePageException(PageContextImpl.java:82"
	 };		
	 
		@Test
		public void testString() {
			
			List<String> stringList = Arrays.asList(singleSamples);
			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(3, stringList);
			 assertEquals(1,resultaArray.size());
			 this.printMathes(resultaArray);
		}
	 
	 
		@Test
		 public void ibmParserParserTest() {
			
			 List<String> stringList = Arrays.asList(samples);			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(3, stringList);
			 assertEquals(3,resultaArray.size());
		}
	 
		@Test
		 public void tryAllParsersForwardTest() {
			
			 List<String> stringList = Arrays.asList(samples);
			 List<LogEvent> resultaArray = null;
			 int parserId = -1;
			
			 for(int i=0; i<12; i++) {
				 
				 resultaArray = stdLogParser.createLogEvents(i, stringList);
				 if(resultaArray.size() == 3) {
					 System.out.println("Forward list matching parser is: "+i);
					 parserId = i;
					 break;
				 }
			 }
			 
			 assertNotNull(resultaArray);
			 assertEquals(3,resultaArray.size());
			 assertEquals(3,parserId);
			 this.printMathes(resultaArray);
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
				 if(resultaArray.size() == 3) {
					 System.out.println("Reverse list matching parser is: "+i);
					 parserId = i;
					 break;
				 }
			 }
			 
			 
			 assertNotNull(resultaArray);
			 assertEquals(3,resultaArray.size());
			 assertEquals(4,parserId);
			 this.printMathes(resultaArray);
		}	
	
	 protected void printMathes( List<LogEvent> resultaArray) {

			for(LogEvent event: resultaArray) {
				System.out.println(event);
			}
	 }	

}
