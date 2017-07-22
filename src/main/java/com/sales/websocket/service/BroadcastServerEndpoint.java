package com.sales.websocket.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.sales.service.model.Message.MessageDecoder;
import com.sales.service.model.Message.MessageEncoder;

@ServerEndpoint(value = "/ws", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class BroadcastServerEndpoint {
	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(final Session session) {
		sessions.add(session);
	}

	@OnClose
	public void onClose(final Session session) {
		sessions.remove(session);
	}

	// @OnMessage
	// public void onMessage( final Message message, final Session client )
	// throws IOException, EncodeException {
	// for( final Session session: sessions ) {
	// session.getBasicRemote().sendObject( message );
	// }
	// }

	@OnMessage
	public void onWebSocketText(String message) {
		System.out.println("Received TEXT message: " + message);
	}
}