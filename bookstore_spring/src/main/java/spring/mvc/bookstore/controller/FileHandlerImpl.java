package spring.mvc.bookstore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileHandlerImpl {

	public String execute(HttpServletRequest req, HttpServletResponse res) {
		String imageFile = "";
		/*String fileName = "imgFile";
		
		MultipartRequest mr = null;
		
		int maxSize = 10 * 1024 * 1024; //업로드 파일 최대 사이즈 (10*1024*1024=10MB)
		String saveDir = req.getRealPath("/images/book"); //임시 파일 저장 위치 - 논리적인 경로
		String realDir = "C:\\Dev\\workspace\\bookstore\\WebContent\\images\\book"; //업로드 위치 - 물리적인 경로
		String encType = "UTF-8";
		
		
		try {
			
			 * new DefaultFileRenamePolicy() : 중복된 파일명이 있을 경우 자동으로 파일명을 변경한다.
			 * ex) filename.png가 이미 존재할 경우 filename1.png
			 
			mr = new MultipartRequest(req, saveDir, maxSize, encType, new DefaultFileRenamePolicy());
			FileInputStream fis = new FileInputStream(saveDir + mr.getFilesystemName(fileName)); //임시 저장
			FileOutputStream fos = new FileOutputStream(realDir + mr.getFilesystemName(fileName));
			int data = 0;
			
			//임시 저장한 파일을 물리적 경로로 복사
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
