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

import jcifs.smb.SmbFile;

public interface Session {

	/**
	 * @see org.springframework.integration.file.remote.session.Session#remove(java.lang.String)
	 */
	public abstract boolean remove(String path) throws IOException;

	/**
	 * @see org.springframework.integration.file.remote.session.Session#list(java.lang.String)
	 */
	public abstract SmbFile[] list(String path) throws IOException;

	/**
	 * @see org.springframework.integration.file.remote.session.Session#read(java.lang.String, java.io.OutputStream)
	 */
	public abstract void read(String source, OutputStream os)
			throws IOException;

	/**
	 * @see org.springframework.integration.file.remote.session.Session#write(java.io.InputStream, java.lang.String)
	 */
	public abstract void write(InputStream inputStream, String destination)
			throws IOException;

	/**
	 * @see org.springframework.integration.file.remote.session.Session#rename(java.lang.String, java.lang.String)
	 */
	public abstract void rename(String pathFrom, String pathTo)
			throws IOException;

	/**
	 * @see org.springframework.integration.file.remote.session.Session#mkdir(java.lang.String)
	 */
	public abstract void mkdir(String directory) throws IOException;

	/**
	 * @see org.springframework.integration.file.remote.session.Session#close()
	 */
	public abstract void close();

	/**
	 * @see org.springframework.integration.file.remote.session.Session#isOpen()
	 */
	public abstract boolean isOpen();
	
	public void read(String source, OutputStream os, long skipLimit) throws IOException;

}