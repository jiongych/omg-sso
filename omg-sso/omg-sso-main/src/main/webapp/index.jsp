<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
<title>认证系统-首页</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
	<style type="text/css">
		body{
			background: url(images/bg.jpg) no-repeat;
			background-position: 100% 100% ;
			background-size:cover; 
			overflow-x:hidden;
			overflow-y:hidden;
		}
		
		.login
		{
			/* position:relative; */
			animation:loginAnimal 1s;
			-moz-animation:loginAnimal 1s; /* Firefox */
			-webkit-animation:loginAnimal 1s; /* Safari and Chrome */
			-o-animation:loginAnimal 1s; /* Opera */
		}
		
		.login-logo
		{
			animation:loginLogoAnimal 1s;
			-moz-animation:loginLogoAnimal 1s; /* Firefox */
			-webkit-animation:loginLogoAnimal 1s; /* Safari and Chrome */
			-o-animation:loginLogoAnimal 1s; /* Opera */
		}
		
		.login-title{
		    margin-top: 150px;
			text-align: center;
			font-size: 40px;
			animation:loginTitleAnimal 2s;
			-moz-animation:loginTitleAnimal 2s; /* Firefox */
			-webkit-animation:loginTitleAnimal 1.5s; /* Safari and Chrome */
			-o-animation:loginTitleAnimal 2s; /* Opera */
		}
		
		
		@keyframes loginAnimal
		{
			0%   {right:100%;opacity:0}
			25%   {opacity:0.1}
			50%   {opacity:0.5}
			75%   {opacity:0.75}
			100% {right: 120px;opacity:1}
		}
		
		@-moz-keyframes loginAnimal /* Firefox */
		{
			0%   {right:100%;opacity:0}
			25%   {opacity:0.1}
			50%   {opacity:0.5}
			75%   {opacity:0.75}
			100% {right: 120px;opacity:1}
		}
		
		@-webkit-keyframes loginAnimal /* Safari and Chrome */
		{
			0%   {right:100%;opacity:0}
			25%   {opacity:0.1}
			50%   {opacity:0.5}
			75%   {opacity:0.75}
			100% {right: 120px;opacity:1}
		}
		
		@-o-keyframes loginAnimal /* Opera */
		{
			0%   {right:100%;opacity:0}
			25%   {opacity:0.1}
			50%   {opacity:0.5}
			75%   {opacity:0.75}
			100% {right: 120px;opacity:1}
		}
		
		@keyframes loginLogoAnimal
		{
			0%   { margin: -360px auto;}
			25%  { margin: -360px auto;}
			50%  { margin: -360px auto;}
			75%  { margin: -360px auto;}
			100% { margin: -77px auto;}
		}
		
		@-moz-keyframes loginLogoAnimal /* Firefox */
		{
			0%   { margin: -360px auto;}
			25%  { margin: -360px auto;}
			50%  { margin: -360px auto;}
			75%  { margin: -360px auto;}
			100% { margin: -77px auto;}
		}
		
		@-webkit-keyframes loginLogoAnimal /* Safari and Chrome */
		{
			0%   { margin: -360px auto;}
			25%  { margin: -360px auto;}
			50%  { margin: -360px auto;}
			75%  { margin: -360px auto;}
			100% { margin: -77px auto;}
		}
		
		@-o-keyframes loginLogoAnimal /* Opera */
		{
			0%   { margin: -360px auto;}
			25%  { margin: -360px auto;}
			50%  { margin: -360px auto;}
			75%  { margin: -360px auto;}
			100% { margin: -77px auto;}
		}
		
		@keyframes loginTitleAnimal
		{
			0%   { margin-right: -1100px;}
			25%  { margin-right: -1100px;}
			50%  { margin-right: -1100px;}
			75%  { margin-right: -1000px;}
			100% { margin-right: 0px;}
		}
		
		@-moz-keyframes loginTitleAnimal /* Firefox */
		{
			0%   { margin-right: -1100px;}
			25%  { margin-right: -1100px;}
			50%  { margin-right: -1100px;}
			75%  { margin-right: -1000px;}
			100% { margin-right: 0px;}
		}
		
		@-webkit-keyframes loginTitleAnimal /* Safari and Chrome */
		{
			0%   { margin-right: -1100px;}
			25%  { margin-right: -1100px;}
			50%  { margin-right: -1100px;}
			75%  { margin-right: -1000px;}
			100% { margin-right: 0px;}
		}
		
		@-o-keyframes loginTitleAnimal /* Opera */
		{
			0%   { margin-right: -1100px;}
			25%  { margin-right: -1100px;}
			50%  { margin-right: -1100px;}
			75%  { margin-right: -1000px;}
			100% { margin-right: 0px;}
		}
		
	</style>
</head>
<body>
	<div class="login" id="login">
		<div class="login-logo">
			<img src="${pageContext.request.contextPath}/images/logo.png"/>
		</div>
		<div class="login-title">
			<p>欢迎使用<Br/><Br/>优速统一登录平台</p>
		</div>
	</div>
	
	<div class="copyright">
        <div id="fullsearch" class="copy-text">
          <h2>&copy; 2017 优速物流有限公司版权所有</h2>
        </div>
      
    </div>
</body>
</html>