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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;

public class Parser4Tests {

	 String[] samples = {
		        "[2012-08-03 07:42:53,388] INFO  AbstractLoggingInterceptor -- Inbound Message",
		        "[  2012-08-03 07:42:53,388] INFO  AbstractLoggingInterceptor -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ] INFO  AbstractLoggingInterceptor -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ]   INFO  AbstractLoggingInterceptor -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ]   INFO     AbstractLoggingInterceptor -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ]   INFO     AbstractLoggingInterceptor   -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ]   INFO     AbstractLoggingInterceptor   --    Inbound Message",
		        "slina.controller.BoxSelector@35bf35bf[lang=gr,entityId=,clientId=,number=11",
		    };
	
	private String ibmParserRegex = "(\\s*\\[\\s*)(\\d{1,2})\\/(\\d{1,2})\\/(\\d{2,4}) (\\d{1,2}):(\\d{2}):(\\d{2}):(\\d{3}) (\\w{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$"; 
	private String parserRegex1 = "(\\s*\\[\\s*)(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*)--(\\s*) (.*)$";
	private String parserRegex2 = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$";
	private String parserRegex3 = "(\\d{2})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$";
	private String parserRegex4 = "(\\d{2})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$";
	private String parserRegex5 = "\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\s*[^ ]*)\\] (\\s*[^ ]*)\\[(\\s*[^ ]*) (\\s*[^ ]*) (.*)$" ;
	private String parserRegex6 =  "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$";
	private String filterParserRegex1 = "\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})\\] (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$" ;
	private String filterParserRegex2 = "(\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}\\]) (.*)$" ;
	private String customParserRegex1 = "(\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) (\\s*\\|) (\\s*[^ ]*) (\\s*\\|) (\\s*[^ ]*) (\\s*\\:) (\\s*[^ ]*) (\\s*\\|) (\\s*[^ ]*) (\\s*\\|) (.*)$" ;
	private String customParserRegex2 = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}).(\\d{3})(\\s*[^ ]*)(\\s*\\[\\s*)(\\s*[^ ]*)(\\]\\s*)(\\s*[^ ]*)(.*)$";
	private String customParserRegex3 = "\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})\\] (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$";
	private String customParserRegex4 = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) (\\s*[^ ]*) (\\s*\\[)(\\s*[^ ]*)(\\s*\\])(.*)$";

	
	private String[] parserArray = { ibmParserRegex, parserRegex1, parserRegex2,parserRegex3,  parserRegex4, parserRegex5,
			parserRegex6, filterParserRegex1, filterParserRegex2, customParserRegex1, customParserRegex2, 
			customParserRegex3.concat(customParserRegex4 )};
	
	
	@Test
	public void testString() {
		
		int arCount=0;
		
		for(String parsingString: parserArray) {
			
			Pattern p = Pattern.compile(parsingString);
			
			Matcher m = p.matcher(samples[0]);  
			
			int count = m.groupCount();
			assertTrue(count > 0);
			boolean matched = m.matches();
			
			if(matched) {
				System.out.println("---------------------------------");
				System.out.println("match --> "+arCount+"  =  "+parsingString);
				
				for(int i=0; i<count; i++) {
					System.out.println(i+"->   "+m.group(i).trim());
				}
				System.out.println("**********************************\n\n");
			} 
			arCount++;
		}

	}
	
	
	
	
	
	/**
	 * Happy path test
	 * @throws IOException
	 */
	@Ignore
	public void testIbmParserRegex() throws IOException {
		
		Pattern p = Pattern.compile(this.parserRegex2);
		
		for(int i=0; i<samples.length; i++ ) {
			
			Matcher m = p.matcher(samples[i]);  
			
			int count = m.groupCount();
			assertTrue(count > 0);
			assertTrue(m.matches());
		
			if(m.matches()) {
	
				  
				 String dateStamp = m.group(2);
				 assertTrue(dateStamp.length() > 0);				 
				 String[] dateNumbers = dateStamp.split("-");
				 
				 String timeStamp = m.group(3);				 
				 assertTrue(timeStamp.length() > 0);
				 String[] timeNumbers = timeStamp.split(":");
				 String[] secondNumbers = timeNumbers[2].split(",");
				 
				 int year = Integer.parseInt(dateNumbers[0]);
				 int month = Integer.parseInt(dateNumbers[1])-1;
				 int date = Integer.parseInt(dateNumbers[2]);
				 int hourOfDay = Integer.parseInt(timeNumbers[0]);
				 int minute = Integer.parseInt(timeNumbers[1]);
				 int second = Integer.parseInt(secondNumbers[0]);
				 int milSecs = Integer.parseInt(secondNumbers[1]);
			
				 
				 Calendar cal = Calendar.getInstance();
				 cal.set(year, month, date, hourOfDay, minute, second);
				 cal.set(Calendar.MILLISECOND, milSecs);
				 
				 Date mydate = cal.getTime();
				
				 assertNotNull(mydate);
				 assertEquals(2012,cal.get(Calendar.YEAR));
				 assertEquals(Calendar.AUGUST,cal.get(Calendar.MONTH));
				 assertEquals(3,cal.get(Calendar.DAY_OF_MONTH));
				 
				 String logLevel = m.group(5).trim();	
				 assertTrue(logLevel.length() > 0);
				 
				 String className = m.group(6).trim();
				 assertTrue(className.length() > 0);
				 
				 String message = m.group(9).trim();  
				 assertTrue(message.length() > 0);
				 			 
				 System.out.println(dateStamp+" - "+timeStamp+" - "+logLevel+" - "+className+" - "+message);

			} else {
				System.out.println("No match \n"+samples[i]);
			}
		}
	}
	
	/**
	 * test log pattern for parserRegex1: should fail
	 */
	@Test
	public void testParserRegex1() {
		
		Pattern p = Pattern.compile(this.parserRegex1);
		Matcher m = p.matcher(samples[0]);  
		assertTrue(m.matches());
		
		 
		 String timeStamp = m.group(3);
		 assertTrue(timeStamp.length() > 0);
		 String[] timeNumbers = timeStamp.split(":");
		 assertFalse(timeNumbers.length == 2);
		 
	}

}
