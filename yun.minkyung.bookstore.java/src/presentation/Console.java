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
	
	// Console로 입력 받기 
	public static String input() {
		String str = null;
		reader =new BufferedReader(new InputStreamReader(System.in));
	
		try {
			str = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\t입력오류");
		}
		return str;
	}
	
	// 메모장에 쓰기
	public static PrintWriter output() {
		try {
			out = new PrintWriter("C:\\Dev\\file\\_bookstore_out.txt" );
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
}
