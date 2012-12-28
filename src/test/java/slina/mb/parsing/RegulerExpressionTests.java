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



import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import slina.mb.domain.LogEventImpl;
import slina.mb.domain.Loglevel;

public class RegulerExpressionTests {
	
	
	 String[] samples = {
		        "[2012-08-03 07:42:53,388] INFO  AbstractLoggingInterceptor -- Inbound Message",
		        "[  2012-08-03 07:42:53,388] INFO  AbstractLoggingInterceptor -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ] INFO  AbstractLoggingInterceptor -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ]   INFO  AbstractLoggingInterceptor -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ]   INFO     AbstractLoggingInterceptor -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ]   INFO     AbstractLoggingInterceptor   -- Inbound Message",
		        "[   2012-08-03 07:42:53,388  ]   INFO     AbstractLoggingInterceptor   --    Inbound Message",
		        "ig.mailbox.controller.MailboxSelector@35bf35bf[lang=fr,igEntityId=,clientId=,consultantNumber=11"
		    };
	 
	 
	 String[] samples1 = {
		        "2012-08-06 04:59:33,129 WARN  corp.soa.serviceinterface.ConnectionTester The following exception occured while testing connection: ssaiap3.gwl.bz/10.90.19.52:26054, 1344246273129",
//		        "2012-08-03 11:37:33,828 ERROR ig.bcs.infrastructure.imp.ConsultantAccessVoter Exception while authorizing page access.",
//		        "2012-07-25 23:17:39,064 INFO  org.springframework.web.context.support.XmlWebApplicationContext Closing org.springframework.web.context.support.XmlWebApplicationContext@130b130b: display name [WebApplicationContext for namespace 'consultantPracticeIWAWar-servlet']; startup date [Sun Jul 22 10:57:08 CDT 2012]; root of context hierarchy",
//		        "2012-08-06 14:05:29,587 DEBUG ig.fs.eLearningSilentLogin.action.SilentLoginAction - ##-----------------------------------------------------------##",
//		        "2012-07-30 14:10:36,535 INFO  ig.fs.highRiskMonitoring.service.MonitoredClientService                User FAILIJ1 saving data for client         3151346 7925506 CANADA INC",
//		        "2012-08-03 10:01:37,502 ERROR ig.fs.MortgageInq.util.AuthorizationUtil                               Authentication Exception : [LDAP: error code 32 - 0000208D: NameErr: DSID-031001D2, problem 2001 (NO_OBJECT), data 0, best match of:'OU=Members,O=Investors Group'",
//		        "2012-08-02 07:07:21,425 WARN  org.apache.cxf.phase.PhaseInterceptorChain Interceptor for {urn:pi:services:integration:11.0.0.3}PortfolioInquiryServiceInterfaceService#{urn:pi:services:integration:11.0.0.3}GetPortfolioSummary has thrown exception, unwinding now",
//			 	  "2012-08-07 06:41:00,750 INFO  getFamilyGroup(\"2501000\")",
//			 	  "2012-08-07 00:51:48,326 DEBUG com.ig.pfp.services.dao.dbimpl.BaseDAO PathwayContact =====> com.ig.pfp.services.domain.LDAPRoleMapping@30b630b6["
		    };

	 String[] samples2 = {
				 "06:02:05,787 | INFO  | WebContainer : 3 | lFileTransferServiceImpl | Executing operation getAchivedFile"
			    };	 
	 
		private  String regex ="(\\s*\\[\\s*)(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*)--(\\s*) (.*)$";

		private  String regex1 ="(\\s*\\[\\s*)(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})(\\s*\\]) (\\s*[^ ]*) (\\s*[^ ]*) (\\s*)--(\\s*) (.*)$";

		private  String regex3 ="(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) (\\s*[^ ]*) (\\s*[^ ]*) (.*)$";

		private  String regex4 ="(\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) (\\s*\\|) (\\s*[^ ]*) (\\s*\\|) (\\s*[^ ]*) (\\s*\\:) (\\s*[^ ]*) (\\s*\\|) (\\s*[^ ]*) (\\s*\\|) (.*)$";

		
		
		/**

0. 1    --->   06
0. 2    --->   02
0. 3    --->   05
0. 4    --->   787
0. 5    --->   |
0. 6    --->   INFO
0. 7    --->    |
0. 8    --->   WebContainer
0. 9    --->   :
0. 10    --->   3
0. 11    --->   |
0. 12    --->   lFileTransferServiceImpl
0. 13    --->   |
0. 14    --->   Executing operation getAchivedFile

		 */
		
