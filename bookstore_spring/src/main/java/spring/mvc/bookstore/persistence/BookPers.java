package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Book;
import spring.mvc.bookstore.vo.BookSub;
import spring.mvc.bookstore.vo.Notice;
import spring.mvc.bookstore.vo.RecentBook;
import spring.mvc.bookstore.vo.StringInt;

public interface BookPers {
/*
 * ���� ��� : ���� �ʿ� > ArrayList
 * �ֹ� ��� : ���� ���ʿ�? > Map
 */
	// �˻��� ����
	public ArrayList<String> getKeywordSuggest(String keyword);
	
	//���� �˻�
	public ArrayList<Book> bookSearch(Map<String, Object> map);
	
	//���� �˻� �����
	public int bookSearchCount(String keyword);
	
	//��� ���� ���
	public ArrayList<Book> getAllBook();
	
	//�ش� ���� ����
	public Book getBookInfo(String no);
	
	//�ش� ���� ��ȸ�� ����
	public int bookViewsUpdate(String no);
	
	//�ֽ� ���� ���
	public ArrayList<Book> getNewBook(Map<String, Object> map);
	
	//����Ʈ����(�α�) ���� ��� : �Ǹŷ� ���� > ��ȸ�� ����
	public ArrayList<Book> getBestBook(Map<String, Object> map);
	
	//��õ ���� ���
	public ArrayList<Book> getGoodTagBook(Map<String, Object> map);
	
	//tag_main ��
	public int getTag_mainCount();
	
	//tag_main ���
	public ArrayList<BookSub> getTag_mainList();
	
	//tag_main ���� ���
	public ArrayList<Book> getTagMainBook(Map<String, Object> map);
	
	//���� ����
	public ArrayList<Book> getDomesticBook(Map<String, Object> map);
	
	//���� ���
	public ArrayList<Book> getBookList(Map<String, Object> map);
	
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
	
	//���� ���� ����-1) �ֹ��� ���� = ��� - �ֹ�����
	public ArrayList<Bespeak> getOrderBookCount(String order_num);
	//public Map<String, Object> getOrderNoCount(String order_num);
	
	//���� ���� ����-2) �ֹ� ���� �Ϸ� �� ���� ���� ����
	public int orderBookCountMul(Map<String, Object> map);
	
	//ȯ�� �Ϸ� �� ���� ���� ����
	public int refundBookCountAdd(Map<String, Object> map);
	
	//���� ����
	public int bookDelete(String no);
	
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
	public ArrayList<StringInt> getTagSalesTotal();
	
	//�±׺� ȯ�ҷ�
	public ArrayList<StringInt> getTagRefundTotal();
	
	//���� �����
	public ArrayList<StringInt> getYearSalesTotalAmount();
	
	//���� �Ǹŷ�
	public ArrayList<StringInt> getYearSalesTotalCount();
	
	//���� ȯ�Ҿ�
	public ArrayList<StringInt> getYearRefundTotalAmount();

	//�ְ� �±׺� �Ǹŷ�-1
	public ArrayList<StringInt> getTagWeeklyCount();
	
	//�ְ� �±׺� �Ǹŷ�-2
	public Map<String, Integer> getTagWeeklyCountDistinct();
	
}
