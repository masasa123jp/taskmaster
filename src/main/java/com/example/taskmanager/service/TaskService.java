package com.example.taskmanager.service;

import com.example.taskmanager.model.*;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TaskService {
    @Autowired private TaskRepository repo;
    @Autowired private SlackService slack;
    public List<Task> all(){ return repo.findAll(); }
    @Transactional public Task create(Task t, User creator){
        t.setStatus(TaskStatus.OPEN); t.setCreatedBy(creator);
        Task saved = repo.save(t); slack.notifyNewTask(saved); return saved;
    }
    @Transactional public Task update(Long id, Task in){
        Task t = repo.findById(id).orElseThrow();
        if(in.getStatus()!=null) t.setStatus(in.getStatus());
        if(in.getTitle()!=null) t.setTitle(in.getTitle());
        return repo.save(t);
    }
    @Transactional public void delete(Long id){ repo.deleteById(id);}
}
