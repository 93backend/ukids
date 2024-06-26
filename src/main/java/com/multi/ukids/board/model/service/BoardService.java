package com.multi.ukids.board.model.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.multi.ukids.board.model.mapper.BoardMapper;
import com.multi.ukids.board.model.vo.B_Reply;
import com.multi.ukids.board.model.vo.Board;
import com.multi.ukids.board.model.vo.Good;
import com.multi.ukids.common.util.PageInfo;

@Service
public class BoardService {
	@Autowired
	private BoardMapper mapper;
	
	@Transactional(rollbackFor = Exception.class)
	public int saveBoard(Board board) {
		int result = 0;
		if(board.getBoardNo() == 0) {
			result = mapper.insertBoard(board);
		}else {
			result = mapper.updateBoard(board);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveReply(B_Reply reply) {
		return mapper.insertReply(reply);
	}

	public String saveFile(MultipartFile file, String savePath) {
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
	
	public int getBoardCount(Map<String, String> param) {
		return mapper.selectBoardCount(param);
	}
	
	public List<Board> getBoardList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectBoardList(param);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Board findByNo(int boardNo) {
		Board board = mapper.selectBoardByNo(boardNo); 
		board.setReadcount(board.getReadcount() + 1);  
		mapper.updateReadCount(board);
		return board; 
	}
	
	public void deleteFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteBoard(int no, String rootPath) {
		Board board = mapper.selectBoardByNo(no);
		deleteFile(rootPath + "\\" + board.getRenamedFileName());
		return mapper.deleteBoard(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteReply(int no) {
		return mapper.deleteReply(no);
	}
	
	public int getGood(Good good) {
		return mapper.selectGood(good);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveGood(Good good) {
		return mapper.insertGood(good);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateGoodPlus(int no) {
		return mapper.updateGoodPlus(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateGoodMinus(int no) {
		return mapper.updateGoodMinus(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteGood(Good good) {
		return mapper.deleteGood(good);
	}
	
}




