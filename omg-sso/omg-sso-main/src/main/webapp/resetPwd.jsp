<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.uce.omg.sso.util.DesUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
	<link rel="shortcut icon" type="image/x-icon" href="/omg-sso-main/images/portal/favicon.png" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <title>新乾坤系统-密码找回</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/encryptionAlgorithm.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/portal-iconfont.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=3"/>
    <style>
        .iconp{
			width: 1em;
			height: 1em;
			vertical-align: -0.15em;
			fill: currentColor;
			overflow: hidden;
		}
    </style>
</head>

<body>
<header>
    <div class="logo"><img src="${pageContext.request.contextPath}/images/repwd202006.png" style="margin-top: 10px;width: 140px;"/></div>
    <div class="title" style="margin-left: 15px;">新乾坤系统-密码找回</div>
</header>
<article>
    <div class="nav-title">重置密码</div>
    <div class="banner">
        <img src="${pageContext.request.contextPath}/images/2.png"/>
        <div class="publice-flex">
            <p class="steps-tip">验证身份</p>
            <p class="steps-tip setps-on set-pwd">设置新密码</p>
            <p class="steps-tip">完成</p>
        </div>
    </div>
    <form action="" class="login-form private new-rule" method="post">
        <input type="hidden" id="refUrl" value="${refUrl}" id="refUrl">
        <input type="hidden" id="systemCode" value="${systemCode}" id="systemCode">
        <input type="hidden" id="empCode" value="${empCode}" id="userName">
        <input type="hidden" id="resetPwdKey" value="${resetPwdKey}" id="code">
        <%-- add by zhangRb 20171020 添加首次登录设置密码提示  --%>
        <input type="hidden" name="setPw" value="" id="setPw">
        <div class="publice eye-box">
            <span class="newpass-span"></span>
            <input type="password" id="password" onkeyup="checkPassword()" placeholder="请输入新密码"/>
            <div class="eye-change" onclick="eye(1)">
                <svg class="iconp"  id="eyeClose1" aria-hidden="true">
                    <use xlink:href="#icon-yj"></use>
                </svg>
                <svg class="iconp"  id="eyeShow1" style="display: none" aria-hidden="true">
                    <use xlink:href="#icon-ai-eye"></use>
                </svg>
            </div>
            <div class="ruleTips">
                <i class="rule"></i>
                密码规则：<br>
                • 密码由英文字母大写、小写，数字及特殊符号构成，必须满足3种类型，且不能少于8位!<br>
                • 密码中不能包含三位以上正序或倒序的连续数字或字母!<br>
                • 密码中不允许出现3位以上重复字符!<br>
            </div>
        </div>
        <div class="pass-error" style="color:green;"><span id="sp" style="display: none;">强度：<span id="strength"></span></span>
        </div>
        <div class="publice eye-box">
            <span class="surepass-span"></span>
            <input type="password" id="newPassword" name="newPassword" placeholder="请确认密码"/>
            <div class="eye-change" onclick="eye(2)">
                <svg class="iconp" id="eyeClose2" aria-hidden="true">
                    <use xlink:href="#icon-yj"></use>
                </svg>
                <svg class="iconp" id="eyeShow2" style="display: none" aria-hidden="true">
                    <use xlink:href="#icon-ai-eye"></use>
                </svg>
            </div>
        </div>
        <div class="pass-error" id="errorMsg"></div>
        <button type="button" id="subBtn" onfocus="this.blur();" onclick="resetPwd()">提&nbsp;&nbsp;&nbsp;&nbsp;交
        </button>
        <p class="red">温馨提示：该账号密码可用于登录新乾坤、壹速通、优速宝、新巴枪、优享寄、优速家选系统！</p>
    </form>
   
</article>

