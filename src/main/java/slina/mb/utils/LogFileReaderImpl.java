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
package slina.mb.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author anil
 * 	    File file = new File("/home/anil/Development2/workspace-logReader/logparser/src/main/resources/ig/mips/mailBox.log");

 *
 */
public class LogFileReaderImpl implements LogFileReader {
	
	private static final Logger LOGGER = Logger.getLogger(LogFileReaderImpl.class);
	private boolean checkClassPath = false;
	
	/* (non-Javadoc)
	 * @see ig.mips.utils.LogFileReader#readFile(java.lang.String)
	 */
	@Override
	public List<String> readFile(String fileName)  {

		List<String> lines = new ArrayList<String>();
		
   	 try {
		
			    long before = System.currentTimeMillis();
		
			    File file = new File(fileName);	
			    
			    if(!file.isFile()) {
			    	
			    	if(this.checkClassPath) {
			    		
			    		Resource classPathResource = new ClassPathResource(fileName);  
			    		file = classPathResource.getFile();				    		
			    	}
			    	
			    	
			    }
			    	    	
			    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));			        			        
			    String line = reader.readLine(); 
			        
			    while(line != null) {
			        lines.add(line);
			        line = reader.readLine(); 
			    }
		
			    long after = System.currentTimeMillis();
			    reader.close();
			    LOGGER.info("conv. run took " + (after - before) + " ms, size = "+lines.size());
			    return lines;
	    
		} catch (IOException e) {
			LOGGER.error(e);
			return lines;
		}
	    
	}	
	
	
	public List<String> readFile(File file) {
		
		String var = file.getAbsolutePath();
		return this.readFile(var);
	}

	
	
	
	
	/**
	 * readFileTest is faster
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public List<String> readnio(String fileName) throws IOException {
		
		List<String> lines = new ArrayList<String>();
		long before = System.currentTimeMillis();
		
		Scanner scanner = new Scanner(new FileReader(fileName));

		while(scanner.hasNext()) {
			lines.add(scanner.next());
		}


		 long after = System.currentTimeMillis();
		 System.out.println("nio run took " + (after - before) + " ms");
		return lines;

	}


	public boolean isCheckClassPath() {
		return checkClassPath;
	}


	public void setCheckClassPath(boolean checkClassPath) {
		this.checkClassPath = checkClassPath;
	}
	
	
	

}
