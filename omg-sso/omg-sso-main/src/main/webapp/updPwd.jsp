<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.uce.omg.sso.util.DesUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="/omg-sso-main/images/portal/favicon.png" />
<title>新乾坤系统-修改密码</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/encryptionAlgorithm.js"></script>
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo_12.png"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body onload="auth()">
	
	<div id="login" style="scrolling:no">
		<form id="form" action="" method="post">
			<input type="hidden" name="refUrl" value="${refUrl }" id="refUrl">
			<input type="hidden" name="systemCode" value="${systemCode }" id="systemCode">
			<input type="hidden" name="userName" value="${userName }" id="userName">
			<input type="hidden" name="tokenId" value="${tokenId }" id="tokenId">
			<table style="width:500px;">
				<tr><td colspan="4" style="height:130px"></td></tr>
				<tr>
					<td style="width:250px;height:20px"></td>
					<td align="left" style="width:40px"></td>
					<td align="left" style="width:120px"></td>
					<td></td>
				</tr>
				<tr>
					<td style="height:20px"></td>
					<td align="left" style="width:40px"><font style="font-size:14;font-family:'Arial Black';color:#004b88">旧密码</font></td>
					<td align="left"><input type="password" class="user_input" style="width:120px;text-transform:uppercase" name="oldPassword" id="oldPassword" onmouseover="this.className='user_input_over'" onmouseout="this.className='user_input'"/></td>
					<td></td>
				</tr>
				<tr>
					<td style="height:20px"></td>
					<td align="left" style="width:40px;height:20px"><font style="font-size:14;font-family:'Arial Black';color:#004b88">新密码</font></td>
					<td align="left"><input type="password" class="user_input" style="width:120px" id="password" onkeyup="checkPassword()" maxlength="20" onmouseover="this.className='user_input_over'" onmouseout="this.className='user_input'"/></td>
					<td></td>
				</tr>
				<tr>
					<td style="height:20px"></td>
					<td align="left" style="width:40px;height:20px"><font style="font-size:14;font-family:'Arial Black';color:#004b88"></font></td>
					<td align="left"><span id="sp" style="display: none;">强度：</span><span id="strength"></span></td>
					<td></td>
				</tr>
				<tr>
					<td style="height:20px"></td>
					<td align="left" style="width:70px;height:20px"><font style="font-size:14;font-family:'Arial Black';color:#004b88">确认密码</font></td>
					<td align="left"><input type="password" class="user_input" style="width:120px" id="newPassword" name="newPassword" onmouseover="this.className='user_input_over'" onmouseout="this.className='user_input'"/></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td align="left" style="height:20px" colspan="2" ><div id="errorMsg" class="errorInfo"></div></td>
				</tr>
				<tr>
					<td style="height:20px"></td>
					<td align="left" colspan="3">
						<input type="button" value="修改" id="subBtn" class="loginbutton" style="margin-left:10px;" onmouseout="this.className='loginbutton'" onmouseover="this.className='loginbuttonhover'" onfocus="this.blur();" onclick="updPwd()"/>
						<input type="reset" value="重置" class="loginbutton" onmouseout="this.className='loginbutton'"	onmouseover="this.className='loginbuttonhover'" onfocus="this.blur();"/>
					</td>
				</tr>
				<tr><td colspan="5" ></td></tr>
			</table>
		</form>
	</div>
	<div class="bottomBox">优速快递有限公司&copy;2019 - 2020 All Rights Reserved.</div>
	<div class="bottomBox" style="color:#818181;margin-top:0px;">本系统支持浏览器：<img src="images/ie.png">IE 10，&nbsp;<img src="images/firefox.png">FireFox 30+，&nbsp;<img src="images/chrome.png">Chrome 36+&nbsp;&nbsp;&nbsp;推荐使用<a href="free/downloadChrome.file"><img src="images/chrome.png">Chrome</a>获取最佳体验</div>
	
	<script type="text/javascript">
	loadSuccess();
	 /*  function auth(){
		  if($("#userName").val() == '' || $("#systemCode").val() == '' ){
			  window.location.href = "${pageContext.request.contextPath}/login.jsp"
		  }
	  } */
		
		function loadSuccess() {
			if ("${errorCode}" != null && "${errorCode}" != '') {
				if (getResultByErrorCode("${errorCode}")) {
					$("#subBtn").attr("disabled", false);
				} else {
					$("#subBtn").attr("disabled", true);
				}
			}
		}
		
		function updPwd() {
			if (validateLogin()) {
				var passwordStrength = '';
				if($("#strength").text() == '低'){
					passwordStrength = 'WEAK';
				}else if($("#strength").text() == '中'){
					passwordStrength = 'AVERAGE';
				}else if($("#strength").text() == '高'){
					passwordStrength = 'STRONG';
				}
				
				$.ajax({
					url : "${pageContext.request.contextPath}/updPwd.action",
					method : "post",
					dataType : "JSON",
					data : {
						"userName" : $("#userName").val(),
						"oldPassword" : hex_md5($("#oldPassword").val()),
						"newPassword" : hex_md5($("#newPassword").val()),
						"systemCode" : $("#systemCode").val(),
						"passwordStrength" : passwordStrength,
						"tokenId" : $("#tokenId").val(),
						"refUrl" : $("#refUrl").val()
						
					},
					success : function(result) {
						if (getResultByErrorCode(result)) {
							loginSuccess(result);
						}
					}
				});
			}
		}
		
		function getResultByErrorCode(result) {
			if (result.errorCode == "success") {
				return true;
			} else {
				$("#errorMsg").text(result.errorMsg);
			}
			return false;
		}
		
		function validateLogin() {
			$("#systemCode").val($("#systemCode").val().trim());
			$("#oldPassword").val($("#oldPassword").val().trim());
			$("#password").val($("#password").val().trim());
			$("#newPassword").val($("#newPassword").val().trim());
			/* if ($("#systemCode").val() == '') {
				$("#errorMsg").text("非法途径登录！");
				return false;
			} */
			if ($("#oldPassword").val() == '') {
				$("#errorMsg").text("请输入旧密码！");
				return false;
			}
			if ($("#password").val() == '') {
				$("#errorMsg").text("请输入新密码！");
				return false;
			}
			if ($("#newPassword").val() == '') {
				$("#errorMsg").text("请再次输入新密码！");
				return false;
			}
			if ($("#password").val().length < 6) {
				$("#errorMsg").text("新密码不能少于6位！");
				return false;
			}
			if ($("#newPassword").val() != $("#password").val()) {
				$("#errorMsg").text("两次输入不一致，请重新输入！");
				return false;
			}
			return true;
		}
		
		function loginSuccess(result) {
			var loadComplete = [];
			/* for (var i = 0; i< result.systemCasUrlList.length; i++) {
			 	var iframe = document.createElement("iframe");
			 	//设置ifame对象src属性
			 	iframe.src = result.systemCasUrlList[i] + "?token=" + encodeURIComponent(result.token) + "&userName=" + encodeURIComponent(result.userName);
			 	iframe.style.display = "none";
			 	iframe.setAttribute("idx", i);
			 	loadComplete[i] = -1;
	 			addEvent(iframe, "load", function(){ 
		 			var i = this.getAttribute("idx");
		 			loadComplete[i] = 1;
		 		});
	 			addEvent(iframe, "error", function(){ 
		 			var i = this.getAttribute("idx");
		 			loadComplete[i] = 1;
		 		});
			 	document.body.appendChild(iframe);
			} */
			var jumpInterval = window.setInterval(function() {
				for (var i = 0; i < loadComplete.length; i++) {
					if (loadComplete[i] == -1) {
						return;
					}
				}
				window.clearInterval(jumpInterval);  
				location.href = "${param.refUrl}" + "?token=" + encodeURIComponent(result.token) + "&userName=" + encodeURIComponent(result.userName);
			},10);
		}
		
		function addEvent(obj,type,fn) {
			 if (obj.attachEvent) {
			 	obj.attachEvent('on'+type,function(){
			    	fn.call(obj)//里面的this的值变成obj 变成当前调用的标签对象
			    }); 
			 } else {
			 	obj.addEventListener(type,fn,false); 
			 }
		}
		 /** 
	     * 密码强度 
	     *  
	     * @return Z = 字母 S = 数字 T = 特殊字符 
	     */  
	     function checkPassword() {  
			var passwordStr = $("#password").val();
			
			var aLvTxt = ['','低','中','高'];
		    var lv = 0;
		    if(passwordStr.match(/[a-z]/g)){lv++;}
		    if(passwordStr.match(/[0-9]/g)){lv++;}
		    if(passwordStr.match(/(.[^a-z0-9])/g)){lv++;}
		    if(passwordStr.length < 6){lv=0;}
		    if(lv > 3){lv=3;}
		    $("#sp").attr("style","");
		    $("#strength").text(aLvTxt[lv]);
	  
	    }  
	</script>
</body>
</html>