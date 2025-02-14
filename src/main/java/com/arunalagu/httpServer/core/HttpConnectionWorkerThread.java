package com.arunalagu.httpServer.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream(); 
            outputStream = socket.getOutputStream(); 

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String line = br.readLine();
            String route = line.split(" ")[1];

            final String CRLF = "\n\r";
            String html = "";
            StringBuffer sb = new StringBuffer();

            switch (route) {
                case "/hello":
                    html = Files.readString(Path.of("src/main/resources/hello.html"));
                    sb = new StringBuffer();
                    sb.append("HTTP/1.1 200 OK"+CRLF); // HTTP Version Response_Code Response_Message
                    sb.append("Content-Length: "+html.getBytes().length+CRLF); // Header
                    sb.append(CRLF); // Header Ends
                    sb.append(html.toString()+CRLF);
                    sb.append(CRLF);
                    outputStream.write(sb.toString().getBytes());
                    break;
            
                case "/":
                    html = Files.readString(Path.of("src/main/resources/index.html"));
                    sb = new StringBuffer();
                    sb.append("HTTP/1.1 200 OK"+CRLF); // HTTP Version Response_Code Response_Message
                    sb.append("Content-Length: "+html.getBytes().length+CRLF); // Header
                    sb.append(CRLF); // Header Ends
                    sb.append(html.toString()+CRLF);
                    sb.append(CRLF);
                    outputStream.write(sb.toString().getBytes());

                default :
                    sb = new StringBuffer();
                    sb.append("HTTP/1.1 404 Not Found"+CRLF);
                    outputStream.write(sb.toString().getBytes());
            }

            // try {
            //     sleep(5000);
            // } catch (InterruptedException e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            // }
            LOGGER.info("Connection process finished");
            
        } catch (IOException e) {
            LOGGER.error("Problem with communication: ", e);
            e.printStackTrace();
        }
        finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }

            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }

            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }

    
    
}
