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

import java.io.Serializable;

/**
 * @author amaraa1
 *
 */
public class LogFilePathImpl implements Serializable, LogFilePath {

	private static final long serialVersionUID = 7494823915005620496L;
	private String logDirectory;
	private String fileName;
	private int parserId;
	private int parserConfigId;
	private String sourceLogDirectory;

	
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.io.LogFilePath#getLogDirectory()
	 */
	@Override
	public String getLogDirectory() {
		return this.logDirectory;
	}
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.io.LogFilePath#setLogDirectory(java.lang.String)
	 */
	@Override
	public void setLogDirectory(String logDirectory) {
		this.logDirectory = logDirectory;
	}
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.io.LogFilePath#getFileName()
	 */
	@Override
	public String getFileName() {
		return this.fileName;
	}
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.io.LogFilePath#setFileName(java.lang.String)
	 */
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public int getParserId() {
		return this.parserId;
	}

	public void setParserId(int parserId) {
		this.parserId = parserId;
	}

	public String getfullFilePath() {
		return this.logDirectory+"/"+this.fileName;
	}

	public String getfullFilePath(String env) {
		return this.logDirectory+"/"+env+"/"+this.fileName;
	}
	
	
	public String getSourceLogDirectory() {
		return this.sourceLogDirectory;
	}

	public void setSourceLogDirectory(String sourceLogDirectory) {
		this.sourceLogDirectory = sourceLogDirectory;
	}

	public int getParserConfigId() {
		return this.parserConfigId;
	}

	public void setParserConfigId(int parserConfigId) {
		this.parserConfigId = parserConfigId;
	}


	
	

}
