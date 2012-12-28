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

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class Parser2Tests {
	
	private String parserRegex1 = "(\\s*\\[\\s*)(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*)--(\\s*) (.*)$";

	
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
	 
	 
	 @Test
	 public void sinParserTest() {
		 
		 Pattern p = Pattern.compile(parserRegex1);
		 
			

			
			for(int i=0; i<7; i++) {
				
				String value = samples[i];
				
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
				 assertEquals(Calendar.AUGUST,cal.get(Calendar.MONTH));
				 assertEquals(3,cal.get(Calendar.DAY_OF_MONTH));
				 
				 String logLevel = m.group(5).trim();	
				 assertTrue(logLevel.length() > 0);
				 
				 String className = m.group(11).trim();
				 assertTrue(className.length() > 0);
				 
				 String message = m.group(9).trim();  
				 assertTrue(message.length() > 0);
				 			 
				 System.out.println(mydate+" - "+" - "+logLevel+" - "+className+" - "+message);

				
			}

		 
	 }
	 
	 
	 
	

}
