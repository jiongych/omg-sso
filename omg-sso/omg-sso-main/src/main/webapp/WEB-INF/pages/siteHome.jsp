<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>首页</title>
	<link rel="stylesheet" type="text/css"
		href="//static-file.uce.cn/uce-static/easyui-themes/themes/insdep/easyui.css" />
	<link rel="stylesheet" href="//static-file.uce.cn/uce-static/easyui-themes/themes/insdep/icon.css" />
	<link href="${pageContext.request.contextPath}/css/home.css?v=20190721" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />
	<link rel="stylesheet" type="text/css" href="//unpkg.com/element-ui@2.3.2/lib/theme-chalk/index.css" />
	<style type="text/css">
		body {
			padding: 15px;
			min-height: 100%;
			box-sizing: border-box;
		}

		body::-webkit-scrollbar {
			display: none;
		}

		.site-list {
			margin-bottom: 15px;
			float: left;
			padding-right: 15px;
			height: 278px;
			box-sizing: border-box;
			/* min-width: 200px; */
		}

		.site-layout {
			background: #fff;
			height: inherit;
			box-shadow: 1px 1px 7px #ddd;
			border-radius: 4px;
		}

		.center div:first-of-type,.center div:first-of-type span:first-of-type {
			width: 100%;
		}
		.header-btn{
			text-align: right;
		}
		.header-btn .icono{
			font-size: 18px;
			cursor: pointer;
		}
	</style>
</head>

