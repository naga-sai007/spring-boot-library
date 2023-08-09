package com.virtusa.springbootlibrary.controller;

import com.virtusa.springbootlibrary.entity.Message;
import com.virtusa.springbootlibrary.requestmodels.AdminQuestionRequest;
import com.virtusa.springbootlibrary.service.MessagesService;
import com.virtusa.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService){
        this.messagesService=messagesService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value="Authorization")String token,
                            @RequestBody Message IMessageRequest){
        String userEmail= ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");
        messagesService.postMessage(IMessageRequest,userEmail);
    }

    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value = "Authorization")String token,
                           @RequestBody AdminQuestionRequest adminQuestionRequest)throws Exception{
        String userEmail=ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");
        String admin=ExtractJWT.payLoadJWTExtraction(token,"\"userType\"");
        if(admin ==null || !admin.equals("admin")){
            throw new Exception("Administration page only");
        }
        messagesService.putMessagw(adminQuestionRequest,userEmail);
    }
}
