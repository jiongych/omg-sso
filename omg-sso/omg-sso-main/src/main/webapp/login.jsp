<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Cache-Control" content="max-age=7200" />
	<link rel="shortcut icon" type="image/x-icon" href="/omg-sso-main/images/portal/favicon.png" />
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
	<title>新乾坤系统-登录</title>
	<link rel="icon" type="image/png" href="/omg-sso-main/images/portal/logoyq202006.png" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vue-2.6.10.js?v=01"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/element-ui.js?v=01"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/qrcode.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/encryptionAlgorithm.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/iconfont.js?v=01"></script>
	<link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/css/element-ui.css?v=01" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=21" />
	<style type="text/css">
		body{
			background: url(images/bg-2.png) no-repeat;
			background-position: 100% 100% ;
			background-size:cover;
		}
		/* add by zhangRb 20171020 添加首次登录设置密码提示 */
		.login-resetpass .setPass {
			margin-left: 11px;
			text-decoration: unset;
		}
		.icono{
			width: 1em;
			height: 1em;
			vertical-align: -0.15em;
			fill: currentColor;
			overflow: hidden;
		}
		.radioClass label[data-v-cccab570] {
			color: #fff;
		}
		.el-radio__input.is-checked .el-radio__inner {
			border-color: #82f2ff;
			background: #82f2ff
		}
		.el-radio__input.is-checked+.el-radio__label {
			color: #82f2ff
		}
	</style>
</head>
<body >
<div class="login layout" id="login">
	<div id="app" class="yimi">
		<div class="login-title">
			<img src="${pageContext.request.contextPath}/images/uc56_logo_202006.png" style="margin-right: 10px;width: 130px;"/>新乾坤
		</div>
		<div class="login-select">
			<div data-v-cccab570="" class="radioClass" style="text-align: left;padding-left: 8px;padding-bottom: 20px">
				<div id="domainDiv" data-v-cccab570="" role="radiogroup" class="el-radio-group">
				</div>
			</div>
			<div class="login-header">
				<span id="MobileMode" onclick="loginType(this)" class="active">快递账号登录</span>
				<span class="line">|</span>
				<span id="codeMode" onclick="loginType(this)">快运账号登录</span>
			</div>
			<!-- 扫码登陆切换 -->
			<div v-if="yimi.qrShow">
				<p class="qr-wrap">
					<i v-text="yimi.tips"></i>
					<span class="qr-box"  @click="qrModel">
								<img src="${pageContext.request.contextPath}/images/qr.png" style="cursor: pointer" alt="" v-show="yimi.isShow==1">
								<svg class="icono" aria-hidden="true" v-show="yimi.isShow==2">
									<use xlink:href="#icon-diannao"></use>
								</svg>
							</span>
				</p>
			</div>
			<div class="login-con" id="loginForm" >
				<form class="login-form" action="" method="post" >
					<input type="hidden" name="refUrl" value="${refUrl}" id="refUrl">
					<input type="hidden" name="sig" value="" id="sigId">
					<input type="hidden" name="t" value="" id="tId">
					<input type="hidden" name="systemCode" value="${systemCode}" id="systemCode">
					<input type="hidden" name="loginModel" value="1" id="loginModel">

					<!--uce账号登陆-->
					<div  id="loginWorkNumber">
						<div class="user">
							<input type="text" class="userName"  name="userName" id="userName" style="text-transform:uppercase" onblur="upperCase(this)" placeholder="请输入员工工号/手机号"/>
						</div>
						<div class="password">
							<input type="password" class="y-psd"  name="password" id="password"  placeholder="请输入密码"/>
							<a class="forget-password" href="javascript:void(0)" onclick="toSendCode()">忘记密码 ?</a>
							<span id="capslock" class="capslock">大小写锁定已打开</span>
						</div>
					</div>
					<!--壹米登录-->
					<div  id="yimiLogin" style="display:none">
						<el-select v-model="value" placeholder="请选择账套">
							<el-option
									v-for="item in options"
									:key="item.typeCode"
									:label="item.typeName"
									:value="item.typeCode">
							</el-option>
						</el-select>
						<div class="user">
							<input type="text" class="userName"  name="yUserName" id="yUserName" v-model="yimi.userName" style="text-transform:uppercase" onblur="upperCase(this)" placeholder="请输入员工工号"/>
						</div>
						<div class="password">
							<input type="password" class="y-psd"  name="yPassword" id="yPassword" v-model="yimi.password" placeholder="请输入密码"/>
						</div>
					</div>
					<div id="c_container" style="text-align: left;">
						<div id="checkcode_img" style="margin-top: -8px;" >
							<img id="loginformvCode" src=""
								 onclick="getImageCode()" style="margin-top: 10px;"/>
						</div>
						<div id="checkcode_input" style="margin-top: -8px;">
							<input name="checkcode" style="width: 85px;height: 29px;margin-top: -24px;margin-left: 120px;font-size: 10px;"  type="text" id="checkcode" value="" placeholder="请输入验证码"/>
						</div>
					</div>
					<!--手机号码登录-->
					<div id="loginMobile" style="display: none">
						<div class="user">
							<input type="text" name="userTel" id="userTel" oninput="iptChange(this)" style="text-transform:uppercase" placeholder="请输入手机号"/>
						</div>
						<div class="imgage-code"  style="text-align: left;">
							<div style="float: left">
								<input name="imageCode" autocomplete="off" oninput="iptChange(this,1)" style="width:190px;margin-top: 0px;"  type="text" id="imageCode" value="" placeholder="请输入右边图片验证码"/>
							</div>
							<div style="float: right">
								<img id="loginMobileCode" src=""
									 onclick="getImageCode()" style="height:30px;display: inline-block"/>
							</div>
						</div>
						<div class="password">
							<input type="text" name="code" autocomplete="off" id="code" maxlength="6" oninput="iptCode(this)" placeholder="请输入短信验证码"/>
							<button type="button"  class="forget-password get-code" id="getCode"  onclick="getMobileCode()">获取验证码</button>
						</div>
					</div>
					<div class="login-error" id="errorMsg" style="margin: 8px 0 10px 0;text-align: left;"></div>
					<button id="btnLogin" :style="{'marginBottom':!yimi.qrShow?'77px':'0'}" type="button" onfocus="this.blur();" onclick="login()">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
					<p class="login-mode" v-if="yimi.qrShow"><span onclick="modeChange(this)">手机验证码登录</span></p>
					<p class="login-resetpass"  v-if="yimi.qrShow" id="loginResetPass">
						<!-- add by zhangRb 20171020 添加首次登录设置密码提示  -->
						<a class="setPass" href="javascript:void();" onclick="setPassWord()">首次登录请<span style="text-decoration: underline;">设置密码</span></a>
					</p>
				</form>
			</div>
			<!--扫码登录-->
			<div class="QR-code" id="loginCode" style="display: none">
				<div id="scan">
					<div class="qr-content">
						<div id="qrcode" style="width:150px;height:150px;"></div>
						<div class="qr-mask" id="qrMask" style="display: none">
							<p class="cotext" id="context">二维码已失效</p>
							<p class="btn" onclick="makeCode()">刷新</p>
						</div>
						<div class="loading" id="loading"></div>
					</div>
					<p class="f12">
						<svg class="icono" aria-hidden="true">
							<use xlink:href="#icon-saoyisao"></use>
						</svg>
						打开&nbsp;<span class="main-color"  onclick="$('#uat').show()">壹速通</span>&nbsp;
						扫一扫登录
					<p class="uct" id="uat" onclick="$('#uat').hide()" style="display: none"><img src="${pageContext.request.contextPath}/images/uct.png" alt=""></p>
					</p>
				</div>
				<!--扫码成功显示-->
				<div id="scanOk" style="display: none">
					<div class="success">
						<svg class="icono" aria-hidden="true">
							<use xlink:href="#icon-saomachenggong"></use>
						</svg>
					</div>
					<p>扫码成功,请在[优速通]上确认登录</p>
					<p class="main-color refresh" onclick="makeCode()">返回重新扫码</p>
				</div>
			</div>
		</div>
	</div>
