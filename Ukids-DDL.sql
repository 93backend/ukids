 DROP SCHEMA IF EXISTS ukids;
 CREATE SCHEMA ukids;
 USE ukids;


-- ========================================= 회원 ==========================================
DROP TABLE IF EXISTS MEMBER;
CREATE TABLE MEMBER (
    memberNo 			INT  			PRIMARY KEY AUTO_INCREMENT,
    id 					VARCHAR(30) 	NOT NULL UNIQUE,		-- 아이디
    password 			VARCHAR(100) 	NOT NULL,				-- 비밀번호
    role 				VARCHAR(10) 	DEFAULT 'ROLE_USER',	-- 역할
    name 				VARCHAR(15) 	NOT NULL,				-- 이름
    postCode			VARCHAR(100), 							-- 우편번호
    address1 			VARCHAR(100),							-- 주소
    address2 			VARCHAR(100),							-- 주소
    email 				VARCHAR(100),							-- 이메일
    phone				VARCHAR(100), 							-- 휴대폰 번호
    kidNum 				INT, 									-- 아이 수
	kidAge1 			INT, 									-- 아이 나이1
    kidAge2 			INT,  									-- 아이 나이2
    kidAge3 			INT,  									-- 아이 나이3
    field				VARCHAR(100),							-- 소속
    fieldCd				VARCHAR(100),							-- 소속 코드
    kakaoToken			VARCHAR(1000),							-- 카카오톡
    STATUS 				VARCHAR(1) 		DEFAULT 'Y' CHECK(STATUS IN('Y', 'N')),
    enrollDate 			DATETIME  		DEFAULT CURRENT_TIMESTAMP,
    modifyDate 			DATETIME 		DEFAULT CURRENT_TIMESTAMP
);
SELECT * FROM MEMBER;

-- ========================================= 장바구니 ==========================================
DROP TABLE IF EXISTS CART;
CREATE TABLE CART (
	no			INT AUTO_INCREMENT PRIMARY KEY,
    toyNo		INT,
    memberNo	INT,
    startDate	DATE,					-- 대여기간(시작)
	endDate		DATE,					-- 대여기간(반납)
	FOREIGN KEY (toyNo) REFERENCES TOY(toyNo),
	FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);

-- ========================================= 게시판 ==========================================
DROP TABLE IF EXISTS BOARD;
CREATE TABLE BOARD (	
    boardNo 			INT 			AUTO_INCREMENT,
    memberNo 			INT, 			-- 회원
	title 				VARCHAR(50), 	-- 제목
	content 			VARCHAR(2000),	-- 내용
    good 				INT,			-- 좋아요
	type 				VARCHAR(100), 	-- 게시글 종류
	originalFileName 	VARCHAR(100), 	-- 기존파일이름
	renamedFileName 	VARCHAR(100),	-- 수정한파일이름
	readcount 			INT 			DEFAULT 0, 
    STATUS 				VARCHAR(1) 		DEFAULT 'Y' CHECK (STATUS IN('Y', 'N')),
    createDate 			DATETIME  		DEFAULT CURRENT_TIMESTAMP, 
    modifyDate 			DATETIME  		DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_BOARD_NO PRIMARY KEY(boardNo),
    CONSTRAINT FK_BOARD_WRITER FOREIGN KEY(memberNo) REFERENCES MEMBER(memberNo) ON DELETE SET NULL
);
SELECT * FROM BOARD;

-- ========================================= 게시판 - 리뷰 ==========================================
DROP TABLE IF EXISTS B_REPLY;
CREATE TABLE B_REPLY(
	bReplyNo 			INT 			PRIMARY KEY AUTO_INCREMENT,
	boardNo 			INT,
	memberNo 			INT,
	content 			VARCHAR(400),
	STATUS 				VARCHAR(1) 		DEFAULT 'Y' CHECK (STATUS IN ('Y', 'N')),
	createDate 			DATETIME 		DEFAULT CURRENT_TIMESTAMP,
	modifyDate 			DATETIME 		DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (boardNo) REFERENCES BOARD(boardNo),
	FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);
