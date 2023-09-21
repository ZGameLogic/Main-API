package com.zgamelogic.controllers;

import com.zgamelogic.data.generic.SuccessMessage;
import com.zgamelogic.data.message.Message;
import com.zgamelogic.services.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @PostMapping("message")
    private SuccessMessage sendMessage(@RequestBody Message message){
        MailService.sendEmail(
                "ben@zgamelogic.com",
                "A message was sent to you from zgamelogic.com by " + message.getName(),
                message.getMessage() + "\nReply to this message: " + message.getEmail()
        );
        return new SuccessMessage(true, "");
    }
}
