// 검색에서 슬라이더 버튼 클릭 시 해당 기관 슬라이더로 이동
const nsBtn = document.getElementById('ns-btn');
const nsSilder = document.querySelector('#ns-silder');
const kgBtn = document.getElementById('kg-btn');
const kgSilder = document.querySelector('#kg-silder');

nsBtn.addEventListener('click', () => {
  nsBtn.classList.add('act');
  nsSilder.classList.remove('d-none')
  kgBtn.classList.remove('act');
  kgSilder.classList.add('d-none')
});
kgBtn.addEventListener('click', () => {
  kgSilder.classList.remove('d-none')
  kgBtn.classList.add('act');
  nsSilder.classList.add('d-none')
  nsBtn.classList.remove('act');
});





/* ========== 카카오 지도 ========== */
// 검색 결과
var searchList = [
  {
    kind: '어',
    type: '사립(법인)',
    name: '대구광역시도시관리본부종합복지회관관리사무소한아름어린이집',
    address: '서울특별시 중구 덕수궁길 140 서울특별시 중구 덕수궁길 140',
    image: null,
  },
  {
    kind: '유',
    type: '공립(병설)',
    name: '(재)천주교수원교구유지재단데레사유치원',
    address: '서울특별시 관악구 관악로37길 20 서울특별시 관악구 관악로37길 20',
    image: null,
  },
  {
    kind: '어',
    type: '사립(법인)',
    name: '부산어린이집',
    address: '부산광역시 금정구 부산대학로63번길 2',
    image: null,
  },
  {
    kind: '유',
    type: '공립(병설)',
    name: '부산유치원',
    address: '부산광역시 해운대구 재반로12번길 16',
    image: null,
  }
];


// 지도 생성
var mapContainer = document.getElementById('kakao-map') 
var mapOption = {
  center: new kakao.maps.LatLng(37.541, 126.986), // 지도 중심좌표
  level: 1 // 지도 확대 레벨
};   
var map = new kakao.maps.Map(mapContainer, mapOption); 


// 각 객체의 주소값을 convertToLatLng() 함수의 매개변수로 넘기기
searchList.forEach((item) => convertToLatLng(item.address));


// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성
var bounds = new kakao.maps.LatLngBounds();


//주소를 좌표로 변환
count = 0;
function convertToLatLng(address) {

  //주소-좌표 변환 객체 생성
  var geocoder = new kakao.maps.services.Geocoder();

  //주소로 좌표 검색
  geocoder.addressSearch(address, function(result, status) {

    //정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {

      // 좌표 생성
      var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

      //결과값으로 받은 위치를 마커로 표시
      var marker = new kakao.maps.Marker({
        position: coords,
        clickable: true,
      });
      marker.setMap(map);

      // 마커에 표시할 인포윈도우를 생성
      // <span class="tag">${searchList[count].kind}</span>  
      var infowindow = new kakao.maps.InfoWindow({
        content: 
          `<a class="marker-box" href="#">
            <span class="info-type">${searchList[count].type}</span>
            <div>
              <span class="info-name">${searchList[count].name}</span>
            </div>
            <span class="info-addr"><i class="ci-location"></i>${searchList[count].address}</span>
          </a>`,
        removable: true,
      });

      // 마커 클릭하면 인포뒤도우 표시
      kakao.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map, marker);  
      });

      // 검색 결과에 맞게 지도 범위 재설정
      bounds.extend(coords);
      map.setBounds(bounds);

      count++;
    }    
  });
}

// 사이드바 목록 추가
const sidebar = document.querySelector('#school-list .overflow-auto');
const searchCount = document.querySelector('#school-list .product-title span');

searchCount.innerHTML = searchList.length;

searchList.forEach((item) => {
  let href = 'nursery-detail.html';
  let alt = 'Nursery';
  
  if (item.kind === '어') {
    href = 'nursery-detail.html';
    alt = 'Nursery';
  }
  if (item.kind === '유') {
    href = 'kinder-detail.html';
    alt = 'Kindergarden';
  }

  sidebar.innerHTML += 
  `<div class="card product-card product-list px-3 py-2">
    <span class="tag btn-wishlist btn-sm">${item.kind}</span>
    <div class="d-sm-flex align-items-center">
      <a class="product-list-thumb" href="${href}"><img src="img/ukids/index/어린이집.png" alt="${alt}"></a>
      <div class="card-body py-2">
        <div class="product-price"><span class="text-accent"><small>${item.type}</small></span></div>
        <h3 class="product-title fs-base mt-1 mb-2"><a href="${href}">${item.name}</a></h3>
        <span class="product-meta d-block fs-xs pb-1"><i class="ci-location me-1"></i>${item.address}</span>
      </div>
    </div>
  </div>
  <div class="border-top pt-0 mt-0"></div>`
});


// 빠른학교찾기에서 기관에 따른 태그 색상 변경
const schoolTagList = document.querySelectorAll('#school-list .tag');

window.addEventListener('load', () => {
  schoolTagList.forEach((item) => {
    if (item.textContent === '어') {
      item.classList.add('ns-tag');
    } else if (item.textContent === '유') {
      item.classList.add('kg-tag');
    }
  });
});





