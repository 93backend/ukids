// 자녀 나이 체크박스 제한 관련 스크립트
$(document).ready(function() {
	$("input[type='checkbox']").on("click", function() {
		let count = $("input:checked[type='checkbox']").length;
		let limit = $("#account-childNum").val();
		if (count > limit) {
			$(this).prop("checked", false);
			//또는 this.checked=false;
			alert("선택한 자녀의 수 만큼 체크가 가능합니다.");
		}
	});
}); 

// 비밀번호 일치 관련
$(function() {
	$("#password2").blur((event) => {
		let pass1 = $("#password1").val();
		let pass2 = $(event.target).val();

		if (pass1.trim() != pass2.trim()) {
			alert("비밀번호가 일치하지 않습니다.");

			$("#password1").val("");
			$(event.target).val("");
			$("#password1").focus();
		}
	});
});


function submitCheck() {
	let name = $("#name").val();
	let password = $("#password1").val();
	let email = $("#account-email").val();
	let phone = $("#account-phone").val();
	let postCode = $("#addressPostcode").val();
	let address = $("#addressBasic").val();
	var regExp = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;
	let regExp2 = /(01[016789])([1-9]{1}[0-9]{2,3})([0-9]{4})$/;
	if (name == "") {
		swal("이름을 입력해 주세요.");
		return false;
	} else if (password == "") {
		swal(" 비밀번호를 입력해 주세요.");
		return false;
	} else if (email == "") {
		swal("이메일을 입력해 주세요.");
		return false;
	} else if (phone == "") {
		swal("연락처를 입력해 주세요.");
		return false;
	} else if (postCode == "") {
		swal("우편번호를 입력해 주세요.");
		return false;
	} else if (address == "") {
		swal("주소를 입력해 주세요.");
		return false;
	}
	
	if (password.length < 4 || password.length > 20) {
		swal("비밀번호는 4자 이상 20자 이하만 가능합니다.")
		return false;
	}
	
	if(!regExp.test(email)) {
		swal("올바른 이메일 형식이 아닙니다.");
		return false
	}
	
	if(regExp2.test(phone)) {
		swal("연락처는 -을 넣어서 입력해 주세요.");
		return false;
	}
	
	if(postCode.length != 5) {
		swal("우편번호가 올바르지 않습니다.")
		return false;
	}
	
	return true;
}
	
	
	




        