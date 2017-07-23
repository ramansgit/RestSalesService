package com.sales.websocket.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.sales.service.model.Message;
import com.sales.service.model.Message.MessageDecoder;
import com.sales.service.model.Message.MessageEncoder;

@ServerEndpoint(value = "/ws", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class BroadcastServerEndpoint {
	private static  Map<String, Session> clients = new HashMap<String, Session>();

	@OnOpen
	public void onOpen(final Session session) {
		System.out.println("CONNECTION OPENED ");
		clients.put(session.getId(), session);
		
		System.out.println(clients.keySet());
		try {
			session.getBasicRemote().sendObject(new Message(session.getId(), "CLIENT_CONNECTED"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(final Session session) {
		System.out.println("CONNECTION closed ");
		clients.remove(session.getId());
	}
	
	@OnError
	public void onError(Throwable cause) throws IOException, EncodeException {
		try {
			System.out.println("error: " + cause.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String message, final Session client) throws IOException, EncodeException {
		try {
			System.out.println("Received TEXT message: " + message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// @OnMessage
	// public void onWebSocketText(String message) {
	// System.out.println("Received TEXT message: " + message);
	// }

	public static boolean isClientFound(String clientId) {
		System.out.println("keys "+clients.keySet());
		return clients.containsKey(clientId);

	}

	public static Session getSession(String clientId) {
		System.out.println("keys "+clients.keySet());
		if (clients.containsKey(clientId)) {
			return clients.get(clientId);
		}
		return null;

	}

}