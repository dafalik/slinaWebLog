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

import org.springframework.core.io.Resource;

public interface LogFilePath {

	public abstract String getLogDirectory();

	public abstract void setLogDirectory(String logDirectory);

	public abstract String getFileName();

	public abstract void setFileName(String fileName);
	
	public int getParserId();
	
	public int getParserConfigId();
	
	public String getfullFilePath();
	
	public String getSourceLogDirectory();
	
	public Resource getResource();
	
	public Long getFileId();
	
}