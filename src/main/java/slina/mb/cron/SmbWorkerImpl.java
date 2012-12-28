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



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import slina.mb.io.FileCopyWorker;
import slina.mb.io.LogFilePath;
import slina.mb.io.SourceInfo;
import slina.mb.smb.DefaultSmbSessionFactory;
import slina.mb.smb.Session;
import slina.mb.smb.SessionFactory;

public class SmbWorkerImpl implements FileCopyWorker  {
	private final static Log logger = LogFactory.getLog(SmbWorkerImpl.class);
	
	private SourceInfo sourceInfo;
	private long fileSizeLmit = 1024*500;
	private String winsServer;

	private ServerInfo serverInfo;
	

	
	public void work() {
		
		
		
		jcifs.Config.setProperty( "jcifs.netbios.wins", this.winsServer );
		SessionFactory factory = new DefaultSmbSessionFactory();
		
		factory.setDomain(this.serverInfo.getDomain());
		factory.setUser(this.serverInfo.getUserId());
		factory.setPassword(this.serverInfo.getPassword());
		Session session = null;
		
		long before = System.currentTimeMillis();
			
			session = factory.getSession();
			
			List<LogFilePath> logFileList = this.sourceInfo.getLogFileList();
			
			for(LogFilePath logFile: logFileList) {
				
				String fullPath = logFile.getfullFilePath();
				
				
				try {				
				
				
					File newFile = new File(fullPath);
					
					if(!newFile.exists()) {
						newFile.createNewFile();
					}
					
					
					OutputStream os = new FileOutputStream (newFile);
					
					String destinationFile = logFile.getSourceLogDirectory()+logFile.getFileName();		
					String serverUrl = "smb://"+this.serverInfo.getServerName()+destinationFile;
					
					session.read(serverUrl, os, this.fileSizeLmit);
					
					if(os!= null) {
						os.flush();
						os.close();
					}
				
				
				}catch(Exception e) {	
					logger.error("\n************************ Error ***********************");
					logger.error("error processing file :"+logFile.getFileName());
					logger.error(e);	
					logger.error("\n************************ Error end ***********************\n\n");
				}				
				
				
				
			}
			
			session.close();
			
		    long after = System.currentTimeMillis();

		    logger.info("\n SMB session took " + (after - before) + " ms \n");

	}

	public void setFileSizeLmit(long fileSizeLmit) {
		this.fileSizeLmit = fileSizeLmit;
	}

	public void setSourceInfo(SourceInfo sourceInfo) {
		this.sourceInfo = sourceInfo;
	}

	public void setWinsServer(String winsServer) {
		this.winsServer = winsServer;
	}


	/**
	 * @return the sourceInfo
	 */
	public SourceInfo getSourceInfo() {
		return sourceInfo;
	}


	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	@Override
	public String getServerName() {
		return this.serverInfo.getServerName();
	}

	
	
	
}