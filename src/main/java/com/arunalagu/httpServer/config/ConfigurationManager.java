package com.arunalagu.httpServer.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.arunalagu.httpServer.config.exceptions.HttpConfigurationException;
import com.arunalagu.httpServer.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager(){}

    public static ConfigurationManager getInstance(){
        if(myConfigurationManager == null)
            myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    /** 
     * Used to load the configuration file by the @param filePath provided
     * @throws IOException 
     */
    public void loadConfigurationFile(String filePath){
        // FileReader fileReader = new FileReader(filePath);
        StringBuffer sb = new StringBuffer();
        // int i;
        // while((i = fileReader.read()) != -1){
        //     sb.append((char) i);
        // }
        try {
            for(byte fileByte : Files.readAllBytes(Path.of(filePath))){
                sb.append((char) fileByte);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        JsonNode conf;
        try {
            conf = Json.parse(sb.toString());
        } catch (JsonMappingException e) {
            throw new HttpConfigurationException("Error parsing the configuration file",e);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the configuration file",e); 
        }

        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the configuration file, Internal",e);
        } catch (IllegalArgumentException e) {
            throw new HttpConfigurationException("Error parsing the configuration file, Internal",e);
        }
    }

    /**
     * Returns the current loaded Configuration
     */
    public Configuration getCurrentConfiguration(){
        if(myCurrentConfiguration == null){
            throw new HttpConfigurationException("No Current Configuration set");
        }
        return myCurrentConfiguration;
    }


}
