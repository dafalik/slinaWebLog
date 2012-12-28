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


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import slina.mb.io.FileCopyWorker;
import slina.mb.io.LogFilePath;
import slina.mb.io.SourceInfo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

/**
 * @author amaraa1
 *
 */
public class CronSSHFileWorkerImpl implements FileCopyWorker {
	
	private static final Logger LOGGER = Logger.getLogger(CronSSHFileWorkerImpl.class);

	private int portNumber = 22;
	private SourceInfo sourceInfo;
	private long fileSizeLmit = 1024*500;

	private ServerInfo serverInfo;

	@Override
	public void work() {
		
		LOGGER.info( " CronFileCopyWorkerImpl has began working.");
		this.copy();
		
	}


	private void copy() {
		
		Session 	session 	= null;
		Channel 	channel 	= null;
		ChannelSftp channelSftp = null;

		try{
			
			long before = System.currentTimeMillis();
			
			JSch jsch = new JSch();
			
			
			LOGGER.info( "creating session  "+this.serverInfo.getServerName()+"  --> "+new Date());
			session = jsch.getSession(this.serverInfo.getUserId(),this.serverInfo.getServerName(),this.portNumber);
			
			session.setPassword(this.serverInfo.getPassword());
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
			
			if(channel != null && !channel.isClosed())
				channel.disconnect();
			
			if(session !=null && session.isConnected()) {
				 session.disconnect();
			}
				
		}		
		
	}


	private void copyFile(ChannelSftp channelSftp) throws IOException  {
		
		BufferedInputStream bis = null ;
		BufferedOutputStream bos = null;
		
		
	
			List<LogFilePath> logFileList = this.sourceInfo.getLogFileList();
			
			for(LogFilePath logFile: logFileList) {
				
				long before = System.currentTimeMillis();				
			
				String remoteDirectory = logFile.getSourceLogDirectory();
				
							
					try {	
								LOGGER.info(this.serverInfo.getServerName()+"  ==> " +remoteDirectory+" : "+logFile.getFileName());
								channelSftp.cd(remoteDirectory);
								
								String fname = logFile.getFileName();
				
								byte[] buffer = new byte[1024];
								bis = new BufferedInputStream(channelSftp.get(logFile.getFileName()));
											
								File localDirectory = new File(logFile.getLogDirectory());
								
								if(!localDirectory.exists()) {
									localDirectory.mkdir();
								}
								
								SftpATTRS stat = channelSftp.lstat(fname);				
				
								long remoteFileSize = stat.getSize();
								
								if(remoteFileSize > this.fileSizeLmit) {
									bis.skip(remoteFileSize - this.fileSizeLmit);
								}
								
								File newFile = new File(logFile.getfullFilePath());
								OutputStream os = new FileOutputStream(newFile);
								bos = new BufferedOutputStream(os);
				
									int readCount;
				
									while( (readCount = bis.read(buffer)) > 0) {
										bos.write(buffer, 0, readCount);
									}
									
									bos.flush();
									bos.close();
								
								bis.close();
							    long after = System.currentTimeMillis();
					
							   
							    LOGGER.info(logFile.getFileName()+ " \n copy took " + (after - before) + " ms \n");
				
						    
						    
						} catch (Exception e) {
							LOGGER.error("\n************************ Error ***********************");
							LOGGER.info(this.serverInfo.getServerName()+"  ==> " +logFile.getLogDirectory());
							LOGGER.error("error processing file :"+logFile.getFileName());
							LOGGER.error(e);
							LOGGER.error("\n************************ Error end ***********************\n\n");
							
							if(bis != null)
								bis.close();
							
							if(bos != null) {
								bos.close();
							}
							
						}	 //end exception   

				
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


	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return this.serverInfo.getServerName();
	}


	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}
	
	
	
	
}
