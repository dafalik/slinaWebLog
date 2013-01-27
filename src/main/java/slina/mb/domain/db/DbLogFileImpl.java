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
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import slina.mb.io.LogFilePathImpl;

/**
 * @author AnilAmarakoon
 *
 */
@Entity
@Table(name = "db_log_file")
public class DbLogFileImpl {

	@Id
	@Column(name = "log_file_id")
	private Long logFileId;
	
	@Column(name = "log_local_directory")
	private String localLogDirectory;
	@Column(name = "log_file_name")
	private String fileName;
	@Column(name = "log_file_parser_id")
	private int    parserId;
	@Column(name = "log_file_parser_config_id")
	private int    parserConfigId;
	@Column(name = "log_file_source_directory")
	private String sourceLogDirectory;


	@OneToMany(  cascade={CascadeType.ALL},  mappedBy = "logFile", fetch = FetchType.EAGER)
	private List<DbLogEventImpl> logEvents = new ArrayList<DbLogEventImpl>();
	
	public DbLogFileImpl() {
		super();
	}
	
	public DbLogFileImpl(LogFilePathImpl logFilePath) {
		super();
		this.localLogDirectory = logFilePath.getLogDirectory();
		this.fileName = logFilePath.getFileName();
		this.sourceLogDirectory = logFilePath.getSourceLogDirectory();
		this.parserId = logFilePath.getParserId();
		this.parserConfigId = logFilePath.getParserConfigId();
		this.logFileId = logFilePath.getFileId();
	}
	

	
	public Long getLogFileId() {
		return logFileId;
	}
	
	public void setLogFileId(Long logFileId) {
		this.logFileId = logFileId;
	}

	public List<DbLogEventImpl> getLogEvents() {
		
		DbLogEventComparator comparator = new DbLogEventComparator();
		Collections.sort(this.logEvents, comparator);
		
		return logEvents;
	}

	
	public void setLogEvents(List<DbLogEventImpl> logEvents) {
		this.logEvents = logEvents;
		
		for(DbLogEventImpl event: this.logEvents) {
			event.setLogFile(this);
		}
	}
	
	public void addLogEvent(DbLogEventImpl event) {
		this.logEvents.add(event);
		event.setLogFile(this);
	}


	public String getLocalLogDirectory() {
		return localLogDirectory;
	}

	public void setLocalLogDirectory(String localLogDirectory) {
		this.localLogDirectory = localLogDirectory;
	}

	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getParserId() {
		return parserId;
	}

	public void setParserId(int parserId) {
		this.parserId = parserId;
	}

	public int getParserConfigId() {
		return parserConfigId;
	}

	public void setParserConfigId(int parserConfigId) {
		this.parserConfigId = parserConfigId;
	}

	public String getSourceLogDirectory() {
		return sourceLogDirectory;
	}

	public void setSourceLogDirectory(String sourceLogDirectory) {
		this.sourceLogDirectory = sourceLogDirectory;
	}
		

	
}
