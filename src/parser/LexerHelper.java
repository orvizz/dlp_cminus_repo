package parser;

public class LexerHelper {
	
	public static int lexemeToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}

	// TODO: Implement the lexemeToChar and lexemeToReal methods

	public static char lexemeToChar(String str) {
		if(str.length() == 3) {
			return str.charAt(1);
		} else if(str.length() > 3) {
			if(str.charAt(2) == 'n') {
				return '\n';
			} else if(str.charAt(2) == 't') {
				return '\t';
			} else {
				int value = lexemeToInt(str.substring(2, str.length()-2));
				if(value == -1) {
					return '\0';
				}
				return (char)value;
			}
		}
		return '\0';
	}

	public static float lexemeToReal(String str) {
		try{
			return Float.parseFloat(str);
		} catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}
	
}
