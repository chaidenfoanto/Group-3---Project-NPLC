package com.restfulnplc.nplcrestful.util;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class ErrorResponse {

    private final String STATUS = "Error";
    private String service;
    private String message;
    private Error error;
}
