<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.uce.omg.sso.util.DesUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<link rel="shortcut icon" type="image/x-icon" href="/omg-sso-main/images/portal/favicon.png" />
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
	<title>新乾坤系统-密码找回</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<header>
		<div><img src="${pageContext.request.contextPath}/images/repwd202006.png" style="margin-top: 10px;width: 140px;"/></div>
		<div class="title" style="margin-left: 15px;">新乾坤系统-密码找回</div>
	</header>
	<article>
		<div class="nav-title">密码找回</div>
		<div class="banner">
			<img src="${pageContext.request.contextPath}/images/3.png"/>
			<div class="publice-flex">
				<p class="steps-tip">验证身份</p>
				<p class="steps-tip set-pwd">设置新密码</p>
				<p class="steps-tip setps-on">完成</p>
			</div>
		</div>
		<div class="login-form private publice-flex" >
			<img src="${pageContext.request.contextPath}/images/complete.png"/>
			<div style="margin-left: 1rem;">
				<p class="success-title">密码重置成功</p>
				<p class="success-tip">请牢记您的新密码。<span onclick="toBack()">返回</span></p>
			</div>
		</div>
	</article>
	<script type="text/javascript">
	var skt = false;
		$(function(){
			<%-- add by zhangRb 20171020 添加首次登录设置密码提示  Strat --%>
			if(getQueryString("setPw") == "true") {
				$(".nav-title").text("首次登录设置密码");
				$(".set-pwd").text("设置密码");
				$(".success-title").text("密码设置成功");
				$(".success-tip").html("请牢记您的密码。<span onclick='toBack()'>返回</span>");
			}
			if(getQueryString("setPw") == "true" && skt == true) {
				document.title = "新乾坤系统-密码设置";
				$(".title").text("新乾坤系统-密码设置");
			}
			<%-- add by zhangRb 20171020 添加首次登录设置密码提示  End --%>
		});
		
		<%-- add by zhangRb 20171020 添加首次登录设置密码提示  Strat --%>
		//获取URl中参数的值
		function getQueryString(name)
		{
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
		}
		<%-- add by zhangRb 20171020 添加首次登录设置密码提示  End --%>
		function toBack() {
			location.href = '${refUrl}'=='' ? "${pageContext.request.contextPath}/toLogin.action" : '${refUrl}';
		}
		//移动/pc判断
	    showlogin()
	    function showlogin(){
	     if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
	    	$(".steps-tip").addClass("oneSteps-tip");
	    	$(".login-form").addClass("showform");
			$(".title").html("新乾坤-密码设置");
			skt = false;
	     } else {
	    	$(".steps-tip").removeClass("oneSteps-tip");
	    	$(".login-form").removeClass("showform");
	      	skt = true;
	     }
	    }
	</script>
</body>
</html>