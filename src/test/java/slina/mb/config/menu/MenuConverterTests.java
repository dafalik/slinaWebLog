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
package slina.mb.config.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.io.LogFilePathImpl;
import slina.mb.menu.LeafImpl;
import slina.mb.menu.Node;
import slina.mb.menu.NodeImpl;
import slina.mb.work.LogServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/config/main-menus.xml"})
public class MenuConverterTests {
	
	@Autowired
	@Qualifier("menuConverter")
	LogServiceImpl menuConverter;

	Map<Integer, Integer> idMap = new HashMap<Integer, Integer>();


	@Test
	public void testMainMenu() {
		
		assertNotNull(menuConverter);
		List<Node> menuNodeList = menuConverter.getProdNodeList();
		NodeImpl prodNode = (NodeImpl) menuNodeList.get(0);
		
		assertNotNull(prodNode);
		assertEquals("Production" , prodNode.getData().getTitle());

		List<Node> prodchildren = prodNode.getChildren();
		assertEquals(3, prodchildren.size());		
	}
	
	@Test
	public void testMainMenuNames() {
		
		assertNotNull(menuConverter);
		List<Node> menuNodeList = menuConverter.getProdNodeList();
		NodeImpl prodNode = (NodeImpl) menuNodeList.get(0);
		
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
	public void testWASApplicationMenus() {
		
		assertNotNull(menuConverter);
		List<Node> menuNodeList = menuConverter.getProdNodeList();
		NodeImpl prodNode = (NodeImpl) menuNodeList.get(0);
		
		assertNotNull(prodNode);
		assertEquals("Production" , prodNode.getData().getTitle());

		List<Node> prodchildren = prodNode.getChildren();
		assertEquals(3, prodchildren.size());		
		
		NodeImpl topNode0 = (NodeImpl) prodNode.getChildren().get(0);
	
		assertEquals("WAS Applications" , topNode0.getData().getTitle());

		NodeImpl topNode0Child0 = (NodeImpl) topNode0.getChildren().get(0);
		NodeImpl topNode0Child1 = (NodeImpl) topNode0.getChildren().get(1);

		assertEquals("Prod 1" , topNode0Child0.getData().getTitle());
		assertEquals("Prod 2" , topNode0Child1.getData().getTitle());

		assertEquals(6 , topNode0Child0.getChildren().size());
		assertEquals(6 , topNode0Child1.getChildren().size());
		
		assertEquals("App 1" , topNode0Child0.getChildren().get(0).getData().getTitle());
		assertEquals("App 8" , topNode0Child0.getChildren().get(1).getData().getTitle());		
		assertEquals("App 9" , topNode0Child0.getChildren().get(2).getData().getTitle());
		assertEquals("Application 1" , topNode0Child0.getChildren().get(3).getData().getTitle());		
		assertEquals("Application 2" , topNode0Child0.getChildren().get(4).getData().getTitle());
		assertEquals("System Out" , topNode0Child0.getChildren().get(5).getData().getTitle());

		assertEquals("App 1" , topNode0Child1.getChildren().get(0).getData().getTitle());
		assertEquals("App 8" , topNode0Child1.getChildren().get(1).getData().getTitle());		
		assertEquals("App 9" , topNode0Child1.getChildren().get(2).getData().getTitle());
		assertEquals("Application 1" , topNode0Child1.getChildren().get(3).getData().getTitle());		
		assertEquals("Application 2" , topNode0Child1.getChildren().get(4).getData().getTitle());
		assertEquals("System Out" , topNode0Child1.getChildren().get(5).getData().getTitle());	
	}

	@Test
	public void testWSApplicationMenus() {
		
		assertNotNull(menuConverter);
		List<Node> menuNodeList = menuConverter.getProdNodeList();
		NodeImpl prodNode = (NodeImpl) menuNodeList.get(0);
		
		assertNotNull(prodNode);
		assertEquals("Production" , prodNode.getData().getTitle());

		List<Node> prodchildren = prodNode.getChildren();
		assertEquals(3, prodchildren.size());		
		
		NodeImpl topNode1 = (NodeImpl) prodNode.getChildren().get(1);
		assertEquals(2 , topNode1.getChildren().size());
		
		NodeImpl topNode1Child0 = (NodeImpl) topNode1.getChildren().get(0);
		NodeImpl topNode1Child1 = (NodeImpl) topNode1.getChildren().get(1);		
		
		assertEquals("WS Prod 1" , topNode1Child0.getData().getTitle());
		assertEquals("WS Prod 2" , topNode1Child1.getData().getTitle());

		assertEquals(4 , topNode1Child0.getChildren().size());
		assertEquals(4 , topNode1Child1.getChildren().size());
		
		assertEquals("App 2" , topNode1Child0.getChildren().get(0).getData().getTitle());
		assertEquals("App 4" , topNode1Child0.getChildren().get(1).getData().getTitle());		
		assertEquals("App 7" , topNode1Child0.getChildren().get(2).getData().getTitle());
		assertEquals("System Out" , topNode1Child0.getChildren().get(3).getData().getTitle());			
		
		assertEquals("App 2" , topNode1Child1.getChildren().get(0).getData().getTitle());
		assertEquals("App 4" , topNode1Child1.getChildren().get(1).getData().getTitle());		
		assertEquals("App 7" , topNode1Child1.getChildren().get(2).getData().getTitle());
		assertEquals("System Out" , topNode1Child1.getChildren().get(3).getData().getTitle());	
	}
	
	@Test
	public void testTomcatmenus() {
		
		assertNotNull(menuConverter);
		List<Node> menuNodeList = menuConverter.getProdNodeList();
		NodeImpl prodNode = (NodeImpl) menuNodeList.get(0);
		
		assertNotNull(prodNode);
		assertEquals("Production" , prodNode.getData().getTitle());

		List<Node> prodchildren = prodNode.getChildren();
		assertEquals(3, prodchildren.size());	
		
		NodeImpl topNode2 = (NodeImpl) prodNode.getChildren().get(2);
		assertEquals("Tomcat" , topNode2.getData().getTitle());
		
		assertEquals("App 10" , topNode2.getChildren().get(0).getData().getTitle());
		assertEquals("App 11" , topNode2.getChildren().get(1).getData().getTitle());		
		assertEquals("App 3" , topNode2.getChildren().get(2).getData().getTitle());
		assertEquals("App 5" , topNode2.getChildren().get(3).getData().getTitle());		
		assertEquals("App 6" , topNode2.getChildren().get(4).getData().getTitle());
		assertEquals("Default Log" , topNode2.getChildren().get(5).getData().getTitle());				
		assertEquals("StdOut Log" , topNode2.getChildren().get(6).getData().getTitle());
	}
	
	
	@Test
	public void testFileMap() {
		
		Map<Integer, List<LogFilePathImpl>> logFilesMap = menuConverter.getLogFilesMap();
		assertNotNull(logFilesMap);
		assertEquals(27, logFilesMap.size());
	}
	

	
	@Ignore
	public void prodAllMenusTest() {
		
		 
		assertNotNull(menuConverter);

		List<Node> menuNodeList = menuConverter.getProdNodeList();
		NodeImpl prodNode = (NodeImpl) menuNodeList.get(0);
		
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

		assertEquals(2 , topNode0.getChildren().size());
		assertEquals(2 , topNode1.getChildren().size());
		assertEquals(7 , topNode2.getChildren().size());
	
		NodeImpl topNode0Child0 = (NodeImpl) topNode0.getChildren().get(0);
		NodeImpl topNode0Child1 = (NodeImpl) topNode0.getChildren().get(1);

		assertEquals("Prod 1" , topNode0Child0.getData().getTitle());
		assertEquals("Prod 2" , topNode0Child1.getData().getTitle());

		assertEquals(6 , topNode0Child0.getChildren().size());
		assertEquals(6 , topNode0Child1.getChildren().size());
		
		assertEquals("App 1" , topNode0Child0.getChildren().get(0).getData().getTitle());
		assertEquals("App 8" , topNode0Child0.getChildren().get(1).getData().getTitle());		
		assertEquals("App 9" , topNode0Child0.getChildren().get(2).getData().getTitle());
		assertEquals("Application 1" , topNode0Child0.getChildren().get(3).getData().getTitle());		
		assertEquals("Application 2" , topNode0Child0.getChildren().get(4).getData().getTitle());
		assertEquals("System Out" , topNode0Child0.getChildren().get(5).getData().getTitle());

		assertEquals("App 1" , topNode0Child1.getChildren().get(0).getData().getTitle());
		assertEquals("App 8" , topNode0Child1.getChildren().get(1).getData().getTitle());		
		assertEquals("App 9" , topNode0Child1.getChildren().get(2).getData().getTitle());
		assertEquals("Application 1" , topNode0Child1.getChildren().get(3).getData().getTitle());		
		assertEquals("Application 2" , topNode0Child1.getChildren().get(4).getData().getTitle());
		assertEquals("System Out" , topNode0Child1.getChildren().get(5).getData().getTitle());		
		
		
		NodeImpl topNode1Child0 = (NodeImpl) topNode1.getChildren().get(0);
		NodeImpl topNode1Child1 = (NodeImpl) topNode1.getChildren().get(1);		
		
		assertEquals("WS Prod 1" , topNode1Child0.getData().getTitle());
		assertEquals("WS Prod 2" , topNode1Child1.getData().getTitle());

		assertEquals(4 , topNode1Child0.getChildren().size());
		assertEquals(4 , topNode1Child1.getChildren().size());
		
		assertEquals("App 2" , topNode1Child0.getChildren().get(0).getData().getTitle());
		assertEquals("App 4" , topNode1Child0.getChildren().get(1).getData().getTitle());		
		assertEquals("App 7" , topNode1Child0.getChildren().get(2).getData().getTitle());
		assertEquals("System Out" , topNode1Child0.getChildren().get(3).getData().getTitle());			
		
		assertEquals("App 2" , topNode1Child1.getChildren().get(0).getData().getTitle());
		assertEquals("App 4" , topNode1Child1.getChildren().get(1).getData().getTitle());		
		assertEquals("App 7" , topNode1Child1.getChildren().get(2).getData().getTitle());
		assertEquals("System Out" , topNode1Child1.getChildren().get(3).getData().getTitle());				
		
		
		assertEquals("App 10" , topNode2.getChildren().get(0).getData().getTitle());
		assertEquals("App 11" , topNode2.getChildren().get(1).getData().getTitle());		
		assertEquals("App 3" , topNode2.getChildren().get(2).getData().getTitle());
		assertEquals("App 5" , topNode2.getChildren().get(3).getData().getTitle());		
		assertEquals("App 6" , topNode2.getChildren().get(4).getData().getTitle());
		assertEquals("Default Log" , topNode2.getChildren().get(5).getData().getTitle());				
		assertEquals("StdOut Log" , topNode2.getChildren().get(6).getData().getTitle());
		
		
		
		System.out.println("********** Prod Node **********\n");
		for(Node node:prodchildren) {
			NodeImpl childx = (NodeImpl) node;
			
			
			
			System.out.println(childx.getData().getTitle());
			this.printChildren(childx.getChildren(),1);
		}
		
		
	}	
	
	
	
	
	
	
	
	private void printChildren(List<Node> prodchildren, int tabs ) {
		
		String myTabs = "";
		for(int i=0; i<tabs; i++) {
			myTabs = myTabs + "\t";
		}
		
		for(Node node:prodchildren) {

			
			if(node.getClass().isAssignableFrom(NodeImpl.class)) {
				
				System.out.println(myTabs+node.getData().getTitle());
				
				NodeImpl childx = (NodeImpl) node;
//				System.out.println("********** Starting child Node **********");
				this.printChildren(childx.getChildren(),2);
//				System.out.println("********** end child Node **********");
			}else if(node.getClass().isAssignableFrom(LeafImpl.class)) {
				
				LeafImpl childx = (LeafImpl) node;
				Integer id = childx.getMetadata().getId();
				
				Integer lookup = idMap.get(id);
				
				if(lookup==null) {
					idMap.put(id, id);
				}else{
					throw new RuntimeException(id+"  exists");
				}
								
				System.out.println(myTabs+node.getData().getTitle()+"  -->  "+id);
				
				
			}
		}
		
	}	
	
	

}
