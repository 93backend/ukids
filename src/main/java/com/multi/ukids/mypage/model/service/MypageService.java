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
	// mypage2
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
	
	
	
	
	
	// mypage3
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
	
	
	//mypage4
	public String getselectNurseryName(int no) {
		return mapper.selectNurseryName(no);
	}
	
	public String getselectKinderName(int no) {
		return mapper.selectKinderName(no);
	}
	
	public List<Claim> getNurseryClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectNurseryClaimList(param);
	}
	
	public List<Claim> getKinderClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectKinderClaimList(param);
	}
	
	public List<Claim> getTNurseryClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectTNurseryClaimList(param);
	}
	
	public List<Claim> getTKinderClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectTKinderClaimList(param);
	}
	
	public int getNurseryClaimCount(Map<String, String> map) {
		return mapper.selectNurseryClaimCount(map);
	}
	public int getKinderClaimCount(Map<String, String> map) {
		return mapper.selectKinderClaimCount(map);
	}
	public int getTNurseryClaimCount(Map<String, String> map) {
		return mapper.selectTNurseryClaimCount(map);
	}
	public int getTKinderClaimCount(Map<String, String> map) {
		return mapper.selectTKinderClaimCount(map);
	}
	
	public String saveClaimFile(MultipartFile file, String savePath) {
		File folder = new File(savePath);
		
		// 폴더 없으면 만드는 코드
		if(folder.exists() == false) {
			folder.mkdir();
		}
		
		System.out.println("savePath : " + savePath);
		
		// 파일이름을 랜덤하게 바꾸는 코드, test.txt -> 20221213_1728291212.txt
		String originalFileName = file.getOriginalFilename();
		String reNameFileName = 
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
		reNameFileName += originalFileName.substring(originalFileName.lastIndexOf("."));
		String reNamePath = savePath + "/" + reNameFileName;
		
		try {
			// 실제 파일이 저장되는 코드
			file.transferTo(new File(reNamePath));
		} catch (Exception e) {
			return null;
		}
		return reNameFileName;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Claim findByNoNursery(int no) {
		Claim claim = mapper.selectNurseryClaimByNo(no);
		claim.setReadCount(claim.getReadCount() + 1);
		mapper.updateReadCountNursery(claim);
		return claim;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Claim findByNoKinder(int no) {
		Claim claim = mapper.selectKinderClaimByNo(no);
		claim.setReadCount(claim.getReadCount() + 1);
		mapper.updateReadCountKinder(claim);
		return claim;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveNurseryClaim(Claim claim) {
		int result = 0;
		if(claim.getNo() == 0) {
			result = mapper.insertNurseryClaim(claim);
		} else {
			result = mapper.updateNurseryClaim(claim);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveKinderClaim(Claim claim) {
		int result = 0;
		if(claim.getNo() == 0) {
			result = mapper.insertKinderClaim(claim);
		} else {
			result = mapper.updateKinderClaim(claim);
		}
		return result;
	}
	
	public void deleteClaimFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteNurseryClaim(int no, String rootPath) {
		Claim claim = mapper.selectNurseryClaimByNo(no);
		deleteClaimFile(rootPath + "\\" + claim.getRenamedFileName());
		return mapper.deleteNurseryClaim(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteKinderClaim(int no, String rootPath) {
		Claim claim = mapper.selectKinderClaimByNo(no);
		deleteClaimFile(rootPath + "\\" + claim.getRenamedFileName());
		return mapper.deleteKinderClaim(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateTNurseryClaim(int no) {
		return mapper.updateTNurseryClaim(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateTKinderClaim(int no) {
		return mapper.updateTKinderClaim(no);
	}

	
	// mypage5
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
	
	
	// mypage6
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
	public int getRentalCount(Map<String, String> map) {
		return mapper.selectRentalCount(map);
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
	
	@Transactional(rollbackFor = Exception.class)
	public int updateRental(int no) {
		return mapper.updateRental(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteRental(int no) {
		return mapper.deleteRental(no);
	}
	
	
	
	

	
	
	// mypage7
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