SELECT * FROM B_REPLY;

-- ========================================= 좋아요 ==========================================
DROP TABLE IF EXISTS GOOD;
CREATE TABLE GOOD (   
    goodNo 		INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    boardNo		INT,
    memberNo 	INT,
    FOREIGN KEY (boardNo) REFERENCES BOARD(boardNo),
    FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);
SELECT * FROM GOOD;

-- ========================================= 유치원 ==========================================
DROP TABLE IF EXISTS KINDER;
CREATE TABLE KINDER(
kinNo 				INT PRIMARY KEY NOT NULL AUTO_INCREMENT, 	-- 유치원번호
kinderCode 			VARCHAR(100), 								-- 유치원코드
officeedu 			VARCHAR(100), 								-- 교육청명
subofficeedu 		VARCHAR(100), 								-- 교육지원청명
kindername 			VARCHAR(100),								-- 유치원명
establish 			VARCHAR(100), 								-- 설립유형
edate 				VARCHAR(100),								-- 설립일
odate 				VARCHAR(100), 								-- 개원일
addr 				VARCHAR(100), 								-- 주소
telno 				VARCHAR(100), 								-- 전화번호
hpaddr 				VARCHAR(100), 								-- 홈페이지
opertime 			VARCHAR(100), 								-- 운영시간
clcnt3 				INT,										-- 만3세학급수
clcnt4 				INT, 										-- 만4세학급수
clcnt5 				INT, 										-- 만5세학급수
mixclcnt 			INT, 										-- 혼합학급수
shclcnt 			INT, 										-- 특수학급수
ppcnt3 				INT, 										-- 만3세유아수
ppcnt4 				INT, 										-- 만4세유아수
ppcnt5 				INT, 										-- 만5세유아수
mixppcnt 			INT, 										-- 혼합유아수
shppcnt 			INT, 										-- 특수유아수
rppnname 			VARCHAR(100), 								-- 대표자명
ldgrname 			VARCHAR(100), 								-- 원장명
pbnttmng 			VARCHAR(100), 								-- 공시차수
prmstfcnt 			INT, 										-- 인가총정원수
ag3fpcnt 			INT, 										-- 3세모집정원수
ag4fpcnt 			INT, 										-- 4세모집정원수
ag5fpcnt 			INT, 										-- 5세모집정원수
mixfpcnt 			INT, 										-- 혼합모집정원수
spcnfpcnt 			INT, 										-- 특수학급모집정원수
phgrindrarea 		VARCHAR(100), 								-- 체육장
drcnt 				INT, 										-- 원장수
adcnt 				INT,										-- 원감수
hdst_thcnt 			INT, 										-- 수석교사수
asps_thcnt 			INT, 										-- 보직교사수
gnrl_thcnt 			INT, 										-- 일반교사수
spcn_thcnt 			INT,										-- 특수교사수
ntcnt 				INT, 										-- 보건교사수
ntrt_thcnt 			INT, 										-- 영양교사수
shcnt_thcnt 		INT, 										-- 기간제교사수
incnt 				INT, 										-- 강사수
owcnt 				INT, 										-- 사무직원수
hdst_tchr_qacnt 	INT, 										-- 수석교사자격수
rgth_gd1_qacnt 		INT, 										-- 정교사1급자격수
rgth_gd2_qacnt 		INT, 										-- 정교사2급자격수
asth_qacnt 			INT, 										-- 준교사자격수
mas_mspl_dclr_yn 	VARCHAR(100), 								-- 집단급식소신고여부
vhcl_oprn_yn 		VARCHAR(100), 								-- 차량운영여부
yy1_undr_thcnt 		INT, 										-- 1년미만교사수
yy1_abv_yy2_undr_thcnt INT, 									-- 1년이상2년미만교사수
yy2_abv_yy4_undr_thcnt INT, 									-- 2년이상4년미만교사수
yy4_abv_yy6_undr_thcnt INT, 									-- 4년이상6년미만교사수
yy6_abv_thcnt 		INT, 										-- 6년이상교사수
fxtm_dsnf_trgt_yn 	VARCHAR(100), 								-- 정기소독 의무대상시설여부
fire_avd_yn 		VARCHAR(100), 								-- 소방대피훈련여부
gas_ck_yn 			VARCHAR(100), 								-- 가스점검여부
fire_safe_yn 		VARCHAR(100), 								-- 소방안전점검여부
elect_ck_yn 		VARCHAR(100), 								-- 전기설비점검여부
plyg_ck_yn 			VARCHAR(100), 								-- 놀이시설 안전검사 대상여부
cctv_ist_yn 		VARCHAR(100), 								-- CCTV 설치여부
cctv_ist_total 		INT, 										-- CCTV 총 설치수
afsc_pros_lsn_dcnt 	INT 										-- 방과후 과정수업일수
);


