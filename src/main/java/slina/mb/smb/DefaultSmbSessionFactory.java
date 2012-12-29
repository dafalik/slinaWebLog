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
package slina.mb.smb;

import jcifs.smb.NtlmPasswordAuthentication;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;



public class DefaultSmbSessionFactory implements SessionFactory  {
	
	private volatile String domain;

	private volatile String user;

	private volatile String password;


	@Override
	public void setDomain(String domain) {	
		this.domain = domain;
	}


	@Override
	public void setUser(String user) {
		this.user = user;
	}


	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Session getSession() {
		Assert.notNull(this.domain, "domain must not be null");
		Assert.hasText(this.user, "user must not be empty");
		Assert.isTrue(StringUtils.hasText(this.password), "password is required");
		try {
			NtlmPasswordAuthentication ntlmAuth = new NtlmPasswordAuthentication(domain, user, password);
			Session smbSession = new SmbSessionImpl(ntlmAuth);
			return smbSession;
		}
		catch (Exception e) {
			throw new IllegalStateException("failed to create SMB Session", e);
		}
	}

}