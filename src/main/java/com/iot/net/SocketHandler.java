package com.iot.net;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    ConcurrentHashMap<String,WebSocketSession> deviceSession = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        String m = message.getPayload();

        if (m.contains("LOGIN")){
            String id=m.split("_")[1];
            deviceSession.put(id,session);
            sessions.remove(session);
            System.out.println("added device session");

            System.out.println("sessions:"+sessions.size()+"devices:"+deviceSession.size());
        }
        else if(m.contains("REQUEST")){
            String id = m.split("_")[1];
            WebSocketSession dsocketSession =deviceSession.get(id);
            if (dsocketSession!=null && dsocketSession.isOpen()){
                dsocketSession.sendMessage(new TextMessage(message.getPayload()));
            }
            else {
                if (dsocketSession!=null){
                    deviceSession.remove(id);
                    System.out.println("removed invalid session!");
                    System.out.println("sessions:"+sessions.size()+"devices:"+deviceSession.size());
                }
            }
        }else{
            System.out.println("received payload:"+m);
            Iterator sessionIterator = sessions.iterator();
            while (sessionIterator.hasNext()){
                WebSocketSession socketSession = (WebSocketSession) sessionIterator.next();
                if (socketSession.isOpen()){
                    socketSession.sendMessage(new TextMessage(message.getPayload()));
                }else{
                    sessionIterator.remove();
                    System.out.println("sessions:"+sessions.size()+"devices:"+deviceSession.size());
                }
            }
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        sessions.add(session);
        System.out.println("Added new Session");
        System.out.println("sessions:"+sessions.size()+"devices:"+deviceSession.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if(sessions.contains(session)){
            sessions.remove(session);
        }

        if(deviceSession.contains(session)){
            deviceSession.remove(session);
        }

        System.out.println("Removed Disconnected Session!");
        System.out.println("sessions:"+sessions.size()+"devices:"+deviceSession.size());
    }

}