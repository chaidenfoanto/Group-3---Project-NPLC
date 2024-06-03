package com.restfulnplc.nplcrestful.util;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class ErrorMessage {
    private String HTTPMessage;
    private String detail;

    public ErrorMessage(HTTPCode HTTPMessage, String detail) {
        this.HTTPMessage = HTTPMessage.getDetailMessage();
        this.detail = detail;
    }

}