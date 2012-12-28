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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.junit.Test;

/**
 * Test for Jasypt framework
 * 
 * 	  XML Configuration 
 * 
 * 
 * 
 * 	  #Set bean with jasypt and master password
	  <bean id="environmentVariablesConfiguration" class="org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig">
   		<property name="algorithm" value="PBEWithMD5AndDES" />
   		<property name="passwordEnvName" value="MasterPassWord" />
 	   </bean>
	 
		<bean id="configurationEncryptor" class="org.jasypt.encryption.pbe.StandardPBEStringEncryptor">
		   <property name="config" ref="environmentVariablesConfiguration" />
		</bean>	 
StandardPBEStringEncryptor
		 <bean id="propertyConfigurer"  class="org.jasypt.spring3.properties.EncryptablePropertyPlaceholderConfigurer">
		   <constructor-arg ref="configurationEncryptor" />
		   <property name="locations">
		     <list>
		       <value>/WEB-INF/classes/application.properties</value>
		     </list>
		   </property>		   
		 </bean>	 

	application.properties	
		 datasource.driver=com.mysql.jdbc.Driver
		 datasource.url=jdbc:mysql://localhost/reportsdb
		 datasource.username=reportsUser
		 datasource.password=ENC(G6N718UuyPE5bHyWKyuLQSm02auQPUtm)

 * @author AnilAmarakoon
 */

public class EncriptionTest {
	

	
	
	/** master password encripter with default encrption algoritm */
	@Test
	public void testencription() {
		
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		String encryptedMasterPassword = passwordEncryptor.encryptPassword("masterPassword");
	
	//	System.out.println("master "+encryptedMasterPassword);
		assertFalse("UTrlsLKXzcWwEUUbzLlBoGYPZXA2Gkcv".compareToIgnoreCase(encryptedMasterPassword) == 0);
		assertTrue(passwordEncryptor.checkPassword("masterPassword", "UTrlsLKXzcWwEUUbzLlBoGYPZXA2Gkcv"));

	}
	
	/** master password encripter with SHA-1 encryption */
	@Test
	public void testencription1() {
		
		
		ConfigurablePasswordEncryptor passwordEncryptor1 = new ConfigurablePasswordEncryptor();
		passwordEncryptor1.setAlgorithm("SHA-1");
		passwordEncryptor1.setPlainDigest(true);
		String encryptedPassword1 = passwordEncryptor1.encryptPassword("999RIverN173");

		assertTrue("fTbFX66KlySNIRAqsfDJa0nsV28=".compareToIgnoreCase(encryptedPassword1) == 0);
		assertTrue(passwordEncryptor1.checkPassword("999RIverN173", "fTbFX66KlySNIRAqsfDJa0nsV28="));
		
	}
	
	/** master password encripter with PBEWithMD5AndDES encryption */
	@Test
	public void testencription3() {
		
		
		StandardPBEStringEncryptor passwordEncryptor1 = new StandardPBEStringEncryptor();
		passwordEncryptor1.setAlgorithm("PBEWithMD5AndDES");
		passwordEncryptor1.setPassword("password");

		
		assertEquals("test", passwordEncryptor1.decrypt("HyPQUAN87j5I++JXF53pAg=="));
	}
	
	/**
	 * encription string changes with time. but decription always returns same string
	 */
	@Test
	public void testencription5() {
		

		EnvironmentStringPBEConfig passwordEncryptor1 = new EnvironmentStringPBEConfig();
		passwordEncryptor1.setAlgorithm("PBEWithMD5AndDES");
		passwordEncryptor1.setPassword("enc{HwYTYJka}enc");
		
		StandardPBEStringEncryptor passwordEncryptor2 = new StandardPBEStringEncryptor();
		passwordEncryptor2.setConfig(passwordEncryptor1);

		String encryptedUserId = passwordEncryptor2.encrypt("userId1");
		String encryptedPassword = passwordEncryptor2.encrypt("999RIverN173");
		String encryptedServerName = passwordEncryptor2.encrypt("serverName");
		String encryptedDomain = passwordEncryptor2.encrypt("mydomain.com");
		
	
		System.out.println("******************************************");
		System.out.println("UserId   "+encryptedUserId);
		System.out.println("password   "+encryptedPassword);
		System.out.println("serverName   "+encryptedServerName);
		System.out.println("domain    "+encryptedDomain);
		System.out.println("******************************************");
		
		
		assertFalse("dYP2bmmaAkF2AY3qhdNjXg==".compareToIgnoreCase(encryptedUserId) == 0);
		assertFalse("3rK/jN4A+VO0bxTT/TUN69MBzHVBsRtC".compareToIgnoreCase(encryptedPassword) == 0);
		assertFalse("7UJSUMgLwDKeJvvVuyngvS6J6VTX12jk".compareToIgnoreCase(encryptedServerName) == 0);
		assertFalse("iULL9cQRmMReDydMe415z/pZYL7G43OT".compareToIgnoreCase(encryptedDomain) == 0);

		
		String password = passwordEncryptor2.decrypt(encryptedPassword);
		String userId = passwordEncryptor2.decrypt(encryptedUserId);
		String serverName = passwordEncryptor2.decrypt(encryptedServerName);
		String domain = passwordEncryptor2.decrypt(encryptedDomain);
		
		
		assertEquals("userId1", userId);
		assertEquals("999RIverN173", password);
		assertEquals("serverName", serverName);
		assertEquals("mydomain.com", domain);

	}
	

}
