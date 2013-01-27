/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package slina.mb.domain.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import slina.mb.domain.LogEvent;
import slina.mb.domain.Loglevel;

/**
 * @author AnilAmarakoon
 *
 */

@Entity
@Table(name = "db_log_event")
public class DbLogEventImpl implements LogEvent {


	private static final long serialVersionUID = 9213561719699138388L;
	@Column(name = "year")
	private int year;
	@Column(name = "month")
	private int month;
	@Column(name = "day")
	private int day;
	@Column(name = "hour")
	private int hour;
	@Column(name = "minute")
	private int minute ;
	@Column(name = "seconds")
	private int seconds;
	@Column(name = "mili_second")
	private int miliseconds;
	@Column(name = "log_level")
	private String logLevel;
	@Column(name = "server")
	private String server;
	@Column(name = "log_class")
	private String logClass;
	@Column(name = "log_message")
	private String logMessage;

	@Column(name = "cal")
	private Calendar cal= GregorianCalendar.getInstance();

	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "log_event_id")
	private Long id;
	
	@OneToMany(mappedBy = "logEvent", cascade={CascadeType.ALL})
	private List<DbLogDetailsImpl> logDetails = new ArrayList<DbLogDetailsImpl>();

	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name="file_to_event_id")
	private DbLogFileImpl logFile;

	
	public DbLogEventImpl() {}
	
	public DbLogEventImpl(LogEvent event, DbLogFileImpl dbFile) {

		this.cal.setTime(event.getCal());
		this.year = event.getYear();
		this.month = event.getMonth();
		this.day = event.getDay();
		this.hour = event.getHour();
		this.minute = event.getMinute();
		this.seconds = event.getSeconds();
		this.miliseconds = event.getMiliseconds();
		
		
		this.logLevel = event.getLogLevel().toString();
		this.logClass = event.getLogClass();
		this.logMessage = event.getLogMessage();
		this.logFile = dbFile;
		this.createDbLogDeails(event.getLogDetails());
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSeconds() {
		return seconds;
	}

	public int getMiliseconds() {
		return miliseconds;
	}

	public Loglevel getLogLevel() {
		
		if(logLevel == null)
			return Loglevel.UNDEFINED;
		
		
		return Loglevel.getEnum(logLevel);
	}

	public String getServer() {
		return server;
	}

	public String getLogClass() {
		return logClass;
	}

	public String getLogMessage() {
		return logMessage;
	}



	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}


	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public void setMiliseconds(int miliseconds) {
		this.miliseconds = miliseconds;
	}

	public void setLogLevel(Loglevel logLevel) {
		this.logLevel = logLevel.toString();
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setLogClass(String logClass) {
		this.logClass = logClass;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}



	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public DbLogFileImpl getLogFile() {
		return logFile;
	}


	public void setLogFile(DbLogFileImpl logFile) {
		this.logFile = logFile;
	}

	public void createDbLogDeails(List<String> logeventDetails) {
		
		int size = logeventDetails.size();
		
		for(int i=0; i<size; i++) {

			DbLogDetailsImpl logdetails = new DbLogDetailsImpl();
			logdetails.setLogLineValue(logeventDetails.get(i));
			this.logDetails.add(logdetails);
			logdetails.setLogEvent(this);
		}
		
	}


	public List<String> getLogDetails() {
		
		List<String> logLines = new ArrayList<String>();
		
		DbLogDetailsComparator comparator = new DbLogDetailsComparator();
		Collections.sort(this.logDetails, comparator);
		
		for(DbLogDetails logDetails: this.logDetails) {
			logLines.add(logDetails.getLogLineValue());
		}
	
		return logLines;
	}


	public void setLogDetails(List<DbLogDetailsImpl> logDetails) {
		this.logDetails = logDetails;
		
		for(DbLogDetailsImpl detail: this.logDetails) {
			detail.setLogEvent(this);
		}

	}

	@Override
	public void setYear(String year) {
		this.year = Integer.parseInt(year);		
	}

	@Override
	public void setMonth(String month) {
		this.month = Integer.parseInt(month)-1;
	}

	@Override
	public void setDay(String day) {
		this.day = Integer.parseInt(day);
	}

	@Override
	public void setHour(String hour) {
		this.hour = Integer.parseInt(hour);
	}

	@Override
	public void setMinute(String minute) {
		this.minute = Integer.parseInt(minute);
	}

	@Override
	public void setSeconds(String seconds) {
		this.seconds = Integer.parseInt(seconds);
	}

	@Override
	public void setMiliseconds(String miliseconds) {
		this.miliseconds = Integer.parseInt(miliseconds);
	}

	@Override
	public void addLogDetails(String logDetails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getCal() {
		return this.cal.getTime();
	}

	@Override
	public long getLongDate() {
		return this.cal.getTime().getTime();
	}
	
	
	
	
}
