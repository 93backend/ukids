const kgCat = document.querySelectorAll('#kg-table tbody');
const kgItemTitle = [
  ['설립일', '개원일', '대표자명', '원장명', '설립유형', '관할 행정기관', '운영기관', '전화번호', '홈페이지', '주소', '불편사항신고 이력'],
  ['특수학급', '방과후 활동', '통학 차량', '급식', '체육장', 'CCTV'],
  ['정기소독관리 대상', '놀이시설안전검사 대상', '소방대피훈련 시행 기관', '소방안전점검 완료', '가스점검 완료','전기설비점검 완료'],
  [],
  ['특수 학급', '혼합 학급', '만 3세 학급', '만 4세 학급', '만 5세 학급'],
  ['특수 아동', '혼합', '만 3세 아동', '만 4세 아동', '만 5세 아동'],
  ['특수 학급 정원', '혼합 학급 정원', '만 3세 학급 정원', '만 4세 학급 정원', '만 5세 학급 정원'],
  ['원장', '원감', '수석교사', '보직교사', '일반교사', '특수교사', '보건교사', '영양교사', '기간제교사'],
  ['수석교사', '정교사 1급', '정교사 2급', '준교사'],
  ['1년 미만 교사', '1년 이상 ~ 2년 미만 교사', '2년 이상 ~ 4년 미만 교사', '4년 이상 ~ 6년 미만 교사', '6년 이상 교사']
];


//kgItems = [{'설립일':['1992-12-12','1992-12-12','1992-12-12']},
//			{'개원일':['1992-12-12','1992-12-12','1992-12-12']}]
			

// 	tableSetting(kgCat, kgItemTitle, kgItems);
//  handleItemTitle(kgCat, kgItemTitle, kgItems);
  			
// 빈 row 생성
function createRow(info) {
  const tr = document.createElement('tr');
  const th = document.createElement('th');
  
  th.innerHTML = '-'
  tr.className = 'compare-item-list';
  tr.appendChild(th);
  
  for (let i=0; i<4; i++) {
    const td = document.createElement('td');
    td.innerHTML = info[i+1];
    td.className = 'item' + (i+1);
    tr.appendChild(td);
  };
  
  return tr;
}

function createRow2() {
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
function tableSetting(cat, itemData, kgItems) {
  for (let i=0; i<cat.length; i++) {
    for (let j=0; j<itemData[i].length; j++) {
	  flag = 1;
	  for (let k=0; k<kgItems.length; k++) {
		  if(itemData[i][j] == kgItems[k][0]){
		      const row = createRow(kgItems[k]);
		      cat[i].appendChild(row);
		      flag = 0;
			  break;			  
		  }
	  }
	  if(flag == 1){
		  const row = createRow2();
		  cat[i].appendChild(row);
	  }
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
