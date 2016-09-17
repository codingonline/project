<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>POP - 在线编程平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="连接代码托管与应用引擎，为程序员搭建完整的在线编程平台">
<meta name="keywords" content="在线开发,代码托管,应用引擎">
<meta name="author" content="mass">
<!-- Site CSS -->
<link href="/static/lib/bootstrap-3.3.5-dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/static/custom/css/login.css" rel="stylesheet">
<link href="/static/lib/font-awesome-4.3.0/css/font-awesome.min.css"
	rel="stylesheet">
<!-- Favicons -->
<link rel="icon" href="/pages/favicon.ico">
</head>
<body>
	<div class="container">
		<div class="omb_login">
			<h3 class="omb_authTitle">
				登录 or <a href="/signup">邮箱注册</a>
			</h3>


			

			<div class="row omb_row-sm-offset-3">
				<div class="col-xs-12 col-sm-6">
					<form class="omb_loginForm" action="/login" autocomplete="off"
						method="POST" id="login-form">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user"></i></span>
							<input type="text" class="form-control" name="username"
								placeholder="请输入邮箱或用户名" required>
						</div>
						<span class="help-block" id="checkemail"></span>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							<input type="password" class="form-control" name="password"
								placeholder="请输入密码" required>
						</div>
						<span class="help-block" id="checkpwd"></span>

						<button class="btn btn-lg btn-primary btn-block" type="submit"
							data-loading-text="登录中，请稍等...">登录</button>
					</form>
				</div>
			</div>
			
<!-- 			<div class="row omb_row-sm-offset-3 omb_loginOr">
				<div class="col-xs-12 col-sm-6">
					<hr class="omb_hrOr">
					<span class="omb_spanOr">or</span>
				</div>
			</div> -->
			<!-- <div class="row omb_row-sm-offset-3 omb_socialButtons">
				<div class="col-xs-4 col-sm-2">
					<a href="/oauthBaidu" class="btn btn-lg btn-block omb_btn-baidu">
						<span class="hidden-xs"><i class="fa fa-paw fa-lg"></i></span>
					</a>
				</div>
				<div class="col-xs-4 col-sm-2">
					<a href="#" class="btn btn-lg btn-block omb_btn-xinlang"><span
						class="hidden-xs"><i class="fa fa-weibo fa-lg"></i></span> </a>
				</div>
				<div class="col-xs-4 col-sm-2">
					<a href="#" class="btn btn-lg btn-block omb_btn-github"><span
						class="hidden-xs"><i class="fa fa-github fa-lg"></i></span> </a>
				</div>
			</div> -->
			<div class="row omb_row-sm-offset-3">
				<div class="col-xs-12 col-sm-3">
					<p class="omb_forgotPwd">
						<a href="#forget-pwd" data-toggle="modal">忘记密码？</a>
					</p>
				</div>
				<div class="col-xs-12 col-sm-3">
					<label class="checkbox"> <input type="checkbox"
						name="remember" value="remember-me" checked>记住我
					</label>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" tabindex="-1" id="forget-pwd">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<p class="modal-title">找回密码</p>
					<div class="modal-body">
						<div class="alert alert-warning" role="alert">
							<p>若您是通过第三方平台（如百度）注册的账号，可通过第三方账号登录，然后在“用户设置”中修改您的密码</p>
							<p>若您是通过邮箱注册的账号，输入您的用户名，系统将发送找回密码邮件到您的邮箱中</p>
						</div>
						<form class="form-inline">
							<div class="form-group">
								<label for="uname" class="control-label">用户名</label> <input
									type="text" class="form-control" id="uname" name="uname"
									placeholder="请输入用户名" required autofocus>
							</div>
							<button type="submit" class="btn btn-default" id="forget-pwd-btn"
								data-loading-text="邮件发送中">发送邮件</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
	<script src="/static/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="/static/custom/js/login.js"></script>
</body>
</html>