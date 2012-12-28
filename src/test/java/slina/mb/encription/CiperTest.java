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
package slina.mb.encription;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import slina.mb.common.encryption.Cipher;

/**
 * Test if master password can be encode and decode
 * 
 * @author anilAmarakoon
 */
public class CiperTest {

	@Test
	public void testEncription() {
		
		Cipher c = new Cipher();
		
		String enc = c.igEncode("enc{HwYTYJka}enc");
		assertEquals("EpACm9aX3ZvUvI5ah/SdHfk4dTbAJmxDlWjvulF1qjI=",enc);
		
		System.out.println(enc);
		
		String dec = c.igDecode(enc);
		
		System.out.println(dec);
		assertEquals("enc{HwYTYJka}enc",dec);
		
	}
	
	
}
