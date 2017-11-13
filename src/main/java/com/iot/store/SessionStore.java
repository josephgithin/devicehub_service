package com.iot.store;

import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {

    private static ConcurrentHashMap<String,WebSocketSession> sessionStore = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, WebSocketSession> getSessionStore() {
        return sessionStore;
    }
}
