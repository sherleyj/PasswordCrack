import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;

public class PasswordCrack {
	public static Hashtable<String,Vector<String>> dictMangles = new Hashtable<String,Vector<String>>();

	public static void main(String args[]) throws IOException{

		try{
			InputStream dictionaryFile = new FileInputStream(args[0]);
			InputStream passwordsFile = new FileInputStream(args[1]);

			InputStreamReader dicStream = new InputStreamReader(dictionaryFile);
			InputStreamReader pswStream = new InputStreamReader(passwordsFile); 

			BufferedReader dictReader = new BufferedReader(dicStream);
			BufferedReader pswReader = new BufferedReader(pswStream);

			String psw_cracked = null;
			buildHashTable(dictReader);

			String psw_line = new String();
			//psw_line = pswReader.readLine();
			long timeBegin = System.nanoTime();
			int count = 0;

			while((psw_line = pswReader.readLine()) != null){
				System.out.println(psw_line);
				String[] user = psw_line.split(":");
				String[] name = user[4].split(" ");
				addHashTable(name[0]);
				addHashTable(name[1]);
				
				psw_cracked = crack(user);
				if (psw_cracked != null){
					System.out.println("CRACKED! : " + psw_cracked);
					count++;
				}
				else
					System.out.println("not cracked :(");
			}


			long timeEnd = System.nanoTime();
			System.out.println( "\nCracked " + count + " out of 20 passwords.");
			System.out.println( "time in ms: " + (timeEnd - timeBegin)/1000000);

		} catch (Exception E) {
			System.out.println("Caught Exception: " + E);}
		
	}


	public static String crack(String[] user){
		String salt = user[1].substring(0,2);
		String pswE = user[1].substring(2);
		String pswESalt = user[1];
		boolean match = false;
		String crackedPsw = null;
		String wordE = new String();
		String key = new String();
		int comp;

		Enumeration<String> keys = dictMangles.keys();
		while (keys.hasMoreElements() && !match) {
			key = keys.nextElement();

			// wordE = jcrypt.crypt(salt, key);
			// if( (comp = pswESalt.compareTo(wordE)) == 0 ){
			// 	crackedPsw = key;
			// 	match = true;
			// 	break;
			// }
			// wordE = jcrypt.crypt(salt, name[0].toLowerCase());
			// if( (comp = pswESalt.compareTo(wordE)) == 0){
			// 	crackedPsw = name[0];
			// 	match = true;
			// 	break;
			// }

			// wordE = jcrypt.crypt(salt, name[1].toLowerCase());
			// if( (comp = pswESalt.compareTo(wordE)) == 0 ){
			// 	crackedPsw = name[1];
			// 	match = true;
			// 	break;
			// }

			Vector<String> wordList = dictMangles.get(key);
			//System.out.println("hi");
			for(int i = 0; i < wordList.size(); i++){
				String word = wordList.get(i);
				//System.out.println(word);
				wordE = jcrypt.crypt(salt, word);
				if( (comp = pswESalt.compareTo(wordE)) == 0 ){
					crackedPsw = word;
					match = true;
					break;
				}
			}


			// System.out.println(key + " : " + dictMangles.get(key));	
			// System.out.println();
			// System.out.println();
			// System.out.println();	
		} 
		
		return crackedPsw;
	}

	// public static boolean WordEncryptCheck(){

	// }

	public static String pre_append (String word, String salt, String pswE){
		String pswCracked = null;
		String temp = new String();

		return pswCracked;
	}

	public static void buildHashTable(BufferedReader dictReader){
		String word = new String();
		String temp = new String();

		try{
			while((word = dictReader.readLine()) != null){
				int wordInt = (int)word.charAt(0);
				Vector<String> v = new Vector<String>();
				Vector<String> vDouble = new Vector<String>();
				// //temp = word;

				//just the word
				v.add(word);

				//all uppercase
				v.add(word.toUpperCase());

				//all lowercase
				v.add(word.toLowerCase());

				//uppercase first letter
				v.add( ((char)((wordInt>96 && wordInt<123)? wordInt - 32: wordInt)) + word.substring(1, word.length()) );

				//remove first letter
				v.add(word.substring(1, word.length()) );

				//remove last letter
				v.add(word.substring(0, word.length() - 1));

				//duplicate string
				v.add(word.concat(word));

				//ncapitalize the string
				temp = word.substring(1, word.length()).toUpperCase();
				v.add(((char)((wordInt>96 && wordInt<123)? wordInt: wordInt + 32)) + temp);

				//ncapitlize the string and append char
				String ncap = ((char)((wordInt>96 && wordInt<123)? wordInt: wordInt + 32)) + temp;
				for (int i = 33; i < 127; i++) {
					v.add( ncap + ((char) i) );
					//System.out.println(ncap + ((char) i) );
				}

				// append char to word.
				for (int i = 33; i < 127; i++) {
					v.add(word + ((char) i));
				}
			
				//prepend char to word
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + word);
				}

				//reverse string
				String rev = new String();
				for (int i = 0; i < word.length(); i++) {
					rev += word.charAt(word.length() - 1 - i);
				}
				v.add(rev);
				
				//add rev all lowercase
				v.add(rev.toLowerCase());

				//add rev all uppercase
				v.add(rev.toUpperCase());

				//reflect the string
				v.add(rev.concat(word));
				v.add(word.concat(rev));

				for (int i = 33; i < 127; i++) {
					v.add(rev + ((char) i) );
				}

				for (int i = 33; i < 127; i++) {
					v.add( ((char) i) + rev );
				}

				//prepend char to word and uppercase first letter
				temp = ((char)((wordInt>96 && wordInt<123)? wordInt - 32: wordInt)) + word.substring(1, word.length());
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + temp);
				}

				//append char to word and uppercase first letter
				for (int i = 33; i < 127; i++) {
					v.add(temp + ((char) i));
				}


				//prepend char to word and lowercase first letter
				temp = ((char)((wordInt>96 && wordInt<123)? wordInt: wordInt + 32)) + word.substring(1, word.length());
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + temp);
				}

				//append char to word and lowercase first letter
				for (int i = 33; i < 127; i++) {
					v.add(temp + ((char) i));
				}

				//remove first letter and append char
				for (int i = 33; i < 127; i++) {
					v.add(word.substring(1, word.length()) + ((char) i)); 
				}

				//remove first letter and prepend char
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + word.substring(1, word.length())); 
				}

				//remove last letter and prepend char
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + word.substring(0, word.length() - 1));
				}

				//remove last letter and append char
				for (int i = 33; i < 127; i++) {
					v.add(word.substring(0, word.length() - 1) + ((char) i));
				}

				//System.out.println(dictMangles.get(word));
				dictMangles.put(word, v);
				//dictDoubleMangles.put(word, vDouble);
				//System.out.println(word + " : " + dictMangles.get(word));
				//v.clear();
			}
		} catch (Exception E) {
			System.out.println("Caught Exception: " + E);}
	}

