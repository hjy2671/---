package me.zhengjie.modules.system.service;


import me.zhengjie.modules.chat.msg.ChatHistory;

import java.util.List;

/**
 * @author hjy
 * @date 2024/3/3 20:48
 */
public interface ChatHistoryService {


    void saveChat(ChatHistory chatHistory);

    List<ChatHistory> getUnReceivedMessage(Long id);

    void updateSendChatsStatus(List<ChatHistory> chatHistories);
}
