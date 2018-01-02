package presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Console extends MenuImpl {
	public static PrintWriter out = null;
	
	private static BufferedReader reader;

	public Console() {
		loginMenu();
	}
	
	// Console�� �Է� �ޱ� 
	public static String input() {
		String str = null;
		reader =new BufferedReader(new InputStreamReader(System.in));
	
		try {
			str = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\t�Է¿���");
		}
		return str;
	}
	
	// �޸��忡 ����
	public static PrintWriter output() {
		try {
			out = new PrintWriter("C:\\Dev\\file\\_bookstore_out.txt" );
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
}
