package com.innosoft.webreservation.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innosoft.webreservation.dao.MessageDao;
import com.innosoft.webreservation.entity.MstMessage;

@Service
@Transactional
public class MessageServiceImpl implements MessageService{
	@Autowired
    private MessageDao messageDao;
	
	public List<MstMessage> listMessage(){
		return messageDao.listMessage();
	}
}