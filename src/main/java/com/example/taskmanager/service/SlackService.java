package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SlackService {
    @Value("${app.slackWebhookUrl:}")
    private String webhook;
    private final RestTemplate restTemplate = new RestTemplate();
    public void notify(String text){
        if(webhook==null||webhook.isEmpty())return;
        HttpHeaders headers=new HttpHeaders();headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.postForEntity(webhook, new HttpEntity<>("{"text":""+text+""}", headers), String.class);
    }
    public void notifyNewTask(Task t){ notify("New task created: "+t.getTitle());}
}
