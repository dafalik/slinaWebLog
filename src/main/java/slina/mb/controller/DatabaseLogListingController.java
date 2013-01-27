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

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import slina.mb.domain.TableInfo;
import slina.mb.menu.Node;
import slina.mb.work.DatabaseLogServiceImpl;

@Controller
public class DatabaseLogListingController {

	protected static Logger logger = Logger.getLogger("controller");
	
	
	@Autowired
	@Qualifier("dataService")
	DatabaseLogServiceImpl logService;
	
	

    
    /**
     * This starts application. javascript on the page calls server back menus,
     * log listings etc.
     * @return WEB-INF/jsp/log_listing.jsp page
     */
    @RequestMapping(value = "/dblogListing", method = RequestMethod.GET)
    public String getmenuPage() {
    	logger.debug("Received request to show welcome page");
    	
    	// This will resolve to /WEB-INF/jsp/log_listing.jsp
    	return "db_log_listing";
	}
    
    
    /**
     * @return menu list as json array
     */
    @RequestMapping(value = "/dbmenuTree", method = RequestMethod.GET)
    public  @ResponseBody List<Node> getJstree() {
    	logger.debug("Received request to jstree page");
    	List<Node> nodeList = this.logService.getNodeList();
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
	@RequestMapping(value = "/dbgetLogListings", method = RequestMethod.GET)
	public @ResponseBody TableInfo initializePageFields(@RequestParam(value="uniqueId") int uniqueId)  {
	   		   
		   if(uniqueId==0) {
			   
			   return this.getDefaultTableInfo();
		   }   		   
		   logger.info("getLogListings id:  "+uniqueId);
		   
				return this.logService.getLogEventListings(uniqueId);


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
	   
		

		
    
}
