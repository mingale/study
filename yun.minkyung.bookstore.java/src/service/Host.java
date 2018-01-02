package service;

import domain.Book;
import domain.Shelf;

public interface Host {

	public static final String ID = "host";
	public static final String PASSWORD = "host";
	
	public void bookList();
	public void bookAdd();
	public void bookUpdate();
	public void bookDel();
	
	public void orderList();
	public void orderConfirm();
	public void orderRefund();
	public void salesTotal();
}
