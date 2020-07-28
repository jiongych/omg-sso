<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="images/favicon.ico">
	<title>优速快递</title>
	<link rel="icon" type="image/png" href="http://www.uce.cn/images/logo_12.ico"/>
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/jquery.easyui.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/vue-2.3.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/jquery-accordion-menu.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/uce.util.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/encryptionAlgorithm.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$.extend($.fn.validatebox.defaults.rules,{
			//当前控件值需与 id 这个控件的值相同
			equals: {
				validator: function(value, param) {
					return value == $(param[0]).val();
				},
				message: "密码不一致"
			},
			diff : {
				validator : function(value, param) {
					return value != $(param[0]).val();
				},
				message : '新密码与旧密码相同！'
			},
			consecutiveRepeatCharacterRegex: {
				validator: function(value) {
					var regex = /(.)(\1){2,}/;
					return !regex.test(value); //false
				},
				message: '密码中不能包含三位以上重复的字符!'
			},
			consecutiveCharacterRegex: {
				validator: function(value) {
					var regex = /((9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){2}\d|(0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){2}\d|(a(?=b)|b(?=c)|c(?=d)|d(?=e)|e(?=f)|f(?=g)|g(?=h)|h(?=i)|i(?=j)|j(?=k)|k(?=l)|l(?=m)|m(?=n)|n(?=o)|o(?=p)|p(?=q)|q(?=r)|r(?=s)|s(?=t)|t(?=u)|u(?=v)|v(?=w)|w(?=x)|x(?=y)|y(?=z)){2}\w|(z(?=y)|y(?=x)|x(?=w)|w(?=v)|v(?=u)|u(?=t)|t(?=s)|s(?=r)|r(?=q)|q(?=p)|p(?=o)|o(?=n)|n(?=m)|m(?=l)|l(?=k)|k(?=j)|j(?=i)|i(?=h)|h(?=g)|g(?=f)|f(?=e)|e(?=d)|d(?=c)|c(?=b)|b(?=a)){2}\w|(A(?=B)|B(?=C)|C(?=D)|D(?=E)|E(?=F)|F(?=G)|G(?=H)|H(?=I)|I(?=J)|J(?=K)|K(?=L)|L(?=M)|M(?=N)|N(?=O)|O(?=P)|P(?=Q)|Q(?=R)|R(?=S)|S(?=T)|T(?=U)|U(?=V)|V(?=W)|W(?=X)|X(?=Y)|Y(?=Z)){2}\w|(Z(?=Y)|Y(?=X)|X(?=W)|W(?=V)|V(?=U)|U(?=T)|T(?=S)|S(?=R)|R(?=Q)|Q(?=P)|P(?=O)|O(?=N)|N(?=M)|M(?=L)|L(?=K)|K(?=J)|J(?=I)|I(?=H)|H(?=G)|G(?=F)|F(?=E)|E(?=D)|D(?=C)|C(?=B)|B(?=A)){2}\w)/;
					return !regex.test(value); //false
				},
				message: '密码中不能包含三位以上正序或倒序的连续数字或字母!'
			},
			passwordRule: {
				validator: function(value) {
					var regex = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,}$/;
					return regex.test(value); //false
				},
				message: '密码由英文字母大写、小写，数字及特殊符号构成，必须满足3种类型，且不能少于8位!'
			}
			/*2017-12-18 修改密码强度校验规则*/
			
			
		});
	</script>
	<!--elementui-->
	<script src="//unpkg.com/element-ui@2.3.2/lib/index.js"></script>
	<link rel="stylesheet" type="text/css" href="//unpkg.com/element-ui@2.3.2/lib/theme-chalk/index.css"/>
	
	<link rel="stylesheet" type="text/css" href="//static-file.uce.cn/uce-static/iconfont/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="//static-file.uce.cn/uce-static/easyui-themes/themes/insdep/easyui.css" />
	<link rel="stylesheet" href="//static-file.uce.cn/uce-static/easyui-themes/themes/insdep/icon.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-accordion-menu2.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/portal-index1.css?v=20190123">
	<style type="text/css">
	
		/* 首页最外面右侧滚动条 */
		/* html{
			height: 150%;
			overflow-y: auto;
		} */
		body {
			height: 100%;
		}
		.el-radio-button:first-child .el-radio-button__inner,.el-radio-button:last-child .el-radio-button__inner{
			border-radius: 0!important;
			border-width: 0;
		}
		.el-radio-button--mini .el-radio-button__inner{
			padding: 7px 10px;
			font-size: 12px;
			line-height: 20px;
			border-width: 0 0 1px 0!important;
			border-radius: 0;
			display: block;
			width: 35px;
			white-space: normal;
		}
		.el-radio-button__orig-radio:checked+.el-radio-button__inner{
			background-color: #f5683d;
			border-color: #f5683d;
		}
		.v-modal{
			opacity: 1;
		}
		
	</style>
</head>

