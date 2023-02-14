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
};

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

// 테이블 세팅
function tableSetting(cat, itemData, nsItems) {
  for (let i=0; i<cat.length; i++) {
    for (let j=0; j<itemData[i].length; j++) {
		flag = 1;
		for(let k=0; k<nsItems.length; k++) {
			if(itemData[i][j] == nsItems[k][0]) {
				const row = createRow(nsItems[k]);
				cat[i].appendChild(row);
				flag = 0;
			  	break;
			}
		}
		if(flag == 1) {
		  const row = createRow2();
		  cat[i].appendChild(row);
	  }
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

