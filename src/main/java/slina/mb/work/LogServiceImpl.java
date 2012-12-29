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
package slina.mb.work;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import slina.mb.cron.AdvanceWarning;
import slina.mb.domain.LogEvent;
import slina.mb.io.LogFilePath;
import slina.mb.io.LogFilePathImpl;
import slina.mb.menu.DataImpl;
import slina.mb.menu.LeafImpl;
import slina.mb.menu.MenuNode;
import slina.mb.menu.MenuNodeImpl;
import slina.mb.menu.Metadata;
import slina.mb.menu.Node;
import slina.mb.menu.NodeImpl;
import slina.mb.menu.NodeType;
import slina.mb.parsing.Log4jParser;
import slina.mb.utils.LogEventComparator;
import slina.mb.utils.LogFileReader;

public class LogServiceImpl {
	
	private static final Logger LOGGER = Logger.getLogger(LogServiceImpl.class);

	private List<MenuNodeImpl> prodMenuList;
	private List<Node> prodNodeList = new ArrayList<Node>();

	private Map<Integer, List<LogFilePathImpl>> logFilesMap = new HashMap<Integer, List<LogFilePathImpl>>();
	private Map<Integer, Log4jParser> logParserMap = new HashMap<Integer, Log4jParser>();	
	private LogFileReader logFileReader;

	private AdvanceWarning prodAdvanceWarning;
	
	
	public List<LogEvent> processLog(int id) throws IOException {
		
		List<LogFilePathImpl> logFilePathList = this.logFilesMap.get(id);
		
		
		List<LogEvent> eventlogList = new ArrayList<LogEvent>();
		
		if(logFilePathList == null) {
			return eventlogList;
		}
		
		
		
		for(LogFilePath logPath: logFilePathList) {
			
			//String filepath = logPath.getfullFilePath();
			Resource resource = logPath.getResource();
			int paserId = logPath.getParserId();
			int paserConfigId = logPath.getParserConfigId();
			LOGGER.info("Process file for display : "+resource.getFilename());
			
			Log4jParser logParser = logParserMap.get(paserId);
			
			List<String> filelist = this.logFileReader.readFile(resource);					
			List<LogEvent> logList = logParser.createLogEvents(paserConfigId,filelist);
			
			eventlogList.addAll(logList);
		}
		
		if(eventlogList.size() > 2 ) {
			Collections.sort(eventlogList, new LogEventComparator());		
		}
		
		return eventlogList;
		
	}
	
	
	public List<LogEvent> processLog(int[] intArray) throws IOException {
		
		HashSet<Integer> uniqueIntSet = new HashSet<Integer>();
		for(int fileId:intArray) {
			uniqueIntSet.add(fileId);
		}
		
		
		
		List<LogEvent> eventlogList = new ArrayList<LogEvent>();
		
		for(int fileId:uniqueIntSet) {
			
			List<LogFilePathImpl> logFilePathList = this.logFilesMap.get(fileId);
						
			for(LogFilePath logPath: logFilePathList) {
				
				String filepath = logPath.getfullFilePath();
				int paserId = logPath.getParserId();
				int paserConfigId = logPath.getParserConfigId();
				LOGGER.info("Process file for display : "+filepath);
				
				Log4jParser logParser = logParserMap.get(paserId);
				
				List<String> filelist = this.logFileReader.readFile(filepath);					
				List<LogEvent> logList = logParser.createLogEvents(paserConfigId,filelist);
				
				eventlogList.addAll(logList);
			}

		}

		if(eventlogList.size() > 2 ) {
			Collections.sort(eventlogList, new LogEventComparator());		
		}
		
		
		
		return eventlogList;
	}
		
	
	
	public List<LogEvent> getProductionWarnings() {
		
		if(this.prodAdvanceWarning == null) {
			return new ArrayList<LogEvent>();
		}
		
		return this.prodAdvanceWarning.getFilteredServList();
	}
	
	
	
