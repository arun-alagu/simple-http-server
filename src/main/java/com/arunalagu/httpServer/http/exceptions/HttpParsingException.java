package com.arunalagu.httpServer.http.exceptions;

import com.arunalagu.httpServer.http.HttpStatusCode;

public class HttpParsingException extends Exception{

    private HttpStatusCode errorCode;

    public HttpParsingException(HttpStatusCode errorCode){
        super(errorCode.MESSAGE);
        this.errorCode = errorCode;
    }

    public HttpStatusCode getErrorCode() {
        return errorCode;
    }

    
}
