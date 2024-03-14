/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author aldo
 */
public class ApiError {
     private HttpStatus status;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    //private LocalDateTime timestamp;
    private String message;
    //public String debugMessage;
    private List<String> errors;

    private ApiError() {
        //timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.setStatus(status);
    }

    public ApiError(String message, List<String> errors) {

        this.message = message;
        this.errors = errors;
    }
    ApiError(HttpStatus status, String message) {
        this();
        this.setStatus(status);
        this.setMessage(message);

    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
