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
package slina.mb.io;


import java.util.List;

import slina.mb.menu.Metadata;

/**
 * @author amaraa1
 *
 */
public class FileConfigureImp implements FileConfigure {
	
	private List<String> filePath;
	private int id;
	private List<LogFilePath> logFilePathList;
	private String name;
	


	@Override
	public List<String> getFilePath() {
		return this.filePath;
	}

	public void setFilePath(List<String> filePath) {
		this.filePath = filePath;
	}


	@Override
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Metadata getMetadata() {		
		return new Metadata(this.id);
	}

	@Override
	public String getData() {
		
		return this.name;
	}

	public List<LogFilePath> getLogFilePathList() {
		return this.logFilePathList;
	}

	public void setLogFilePathList(List<LogFilePath> logFilePathList) {
		this.logFilePathList = logFilePathList;
	}


}
