package com.multi.ukids.playground.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.ukids.playground.model.service.PlaygroundService;
import com.multi.ukids.playground.model.vo.Playground;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PlaygroundController {
	
	@Autowired
	private PlaygroundService playgroundService;
	
	@GetMapping("/playground")
	public String playground(Model model, @RequestParam Map<String, String> param) {
		List<Playground> list = playgroundService.getPlaygroundList(param);
		
		model.addAttribute("list", list);
		
		log.info(param.toString());
		log.info("list : " + Integer.toString(list.size()));
		
		return "playground";
	}
}