-- ========================================= 유치원 - 리뷰 ==========================================
DROP TABLE IF EXISTS K_REVIEW;
CREATE TABLE K_REVIEW(
	no				INT AUTO_INCREMENT PRIMARY KEY,
    kinNo			INT,
    memberNo		INT,
    content			VARCHAR(100),
    createDate 		DATETIME 		DEFAULT CURRENT_TIMESTAMP,
	modifyDate 		DATETIME 		DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (kinNo) REFERENCES KINDER(kinNo),
	FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);

-- ========================================= 유치원 - 찜 ==========================================
DROP TABLE IF EXISTS K_WISH;
CREATE TABLE K_WISH (   
    no 			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    kinNo 		INT,
    memberNo 	INT,
    FOREIGN KEY (kinNo) REFERENCES KINDER(kinNo),
    FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);

-- ========================================= 유치원 - 불편사항 ==========================================
DROP TABLE IF EXISTS K_CLAIM;
CREATE TABLE K_CLAIM (	
    no 					INT 	AUTO_INCREMENT PRIMARY KEY,
    kinNo				INT,			-- 유치원
    memberNo 			INT, 			-- 회원
	title 				VARCHAR(50), 	-- 제목
	content 			VARCHAR(2000),	-- 내용
	originalFileName 	VARCHAR(100), 	-- 기존파일이름
	renamedFileName 	VARCHAR(100),	-- 수정한파일이름
	readcount 			INT 			DEFAULT 0, 
    confirm				VARCHAR(1) 		DEFAULT 'N' CHECK (confirm IN('Y', 'N')),	-- 선생님 확인용
    STATUS 				VARCHAR(1) 		DEFAULT 'Y' CHECK (STATUS IN('Y', 'N')),
    createDate 			DATETIME  		DEFAULT CURRENT_TIMESTAMP, 
    modifyDate 			DATETIME  		DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (kinNo) REFERENCES KINDER(kinNo),
    FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);

-- ========================================= 유치원 - 입소신청 ==========================================
DROP TABLE IF EXISTS K_ADMISSION;
CREATE TABLE K_ADMISSION (	
	no			INT AUTO_INCREMENT PRIMARY KEY,
    kinNo		INT,
	memberNo 	INT,
    hopeDate	DATE,
    status		VARCHAR(1),
    FOREIGN KEY (kinNo) REFERENCES KINDER(kinNo),
    FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);

