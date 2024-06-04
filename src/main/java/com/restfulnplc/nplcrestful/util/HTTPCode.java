package com.restfulnplc.nplcrestful.util;

import org.springframework.http.HttpStatus;

public enum HTTPCode {

    // Informational responses
    CONTINUE(100, HttpStatus.CONTINUE, "Continue", "Request received, please continue"),
    SWITCHING_PROTOCOLS(101, HttpStatus.SWITCHING_PROTOCOLS, "Switching Protocols", "Switching to new protocol; obey Upgrade header"),
    
    // Successful responses
    OK(200, HttpStatus.OK, "OK", "Request successfully processed"),
    CREATED(201, HttpStatus.CREATED, "Created", "Resource successfully created"),
    ACCEPTED(202, HttpStatus.ACCEPTED, "Accepted", "Request accepted for processing"),
    NO_CONTENT(204, HttpStatus.NO_CONTENT, "No Content", "Request successfully processed but no content returned"),
    
    // Redirection messages
    MOVED_PERMANENTLY(301, HttpStatus.MOVED_PERMANENTLY, "Moved Permanently", "Resource has been moved to a new URL permanently"),
    FOUND(302, HttpStatus.FOUND, "Found", "Resource found at a different URL"),
    
    // Client error responses
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "Bad Request", "Malformed request syntax"),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "Unauthorized", "Authentication is required and has failed or has not yet been provided"),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "Forbidden", "You do not have permission to access this resource"),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "Not Found", "The requested resource could not be found"),
    METHOD_NOT_ALLOWED(405, HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed", "Request method is not supported for the requested resource"),
    CONFLICT(409, HttpStatus.CONFLICT, "Conflict", "Request could not be processed because of conflict in the request"),
    
    // Server error responses
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred on the server"),
    NOT_IMPLEMENTED(501, HttpStatus.NOT_IMPLEMENTED, "Not Implemented", "The server does not support the functionality required to fulfill the request"),
    BAD_GATEWAY(502, HttpStatus.BAD_GATEWAY, "Bad Gateway", "Received an invalid response from the upstream server"),
    SERVICE_UNAVAILABLE(503, HttpStatus.SERVICE_UNAVAILABLE, "Service Unavailable", "The server is currently unable to handle the request"),
    GATEWAY_TIMEOUT(504, HttpStatus.GATEWAY_TIMEOUT, "Gateway Timeout", "The server did not receive a timely response from the upstream server");

    private final int code;
    private final HttpStatus status;
    private final String reasonPhrase;
    private final String detailMessage;

    HTTPCode(int code, HttpStatus status, String reasonPhrase, String detailMessage) {
        this.code = code;
        this.status = status;
        this.reasonPhrase = reasonPhrase;
        this.detailMessage = detailMessage;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}