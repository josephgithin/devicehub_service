package com.iot.net;

import com.iot.store.MessageStore;
import com.iot.wrapper.MessageWrapper;
import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SessionWSocketHandler extends TextWebSocketHandler {


    public static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public static ConcurrentHashMap<String,WebSocketSession> userSession = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        String payload = message.getPayload();
        JSONObject jsonPayload = new JSONObject(payload);
        String type = jsonPayload.getString("type");

        switch (type){
            case "connect":
                System.out.println("Connect:"+jsonPayload);
                userSession.put(jsonPayload.getString("recipient"),session);
                sessions.remove(session);
                System.out.println("usersessions:"+userSession.size());
                break;
            case "message":
                System.out.println("Message:"+jsonPayload);
                MessageStore.getQueue().put(new MessageWrapper(jsonPayload.getString("recipient"),jsonPayload.getString("speech"),"TOBOT"));
                break;
            case "disconnect":
                userSession.remove(jsonPayload.getString("recipient"));
                break;
        }


    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        //sessions.add(session);
        //System.out.println("Added new Session");
        //System.out.println("sessions:"+sessions.size()+"devices:"+userSession.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if(sessions.contains(session)){
            sessions.remove(session);
        }

        if(userSession.contains(session)){
            userSession.remove(session);
        }

        System.out.println("Removed Disconnected Session!");
        System.out.println("sessions:"+sessions.size()+"devices:"+userSession.size());
    }
}
