/* ===== 테이블 세팅 ===== */
const nsCat = document.querySelectorAll('#ns-table tbody');
const nsItemTitle = [
  ['설립일', '개원일', '대표자명', '설립유형', '전화번호', '홈페이지', '주소', '불편사항신고 이력'],
  ['특수학급', '방과후 활동', '통학 차량', '급식', '체육장', 'CCTV'],
  [],
  ['특수 학급', '영아 혼합 학급 (만 0세~2세)', '유아 혼합 학급 (만 3세~5세)', '만 0세 학급', '만 1세 학급', '만 2세 학급', '만 3세 학급', '만 4세 학급', '만 5세 학급'],
  ['특수 아동', '영아 혼합 (만 0세~2세)', '유아 혼합 (만 3세~5세)', '만 0세 아동', '만 1세 아동', '만 2세 아동', '만 3세 아동', '만 4세 아동', '만 5세 아동'],
  ['원장', '보육교사', '특수교사', '치료교사', '영양사', '간호사', '간호조무사', '조리원'],
  ['1년 미만 교사', '1년 이상 ~ 2년 미만 교사', '2년 이상 ~ 4년 미만 교사', '4년 이상 ~ 6년 미만 교사', '6년 이상 교사']
];

// [['설립일', 2022-12-12,2022-3-3,3,4,] ['#제공서비'] []]

// 빈 row 생성
function createRow() {
  const tr = document.createElement('tr');
  const th = document.createElement('th');
  
  th.innerHTML = '-'
  tr.className = 'compare-item-list';
  tr.appendChild(th);
  
  for (let i=0; i<4; i++) {
    const td = document.createElement('td');
    td.innerHTML = '-';
    td.className = 'item' + (i+1);
    tr.appendChild(td);
  };
  
  return tr;
};

// 테이블 세팅
function tableSetting(cat, itemData) {
  for (let i=0; i<cat.length; i++) {
    for (let j=0; j<itemData[i].length; j++) {
      const row = createRow();
      cat[i].appendChild(row);
    };
  };
};

// 세팅된 테이블에 비교 항목 표시
function handleItemTitle(cat, itemData) {
  for (let i=0; i<cat.length; i++) {
    const tr = Array.from(cat[i].children).filter((tr) => tr.className === 'compare-item-list');
    for (let j=0; j<itemData[i].length; j++) {
      const th = tr[j].firstElementChild;
      th.innerHTML = itemData[i][j];
    };
  };

};

function showItemData() {
  $(".item1").html('data1');
  $(".item2").html('data2');
  // $(".item3").html('item3');
  // $(".item4").html('item4');
}

$(document).ready(function(){
  tableSetting(nsCat, nsItemTitle);
  handleItemTitle(nsCat, nsItemTitle);
  showItemData();
});





/* ===== 테이블 필터링 기준 변경에 따른 카테고리 컨트롤 ===== */
const formSelect = document.querySelector('#ns-table select');

// 선택된 option에 따라 표시되는 비교 카테고리 설정
function handleCategory(opt, allCat, basicCat, detailCat) {
  allCat.forEach((cat) => cat.classList.remove('selected-category'));    //초기화

  if (opt.classList.contains('basic-option')) {
    basicCat.forEach((cat) => cat.classList.add('selected-category'));
  };  
  if (opt.classList.contains('detail-option')) {
    detailCat.forEach((cat) => cat.classList.add('selected-category'));
  };
}; 

// 선택된 option 찾기
function selectOption() {
  const nsSelectOpts = document.querySelectorAll('#ns-table .form-select option');

  nsSelectOpts.forEach((opt) => {
    if (opt.selected === true) {
      const allCat = document.querySelectorAll('#ns-table tbody')
      const basicCat = document.querySelectorAll('#ns-table .basic-category');
      const detailCat = document.querySelectorAll('#ns-table .detail-category');
      handleCategory(opt, allCat, basicCat, detailCat);
    };
  });
};

formSelect.addEventListener('change', selectOption);




/* ===== 카카오지도 ===== */
// 검색 결과
var searchList = [
  {
    kind: '어',
    type: '사립(법인)',
    name: '서울어린이집',
    address: '서울특별시 중구 덕수궁길 140',
    image: null,
  },
  {
    kind: '유',
    type: '공립(병설)',
    name: '서울유치원',
    address: '서울특별시 관악구 관악로37길 20',
    image: null,
  },
];
  
  
/* ========== 카카오 지도 ========== */
// 지도 생성
var mapContainer = document.getElementById('ns-kakaomap');
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
          `<a id="marker-box" href="#">
            <span id="info-type">${searchList[count].type}</span>
            <div>
              <span id="info-name">${searchList[count].name}</span>
            </div>
            <span id="info-addr"><i class="ci-location"></i>${searchList[count].address}</span>
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
};