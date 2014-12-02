import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;

public class PasswordCrack {

	public static void main(String args[]) throws IOException{

		try{
			InputStream dictionaryFile = new FileInputStream(args[0]);
			InputStream passwordsFile = new FileInputStream(args[1]);

			InputStreamReader dicStream = new InputStreamReader(dictionaryFile);
			InputStreamReader pwsStream = new InputStreamReader(passwordsFile); 

			BufferedReader dict = new BufferedReader(dicStream);
			BufferedReader pws = new BufferedReader(pwsStream);

			String pws_line = new String();
			while((pws_line = pws.readLine()) != null){
				System.out.println(pws_line);
				int index1 = pws_line.indexOf(":");
				int index2 = pws_line.indexOf(":", index1 + 1);
				String pws_i = pws_line.substring(index1 + 1, index2);
				System.out.println(pws_i);

			}



		} catch (Exception E) {
			System.out.println("Caught Exception: " + E);}
		
	}
}