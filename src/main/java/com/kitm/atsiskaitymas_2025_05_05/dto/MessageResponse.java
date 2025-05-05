package com.kitm.atsiskaitymas_2025_05_05.dto;

public class MessageResponse {
    private String message;

    public MessageResponse(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
