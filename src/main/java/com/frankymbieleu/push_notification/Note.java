package com.frankymbieleu.push_notification;

import lombok.Data;

import java.util.Map;

@Data
public class Note {
    private String subject;
    private String content;
    private String token;
    private String target;
    private Map<String, String> data;
}
