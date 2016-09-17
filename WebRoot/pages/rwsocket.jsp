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
    Welcome<br/>
    <input id="uname" type="text" value="pkusei"/>
    <input id="filepath" type="text" value="/123/123.txt"/>
    <br/>
    <textarea id="text" rows="15" cols="100"></textarea>
    <br/>
    <button onclick="opensocket()">Open</button>
    <button onclick="send()">Save</button>    
    <button onclick="closeWebSocket()">Close</button>
    <button onclick="clearmsg()">Clear</button>
    <div id="message">
    </div>
  </body>
  
  <script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
  <script type="text/javascript">
      var websocket = null;
      var username = null;
      var sender = null;
      var oldString = document.getElementById("text").value;
      
      function opensocket(){
      	//alert("open");
      	//判断当前浏览器是否支持WebSocket
      	username = document.getElementById("uname").value;
	      if('WebSocket' in window){
	      	  var uri = "ws://" + document.location.host + "/rwsocket/"+ document.getElementById("uname").value +"?filepath="
	      	  + encodeURIComponent(document.getElementById("filepath").value);
	      	  console.log(uri);
	          websocket = new WebSocket(uri);
	          //连接发生错误的回调方法
		      websocket.onerror = function(){
		          setMessageInnerHTML("error");
		      };
		       
		      //连接成功建立的回调方法
		      websocket.onopen = function(event){
		          setMessageInnerHTML("open");
		      }
		       
		      //接收到消息的回调方法
		      websocket.onmessage = function(event){
		         
		          msg = JSON.parse(event.data);
		          if(msg.code==201){
		          	 	setMessageInnerHTML(msg.content);
		          	 	var queue = JSON.parse(msg.content);
		          	 	if(sender==null&&queue[0]==username){
		          	 		sender = setInterval(send, 1000);
		          	 	}
		          }else if(msg.code==202){
		          		document.getElementById("text").value = msg.content;
		          }
		          
		      }
		       
		      //连接关闭的回调方法
		      websocket.onclose = function(){
		      	  clearInterval(sender);
		      	  sender=null;
		          setMessageInnerHTML("close");
		      }
	      }
	      else{
	          alert('Not support websocket')
	      }
	            //将消息显示在网页上
     	 	function setMessageInnerHTML(innerHTML){
          		document.getElementById('message').innerHTML += innerHTML + '<br/>';
      		}
      }
      
      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function(){
      	if(websocket!=null)
          websocket.close();
      }

      //关闭连接
      function closeWebSocket(){
          if(websocket!=null)
          	websocket.close();
      }
       
      //发送消息
      function send(){
      		var newString = document.getElementById("text").value;
      		if(oldString!=newString&&websocket!=null){
      			console.log('send');
      			oldString = newString;
      			var message = document.getElementById('text').value;
         		var msg = new Object();
          		msg['content'] = message;
          		msg['code'] = 102;
          		msg['timestamp'] = Date.parse(new Date()).toString();
  				websocket.send(JSON.stringify(msg));
      		}
          
      }
      
      //清空消息栏
      function clearmsg(){
      		document.getElementById("message").innerHTML="";
      		document.getElementById("text").value="";
      }
      
      $(document).click(function(e){
			var target = e.target;
			if($(target).attr("id")=="text"){
				if(websocket!=null&&websocket.readyState==websocket.CLOSED){
					opensocket();
				}
			}
      });
      
      
  </script>
</html>