	public void init( ) {
		
		for(MenuNodeImpl menu: prodMenuList) {
			prodNodeList.add(this.createMenuItems(menu));
		}		
	}
	
	
	
	
	/**
	 * Top node has to be parent node
	 * @param menuItem
	 */
	private Node createMenuItems(MenuNode menuItem ) {
		
		String title = menuItem.getMenuTitle();
		String state = menuItem.getState();
		
		NodeImpl menuFolder  = new NodeImpl();
		List<Node> subNodes = new ArrayList<Node>();
		
		menuFolder.setState(state);
		menuFolder.setChildren(subNodes);
		
		DataImpl data = new DataImpl();
		data.setTitle(title);
		menuFolder.setData(data);
		
		NodeType nodeType = menuItem.getNodeType();
		
		if(nodeType != NodeType.ParentNode) {
			throw new RuntimeException("You need to provide Parent Folder");
		}
		
			
		List<MenuNode> children = menuItem.getChildren();
			
		for(MenuNode node:children) {

			NodeType childNodeType = node.getNodeType();
			
			if(childNodeType == NodeType.ParentNode) {
				
				this.createParentFolderNode(menuFolder, node);
				
			} else if(childNodeType == NodeType.Branch) {
				this.createBranchFolderNode(menuFolder, node);
			} 		
		}

		return menuFolder;
		
	}
	
	

	
	


	private NodeImpl createParentFolderNode(NodeImpl parentFolder, MenuNode menuItem) {
		
		String title = menuItem.getMenuTitle();
		String state = menuItem.getState();


		
		NodeImpl submenuFolder  = new NodeImpl();
		List<Node> subNodes = new ArrayList<Node>();
		
		submenuFolder.setState(state);
		submenuFolder.setChildren(subNodes);

		DataImpl data = new DataImpl();
		data.setTitle(title);		

		String icon = menuItem.getIcon();
		
		if(icon != null) {
			data.setIcon(icon);	
		}
		
		
		List<MenuNode> children = menuItem.getChildren();
		
		for(MenuNode node:children) {

			NodeType childNodeType = node.getNodeType();
			
			if(childNodeType == NodeType.ParentNode) {
				
				this.createParentFolderNode(submenuFolder, node);
				
			} else if(childNodeType == NodeType.Branch) {
				this.createBranchFolderNode(submenuFolder, node);
			} 
					
		}
		

		submenuFolder.setData(data);
		parentFolder.addChild(submenuFolder);

		return submenuFolder;

	}	
	




	private void createBranchFolderNode(NodeImpl parentFolder, MenuNode menuItem) {
		
		String title = menuItem.getMenuTitle();
		String state = menuItem.getState();


		
		NodeImpl menuFolder  = new NodeImpl();
		List<Node> subNodes = new ArrayList<Node>();
		
		menuFolder.setState(state);
		menuFolder.setChildren(subNodes);

		DataImpl data = new DataImpl();
		data.setTitle(title);		

		String icon = menuItem.getIcon();
		data.setIcon(icon);	
		this.convertBranchChildren(menuFolder, menuItem);		
		
		menuFolder.setData(data);
		
		parentFolder.addChild(menuFolder);

	}
	
	
	
	
	
	
	
	

	private void convertBranchChildren(NodeImpl menuFolder, MenuNode menuItem) {
		
		List<MenuNode> children = menuItem.getChildren();
		
		List<Node> childNodes = menuFolder.getChildren();
		
		for(MenuNode node:children) {
			
			LeafImpl leaf = this.createFileNode(node);
			childNodes.add(leaf);			
		}
	}
	
	
	
	private LeafImpl createFileNode(MenuNode menuItem) {
		
		NodeType nodeType = menuItem.getNodeType();
		
		if( !(nodeType == NodeType.Leaf || nodeType == NodeType.CollectionLeaf)) {
			throw new RuntimeException("You need to provide Leaf Folder");
		}
				
		String title = menuItem.getMenuTitle();

		String icon = menuItem.getIcon();
		int id = menuItem.getId();
		
		LeafImpl leaf = new LeafImpl();
		
		DataImpl data = new DataImpl();
		data.setTitle(title);
		data.setIcon(icon);
		data.getAttr().put("id", Integer.toString(id));
	
		leaf.setData(data);
		
		Metadata metadata = new Metadata();
		metadata.setId(id);
		
		leaf.setMetadata(metadata);
		
		
		this.logFilesMap.put(id,  menuItem.getLogFilePathList());
	
		return leaf;
		
		
	}

	public void setProdMenuList(List<MenuNodeImpl> prodMenuList) {
		this.prodMenuList = prodMenuList;
	}

	public List<Node> getProdNodeList() {
		return this.prodNodeList;
	}

	public Map<Integer, List<LogFilePathImpl>> getLogFilesMap() {
		return this.logFilesMap;
	}



	public void setLogParserMap(Map<Integer, Log4jParser> logParserMap) {
		this.logParserMap = logParserMap;
	}


	public void setLogFileReader(LogFileReader logFileReader) {
		this.logFileReader = logFileReader;
	}


	public void setProdAdvanceWarning(AdvanceWarning prodAdvanceWarning) {
		this.prodAdvanceWarning = prodAdvanceWarning;
	}

	
	

}
