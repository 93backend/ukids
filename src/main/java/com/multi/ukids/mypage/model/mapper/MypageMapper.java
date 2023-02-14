package com.multi.ukids.mypage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.board.model.vo.Board;
import com.multi.ukids.claim.model.vo.Claim;
import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.nursery.model.vo.NAdmission;
import com.multi.ukids.toy.model.vo.Cart;
import com.multi.ukids.toy.model.vo.Pay;
import com.multi.ukids.toy.model.vo.Rental;
import com.multi.ukids.wish.model.vo.Wish;


@Mapper
public interface MypageMapper {
	
	// mypage2 - 입소 신청 조회
	List<KAdmission> selectKAdmissionList(Map<String, String> map);
	List<KAdmission> selectTKAdmissionList(Map<String, String> map);
	int selectKAdmissionCount(Map<String, String> map);
	int selectTKAdmissionCount(Map<String, String> map);
	int deleteKAdmission(int no);
	int updateTKinderAdmissionY(int no);
	int updateTKinderAdmissionN(int no);
	
	List<NAdmission> selectNAdmissionList(Map<String, String> map);
	List<NAdmission> selectTNAdmissionList(Map<String, String> map);
	int selectNAdmissionCount(Map<String, String> map);
	int selectTNAdmissionCount(Map<String, String> map);
	int deleteNAdmission(int no);
	int updateTNurseryAdmissionY(int no);
	int updateTNurseryAdmissionN(int no);
	
	// mypage3 - 찜
	List<Wish> selectNurseryWishList(Map<String, String> map);
	List<Wish> selectAllNAdmissionList(Map<String, String> map);
	int selectNurseryWishCount(Map<String, String> map);
	int updateNurseryWish(Wish wish);
	int deleteNurseryWish(int no);
	int deleteAllNurseryWish();
	
	List<Wish> selectKinderWishList(Map<String, String> map);
	int selectKinderWishCount(Map<String, String> map);
	int updateKinderWish(Wish wish);
	int deleteKinderWish(int no);
	int deleteAllKinderWish();
	
	//mypage4 - 불편사항 신고목록
	String selectNurseryName(int no);
	List<Claim> selectNurseryClaimList(Map<String, String> map);
	List<Claim> selectTNurseryClaimList(Map<String, String> map);
	int selectNurseryClaimCount(Map<String, String> map);
	int selectTNurseryClaimCount(Map<String, String> map);
	Claim selectNurseryClaimByNo(int no);
	int updateReadCountNursery(Claim claim);
	int insertNurseryClaim(Claim claim);
	int updateNurseryClaim(Claim claim);
	int updateTNurseryClaim(int no);
	int deleteNurseryClaim(int no);
	
	String selectKinderName(int no);
	List<Claim> selectKinderClaimList(Map<String, String> map);
	List<Claim> selectTKinderClaimList(Map<String, String> map);
	int selectKinderClaimCount(Map<String, String> map);
	int selectTKinderClaimCount(Map<String, String> map);
	Claim selectKinderClaimByNo(int no);
	int updateReadCountKinder(Claim claim);
	int insertKinderClaim(Claim claim);
	int updateKinderClaim(Claim claim);
	int updateTKinderClaim(int no);
	int deleteKinderClaim(int no);
	
	// mypage5 - 내가 쓴 게시글
	List<Board> selectBoardList(Map<String, String> map);
	int selectBoardCount(Map<String, String> map);
	Board selectBoardByNo(int no);
	int updateBoard(Board board);
	int updateReadCount(Board board);
	int deleteBoard(int no);
	
	// mypage6 - 대여 내역 조회
	List<Rental> selectRentalList(Map<String, String> map);
	List<Pay> selectRentalList2(int memberNo); //변경
	List<Pay> selectRentalList3();
	int selectRentalCount(Map<String, String> map);
	Rental selectRentalByNo(int no);
	int updateToyType(int no);
	int updateToyStatus(int no);
	int updateToyStatusT(int no);
	int updateToyStatusY(int no);
	int updateToyStatusN(int no);
	int updateRental(int no);
	int deleteRental(int no);
	
	// mypage7 - 장바구니
	List<Cart> selectCartList(Map<String, String> map);
	int selectCartCount(Map<String, String> map);
	Cart selectCartByNo(int no);
	int insertCart(Cart cart);
	int updateCart(Cart cart);
	int deleteCart(int no);
	int deleteAllCart();
}