<body>
	<!--菜单模板-->
	<script type="text/x-template" id="item-template">
			<li @click="select($event)" v-bind:id="id"  v-if="nodeType==type || type=='ALL'">
				<a style="display: flex;"><i class="iconfont" :class="iconClass"></i><span style="flex: 1;">{{model.text}}</span></a>
				<ul class="submenu" v-if="model.children">
					<items v-for="child in model.children" v-bind:model="child" :type="type"></items>
				</ul>
			</li>
		</script>

	<div id="vueScope" :class="settings.themes" v-cloak>
		<!------------header------------------->
		<header class="header">
			<div class="left">
				<img alt="logo" src="${pageContext.request.contextPath}/images/uc56_logo_new1.png" style="width:70px;height:46px;">
				<div class="title">优速快递</div>
			</div>
			<div class="searchArea" id="searchBill" style="width:220px;display:none;">
				<input id="searchBillInput" class="easyui-textbox" data-options="multiline:true, 
						height:30,iconWidth:28,prompt:'请输入运单编号',  
						required:false, tipPosition:'left'">
				
				<!--搜索结果
				<div class="search-data" v-if="config.searchType==true">
					<i class="iconfont uce-cancel" @click="config.searchType=false"></i>
				</div>-->
			</div>
			<div class="right">
				<!-- 如果有加按钮，需要手动调整 padding  -->
				<a id="userdtl"><i class="iconfont uce-people"></i><span id="empName">{{user.empNameShow}}</span><!--<i class="iconfont uce-triangledown"></i>--></a>|
				<a @click="opDept()">
					<i class="iconfont uce-qiehuan"></i><span id="orgNmae">{{user.userInfo.orgName}}</span>
				</a>
				<a v-if="showPrise" @click="detailOmg()"><img id="detailOmg" style="margin-left:-20px;margin-top:-10px;" src="${pageContext.request.contextPath}/images/portal/detail-white.png"
					onMouseOver="mover()" onMouseOut="mout()"></a>|
				<!--<a id="message"><i class="iconfont uce-remind-circle"></i><span class="span-tip">99</span><span>消息</span></a>|-->
				<!--<a onclick="updatePwd()"><i class="iconfont uce-password"></i><span>密码修改</span></a>|-->
				<a @click="$('#dlgUpdatePwd').dialog('open');" id="setting"><i class="iconfont uce-password"></i><span>密码修改</span></a>|
				<a @click="logout()"><i class="iconfont uce-exit"></i><span>退出</span></a>
			</div>
		</header>
		<!--------------主体布局------------->
		<article class="uce-layout">
			<!--菜单-->
			<div id="vuedom" class="navigation" style="display: flex;">
			<!-- <div class="menu-type">
					<el-radio-group size="mini" v-model="menuType" @change="changeMenuType">
					  <el-radio-button label="ALL">全部</el-radio-button>
					  <el-radio-button label="A">A类</el-radio-button>
					  <el-radio-button label="B">B类</el-radio-button>
					  <el-radio-button label="C">C类</el-radio-button>
					  <el-radio-button label="D">D类</el-radio-button>
					</el-radio-group>
				</div> -->
				<div class="main-menu" v-cloak onMouseOver="moverCompanyFlag()" onMouseOut="moutCompanyFlag()">
					<ul id="demo-list">
						<li id="homepage" :class="{'cell-choise':choiseId==-2}" @mouseenter="columnFlage=false;choiseId=-2" @click="">
							<a>
								<i class="iconfont uce-home"></i>
								<span>首页</span>
							</a>
						</li>
						<li v-for="(item, index) in dataMenu" :class="{'cell-choise':choiseId==item.id}" 
							@mouseenter="openColumnMenu(item)" @mousedown="toggleColumn()">
							<a>
								<i class="iconfont" :class="item.permissionIcon?item.permissionIcon:'uce-file'"></i>
								<span>{{item.text}}</span>
								<i class="iconfont uce-toggle" style="transform: rotate(-90deg);position: absolute;right: 5px;"></i>
							</a>
						</li>
					</ul>
					<div id="companyFlag" style="position:fixed;bottom: 0;width: 200px;text-align: center;line-height: 40px;color:#fff;display:none">&copy;2018优速物流有限公司版权所有</div>
				</div>
				<div class="second-menu" v-show="columnFlage">
					<div class="column-list">
						<div class="third-cell" v-for="(item, index) in nextPermissionMenu">
							<h3>{{item.text}}</h3>
							<li v-for="(childItem, index) in item.children"
								@click="openTab(childItem.text, childItem.attributes, true, childItem.id,childItem.systemCode, false);$('#companyFlag').hide();$('#vuedom').removeClass('vuedom-hover');">
								<span>{{childItem.text}}</span>
							</li>
						</div>
					</div>
				</div>
				
			</div>
			<!--主体内容-->
			<div id="mainTab" class="easyui-tabs" fit="true" style="margin-left: 50px;"></div>
		</article>

		<!-------------------------模板/模块------------------------->
		<!--用户信息-->
		<!--<div class="userdtl" style="display:none;">
			<div id="triangle-facing-left" class="triangle-facing-left"></div>
			<div class="user-content">
				<ul>
					<li>
						<i class="iconfont uce-status"></i>
						<span>{{user.userInfo.position}}</span>
					</li>
					<li>
						<i class="iconfont uce-watch"></i>
						<span id="loginTime">{{formatTime(user.userInfo.lastLoginTime)}}</span>
					</li>
				</ul>
			</div>
		</div>-->

		<!--消息列表-->
		<!--<div class="messages msg-p" style="display:none;">
		<div id="triangle-facing-left" class="triangle-facing-left" ></div>
		<div class="message-content">
			<ul>
				<li v-for="tip in message.msgs" @click="openMessage(tip)">
					<i class="read-tip" v-bind:class="{'read-tip-off':tip.state==true}"></i>
					<div>
						<span>{{tip.title}}</span>
						<span>{{tip.time}}</span>
					</div>
				</li>
			</ul>
		</div>
		<div class="more-info">查看全部消息</div>
	</div>-->
		<!--消息详情model-->
		<!--<div id="messageDetail" class="easyui-dialog" data-options="closed:true,title:'消息详情'"
	style=" width:500px;height:300px;padding:5px 15px;display:none;" v-cloak>
		<div class="msgdtl">{{message.msgdetl.content}}</div>
	</div>-->
		<div id="department" class="easyui-dialog" data-options="closed:true,modal:true,title:'请选择部门'" style=" width:200px;height:300px;padding:5px 15px;display:none;" v-cloak>
			<div class="department-dlg" style="border:none;">
				<ul style="padding:0;">
					<li v-for="(item, index) in user.userInfo.orgRefRel">
						<input type="radio" :id="'org'+index" :value="item.orgId" :disabled="item.otherEmpId == null" v-model="user.choseOrg">
						<label :for="'org'+index">{{item.orgName+(item.otherEmpId == null ? '(未绑定)' : '')}}</label>
					</li>
				</ul>
				<button class="department-button" @click="changeOrg()">确定</button>
			</div>
		</div>

		<!--功能设置-->
		<div class="setting">
			<!--<div class="setting-themes">
				<span class="themes-title"><i class="iconfont uce-themes" style="font-size:24px"></i></span>
				<ul>
					<li class="themes-choice default" @click="settingThemes('default')">
						<i v-if="settings.themes == 'default'" class="iconfont uce-success-circle"></i>
					</li>
					<li class="themes-choice blue" @click="settingThemes('blue')">
						<i v-if="settings.themes == 'blue'" class="iconfont uce-success-circle"></i>
					</li>
					<li class="themes-choice black" @click="settingThemes('black')">
						<i v-if="settings.themes == 'black'" class="iconfont uce-success-circle"></i>
					</li>
				</ul>
			</div>-->
			<div>
				<a class="setPass" @click="$('#dlgUpdatePwd').dialog('open');"><i class="iconfont uce-password"></i><span>密码修改</span></a>
			</div>
		</div>

	</div>

	<!--右键菜单-->
	<div id="mm" class="easyui-menu" style="width:120px;display:none;">
		<div data-options="iconCls:'iconfont uce-cancel'" id="closecur">
			关闭
		</div>
		<div data-options="iconCls:'iconfont uce-cancel'" id="closeall">
			关闭全部
		</div>
		<div data-options="iconCls:'iconfont uce-cancel'" id="closeother">
			关闭其他
		</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'iconfont uce-cancel'" id="closeleft">
			关闭左侧标签
		</div>
		<div data-options="iconCls:'iconfont uce-cancel'" id="closeright">
			关闭右侧标签
		</div>
	</div>
	
	<!--修改密码-->
	<div id="dlgUpdatePwd" class="easyui-dialog" style="padding:10px 20px;width:380px;height:280px" data-options="title:'修改密码',border:false,closed:true,modal:true,buttons:'#divUpdatePwdBtn',onBeforeOpen:function(){$('#formUpdatePwd').form('clear')}">
		<span style="display:none" id="updatePwdSpan">
	        <form id="formUpdatePwd" method="post" >
	        	<table class="table" style="width: 100%; border:0px;">
					<tbody>
						<tr style="padding-left: 20px">
							<td width="30%">旧密码:</td>
							<td width="70%">
								<input id="oldPassword" class="easyui-passwordbox" required="true" iconWidth="24" style="width:100%;height:40px" data-options="prompt:'请输入原密码..'">
								<!-- <input id="oldPasswordHidden" name="oldPassword" type="hidden"> -->
							</td>
						</tr>
						<tr>
							<td>新密码:</td>
							<td>
								<!-- 2017-12-18 修改密码强度校验规则 -->
								<input id="newPassword"  class="easyui-passwordbox" required="true" iconWidth="24" style="width:100%;height:40px" data-options="prompt:'请输入新密码..',validType:['consecutiveRepeatCharacterRegex','consecutiveCharacterRegex','passwordRule']">
								<!-- <input id="newPasswordHidden" name="newPassword" type="hidden"> -->
								<!-- 2017-12-18 修改密码强度校验规则 -->
							</td>
						</tr>
						<tr>
							<td>确认密码:</td>
							<td>
								<input class="easyui-passwordbox" required="true" iconWidth="24" style="width:100%;height:40px" data-options="prompt:'请再次输入新密码..'" validType="equals['#newPassword']">
							</td>
						</tr>
					</tbody>
				</table>
	        </form>
		</span>
    </div>
    <div id="divUpdatePwdBtn" style="display:none;">
        <a href="javascript:void(0)" class="easyui-linkbutton save" onclick="vm.updatePwd()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('dlgUpdatePwd')" style="width:90px">取消</a>
    </div>

	<!------------------业务逻辑------------------------>
	<script type="text/javascript">
		var domain = window.location.protocol + "//" + window.location.host;
		//关闭消息窗口
		var closeMessage = function() {
			$(".messages").slideUp();
			//$(".userdtl").hide();
		}
		

		//菜单组件
		Vue.component('items', {
			props: ['model','type'],
			template: '#item-template',
			computed: {
				nodeType: function(){
					return this.model.state
				},
				id: function() {
					return this.model.id
				},
				title: function() {
					return this.model.text
				},
				systemCode:function() {
					return this.model.systemCode
				},
				href: function() {
					var localObj = window.location;
					var contextPath = localObj.pathname.split("/")[1];
					var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;
					if(this.model.attributes != null) {
						if(this.model.attributes.indexOf("sysurl") == 0) {
							this.model.attributes = this.model.attributes.replace(/sysurl/, basePath);
						}
					};
					return this.model.attributes;
				},
				iconClass: function() {
					if(this.model.children != null) {
						if(this.model.permissionIcon != null && this.model.permissionIcon.replace(/(^\s*)|(\s*$)/g, "") != "") {
							return this.model.permissionIcon;
						} else {
							return 'uce-file';
						}
					} else {
						return 'uce-thirdfile';
					}
				}

			},
			methods: {
				select: function(ev) {
					ev.stopPropagation();
					if(this.model.children != null) {
						return;
					}
					vm.openTab(this.title, this.href, true, this.id,this.systemCode,false)
				}
			}
		})

		//消息列表
		var messages = [{
				"title": "东方时代防水方式方式单身的1",
				"time": "2017-05-06",
				"state": true,
				"content": "contentcontentcontentcontentcontent"
			},
			{
				"title": "东方时代防水方式方式单身的2",
				"time": "2017-05-06",
				"state": false,
				"content": "contentcontentcontentcontentcontent"
			},
			{
				"title": "东方时代防水方式方式单身的3",
				"time": "2017-05-06",
				"state": true,
				"content": "contentcontentcontentcontentcontent"
			},
			{
				"title": "东方时代防水方式方式单身的4",
				"time": "2017-05-06",
				"state": false,
				"content": "contentcontentcontentcontentcontent"
			},
		]
		var settings = localStorage.settings;
		
		/*定时锁屏*/
		var count = 0;
        var outTime=30;//分钟
        var lock = false;//是否锁屏
     	var x, y;
        document.onmousemove = function (event) {
            var x1 = event.clientX;
            var y1 = event.clientY;
            if (x != x1 || y != y1) {
                count = 0;
            }
            x = x1;
            y = y1;
        };
        document.onkeydown = function () {
            count = 0;
        };
        /*定时锁屏*/

		//实例化vue
		var vm = new Vue({
			el: "#vueScope",
			data: {
				user: {
					empNameShow:"",
					userInfo: {
						orgRefRel: {}
					}, //用户信息
					permissionCode:[],
					choseOrg: "" //选择的部门
				},
				dataMenu: [], //左侧菜单数据
				choiseId:-1,//选中的菜单id
				columnFlage:false,//开关状态
				nextPermissionMenu:[],
				portalInfo: [],
				codeRefMenu:{},
				tabIndexRefTitle:{},
				message: {
					"msgs": messages, //消息集合
					"msgdetl": {} //单条消息详情
				},
				tretabs: {}, //打开的tabs
				settings: settings ? JSON.parse(settings) : {
					type: false,
					themes: "default"
				},
				billCode: "",
				config: {
					searchType: false
				},
				menuType:"ALL",
				showPrise:false
			},
			mounted: function() { //#el挂在后执行
				var vueThis = this;
				/*定时锁屏*/
				/* if(localStorage.lock=='true'){
					vueThis.lockScreen();
				}
				window.setInterval(function(){
					count++;
		            if (count == outTime*60 && lock==false) {
		            	localStorage.setItem('lock','true');
		              	vueThis.lockScreen(); 	
		            }
				}, 1000); */
				/*定时锁屏*/
				vueThis.getMenus();
				vueThis.getUser();
				vueThis.findDictPortalInfo();
				//折叠消息窗口
				$('#message').click(function(event) {
					var e = event || window.event;
					$(".messages").slideDown(500);
					//$(".userdtl").hide();
					$(document).one("click", function() {
						$(".messages").hide();
					});
					/*$(".messages").click(function (event) {
						event.stopPropagation();
					});*/
					e.stopPropagation();
				})
				//用户信息
				/*$("#userdtl").mouseenter(function(event) {
					var offsetLeft = $("#userdtl").offset().left - $("#userdtl")[0].offsetWidth / 2 + 15;
					var e = event || window.event;
					$(".userdtl").css({
						"left": offsetLeft
					})
					$(".userdtl").slideDown(500);
					$(".messages").hide();
					$(".userdtl").mouseleave(function(event) {
						var e = event || window.event;
						$(".userdtl").hide();
						e.stopPropagation();
					})
					$(document).one("click", function() {
						$(".userdtl").hide();
					});
					e.stopPropagation();
				})*/
				//tab切换
				$('#mainTab').tabs({
					onSelect: function(title, index) {
						$("#demo-list li.active").removeClass("active")
						$(".jquery-accordion-menu .submenu a").css("background-color", '')
						$(".jquery-accordion-menu a").css("background-color", '')
						if(vm.tretabs[title] != '000') {
							try {
								$("#" + vm.tretabs[title])[0].childNodes[0].style.background = "rgb(238, 120, 0)";
							} catch(e) {

							}
						} else if(vm.tretabs[title] === '000') {
							//$("#homepage").addClass("active");
						}
					},
					onAdd:function(title,index){
						var id = $(this).tabs('getTab',index).attr('id');
						vueThis.tabIndexRefTitle[id]=title;
					},
					onBeforeClose:function(title,index){
						var id = $(this).tabs('getTab',index).attr('id');
						delete vueThis.tabIndexRefTitle[id];
						delete vueThis.tretabs[title];
					}
				});
				//tab右键菜单
				tabRightMenu('mainTab', 'mm');

				//折叠菜单
				$("#vuedom").mouseenter(function(event){
					event.stopPropagation();
					$('#vuedom').addClass('vuedom-hover'); 
				})
				$('.header, #mainTab').mouseenter(function(event){
					$('#vuedom').removeClass('vuedom-hover'); 
					$('#companyFlag').hide();
					$(".messages").hide();
				})
				
				$(".uce-layout").mousedown(function(event){
					if(vueThis.settings.type){
						$(".setting").removeClass('active');
						vueThis.settings.type = false;
						localStorage.setItem("settings",JSON.stringify(vueThis.settings));
					}else{
						return;
					}
				})
				//运单跟踪搜索框校验规则
				var unValidMsg = "";
				$.extend($.fn.validatebox.defaults.rules, {
				    searchBillCheck: {
						validator: function(value,param){
							var $this = $(this);
							param = param == undefined || param == null || param.length == 0 ? [20] : param;
							if (null == value || value.length == 0) {
								$.fn.validatebox.defaults.rules.searchBillCheck.message = '运单编号不能为空';
								return false;
							} else if (value.substring(0) == "," || value.replace(/\n/g,",").substring(0,1) == ",") {
								$.fn.validatebox.defaults.rules.searchBillCheck.message = '查询条件不能以逗号或换行开头';
								return false;
							} else {
								var reg = /^[A-Za-z0-9\,]+$/;//英文和数字
								value = value.replace(/\n/g,",");
								value = value.replace(new RegExp(",+","gm"),",");
								value = /,$/.test(value) ? value.substring(0, value.length - 1) : value;
								if (!reg.test(value)) {
									$.fn.validatebox.defaults.rules.searchBillCheck.message = '只能输入：字母、数字、逗号';
									return false;
								} else {
									if (value.split(",").length > param[0]) {
										$.fn.validatebox.defaults.rules.searchBillCheck.message = '查询运单编号不能超过' + param[0] + '条';
										return false;
									} else {
										var billArr = value.split(",");
										for (var i = 0; i < billArr.length; i++) {
											if (billArr[i].length > 30) {
												$.fn.validatebox.defaults.rules.searchBillCheck.message = '运单编号长度不能大于30';
												return false;
											}
										}
										return true;
									}
								}
							}
						}
				    }
				});
				
				//运单跟踪搜索框事件
				$('#searchBillInput').textbox({
					validType:'searchBillCheck[20]',
					icons: [{
								iconCls:'iconfont uce-search',
								handler: function(e){
									$('#searchBillInput').textbox({required:true, missingMessage:'运单编号不能为空'});
									$('#searchBillInput').textbox('textbox').mouseover(function(){
										var val = $('#searchBillInput').textbox('getValue');
										if (val == '') {
											//$('#searchBillInput').textbox({required:false});
											//$('#searchBillInput').textbox('textbox').mouseover(arguments.callee);
											var opt = $.data($('#searchBillInput').textbox('textbox')[0],"validatebox");
											if (opt != undefined) opt.message = '';
										}
									});
									if ($('#searchBill').form('enableValidation').form('validate')) {
										//add by zhangRb 20180913 修复鼠标粘贴后无法查询的BUG
										vueThis.billCode = $('#searchBillInput').textbox('getValue');
										vueThis.searchBillFun();
									}
								}
							}
					],
					inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,
							{
								focus: function(){
									this.select();
									$(this).scrollTop(0);
			 					},
			 					/* keyup: function() {
			 						vueThis.billCode = event.target.value;
			 					}, */
			 					blur: function() {
			 						$(this).scrollTop(0);
			 					},
			 					mouseleave: function(){
			 						$(this).scrollTop(0);
			 					}
			     	   		}
			     	   )
				});
			},
			updated: function() { //视图更新后执行

			},
			computed: {
				//计算属性
			},
			methods: {
				opDept : function(){
					var vueThis = this;
					$.ajax({
						url: '../portal/getCurrentUserNew.action',
						data: {},
						success: function(result) {
							var isLoginOut = false;
							if(result.orgRefRel==null||result.orgRefRel.length==0){
								isLoginOut = true;
							}else{
								var orgId= vueThis.user.userInfo.orgId;
								for(var i = 0;i<result.orgRefRel.length;i++){
									isLoginOut = true;
									if(result.orgRefRel[i].orgId==orgId){
										isLoginOut = false;
										break;
									}
								}
							}
							
							if(isLoginOut){
								window.location.href='${pageContext.request.contextPath}/portal/logout.action'; 
							}else{
								vueThis.user.userInfo.orgRefRel = result.orgRefRel;
								$('#department').dialog('open');
							}
							
						}
					});
					
				},
				getUser: function() {
					var vueThis = this;
					//异步加载当前用户信息
					$.ajax({
						url: '../portal/getCurrentUser.action',
						data: {},
						success: function(result) {
							vueThis.user.userInfo = result;
							vueThis.user.choseOrg = result.orgId;
							vueThis.user.empNameShow = result.empName+"(工号："+result.empCode+")";
							//vueThis.user.userInfo.position = "开发工程师";
							
							if (result.cmsOrgType == "30") {
								vueThis.showPrise = true;
							}
						}
					})
					$.ajax({
						url: '../portal/findPermissionCodeByCurrentUser.action',
						data: {},
						success: function(result) {
							vueThis.user.permissionCode = result;
						}
					})
				},
				getMenus: function() {
					var vueThis = this;
					$.ajax({
						url: '../portal/getMenuTree.action',
						data: {},
						async: true,
						success: function(result) {
							var idNodeRef = {};
							var menuData = [];
							if(result != null){
								result.map(function(item){
									idNodeRef[item.id] = item;
									vueThis.codeRefMenu[item.permissionCode] = item;
								})
								result.map(function(item){
									if(item.parentId != null && idNodeRef[item.parentId] != null){
										if(idNodeRef[item.parentId].children == null ){
											idNodeRef[item.parentId].children = [];
										}
										if(!item.isHide){
											idNodeRef[item.parentId].children.push(item);
										}
									}else{
										menuData.push(item);
									}
								})
							}
							vueThis.dataMenu = menuData;
							setTimeout(function() {
								$("#jquery-accordion-menu").jqueryAccordionMenu();
							}, 500)
						}
					})
				},
				openTab: function(title,href,closable,permissionId,systemCode,/**是否刷新*/refresh) {
					var treeid = systemCode + title;//构造iframe的ID
					//打开tab页
					//打开tab页
					var tabTitle = vm.tabIndexRefTitle['idtab'+treeid];
					//var e = $('#mainTab').tabs('exists', title);
					if(tabTitle) {
						$("#mainTab").tabs('select', title);
						var tab = $("#mainTab").tabs('getSelected');
						if(refresh != false){
							document.getElementById('indextab' + treeid).src = href;
						}
					} else {
						if($('#mainTab').tabs('tabs').length > 10) {
							showTips("最多只能打开10个标签页！", "warning", 3000);
							return false;
						}
						//这个判断可能打开的页面不是这个用户的菜单
						if(permissionId){
							vm.tretabs[title] = permissionId;
						}
						/*$('#mainTab').tabs('add', {
							title: title,
							content: '<iframe id="indextab' + treeid + '" scrolling="auto" src="' + href + '" frameborder="0" style="width:100%;height:100%"></iframe>',
							closable: closable
						});*/
						//判断是否跨域
						if(href.indexOf(domain) == 0){
							//没跨域直接打开
							var iframe = '<iframe src="' + href + '" id="indextab' + treeid + '" scrolling="auto" frameborder="0" style="width:100%;height:100%"></iframe>';
							$('#mainTab').tabs('add', {
								id: 'idtab'+treeid,
								title: title,
								content: iframe,
								closable: closable
							});
						} else {
							//跨域实现跳转
							var state = 1;
							var iframe = '<iframe src="${pageContext.request.contextPath}/portal/crossDomain.action" id="indextab' + treeid + '" scrolling="auto" frameborder="0" style="width:100%;height:100%"></iframe>';
							$('#mainTab').tabs('add', {
								id: 'idtab'+treeid,
								title: title,
								content: iframe,
								closable: closable
							});

							var crossDomainParams = {
								currentUser: this.user.userInfo,
								permissionCode: this.user.permissionCode
							};
							document.getElementById('indextab' + treeid).addEventListener("load",
								function() {
									if(state == 1) {
										state = 0;
										document.getElementById('indextab' + treeid).src = href;
										document.getElementById('indextab' + treeid).contentWindow.name = JSON.stringify(crossDomainParams);
									}
								}, true);
						}
					}
				},
				openColumnMenu(item){
					this.choiseId = item.id;
					this.columnFlage = true;
					this.nextPermissionMenu = item.children;
				},
				toggleColumn(){
					this.columnFlage = !this.columnFlage;
					//阻止事件冒泡
					$("#demo-list li").click().mouseleave(function(event){
						event.stopPropagation();
					})
				},
				detailOmg : function() {
					var cmsBaseOrgCode = this.user.userInfo.cmsBaseOrgCode;
					
					$.ajax({
						url:'findPermissionAndSystemCode.action',
						data:{permissionCode:'omg_wangdian_info', systemCode:'OMG'},
						async:true, 
						success:function(result){
							if (result != null && result.sysUrl != null) {
								window.top.vm.openTab("网点信息", result.sysUrl + "/cmsOrg/cmsOrgDetail.do?baseOrgCode=" + cmsBaseOrgCode,true,null);
							} else {
								window.top.vm.openTab("网点信息", window.top.vm.dataMenu[0].attributes + "/cmsOrg/cmsOrgDetail.do?baseOrgCode=" + cmsBaseOrgCode,true,null);
							}
						}
					})
					
				},
				openTabFromIFrame : function(permissionCode,params,attr){
					var menuItem = this.codeRefMenu[permissionCode];
					var urlParams = '';
					var closable = true;
					if(params){
						for(key in params){
							urlParams += key+'='+params[key] + '&';
							if (key == 'closable') {
								closable = params[key];
							}
						}
					}
					if( menuItem != null){
						this.openTab(attr && attr.title ? attr.title : menuItem.text,menuItem.attributes +'?' + urlParams,true,menuItem.id,attr.systemCode,attr && attr.refresh ? attr.refresh : undefined);
					}else{//自定义url
						if(attr && attr.url)
						this.openTab(attr.title,attr.url +'?' + urlParams,closable,null,attr.systemCode,attr && attr.refresh ? attr.refresh : undefined);
					}
				},
				/**
				 * 关闭当前页面
				 */
				 closeTabFromIFrame : function(title) {
					if(title!= undefined && title!= null && title != ''){
						$('#mainTab').tabs('close',title);
					} else {
						var tab = $('#mainTab').tabs('getSelected');
						var index = $('#mainTab').tabs('getTabIndex',tab);
						$('#mainTab').tabs('close',index);
					}
				},
				changeOrg: function(){
					$('#department').dialog('close');
					if(this.user.choseOrg!=vm.user.userInfo.orgId){
						$.ajax({
							url:'${pageContext.request.contextPath}/portal/changeOrg.action',
							data:{orgId:this.user.choseOrg,operateFlag:'CHANGE_ORG'},
							success: function(result){
								window.location.href="${pageContext.request.contextPath}/portal/forward.action";
							}
						})
					}
				},
				updatePwd: function(){
					/* $("#newPasswordHidden").val(hex_md5($("#newPassword").val()));
					$("#oldPasswordHidden").val(hex_md5($("#oldPassword").val())); */
					$('#formUpdatePwd').form('submit',{
						url : '${pageContext.request.contextPath}/portal/updPwd.action',
						onSubmit : function(params) {
							if($(this).form('validate')){
								params.newPassword = hex_md5($("#newPassword").val());
								params.oldPassword = hex_md5($("#oldPassword").val());
								params.passwordStrength = 'STRONG';
								return true;
							}
							return false;		
						},
						success : function(result) {
							updPwdFlag="false";
							var json = eval('('+ result +')')
							if (json.errorCode != "success") {
								showErrorMsg(json.errorMsg);
			            	} else {
			            		closeDialog("dlgUpdatePwd");
			            		confirmMsg('密码修改成功,将跳转到登陆界面重新登陆！',function(){
			            			//跳转到登陆界面重新登陆
									window.location.href='${pageContext.request.contextPath}/portal/logout.action'; 
			            		});
			            	}
						}
					});
				
				},
				logout : function(){
					confirmMsg("您将退出系统..",function(){
						window.location.href='${pageContext.request.contextPath}/portal/logout.action'; 
					})
				},
				openMessage: function(news) {
					//打开消息详情
					this.message.msgdetl = news;
					$('#messageDetail').dialog('options').title = news.title;
					$('#messageDetail').dialog({
						modal: true
					}).dialog('open');
				},
				openSetting: function(event) {
					//折叠设置窗口
					var e = event || window.event;
					e.stopPropagation()
					//$(".setting").toggle(!this.settings.type);
					if(this.settings.type) {
						$(".setting").removeClass('active');
					} else {
						$(".setting").addClass('active');
					}
					this.settings.type = !this.settings.type;
					localStorage.setItem("settings", JSON.stringify(this.settings));
				},
				settingThemes: function(val) {
					//皮肤设置
					this.settings.themes = val;
					for(var i = 0; i < frames.length; i++) {
						frames[i].postMessage({fn:'settingThemes',params:{theme:val}},'*');
						/*var cssStyleDom = frames[i].frameElement.contentDocument.getElementById('themes-setting');
						if(cssStyleDom == null) {
							var css = document.createElement('style');
							css.type = 'text/css';
							css.id = "themes-setting";
							if(val == 'default') {
								css.innerHTML = ".iconfont{color:#ff9372;}";
							} else if(val == 'blue') {
								css.innerHTML = ".iconfont{color:#23b7e5;}";
							} else {
								css.innerHTML = ".iconfont{color:#464c5b;}";
							}
							frames[i].frameElement.contentDocument.head.appendChild(css);
						} else {
							if(val == 'default') {
								cssStyleDom.innerHTML = ".iconfont{color:#ff9372;}";
							} else if(val == 'blue') {
								cssStyleDom.innerHTML = ".iconfont{color:#23b7e5;}";
							} else {
								cssStyleDom.innerHTML = ".iconfont{color:#464c5b;}";
							}
						}*/
					}
				},
				changeMenuType(val){
					setTimeout(function(){
						$("#jquery-accordion-menu").jqueryAccordionMenu();
					},200)
				},
				searchBillFun: function() {
					var vueThis = this;
					var billCodeValue = vueThis.billCode;
					if (null == billCodeValue || billCodeValue.length == 0) {
						return;
					} else {
						billCodeValue = billCodeValue.replace(/\n/g,",");
						billCodeValue = billCodeValue.replace(new RegExp(",+","gm"),",");
						var firstBill = billCodeValue.indexOf(",");
						var info = window.top.vm.portalInfo;
						var billUrl;
						for (var i = 0; i < info.length; i++) {
							if (info[i].typeName == "PORTAL_URL") {
								billUrl = info[i].typeCode + "/bill/queryQkBill.do?billCodes=" + billCodeValue;
								break;
							}
						}
						var viewBill = billCodeValue;
						if (firstBill != -1) {
							viewBill = billCodeValue.substring(0, firstBill);
						}
						if (viewBill.length > 15) {
							viewBill = viewBill.substring(0, 15)+"...";
						}
						window.top.vm.openTab("运单查询-" + viewBill, billUrl,true,null);
					}
				},
				findDictPortalInfo: function() {
					var vueThis = this;
					$.ajax({
						url: '${pageContext.request.contextPath}/portal/getDictDataByTypeClassCode.action',
						data: {typeClassCode: 'PORTAL_INFO'},
							success: function(result){
								vueThis.portalInfo = result;
								for (var i = 0; i < result.length; i++) {
									if (result[i].typeName == "SEARCH_BILL_SWITCH" && result[i].typeCode == "SEARCH_BILL_OPEN") {
										$("#searchBill").show();
										break;
									}
								}
							}
					})
				}
			},
			directives: {},
			watch: {
				'settings': function(newvalue, oldvalue) {
					var settings = JSON.stringify(newvalue);
					localStorage.setItem("settings", settings);
				},
				'settings.themes': function(newvalue, oldvalue) {
					//皮肤设置
					var settings = JSON.stringify(this.settings);
					localStorage.setItem("settings", settings);
				}
			}
		})
		/**
		 * 鼠标放上去后变成黄色图片【右上角OMG的网点信息查询】
		 */
		function mover() {
			document.getElementById("detailOmg").src="${pageContext.request.contextPath}/images/portal/detail-yellow.png";
		}
		/**
		 * 鼠标放上去后变成白色图片【右上角OMG的网点信息查询】
		 */
		function mout() {
			document.getElementById("detailOmg").src="${pageContext.request.contextPath}/images/portal/detail-white.png";
		}
		/**
		 * 鼠标放上去后底部版权div显示
		 */
		function moverCompanyFlag() {
			$('#companyFlag').show();
		}
		/**
		 * 鼠标离开后底部版权div隐藏
		 */
		function moutCompanyFlag() {
			$('#companyFlag').hide();
		}
		
		//初始化打开首页
		vm.openTab("首页", domain+'${pageContext.request.contextPath}/portal/home.action', false, 0,'SSO',false);
		$("#updatePwdSpan").show();
		/**
		 * 监听跨域iframe传递的消息
		 */
		window.addEventListener('message', function(e) {
			var msg = e.data;
			switch(msg.fn) {
				case 'click':
					if(vm.settings.type){
						vm.openSetting();
					}
					break;
				case 'openTab':
					this.vm.openTabFromIFrame(msg.permissionCode,msg.params,msg.attr);
					break;
				case 'closeTab':
					this.vm.closeTabFromIFrame(msg.title);
					break;
				default:
					break;
			}

		})
		
		var length = 0;
		var count = 0;
		var refresh = false;
		$.ajax({
			url: '${pageContext.request.contextPath}/portal/getDictDataByTypeClassCode.action',
			data: {typeClassCode: 'SESSION_HEART_REGISTER'},
			success: function(result){
				if(result){
					length = result.length;
					window.setInterval(function(){
						result.map(function(item){
							$.ajax({ 
						        url: item.typeCode + "/login/getSession.do", // 跨域URL 
						        dataType: 'jsonp', 
						        complete : function(){
						        	count ++;
						        },
						        success: function (session) { 
						        	if(result){
						        		if(session && session.attributeKeys && session.attributeKeys.length >0){
						        			refresh = true;
						        		}
						        	}
						        }
						    });
						})
					}, 900000);
					window.setInterval(function(){
						if(length != 0 && count == length){
					   	  	if(refresh){
						   		$.ajax({
					        		async:false,
									url : '${pageContext.request.contextPath}/portal/refreshToken.action',
									success : function(data){
										count = 0;
										refresh=false;
									}
								})
						   	}
					   	}
					},300000)
				}
			}
		})
	</script>
</body>
</html>