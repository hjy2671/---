package me.zhengjie.modules.chat.msg;

import lombok.Data;

import java.util.Date;

/**
 * @author hjy
 * @date 2024/4/27 19:28
 */
@Data
public class Message {

    private String fromNick;

    private String toNick;

    private Long fromId;

    private Long toId;

    private String content;

    private Date sendTime;

}
