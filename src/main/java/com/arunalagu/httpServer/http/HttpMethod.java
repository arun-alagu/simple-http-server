package com.arunalagu.httpServer.http;

public enum HttpMethod {
    GET, HEAD;

    public static final int MAX_LENGTH;

    static {
        int tempMaxLength = -1;
        for(HttpMethod m : values()){
            tempMaxLength = Math.max(tempMaxLength, m.name().length());
        }

        MAX_LENGTH = tempMaxLength;
    }
}
