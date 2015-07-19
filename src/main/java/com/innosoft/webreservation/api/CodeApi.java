package com.innosoft.webreservation.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.innosoft.webreservation.entity.MstCode;
import com.innosoft.webreservation.service.CodeService;


@Controller
@RequestMapping("api/code")
public class CodeApi {
	@Autowired
	private CodeService codeService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<MstCode> listCode() {
		List<MstCode> list = codeService.listCode();
		return list;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<MstCode> updateCode(@RequestBody MstCode code) {
		try {
			if(code.getCODE_ID()==0) {
				MstCode newCode = codeService.addCode(code);
				return new ResponseEntity<MstCode>(newCode, HttpStatus.OK);
			} else {
				return new ResponseEntity<MstCode>(code, HttpStatus.OK);
			}
		} catch(Exception e) {
			return new ResponseEntity<MstCode>(code, HttpStatus.BAD_REQUEST);
		}
		
	}	
	
}
