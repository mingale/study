package mvc.controller;

import java.util.Random;

public class EmailCheckHandler {
	
	//인증키 랜덤 문자
	public String randomKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();
		
		for(int i = 0; i < 6; i += 1) {
			int rIndex = rnd.nextInt(2);
			
			switch(rIndex) {
			case 0: key.append((char) ((int)(rnd.nextInt(26)) + 65)); break; //A-Z
			case 1: key.append(rnd.nextInt(10)); break; //0-9
			}
		}
		
		return key.toString(); //StringBuffer -> String
	}
	
}
