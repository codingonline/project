<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>POP - 控制台</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="连接代码托管与应用引擎，为程序员搭建完整的在线编程平台">
<meta name="keywords" content="在线开发,代码托管,应用引擎">
<meta name="author" content="mass">
<link href="/static/lib/bootstrap-3.3.5-dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/static/lib/font-awesome-4.3.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="/static/custom/css/login.css" rel="stylesheet">
<link rel="icon" href="/pages/favicon.ico">
</head>
<body>
	<div class="container">
		<div class="omb_login">
			<h3 class="omb_authTitle">
				<a href="/login">登录</a> or 邮箱注册
			</h3>
			<div class="row omb_row-sm-offset-3">
				<div class="col-xs-12 col-sm-6">
					<form class="omb_loginForm" action="/signup" autocomplete="off"
						method="POST" id="signup-form">
						<input name="action" value="register" hidden>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
							<input type="email" class="form-control" name="email"
								placeholder="请输入邮箱地址" required autofocus>
						</div>
						<span class="help-block" id="checkemail"></span>
						<button class="btn btn-lg btn-primary btn-block" type="submit"
							data-loading-text="邮件发送中，请稍等...">注册</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" tabindex="-1" id="popemail">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<p class="modal-title"></p>
				</div>
			</div>
		</div>
	</div>
	<script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
	<script src="/static/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="/static/custom/js/signup.js"></script>
</body>
</html>