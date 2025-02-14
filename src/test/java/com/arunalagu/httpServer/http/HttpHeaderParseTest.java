package com.arunalagu.httpServer.http;

import java.io.InputStreamReader;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class HttpHeaderParseTest {
   
    private HttpParser httpParser;

    private Method parseHeadersMethod;

    @BeforeAll
    public void beforeClass() throws NoSuchMethodException, SecurityException{
        httpParser = new HttpParser();
        Class<HttpParser> cls = HttpParser.class;
        parseHeadersMethod = cls.getDeclaredMethod("parseMethods", InputStreamReader.class,
        HttpRequest.class);
        parseHeadersMethod.setAccessible(true); 
    }


    @Test
    void testParseHttpHeader() {

    }
}
