package com.chenyi.safe.controller;


import com.chenyi.safe.pojo.MsgInfo;
import com.chenyi.safe.pojo.SystemInfo;
import com.chenyi.safe.service.RedisService;
import com.chenyi.safe.utils.CurPool;
import com.chenyi.safe.utils.JsonUtils;
import com.chenyi.safe.utils.RedisUtils;
import com.chenyi.safe.utils.SpringContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.chenyi.safe.utils.SystemUtils.flattenMap;


/**
 * safe
 * 2024/6/17 上午1:56
 * websocket服务端
 *
 * @author chenyi
 * @since
 **/

@Component
@ServerEndpoint("/websocket/{userId}/{sessionId}")
public class WebSocket {

    private static final Logger log = LoggerFactory.getLogger(WebSocket.class);
    private Session session;

    @Autowired
    private RedisService redisService;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId, @PathParam("sessionId") String sessionId) {
        this.session = session;
        CurPool.webSockets.put(userId, this);
        List<Object> list = new ArrayList<>();
        list.add(sessionId);
        list.add(session);
        CurPool.sessionPool.put(userId, list);
        log.info("【websocket消息】有新的连接，总数为: {}", CurPool.webSockets.size());
    }

    @OnClose
    public void onClose() {
        Integer userId = Integer.parseInt(this.session.getRequestParameterMap().get("userId").get(0));
        CurPool.sessionPool.remove(userId);
        CurPool.webSockets.remove(userId);
        log.info("【websocket消息】连接断开，总数为: {}", CurPool.webSockets.size());
    }


    @OnMessage
    public void onMessage(String message) {
        try {
            MsgInfo messageInfo = JsonUtils.jsonToPojo(message, MsgInfo.class);
            // 处理消息
            String sessionId = this.session.getRequestParameterMap().get("sessionId").get(0);
            if (sessionId == null) {
                log.info("Session ID 错误");
            }
            // 系统状态
            if (messageInfo.getMessage() instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) messageInfo.getMessage();
                if (map.containsKey("CPU")) {
                    RedisService redisService = SpringContext.getBean(RedisService.class);
                    try {
                        redisService.setSystem(map);
                    } catch (Exception e) {
                        log.error("存储redis出错: {}", e.getMessage());
                    }
                }
            }
            sendTextMessage(Integer.valueOf((String) messageInfo.getUserId()), String.valueOf(messageInfo.getMessage()));
            log.info("【websocket消息】收到客户端消息: {}", message);
        } catch (Exception e) {
            log.error("处理消息时出错", e);
            // 可以发送错误信息回客户端
        }
    }


    public void sendAllMessage(String message) {
        for (WebSocket webSocket : CurPool.webSockets.values()) {
            System.out.println("【websocket消息】广播消息:" + message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendTextMessage(Integer userId, String message) {
        Session session = (Session) CurPool.sessionPool.get(userId).get(1);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
