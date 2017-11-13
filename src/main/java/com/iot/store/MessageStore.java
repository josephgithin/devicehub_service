package com.iot.store;

import com.iot.wrapper.MessageWrapper;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageStore {

    public static LinkedBlockingQueue<MessageWrapper> queue = new LinkedBlockingQueue();

    public static LinkedBlockingQueue getQueue() {
        return queue;
    }
}
