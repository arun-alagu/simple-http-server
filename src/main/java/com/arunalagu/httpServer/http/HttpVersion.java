package com.arunalagu.httpServer.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.arunalagu.httpServer.http.exceptions.BadHttpVersionException;

public enum HttpVersion {
    HTTP_1_1 ("HTTP/1.1" , 1, 1);

    public final String LITERAL;
    public final int MAJOR;
    public final int MINOR;

    HttpVersion(String literal, int major, int minor){
        this.LITERAL = literal;
        this.MAJOR = major;
        this.MINOR = minor;
    }

    private static final Pattern httpVersionRegexPattern = Pattern.compile("^HTTP/(?<major>\\d+).(?<minor>\\d+)");

    public static HttpVersion getBestCompatibleVersion(String literalVersion) throws BadHttpVersionException{
        Matcher matcher = httpVersionRegexPattern.matcher(literalVersion);

        if(!matcher.find() || matcher.groupCount() != 2){
            throw new BadHttpVersionException();
        }

        int major = Integer.parseInt(matcher.group("major"));
        int minor = Integer.parseInt(matcher.group("minor"));

        HttpVersion tempCompatibleVersion = null;
        for(HttpVersion version : values()){
            if(version.LITERAL.equals(literalVersion)){
                return version;
            }
            else{
                if(version.MAJOR == major){
                    if(version.MINOR < minor){
                        tempCompatibleVersion = version;
                    }
                }
            }
        }
        return tempCompatibleVersion;
    }
}
