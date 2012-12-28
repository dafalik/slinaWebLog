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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.domain.LogEvent;
import slina.mb.menu.Node;
import slina.mb.menu.NodeImpl;
import slina.mb.work.LogServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/config/main-menus.xml"})
public class LogserviceTests {

	@Autowired
	@Qualifier("menuConverter")
	LogServiceImpl logService;
	
	@Test
	public void initTest() {
		assertTrue(true);
	}
	
	@Test
	public void testprocessLog1() throws IOException {
		
		 List<LogEvent> logEventsList = this.logService.processLog(130);
			assertNotNull(logEventsList);
			assertEquals(7, logEventsList.size());
	}
	
	@Test
	public void testprocessLog2() throws IOException {
		
		 List<LogEvent> logEventsList = this.logService.processLog(1130);
			assertNotNull(logEventsList);
			assertEquals(0, logEventsList.size());
	}	
	
	@Test
	public void testprocessMultiLog() throws IOException {
		
		int[] combinedLogEventids = { 130, 160 };
		
		 List<LogEvent> logEventsList = this.logService.processLog(combinedLogEventids);
			assertNotNull(logEventsList);
			assertEquals(14, logEventsList.size());
	}

	@Test 
	public void getAdvanceWarnings() {
		
		List<LogEvent> logEventsList = this.logService.getProductionWarnings();
		assertNotNull(logEventsList);
		assertEquals(0, logEventsList.size());	
	}
	
	
	@Test
	public void getMenus() {
		
		List<Node> prodMenuList = this.logService.getProdNodeList();
		assertNotNull(prodMenuList);
		assertEquals(1, prodMenuList.size());	

		NodeImpl node = (NodeImpl) prodMenuList.get(0);
		assertEquals(3, node.getChildren().size());	
		assertEquals("Production", node.getData().getTitle());	
		
		assertEquals("WAS Applications" , node.getChildren().get(0).getData().getTitle());
		assertEquals("Web Services" , node.getChildren().get(1).getData().getTitle());
		assertEquals("Tomcat" , node.getChildren().get(2).getData().getTitle());
	}
	
}
