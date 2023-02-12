package com.multi.ukids.toy.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.member.model.vo.Member;
import com.multi.ukids.mypage.model.service.MypageService;
import com.multi.ukids.toy.model.service.ToyService;
import com.multi.ukids.toy.model.vo.Cart;
import com.multi.ukids.toy.model.vo.Pay;
import com.multi.ukids.toy.model.vo.Rental;
import com.multi.ukids.toy.model.vo.T_Review;
import com.multi.ukids.toy.model.vo.Toy;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("") // 요청 url의 상위 url을 모두 처리할때 사용
@Controller
public class ToyController {
   @Autowired
   private ToyService toyService;
   
   @Autowired
   private MypageService mypageService;
   
   @GetMapping("/toy-main")
      public String toyMain(Model model,Integer page, @RequestParam(required=false) String searchValue,@RequestParam(required=false) List<String> searchType) {
       if(page == null) {
          page = 1;
       }
         Map<String, Object> searchMap = new HashMap<String, Object>(); // 문자열, 리스트를 같이 저장할 수 있도록 <키(문자열), 값(Object)> 으로 저장, Map은 Key로 값(Value)를 추출 가능
         try {
            if(searchValue != null && searchValue.length() > 0) {
               searchMap.put("searchValue", searchValue); // Map에 검색어 스트링으로 저장 key:"searchValue",
               searchMap.put("searchType", searchType); // Map에 검색분류 리스트로 저장
            }
            if(searchType != null) {
               searchMap.put("searchType", searchType); 
            }
         } catch (Exception e) {}         
                  
         int toyCount = toyService.getToyCount(searchMap);
         System.out.println("장난감갯수 : "+toyCount);
         PageInfo pageInfo = new PageInfo(page, 5, toyCount, 9);
         List<Toy> list = toyService.getToyList(pageInfo,searchMap);
         List<String> cate = toyService.getCateList();
         
         model.addAttribute("cate", cate);
         model.addAttribute("list", list);
         model.addAttribute("searchType", searchType);
         model.addAttribute("searchValue", searchValue);
         model.addAttribute("pageInfo", pageInfo);
         return "toy-main";
         
      }
   
   
   @GetMapping("/toy-detail")
   public String toyDetail(Model model, @RequestParam("no") int no) {      
      Toy toy = toyService.findByNo(no);
     
      
      List<T_Review> reviewList = toyService.selectToyReviewByNo(no);
      String tcm = toy.getToyCategoryM();

      List<Toy> smilarToy = toyService.selectSimilarToy(toy);
      System.out.println(toy.getToyCategoryM());
      
      List<Pay> dateToy = toyService.selectPayDate(no);
      

      model.addAttribute("dateToy", dateToy);
      model.addAttribute("toy", toy);
      model.addAttribute("reviewList", reviewList);
      model.addAttribute("smilarToy", smilarToy);
      return "toy-detail";
   }

   @PostMapping("/toyReview")
   public String writeReply(Model model, 
         @SessionAttribute(name = "loginMember", required = false) Member loginMember,
         @ModelAttribute T_Review toyReview
         ) {
      toyReview.setMemberNo(loginMember.getMemberNo());
      log.info("리뷰 작성 요청 Review : " + toyReview);
      
      int result = toyService.saveToyReview(toyReview);
      
      if(result > 0) {
         model.addAttribute("msg", "리뷰가 등록되었습니다.");
      }else {
         model.addAttribute("msg", "리뷰 등록에 실패하였습니다.");
      }
      model.addAttribute("location", "/toy-detail?no="+toyReview.getToyNo());
      return "common/msg";
   }

   @GetMapping("/toyReviewDel")
   public String deleteReply(Model model, 
         @SessionAttribute(name = "loginMember", required = false) Member loginMember,
         int no, int toyNo
         ){
      log.info("리뷰 삭제 요청");
      int result = toyService.deleteToyReview(no);
      
      if(result > 0) {
         model.addAttribute("msg", "리뷰 삭제가 정상적으로 완료되었습니다.");
      }else {
         model.addAttribute("msg", "리뷰 삭제에 실패하였습니다.");
      }
      model.addAttribute("location", "/toy-detail?no=" + toyNo);
      return "/common/msg";
   }
   
