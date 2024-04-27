package me.zhengjie.modules.chat.config;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hjy
 * @date 2024/4/27 19:30
 */
public class SocketStorage {

    /**
     * 用户id，session
     */
    public static ConcurrentHashMap<Long, Session> SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 根据Socket会话ID存储的name的Map
     * key:sessionId, value:id
     */
    public static ConcurrentHashMap<String, Long> ID_MAP = new ConcurrentHashMap<>();

    public static Session putSession(Long id, Session session) {
        return SESSION_MAP.putIfAbsent(id, session);
    }

    public static void putID(String sessionId, Long id) {
        ID_MAP.put(sessionId, id);
    }

    public static Long removeId(String id) {
        return ID_MAP.remove(id);
    }

    public static void removeSession(Long id) {
        SESSION_MAP.remove(id);
    }

    public static Long getId(String sessionId) {
        return ID_MAP.get(sessionId);
    }

    public static Session getSession(Long id) {
        return SESSION_MAP.get(id);
    }

    public static void removeAll(String id) {
        Long userId = ID_MAP.remove(id);
        if (userId != null) {
            SESSION_MAP.remove(userId);
        }
    }
}
