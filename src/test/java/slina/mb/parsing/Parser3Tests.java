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

public class Parser3Tests {
	
	private String ibmParserRegex = "(\\s*\\[\\s*)(\\d{1,2})\\/(\\d{1,2})\\/(\\d{2,4}) (\\d{1,2}):(\\d{2}):(\\d{2}):(\\d{3}) (\\w{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$"; 
	
	 String[] samples = {
		        "[11/2/12 8:23:59:400 CDT] 00000065 SpnegoHandler W com.ibm.ws.security.spnego.SpnegoHandler handleRequest CWSPN0021E: No delegated credentials were found for user: CHARDK1@IGA.BZ.",
		        "[11/2/12 8:24:07:258 CDT] 00000069 ClientSideUse I   UserCredentialsClientInterceptor handleMessage invoked",
		        "[11/2/12 8:57:58:221 CDT] 00000007 TimeoutManage I   WTRN0124I: When the timeout occurred the thread with which the transaction is, or was most recently, associated was Thread[WebContainer : 33,5,main]. The stack trace of this thread when the timeout occurred was: ",
		        "[11/2/12 7:59:31:820 CDT] 00000014 HttpConnectio W org.apache.commons.httpclient.HttpConnection releaseConnection HttpConnectionManager is null.  Connection cannot be released.",
		        "[11/2/12 12:56:36:534 CST] 00002b37 ExchangeSyncI W   [sa <ExchangeFolder:OID:1:V32:BC85AD2FDCC14917B03B50160F9A8243>] Synchronization completed, but some instances could not be synchronized."
		    };

	 
	 
		@Test
		public void testString() {
	
				
				Pattern p = Pattern.compile(ibmParserRegex);
				
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
		 
		 Pattern p = Pattern.compile(ibmParserRegex);
		List<String> resultaArray = new ArrayList<String>();
			

			
			for(String value: samples) {

				
				Matcher m = p.matcher(value);  
				int count = m.groupCount();
				assertTrue(count > 0);
				boolean matched = m.matches();
				assertTrue(matched);

				 
				 int year = Integer.parseInt(m.group(2));
				 
				 if(year < 100)
					 year = year +2000;
				 
				 
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
				 assertEquals(2011,cal.get(Calendar.YEAR));
				 assertEquals(Calendar.FEBRUARY,cal.get(Calendar.MONTH));
				 assertEquals(12,cal.get(Calendar.DAY_OF_MONTH));
				 
				 String logLevel = m.group(13).trim();	
				 assertTrue(logLevel.length() > 0);
				 
				 String className = m.group(12).trim();
				 assertTrue(className.length() > 0);
				 
				 String message = m.group(14).trim();  
				 assertTrue(message.length() > 0);
				 			 
				 String logRecord = mydate+" - "+" - "+logLevel+" - "+className+" - "+message;				 			 
				 System.out.println(logRecord);
				 resultaArray.add(logRecord);
				
			}
					
		 
			assertEquals(5,resultaArray.size());
		 
	 }
	 
	 
	 private void printMathes(int count, Matcher m) {
		 
			System.out.println("---------------------------------");
			
			
			
			for(int i=0; i<=count; i++) {
				System.out.println(i+"->   "+m.group(i));
			}
			System.out.println("**********************************\n\n");
		 
	 }
	 
	

}
