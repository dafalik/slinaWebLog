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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.db.repositories.SlinaDbLogFileRepository;
import slina.mb.domain.Loglevel;

/**
 * @author AnilAmarakoon
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/demo-db.xml" })
public class DbLogEventIntegration {

	 @Autowired
	 SlinaDbLogFileRepository linadbService;
	
	 @Test
	 public void testConfiguration() {
		 assertTrue(true);
	 }
	 
	 @Test
	 public void deleteFileRecord() {
		 this.linadbService.deleteAll();
	 }
	 
	 @Test
	 public void insertLogFileRecord() {
		 
		 DbLogFileImpl logFile = new DbLogFileImpl();
		 logFile.setLogFileId(100l);
		 logFile.setFileName("stdout.log");

		 DbLogEventImpl event = createLogEvent();
		 
		 logFile.addLogEvent(event);
		 
		 List<String> logdetails = new ArrayList<String>();
		 logdetails.add("This is log line value1");
		 logdetails.add("This is log line value1");
		 
		 event.createDbLogDeails(logdetails);

		 DbLogEventImpl event1 = createLogEvent();
		 logFile.addLogEvent(event1);
		 
		 logFile = this.linadbService.save(logFile);
		 assertNotNull(logFile.getLogFileId());
	 }
	 

 
	 @Test
	 public void testGetAll() {
		 List<DbLogFileImpl> ierator = this.linadbService.findAll();
		 assertNotNull(ierator);
		 assertEquals(1, ierator.size());
	 }
	 
	 @Test
	 public void getRecord() {
		 DbLogFileImpl file = this.linadbService.findOne(100l);
		 assertNotNull(file);
		 assertEquals(100, file.getLogFileId().intValue());
		 assertEquals("stdout.log", file.getFileName());
		 assertEquals(2, file.getLogEvents().size());
		 assertEquals("LogOriginated.class", file.getLogEvents().get(0).getLogClass());
		 
		assertEquals(2, file.getLogEvents().get(0).getLogDetails().size());
		 
		 DbLogEventImpl event =file.getLogEvents().get(0);
		 List<String> logDetails = event.getLogDetails();
		 String detail1 = logDetails.get(0);
		 assertEquals("This is log line value1",  detail1);
		 
		 assertEquals("This is log line value1", file.getLogEvents().get(0).getLogDetails().get(0));
	 }



	private DbLogEventImpl createLogEvent() {
		DbLogEventImpl event = new DbLogEventImpl();
		 event.setYear(2013);
		 event.setMonth(10);
		 event.setDay(01);
		 event.setHour(12);
		 event.setMinute(30);
		 event.setSeconds(20);
		 event.setMiliseconds(245);
		 event.setServer("my_server.domain.com");
		 event.setLogLevel(Loglevel.ERROR);
		 event.setLogClass("LogOriginated.class");
		 event.setLogMessage(" This log message: show some details here");
		 return event;
	}
	 
	@Test
	 public void findOneRecord() {
		 DbLogFileImpl logFile = this.linadbService.findOne(100l);
		 
		 List<DbLogEventImpl> logEvents = logFile.getLogEvents();
		 List<String> logDetails = logEvents.get(0).getLogDetails();		 
		 String details = logDetails.get(0);
		 DbLogFileImpl logFile1 = logEvents.get(0).getLogFile();
		 
		 assertEquals("stdout.log", logFile1.getFileName());
		 assertEquals("This is log line value1",  details);
	 }
	 
	 
}
