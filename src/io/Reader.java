package io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Stack;

public class Reader {
	private static final boolean DEBUG = false;
	
	private static final int CHAR = 0,
			TEXT = 1,
			NUMERIC = 2,
			CUSTOM_TOKEN = 3
			;
	private int type;
	public char charVal;
	public String txtVal;
	public int numVal;
	
	private BufferedInputStream in;
	private char read;
	private int line;
	private int readLine;
	
	private HashSet<Character> identifiers;
	private HashSet<String> tokens;
	private Stack<Character> mag;
	
	public Reader(InputStream in) {
		this(in, new char[0], new String[0]);
	}
	
	public Reader(InputStream in, char... identifiers) {
		this(in, identifiers, new String[0]);
	}
	
	public Reader(InputStream in, String... tokens) {
		this(in, new char[0], tokens);
	}
	
	/**
	 * Reader object is used for tokenization of an InputStream.
	 * @param in					Input for tokenization.
	 * @param identifiers	optional chars being considered identifier chars, meaning strings found can consist of these.
	 * @param tokens		optional strings being considered as tokens, meaning these will be read as whole strings, not individual chars.
	 */
	public Reader(InputStream in, char[] identifiers, String... tokens) {
		this.in = new BufferedInputStream(in);
		this.line = 1;
		this.readLine = 1;
		this.identifiers = new HashSet<Character>();
		this.tokens = new HashSet<String>();
		this.mag = new Stack<Character>();
		for (char c : identifiers) {
			this.identifiers.add(c);
		}
		for (String s : tokens) {
			this.tokens.add(s);
		}
		read();
		next();
	}
	
	/**
	 * Checks if there is more to read.
	 * @return	true if more can be read.
	 */
	public boolean hasNext() {
		return read != -1;
	}
	
	/**
	 * Reads the next token.
	 */
	public void next() {
		if (!finishCustomToken()) {
			if (numeric(read)) {
				finishNumber();
			} else if (alphabetic(read)) {
				finishIdentifier();
			} else {
				finishChar();
			}
		}
		if (DEBUG) System.out.println("Next:\t " + this);
	}
	
	/**
	 * Returns read number and reads next token.
	 * @return	Current token as int.
	 */
	public int pollNumber() {
		if (type != NUMERIC) {
			throw new IllegalStateException("No numeric token found.");
		}
		int num = numVal;
		next();
		return num;
	}
	
	/**
	 * Notify the reader that a specific string is expected at it's current position.
	 * The reader will check if the string matches what is read, and call next() if successful.
	 * @param s		the expected string.
	 * @return		true for success.
	 */
	public boolean check(String s) {
		if (found().equals(s)) {
			if (DEBUG) System.out.println("Dmd: " + s);
			next();
			return true;
		} 
		if (DEBUG)System.out.println("DEMAND FAILED...\t\texpect " + s + "\t found " + charVal);
		return false;
	}
	
	/**
	 * Notify the reader that a specific char is expected at it's current position.
	 * The reader will check if the char matches what is read, and call next() if successful.
	 * @param c		the expected char.
	 * @return		true for success.
	 */
	public boolean check(char c) {
		if (type == CHAR && charVal == c || type == CUSTOM_TOKEN && txtVal.length() == 1 && txtVal.charAt(0) == c) {
			if (DEBUG) System.out.println("Dmd: " + c);
			next();
			return true;
		} 
		if (DEBUG)System.out.println("DEMAND FAILED...\t\texpect " + c + " (" + (int)c + ")\t found " + charVal + " (" + (int)charVal + ")");
		return false;
	}
	
	/**
	 * Can check if one out of many characters matches what is read.
	 * @param nextIfFound		if true, reader will call next() if any char matches.
	 * @param value					characters to be checked for matching.
	 * @return					integer telling the array index of matching char in c, -1 if no match. 
	 */
	public int check(boolean nextIfFound, String... s) {
		for (int x = 0; x < s.length; x++) {
			if (found().equals(s[x])) {
				if (nextIfFound) {
					next();
				}
				return x;
			} 
		}
		return -1;
	}
	
	/**
	 * Checks if the following read matches a char.
	 * @param value		checked for matching.
	 * @return		true if matching.
	 */
	public boolean peek(String s) {
		return s.charAt(0) == read;
	}
	