-- ========================================= 어린이집 ==========================================
DROP TABLE IF EXISTS NURSERY;
CREATE TABLE NURSERY (
	no				INT AUTO_INCREMENT PRIMARY KEY,
    stcode  		VARCHAR(100),		-- 어린이집코드                
    crname			VARCHAR(100), 		-- 어린이집명                 
	crtypename 		VARCHAR(100),		-- 어린이집유형            
	crstatusname 	VARCHAR(100),		-- 운영현황            
    
	zipcode 		VARCHAR(100),		-- 우편번호                 
	craddr 			VARCHAR(100),		-- 상세주소                  
	crtelno 		VARCHAR(100),		-- 전화번호                 
	crfaxno 		VARCHAR(100),		-- 팩스번호                 
	crhome 			VARCHAR(200),		-- 홈페이지주소   
    
	nrtrroomcnt 	INT,				-- 보육실수                
	nrtrroomsize 	INT,				-- 보육실 면적             
	plgrdco 		INT,				-- 놀이터수                    
	cctvinstlcnt 	INT,				-- CCTV총설치수           
	chcrtescnt 		INT,				-- 보육교직원수    
    
	crcapat 		INT,				-- 정원                      
	crchcnt 		INT,				-- 현원                      
	la 				DOUBLE,				-- 시설 위도(좌표값)
	lo 				DOUBLE,				-- 시설 경도(좌표값)
	crcargbname 	VARCHAR(100),		-- 통학차량운영여부    
    
	crcnfmdt 		VARCHAR(100),		-- 인가일자                
	crpausebegindt	VARCHAR(100),		-- 휴지시작일자        
	crpauseenddt	VARCHAR(100),		-- 휴지종료일자          
	crabldt 		VARCHAR(100),		-- 폐지일자                 
	crspec 			VARCHAR(100),		-- 제공서비스   
    
	class_cnt_00 	INT,				-- 반수-만0세             
	class_cnt_01 	INT,				-- 반수-만1세             
	class_cnt_02 	INT,				-- 반수-만2세             
	class_cnt_03 	INT,				-- 반수-만3세             
	class_cnt_04 	INT,				-- 반수-만4세 
    
	class_cnt_05 	INT,				-- 반수-만5세             
	class_cnt_m2 	INT,				-- 반수-영아혼합(만0~2세)     
	class_cnt_m5 	INT,				-- 반수-유아혼합(만3~5세)     
	class_cnt_sp 	INT,				-- 반수-특수장애
	class_cnt_tot 	INT,				-- 반수-총계
    
	child_cnt_00 	INT,				-- 아동수-만0세
	child_cnt_01 	INT,				-- 아동수-만1세            
	child_cnt_02 	INT,				-- 아동수-만2세            
	child_cnt_03 	INT,				-- 아동수-만3세            
	child_cnt_04 	INT,				-- 아동수-만4세 
    
	child_cnt_05 	INT,				-- 아동수-만5세            
	child_cnt_m2 	INT,				-- 아동수-영아혼합(만0~2세)    
	child_cnt_m5 	INT,				-- 아동수-유아혼합(만3~5세)    
	child_cnt_sp 	INT,				-- 아동수-특수장애
	child_cnt_tot 	INT,				-- 아동수-총계
    
	em_cnt_0y 		INT,				-- 근속년수-1년미만
	em_cnt_1y 		INT,				-- 근속년수-1년이상~2년미만
	em_cnt_2y 		INT,				-- 근속년수-2년이상~4년미만
	em_cnt_4y 		INT,				-- 근속년수-4년이상~6년미만
	em_cnt_6y 		INT,				-- 근속년수-6년이상
    
	em_cnt_a1 		INT,				-- 교직원현황-원장
	em_cnt_a2 		INT,				-- 교직원현황-보육교사
	em_cnt_a3 		INT,				-- 교직원현황-특수교사
	em_cnt_a4 		INT,				-- 교직원현황-치료교사            
	em_cnt_a5 		INT,				-- 교직원현황-영양사
    
	em_cnt_a6 		INT,				-- 교직원현황-간호사             
	em_cnt_a10 		INT,				-- 교직원현황-간호조무사          
	em_cnt_a7 		INT,				-- 교직원현황-조리원             
	em_cnt_a8 		INT,				-- 교직원현황-사무직원            
	em_cnt_tot 		INT,				-- 교직원현황-총계   
    
	Crrepname 		VARCHAR(100)		-- 대표자명    
);

