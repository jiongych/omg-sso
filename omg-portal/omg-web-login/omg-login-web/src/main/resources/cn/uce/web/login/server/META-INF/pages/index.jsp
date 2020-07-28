<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">    
<html xmlns="http://www.w3.org/1999/xhtml">   
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>优速快递</title>
    <%@include file="../common/common-base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/common/jquery-accordion-menu.css">
   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/common/common-index.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/encryptionAlgorithm.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/jquery-accordion-menu.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/vue-2.3.min.js"></script>
</head>

<body>
	<!--菜单模板-->
	<script type="text/x-template" id="leftmenu">
		<li @click="select()" v-bind:id="id">
			<a href="#"><i class="iconfont" :class="iconClass"></i>{{model.text}} </a>
			<ul class="submenu" v-if="model.children">
				<leftmenu v-for="child in model.children" v-bind:model="child" ></leftmenu>
			</ul>
		</li>
	</script>
	
	<script type="text/x-template" id="treeboxmenu">
		
		<li :id="data.id" :class="{'check':data.children==null}">
			<a v-if="data.children==null">
				<input type="checkbox" :id="'box'+data.id" v-model="data.checked" :value="data.id" @change="checked(data)"/>
				<label :for="'box'+data.id">{{data.text}}</label>
			</a>
			
			<a :id="'floor'+data.id" @click="toggleMenu()" class="list-title" v-if="data.children!=null">
				<i class="iconfont" :class="{'uce-plus':!open,'uce-minus':open}"></i>
				{{data.text}}
			</a>
			<ul :class="'floor'+data.id" class="submenu" v-if="data.children!=null && open==true">
				<treeboxmenu v-for="item in data.children" :data="item" :opentype="false"></treeboxmenu>
			</ul>
			
		</li>
	</script>
	<!--菜单模板-->
	
	<div id="vueScope" :class="settings.themes" v-cloak>
		<!--header-->
		<header class="header">
			<div  class="left">
				<img alt="logo" src="${applicationScope.STATIC_SERVERADDRESS}/images/uc56_logo.png" style="width:70px;height:46px;padding: 0 0 0 10px;">
				<div class="title">
				${applicationScope.SYSTEM_NAME}
				</div>
			</div>
			<div class="right">
				<!-- 如果有加按钮，需要手动调整 padding  -->
				<a id="userdtl"><i class="iconfont uce-people"></i><span id="empName">{{user.userInfo.empName}}</span><!-- <i class="iconfont uce-triangledown"></i> --></a>|
				<a @click="changeOrg(1)"><i class="iconfont uce-qiehuan"></i><span id="orgName">{{user.userInfo.orgName}}</span></a>|
				<!--<a id="message"><i class="iconfont uce-remind-circle"></i><span class="span-tip">99</span><span>消息</span></a>| -->
				<a @click="openPwdlg()"><i class="iconfont uce-password"></i><span>密码修改</span></a>|
				<a @click="openSetting($event)" id="setting"><i class="iconfont uce-set"></i><span>设置</span></a>|
				<a @click="logout()" ><i class="iconfont uce-exit"></i><span>退出</span></a>
			</div>
		</header>
		
		<article class="uce-layout" >
			<div id="content" class="easyui-layout" style="width:100%;height:100%;">
				<!--菜单-->
				<div id="caidan" data-options="region:'west',split:true,border:true" style="width:210px;background:#ebeff3">
					<div id="vuedom">
						<div class="jquery-accordion-menu red" style="min-width:100%;width:100%;" v-cloak>
							<ul id="demo-list">
								<li id="homepage" class="" @click="openTab('首页','${pageContext.request.contextPath}/login/home.do',false,0)">
									<a href="#"><i class="iconfont uce-home"></i>首页</a>
									<i class="iconfont uce-turnleft menu-switch"></i>
								</li>
								<li>
									<a href="#" @click="toggleRapidMenus()"><i class="iconfont uce-collect"></i>常用菜单</a>
									<i class="iconfont uce-liebiao menu-switch" @click="$('#shortcutMenu').dialog({modal:true}).dialog('open');"></i>
									<ul class="submenu" id="bbs">
										<li v-if="fastMenu.length==0" @click="$('#shortcutMenu').dialog({modal:true}).dialog('open');"><button class="add-menu"><i class="iconfont uce-add"></i>添加菜单</button></li>
										<li v-for="item in fastMenu" @click="openTab(item.text,item.attributes,true,item.id)"><a href="#"><i class="iconfont uce-thirdfile"></i>{{item.text}}</a></li>
									</ul>
								</li>
							</ul>
						</div>
						<div id="jquery-accordion-menu" class="jquery-accordion-menu red" style="min-width:100%;width:100%;" v-cloak>
							<ul id="demo-list">
								<leftmenu v-for="item in leftMenu" :model="item"></leftmenu>
							</ul>
						</div>
					</div>

				</div> 
				<!--主体内容-->
				<div data-options="region:'center',border:false" style="width:100%!important;">
					<div id="mainTab" class="easyui-tabs" fit="true"></div>
				</div> 
			</div>
		</article>
		
		<!--用户信息-->
		<!-- <div class="userdtl" style="display:none;">
			<div id="triangle-facing-left" class="triangle-facing-left" ></div>
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
		</div> -->
		
		<!--消息列表-->
		<div class="messages msg-p" style="display:none;">
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
		</div>
		<!--消息详情model-->
		<div id="messageDetail" class="easyui-dialog" data-options="closed:true,title:'消息详情'"
		style=" width:500px;height:300px;padding:5px 15px;display:none;" v-cloak>
			<div class="msgdtl">{{message.msgdetl.content}}</div>
		</div>
		
		<!--切换部门model-->
		<div id="department" class="easyui-dialog" data-options="closed:true,title:'请选择部门'"
		style=" width:200px;height:300px;padding:5px 15px;display:none;" v-cloak>
			<div class="menu-dlg-left" style="border:none;">
				<ul style="padding:0;">
					<li v-for="(item, index) in user.userInfo.orgRefRel">
						<input type="radio" :id="'org'+index" :value="item.orgId" v-model="user.changeOrg">
						<label :for="'org'+index" >{{item.orgName}}</label>
					</li>
				</ul>
				<button class="changeOrg" @click="changeOrg(2)">确定</button>
			</div>
		</div>
		
		<!--功能设置-->
		<div class="setting">
			<!--<button @click="openSetting($event)" class="iconfont uce-set setting-btn"></button>-->
			<div class="setting-themes">
				<span class="themes-title"><i class="iconfont uce-themes" style="font-size:24px"></i></span>
				<ul>
					<li class="themes-choice default" @click="settingThemes('default',$event)">
						<i v-if="settings.themes == 'default'" class="iconfont uce-success-circle"></i>
					</li>
					<li class="themes-choice blue" @click="settingThemes('blue',$event)">
						<i v-if="settings.themes == 'blue'" class="iconfont uce-success-circle"></i>
					</li>
					<li class="themes-choice black" @click="settingThemes('black',$event)">
						<i v-if="settings.themes == 'black'" class="iconfont uce-success-circle"></i>
					</li>
				</ul>
			</div>
		</div>
		
		<!--快捷菜单设置-->

		<div id="shortcutMenu" class="easyui-dialog" data-options="closed:true,title:'常用菜单设置',constrain:true"
		style=" width:700px;height:500px;padding:5px 15px;display:none;" buttons="#saveBtn" v-cloak>
			<div class="flex-style menu-dlg">
				<div class="treeboxmenu">
					
					<ul class="xza-menulist" style="border:none;">
						<treeboxmenu v-for="item in rapidMenus.lefts" :data="item" ></treeboxmenu>
					</ul>
					
				</div>
				<div class="menu-dlg-center">
					<div>
						<span @click="getAddRemove('allin')">全部移入<i class="iconfont uce-turnright"></i></span>
						<span @click="getAddRemove('in')">移入<i class="iconfont uce-turnright"></i></span>
						<span @click="getAddRemove('out')"><i class="iconfont uce-turnleft"></i>移出</span>
						<span @click="getAddRemove('allout')"><i class="iconfont uce-turnleft"></i>全部移出</span>
						<!--<span @click="saveMenu()" class="success"><i class="iconfont uce-success-circle"></i>保存</span>-->
					</div>
				</div>
				<div class="menu-dlg-right">
					<div class="selected">快捷菜单</div>
					<ul>
						<li v-for="(item, index) in rapidMenus.rights">
							<input type="checkbox" :id="'K'+index" :value="item.id" v-model="rapidMenus.rightChecked">
							<label :for="'K'+index" >{{item.text}}</label>
							<span class="iconfont uce-upstairs move" :id="'up'+item.id" @click="sortMenu('up',item.id)"></span>
							<span class="iconfont uce-downstairs move" :id="'down'+item.id" @click="sortMenu('down',item.id)"></span>
						</li>
					</ul>
					<div class="selected"><input type="checkbox" id="checkAll" v-model="rapidMenus.rightAllChecked"/><label>已选: {{ rapidMenus.rightChecked.length }}/{{rapidMenus.rights.length}}</label></div>
				</div>
			</div>
		</div>
		<div id="saveBtn" style="text-align:center;display:none">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-success-circle"  @click="saveMenu()" style="width:90px">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-cancel" onclick="javascript:closeDialog('shortcutMenu')" style="width:90px">取消</a>
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
		<div  data-options="iconCls:'iconfont uce-cancel'"id="closeother">
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
	<!--右键菜单-->
	<!--修改密码-->
	<div id="dlgUpdatePwd" class="easyui-dialog" style="width:380px;height:280px; padding:10px 20px;display:none;" closed="true"  buttons="#divUpdatePwdBtn">
        <form id="formUpdatePwd" method="post" >
        	<table class="table" style="width: 100%; border:0px;">
				<tbody>
					<tr style="padding-left: 20px">
						<td width="30%">旧密码:</td>
						<td width="70%">
							<input id="oldPassword"  class="easyui-passwordbox" required="true" iconWidth="24" style="width:100%;height:40px" data-options="prompt:'请输入原密码..'">
							<input id="oldPasswordHidden" name="oldPassword" type="hidden">
						</td>
					</tr>
					<tr>
						<td>新密码:</td>
						<td>
							<!-- 2017-12-18 修改密码强度校验规则 -->
							<input id="newPassword" class="easyui-passwordbox" required="true" iconWidth="24" style="width:100%;height:40px" data-options="prompt:'请输入新密码..',validType:['consecutiveRepeatCharacterRegex','consecutiveCharacterRegex','passwordRule']">
							<input id="newPasswordHidden" name="newPassword" type="hidden">
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
			<input id="passwordStrength" name="passwordStrength" type="hidden" value="STRONG"/>
        </form>
    </div>
    <div id="divUpdatePwdBtn" style="display:none;">
        <a href="javascript:void(0)" class="easyui-linkbutton save" onclick="vm.savePassword()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('dlgUpdatePwd')" style="width:90px">取消</a>
    </div>

	<script type="text/javascript">
		var updPwdFlag="${sessionScope.updPwdFlag}";
		var updPwdIntervalDay="${sessionScope.updPwdIntervalDay}";
		
		document.title = "${applicationScope.SYSTEM_NAME}";
		//var loginUser="";//用户信息-用于iframe调用
		//var permissionCode="";//权限 -用于iframe调用
		//关闭消息窗口
		var closeMessage = function(){
			$(".messages").slideUp();
			$(".userdtl").hide();
		}
		
		//vue树层级节点全局模板
		Vue.component('leftmenu', {
			props: ['model'],
			template: '#leftmenu',
			computed: {
				id: function () {
				  return this.model.id
				},
				title: function () {
					return this.model.text
				},
				href : function () {
					return this.model.attributes;
				},
				iconClass: function(){
					if(this.model.children!=null){
						if(this.model.permissionIcon != null && this.model.permissionIcon.replace(/(^\s*)|(\s*$)/g, "") != ""){
							return this.model.permissionIcon;
						}else{
							return 'uce-file';
						}
					}else{
						return 'uce-thirdfiles';
					}
				}
				
			},
			methods: {
				select: function(){
					//console.log(vm.tretabs)
					if(this.model.children!=null){
						return;
					}
					vm.openTab(this.title,this.href,true,this.id)
				}
			}
		});
		
		/*带CheckBox菜单*/
		Vue.component('treeboxmenu', {
			props: {
				data:{
					type:Array,
					required: true,
					default:[]
				},
				opentype:{
					type:Boolean,
					default:true
				},
				style:{
					type:Object,
					required:false,
					default:{}
				}
			},
			template: '#treeboxmenu',
			data:function(){
				return {
					selected:[],
					selectDatas:[]
				}
			},
			computed: {
				id: function () {
				  return this.model.id
				},
				title: function () {
					return this.model.text
				},
				open:function(){
					return this.opentype
					//计算属性依赖于原始属性，想修改计算属性可修改原始属性或者通过计算属性的set方法赋值
				}
			},
			methods: {
				checked: function(item){
					this.$root.getChecked(item);//直接触发父节点函数
					//this.$emit('myselffn',item);//组件嵌套2次以上无法将事件传递至最外层
					
				},
				toggleMenu:function(){
					this.opentype = !this.opentype;
				}
			},
			watch:{}
		})
		

		
		//消息列表
		var messages = [
			{"title":"东方时代防水方式方式单身的1","time":"2017-05-06","state":true,"content":"contentcontentcontentcontentcontent"},
			{"title":"东方时代防水方式方式单身的2","time":"2017-05-06","state":false,"content":"contentcontentcontentcontentcontent"},
			{"title":"东方时代防水方式方式单身的3","time":"2017-05-06","state":true,"content":"contentcontentcontentcontentcontent"},
			{"title":"东方时代防水方式方式单身的4","time":"2017-05-06","state":false,"content":"contentcontentcontentcontentcontent"},
		]

		var settings = localStorage.settings;
		var oldUserMenu = [];
		var oldArrayCheck =[];
		//实例化vue
		var vm = new Vue({
			el: "#vueScope",
			data: {
				user:{
					userInfo:{
						orgRefRel:{}
					},//用户信息
					changeOrg:[],//选择的部门
					Orgs:[]//部门列表
				},
				baseData:[],//全局基础数据
				fastMenu:[],//用于展示快捷菜单
				leftMenu:[],//左侧菜单数据
				codeRefMenu:{},
				message:{
					"msgs":messages,//消息集合
					"msgdetl":{}//单条消息详情
				},
				tretabs:{},//打开的tabs
				onThisMenuid:null,//当前menuid
				settings:settings?JSON.parse(settings):{
					type:false,
					themes:"default"
				},
				checked:[],
				rapidMenus:{//常用菜单设置
					"type":false,
					"leftChecked":[],//左侧已选择项id集合
					"rightChecked":[],//右侧已选择项id集合
					"leftAllChecked":false,//左侧全选状态
					"rightAllChecked":false,//右侧全选状态
					"lefts":[],//左侧数据
					"rights":[],//右侧数据
					"keyWord":""//搜索关键词
				}
			},
			mounted:function(){//#el挂在后执行
				var vueThis = this;
				
				vueThis.getMenus();
				vueThis.getUser();
				
				//tab切换
				$('#mainTab').tabs({
					onSelect:function(title,index){
						$("#demo-list li.active").removeClass("active")
						$(".jquery-accordion-menu .submenu a").css("background-color",'')
						$(".jquery-accordion-menu a").css("background-color",'')
						if(vm.tretabs[title]!='0'){
							$("#"+vm.tretabs[title])[0].childNodes[0].style.background = "rgba(200, 200, 200, 0.50)";
						}else if(vm.tretabs[title] === '0'){
							//$("#homepage").addClass("active");
						}
						vueThis.onThisMenuid = vueThis.tretabs[title];
					}
				});
				//tab右键菜单
				tabRightMenu('mainTab','mm');
				
				//折叠菜单
				$(".uce-turnleft.menu-switch").click(function(event){
					var e =event||window.event;
					e.stopPropagation();
					$('#content').layout('collapse','west'); 
					
					setTimeout(function(){
						$('#mainTab').tabs('getSelected').resize();
					},1000);
				});
				
				$("body").click(function(event){
					if(vueThis.settings.type){
						$(".setting").removeClass('active');
						vueThis.settings.type = !vueThis.settings.type;
						localStorage.setItem("settings",JSON.stringify(vueThis.settings));
					}
				});
			},
			updated:function(){//视图更新后执行
				var vueThis = this;
				
				/*
				//折叠消息窗口
				$('#message').click(function(event){
					var e =event||window.event;
					$(".messages").slideDown(500);
					$(".userdtl").hide();
					$(document).one("click", function () {
						$(".messages").hide();
					});
					//$(".messages").click(function (event) {
						//event.stopPropagation();
					//});
					e.stopPropagation();
				})
				//用户信息
				$("#userdtl").mouseenter(function(event){
					var offsetLeft = $("#userdtl").offset().left - $("#userdtl")[0].offsetWidth/2 + 15;
					var e =event||window.event;
					$(".userdtl").css({"left":offsetLeft})
					$(".userdtl").slideDown(500);
					$(".messages").hide();
					$(".userdtl").mouseleave(function(event){
						var e =event||window.event;
						$(".userdtl").hide();
						e.stopPropagation();
					})
					$(document).one("click", function () {
						$(".userdtl").hide();
					});
					e.stopPropagation();
				})*/
			},
			computed:{
				//计算属性
			},
			methods: {
				getUser:function(){
					var vueThis = this;
					//异步加载当前用户信息
					$.ajax({
						url:'${pageContext.request.contextPath}/login/getCurrentUser.do',
						data:{},
						success:function(result){
							vueThis.user.userInfo = result;
							vueThis.user.userInfo.position = "开发工程师";
						}
					})
					//异步加载权限码
					$.ajax({
						url:'${pageContext.request.contextPath}/login/findPermissionCodeByCurrentUser.do',
						data:{},
						success:function(result){
							vueThis.user.permissionCode=result;
						}
					})
				},
				getMenus:function(){
					var vueThis = this;
					$.ajax({
						url:'${pageContext.request.contextPath}/login/getMenuTree.do',
						data:{},
						async:true, 
						success:function(result){
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
										idNodeRef[item.parentId].children.push(item);
									}else{
										menuData.push(item);
									}
								})
							}
							vueThis.leftMenu = menuData;
							setTimeout(function(){
								$("#jquery-accordion-menu").jqueryAccordionMenu();
							},500)
						
							vueThis.getUserMenus();
						}
					})
				},
				getUserMenus:function(){
					var vueThis = this;
					var menudatasLeft = [];
					var menudatasRight = [];
					$.ajax({
						url:'${pageContext.request.contextPath}/menu/getUserMenu.do',
						data:{},
						async:true, 
						success:function(result){
							oldUserMenu = result;
							//数组指向同一Array对象，将导致数据异常
							var newArrays = JSON.parse(JSON.stringify(vueThis.baseData));
							function eachArray(Arrays,key){
								for (var i = 0; i < Arrays.length; i++) {
									if(Arrays[i].children==null){
										if(Arrays[i].id == key){
											menudatasRight.push(Arrays[i]);
											Arrays.splice(i,1)
										}
									}else{
										eachArray(Arrays[i].children,key)
									}
									menudatasLeft = Arrays;
								}
							}
							if(result.length>0){
								for(var i =0;i<result.length;i++){
									var key = result[i];
									eachArray(newArrays.slice(0),key)
								}
								
								vueThis.rapidMenus.lefts = menudatasLeft;
								vueThis.rapidMenus.rights = menudatasRight;	
							}else{
								vueThis.rapidMenus.lefts = newArrays;
								vueThis.rapidMenus.rights = [];	
							}
							vueThis.fastMenu = menudatasRight.slice();
						}
					})
				},
				getChecked:function(item){
					var val = item.id;
					if(item.checked){
						if(this.checked.length>0){
							if(this.checked.indexOf(val) == -1){
								this.checked.push(val);
							}

						}else{
							this.checked.push(val);
						}
					}else{
						if(this.checked.length>0){
							if(this.checked.indexOf(val) != -1){
								this.checked.splice(this.checked.indexOf(val),1);
							}
							
						}
					}
				},
				getAddRemove: function(way) {
					var vueThis = this;
					var addIn = vueThis.checked;
					var cancelOut = vueThis.rapidMenus.rightChecked;
					/*所有的末级菜单集合*/
					var allChildList = [];
						function eachArray(Arrays){
							for (var i = 0; i < Arrays.length; i++) {
								if(Arrays[i].children==null){
									allChildList.push(Arrays[i])
								}else{
									eachArray(Arrays[i].children)
								}
							}
						}
						//根据基础数据筛选
						eachArray(vueThis.baseData);
					/*所有的末级菜单集合*/
					
					if(way =='in'){
						if(addIn.length === 0 ){
							return;
						}
						
						for(var i=0;i<addIn.length;i++){
							var id = addIn[i];
							
							allChildList.forEach(function(item,index){
								if(item.id == id){
									if(vueThis.rapidMenus.rights.indexOf(item) == -1){
										vueThis.rapidMenus.rights.push(item);
									}
								}
							})
						}
						
					}else if(way =='out'){
						if(cancelOut.length === 0){
							return;
						}
						for(var i=0;i<cancelOut.length;i++){
							var id = cancelOut[i];
							for(var j in vueThis.rapidMenus.rights){
								if(vueThis.rapidMenus.rights[j].id === id){
									vueThis.rapidMenus.rights.splice(j,1);
								}
							}
							vueThis.checked.forEach(function(item,index){
								if(item == id){
									vueThis.checked.splice(index,1)
								}
							})
						}
					}else if(way == 'allin'){
						vueThis.rapidMenus.rights = allChildList.slice(0);
					}else{
						vueThis.rapidMenus.rights = [];
					}
					
					vueThis.checked = [];
					vueThis.rapidMenus.rightChecked = [];
					//过滤
					vueThis.setLefts();
					/*if(this.rapidMenus.keyWord!=""){
						this.filterMenu(this.rapidMenus.keyWord);
					}*/
				},
				filterMenu:function(filterKey){
					this.rapidMenus.keyWord = filterKey;
					this.rapidMenus.leftChecked = [];

					//根据输入框内容检索列表
					var searchRegex = new RegExp(JSON.parse(filterKey), 'i');
					var arr =[];
					var list = this.rapidMenus.lefts;
					for(var i=0,len=list.length;i<len;i++){
						if(searchRegex.test(list[i].text)){
							arr.push(list[i])
						}
					}
					this.rapidMenus.lefts = arr;
				},
				setLefts:function(){
					//该方法为比较左右数据重复，筛选出左边剩余数据
					var vueThis  = this;
					var rights = vueThis.rapidMenus.rights;
					var menudatasLeft = [];
					var newArrays = JSON.parse(JSON.stringify(vueThis.baseData));
					function eachArray(Arrays,key){
						for (var i = 0; i < Arrays.length; i++) {
							if(Arrays[i].children==null){
								if(Arrays[i].id == key){
									Arrays.splice(i,1);
								}
							}else{
								eachArray(Arrays[i].children,key)
							}
							menudatasLeft = Arrays;
						}
					}
					if(rights.length>0){
						for(var i =0;i<rights.length;i++){
							var id = rights[i].id;
							eachArray(newArrays,id)
						}
						vueThis.rapidMenus.lefts = menudatasLeft;
					}else{
						vueThis.rapidMenus.lefts = newArrays;
					}
					
				},
				sortMenu:function(type,id){
					var vueThis = this;
					var sortArray = JSON.parse(JSON.stringify(this.rapidMenus.rights));
					var moveid = id;
					try{
						sortArray.forEach(function(item,index){
							if(item.id == moveid){
								if(type == 'up'){
									if(index==0)return;
									sortArray.splice(index,1);
									sortArray.splice(index-1,0,item);
								}else{
									if(index==(sortArray.length-1))return;
									sortArray.splice(index,1);
									sortArray.splice(index+1,0,item);
								}
								vueThis.rapidMenus.rights = sortArray;
								//vueThis.fastMenu = sortArray;
								foreach.break=new Error("StopIteration");
							}
						})
					}catch(e){
						 if(e.message==="foreach is not defined") {
							return;
						}else throw e;
					}
				},
				saveMenu:function(){
					var vueThis = this;
					var rightInfo = vueThis.rapidMenus.rights.slice(0);
					var ids = [];
					for (var i = 0; i < rightInfo.length; i++) {
						ids.push(rightInfo[i].id);
					}
					$.ajax({
					   url:'${pageContext.request.contextPath}/menu/saveUserMenu.do?',
					   data :{'ids': ids.join(','),'empCode':this.user.userInfo.empCode,'cmsBaseOrgCode':this.user.userInfo.cmsBaseOrgCode,'oldUserMenu':oldUserMenu.join(',')},
					   success :function(data) {
						   $('#shortcutMenu').dialog('close');
						   vueThis.fastMenu = rightInfo;
					   }
					});
				},
				openTab: function(title,href,closable,treeid){
					var localObj = window.location;
					var contextPath = localObj.pathname.split("/")[1];
					var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
					if(href != null){
						if(href.indexOf("sysurl") == 0){
							href = href.replace(/sysurl/,basePath);
						}
					};
					//打开tab页
					var e = $('#mainTab').tabs('exists',title);
					if(e){
						$("#mainTab").tabs('select',title);
						var tab = $("#mainTab").tabs('getSelected');
					}else{
						if($('#mainTab').tabs('tabs').length >= 10){
							showTips("最多只能打开10个标签页！","warning",3000);
							return false;
						}
						vm.tretabs[title] = treeid;
						$('#mainTab').tabs('add',{
							title:title,
							content:'<iframe src="'+href+'" id="indextab'+treeid+'" scrolling="auto" frameborder="0" style="width:100%;height:100%"></iframe>',
							closable:closable
						});
					}
				},
				changeOrg:function(type){
					//切换部门
					var vueThis = this;
					if(type==1){
						$.ajax({
							url:'${pageContext.request.contextPath}/org/findListByIds.do',
							data:{cmsOrgIdStr:vueThis.user.userInfo.cmsOrgIdStr},
							success: function(result){
								vueThis.user.Orgs=result;
							}
						})
						$('#department').dialog({modal:true}).dialog('open');
					}else{
						$('#department').dialog('close');
						var choice = vueThis.user.changeOrg;
						$.ajax({
							url:'${pageContext.request.contextPath}/login/changeOrg.do',
							data:{cmsOrgId:choice,operateFlag:'CHANGE_ORG'},
							success: function(result){
								window.location.href="${pageContext.request.contextPath}/login/loginIndex.do";
							}
						})
					}
				},
				openMessage:function(news){
					//打开消息详情
					this.message.msgdetl = news;
					$('#messageDetail').dialog('options').title = news.title;
					$('#messageDetail').dialog({modal:true}).dialog('open');
				},
				openSetting:function(event){
					//折叠设置窗口
					var e = event || window.event;
					e.stopPropagation()
					//$(".setting").toggle(!this.settings.type);
					if(this.settings.type){
						$(".setting").removeClass('active');
					}else{
						$(".setting").addClass('active');
					}
					this.settings.type = !this.settings.type;
					localStorage.setItem("settings",JSON.stringify(this.settings));
				},
				settingThemes:function(val,event){
					var e = event || window.event;
					e.stopPropagation();
					//皮肤设置
					this.settings.themes = val;
					for (var i=0; i<frames.length; i++){
						var cssStyleDom = frames[i].frameElement.contentDocument.getElementById('themes-setting');
						if(cssStyleDom == null){
							var css = document.createElement('style');
							css.type='text/css';
							css.id="themes-setting";
							if(val=='default'){
								css.innerHTML =".iconfont{color:#ff9372;}";
							}else if(val =='blue'){
								css.innerHTML =".iconfont{color:#23b7e5;}";
							}else{
								css.innerHTML =".iconfont{color:#464c5b;}";
							}
							frames[i].frameElement.contentDocument.head.appendChild(css);
						}else{
							if(val=='default'){
								cssStyleDom.innerHTML =".iconfont{color:#ff9372;}";
							}else if(val =='blue'){
								cssStyleDom.innerHTML =".iconfont{color:#23b7e5;}";
							}else{
								cssStyleDom.innerHTML =".iconfont{color:#464c5b;}";
							}
						}
					}
				},
				toggleRapidMenus:function(){
					//折叠常用菜单
					$("#bbs").toggle(!this.rapidMenus.type);
					this.rapidMenus.type =!this.rapidMenus.type;
				},
				openPwdlg:function(){
					$("#formUpdatePwd").form('clear');
					$("#dlgUpdatePwd").dialog({
						onBeforeClose:function(){
							if(updPwdFlag == "true"){
								showErrorMsg('请修改密码，密码由数字和字母构成，且不能少于8位！');
								return false;
							}
						}
					})
					openDialog("dlgUpdatePwd","修改密码");
				},
				savePassword:function(){
					$("#newPasswordHidden").val(hex_md5($("#newPassword").val()));
					$("#oldPasswordHidden").val(hex_md5($("#oldPassword").val()));
					$('#formUpdatePwd').form('submit',{
						url : '${pageContext.request.contextPath}/login/updatePwd.do',
						onSubmit : function() {
							if($(this).form('validate')){
								return true;
							}
							return false;		
						},
						task : function(result) {
							updPwdFlag="false";
							closeDialog("dlgUpdatePwd");
							if (result.success == false) {
								showErrorMsg(data.message);
			            	} else {
			            		showInfoMsg('密码修改成功,将跳转到登陆界面重新登陆！',function(){
			            			//跳转到登陆界面重新登陆
									window.location.href='${pageContext.request.contextPath}/login/logout.do'; 
			            		});
			            	}
						}
					});
				},
				logout:function(){
					window.location.href="${pageContext.request.contextPath}/login/logout.do";
				}
			},
			directives:{
				keyup(el,binding){
					el.oninput = function(){
						//非常关键的赋值
						binding.value.setLefts();
						
						//删除左右空格
						var trimVal = el.value.replace(/(^\s*)|(\s*$)/g, "");
						var filterKey = JSON.stringify(trimVal);
						binding.value.filterMenu(filterKey);
						
					}
				}
			},
			watch:{
				'settings':function(newvalue,oldvalue){
					var settings = JSON.stringify(newvalue);
					localStorage.setItem("settings",settings);
				},
				'settings.themes':function(newvalue,oldvalue){
					//皮肤设置
					var settings = JSON.stringify(this.settings);
					localStorage.setItem("settings",settings);
				},
				'rapidMenus.leftAllChecked':function(newvalue,oldvalue){
					if(newvalue==true){
						//全选
						var left = this.rapidMenus.lefts
						this.rapidMenus.leftChecked = [];
						for(var i=0;i<left.length;i++){
							this.rapidMenus.leftChecked.push(left[i].id);
						}
					}else{
						//全选状态 ==> 全不选
						if(this.rapidMenus.leftChecked.length == this.rapidMenus.lefts.length){
							this.rapidMenus.leftChecked =[];
						}else{
							//全选状态 ==> 部分选择
						}
					}
				},
				'rapidMenus.rightAllChecked':function(newvalue,oldvalue){
					if(newvalue==true){
						//全选
						var right = this.rapidMenus.rights;
						this.rapidMenus.rightChecked =[];
						for(var i=0;i<right.length;i++){
							this.rapidMenus.rightChecked.push(right[i].id);
						}
					}else{
						//全选状态 ==> 全不选
						if(this.rapidMenus.rightChecked.length == this.rapidMenus.rights.length){
							this.rapidMenus.rightChecked =[];
						}else{
							//全选状态 ==> 部分选择
						}
					}
				},
				'rapidMenus.leftChecked':function(newvalue,oldvalue){
					//console.log(newvalue)
					if(newvalue.length ==0 || newvalue.length < this.rapidMenus.lefts.length){
						this.rapidMenus.leftAllChecked = false;
					}else{
						this.rapidMenus.leftAllChecked = true;
					}
				},
				'rapidMenus.rightChecked':function(newvalue,oldvalue){
					//console.log(newvalue)
					if(newvalue.length ==0 || newvalue.length < this.rapidMenus.rights.length){
						this.rapidMenus.rightAllChecked = false;
					}else{
						this.rapidMenus.rightAllChecked = true;
					}
				}
			}
		})
		$(function(){
			//初始化打开首页
			vm.openTab("首页",'${pageContext.request.contextPath}/login/home.do',false,0);
			
			if(updPwdFlag == "true"){
				if (updPwdIntervalDay == 0) {
					showInfoMsg("用户首次登录请修改密码!", function () { 
						vm.openPwdlg();
					});  
				} else if (updPwdIntervalDay > 0) {
					showInfoMsg("您的密码已过期,请修改密码", function () { 
						vm.openPwdlg();
					});
				} 
			}
		})
	</script>
</body>
</html>