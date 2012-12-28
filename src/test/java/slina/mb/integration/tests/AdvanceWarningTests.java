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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slina.mb.cron.AdvanceWarning;
import slina.mb.domain.LogEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/config/logParsers.xml","/config/demo-cron-config.xml", "/config/demo-app-prod-logs.xml" })
public class AdvanceWarningTests {

	@Autowired
    @Qualifier("prodWarning")
	AdvanceWarning prodWarning;	
	
	@Test
	public void getprodWarningTest() throws InterruptedException {
		Thread.sleep(6*60*1000);
		assertNotNull(prodWarning);
		List<LogEvent> filteredServList = prodWarning.getFilteredServList();
		assertNotNull(filteredServList);
		assertTrue(filteredServList.size()>0);
	}


	
	

}
