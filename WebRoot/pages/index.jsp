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
<!-- Site CSS -->
<link href="/static/lib/bootstrap-3.3.5-dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/static/custom/css/index.css" rel="stylesheet">
<!-- Favicons -->
<link rel="icon" href="/pages/favicon.ico">
<script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
<script src="/static/lib/jquery/jquery.cookie.js"></script>
<script src="/static/custom/js/index.js"></script>
</head>
<body>

	<div class="navbar navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand hidden-sm" href="#">POP在线编程平台</a>
			</div>
			<div class="navbar-collapse collapse" role="navigation">
				<ul class="nav navbar-nav">
					<li><a href="#projects">项目</a></li>
					<li><a href="#features">特色</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/login">登录</a></li>
				</ul>
			</div>

		</div>
	</div>

	<div class="jumbotron masthead">
		<div class="container">
			<h1>POP</h1>

			<h2>Public Online Programming</h2>

			<h3>连接在线代码托管与在线应用引擎，为程序员搭建完整的在线开发平台</h3>

			<p>
				<a class="btn btn-lg btn-outline-inverse " href="/login">立即体验</a>
			</p>
		</div>
	</div>

	<div class="container projects">
		<div class="projects-header page-header">
			<a id="projects"></a>

			<h2>{ 项目 }</h2>

			<p class="info">使用POP，您可以选择以下项目类型来开发web应用</p>
		</div>

		<div class="row">
			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<div class="thumbnail">
					<img width="300" height="150"
						src="/static/custom/img/index/java.png" alt="java web">
				</div>
			</div>

			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<div class="thumbnail">
					<img width="300" height="150"
						src="/static/custom/img/index/php.png" alt="php">
				</div>
			</div>

			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<div class="thumbnail">
					<img width="300" height="150"
						src="/static/custom/img/index/python.png" alt="python">
				</div>
			</div>

			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<div class="thumbnail">
					<img width="300" height="150"
						src="/static/custom/img/index/nodejs.png" alt="node.js">
				</div>
			</div>
		</div>

		<div class="projects-header page-header">
			<a id="features"></a>

			<h2>{ 特色 }</h2>

			<p class="info">POP整合了以下功能，为您创造全栈式开发环境</p>
		</div>

		<div class="row">
			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<div class="thumbnail">
					<img width="300" height="150"
						src="/static/custom/img/index/code.png" alt="csdn code">

					<div class="caption">
						<h3>在线代码托管</h3>

						<p>CSDN旗下的开源平台，面向国内开发者提供提供项目托管、代码片托管、社交编程、组织管理等服务。</p>
					</div>
				</div>
			</div>

			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<div class="thumbnail">
					<img width="300" height="150"
						src="/static/custom/img/index/bae.png" alt="bae">

					<div class="caption">
						<h3>在线应用引擎</h3>

						<p>提供多语言、弹性的服务端运行环境，能帮助开发者快速开发并部署应用</p>
					</div>
				</div>
			</div>

			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<div class="thumbnail">
					<img width="300" height="150"
						src="/static/custom/img/index/monitor.png" alt="monitor">

					<div class="caption">
						<h3>在线缺陷查找</h3>

						<p>通过静态分析代码，查找和修复代码中潜在的缺陷，提高代码质量</p>
					</div>
				</div>
			</div>

			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<div class="thumbnail">
					<img width="300" height="150"
						src="/static/custom/img/index/code_search.png" alt="code search">

					<div class="caption">
						<h3>在线代码搜索</h3>

						<p>输入待实现的功能描述，快速准确搜索相似功能的参考代码，提升开发效率</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<footer class="footer">
		<div class="container">
			<ul class="footer-links">
				<li>合作网站：<a href="http://code.csdn.net/">CSDN CODE 代码托管</a></li>
				<li><a href="http://developer.baidu.com/cloud/rt">BAE 应用引擎</a></li>
			</ul>
			<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>&nbsp;联系我们：masspop@126.com
			<div class="row footer-bottom">&copy; PKU MASS 2016</div>
		</div>
	</footer>

	<!-- Bootstrap core JavaScript
================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- 	<script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
	<script src="/static/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="/static/lib/jquery/jquery.cookie.js"></script>
	<script src="/static/custom/js/index.js"></script> -->
</body>
</html>
