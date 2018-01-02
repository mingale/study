package service;

import java.util.HashMap;
import java.util.Map;

import domain.Book;

public class Test {
	static Map<Integer, Book> book = new HashMap<Integer, Book>();
	
	public static void main(String[] args) {
		book.put(1, new Book());
	}
}
