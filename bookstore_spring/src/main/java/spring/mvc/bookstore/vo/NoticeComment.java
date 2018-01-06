package spring.mvc.bookstore.vo;

import java.sql.Date;

public class NoticeComment {
	private String com_idx;
	private String notice_idx;
	private String writer_id;
	private String com_content;
	private int ref_com_idx;
	private int com_step;
	private Date com_add_date;

	public String getCom_idx() {
		return com_idx;
	}

	public void setCom_idx(String com_idx) {
		this.com_idx = com_idx;
	}

	public String getNotice_idx() {
		return notice_idx;
	}

	public void setNotice_idx(String notice_idx) {
		this.notice_idx = notice_idx;
	}

	public String getWriter_id() {
		return writer_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}

	public String getCom_content() {
		return com_content;
	}

	public void setCom_content(String content) {
		this.com_content = content;
	}

	public int getRef_com_idx() {
		return ref_com_idx;
	}

	public void setRef_com_idx(int ref_com_idx) {
		this.ref_com_idx = ref_com_idx;
	}

	public int getCom_step() {
		return com_step;
	}

	public void setCom_step(int com_step) {
		this.com_step = com_step;
	}

	public Date getCom_add_date() {
		return com_add_date;
	}

	public void setCom_add_date(Date com_add_date) {
		this.com_add_date = com_add_date;
	}

}
