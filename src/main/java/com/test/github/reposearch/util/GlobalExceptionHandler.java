package com.test.github.reposearch.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(HttpClientErrorException.class)
    public void handleException(HttpServletResponse response, HttpClientErrorException ex) throws Exception {

        logger.log(Level.SEVERE, ex.getMessage());
        response.sendError(500, "Some unexpected error has occurred. Please try again");
    }
}