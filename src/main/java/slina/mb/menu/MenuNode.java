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

public interface MenuNode {

	/**
	 * @return the id
	 */
	public abstract int getId();

	/**
	 * @return the menuTitle
	 */
	public abstract String getMenuTitle();

	/**
	 * @return the nodeType
	 */
	public abstract NodeType getNodeType();

	/**
	 * @return the nodeState
	 */
	public String getState();

	/**
	 * @return the children
	 */
	public abstract List<MenuNode> getChildren();

	
	public List<LogFilePathImpl> getLogFilePathList();
	
	public boolean isCollectionLeaf();
	
	
	public String getIcon();

}