</div>
</div>


<div class="copyright">
	<div id="fullsearch" class="copy-text">
		&copy; 2018 优速物流有限公司版权所有
		<p class="download">推荐使用
			<img src="https://download.uce.cn/update/ucp/prod/resource/images/icon/google.png" alt="">
			<a onclick="getCpu()" title="请下载谷歌浏览器">谷歌浏览器</a>
		</p>
	</div>

</div>
<script type="text/javascript">
    var vm = new Vue({
        el: "#app",
        data: {
            options: [],
            value: '',
            yimi:{
                userName:"",
                password:"",
                isShow:1,
                qrShow:true,
                tips:"扫码登录点这里！",
                uceFlag:1, //1默认uce账号登陆，2默认uce手机号登陆，3uce扫码登陆
            }
        },
        mounted() {
            this.init();
        },
        methods: {
            init(){
                let vueThis=this,url="${pageContext.request.contextPath}"; //http://loc.sso.uce.cn:8022/omg-sso-main
                $.ajax({
                    url : url+"/findCompCode.action",
                    method : "get",
                    dataType : "JSON",
                    data:{},
                    success : function(result) {
                        vueThis.options=result;
                    },
                    error:function(err){
                        vueThis.options=[]
                    }
                })
            },
            qrModel(){
                //isShow 1 账号登陆 2扫码登陆
                if(this.yimi.isShow==1){
                    this.yimi.isShow=2;
                    this.yimi.tips="账号登录点这里！";
                    $("#loginForm").hide();
                    $("#loginCode").show();
                    $("#c_container").hide();
                    $("#errorMsg").html("");
                    makeCode();
                }else{
                    this.yimi.isShow=1;
                    this.yimi.tips="扫码登录点这里！";
                    $("#loginForm").show();
                    $("#loginCode").hide();
                    $("#c_container").hide();
                    $("#errorMsg").html("");
                    $("#qrcode").html("");
                    window.clearInterval(countTime);
                    let ltype=$("#loginModel").val();
                    if(ltype==1){
                        let userName=$("#userName").val();
                        let password=$("#password").val();
                        $("#userName").val("");
                        $("#password").val("");
                        setTimeout(res=>{
                            $("#userName").val(userName);
                        $("#password").val(password);
                    })
                        $("#loginWorkNumber").show();
                    }else{
                        $("#imageCode").val("");
                        $("#code").val("");
                        let userTel=$("#userTel").val();
                        $("#userTel").val("");
                        setTimeout(res=>{
                            $("#userTel").val(userTel);
                    })
                        $("#loginMobileCode").click().show();
                        $("#loginMobile").show();
                    }
                }
            },
            //登陆模式切换
            modeInit(){
                if(vm.yimi.uceFlag==1){
                    let userName=$("#userName").val();
                    let password=$("#password").val();
                    $("#userName").val("");
                    $("#password").val("");
                    setTimeout(res=>{
                        $("#userName").val(userName);
                    $("#password").val(password);
                })
                    $("#loginWorkNumber").show();
                }else if(vm.yimi.uceFlag==2){
                    $("#imageCode").val("");
                    $("#code").val("");
                    let userTel=$("#userTel").val();
                    $("#userTel").val("");
                    setTimeout(res=>{
                        $("#userTel").val(userTel);
                })
                    $("#loginMobileCode").click().show();
                    $("#loginMobile").show()
                }else if(vm.yimi.uceFlag==3){
                    $("#loginCode").show();
                    $("#loginForm").hide();
                    $("#c_container").hide();
                    $("#errorMsg").html("");
                    makeCode();
                }
            },
        }
    })
    var show = '<%=request.getAttribute("aaa")%>' ;
    //移动/pc判断
    showlogin()
    function showlogin(){
        if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
            $("#login").addClass("showlogin")
        } else {
            $("#login").removeClass("showlogin")
        }
    }
    var countTime,isScan=false;; //扫码登陆倒计时
    $(function(){
        $("#c_container").hide();
        var result='${loginResult}';
        if (result !='') {
            var ob = JSON.parse(result);
            getResultByErrorCode(ob);
        }
        var capsLock = false;
        document.querySelector('#password').onkeypress = function(e){
            var e = document.all ? window.event : e;
            var keyCode = e.keyCode||e.which; // 按键的keyCode
            var isShift = e.shiftKey ||(keyCode == 16 ) || false ; // shift键是否按住
            if (
                ((keyCode >= 65 && keyCode <= 90 ) && !isShift) // Caps Lock 打开，且没有按住shift键
                || ((keyCode >= 97 && keyCode <= 122 ) && isShift)// Caps Lock 打开，且按住shift键
            ){
                capsLock = true;
                $("#capslock").show();
            }else{
                capsLock = false;
                $("#capslock").hide();
            }
        };

        //密码输入框回车事件
        $("#password").keydown(function(event){
            if(event.keyCode == 13) {
                login();
            }
        });
        //密码输入框回车事件
        $("#yPassword").keydown(function(event){
            if(event.keyCode == 13) {
                login();
            }
        });

        loadDomain();
    });

    function loadDomain() {
        var str ='';
        var domain = document.domain;
        $.ajax({
            url : "${pageContext.request.contextPath}/findDomain.action",
            method : "get",
            dataType : "JSON",
            data:{},
            success : function(result) {
                if(result == null ||  result == 'undefined' || result.length <= 1) {
                    return;
                }
                $.each(result, function (index, val) {
                    var checkClass='';
                    if (val.typeCode == domain) {
                        checkClass='is-checked';
                    }
                    str +="<label data-v-cccab570='' role='radio' aria-checked='true' tabindex='0' class='el-radio is-checked' onclick='toggleChecked(this,\""+val.typeCode+"\" )'>"+
                        "<span class='el-radio__input "+checkClass+"'><span class='el-radio__inner'></span>"+
                        "<input type='radio' aria-hidden='true' tabindex='-1' class='el-radio__original' value='主站域名' ></span>"+
                        "<span class='el-radio__label'>"+val.typeName+"</span>"+
                        "</label> "
                });
                $("#domainDiv").html(str);
            },
            error:function(err){
            }
        })
    }

    function toggleChecked(obj,host) {
        var protocol = window.location.protocol;
        var port = window.location.port;
        var pathname = window.location.pathname;
        var search = window.location.search;
        $(".el-radio__input").removeClass("is-checked");
        $(obj).children("span").addClass("is-checked");
        if(port == null ||  port == 'undefined' || port == '') {
            location.href= protocol + "//" + host + pathname + search;
        } else {
            location.href= protocol + "//" + host + ":"+port + pathname + search;
        }

    }

    function toSendCode() {
        location.href = "${pageContext.request.contextPath}/toSendCode.action?systemCode="+ $('#systemCode').val() +"&refUrl="+ $('#refUrl').val();;
    }

    /* add by zhangRb 20171020 添加首次登录设置密码提示  Strat*/
    function setPassWord() {
        location.href = "${pageContext.request.contextPath}/toSendCode.action?systemCode="+ $('#systemCode').val() +"&refUrl="+ $('#refUrl').val()+"&setPw=true";
    }
    /* add by zhangRb 20171020 添加首次登录设置密码提示  End*/

    loadSuccess();

    function loadSuccess() {
        if ("${errorCode}" != null && "${errorCode}" != '') {
            if ("${errorCode}" == 'B2005') {
                $("#btnLogin").attr("disabled", true);
                $("#errorMsg").text("系统参数错误，系统信息不存在");
                $("#errorMsg").css("padding-bottom",'0px');
            } else {
                $("#btnLogin").attr("disabled", false);
            }
        }
    }

    function login() {
        var t = $("#tId").val() == null || $("#tId").val() == undefined || $("#tId").val() == '' ? 0 : $("#tId").val();
        let ltype=$("#loginModel").val(),params,password="";
        if(ltype==1){
            params={
                "userName" : $("#userName").val(),
                "password" : hex_md5($("#password").val()),
                "systemCode" : $("#systemCode").val(),
                "refUrl" : $("#refUrl").val() ,
                "checkCode": $("#checkcode").val().toLowerCase(),
                "sig":$("#sigId").val(),
                "t":t,
                "browserInfo":getBrowserInfo()
            }
        }else if(ltype==2){
            params={
                "userName" : $("#userTel").val().trim(),
                "checkCode" : $("#imageCode").val().toLowerCase(),
                "password" : $("#code").val(),
                "systemCode" : $("#systemCode").val(),
                "refUrl" : $("#refUrl").val() ,
                "sig":$("#sigId").val(),
                "t":t,
                "browserInfo":getBrowserInfo(),
                "loginType" : 'Captcha'
            }
        }
        if (validateLogin()) {
            //uce登录
            if(vm.yimi.qrShow){
                params.userAccountType="Portal";
            }else {
                params={
                    "userName" : vm.yimi.userName,
                    "password" : hex_md5(vm.yimi.password),
                    "systemCode" : $("#systemCode").val(),
                    "refUrl" : $("#refUrl").val() ,
                    "checkCode": $("#checkcode").val().toLowerCase(),
                    "sig":$("#sigId").val(),
                    "t":t,
                    "browserInfo":getBrowserInfo()
                }
                params.userAccountType="Yinhe";
                params.compCode=vm.value;
                ltype=1;
            }
            if(ltype==2){
                $.ajax({
                    url : "${pageContext.request.contextPath}/checkMobile.action",
                    method : "post",
                    dataType : "JSON",
                    data : {
                        "mobile" : $("#userTel").val(),
                        "checkCode": $("#imageCode").val().toLowerCase(),
                        "sig":$("#sigId").val(),
                        "t":$("#tId").val()
                    },
                    success : function(result) {
                        if (getResponeResult(result)) {
                            $("#btnLogin").attr("disabled",true);
                            $.ajax({
                                url : "${pageContext.request.contextPath}/login.action",
                                method : "post",
                                dataType : "JSON",
                                data : params,
                                success : function(result) {
                                    $("#btnLogin").attr("disabled",false);
                                    //add by zhangRb 20170824 登录请求系统URL不正确时跳转到portal主页
                                    if (result.errorCode == "B3008") {
                                        location.href = "${pageContext.request.contextPath}/portal/forward.action";
                                    }else if (getResultByErrorCode(result)) {
                                        loginSuccess(result);
                                    }
                                },
                                error:function(err){
                                    $("#btnLogin").attr("disabled",false);
                                }
                            });
                        }
                    }
                })
            }else{
                $("#btnLogin").attr("disabled",true);
                let url="${pageContext.request.contextPath}"; // http://loc.sso.uce.cn:8022/omg-sso-main
                $.ajax({
                    url : url+"/login.action",
                    method : "post",
                    dataType : "JSON",
                    data : params,
                    success : function(result) {
                        $("#btnLogin").attr("disabled",false);
                        //add by zhangRb 20170824 登录请求系统URL不正确时跳转到portal主页
                        if (result.errorCode == "B3008") {
                            location.href = "${pageContext.request.contextPath}/portal/forward.action";
                        }else if (getResultByErrorCode(result)) {
                            loginSuccess(result);
                        }
                    },
                    error:function(err){
                        $("#btnLogin").attr("disabled",false);
                    }
                });
            }
        }
    }
    function getBrowserInfo() {
        var ua = navigator.userAgent.toLocaleLowerCase();
        var osVersion =ua.substring(ua.indexOf('(')+1,ua.indexOf(';')+7);
        var browserName=null;
        var browserVersion=ua;
        var strStart,strStop;
        if (ua.match(/opr/) != null || ua.match(/opera/) != null) {
            browserName = "Opera";
        }else if (ua.match(/ubrowser/) != null) {
            browserName = "UC";
        }else if (ua.match(/bidubrowser/) != null) {
            browserName = "百度";
        }else if (ua.match(/metasr/) != null) {
            browserName = "搜狗";
        }else if (ua.match(/tencenttraveler/) != null || ua.match(/qqbrowse/) != null) {
            browserName = "QQ";
        }else if (ua.match(/maxthon/) != null) {
            browserName = "遨游";
        }else if (ua.match(/liebao/) != null || ua.match(/lbbrowser/) != null) {
            browserName = "猎豹";
        }else if (ua.match(/theworld/) != null) {
            browserName = "世界之窗";
        }else if (ua.match(/msie/) != null || ua.match(/trident/) != null) {
            browserName = "IE";
            browserVersion = ua.match(/msie ([\d.]+)/) != null ? ua.match(/msie ([\d.]+)/)[1] : ua.match(/rv:([\d.]+)/)[1];
        }else if(ua.match(/edge/) != null){
            browserName = "Edge";
            strStart = ua.indexOf('edge');
            browserVersion = ua.substring(strStart).split('/')[1];
        }else if (ua.match(/firefox/) != null) {
            browserName = "Firefox";
            strStart = ua.indexOf('firefox');
            browserVersion = ua.substring(strStart).split('/')[1];
        }else if (ua.match(/chrome/) != null) {
            //仅限急速模式下有效
            var is360 = _mime("type", "application/vnd.chromium.remoting-viewer");
            function _mime(option, value) {
                var mimeTypes = navigator.mimeTypes;
                for (var mt in mimeTypes) {
                    if (mimeTypes[mt][option] == value) {
                        return true;
                    }
                }
                return false;
            }
            if(is360){
                if (window.clientInformation && window.clientInformation.languages && window.clientInformation.languages.length > 2) {
                    browserName = '360急速浏览器'; //内核版本为v63.0有效
                }else{
                    browserName = '360浏览器';
                }
            }else{
                browserName = "Chrome";
            }
        }else if (ua.match(/safari/) != null) {
            browserName = "Safari";
        }
        var browserInfo = {"osVersion":osVersion,"screen":screen,"browserName":browserName,"browserVersion":browserVersion};
        return JSON.stringify(browserInfo);
    }


    /* function postcall( url, params, target){
          var tempform = document.createElement("form");
          tempform.action = url;
          tempform.method = "post";
          tempform.style.display="none"
          if(target) {
              tempform.target = target;
          }

          for (var x in params) {
              var opt = document.createElement("input");
              opt.name = x;
              opt.value = params[x];
              tempform.appendChild(opt);
          }

          var opt = document.createElement("input");
          opt.type = "submit";
          tempform.appendChild(opt);
          document.body.appendChild(tempform);
          tempform.submit();
          document.body.removeChild(tempform);
      }   */



    function getResultByErrorCode(result,idx) {
        //2为扫码错误处理
        let ltype=$("#loginModel").val(),params;
        //ltype 1为账号密码登录  2为手机号码登录
        if (result.errorCode == "success") {
            return true;
        }else {
            //add by zhangRb 20180319 密码错误提示中添加“忘记密码”操作提示
            if (result.errorCode == "B1003" || result.errorCode == "B1004") {
                // 用户名或者密码错误时，清除密码，等待时，不清除。
                if(vm.yimi.qrShow){
                    if (result.errorCode == "B1003") {
                        if(ltype==1){
                            $("#password").val("");
                        }
                    }
                    let temp=ltype==1?'用户名错误':'手机号码';
                    $("#errorMsg").text(result.errorMsg + "，您是否");
                    $("#errorMsg").append('<span style="color:#FFFFFF;">'+temp+'</span>');
                    $("#errorMsg").append('或');
                    if(ltype==1){
                        $("#errorMsg").append('<a style="cursor: pointer;color: #FFFFFF;" href="javascript:void();" onclick="toSendCode()">忘记密码</a>');
                    }else if(ltype==2){
                        $("#errorMsg").append('<span style="cursor: pointer;color: #FFFFFF;" >验证码错误</span>');
                    }
                }else{
                    if (result.errorCode == "B1003") {
                        vm.yimi.password="";
                    }
                    let temp='用户工号错误';
                    $("#errorMsg").text(result.errorMsg + "，您是否");
                    $("#errorMsg").append('<span style="color:#FFFFFF;">'+temp+'</span>');
                    $("#errorMsg").append('或');
                    $("#errorMsg").append('<span style="cursor: pointer;color: #FFFFFF;">密码错误</span>');
                }
                $("#errorMsg").css("padding-bottom",'10px');
            }else{
                if(ltype==1){
                    $("#checkcode").val("");
                }
                if(idx!=2){
                    $("#errorMsg").text(result.errorMsg);
                    $("#errorMsg").css("padding-bottom",'0px');
                }
            }
            if(result.errorCount >= 3){
                if(ltype==1){
                    $("#loginformvCode").click().show();
                    $("#c_container").show();
                    $('#checkcode').focus();
                }else if(ltype==2){
                    $("#loginMobileCode").click().show();
                }
            }
            if(idx==2){
                if(result.errorCode=="B3020"){    //扫码取消
                    window.clearInterval(countTime);
                    makeCode();
                }else if(result.errorCode=='B3015'){ //未扫码

                }else if(result.errorCode=="B3016"){ //已扫码未登录 可查询扫描人信息
                    $("#scan").hide();
                    $("#scanOk").show();
                }else if(result.errorCode=="B3017"){ //没有扫描信息
                    window.clearInterval(countTime);
                    $("#qrMask").show();
                    $("#scanOk").hide();
                    $("#scan").show();
                }else{
                    window.clearInterval(countTime);
                    $("#qrMask").show();
                    $("#scanOk").hide();
                    $("#scan").show();
                }
            }
        }
        return false;
    }

    function getImageCode(){
        var userName =$("#userName").val();
        let ltype=$("#loginModel").val(),params="",url="";
        if(ltype==1){
            params="&userName="+userName;
            url="${pageContext.request.contextPath}/getJPGCode.action?key=true"+params;
        }else{
            url="${pageContext.request.contextPath}/getJPGCode.action?key=true"
        }
        $.ajax({
            url : url,
            method : "get",
            dataType : "JSON",
            success : function(result) {
                if(result.resultCode == 'success'){
                    if(ltype==1){
                        $("#loginformvCode").prop('src',result.image);
                    }else if(ltype==2){
                        $("#loginMobileCode").prop('src',result.image);
                    }
                    $("#sigId").val(result.sig);
                    $("#tId").val(result.t);
                }else{
                    $("#errorMsg").text(result.errorMsg);
                }
            }
        });
    }
    function validateLogin() {
        $("#systemCode").val($("#systemCode").val().trim());
        $("#userName").val($("#userName").val().trim());
        $("#password").val($("#password").val());
        let ltype=$("#loginModel").val();
        if ($("#systemCode").val() == '') {
            $("#errorMsg").text("非法途径登录！");
            return false;
        }
        if(!vm.yimi.qrShow){
            if(!vm.value){
                $("#errorMsg").text("请选择账套！");
                return false;
            }
            if(!vm.yimi.userName.trim()){
                $("#errorMsg").text("工号不能为空！");
                return false;
            }
            if(!vm.yimi.password.trim()){
                $("#errorMsg").text("密码不能为空！");
                return false;
            }
            $("#errorMsg").html("");
            return true;
        }else{
            if(ltype==1){
                if ($("#userName").val() == '') {
                    $("#errorMsg").text("请输入用户名！");
                    return false;
                }
                if ($("#password").val() == '') {
                    $("#errorMsg").text("请输入密码！");
                    return false;
                }
                if(document.getElementById("c_container").style.display !="none"){
                    if($("#checkcode").val().trim()== ''){
                        $("#checkcode").text("请输入验证码！");
                        return false;
                    }
                }
                $("#errorMsg").html("");
                return true;
            }else if(ltype==2){
                $("#userTel").val($("#userTel").val().trim());
                $("#imageCode").val($("#imageCode").val().trim());
                $("#code").val($("#code").val().trim());
                if(!validateMobile()){
                    return false
                }
                if ($("#code").val() == '') {
                    $("#errorMsg").text("请输入短信验证码！");
                    return false;
                }
                $("#errorMsg").html("")
                return true;
            }
        }
    }

    function loginSuccess(result) {
// 			var loadComplete = [];
// 			for (var i = 0; i< result.systemCasUrlList.length; i++) {
// 			 	var iframe = document.createElement("iframe");
// 			 	//设置ifame对象src属性
// 			 	iframe.src = result.systemCasUrlList[i] + "?token=" + encodeURIComponent(result.token) + "&userName=" + encodeURIComponent(result.userName) + "&empCode=" + encodeURIComponent(result.empCode);
// 			 	iframe.style.display = "none";
// 			 	iframe.setAttribute("idx", i);
// 			 	loadComplete[i] = -1;
// 	 			addEvent(iframe, "load", function(){ 
// 		 			var i = this.getAttribute("idx");
// 		 			loadComplete[i] = 1;
// 		 		});
// 	 			addEvent(iframe, "error", function(){ 
// 		 			var i = this.getAttribute("idx");
// 		 			loadComplete[i] = 1;
// 		 		});
// 			 	document.body.appendChild(iframe);
// 			}
// 			var jumpInterval = window.setInterval(function() {
// 				for (var i = 0; i < loadComplete.length; i++) {
// 					if (loadComplete[i] == -1) {
// 						return;
// 					}
// 				}
// 				window.clearInterval(jumpInterval);  
        // 支持http和https，将协议头去掉，返回时按照请求的sso的协议返回。
        var hrefUrl = '${param.refUrl}';
        if (hrefUrl.length > 0) {
            var i = hrefUrl.indexOf(":");
            if (i > -1) {
                hrefUrl = hrefUrl.substring(i + 1);
            }
        }
        location.href = hrefUrl + "?token=" + encodeURIComponent(result.token) + "&userName=" + encodeURIComponent(result.userName) + "&empCode=" + encodeURIComponent(result.empCode);
// 			},10);
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

    function upperCase(dom)   {
        $(dom).val($(dom).val().toUpperCase());
    }

    function createCode(){
        code = "";
        var codeLength = 4;//验证码的长度
        var checkCode = document.getElementById("code");
        var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
            'S','T','U','V','W','X','Y','Z');//随机数
        for(var i = 0; i < codeLength; i++) {//循环操作
            var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
            code += random[index];//根据索引取得随机数加到code上
        }
        checkCode.value = code;//把code值赋给验证码
    }

    //校验验证码
    function validate(){
        var inputCode = document.getElementById("input").value.toUpperCase(); //取得输入的验证码并转化为大写
        if(inputCode.length <= 0) { //若输入的验证码长度为0
            alert("请输入验证码！"); //则弹出请输入验证码
        }
        else if(inputCode != code ) { //若输入的验证码与产生的验证码不一致时
            alert("验证码输入错误！@_@"); //则弹出验证码输入错误
            createCode();//刷新验证码
            document.getElementById("input").value = "";//清空文本框
        }
        else { //输入正确时
            alert("^-^"); //弹出^-^
        }
    }
    function getCpu(){
        let ua=navigator.userAgent;
        if(ua.indexOf("Windows NT 5.1")!=-1) {
            location.href="http://www.chromeliulanqi.com/forxp.exe"
        }else{
            location.href="http://www.chromeliulanqi.com/ChromeStandaloneSetup.exe"
        }
    }
    //登录方式切换
    function loginType(dom){
        //MobileMode uce 账户登录  codeMode壹米账户登录
        if(!$(dom).hasClass('active')){
            $(dom).parent().children("span").removeClass("active")
            $(dom).addClass("active");
            if($("#MobileMode").hasClass("active")){
                $("#loginForm").show();
                $("#yimiLogin").hide();
                vm.yimi.qrShow=true;
                $("#c_container").hide();
                $("#errorMsg").html("");
                $("#qrcode").html("");
                vm.modeInit();
            }
            if($("#codeMode").hasClass("active")){
                /* 壹米登录
                    loginWorkNumber 优速账号登陆
                    yimiLogin  一米账号登陆
                    loginCode 优速二维码登陆
                    loginMobile  手机登陆
                    loginForm 登陆父元素，不包含二维码登陆
                    yimi.qrShow  不显示账号二维码切换
                    c_container   三次错误密码以后显示图片验证码
                    errorMsg      错误信息
                    imageCode     手机图形验证码
                */
                //判断切换壹米登陆锁时，UCE账户登陆方式
                if(!$("#loginWorkNumber").is(":hidden")){
                    vm.yimi.uceFlag=1;
                }else if(!$("#loginMobile").is(":hidden")){
                    vm.yimi.uceFlag=2;
                }else if(!$("#loginCode").is(":hidden")){
                    vm.yimi.uceFlag=3;
                }
                $("#loginForm").show();
                $("#loginWorkNumber").hide();
                $("#yimiLogin").show();
                $("#loginCode").hide();
                $("#loginMobile").hide();
                window.clearInterval(countTime);
                vm.yimi.qrShow=false;
                $("#c_container").hide();
                $("#errorMsg").html("");
                let userName=vm.yimi.userName,password=vm.yimi.password;
                vm.yimi.userName="";
                vm.yimi.password="";
                setTimeout(res=>{
                    vm.yimi.userName=userName;
                vm.yimi.password=password;
            })
            }
        }
    }
    //生成二维码
    function makeCode(){
        $("#loading").show();
        $("#qrcode").html("");
        $("#qrMask").hide();
        $("#scanOk").hide();
        $("#scan").show();
        $.ajax({
            url : "${pageContext.request.contextPath}/getLoginCode.action?systemCode="+ $('#systemCode').val(),
            method : "get",
            dataType : "JSON",
            /* xhrFields: { withCredentials: true }, */
            success : function(result) {
                if(result.code){
                    $("#loading").hide();
                    let CountDown=result.t+120*1000;//一百秒后到期
                    var qrcode = new QRCode(document.getElementById("qrcode"), {
                        text: result.code,
                        width: 150,
                        height: 150,
                        colorDark : "#000000",
                        colorLight : "#ffffff",
                        correctLevel : QRCode.CorrectLevel.H
                    });
                    $("#qrcode").attr("title","");
                    loginForLoginCode(result.code);
                    window.clearInterval(countTime);
                    let count=(CountDown-result.t)/1000;
                    countTime=window.setInterval("countDown()",2000)
                    countDown=()=>{
                        if(count==0){
                            window.clearInterval(countTime);
                            $("#qrMask").show();
                            $("#scan").show();
                            $("#scanOk").hide();
                        }else{
                            count=count-2;
                            if(isScan){
                                loginForLoginCode(result.code);
                            }
                        }
                    }
                }
            },
            error:function(){
                $("#loading").hide();
            }
        });
    }
    //校验扫码登录
    function loginForLoginCode(code){
        var t = $("#tId").val() == null || $("#tId").val() == undefined || $("#tId").val() == '' ? 0 : $("#tId").val();
        isScan=false;
        let params={
            "systemCode" : $("#systemCode").val(),
            "refUrl" : $("#refUrl").val() ,
            "sig":$("#sigId").val(),
            "t":t,
            "browserInfo":getBrowserInfo(),
            "checkCode":code
        }
        $.ajax({
            url : "${pageContext.request.contextPath}/loginForLoginCode.action",
            method : "get",
            dataType : "JSON",
            /* xhrFields: { withCredentials: true }, */
            data:params,
            success : function(result) {
                isScan=true;
                //result.errorCode="B3016";
                if(result.errorCode=='B3008'){//成功
                    window.clearInterval(countTime);
                    location.href = "${pageContext.request.contextPath}/portal/forward.action";
                }else if (getResultByErrorCode(result,2)) {
                    window.clearInterval(countTime);
                    loginSuccess(result);
                }
            },
        })
    }
    //手机号登录与密码登录切换
    function modeChange(idx){
        //idx 当前元素 ltype ==1  uce 账号登录， ==2手机号码登录
        let ltype=$("#loginModel").val();
        let str=$(idx).text();
        if(ltype==1){
            $(idx).text("账号密码登录");
            $("#loginModel").val("2");
            $("#loginWorkNumber").hide();
            $("#loginMobile").show();
            $("#c_container").hide();
            $("#loginMobileCode").click().show();
            $("#errorMsg").html("");
            $("#imageCode").val("");
            $("#code").val("");
            let userTel=$("#userTel").val();
            $("#userTel").val("");
            setTimeout(res=>{
                $("#userTel").val(userTel);
        })
        }else if(ltype==2){
            $(idx).text("手机验证码登录");
            $("#loginModel").val("1");
            $("#loginWorkNumber").show();
            $("#loginMobile").hide();
            $("#c_container").hide();
            $("#errorMsg").html("");
            let userName=$("#userName").val();
            let password=$("#password").val();
            $("#userName").val("");
            $("#password").val("");
            setTimeout(res=>{
                $("#userName").val(userName);
            $("#password").val(password);
        })
        }
    }
    //校验手机号码
    function validateMobile(){
        let userTel=$("#userTel").val().trim();
        let imgCode=$("#imageCode").val().trim();
        //判断手机号为11位数字，以1开头,港澳台以00852,00853,00886开头,长度8-10位
        let regex = /^(1|00852|00853|00886)\d{8,10}$/;
        if(userTel==''){
            $("#errorMsg").text("请输入手机号!");
            $("#userTel").val("");
            return false;
        }else if(userTel.length<7||userTel.length>15){
            $("#errorMsg").text("输入内容长度必须介于7和15之间!");
            $("#userTel").val("");
            return false;
        }else if(!regex.test(userTel)){
            $("#errorMsg").text("请输入正确的手机号!");
            return false;
        }
        if(imgCode==""){
            $("#errorMsg").text("请输入图片验证码！");
            return false;
        }
        $("#errorMsg").html("");
        return true;
    }
    function getResponeResult(result) {
        if (!result.success) {
            if(result.errorCode == "CCEC"){
                $("#errorMsg").text(result.errorMsg);
                $("#loginMobileCode").click().show();
            }else{
                $("#errorMsg").text(result.errorMsg);
            }
            return false;
        }
        $("#errorMsg").html("");
        return true;
    }
    //获取验证码处理
    var countdown = 60;
    function settime(val) {
        if (countdown == 0) {
            val.classList.add("active");
            $("#getCode").attr("disabled",false);
            val.innerHTML = "重新发送";
            countdown = 60;
            return;
        } else {
            val.classList.remove('active');
            val.innerHTML = countdown + "秒后可重发";
            $("#getCode").attr("disabled",true);
            countdown--;
        }
        setTimeout(function() {
            settime(val)
        }, 1000)
    }
    //获取验证码
    function getMobileCode(){
        if (!validateMobile()) {
            return;
        }
        $.ajax({
            url : "${pageContext.request.contextPath}/checkMobile.action",
            method : "post",
            dataType : "JSON",
            data : {
                "mobile" : $("#userTel").val(),
                "checkCode": $("#imageCode").val().toLowerCase(),
                "sig":$("#sigId").val(),
                "t":$("#tId").val()
            },
            success : function(result) {
                if (getResponeResult(result)) {
                    $.ajax({
                        url : "${pageContext.request.contextPath}/sendCode.action",
                        method : "post",
                        dataType : "JSON",
                        data : {
                            "systemCode" : $("#systemCode").val(),
                            "mobile" : $("#userTel").val(),
                            "initPw" : false,
                            "sig":$("#sigId").val(),
                            "t":$("#tId").val(),
                            "checkCode": $("#imageCode").val().toLowerCase(),
                            "codeType" : "LOGIN" //登陆方式获取验证码
                        },
                        success : function(result) {
                            if (getResponeResult(result)) {
                                settime($("#getCode").get(0));
                            }
                        }
                    });
                }
            },
        });
    }
    //监控手机号,验证码实时输入
    function iptChange(ipt,idx){
        //idx为1,图片验证码
        let val=$(ipt).val().trim();
        //判断是否是数字
        if(isNaN(Number(val))&&idx!=1){
            val=val.substring(0,val.length-1);
        }
        $(ipt).val(val);
        let tel=$("#userTel").val().trim();
        let code=$("#imageCode").val().trim();
        if(tel&&code){
            $("#getCode").addClass("active");
        }else{
            $("#getCode").removeClass("active");
        }
    }
    //监听验证码实时输入
    function iptCode(ipt){
        let val=$(ipt).val().trim();
        //判断是否是数字
        if(isNaN(Number(val))){
            val=val.substring(0,val.length-1);
        }
        $(ipt).val(val);
    }
</script>
</body>
</html>