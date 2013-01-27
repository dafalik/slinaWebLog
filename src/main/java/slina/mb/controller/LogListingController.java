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
package slina.mb.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import slina.mb.domain.LogEvent;
import slina.mb.domain.TableInfo;
import slina.mb.domain.TableRows;
import slina.mb.menu.Node;
import slina.mb.work.LogServiceImpl;

@Controller
public class LogListingController {

	protected static Logger logger = Logger.getLogger("controller");
	
	
	@Autowired
	@Qualifier("menuConverter")
	LogServiceImpl logService;
	
	
	/**
	 * Handles and retrieves the common JSP page that everyone can see
	 * 
	 * @return WEB-INF/jsp/welcomepage.jsp page
	 */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String getCommonPage() {
    	logger.debug("Received request to show welcome page");
    	
    	// This will resolve to /WEB-INF/jsp/welcomepage.jsp
    	return "welcomepage";
	}
    
    /**
     * This starts application. javascript on the page calls server back menus,
     * log listings etc.
     * @return WEB-INF/jsp/log_listing.jsp page
     */
    @RequestMapping(value = "/logListing", method = RequestMethod.GET)
    public String getmenuPage() {
    	logger.debug("Received request to show welcome page");
    	
    	// This will resolve to /WEB-INF/jsp/log_listing.jsp
    	return "log_listing";
	}
    
    
    /**
     * @return menu list as json array
     */
    @RequestMapping(value = "/menuTree", method = RequestMethod.GET)
    public  @ResponseBody List<Node> getJstree() {
    	logger.debug("Received request to jstree page");
    	List<Node> nodeList = this.logService.getProdNodeList();
    	return nodeList;
	}
    
    
    /**
     * Returns log listing details as jason array. If uniqueId=0 (default value) return empty log list.
     * Converts List<LogEvent> eventList to TableRows objects for presentation. 
     * Creates TableInfo objects that carries TableRows and other information (like totals) back to screen
     * Spring framework converts TableInfo object to jason object and delivers back to screen.
     * @param uniqueId of log listing requested
     * @param session
     * @return log listing as json array
     */
	@RequestMapping(value = "/getLogListings", method = RequestMethod.GET)
	public @ResponseBody TableInfo initializePageFields(@RequestParam(value="uniqueId") int uniqueId)  {
	   		   
		   if(uniqueId==0) {
			   
			   return this.getDefaultTableInfo();
		   }   		   
		   logger.info("getLogListings id:  "+uniqueId);
		   
		try {
			   List<LogEvent> eventList = this.logService.processLog(uniqueId);
			   TableInfo tableInfo = this.createTableInfoFromLogEventList(eventList, false);
			   return tableInfo;
			   
			   
		} catch (IOException e) {
			
				e.printStackTrace();
			   return this.getDefaultTableInfo();						
		}

	  }


      
	  /**
	   * Returns combined log listing details as jason array. The input combine log listings are taken
	   * from scopeArray input variable sent from screen.
	   * If scopeArray does not have values, return empty log list.
	   * @param scopeArray
	   * @param session
	   * @return log listing as json array
	   */
	  @RequestMapping(value = "/getLogMultiListings", method = RequestMethod.GET)
	  public @ResponseBody TableInfo getMultiLogs(@RequestParam(value="scope") int[] scopeArray)  {
	   
		   
		   if(scopeArray==null || scopeArray.length == 0) {
			   
			   return this.getDefaultTableInfo();
		   }   
		   
		   logger.info("getLogListings id:  "+scopeArray);
		   
		try {
						
			   List<LogEvent> eventList = this.logService.processLog(scopeArray);
			   return this.createTableInfoFromLogEventList(eventList, false);
	   
		} catch (IOException e) {
			
				e.printStackTrace();
			   return this.getDefaultTableInfo();
		}

	  }    
    
 
	   @RequestMapping(value = "/getProdWarnings", method = RequestMethod.GET)
	   public @ResponseBody TableInfo getAdvanceWarnings(@RequestParam(value="env") String envId)  {
	   
		   
		   if(envId==null || envId.length() == 0) {
			   
			   return this.getDefaultTableInfo();
		   }   
		   
		   logger.info("getAdvanceWarnings id:  "+envId);
	
		   List<LogEvent> eventList = this.logService.getProductionWarnings();
		   return this.createTableInfoFromLogEventList(eventList, true);
	  }    	   
	   
		/**
		 * @return empty log listing
		 */
		private TableInfo getDefaultTableInfo() {
			TableInfo info = new TableInfo();
			   info.setsEcho(1);
			   info.setiTotalDisplayRecords(0);
			   info.setiTotalRecords(0);
			   return info;
		}	   
	   
		
		/**
		 * Converts List<LogEvent> eventList to TableRows objects for presentation. 
		 * Creates TableInfo objects that carries TableRows and other information (like totals) back to screen
		 * @param eventList
		 * @param setserver
		 * @return log listing as TableInfo object, which wrraps LogEvents converted to TableRows
		 */
		private TableInfo createTableInfoFromLogEventList(List<LogEvent> eventList, boolean setserver) {
			
			   TableInfo info = new TableInfo();
			   info.setsEcho(1);			   
			   
			   for(LogEvent logEvent:eventList) {
				   
				   if(logEvent == null)
					   continue;
				   				   
				   TableRows row = new TableRows(logEvent.getCal().toString(), logEvent.getLogLevel().getName(), logEvent.getLogClass(), 
						   logEvent.getLogMessage());
				   
				   
				   if(setserver) {
					   row.setServer(logEvent.getServer());
				   }
				   
				   row.setTimestamp(logEvent.getCal().getTime());
				   
				   if(logEvent.getLogDetails().size() == 0 ) {
					   row.setDetailsLink("");					   

				   } else {					   
					   List<String> logDetails = logEvent.getLogDetails();					   
					   row.setLogDetails(logDetails);
				   }				   
				   info.getAaData().add(row);				   
			   }
			   
			   info.setiTotalDisplayRecords(eventList.size());
			   info.setiTotalRecords(eventList.size());
			   return info;
			
		}
		
    
}
