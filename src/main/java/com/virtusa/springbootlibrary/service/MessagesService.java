package com.virtusa.springbootlibrary.service;


import com.virtusa.springbootlibrary.dao.MessageRepository;
import com.virtusa.springbootlibrary.entity.Message;
import com.virtusa.springbootlibrary.requestmodels.AdminQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class MessagesService {

    private MessageRepository messageRepository;

    @Autowired
    public MessagesService(MessageRepository messageRepository){
        this.messageRepository=messageRepository;
    }

    public void postMessage(Message messageRequest, String userEmail){
        Message message=new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        message.setClosed(false);
        messageRepository.save(message);
    }

    public void putMessagw(AdminQuestionRequest adminQuestionRequest,String userEmail)throws Exception{
        Optional<Message> message=messageRepository.findById(adminQuestionRequest.getId());
        if(!message.isPresent()){
            throw new Exception("Message not found");
        }

        message.get().setAdminEmail(userEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }


}
