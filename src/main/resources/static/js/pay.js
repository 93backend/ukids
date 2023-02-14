//이메일
$( "#email3" ).change(function(){
    $("#email2").val( $("#email3").val() );
});

$('#email3').change(function(){
  $("#email3 option:selected").each(function () {
   
   if($(this).val()== '1'){ //직접입력일 경우
      $("#email2").val('');                        //값 초기화
      $("#email2").attr("disabled",false); //활성화
   }else{ //직접입력이 아닐경우
      $("#email2").val($(this).text());      //선택값 입력
      $("#email2").attr("disabled",true); //비활성화
   }
  });
});

//배송지 체크박스
$(document).ready(function() {
  $('input[type="checkbox"][name="onlyone"]').click(function(){  
    if($(this).prop('checked')){  
      $('input[type="checkbox"][name="onlyone"]').prop('checked',false); 
      $(this).prop('checked',true);  
    }
  });
});

//주문자정보와 동일시
function Copy() {
  if ($('#copy').is(':checked')){
    $('#copyName').val($('#name').val());
    $('#copyphone1').val($('#phone1').val());
    $('#copyphone2').val($('#phone2').val());
    $('#copyphone3').val($('#phone3').val());
  }else {
    $('#copyName').val('');
    $('#copyphone1').val('');
    $('#copyphone2').val('');
    $('#copyphone3').val('');
  }
}
function newIn(){
  $('#copyName').val('');
  $('#copyphone1').val('');
  $('#copyphone2').val('');
  $('#copyphone3').val('');
}


//배송요청사항 직접입력
$(document).ready(function() {
  $('#validationCustom03').change(function() {
    var result = $('#validationCustom03 option:selected').val();
    if (result == '1') {
      $('.memo').show();
    } else if(result == $('.memo').val()){
	  $('.memo').show();
	}else{
      $('.memo').hide();
		
    }
  }); 
}); 


// 주문자 동의 체크박스
$(document).ready(function() {
  $("#selectAll").click(function() {
    if($("#selectAll").is(":checked")) $("input[name=terms]").prop("checked", true);
    else $("input[name=terms]").prop("checked", false);
  });

  $("input[name=terms]").click(function() {
    var total = $("input[name=terms]").length;
    var checked = $("input[name=terms]:checked").length;

    if(total != checked) $("#selectAll").prop("checked", false);
    else $("#selectAll").prop("checked", true); 
  });
});

// //우편번호
function postcode() {
  new daum.Postcode({
      oncomplete: function(data) {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          var addr = ''; // 주소 변수
          var extraAddr = ''; // 참고항목 변수

          //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
              addr = data.roadAddress;
          } else { // 사용자가 지번 주소를 선택했을 경우(J)
              addr = data.jibunAddress;
          }

          // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
          if(data.userSelectedType === 'R'){
              // 법정동명이 있을 경우 추가한다. (법정리는 제외)
              // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
              if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                  extraAddr += data.bname;
              }
              // 건물명이 있고, 공동주택일 경우 추가한다.
              if(data.buildingName !== '' && data.apartment === 'Y'){
                  extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
              }

          }

          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          document.getElementById("ka_postcode").value = data.zonecode;
          document.getElementById("ka_address").value = addr;
          // 커서를 상세주소 필드로 이동한다.
          document.getElementById("ka_detailAddress").focus();
      }
  }).open();
}



//결제

/*var clientKey = 'test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq'
var tossPayments = TossPayments(clientKey) // 클라이언트 키로 초기화하기

var button = document.getElementById('payment-button') // 결제하기 버튼

function checkForm(){  
  // console.log('daafs');
  if($('#cardbtn').is(':checked')){

    tossPayments.requestPayment('카드', { // 결제 수단 파라미터
        // 결제 정보 파라미터
        amount: 2800,
        orderId: 'nCC9h5aZXtS4TA4hkNIX8',
        orderName: '페어리루비즈아트 18000 비즈놀이 비즈공예 유아장난감외 1건',
        customerName: '유키즈',
        successUrl: 'http://localhost:8080/success',
        failUrl: 'http://localhost:8080/fail',
      })
      .catch(function (error) {
        if (error.code === 'USER_CANCEL') {
          // 결제 고객이 결제창을 닫았을 때 에러 처리
        } else if (error.code === 'INVALID_CARD_COMPANY') {
          // 유효하지 않은 카드 코드에 대한 에러 처리
        }
      })
  }
  return false;
}*/
