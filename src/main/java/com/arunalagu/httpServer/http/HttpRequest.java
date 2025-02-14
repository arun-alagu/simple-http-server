package com.arunalagu.httpServer.http;

import com.arunalagu.httpServer.http.exceptions.BadHttpVersionException;
import com.arunalagu.httpServer.http.exceptions.HttpParsingException;

public class HttpRequest extends HttpMessage{
    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion; // requesed HTTP version
    private HttpVersion bestCompatibleHTTPVersion; // best compatible HTTP version

    HttpRequest(){}

    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(String method) throws HttpParsingException {
        for(HttpMethod m : HttpMethod.values()){
            if(method.equals(m.name())){
                this.method = HttpMethod.valueOf(method);
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if(requestTarget == null || requestTarget.length() == 0)
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        this.requestTarget = requestTarget;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }
    
    public HttpVersion getBestCompatibleHTTPVersion() {
        return bestCompatibleHTTPVersion;
    }

    public void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleHTTPVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);

        if(bestCompatibleHTTPVersion == null){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_METHOD_NOT_SUPPORTED);
        }
    }


}
