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

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Ignore;
import org.junit.Test;


/**
 * @author anil
 *
 */
public class SftpIntegration {
	//http://krams915.blogspot.ca/2011/01/spring-3-task-scheduling-via-scheduled.html
	@Ignore
	public void getRemoteFileWithSftp(){
		
		SFTPDownload connector = new SFTPDownload();
		connector.setHostName("someServer.domain.com");
		connector.setLocalFilePathAndName("C:/temp/WDC/test.log");
		connector.setPortNumber(22);
		connector.setRemoreFileName("app1.log");
		connector.setRemoreFilePath("/apps/logs/apps/");
		connector.setSftpUser("testUser");
		connector.setSftpPassword("xcxcxxcxcxc");		
		connector.downloadFile();

	}
	
	@Test
	public void selftest() {
		assertTrue(true);
	}
	
	
	public void getRemoteFileWithApacheIOUtils() throws MalformedURLException, IOException {
		
		

		
		
	}
	
	
	

}