   @GetMapping("/pay")
   public String pay(Model model, @SessionAttribute(name = "loginMember") Member loginMember,
		   @RequestParam(required=false)String startDate,@RequestParam(required=false)String endDate, 
		   @RequestParam(required=false)Integer toyNo, @RequestParam(required=false)boolean ListOrNot, 
		   @RequestParam(required=false) List<Integer> CartNoList,HttpSession session) {   
      
      if(ListOrNot == true) {
         Toy toy = toyService.findByNo(toyNo);
         model.addAttribute("toy", toy);
         model.addAttribute("startDate", startDate);
         model.addAttribute("endDate", endDate);
      }else {
    	  List<Cart> toyList = new ArrayList<Cart>();
    	  List<Integer> cartNoList = new ArrayList<Integer>();
    	  for (Integer integer : CartNoList) {
    		  toyList.add(mypageService.findByNo(integer));		
    		  cartNoList.add(mypageService.findByNo(integer).getNo());
    	  }
    	  session.setAttribute("cartNoList", cartNoList);
    	  int pay = 0;
    	  
    		  for (Cart c : toyList) {
    			  System.out.println(c.getToyPay());
    			  int p = (Integer.parseInt(c.getToyPay()) / 10);
    			  int t = Math.round(p / 100) * 100;
    			  if (c.getStartDate() != null || c.getEndDate() != null) {
    				  long sec = (c.getEndDate().getTime() - c.getStartDate().getTime()) / 1000;
    				  int date = (int)(sec / (24*60*60));
    				  c.setDate(date);
    				  t *= date;
    			  }
    			  c.setRealPay(t);
    			  pay += t;

    		  }

    		
    		int totalPay = pay + 1000;
    		
    		System.out.println("List!!!!!!!!!!:::::"+CartNoList.toString());

    	  model.addAttribute("pay", pay);
    	  model.addAttribute("totalPay", totalPay);
    	  model.addAttribute("toy", toyList);
      }
     
      return "pay";
   }

   
   private static int payCount = 0;
   
   @ResponseBody
   @PostMapping(value = "/insertPayForm")
   public String insertPayForm(@SessionAttribute(name = "loginMember", required = false) Member loginMember, HttpSession session, @RequestBody Pay pay) {   
      System.out.println("이름 : " + pay.getName());
      System.out.println("주소 : " + pay.getAddress());
      System.out.println("주소2 : " + pay.getAddress2());
      System.out.println("회원번호 : " + pay.getMemberNo());
      System.out.println("가격 : " + pay.getPrice());
      System.out.println("장난감번호 : " + pay.getToyNo());
//      System.out.println("장난감번호그룹 : " + pay.getToyNoGroup());
//      System.out.println("장난감시작일그룹 : " + pay.getStartDateGroup());
//      System.out.println("장난감반납일그룹 : " + pay.getEndDateGroup());
      session.setAttribute("" + payCount, pay);
      
      return ""+payCount++;
   }
   
   @GetMapping("/success")
   public String success(Model model, @SessionAttribute(name = "loginMember", required = false) Member loginMember, HttpSession session,
                   String orderId, int amount, @RequestParam Map<String, String> requestParams, String paymentKey) throws IOException, InterruptedException, ParseException {   
      HttpRequest request = HttpRequest.newBuilder()
             .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
             .header("Authorization", "Basic dGVzdF9za181R2VQV3Z5Sm5yS1cwUUJ6ZXdFcmdMek45N0VvOg==")
             .header("Content-Type", "application/json")
             .method("POST", HttpRequest.BodyPublishers.ofString("{\"paymentKey\":\""+paymentKey+"\",\"amount\":"+amount+",\"orderId\":\""+orderId+"\"}"))
             .build();
      HttpResponse<String> response;
      
      System.out.println(paymentKey);
      Map <String, Object> resultMap = new HashMap<String,Object>();
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
      JSONParser parser = new JSONParser();
      Object objP = parser.parse( response.body() );
      JSONObject jsonObjP = (JSONObject) objP;//root
      
      resultMap.put("orderName", jsonObjP.get("orderName"));
      
      String[] payNoAry = orderId.split("-");
      
      String payCountNo = payNoAry[1];
      Pay pay =  (Pay) session.getAttribute(""+payCountNo);      
      log.info(":::::::::::::::"+pay);
      toyService.insertPay(pay);
      
      List<Integer> cartNoList = (List<Integer>) session.getAttribute("cartNoList");
      
      if(cartNoList != null) {
    	  for (Integer integer : cartNoList) {
        	  log.info(":::::::::::::::::::::::::::::!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+ integer);
        	  toyService.deleteCart(integer);
          }
      }
      
      String[] toyNoAry = pay.getToyNo().split(",");
      for (String toyNolist : toyNoAry) {
    	  toyService.updateToyType(Integer.parseInt(toyNolist));
      }
      Pay pay2 = toyService.selectPay(pay.getPayNo());
      model.addAttribute("pay", pay2);
      model.addAttribute("date", new Date());
      return "successPay";
   }
   
   @GetMapping("/failPay")
   public String failPay(Model model,@SessionAttribute(name = "loginMember", required = false) Member loginMember, @RequestParam("payNo") int payNo) {      
//	   log.info("결제실패 payNo : " + payNo);
//	   toyService.deletePay(payNo);
	      

      return "failPay";
   }
   
   @ResponseBody
   @PostMapping(value = "/addCart")
   public String addCart(@SessionAttribute(name = "loginMember", required = false) Member loginMember, HttpSession session, @RequestBody Cart cart) {   
      
	   mypageService.saveCart(cart);
	  
      return "";
   }
   
   
   
 
}