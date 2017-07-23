package com.sales.service.model;

import java.io.StringReader;
import java.util.Collections;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Message pojo for socket communication
 * 
 * @author ramans
 *
 */
public class Message {
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public static class MessageEncoder implements Encoder.Text<Message> {
		@Override
		public void init(final EndpointConfig config) {
		}

		@Override
		public String encode(final Message message) throws EncodeException {
			return Json.createObjectBuilder().add("clientId", message.getClientId())
					.add("message", message.getMessage()).build().toString();
		}

		@Override
		public void destroy() {
		}
	}

	public static class MessageDecoder implements Decoder.Text<Message> {
		private JsonReaderFactory factory = Json.createReaderFactory(Collections.<String, Object> emptyMap());

		@Override
		public void init(final EndpointConfig config) {
		}

		@Override
		public Message decode(final String str) throws DecodeException {
			final Message message = new Message();

			JsonReader reader = factory.createReader(new StringReader(str));
			final JsonObject json = reader.readObject();
			message.setClientId(json.getString("clientId"));
			message.setMessage(json.getString("message"));

			return message;
		}

		@Override
		public boolean willDecode(final String str) {
			return true;
		}

		@Override
		public void destroy() {
		}
	}

	private String clientId;
	private String message;

	public Message() {
	}

	public Message(final String clientId, final String message) {
		this.clientId = clientId;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}