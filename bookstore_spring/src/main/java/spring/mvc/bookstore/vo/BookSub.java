package spring.mvc.bookstore.vo;

import java.sql.Date;

public class BookSub {
	
	private String no;
	private String tag;
	private String tag_main;
	private String intro;
	private String list;
	private String pub_intro;
	private String review;
	private Date add_date;
	private int views;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTag_main() {
		return tag_main;
	}
	public void setTag_main(String tag_main) {
		this.tag_main = tag_main;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getPub_intro() {
		return pub_intro;
	}
	public void setPub_intro(String pub_intro) {
		this.pub_intro = pub_intro;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public Date getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getTagMainName() {
		String tagName = "";
		switch(Integer.parseInt(tag_main)) {
		case 1: tagName = "소설"; break;
		case 2: tagName = "장르소설"; break;
		case 3: tagName = "경제/경영"; break;
		case 4: tagName = "자기계발"; break;
		case 5: tagName = "시/에세이"; break;
		case 6: tagName = "인문"; break;
		case 7: tagName = "외국어"; break;
		case 8: tagName = "정치/사회"; break;
		case 9: tagName = "역사/문화"; break;
		case 10: tagName = "자연과학/공학"; break;
		case 11: tagName = "컴퓨터/인터넷"; break;
		case 12: tagName = "건강"; break;
		case 13: tagName = "가정/육아"; break;
		case 14: tagName = "요리"; break;
		case 15: tagName = "취미/실용/스포츠"; break;
		case 16: tagName = "여행"; break;
		case 17: tagName = "예술/대중문화"; break;
		case 18: tagName = "잡지"; break;
		case 19: tagName = "종교"; break;
		case 20: tagName = "취업/수험서"; break;
		case 21: tagName = "외국도서"; break;
		case 22: tagName = "청소년"; break;
		case 23: tagName = "유아"; break;
		case 24: tagName = "아동"; break;
		case 25: tagName = "만화"; break;
		case 26: tagName = "eBook"; break;
		case 27: tagName = "e-오디오북"; break;
		}
		return tagName;
	}
}
