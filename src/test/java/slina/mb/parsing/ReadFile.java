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
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author anil
 *
 */
public class ReadFile {
	 String[] samples = {
		        "1999-11-27 15:49:37,459 [thread-x] ERROR mypackage - Catastrophic system failure",
		        "[2012-08-03 07:42:53,388    ] INFO  AbstractLoggingInterceptor -- Inbound Message",
		        "[8/3/12 8:44:44:810 CDT] 0000008e ClientSideUse I   UserCredentialsClientInterceptor handleMessage invoked",
		        "[8/3/12 8:44:44:810 CDT ] 0000003 goHandler W com.ibm.ws.security.spnego.SpnegoHandler",
		        "[8/3/12 9:13:47:517 CDT] 0000004c LTPAServerObj W   SECJ0371W: Validation of the LTPA token failed because the token expired with the following info: Token expiration Date: Thu",
		        "[8/3/12 9:17:27:739 CDT] 0000004c SystemOut     O message : ",
		        "[8/3/12 8:34:46:988 CDT] 00000038 ClientSideUse I   UserCredentialsClientInterceptor handleMessage invoked",
		        "[2012-08-01 08:20:54,915  ] INFO   -- "
		    };

	 
	 String[] ibmsamples = {
		        "[8/3/12 8:44:44:810 CDT] 0000008e ClientSideUse I   CredentialsClientInterceptor handleMessage invoked",
		        "[8/3/12 8:44:44:810 CDT ] 0000003 goHandler W com.ibm.ws.security.spnego.SpnegoHandler",
		        "[8/3/12 9:13:47:517 CDT] 0000004c LTPAServerObj W   SECJ0371W: Validation of the LTPA token failed because the token expired with the following info: Token expiration Date: Thu",
		        "[8/3/12 9:17:27:739 CDT] 0000004c SystemOut     O message : ",
		        "[8/3/12 8:34:46:988 CDT] 00000038 ClientSideUse I   UserCredentialsClientInterceptor handleMessage invoked",
		    };
	 
	 
	String regex = "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) \\[(.*)\\] ([^ ]*) ([^ ]*) - (.*)$";	
	String regex1 ="(\\s*\\[\\s*)(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*)--(\\s*) (.*)$";
	String regex2 ="(\\s*\\[\\s*)(\\d{4}\\/\\d{2}\\/\\d{2} ) (\\d{2}:\\d{2}:\\d{2},\\d{3} \\w{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*)--(\\s*) (.*)$";
	String regex21 ="(\\s*\\[\\s*)(\\d{1,2})\\/(\\d{1,2})\\/(\\d{2,4}) (\\d{1}):(\\d{2}):(\\d{2}):(\\d{3}) (\\w{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$";
	String regex212 ="(\\s*\\[\\s*)(\\d{1,2})\\/(\\d{1,2})\\/(\\d{2,4}) (\\d{1}):(\\d{2}):(\\d{2}):(\\d{3}) (\\w{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$";
	String regex211 ="(\\d{1,2}-\\d1,2}-\\d{2,4}) (\\d{1,2}:\\d{1,2}:\\d{1,2},\\d{3})";
	

	@Test
	public void testForIBMtimestamp() {
		
	    Pattern p = Pattern.compile(regex212);

	    Matcher m = p.matcher(ibmsamples[0]);	    
	    boolean matched = m.matches(); 
	    assertTrue(matched);
  
	    int count = m.groupCount();
	    assertEquals(14,  count);

	    assertEquals(ibmsamples[0],m.group(0));
	    assertEquals("[",m.group(1));
	    assertEquals("8",m.group(2));
	    assertEquals("3",m.group(3));
	    assertEquals("12",m.group(4));
	    assertEquals("8",m.group(5));
	    assertEquals("44",m.group(6));
	    assertEquals("44",m.group(7));
	    assertEquals("810",m.group(8));
	    assertEquals("CDT",m.group(9));
	    assertEquals("]",m.group(10));
	    assertEquals("0000008e",m.group(11));
	    assertEquals("ClientSideUse",m.group(12));
	    assertEquals("I",m.group(13));
	    assertEquals("  CredentialsClientInterceptor handleMessage invoked",m.group(14));
	
/*	    
	   for(int i=0; i<=count; i++) {
		   System.out.println(i+"  --> "+m.group(i));
	   }
	   */
	   
	}
		
	@Test
	public void testSystemOutLogLines() {
		
		 Pattern p = Pattern.compile(regex212);
		 
		 for(String t: ibmsamples) {
			 
			 Matcher m = p.matcher(t);	
			 boolean matched = m.matches(); 
			 assertTrue(matched);
			 int count = m.groupCount();
	 
			 assertTrue(count > 0);
			 assertTrue(Integer.parseInt(m.group(2)) > 0);
			 assertTrue(Integer.parseInt(m.group(3)) > 0);
			 assertTrue(Integer.parseInt(m.group(4)) > 0);
			 assertTrue(Integer.parseInt(m.group(5)) > 0);
			 assertTrue(Integer.parseInt(m.group(6)) > 0);
			 assertTrue(Integer.parseInt(m.group(7)) > 0);
			 assertTrue(Integer.parseInt(m.group(8)) > 0);
			 assertEquals("CDT",m.group(9));			 
			 
			 assertTrue(m.group(12).length() > 0);
			 assertTrue(m.group(14).length() > 0);
			 
			 String flag = m.group(13).trim();			 
			 assertTrue(flag.equalsIgnoreCase("I") || flag.equalsIgnoreCase("W")  || flag.equalsIgnoreCase("O")  );
			 
			 
		 }
		
		
	}
	
	
	@Test
	public void testFortimestamp() {
		
	    Pattern p = Pattern.compile(regex1);

	    Matcher m = p.matcher(samples[7]);
	    
	    boolean matched = m.matches(); 
	    assertTrue(matched);
	    
	    int count = m.groupCount();
	    assertEquals(9,count);
	    
	    String date = m.group(2);
	    assertEquals("2012-08-01",date);
	    
        String time = m.group(3);
        assertEquals("08:20:54,915",time);
        
        String type = m.group(5);
        assertEquals("INFO",type);
        
        String classname = m.group(6);
        assertEquals(" ",classname);
        
        String message = m.group(9);
        assertEquals("",message);	    
	    
	}
	

	
	
}
