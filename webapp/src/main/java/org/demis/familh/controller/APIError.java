package org.demis.familh.controller;

public class APIError {

    private String message;

    private String code;

    public APIError() {
    }

    public APIError(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
