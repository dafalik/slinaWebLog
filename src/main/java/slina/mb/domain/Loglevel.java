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
package slina.mb.domain;


public enum Loglevel {
	
	SEVERE ("SEVERE", "#FF00FF"), 
	WARN ("WARN", "#FFFF00"), 
	INFO("INFO", "#0000FF"), 
	ERROR("ERROR", "#600000"),
	FATAL("FATAL", "#FF0000"), 
	DEBUG("DEBUG", "#282828"), 
	TRACE("TRACE", "black"),
	AUDIT("AUDIT", "blue"),
	APP_LOG("APP_LOG", "#FF00FF"),
	CONFIG("CONFIG", "#FF00FF"),
	APP_ERROR("APP_ERROR", "#FF00FF"),
	UNDEFINED("UNDEFINED", "#BDB76B");

	private final String color;  
	private final String name;



   Loglevel(String  name, String color) {
		this.color = color;
		this.name = name;
	}



	public String getColor() {
		return color;
	}
	
	
	
	public String getName() {
		return name;
	}

   


		public static Loglevel getEnum(String s){
	        if(SEVERE.name().equalsIgnoreCase(s)){
	            return SEVERE;
	        }else if(WARN.name().equalsIgnoreCase(s)){
	            return WARN;
	        }else if(INFO.name().equalsIgnoreCase(s)){
	            return INFO;
	        }else if (ERROR.name().equalsIgnoreCase(s)){
	            return ERROR;
	        }else if (FATAL.name().equalsIgnoreCase(s)){
	            return FATAL;
	        }else if (DEBUG.name().equalsIgnoreCase(s)){
	            return DEBUG;
	        }else if (TRACE.name().equalsIgnoreCase(s)){
	            return TRACE;
	        }
	        
	        throw new IllegalArgumentException("No Enum specified for this string "+s);
	    }


}
