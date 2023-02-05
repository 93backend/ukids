package com.multi.ukids.claim.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.claim.model.vo.Claim;

@Mapper
public interface ClaimMapper {
	String selectNurseryName(int no);
	List<Claim> selectNurseryClaimList(Map<String, String> map);
	int selectNurseryClaimCount(int no);
	Claim selectNurseryClaimByNo(int no);
	int updateReadCountNursery(Claim claim);
	int insertNurseryClaim(Claim claim);
	int updateNurseryClaim(Claim claim);
	int deleteNurseryClaim(int no);
	
	String selectKinderName(int no);
	List<Claim> selectKinderClaimList(Map<String, String> map);
	int selectKinderClaimCount(int no);
	Claim selectKinderClaimByNo(int no);
	int updateReadCountKinder(Claim claim);
	int insertKinderClaim(Claim claim);
	int updateKinderClaim(Claim claim);
	int deleteKinderClaim(int no);
}
