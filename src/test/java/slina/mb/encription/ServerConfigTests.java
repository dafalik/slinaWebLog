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
package slina.mb.encription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.cron.ServerInfo;

/**
 * version: D:\anil\java-apps\jasypt-1.9.0
 */



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/config/serverConfig.xml")
public class ServerConfigTests {
	
	@Autowired
	@Qualifier("encryptorConfig")
	EnvironmentStringPBEConfig encryptorConfig;
	
	
	@Autowired
	@Qualifier("encryptor")
	StandardPBEStringEncryptor encryptor;

	
	@Autowired
	@Qualifier("tomcatProdServer")
	ServerInfo tomcatProdServer;
	
	@Autowired
	@Qualifier("iwaProd1Server")
	ServerInfo iwaProd1Server;
	
	
	
	
	@Test
	public void testTomcatProdEncriptions() {	

		assertNotNull(tomcatProdServer);
		assertEquals("serverName", tomcatProdServer.getServerName());
		assertEquals("userId1", tomcatProdServer.getUserId());
		assertEquals("mydomain.com", tomcatProdServer.getDomain());	
		assertEquals("999RIverN173", tomcatProdServer.getPassword());	

	}
	
	@Test
	public void testIwaProd1Encriptions() {		
		assertNotNull(iwaProd1Server);
		assertEquals("serverName", iwaProd1Server.getServerName());
		assertEquals("userId1", iwaProd1Server.getUserId());
		assertEquals("999RIverN173", iwaProd1Server.getPassword());
		assertEquals(22, iwaProd1Server.getPortNumber());
	}
	

	
	
	
}
