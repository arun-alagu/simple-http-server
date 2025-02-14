package com.arunalagu.httpServer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arunalagu.httpServer.config.Configuration;
import com.arunalagu.httpServer.config.ConfigurationManager;
import com.arunalagu.httpServer.core.ServerListenerThread;

/**
 * Driver class for HTTP Server
 */
public class HttpServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {

        LOGGER.info("Starting Server ...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("port:" + conf.getPort());
        LOGGER.info("WebRoot:"+ conf.getWebroot()); 

        ServerListenerThread serverListenerThread;
        try {
            serverListenerThread = new ServerListenerThread(conf.getPort(),
            conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        

    }
}