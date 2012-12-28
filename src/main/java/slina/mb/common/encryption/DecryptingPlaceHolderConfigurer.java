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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * A property place-holder configure that allows encryption of the property
 * values that replace place-holders.  This allows us to store sensitive
 * values in property files that are kept under source-code control, then
 * deploy them via BDS without post-deploy intervention to edit them.
 *
 * <p>Usage: declare a PropertyConfig bean in a Spring configuration file,
 * configured with the location of the properties file:</p>
 * <pre>
 * &lt;bean class= "slina.mb.common.encryption.DecryptingPlaceHolderConfigurer"&gt;
 *   &lt;property name="location" value="classpath:/config/foo/foo.properties" /&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>Use the "decrypt:" prefix on encrypted values in the properties file:</p>
 * <pre>
 * db.password = decrypt:M0s5vp4DY/Y7iL6rrnVvEh4ibD0c7lwhfYSpcR8eBqU=
 * </pre>
 *
 * <p>Generate encrypted strings by modifying the "DecryptingPlaceHolderConfigurerTest" unit test.</p>
 * @author AnilAmarakoon
 *
 * @see org.springframework.beans.factory.config.PropertyResourceConfigurer
 */
public class DecryptingPlaceHolderConfigurer extends PropertyPlaceholderConfigurer {


    private static final String PREFIX   = "decrypt:";
    private static final Logger logger = Logger.getLogger(DecryptingPlaceHolderConfigurer.class);

    public DecryptingPlaceHolderConfigurer() {
        super();
        logger.info("Starting EncryptingPlaceHolderConfigurer");
    }
    /**
     * @see org.springframework.beans.factory.config.PropertyResourceConfigurer#convertPropertyValue(java.lang.String)
     */
    @Override
    public String convertPropertyValue(String originalValue) {

    	SimpleEncripter   encripter  = new SimpleEncripter();
        logger.debug(originalValue);
        
        if (originalValue.startsWith(PREFIX)) {
    	
        	String codedText = originalValue.substring(PREFIX.length());
        	originalValue = encripter.decrypt(codedText);

        }

        return originalValue;

    }


}
