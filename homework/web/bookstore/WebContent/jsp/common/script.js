var id_msg = "아이디를 입력하세요.";
var pwd_msg = "비밀번호를 입력하세요.";
var repwd_msg = "비밀번호 재확인을 입력하세요.";
var pwdCheck_msg = "비밀번호가 일치하지 않습니다.";
var name_msg = "이름을 입력하세요.";
var email_msg = "이메일을 입력하세요.";
var add_msg = "주소를 입력하세요.";

var title_msg = "도서명을 입력하세요.";
var author_msg = "저자를 입력하세요.";
var publisher_msg = "출판사를 입력하세요";
var price_msg = "가격을 입력하세요.";
var count_msg = "수량을 입력하세요.";

var price_chk = "가격을 0 이상 입력하세요.";
var count_chk = "수량을 0 이상 입력하세요.";

var deleteConfirm = "정말로 주문을 취소하시겠습니까?\n주문 취소 시 철회가 불가능합니다.";
var refundConfirm = "정말로 환불 신청하시겠습니까?\n환불 신청 시 철회가 불가능합니다.";

var updateError = "수정 작업을 실패했습니다.\n다시 시도하세요.";
var deleteError = "삭제 작업을 실패했습니다.\n다시 시도하세요.";

var inputError = "잘못된 입력입니다.";
var errorMsg = "작업을 실패했습니다.\n잠시 후에 다시 시도하세요.";

//에러 메시지
function errorAlert(msg) {
	alert(msg);
	window.history.back();
}

// SignIn
function signInFocus() {
	document.signInForm.id.focus();
}

//로그인 입력창 확인
function signInCheck() {
	if(!document.signInForm.id.value) {
		document.signInForm.id.focus();
		alert(id_msg);
		return false;
	} else if(!document.signInForm.pwd.value) {
		document.signInForm.pwd.focus();
		alert(pwd_msg);
		return false;
	}
}

// Join
var joinFlg = false;
function joinAll() {
	joinFlg = !joinFlg;
	for(var i in document.joinForm.agree) {
		document.joinForm.agree[i].checked = joinFlg;
	}
}

//약관동의 확인
function joinCheck() {
	if(!document.joinForm.allAgree.checked) {
		if(!document.joinForm.agree[0].checked) {
			alert("이용약관을 동의해주세요");
			document.joinForm.agree[0].focus();
			return false;
		} else if(!document.joinForm.agree[1].checked) {
			alert("개인정보 수입 및 이용에 대한 안내 약관을 동의해주세요.");
			document.joinForm.agree[1].focus();
			return false;
		}
	}
}

// SignUp
function signUpFocus() {
	if(!document.signUpForm.id.value) {
		document.signUpForm.id.focus();
	} else {
		document.signUpForm.pwd.focus();
	}
}

//회원가입 입력창 확인
function signUpCheck() {
	if(!document.signUpForm.id.value) {
		alert(id_msg);
		document.signUpForm.id.focus();
		return false;
		
	} else if(!document.signUpForm.pwd.value) {
		alert(pwd_msg);
		document.signUpForm.pwd.focus();
		return false;
		
	} else if(!document.signUpForm.repwd.value) {
		alert(repwd_msg);
		document.signUpForm.repwd.focus();
		return false;
		
	} else if(document.signUpForm.pwd.value != document.signUpForm.repwd.value) {
		alert(pwdCheck_msg);
		document.signUpForm.repwd.focus();
		return false;
		
	} else if(!document.signUpForm.name.value) {
		alert(name_msg);
		document.signUpForm.name.focus();
		return false;

	} else if(!document.signUpForm.email.value) {
		alert(email_msg);
		document.signUpForm.email.focus();
		return false;
		
	}else if(!document.signUpForm.address.value) {
		alert(add_msg);
		document.signUpForm.address.focus();
		return false;
	} 
}

