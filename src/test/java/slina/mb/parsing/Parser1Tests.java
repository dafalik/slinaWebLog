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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Test strings for parserConfig1 
 */
public class Parser1Tests {

	private String parserRegex = "(\\s*\\[\\s*)(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*)--(\\s*) (.*)$";
	
	String[] samples = { "[2012-11-02 09:31:34,971  ] INFO   Log4jLoggerImpl -- Pool: Inquiry; Active Connections: 0; Available Connections: 1; Min Connections: 1; Max Connections: 15",
						 "[2012-11-02 09:31:34,973  ] INFO   Log4jLoggerImpl -- Service 'verifyconnection', Initialize=0, Format=0, Send=1, Receive=1, Parse=0, WS=2, Total=2",
						 "[2012-11-02 09:38:47,084  ] INFO   AuthUserDetailsService -- Authticating garmix@mydom.com",
						 "[2012-11-02 09:38:47,112  ] INFO   LdapUserDetailsMapper -- Mapping user details from context with DN: cn=gaviz\\, kiyus,ou=Sales,ou=Accounts,ou=tricx,dc=ytx,dc=com",
						 "[2012-11-02 09:38:47,112  ] INFO   LdapUserDetailsMapper -- essence: security.ldap.userdetails.LdapUserDetailsImpl@fe305159: Dn: cn=gaviz\\, kiyus,ou=Sales,ou=Accounts,ou=tricx,dc=ytx,dc=com; Username: gavizk@ytx.com; Enabled: true; AccountNonExpired: true; CredentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_SALES",					 
						 "[2012-11-02 09:38:47,112  ] INFO   PreAuthUserDetailsService -- loadUserByUsername  security.ldap.userdetails.LdapUserDetailsImpl@fe305159: Dn: cn=gaviz\\, kiyus,ou=Sales,ou=Accounts,ou=tricx,dc=ytx,dc=com; Username: gavizk@ytx.com; Username: gavizk@ytx.com; Enabled: true; AccountNonExpired: true; CredentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_SALES",
						 "[2012-11-02 09:38:47,114  ] INFO   AjaxController -- client locale in session: fr" };

	
	 
		@Test
		public void testString() {
				
				Pattern p = Pattern.compile(parserRegex);
				
				Matcher m = p.matcher(samples[0]);  
				
				int count = m.groupCount();
				assertTrue(count > 0);
				boolean matched = m.matches();
				assertTrue(matched);
				
				if(matched) {
					this.printMathes(count, m);
				}
		}		
	

		
		
		@Test
		 public void sinParserTest() {
			 
			 Pattern p = Pattern.compile(parserRegex);
			List<String> resultaArray = new ArrayList<String>();
				

				
				for(String value: samples) {

					
					Matcher m = p.matcher(value);  
					int count = m.groupCount();
					assertTrue(count > 0);
					boolean matched = m.matches();
					assertTrue(matched);

					 
					 int year = Integer.parseInt(m.group(2));
					 
					 int month = Integer.parseInt(m.group(3))-1;
					 int date = Integer.parseInt(m.group(4));
					 int hourOfDay = Integer.parseInt(m.group(5));
					 int minute = Integer.parseInt(m.group(6));
					 int second = Integer.parseInt(m.group(7));
					 int milSecs = Integer.parseInt(m.group(8));
				
					 
					 Calendar cal = Calendar.getInstance();
					 cal.set(year, month, date, hourOfDay, minute, second);
					 cal.set(Calendar.MILLISECOND, milSecs);
					 
					 Date mydate = cal.getTime();
					
					 assertNotNull(mydate);
					 assertEquals(2012,cal.get(Calendar.YEAR));
					 assertEquals(Calendar.NOVEMBER,cal.get(Calendar.MONTH));
					 assertEquals(2,cal.get(Calendar.DAY_OF_MONTH));
					 
					 String logLevel = m.group(10).trim();	
					 assertTrue(logLevel.length() > 0);
					 
					 String className = m.group(11).trim();
					 assertTrue(className.length() > 0);
					 
					 String message = m.group(14).trim();  
					 assertTrue(message.length() > 0);
					 			 
					 String logRecord = mydate+" - "+" - "+logLevel+" - "+className+" - "+message;				 			 
					 System.out.println(logRecord);
					 resultaArray.add(logRecord);
					
				}	 
				assertEquals(7,resultaArray.size());		 
		 }
		
		
		 private void printMathes(int count, Matcher m) {
			 
				System.out.println("---------------------------------");
			
				for(int i=0; i<=count; i++) {
					System.out.println(i+"->   "+m.group(i));
				}
				System.out.println("**********************************\n\n");
			 
		 }	
	
	
}
