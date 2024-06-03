package com.restfulnplc.nplcrestful.util;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class Response {

    private boolean error;
    private HTTPCode httpCode;
    private String service;
    private String message;
    private Object data;

    public Response(boolean error, String service, HTTPCode httpCode, String message) {
        this.error = error;
        this.service = service;
        this.message = message;
        this.httpCode = httpCode;
    }

}
