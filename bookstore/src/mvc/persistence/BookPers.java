package mvc.persistence;

import java.util.ArrayList;
import java.util.Map;

import mvc.vo.Book;
import mvc.vo.BookSub;
import mvc.vo.RecentBook;

public interface BookPers {
/*
 * ���� ��� : ���� �ʿ� > ArrayList
 * �ֹ� ��� : ���� ���ʿ�? > Map
 */
	//���� �˻�
	public ArrayList<Book> bookSearch(String search, int start, int end);
	
	//���� �˻� �����
	public int bookSearchCount(String search);
	
	//��� ���� ���
	public ArrayList<Book> getAllBook();
	
	//�ش� ���� ����
	public Book getBookInfo(String no);
	
	//�ش� ���� �ΰ� ����
	public BookSub getBookSubInfo(String no);

	//�ش� ���� ��ȸ�� ����
	public int bookViewsUpdate(String no);
	
	//�ֽ� ���� ���
	public ArrayList<Book> getNewBook(int start, int end);
	
	//����Ʈ����(�α�) ���� ��� : �Ǹŷ� ���� > ��ȸ�� ����
	public ArrayList<Book> getBestBook(int start, int end);
	
	//��õ ���� ���
	public ArrayList<Book> getGoodTagBook(int start, int end);
	
	//tag_main ��
	public int getTag_mainCount();
	
	//tag_main ���
	public ArrayList<BookSub> getTag_mainList();
	
	//tag_main ���� ���
	public ArrayList<Book> getTagMainBook(String tagMain, int start, int end);
	
	//���� ����
	public ArrayList<Book> getDomesticBook(int start, int end);
	
	//���� ���
	public ArrayList<Book> getBookList(int start, int end);
	
	//���� �ΰ� ���� ���
	public ArrayList<BookSub> getBookSubList(int start, int end);
	
	//����Ʈ(�α�) ���� ����
	public int getBestCount();
	
	//�Ű� ���� ����
	public int getNewCount();
	
	//��õ ���� ����
	public int getGoodCount();
	
	//tag_main ���� ����
	public int getTagMainCount(String tag);

	//���� ����
	public int getBookCnt();
	
	//���� ����
	public int bookUpdate(Book book);
	
	//�ֹ� ���� �Ϸ� �� ���� ���� ����
	public int orderBookCountMul(String order_num);
	
	//ȯ�� �Ϸ� �� ���� ���� ����
	public int refundBookCountAdd(String order_num);
	
	//���� ����
	public int bookDelete(String no);
	
	//���� �ټ� ����
	public int bookAllDelete(String[] nos);
	
	//���� �߰�
	public int bookInsert(Book book);
	
	//���� �ΰ� ���� ����
	public int bookSubUpdate(BookSub bSub);
	
	//���� �ΰ� ���� �߰�
	public int bookSubInsert(BookSub bSub);

	//�ֱ� �� ���� ����
	public ArrayList<RecentBook> recentUpdate(ArrayList<RecentBook> list, Book b);

	//no, title
	public Map<String, String> getNoTitle();
	
	//�� ���� �Ǹŷ�
	public int getSalesTotalCount();

	//�� �����
	public int getSalesTotalAmount();

	//�±׺� �Ǹŷ�
	public Map<String, Integer> getTagSalesTotal();
	
	//�±׺� ȯ�ҷ�
	public Map<String, Integer> getTagRefundTotal();
	
	//���� �����
	public Map<String, Integer> getYearSalesTotalAmount();
	
	//���� �Ǹŷ�
	public Map<String, Integer> getYearSalesTotalCount();
	
	//���� ȯ�Ҿ�
	public Map<String, Integer> getYearRefundTotalAmount();
	
	//�ְ� �±׺� �Ǹŷ�
	public Map<String, Integer> getTagWeeklyCount();
}
