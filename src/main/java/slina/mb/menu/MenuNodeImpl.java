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
package slina.mb.menu;


import java.util.List;

import slina.mb.io.LogFilePathImpl;

/**
 * @author anil
 * configuration class
 */
public class MenuNodeImpl implements MenuNode {

	 private int id;
	 
	 /* name of the menu */
	 private String menuTitle;
	 
	 /** see comments in NodeType */
	 private NodeType nodeType; 
	 
	 /** if Node, is open or close */
	 private String  state;
	 
	 private boolean collectionLeaf;

	 private List<MenuNode> children;
	 
	 private List<LogFilePathImpl> logFilePathList;
	 
	 private String icon;

	/**
	 * @see slina.mb.menu.MenuNode#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @see slina.mb.menu.MenuNode#getMenuTitle()
	 */
	@Override
	public String getMenuTitle() {
		return menuTitle;
	}

	/**
	 * @param menuTitle the menuTitle to set
	 */
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	/**
	 * @see slina.mb.menu.MenuNode#getNodeType()
	 */
	@Override
	public NodeType getNodeType() {
		return nodeType;
	}

	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}


	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @see slina.mb.menu.MenuNode#getChildren()
	 */
	@Override
	public List<MenuNode> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}



	/**
	 * @return the logFilePathList
	 */
	public List<LogFilePathImpl> getLogFilePathList() {
		return logFilePathList;
	}

	/**
	 * @param logFilePathList the logFilePathList to set
	 */
	public void setLogFilePathList(List<LogFilePathImpl> logFilePathList) {
		this.logFilePathList = logFilePathList;
	}

	/**
	 * @return the collectionLeaf
	 */
	public boolean isCollectionLeaf() {
		return collectionLeaf;
	}

	/**
	 * @param collectionLeaf the collectionLeaf to set
	 */
	public void setCollectionLeaf(boolean collectionLeaf) {
		this.collectionLeaf = collectionLeaf;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	 

	 
}