//아이디 중복 확인
function confirmId() {
	if(document.signUpForm.id.value) {
		window.location="/bookstore/confirmId.do?id=" + document.signUpForm.id.value;
	}
}

//핸드폰 번호 확인
function confirmPh() {
	if(document.signUpForm.phone.value) {
		var reg = /^(010|011)-?[0-9]{3,4}-?[0-9]{4}$/;
		if(!reg.test(document.signUpForm.phone.value)) {
			alert("잘못된 형식입니다.");
		}
	}
}

// MyPagePwd
function myPagePwdFocus() {
	document.confirmPwdForm.pwd.focus();
}

// MyPagePwd 비밀번호 확인
function pwdCheck() {
	if(!document.confirmPwdForm.pwd.value) {
		alert(pwd_msg);
		document.confirmPwdForm.pwd.focus();
		return false;
	}
}

//********재고관리
//수정
function updateCheck() {
	if(!document.stockUpdateForm.title.value) {
		alert(title_msg);
		document.stockUpdateForm.title.focus();
		return false;
	} else if(!document.stockUpdateForm.author.value) {
		alert(author_msg);
		document.stockUpdateForm.author.focus();
		return false;
	} else if(!document.stockUpdateForm.publisher.value) {
		alert(publisher_msg);
		document.stockUpdateForm.publisher.focus();
		return false;
	} else if(!document.stockUpdateForm.price.value) {
		alert(price_msg);
		document.stockUpdateForm.price.focus();
		return false;
	} else if(!document.stockUpdateForm.count.value) {
		alert(count_msg);
		document.stockUpdateForm.count.focus();
		return false;
	} else if(document.stockUpdateForm.price.value < 0) {
		alert(price_chk);
		document.stockUpdateForm.price.focus();
		return false;
	} else if(document.stockUpdateForm.count.value < 0) {
		alert(count_chk);
		document.stockUpdateForm.count.focus();
		return false;
	}
}

var stockFlg = false;
function stockAll() {
	stockFlg = !stockFlg;
	for(var i in document.stockForm.list) {
		document.stockForm.list[i].checked = stockFlg;
	}
}

//재고관리 다수 삭제
function stockAllDelete() {
	if(confirm('정말 삭제하시겠습니까?')) {
		var nos = new Array();
		
		for(var i in document.stockForm.no) {
			if(document.stockForm.list[i].checked) {
				nos.push(document.stockForm.no[i].value);
			}
		}
		
		var no = nos.join(",");
		window.location="stockDelete.ho?chk=1&no=" + no;
	}
}


var cartflag = false;
function cartAllCheck() {
	cartflag = !cartflag;
	
	//도서가 하나 밖에 없으면 일반 변수. 배열X
	if(document.cartForm.cart_check.length == null) {
		document.cartForm.cart_check.checked = cartflag;
	} else {
		for(var i in document.cartForm.cart_check) {
			document.cartForm.cart_check[i].checked = cartflag;
		}
	}
}

function cartOrder() {
	var check = false;
	var arr = new Array();

	var carts;
	
	//도서 여러 개
	if(document.cartForm.cart_check.length != null) {
		for(var i in document.cartForm.cart_check) {
			if(document.cartForm.cart_check[i].checked) {
				if(document.cartForm.cart_check[i].value != null) {
					check = true;
					arr.push(document.cartForm.cart_check[i].value);
				}
			}
		}
		
		carts = arr.join(",");
	//도서 하나
	} else if(document.cartForm.cart_check.checked) {
		check = true;
		carts = document.cartForm.cart_check.value;
	}
	
	if(!check) {
		alert("구매할 도서를 선택해주세요.");
	} else {
		window.location="order.go?carts=" + carts;
	}
}

// 주문 페이지
function orderFocus() {
	document.orderForm.name1.focus();
}

