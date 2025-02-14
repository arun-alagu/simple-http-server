package com.arunalagu.httpServer.config.exceptions;

public class HttpConfigurationException extends RuntimeException{
    
    public HttpConfigurationException(String message){
        super(message);
    }

    public HttpConfigurationException(String message, Throwable var2) {
        super(message, var2);
    }

    public HttpConfigurationException(Throwable var1) {
        super(var1);
    }
}
