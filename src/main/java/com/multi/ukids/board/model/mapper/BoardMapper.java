package com.multi.ukids.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.board.model.vo.B_Reply;
import com.multi.ukids.board.model.vo.Board;
import com.multi.ukids.board.model.vo.Good;


@Mapper
public interface BoardMapper {
	List<Board> selectBoardList(Map<String, String> map);
	int selectBoardCount(Map<String, String> map);
	Board selectBoardByNo(int no);
	int insertBoard(Board board);
	int insertReply(B_Reply reply);
	int updateBoard(Board board);
	int updateReadCount(Board board);
	int deleteBoard(int no);
	int deleteReply(int no);
	
	int selectGood(Good good);
	int insertGood(Good good);
	int updateGoodPlus(int no);
	int updateGoodMinus(int no);
	int deleteGood(Good good);
}
