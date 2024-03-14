/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controllers;

/**
 *
 * @author aldo
 */
import com.example.demo.dto.BaseResponse;
import helpers.TimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler  {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse> handleMissingParameterException(MissingServletRequestParameterException ex) {
        BaseResponse response = new BaseResponse();
        response.setMessage("Invalid Format Request");
        response.setStatus("30");
        response.setTime(TimeHelper.getCurrentTimeYYYYMMDDHHmmss());
        //response.setData(info.getContent());
        logger.error(ex.getMessage());
        return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            Exception ex,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(
                "Constraint Violation" ,
                details);
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(
                "Invalid arguments" ,
                details);
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
 

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<BaseResponse> handleDBAccessFailureException(
            DataAccessResourceFailureException ex) {

        BaseResponse response = new BaseResponse();
        response.setMessage("Database Error");
        response.setStatus("91");
        response.setTime(TimeHelper.getCurrentTimeYYYYMMDDHHmmss());
        //response.setData(info.getContent());
        logger.error(ex.getMessage());
        return new ResponseEntity<BaseResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }




    //the last
    @ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<BaseResponse> handleGeneralException(Exception ex) {
        BaseResponse response = new BaseResponse();
        response.setMessage("General Error");
        response.setStatus("99");
        response.setTime(TimeHelper.getCurrentTimeYYYYMMDDHHmmss());
        //response.setData(info.getContent());
        logger.error(ex.getMessage());
        return new ResponseEntity<BaseResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}