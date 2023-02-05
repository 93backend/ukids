package com.multi.ukids.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.multi.ukids.member.model.service.MemberService;
import com.multi.ukids.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes("loginMember")
@Controller
public class MemberContoller {
	
	@Autowired MemberService service;
	
	@PostMapping("/login")
	String login(Model model, String userId, String userPwd) {
		log.info("id : " + userId + ", pwd : " + userPwd);
		Member loginMember = service.login(userId, userPwd);
		
		// 로그인 성공
		if(loginMember != null) {
			// 세션에 로그인멤버 저장
			model.addAttribute("loginMember", loginMember);
			System.out.println("아이디 : " + loginMember.getId());
			System.out.println("비밀번호 : " + loginMember.getPassword());
			return "redirect:/";
		} else { // 로그인 실패
			model.addAttribute("msg", "로그인에 실패하였습니다.");
			model.addAttribute("location", "/");
			return "common/msg";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(SessionStatus status) { // status : 세션의 상태 확인과 해제가 가능한 클래스
		log.info("status : " + status.isComplete());
		status.setComplete();
		log.info("status : " + status.isComplete());
		return "redirect:/";
	}
	
	@RequestMapping("/join")
	public String join() {
		log.info("회원가입 페이지 요청");
		return "join";
	}
	
}