-- ========================================= 어린이집 - 리뷰 ==========================================
DROP TABLE IF EXISTS N_REVIEW;
CREATE TABLE N_REVIEW(
	no				INT AUTO_INCREMENT PRIMARY KEY,
    nuNo			INT,
    memberNo		INT,
    content			VARCHAR(100),
    createDate 		DATETIME 		DEFAULT CURRENT_TIMESTAMP,
	modifyDate 		DATETIME 		DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (nuNo) REFERENCES NURSERY(no),
	FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);

SELECT * FROM N_REVIEW;

-- ========================================= 어린이집 - 찜 ==========================================
DROP TABLE IF EXISTS N_WISH;
CREATE TABLE N_WISH (   
    no 			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nuNo 		INT,
    memberNo 	INT,
    FOREIGN KEY (nuNo) REFERENCES NURSERY(no),
    FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);

-- ========================================= 어린이집 - 불편사항 ==========================================
DROP TABLE IF EXISTS N_CLAIM;
CREATE TABLE N_CLAIM (	
    no 					INT 	AUTO_INCREMENT PRIMARY KEY,
    nuNo				INT,			-- 어린이집
    memberNo 			INT, 			-- 회원
	title 				VARCHAR(50), 	-- 제목
	content 			VARCHAR(2000),	-- 내용
	originalFileName 	VARCHAR(100), 	-- 기존파일이름
	renamedFileName 	VARCHAR(100),	-- 수정한파일이름
	readcount 			INT 			DEFAULT 0, 
    confirm				VARCHAR(1) 		DEFAULT 'N' CHECK (confirm IN('Y', 'N')),	-- 선생님 확인용
    STATUS 				VARCHAR(1) 		DEFAULT 'Y' CHECK (STATUS IN('Y', 'N')),
    createDate 			DATETIME  		DEFAULT CURRENT_TIMESTAMP, 
    modifyDate 			DATETIME  		DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (nuNO) REFERENCES NURSERY(no),
    FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);


-- ========================================= 어린이집 - 입소신청 ==========================================
DROP TABLE IF EXISTS N_ADMISSION;
CREATE TABLE N_ADMISSION (	
	no			INT AUTO_INCREMENT PRIMARY KEY,
    nuNo		INT,
	memberNo 	INT,
    hopeDate	DATE,
    status		VARCHAR(1),
    FOREIGN KEY (nuNo) REFERENCES NURSERY(no),
    FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);

-- ========================================= 아동 복지시설 ==========================================
DROP TABLE IF EXISTS WELFARE;
CREATE TABLE WELFARE(
    cwNo             	INT 			AUTO_INCREMENT,
	fcltCd				VARCHAR(100),	       -- 시설코드
	fcltNm				VARCHAR(100),	       -- 시설명
	fcltEngNm			VARCHAR(100),	       -- 시설영문명 
	rprsNm				VARCHAR(100),	       -- 대표자명
	homepageAddr		VARCHAR(100),	       -- 홈페이지주소
	fcltMailAddr		VARCHAR(100),	       -- 시설이메일주소
	fcltZipcd			VARCHAR(100),	       -- 시설우편번호
	fcltAddr			VARCHAR(100),	       -- 시설주소
	fcltDtl_1Addr		VARCHAR(100),	       -- 시설상세1주소
	fcltDtl_2Addr		VARCHAR(100),	       -- 시설상세2주소
	fcltTelNo			VARCHAR(100),	       -- 시설전화번호
	faxNo				VARCHAR(100),	       -- 팩스번호
    estbDe				VARCHAR(100),		   -- 설립일자
	cfbClCd				VARCHAR(100),		   -- 업종분류코드
	cprNm				VARCHAR(100),		   -- 법인명
    cfbNm				VARCHAR(100),		   -- 업종명
    PRIMARY KEY (cwNo)
);