//주문 페이지의 주문자 동일 checkBox
function orderText() {
	if(document.orderForm.nameAuto.checked == true) {
		document.orderForm.name.value = document.orderForm.name1.value;
		document.orderForm.phone.value = document.orderForm.phone1.value;
		document.orderForm.address.value = document.orderForm.address1.value;
	} else {
		document.orderForm.name.value = "";
		document.orderForm.phone.value = "";
		document.orderForm.address.value = "";
	}
}

//주문 페이지 입력창 확인
function orderCheck() {
	if(!document.orderForm.name.value) {
		alert("받는 이의 이름을 입력하세요.");
		document.orderForm.name.focus();
		return false;
		
	} else if(!document.orderForm.phone.value) {
		alert("받는 이의 연락처를 입력하세요.");
		document.orderForm.phone.focus();
		return false;
		
	} else if(!document.orderForm.address.value) {
		alert("받는 이의 주소를 입력하세요");
		document.orderForm.address.focus();
		return false;
		
	} else if(!document.orderForm.account.value) {
		alert("환불 계좌 번호를 입력하세요");
		document.orderForm.account.focus();
		return false;
		
	} else if(isNaN(document.orderForm.account.value)) { //isNaN() 숫자가 아닌 값을 찾는 함수
		alert("환불 계좌 번호는 숫자만 입력 가능합니다.");
		document.orderForm.account.focus();
		return false;
		
	} else if(!document.orderForm.name1.value) {
		alert("주문자의 이름 입력하세요.");
		document.orderForm.name.focus();
		return false;
		
	} else if(!document.orderForm.phone1.value) {
		alert("주문자의 연락처를 입력하세요.");
		document.orderForm.phone.focus();
		return false;
		
	} else if(!document.orderForm.address1.value) {
		alert("주문자의 주소를 입력하세요");
		document.orderForm.address.focus();
		return false;
	}	
}

//회원 주문 취소
function myOrderDelete(num) {
	if(confirm(deleteConfirm)) {
		window.location="myOrderDelete.go?order_num=" + num;
	}
}

//관리자 주문 삭제
function hostOrderDelete(num) {
	if(confirm(deleteConfirm))
		window.location="orderDelete.ho?order_num=" + num;
}

//관리주 주문목록의 배송 시작 버튼
function hostOrderShipping(num) {
	var winY = window.screenTop;	// 현재창의 y좌표
	var winX = window.screenLeft;	// 현재창의 x좌표
	//var winWidth = document.body.clientWidth;	// 현재창의 너비
	//var winHeight = document.body.clientHeight;	// 현재창의 높이
	
	window.open("orderShipping.ho?order_num=" + num, "ShippingStart", "menubar=no, top=" + winY + ", left=" + winX +" width=500, height=150, resizable=no");
}

//송장번호
function shippingFocus() {
	document.shippingForm.shippingNum.focus();
}

//송장번호 입력창 확인
function shippingCheck() {
	var reg= /^[0-9]{4}-?[0-9]{4}-?[0-9]{4}$/;
	if(!document.shippingForm.shippingNum.value) {
		alert("송장번호를 입력해주세요.");
		document.shippingForm.shippingNum.focus();
		return false;
	} else if(!reg.test(document.shippingForm.shippingNum.value)) {
		alert(inputError);
		return false;
	}
}

//이미지 업로드 미리보기. stockUpdate - <input type="file" name="imgFile" onchange="imageLoad(this);">
function imageLoad(value) {
	if(value.files && value.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) {
			document.getElementById("loadImg").setAttribute("src", e.target.result);
			document.stockUpdateForm.image.value = "null.jpg";
		}
		reader.readAsDataURL(value.files[0]);
	}
}

//회원관리 수정
//개별 수정해야 할 경우 form을 해당 ul마다 넣어야 함 -> 이 경우 checkBox 전체 선택이 불가능
//checkBox 전부 선택은 form이 전체ul를 감싸고 있어야 가능함.
function memUpdate(id) {
	var mId = "memo" + id;
	var rId = "rating" + id;
	var memo = document.getElementById(mId).value;
	var rating = document.getElementById(rId).value;
	
	window.location="memberUpdate.ho?chk=0&id=" + id + "&memo=" + memo + "&rating=" + rating;
}

