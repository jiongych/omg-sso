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
	<title >新乾坤系统-密码找回</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/encryptionAlgorithm.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=8" />
	<style type="text/css">
		.disabledSendCode{
			width: 120px;
		    height: 52px;
		    margin-left: 20px;
		    border-radius: 5px;
		    box-shadow: none;
		    outline: none;
		    background: #C8C8C8;	
		    font-size: 15px;
		    border: none;
		    cursor: pointer;
		}
	
	</style>

</head>

<body class="mobileCon">
		<header>
			<div class="logo"><img src="${pageContext.request.contextPath}/images/repwd202006.png" style="margin-top: 10px;width: 140px;"/></div>
			<div class="title" style="margin-left: 15px;">新乾坤系统-密码找回</div>
		</header>
		<article  id="login">
			<div class="nav-title">密码找回</div>
			<div class="banner">
				<img src="${pageContext.request.contextPath}/images/1.png"/>
				<div class="publice-flex">
					<p class="steps-tip setps-on">验证身份</p>
					<p class="steps-tip set-pwd">设置新密码</p>
					<p class="steps-tip">完成</p>
				</div>
			</div>
			<form id="form" action="" class="login-form private" method="post">
				<input type="hidden" name="systemCode" value="${systemCode}" id="systemCode">
				<input type="hidden" name="sig" value="" id="sigId">
				<input type="hidden" name="t" value="" id="tId">
				<%-- <input type="hidden" name="empCode" value="${empCode}" id="empCode"> --%>
				<input type="hidden" name="refUrl" value="${refUrl}" id="refUrl">
				<%-- add by zhangRb 20171020 添加首次登录设置密码提示  --%>
				<input type="hidden" name="setPw" value="" id="setPw">
				<div class="con-ft">
					<div class="publice">
						<span class="user-span"></span>
						<input type="text" id="userName" name="userName" oninput="iptChange(this)" maxlength="30"  value="" placeholder="请输入员工工号"/>
					</div>
					<div class="pass-error" id="errorName"></div>
					<div id="c_container" class="publice-flex">
						<div class="publice code-input">
							<span class="code-span"></span>
							<input type="text" autocomplete="off"  id="checkcode" value="" oninput="iptChange(this,1)" style="width: 140px;" placeholder="请输入图片验证码"/>
						</div>
						<div id="checkcodeImg" style="margin-top: -1px;margin-left: 30px" >
							<img id="phoneCheckCode" src="" onclick="getImageCode()"
								  style="margin-top: 10px;"/>
						</div>
					</div>
					<div class="pass-error" id="errorMsgCheckCode"></div>
				</div>
				<div class="con-sd" style="display: none">
					<div class="publice">
						<span class="phone-span"></span>
						<input type="text" readonly="readonly" id="mobileHide" name="mobile" value="" placeholder="请输入手机号码"/>
						<input type="text" value="" id="mobile" style="display: none">
						<span class="red">如手机号已更换请联系人资或网点负责人更改！</span>
					</div>
					<div class="pass-error" id="errorMobile"></div>
					<div class="publice-flex">
						<div class="publice code-input">
							<span class="code-span"></span>
							<input type="text"  id="code" autocomplete="off"  name="code"  style="width: 140px;" oninput="iptChange(this,2)" placeholder="请输入验证码"/>
						</div>
						<button type="button" class="getcode" id="sendCode" onclick="doSendCode()">获取验证码</button>
					</div>
				</div>
				<div class="pass-error" id="errorMsg"></div>
				<button type="button"  class="default" onfocus="this.blur();" id="stepBtn" onclick="nextStep()">下&nbsp;&nbsp;一&nbsp;&nbsp;步</button>
				<button type="button" class="default"  onfocus="this.blur();" id="submit" style="display: none" onclick="checkCode()">提&nbsp;&nbsp;&nbsp;&nbsp;交</button>
			</form>
			
		</article>

	<script type="text/javascript">
	var skt=false;
		$(function(){
			 //location.href = "${pageContext.request.contextPath}/toResetPwd.action?setPw=true&systemCode=SSO&refUrl=" + $("#refUrl").val() + "&empCode=13052060561&resetPwdKey=3d4e61c7514f5d4cf59faa2daf1176ba";
			//验证码回车
			$("#code").keydown(function(event){
				if(event.keyCode == 13) {
					checkCode();
				}
			});
			<%-- add by zhangRb 20171020 添加首次登录设置密码提示  Strat --%>
			if(getQueryString("setPw") == "true") {
				$("#setPw").val(true);
				$(".nav-title").text("首次登录设置密码");
				$(".set-pwd").text("设置密码");
			}
			if(getQueryString("setPw") == "true" && skt == true) {
				document.title = "新乾坤系统-密码设置";
				$(".title").text("新乾坤系统-密码设置");
			}
			<%-- add by zhangRb 20171020 添加首次登录设置密码提示  End --%>
			$.ajax({
				url : "${pageContext.request.contextPath}/getJPGCode.action?key=true",
				method : "get",
				dataType : "JSON",
				success : function(result) {
					//debugger;
					if(result.resultCode == 'success'){
						$("#phoneCheckCode").prop('src',result.image);
						$("#sigId").val(result.sig);
						$("#tId").val(result.t);
					}else{
						$("#resultMessage").text(result.resultMessage);
					}
				}
			});
	    });
		
		function getImageCode(){
			$.ajax({
				url : "${pageContext.request.contextPath}/getJPGCode.action?key=true",
				method : "get",
				dataType : "JSON",
				success : function(result) {
					if(result.resultCode == 'success'){
						$("#phoneCheckCode").prop('src',result.image);
						$("#sigId").val(result.sig);
						$("#tId").val(result.t);
					}else{
						$("#resultMessage").text(result.errorMsg);
					}
				}
			});
		}
		<%-- add by zhangRb 20171020 添加首次登录设置密码提示  Strat --%>
		//获取URl中参数的值
		function getQueryString(name)
		{
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
		}
		<%-- add by zhangRb 20171020 添加首次登录设置密码提示  End --%>
		function validateMobile(){
			//let regex = /^(1|00852|00853|00886)(\d|\*){8,10}$/;
			let regex = /^(1|008\*\*)(\d|\*){8,10}$/;
			let userTel=$("#mobile").val().trim();
			if(!regex.test(userTel)){
				$("#errorMobile").text("手机号格式不正确！");
				return false;
			}else{
				$("#errorMobile").text("");
			}
			return true;
		}
	    //发送验证码
		function doSendCode() {
			if(validateMobile()){
				$.ajax({
					url : "${pageContext.request.contextPath}/sendCodeByEmpCode.action",
					method : "post",
					dataType : "JSON",
					data : {
						"systemCode" : $("#systemCode").val(),
						"empCode" : $("#userName").val(),
						"initPw" : $("#setPw").val(),
						"sig":$("#sigId").val(),
						"t":$("#tId").val(),
						'isSelfCall':true
					},
					success : function(result) {
						if (getResponeResult(result)) {
							settime($("#sendCode").get(0));
							$("#sigId").val("");
							//$("#code").removeAttr("readonly");
						}
					}
				});
			}
		}
		function loadSuccess() {
			if ("${errorCode}" != null && "${errorCode}" != '') {
				if (getResultByErrorCode("${errorCode}")) {
					$("#subBtn").attr("disabled", false);
				} else {
					$("#subBtn").attr("disabled", true);
				}
			}
		}
		

		var countdown = 60;
		function settime(val) {
			if (countdown == 0) {
				val.removeAttribute("disabled");
				val.className = "getcode";
				val.innerHTML = "重新发送";
				countdown = 60;
				return;
			} else {
				val.setAttribute("disabled", true);
				val.className = "disabledSendCode";
				val.innerHTML = "重新发送(" + countdown + ")";
				countdown--;
			}
			setTimeout(function() {
				settime(val)
			}, 1000)
		}
		//监控工号,动态图,验证码实时输入
		function iptChange(ipt,idx){
			//idx 1为 动态图，2为手机验证
			let val=$(ipt).val().trim();
			//判断是否是数字
			if(isNaN(Number(val))&&idx!=1){
				val=val.substring(0,val.length-1);
			}
			$(ipt).val(val);
			let name=$("#userName").val().trim();
			let code=$("#checkcode").val().trim();
			let mobile=$("#code").val().trim();
			if(idx==2){
				if(mobile){
					$("#submit").removeClass("default");
				}else{
					$("#submit").addClass("default");
				}
			}else{
				if(name&&code){
					$("#stepBtn").removeClass("default");
				}else{
					$("#stepBtn").addClass("default");
				}
			}
		}
		//校验工号
		function validateName(){
			var userName = $("#userName").val();
			if (userName == '' || userName.trim() == '') {
				$("#errorName").text("请输入员工工号！");
				$("#userName").val("");
				return false;
			}else{
				$("#errorName").text("");
			}
            if($("#checkcode").val() == '' || $("#checkcode").val().trim()==''){
                $("#errorMsgCheckCode").text("请输入图片验证码！");
                  return false;
            }else{
                $("#errorMsgCheckCode").text("");
			}
			$("#errorMsg").html("");
			return true;
		}
		//下一步
		function nextStep(){
			if(validateName()){
				$("#stepBtn").attr("disabled",true);
				$.ajax({
					url : "${pageContext.request.contextPath}/findMobileAndCheckEmp.action",
					type  : "get",
					dataType : "JSON",
					// xhrFields: { withCredentials: true },
					data : {
						"empCode" : $("#userName").val(),
						"checkCode": $("#checkcode").val().toLowerCase(),
						"systemCode" : $("#systemCode").val(),
						"sig":$("#sigId").val(),
						"t":$("#tId").val(),
					},
					success : function(result) {
						$("#stepBtn").attr("disabled",false);
						
						if (getResponeResult(result)) {
							if(result.success&&result.mobile){
								$("#stepBtn").hide();
								$(".con-ft").hide();
								$("#submit").show();
								$(".con-sd").show();
								$("#mobile").val(result.mobile);
/* 								var tel = result.mobile;
								tel = "" + tel;
								var ary = tel.split("");
								ary.splice(3,4,"****");
								var tel1=ary.join(""); */
								$("#mobileHide").val(result.mobile);
							}
						}
					},
					error:function(err){
						$("#stepBtn").attr("disabled",false);
					}
				})
			}
		}
		//校验验证码
		function checkCode() {
			if(validateMobile()){
				var code = $("#code").val();
				if (code == '') {
					$("#errorMsg").text("请输入验证码！");
					return;
				}else{
					$("#errorMsg").text("");
				}
				$.ajax({
					url : "${pageContext.request.contextPath}/checkCodeByEmpCode.action",
					type  : "post",
					dataType : "JSON",
					data : {
						"systemCode" : $("#systemCode").val(),
						"empCode" : $("#userName").val(),
						"code" : code
					},
					success : function(result) {
						if (getResponeResult(result)) {
							<%-- modify by zhangRb 20171020 添加首次登录设置密码提示  Strat --%>
							location.href = "${pageContext.request.contextPath}/toResetPwd.action?setPw=" + $("#setPw").val() + "&systemCode=" + $("#systemCode").val() + "&refUrl=" + $("#refUrl").val() + "&empCode=" + result.empCode + "&resetPwdKey=" + result.resetPwdKey;
						}
					}
				});
			}
		}
		function getResponeResult(result) {
			if (!result.success) {
			    if(result.errorCode == "CCEC"){
                    $("#errorMsgCheckCode").text(result.errorMsg);
                    $("#phoneCheckCode").click().show();
				}else{
			        $("#errorMsg").text(result.errorMsg);
				}
				return false;
			}
			return true;
		}
		
		function upperCase(dom)   {
		    $(dom).val($(dom).val().toUpperCase());
		}
		
		//移动/pc判断
	    showlogin()
	    function showlogin(){
	     if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
			$("body").addClass("mobileCon"); 
	      	$("#form").addClass("showform");
	      	$(".pass-error").addClass("ismosht");
	      	$(".publice").addClass("onePublice");
	      	$(".publice>input").addClass("towPublice");
	      	$(".banner").addClass("oneBanner");
	      	$(".steps-tip").addClass("oneSteps-tip");
			$(".title").html("新乾坤-密码设置");
			skt = false;
	     } else {
			$("body").removeClass("mobileCon"); 
	      	$("#form").removeClass("showform");
	      	$(".pass-error").removeClass("ismosht");
	      	$(".publice").removeClass("onePublice");
	      	$(".banner").removeClass("oneBanner");
	      	$(".steps-tip").removeClass("oneSteps-tip");
	      	skt = true;
	     }
	    }
	</script>
</body>
</html>