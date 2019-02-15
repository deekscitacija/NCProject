package com.ftn.nc.NCBackend.sockets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//@Component
public class PaymentSocket extends TextWebSocketHandler {
	
	static Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.println("Opened");
		System.out.println("Opened");
		System.out.println("Opened");
		sessions.add(session);
	}
	
	public void afterConnectionClosed(WebSocketSession session) {
		System.out.println("Closed");
		//LoggedUser loggedUser = (LoggedUser) session.getAttributes().get("loggedUser");
		//sessions.remove(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {

		System.out.println(message.toString());
	}

}
