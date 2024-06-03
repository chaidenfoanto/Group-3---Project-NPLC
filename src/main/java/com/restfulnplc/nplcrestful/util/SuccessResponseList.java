package com.restfulnplc.nplcrestful.util;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class SuccessResponseList {

    private final String STATUS = "Success";
    private String service;
    private String message;
    private List<Map<String, Object>> data;
}
