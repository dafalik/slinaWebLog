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
package slina.mb.cron;

/**
 * @author amaraa1
 *
 */
public class ServerInfoImpl implements ServerInfo {
	
	private String serverName;
	private String userId;
	private String password;
	private int portNumber = 22;
	private String domain;

	
	
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.cron.ServerInfo#getServerName()
	 */
	@Override
	public String getServerName() {
		return this.serverName;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.cron.ServerInfo#getUserId()
	 */
	@Override
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.cron.ServerInfo#getPassword()
	 */
	@Override
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.cron.ServerInfo#getPortNumber()
	 */
	@Override
	public int getPortNumber() {
		return this.portNumber;
	}
	
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.cron.ServerInfo#getDomain()
	 */
	@Override
	public String getDomain() {
		return this.domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setProp1(String prop1) {
		this.serverName = prop1;
	}

	public void setProp2(String prop2) {
		this.userId = prop2;
	}

	public void setProp3(String prop3) {
		this.password = prop3;
	}

	public void setProp4(String prop4) {
		this.domain = prop4;
	}
	

	
	
	
	
	
	
	
	
}