SELECT * FROM WELFARE;
SELECT COUNT(*) FROM WELFARE;

-- ========================================= 어린이병원 ==========================================
DROP TABLE IF EXISTS HOSPITAL;
CREATE TABLE HOSPITAL (
	no			INT AUTO_INCREMENT PRIMARY KEY,
	hpid		VARCHAR(100),						-- 기관ID
	hpAddr		VARCHAR(100)	NULL,				-- 주소
	divName		VARCHAR(100)	NULL,				-- 병원분류명
	hpName		VARCHAR(100)	NULL,				-- 기관명
	tel1		VARCHAR(100)	NULL,				-- 대표전화1
    erTel		VARCHAR(100)	NULL,				-- 응급실전화
	er			VARCHAR(100)	NULL,				-- 응급실운영여부(1/2)
	time1c		VARCHAR(100)	NULL,				-- 진료시간(월요일)C
	time2c		VARCHAR(100)	NULL,				-- 진료시간(화요일)C
	time3c		VARCHAR(100)	NULL,				-- 진료시간(수요일)C
	time4c		VARCHAR(100)	NULL,				-- 진료시간(목요일)C
	time5c		VARCHAR(100)	NULL,				-- 진료시간(금요일)C
	time6c		VARCHAR(100)	NULL,				-- 진료시간(토요일)C
	time7c		VARCHAR(100)	NULL,				-- 진료시간(일요일)C
	time8c		VARCHAR(100)	NULL,				-- 진료시간(공휴일)C
	time1s		VARCHAR(100)	NULL,				-- 진료시간(월요일)S
	time2s		VARCHAR(100)	NULL,				-- 진료시간(화요일)S
	time3s		VARCHAR(100)	NULL,				-- 진료시간(수요일)S
	time4s		VARCHAR(100)	NULL,				-- 진료시간(목요일)S
	time5s		VARCHAR(100)	NULL,				-- 진료시간(금요일)S
	time6s		VARCHAR(100)	NULL,				-- 진료시간(토요일)S
	time7s		VARCHAR(100)	NULL,				-- 진료시간(일요일)S
	time8s		VARCHAR(100)	NULL,				-- 진료시간(공휴일)S
	post1		VARCHAR(100)	NULL,				-- 우편번호1
	post2		VARCHAR(100)	NULL,				-- 우편번호2
	lon			DOUBLE			NULL,				-- 병원경도
	lat			DOUBLE			NULL				-- 병원위도
);

SELECT * FROM HOSPITAL;
SELECT COUNT(*) FROM HOSPITAL;

-- ========================================= 소아야간 진료 ==========================================
DROP TABLE IF EXISTS NIGHTCARE;
CREATE TABLE NIGHTCARE (
	no			INT AUTO_INCREMENT PRIMARY KEY,
    hpName		VARCHAR(100)	NULL,				-- 기관명
    divName		VARCHAR(100)	NULL,				-- 병원분류명
	hpAddr		VARCHAR(100)	NULL,				-- 주소
	tel			VARCHAR(100)	NULL,				-- 전화번호
	post1		VARCHAR(100)	NULL,				-- 우편번호
	openDate	VARCHAR(100)	NULL				-- 개설일자
);

SELECT * FROM NIGHTCARE;
SELECT COUNT(*) FROM NIGHTCARE;


