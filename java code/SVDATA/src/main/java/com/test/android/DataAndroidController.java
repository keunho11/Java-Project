package com.test.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.Data;
import com.test.mapper.SVDATAMapper;

@RestController
public class DataAndroidController {

	@Autowired //의존성 주입(DI:Dependency Injection)
	SVDATAMapper mapper;
	
	@GetMapping("/android/dataList")
	List<Data> getDataList() {	
		return mapper.selectDatasList();
	}
	
	/*
	 * @GetMapping("/android/updateStatus/{status}") void
	 * getUpdateStatus(@PathVariable("status") String status) { if (status == ON) {
	 * String statusON = "ON"; mapper.updataData(statusON);
	 * 
	 * }else if(status == OFF) { String statusOFF = "OFF";
	 * mapper.updataData(statusOFF); }
	 * 
	 * }
	 */
	
	@GetMapping("/android/updateData/{status}")
	void getUpdateStatus(@PathVariable("status") String status) {
		mapper.updataData(status);
	}
	
	@GetMapping("/android/pTemp")
	void getpTemp() {
		mapper.pTemp();
	}
	
	@GetMapping("/android/mTemp")
	void getmTemp() {
		mapper.mTemp();
	}
	
}
