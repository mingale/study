package spring.mvc.bookstore.vo;

import java.sql.Date;
import java.util.ArrayList;

public class Notice {
	private String idx;
	private String title;
	private String content;
	private String id;
	private Date add_date;
	private ArrayList<NoticeComment> noticeComment;

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAdd_date(Date date) {
		this.add_date = date;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public ArrayList<NoticeComment> getNoticeComment() {
		return noticeComment;
	}

	public void setNoticeComment(ArrayList<NoticeComment> noticeComment) {
		this.noticeComment = noticeComment;
	}
	
	
}
