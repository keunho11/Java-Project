package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.entity.Data;
import com.test.mapper.SVDATAMapper;

@Controller
public class DataController {
	
	@Autowired
	SVDATAMapper mapper; //의존성 주입
	
	@GetMapping("/data/dataList")
	public void listData(Model model) {
		model.addAttribute("list", mapper.selectDatasList());
	}
	
	@GetMapping("/data/dataRegistry")
	public void getDataRegistry() {}
	
	@PostMapping("/data/dataRegistry")
	public String postDataRegistry(Data data) {
		
		mapper.insertData(data.getStatus(), data.getTemp());
		return "redirect:/data/dataList";
	}
}
