package com.calmomentree.projectree.controllers;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Slf4j
@Controller
@RequestMapping("/error")
public class ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String errorPath(
        HttpServletRequest request,HttpServletResponse response) {

        Integer statusCode = (Integer) (request.getAttribute("javax.servlet.error.status_code"));

        switch (statusCode) {
            case 404 :
                return "error/404";
            case 500 :
                return "error/404";
            default :
                return "error/404";
        }
    }
        
}
