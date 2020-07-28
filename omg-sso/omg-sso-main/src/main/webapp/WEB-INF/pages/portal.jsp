<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- <link rel="shortcut icon" href="images/favicon.ico"> -->
	<link rel="shortcut icon" type="image/x-icon" href="/omg-sso-main/images/portal/favicon.png" />
	<title>新乾坤系统</title>
	<link rel="icon" type="image/png" href="/omg-sso-main/images/logo_ym202006.png" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewer.min.css?v=01">
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/jquery.easyui.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/vue-2.3.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/jquery-accordion-menu.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/iconfont.js?v=20190820" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/uce.util.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/encryptionAlgorithm.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/message.js?v=20190722" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/reconnect-websocket-message.js?v=20190709" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/websocket-message.js?v=20190722" type="text/javascript" charset="utf-8"></script>
	<%-- <script src="${pageContext.request.contextPath}/scripts/web_socket.js?v=20190408" type="text/javascript" charset="utf-8"></script> --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/guide.js?v=20190805"></script>
	<script src="${pageContext.request.contextPath}/scripts/viewer.min.js?v=01"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/guide.css?v=20190805" />

	<script type="text/javascript">
        $.extend($.fn.validatebox.defaults.rules, {
            //当前控件值需与 id 这个控件的值相同
            equals: {
                validator: function (value, param) {
                    return value == $(param[0]).val();
                },
                message: "密码不一致"
            },
            diff: {
                validator: function (value, param) {
                    return value != $(param[0]).val();
                },
                message: '新密码与旧密码相同！'
            },
            consecutiveRepeatCharacterRegex: {
                validator: function (value) {
                    var regex = /(.)(\1){2,}/;
                    return !regex.test(value); //false
                },
                message: '密码中不能包含三位以上重复的字符!'
            },
            consecutiveCharacterRegex: {
                validator: function (value) {
                    var regex = /((9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){2}\d|(0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){2}\d|(a(?=b)|b(?=c)|c(?=d)|d(?=e)|e(?=f)|f(?=g)|g(?=h)|h(?=i)|i(?=j)|j(?=k)|k(?=l)|l(?=m)|m(?=n)|n(?=o)|o(?=p)|p(?=q)|q(?=r)|r(?=s)|s(?=t)|t(?=u)|u(?=v)|v(?=w)|w(?=x)|x(?=y)|y(?=z)){2}\w|(z(?=y)|y(?=x)|x(?=w)|w(?=v)|v(?=u)|u(?=t)|t(?=s)|s(?=r)|r(?=q)|q(?=p)|p(?=o)|o(?=n)|n(?=m)|m(?=l)|l(?=k)|k(?=j)|j(?=i)|i(?=h)|h(?=g)|g(?=f)|f(?=e)|e(?=d)|d(?=c)|c(?=b)|b(?=a)){2}\w|(A(?=B)|B(?=C)|C(?=D)|D(?=E)|E(?=F)|F(?=G)|G(?=H)|H(?=I)|I(?=J)|J(?=K)|K(?=L)|L(?=M)|M(?=N)|N(?=O)|O(?=P)|P(?=Q)|Q(?=R)|R(?=S)|S(?=T)|T(?=U)|U(?=V)|V(?=W)|W(?=X)|X(?=Y)|Y(?=Z)){2}\w|(Z(?=Y)|Y(?=X)|X(?=W)|W(?=V)|V(?=U)|U(?=T)|T(?=S)|S(?=R)|R(?=Q)|Q(?=P)|P(?=O)|O(?=N)|N(?=M)|M(?=L)|L(?=K)|K(?=J)|J(?=I)|I(?=H)|H(?=G)|G(?=F)|F(?=E)|E(?=D)|D(?=C)|C(?=B)|B(?=A)){2}\w)/;
                    return !regex.test(value); //false
                },
                message: '密码中不能包含三位以上正序或倒序的连续数字或字母!'
            },
            passwordRule: {
                validator: function (value) {
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
	<link rel="stylesheet" type="text/css" href="//unpkg.com/element-ui@2.3.2/lib/theme-chalk/index.css" />
	<link rel="stylesheet" type="text/css" href="//static-file.uce.cn/uce-static/iconfont/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="//static-file.uce.cn/uce-static/easyui-themes/themes/insdep/easyui.css" />
	<link rel="stylesheet" href="//static-file.uce.cn/uce-static/easyui-themes/themes/insdep/icon.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-accordion-menu2.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css?v=20190222" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/portal-index.css?v=20190820">
	<style type="text/css">
		/* 首页最外面右侧滚动条 */

		/* html{
    height: 150%;
    overflow-y: auto;
} */

		body {
			height: 100%;
		}

		.el-radio-button:first-child .el-radio-button__inner,
		.el-radio-button:last-child .el-radio-button__inner {
			border-radius: 0!important;
			border-width: 0;
		}

		.el-radio-button--mini .el-radio-button__inner {
			padding: 7px 10px;
			font-size: 12px;
			line-height: 20px;
			border-width: 0 0 1px 0!important;
			border-radius: 0;
			display: block;
			width: 35px;
			white-space: normal;
		}

		.el-radio-button__orig-radio:checked+.el-radio-button__inner {
			background-color: #f5683d;
			border-color: #f5683d;
		}

		.v-modal {
			opacity: 1;
		}

		.msg_a:hover {
			/*font: 12px blue;*/
			text-decoration: underline;
		}

		/*.msg_a a:hover {*/

		/*background-color: #7747ab;;*/

		/*}*/

		.msg_t {
			bottom: 100px;
			border-radius: 4px;
		}
		.on{background:#392e4d;color:#ffffff !important}
		.msgMark{
			position: fixed;
			left: 0;
			right:0;
			bottom:0;
			top:0;
			background-color: rgba(255,255,255,0);
			opacity: 0;
			display: none;
		}
	</style>
</head>

<body>
<!-- 消息提示声-->
<div id="messageSoundDiv">
	<audio src="${pageContext.request.contextPath}/static/sound/dongdongdong.mp3" style='display:none' id='dongdongdong'></audio>
	<audio src="${pageContext.request.contextPath}/static/sound/carpostion.mp3" style='display:none' id='carpostion'></audio>
	<audio src="${pageContext.request.contextPath}/static/sound/dianti.mp3" style='display:none' id='dianti'></audio>
	<audio src="${pageContext.request.contextPath}/static/sound/facebook.mp3" style='display:none' id='facebook'></audio>
	<audio src="${pageContext.request.contextPath}/static/sound/ios.mp3" style='display:none' id='ios'></audio>
	<audio src="${pageContext.request.contextPath}/static/sound/iphoneLine.mp3" style='display:none' id='iphoneLine'></audio>
	<audio src="${pageContext.request.contextPath}/static/sound/leng.mp3" style='display:none' id='leng'></audio>
	<audio src="${pageContext.request.contextPath}/static/sound/wechat.mp3" style='display:none' id='wechat'></audio>
	<audio src="${pageContext.request.contextPath}/static/sound/dingdan.mp3" style='display:none' id='wechat'></audio>
</div>
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
	<!-- 消息提醒详情model -->
	<div id="msgDetail"  class="easyui-dialog msg-detail" data-options="closed:true,title:'提醒',resizable:true"
		 style="padding: 20px 30px;width: 900px;height: 500px;display:none;" v-cloak buttons="#noticeDetailBtn">
		<div  v-if="NoticeDetail.isNotice" >
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
		<div v-else>
			<div class="title" style="text-align: center;font-size: 16px;" v-text="NoticeDetail.title"></div>
			<div class="time" style="overflow: hidden;color: #999999;font-size: 14px;margin: 10px 0;">
				发文时间：{{formatDate(NoticeDetail.createTime)}}</div>
			<div class="dtl" v-html="NoticeDetail.content"></div>
		</div>

	</div>
	<div id="noticeDetailBtn">
		<a  href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('msgDetail')">关闭</a>
	</div>

	<!-- 消息提醒个人设置 -->
	<div id="messageSelfSet"  class="easyui-dialog msg-detail" data-options="closed:true,title:'提醒'"
		 style="padding: 20px 30px;width: 900px;height: 500px;display:none;" v-cloak buttons="#messageSelfSeBtn">
		<div class="title" style="text-align: left;font-size: 14px;font-weight:bold">		请选择需要屏蔽提醒的消息类型，不勾选则默认接收提醒
		</div>
		<div id="seltSetDetail">

		</div>
	</div>
	<div id="messageSelfSeBtn" style="text-align: center;">
		<a href="javascript:void(0)" style="background-color:#541b86;color:#ffffff;width:70px;" class="easyui-linkbutton save" onclick="javascript:saveMessageSelfSet()">保存</a>
		<a href="javascript:void(0)" style="width:70px;" class="easyui-linkbutton call" onclick="javascript:closeDialog('messageSelfSet')">关闭</a>
	</div>

	<!------------header------------------->
	<header class="header">
		<div class="left">
            <img alt="logo" src="${pageContext.request.contextPath}/images/logo_full.png" style="height: 59px; margin-top: -6px;">
			<div class="title">新乾坤</div>
		</div>
		<!-- 菜单 -->
		<div class="search-box">
						<span class="con search-title" @click="menuSwitch" >
							<i v-text="search.title" :title="search.title"></i>
							<svg class="icono" aria-hidden="true">
								<use xlink:href="#icon-xiajiantou"></use>
							</svg>
							<!-- 下拉列表选择 -->
							<ul class="search-menus" v-show="isShow==2">
								<li v-for="(item,index) in search.menus" :key="index">
									<span v-text="item.title" @click="selectOption(item,index)" :class="{'active':item.selected}"></span>
								</li>
							</ul>
						</span>
			<div>
						<span class="con search-ipt">
							<div v-if="search.isText" >
								<div class="rule-tips" id="ruleTips" v-text="search.rulesTips" v-if="search.rulesTips"></div>
							</div>
							<textarea  name="" :class="{'active':search.isText}" v-if="search.indexing===0&&isShow==0" v-model="search.textarea" placeholder="请输入运单编号"
									   @blur="search.isText=false" @focus="txtAreaFocus" autocomplete="off" id="searchNum" cols="30" rows="6" ></textarea>
							<input type="text" v-else id="searchIpt" @keyup.enter="oneSubmit" v-model="search.value" autocomplete="off" @mousedown="iptFocus" @blur="isShow=0" :maxlength="search.max"
								   :placeholder="search.tips" onkeydown="checkKeyCode(event)" />
							<svg v-show="eliminate" class="icono " aria-hidden="true" @click="iconoClose()" style="position: absolute;left: 188px;color: #cccccc;font-size: 15px;top: 2px;">
								<use xlink:href="#icon-guanbi"></use>
							</svg>
							<a title="搜索" @click="goSearch"  @mouseleave="search.isText=false">
								<svg class="icono" aria-hidden="true">
									<use xlink:href="#icon-sousuo"></use>
								</svg>
							</a>
						</span>

				<!--搜索菜单-->
				<ul id="showCaidan"  class="menu-list" v-show="isShow==3">
					<li v-for="(item,index) in options" :class="{'active':item.text==search.value}" :key="index" v-text="item.text"  @mousedown="toMenuChild(item,1)"></li>
				</ul>
				<!--搜索网点资料-->
				<ul class="menu-list" v-show="isShow==4" id="dotData">
					<li v-for="(item,index) in search.serviceMenu" :class="{'active':search.value== item.orgName|| search.value==item.orgCode}" :key="index" @mousedown="toMenuChild(item,2)">
						<span v-text="item.orgName"></span>
						<span v-text="'('+item.orgCode+')'"></span>
					</li>
				</ul>
			</div>
		</div>
		<!-- 菜单end -->
		<div class="right">
			<!-- 如果有加按钮，需要手动调整 padding  -->
			<a @mouseenter="onUnReadPortalMessage()" @mouseleave="closeUnReadPortalMessage(2000)">
							<span class="message-icon" @click="openUnReadPortalMessage()" title="未读消息">
								<svg  class="icono" aria-hidden="true"  >
									<use xlink:href="#icon-wangdianxinxi"></use>
								</svg>		
								<i class="message-total" v-if="messageTotal&&messageTotal!=0" v-text="messageTotal"></i>
							</span>
				<div style='display:none;cursor: default' class="unRead-message" id="unReadMessage">
				</div>
			</a>
			<span id="totalMsgSettingSpan">|</span>
			<!-- <a @mouseenter="clearDoor()" @mouseleave="closeDoor()">
                <div class="message-icon door"  >
                    <span @click="openDoor()" title="切换门户">
                        <svg  class="icono" aria-hidden="true"  >
                            <use xlink:href="#icon-menhu"></use>
                        </svg>
                    </span>
                    <ul class="door-lists" id="doorLists" style="display: none">
                        <li class="clearfix" v-for="(item,index) in doors.lists" :key="index" >
                            <div class="fl">
                                <svg  class="icono" aria-hidden="true"  v-if="index==0" >
                                    <use xlink:href="#icon-zhuye"></use>
                                </svg>
                                <svg  class="icono" aria-hidden="true"  v-if="index==1" >
                                    <use xlink:href="#icon-jiangzhang"></use>
                                </svg>
                                <i class="door-name" @click="setDoor(item)" v-text="item.name"></i>
                            </div>
                            <div class="fr">
                                <svg  class="icono" aria-hidden="true" v-if="item.selected"  >
                                    <use xlink:href="#icon-xuanzhong"></use>
                                </svg>
                                <i @click="setDoor(item,1)" v-if="!item.selected">默认</i>
                            </div>
                        </li>
                    </ul>
                </div>
            </a>
            |
            -->
			<a @click="openUnReadMsgdlg()" id="totalMsgSetting">
				<i class="iconfont uce-mail"></i>
				<span>待办工单</span>
			</a>
			|
			<a id="orgBox" @mouseenter="isShowmoney()" @mouseleave="moneyShow.accountNum = false">
				<svg v-show="moneyShow.moneyBeyond" class="icono icono-fill flicker" aria-hidden="true" style="left: 67px;color:red;font-size: 23px;animation:myfirst 1s infinite;">
					<use xlink:href="#icon-yujing"></use>
				</svg>
				<span id="orgNmae" @click="opDept()">{{user.userInfo.cmsOrgName}}</span>
				<div v-if="moneyShow.accountNum"  class="user-info" style="width:175px;top: 43px;">
					<svg id="leftPosition" class="icono icono-fill" aria-hidden="true">
						<use xlink:href="#icon-arrRight-fill"></use>
					</svg>
					<!-- 刷新图标 -->
					<svg v-if="judgeCountDown == 3" @click="refreshMoney()"  class="icono" aria-hidden="true" style="position: absolute;left: 74%;top: 18%;font-size: 24px;color:#ff830e">
						<use xlink:href="#icon-shuaxin"></use>
					</svg>
					<div v-if="judgeCountDown == 2" style="position: absolute;left: 74%;top: 18%; width: 25px;height: 25px;background-color: #895ff3;border-radius: 50%;">
						<span style="position: absolute;top: -11px;left: -1px;color: #fff;">{{countDown}}s</span>
					</div>
					<div class="user-top" style="padding:10px 0px 10px;">
						<p>预付款总余额：</p>
						<p style="color:red;width: 110px;word-wrap: break-word;white-space: normal;">{{moneyShow.prepayAccountFormat||0}}元</p>
					</div>
					<div class="user-btm" style="padding: 10px 0;">
						<em @click="collectMoney()" v-show="moneyShow.rechargeNum">
							<svg  class="icono" aria-hidden="true">
								<use xlink:href="#icon-chongzhi"></use>
							</svg>
							<i>充值</i>
						</em>
						<em  @click="takeMoney()" v-show="moneyShow.cashNum">
							<svg  class="icono" aria-hidden="true">
								<use xlink:href="#icon-tixian"></use>
							</svg>
							<i>提现</i>
						</em>
					</div>
				</div>
			</a>


			<a v-if="showPrise" style="padding:0">
				<img id="detailOmg" @click="detailOmg()" v-if="!countTimeFlag" style="margin-left:-20px;margin-top:-10px;padding:0 15px;" src="${pageContext.request.contextPath}/images/portal/detail-white.png"
					 onMouseOver="mover()" onMouseOut="mout()">
				<span v-else style="color:#f00;margin-left: -20px;padding:0 15px;" v-text="countTime+'s'"></span>
			</a>|
			<a id="userdtl" @mouseenter="isShow=1" @mouseleave="isShow=0"  style="cursor:default">
				<i class="iconfont uce-people"></i>
				<span id="empName">{{user.empNameShow}}</span>
				<em></em>
				<!--<i class="iconfont uce-triangledown"></i>-->
				<div v-show="isShow==1" class="user-info">
					<svg class="icono icono-fill" aria-hidden="true">
						<use xlink:href="#icon-arrRight-fill"></use>
					</svg>
					<div class="user-top" style="font-weight:bold;font-size:12.5px;font-family:arial;">
						<p style="margin-top:2px;">姓名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<i v-text="user.userInfo.empName"></i>
						</p>
						<p style="margin-top:5px;">集团工号：
							<i v-text="user.userInfo.ymEmpCode"></i>
						</p>
						<p style="margin-top:5px;">优速工号：
							<i v-text="user.userInfo.empCode"></i>
						</p>
						<p style="margin-top:5px;">
							<em>
								<i class='iconfont uce-set' style='color:#645dba;    font-size: 12px;'></i>
								<i style='text-decoration:underline;color:#645dba;    font-size: 12px;cursor:pointer;' @click="findMessageType()">消息提醒设置</i>
							</em>
						</p>
					</div>

					<div class="user-btm">
						<em @click="$('#dlgUpdatePwd').dialog('open');" id="setting">
							<svg class="icono" aria-hidden="true">
								<use xlink:href="#icon-xiugaimima"></use>
							</svg>
							<i>密码修改</i>
						</em>
						<em @click="logout()">
							<svg class="icono" aria-hidden="true">
								<use xlink:href="#icon-tuichu"></use>
							</svg>
							<i>退出</i>
						</em>
					</div>
				</div>
			</a>

		</div>
	</header>
	<!--------------主体布局------------->
	<article class="uce-layout">
		<!--菜单-->
		<div id="vuedom" class="navigation main-nav" @mouseleave="submenuColumn()" style="display: flex;">
			<div class="main-menu" v-cloak>
				<ul id="demo-list">
					<li id="homepage" :class="{'cell-choise':choiseId==-2}" @mouseenter="columnFlage=false;choiseId=-2" @click="To_home()">
						<a>
							<!-- <i class="iconfont uce-home"></i> -->
							<svg class="icono" aria-hidden="true">
								<use xlink:href="#icon-shouye1"></use>
							</svg>
							<span>首页</span>
						</a>
					</li>
					<li v-for="(item, index) in dataMenu" :class="{'cell-choise':choiseId==item.id}" @mouseenter="openColumnMenu(item)" @mousedown="toggleColumn()"
						@mouseleave="leaveColumn()">
						<a>
							<svg class="icono" aria-hidden="true">
								<use v-if="item.permissionIcon" :xlink:href="'#'+item.permissionIcon"></use>
								<use v-else xlink:href="#icon-wenjianjia"></use>
							</svg>
							<span>{{item.text}}</span>
							<i class="iconfont uce-toggle" style="transform: rotate(-90deg);position: absolute;right: 5px;"></i>
						</a>
					</li>
				</ul>
				<div id="company" style="position:fixed;bottom: 0;width: 160px;text-align: center;line-height: 20px;color:#522876;display:none">&copy;2019 优速物流有限公司版权所有</div>
			</div>
			<div class="second-menu" v-show="columnFlage">
				<div class="column-list">
					<div class="third-cell" v-for="(item, index) in nextPermissionMenu" >
						<h3>{{item.text}}</h3>
						<li v-for="(childItem, index) in item.children" @mouseenter="childItem.isCollect=true" @mouseleave="childItem.isCollect=false">
							<span class="third-title" :title="childItem.text" @click="openTab(childItem.text, childItem.attributes, true, childItem.id,childItem.systemCode, false, 1);$('#company').hide();$('#vuedom').removeClass('vuedom-hover');">{{childItem.text}}</span>
							<span class="star" @click="collect(childItem)">
											<img src="${pageContext.request.contextPath}/images/star-empty.png"  v-if="!childItem.selected&&childItem.isCollect" alt="">
											<img src="${pageContext.request.contextPath}/images/star.png" v-if="childItem.selected" alt="">
										</span>
						</li>
					</div>
				</div>
			</div>

		</div>
		<!--主体内容-->
		<div id="mainTab" class="easyui-tabs" fit="true" style="margin-left: 60px;"></div>
	</article>
	<div id="department" class="easyui-dialog" data-options="closed:true,modal:true,title:'请选择部门'" style=" width:200px;height:300px;padding:5px 15px;display:none;"
		 v-cloak>
		<div class="department-dlg" style="border:none;">
			<ul style="padding:0;">
				<li v-for="(item, index) in user.userInfo.orgRefRel">
					<input type="radio" :id="'org'+index" :value="item.orgId" :disabled="item.otherEmpId == null" v-model="user.choseOrg">
					<label :for="'org'+index">{{item.otherOrgName+(item.otherEmpId == null ? '(未绑定)' : '')}}</label>
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
			<a class="setPass" @click="$('#dlgUpdatePwd').dialog('open');">
				<i class="iconfont uce-password"></i>
				<span>密码修改</span>
			</a>
		</div>
	</div>
	<!-- 消息提醒start -->
	<div class="msg-box" id="msgInfo">
		<div class="msg-lists" id="msgInfo1" v-dialogDrag :class="{'no-lists':message.lists.length==0}" @mouseenter="msgEnter" @mouseleave="msgLeave">
			<el-carousel  :arrow="(message.lists.length!=1&&message.lists.length!=0)?'always':'never'" :initial-index="message.initialIndex" indicator-position='none' height="170px"  interval="0" autoplay="false">
				<el-carousel-item  v-for="(item,index) in message.lists" :key="index" v-if="message.lists.length!=0">
					<div class="header">
						<div class="header-title" style="color:#ffffff">
							<svg class="icono" aria-hidden="true" v-if="!item.portalMessageTypeVo||!item.portalMessageTypeVo.icon">
								<use xlink:href="#icon-xiaoxi1"></use>
							</svg>
							<svg class="icono" style="color:#fff" aria-hidden="true"  v-else>
								<use :xlink:href="'#'+item.portalMessageTypeVo.icon"></use>
							</svg>
							<span style="color:#f00" v-if="item.portalMessageTypeVo&&item.portalMessageTypeVo.mustReadFlag==1" v-text="'【'+'必读'+'】'"></span>
							<span style="color:#f00" v-if="item.portalMessageTypeVo&&item.portalMessageTypeVo.mustReadFlag!=1&&item.portalMessageTypeVo.messageLevel == 2" v-text="'【'+'重要'+'】'"></span>
							<span v-html="item.portalMessageTypeVo&& item.portalMessageTypeVo.sonLevelName?item.portalMessageTypeVo.sonLevelName:'消息提醒'"></span>
						</div>
						<div class="msg-close" v-if="!message.mustReadFlag"  @click="msgClose(item,index)">
							<svg class="icono" aria-hidden="true">
								<use xlink:href="#icon-shibai"></use>
							</svg>
						</div>
					</div>
					<div class="msg-content" @click="msgShow(item,index)">
						<div v-if="item.messageTypeCode=='uct_notice'||item.messageTypeCode=='uce_portal_message'">
							<div class="msg-title" style="font-size:14px;color:#333333" v-text="item.title"></div>
						</div>
						<div class="msg-con" :class="{'no-title':item.messageTypeCode!='uct_notice'&&item.messageTypeCode!='uce_portal_message'}" style="font-size:12px;color:#999999" v-html="item.content"></div>
					</div>
					<div class="msg-footer clearfix">
						<span class="fl" v-if="!message.mustReadFlag" @click="closeAll">忽略全部</span>
						<div class="fr">
							<span class="num">{{index+1}}/{{message.lists.length}}</span>
							<span class="view-more"  v-if="!message.mustReadFlag" @click="viewAll">查看全部</span>
						</div>
					</div>
				</el-carousel-item>
			</el-carousel>
		</div>
	</div>
	<div class="msgMark" id="msgMark"></div>
	<!-- 消息提醒end -->
</div>
<div id="totalDialog" class="msg_t" title="消息统计" data-options="iconCls:'icon-tip',closed:true,resizable:true" style="display:none">
	<table id="totalDataGridMsg" style="width: 100%; border:0px;" class="easyui-datagrid" data-options="fit:true"></table>
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
<div id="dlgUpdatePwd" class="easyui-dialog" style="padding:10px 20px;width:480px;height:330px" data-options="title:'修改密码',border:false,closed:true,modal:true,buttons:'#divUpdatePwdBtn',onBeforeOpen:function(){$('#formUpdatePwd').form('clear')}">
				<span style="display:none" id="updatePwdSpan">
					<form id="formUpdatePwd" method="post">
						<table class="table" style="width: 95%; border:0px;">
							<tbody>
								<tr style="padding-left: 20px">
									<td width="30%" style="padding-right:10px;text-align:right">旧密码:</td>
									<td width="70%">
										<input id="oldPassword" class="easyui-passwordbox" required="true" iconWidth="24" style="width:100%;height:40px" data-options="prompt:'请输入原密码..'">
										<!-- <input id="oldPasswordHidden" name="oldPassword" type="hidden"> -->
									</td>
								</tr>
								<tr>
									<td style="padding-right:10px;text-align:right">新密码:</td>
									<td>
										<!-- 2017-12-18 修改密码强度校验规则 -->
										<input id="newPassword" class="easyui-passwordbox" required="true" iconWidth="24" style="width:100%;height:40px" data-options="prompt:'请输入新密码..',validType:['consecutiveRepeatCharacterRegex','consecutiveCharacterRegex','passwordRule']">
										<!-- <input id="newPasswordHidden" name="newPassword" type="hidden"> -->
										<!-- 2017-12-18 修改密码强度校验规则 -->
									</td>
								</tr>
								<tr>
									<td style="padding-right:10px;text-align:right">确认密码:</td>
									<td>
										<input class="easyui-passwordbox" required="true" iconWidth="24" style="width:100%;height:40px" data-options="prompt:'请再次输入新密码..'"
											   validType="equals['#newPassword']">
									</td>
								</tr>
							</tbody>
						</table>
					</form>
					<div class="red" style="color: #f00;margin-top:10px;">
						密码规则：<br>
						• 密码由英文字母大写、小写，数字及特殊符号构成，必须满足3种类型，且不能少于8位!<br>
						• 密码中不能包含三位以上正序或倒序的连续数字或字母!<br>
						• 密码中不允许出现3位以上重复字符!<br>
					</div>
				</span>
</div>
<div id="divUpdatePwdBtn" style="display:none;text-align: center">
	<a href="javascript:void(0)" class="easyui-linkbutton save" onclick="vm.updatePwd()" style="width:90px">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('dlgUpdatePwd')" style="width:90px">取消</a>
</div>
<!------------------隐藏域(全局变量--message.js中使用)------------------------>
<input id="pageContext" type="hidden" value="${pageContext.request.contextPath}" />

<!--用户向导start-->
<div id="guide-step" style="display: none;">
	<div style="margin-top:270px;height:10px;line-height:99em;overflow:hidden;" id="stepflow03">第二步骤</div>
	<div style="margin-top:530px;height:10px;line-height:99em;overflow:hidden;" id="stepflow04">第三步骤</div>
	<div class="tipSwitch"></div>
</div>
<!--用户向导end-->
<!------------------业务逻辑------------------------>
<script type="text/javascript">
    var msgInfo1 = function (obj) {
        var dialogHeaderEl = obj.querySelector('.header');
        dialogHeaderEl.style.cursor = 'pointer';
        var sty = obj.currentStyle || window.getComputedStyle(obj, null);

        dialogHeaderEl.onmousedown = function(e){
            // 鼠标按下，计算当前元素距离可视区的距离
            var disX = e.clientX - dialogHeaderEl.offsetLeft;
            var disY = e.clientY - dialogHeaderEl.offsetTop;

            // 获取到的值带px 正则匹配替换
            var styL, styT;

            // 注意在ie中 第一次获取到的值为组件自带50% 移动之后赋值为px
            if (sty.left.includes('%')) {
                styL = +document.body.clientWidth * (+sty.left.replace(/\%/g, '') / 100);
                styT = +document.body.clientHeight * (+sty.top.replace(/\%/g, '') / 100);
            } else {
                if(sty.left=='auto'){
                    styL = +sty.x.replace(/\px/g, '');
                    styT = +sty.y.replace(/\px/g, '');
                }else{
                    styL = +sty.left.replace(/\px/g, '');
                    styT = +sty.top.replace(/\px/g, '');
                }
            }
            $('#msgMark').show();

            document.onmousemove = function (e) {
                //当前屏幕的宽度减去元素的宽度

                // 通过事件委托，计算移动的距离
                var l = e.clientX - disX + styL;
                var t = e.clientY - disY + styT;
                // console.log(l,t);
                // if(Math.abs(t + styT) < parseFloat(sty.marginTop)){
                // 移动当前元素
				if(l <=0){
				    l= 0;
				}
                if(t <=0){
                    t= 0;
                }
                if(l >= document.body.clientWidth -320){
                    l = document.body.clientWidth -320;
				}
                if(t >= document.body.clientHeight -170){
                    t = document.body.clientHeight -170;
                }
                obj.style.left = l + 'px';
                obj.style.top = t + 'px';
                obj.style.bottom = 'auto';
                // }


                // 将此时的位置传出去
                // binding.value({x:e.pageX,y:e.pageY})
            };

            document.onmouseup = function (e) {
                document.onmousemove = null;
                document.onmouseup = null;
                $('#msgMark').hide();
            };
            e.preventDefault();
        };
    }

    var domain = window.location.protocol + "//" + window.location.host;
    //关闭消息窗口
    var closeMessage = function () {
        $(".messages").slideUp();
        //$(".userdtl").hide();
    }
    var countDown,timer,defaultLists=[]; //初始化倒计时
    var DoorTime; //移入门户，清除倒计时
    //菜单组件
    Vue.component('items', {
        props: ['model', 'type'],
        template: '#item-template',
        computed: {
            nodeType: function () {
                return this.model.state
            },
            id: function () {
                return this.model.id
            },
            title: function () {
                return this.model.text
            },
            systemCode: function () {
                return this.model.systemCode
            },
            href: function () {
                var localObj = window.location;
                var contextPath = localObj.pathname.split("/")[1];
                var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;
                if (this.model.attributes != null) {
                    if (this.model.attributes.indexOf("sysurl") == 0) {
                        this.model.attributes = this.model.attributes.replace(/sysurl/, basePath);
                    }
                };
                return this.model.attributes;
            },
            iconClass: function () {
                if (this.model.children != null) {
                    if (this.model.permissionIcon != null && this.model.permissionIcon.replace(/(^\s*)|(\s*$)/g, "") != "") {
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
            select: function (ev) {
                ev.stopPropagation();
                if (this.model.children != null) {
                    return;
                }
                vm.openTab(this.title, this.href, true, this.id, this.systemCode, false, 5)
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
    var outTime = 30;//分钟
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
                empNameShow: "",
                userInfo: {
                    orgRefRel: {}
                }, //用户信息
                permissionCode: [],
                choseOrg: "" //选择的部门
            },
            moneyShow: {
                accountNum: false,//预付款查询权限
                thirdData: false,//预付款查询第三值判断
                rechargeNum: false,//预付款充值权限
                cashNum: false,//预付款提现权限
                moneyBeyond: false,//警戒金额
                prepayAccount: 0,//预付款金额
                prepayAccountFormat: 0,//预付款金额

            },
            urlData: '',//prs接口地址
            dataMenu: [], //左侧菜单数据
            choiseId: -1,//选中的菜单id
            columnFlage: false,//开关状态
            nextPermissionMenu: [],
            portalInfo: [],
			ymSystemCodes:[],
            codeRefMenu: {},
            tabIndexRefTitle: {},
            message: {
                "msgs": messages, //消息集合
                "msgdetl": {}, //单条消息详情
                lists:[], //消息列表
                isMouse:"", //是否鼠标引入移出
                newMsg:"", //实时消息
                messageTimeOut:"", //倒计时
                mustReadFlag:false, //是否为必读消息
                initialIndex:0,//消息跑马灯默认索引
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
            menuType: "ALL",
            showPrise: false,
            options: [], //菜单数据
            defaultOptions: [],//原始数据
            isShow: 0,
            leave: '',//侧边栏二级菜单延时
            submenu: '',//菜单
            search: {
                title: '运单跟踪', //选中的菜单标题
                value: '', //搜索的文本框值
                textarea: '',//多行文本框搜索
                tips: '请输入运单编号',   //提示语
                menus: [],   //下拉菜单
                indexing: 0, //当前菜单索引
                max: 255, //最大值
                serviceMenu: [],//网点数据
                defaultserviceMenu: [],//网点初始数据
                omgUrl: '',//OMG请求url
                rulesTips: '',//规则提示语
                isBigHead: "",//大头笔访问权限
                isText: false,//显示多行文本框
            },
            countDown: '60',//倒计时
            countTime:6,
            countTimeFlag:false,
            judgeCountDown: 3,//倒计时判断
            eliminate:false,//清除input输入判断
            portalUrl:"",//portal url
            ufeUrl:"",//ufeUrl
            ymSsoUrl:"",
            uCountryOpen:"",//小U是否全国放开，open为放开，其它为不放开
            aiUrl:"",//机器人url
            mcsPortalUrl:"",//站内消息url
            billSearchUrl:"",//运单查询地址
			prepayRecharge:"estl_recharge_management_code",//预付款充值权限码
			prepayCashOut:"estl_prepayWithdrawCash",//预付款提现权限码
            NoticeDetail: "",//详情
            notice_content:"",//公告内容
            messageTotal:0,//消息数量
            doors:{      //门户
                lists:[{'name':'工作门户',selected:false,hover:false,idx:1},{'name':'网点门户',selected:false,hover:false,idx:2}],
                name:"工作门户",
                idx:1,
            }
        },
        mounted: function () { //#el挂在后执行
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

            //三角图片位置
            //搜索下拉菜单change事件
            $("#searchSelected").combobox({
                onChange: function (newValue, oldValue) {
                    if (newValue == 1) {
                        $("#searchBill").show();
                        $("#_easyui_textbox_input3").parents(".textbox").show();
                        $("#searchMenuBox").hide();
                    } else if (newValue == 2) {
                        $("#searchBill").hide();
                        $("#_easyui_textbox_input3").parents(".textbox").hide();
                        $("#searchMenuBox").show();
                    }
                }
            });
            /*定时锁屏*/
            vueThis.getMenus();
            vueThis.getUser();

            sessionStorage.removeItem("showfastMenu");
            //折叠消息窗口
            $('#message').click(function (event) {
                var e = event || window.event;
                $(".messages").slideDown(500);
                //$(".userdtl").hide();
                $(document).one("click", function () {
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
                onSelect: function (title, index) {
                    $("#demo-list li.active").removeClass("active")
                    $(".jquery-accordion-menu .submenu a").css("background-color", '')
                    $(".jquery-accordion-menu a").css("background-color", '')
                    if (vm.tretabs[title] != '000') {
                        try {
                            $("#" + vm.tretabs[title])[0].childNodes[0].style.background = "rgb(238, 120, 0)";
                        } catch (e) {

                        }
                    } else if (vm.tretabs[title] === '000') {
                        //$("#homepage").addClass("active");
                    }
                },
                onAdd: function (title, index) {
                    var id = $(this).tabs('getTab', index).attr('id');
                    vueThis.tabIndexRefTitle[id] = title;
                },
                onBeforeClose: function (title, index) {
                    var id = $(this).tabs('getTab', index).attr('id');
                    delete vueThis.tabIndexRefTitle[id];
                    delete vueThis.tretabs[title];
                }
            });
            //tab右键菜单
            tabRightMenu('mainTab', 'mm');

            //折叠菜单
            $("#vuedom").mouseenter(function (event) {
                vueThis.submenu = setTimeout(function () {
                    event.stopPropagation();
                    $('#vuedom').addClass('vuedom-hover');
                    $('#company').show();
                }, 200);
            })
            $('.header, #mainTab').mouseenter(function (event) {
                vueThis.submenu = setTimeout(function () {
                    $('#vuedom').removeClass('vuedom-hover');
                    $('#company').hide();
                    $(".messages").hide();
                }, 200);
            })
            $(".uce-layout").mousedown(function (event) {
                if (vueThis.settings.type) {
                    $(".setting").removeClass('active');
                    vueThis.settings.type = false;
                    localStorage.setItem("settings", JSON.stringify(vueThis.settings));
                } else {
                    return;
                }
            })
            this.defaultDoor();
        },
        updated: function () { //视图更新后执行

        },
        computed: {
            //计算属性
        },
        methods: {
            //切换菜单
            menuSwitch() {
                $('.search-box').mouseleave(res=>{
                    this.isShow = 0;
            })
                //搜索下拉菜单
                let menuArr = [
                    { title: "运单跟踪", selected: true },
                    { title: "查菜单", selected: false },
                    { title: "查网点资料", selected: false }
                ]
                let obj = { title: "大头笔/目的站", selected: false };
                if (this.search.isBigHead) {  //判断用户是否有查询GIS权限
                    menuArr.push(obj);
                }
                this.search.menus = menuArr;
                this.search.menus.forEach((item, index) => {
                    item.selected = false;
                if (item.title == this.search.title) {
                    item.selected = true;
                }
            })
                let isMenuShow = this.isShow;
                this.isShow = isMenuShow == 2 ? 0 : 2;
            },
            //选择菜单
            selectOption(item, index) {
                this.search.menus.forEach((val, idx) => {
                    val.selected = false;
            })
                item.selected = true;
                this.search.title = item.title;
                this.search.indexing = index;
                setTimeout(() => {
                    this.isShow = 0;
            }, 100);
            },
            //搜索菜单
            menuSecond(nVal) {
                let tempArr = this.defaultOptions, optArr = this.options;
                /* 1.有值，匹配输入的值与菜单匹配 2.无值，展示所有菜单项 */
                if (nVal) {
                    let arr = [];
                    tempArr.forEach(function (item, index) {
                        if (item.text.indexOf(nVal) != -1) {
                            arr.push(item)
                        }
                    })
                    this.options = arr;
                } else {
                    this.options = tempArr;
                }
            },
            //搜索网点资料
            menuThird(nVal) {
                let tempArr = this.search.defaultserviceMenu, optArr = this.search.serviceMenu;
                /* 1.有值，匹配输入的值与菜单匹配 2.无值，展示所有菜单项 */
                if (nVal) {
                    let arr = [];
                    tempArr.forEach(function (item, index) {
                        if (item.orgName.indexOf(nVal) != -1 || item.orgCode.indexOf(nVal) != -1) {
                            arr.push(item)
                        }
                    })
                    this.search.serviceMenu = arr;
                } else {
                    this.search.serviceMenu = [];
                }
                this.isShow=4;
            },
            //ruletips
            ruleTips() {
                setTimeout(() => {
                    let w = document.getElementById("ruleTips").clientWidth;
                document.getElementById("ruleTips").style.left = -(w + 10) + 'px';
            }, 100);
            },
            //搜索
            goSearch() {
                this.isShow = 0;
                if (this.search.indexing == 0) {
                    if (!this.search.value) {
                        this.search.isText = true;
                        if (this.search.rulesTips) {
                            this.ruleTips();
                        }
                    }
                    if (this.validator(this.search.textarea, [20])) {
                        this.searchBillFun();
                    }
                } else if (this.search.indexing == 3) {
                    if (this.search.value && this.search.isBigHead) {
                        let obj = this.search.isBigHead;
                        let url = obj.attributes + '?address=' + this.search.value;
                        this.openTab(obj.text, url, true, obj.id, obj.systemCode, true, 5)
                    } else {
                        this.search.rulesTips = "请输入目的地址!";
                        this.search.isText = true;
                        this.ruleTips();
                    }
                } else if (this.search.indexing == 1){
                    //查菜单
                    if(!this.search.value){
                        this.$message.error('请输入查询内容！');
                    }
                    if(this.options.length < 1){
                        this.$message.error('未查询到该菜单，请检查后重新输入！');
                    }
                } else if (this.search.indexing == 2){
                    //查网点资料
                    if (!this.search.value) {
                        this.$message.error('请输入查询内容！');
                    }
                    if(this.search.serviceMenu.length < 1){
                        this.$message.error('未查询到该网点，请检查后重新输入！');
                    }

                }
            },
            //运单跟踪搜索框校验规则
            validator(value, param) {
                param = param == undefined || param == null || param.length == 0 ? [20] : param;
                if (null == value || value.length == 0) {
                    this.search.rulesTips = '运单编号不能为空';
                    return false;
                } else if (value.substring(0) == "," || value.replace(/\n/g, ",").substring(0, 1) == ",") {
                    this.search.rulesTips = '查询条件不能以逗号或换行开头';
                    return false;
                } else {
                    var reg = /^[A-Za-z0-9\,]+$/;//英文和数字
                    value = value.replace(/\n/g, ",");
                    value = value.replace(new RegExp(",+", "gm"), ",");
                    value = /,$/.test(value) ? value.substring(0, value.length - 1) : value;
                    if (!reg.test(value)) {
                        this.search.rulesTips = '只能输入：字母、数字、英文逗号';
                        return false;
                    } else {
                        if (value.split(",").length > param[0]) {
                            this.search.rulesTips = '查询运单编号不能超过' + param[0] + '条';
                            return false;
                        } else {
                            var billArr = value.split(",");
                            for (var i = 0; i < billArr.length; i++) {
                                if (billArr[i].length > 30) {
                                    this.search.rulesTips = '运单编号长度不能大于30';
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                }
            },
            //收藏
            collect: function (item) {
                if (item.disabled) return;
                item.disabled = true;
                let prams = item.selected ? 1 : 0; //0为增加，1为取消
                let vueThis = this;
                item.isCollect = false;
                $.ajax({
                    url: '../portal/operatorUserMenu.action',
                    data: { permissionId: item.id, 'empCode':window.top.vm.user.userInfo.empCode,'cmsBaseOrgCode':window.top.vm.user.userInfo.cmsBaseOrgCode, operatorFlag: prams },
                    async: true,
                    type: "post",
                    success: function (result) {
                        setTimeout(() => {
                            item.disabled = false;
                    }, 1000);
                        if (result != null && result == 'success') {
                            let storage = window.sessionStorage, temp;
                            if (storage.showfastMenu) {
                                temp = JSON.parse(storage.showfastMenu);
                            }
                            if (prams === 0) {
                                vueThis.$message({
                                    message: '已添加为常用菜单!',
                                    type: 'success'
                                });
                                item.selected = true;
                                temp.push(item);
                            } else if (prams === 1) {
                                vueThis.$message({
                                    message: '已移除此常用菜单!',
                                    type: 'success'
                                });
                                item.selected = false;
                                temp.forEach((res, index) => {
                                    if (item.text == res.text) {
                                    temp.splice(index, 1);
                                }
                            })
                            }
                            sessionStorage.setItem("showfastMenu", JSON.stringify(temp));
                            let treeid=vueThis.treeid;
                            document.getElementById("indextabSSO首页").contentWindow.menuAdd();
                        } else {
                            vueThis.$message.error('收藏失败,请稍后重试！');
                        }
                    },
                    error: function (err) {
                        setTimeout(() => {
                            item.disabled = false;
                    }, 1000);
                        vueThis.$message.error('网络繁忙,请稍后重试！');
                        console.log(err)
                    }
                })

            },
            //跳转首页
            To_home() {
                $('#vuedom').removeClass('vuedom-hover');
                $('#company').hide();
                if(vm.doors.idx==1){
                    vm.openTab("首页", domain + '${pageContext.request.contextPath}/portal/home.action', false, 0, 'SSO', false);
                }else if(vm.doors.idx==2){
                    vm.openTab("首页", domain + '${pageContext.request.contextPath}/portal/siteHome.action', false, 0, 'SSO', false);
                }
            },
            iptFocus() {
                this.isShow = 0;
                if (this.search.indexing == 0) {
                    //this.search.isText = true
                } else if (this.search.indexing == 1) {
                    if (!this.search.value) {
                        this.options = this.defaultOptions;
                    }
                    this.isShow = 3;
                }  else if (this.search.indexing == 2) {
                    if(this.search.value){
                        this.isShow = 4;
                    }
                }
            },
            txtAreaFocus() {
                this.isShow = 0;
                this.search.isText = true;
                if (this.search.rulesTips) {
                    this.ruleTips()
                }
            },
            //60秒倒计时
            settime: function (idx) {
                //idx 1 刷新金额 2打开网点信息
                var vueThis = this;
                let time=idx==1?vueThis.countDown:vueThis.countTime;
                if (time == 0) {
                    if(idx==1){
                        vueThis.countDown = 60;
                        //图标显示，倒计时结束
                        vueThis.judgeCountDown = 3;
                    }else if(idx==2){
                        vueThis.countTime=6;
                        vueThis.countTimeFlag=false;
                    }
                } else {
                    if(idx==1){
                        vueThis.countDown--;
                        //图标隐藏，倒计时开始
                        vueThis.judgeCountDown = 2;
                    }else if(idx==2){
                        vueThis.countTime--;
                        vueThis.countTimeFlag=true;
                    }
                    setTimeout(function () {
                        vueThis.settime(idx);
                    }, 1000)
                }
            },

            //预付款触摸显示
            isShowmoney() {
                if (this.moneyShow.thirdData == true) {
                    this.moneyShow.accountNum = true;
                    // let orgWidth=(document.getElementById("orgBox").clientWidth-30)/2;
                    // setTimeout(()=>{
                    // 	$("#leftPosition").css("left",orgWidth)
                    // })
                }
            },

            //刷新金额
            refreshMoney: function () {
                var vueThis = this;
                vueThis.settime(1);
                vueThis.moneyData();
            },
            //点击图标清除input数据
            iconoClose(){
                this.search.value = "";
                this.search.textarea = '';
            },
            //充值
            collectMoney() {
                var skt = this.options;
                var vueThis = this;
                /* skt.forEach((element,index)=>{
                    if(element.text == "预付款充值"){
                        if(element&&element.attributes){
                            vueThis.openTab(element.text,element.attributes,true, element.id,element.systemCode, false);
                        }
                    }
                }) */
                window.postMessage({ fn: 'openTab', permissionCode: vueThis.prepayRecharge, params: { uidcode: '1000' }, attr: vueThis.urlData }, '*');
            },
            //提现
            takeMoney() {
                var skt = this.options;
                var vueThis = this;
                /* skt.forEach((element,index)=>{
                    if(element.text == "预付款提现"){
                        if(element&&element.attributes){
                            vueThis.openTab(element.text,element.attributes,true, element.id,element.systemCode, false);
                        }
                    }
                }) */
                window.postMessage({ fn: 'openTab', permissionCode: vueThis.prepayCashOut, params: { uidcode: '1001' }, attr: vueThis.urlData }, '*');
            },

            //菜单跳转
            toMenuChild(res, idx) {
                //idx 为1 搜索菜单 2为搜索网点资料
                if (idx == 1) {
                    if (res && res.attributes) {
                        this.openTab(res.text, res.attributes, true, res.id, res.systemCode, true,5);
                        this.search.value = res.text;
                    }
                } else if (idx == 2) {
                    if (res && this.omgUrl) {
                        this.search.value = res.orgName;
                        window.top.vm.openTab("网点信息", this.omgUrl + "/cmsOrg/quickInquiryCmsOrgInfo.do?baseOrgCode=" + res.baseOrgCode, true, null,'OMG',true,5);
                    }
                }
                this.isShow = 0;
            },

            opDept: function () {
                var vueThis = this;
                $.ajax({
                    url: '../portal/getCurrentUserNew.action',
                    data: {},
                    success: function (result) {
                        var isLoginOut = false;
                        if (result.orgRefRel == null || result.orgRefRel.length == 0) {
                            isLoginOut = true;
                        } else {
                            var orgId = vueThis.user.userInfo.orgId;
                            for (var i = 0; i < result.orgRefRel.length; i++) {
                                isLoginOut = true;
                                if (result.orgRefRel[i].orgId == orgId) {
                                    isLoginOut = false;
                                    break;
                                }
                            }
                        }
                        if (isLoginOut) {
                            window.location.href = '${pageContext.request.contextPath}/portal/logout.action';
                        } else {
                            vueThis.user.userInfo.orgRefRel = result.orgRefRel;
                            $('#department').dialog('open');
                        }
                    }
                });

            },
            oneSubmit(){
                let caidan = this.search.value;
                if(this.search.value != ''){
                    if(this.isShow == 3){
                        let obj = {
                            text : '',
                            attributes:'',
                            id:'',
                            systemCode:'',
                        };
                        this.options.forEach(v=>{
                            if(v.text === caidan){
                            obj.text = v.text,obj.attributes = v.attributes,obj.id = v.id,obj.systemCode = v.systemCode;
                        }
                    });
                        if(caidan == obj.text){
                            this.openTab(obj.text, obj.attributes, true, obj.id, obj.systemCode, true,5);
                        }else{
                            this.$message.error('未查询到该菜单，请检查后重新输入！');
                        }
                    }
                    if(this.isShow == 4){
                        let objques = {
                            orgName:'',
                            orgCode:'',
                            baseOrgCode:'',
                        };
                        this.search.serviceMenu.forEach(v=>{
                            if(v.orgName === caidan || v.orgCode === caidan){
                            objques.baseOrgCode = v.baseOrgCode,
                                objques.orgName = v.orgName,
                                objques.orgCode = v.orgCode;
                        }
                    });
                        if(caidan === objques.orgName || caidan === objques.orgCode){
                            this.openTab("网点信息", this.omgUrl + "/cmsOrg/quickInquiryCmsOrgInfo.do?baseOrgCode=" + objques.baseOrgCode, true,null,'OMG',true,5);
                        }else{
                            this.$message.error('未查询到该网点，请检查后重新输入！');
                        }
                    }
                    if(this.search.indexing == 3){
                        if (this.search.value && this.search.isBigHead) {
                            let obj = this.search.isBigHead;
                            let url = obj.attributes + '?address=' + this.search.value;
                            this.openTab(obj.text, url, true, obj.id, obj.systemCode, true, 5)
                        }
                    }
                }else{
                    this.$message.error('请输入查询内容！');
                }
                this.isShow = 0;
            },
            getUser: function () {
                var vueThis = this;
                //异步加载当前用户信息
                $.ajax({
                    url: '../portal/getCurrentUser.action',
                    data: {},
                    success: function (result) {
                        vueThis.user.userInfo = result;
                        vueThis.user.choseOrg = result.orgId;
                        vueThis.user.empNameShow = result.empName + "(" + result.empCode + ")";
                        vueThis.findDictPortalInfo();
                        vueThis.findYmSystemCode();
						vueThis.findDictPrsPayInfo();
                        menuPermissionCheck('ICS');
                        //vueThis.user.userInfo.position = "开发工程师";
                        if (result.cmsOrgType == "30") {
                            vueThis.showPrise = true;
                        }
                    }
                })
                $.ajax({
                    url: '../portal/findPermissionCodeByCurrentUser.action',
                    data: {},
                    success: function (result) {
                        vueThis.user.permissionCode = result;
                    }
                })
            },
            getMenus: function () {
                var vueThis = this;
                $.ajax({
                    url: '../portal/getMenuTree.action',
                    data: {},
                    async: true,
                    success: function (result) {
                        var idNodeRef = {};
                        var menuData = [];
                        if (result != null) {
                            let tempArr = [];
                            result.forEach(function (item, index) {
                                if (item.attributes && !item.isHide && item.systemCode != "MENU") {
                                    tempArr.push(item);
                                    if (item.permissionCode == "org_query" && item.systemCode == "GIS") {
                                        vueThis.search.isBigHead = item;
                                    }
                                }
                            })
                            vueThis.options = tempArr;
                            vueThis.defaultOptions = tempArr;
                            result.map(function (item) {
                                idNodeRef[item.id] = item;
                                vueThis.codeRefMenu[item.permissionCode] = item;
                            })
                            result.map(function (item) {
                                if (item.parentId != null && idNodeRef[item.parentId] != null) {
                                    if (idNodeRef[item.parentId].children == null) {
                                        idNodeRef[item.parentId].children = [];
                                    }
                                    if (!item.isHide) {
                                        idNodeRef[item.parentId].children.push(item);
                                    }
                                } else {
                                    menuData.push(item);
                                }
                            })
                        }
                        menuData.forEach((item,index)=>{
                            if(item.children&&item.children.length!=0){
                            item.children.forEach((res, index) => {
                                if (res.children && res.children.length != 0) {
                                res.children.forEach((val, index) => {
                                    vueThis.$set(val, 'selected', false)
                                vueThis.$set(val, 'isCollect', false)
                                vueThis.$set(val, "disabled", false)
                            })
                            }
                        })
                        }
                    })
                        vueThis.dataMenu=menuData;
                        setTimeout(function () {
                            $("#jquery-accordion-menu").jqueryAccordionMenu();
                        }, 500)
                    }
                })
            },
            updateHost: function(urlString) {
                //当前域名 例:my-a.uce.cn
                var hostStr = vm.getHost(window.location.host);
                //菜单url域名 例:prs.uce.cn
                var urlHost = vm.getHost(urlString);
                if (hostStr == urlHost) {
                    return urlString;
                }
                //获取三级域名 例:my-a.uce.cn --> my-a
                var first = hostStr.split(".")[0];
                var urlThreeDomain = urlString.split(".")[0];
                var suffix = "-b";
                if (first.indexOf(suffix) != -1 && urlThreeDomain.indexOf(suffix) <= 0) {
                    //备用域名字符 例:my-a.uce.cn --> -a
                    //var spare = first.substring(first.indexOf(suffix),hostStr.indexOf("."));
                    //菜单url顶级域名 例:prs.uce.cn --> uce.cn
                    var urlTopHost = urlHost.split('.').slice(-2).join('.');
                    //替换菜单中的域名为备用域名 例prs.uce.cn --> prs-a.uce.cn
                    var urlFirst = urlHost.split(".")[0] + suffix + "." + urlTopHost;
                    urlString = urlString.replace(urlHost, urlFirst);
                }
                //获取顶级域名
                /*	var topHost = hostStr.split('.').slice(-2).join('.');
                    var urlTopHost = urlHost.split('.').slice(-2).join('.'); */
                //顶级域名一致并且菜单链接域名与当前域名不一致
                /* var first = hostArr[0];
                if (topHost == urlTopHost && hostArr.length == urlHostArr.length) {
                    return urlString;
                }
                urlString = urlString.replace(urlHost, hostArr[0] + "." + urlHost); */
                return urlString;
            },
            getHost: function(urlString) {
                var index =  urlString.indexOf("://");
                if (index != -1) {
                    urlString = urlString.substring(index + 3);
                }
                index = urlString.indexOf(":");
                if (index != -1) {
                    urlString = urlString.substring(0, index);
                    return urlString;
                }
                index = urlString.indexOf("/");
                if (index != -1) {
                    urlString = urlString.substring(0, index);
                }
                return urlString;
            },
            openTab: function (title, href, closable, permissionId, systemCode,/**是否刷新*/refresh, source) {
                var treeid = systemCode + title;//构造iframe的ID
                //打开tab页
                //打开tab页
                var tabTitle = vm.tabIndexRefTitle['idtab' + treeid];
                //var e = $('#mainTab').tabs('exists', title);
                if (tabTitle) {
                    $("#mainTab").tabs('select', title);
                    var tab = $("#mainTab").tabs('getSelected');
                    if (refresh != false) {
                        document.getElementById('indextab' + treeid).src = href;
                    }
                } else {
                    if ($('#mainTab').tabs('tabs').length > 10) {
                        showTips("最多只能打开10个标签页！", "warning", 3000);
                        return false;
                    }
                    //这个判断可能打开的页面不是这个用户的菜单
                    if (permissionId) {
                        vm.tretabs[title] = permissionId;
                    }
                    /*$('#mainTab').tabs('add', {
                        title: title,
                        content: '<iframe id="indextab' + treeid + '" scrolling="auto" src="' + href + '" frameborder="0" style="width:100%;height:100%"></iframe>',
                        closable: closable
                    });*/
                    if (systemCode == 'EBU') {
                        $.ajax({
                            url: '../portal/getCurrentUserToken.action',
                            data: {},
                            async: false,
                            success: function (result) {
                                if (href.indexOf("?") != -1) {
                                    href += '&';
                                } else {
                                    href += '?';
                                }
                                href += "loginId=" + encodeURIComponent(result.empCode) + "&institutionId=" + encodeURIComponent(result.cmsOrgId) + "&systemCode=PORTAL&token=" + encodeURIComponent(result.token);
                            }
                        })
                    }
                    //备战域名时修改子菜单中的域名 haungting 2019/11/09
                    href = vm.updateHost(href);
                    //判断是否跨域
                    if (href.indexOf(domain) == 0) {
                        //没跨域直接打开
                        var iframe = '<iframe src="' + href + '" id="indextab' + treeid + '" scrolling="auto" frameborder="0" style="width:100%;height:100%"></iframe>';
                        $('#mainTab').tabs('add', {
                            id: 'idtab' + treeid,
                            title: title,
                            content: iframe,
                            closable: closable
                        });
                    } else {
                        var ymSystemFlag = false;
                        this.ymSystemCodes.forEach(element => {
                            if (element.typeCode === systemCode) {
								ymSystemFlag = true;
								return false;
							}
						});

                        //如果是快运体系的菜单
                        if (ymSystemFlag) {
                            var ymEmpCode = encodeURIComponent(window.top.vm.user.userInfo.ymEmpCode);
                            var baseOrgCode = window.top.vm.user.userInfo.cmsBaseOrgCode;
                            var ymCookie = ymEmpCode + "ymToken";
                            var ymToken = GetCookie(ymCookie);
                            if (ymToken == null || ymToken == '') {
                                var token = BASE64(window.top.vm.user.userInfo.token);
                                var compCode = encodeURIComponent(window.top.vm.user.userInfo.compCode);
                                var ymSsoUrl = this.ymSsoUrl + "/authentication/transformToken?tokenReturnType=json&fromSystemToken=" + token + "&fromSystemId=Portal&toSystemId=" + systemCode + "&userCode=" + ymEmpCode;
                                $.ajax({
                                    url: ymSsoUrl,
                                    data: {},
                                    async: false,
                                    success: function (result) {
                                        if (result.success == 'success') {
                                            var millisecond = new Date().getTime();
                                            var expiresTime = new Date(millisecond + 60 * 1000 * 90);
                                            SetCookie(ymCookie, result.data.appToken,{expires:expiresTime});
                                            href += "?layout=blank&systemID=2&appToken=" + result.data.appToken + "&baseOrgCode=" + baseOrgCode;
                                        } else{
                                            console.log(result);
										}
                                    }
                                })
                            } else{
                                href += "?layout=blank&systemID=2&appToken=" + ymToken + "&baseOrgCode=" + baseOrgCode;
                            }
                        }
                        //跨域实现跳转
                        var state = 1;
                        var iframe = '<iframe src="${pageContext.request.contextPath}/portal/crossDomain.action" id="indextab' + treeid + '" scrolling="auto" frameborder="0" style="width:100%;height:100%"></iframe>';
                        $('#mainTab').tabs('add', {
                            id: 'idtab' + treeid,
                            title: title,
                            content: iframe,
                            closable: closable
                        });

                        var crossDomainParams = {
                            currentUser: this.user.userInfo,
                            permissionCode: this.user.permissionCode
                        };
                        document.getElementById('indextab' + treeid).addEventListener("load",
                            function () {
                                if (state == 1) {
                                    state = 0;
                                    document.getElementById('indextab' + treeid).src = href;
                                    document.getElementById('indextab' + treeid).contentWindow.name = JSON.stringify(crossDomainParams);

                                }
                            }, true);
                    }
                }
                var vueThis = this;
                vueThis.openPermission(title,permissionId,systemCode,href,source);
            },
            openPermission: function(title,permissionId,systemCode,href,source) {
                var vueThis = this;
                if (title != "首页" && permissionId != 0) {
                    if (source == undefined || source == "" || source == null) {
                        source = 99;
                    }
                    $.ajax({
                        url:'collectOpenPermissionInfo.action',
                        data:{empCode:window.top.vm.user.userInfo.empCode, createOrg:window.top.vm.user.userInfo.orgId,
                            empName:window.top.vm.user.userInfo.empName,permissionId:permissionId,source:source,
                            permissionName:title,systemCode:systemCode,url:href},
                        async:true,
                        success:function(result){

                        }
                    })
                }

            },
            openColumnMenu: function (item) {
                var vueThis = this;
                let storage = window.sessionStorage, temp;
                if (storage.showfastMenu) {
                    temp = JSON.parse(storage.showfastMenu);
                    if(item.children&&item.children.length!=0){
                        item.children.forEach((res, index) => {
                            if (res.children && res.children.length != 0) {
                            res.children.forEach((val, index) => {
                                val.selected=false;
                            val.isCollect=false;
                            val.disabled=false;
                            if (temp) {
                                temp.forEach((menu, index) => {
                                    if (val.text == menu.text) {
                                    val.selected=true;
                                }
                            })
                            }
                        })
                        }
                    })
                    }
                }
                vueThis.leave = setTimeout(function () {
                    vueThis.columnFlage=true;
                    vueThis.choiseId = item.id;
                    vueThis.nextPermissionMenu = item.children;
                    $('#vuedom').addClass('vuedom-hover');
                }, 200);
            },
            leaveColumn() {
                clearTimeout(this.leave);
            },
            submenuColumn() {
                clearTimeout(this.submenu);
            },
            toggleColumn: function () {
                this.columnFlage = !this.columnFlage;
                //阻止事件冒泡
                $("#demo-list li").click(function (event) {
                    event.stopPropagation();
                })
            },
            detailOmg: function () {
                var cmsBaseOrgCode = this.user.userInfo.cmsBaseOrgCode;
                let vueThis=this;
                $.ajax({
                    url: 'findPermissionAndSystemCode.action',
                    data: { permissionCode: 'omg_wangdian_info', systemCode: 'OMG' },
                    async: true,
                    success: function (result) {
                        vueThis.settime(2);
                        if (result != null && result.sysUrl != null) {
                            window.top.vm.openTab("网点信息", result.sysUrl + "/cmsOrg/cmsOrgDetail.do?baseOrgCode=" + cmsBaseOrgCode, true, null,'OMG',true,4);
                        } else {
                            window.top.vm.openTab("网点信息", window.top.vm.dataMenu[0].attributes + "/cmsOrg/cmsOrgDetail.do?baseOrgCode=" + cmsBaseOrgCode, true, null,'OMG',true,4);
                        }
                    }
                })

            },
            openTabFromIFrame: function (permissionCode, params, attr) {
                var menuItem = this.codeRefMenu[permissionCode];
                var urlParams = '';
                var closable = true;
                if (params) {
                    for (key in params) {
                        urlParams += key + '=' + params[key] + '&';
                        if (key == 'closable') {
                            closable = params[key];
                        }
                    }
                }
                if (menuItem != null) {
                    this.openTab(attr && attr.title ? attr.title : menuItem.text, menuItem.attributes + '?' + urlParams, true, menuItem.id, attr.systemCode, attr && attr.refresh ? attr.refresh : undefined);
                } else {//自定义url
                    if (attr && attr.url)
                        this.openTab(attr.title, attr.url + '?' + urlParams, closable, null, attr.systemCode, attr && attr.refresh ? attr.refresh : undefined);
                }
            },
            /**
             * 关闭当前页面
             */
            closeTabFromIFrame: function (title) {
                if (title != undefined && title != null && title != '') {
                    $('#mainTab').tabs('close', title);
                } else {
                    var tab = $('#mainTab').tabs('getSelected');
                    var index = $('#mainTab').tabs('getTabIndex', tab);
                    $('#mainTab').tabs('close', index);
                }
            },
            changeOrg: function () {
                $('#department').dialog('close');
                if (this.user.choseOrg != vm.user.userInfo.orgId) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/portal/changeOrg.action',
                        data: { orgId: this.user.choseOrg, operateFlag: 'CHANGE_ORG' },
                        success: function (result) {
                            window.location.href = "${pageContext.request.contextPath}/portal/forward.action";
                        }
                    })
                }
            },
            updatePwd: function () {
                /* $("#newPasswordHidden").val(hex_md5($("#newPassword").val()));
                $("#oldPasswordHidden").val(hex_md5($("#oldPassword").val())); */
                $('#formUpdatePwd').form('submit', {
                    url: '${pageContext.request.contextPath}/portal/updPwd.action',
                    onSubmit: function (params) {
                        if ($(this).form('validate')) {
                            params.newPassword = hex_md5($("#newPassword").val());
                            params.oldPassword = hex_md5($("#oldPassword").val());
                            params.passwordStrength = 'STRONG';
                            return true;
                        }
                        return false;
                    },
                    success: function (result) {
                        updPwdFlag = "false";
                        var json = eval('(' + result + ')')
                        if (json.errorCode != "success") {
                            showErrorMsg(json.errorMsg);
                        } else {
                            closeDialog("dlgUpdatePwd");

                            confirmMsg("密码修改成功，<span id='updatePwd' style='color:#f00'>该账号密码可用于登录新乾坤、优速通、优速宝、新巴枪、优享寄、优速家选系统！</span>请点击【确定】跳转到登录界面重新登录！", function () {
                                //跳转到登陆界面重新登陆
                                window.location.href = '${pageContext.request.contextPath}/portal/logout.action';
                            });
                            $("#updatePwd").parent('p').parent('div').siblings(".messager-question").css('height','90px');
                            $("#updatePwd").parent('p').parent('div').css('marginTop','5px')
                        }
                    }
                });

            },
            logout: function () {
                confirmMsg("您将退出系统..", function () {
                    window.location.href = '${pageContext.request.contextPath}/portal/logout.action';
                })
            },
            openMessage: function (news) {
                //打开消息详情
                this.message.msgdetl = news;
                $('#messageDetail').dialog('options').title = news.title;
                $('#messageDetail').dialog({
                    modal: true
                }).dialog('open');
            },
            openSetting: function (event) {
                //折叠设置窗口
                var e = event || window.event;
                e.stopPropagation()
                //$(".setting").toggle(!this.settings.type);
                if (this.settings.type) {
                    $(".setting").removeClass('active');
                } else {
                    $(".setting").addClass('active');
                }
                this.settings.type = !this.settings.type;
                localStorage.setItem("settings", JSON.stringify(this.settings));
            },
            settingThemes: function (val) {
                //皮肤设置
                this.settings.themes = val;
                for (var i = 0; i < frames.length; i++) {
                    frames[i].postMessage({ fn: 'settingThemes', params: { theme: val } }, '*');
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
            changeMenuType: function (val) {
                setTimeout(function () {
                    $("#jquery-accordion-menu").jqueryAccordionMenu();
                }, 200)
            },
            searchBillFun: function () {
                var vueThis = this;
                //var billCodeValue = vueThis.billCode;
                var billCodeValue = vueThis.search.textarea;
                if (null == billCodeValue || billCodeValue.length == 0) {
                    return;
                } else {
                    billCodeValue = billCodeValue.replace(/\n/g, ",");
                    billCodeValue = billCodeValue.replace(new RegExp(",+", "gm"), ",");
                    var firstBill = billCodeValue.indexOf(",");
                    var info = window.top.vm.portalInfo;
                    var billUrl = vueThis.billSearchUrl +"?billCodes=" + billCodeValue;

                    var viewBill = billCodeValue;
                    if (firstBill != -1) {
                        viewBill = billCodeValue.substring(0, firstBill);
                    }
                    if (viewBill.length > 15) {
                        viewBill = viewBill.substring(0, 15) + "...";
                    }
                    window.top.vm.openTab("运单查询-" + viewBill, billUrl, true, null,"WET",true, 5);
                }
            },
            findDictPortalInfo: function () {
                var vueThis = this;
                $.ajax({
                    url: '${pageContext.request.contextPath}/portal/getDictDataByTypeClassCode.action',
                    data: { typeClassCode: 'PORTAL_INFO' },
                    success: function (result) {
                        vueThis.portalInfo = result;
                        result.forEach(element => {
                            if (element.typeName === "PORTAL_URL") {
								let showUrl = element.typeCode;
								//备战域名时修改子菜单中的域名 haungting 2019/11/09
								vueThis.portalUrl= vueThis.updateHost(showUrl);;
								vueThis.addressData(vueThis.portalUrl);
								if (vueThis.user.userInfo.cmsOrgType != 10) {
									vueThis.moneyData();
								}
							}
							if (element.typeName == "OMG_URL") {
								vueThis.omgUrl = vueThis.updateHost(element.typeCode);
							}
							if (element.typeName == "UFE_URL") {
								vueThis.ufeUrl = vueThis.updateHost(element.typeCode);
							}
							if (element.typeName == "U_COUNTRY_OPEN") {
								vueThis.uCountryOpen = element.typeCode;
							}
							//REQ add by huangting 20190426 begin
							if (element.typeName == "AI_URL") {
								vueThis.aiUrl = vueThis.updateHost(element.typeCode);
							}
							//REQ add by huangting 20190426 end
							//REQ add by jiyongchao 20190521 begin
							if (element.typeName == "MCS_PORTAL_URL") {
								vueThis.mcsPortalUrl = vueThis.updateHost(element.typeCode);
							}
							//REQ add by jiyongchao 20190521 end
							//REQ add by jiyongchao 20190528 begin
							if (element.typeName == "BILL_SEARCH_URL") {
								vueThis.billSearchUrl = vueThis.updateHost(element.typeCode);
							}
							if (element.typeName == "YM_SSO_URL") {
								vueThis.ymSsoUrl = element.typeCode;
							}
							//REQ add by jiyongchao 20190528 end
						});
                        for (var i = 0; i < result.length; i++) {
                            if (result[i].typeName == "SEARCH_BILL_SWITCH" && result[i].typeCode == "SEARCH_BILL_OPEN") {
                                $("#searchBill").show();
                                vueThis.darAutoSearchBill();
                                break;
                            }
                        }
                    }
                })
            },
            findDictPrsPayInfo: function () {
                var vueThis = this;
                $.ajax({
                    url: '${pageContext.request.contextPath}/portal/getDictDataByTypeClassCode.action',
                    data: { typeClassCode: 'PRS_PER_TYPE' },
                    success: function (result) {
                        vueThis.portalInfo = result;
                        result.forEach(element => {
							if (element.typeName == "预付款充值") {
								vueThis.prepayRecharge = element.typeCode;
							}
							if (element.typeName == "预付款提现") {
								vueThis.prepayCashOut = element.typeCode;
							}
						});
                    }
                })
            },
            findYmSystemCode: function () {
                var vueThis = this;
                $.ajax({
                    url: '${pageContext.request.contextPath}/portal/getDictDataByTypeClassCode.action',
                    data: { typeClassCode: 'YM_SYSTEM_CODE' },
                    success: function (result) {
                        vueThis.ymSystemCodes = result;
                    }
                })
            },
            darAutoSearchBill() {
                var vueThis = this;
                var billCode = vueThis.getUrlParam("billCode");
                if (billCode) {
                    vueThis.search.textarea = billCode;
                    vueThis.goSearch();
                    setTimeout(function(){
                        $("#searchNum").attr("class","");
                    },300);

                }
            },
            getUrlParam(paraName) {
                var url = document.location.toString();
                var arrObj = url.split("?");
                if (arrObj.length > 1) {
                    var arrPara = arrObj[1].split("&");
                    var arr;
                    for (var i = 0; i < arrPara.length; i++) {
                        arr = arrPara[i].split("=");
                        if (arr != null && arr[0] == paraName) {
                            return arr[1];
                        }
                    }
                    return "";
                } else {
                    return "";
                }
            },
            //prs金额接口
            moneyData: function () {
                var vueThis = this;
                vueThis.urlData = vueThis.portalUrl + "prs/findPrepayAccount.do";
                $.ajax({
                    url: vueThis.urlData,
                    xhrFields: { withCredentials: true },
                    data: {baseOrgCode:vueThis.user.userInfo.cmsBaseOrgCode},
                    success: function (result) {
                        if (result.accountNum > 0) {
                            vueThis.moneyShow.thirdData = true;
                        }
                        if (result.rechargeNum > 0) {
                            vueThis.moneyShow.rechargeNum = true;
                            vueThis.prspayMessage(result.prepayAccount,result.prepayAccountFormat,result.alertMoney,result.temporaryLockMoney,result.lockMode,result.cmsStatus);
                        }
                        if (result.cashNum > 0) {
                            vueThis.moneyShow.cashNum = true;
                        }
                        if (result.alertMoney >= result.prepayAccount) {
                            vueThis.moneyShow.moneyBeyond = true
                        } else {
                            vueThis.moneyShow.moneyBeyond = false;
                        }
                        vueThis.moneyShow.prepayAccount = result.prepayAccount;
                        vueThis.moneyShow.prepayAccountFormat = result.prepayAccountFormat;
                    }
                })
            },
            //目的站查询
            addressData(url) {
                let temp = url + 'org/findSiteInfo.do';
                let vueThis = this;
                $.ajax({
                    url: temp,
                    xhrFields: { withCredentials: true },
                    data: { orgType: 30, status: 9901 },
                    success: function (result) {
                        if (result && result.length != 0) {
                            vueThis.search.serviceMenu = result;
                            vueThis.search.defaultserviceMenu = result;
                        }
                    },
                    error: function (err) {
                    }
                })
            },
            //prs预付款消息弹框
            prspayMessage: function(preAccount,preAccountFormat,aleMoney,temLockMoney,preStatus,cmsStatus) {
                var vueThis = this;
                if (cmsStatus == "9901" && vueThis.user.userInfo.cmsOrgType == 30 && preStatus == 2 && (aleMoney != null || temLockMoney != null)) {
                    if (preAccount > temLockMoney && preAccount <= aleMoney) {
                        var content = "<a style='margin-top:10px;display: block;' id='rechargeMoney' onclick='rechargeMoney()'>温馨提醒，【<strong>" + vueThis.user.userInfo.cmsOrgName + "</strong>】网点的预付款余额为<label style='color:red'>" + preAccountFormat + "</label>，即将锁机，请贵站及时充值，以免影响出货，谢谢！</a>";
                        showMag(content,'<img src="${pageContext.request.contextPath}/images/preyMessage.png" "><label style="color:red;margin-left:3px;">锁机预警</lable>',null,'PRS','1');
                    } else if (preAccount <= temLockMoney) {
                        var content = "<a style='margin-top:10px;display: block;' id='rechargeMoney' onclick='rechargeMoney()'>温馨提醒，【<strong>" + vueThis.user.userInfo.cmsOrgName + "</strong>】网点的预付款余额为<label style='color:red'>" + preAccountFormat + "</label>，己锁机，请贵站及时充值，以免影响出货，谢谢！</a>";
                        showMag(content,'<img src="${pageContext.request.contextPath}/images/preyMessage.png" "><label style="color:red">锁机预警</lable>',null,'PRS','1');
                    }
                }
            },
            //消息提醒
            msgTips(msg,messageTimeOut,ismouse){
                //msg  实时消息  messageTimeOut 倒计时   ismouse 1 鼠标移入  2鼠标移除。
                //ismouse 鼠标移入时 倒计时停止，鼠标移除，重新倒计时。
                this.message.newMsg=msg; //移入移出再次出发消息
                if(messageTimeOut){
                    this.message.messageTimeOut=messageTimeOut
                }
                //ismouse 接收后台消息，未触发移入移出操作。
                if(!ismouse){
                    this.message.isMouse=""
                }
                let tempObj;
                try{
                    tempObj=JSON.parse(msg);
                } catch(e) {
                    tempObj=msg;
                }
                let timeOut;
                if(msg){
                    //判断消息列表全为必读消息的情况下，又来了一条非必读消息
                    if(!tempObj.portalMessageTypeVo||(tempObj.portalMessageTypeVo&&tempObj.portalMessageTypeVo.mustReadFlag!=1)){
                        this.message.mustReadFlag=false;
                    }
                    if(this.message.isMouse==1){ //移入 终止倒计时
                        window.clearInterval(timer);
                        return;
                    }else if(this.message.isMouse==2){ //移出 重新计时
                        timeOut = messageTimeOut;
                    }
                    if(!this.message.isMouse){
                        defaultLists.unshift(tempObj);
                        // //数组去重
                        let obj = {};
                        defaultLists = defaultLists.reduce(function(item, next) {
                            obj[next.reqId] ? '' : obj[next.reqId] = true && item.push(next);
                            return item;
                        }, []);
                        this.message.lists=defaultLists;
                        //语音播放
                        if (undefined != tempObj && undefined != tempObj.portalMessageTypeVo && tempObj.portalMessageTypeVo.messageSound) {
                            var soundName = tempObj.portalMessageTypeVo.messageSound;
                            try {
                                document.getElementById(soundName).play();
                            } catch(e) {
                                document.getElementById("dongdongdong").play();
                            }
                        } else {
                            document.getElementById("dongdongdong").play();
                        }
                        $("#msgInfo").addClass('msg-show');
                        this.$nextTick(function(){
                            console.log('1111')
                            msgInfo1(document.getElementById('msgInfo1'));//传入的必须是jQuery对象，否则不能调用jQuery的自定义函数
						});

                        timeOut = messageTimeOut;
                        if(tempObj.msgId){
                            this.msgId(tempObj,this.message.messageTimeOut)
                        }
                    }
                    /*messageTimeOut 为-1,不执行倒计时*/
                    if(timeOut==-1){
                        return;
                    }
                    window.clearInterval(timer);
                    timer=window.setInterval("countDown()",1000)
                    countDown=()=>{
                        if(timeOut==0){
                            timeOut=messageTimeOut;
                            window.clearInterval(timer);
                            if(this.mustRead().mustRead.length>0){
                                defaultLists=this.mustRead().mustRead;
                                this.message.lists=this.mustRead().mustRead;
                            }else{
                                $("#msgInfo").removeClass("msg-show");
                                this.message.lists=[]; //倒计时结束，清空消息列表
                                defaultLists=[];
                            }
                            this.message.isMouse="";
                        }else{
                            timeOut--;
                        }
                    }
                }
            },
            //获取必读消息
            mustRead(){
                let mustRead=[],noMustRead=[];
                this.message.lists.forEach(res=>{
                    if(res.portalMessageTypeVo&&res.portalMessageTypeVo.mustReadFlag==1){
                    mustRead.push(res)
                }else{
                    noMustRead.push(res)
                }
            })

                let Obj={
                    mustRead:mustRead,
                    noMustRead:noMustRead
                }
                this.message.mustReadFlag=true; //设置必读标识
                this.message.isMouse="";
                return Obj;
            },
            //msgID
            msgId(msg,messageTimeOut){
                if(messageTimeOut==-1){
                    messageTimeOut=15*1000;
                }else{
                    messageTimeOut=messageTimeOut*1000;
                }
                setTimeout(res=>{
                    var woCode = msg.msgId.split("_")[1];
                msgRead(msg.msgId,woCode);
            },messageTimeOut)
            },
            //忽略全部
            closeAll(){
                //更新消息状态为02 更新未读消息状态
                var reqId = "";
                var empCode = "";
                var messageList = this.mustRead().noMustRead;
                if (null != messageList) {
                    messageList.forEach(function (item, index) {
                        reqId += item.reqId + ",";
                        empCode = item.empCode;
                    })
                }
                sendMessageStatus(reqId,empCode,'02');
                this.message.isMouse=""
                window.clearInterval(timer);
                if(this.mustRead().mustRead.length>0){
                    defaultLists=this.mustRead().mustRead;
                    this.message.lists=this.mustRead().mustRead;
                }else{
                    this.message.lists=[];
                    defaultLists=[];
                    $("#msgInfo").removeClass("msg-show");
                }
            },
            viewAll(){
                var messageList = this.mustRead().noMustRead;
                var reqId = "";
                var empCode = "";
                if (null != messageList) {
                    messageList.forEach(function (item, index) {
                        reqId += item.reqId + ",";
                        empCode = item.empCode;
                    })
                }
                //更新消息状态为02
                sendMessageStatus(reqId,empCode,'02');
                window.clearInterval(timer);
                this.message.isMouse="";
                if(this.mustRead().mustRead.length>0){
                    defaultLists=this.mustRead().mustRead;
                    this.message.lists=this.mustRead().mustRead;
                }else{
                    this.message.lists=[];
                    defaultLists=[];
                    $("#msgInfo").removeClass("msg-show");
                }
                var mcsUrl = this.mcsPortalUrl + 'mcs-portal-main/message/forward.do';
                this.openTab("消息列表", mcsUrl, true, '100000039','MCS', false, 3);
            },
            //消息提醒
            msgClose(item,index){
                var reqId = "";
                var empCode = "";
                //判断当前消息是否非必读
                if(item.portalMessageTypeVo&&item.portalMessageTypeVo.mustReadFlag==1){
                    this.$message({
                        message: '必读消息不可关闭!',
                        type: 'error'
                    });
                }else{
                    reqId=item.reqId;
                    empCode=item.empCode;
                    //更新非必读消息状态为02
                    sendMessageStatus(reqId,empCode,'02');
                    this.message.lists.splice(index,1);
                    this.message.initialIndex=index;
                    if(this.message.lists.length==0){
                        $("#msgInfo").removeClass("msg-show");
                        window.clearInterval(timer);
                        defaultLists=[];
                        this.message.isMouse=""
                    }
                }
            },
            removeMsg(item) {
                var msgVo = this.message.lists;
                msgVo.forEach((res,index)=>{
                    if(res.reqId==item.reqId){
                    this.message.lists.splice(index,1)
                    return;
                }
            })
            },
            //打开消息
            msgShow(item,index){
                this.message.initialIndex=index;
                this.message.lists.splice(index,1);
                if(this.message.lists.length==0){
                    $("#msgInfo").removeClass("msg-show");
                    window.clearInterval(timer);
                    defaultLists=[];
                    this.message.isMouse=""
                }
                if (!item.updateStatus || item.updateStatus != 'no') {
                    this.singeMessageReadStatus(item.reqId, item.messageTypeCode);
                }

                //优速通公告
                if (item.messageTypeCode == "uct_notice") {
                    var backParam = item.backParam;
                    if (null == backParam || backParam.length == 0) {
                        backParam = eval("("+item.urlParam+")");
                        if (null != backParam) {
                            backParam = backParam.id;
                        }
                    }
                    let obj={
                        id:backParam
                    }
                    this.getNoticeDetail(obj);
                    return;
                }
                if (!item.portalMessageTypeVo || !item.portalMessageTypeVo.permissionCode) {
                    this.$set(item,'isNotice',false);
                    this.NoticeDetail=item;
                    $('#msgDetail').dialog('options').title = "提醒";
                    let ht = $(window).height(); //浏览器当前窗口可视区域高度
                    let menuHt = $("#msgDetail").parent(".panel").height();
                    $('#msgDetail').dialog({
                        top: ((ht - menuHt) / 2)
                    });
                    $('#msgDetail').dialog({ modal: true }).dialog('open');
                    $("#msgDetail").scrollTop(0);
                    setTimeout(res=>{
                        $(".msgdtl img").on('click',function(){
                        $('.msgdtl').viewer('update');
                        $(".msgdtl").viewer();
                    })
                })
                } else if (item.openType && item.openType == "url") {
                    //如果是通过url弹框，暂不开发。
                } else {
                    var keys = item.portalMessageTypeVo.param;
                    var urlParam;
                    var permissionCode;
                    var systemCode;
                    var permissionName;
                    if (item.permissionCode) {
                        permissionCode = item.permissionCode;
                    } else {
                        permissionCode=item.portalMessageTypeVo.permissionCode;
                    }
                    if(item.urlParam) {
                        urlParam = item.urlParam;
                    }
                    if (item.systemCode) {
                        systemCode = item.systemCode;
                    } else {
                        systemCode = item.portalMessageTypeVo.systemCode;
                    }
                    if (item.menuName) {
                        permissionName = item.menuName;
                    } else {
                        permissionName = item.portalMessageTypeVo.permissionName;
                    }

                    var paramV = "{" + urlParam + "}";
                    var obj = eval("("+urlParam+")");
                    this.openTabFromIFrame(permissionCode,obj,{title:permissionName,systemCode:systemCode});
                }
                if (item.portalMessageTypeVo && item.portalMessageTypeVo.sameRead == '2') {
                    var messageType = item.messageTypeCode;
                    var msgVo = this.message.lists;
                    for(var i = 0; i < msgVo.length; i) {
                        var difCount = this.removeDifMsgType(this.message.lists,messageType);
                        if (difCount == 0) {
                            return;
                        }
                    }
                }
            },
            removeDifMsgType(msgVo,messageType) {
                var difCount = 0;
                msgVo.forEach((res,index)=>{
                    if(res.messageTypeCode==messageType){
                    msgVo.splice(index,1);
                    difCount = 1;
                    return;
                }
            })
                return difCount;
            },
            getNoticeDetail(notice) {
                var vueThis = this;
                $.ajax({
                    url: 'findNoticById.action',
                    data: { id: notice.id, empName: vueThis.user.userInfo.empName, orgCode: vueThis.user.userInfo.orgCode, empCode:vueThis.user.userInfo.empCode },
                    async: true,
                    success: function (result) {
                        var noticeContent = result.content;
                        if ((result + "").indexOf("/omg-sso-main/login.action") != -1 && (noticeContent == null || noticeContent == "undefined")) {
                            top.location.reload();
                        } else {
                            noticeContent = noticeContent.replace(/data-original/g, "src").replace(/<img /g, "<img style='max-width:100%;max-height:100%'");
                            vueThis.notice_content = noticeContent;
                            $('#msgDetail').dialog('options').title = "公告";
                            let ht = $(window).height(); //浏览器当前窗口可视区域高度
                            let menuHt = $("#msgDetail").parent(".panel").height();
                            $('#msgDetail').dialog({
                                top: ((ht - menuHt) / 2)
                            });
                            $('#msgDetail').dialog({ modal: true }).dialog('open');
                            $("#msgDetail").scrollTop(0);
                            setTimeout(res=>{
                                $(".msgdtl img").on('click',function(){
                                $('.msgdtl').viewer('update');
                                $(".msgdtl").viewer();
                            })
                        })
                            result.isNotice=true;
                            vueThis.NoticeDetail = result;
                        }
                    }
                })
            },
            //消息在web端接收后的回调函数（更新消息状态）
            sendMessageStatus(reqId, empCode, status) {
                $.ajax({
                    url:'updateMessageStatus.action',
                    async:true,
                    data: {reqId: reqId, empCode:empCode, status: status},
                    success: function(result){

                    }
                });
            },
            //点击查看详情后调用更新接口
            singeMessageReadStatus(reqId, messageTypeCode) {
                $.ajax({
                    url:this.mcsPortalUrl + 'mcs-portal-main/gateway/updateSingeMessageReadStatus.do',
                    async:true,
                    xhrFields: {withCredentials: true},
                    data: {reqId: reqId, messageTypeCode:messageTypeCode},
                    success: function(result){

                    }
                });
            },
            msgEnter(){
                if(!this.message.mustReadFlag){
                    this.message.isMouse=1;
                }else{
                    this.message.isMouse=""
                }
            },
            msgLeave(){
                if(!this.message.mustReadFlag){
                    this.message.isMouse=2;
                }else{
                    this.message.isMouse="";
                }
            },
            //日期格式化
            formatDate(val) {
                if(val){
                    let time = new Date(val);
                    let y = time.getFullYear();
                    let m = time.getMonth() + 1 >= 10 ? time.getMonth()+ 1 : "0" + (time.getMonth() + 1);
                    let d = time.getDate() >= 10 ? time.getDate() : "0" + time.getDate();
                    let h = time.getHours()>= 10 ? time.getHours() : "0" + time.getHours();
                    let n = time.getMinutes() >= 10 ? time.getMinutes() : "0" + time.getMinutes();
                    let s = time.getSeconds() >= 10 ? time.getSeconds() : "0" + time.getSeconds();
                    return y + "/" + m + "/" + d + " " + h + ":"+ n+":"+s;
                }else{
                    return ""
                }
            },
            messageRemind(remindContent) {

                this.$message({
                    message: remindContent,
                    type: 'success'
                });
            },
            //展示默认门户
            defaultDoor(){
                if(localStorage.doorName){
                    this.doors.lists.forEach(res=>{
                        res.selected=false;
                    if(res.name==localStorage.doorName){
                        res.selected=true;
                    }
                })
                }else{
                    this.doors.lists.forEach(res=>{
                        res.selected=false;
                    if(res.name==this.doors.name){
                        res.selected=true;
                    }
                })
                }
            },
            //设置默认门户
            setDoor(item,idx){
                //idx 1 设置默认缓存
                $("#doorLists").hide();
                if(idx==1){
                    localStorage.doorName=item.name;
                }
                this.doors.lists.forEach(res=>{
                    res.selected=false;
            })
                item.selected=true;
            },
            //打开门户
            openDoor(){
                $("#doorLists").slideDown();
            },
            //关闭门户
            closeDoor(){
                let id = setTimeout(() => {
                    $("#doorLists").slideUp();
            }, 1000);
                DoorTime = id;
            },
            clearDoor(){
                clearTimeout(DoorTime);
            },
        },
        directives: {},
        watch: {
            'message.isMouse':function(nVal,oVal){
                if(nVal){
                    if(nVal==1){
                        this.msgTips(this.message.newMsg,null,true)
                    }else if(nVal==2){
                        let messageTimeOut=this.message.messageTimeOut;
                        this.msgTips(this.message.newMsg,messageTimeOut,true)
                    }
                }
            },
            'options': function(newvalue, oldvalue){
                let iskow = document.getElementById('showCaidan').getElementsByTagName('li');
                if(window.cur >= 0){
                    if(iskow.length!=0){
                        iskow[window.cur].className='';
                        $("#showCaidan").scrollTop(0);
                    }
                }
                if(oldvalue != newvalue){
                    window.cur = -1;
                    window.isScroll = 0;
                    window.oneDown = 0;
                }
            },
            'search.serviceMenu': function(newvalue, oldvalue){
                let iskow = document.getElementById('dotData').getElementsByTagName('li');
                if(window.cur >= 0){
                    if(iskow.length!=0){
                        iskow[window.cur].className='';
                        $("#dotData").scrollTop(0);
                    }
                } if(oldvalue != newvalue){
                    window.cur= -1;
                    window.isScroll = 0;
                    window.oneDown = 0;
                }
            },
            'settings': function (newvalue, oldvalue) {
                var settings = JSON.stringify(newvalue);
                localStorage.setItem("settings", settings);
            },
            'settings.themes': function (newvalue, oldvalue) {
                //皮肤设置
                var settings = JSON.stringify(this.settings);
                localStorage.setItem("settings", settings);
            },
            "search.indexing"(nVal, oVal) {
                if (nVal || nVal === 0) {
                    let temp;
                    switch (nVal) {
                        case 1:
                            temp = "请输入菜单名称"
                            break;
                        case 2:
                            temp = "请输入网点名称或编码";
                            break;
                        case 3:
                            temp = "请输入目的地址";
                            break;
                        default:
                            temp = "请输入运单编号";
                            break;

                    }
                    this.search.isText = false;
                    this.search.value = "";
                    this.search.tips = temp;
                    this.search.textarea = '';
                    // window.cur = -1; window.isScroll = 0; window.oneDown = 0;
                }
            },
            //搜索内容
            "search.value"(nVal, oVal) {
                if (this.search.indexing == 1) { //查菜单
                    this.search.max=255;
                    this.menuSecond(nVal)
                } else if (this.search.indexing == 2) {  //查服务网点
                    this.search.max = 255;
                    this.menuThird(nVal);
                } else if (this.search.indexing == 3) { //查大头笔
                    this.search.isText = false;
                    this.search.max = 100;
                }
                if(nVal.length >= 255){
                    this.$message.error('长度不能超过255个字符');
                }
                if(nVal.length >= 0){
                    this.eliminate = true;
                }
                if(nVal == ''){
                    this.eliminate = false;
                }
            },
            //搜索工单
            "search.textarea"(nVal, oVal) {
                if (this.search.indexing == 0) {  //查运单
					nVal = nVal.replace(/\ +/g,"");
					this.search.textarea = nVal;
                    if (this.validator(nVal, [20])) {
                        this.search.rulesTips = ""
                    }
                }
                if(nVal.length >= 0){
                    this.eliminate = true;
                }
                if(nVal.length == ''){
                    this.eliminate = false;
                }
            },
            //提示语
            "search.rulesTips"(nVal, oVal) {
                if (nVal) {
                    this.ruleTips();
                }
            },
            "doors.lists":{
                handler(nVal, oVal) {
                    if(nVal){
                        nVal.forEach(res=>{
                            if(res.selected){
                            this.doors.idx=res.idx;
                            if(this.doors.idx==1){
                                this.openTab("首页", domain + '${pageContext.request.contextPath}/portal/home.action', false, 0, 'SSO', false, 6);
                            }else if(this.doors.idx==2){
                                this.openTab("首页", domain + '${pageContext.request.contextPath}/portal/siteHome.action', false, 0, 'SSO', false, 6);
                            }
                        }
                    })
                    }
                },
                deep: true
            },
            'message.lists'(nVal,oVal){
                if(nVal){
                    if(nVal.length==1){
                        nVal.forEach(item=>{
                            if(item.portalMessageTypeVo&&item.portalMessageTypeVo.mustReadFlag==1){
                            this.message.mustReadFlag=true;
                        }
                    })
                    }
                }
            }
        }
    })

    function  _utf8_encode(string) {
        string = string.replace(/\r\n/g,"\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    }

    function BASE64(input) {
        _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
                _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    }

    /**
     * 鼠标放上去后变成黄色图片【右上角OMG的网点信息查询】
     */
    function mover() {
        document.getElementById("detailOmg").src = "${pageContext.request.contextPath}/images/portal/detail-yellow.png";
    }
    /**
     * 鼠标放上去后变成白色图片【右上角OMG的网点信息查询】
     */
    function mout() {
        document.getElementById("detailOmg").src = "${pageContext.request.contextPath}/images/portal/detail-white.png";
    }
    //初始化打开首页

    $("#updatePwdSpan").show();
    /**
     * 监听跨域iframe传递的消息
     */
    window.addEventListener('message', function (e) {
        var msg = e.data;
        switch (msg.fn) {
            case 'click':
                if (vm.settings.type) {
                    vm.openSetting();
                }
                break;
            case 'openTab':
                this.vm.openTabFromIFrame(msg.permissionCode, msg.params, msg.attr);
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
        data: { typeClassCode: 'SESSION_HEART_REGISTER' },
        success: function (result) {
            if (result) {
                length = result.length;
                window.setInterval(function () {
                    result.map(function (item) {
                        $.ajax({
                            url: item.typeCode + "/login/getSession.do", // 跨域URL
                            dataType: 'jsonp',
                            complete: function () {
                                count++;
                            },
                            success: function (session) {
                                if (result) {
                                    if (session && session.attributeKeys && session.attributeKeys.length > 0) {
                                        refresh = true;
                                    }
                                }
                            }
                        });
                    })
                }, 900000);
                window.setInterval(function () {
                    if (length != 0 && count == length) {
                        if (refresh) {
                            $.ajax({
                                async: false,
                                url: '${pageContext.request.contextPath}/portal/refreshToken.action',
                                success: function (data) {
                                    count = 0;
                                    refresh = false;
                                }
                            })
                        }
                    }
                }, 300000)
            }
        }
    })

    //增加新乾坤上线的用户引导面，通过cookie和时间做限制,stop_time之后，即使用户清除掉缓存也不会显示。
    var current_time = new Date();
    var stop_time = "2019-08-14 00:00";  //超过这个时间用户引导页面就不会显示了。
    var stop_show = current_time > (new Date(stop_time.replace(/-/g, "\/")));
    emp_code_cookie = decodeURI('${sessionScope.CURRENT_USER.empCode}');
    if (!stop_show) {
        var neverShow = GetCookie(emp_code_cookie + "neverShow");
        if (neverShow != "no") {
            showSearchTip();
            //setSearchTip();
        }
    }
    var cur = -1;
    var isScroll = 0;
    var oneDown = 0;
    function checkKeyCode(e) {
        var as;
        if(window.vm.search.indexing==1){
            as = document.getElementById('showCaidan').getElementsByTagName('li');
        }else if(window.vm.search.indexing==2){
            as = document.getElementById("dotData").getElementsByTagName("li");
        }
        if(e.keyCode == 40){
            oneDown = 1;
        }
        if(oneDown == 1){
            switch (e.keyCode) {
                case 38://上
                    //先执行下键操作，上键的初始值cur为0
                    //cur为0，不执行上键操作。
                    if(cur == 0){
                        return;
                    }
                    as[cur].className = ''; cur -= 1;
                    if (cur > 0) {
                        if (isScroll > 0) {
                            if (window.vm.search.indexing == 1) {
                                $("#showCaidan").scrollTop(isScroll -= 21);
                            } else if (window.vm.search.indexing == 2) {
                                $("#dotData").scrollTop(isScroll -= 21);
                            }
                        } else {
                            isScroll = 0;
                        }
                    }
                    as[cur].classList = 'on';
                    break;
                case 40://下
                    //当前索引等于最后一个，不在执行。
                    if(cur == as.length-1){
                        return;
                    }
                    //等于-1的情况下，第一次执行，设置初始索引值为0;否则，清除类名，索引+1
                    if (cur == -1){
                        cur = 0;
                    } else {
                        as[cur].className = '';
                        cur++;
                    }
                    //当前索引大于等于2，执行滚动事件
                    if (cur > 4) {
                        if (window.vm.search.indexing == 1) {
                            $("#showCaidan").scrollTop(isScroll += 21);
                        } else if (window.vm.search.indexing == 2) {
                            $("#dotData").scrollTop(isScroll += 21);
                        }
                    }
                    //给当前索引添加类
                    as[cur].classList='on';
                    break;
                case 13://回车选择
                    if(window.vm.search.indexing==1){
                        window.vm.search.value = window.vm.options[cur].text;
                    }else if(window.vm.search.indexing==2){
                        window.vm.search.value = window.vm.search.serviceMenu[cur].orgName;
                    }
                    window.vm.oneSubmit();
                    break;
            }
        }
    };

</script>
</body>
</html>