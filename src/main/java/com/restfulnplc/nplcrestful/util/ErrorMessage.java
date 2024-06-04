package com.restfulnplc.nplcrestful.util;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

import com.restfulnplc.nplcrestful.enums.HTTPCode;

@Getter
@Setter
@Configuration
public class ErrorMessage {
    private String HTTPMessage;
    private String detail;

    public ErrorMessage(HTTPCode HTTPMessage) {
        this.HTTPMessage = HTTPMessage.getReasonPhrase();
        this.detail = HTTPMessage.getDetailMessage();
    }

}