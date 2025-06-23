package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService svc; private final UserRepository users;
    public TaskController(TaskService s, UserRepository u){svc=s;users=u;}
    @GetMapping public List<Task> all(){ return svc.all(); }
    @PostMapping public ResponseEntity<Task> create(@RequestBody Task t){
        String uname=(String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u=users.findByUsername(uname);
        return ResponseEntity.status(201).body(svc.create(t,u));
    }
    @PutMapping("/{id}") public Task update(@PathVariable Long id,@RequestBody Task t){return svc.update(id,t);}
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){svc.delete(id);}
}
