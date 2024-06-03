package com.restfulnplc.nplcrestful.util;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class SuccessResponse {

    private final String STATUS = "Success";
    private String service;
    private String message;
    private Map<String, Object> data;
}
