<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>POP - 在线编程平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="连接代码托管与应用引擎，为程序员搭建完整的在线编程平台">
<meta name="keywords" content="在线开发,代码托管,应用引擎">
<meta name="author" content="mass">
</head>

<body>
	<button onclick="opensocket()">Open</button>
	<button onclick="closeWebSocket()">Close</button>
	<h2>数据库</h2>
	<table id="database">
	</table>
	<h2>内存</h2>
	<table id="monitor">
	</table>
	<hr />
	<div id="message"></div>
</body>

<script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
Date.prototype.Format = function (fmt) {  
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
</script>
<script type="text/javascript">
	var websocket = null;
	var username = "rwmonitor";
	var filepath = "rwmonitor";

	function opensocket() {
		//判断当前浏览器是否支持WebSocket
		if ('WebSocket' in window) {
			var uri = "ws://" + document.location.host + "/rwsocket/"
					+ username + "?filepath=" + filepath;
			console.log(uri);
			websocket = new WebSocket(uri);
			//连接发生错误的回调方法
			websocket.onerror = function() {
				setMessageInnerHTML("socket error");
			};

			//连接成功建立的回调方法
			websocket.onopen = function(event) {
				setMessageInnerHTML("socket open");
			}

			//接收到消息的回调方法
			websocket.onmessage = function(event) {
				msg = JSON.parse(event.data);
				console.log(msg);
				var dbtable = JSON.parse(msg.database);
				var memtable = JSON.parse(msg.memory);
				var dbmonitor = $("#database");
				dbmonitor.empty();
				dbmonitor.append("<tr><th>path</th><th>users</th><th>timestamp</th></tr>");
				for(i in dbtable){			
					dbmonitor.append($("<tr>").append("<td>"+dbtable[i].path+"</td><td>"+dbtable[i].users+"</td><td>"+new Date(dbtable[i].timestamp).Format("yyyy-MM-dd hh:mm:ss")+"</td>"));
				}
				var memonitor = $("#monitor");
				memonitor.empty();
				memonitor.append("<tr><th>path</th><th>users</th></tr>");
				for(i in memtable){			
					memonitor.append($("<tr>").append("<td>"+memtable[i].path+"</td><td>"+memtable[i].users+"</td>"));
				}
			}

			//连接关闭的回调方法
			websocket.onclose = function() {
				setMessageInnerHTML("socket close");
			}
		} else {
			alert('Not support websocket')
		}
		//将消息显示在网页上
		function setMessageInnerHTML(innerHTML) {
			document.getElementById('message').innerHTML += innerHTML + '<br/>';
		}
	}

	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		if (websocket != null)
			websocket.close();
	}

	//关闭连接
	function closeWebSocket() {
		if (websocket != null)
			websocket.close();
	}
</script>
</html>
