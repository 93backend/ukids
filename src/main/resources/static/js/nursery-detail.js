
  window.onload = function () {
    pieChartDraw();
    pieChartDraw2();
    barChartDraw();
    pieChartDraw4();
    barChartDraw2();
    document.getElementById('legend-div').innerHTML = window.pieChart.generateLegend();
    document.getElementById('legend-div2').innerHTML = window.pieChart2.generateLegend();
    document.getElementById('legend-div3').innerHTML = window.barChart.generateLegend();
    document.getElementById('legend-div4').innerHTML = window.pieChart4.generateLegend();
    document.getElementById('legend-div5').innerHTML = window.barChart2.generateLegend();
    
}

// 로그인 되어있지 않은 경우 기능 차단
function cantDo() {
	alert('로그인 후 이용가능합니다.');
}

// 해당 유치원 선생님이 아니면 채팅 X
function cantChat() {
	alert('해당 어린이집 선생님이 아닙니다.');
}

// 입소 신청 날짜 값 확인
function submitCheck() {
    var date = $("#hopeDate").val();
    if(date == "") {
		$("#date-notice").text(" * 희망날짜를 선택해주세요.")
        return false;
    }
    return true
};

//리뷰 더 보기
//댓글 더 보기 누르면 댓글 전체 나옴

$(function () {
  isHidden = false;
  $('.reviewlist').slice(0, 4).show(); // 첨에 3개 보여줌
  if ($('.reviewlist:hidden').length == 0)  // 댓글이 없을때
  {
      $('#seeMore').attr('disabled',true);
      document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2"></i><span>댓글 없음</span>';
  }
  $('#seeMore').click(function (e) {  //댓글더보기 클릭시 
      if (isHidden == true)
      {
          document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2"></i><span>댓글 더 보기</span>';
          $('.reviewlist').slice(4, $(".reviewlist").length).hide();
      } else
      {
          document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2"></i><span> 댓글 감추기 </span>';
          $('.reviewlist').show();
      }
      isHidden = !isHidden;
  });
});



