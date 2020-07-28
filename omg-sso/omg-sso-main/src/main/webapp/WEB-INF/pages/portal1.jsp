<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<title>优速快递</title>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/encryptionAlgorithm.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/easyui-lang-zh_CN.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/swiper.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/easyui.extend.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="//static-file.uce.cn/uce-static/iconfont/iconfont.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/swiper.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css"/>
		<style type="text/css">
			body{
				background: #f3f5f6;
			}
			/*-------------------*/
			.header{
				display: flex;
				display: -webkit-flex;
				display: -moz-flex;
				display: -ms-flex;
				height:46px;
				background:#6952a0;
				padding: 0 5%;
			}
			.header .left{
				flex:1;
				display: flex;
				display: -webkit-flex;
				display: -moz-flex;
				display: -ms-flex;
			}
			.header div.name{
				height:46px;
				line-height:46px;
				font-size:20px;
				color:#fff;
			    white-space: nowrap;
			}
			.header .right{
				flex:2;
				height:46px;
				line-height:46px;
				text-align:right;
				color:#fff;
				font-size:16px;
				position:relative;
				top:-2px;
			    white-space: nowrap;
			}
			.header .right>a{
				padding: 0 15px;
				display:inline-block;
				position:relative;
				top:2px;
			}
			.header .right>a span{
				position:relative;
				top:-2px;
				font-size:14px;
				color:#fff;
				padding-left:2px;
			}
			.header .right>a:hover span{
				border:none;
				color:rgba(238,120,0,1);
				cursor: pointer;
			}
			.header .right>a span.span-tip{
				border-radius: 45%;
				text-align: center;
				background: red;
				font-size: 12px;
				padding: 0 1px;
				margin-left: -10px;
				top: -10px;
			}
			.header .right>a:hover span.span-tip{color:#fff;}
			/*-------------------*/
			.auto-swiper{
				height: auto;
			}
			.auto-swiper img{
				width: 100%;
			}
		 	.swiper-container {
		      width: 100%;
		      height: auto;
		    }
		    .swiper-slide {
		      text-align: center;
		      font-size: 18px;
		      background: #fff;
		
		    }
		    .swiper-container .swiper-slide {
		      height: 200px;
		      line-height: 200px;
		    }
		    /*-------------------*/
		    .content{
		   		padding: 0 5%;
		   		margin-top: 15px;
		   		display: -webkit-box;
			    display: -ms-flexbox;
			    display: flex;
			    -webkit-box-orient: horizontal;
			    -webkit-box-direction: normal;
			    -ms-flex-flow: row;
			    flex-flow: row;
		    }
		    .fast-way{
		    	-webkit-box-flex: 3;
			    -ms-flex: 3;
			    flex: 3;
		    }
		    .system-msg{
		    	-webkit-box-flex: 1;
			    -ms-flex: 1;
			    flex: 1;
			    padding-left: 30px;
		    }
		    .title{
		    	padding-left: 10px;
		    	border-left: 6px solid #6952a0;
		    	margin-bottom: 10px;
		    	margin-left: 10px;
	    	    line-height: 25px;
    			font-size: 16px;
		    }
		    .system-list li{
	    	    position: relative;
			    /* width: calc(20% - 20px); */
			    width: 155px;
			    float: left;
			    margin: 10px;
			    cursor: pointer;
		    }
		    .system-list li>div:nth-child(1){
		    	width: 100%;
		    	padding: 50% 0;
		    }
		    .sys-details{
	    	    position: absolute;
			    width: 100%;
			    height: 100%;
			    top: 0;
			    text-align: center;
			    overflow: hidden;
			    border-radius: 4px;
			    box-shadow:0 2px 16px #ddd, 0 0 1px #ddd, 0 0 1px #ddd;
		    }
		    .sys-details:hover{
		    	background: #ededed;
		    }
		    .sys-details img{
			   /*  margin: 30px auto 0 auto; */
			    margin: 15% auto 0 auto;
			    display: block;
			    /* width: 40px; */
			    width : 20%;
			    height : 20%;
		    }
		    .sys-details span.nickname{
		    	position: absolute;
			    top: 3px;
			    right: 0;
	    	    background: #ccc;
			    color: #fff;
			    font-size: 14px;
			    font-weight: bold;
			    line-height: 20px;
			    display: block;
			    width: 40px;
		    }
		    .sys-details span.name{
		    	display: block;
		    	font-size: 16px;
		    	font-weight: bold;
		    	color: #6952a0;
		    	text-align: center;
		    	margin-top: 20px;
		    }
		    .sys-details span.inline{
		    	display: inline-block;
		    	width: 30%;
		    	height: 2px;
		    	background: #6952a0;
		    	margin: 10px 0;
		    }
		    .sys-details span.description{
		    	display: block;
		    	font-size: 14px;
		    	color: #908d8d;
		    	text-align: center;
		    	text-overflow: ellipsis;
                white-space: nowrap;
                overflow: hidden;
		    	margin-right: 8px;
		    	margin-left: 8px;
		    }
		    .msg-list{
		    	padding: 15px;
			    background: #fff;
			    border: 1px solid #ddd;
			    margin-top: 20px;
			    margin-left: 10px;
		    }
		    .msg-list li{
			    line-height: 20px;
			    font-size: 14px;
			    word-break: break-all;
			    padding: 5px 0;
			    border-bottom: 1px solid #d5d5d5;
			    cursor: pointer;
			    overflow: hidden;
		    }
		    .msg-list li:hover{
		    	background: #eeedf0;
		    }
		    .msg-list li:before{
		    	content: "";
			    width: 6px;
			    height: 6px;
			    border-radius: 50%;
			    background-color: #f17509;
			    display: inline-block;
			    vertical-align: middle;
		    }
		    .msg-list li span{
		    	padding-left: 10px;
		    }
		    .msg-list li span.time{
	    	    display: block;
			    text-align: right;
			    font-size: 12px;
			    color: #838080;
		    }
		    .more-msg{text-align: center;padding: 20px 0;}
		    .more-msg a{
		    	font-size: 16px;
		    	color: #6952a0;
		    	text-decoration: underline;
		    }
		    
		    .footer{
		    	margin: 40px 0;
		    	text-align: center;
		    }
		    .swiper-pagination-bullet-active{
		    	background-color: #fff;
		    }
		    
		    .window{
		   		 background: linear-gradient(to bottom,#8277bb 0,#bfacec 20%);
		    }

			.window, .window .window-body {
			    border-color: #8976b7;
			}
			
			.window-shadow {
			    -moz-box-shadow: 2px 2px 3px #7a7582;
			    -webkit-box-shadow: 2px 2px 3px #7a7582;
			    box-shadow: 2px 2px 3px #7a7582;
			}
			
		</style>
	</head>
	<body>
		<header class="header">
			<div  class="left">
				<img alt="logo" src="${pageContext.request.contextPath}/images/uc56_logo_new1.png" style="width:70px;height:46px;padding: 0 0 0 10px;">
				<div class="name">优速快递</div>
			</div>
			<div class="right">
				<!-- 如果有加按钮，需要手动调整 padding  -->
				<a id="userdtl"><i class="iconfont uce-people"></i><span id="portalEmpName"></span></a>|
				<a><i class="iconfont uce-qiehuan"></i><span id="portalOrgName"></span></a>|
				<a onclick="openDialog('winPassword')"><i class="iconfont uce-password"></i><span>密码修改</span></a>|
				<!-- <a id="setting"><i class="iconfont uce-set"></i><span>设置</span></a>| -->
				<a onclick="quit()"><i class="iconfont uce-exit"></i><span>退出</span></a>
			</div>
		</header>
		<div class="auto-swiper">
			<div class="swiper-container">
			    <div class="swiper-wrapper">
			      <div class="swiper-slide"><img src="${pageContext.request.contextPath}/images/banner.png" alt="loading"/></div>
			      <!-- <div class="swiper-slide"><img src="images/system/banner.png" alt="loading"/></div>
			      <div class="swiper-slide"><img src="images/system/banner.png" alt="loading"/></div> -->
			    </div>
    			<div class="swiper-pagination"></div>
		  	</div>
		</div>
		<div class="content">
			<div class="fast-way">
				<h2 class="title">快速入口</h2>
				<ul class="system-list" id="systemInfoId"></ul>
			</div>
			<div class="system-msg">
				<h2 class="title">公告</h2>
				<ul class="msg-list" id="articleId"></ul>
			</div>
		</div>
		
		<div id="announcementId"></div>  
		
   <div id="winPassword" class="easyui-dialog" data-options="title:'修改密码',closed:true"
     style="width:300px;height:200px;padding:5px">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center',border:false"
             style="padding:10px 20px;background:#fff;border:1px solid #ccc;">
            <form id="fomPassword" method="post">
                <table class="table">
                    <tr>
                        <td>原密码</td>
                        <td><input type="password" id="oldPassword" name="oldPassword" class="easyui-textbox"
                                   data-options="required:true"></td>
                    </tr>
                    <tr>
                        <td>新密码</td>
                        <%--add by BaoJingyu 2017-12-09 增加校验规则 begin--%>
                        <td><input type="password" id="password" name="password" class="easyui-textbox"
                                   data-options="required:true,validType:['diff[&quot;#oldPassword&quot;]','consecutiveRepeatCharacterRegex','consecutiveCharacterRegex','passwordRule']">
                            <%--add by BaoJingyu 2017-12-09 end--%>
                        </td>
                    </tr>
                    <tr>
                        <td>重复密码</td>
                        <td><input type="password" id="repeatPassword" class="easyui-textbox"
                                   data-options="required:true" validType="equals['#password']"></td>
                    </tr>
                </table>
            </form>
        </div>
        <div data-options="region:'south',border:false" style="height:38px;text-align:right;padding:5px 0;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0)"
               onclick="updatePassword()">保存</a>
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
               onclick="closeDialog('winPassword')">取消</a>
	        </div>
	    </div>
	</div>
		
		<!-- <footer class="footer">
			<div>&copy;  2009-2018  优速物流有限公司版权所有</div>
		</footer> -->
		
		<!-- <div class="copyright">
        <div id="fullsearch" class="copy-text">
          <h2>&copy; 2017 优速物流有限公司版权所有</h2>
        </div> -->
      
    </div>
		
		<script type="text/javascript">
		   //总部
		   var headquarters = ["NOS","PRS","DAR","WPM","UBS","EBU","GIS","OMG"];
		   //财务中心
		   var financialCenter = ["NOS","PRS","DAR","UBS","EBU","GIS","OMG"];
		   //分拨中心
		   var distributionCenter = ["NOS","PRS","DAR","WPM","UBS","EBU","GIS"];
		   //网点
		   var outlets = ["NOS","PRS","DAR","UBS","EBU","GIS","CCP","OMG"]; //"NOS"
		   //承包区
		   var contractArea = ["NOS","PRS","DAR","UBS","EBU","GIS","CCP","OMG"]; 
		   
		
		  	var swiper = new Swiper('.swiper-container', {
	  		  autoplay:true,
	  		  loop : true,
		      autoHeight: true, //enable auto height
		      spaceBetween: 30,
		      pagination: {
		        el: '.swiper-pagination',
		        clickable: true,
		      },
		      navigation: {
		        nextEl: '.swiper-button-next',
		        prevEl: '.swiper-button-prev',
		      }
		    });
		  	
	        //修改密码
	        function updatePassword() {
	            if ($("#fomPassword").form('enableValidation').form('validate')) {
	                $.ajax({
	                    type: 'POST',
	                    data: {
	                        "oldPassword": hex_md5($("#oldPassword").textbox('getValue')),
	                        "newPassword": hex_md5($("#password").textbox('getValue')),
	                        "passwordStrength" : "STRONG",
	                    },
	                    url: '${pageContext.request.contextPath}/portal/updPwd.action',
	                    success: function (data) {
	                    	if (data.errorCode == "success") {
	                    		$.messager.alert('', '密码修改成功,将跳转到登陆界面重新登陆！', 'info', function (r) {
	                                //跳转到登陆界面重新登陆
	                                window.location.href = '${pageContext.request.contextPath}/toLogin.action';
	                            });
	            			} else {
	            				errorMsg(data.errorMsg);
	            			}
	                    }
	                });
	            }
	        }
		  	
		      //总部-10,财务中心-20,操作中心-21,网点-30,承包区-40
		    $(document).ready(function(){
		    	$.post("${pageContext.request.contextPath}/portal/portalInfo.action", function(result) {
		    		if(typeof result == "string" ){
		    			result = eval('(' + result + ')');
		    		} 
		    		if(result.status === 0){
		    			$("#portalEmpName").text(result.data.empName);
		    			$("#portalOrgName").text(result.data.orgName);
		    			var sysInfos = result.data.sysTmeInfo;
		    			var orgType = result.data.orgType;
		    			var otherOrgs = result.data.otherOrg;
		    			var myArray = [];
		    			for(var j = 0 ; j < otherOrgs.length ; j++){
		    				var otherOrg = otherOrgs[j];
		    				if(otherOrg.orgType === 10){
		    					myArray = myArray.concat(headquarters);
		    				}else if(otherOrg.orgType === 20){
		    					myArray = myArray.concat(financialCenter);
		    				}else if(otherOrg.orgType === 21){
		    					myArray = myArray.concat(distributionCenter);
		    				}else if(otherOrg.orgType === 30){
		    					myArray = myArray.concat(outlets);
		    				}else if(otherOrg.orgType === 40){
		    					myArray = myArray.concat(contractArea);
		    				}
		    			}
		    			//去除重复
		    			myArray = myArray.unique3();
		    			
		    			var sort = [];
		    			
		    			for(var i = 0 ; i < sysInfos.length ; i++){
		    				//var sysInfo = sysInfos[i];
		    				//var sysCode = sysInfo.systemCode.toLowerCase();
	    					
		    				if(i == 0){
		    					sort[i] = getArray("PRS",sysInfos);
		    				}else if ( i == 1){
		    					sort[i] = getArray("NOS",sysInfos);
		    				}else if ( i == 2){
		    					sort[i] = getArray("DAR",sysInfos);
		    				}else if ( i == 3){
		    					sort[i] = getArray("WPM",sysInfos);
		    				}else if ( i == 4){
		    					sort[i] = getArray("UBS",sysInfos);
		    				}else if ( i == 5){
		    					sort[i] = getArray("EBU",sysInfos);
		    				}else if ( i == 6){
		    					sort[i] = getArray("CCP",sysInfos);
		    				}else if ( i == 7){
		    					sort[i] = getArray("GIS",sysInfos);
		    				}else if ( i == 8){
		    					sort[i] = getArray("OMG",sysInfos);
		    				}
		    			} 
		    			
		    			if(sort != null && sort.length > 0){
		    				for(var j = 0 ; j < sort.length ; j++){
		    					var sysInfo = sort[j];
			    				var sysCode = sysInfo.systemCode.toLowerCase();
			    				if(myArray.indexOf(sysInfo.systemCode) > -1){
		    						if("CCP" == sysInfo.systemCode){
		    							$("#systemInfoId").append('<li onClick="gotoPage(\''+sysInfo.refUrl+'\')"><div></div><div class="sys-details" title="'+(sysInfo.remark == null ? '' : sysInfo.remark)+'"><img src="${pageContext.request.contextPath}/images/portal/icon-'+sysCode+'.png"/><span class="nickname">UCP</span><span class="name">'+sysInfo.systemName+'</span><span class="inline"></span><span class="description">'+(sysInfo.remark == null ? '' : sysInfo.remark)+'</span></div></li>');
		    						}else{
		    							$("#systemInfoId").append('<li onClick="gotoPage(\''+sysInfo.refUrl+'\')"><div></div><div class="sys-details" title="'+(sysInfo.remark == null ? '' : sysInfo.remark)+'"><img src="${pageContext.request.contextPath}/images/portal/icon-'+sysCode+'.png"/><span class="nickname">'+sysInfo.systemCode+'</span><span class="name">'+sysInfo.systemName+'</span><span class="inline"></span><span class="description">'+(sysInfo.remark == null ? '' : sysInfo.remark)+'</span></div></li>');
		    						}
		    					}
		    				}
		    			}
		    			
		    		}else if (result.status === 2){
		    			window.location.href = '${pageContext.request.contextPath}/toLogin.action';
		    		}else{
		    			infoMsg(result.message);
		    		}
		    	});
		    	
		    	$.post("${pageContext.request.contextPath}/portal/article.action", function(result) {
		    		if(result.status === 0){
		    			var article = result.data;
		    			if(article.length > 0){
		    				addAnnouncement(article,0);
		    			}else{
		    				$("#articleId").append('<li><span>暂无任何公告</span><span class="time"></span></li>');
		    			}
		    		}else if (result.status === 2){
		    			window.location.href = '${pageContext.request.contextPath}/toLogin.action';
		    		}else{
		    			$("#articleId").append('<li><span>'+result.message+'</span><span class="time"></span></li>');
		    		}
		    	});
		    });
		      
		    function getArray(systemCode,sysInfos){
		    	if(sysInfos != null && sysInfos.length > 0){
		    		for(var i = 0 ; i < sysInfos.length ; i++){
		    			var sysCode = sysInfos[i].systemCode;
		    			if(systemCode == sysCode){
		    				return sysInfos[i];
		    			}
		    		}
		    	}
		    }
		    
			Array.prototype.unique3 = function() {
				var res = [];
				var json = {};
				for (var i = 0; i < this.length; i++) {
					if (!json[this[i]]) {
						res.push(this[i]);
						json[this[i]] = 1;
					}
				}
				return res;
			}

			function viewAnnouncement(fileName) {
				$('#announcementId').dialog({
					title : '公告信息',
					width : 600,
					height : 600,
					closed : false,
					cache : false,
					minimizable : true,
					maximizable : true,
					modal : true,
					resizable : true,
				});
				
				$.post("${pageContext.request.contextPath}/portal/getArticle.action?fileName="+ encodeURIComponent(fileName), function(result) {
					if (result.indexOf('"message":"用户登录失效","status":2') > -1){
		    			window.location.href = '${pageContext.request.contextPath}/toLogin.action';
		    		}else{
		    			$('#announcementId').dialog({"content":result});
		    		}
		    	});
				
				/* $('#announcementId').dialog(
						'refresh',
						"${pageContext.request.contextPath}/portal/getArticle.action?fileName="+ encodeURIComponent(fileName)); */ 
				$('#announcementId').dialog("center");
			}

			function addAnnouncement(article, i) {
				if (article.length > 0) {
					var num = $('#articleId').children().length + 10 - i;
					$("#articleId").empty();
					if (article.length > num) {
						for (var i = 0; i < num; i++) {
							$("#articleId").append(
									'<li onclick="viewAnnouncement(\''
											+ article[i].fileNameAll
											+ '\')"><span>'
											+ article[i].fileName
											+ '</span><span class="time">'
											+ article[i].date + '</span></li>');
						}
						$("#articleId")
								.append(
										'<div class="more-msg"><a onclick="getMore()">查看更多</a></div>');
					} else {
						for (var i = 0; i < article.length; i++) {
							$("#articleId").append(
									'<li onclick="viewAnnouncement(\''
											+ article[i].fileNameAll
											+ '\')"><span>'
											+ article[i].fileName
											+ '</span><span class="time">'
											+ article[i].date + '</span></li>');
						}
					}
				} else {
					$("#articleId")
							.append(
									'<li><span>暂无任何公告</span><span class="time"></span></li>');
				}
			}
			function getMore() {
				$.post("${pageContext.request.contextPath}/portal/article.action",
								function(result) {
									if (result.status === 0) {
										var article = result.data;
										addAnnouncement(article, 1);
									} else {
										$("#articleId")
												.append(
														'<li><span>'
																+ result.message
																+ '</span><span class="time"></span></li>');
									}
								});
			}
			function gotoPage(refUrl) {
				if (refUrl == null || refUrl == '' || refUrl == undefined
						|| refUrl == 'null') {
					infoMsg('该项目没有配置主页地址，请联系该项目管理员进行配置。');
				} else {
					window.open(refUrl);
				}
			}

			/*   function getQueryString(name) {                                       //name为传入参数
			      var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");     
			      var r = window.location.search.substr(1).match(reg);
			      if (r != null) return unescape(r[2]); return null;						
			  } */

			//退出登录
			function quit() {
				confirmMsg(
						"您确定要退出系统吗？",
						function() {
							$.post( "${pageContext.request.contextPath}/portal/logout.action",
											function(result) {
												if (result.status === 0) {
													window.location.href = '${pageContext.request.contextPath}/toLogin.action';
												} else {
													infoMsg(result.message);
												}
											});
						});
			}

			$.extend($.fn.validatebox.defaults.rules, {
				equals : {
					validator : function(value, param) {
						return value == $(param[0]).val();
					},
					message : '两次输入密码不一致！'
				},
				diff : {
					validator : function(value, param) {
						return value != $(param[0]).val();
					},
					message : '新密码与旧密码相同！'
				},
				minLength : {
					validator : function(value, param) {
						return value.length >= param[0];
					},
					message : '密码长度不能小于{0}位！'
				}
			});
		</script>
		
	</body>
</html>
