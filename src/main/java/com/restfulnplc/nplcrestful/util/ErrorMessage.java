package com.restfulnplc.nplcrestful.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    private String HTTPMessage;
    private String detail;

    public ErrorMessage(HTTPCode HTTPMessage) {
        this.HTTPMessage = HTTPMessage.getReasonPhrase();
        this.detail = HTTPMessage.getDetailMessage();
    }

}