package com.multi.ukids.mypage.model.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.multi.ukids.board.model.vo.Board;
import com.multi.ukids.claim.model.vo.Claim;
import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.mypage.model.mapper.MypageMapper;
import com.multi.ukids.nursery.model.vo.NAdmission;
import com.multi.ukids.toy.model.mapper.ToyMapper;
import com.multi.ukids.toy.model.vo.Cart;
import com.multi.ukids.toy.model.vo.Pay;
import com.multi.ukids.toy.model.vo.Rental;
import com.multi.ukids.toy.model.vo.Toy;
import com.multi.ukids.wish.model.vo.Wish;

@Service
public class MypageService {
	@Autowired
	private MypageMapper mapper;
	@Autowired
	private ToyMapper toyMapper;
	
	// ============================== mypage1 - 회원정보 수정 ==============================
	// MemberService.java 참고
	
	// ============================== mypage2 - 입소 신청 조회 ==============================
	
	// 어린이집 - USER
	public List<NAdmission> getNAdmissionList(PageInfo pageInfo, Map<String, String> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectNAdmissionList(map);
	}
	
	public int getNAdmissionCount(Map<String, String> map) {
		return mapper.selectNAdmissionCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteNAdmission(int no) {
		return mapper.deleteNAdmission(no);
	}
	
	
	// 유치원 - USER
	public List<KAdmission> getKAdmissionList(PageInfo pageInfo, Map<String, String> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectKAdmissionList(map);
	}
	
	public int getKAdmissionCount(Map<String, String> map) {
		return mapper.selectKAdmissionCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteKAdmission(int no) {
		return mapper.deleteKAdmission(no);
	}
	
	
	// 어린이집 - TEACHER
	public List<NAdmission> getTNAdmissionList(PageInfo pageInfo, Map<String, String> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectTNAdmissionList(map);
	}
	
	public int getTNAdmissionCount(Map<String, String> map) {
		return mapper.selectTNAdmissionCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateTNurseryAdmissionY(int no) {
		return mapper.updateTNurseryAdmissionY(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateTNurseryAdmissionN(int no) {
		return mapper.updateTNurseryAdmissionN(no);
	}
	
	
	// 유치원 - TEACHER
	public List<KAdmission> getTKAdmissionList(PageInfo pageInfo, Map<String, String> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectTKAdmissionList(map);
	}
	
	public int getTKAdmissionCount(Map<String, String> map) {
		return mapper.selectTKAdmissionCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateTKinderAdmissionY(int no) {
		return mapper.updateTKinderAdmissionY(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateTKinderAdmissionN(int no) {
		return mapper.updateTKinderAdmissionN(no);
	}
	
	
	// ============================== mypage3 - 찜 ==============================
	
	public List<Wish> getNurseryWishList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectNurseryWishList(param);
	}
	
	public List<Wish> getKinderWishList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectKinderWishList(param);
	}
	
	public int getNurseryWishCount(Map<String, String> param) {
		return mapper.selectNurseryWishCount(param);
	}
	public int getKinderWishCount(Map<String, String> map) {
		return mapper.selectKinderWishCount(map);
	}

	
	@Transactional(rollbackFor = Exception.class)
	public int deleteNurseryWish(int no) {
		return mapper.deleteNurseryWish(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteKinderWish(int no) {
		return mapper.deleteKinderWish(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteAllNurseryWish() {
		return mapper.deleteAllNurseryWish();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteAllKinderWish() {
		return mapper.deleteAllKinderWish();
	}
	
	
	// ============================== mypage4 - 불편사항 신고 조회 ==============================
	
	// 어린이집 - USER
	public List<Claim> getNurseryClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectNurseryClaimList(param);
	}
	
	public int getNurseryClaimCount(Map<String, String> map) {
		return mapper.selectNurseryClaimCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteNurseryClaim(int no, String rootPath) {
		Claim claim = mapper.selectNurseryClaimByNo(no);
		deleteClaimFile(rootPath + "\\" + claim.getRenamedFileName());
		return mapper.deleteNurseryClaim(no);
	}
	
	
	// 유치원 - USER
	public List<Claim> getKinderClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectKinderClaimList(param);
	}
	
	public int getKinderClaimCount(Map<String, String> map) {
		return mapper.selectKinderClaimCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteKinderClaim(int no, String rootPath) {
		Claim claim = mapper.selectKinderClaimByNo(no);
		deleteClaimFile(rootPath + "\\" + claim.getRenamedFileName());
		return mapper.deleteKinderClaim(no);
	}
	
	
	// 어린이집 - TEACHER
	public List<Claim> getTNurseryClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectTNurseryClaimList(param);
	}
	
	public int getTNurseryClaimCount(Map<String, String> map) {
		return mapper.selectTNurseryClaimCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateTNurseryClaim(int no) {
		return mapper.updateTNurseryClaim(no);
	}

	
	// 유치원 - TEACHER
	public List<Claim> getTKinderClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectTKinderClaimList(param);
	}
	
	public int getTKinderClaimCount(Map<String, String> map) {
		return mapper.selectTKinderClaimCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateTKinderClaim(int no) {
		return mapper.updateTKinderClaim(no);
	}
	
	
	// delete
	public void deleteClaimFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	
	// ADMIN
	public List<Claim> getANurseryClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectANurseryClaimList(param);
	}
	
	public List<Claim> getAKinderClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectAKinderClaimList(param);
	}
	
	public int getANurseryClaimCount() {
		return mapper.selectANurseryClaimCount();
	}
	public int getAKinderClaimCount() {
		return mapper.selectAKinderClaimCount();
	}
	
	
	
	

	
	// ============================== mypage5 - 내가 쓴 게시글 ==============================
	
	public List<Board> getBoardList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectBoardList(param);
	}
	
	public int getBoardCount(Map<String, String> param) {
		return mapper.selectBoardCount(param);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Board findBoardByNo(int boardNo) {
		Board board = mapper.selectBoardByNo(boardNo); 
		board.setReadcount(board.getReadcount() + 1);  
		mapper.updateReadCount(board);
		return board; 
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateBoard(Board board) {
		return mapper.updateBoard(board);
	}
	
	public String saveBoardFile(MultipartFile upFile, String savePath) {
		File folder = new File(savePath);
		
		// 폴더 없으면 만드는 코드
		if(folder.exists() == false) {
			folder.mkdir();
		}
		System.out.println("savePath : " + savePath);
		
		// 파일이름을 랜덤하게 바꾸는 코드, test.txt -> 20221213_1728291212.txt
		String originalFileName = upFile.getOriginalFilename();
		String reNameFileName = 
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
		reNameFileName += originalFileName.substring(originalFileName.lastIndexOf("."));
		String reNamePath = savePath + "/" + reNameFileName;
		
		try {
			// 실제 파일이 저장되는 코드
			upFile.transferTo(new File(reNamePath));
		} catch (Exception e) {
			return null;
		}
		return reNameFileName;
	}
	
	public void deleteBoardFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteBoard(int no, String rootPath) {
		Board board = mapper.selectBoardByNo(no);
		deleteBoardFile(rootPath + "\\" + board.getRenamedFileName());
		return mapper.deleteBoard(no);
	}
	
	
	// ============================== mypage6 - 대여 내역 조회 ==============================
	// USER
	public List<Rental> getRentalList(PageInfo pageInfo,Map<String, String> paramMap){
		paramMap.put("limit", "" + pageInfo.getListLimit());
		paramMap.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectRentalList(paramMap);
	}
	// mypage6 바뀐다음 예)
	public List<Pay> getRentalList2(int memberNo){
		List<Pay> payList = new ArrayList<Pay>();
		payList = mapper.selectRentalList2(memberNo);
		
		int cnt = 1;
		for (Pay pay : payList) {
			String toyNoListSt = pay.getToyNo(); // ex) 1303,1302,1304
			String startDateSt = pay.getStartDate(); // ex) 23-1-2,23-1-3,23-1-4
			String endDateSt = pay.getEndDate(); // ex) 23-1-5,23-1-6,23-1-6
			String[] toyNoEach = toyNoListSt.split(",");
			String[] startDateEach = startDateSt.split(",");
			String[] endDateEach = endDateSt.split(",");
			List<Toy> toyList = new ArrayList<Toy>();
			List<String> startDateList = new ArrayList<String>();
			List<String> endDateList = new ArrayList<String>();
			List<Integer> cntList = new ArrayList<Integer>();
			for(int i=0; i<toyNoEach.length; i++){
				toyList.add(toyMapper.selectToyByNo(Integer.parseInt(toyNoEach[i])));
				startDateList.add(startDateEach[i]);
				endDateList.add(endDateEach[i]);
				cntList.add(cnt);
				cnt++;
			}
			pay.setToyList(toyList);
			pay.setStartDateList(startDateList);
			pay.setEndDateList(endDateList);
			pay.setCntList(cntList);
		}

		return payList;
	}
	
	public int getRentalCount(int memberNo) {
		List<Pay> payList = new ArrayList<Pay>();
		payList = mapper.selectRentalList2(memberNo);
		
		int cnt = 1;
		for (Pay pay : payList) {
			String toyNoListSt = pay.getToyNo(); // ex) 1303,1302,1304
			String startDateSt = pay.getStartDate(); // ex) 23-1-2,23-1-3,23-1-4
			String endDateSt = pay.getEndDate(); // ex) 23-1-5,23-1-6,23-1-6
			String[] toyNoEach = toyNoListSt.split(",");
			String[] startDateEach = startDateSt.split(",");
			String[] endDateEach = endDateSt.split(",");
			List<Toy> toyList = new ArrayList<Toy>();
			List<String> startDateList = new ArrayList<String>();
			List<String> endDateList = new ArrayList<String>();
			List<Integer> cntList = new ArrayList<Integer>();
			for(int i=0; i<toyNoEach.length; i++){
				toyList.add(toyMapper.selectToyByNo(Integer.parseInt(toyNoEach[i])));
				startDateList.add(startDateEach[i]);
				endDateList.add(endDateEach[i]);
				cntList.add(cnt);
				cnt++;
			}
			pay.setToyList(toyList);
			pay.setStartDateList(startDateList);
			pay.setEndDateList(endDateList);
			pay.setCntList(cntList);
		}
		return cnt - 1;
	}
	
	public Rental findByRentalNo(int no) {
		return mapper.selectRentalByNo(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateToyType(int no) {
		return mapper.updateToyType(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateToyStatus(int no) {
		return mapper.updateToyStatus(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateRental(int no) {
		return mapper.updateRental(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteRental(int no) {
		return mapper.deleteRental(no);
	}
	
	
	// ADMIN
	public List<Pay> getRentalList3(){
		List<Pay> payList = new ArrayList<Pay>();
		payList = mapper.selectRentalList3();
		
		int cnt = 1;
		for (Pay pay : payList) {
			String toyNoListSt = pay.getToyNo(); // ex) 1303,1302,1304
			String startDateSt = pay.getStartDate(); // ex) 23-1-2,23-1-3,23-1-4
			String endDateSt = pay.getEndDate(); // ex) 23-1-5,23-1-6,23-1-6
			String[] toyNoEach = toyNoListSt.split(",");
			String[] startDateEach = startDateSt.split(",");
			String[] endDateEach = endDateSt.split(",");
			List<Toy> toyList = new ArrayList<Toy>();
			List<String> startDateList = new ArrayList<String>();
			List<String> endDateList = new ArrayList<String>();
			List<Integer> cntList = new ArrayList<Integer>();
			for(int i=0; i<toyNoEach.length; i++){
				toyList.add(toyMapper.selectToyByNo(Integer.parseInt(toyNoEach[i])));
				startDateList.add(startDateEach[i]);
				endDateList.add(endDateEach[i]);
				cntList.add(cnt);
				cnt++;
			}
			pay.setToyList(toyList);
			pay.setStartDateList(startDateList);
			pay.setEndDateList(endDateList);
			pay.setCntList(cntList);
		}

		return payList;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateToyStatusT(int no) {
		return mapper.updateToyStatusT(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateToyStatusY(int no) {
		return mapper.updateToyStatusY(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateToyStatusN(int no) {
		return mapper.updateToyStatusN(no);
	}
	
	
	// ============================== mypage7 - 장바구니 ==============================
	public List<Cart> getCartList(PageInfo pageInfo,Map<String, String> paramMap){
		paramMap.put("limit", "" + pageInfo.getListLimit());
		paramMap.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectCartList(paramMap);
	}
	
	public int getCartCount(Map<String, String> map) {
		return mapper.selectCartCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveCart(Cart cart) {
		int result = 0;
		if(cart.getNo() == 0) {
			result = mapper.insertCart(cart);
		}else {
			result = mapper.updateCart(cart);
		}
		return result;
	}
	
	public Cart findByNo(int no) {
		Cart cart = mapper.selectCartByNo(no);
		return cart;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteCart(int no) {
		return mapper.deleteCart(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteAllCart() {
		return mapper.deleteAllCart();
	}
	
}
