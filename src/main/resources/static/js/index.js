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


// 검색 화면으로 이동
function goSearch() {
	var type = $('#searchType').val();
	var search = $('#searchValue').val();
	var url = null;
	if(type == '어린이집') {
		url = '/nursery-main?search=' + search;
	} else {
		url = '/kinder-main?search=' + search;
	}
	location.replace(url);
}

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