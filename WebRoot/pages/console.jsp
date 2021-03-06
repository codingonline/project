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
<link href="/static/custom/css/console.css" rel="stylesheet">
<link rel="icon" href="/pages/favicon.ico">
</head>
<body>
	<div class="navbar navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand hidden-sm" href="/console">POP在线编程平台</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="#create" data-toggle="modal">新建项目</a></li>
					<!-- 					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">导入项目<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#import-bae" data-toggle="modal">BAE</a></li>
							<li><a href="#">SAE</a></li>
							<li><a href="#">Git仓库</a></li>
						</ul></li> -->
				</ul>
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

	<div class="col-md-2">
		<div class="selector">
			<ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
				<li class="active"><a href="#">所有项目</a></li>
				<li><a href="#">java</a></li>
				<li><a href="#">php</a></li>
				<li><a href="#">python</a></li>
			</ul>
		</div>
	</div>

	<div class="col-md-10 col-md-offset-2">
		<div class="projects"></div>
	</div>

	<!-- 新建项目 -->
	<div class="modal fade" id="create">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">新建项目</h4>
				</div>
				<div>
					<ul class="nav nav-tabs nav-justified">
						<li class="active"><a href="#empty" data-toggle="tab">空项目</a></li>
						<li><a href="#sample" data-toggle="tab">示例项目</a></li>
						<li><a href="#gitrepo" data-toggle="tab">Git仓库</a></li>
					</ul>
				</div>
				<!-- <form class="form-horizontal" id="create-form"> -->

				<div class="tab-content" id="create-panel">
					<div class="tab-pane active" id="empty">
						<form class="form-horizontal create-form">
							<div class="modal-body">
								<div class="form-group">
									<label for="app-name" class="col-sm-2 control-label">项目名称*</label>
									<!-- 项目名称-->
									<div class="col-sm-10">
										<input type="text" id="app-name" name="app-name"
											class="col-sm-10 form-control" placeholder="请输入项目名称" required
											autofocus>
									</div>
								</div>
								<div class="form-group">
									<label for="app-type" class="col-sm-2 control-label">项目类型*</label>
									<!-- 项目类型-->
									<div class="col-sm-10">

										<input type="radio" id="javaweb" name="app-type" class="img"
											value="javaweb" /> <label for="javaweb"><img
											src="../static/custom/img/index/java.png" class="img-rounded" /></label>

										<input type="radio" id="php" name="app-type" class="img"
											value="php" /> <label for="php"><img
											src="../static/custom/img/index/php.png" class="img-rounded" /></label>

										<input type="radio" id="python" name="app-type" class="img"
											value="python" /> <label for="python"><img
											src="../static/custom/img/index/python.png"
											class="img-rounded" /></label>

									</div>
								</div>
							</div>
							<div class="modal-footer">
								<input name="rst" type="reset" hidden>
								<button type="submit" class="btn btn-primary create-btn"
									data-loading-text="创建中，请稍等...">创建</button>
							</div>
						</form>
					</div>


					<div class="tab-pane" id="sample">
						<form class="form-horizontal create-form">
							<div class="modal-body">
								<div class="form-group">
									<!-- 项目名称-->
									<div class="col-sm-12">
										<input type="text" id="app-name-sample" name="app-name"
											class="col-sm-10 form-control" placeholder="请输入项目名称" required
											autofocus>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<select class="form-control" id="app-type-sample"
											name="app-type">
											<optgroup label="Java">
												<option value="javaweb_ssh">Spring 4 + Struts 2 +
													Hibernate Example</option>

											</optgroup>
										</select>
									</div>
								</div>
								<div class="modal-footer">
									<input name="rst" type="reset" hidden>
									<button type="submit" class="btn btn-primary create-btn"
										data-loading-text="创建中，请稍等...">创建</button>
								</div>
							</div>
						</form>
					</div>

					<div class="tab-pane" id="gitrepo">gitrepo</div>
				</div>
			</div>
		</div>
	</div>

	<!--导入bae项目-->
	<div class="modal fade" id="import-bae">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">
						导入<a href="#" data-toggle="tooltip" title="没有BAE应用？立即申请">BAE</a>项目
					</h4>
				</div>
				<form class="form-horizontal" id="import-bae-form">
					<div class="modal-body">
						<div class="form-group">
							<label for="bae-app-name" class="col-sm-2 control-label">项目名称*</label>
							<!-- 项目名称-->
							<div class="col-sm-10">
								<input type="text" class="col-sm-10 form-control"
									id="bae-app-name" name="bae-app-name" placeholder="请输入项目名称"
									required autofocus>
							</div>
						</div>
						<div class="form-group">
							<label for="bae-app-type" class="col-sm-2 control-label">项目类型*</label>
							<!-- 项目类型-->
							<div class="col-sm-10">
								<select id="bae-app-type" name="bae-app-type"
									class="form-control" required>
									<option value="">请选择...</option>
									<option value="java">Java Web</option>
									<option value="php">PHP</option>
									<option value="python">Python</option>
									<option value="nodejs">Node.js</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="bae-app-url" class="col-sm-2 control-label">Git/SVN
								地址*</label>
							<!-- 项目名称-->
							<div class="col-sm-10">
								<input type="text" id="bae-app-url" name="bae-app-url"
									class="col-sm-10 form-control" placeholder="请输入项目导入地址" required>
							</div>
						</div>
						<div class="form-group">
							<label for="bae-app-domain" class="col-sm-2 control-label">域名</label>
							<!-- 项目域名-->
							<div class="col-sm-10">
								<input type="text" id="bae-app-domain" name="bae-app-domain"
									class="col-sm-10 form-control" placeholder="请输入项目域名">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input name="rst" type="reset" hidden>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary"
							data-loading-text="创建中，请稍等..." id="import-bae-btn">保存</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 查看详细 -->
	<div class="modal fade" id="detailsModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">查看详细</h4>
				</div>
				<form class="form-horizontal">
					<div class="modal-body">
						<div class="form-group">
							<label for="appName" class="col-sm-3 control-label">项目名称</label>
							<div class="col-sm-8">
								<p class="form-control-static" id="appName"></p>
							</div>
						</div>
						<div class="form-group">
							<label for="appType" class="col-sm-3 control-label">项目类型</label>
							<div class="col-sm-8">
								<p class="form-control-static" id="appType"></p>
							</div>
						</div>
						<div class="form-group">
							<label for="appOwner" class="col-sm-3 control-label">项目创建人</label>
							<div class="col-sm-8">
								<p class="form-control-static" id="appOwner"></p>
							</div>
						</div>
						<div class="form-group">
							<label for="paasName" class="col-sm-3 control-label">PaaS平台</label>
							<div class="col-sm-8" id="paasName"></div>
						</div>
						<div class="form-group">
							<label for="importUrl" class="col-sm-3 control-label">代码仓库</label>
							<div class="col-sm-8">
								<p class="form-control-static" id="importUrl"></p>
							</div>
						</div>
						<div class="form-group">
							<label for="date" class="col-sm-3 control-label">创建日期</label>
							<div class="col-sm-8">
								<p class="form-control-static" id="date"></p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>


	<div class="modal fade" id="sharesModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">添加合作开发者</h4>
				</div>
				<form class="form-horizontal" id="addeveloper-form">
					<div class="modal-body">
						<div class="form-group">
							<div class="panel panel-info" id="developers-panel">
								<div class="panel-heading" id="appName-share"></div>
								<div class="panel-body">
									<table class="table" style="margin-bottom:0">
									</table>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="developer-name" class="col-sm-3 control-label">账号</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="developer-name"
									name="developer-name" placeholder="用户名或邮箱地址"
									style="width:320px" required>
								<p class="help-block" id="addeveloper-error"></p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input name="rst" type="reset" hidden>
						<button type="submit" class="btn btn-primary">确认添加</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 删除项目 -->
	<div class="modal fade" id="delete">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">删除项目</h4>
				</div>
				<div class="modal-body">项目删除后，将不可恢复，请确认操作。</div>
				<div class="modal-footer">
					<input name="rst" type="reset" hidden>
					<button type="button" class="btn btn-default" data-dismiss="modal">点错了</button>
					<button type="submit" class="btn btn-danger"
						data-loading-text="删除中，请稍等..." id="delete-btn">确认删除</button>
				</div>
			</div>
		</div>
	</div>

	<script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
	<script src="/static/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="/static/lib/jquery/jquery.cookie.js"></script>
	<script src="/static/custom/js/console.js"></script>
</body>
</html>