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

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.io.FileCopyWorker;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/config/demo-cron-config.xml", "/config/demo-app-prod-logs.xml", "/config/serverInformation.xml", 
		"/config/demo-ws-prod-logs.xml", "/config/demo-tomcat-prod.xml", "/config/logParsers.xml" })
public class ProdCopyFiles {
	
	
	@Autowired
    @Qualifier("tomcatProdWorker")
	FileCopyWorker tomcatProdWorker;
	
	@Autowired
    @Qualifier("prod1AppWSWorker")
	FileCopyWorker prod1AppWSWorker;

	@Autowired
    @Qualifier("prod2AppWSWorker")
	FileCopyWorker prod2AppWSWorker;
	

	
	
	@Ignore
	public void testTomcatFileCopy() {
		this.tomcatProdWorker.work();
		assertTrue(true);
	}
	
	@Ignore
	public void testProd1FileCopy() {	
		this.prod1AppWSWorker.work();
		assertTrue(true);
	}

	@Ignore
	public void testProd2FileCopy() {		
		this.prod2AppWSWorker.work();
		assertTrue(true);
	}

	
}
