
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
  $('.reviewlist').slice(0, 4).show(); // 첨에 3개 보여줌
  if ($('.reviewlist:hidden').length == 0)  // 댓글이 없을때
  {
      $('#seeMore').attr('disabled',true);
      document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;">댓글 없음</span>';
  }
  $('#seeMore').click(function (e) {  //댓글더보기 클릭시 
      if (isHidden == true)
      {
          document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;">댓글 더 보기</span>';
          $('.reviewlist').slice(4, $(".reviewlist").length).hide();
      } else
      {
          document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;"> 댓글 감추기 </span>';
          $('.reviewlist').show();
      }
      isHidden = !isHidden;
  });
});


/* 1:1 채팅 모달 부분 */


// var ws;
// var memberId = "${sessionScope.member.memberId}";

// function connect() {
//   ws = new WebSocket('ws://localhost:80${path}/chat.do');
//   ws.onopen = function() {
//             console.log("웹소켓 연결 생성");
            
//     var msg = {
//                 type: "register",
//                 memberId: memberId
//             }
            
//             ws.send(JSON.stringify(msg));
//   };
  
//   ws.onerror = function(event){
//     console.log(event);
//   }
//   ws.onmessage = function(e) {
//     var msg = e.data;
//             var chat = $("#msgArea").val() + "\n" + msg;
//             $("#msgArea").val(chat);
//             const top = $('#msgArea').prop('scrollHeight');
//             $('#msgArea').scrollTop(top);
//   };
  
//   ws.onclose = function() {
//     console.log(ws);
//     console.log("연결종료");
//   };
// }
// connect();
    
//     $(function() {
//         $("#sendBtn").click(function() {
//             var chat = $("#chatMsg").val();
//             var msg = $("#msgArea").val();
//             $("#msgArea").val(msg);
//             $("#chatMsg").val("");
            
//             const top = $('#msgArea').prop('scrollHeight');
//             $('#msgArea').scrollTop(top);
            
//             var sendMsg = {
//               id : memberId,
//                 type: "chat",
//                 target: $("#target").val(),
//                 msg: chat
//             }
            
//             ws.send(JSON.stringify(sendMsg));
//         });
//     });


    /* 달력 관련 */


    /* 찜 관련, 비교 관련 */
let isHeartChecked = false;
let isCompareChecked = false;
// const wishButton = document.querySelector('#wishButton');

$(document).ready(function() {
$('#wishButton').click(function() {
  isHeartChecked = !isHeartChecked;

  if (isHeartChecked == false) {
    wishButton.innerHTML = '<i id="heart1" class="ci-heart"> 찜</i>';
  } else {
    wishButton.innerHTML = '<i id="heart1" class="ci-heart-filled"> 찜</i>';
  }
});

$('#compareButton').click(function() {
  isCompareChecked = !isCompareChecked;
  if (isCompareChecked == false) {
    compareButton.innerHTML = '<i id="compareIcon1" class="ci-compare"> 비교</i>';
  } else {
    compareButton.innerHTML = '<i id="compareIcon2" class="ci-compare fw-bolder" style="color:#fc6c93;"> 비교</i>';
  }

});

});















 
















