package com.innosoft.webreservation.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.innosoft.webreservation.dao.CodeDao;
import com.innosoft.webreservation.entity.MstCode;


@Service
@Transactional
public class CodeServiceImpl implements CodeService {
	@Autowired
    private CodeDao codeDao;
	
	public List<MstCode> listCode(){
		return codeDao.listCode();
	}
}