public static void addHashTable(String word){
		//String word = new String();
		String temp = new String();

		// try{
			//while((word = dictReader.readLine()) != null){
				int wordInt = (int)word.charAt(0);
				Vector<String> v = new Vector<String>();
				Vector<String> vDouble = new Vector<String>();
				// //temp = word;

				//just the word
				v.add(word);

				//all uppercase
				v.add(word.toUpperCase());

				//all lowercase
				v.add(word.toLowerCase());

				//uppercase first letter
				v.add( ((char)((wordInt>96 && wordInt<123)? wordInt - 32: wordInt)) + word.substring(1, word.length()) );

				//remove first letter
				v.add(word.substring(1, word.length()) );

				//remove last letter
				v.add(word.substring(0, word.length() - 1));

				//duplicate string
				v.add(word.concat(word));

				//ncapitalize the string
				temp = word.substring(1, word.length()).toUpperCase();
				v.add(((char)((wordInt>96 && wordInt<123)? wordInt: wordInt + 32)) + temp);

				//ncapitlize the string and append char
				String ncap = ((char)((wordInt>96 && wordInt<123)? wordInt: wordInt + 32)) + temp;
				for (int i = 33; i < 127; i++) {
					v.add( ncap + ((char) i) );
					//System.out.println(ncap + ((char) i) );
				}

				// append char to word.
				for (int i = 33; i < 127; i++) {
					v.add(word + ((char) i));
				}
			
				//prepend char to word
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + word);
				}
				
				//reverse string
				String rev = new String();
				for (int i = 0; i < word.length(); i++) {
					rev += word.charAt(word.length() - 1 - i);
				}
				v.add(rev);

				//add rev all lowercase
				v.add(rev.toLowerCase());

				//add rev all uppercase
				v.add(rev.toUpperCase());

				//reflect the string
				v.add(rev.concat(word));
				v.add(word.concat(rev));

				//prepend char to word and uppercase first letter
				temp = ((char)((wordInt>96 && wordInt<123)? wordInt - 32: wordInt)) + word.substring(1, word.length());
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + temp);
				}

				//append char to word and uppercase first letter
				for (int i = 33; i < 127; i++) {
					v.add(temp + ((char) i));
				}


				//prepend char to word and lowercase first letter
				temp = ((char)((wordInt>96 && wordInt<123)? wordInt: wordInt + 32)) + word.substring(1, word.length());
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + temp);
				}

				//append char to word and lowercase first letter
				temp = ((char)((wordInt>96 && wordInt<123)? wordInt: wordInt + 32)) + word.substring(1, word.length());
				for (int i = 33; i < 127; i++) {
					v.add(temp + ((char) i));
				}

				//append char to word and lowercase first letter
				for (int i = 33; i < 127; i++) {
					v.add(temp + ((char) i));
				}

				//remove first letter and append char
				for (int i = 33; i < 127; i++) {
					v.add(word.substring(1, word.length()) + ((char) i)); 
				}

				//remove first letter and prepend char
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + word.substring(1, word.length())); 
				}

				//remove last letter and prepend char
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + word.substring(0, word.length() - 1));
				}

				//remove last letter and append char
				for (int i = 33; i < 127; i++) {
					v.add(word.substring(0, word.length() - 1) + ((char) i));
				}

				//System.out.println(dictMangles.get(word));
				dictMangles.put(word, v);
				//dictDoubleMangles.put(word, vDouble);
				//System.out.println(word + " : " + dictMangles.get(word));
				//v.clear();
			// }
		// } catch (Exception E) {
		// 	System.out.println("Caught Exception: " + E);}
	}



}