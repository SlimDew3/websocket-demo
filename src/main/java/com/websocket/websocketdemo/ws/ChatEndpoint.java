package com.websocket.websocketdemo.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocketdemo.pojo.Message;
import com.websocket.websocketdemo.utils.MessageUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * WebSocket注册位置
 */
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {
    //    用来存储每个客户端对应的endpoint
    private static Map<String, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();
    //    声明session对象
    private Session session;
    //    声明一个HttpSession对象，我们在HttpSession存储了用户名
    private HttpSession httpSession;

    /**
     * 链接关闭时被调用
     *
     * @param session
     */
    @OnClose
    public void OnClose(Session session) {
//         获取离线用户信息
        String user = httpSession.getAttribute("user").toString();
        onlineUsers.remove(user);
//        删除用户后更新实时在线用户
        String message = MessageUtils.getMessage(true, null, onlineUsers.entrySet().stream().collect(Collectors.toSet()));
//        推送给所有的用户
        broadcastAllUsers(message);
    }

    /**
     * 接受到客户端发送的数据
     *
     * @param session
     * @param message
     */
    @OnMessage
    public void OnMessage(Session session, String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Message mess = objectMapper.readValue(message, Message.class);
//           获取要将数据发送给的用户名
            String toName = mess.getToName();
//           获取消息数据
            String data = mess.getMessage();
//           获取给推送给指定用户的消息的格式
            String user = httpSession.getAttribute("user").toString();
            String resultMessage = MessageUtils.getMessage(false, user, data);
            onlineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接时被调用
     *
     * @param session
     * @param config
     */
    @OnOpen
    public void OnOpen(Session session, EndpointConfig config) {
        this.session = session;
//        获取httpSession对象
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
//        将用户存储到容器中
        String userName = httpSession.getAttribute("user").toString();

        onlineUsers.put(userName, this);
//
//        获取消息
        String message = MessageUtils.getMessage(true, null, onlineUsers.keySet().stream().collect(Collectors.toSet()));
        broadcastAllUsers(message);
    }

    /**
     * 消息广播
     *
     * @param name
     */
    private void broadcastAllUsers(String name) {
//        将所有消息推送给所有的用户
        onlineUsers.keySet().stream().forEach(item -> {
            ChatEndpoint chatEndpoint = onlineUsers.get(item);
            try {
                chatEndpoint.session.getBasicRemote().sendText(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
