/**
 * Created by w2qiao on 2015/7/14.
 */


imgbase = '../static/custom/img/console/';
url = 'http://123.57.2.1:8000/';

applist = [];
username = $("#username").text().trim();
delapp = "";

(function(){
	$.get('/console', {action: 'apps'}, function(data){
		applist = $.parseJSON(data);
		var app_panel = $('.projects');
		for(i in applist){
			var panel = $('<div class="panel panel-default project"/>');
			appendApp(panel, applist[i]);
			app_panel.append(panel);
		}
	});
}());

$(function(){
	$(".create-form").on('submit', function (e) {
		e.preventDefault();
		var active = $("#create-panel").find(".active");
		var selected = active[0].id;
		var appname, apptype;
		appname = active.find("input[name='app-name']").val().trim();
		if(selected=="empty"){
			apptype = active.find("input[name='app-type']:checked").val().trim();
		}else{
			apptype = active.find("select[name='app-type']").val().trim();
		}
		
		var btn = $(".create-btn").button('loading');
		$.post('/console', {action: 'create', appname: appname, apptype: apptype, username: username}, function(data, status){
			console.log(data, status);
			var app = $.parseJSON(data);
			applist.push(app);
			$("#create").modal('hide');
			btn.button('reset');
			$("input[name='rst']").click();
			var parent = $("<div class='panel panel-default project'></div>");
			appendApp(parent, app);
			$(".projects").prepend(parent);
		});
	});
	$("input[name='app-name']").on("blur", function(e){
		var active = $("#create-panel").find(".active");
		var appname = active.find("input[name='app-name']").val().trim();
		var digits = new RegExp(/^\d+$/);
		if(digits.test(appname)){
			disform(active.find("input[name='app-name']"));
			active.find("input[name='app-name']").next().remove();
			active.find("input[name='app-name']").after($("<div>").addClass("help-block").text("项目名不能全为数字"));
		}else{
			enform(active.find("input[name='app-name']"));
			active.find("input[name='app-name']").next().remove();
		}
	})
	$("#import-bae-form").on('submit', function (e) {
		e.preventDefault();
		console.log($(this).serialize());
		var btn = $("#import-bae-btn").button('loading');
		var appname = $("#bae-app-name").val().trim();
		var apptype = $("#bae-app-type").val().trim();
		var url = $("#bae-app-url").val().trim();
		var domain = $("#bae-app-domain").val().trim();
		$.post('/console', {action: 'import-bae', appname: appname, apptype: apptype, username: username, url: url, domain: domain, paasname: 'bae'}, 
				function(data, status){
			console.log(data, status);
			var app = $.parseJSON(data);
			applist.push(app);
			$("#import-bae").modal('hide');
			btn.button('reset');
			$("input[name='rst']").click();
			var parent = $("<div class='panel panel-default project'></div>");
			appendApp(parent, app);
			$(".projects").prepend(parent)
		});
	});

	$("#addeveloper-form").on('submit', function(e){
		e.preventDefault();
		var _this = $("#addeveloper-form");
		var developer_name = _this.find("#developer-name").val();
		var rights = _this.find($('input:radio:checked')).val();
		var appname = $("#appName-share").text();
		$.post('/console', {
			action: 'addeveloper',
			username: username,
			developer_name: developer_name,
			rights: rights,
			appname: appname,
		}, function(data, status){
			$("input[name='rst']").click();
			var app = $.parseJSON(data);
			$("#addeveloper-error").text("");
			refreshDeveloper(app.ownerName, app.appName, developer_name);
		});
	});

	$("#developer-name").on('blur', function(e){
		var developer_name = $(this).val();
		var appname = $("#appName-share").text();
		if(developer_name!=""){
			$.post('/console',{
				action: 'checkDeveloper',
				username: username,
				developer_name: developer_name,
				appname: appname,
			}, function(data, status){
				if(data!="success"){
					disform($("#developer-name"));
					$("#addeveloper-error").text(data);
				}else{
					enform($("#developer-name"));
					$("#addeveloper-error").text('');
				}
			});
		}
	});

	$("#delete").find('button[type="submit"]').on('click', function(){
		console.log("delete");
		var btn = $("#delete-btn").button('loading');
		var _this = $(".projects").find('.panel-heading a[data-appname="'+delapp+'"] ').parents('.panel');
		$.post("/console", {action: 'delete', appname: delapp, username: username}, function(data, status){
			if(status=='success'){
				btn.button('reset');
				_this.remove();
				applist.splice(appIndex(delapp), 1);
				$("#delete").modal('hide');
			}
		});
	});

	$(document).on('click', ".close[data-toggle='modal']", function () {
		delapp = $(this).parents('.panel').find('.panel-heading a').data('appname');
	});

	$(".selector").children().on('click', function(event){
		var target = $(event.target);
		var children = $(this).find(".active").removeClass("active");
		$(target).parent().addClass("active");
		var nav_text = target.text();
		if(nav_text!=null){
			switch(nav_text){
			case "java":
				showApp("javaweb");
				break;
			case "php":
				showApp("php");
				break;
			case "python": 
				showApp("python");
				break;
			default:
				showAllApp();
			}
		}
	});
	$('#detailsModal').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget); // Button that triggered the modal
		var appname = button.data('appname');
		var app = applist[appIndex(appname)];
		// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
		var modal = $(this);
		var date = new Date(app.date);
		modal.find('.modal-body #appName').text(appname);
		modal.find('.modal-body #appType').text(app.appType);
		modal.find('.modal-body #appOwner').text(app.ownerName);
		modal.find('.modal-body #date').text(date.getFullYear() + "-"  + (date.getMonth()+1) + '-' + date.getDate());
		var paas = modal.find('.modal-body #paasName');
		if(app.paasName==null||app.paasName==""){
			paas.parents('.form-group').css('display', 'none');
		}else{
			paas.parents('.form-group').css('display', 'inherit');
			paas.parents('.form-group').find('p').remove();
			paas.append($('<p class="form-control-static"></p>').text('平台名称：  ' + app.paasName));
			if(app.gitUrl!=null&&app.gitUrl!=""){
				paas.append($('<p class="form-control-static"></p>').text('平台Git地址：  ' + app.gitUrl));
			}else if(app.svnUrl!=null&&app.svnUrl!=""){
				paas.append($('<p class="form-control-static"></p>').text('平台SVN地址：  ' + app.svnUrl));
			}
		}
		var importUrl = modal.find('.modal-body #importUrl');
		if(app.importUrl==null||app.importUrl==""){
			importUrl.parents('.form-group').css('display', 'none');
		}else{
			importUrl.parents('.form-group').css('display', 'inherit');
			importUrl.text(app.importUrl);
		}
	});

	$('#sharesModal').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget); // Button that triggered the modal
		var appname = button.data('appname');
		var modal = $(this);
		modal.find('#appName-share').text(appname);
		var username = $("#username").text().trim();
		refreshDeveloper(username, appname);
	});

});

