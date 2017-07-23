package com.sales.websocket.service;

import java.io.File;
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

@ServerEndpoint(value = "/ws/sales", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class SalesWSEndpoint {
	private static Map<String, Session> clients = Collections.synchronizedMap(new HashMap<String, Session>());

	@OnOpen
	public void onOpen(final Session session) {
		System.out.println("Connection Opened ");
		clients.put(session.getId(), session);
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
		System.out.println("Connection Closed ");
		clients.remove(session.getId());

		String fileName = "sales" + SalesWSEndpoint.getClientFileName(session.getId()) + ".csv";
		File file = new File(fileName);
		if (file.exists() && !file.isDirectory()) {
			System.out.println("file exist ");
			file.delete();
		}

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

	public static boolean isClientFound(String clientId) {
		System.out.println("client ids ->" + clients.keySet());
		return clients.containsKey(clientId);

	}

	public static Session getSession(String clientId) {
		if (clients.containsKey(clientId)) {
			return clients.get(clientId);
		}
		return null;

	}

	public static String getClientFileName(String clientId) {
		if (clientId != null && !clientId.isEmpty()) {
			String arr[] = clientId.split(":");
			if (arr.length > 2) {
				return arr[2].trim() != null ? arr[2].trim() : "";
			}

		}
		return "";
	}

	public static void main(String[] args) {

		System.out.println(getClientFileName("127.0.0.1:8080->127.0.0.1:65499"));
	}

}