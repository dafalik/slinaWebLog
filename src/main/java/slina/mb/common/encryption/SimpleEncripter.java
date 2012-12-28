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

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;

/**
 * 
 * Credit goes to Herbert Wu
 * http://herbertwu.wordpress.com/2010/03/06/a-simple-string-cipher-in-java-6/
 * 
 * @author AnilAmarakoon
 *
 */


public class SimpleEncripter {

	 
	 private  String secret = "tvnw63ufg9gh5392"; // secret key length must be 16
	 private  SecretKey key;
	 private  Cipher cipher;
	 private  Base64 coder;
	
	
	public SimpleEncripter() {
		super();

	     try {
	    	 
			 this.key  = 	new SecretKeySpec(secret.getBytes(), "AES");
			 this.coder = new Base64();
			 this.cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public SimpleEncripter(String encode) {
		super();

	     try {
	    	 
	    	 if(encode.length() != 16 ) {
	    		 throw new RuntimeException("Key length must be 16 in length");
	    	 }
	    	 
			 this.key  = 	new SecretKeySpec(encode.getBytes(), "AES");
			 this.coder = new Base64();
			 this.cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}	
	
	
	
	public String encrypt(String plainText)  {
        
		try {
			
			this.cipher.init(Cipher.ENCRYPT_MODE, key);
			 byte[] cipherText = cipher.doFinal(plainText.getBytes());
			 return  new String(coder.encode(cipherText));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
       
        return null;
 }

 public  String decrypt(String codedText)  {
        
        try {
        	
        	byte[] encypted = this.coder.decode(codedText.getBytes());
			this.cipher.init(Cipher.DECRYPT_MODE, key);
			  byte[] decrypted = this.cipher.doFinal(encypted);  
			  return new String(decrypted);
			  
		} catch (Exception e) {
			e.printStackTrace();
		} 
      
       return null;
 }
	
	
	
}
