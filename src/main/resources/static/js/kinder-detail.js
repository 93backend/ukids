
  /* 첫번째 그래프(총 학급수) */
  window.onload = function () {
    pieChartDraw();
    pieChartDraw2();
    barChartDraw();
    pieChartDraw4();
    pieChartDraw5();
    document.getElementById('legend-div').innerHTML = window.pieChart.generateLegend();
    document.getElementById('legend-div2').innerHTML = window.pieChart2.generateLegend();
    document.getElementById('legend-div3').innerHTML = window.barChart.generateLegend();
    document.getElementById('legend-div4').innerHTML = window.pieChart4.generateLegend();
    document.getElementById('legend-div5').innerHTML = window.pieChart5.generateLegend();
    
}

let pieChartData = {
    labels: ['만 3세반', '만 4세반', '만 5세반'],
    datasets: [{
        data: [2, 2, 2],
        backgroundColor: ['rgb(255, 99, 132)',  'rgb(255, 205, 86)',  'rgb(54, 162, 235)'],
        datalabels: {
          labels: {
            value: {
              formatter: function(value, ctx) {
                var value = ctx.dataset.data[ctx.dataIndex];
                return value > 0 ? Math.round(value / (ctx.dataset.data[0] + ctx.dataset.data[1] + ctx.dataset.data[2]) * 100) + ' %' : null;
              },
              color: '#fff',
              font: {
                size: 18
              }
            }
          }
        }
    }] 
};


let pieChartDraw = function () {
    let ctx = document.getElementById('pieChartCanvas').getContext('2d');
    
    window.pieChart = new Chart(ctx, {
        type: 'pie',
        data: pieChartData,
        options: {
            responsive: false,
            legend: {
                display: false
            },
            legendCallback: customLegend
        },
    });
};

let customLegend = function (chart) {
    let ul = document.createElement('ul');
    let color = chart.data.datasets[0].backgroundColor;
    let sum = chart.data.datasets[0].data[0] + chart.data.datasets[0].data[1] + chart.data.datasets[0].data[2];

    chart.data.labels.forEach(function (label, index) {
        ul.innerHTML += `<li><span style="background-color: ${color[index]}; display: inline-block; width: 15px; height: 15px;"></span> ${label}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${chart.data.datasets[0].data[index]}개&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${Math.round((chart.data.datasets[0].data[index] / sum) * 100)}%</li>`;
    });

    return ul.outerHTML;
};


/* 두번째 그래프(총 아동수) */




let pieChartData2 = {
  labels: ['만 3세반', '만 4세반', '만 5세반'],
  datasets: [{
      data: [16, 21, 43],
      backgroundColor: ['rgb(255, 99, 132)',  'rgb(255, 205, 86)',  'rgb(54, 162, 235)'],
      datalabels: {
        labels: {
          value: {
            formatter: function(value, ctx) {
              var value = ctx.dataset.data[ctx.dataIndex];
              return value > 0 ? Math.round(value / (ctx.dataset.data[0] + ctx.dataset.data[1] + ctx.dataset.data[2]) * 100) + ' %' : null;
            },
            color: '#fff',
            font: {
              size: 18
            }
          }
        }
      }
  }] 
};


let pieChartDraw2 = function () {
  let ctx = document.getElementById('pieChartCanvas2').getContext('2d');
  
  window.pieChart2 = new Chart(ctx, {
      type: 'pie',
      data: pieChartData2,
      options: {
          responsive: false,
          legend: {
              display: false
          },
          legendCallback: customLegend2
      },
  });
};

let customLegend2 = function (chart) {
  let ul2 = document.createElement('ul');
  let color = chart.data.datasets[0].backgroundColor;
  let sum = chart.data.datasets[0].data[0] + chart.data.datasets[0].data[1] + chart.data.datasets[0].data[2];

  chart.data.labels.forEach(function (label, index) {
      ul2.innerHTML += `<li><span style="background-color: ${color[index]}; display: inline-block; width: 15px; height: 15px;"></span> ${label}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${chart.data.datasets[0].data[index]}명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${Math.round((chart.data.datasets[0].data[index] / sum) * 100)}%</li>`;
  });

  return ul2.outerHTML;
};

/* 
세번째 막대 그래프(교사당/학급당 아동수)
*/

let barChartData = {
  labels: ['교사당 아동수', '학급당 아동수'],
  datasets: [{
      data: [3, 4],
      backgroundColor: ['rgb(252, 3, 78)',  'rgb(255, 205, 86)'],
      datalabels: {
        labels: {
          display: false
        } 
      }
  }] 
};


let barChartDraw = function () {
  let ctx = document.getElementById('barChartCanvas').getContext('2d');
  
  window.barChart = new Chart(ctx, {
      type: 'bar',
      data: barChartData,
      options: {
          responsive: false,
          legend: {
              display: false
          },
          scales: {
            xAxes: [{
              display: false
            }],
            yAxes: [{
              display: false,
              ticks: {
                min: 0,
                stepSize: 2,
                fontSize: 14
              }
            }]
          },
          legendCallback: customLegend3
      },
  });
};

let customLegend3 = function (chart) {
  let ul = document.createElement('ul');
  let color = chart.data.datasets[0].backgroundColor;

  chart.data.labels.forEach(function (label, index) {
      ul.innerHTML += `<li><span style="background-color: ${color[index]}; display: inline-block; width: 15px; height: 15px;"></span> ${label}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3명</li>`;
  });

  return ul.outerHTML;
};

/* 4번째 그래프(교사현황) */