/* ========== 차트 ========== */
// 1. 해당 지역 어린이집/유치원 현황 그래프
let pieChartData = {
    labels: ['어린이집', '유치원'],
    datasets: [{
        data: [6, 4],
        backgroundColor: ['#69b3fe',  '#42d697'],
        datalabels: {
          labels: {
            value: {
              formatter: function(value, ctx) {
                var value = ctx.dataset.data[ctx.dataIndex];
                return value > 0 ? Math.round(value / (ctx.dataset.data[0] + ctx.dataset.data[1]) * 100) + ' %' : null;
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


// 2. 해당 지역 어린이집 국공립/사립 현황 그래프
let barChartData1 = {
  labels: ['국공립', '사립'],
  datasets: [{
      data: [3, 7],
      backgroundColor: ['#f34770',  '#fea569'],
      datalabels: {
        labels: {
          display: false
        } 
      }
  }] 
};

let barChartDraw1 = function () {
  let ctx = document.getElementById('barChartCanvas1').getContext('2d');
  
  window.barChart1 = new Chart(ctx, {
      type: 'bar',
      data: barChartData1,
      options: {
          responsive: false,
          legend: {
              display: false
          },
          scales: {
            xAxes: [{
              display: false,
              barPercentage: 0.4,
              
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
          legendCallback: customLegend
      },
  });
};


// 3. 해당 지역의 유치원 국공립/사립 현황 그래프
let barChartData2 = {
  labels: ['국공립', '사립'],
  datasets: [{
      data: [5, 5],
      backgroundColor: ['#ffd700',  '#81c147'],
      datalabels: {
        labels: {
          display: false
        } 
      }
  }] 
};

let barChartDraw2 = function () {
  let ctx = document.getElementById('barChartCanvas2').getContext('2d');
  
  window.barChart2 = new Chart(ctx, {
      type: 'bar',
      data: barChartData2,
      options: {
          responsive: false,
          legend: {
              display: false
          },
          scales: {
            xAxes: [{
              display: false,
              barPercentage: 0.4,
              
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
          legendCallback: customLegend
      },
  });
};


// 4. 주요 지역별 어린이집/유치원 현황 그래프
let barChartData3 = {
  labels: ['서울', '부산', '대구', '인천', '광주', '대전', '울산', '제주'],
  datasets: [
    {
      label: '어린이집',
      data: [6, 6, 6, 6, 6, 6, 6, 6],
      backgroundColor: '#ffc0cb',
      datalabels: {
        labels: {
          display: false
        } 
      }
    },
    {
      label: '유치원',
      data: [3, 3, 3, 3, 3, 3, 3, 3],
      backgroundColor: '#8977ad',
      datalabels: {
        labels: {
          display: false
        } 
      }
    }
  ]  
};

let barChartDraw3 = function () {
  let ctx = document.getElementById('barChartCanvas3').getContext('2d');
  
  window.barChart3 = new Chart(ctx, {
      type: 'horizontalBar',
      data: barChartData3,
      options: {
          indexAxis: 'y',
          responsive: true,
          legend: {
              display: false
          },
          scales: {
            xAxes: [{
              display: false,
              barPercentage: 0.5,
              ticks: {
                beginAtZero: true
              }
            }],
            yAxes: [{
              display: true,
              ticks: {
                min: 0,
                stepSize: 2,
                fontSize: 14
              }
            }]
          },
          legendCallback: customLegend2
      },
  });
};


// 범례 ver1
let customLegend = function (chart) {
  let ul = document.createElement('ul');
  let color = chart.data.datasets[0].backgroundColor;
  let sum = chart.data.datasets[0].data[0] + chart.data.datasets[0].data[1];

  chart.data.labels.forEach(function (label, index) {
      ul.innerHTML += 
        `<li>
          <div>
            <span class="circle" style="background-color: ${color[index]};"></span>
            <span>${label}</span>
          </div>
          <div>
            <span class="count">${chart.data.datasets[0].data[index]}개</span>
            <span>(${Math.round((chart.data.datasets[0].data[index] / sum) * 100)}%)</span>
          </div>
        </li>`;
  });
  return ul.outerHTML;
};


// 범례 ver2
let customLegend2 = function (chart) {
  let ul = document.createElement('ul');
  let color = [chart.data.datasets[0].backgroundColor, chart.data.datasets[1].backgroundColor];

  chart.data.datasets.forEach(function (label, index) {
      ul.innerHTML += 
        `<li>
          <div>
            <span class="circle" style="background-color: ${color[index]};"></span>
            <span>${label.label}</span>
          </div>
        </li>`;
  });
  return ul.outerHTML;
};


// 실행
window.onload = function () {
  pieChartDraw();
  barChartDraw1();
  barChartDraw2();
  barChartDraw3();
  document.querySelector('.legend-div.pie').innerHTML = window.pieChart.generateLegend();
  document.querySelector('.legend-div.bar1').innerHTML = window.barChart1.generateLegend();
  document.querySelector('.legend-div.bar2').innerHTML = window.barChart2.generateLegend();
  document.querySelector('.legend-div.bar3').innerHTML = window.barChart3.generateLegend();
}