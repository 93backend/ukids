package com.multi.ukids.chat;

import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component("chatHandler")
public class ChatHandler extends TextWebSocketHandler {

	private ArrayList<WebSocketSession> members;
	
	public ChatHandler() {
		members = new ArrayList<WebSocketSession>();
		System.out.println("소켓 생성됨!");
	}
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("연결 성공!!");
		members.add(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message.getPayload());
		
		for(WebSocketSession wss : members) {
			wss.sendMessage(message);
		}
	}
	
	//연결이 끊겼을 때 동작하는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("연결 종료!!");
		members.remove(session);
	}
}