function appendApp(parent, app){
	var token = $.cookie("token");
	var heading_a = $('<a></a>');
	var href = url + '?token=' + token + '&appname=' + app.appName;
	heading_a.attr({
		"href": href,
		"target": "_blank",
		"data-appname": app.appName
	});
	heading_a.text(app.appName);
	var heading_close = $('<a></a>');
	heading_close.attr({
		"class": "close",
		"data-toggle": "modal",
		"title": "删除项目",
		"href": "#delete",
	});
	var heading = $('<div class="panel-heading"></div>').append(heading_a, heading_close.append("<span>&times;</span>"));	

	var body = $('<div class="panel-body"></div>');
	var content_img = $('<div class="content-img"></div>');
	var content_img_ = $('<img>');
	var img_src = "http://placehold.it/94x112";
	if(app.paasName!=null&&app.importUrl!=null){
		img_src = imgbase + app.appType + (app.paasName!=""?"-"+app.paasName:"") + (app.importUrl!=""?"-git":"") + "-sm.png";
	}else{
		img_src = imgbase +  app.appType +  "-sm.png";
	}
	content_img_.attr({
		"src": img_src
	});

	var content_info = $('<div class="content-info"></div>');
	var info_date = $('<p></p>');
	var date = new Date(app.date);
	info_date.text("创建于 " + date.getUTCFullYear() + "-"  + (date.getUTCMonth()+1) + '-' + date.getUTCDate());
	var info_details = $('<p></p>');
	var info_details_a = $('<a></a>');
	info_details_a.attr({
		"href": "#detailsModal",
		"data-toggle": "modal",
		"data-appname": app.appName,
	});
	info_details_a.html('查看详细')
	info_details.append(info_details_a);

//	var info_rights = $('<p></p>');
//	if(app.ownerName==app.userName){
//		var share = $('<a></a>');
//		share.attr({
//			"href": "#sharesModal",
//			"data-toggle": "modal",
//			"data-appname": app.appName
//		});
//		info_rights.append(share.append($('<i class="fa fa-share-alt"></i>'), '&nbsp;合作开发'));
//	}

	body.append(content_img.append(content_img_), content_info.append(info_date, /*info_rights,*/ info_details));

	parent.on('click', function(event){
		var target = $(event.target);
		console.log(target.prop("tagName"));
		if(target.prop("tagName").toLowerCase()!="a"&&target.prop("tagName").toLowerCase()!="span"){
			window.open(href, '_blank');
		}
	})
	.attr({"data-toggle": "tooltip", "title": "点击进入开发"});
	parent.append(heading, body);
	$('[data-toggle="tooltip"]').tooltip();
}

function refreshDeveloper(ownerName, appName){
	$.get('/console', {action: 'developers', ownerName: ownerName, appName: appName}, function(data, status){
		console.log(data);
		var apps = $.parseJSON(data);
		var developers_panel = $('#sharesModal').find(".modal-body #developers-panel");
		var developers = developers_panel.find("table");
		if(apps!=null){
			developers.children().remove();
			developers.append($('<tr><th>用户名</th><th>操作</th></tr>'));
			for(var i=0; i<apps.length; i++){
				var app = apps[i];
				var row = $('<tr><td>'+app.userName+'</td><td><a href="#">删除</a></td></tr>')
				developers.append(row);
			}

			developers_panel.css('display', 'inherit');
		}else{
			developers_panel.css('display', 'none');
		}
	});
}

function appIndex(appname){
	for(var i=0; i<applist.length; i++){
		var app = applist[i];
		if(app.appName==appname){
			return i;
		}
	}
	return applist.length;
}

function showApp(type){
	var projects = $(".projects");
	projects.children().remove();
	var app, parent;
	for(var i=0; i<applist.length; i++){
		app = applist[i];
		if(app.appType==type){
			parent = $("<div class='panel panel-default project'></div>");
			appendApp(parent, app);
			projects.append(parent);
		}
	}
}

function showAllApp(){
	var projects = $(".projects");
	projects.children().remove();
	for(var i=0; i<applist.length; i++){
		var app = applist[i];
		var parent = $("<div class='panel panel-default project'></div>");
		appendApp(parent, app);
		projects.append(parent);
	}
}

function disform(e){
	e.parents('.form-group').addClass('has-error');
	$("button[type='submit']").attr({"disabled":"disabled"});
}

function enform(e){
	e.parents('.form-group').removeClass('has-error');
	$("button[type='submit']").removeAttr("disabled");
}