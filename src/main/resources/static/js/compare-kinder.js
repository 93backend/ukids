const kgCat = document.querySelectorAll('#kg-table tbody');
const kgItemTitle = [
  ['설립일', '개원일', '대표자명', '원장명', '설립유형', '관할 행정기관', '운영기관', '전화번호', '홈페이지', '주소', '불편사항신고 이력'],
  ['특수학급', '방과후 활동', '통학 차량', '급식', '체육장', 'CCTV'],
  ['정기소독관리 대상', '놀이시설안전검사 대상', '소방대피훈련 시행 기관', '소방안전점검 완료', '가스점검 완료',' 전기설비점검 완료'],
  [],
  ['특수 학급', '혼합 학급', '만 3세 학급', '만 4세 학급', '만 5세 학급'],
  ['특수 아동', '혼합', '만 3세 아동', '만 4세 아동', '만 5세 아동'],
  ['특수 학급', '혼합 학급', '만 3세 학급', '만 4세 학급', '만 5세 학급'],
  ['원장', '원감', '수석교사', '보직교사', '일반교사', '특수교사', '보건교사', '영양교사', '기간제교사'],
  ['수석교사', '정교사 1급', '정교사 2급', '준교사'],
  ['1년 미만 교사', '1년 이상 ~ 2년 미만 교사', '2년 이상 ~ 4년 미만 교사', '4년 이상 ~ 6년 미만 교사', '6년 이상 교사']
];

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
}

// 테이블 셋팅
function tableSetting(cat, itemData) {
  for (let i=0; i<cat.length; i++) {
    for (let j=0; j<itemData[i].length; j++) {
      const row = createRow();
      cat[i].appendChild(row);
    };
  };
};

// 현재 테이블의 비교 항목 표시
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
  $(".item3").html('data3');
  // $(".item4").html('item4');
}

$(document).ready(function(){
  tableSetting(kgCat, kgItemTitle);
  handleItemTitle(kgCat, kgItemTitle);
  showItemData();
});





// 테이블 필터링 기준 변경에 따른 카테고리 컨트롤
const formSelect = document.querySelector('#kg-table select');

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
  const kgSelectOpts = document.querySelectorAll('#kg-table .form-select option');

  kgSelectOpts.forEach((opt) => {
    if (opt.selected === true) {
      const allCat = document.querySelectorAll('#kg-table tbody')
      const basicCat = document.querySelectorAll('#kg-table .basic-category');
      const detailCat = document.querySelectorAll('#kg-table .detail-category');
      handleCategory(opt, allCat, basicCat, detailCat);
    };
  });
};

formSelect.addEventListener('change', selectOption);




/* ========== 카카오 지도 ========== */
// 검색 결과
var searchList = [
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
var mapContainer = document.getElementById('kg-kakaomap');
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
          `<a id="marker-box">
            <span id="info-type" class="mt-1 ms-1">${searchList[count].type}</span>
            <div class="ms-1">
              <span id="info-name">${searchList[count].name}</span>
            </div>
            <span id="info-addr" class="mb-1 ms-1"><i class="ci-location"></i>${searchList[count].address}</span>
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