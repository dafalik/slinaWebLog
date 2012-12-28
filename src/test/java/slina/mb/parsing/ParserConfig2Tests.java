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
public class ParserConfig2Tests {
	
	@Autowired
    @Qualifier("stdLogParser")
	Log4jParser stdLogParser;	
	

	
	String[] samples = { "2012-11-02 10:06:51,134 ERROR infrastructure.imp.AccessVoter Exception while authorizing page access.",
			 "org.springframework.jdbc.UncategorizedSQLException: CallableStatementCallback; uncategorized SQLException for SQL [{call PKG_EMPY_PERIODS(?, ?)}]; SQL state [72000]; error code [20020]; ORA-20020: invalid input;sysNum is null;procName:prc_cmp_empy_periods",
			"2012-11-02 11:42:29,132 ERROR infrastructure.imp.AccessVoter Exception while authorizing page access",
			 "2012-10-28 04:47:01,885 WARN  soa.interface.ConnectionTester The following exception occured while testing connection: xyz.tyu.com/192.168.1.1:52604, 1351414916340"
			  };


	
	 String[] singleSamples = {
			 "2012-11-02 10:06:51,134 ERROR infrastructure.imp.AccessVoter Exception while authorizing page access.",
		     "org.springframework.jdbc.UncategorizedSQLException: CallableStatementCallback; uncategorized SQLException for SQL [{call PKG_EMPY_PERIODS(?, ?)}]; SQL state [72000]; error code [20020]; ORA-20020: invalid input;sysNum is null;procName:prc_cmp_empy_periods"
	 };		
	 
		@Test
		public void testString() {
			
			List<String> stringList = Arrays.asList(singleSamples);
			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(2, stringList);
			 assertEquals(1,resultaArray.size());
			 this.printMathes(resultaArray);
		}
	 
	 
		@Test
		 public void ibmParserParserTest() {
			
			 List<String> stringList = Arrays.asList(samples);			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(2, stringList);
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
			 assertEquals(2,parserId);
			 this.printMathes(resultaArray);
		}	 

		/**
		 * Parser id 2 & 6 get the same results 
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
			 assertEquals(6,parserId);
			 this.printMathes(resultaArray);
		}	
	
	 protected void printMathes( List<LogEvent> resultaArray) {

			for(LogEvent event: resultaArray) {
				System.out.println(event);
			}
	 }	

}