<body>
	<div id="app" v-cloak>
		<div class="header-btn">
			<span @click="reset">
				<svg class="icono" aria-hidden="true">
					<use xlink:href="#icon-shuaxin"></use>
				</svg>
			</span>
		</div>
		<div class="center">
			<draggable v-model="siteList" @start="dragstart()" @end="dragend()">
				<transition-group name="list-complete">
					<li class="site-list" :style="{'width':rowWidth(item.width)}" v-for="item in siteList" :key="item.name">
						<div class="site-layout"  v-text="item.name"></div>
					</li>
				</transition-group>
			</draggable>
		</div>

		<!--快捷菜单弹窗end-->
		<!-----公告详情-------->
		<div id="noticeDetail" class="easyui-dialog" data-options="closed:true,title:'公告详情',top:50"
			style=" width:900px;height:500px;padding:20px 30px;display:none;" v-cloak buttons="#noticeDetailBtn">
			<div class="notice-box">
				<div class="notice-title" v-text="NoticeDetail.title"></div>
				<div class="notice-header">
					<span>类别：
						<em v-text="NoticeDetail.oneName?NoticeDetail.oneName:''"></em>
						<em v-if="NoticeDetail.oneName&&NoticeDetail.twoName">-</em>
						<em v-text="NoticeDetail.twoName?NoticeDetail.twoName:''"></em>
					</span>
					<span>发文部门：{{NoticeDetail.createOrgName?NoticeDetail.createOrgName:""}}</span>
					<span>发文时间：{{formatDate(NoticeDetail.createTime)}}</span>
					<span
						class="fr">阅读量：{{NoticeDetail.viewCount||NoticeDetail.viewCount==0?NoticeDetail.viewCount:''}}</span>
				</div>
			</div>
			<div class="msgdtl" v-html="notice_content"></div>
		</div>
		<div id="noticeDetailBtn">
			<span v-if="datashow" style="margin-right:10px">还剩<span
					style="color:red;font-size:20px;margin:5px;">{{countDown}}</span>秒可关闭</span>
			<a href="javascript:void(0)" class="easyui-linkbutton call"
				onclick="javascript:closeDialog('noticeDetail')">关闭</a>
		</div>
		<!-----公告详情end-------->
		<div class="robot" id="robotDrag">
			<!-- <img class="showimg" id="robotDrag" src="${pageContext.request.contextPath}/images/robot.gif" @mouseenter="ITshow()" @mouseleave="ITlide()" @click="ITtiaozhuan()"> -->
			<div class="showimgDiv">
				<span style="line-height: 20px;margin-left: 5px;display: inline-block;">点我上报IT服务台!</span>
				<p style="margin-left: 5px;line-height: 20px;">按住我可以拖拽哦~</p>
				<div class="triangle_border_right">
					<span></span>
				</div>
			</div>
		</div>
	</div>
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/jquery.min.js" type="text/javascript"
		charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/iconfont.js?v=20190523" type="text/javascript"
		charset="utf-8"></script>
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/jquery.easyui.min.js" type="text/javascript"
		charset="utf-8"></script>
	<!-- CDNJS :: Vue (https://cdnjs.com/) -->
	<script src="${pageContext.request.contextPath}/scripts/vue-2.3.min.js"></script>
	<!-- CDNJS :: Sortable (https://cdnjs.com/) -->
	<script src="${pageContext.request.contextPath}/scripts/Sortable.min.js"></script>
	<!-- CDNJS :: Vue.Draggable (https://cdnjs.com/) -->
	<script src="${pageContext.request.contextPath}/scripts/vuedraggable.min.js"></script>
	<!--elementui-->
	<script src="//unpkg.com/element-ui@2.3.2/lib/index.js"></script>


	<script type="text/javascript">
		var domain = window.location.protocol + "//" + window.location.host;
		//小优拖动事件
		var key = false; //设置了一个标志 false为点击事件 ture为鼠标移动事件
		var firstTime = 0;
		var lastTime = 0;
		var x, y;
		var robotDrag = function (obj) {
			obj.bind("mousedown", start);
			function start(event) {
				/*
				 * clientX和clientY代表鼠标当前的横纵坐标
				 * offset()该方法返回的对象包含两个整型属性：top 和 left，以像素计。此方法只对可见元素有效。
				 * bind()绑定事件，同样unbind解绑定，此效果的实现最后必须要解绑定，否则鼠标松开后拖拽效果依然存在
				 * getX获取当前鼠标横坐标和对象离屏幕左侧距离之差（也就是left）值，
				 * getY和getX同样道理，这两个差值就是鼠标相对于对象的定位，因为拖拽后鼠标和拖拽对象的相对位置是不变的
				 */
				firstTime = new Date().getTime();
				x = document.getElementById("robotDrag").clientWidth;
				y = document.getElementById("robotDrag").clientHeight;
				//movemove事件必须绑定到$(document)上，鼠标移动是在整个屏幕上的
				$(document).bind("mousemove", move);
				//此处的$(document)可以改为obj
				$(document).bind("mouseup", stop);
				return false;//阻止默认事件或冒泡
			}
			function move(ev) {
				let oEvent = ev || event;
				//当前屏幕的宽度减去元素的宽度
				let l = oEvent.clientX - x / 2;
				let t = oEvent.clientY - y / 2;
				var width = oEvent.clientX - x / 2;
				var top = oEvent.clientY - y / 2;
				if (width < -20) {
					$(document).unbind("mouseup", stop);
					$(document).unbind("mousemove", move);
					l = 0;
				}
				if (width > document.body.clientWidth - 50) {
					l = document.body.clientWidth - 50;
					$(document).unbind("mousemove", move);
					$(document).unbind("mouseup", stop);
				}
				if (top < -20) {
					t = 0;
					$(document).unbind("mouseup", stop);
					$(document).unbind("mousemove", move);
				}
				if (top > document.body.clientHeight - 220) {
					t = document.body.clientHeight - 220;
					$(document).unbind("mouseup", stop);
					$(document).unbind("mousemove", move);
				}
				obj.css({
					"left": l + "px",
					"top": t + "px"
				});
				return false;//阻止默认事件或冒泡
			}
			function stop() {
				//解绑定，这一步很必要，前面有解释
				$(document).unbind("mousemove", move);
				$(document).unbind("mouseup", stop);
				//鼠标抬起后 记录时间 超过200ms就是移动事件
				lastTime = new Date().getTime();
				if ((lastTime - firstTime) < 200) {
					key = true;
				}
			}
		}
		function closeDialog(id) {
			$('#' + id).dialog({ modal: true }).dialog('close');
		}
		var vm = new Vue({
			el: '#app',
			name: 'home',
			data: {
				NoticeDetail: "",//公告详情 
				notice_content: "",//公告内容
				IMGBASEURL: '${pageContext.request.contextPath}/images/portal/',
				countDown: 6,//倒计时
				datashow: false,
				siteList: [],
				defaultList:[]
			},
			mounted() {
				var vueThis = this;
				vueThis.getAlertNotice();
				//小优拖拽
				obj = $("#robotDrag");
				robotDrag(obj);//传入的必须是jQuery对象，否则不能调用jQuery的自定义函数
				$("#robotDrag").on('click', function () {
					vueThis.ITtiaozhuan();
				})
				this.init();
			},
			methods: {
				init() {
					let tempArr = [{ name: "欢迎您", width: 1 }, { name: "订单", width: 3 }, { name: "账户信息", width: 2 }, { name: "罚款", width: 2 }, { name: "赔款", width: 2 }, { name: "寄派件货量", width: 2 }, { name: "问题件", width: 4 }, { name: "寄派考核", width: 2 }];
					this.defaultList=tempArr;	
					let siteArr = [];
					if (localStorage.siteList == undefined) {
						localStorage.siteList = JSON.stringify(tempArr);
						siteArr = tempArr;
					} else {
						siteArr = JSON.parse(localStorage.siteList);
					}
					this.siteList = siteArr;
					this.demo();
				},
				demo(){
					//demo 
					$.ajax({
						type: "POST",
						url: "http://dar.sit.uc56.com/dar-report-main/dynamic/findByPagination.do?sqlCode=site_backbill_aging_monitor&dateCode=2019-08-04&orgCode=V000000&page=1&rows=50",
						xhrFields: {
								withCredentials: true
						},
						crossDomain: true,
						success:function(data){
							console.log(data);
						},
							error:function(){
						}
					})
					let lists=new Array();
					lists.push(1);
					$.ajax({
						type: "POST",
						url: "https://oms.sit.uc56.com/oms-order-remind/common/todayPlanOrderQuery.do",
						xhrFields: {
							withCredentials: true
						},
						data:{
							orderTimeBegin:"2019-08-16 00:00:01",
							ordeerTimeEnd:"2019-08-16 23:59:59",
							acceptStatuses:"1,2", //gotStatuses
							acceptSiteMay:"dsd52"
						},
						success:function(data){
							console.log(data);
						},
						error:function(){
						}
					})
					
				},
				dragstart() {
					
				},
				dragend() {
					localStorage.siteList = JSON.stringify(this.siteList);
				},
				//计算宽度
				rowWidth(item) {
					let width = $(window).width();
					return item * 16.6 + '%';
				},
				//重置模块布局
				reset(){
					localStorage.siteList=JSON.stringify(this.defaultList);
					this.siteList=this.defaultList;
				},
				//IT服务台跳转方法
				ITtiaozhuan() {
					if (key) {
						let portalUrl = window.top.vm.portalUrl;
						let aiUrl = window.top.vm.aiUrl;
						let orgId = window.top.vm.user.userInfo.orgId;
						//查询该用户各上线orgID
						let orgFullId = window.top.vm.user.userInfo.orgFullId;
						//用户机构类型
						let userType = window.top.vm.user.userInfo.orgType;
						//开放小U的城市
						var uCountryOpen = window.top.vm.uCountryOpen;
						var shFlag = false;
						console.log(window.top.vm.user.userInfo);
						if (null != uCountryOpen) {
							if (uCountryOpen == "open") {
								shFlag = true;
							} else {
								var openCountryArr = uCountryOpen.split(',');
								var orgFullIdArr = orgFullId.split(',');
								for (var i = 0; i < openCountryArr.length; i++) {
									var countryOpen = openCountryArr[i];
									for (var j = 0; j < orgFullIdArr.length; j++) {
										var orgFid = orgFullIdArr[j];
										if (countryOpen == orgFid || countryOpen == userType) {
											shFlag = true;
											break;
										}
									}
									if (shFlag) {
										break;
									}
								}
							}
						}
						if (shFlag) {
							window.top.vm.openTab("IT小助手", aiUrl + "/view/webchat/robot.jsp?source=20&anonymous=1&consult=-1&linkITUrl=" + portalUrl + "/jira/createIssue.do", true, 1000009400, "PORTAL", true, 4);
						} else {
							window.top.vm.openTab("上报IT服务台", /* aiUrl + "/view/webchat/robot.jsp?source=20&anonymous=1&consult=-1&linkITUrl=" + */ portalUrl + "/jira/createIssue.do", true, 1000009400, "PORTAL", true, 4);
						}

					}
					key = false;
				},
				getAlertNotice: function () {
					var vueThis = this;
					$.ajax({
						url: 'findNoticeAlert.action',
						data: { orgId: window.top.vm.user.userInfo.orgId },
						async: true,
						success: function (result) {
							if (result.length > 0) {
								vueThis.getNoticeDetail(result[0], 1);
							}
						}
					})
				},
				getNoticeDetail(notice, idx) {
					var vueThis = this;
					notice.viewFlag = true;
					$.ajax({
						url: 'findNoticById.action',
						data: { id: notice.id, empName: window.top.vm.user.userInfo.empName, orgCode: window.top.vm.user.userInfo.orgCode, empCode: window.top.vm.user.userInfo.empCode },
						async: true,
						success: function (result) {
							var noticeContent = result.content;
							if ((result + "").indexOf("/omg-sso-main/login.action") != -1 && (noticeContent == null || noticeContent == "undefined")) {
								top.location.reload();
							} else {
								noticeContent = noticeContent.replace(/data-original/g, "src").replace(/<img /g, "<img style='max-width:100%;max-height:100%'");
								vueThis.notice_content = noticeContent;
								$('#noticeDetail').dialog('options').title = "公告";
								$('#noticeDetail').dialog({ modal: true }).dialog('open');
								vueThis.NoticeDetail = result;
								if (idx == 1) {
									if (vueThis.countDown == 6) {
										$("#noticeDetail").siblings(".panel-header").find(".panel-tool").hide();
										$("#noticeDetailBtn").find("a").hide();
										$("#noticeDetailBtn").css("margin-bottom", "10px");
										vueThis.datashow = true;
										vueThis.settime();
									}
								}
							}

						}
					})
				},
				//5秒倒计时
				settime: function () {
					var vueThis = this;
					if (vueThis.countDown == 0) {
						if (vueThis.countDown == 0) {
							$("#noticeDetail").siblings(".panel-header").find(".panel-tool").show();
							$("#noticeDetailBtn").find("a").show();
							$("#noticeDetailBtn").css("margin-bottom", "0px");
							vueThis.datashow = false
						}
						vueThis.countDown = 6;
						//图标显示，倒计时结束

					} else {
						vueThis.countDown--;
						setTimeout(function () {
							vueThis.settime()
						}, 1000)
						//图标隐藏，倒计时开始
					}
				},
				//日期格式化
				formatDate(val) {
					if (val) {
						let time = new Date(val);
						let y = time.getFullYear();
						let m = time.getMonth() + 1 >= 10 ? time.getMonth() + 1 : "0" + (time.getMonth() + 1);
						let d = time.getDate() >= 10 ? time.getDate() : "0" + time.getDate();
						let h = time.getHours() >= 10 ? time.getHours() : "0" + time.getHours();
						let n = time.getMinutes() >= 10 ? time.getMinutes() : "0" + time.getMinutes();
						let s = time.getSeconds() >= 10 ? time.getSeconds() : "0" + time.getSeconds();
						return y + "/" + m + "/" + d + " " + h + ":" + n + ":" + s;
					} else {
						return ""
					}
				},
			},
			watch: {

			}
		})
	</script>
</body>

</html>