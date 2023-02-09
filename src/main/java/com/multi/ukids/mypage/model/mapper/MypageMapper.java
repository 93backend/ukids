package com.multi.ukids.mypage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.board.model.vo.Board;
import com.multi.ukids.claim.model.vo.Claim;
import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.nursery.model.vo.NAdmission;
import com.multi.ukids.toy.model.vo.Cart;
import com.multi.ukids.toy.model.vo.Rental;
import com.multi.ukids.wish.model.vo.Wish;


@Mapper
public interface MypageMapper {
	
	// mypage2 - 입소 신청 조회
	List<KAdmission> selectKAdmissionList(Map<String, Object> map);
	int selectKAdmissionCount(Map<String, Object> map);
	int deleteKAdmission(int no);
	
	List<NAdmission> selectNAdmissionList(Map<String, Object> map);
	int selectNAdmissionCount(Map<String, Object> map);
	int deleteNAdmission(int no);
	
	// mypage3 - 찜
	List<Wish> selectNurseryWishList(Map<String, Object> map);
	int selectNurseryWishCount(Map<String, Object> map);
	int updateNurseryWish(Wish wish);
	int deleteNurseryWish(int no);
	
	List<Wish> selectKinderWishList(Map<String, Object> map);
	int selectKinderWishCount(Map<String, Object> map);
	int updateKinderWish(Wish wish);
	int deleteKinderWish(int no);
	
	//mypage4 - 불편사항 신고목록
	String selectNurseryName(int no);
	List<Claim> selectNurseryClaimList(Map<String, Object> map);
	int selectNurseryClaimCount(Map<String, Object> map);
	Claim selectNurseryClaimByNo(int no);
	int updateReadCountNursery(Claim claim);
	int insertNurseryClaim(Claim claim);
	int updateNurseryClaim(Claim claim);
	int deleteNurseryClaim(int no);
	
	String selectKinderName(int no);
	List<Claim> selectKinderClaimList(Map<String, Object> map);
	int selectKinderClaimCount(Map<String, Object> map);
	Claim selectKinderClaimByNo(int no);
	int updateReadCountKinder(Claim claim);
	int insertKinderClaim(Claim claim);
	int updateKinderClaim(Claim claim);
	int deleteKinderClaim(int no);
	
	// mypage6 - 내가 쓴 게시글
	List<Board> selectBoardList(Map<String, Object> map);
	int selectBoardCount(Map<String, Object> map);
	Board selectBoardByNo(int no);
	int updateBoard(Board board);
	int updateReadCount(Board board);
	int deleteBoard(int no);
	
	// mypage6 - 대여 내역 조회
	List<Rental> selectRentalList(Map<String, Object> map);
	int selectRentalCount(Map<String, Object> map);
	Rental selectRentalByNo(int no);
	int updateRental(Rental rental);
	int deleteRental(int no);
	
	// mypage7 - 장바구니
	List<Cart> selectCartList(Map<String, Object> map);
	int selectCartCount(Map<String, Object> map);
	Cart selectCartByNo(int no);
	int insertCart(Cart cart);
	int updateCart(Cart cart);
	int deleteCart(int no);
}
