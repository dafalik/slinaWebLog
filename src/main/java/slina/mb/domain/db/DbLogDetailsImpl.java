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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author AnilAmarakoon
 *
 */
@Entity
@Table(name = "db_log_line_details")
public class DbLogDetailsImpl implements DbLogDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="line_detail_id")
	private Long logDetailLineNumber;
	
	
	@Column(name="log_detail_value")
	private String logLineValue;
	
	@ManyToOne( cascade={CascadeType.ALL})
	@JoinColumn(name="event_to_detail_id")
	private DbLogEventImpl logEvent;
	
	public DbLogDetailsImpl() {
		super();
	}

	/**
	 * @see slina.mb.domain.db.DbLogDetails#getLogDetailLineNumber()
	 */
	@Override
	public Long getLogDetailLineNumber() {
		return logDetailLineNumber;
	}

	public void setLogDetailLineNumber(Long logDetailLineNumber) {
		this.logDetailLineNumber = logDetailLineNumber;
	}

	/**
	 * @see slina.mb.domain.db.DbLogDetails#getLogLineValue()
	 */
	@Override
	public String getLogLineValue() {
		return logLineValue;
	}

	public void setLogLineValue(String logLineValue) {
		this.logLineValue = logLineValue;
	}

	/**
	 * @see slina.mb.domain.db.DbLogDetails#getLogEvent()
	 */
	@Override
	public DbLogEventImpl getLogEvent() {
		return logEvent;
	}

	public void setLogEvent(DbLogEventImpl logEvent) {
		this.logEvent = logEvent;
	}

	
}
