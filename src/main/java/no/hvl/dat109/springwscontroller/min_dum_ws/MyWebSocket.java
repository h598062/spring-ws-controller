package no.hvl.dat109.springwscontroller.min_dum_ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocket extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(MyWebSocket.class);


	// Use a ConcurrentHashMap to store sessions. It's thread-safe.
	private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("WebSocket opened: {}", session.getId());
		sessions.put(session.getId(), session);
		session.sendMessage(new TextMessage("You are now connected to the server"));
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		logger.info("Message received: {}", message.getPayload());
		// Iterate over the sessions and send the message to each one
		for (WebSocketSession webSocketSession : sessions.values()) {
			webSocketSession.sendMessage(new TextMessage(message.getPayload()));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("Closing a WebSocket due to {}", status.getReason());
		sessions.remove(session.getId());
	}
}
