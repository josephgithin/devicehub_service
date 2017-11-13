package com.iot.wrapper;

public class MessageWrapper {
    String recipient;
    String speech;
    String direction;

    public MessageWrapper(String recipient, String speech, String direction) {
        this.recipient = recipient;
        this.speech = speech;
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "MessageWrapper{" +
                "recipient='" + recipient + '\'' +
                ", speech='" + speech + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }
}
