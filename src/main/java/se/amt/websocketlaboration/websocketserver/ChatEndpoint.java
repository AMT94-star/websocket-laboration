package se.amt.websocketlaboration.websocketserver;


import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/chat")
public class ChatEndpoint {
    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New client connected to session: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        for (Session s : sessions) {
            if (s.isOpen()) {
                try {
                    s.getBasicRemote().sendText(message);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Client disconnected from session: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.getMessage();
        error.printStackTrace();
    }
}
