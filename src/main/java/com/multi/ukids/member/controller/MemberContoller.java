package com.multi.ukids.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.multi.ukids.kakao.KaKaoService;
import com.multi.ukids.mail.vo.MailDto;
import com.multi.ukids.mail.vo.SendEmailService;
import com.multi.ukids.member.model.service.MemberService;
import com.multi.ukids.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes("loginMember")
@Controller
public class MemberContoller {
	
	@Autowired MemberService service;
	@Autowired KaKaoService kakaoService;
	@Autowired SendEmailService sendEmailService;
	
	
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
	
	@GetMapping("/join")
	public String join() {
		log.info("회원가입 페이지 요청");
		return "join";
	}
	
	@GetMapping("/kakaoLogin")
	public String kakaoLogin(Model model, String code) {
		log.info("카카오 로그인 요청");
		if (code != null) {
			try {
				String loginUrl = "http://localhost/kakaoLogin";
				String token = kakaoService.getToken(code, loginUrl);
				Map<String, Object> map = kakaoService.getUserInfo(token);
				String kakaoToken = (String) map.get("id");
				Member loginMember = service.loginKaKao(kakaoToken);

				if (loginMember != null) { // 로그인 성공
					model.addAttribute("loginMember", loginMember); // 세션으로 저장되는 코드, 이유: @SessionAttributes
					return "redirect:/";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("msg", "로그인에 실패하였습니다.");
		model.addAttribute("location", "/");
		return "common/msg";
	}
	
	@PostMapping("/member/enroll")
	public ModelAndView enroll(ModelAndView model, @ModelAttribute Member member) { //@ModelAttribute 생략 가능
		log.info("회원가입, member : " + member);
		
		
		int result = 0;
		try {
			result = service.save(member);
			System.out.println("결과 : " + result);
		} catch (Exception e) {
			result = -1;
		}
		
		if(result > 0) {
			model.addObject("msg","회원가입에 성공하였습니다.");
			model.addObject("location","/");
		}else {
			if (member.getName().equals("") || member.getName().equals(" ")) {
				model.addObject("msg", "이름을 입력하지 않았거나 잘못된 형식을 입력하였습니다.");
				model.addObject("location","/");
			} else {
				model.addObject("msg","회원가입에 실패하였습니다. 다시 확인 해주세요.");
				model.addObject("location","/");
			}
		}
		model.setViewName("common/msg");
		return model;
	}
	
	@GetMapping("/member/kakaoEnroll")
	public String enrollKakao(Model model, String code) {
		log.info("가입 페이지 요청");
		if(code != null) {
			try {
				String enrollUrl = "http://localhost/member/kakaoEnroll";
				System.out.println("code : " + code);
				String token = kakaoService.getToken(code, enrollUrl);
				System.out.println("token : " + token);
				Map<String, Object> map = kakaoService.getUserInfo(token);
				System.out.println(map);
				model.addAttribute("kakaoMap",map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "join";
	}
	
	// AJAX 회원아이디 중복 검사부
	@GetMapping("/member/idCheck")
	public ResponseEntity<Map<String, Object>> idCheck(String id){
		log.info("아이디 중복 확인 : " + id);
		
		boolean result = service.validate(id);
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("validate", result);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	@GetMapping("/member/recovery")
	public String passwordRecovery() {
		return "recovery";
	}
	
	@GetMapping("/index")
	public String index() {
		return "redirect:/";
	}
	
	@GetMapping("/member/findPw")
	public @ResponseBody Map<String, Boolean> pw_find(String name, String email) {
		log.info("pw_find 호출!!");
		log.info("name : " + name + ", email : " + email);
		Map<String, Boolean> json = new HashMap<>();
		
		boolean pwFindCheck = (service.findByNameAndEmail(name, email)) != null ? true : false;
		System.out.println("메일 조회 결과 : " + pwFindCheck);
		
		json.put("check", pwFindCheck);
		System.out.println(json);
		return json;
		
	}
		
	
	  @PostMapping("/member/findPw/sendEmail")
	  public @ResponseBody void sendEmail(String name, String email) {
		  log.info("sendEmail 호출!!");
		  log.info("name : " + name + ", email : " + email);
		  MailDto dto = sendEmailService.createMailAndChangePassword(name, email);
		  sendEmailService.mailSend(dto);
		  System.out.println("메일 발송 완료!!!");
		  
	  }
	  


	
}
