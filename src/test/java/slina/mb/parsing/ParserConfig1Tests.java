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
public class ParserConfig1Tests {
	
	@Autowired
    @Qualifier("stdLogParser")
	Log4jParser stdLogParser;	
	
	
	String[] samples = { "[2012-11-02 09:31:34,971  ] INFO   Log4jLoggerImpl -- Pool: Inquiry; Active Connections: 0; Available Connections: 1; Min Connections: 1; Max Connections: 15",
			 "Exception: logging stack trace goes here",
			"[2012-11-02 09:31:34,973  ] INFO   Log4jLoggerImpl -- Service 'verifyconnection', Initialize=0, Format=0, Send=1, Receive=1, Parse=0, WS=2, Total=2",
			 "[2012-11-02 09:38:47,084  ] INFO   AuthUserDetailsService -- Authticating garmix@mydom.com",
			 "[2012-11-02 09:38:47,112  ] INFO   LdapUserDetailsMapper -- Mapping user details from context with DN: cn=gaviz\\, kiyus,ou=Sales,ou=Accounts,ou=tricx,dc=ytx,dc=com",
			 "[2012-11-02 09:38:47,112  ] INFO   LdapUserDetailsMapper -- essence: security.ldap.userdetails.LdapUserDetailsImpl@fe305159: Dn: cn=gaviz\\, kiyus,ou=Sales,ou=Accounts,ou=tricx,dc=ytx,dc=com; Username: gavizk@ytx.com; Enabled: true; AccountNonExpired: true; CredentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_SALES",					 
			 "[2012-11-02 09:38:47,112  ] INFO   PreAuthUserDetailsService -- loadUserByUsername  security.ldap.userdetails.LdapUserDetailsImpl@fe305159: Dn: cn=gaviz\\, kiyus,ou=Sales,ou=Accounts,ou=tricx,dc=ytx,dc=com; Username: gavizk@ytx.com; Username: gavizk@ytx.com; Enabled: true; AccountNonExpired: true; CredentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_SALES",
			 "[2012-11-02 09:38:47,114  ] INFO   AjaxController -- client locale in session: fr" };


	
	 String[] singleSamples = {
			 "[2012-11-02 09:31:34,971  ] INFO   Log4jLoggerImpl -- Pool: Inquiry; Active Connections: 0; Available Connections: 1; Min Connections: 1; Max Connections: 15",
		        "Exception: logging stack trace goes here"
	 };		
	 
		@Test
		public void testString() {
			
			List<String> stringList = Arrays.asList(singleSamples);
			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(1, stringList);
			 assertEquals(1,resultaArray.size());
			 this.printMathes(resultaArray);
		}
	 
	 
		@Test
		 public void ibmParserParserTest() {
			
			 List<String> stringList = Arrays.asList(samples);			
			 List<LogEvent> resultaArray = stdLogParser.createLogEvents(1, stringList);
			 assertEquals(7,resultaArray.size());
		}
	 
		@Test
		 public void tryAllParsersForwardTest() {
			
			 List<String> stringList = Arrays.asList(samples);
			 List<LogEvent> resultaArray = null;
			 int parserId = -1;
			
			 for(int i=0; i<12; i++) {
				 
				 resultaArray = stdLogParser.createLogEvents(i, stringList);
				 if(resultaArray.size() == 7) {
					 System.out.println("Forward list matching parser is: "+i);
					 parserId = i;
					 break;
				 }
			 }
			 
			 assertNotNull(resultaArray);
			 assertEquals(7,resultaArray.size());
			 assertEquals(1,parserId);
		}	 

		
		@Test
		 public void tryAllParsersReverseTest() {
			
			 List<String> stringList = Arrays.asList(samples);
			 List<LogEvent> resultaArray = null;
			 int parserId = -1;
			
			 for(int i=11; i >-1; i--) {
				 
				 resultaArray = stdLogParser.createLogEvents(i, stringList);
				 if(resultaArray.size() == 7) {
					 System.out.println("Reverse list matching parser is: "+i);
					 parserId = i;
					 break;
				 }
			 }
			 
			 
			 assertNotNull(resultaArray);
			 assertEquals(7,resultaArray.size());
			 assertEquals(1,parserId);
		}	
	
	 protected void printMathes( List<LogEvent> resultaArray) {

			for(LogEvent event: resultaArray) {
				System.out.println(event);
			}
	 }	

}
