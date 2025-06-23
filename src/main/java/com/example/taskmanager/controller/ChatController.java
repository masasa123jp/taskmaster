package com.example.taskmanager.controller;

import com.example.taskmanager.model.ChatMessageDto;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ChatController {
    private static final List<ChatMessageDto> recent = Collections.synchronizedList(new ArrayList<>());
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessageDto handle(ChatMessageDto msg){
        msg.timestamp = LocalDateTime.now();
        recent.add(msg); if(recent.size()>50) recent.remove(0);
        return msg;
    }
    @org.springframework.web.bind.annotation.GetMapping("/api/chat/history")
    @org.springframework.web.bind.annotation.ResponseBody
    public List<ChatMessageDto> history(){ return recent; }
}