		@Test
		public void readStringTest4() throws IOException {
			
			Pattern p = Pattern.compile(this.regex4);
			
			for(int i=0; i<samples2.length; i++ ) {
				
				Matcher m = p.matcher(samples2[i]);
//				int count = m.groupCount();
				
				if(m.matches()) {
		
				//	   for(int j=1; j<=count; j++) {
				//		  System.out.println(i+". "+j+"    --->   "+m.group(j));  
				//	   }
					  
					   
	LogEventImpl bf = new LogEventImpl("2050","12","12",m.group(1),m.group(2),m.group(3),m.group(4),m.group(12).trim(), m.group(14).trim());	
//	String logLevelString = m.group(6).trim();
	bf.setLogLevel(Loglevel.INFO);
	
	 System.out.println(bf.toString());	
	// System.out.println(bf.getCal());	
	
				//	 String dateStamp = m.group(2);
				//	 String timeStamp = m.group(3);
				//	 String logLevel = m.group(5).trim();	   
				//	 String className = m.group(6).trim();
				//	 String message = m.group(9).trim();  
					 
					 
				//	 System.out.println(dateStamp+" - "+timeStamp+" - "+logLevel+" - "+className+" - "+message);
					 
					 
					   
				} else {
					System.out.println("No match \n"+samples2[i]);
					

					
				}
			}
		}	 		
		

		/**
		 * 
0. 1    --->   2012
0. 2    --->   08
0. 3    --->   06
0. 4    --->   04
0. 5    --->   59
0. 6    --->   33
0. 7    --->   129
0. 8    --->   WARN
0. 9    --->    corp.soa.serviceinterface.ConnectionTester
0. 10    --->   The following exception occured while testing connection: ssaiap3.gwl.bz/10.90.19.52:26054, 1344246273129

		 */
		
		
		@Test
		public void readStringTest3() throws IOException {
			
			Pattern p = Pattern.compile(this.regex3);
			
			for(int i=0; i<samples1.length; i++ ) {
				
				Matcher m = p.matcher(samples1[i]);
	//			int count = m.groupCount();
				
				if(m.matches()) {
		
	//				   for(int j=1; j<=count; j++) {
		//				  System.out.println(i+". "+j+"    --->   "+m.group(j));  
	//				   }

 LogEventImpl bf = new LogEventImpl(m.group(1),m.group(2),m.group(3),m.group(4),m.group(5),m.group(6),m.group(7),m.group(9).trim(), m.group(10).trim());
// String logLevelString = m.group(8).trim();		
 bf.setLogLevel(Loglevel.INFO);
 
 System.out.println(bf.toString());
				//	 String dateStamp = m.group(2);
				//	 String timeStamp = m.group(3);
				//	 String logLevel = m.group(5).trim();	   
				//	 String className = m.group(6).trim();
				//	 String message = m.group(9).trim();  

					 
				//	 System.out.println(dateStamp+" - "+timeStamp+" - "+logLevel+" - "+className+" - "+message);
					 
					 
					   
				} else {
					System.out.println("No match \n"+samples1[i]);
					

					
				}
			}
		}	 
		
		
		
		//@Test
		public void readStringTest() throws IOException {
			
			Pattern p = Pattern.compile(this.regex);
			
			for(int i=0; i<samples.length; i++ ) {
				
				Matcher m = p.matcher(samples[i]);
	//			int count = m.groupCount();
				
				if(m.matches()) {
		
				//	   for(int j=1; j<=count; j++) {
				//		  System.out.println(i+". "+j+"    --->   "+m.group(j));  
				//	   }
					   
					 String dateStamp = m.group(2);
					 String timeStamp = m.group(3);
					 String logLevel = m.group(5).trim();	   
					 String className = m.group(6).trim();
					 String message = m.group(9).trim();  
					 
					 
					 System.out.println(dateStamp+" - "+timeStamp+" - "+logLevel+" - "+className+" - "+message);
					 
					 
					   
				} else {
					System.out.println("No match \n"+samples[i]);
				}
			}
		}	 

		//@Test
		public void readStringTest1() throws IOException {
			
			Pattern p = Pattern.compile(this.regex1);
			
			for(int i=0; i<samples.length; i++ ) {
				
				Matcher m = p.matcher(samples[i]);
	//			int count = m.groupCount();
				
				if(m.matches()) {
		
					// System.out.println(i +" matched ");
					//   for(int j=1; j<=count; j++) {
					//	  System.out.println(i+". "+j+"    --->   "+m.group(j));  
					//   }
					   String year = m.group(2);
					   String month = m.group(3);
					   String day = m.group(4);
					   String hour = m.group(5);
					   String minute = m.group(6);
					   String seconds = m.group(7);
					   String miliseconds = m.group(8);
					   String logLevel = m.group(10).trim();
					   String logClass = m.group(11).trim();
					   String logMessage = m.group(14).trim();
					   
					   System.out.println(year+"-"+month+"-"+day+" "+ " - "+hour+":"+minute+":"+
							   seconds+":"+miliseconds+" -- "+logLevel+" -- "+logClass+" -- "+logMessage);
					   
				} else {
					System.out.println("No match \n"+samples[i]);
				}
			}
		}

}
