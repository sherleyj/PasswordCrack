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
			
			String word = new String();
			while((word = dictReader.readLine()) != null){
				Vector<String> v = new Vector<String>();

				// append char to word.
				for (int i = 33; i < 127; i++) {
					v.add(word + ((char) i));
				}
			
				//prepend char to word
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + word);
				}

				//all uppercase
				v.add(word.toUpperCase());
				

				//uppercase first letter
				int wordInt = word.chatAt(0);
				v.add(((char) i) + word);

				//remove first letter
				v.add(word.substring(1, word.length()) );

				//remove last letter
				v.add(word.substring(0, word.length() - 1));

				//prepend char to word and uppercase first letter
				for (int i = 33; i < 127; i++) {
					v.add(((char) i) + word);
				}

				//System.out.println(dictMangles.get(word));
				dictMangles.put(word, v);
				v.clear();
			}

			String psw_line = new String();
			while((psw_line = pswReader.readLine()) != null){
				System.out.println(psw_line);
				String[] user = psw_line.split(":");
				
				psw_cracked = crack(user);
				//System.out.println(psw_cracked);
			}

		} catch (Exception E) {
			System.out.println("Caught Exception: " + E);}
		
	}


	public static String crack(String[] user){
		String salt = user[1].substring(0,2);
		String pswE = user[1].substring(2);
		String[] name = user[4].split(" ");
		// System.out.println(name[0] + name[1]);
		// System.out.println(user[1]);
		boolean match = false;
		String crackedPsw = null;
		int comp;

		// try{
		// 	String word = new String();
		// 	while (((word = dictReader.readLine()) != null)){
				
		// 		String wordE = jcrypt.crypt(salt, word);
		// 		if( (comp = wordE.compareTo(pswE)) == 0) {
		// 			match = true;
		// 			crackedPsw = word;
		// 			break;
		// 		}
		// 		String temp = new String();
  //     			if( (temp = pre_append(word, salt, pswE)) != null){
  //     				match = true;
		// 			crackedPsw = word;
		// 			break;
  //     			}
		// 	}
		// } catch (Exception E) {
		// 	System.out.println("Caught Exception: " + E);}

		return crackedPsw;
	}

	public static String pre_append (String word, String salt, String pswE){
		String pswCracked = null;
		String temp = new String();

		return pswCracked;
	}


}