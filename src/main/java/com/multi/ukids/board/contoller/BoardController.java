package com.multi.ukids.board.contoller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.multi.ukids.board.model.service.BoardService;
import com.multi.ukids.board.model.vo.B_Reply;
import com.multi.ukids.board.model.vo.Board;
import com.multi.ukids.board.model.vo.Good;
import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	final static private String savePath = "c:\\UKIDS\\";
	
	@GetMapping("/each")
	public String each(Model model, String type, Map<String, String> paramMap) {
		list(model, type, paramMap);
		return "basic/each";
	}
	
	@GetMapping("/community/{type}")
	public String list(Model model,
		@PathVariable("type") String type,
		@RequestParam Map<String, String> param) {
		
		int page = 1;

		// 탐색할 맵을 선언
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String search = param.get("search");
			if(search != null) {
				searchMap.put("search", search);
			}
			searchMap.put("type", type);
			page = Integer.parseInt(param.get("page"));
		} catch (Exception e) {}
		
		int boardCount = service.getBoardCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 5, boardCount, 10);
		List<Board> list = service.getBoardList(pageInfo, searchMap);
		
		log.info(type + " 목록 : " + list);
		
		int[] num = new int[10];
		num[0] = (page-1) * 10 + 1;
		for(int i = 1; i < num.length; i++) {
			num[i] = num[i-1] + 1;
		}
		
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		model.addAttribute("param", param);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("num", num);
		
		return "community";
	}
	
	@GetMapping("/community/view-community")
	public String view(Model model, @RequestParam("no") int no, @RequestParam("type") String type,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember
	) {
		Board board = service.findByNo(no);
		if(board == null) {
			return "redirect:error";
		}
		int good = 0;
		if(loginMember != null) {
			Good g = new Good(no, loginMember.getMemberNo());
			good = service.getGood(g);
		}
		log.info("상세보기 : " + board);
		model.addAttribute("type", type);
		model.addAttribute("board", board);
		model.addAttribute("good", good);
		model.addAttribute("replyList", board.getReplies());
		return "view-community";
	}
	
	@GetMapping("/community/write")
	public String writeView(Model model, @RequestParam("type")String type) {
		model.addAttribute("type", type);
		return "write-comm";
	}
	
	@PostMapping("/community/write")
	public String writeBoard(Model model, HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@ModelAttribute Board board,
			@RequestParam Map<String, String> param,
			@RequestParam("upfile") MultipartFile upfile
			) {
		String type = param.get("type");
		board.setMemberNo(loginMember.getMemberNo());
		board.setId(loginMember.getId());
		
		// 파일 저장 로직
		if(upfile != null && upfile.isEmpty() == false) {
			String renameFileName = service.saveFile(upfile, savePath); 
			
			if(renameFileName != null) {
				board.setOriginalFileName(upfile.getOriginalFilename());
				board.setRenamedFileName(renameFileName);
			}
		}
		
		log.info("board : " + board);
		int result = service.saveBoard(board);

		if(result > 0) {
			model.addAttribute("msg", "게시글이 등록 되었습니다.");
		} else {
			model.addAttribute("msg", "게시글 작성에 실패하였습니다.");
		}
		
		model.addAttribute("location", "/community/" + type);
		model.addAttribute("type", type);
		
		return "common/msg";
	}

	@PostMapping("/reply")
	public String writeReply(Model model, 
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@ModelAttribute B_Reply reply
	) {
		reply.setMemberNo(loginMember.getMemberNo());
		int result = service.saveReply(reply);
		
		if(result > 0) {
			model.addAttribute("msg", "댓글이 등록되었습니다.");
		}else {
			model.addAttribute("msg", "댓글 등록에 실패하였습니다.");
		}
		model.addAttribute("location", "/community/view-community?type=freeboard&no="+reply.getBoardNo());
		return "common/msg";
	}
	
	@RequestMapping("/community/delete")
	public String deleteBoard(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int boardNo,
			@RequestParam Map<String, String> param
			) {
		String type = param.get("type");
		int result = service.deleteBoard(boardNo, savePath);
	
		if(result > 0) {
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
		}else {
			model.addAttribute("msg", "게시글 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/community/" + type + "?type=" + type);
		
		return "common/msg";
	}
	
	@RequestMapping("/replyDel")
	public String deleteReply(Model model, 
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		int replyNo, int boardNo
	){

		int result = service.deleteReply(replyNo);
		
		if(result > 0) {
			model.addAttribute("msg", "댓글이 삭제되었습니다.");
		}else {
			model.addAttribute("msg", "댓글 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/community/view-community?type=freeboard&no=" + boardNo);
		return "/common/msg";
	}
	
	
	@GetMapping("/community/update")
	public String updateView(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam("no") int no, @RequestParam Map<String, String> param
			) {
		String type = param.get("type");
		Board board = service.findByNo(no);
		
		model.addAttribute("board",board);
		model.addAttribute("type",type);
		return "update";
	}

	@PostMapping("/community/update")
	public String updateBoard(Model model, HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@ModelAttribute Board board,
			@RequestParam Map<String, String> param,
			@RequestParam(value="reloadFile", required=false) MultipartFile reloadFile
			) {
		System.out.println(board);
		String type = param.get("type");
		
		board.setMemberNo(loginMember.getMemberNo());
		
		// 파일 저장 로직
		if(reloadFile != null && reloadFile.isEmpty() == false) {
			// 기존 파일이 있는 경우 삭제
			if(board.getRenamedFileName() != null) {
				service.deleteFile(savePath + "/" +board.getRenamedFileName());
			}
			
			String renameFileName = service.saveFile(reloadFile, savePath); // 실제 파일 저장하는 로직
			
			if(renameFileName != null) {
				board.setOriginalFileName(reloadFile.getOriginalFilename());
				board.setRenamedFileName(renameFileName);
			}
		}

		int result = service.saveBoard(board);
		
		if(result > 0) {
			model.addAttribute("msg", "게시글 수정되었습니다.");
		}else {
			model.addAttribute("msg", "게시글 수정에 실패하였습니다.");
		}
		model.addAttribute("location", "/community/" + type + "?no=" +board.getBoardNo()+"&type=" + type);
		
		return "common/msg";
	}
	
	@GetMapping("/community/file/{fileName}")
	@ResponseBody
	public Resource downloadImage(@PathVariable("fileName") String fileName, Model model) throws IOException {
		return new UrlResource("file:" + savePath + fileName);
	}
	
	@RequestMapping("/community/fileDown")
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
	
	// 좋아요
	@PostMapping("/community/addGood")
	public ResponseEntity<Map<String, Object>> addGood(int no, int memberNo) {
		log.info("좋아요 추가 : " + no + " " + memberNo);
		
		Good good = new Good(no, memberNo);
		
		int result = service.saveGood(good);
		result += service.updateGoodPlus(no);
		
		boolean add;
		if(result > 1) {
			add = true;
		} else {
			add = false;
		}
		
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("add", add);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	// 좋아요 취소
	@PostMapping("/community/deleteGood")
	public ResponseEntity<Map<String, Object>> deleteGood(int no, int memberNo) {
		log.info("좋아요 취소 : " + no + " " + memberNo);
		
		Good good = new Good(no, memberNo);
		
		int result = service.deleteGood(good);
		result += service.updateGoodMinus(no);
		
		boolean remove;
		if(result > 1) {
			remove = true;
		} else {
			remove = false;
		}
		
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("remove", remove);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}

}

