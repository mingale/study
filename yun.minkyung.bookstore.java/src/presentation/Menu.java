package presentation;

public interface Menu {

	public void commonMenu(int menu);	// �޴� ����

	public void loginMenu();	   // �α��� �޴�
	public void hostMenu();	       // ������ �޴�
	public void hostStockMenu();   // ������ ��� �޴�
	public void hostOrderMenu();   // ������ �ֹ����� �޴�
	
	public void guestMenu();	   // �� �޴�
	public void guestCartMenu();   // �� ��ٱ���
}
