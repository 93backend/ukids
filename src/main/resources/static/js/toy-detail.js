//대여일 지정
  var today = new Date();
  var tmrw = new Date(today.setDate(today.getDate()+2));
  $("#start-date").flatpickr({
    monthSelectorType : 'static',
    locale: "ko",                            
    minDate: tmrw,
    dateFormat: "Y-m-d",
  });


function changePickr(){
  var date = new Date($("#start-date").val());
  date.setDate(date.getDate() + 1);
  $("#end-date").flatpickr({
    monthSelectorType : 'static',
    minDate : date,
    locale: "ko",  
    dateFormat: "Y-m-d"
  });
}

$("#end-date").flatpickr({
  monthSelectorType : 'static',
    locale: "ko",   
    minDate: tmrw,                  
    dateFormat: "Y-m-d",
    disable: [
      function(date) {
        // disable every multiple of 8
        return (date.getDate());
      }
    ]  
  });

function clickPickr(){
  if($('#start-date').val()==false){
    $("#end-date").flatpickr({
      monthSelectorType : 'static',
        locale: "ko",   
        minDate: tmrw,                  
        dateFormat: "Y-m-d",
        disable: [
          function(date) {
            // disable every multiple of 8
            return (date.getDate());
          }
        ]  
      });
      alert("대여 시작일을 선택해주세요.")
  }
}


//장바구니 
$( document ).ready( function() {
  $( '#cartbtn' ).click( function(e) {
    if($('#start-date').val() != ''&& $('#end-date').val() != ''){
      $( '.add_cart' ).fadeIn(1000).delay(1000).fadeOut(1000);
      e.preventDefault();
    }else{
      alert("대여일을 선택해주세요.")
      e.preventDefault();
    }
  } );
} );

//결제 
$( document ).ready( function() {
  $( '#rentbtn2' ).click( function(e) {
    if($('#start-date').val() != ''&& $('#end-date').val() != ''){
      $(location).attr("href", "pay.html")
      e.preventDefault();
    }else{
      alert("대여일을 선택해주세요.")
      e.preventDefault();
    }
  } );
} );


//상세 더 보기
document.addEventListener('DOMContentLoaded', function(){ //DOM 생성 후 이벤트 리스너 등록
  //더보기 버튼 이벤트 리스너
  document.querySelector('.toydetailbtn').addEventListener('click', function(e){
      
      let classList = document.querySelector('.detailinfo').classList; // 더보기 프레임의 클래스 정보 얻기
      let contentHeight = document.querySelector('.detailinfo > .content').offsetHeight; //컨텐츠 높이 얻기

      // 전체보기로
      if(classList.contains('more')){
          classList.remove('more');
      }

      //전체보기시 더보기 버튼 숨기기 & 감추기 버튼 표시
      if(!classList.contains('more')){
          e.target.classList.add('hide');
          document.querySelector('.toydetailclose').classList.remove('hide');          
      }
      
  });
});

document.querySelector('.toydetailclose').addEventListener('click', function(e){
  e.target.classList.add('hide');
  document.querySelector('.toydetailbtn').classList.remove('hide'); // 더보기 버튼 감춤
  document.querySelector('.detailinfo').classList.add('more'); // 초기 감춤 상태로 복귀
});



//댓글 삭제

$(document).ready(function(){
  $('.trashbtn').click(function(){
      $(this).closest('.reviewlist').remove();
  });
});


//리뷰 더 보기
//댓글 더 보기 누르면 댓글 전체 나옴

$(function () {
  isHidden = false;
  $('.reviewlist').slice(0, 6).show(); // 첨에 3개 보여줌
  if ($('.reviewlist:hidden').length == 0)  // 댓글이 없을때
  {
      $('#seeMore').attr('disabled',true);
      document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;">댓글 없음</span>';
  }
  $('#seeMore').click(function (e) {  //댓글더보기 클릭시 
      if (isHidden == true)
      {
          document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;">댓글 더 보기</span>';
          $('.reviewlist').slice(6, $(".reviewlist").length).hide();
      } else
      {
          document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;"> 댓글 감추기 </span>';
          $('.reviewlist').show();
      }
      isHidden = !isHidden;
  });
});

//리스트 3개씩 나눠져서 나옴
// $(function(){
//   $(".reviewlist").slice(0, 6).show(); // 초기갯수
//   if ($('.reviewlist:hidden').length == 0)
//   {
//       $('#seeMore').attr('disabled',true);
//       document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;">댓글 없음</span>';
//   }
//   $("#seeMore").click(function(e){ 
//       e.preventDefault();
//       if($(".reviewlist:hidden").length == 0){ // 댓글감추기를 누르면 마지막 댓글 노출 후 댓글 감춘 후 초기화
//         document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;">댓글 더 보기</span>';
//         $('.reviewlist').slice(6, $(".reviewlist").length).hide();
//       }else if($(".reviewlist:hidden").length < 7){ //댓글 리스트가 마지막 전일 때 버튼 변경 
//         $(".reviewlist:hidden").slice(0, 6).show(); 
//         document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;"> 댓글 감추기 </span>';
//       }else{
//         $(".reviewlist:hidden").slice(0, 6).show(); //댓글 리스트 3개씩 
//       }
//   });
// });


//글자수 세기
$(function () {
  $('.reWrite').keydown(function () {
      var inputTextLength = $(this).val().length;//글자수
      if (inputTextLength > 1000)
      {
          $(this).val($(this).val().substring(0, 1000));
      }
      var inputPossible = 1000 - ($(this).val().length);
      $('.textLength').html(inputPossible);
  });
});
