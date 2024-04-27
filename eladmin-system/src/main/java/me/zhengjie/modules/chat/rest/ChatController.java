package me.zhengjie.modules.chat.rest;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.chat.config.SocketStorage;
import me.zhengjie.modules.chat.msg.ChatHistory;
import me.zhengjie.modules.chat.msg.Message;
import me.zhengjie.modules.system.service.ChatHistoryService;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.EOFException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author hjy
 * @date 2024/4/27 19:26
 */
@Slf4j
@RestController
@ServerEndpoint(value = "/chat/{id}")
@AllArgsConstructor
public class ChatController {

    private final ChatHistoryService chatService;

    @OnOpen
    public void onOpen(@PathParam("id") Long id, Session session) {
        if (SocketStorage.putSession(id, session) != null){
            throw new RuntimeException("用户名已存在，请更换用户名。");
        }
        SocketStorage.putID(session.getId(), id);
        sendMessage(chatService.getUnReceivedMessage(id), id);
    }

    @OnClose
    public void onClose(Session session){
        Long id = SocketStorage.removeId(session.getId());
        SocketStorage.removeSession(id);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Message messageBody = JSONObject.parseObject(message, Message.class);
        if (messageBody == null){
            return;
        }
        Long fromId = SocketStorage.getId(session.getId());
        //设置发消息的人
        messageBody.setFromId(fromId);
        messageBody.setSendTime(new Date());

        //将消息转发给收消息的人
        sendMessage(messageBody);
    }

    private void sendMessage(Message message) {
        Session toSession = SocketStorage.getSession(message.getToId());
        if (toSession != null){
            try {
                toSession.getBasicRemote().sendText(JSONObject.toJSONString(message));
                chatService.saveChat(ChatHistory.from(message, ChatHistory.SEND));
            } catch (IOException e) {
                log.error(e.getMessage());
                chatService.saveChat(ChatHistory.from(message, ChatHistory.UN_SEND));
            }
        }else {
            chatService.saveChat(ChatHistory.from(message, ChatHistory.UN_SEND));
        }
    }

    private void sendMessage(List<ChatHistory> chatHistories, Long toId) {
        Session toSession = SocketStorage.getSession(toId);
        if (toSession != null){
            try {
                toSession.getBasicRemote().sendText(JSONObject.toJSONString(chatHistories));
                chatService.updateSendChatsStatus(chatHistories);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        if (error instanceof EOFException && error.getMessage() == null) {
            SocketStorage.removeAll(session.getId());
        }
    }

}
