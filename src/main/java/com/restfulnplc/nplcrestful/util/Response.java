package com.restfulnplc.nplcrestful.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private boolean error;
    private HTTPCode httpCode;
    private String service;
    private String message;
    private Object data;

}
