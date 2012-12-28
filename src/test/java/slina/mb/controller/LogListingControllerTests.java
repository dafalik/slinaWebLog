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

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.domain.TableInfo;
import slina.mb.menu.Node;
import slina.mb.menu.NodeImpl;
import slina.mb.work.LogServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/config/main-menus.xml", "/config/web-context.xml"})
public class LogListingControllerTests {

	@Autowired
	@Qualifier("menuConverter")
	LogServiceImpl logService;
	
	@Autowired
	LogListingController controller;
	
	
	
	@Test
	public void tinitializeTest() {
	
		assertNotNull(logService);
		assertNotNull(controller);
	}
	
	@Test
	public void testWelcomePage() {
		
		String welcome = this.controller.getCommonPage();
		assertEquals("welcomepage", welcome);	
	}
	
	@Test
	public void testLogListingPage() {
		
		String menuPage = this.controller.getmenuPage();
		assertEquals("log_listing", menuPage);	
	}
	
	@Test
	public void getJsTree() {
		List<Node> menuNode = this.controller.getJstree();
		assertNotNull(menuNode);
		
		NodeImpl prodNode = (NodeImpl) menuNode.get(0);
		
		assertNotNull(prodNode);
		assertEquals("Production" , prodNode.getData().getTitle());

		List<Node> prodchildren = prodNode.getChildren();
		assertEquals(3, prodchildren.size());	
		
		NodeImpl topNode0 = (NodeImpl) prodNode.getChildren().get(0);
		NodeImpl topNode1 = (NodeImpl) prodNode.getChildren().get(1);
		NodeImpl topNode2 = (NodeImpl) prodNode.getChildren().get(2);
		
		assertEquals("WAS Applications" , topNode0.getData().getTitle());
		assertEquals("Web Services" , topNode1.getData().getTitle());
		assertEquals("Tomcat" , topNode2.getData().getTitle());
	}
	
	@Test
	public void getTableLogListingById() {
		
		TableInfo logListing = this.controller.initializePageFields(130);
		assertNotNull(logListing);
		assertEquals(7, logListing.getiTotalRecords());
		assertEquals(7, logListing.getAaData().size());	
	}
	
	@Test
	public void getTableLogListingById1() {
		
		TableInfo logListing = this.controller.initializePageFields(1130);
		assertNotNull(logListing);
		assertEquals(0, logListing.getiTotalRecords());
		assertEquals(0, logListing.getAaData().size());	
	}
		
	@Test
	public void getCombinedLoges() {
		
		int[] combinedLogEventids = { 130, 160 };
		
		TableInfo multiLogs = this.controller.getMultiLogs(combinedLogEventids);
		
		assertNotNull(multiLogs);
		assertEquals(14, multiLogs.getiTotalRecords());
		assertEquals(14, multiLogs.getAaData().size());	
		
	}
	
	@Test 
	public void getAdvanceWarnings() {
		
		TableInfo warnings = this.controller.getAdvanceWarnings("prod");
		assertNotNull(warnings);
		assertEquals(0, warnings.getiTotalRecords());
		assertEquals(0, warnings.getAaData().size());	
	}
	
}
