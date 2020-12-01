package com.test;

import java.time.Instant;

public class MessageRecord {
    private Instant sentDate;
    private String message;

    public MessageRecord() {

    }

    public MessageRecord(Instant sentDate, String message) {
        this.sentDate = sentDate;
        this.message = message;
    }

    public Instant getSentDate() {
        return sentDate;
    }

    public void setSentDate(Instant sentDate) {
        this.sentDate = sentDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
