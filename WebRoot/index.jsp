<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
<title>WebSocket demo</title>
<style>
body {
	padding: 40px;
}

#outputPanel {
	margin: 10px;
	padding: 10px;
	background: #eee;
	border: 1px solid #000;
	min-height: 300px;
}
</style>
</head>
<body>
	<div class="main">
		<div class="login">
			<input type="button" id="buttonConnect" value="Connect to server" /> <input type="button" id="buttonClose" value="Disconnect" />
		</div>
		<div class="chat">
			发送内容：<input type="text" id="messagebox" size="60" /> <input type="button" id="buttonSend" value="Send Message" />
		</div>
	</div>
	<br>
	<%
		out.println("Session ID = " + session.getId());
	%>
	<div id="outputPanel"></div>
</body>
<script type="text/javascript" src="<%=path%>/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/chat.js"></script>
<script type="text/javascript">
	var console = {
		log : function(text) {
			$("#outputPanel").html($("#outputPanel").html() + text + "<br/>");
		}
	};
	$("#buttonSend").click(function() {
		var message = $("#messagebox").val();
		if (!message)
			return;
		if (!chat.send(message))
			return;
		$("#messagebox").val("");
	});
	$("#buttonConnect").click(function() {
		if (!chat.socket) {
			chat.connect();
		} else {
			console.log('websocket already exists .');
		}
	});
	$("#buttonClose").click(function() {
		if (chat.socket) {
			chat.socket.close();
		} else {
			console.log('websocket is not found .');
		}
	});
</script>
</html>
