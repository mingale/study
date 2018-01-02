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
		case 1: tagName = "�Ҽ�"; break;
		case 2: tagName = "�帣�Ҽ�"; break;
		case 3: tagName = "����/�濵"; break;
		case 4: tagName = "�ڱ���"; break;
		case 5: tagName = "��/������"; break;
		case 6: tagName = "�ι�"; break;
		case 7: tagName = "�ܱ���"; break;
		case 8: tagName = "��ġ/��ȸ"; break;
		case 9: tagName = "����/��ȭ"; break;
		case 10: tagName = "�ڿ�����/����"; break;
		case 11: tagName = "��ǻ��/���ͳ�"; break;
		case 12: tagName = "�ǰ�"; break;
		case 13: tagName = "����/����"; break;
		case 14: tagName = "�丮"; break;
		case 15: tagName = "���/�ǿ�/������"; break;
		case 16: tagName = "����"; break;
		case 17: tagName = "����/���߹�ȭ"; break;
		case 18: tagName = "����"; break;
		case 19: tagName = "����"; break;
		case 20: tagName = "���/���輭"; break;
		case 21: tagName = "�ܱ�����"; break;
		case 22: tagName = "û�ҳ�"; break;
		case 23: tagName = "����"; break;
		case 24: tagName = "�Ƶ�"; break;
		case 25: tagName = "��ȭ"; break;
		case 26: tagName = "eBook"; break;
		case 27: tagName = "e-�������"; break;
		}
		return tagName;
	}
}
