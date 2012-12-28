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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import slina.mb.common.encryption.DecryptingPlaceHolderConfigurer;
import slina.mb.common.encryption.SimpleEncripter;

/**
 * Tests for password encryption
 * @author AnilAmarakoon
 */
public class DecryptingPlaceHolderConfigurerTest {

 
    private static final SimpleEncripter   encripter = new SimpleEncripter();
    private static final String PREFIX   = "decrypt:";

    private final static String word1 = "winnipeg";
    private final static String word2 = "CanaDianWinTer2";
    private final static String word3 = "jhdjd6543uweuwe_uiwyeuwye";
    private final static String word4 = "oYBGeUI1OFEa7IjRt2fRWA==";
    private final static String word5 = "vAhvryr+/8ZqaZ4wkJ+XiQ==";
    private final static String word6 = "M0s5vp4DY/Y7iL6rrnVvEh4ibD0c7lwhfYSpcR8eBqU=";


    
    @Test
    public void faketest() {
    	assertEquals(true, true);
    }
    
    /**
     * Test encripter for known encryptions 
     */
    @Test
    public void testEncription() {

        String encriptedString = encripter.encrypt(word1);
        System.out.println(encriptedString);
        assertNotNull(encriptedString);
        assertEquals(24, encriptedString.length());
        assertEquals(word4, encriptedString);

        encriptedString = encripter.encrypt(word2);
        assertEquals(24, encriptedString.length());
        assertEquals(word5, encriptedString);
        System.out.println(encriptedString);

        encriptedString = encripter.encrypt(word3);
        assertEquals(44, encriptedString.length());
        assertEquals(word6, encriptedString);
        System.out.println(encriptedString);
    }


    /**
     * test extended spring class DecryptingPlaceHolderConfigurer for known encryptions
     */
    @Test
    public void testDecrypting() {

        DecryptingPlaceHolderConfigurer decrypter = new DecryptingPlaceHolderConfigurer();

        String w1 = decrypter.convertPropertyValue(PREFIX+word4);
        assertNotNull(w1);
        assertEquals(word1, w1);

        w1 = decrypter.convertPropertyValue(PREFIX+word5);
        assertNotNull(w1);
        assertEquals(word2, w1);

        w1 = decrypter.convertPropertyValue(PREFIX+word6);
        assertNotNull(w1);
        assertEquals(word3, w1);
    }

    /**
     * test extended spring class DecryptingPlaceHolderConfigurer for known encryptions with prefix
     */
    @Test
    public void tewstdatabasePasword() {
    	
    	 DecryptingPlaceHolderConfigurer decrypter = new DecryptingPlaceHolderConfigurer();
    	
    	 String encriptedString = encripter.encrypt("MIQJsldosdkskd1");
    	 assertEquals("CidwufyPDP3Vkm1oDzvOtg==", encriptedString);
    	
    	 String w1 = decrypter.convertPropertyValue(PREFIX+encriptedString);
    	 assertEquals("MIQJsldosdkskd1", w1);
    	 
    	 String w11 = decrypter.convertPropertyValue("decrypt:M0s5vp4DY/Y7iL6rrnVvEh4ibD0c7lwhfYSpcR8eBqU=");
    	 assertEquals("jhdjd6543uweuwe_uiwyeuwye", w11);
    	 
    }

    /**
     * test extended spring class DecryptingPlaceHolderConfigurer for non encryption words
     */
    @Test
    public void testforNonEncriptionWord() {
    	
    	 DecryptingPlaceHolderConfigurer decrypter = new DecryptingPlaceHolderConfigurer();
    	 String w11 = decrypter.convertPropertyValue("plain string");
    	 assertEquals("plain string", w11);
    }
    
}
