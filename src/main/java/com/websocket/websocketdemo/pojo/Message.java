package com.websocket.websocketdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//呃..
public class Message {
    private String toName;
    private String message;
    private String fromName;
}
