package com.multi.ukids.claim.model.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.multi.ukids.claim.model.mapper.ClaimMapper;
import com.multi.ukids.claim.model.vo.Claim;
import com.multi.ukids.common.util.PageInfo;

@Service
public class ClaimService {
	@Autowired
	private ClaimMapper mapper;
	
	public String getselectNurseryName(int no) {
		return mapper.selectNurseryName(no);
	}
	
	public String getselectKinderName(int no) {
		return mapper.selectKinderName(no);
	}
	
	public List<Claim> getNurseryClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectNurseryClaimList(param);
	}
	
	public List<Claim> getKinderClaimList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectKinderClaimList(param);
	}
	
	public int getNurseryClaimCount(int no) {
		return mapper.selectNurseryClaimCount(no);
	}
	public int getKinderClaimCount(int no) {
		return mapper.selectKinderClaimCount(no);
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
	
	@Transactional(rollbackFor = Exception.class)
	public Claim findByNoNursery(int no) {
		Claim claim = mapper.selectNurseryClaimByNo(no);
		claim.setReadCount(claim.getReadCount() + 1);
		mapper.updateReadCountNursery(claim);
		return claim;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Claim findByNoKinder(int no) {
		Claim claim = mapper.selectKinderClaimByNo(no);
		claim.setReadCount(claim.getReadCount() + 1);
		mapper.updateReadCountKinder(claim);
		return claim;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveNurseryClaim(Claim claim) {
		int result = 0;
		if(claim.getNo() == 0) {
			result = mapper.insertNurseryClaim(claim);
		} else {
			result = mapper.updateNurseryClaim(claim);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveKinderClaim(Claim claim) {
		int result = 0;
		if(claim.getNo() == 0) {
			result = mapper.insertKinderClaim(claim);
		} else {
			result = mapper.updateKinderClaim(claim);
		}
		return result;
	}
	
	public void deleteFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteNurseryClaim(int no, String rootPath) {
		Claim claim = mapper.selectNurseryClaimByNo(no);
		deleteFile(rootPath + "\\" + claim.getRenamedFileName());
		return mapper.deleteNurseryClaim(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteKinderClaim(int no, String rootPath) {
		Claim claim = mapper.selectKinderClaimByNo(no);
		deleteFile(rootPath + "\\" + claim.getRenamedFileName());
		return mapper.deleteKinderClaim(no);
	}

}