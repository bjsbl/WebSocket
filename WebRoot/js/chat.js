var chat = {
	socket : null, // WebSocket连接对象
	host : 'ws://127.0.0.1:8083/Socket/message', // WebSocket连接 url
	connect : function() { // 连接服务器
		window.WebSocket = window.WebSocket || window.MozWebSocket;
		if (!window.WebSocket) { // 检测浏览器支持
			console.log('Error: WebSocket is not supported .');
			return;
		}
		this.socket = new WebSocket(this.host); // 创建连接并注册响应函数
		this.socket.onopen = function() {
			console.log("websocket is opened .");
		};
		this.socket.onmessage = function(message) {
			console.log(message.data);
		};
		this.socket.onclose = function() {
			console.log("websocket is closed .");
			chat.socket = null; // 清理
		};
	},
	send : function(message) { // 发送消息方法
		if (this.socket) {
			this.socket.send(message);
			return true;
		}
		console.log('please connect to the server first !!!');
		return false;
	}
};