<script type="text/javascript">
	var skt = false;
    $(function () {
        //重置密码回车事件
        
        $("#newPassword").keydown(function (event) {
            if (event.keyCode == 13) {
                resetPwd();
            }
        });
        <%-- add by zhangRb 20171020 添加首次登录设置密码提示  Strat --%>
        if (getQueryString("setPw") == "true") {
            $("#setPw").val(true);
            $(".nav-title").text("设置密码");
            $(".set-pwd").text("设置密码");
        }
        if (getQueryString("setPw") == "true" && skt == true) {
            document.title = "新乾坤系统-密码设置";
            $(".title").text("新乾坤系统-密码设置");
        }
        <%-- add by zhangRb 20171020 添加首次登录设置密码提示  End --%>
    });

    <%-- add by zhangRb 20171020 添加首次登录设置密码提示  Strat --%>
    //获取URl中参数的值
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)return unescape(r[2]);
        return null;
    }
    <%-- add by zhangRb 20171020 添加首次登录设置密码提示  End --%>

    /**
     * 密码强度
     *
     * @return Z = 字母 S = 数字 T = 特殊字符
     */
    function checkPassword() {
        var passwordStr = $("#password").val();
        var aLvTxt = ['', '低', '中', '高'];
        var lv = 0;
        //小写
        if (passwordStr.match(/[a-z]/g)) {
            lv++;
        }
        //大写
        if (passwordStr.match(/[A-Z]/g)) {
            lv++;
        }
        //数字
        if (passwordStr.match(/[0-9]/g)) {
            lv++;
        }
        //同时满足三种条件 
        if (passwordStr.match(/(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,}/g)) {
            lv++;
        }
        /*
         if (passwordStr.match(/(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{8,}/g)) {
         lv++;
         }
         */
        if (passwordStr.length < 8) {
            lv = 0;
        }
        if (lv > 3) {
            lv = 3;
        }
        if (passwordStr == "undefined" || passwordStr == "" || lv == 0) {
            $("#sp").attr("style", "display: none;");
        } else {
            $("#sp").attr("style", "");
            $("#strength").text(aLvTxt[lv]);
        }

    }

    //重置密码
    function resetPwd() {
        var passwordStrength = '';
        if ($("#strength").text() == '低') {
            passwordStrength = 'WEAK';
        } else if ($("#strength").text() == '中') {
            passwordStrength = 'AVERAGE';
        } else if ($("#strength").text() == '高') {
            passwordStrength = 'STRONG';
        }
        // add by BaoJingyu 2017-12-09 校验密码强度
        if (validateData()) {
            $.ajax({
                url: "${pageContext.request.contextPath}/resetPwd.action",
                method: "post",
                dataType: "JSON",
                data: {
                    "newPassword": hex_md5($("#newPassword").val()),
                    "passwordStrength": passwordStrength,
                    "systemCode": $("#systemCode").val(),
                    "empCode": $("#empCode").val(),
                    "resetPwdKey": $("#resetPwdKey").val(),
                    "refUrl": $("#refUrl").val()
                },
                success: function (result) {
                    console.log("密码重置" + result.refUrl);
                    if (getResponseResult(result)) {
                        <%-- modify by zhangRb 20171020 添加首次登录设置密码提示  Start --%>
                        location.href = "${pageContext.request.contextPath}/toResetPwdSuccess.action?setPw=" + $("#setPw").val() + "&refUrl=" + $("#refUrl").val();
                    }
                }
            });
        }
    }

    function getResponseResult(result) {
        if (!result.success) {
            $("#errorMsg").html(result.errorMsg);
            return false;
        }
        return true;
    }
    //校验提交数据
    function validateData() {
        if ($("#password").val() == '') {
            $("#errorMsg").text("请输入新密码!");
            return false;
        }
        if ($("#newPassword").val() == '') {
            $("#errorMsg").text("请再次输入新密码!");
            return false;
        }
        // add by BaoJingyu 2017-12-09 begin
        //var regex = /^(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{8,}$/;
        var regex = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,}$/;
        if (!(regex.test($("#password").val()))) {//满足条件true
            $("#errorMsg").text("密码由英文字母大写、小写，数字及特殊符号构成，必须满足3种类型，且不能少于8位!");
            return false;
        }
        /* if ($("#password").val().length < 8) {
         $("#errorMsg").text("新密码不能少于8位!");
         return false;
         } */
        if ($("#newPassword").val() != $("#password").val()) {
            $("#errorMsg").text("两次输入不一致，请重新输入!");
            return false;
        }
        //三位以上的重复字符   4444
        var regRepNum = /(.)(\1){2,}/;
        if (regRepNum.test($("#password").val())) {//true
            $("#errorMsg").text("密码中不能包含三位以上重复的字符!");
            return false;
        }
        var reg = /((9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){2}\d|(0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){2}\d|(a(?=b)|b(?=c)|c(?=d)|d(?=e)|e(?=f)|f(?=g)|g(?=h)|h(?=i)|i(?=j)|j(?=k)|k(?=l)|l(?=m)|m(?=n)|n(?=o)|o(?=p)|p(?=q)|q(?=r)|r(?=s)|s(?=t)|t(?=u)|u(?=v)|v(?=w)|w(?=x)|x(?=y)|y(?=z)){2}\w|(z(?=y)|y(?=x)|x(?=w)|w(?=v)|v(?=u)|u(?=t)|t(?=s)|s(?=r)|r(?=q)|q(?=p)|p(?=o)|o(?=n)|n(?=m)|m(?=l)|l(?=k)|k(?=j)|j(?=i)|i(?=h)|h(?=g)|g(?=f)|f(?=e)|e(?=d)|d(?=c)|c(?=b)|b(?=a)){2}\w|(A(?=B)|B(?=C)|C(?=D)|D(?=E)|E(?=F)|F(?=G)|G(?=H)|H(?=I)|I(?=J)|J(?=K)|K(?=L)|L(?=M)|M(?=N)|N(?=O)|O(?=P)|P(?=Q)|Q(?=R)|R(?=S)|S(?=T)|T(?=U)|U(?=V)|V(?=W)|W(?=X)|X(?=Y)|Y(?=Z)){2}\w|(Z(?=Y)|Y(?=X)|X(?=W)|W(?=V)|V(?=U)|U(?=T)|T(?=S)|S(?=R)|R(?=Q)|Q(?=P)|P(?=O)|O(?=N)|N(?=M)|M(?=L)|L(?=K)|K(?=J)|J(?=I)|I(?=H)|H(?=G)|G(?=F)|F(?=E)|E(?=D)|D(?=C)|C(?=B)|B(?=A)){2}\w)/;
        if (reg.test($("#password").val())) {//true
            $("#errorMsg").text("密码中不能包含三位以上正序或倒序的连续数字或字母!");
            return false;
        }
        return true;
        // add by BaoJingyu 2017-12-09 end
    }
  //移动/pc判断
    showlogin()
    function showlogin(){
     if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
      	$(".login-form").addClass("showform");
      	$(".pass-error").addClass("ismosht");
      	$(".publice").addClass("onePublice");
      	$(".publice>input").addClass("towPublice");
      	$(".banner").addClass("oneBanner");
      	$(".steps-tip").addClass("oneSteps-tip");
      	$("#subBtn").addClass("oneTobbom");
      	
		$(".title").html("新乾坤-密码设置");
		skt = false;
     } else {
      	$("login-form").removeClass("showform");
      	$(".pass-error").removeClass("ismosht");
      	$(".publice").removeClass("onePublice");
      	$(".banner").removeClass("oneBanner");
      	$(".steps-tip").removeClass("oneSteps-tip");
      	$("#subBtn").removeClass("oneTobbom");
      	skt = true;
     }
    }
    function eye(idx){
        if(idx==1){
            if(document.getElementById("eyeClose1").style.display=="none"){
                $("#eyeShow1").hide();
                $("#eyeClose1").show();
                $("#password").attr("type",'password');
            }else{
                $("#eyeShow1").show();
                $("#eyeClose1").hide();
                $("#password").attr("type",'text');
            }
        }else if(idx==2){
            if(document.getElementById("eyeClose2").style.display=="none"){
                $("#eyeShow2").hide();
                $("#eyeClose2").show();
                $("#newPassword").attr("type",'password');
            }else{
                $("#eyeShow2").show();
                $("#eyeClose2").hide();
                $("#newPassword").attr("type",'text');
            }
        }
    }
</script>
</body>
</html>