	/**
	 * Reads the next character, white space will be skipped.
	 * @return			False if any white space were skipped.
	 */
	private boolean read() {
		boolean continuous = true;
		line = readLine;
		try {
			readChar();
			boolean found10 = false;
			boolean found13 = false;
			while (read == 9 || read == 10 || read == 13 || read == 32 || read == 194) {
				if (read == 10 && !found13) {
					found10 = true;
					found13 = false;
					readLine++;
				} else if (read == 13 && !found10) {
					found13 = true;
					found10 = false;
					readLine++;
				} else {
					found10 = false;
					found13 = false;
				} 
				continuous = false;
				readChar();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return continuous;
	}
	
	private void readChar() throws IOException {
		if (mag.isEmpty()) {
			read = (char)in.read();
		} else {
			read = mag.pop();
		}
		if (DEBUG) System.out.println("Read: " + this);
	}
	
	private boolean finishCustomToken() {
		String temp = "";
		StringBuilder sb = new StringBuilder();
	
		boolean noWS = true;
		boolean matches = false;
		
		sb.append(read);
		if (completeTokenMatch(sb.toString())) {
			temp = sb.toString();
		}
		
		while (noWS && potentialTokenMatches(sb.toString()) > 0) {
			matches = true;
			noWS = read();
			sb.append(read);
			if (completeTokenMatch(sb.toString())) {
				temp = sb.toString();
			}
		}
		if (matches) {
			if (temp.length() < sb.length() - 1) {
				read();
			}
			if (temp == "") {
				prepareMag(sb);
				return false;			// no custom token found
			}
			// -------- custom token found ----------
			type = CUSTOM_TOKEN;			
			txtVal = temp;
			if (DEBUG) System.out.println("Custom token: " + temp);
			sb.replace(0, temp.length(), "");			// clears token from sb
			prepareMag(sb);
			return true;
		}
		return false;
	}
	
	private void prepareMag(StringBuilder sb) {
		if (sb.length() > 1) {
			mag.push(read);
			while (sb.length() > 1) {
				mag.push(sb.charAt(sb.length() - 1));		// adds rest of sb to mag
				sb.deleteCharAt(sb.length() - 1);
			}
			read = sb.charAt(0);
		}
	}
	
	private boolean completeTokenMatch(String str) {
		for (String token : tokens) {
			if (token.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	private int potentialTokenMatches(String str) { // replace tokens with param starting at tokens (from finishCustomToken) and remove all non matching.
		int matches = 0;
		for (String token : tokens) {
			if (token.length() >= str.length() && token.substring(0, str.length()).equals(str)) {
				matches++;
			}
		}
		return matches;
	}
	
	private void finishNumber() {
		StringBuilder sb = new StringBuilder();
		sb.append(read);
		boolean dec = false;
		while (!dec && read() && (numeric(read) || read == '.')) {
			sb.append(read);
			if (read == '.') {
				dec = true;
				while(read() && numeric(read)) {
					sb.append(read);
				}
			}
		}
		if (check("i")) {
			numVal = Integer.parseInt(sb.toString());
//		} else if (check("d")) {
//			numVal = Double.parseDouble(sb.toString());
//			numVal = NumberParser.parseDouble(sb, dec);
		} else {
			numVal = Integer.parseInt(sb.toString());
		}
		type = NUMERIC;
	}
	
	private void finishIdentifier() {
		StringBuilder sb = new StringBuilder();
		sb.append(read);
		while (read() && identifiable(read)) {
			sb.append(read);
		}
		txtVal = sb.toString();
		type = TEXT;
	}
	
	private void finishChar() {
		charVal = read;
		type = CHAR;
		read();
	}
	
	private boolean numeric(int z) {
		return z >= '0' && z <= '9';
	}
	
	private boolean alphabetic(int z) {
		return z >= 'a' && z <= 'z' || z >= 'A' && z <= 'Z';
	}
	
	private boolean identifiable(int z) {
		return alphabetic(z) || numeric(z) || identifiers.contains((char)z);
	}
	
	/**
	 * @return		string representation of read.
	 */
	public String found() {
		if (type == TEXT || type == CUSTOM_TOKEN) {
			return txtVal;
		} else if (type == CHAR) {
			return Character.toString(charVal);
		} else { //numeric
			return Integer.toString(numVal);
		}
	}
	
	public boolean foundText() {
		return type == TEXT;
	}
	
	public boolean foundNumeric() {
		return type == NUMERIC;
	}
	
	public boolean foundChar() {
		return type == CHAR || type == CUSTOM_TOKEN;
	}
	
	/**
	 *	tells whether a string was found
	 *	@param str		the string
	 */
	public boolean found(String str) {
		return found().equals(str);
	}
	
	@Override
	public String toString() {
		String s = "";
		if (type == CHAR || type == CUSTOM_TOKEN) {
			s = "chr";
		} else if (type == NUMERIC) {
			s = "num";
		} else if (type == TEXT) {
			s = "txt";
		}
		return "[Reader @ line " + line + ": " + s + " \"" + found() + "\" , \'" + read + "' /" + (int)read + "]";
	}
}