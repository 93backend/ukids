function cityChange() {
	const urlParams = new URL(location.href).searchParams;
	var town = urlParams.get('town');
	
    var seoul = [   // 서울특별시
        '강남구', '강동구', '강북구', '강서구', '관악구', 
        '광진구', '구로구', '금천구', '노원구', '도봉구', 
        '동대문구', '동작구', '마포구', '서대문구', '서초구', 
        '성동구', '성북구', '송파구', '양천구', '영등포구', 
        '용산구', '은평구', '종로구', '중구', '중랑구'
    ];
    var busan = [   // 부산광역시
        '강서구', '금정구', '기장군', '남구', '동구', '동래구', 
        '부산진구', '북구', '사상구', '사하구', '서구', 
        '수영구', '연제구', '영도구', '중구', '해운대구'
    ];
    var daegu = [   // 대구광역시
        '남구', '달서구', '달성군', '동구', 
        '북구', '서구', '수성구', '중구'
    ];
    var incheon = [ // 인천광역시
        '강화군', '계양구', '남동구', '동구', '미추홀구', 
        '부평구', '서구', '연수구' ,'옹진군', '중구'
    ];
    var gwangju = [ // 광주광역시
        '광산구', '남구', '동구', '북구', '서구'
    ];
    var daejeon = [ // 대전광역시
        '대덕구', '동구', '서구', '유성구', '중구'
    ];
    var ulsan = [   // 울산광역시
        '남구', '동구', '북구', '울주군', '중구'
    ];
    var sejong = [  // 세종특별자치시
        '세종특별자치시'
    ];
    var gyeonggi = [    // 경기도
        '가평군', '고양시 덕양구', '고양시 일산동구', '고양시 일산서구', '과천시', 
        '광명시', '광주시', '구리시', '군포시', '김포시', 
        '남양주시', '동두천시', '부천시 소사구', '성남시 분당구', '성남시 수정구', 
        '성남시 중원구', '수원시 권선구', '수원시 영통구', '수원시 장안구', '수원시 팔달구', 
        '시흥시', '안산시 단원구', '안산시 상록구', '안성시', '안양시 동안구', 
        '안양시 만안구', '양주시', '양평군', '여주시', '연천군', 
        '오산시', '용인시 기흥구', '용인시 수지구', '용인시 처인구', '의왕시', 
        '의정부시', '이천시', '파주시', '평택시', '포천시', '하남시', '화성시'
    ];
    var gangwon = [ // 강원도
        '강릉시', '고성군', '동해시', '삼척시', '속초시', 
        '양구군', '양양군', '영월군', '원주시', '인제군', 
        '정선군', '철원군', '춘천시', '태백시', '평창군', '홍천군', '화천군', '횡성군'
    ];
    var chungbuk = [    // 충청북도
        '괴산군', '단양군', '보은군', '영동군', '옥천군', 
        '음성군', '제천시', '증평군', '진천군', '청주시 상당구', 
        '청주시 서원구', '청주시 청원구', '청주시 흥덕구', '충주시'
    ];
    var chungnam = [    // 충청남도
        '계룡시', '공주시', '금산군', '논산시', '당진시', 
        '보령시', '부여군', '서산시', '서천군', '아산시', '예산군', 
        '천안시 동남구', '천안시 서북구', '청양군', '태안군', '홍성군'
    ];
    var jeonbuk = [ // 전라북도
        '고창군', '군산시', '김제시', '남원시', '무주군', 
        '부안군', '순창군', '완주군', '익산시', '임실군', 
        '장수군', '전주시 덕진구', '전주시 완산구', '정읍시', '진안군'
    ];
    var jeonnam = [ // 전라남도
        '강진군', '고흥군', '곡성군', '광양시', '구례군', '나주시', '담양군', 
        '목포시', '무안군', '보성군', '순천시', '신안군', '여수시', '영광군', 
        '영암군', '완도군', '장성군', '장흥군', '진도군', '함평군', '해남군', '화순군'
    ];
    var gyeongbuk = [   // 경상북도
        '경산시', '경주시', '고령군', '구미시', '군위군', '김천시', '문경시', 
        '봉화군', '상주시', '성주군', '안동시', '영덕군', '영양군', '영주시', 
        '영천시', '예천군', '울릉군', '울진군', '의성군', '청도군', '청송군', 
        '칠곡군', '포항시 남구', '포항시 북구'
    ];
    var gyeongnam = [   // 경상남도
        '거제시', '거창군', '고성군', '김해시', '남해군', '밀양시' ,'사천시', 
        '산청군', '양산시' ,'의령군', '진주시', '창녕군', '창원시 마산합포구', 
        '창원시 마산회원구', '창원시 성산구', '창원시 의창구', '창원시 진해구', 
        '통영시', '하동군', '함안군', '함양군', '합천군'
    ];
    var jeju = [    // 제주특별자치도
        '서귀포시', '제주시'
    ];

    var selected_city = $('#city').val();
    var town_list;
    if(selected_city == '서울') {
        town_list = seoul;
    } else if(selected_city == '부산') {
        town_list = busan;
    } else if(selected_city == '대구') {
        town_list = daegu;
    } else if(selected_city == '인천') {
        town_list = incheon;
    } else if(selected_city == '광주') {
        town_list = gwangju;
    } else if(selected_city == '대전') {
        town_list = daejeon;
    } else if(selected_city == '울산') {
        town_list = ulsan;
    } else if(selected_city == '세종특별' || selected_city == '세종') {
        town_list = sejong;
    } else if(selected_city == '경기도' || selected_city == '경기') {
        town_list = gyeonggi;
    } else if(selected_city == '강원도' || selected_city == '강원') {
        town_list = gangwon;
    } else if(selected_city == '충청북도') {
        town_list = chungbuk;
    } else if(selected_city == '충청남도') {
        town_list = chungnam;
    } else if(selected_city == '전라북도') {
        town_list = jeonbuk;
    } else if(selected_city == '전라남도') {
        town_list = jeonnam;
    } else if(selected_city == '경상북도') {
        town_list = gyeongbuk;
    } else if(selected_city == '경상남도') {
        town_list = gyeongnam;
    } else if(selected_city == '제주') {
        town_list = jeju;
    }

    $('#town').empty();
    var option = $('<option value=" ">전체</option>');
    $('#town').append(option);
    for(var i in town_list) {
		if(town == town_list[i]) {
	        option = $('<option vaule="' + town_list[i] + '" selected>' + town_list[i] + '</option>');
		} else {
	        option = $('<option vaule="' + town_list[i] + '" >' + town_list[i] + '</option>');
		}
        $('#town').append(option);
    }
}