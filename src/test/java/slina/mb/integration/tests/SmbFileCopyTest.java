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
package slina.mb.integration.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Ignore;
import org.junit.Test;

import slina.mb.smb.DefaultSmbSessionFactory;
import slina.mb.smb.Session;
import slina.mb.smb.SessionFactory;

/**
 * @author amaraa1
 *
 */
public class SmbFileCopyTest {
	
	@Ignore
	public void copyFileTest() throws IOException {
		
		jcifs.Config.setProperty( "jcifs.netbios.wins", "192.168.1.1" );
		SessionFactory factory = new DefaultSmbSessionFactory();
		factory.setDomain("IGNATIONAL");
		factory.setUser("userId");
		factory.setPassword("dmdjkfdjkfj");
		
		Session session = factory.getSession();
		
		OutputStream os = new FileOutputStream ("C:/temp/WDC/xxxxxxxx_log.2012-09-19.txt");
		

		session.read("smb://windowsServer.domain.bz/TomcatLogs/localhost_log.2012-10-02.txt", os);
		session.close();
		os.flush();
		os.close();
		
		File copiedFile = new File("C:/temp/WDC/xxxxxxxx_log.2012-09-19.txt");
		
		assertTrue(copiedFile.canRead());
		assertTrue(copiedFile.exists());
		assertTrue(copiedFile.length() > 0);
	}

	
	@Test
	public void selfTest() {		
		assertTrue(true);
	}
}
