package com.restfulnplc.nplcrestful.util;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

import com.restfulnplc.nplcrestful.enums.HTTPCode;

@Getter
@Setter
@Configuration
public class Response {

    private boolean error;
    private HTTPCode httpCode;
    private String service;
    private String message;
    private Object data;

}