let pieChartData4 = {
  labels: ['수석교사', '정교사1급', '정교사2급', '준교사', '특수교사', '보건교사', '영양교사'],
  datasets: [{
      data: [0, 5, 2, 0, 1, 0, 0],
      backgroundColor: [
          'rgb(255, 99, 132)',  
          'rgb(255, 205, 86)',
          'rgb(83, 235, 211)',  
          'rgb(54, 162, 235)',
          'rgb(193, 153, 211)',
          'rgb(171, 223, 133)',
          'rgb(255, 249, 80)'],
      datalabels: {
        labels: {
          value: {
            formatter: function(value, ctx) {
              var value = ctx.dataset.data[ctx.dataIndex];
              return value > 0 ? Math.round(value / (ctx.dataset.data[0] + ctx.dataset.data[1] + ctx.dataset.data[2]) * 100) + ' %' : null;
            },
            color: '#fff',
            font: {
              size: 16
            }
          }
        }
      }
  }] 
};


let pieChartDraw4 = function () {
  let ctx = document.getElementById('pieChartCanvas4').getContext('2d');
  
  window.pieChart4 = new Chart(ctx, {
      type: 'doughnut',
      data: pieChartData4,
      options: {
          responsive: false,
          legend: {
              display: false
          },
          legendCallback: customLegend4
      },
  });
};

let customLegend4 = function (chart) {
  let ul = document.createElement('ul');
  let color = chart.data.datasets[0].backgroundColor;
  let sum = chart.data.datasets[0].data[0] + chart.data.datasets[0].data[1] + chart.data.datasets[0].data[2] +
            chart.data.datasets[0].data[3] + chart.data.datasets[0].data[4] + chart.data.datasets[0].data[5] + 
            chart.data.datasets[0].data[6];


  chart.data.labels.forEach(function (label, index) {
      let val = chart.data.datasets[0].data[index]
      if (val == 0) {
          ul.innerHTML += `<li style="color: gray;"><span style="background-color: gray; color: gray; opacity: 20%; display: inline-block; width: 15px; height: 15px;"></span> ${label}<div style="display: inline-block; width: 180px; text-align: left; position: absolute; right: 970px;">&emsp;&emsp;&emsp;&emsp;-&emsp;&emsp;&emsp;&emsp;-</div></li>`;
      } else {
          ul.innerHTML += `<li><span style="background-color: ${color[index]}; display: inline-block; width: 15px; height: 15px;"></span> ${label}<div style="display: inline-block; width: 180px; text-align: left; position: absolute; right: 970px;">&emsp;&emsp;&emsp;&nbsp;${val}명&emsp;&emsp;&emsp;${Math.round((chart.data.datasets[0].data[index] / sum) * 100)}%</div></li>`;
      }
      
  });

  return ul.outerHTML;
};

/* 5번째 그래프(현 기관 근속 연수) */

let pieChartData5 = {
  labels: ['1년 미만', '1년~2년 미만', '2년~4년 미만', '4년~6년 미만', '6년 이상'],
  datasets: [{
      data: [1, 1, 1, 3, 1],
      backgroundColor: [
          'rgb(255, 99, 132)',  
          'rgb(255, 205, 86)',
          'rgb(83, 235, 211)',  
          'rgb(54, 162, 235)',
          'rgb(193, 153, 211)'],
      datalabels: {
        labels: {
          value: {
            formatter: function(value, ctx) {
              var value = ctx.dataset.data[ctx.dataIndex];
              return value > 0 ? Math.round(value / (ctx.dataset.data[0] + ctx.dataset.data[1] + ctx.dataset.data[2] +
              ctx.dataset.data[3] + ctx.dataset.data[4]) * 100) + 
              ' %' : null;
            },
            color: '#fff',
            font: {
              size: 16
            }
          }
        }
      }
  }] 
};


let pieChartDraw5 = function () {
  let ctx = document.getElementById('pieChartCanvas5').getContext('2d');
  
  window.pieChart5 = new Chart(ctx, {
      type: 'doughnut',
      data: pieChartData5,
      options: {
          responsive: false,
          legend: {
              display: false
          },
          legendCallback: customLegend5
      },
  });
};

let customLegend5 = function (chart) {
  let ul = document.createElement('ul');
  let color = chart.data.datasets[0].backgroundColor;
  let sum = chart.data.datasets[0].data[0] + chart.data.datasets[0].data[1] + chart.data.datasets[0].data[2] +
  chart.data.datasets[0].data[3] + chart.data.datasets[0].data[4];

  chart.data.labels.forEach(function (label, index) {
      let val = chart.data.datasets[0].data[index]
      if (val == 0) {
          ul.innerHTML += `<li style="color: gray;"><span style="background-color: gray; color: gray; opacity: 20%; display: inline-block; width: 15px; height: 15px;"></span> ${label}<div style="display: inline-block; width: 180px; text-align: left; position: absolute; right: 370px;"">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;-&emsp;&emsp;&emsp;&emsp;-</div></li>`;
      } else {
          ul.innerHTML += `<li><span style="background-color: ${color[index]}; display: inline-block; width: 15px; height: 15px;"></span> ${label}<div style="display: inline-block; width: 150px; text-align: left; position: absolute; right: 370px;">&emsp;&emsp;&emsp;&nbsp;${val}명&emsp;&emsp;&emsp;${Math.round((chart.data.datasets[0].data[index] / sum) * 100)}%</div></li>`;
      }
      
  });

  return ul.outerHTML;
};

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















 
















