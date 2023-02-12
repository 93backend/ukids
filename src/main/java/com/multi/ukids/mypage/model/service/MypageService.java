package com.multi.ukids.mypage.model.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.multi.ukids.toy.model.vo.Cart;
import com.multi.ukids.toy.model.vo.Rental;
import com.multi.ukids.wish.model.vo.Wish;

@Service
public class MypageService {
	@Autowired
	private MypageMapper mapper;
	
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
	
	public int getNurseryClaimCount(Map<String, String> map) {
		return mapper.selectNurseryClaimCount(map);
	}
	public int getKinderClaimCount(Map<String, String> map) {
		return mapper.selectKinderClaimCount(map);
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
