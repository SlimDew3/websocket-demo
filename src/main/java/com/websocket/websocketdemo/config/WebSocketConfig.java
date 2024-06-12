package com.websocket.websocketdemo.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpointConfig;

/**
 * Websocket配置类
 */
@Configurable
public class WebSocketConfig {
    @Bean
//    注入ServerEndpointExporter bean对象，自动注册使用了@ServerEndpoint注解的bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
