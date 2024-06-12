package com.websocket.websocketdemo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocketdemo.pojo.ResultMessage;
//封装发送的消息内容
public class MessageUtils {
    public static String getMessage(boolean isSystemMessage,String fromName,Object message){
        try{
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setSystem(isSystemMessage);
            resultMessage.setMessage(message);
            if(fromName != null){
                resultMessage.setFromName(fromName);
            }
//            if(toName !=null ){
//                resultMessage.setToName(toName);
//            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(resultMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
