package com.multi.ukids.member.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.multi.ukids.member.model.vo.Member;

@Mapper
public interface MemberMapper {
	
	int selectCount();
	List<Member> selectAll();
	Member selectMember(@Param("id") String id); // @Param : 파라메터임을 알리는 어노테이션. 없어 된다.
	int insertMember(Member member);
	int updateMember(Member member);
	int updatePwd(Map<String, String> map);
	int deleteMember(int no);
	Member selectMemberByKakaoToken(String kakaoToken);

}
