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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

/**
 * @author anil
 *
 */
public class SFTPDownload {

	private String hostName;
	private int portNumber = 22;
	private String sftpUser;
	private String sftpPassword;
	private String remoreFilePath;
	private String remoreFileName;
	private String localFilePathAndName;
 	
	public void downloadFile() {

		Session 	session 	= null;
		Channel 	channel 	= null;
		ChannelSftp channelSftp = null;

		try{
			JSch jsch = new JSch();
			session = jsch.getSession(this.sftpUser,this.hostName,this.portNumber);
			session.setPassword(this.sftpPassword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp)channel;
			channelSftp.cd(this.remoreFilePath);
			
//			String home = channelSftp.getHome();
//			String lpwd = channelSftp.lpwd();
			SftpATTRS stat = channelSftp.lstat(this.remoreFileName);
			long remoteFileSize =stat.getSize();
			
			long needLength = 1024*500;
			long skip = 0;
			
			if(remoteFileSize > needLength) {
				skip = remoteFileSize-needLength;
			}
			
			
			byte[] buffer = new byte[1024];

			BufferedInputStream bis = new BufferedInputStream(channelSftp.get(this.remoreFileName));
			bis.skip(skip);
						
			File newFile = new File(this.localFilePathAndName);

			
			OutputStream os = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount;
			//System.out.println("Getting: " + theLine);
			while( (readCount = bis.read(buffer)) > 0) {
				//System.out.println("Writing: " );
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			bos.close();
		     channel.disconnect();
		     session.disconnect();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}

	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public String getSftpPassword() {
		return this.sftpPassword;
	}


	public void setSftpUser(String sftpUser) {
		this.sftpUser = sftpUser;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public void setRemoreFilePath(String remoreFilePath) {
		this.remoreFilePath = remoreFilePath;
	}

	public void setRemoreFileName(String remoreFileName) {
		this.remoreFileName = remoreFileName;
	}

	public void setLocalFilePathAndName(String localFilePathAndName) {
		this.localFilePathAndName = localFilePathAndName;
	}

	
	
}
