package com.iot.worker;

import com.iot.net.RestClient;
import com.iot.net.SessionWSocketHandler;
import com.iot.store.MessageStore;
import com.iot.wrapper.MessageWrapper;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Scanner;

public class MessageWorker extends Thread{

    RestClient restClient = new RestClient("http://localhost:5001/");

    @Override
    public void run() {
        while (true){
            MessageWrapper message = null;
            try {
                message = (MessageWrapper) MessageStore.getQueue().take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("NewMSG:"+message.toString());
            switch (message.getDirection()){
                case "TOBOT":
                    System.out.println("TOBOT:"+message.toString());
                        restClient.post("/app/chat",new JSONObject().put("q",message.getSpeech()).put("recipient",message.getRecipient()).toString());
                    break;
                case "FROMBOT":
                   WebSocketSession socketSession = SessionWSocketHandler.userSession.get(message.getRecipient());
                   if (socketSession!=null && socketSession.isOpen()){
                       try {
                           socketSession.sendMessage(new TextMessage(message.getSpeech()));
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }else {
                       System.out.println("Invalid Session");
                   }
                    break;
            }
        }
    }

}
