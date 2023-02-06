package com.multi.ukids.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.ukids.member.model.mapper.MemberMapper;
import com.multi.ukids.member.model.vo.Member;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	private BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	public Member login (String id, String pw) {
		System.out.println("로그인 호출!!!");
		Member member = mapper.selectMember(id);
		
		// 멤버가 없는 경우
		if(member == null) {
			return null;
		}
		
		// passwordEncoder 활용법
		System.out.println(member.getPassword()); // hash로 암호화된 코드가 들어있다.
		System.out.println(pwEncoder.encode(pw)); // encode를 통해 평문에서 hash 코드로 변환
		System.out.println(pwEncoder.matches(pw, member.getPassword())); // 평문 변환하고 비교까지

		if (id.equals("admin")) { // admin 테스트를 위한 코드
			return member;
		}

		if (member != null && pwEncoder.matches(pw, member.getPassword()) == true) {
			// 성공
			return member;
		} else {
			// 로그인 실패
			return null;
		}
		
//		if (member != null && member.getPassword().equals(pw)) {
//			return member;
//		} else {
//			return null;
//		}
		
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int save(Member member) {
		int result = 0;
		if(member.getMemberNo() == 0) { // 회원가입
			String encodePW = pwEncoder.encode(member.getPassword());
			member.setPassword(encodePW);
			result = mapper.insertMember(member);
		}else { // 회원 수정
			result = mapper.updateMember(member);
		}
		return result;
	}
	
	public boolean validate(String userId) {
		return this.findById(userId) != null;
	}
	
	public Member findById(String id) {
		return mapper.selectMember(id);
	}

	
	@Transactional(rollbackFor = Exception.class)
	public int delete(int no) {
		return mapper.deleteMember(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updatePwd(Member loginMember, String userPW) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberNo", "" + loginMember.getMemberNo());
		map.put("password", pwEncoder.encode(userPW));
		return mapper.updatePwd(map);
	}
	
	public Member loginKaKao(String kakaoToken) {
		Member member = mapper.selectMemberByKakaoToken(kakaoToken);
		
		if (member != null) {
			// 성공일때!
			return member;
		} else {
			// 인증 실패했을때
			return null;
		}
	}
}
