package com.example.activitycommunicate;

public class MessageEvent {
    private MyUser user;

    private String messageSource;

    public MessageEvent(MyUser user, String messageSource) {
        this.user = user;
        this.messageSource = messageSource;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(String messageSource) {
        this.messageSource = messageSource;
    }
}