//회원관리 선택
var memFlag = false;
function memAllCheck() {
	memFlag = !memFlag;
	for(var i in document.memUpForm.list)
		document.memUpForm.list[i].checked = memFlag;
}

//회원관리 다수 수정
function allMemberUpdate() {
	var ids = new Array();
	var memos = new Array();
	var ratings = new Array();
	
	for(var i in document.memUpForm.list) {
		if(document.memUpForm.list[i].checked) {
			ids.push(document.memUpForm.id[i].value);
			memos.push(document.memUpForm.memo[i].value);
			ratings.push(document.memUpForm.rating[i].value);
		}
	}
	
	var id = ids.join(",");
	var memo = memos.join(",");
	var rating = ratings.join(",");
	
	alert(id);
	window.location="memberUpdate.ho?chk=1&id=" + id + "&memo=" + memo + "&rating=" + rating;
}

//회원관리 다수 삭제
function allMemberDelete() {
	var ids = new Array();
	
	for(var i in document.memUpForm.list) {
		if(document.memUpForm.list[i].checked) {
			ids.push(document.memUpForm.id[i].value);
		}
	}
	
	var id = ids.join(",");
	
	window.location="memberDelete.ho?chk=1&id=" + id;
}

//메인 화면의 슬라이드쇼. onload를 통해 호출해야 정상 작동
var slideIndex = 0;
function showSlides() {
	var slides = document.getElementsByClassName("mySlides");
	
	for(var i = 0; i < slides.length; i += 1) {
		slides[i].style.display = "none"; //모든 슬라이드 이미지 숨기기
	}
	slideIndex += 1;
	if(slideIndex > slides.length) {
		slideIndex = 1; //처음으로 돌아가기
	}
	
	var slideContain = document.getElementsByClassName("slideContainer");
	switch(slideIndex) {
	case 1: slideContain[0].style.backgroundColor="rgb(47, 49, 89)"; break;
	case 2: slideContain[0].style.backgroundColor="rgb(5, 116, 174)"; break;
	case 3: slideContain[0].style.backgroundColor="rgb(1, 83, 100)"; break;
	case 4: slideContain[0].style.backgroundColor="rgb(89, 29, 26)"; break;
	}
	
	
	slides[slideIndex - 1].style.display = "block"; //하나만 이미지 보이기
	setTimeout(showSlides, 2000); //Change Image every 2seconds
}

var navF;
function navFocus(name) {
	navF = document.getElementsByClassName(name);
	navF[0].style.backgroundColor="steelblue";
}
function navOver(name) {
	var stockNav = document.getElementsByClassName(name);
	stockNav[0].style.backgroundColor="steelblue";
}
function navOut(name) {
	var stockNav = document.getElementsByClassName(name);
	if(navF != null) if(navF[0] != stockNav[0]) stockNav[0].style.backgroundColor="powderblue";
	
	//관리자 order,refund,result 외에서 사용시
	if(navF == null) stockNav[0].style.backgroundColor="powderblue"; 
}

//c:redirect로 이동하면 alert이 안뜬다.
/*
 * Parameter로 배열을 넘기나, 배열을 문자열로 연결하나 똑같이 빈값이 존재. ex) 값1,값2,,,,
 * 하지만 바로 사용할 수 없으므로 null 체크해가며 사용하거나
 * 문자열로 결합후 다시 해체
 */

/*
 * documents.getElementByClassName returns an array. You should access the element you want to style by using the brackets notation "[index]".
 * documents.getElementByClassName은 요소를 배열로 가져오므로, 반드시 인덱스를 사용해야 한다. 
 */
/*
 * html에서 값을 넘길 때 주의
 * error: <a onmouseover="navOver(a);> : javascript 안의 변수 호출
 * 정장 : <a onmouseover="navOver('a');> : 값 넘기기
 * 
 */