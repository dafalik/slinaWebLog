package slina.mb.parsing;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class ReverseParserFromLog4jPattern {
	
	
	  //all lines other than first line of exception begin with tab followed by 'at' followed by text
	  private static final String EXCEPTION_PATTERN = "^\\s+at.*";
	  private static final String REGEXP_DEFAULT_WILDCARD = ".*?";
	  private static final String REGEXP_GREEDY_WILDCARD = ".*";
	  private static final String PATTERN_WILDCARD = "*";
	  private static final String NOSPACE_GROUP = "(\\S*\\s*?)";
	  private static final String DEFAULT_GROUP = "(" + REGEXP_DEFAULT_WILDCARD + ")";
	  private static final String GREEDY_GROUP = "(" + REGEXP_GREEDY_WILDCARD + ")";
	  private static final String MULTIPLE_SPACES_REGEXP = "[ ]+";
	  private final String newLine = System.getProperty("line.separator");	
	
	  private SimpleDateFormat dateFormat;
	  private String timestampFormat = "yyyy-MM-d HH:mm:ss,SSS";
	  
	  private static final String VALID_DATEFORMAT_CHARS = "GyMwWDdFEaHkKhmsSzZ";
	  private static final String VALID_DATEFORMAT_CHAR_PATTERN = "[" + VALID_DATEFORMAT_CHARS + "]";

	  
	  
	  private String convertTimestamp() {
		    //some locales (for example, French) generate timestamp text with characters not included in \w -
		    // now using \S (all non-whitespace characters) instead of /w 
		    String result = timestampFormat.replaceAll(VALID_DATEFORMAT_CHAR_PATTERN + "+", "\\\\S+");
		    //make sure dots in timestamp are escaped
		    result = result.replaceAll(Pattern.quote("."), "\\\\.");
		    return result;
	  }
	  
}
