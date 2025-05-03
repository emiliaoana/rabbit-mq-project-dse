package com.example.receiver.model;
import java.io.Serializable;

public class LogMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String service;
    private String level;
    private String message;
    private String timestamp;

    public LogMessage() {
    }

    public LogMessage(String service, String level, String message, String timestamp) {
        this.service = service;
        this.level = level;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
