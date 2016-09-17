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
			<h3 class="omb_authTitle">请完善您的用户信息</h3>
			<div class="row omb_row-sm-offset-3">
				<div class="col-xs-12 col-sm-6">
					<form class="omb_loginForm" action="/signup" autocomplete="off"
						method="POST" id="userinfo-form">
						<input name="action" value="completeUserInfo" hidden> <input
							name="id" value="${user.id }" hidden>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user"></i></span>
							<input type="text" class="form-control" name="username"
								placeholder="请输入用户名" required autofocus>
						</div>
						<span class="help-block" id="checkusername"></span>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							<input type="password" class="form-control" name="password"
								placeholder="请输入密码" required>
						</div>
						<span class="help-block"></span>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							<input type="password" class="form-control" name="password1"
								placeholder="请再次输入密码" required>
						</div>
						<span class="help-block" id="checkpwd"></span>
						<button class="btn btn-lg btn-primary btn-block" type="submit">确定</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
	<script src="/static/custom/js/welcome.js"></script>
</body>