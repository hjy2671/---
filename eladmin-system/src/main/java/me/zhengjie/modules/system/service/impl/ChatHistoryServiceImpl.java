package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.chat.msg.ChatHistory;
import me.zhengjie.modules.system.service.ChatHistoryService;
import me.zhengjie.modules.system.service.mapper.ChatHistoryMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ChatHistoryServiceImpl extends CommonServiceImpl<ChatHistoryMapper, ChatHistory> implements ChatHistoryService {

    private final ChatHistoryMapper chatMapper;

    @Async
    @Override
    public void saveChat(ChatHistory entity) {
        super.save(entity);
    }

    @Override
    public List<ChatHistory> getUnReceivedMessage(Long id) {
        return chatMapper.selectList(
                new LambdaQueryWrapper<ChatHistory>()
                        .eq(ChatHistory::getToId, id)
                        .eq(ChatHistory::getIsSend, ChatHistory.UN_SEND)
        );
    }

    @Override
    public void updateSendChatsStatus(List<ChatHistory> chatHistories) {
        chatHistories.forEach(c -> c.setIsSend(ChatHistory.SEND));
        chatMapper.updateBatch(chatHistories);
    }
}
