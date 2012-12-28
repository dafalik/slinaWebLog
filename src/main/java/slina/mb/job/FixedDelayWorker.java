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
package slina.mb.job;

import org.apache.log4j.Logger;

public class FixedDelayWorker implements Worker {
	 
	 protected static Logger logger = Logger.getLogger("worker");
	 
	 public void work() {
	  String threadName = Thread.currentThread().getName();
	  logger.debug("   " + threadName + " FixedDelayWorker has began working.");
	        try {
	         logger.debug("FixedDelayWorker working...");
	            Thread.sleep(10000); // simulates work
	        }
	        catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	        logger.debug("   " + threadName + " FixedDelayWorker has completed work.");
	 }
	  
	}
