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
package slina.mb.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

/**
 * @author amaraa1
 *
 */
public class CronFileCopyWorkerImpl implements FileCopyWorker {
	
	private static final Logger LOGGER = Logger.getLogger(CronFileCopyWorkerImpl.class);

	private int portNumber = 22;
	private SourceInfo sourceInfo;
	private long fileSizeLmit = 1024*500;
	private String serverName;
	private String userId;
	private String password = "25arabepola";
	
	
	protected static Logger logger = Logger.getLogger(CronFileCopyWorkerImpl.class);
	

	@Override
	public void work() {
		
		logger.info( " CronFileCopyWorkerImpl has began working.");
		this.copy();
		
	}


	private void copy() {
		
		Session 	session 	= null;
		Channel 	channel 	= null;
		ChannelSftp channelSftp = null;

		try{
			
			long before = System.currentTimeMillis();
			
			JSch jsch = new JSch();
			
			
			 LOGGER.info( " creating session "+this.serverName+"  : "+new Date());
			session = jsch.getSession(this.userId,this.serverName,this.portNumber);
			
			session.setPassword(this.password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp)channel;

			this.copyFile(channelSftp);

		    channel.disconnect();
		    session.disconnect();
			
		    long after = System.currentTimeMillis();

		    LOGGER.info("\n JSch session took " + (after - before) + " ms \n");
		    
		    
		}catch(Exception ex){
			LOGGER.error(ex);
			ex.printStackTrace();
		}		
		
	}


	private void copyFile(ChannelSftp channelSftp)  {
		

			List<LogFilePath> logFileList = this.sourceInfo.getLogFileList();
			
			for(LogFilePath logFile: logFileList) {
				
				try {			
				
					LOGGER.info(this.serverName+"  ==> " +logFile.getSourceLogDirectory()+" : "+logFile.getFileName());
					long before = System.currentTimeMillis();				
				
					channelSftp.cd(logFile.getSourceLogDirectory());
					
	
					byte[] buffer = new byte[1024];
					BufferedInputStream bis = new BufferedInputStream(channelSftp.get(logFile.getFileName()));
								
					LOGGER.info(logFile.getfullFilePath());
					File newFile = new File(logFile.getfullFilePath());
					SftpATTRS stat = channelSftp.lstat(logFile.getFileName());
					
	
					long remoteFileSize =stat.getSize();
					
					if(remoteFileSize > this.fileSizeLmit) {
						bis.skip(remoteFileSize - this.fileSizeLmit);
					}
					
					OutputStream os = new FileOutputStream(newFile);
					BufferedOutputStream bos = new BufferedOutputStream(os);
	
						int readCount;
						//System.out.println("Getting: " + theLine);
						while( (readCount = bis.read(buffer)) > 0) {
							//System.out.println("Writing: " );
							bos.write(buffer, 0, readCount);
						}
						
						bos.flush();
						bos.close();
					
					bis.close();
				    long after = System.currentTimeMillis();
		
				    LOGGER.info(logFile.getFileName()+ " \n copy took " + (after - before) + " ms \n");
	
				} catch (Exception e) {
					LOGGER.error(e);
					LOGGER.error("\n************************ Error ***********************");
					LOGGER.info(this.serverName+"  ==> " +logFile.getLogDirectory());
					LOGGER.error("error processing file :"+logFile.getFileName());
					LOGGER.error(e);
					LOGGER.error("\n************************ Error end ***********************\n\n");
				}
			    
			    
			    
				
			}


	}


	public SourceInfo getSourceInfo() {
		return sourceInfo;
	}


	public void setSourceInfo(SourceInfo sourceInfo) {
		this.sourceInfo = sourceInfo;
	}


	public void setFileSizeLmit(long fileSizeLmit) {
		this.fileSizeLmit = fileSizeLmit;
	}


	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}


	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}
	
	
	
	
}
