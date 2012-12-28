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
package slina.mb.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anil
 *
 */
public class TableRows implements Serializable {

	private static final long serialVersionUID = 5004757042651668915L;
	
	private String detailsLink ="<img src='images/details_open.png'>";
	private long timestamp;
	private String dateTime;
	private String server;
	private String logLevel;
	private String logClass;
	private String logSummary;
	private List<String> logDetails = new ArrayList<String>();
	
	
	
	
	
	public TableRows(String dateTime, String logLevel, String logClass,
			String logSummary) {
		super();
		this.dateTime = dateTime;
		this.logLevel = logLevel;
		this.logClass = logClass;
		this.logSummary = logSummary;
	}
	
	
	
	
	public String getDetailsLink() {
		return detailsLink;
	}


	public void setDetailsLink(String detailsLink) {
		this.detailsLink = detailsLink;
	}

	public String getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getLogLevel() {
		return logLevel;
	}
	
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
	public String getLogClass() {
		return logClass;
	}
	
	public void setLogClass(String logClass) {
		this.logClass = logClass;
	}
	
	public String getLogSummary() {
		return logSummary;
	}
	
	public void setLogSummary(String logSummary) {
		this.logSummary = logSummary;
	}


	public List<String> getLogDetails() {
		return this.logDetails;
	}


	public void setLogDetails(List<String> logDetails) {
		this.logDetails = logDetails;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}


	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	

}
