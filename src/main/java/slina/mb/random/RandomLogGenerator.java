package slina.mb.random;

public class RandomLogGenerator {

	String timeStamp = "";
	String timeStampWithZone  = "";
	String timeOnlyStamp = "";
	String logLevel = "";
	String logLevelLetter = "";
	String logClass = "";
	String logMessage = "";
	String randomString2 = "";
	String randomString1 = "";
	
	String[] app1 = {"[",timeStamp,"  ] ",logLevel,"   ",logClass," -- ",logMessage  };
	//[2012-11-02 09:31:34,971  ] INFO   Log4jLoggerImpl -- Pool: Inquiry; Active Connections: 0; Available Connections: 1; Min Connections: 1; Max Connections: 15,

	String[] app8 = { timeOnlyStamp," | ", logLevel," | ", randomString2 , logClass, " | ", logMessage };
//	String app8 = "16:10:17,185 | INFO  | WebContainer : 5 | TrsferServiceImpll | Executing operation getAchivedFile";

	String[] app9 = {timeStamp, " [" , logLevel, "] ", logClass, " ", logMessage};
	//String app9 = "2012-11-02 10:14:05,407 [ERROR] com.drfxtry.Logger R1062143	-	-	Authenticator.getProfile - There was an issue getting user profile from external service.,";

	String[] application1,application2 = {"[", timeStamp,"]", randomString2, logClass, " - ", logMessage};
//application1 = [2012-11-02 10:28:41,529] Default : 9 ERROR exception.CAStrutsExceptionHandler - A struts action threw an exception:
//               [2012-11-02 09:01:20,015] Default : 51 WARN  slina.action.Login - Received the error codes= 88CF0428 from webseal

	String[] systemOut = { "[", timeStampWithZone,"]", randomString1 , logLevelLetter, randomString1,logMessage };
//[11/2/12 8:57:58:221 CDT] 00000007 TimeoutManage I   WTRN0124I: When the timeout occurred the thread with which the transaction is, or was most recently, associated was Thread[WebContainer : 33,5,main]. The stack trace of this thread when the timeout occurred was: ,

	String[] app2 = { timeStamp, logLevel, logClass, logMessage };
	//2012-08-02 11:06:51,134 ERROR infrastructure.imp.AccessVoter Exception while authorizing page access.,

	String[] app4 = {  timeStamp, logLevel, randomString2, "Class:",logClass, logMessage };
	//12-10-02 10:52:53,626 DEBUG org.apache.jk.server.JkCoyoteHandler Class:org.apache.jk.server.JkCoyoteHandler Method:doWrite: Line:250 - doWrite 0 8184 8

	String[] app7 = { "[", timeStamp,"]", randomString2, logLevel, logClass ," - ", logMessage};
//[2012-11-02 07:09:46,806] Default : 22 WARN  tivo.ca.bc.Vindmar - Due to XSD requirements, data objects are being populated on this request



}
