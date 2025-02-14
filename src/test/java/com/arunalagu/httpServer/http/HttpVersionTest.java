package com.arunalagu.httpServer.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.arunalagu.httpServer.http.exceptions.BadHttpVersionException;

public class HttpVersionTest {
    @Test
    void testGetBestCompatibleVersion() {
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("HTTP/1.1");
            assertNotNull(version);
            assertEquals(version, HttpVersion.HTTP_1_1);
        } catch (BadHttpVersionException e) {
            fail(e);
        }

    } 

    @Test
    void testGetBestCompatibleVersionBadFormat() {
        try {
            HttpVersion.getBestCompatibleVersion("http/1.1");
            fail();
        } catch (BadHttpVersionException e) {}
    } 

    @Test
    void testGetBestCompatibleVersionHigherVersion() {
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("HTTP/1.5");
            assertNotNull(version);
            assertEquals(version, HttpVersion.HTTP_1_1);
        } catch (BadHttpVersionException e) {
            fail(e);
        }
    }

    // @Test
    // void testValueOf() {

    // }

    // @Test
    // void testValues() {

    // }
}
