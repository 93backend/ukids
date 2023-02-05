package com.multi.ukids.claim.controller;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.multi.ukids.claim.model.service.ClaimService;
import com.multi.ukids.claim.model.vo.Claim;
import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClaimController {
	@Autowired
	private ClaimService service;
	
	final static private String savePath = "c:\\UKIDS\\";
	
	@GetMapping("/claim-main")
	public String claimMain(Model model, @RequestParam Map<String, String> param) {
		
		String type = param.get("type");
		int no = Integer.parseInt(param.get("no"));
		
		int page = 1;
		
		try {
			if(param.get("page") != null) {
				page = Integer.parseInt((String) param.get("page"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String name = null;
		List<Claim> list = null;
		int count = 0;
		PageInfo pageInfo = null;
		
		if(type.equals("어린이집")) {
			name = service.getselectNurseryName(no);
			count = service.getNurseryClaimCount(no);
			pageInfo = new PageInfo(page, 5, count, 10);
			list = service.getNurseryClaimList(pageInfo, param);
		} else {
			name = service.getselectKinderName(no);
			count = service.getKinderClaimCount(no);
			pageInfo = new PageInfo(page, 5, count, 10);
			list = service.getKinderClaimList(pageInfo, param);
		}
		
		int[] num = new int[10];
		num[0] = page;
		for(int i = 1; i < num.length; i++) {
			num[i] = num[i-1] + 1;
		}
		
		model.addAttribute("type", type);
		model.addAttribute("name", name);
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("num", num);
		
		return "claim-main";
	}
	
	@GetMapping("/claim-view")
	public String claimView(Model model, @RequestParam Map<String, String> param) {
		String type = param.get("type");
		int no = Integer.parseInt(param.get("no"));
		
		String name = "";
		int count = 0;
		Claim claim = null;
		
		if(type.equals("어린이집")) {
			claim = service.findByNoNursery(no);
			name = service.getselectNurseryName(claim.getKnNo());
			count = service.getNurseryClaimCount(claim.getKnNo());
		} else {
			claim = service.findByNoKinder(no);
			name = service.getselectKinderName(claim.getKnNo());
			count = service.getKinderClaimCount(claim.getKnNo());
		}
		
		System.out.println(claim.toString());
		
		model.addAttribute("type", type);
		model.addAttribute("name", name);
		model.addAttribute("count", count);
		model.addAttribute("claim", claim);
		
		return "claim-view";
	}
	
	@GetMapping("/claim-write")
	public String goClaimWrite(Model model, @RequestParam Map<String, String> param) {
		model.addAttribute("param", param);
		return "claim-write";
	}
	
	@PostMapping("/claim-write")
	public String doClaimWrite(Model model, HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@ModelAttribute Claim claim,
		@RequestParam Map<String, String> param,
		@RequestParam("file") MultipartFile file
	) {
		String type = param.get("type");
		int knNo = Integer.parseInt(param.get("knNo"));
		
		claim.setKnNo(knNo);
		claim.setMemberNo(loginMember.getMemberNo());
		
		// 파일 저장
		if(file != null && file.isEmpty() == false) {
			String renameFileName = service.saveFile(file, savePath); 
			
			if(renameFileName != null) {
				claim.setOriginalFileName(file.getOriginalFilename());
				claim.setRenamedFileName(renameFileName);
			}
		}
		
		int result = 0;
		if(type.equals("어린이집")) {
			result = service.saveNurseryClaim(claim);
		} else {
			result = service.saveKinderClaim(claim);
		}
		
		if(result > 0) {
			model.addAttribute("msg", "불편사항이 등록 되었습니다.");
		}else {
			model.addAttribute("msg", "불편사항 작성에 실패하였습니다.");
		}
		model.addAttribute("location", "/claim-main?type=" + type +"&no=" + claim.getKnNo());
		
		return "common/msg";
	}
	
	@GetMapping("/claim-update")
	public String goClaimUpdate(Model model,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
		@RequestParam Map<String, String> param
	) {
		String type = param.get("type");
		int no = Integer.parseInt(param.get("no"));
		Claim claim = null;
		
		if(type.equals("어린이집")) {
			claim = service.findByNoNursery(no);
		} else {
			claim = service.findByNoKinder(no);
		}
		model.addAttribute("param", param);
		model.addAttribute("claim", claim);
		
		return "claim-update";
	}
	
	@PostMapping("/claim-update")
	public String doClaimUpdate(Model model, HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@ModelAttribute Claim claim,
		@RequestParam Map<String, String> param,
		@RequestParam("reloadFile") MultipartFile reloadFile
	) {
		String type = param.get("type");
		
		claim.setMemberNo(loginMember.getMemberNo());
		
		// 파일 저장 로직
		if(reloadFile != null && reloadFile.isEmpty() == false) {
			// 기존 파일이 있는 경우 삭제
			if(claim.getRenamedFileName() != null) {
				service.deleteFile(savePath + "/" +claim.getRenamedFileName());
			}
			
			String renameFileName = service.saveFile(reloadFile, savePath); // 실제 파일 저장하는 로직
			
			if(renameFileName != null) {
				claim.setOriginalFileName(reloadFile.getOriginalFilename());
				claim.setRenamedFileName(renameFileName);
			}
		}
		
		int result = 0;
		
		if(type.equals("어린이집")) {
			result = service.saveNurseryClaim(claim);
		} else {
			result = service.saveKinderClaim(claim);
		}
		
		if(result > 0) {
			model.addAttribute("msg", "불편사항 신고글이 수정되었습니다.");
		} else {
			model.addAttribute("msg", "불편사항 신고글 수정에 실패하였습니다.");
		}
		model.addAttribute("location", "/claim-view?type=" + type +"&no=" + claim.getNo());
		return "common/msg";
	}
	
	@RequestMapping("/claim-delete")
	public String claimDelete(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		String type = param.get("type");
		int no = Integer.parseInt(param.get("no"));
		int knNo = Integer.parseInt(param.get("knNo"));
		
		int result = 0;
		
		if(type.equals("어린이집")) {
			result = service.deleteNurseryClaim(no, savePath);
		} else {
			result = service.deleteKinderClaim(no, savePath);
		}
		
		if(result > 0) {
			model.addAttribute("msg", "불편사항 신고글 삭제가 정상적으로 완료되었습니다.");
		} else {
			model.addAttribute("msg", "불편사항 신고글 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/claim-main?type=" + type +"&no=" + knNo);
		
		return "common/msg";
	}
	
	@GetMapping("/error")
	public String error() {
		return "common/error";
	}
	
	@RequestMapping("/claim/fileDown")
	public ResponseEntity<Resource> fileDown(@RequestParam("oriname") String oriname,
			@RequestParam("rename") String rename, @RequestHeader(name = "user-agent") String userAgent) {
		try {
			Resource resource = new UrlResource("file:" + savePath + rename + "");
			String downName = null;

			// 인터넷 익스플로러 인 경우
			boolean isMSIE = userAgent.indexOf("MSIE") != -1 || userAgent.indexOf("Trident") != -1;

			if (isMSIE) { // 익스플로러 처리하는 방법
				downName = URLEncoder.encode(oriname, "UTF-8").replaceAll("\\+", "%20");
			} else {
				downName = new String(oriname.getBytes("UTF-8"), "ISO-8859-1"); // 크롬
			}
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + downName + "\"")
					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString()).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 실패했을 경우
	}
}
