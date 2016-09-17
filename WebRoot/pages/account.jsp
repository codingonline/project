<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href="/static/custom/css/account.css" rel="stylesheet">
<link rel="icon" href="/pages/favicon.ico">
</head>
<body>

	<div class="navbar navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand hidden-sm" href="/console">POP在线编程平台</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						id="username" data-toggle="dropdown">${user.username }<span
							class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">使用说明</a></li>
							<li><a href="#">常见问题</a></li>
							<li><a href="#">意见反馈</a></li>
							<li class="divider"></li>
							<li><a href="/account">账号设置</a></li>
						</ul></li>
					<li><a href="/logout" id="logout"><i
							class="fa fa-power-off"></i></a></li>
				</ul>
			</div>
		</div>
	</div>


	<div class="container">
		<div class="col-sm-8">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h1 class="panel-title">账号设置</h1>
				</div>
				<div class="panel-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label for="username" class="col-sm-4 control-label">用户名</label>
							<div class="col-sm-8">
								<p class="form-control-static" id="username">${user.username }</p>
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-4 control-label">密码</label>
							<div class="col-sm-8">
								<p class="form-control-static" id="password">
									<button class="btn btn-default btn-xs" id="set-pwd">修改密码</button>
								</p>
								<div id="pwd-info"></div>
							</div>
						</div>
						<form id="pwd-form" style="display:none">
							<div class="form-group">
								<label for="pwd1" class="col-sm-4 control-label">新密码</label>
								<div class="col-sm-8">
									<input type="password" name="pwd1" id="pwd1"
										class="col-sm-10 form-control" placeholder="请输入密码" required
										autofocus>
								</div>
							</div>
							<div class="form-group">
								<label for="pwd2" class="col-sm-4 control-label">确认新密码</label>
								<div class="col-sm-8">
									<input type="password" name="pwd2" id="pwd2"
										class="col-sm-10 form-control" placeholder="请再次输入新密码" required
										autofocus>
									<p class='help-block' />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-8 col-sm-offset-4">
									<button type="submit" class="btn btn-primary"
										data-loading-text="保存中，请稍等..." id="save-pwd">保存</button>
									<button type="button" class="btn btn-default" id="cancel-pwd">取消</button>
								</div>
							</div>
						</form>

						<div class="form-group">
							<label for="lastLogin" class="col-sm-4 control-label">上次登录时间</label>
							<div class="col-sm-8">
								<p class="form-control-static" id="lastLogin">${user.lastLogin }</p>
							</div>
						</div>
						<div class="form-group">
							<label for="email" class="col-sm-4 control-label">绑定邮箱</label>
							<div class="col-sm-8">
								<c:if test="${not empty user.email}">
									<p class="form-control-static" id="email">
										<span>${user.email }</span>&nbsp;
										<button class="btn btn-default btn-xs" id="set-email">修改</button>
									</p>
								</c:if>
								<c:if test="${empty user.email}">
									<p class="form-control-static" id="email">
										<span>${user.email }</span>&nbsp;
										<button class="btn btn-default btn-xs" id="set-email">绑定邮箱</button>
									</p>
								</c:if>
								<c:if test="${not user.registerStatus }">
									<p id="email-info" class='help-block'>认证邮件已发送，请进入邮箱查看</p>
								</c:if>
								<c:if test="${user.registerStatus }">
									<p id="email-info" class='help-block'></p>
								</c:if>
							</div>
						</div>
						<form id="email-form" style="display:none">
							<div class="form-group">
								<label for="save-email" class="col-sm-4 control-label">邮箱</label>
								<div class="col-sm-8">
									<input type="email" name="new-email"
										class="col-sm-10 form-control" placeholder="请输入邮箱" required
										autofocus>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-8 col-sm-offset-4">
									<button type="submit" class="btn btn-primary"
										data-loading-text="邮件发送中，请稍等..." id="save-email">保存</button>
									<button type="button" class="btn btn-default" id="cancel-email">取消</button>
								</div>
							</div>
						</form>
						<div class="form-group">
							<label for="sae" class="col-sm-4 control-label">SAE账号</label>
							<div class="col-sm-8">
								<c:if test="${empty user.unameSae}">
									<p class="form-control-static" id="sae">
										<span>${user.unameSae }</span>&nbsp;
										<button class="btn btn-default btn-xs" id="set-sae">绑定SAE账号</button>
									</p>
								</c:if>
								<c:if test="${not empty user.unameSae }">
									<p class="form-control-static" id="sae">
										<span>${user.unameSae }</span>&nbsp;
										<button class="btn btn-default btn-xs" id="set-sae">修改</button>
									</p>
								</c:if>
								<div id="sae-info"></div>
							</div>
						</div>
						<form id="sae-form" style="display:none">
							<div class="alert alert-warning" role="alert">
								<p>
									SAE用户名及密码将用于获取和部署SAE平台上的应用，用户名和密码为SAE平台安全邮箱和安全密码，不是SAE平台的登录账号(即微博账号和微博密码)！
									如已启用微盾动态密码，则密码应该是“安全密码”+“微盾动态密码”。点击<a
										href="http://www.sinacloud.com/ucenter/profile.html"
										target="_blank">SAE用户中心</a>查看和设置安全邮箱和安全密码，点击<a
										href="http://www.sinacloud.com/doc/sae/tutorial/code-deploy.html"
										target="_blank">这里</a>查看SAE代码部署说明
								</p>
							</div>
							<div class="form-group">
								<label for="sae-uname" class="col-sm-4 control-label">安全邮箱</label>
								<div class="col-sm-8">
									<input type="text" name="sae-uname"
										class="col-sm-10 form-control" placeholder="请输入SAE安全邮箱"
										required autofocus>
								</div>
							</div>
							<div class="form-group">
								<label for="sae-pwd" class="col-sm-4 control-label">安全密码</label>
								<div class="col-sm-8">
									<input type="password" name="sae-pwd1" id="sae-pwd1"
										class="col-sm-10 form-control" placeholder="请输入安全密码" required>
								</div>
							</div>
							<div class="form-group">
								<label for="sae-pwd1" class="col-sm-4 control-label">确认安全密码</label>
								<div class="col-sm-8">
									<input type="password" name="sae-pwd2" id="sae-pwd2"
										class="col-sm-10 form-control" placeholder="请再次输入安全密码"
										required>
									<p class="help-block" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-8 col-sm-offset-4">
									<button type="submit" class="btn btn-primary"
										data-loading-text="保存中，请稍等..." id="save-sae">保存</button>
									<button type="button" class="btn btn-default" id="cancel-sae">取消</button>
								</div>
							</div>
						</form>
						<div class="form-group">
							<label for="bae" class="col-sm-4 control-label">BAE账号</label>
							<div class="col-sm-8">
								<c:if test="${empty user.unameBaidu}">
									<p class="form-control-static" id="bae">
										<span>${user.unameSae }</span>&nbsp;
										<button class="btn btn-default btn-xs" id="set-bae">绑定BAE账号</button>
									</p>
								</c:if>
								<c:if test="${not empty user.unameBaidu }">
									<p class="form-control-static" id="bae">
										<span>${user.unameBaidu }</span>&nbsp;
										<button class="btn btn-default btn-xs" id="set-bae">修改</button>
									</p>
								</c:if>
								<div id="bae-info"></div>
							</div>
						</div>
						<form id="bae-form" style="display:none">
							<div class="alert alert-warning" role="alert">
								<p>
									BAE用户名及密码将用于获取和部署BAE平台上的应用，用户名和密码为登录百度开放云的账号和密码。点击<a
										href="http://bce.baidu.com/doc/BAE/index.html" target="_blank">这里</a>查看BAE说明文档
								</p>
							</div>
							<div class="form-group">
								<label for="bae-uname" class="col-sm-4 control-label">用户名</label>
								<div class="col-sm-8">
									<input type="text" name="bae-uname"
										class="col-sm-10 form-control" placeholder="请输入BAE用户名"
										required autofocus>
								</div>
							</div>
							<div class="form-group">
								<label for="bae-pwd1" class="col-sm-4 control-label">密码</label>
								<div class="col-sm-8">
									<input type="password" name="bae-pwd1" id="bae-pwd1"
										class="col-sm-10 form-control" placeholder="请输入密码" required>
								</div>
							</div>
							<div class="form-group">
								<label for="bae-pwd2" class="col-sm-4 control-label">确认密码</label>
								<div class="col-sm-8">
									<input type="password" name="bae-pwd2" id="bae-pwd2"
										class="col-sm-10 form-control" placeholder="请再次输入密码" required>
									<p class='help-block' />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-8 col-sm-offset-4">
									<button type="submit" class="btn btn-primary"
										data-loading-text="保存中，请稍等..." id="save-bae">保存</button>
									<button type="button" class="btn btn-default" id="cancel-bae">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
	<script src="/static/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="/static/lib/jquery/jquery.cookie.js"></script>
	<script src="/static/custom/js/account.js"></script>
</body>