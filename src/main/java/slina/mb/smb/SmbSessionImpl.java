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
package slina.mb.smb;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.NestedIOException;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

public class SmbSessionImpl implements Session  {
	private final static Log logger = LogFactory.getLog(SmbSessionImpl.class);
	
	private final NtlmPasswordAuthentication ntlmAuth;
	
	private boolean isOpen;
	private long fileSizeLmit = 1024*10;
	
	/**
	 * The default constructor.
	 * @param ntlmAuth NTLM user credentials
	 */
	public SmbSessionImpl(NtlmPasswordAuthentication ntlmAuth) {
		Assert.notNull(ntlmAuth, "ntlmAuth must not be null");
		this.ntlmAuth = ntlmAuth;
		this.isOpen = true;
	}


	/* (non-Javadoc)
	 * @see ig.mips.anil.smb.Session#remove(java.lang.String)
	 */
	@Override
	public boolean remove(String path) throws IOException {
		try {
			SmbFile file = new SmbFile(path, ntlmAuth);
			file.delete();
		} catch( SmbAuthException ex ) {
		    // handle authentication related issue here
			isOpen = false;
			throw new NestedIOException("Failed to remove resource " + path, ex);
		} catch( SmbException ex ) {
		    // any special SMB related exception handling
			throw new NestedIOException("Failed to remove resource " + path, ex);
		}
		if (logger.isDebugEnabled()){
			logger.debug("Successfully removed resource " + path);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see ig.mips.anil.smb.Session#list(java.lang.String)
	 */
	@Override
	public SmbFile[] list(String path) throws IOException {
		try {
			SmbFile file = new SmbFile(path, ntlmAuth);
			SmbFile[] list = file.listFiles();
			if (list == null) {
				throw new NestedIOException("Failed to list resource - unable to resolve " + path);
			}
			return list;
		} catch( SmbAuthException ex ) {
		    // handle authentication related issue here
			isOpen = false;
			throw new NestedIOException("Failed to list resource " + path, ex);
		} catch( SmbException ex ) {
		    // any special SMB related exception handling
			throw new NestedIOException("Failed to list resource " + path, ex);
		}
	}

	
	public void read(String source, OutputStream os, long skipLimit) throws IOException {
		
		try {
			SmbFile file = new SmbFile(source, ntlmAuth);
			InputStream is  = file.getInputStream();
		
			long remoteFileSize = file.length();
			
			
			if(remoteFileSize > skipLimit) {
				
				long skip = remoteFileSize-skipLimit;
				is.skip(skip);				
			}
			FileCopyUtils.copy(is, os);
		} catch( SmbAuthException ex ) {
		    // handle authentication related issue here
			isOpen = false;
			throw new NestedIOException("Failed to read resource " + source, ex);
		} catch( SmbException ex ) {
		    // any special SMB related exception handling
			throw new NestedIOException("Failed to read resource " + source, ex);
		}
		if (logger.isDebugEnabled()){
			logger.debug("Successfully read resource " + source);
		}
		

		
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see ig.mips.anil.smb.Session#read(java.lang.String, java.io.OutputStream)
	 */
	@Override
	public void read(String source, OutputStream os) throws IOException {
		try {
			SmbFile file = new SmbFile(source, ntlmAuth);
			InputStream is  = file.getInputStream();
		
			long remoteFileSize = file.length();
			
			
			if(remoteFileSize > this.fileSizeLmit) {
				
				long skip = remoteFileSize-this.fileSizeLmit;
				is.skip(skip);
				
			}
			FileCopyUtils.copy(is, os);
		} catch( SmbAuthException ex ) {
		    // handle authentication related issue here
			isOpen = false;
			throw new NestedIOException("Failed to read resource " + source, ex);
		} catch( SmbException ex ) {
		    // any special SMB related exception handling
			throw new NestedIOException("Failed to read resource " + source, ex);
		}
		if (logger.isDebugEnabled()){
			logger.debug("Successfully read resource " + source);
		}
	}

	/* (non-Javadoc)
	 * @see ig.mips.anil.smb.Session#write(java.io.InputStream, java.lang.String)
	 */
	@Override
	public void write(InputStream inputStream, String destination) throws IOException {
		try {
			SmbFile file = new SmbFile(destination, ntlmAuth);
			OutputStream os = file.getOutputStream();
			FileCopyUtils.copy(inputStream, os);
		} catch( SmbAuthException ex ) {
		    // handle authentication related issue here
			isOpen = false;
			throw new NestedIOException("Failed to write resource " + destination, ex);
		} catch( SmbException ex ) {
		    // any special SMB related exception handling
			throw new NestedIOException("Failed to write resource " + destination, ex);
		}
		if (logger.isDebugEnabled()){
			logger.debug("Successfully written resource " + destination);
		}	
	}

	/* (non-Javadoc)
	 * @see ig.mips.anil.smb.Session#rename(java.lang.String, java.lang.String)
	 */
	@Override
	public void rename(String pathFrom, String pathTo) throws IOException {
		try {
			SmbFile source = new SmbFile(pathFrom, ntlmAuth);
			SmbFile dest = new SmbFile(pathTo, ntlmAuth);
			source.renameTo(dest);
		} catch( SmbAuthException ex ) {
		    // handle authentication related issue here
			isOpen = false;
			throw new NestedIOException("Failed to rename from " + pathFrom + " to " + pathTo, ex);
		} catch( SmbException ex ) {
		    // any special SMB related exception handling
			throw new NestedIOException("Failed to rename from " + pathFrom + " to " + pathTo, ex);
		}
		if (logger.isDebugEnabled()){
			logger.debug("Resource " + pathFrom + " was successfully renamed to " + pathTo);
		}	
	}

	/* (non-Javadoc)
	 * @see ig.mips.anil.smb.Session#mkdir(java.lang.String)
	 */
	@Override
	public void mkdir(String directory) throws IOException {
		try {
			SmbFile file = new SmbFile(directory, ntlmAuth);
			file.mkdirs();
		} catch( SmbAuthException ex ) {
		    // handle authentication related issue here
			isOpen = false;
			throw new NestedIOException("Failed to create directory " + directory, ex);
		} catch( SmbException ex ) {
		    // any special SMB related exception handling
			throw new NestedIOException("Failed to create directory " + directory, ex);
		}
		if (logger.isDebugEnabled()){
			logger.debug("Successfully created " + directory);
		}	
	}

	/* (non-Javadoc)
	 * @see ig.mips.anil.smb.Session#close()
	 */
	@Override
	public void close() {
		isOpen = false;
	}

	/* (non-Javadoc)
	 * @see ig.mips.anil.smb.Session#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return isOpen;
	}


	public void setFileSizeLmit(long fileSizeLmit) {
		this.fileSizeLmit = fileSizeLmit;
	}

	
	
}