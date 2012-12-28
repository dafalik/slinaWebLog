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
package slina.mb.common.encryption;

import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;



/**
 * Encription helper class to encode and decode master password.
 * Uses SimpleEncripter to encrip and decript passwords
 * 
 * @author AnilAmarakoon
 */
public class Cipher extends EnvironmentStringPBEConfig {
	
    private SimpleEncripter en = new SimpleEncripter();
	
	
	public void setPassword(final String password) {
		  
		 String pw = en.decrypt(password);
			super.setPassword(pw);

	  }
	  
	public String igEncode(String input) {
		return en.encrypt(input);
	}
	
	public String igDecode(String input) {
		return en.decrypt(input);
	}

	
	
}
