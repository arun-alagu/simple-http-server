package com.arunalagu.httpServer.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.arunalagu.httpServer.http.exceptions.HttpParsingException;

@TestInstance(Lifecycle.PER_CLASS)
public class HttpParserTest {
    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass(){
        httpParser = new HttpParser();
    }

    @Test
    void testParseHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generatedValidGETTestCase());
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertNotNull(request);
        assertEquals(request.getMethod(), HttpMethod.GET);
        assertEquals(request.getRequestTarget(), "/");
        assertEquals(request.getOriginalHttpVersion(), "HTTP/1.1");
        assertEquals(request.getBestCompatibleHTTPVersion(), HttpVersion.HTTP_1_1);
    }

    @Test
    void testParseHttpRequestBadMethodName1() {
        try {
            httpParser.parseHttpRequest(generatedBadTestCaseMethodName1());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void testParseHttpRequestBadMethodName2() {
        try {
            httpParser.parseHttpRequest(generatedBadTestCaseMethodName2());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void testParseHttpRequestInvNumItems1() {
        try {
            httpParser.parseHttpRequest(generatedBadTestCaseRequestLineInvNumItems1());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void testParseHttpEmptyRequest() {
        try {
            httpParser.parseHttpRequest(generatedBadTestCaseRequestLineEmpty());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void testParseHttpRequestNoLF() {
        try {
            httpParser.parseHttpRequest(generatedBadTestCaseNoLF());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void testParseHttpRequestBadHttpVersion() {
        try {
            httpParser.parseHttpRequest(generatedBadTestCaseBadHttpVersion());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void testParseHttpRequestUnsupportedHttpVersion() {
        try {
            httpParser.parseHttpRequest(generatedBadTestCaseUnsupportedHttpVersion());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_505_METHOD_NOT_SUPPORTED);
        }
    }

    @Test
    void testParseHttpRequestSupportedHttpVersion1() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generatedTestCaseSupportedHttpVersion1());
            assertNotNull(request);
            assertEquals(request.getBestCompatibleHTTPVersion(), HttpVersion.HTTP_1_1);
            assertEquals(request.getOriginalHttpVersion(), "HTTP/1.2");
        } catch (HttpParsingException e) {
            fail(e);
        }
    }

    private InputStream generatedValidGETTestCase(){
        String rawData = 
        "GET / HTTP/1.1\r\n"+
        "Host: localhost:8080\r\n"+
        "Connection: keep-alive\r\n"+
        "sec-ch-ua: \"Brave\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"\r\n"+
        "sec-ch-ua-mobile: ?0\r\n"+
        "sec-ch-ua-platform: \"Linux\"\r\n"+
        "Upgrade-Insecure-Requests: 1\r\n"+
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36\r\n"+
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8\r\n"+
        "Sec-GPC: 1\r\n"+
        "Accept-Language: en-US,en\r\n"+
        "Sec-Fetch-Site: none\r\n"+
        "Sec-Fetch-Mode: navigate\r\n"+
        "Sec-Fetch-User: ?1\r\n"+
        "Sec-Fetch-Dest: document\r\n"+
        "Accept-Encoding: gzip, deflate, br, zstd\r\n";

        InputStream inputStream = new ByteArrayInputStream(
            rawData.getBytes(
                StandardCharsets.US_ASCII
            )
        );

        return inputStream;
    }

    private InputStream generatedBadTestCaseMethodName1(){
        String rawData = 
        "GA / HTTP/1.1\r\n"+
        "Host: localhost:8080\r\n";

        InputStream inputStream = new ByteArrayInputStream(
            rawData.getBytes(
                StandardCharsets.US_ASCII
            )
        );

        return inputStream;
    }

    private InputStream generatedBadTestCaseMethodName2(){
        String rawData = 
        "GETTTI / HTTP/1.1\r\n"+
        "Host: localhost:8080\r\n"+
        "Connection: keep-alive\r\n"+
        "sec-ch-ua: \"Brave\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"\r\n"+
        "sec-ch-ua-mobile: ?0\r\n"+
        "sec-ch-ua-platform: \"Linux\"\r\n"+
        "Upgrade-Insecure-Requests: 1\r\n"+
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36\r\n"+
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8\r\n"+
        "Sec-GPC: 1\r\n"+
        "Accept-Language: en-US,en\r\n"+
        "Sec-Fetch-Site: none\r\n"+
        "Sec-Fetch-Mode: navigate\r\n"+
        "Sec-Fetch-User: ?1\r\n"+
        "Sec-Fetch-Dest: document\r\n"+
        "Accept-Encoding: gzip, deflate, br, zstd\r\n";

        InputStream inputStream = new ByteArrayInputStream(
            rawData.getBytes(
                StandardCharsets.US_ASCII
            )
        );

        return inputStream;
    }

    private InputStream generatedBadTestCaseRequestLineInvNumItems1(){
        String rawData = 
        "GET / AAAA HTTP/1.1\r\n"+
        "Host: localhost:8080\r\n";

        InputStream inputStream = new ByteArrayInputStream(
            rawData.getBytes(
                StandardCharsets.US_ASCII
            )
        );

        return inputStream;
    }
    
    private InputStream generatedBadTestCaseRequestLineEmpty(){
        String rawData = 
        "\r\n"+
        "Host: localhost:8080\r\n";

        InputStream inputStream = new ByteArrayInputStream(
            rawData.getBytes(
                StandardCharsets.US_ASCII
            )
        );

        return inputStream;
    }

    private InputStream generatedBadTestCaseNoLF(){
        String rawData = 
        "GET / HTTP/1.1\r"+ // <-- No LF
        "Host: localhost:8080\r\n";

        InputStream inputStream = new ByteArrayInputStream(
            rawData.getBytes(
                StandardCharsets.US_ASCII
            )
        );

        return inputStream;
    }

    private InputStream generatedBadTestCaseBadHttpVersion(){
        String rawData = 
        "GET / HTP/1.1\r\n"+
        "Host: localhost:8080\r\n";

        InputStream inputStream = new ByteArrayInputStream(
            rawData.getBytes(
                StandardCharsets.US_ASCII
            )
        );

        return inputStream;
    }   

    private InputStream generatedBadTestCaseUnsupportedHttpVersion(){
        String rawData = 
        "GET / HTTP/2.1\r\n"+
        "Host: localhost:8080\r\n";

        InputStream inputStream = new ByteArrayInputStream(
            rawData.getBytes(
                StandardCharsets.US_ASCII
            )
        );

        return inputStream;
    }   

    private InputStream generatedTestCaseSupportedHttpVersion1(){
        String rawData = 
        "GET / HTTP/1.2\r\n"+
        "Host: localhost:8080\r\n";

        InputStream inputStream = new ByteArrayInputStream(
            rawData.getBytes(
                StandardCharsets.US_ASCII
            )
        );

        return inputStream;
    }   

}



