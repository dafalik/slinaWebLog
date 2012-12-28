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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NodeImpl implements Node,Serializable {

	private static final long serialVersionUID = 1L;
	protected List<Node> children;
	protected DataImpl data;
	protected String state = "open";
	
	public NodeImpl() {
		super();
	}

	public DataImpl getData() {
		return this.data;
	}

	public void setData(DataImpl data) {
		this.data = data;
	}

	public List<Node> getChildren() {
		return this.children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public void addChild(Node child) {
		
		if(children == null) {
			children = new ArrayList<Node>();
		}
		
		
		this.children.add(child);
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	
}
