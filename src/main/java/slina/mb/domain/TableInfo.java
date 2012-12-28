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
package slina.mb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableInfo implements Serializable {

	private static final long serialVersionUID = -8947543926318846756L;
	
	private int sEcho;
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private List<TableRows> aaData = new ArrayList<TableRows>();
	
	
	public int getsEcho() {
		return sEcho;
	}
	
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}
	
	public int getiTotalRecords() {
		return iTotalRecords;
	}
	
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	
	public List<TableRows> getAaData() {
		return aaData;
	}
	
	public void setAaData(List<TableRows> aaData) {
		this.aaData = aaData;
	}

	
	
	
}