-- ========================================= 놀이시설 ==========================================
DROP TABLE IF EXISTS PLAYGROUND;
CREATE TABLE PLAYGROUND (
	NO			INT AUTO_INCREMENT PRIMARY KEY,
	pgid		VARCHAR(50)	NOT NULL,	-- 고유ID
	divL		VARCHAR(200),			-- 대분류명
	divM		VARCHAR(200),			-- 중분류명
	pgName		VARCHAR(200),			-- 시설명
	pgAddr1		VARCHAR(500),			-- 시설도로명주소
	pgAddr2		VARCHAR(200),			-- 지번주소
	bName		VARCHAR(200),			-- 건물명
	tel			VARCHAR(20),			-- 전화번호
	post		VARCHAR(5),				-- 우편번호
	url			VARCHAR(500),			-- 홈페이지URL
	lat			VARCHAR(20),			-- 시설위도
	lon			VARCHAR(20),			-- 시설경도
	water		VARCHAR(1) DEFAULT 'N',	-- 물놀이시설여부
	dc			VARCHAR(4000)			-- 추가설명
);

SELECT * FROM PLAYGROUND;
SELECT COUNT(*) FROM PLAYGROUND;

-- ========================================= 장난감 ==========================================
DROP TABLE IF EXISTS TOY;
CREATE TABLE TOY(
    toyNo             	INT 	AUTO_INCREMENT,
	toyCd				VARCHAR(100),	       -- 자체 상품코드
	toyNm1				VARCHAR(100),	       -- 상품명
	toyNm2				VARCHAR(100),	       -- 공급사 상품명
	toyEx				VARCHAR(1000),	       -- 상품 상세설명
	toySearch			VARCHAR(1000),	       -- 검색어설정
    toyPay				VARCHAR(100),		   -- 금액
	toyImg				VARCHAR(1000),	       -- 이미지
	toyCategoryB		VARCHAR(100),	       -- 대분류
    toyCategoryM		VARCHAR(100),		   -- 중분류
	toyCategoryS		VARCHAR(100),		   -- 소분류
    toyType				VARCHAR(100),		   -- 대여여부
    PRIMARY KEY (toyNo)
);

SELECT * FROM TOY;
SELECT COUNT(*) FROM TOY;

-- ========================================= 장난감 - 리뷰 ==========================================
DROP TABLE IF EXISTS T_REVIEW;
CREATE TABLE T_REVIEW(
	no				INT AUTO_INCREMENT PRIMARY KEY,
    toyNo			INT,
    memberNo		INT,
    content			VARCHAR(100),
    createDate 		DATETIME 		DEFAULT CURRENT_TIMESTAMP,
	modifyDate 		DATETIME 		DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (toyNo) REFERENCES TOY(toyNo),
	FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo)
);

-- ========================================= 결제 ==========================================

DROP TABLE IF EXISTS Pay;
CREATE TABLE Pay(
	payNo				INT 			PRIMARY KEY AUTO_INCREMENT,
    memberNo			INT,					-- 회원
    toyNo				INT,					-- 장난감
	price				VARCHAR(100),			-- 금액
	name				VARCHAR(100),			-- 이름
	phone				VARCHAR(100),			-- 연락처
	postCode			VARCHAR(100),			-- 우편번호
	address				VARCHAR(100),			-- 주소
	request				VARCHAR(1000),			-- 요청사항
	method				VARCHAR(100),			-- 결제수단
	startDate			DATE,					-- 대여기간(시작)
	endDate				DATE,					-- 대여기간(반납)
    FOREIGN KEY (memberNo) REFERENCES MEMBER(memberNo),
    FOREIGN KEY (toyNo) REFERENCES TOY(toyNo)
);

SELECT * FROM Pay;
SELECT COUNT(*) FROM Pay;




-- ========================================= 추천 도서 ==========================================
DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK (
	no 					INT AUTO_INCREMENT PRIMARY KEY,
    ranking 			INT,				-- 순위
    bookname 			VARCHAR(100),		-- 도서명
    authors 			VARCHAR(100),		-- 저자명
    publisher 			VARCHAR(100),		-- 출판사
	publication_year 	INT,				-- 출판년도
	class_nm 			VARCHAR(100),		-- 분류
    loan_count 			INT,				-- 대출 건수
    bookImageURL 		VARCHAR(100),		-- 이미지
	age					INT					-- 연령
);

SELECT * FROM BOOK; 


COMMIT;


