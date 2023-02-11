// 검색 결과 (JSTL - forEach)
var searchList = [
  {
    type: '놀이공원',
    name: '천왕 이펜하우스 8단지 아파트 803동 어린이집옆 놀이시설(설치장소는 어린이집, 관리는 아파트 관리사무소)',
    address: '경기도 용인시 기흥구 흥덕중앙로105번길 40 (영덕동, 흥덕마을15단지 우남퍼스트빌리젠트)',
  },
  {
    type: '워터파크',
    name: '오션월드',
    address: '강원 홍천군 서면 한치골길 262',
  },
  {
    type: '놀이공원',
    name: '서울대공원',
    address: '경기 과천시 대공원광장로 102',
  },
  {
    type: '동물원',
    name: '코엑스아쿠아리움',
    address: '서울 강남구 영동대로 513',
  }
];


/* ========== 카카오 지도 ========== */
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
      var infowindow = new kakao.maps.InfoWindow({
        content: 
          `<a class="marker-box" href="#">
            <span class="info-type">${searchList[count].type}</span>
            <div><span class="info-name">${searchList[count].name}</span></div>
            <span class="info-addr text-truncate"><i class="ci-location"></i>${searchList[count].address}</span>
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


/* ========== 사이드바 목록 ========== */
const sidebar = document.querySelector('#pg-list .overflow-auto');
const searchCount = document.querySelector('#pg-list .product-title span');

searchCount.innerHTML = searchList.length;

searchList.forEach((item) => {
  sidebar.innerHTML += 
  `<div class="card product-card product-list">
    <div class="card-body py-2">
      <div class="product-price"><span class="text-accent"><small>${item.type}</small></span></div>
      <h3 class="product-title fs-base mt-1 mb-2"><a href="shop-single-v1.html">${item.name}</a></h3>
      <span class="product-meta d-block fs-xs pb-1 text-truncate"><i class="ci-location me-1"></i>${item.address}</span>
    </div>
   </div><div class="border-top pt-0 mt-0"></div>`
});