package me.zhengjie.modules.chat.msg;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hjy
 * @date 2024/4/27 19:28
 */
@Data
@TableName("chat_history")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistory {

    public static final String SEND = "send";
    public static final String UN_SEND = "un_send";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long fromId;

    private Long toId;

    private String content;

    private Date sendTime;

    private String isSend;

    public static ChatHistory from(Message message, String isSend) {
        return ChatHistory.builder()
                .fromId(message.getFromId())
                .toId(message.getToId())
                .content(message.getContent())
                .sendTime(message.getSendTime())
                .isSend(isSend)
                .build();
    }

}
