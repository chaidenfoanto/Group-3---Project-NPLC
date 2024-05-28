package com.restfulnplc.nplcrestful.util;

import lombok.Getter;
import lombok.Setter;

/*Generic Method*/
@Setter /*Setter*/
@Getter /*Getter*/
public class Response<T> {
    private String service;
    private String message;
    private T data;
}