package slina.mb.domain.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/demo-db.xml" })
public class DBLogRepositoryServiceIntegration {

	@Autowired
	private SlinaRepositoryService slinaRepositoryService;
	
	@Ignore
	 public void testGetAll() {
		 List<DbLogFileImpl> ierator = this.slinaRepositoryService.findAll();
		 assertNotNull(ierator);
		 assertEquals(27, ierator.size());
	 }

	 @Test
	 public void getRecord() {
		 DbLogFileImpl file = this.slinaRepositoryService.findOne(1004l);
		 assertNotNull(file);
		 assertEquals(1004, file.getLogFileId().intValue());
		 assertEquals("application1.log", file.getFileName());
		 assertEquals(31, file.getLogEvents().size());
		 assertEquals("The", file.getLogEvents().get(0).getLogClass());
		 
		assertEquals(12, file.getLogEvents().get(0).getLogDetails().size());
		 
		 DbLogEventImpl event =file.getLogEvents().get(0);
		 List<String> logDetails = event.getLogDetails();
		 String detail1 = logDetails.get(0);
		 assertEquals("java.io.EOFException",  detail1);
		 
		 assertEquals("java.io.EOFException", file.getLogEvents().get(0).getLogDetails().get(0));
	 }
	 
	@Ignore
	public void getAllEventsTest() {
		List<DbLogEventImpl> eventList = this.slinaRepositoryService.getLogEvents();
		 assertEquals(12, eventList.size());
		
	}
	 
	 
}
