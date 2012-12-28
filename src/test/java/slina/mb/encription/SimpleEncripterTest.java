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


import java.math.BigInteger;

import org.junit.Test;

import slina.mb.common.encryption.SimpleEncripter;

public class SimpleEncripterTest {
	
	byte key[] = new BigInteger("39e858f86df9b909a8c87cb8d9ad599", 16).toByteArray();

	
	
	
	@Test
	public void testTeaEncode() throws Exception {
		
		SimpleEncripter en = new SimpleEncripter();
		String plainText = "Hello World";
		
		String encoded = en.encrypt(plainText);
		System.out.println(plainText +" encoded ->  "+encoded);
	}
	
	
	@Test
	public void TestTeaDecode() throws Exception {
		SimpleEncripter en = new SimpleEncripter();
		
		String codedText = "Jdai+7nxDamgB8pMfsYTyw==";
		String decoded = en.decrypt(codedText);
		
		System.out.println(codedText+ " decoded output -> " + decoded);
	}

	
	
	
}
