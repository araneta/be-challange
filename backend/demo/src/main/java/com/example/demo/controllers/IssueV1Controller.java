/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controllers;

import com.example.demo.dto.BaseResponse;
import com.example.demo.entities.Issue;
import com.example.demo.repositories.IssueRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.TimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author aldo
 */
@RestController
@RequestMapping("/api/v1")
public class IssueV1Controller {
    Logger logger = LoggerFactory.getLogger(IssueV1Controller.class);
    
    @Autowired
    IssueRepository issueRepo;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @PostMapping("/issues")
    public ResponseEntity<? extends Object> create(@Valid @RequestBody Issue issue) {
        //log the object
        try{
            logger.info("add issue: {}", objectMapper.writeValueAsString(issue));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        //save to db
        var saved = issueRepo.save(issue);
        
        BaseResponse response = new BaseResponse();
        response.setMessage("Success");
        response.setData(saved);
        response.setStatus("ok");
        response.setTime(TimeHelper.getCurrentTimeYYYYMMDDHHmmss());
       
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);

    }
    
    @GetMapping("/issues/{id}")
    public ResponseEntity<? extends Object>  getIssue(@PathVariable Long id){
        var issue = issueRepo.findById(id);
        if(issue.isPresent()){
            return new ResponseEntity<Issue>(issue.get(), HttpStatus.OK);
        }
        BaseResponse response = new BaseResponse();
        response.setMessage("");
        response.setStatus("not found");
        response.setTime(TimeHelper.getCurrentTimeYYYYMMDDHHmmss());        
        
        return new ResponseEntity<BaseResponse>(response, HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/issues/{id}")
    public ResponseEntity<? extends Object>  updateIssue(@PathVariable Long id, @RequestBody Issue newIssue){
        var oIssue = issueRepo.findById(id);
        //if not found
        if(!oIssue.isPresent()){
            BaseResponse response = new BaseResponse();
            response.setMessage("");
            response.setStatus("not found");
            response.setTime(TimeHelper.getCurrentTimeYYYYMMDDHHmmss());

            return new ResponseEntity<BaseResponse>(response, HttpStatus.NOT_FOUND);
        }
        
        //if exist update it
        var issue = oIssue.get();
        issue.setTitle(newIssue.getTitle());
        issue.setDescription(newIssue.getDescription());

        var saved = issueRepo.save(issue);

        BaseResponse response = new BaseResponse();
        response.setMessage("Success");
        response.setData(saved);
        response.setStatus("ok");
        response.setTime(TimeHelper.getCurrentTimeYYYYMMDDHHmmss());

        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
        
    }
}
