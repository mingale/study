package mvc.vo;

public class Bespeak {

	private String order_num;
	private String id;
	private String name;
	private String phone;
	private String address;
	private String no;
	private int count;
	private int price;
	private String etc;
	private String bank;
	private int account;
	private int order_state;

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getOrder_state() {
		return order_state;
	}

	public void setOrder_state(int order_state) {
		this.order_state = order_state;
	}

	public String getState() {
		String state = "";
		switch (order_state) {
		case 0:
			state = "결제 확인 중";
			break;
		case 1:
			state = "배송 준비 중";
			break;
		case 2:
			state = "배송 중";
			break;
		case 3:
			state = "배송 완료";
			break;
		case 7:
			state = "환불 불가";
			break;
		case 8:
			state = "환불 요청 중";
			break;
		case 9:
			state = "환불 완료";
			break;
		default:
			state = "오류";
		}
		return state;
	}

	private String nos = "";

	public String getNos() {
		return nos;
	}

	public void setNos(String nos) {
		this.nos = nos;
	}
}