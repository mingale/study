package spring.mvc.bookstore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileHandlerImpl {

	public String execute(HttpServletRequest req, HttpServletResponse res) {
		String imageFile = "";
		/*String fileName = "imgFile";
		
		MultipartRequest mr = null;
		
		int maxSize = 10 * 1024 * 1024; //���ε� ���� �ִ� ������ (10*1024*1024=10MB)
		String saveDir = req.getRealPath("/images/book"); //�ӽ� ���� ���� ��ġ - ������ ���
		String realDir = "C:\\Dev\\workspace\\bookstore\\WebContent\\images\\book"; //���ε� ��ġ - �������� ���
		String encType = "UTF-8";
		
		
		try {
			
			 * new DefaultFileRenamePolicy() : �ߺ��� ���ϸ��� ���� ��� �ڵ����� ���ϸ��� �����Ѵ�.
			 * ex) filename.png�� �̹� ������ ��� filename1.png
			 
			mr = new MultipartRequest(req, saveDir, maxSize, encType, new DefaultFileRenamePolicy());
			FileInputStream fis = new FileInputStream(saveDir + mr.getFilesystemName(fileName)); //�ӽ� ����
			FileOutputStream fos = new FileOutputStream(realDir + mr.getFilesystemName(fileName));
			int data = 0;
			
			//�ӽ� ������ ������ ������ ��η� ����
			while((data = fis.read()) != -1) {
				fos.write(data);
			}
			fis.close();
			fos.close();
			
			imageFile = mr.getFilesystemName(fileName);
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		return imageFile;
	}
}
