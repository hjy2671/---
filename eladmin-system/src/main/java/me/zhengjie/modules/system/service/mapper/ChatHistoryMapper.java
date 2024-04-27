package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.chat.msg.ChatHistory;
import me.zhengjie.modules.system.domain.RepairAndOss;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatHistoryMapper extends CommonMapper<ChatHistoryMapper, ChatHistory> {
}
