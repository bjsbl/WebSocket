package com.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

@WebServlet(urlPatterns = { "/message" })
public class SocketServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;
	private final Set<WebSocketMessageInbound> connections = new HashSet<WebSocketMessageInbound>();

	private final AtomicInteger connectionIds = new AtomicInteger(0);

	@Override
	protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest request) {
		return new WebSocketMessageInbound(connectionIds.getAndIncrement());
	}

	class WebSocketMessageInbound extends MessageInbound {

		private String nickname;

		WebSocketMessageInbound(int id) {
			this.nickname = "Guest_" + id;
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			// TODO Auto-generated method stub
			connections.add(this);
			String message = String.format("* %s %s", nickname, "has joined.");
			broadcast(message);
		}

		@Override
		protected void onClose(int status) {
			// TODO Auto-generated method stub
			super.onClose(status);
		}

		@Override
		protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
			// TODO Auto-generated method stub
			String filteredMessage = String.format("%s: %s", nickname, message.toString());
			broadcast(filteredMessage);
		}

		private void broadcast(String message) {
			for (WebSocketMessageInbound connection : connections) {
				try {
					CharBuffer buffer = CharBuffer.wrap(message);
					connection.getWsOutbound().writeTextMessage(buffer);
				} catch (IOException ignore) {
				}
			}
		}
	}

}
