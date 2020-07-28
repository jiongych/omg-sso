<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>首页</title>
	<link rel="stylesheet" type="text/css" href="//static-file.uce.cn/uce-static/iconfont/iconfont.css" />
	<link rel="stylesheet" type="text/css"
		  href="//static-file.uce.cn/uce-static/easyui-themes/themes/insdep/easyui.css" />
	<link rel="stylesheet" href="//static-file.uce.cn/uce-static/easyui-themes/themes/insdep/icon.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/swiper.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewer.min.css?v=01">
	<link href="${pageContext.request.contextPath}/css/home.css?v=202006" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/jquery.min.js" type="text/javascript"
			charset="utf-8"></script>
	<script src="//static-file.uce.cn/uce-static/jquery-easyui-1.5.1/jquery.easyui.min.js" type="text/javascript"
			charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/scripts/swiper.min.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/viewer.min.js?v=01"></script>
	<!-- CDNJS :: Vue (https://cdnjs.com/) -->
	<script src="${pageContext.request.contextPath}/scripts/vue-2.3.min.js"></script>
	<!-- CDNJS :: Sortable (https://cdnjs.com/) -->
	<script src="${pageContext.request.contextPath}/scripts/Sortable.min.js"></script>
	<!-- CDNJS :: Vue.Draggable (https://cdnjs.com/) -->
	<script src="${pageContext.request.contextPath}/scripts/vuedraggable.min.js"></script>
	<!--elementui-->
	<script src="//unpkg.com/element-ui@2.3.2/lib/index.js"></script>
	<link rel="stylesheet" type="text/css" href="//unpkg.com/element-ui@2.3.2/lib/theme-chalk/index.css" />
	<style type="text/css">
		body {
			padding: 15px;
		}

		.inline {
			display: inline-block;
			width: 40%;
			height: 2px;
			background: #6952a0;
			margin: 10px auto;
		}

		.homeName {
			display: block;
			font-size: 15px;
			font-weight: bold;
			color: #6952a0;
			text-align: center;
			margin-top: 5px;
		}

		.description {
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

		/* ul li以横排显示 */

		/* 所有class为menu的div中的ul样式 */
		div.noticeMenu ul {
			list-style: none;
			/* 去掉ul前面的符号 */
			margin: 0px;
			/* 与外界元素的距离为0 */
			padding: 0px;
			/* 与内部元素的距离为0 */
			width: auto;
			/* 宽度根据元素内容调整 */
		}

		/* 所有class为menu的div中的ul中的li样式 */
		div.noticeMenu ul li {
			float: left;
			/* 向左漂移，将竖排变为横排 */
		}

		/* 所有class为menu的div中的ul中的a样式(包括尚未点击的和点击过的样式) */
		div.noticeMenu ul li a,
		div.noticeMenu ul li a:visited {
			background-color: #f5683d;
			/* 背景色 */
			border: 1px #FFFFFF solid;
			/* 边框 */
			color: #dde4ec;
			/* 文字颜色 */
			display: block;
			/* 此元素将显示为块级元素，此元素前后会带有换行符 */
			line-height: 1.35em;
			/* 行高 */
			padding: 4px 12.5px;
			/* 内部填充的距离 */
			text-decoration: none;
			/* 不显示超链接下划线 */
			white-space: nowrap;
			/* 对于文本内的空白处，不会换行，文本会在在同一行上继续，直到遇到 <br> 标签为止。 */
		}

		/* 所有class为menu的div中的ul中的a样式(鼠标移动到元素中的样式) */
		div.noticeMenu ul li a:hover {
			background-color: #f5683d;
			/* 背景色 */
			color: #dde4ec;
			/* 文字颜色 */
			text-decoration: none;
			/* 不显示超链接下划线 */
		}

		/* 所有class为menu的div中的ul中的a样式(鼠标点击元素时的样式) */
		div.noticeMenu ul li a:active {
			background-color: #465c71;
			/* 背景色 */
			color: #cfdbe6;
			/* 文字颜色 */
			text-decoration: none;
			/* 不显示超链接下划线 */
		}
	</style>
</head>

<body>
<div id="app" v-cloak>
	<!--工作门户-->
	<div class="center">
		<!---------左侧---------->
		<div class="left-area">
			<draggable v-model="layouts[0].panels" @start="dragstart()" @end="dragend()">
				<transition-group name="list-complete">
					<li v-for="(panel, index) in layouts[0].panels" class="list-complete-item"
						v-bind:key="panel.panelName">
						<!-------------常用菜单---------------->
						<div v-if="panel.panelName=='常用菜单'">
							<div class="mymenu">
								<div class="mail-top" style="cursor: move;">
									<div class="tubiao"><i class="iconfont uce-ziliaoku"></i></div>
									<p>{{panel.panelName}}</p>
									<div class="tubiao2" @click="dialogShow"><a>
										<!--<button class="gearmand">添加</button>常用菜单+号图标--><i
											class="iconfont uce-add"></i></a></div>
								</div>
								<div class="menu-list menu-layout">
									<ul v-if="showfastMenu.length > 0">
										<li v-for="(item, index) in showfastMenu" v-if="item.children==null"
											class="fast-way"
											@click="window.top.vm.openTab(item.text,item.attributes,true,item.id,item.systemCode,false,2);"
											style="width:85px;">
											<i class="iconfont"
											   :class="item.permissionIcon?item.permissionIcon:'uce-guihua'"></i>
											<p :title="item.text">{{item.text}}</p>
										</li>
									</ul>
									<div v-if="showfastMenu.length == 0" style="height:40px;margin-top:56px;">
										<i class="iconfont uce-add" style="font-size: 30px;cursor: pointer;"
										   @click="$('#shortcutMenu').dialog({modal:true}).dialog('open');"></i>
									</div>
								</div>
							</div>
						</div>
						<!--待办事项-->
						<div v-if="panel.panelName=='待办事项'">
							<div class="mymenu todo-list">
								<div class="mail-top" style="cursor: move;">
									<div class="tubiao"><i class="iconfont uce-edit"></i></div>
									<p>{{panel.panelName}} <span class="c-99">每10min刷新一次</span></p>
									<div class="tubiao2">
										<span @click="todoRefresh" v-if="!todo.showTime"><i class="iconfont uce-update"></i>刷新</span>
										<span v-else class="red" v-text="todo.countDown+'s'"></span>
									</div>
								</div>
								<div class="menu-layout">
									<ul>
										<li>
											<div class="con-title">
												<div class="header" @click="todoNav(1)" v-for="(item,index) in todo.orderList" :class="{'active':todo.isShow==1}" :key="index">
													<span v-text="item.name"></span>
													<span v-if="item.num||item.num==0">(<i class="red" style="font-style: normal" v-text="item.num"></i>)</span>
												</div>
												<div class="header" @click="todoNav(2)" v-for="(item,index) in todo.todoList" :class="{'active':todo.isShow==2}" :key="index">
													<span v-text="item.name"></span>
													<span v-if="item.num||item.num==0">(<i class="red" style="font-style: normal" v-text="item.num"></i>)</span>
												</div>
											</div>
											<div class="con-box clearfix" v-if="todo.isShow==1" v-for="(item,index) in todo.orderList" :key="index">
												<div  class="nav-lists" @click="toPage(val)" v-for="(val,idx) in item.lists" :key="idx" v-if="val.selected">
													<p class="num" :style="{'color':val.num==0?'#c1c1c1':'#ff9372'}" v-text="val.num"   :title="val.num=='?'?'网络异常,请刷新重试':''"></p>
													<p v-text="val.name"></p>
												</div>
											</div>
											<div class="con-box clearfix" v-if="todo.isShow==2" v-for="(item,index) in todo.todoList" :key="index">
												<div  class="nav-lists" @click="toPage(val)" v-for="(val,idx) in item.lists" :key="idx" v-if="val.selected">
													<p class="num" v-text="val.num" :style="{'color':val.num==0?'#c1c1c1':'#ff9372'}"  :title="val.num=='?'?'网络异常,请刷新重试':''"></p>
													<p v-text="val.name"></p>
												</div>
											</div>
										</li>
										<p style="display: flex;align-items: center;justify-content: center;flex-direction: column;height: 100%;" v-if="todo.orderList.length==0&&todo.todoList.length==0">
											<img src='../images/empty.png' style="margin-bottom: 10px"/>
											暂无待办事项。
										</p>
									</ul>
								</div>
							</div>
						</div>
						<!-------------快速入口---------------->
						<div v-if="panel.panelName=='快速入口'">
							<div class="mymenu">
								<div class="mail-top" style="cursor: move;">
									<div class="tubiao"><i class="iconfont uce-ziliaoku"></i></div>
									<p>{{panel.panelName}}</p>
									<!-- <div class="tubiao2"><a><i class="iconfont uce-turnright"></i></a></div> -->
								</div>
								<div class="menu-list menu-layout" >
									<ul v-if="homePages.length > 0" >
										<li v-for="(page, index) in homePages" class="fast-way kslk-li"
											@click="openHomepage(page)" style="height:100px;width:100px">
											<!-- <i class="iconfont uce-guihua" ></i> -->
											<img style="width:50px;height:40px;margin:0 auto;"
												 :src="IMGBASEURL+page.sysIcon.split('/')[1]">
											<p :title="page.sysName" class="homeName">{{page.sysName}}</p>
											<span class="inline"></span>
											<p :title="page.remark" class="description">{{page.remark}}</p>
										</li>
									</ul>
									<div v-if="homePages.length==0"></div>
								</div>
							</div>
						</div>
						<!-------------快递100---------------->
						<div v-if="panel.panelName=='快递100'">
							<div class="mymenu">
								<div class="mail-top" style="cursor: move;">
									<div class="tubiao"><i class="iconfont uce-ziliaoku"></i></div>
									<p>{{panel.panelName}}</p>
								</div>
								<div class="layer" v-if="layer"></div>
								<!--解决向iframe里拖动失效问题-->
								<iframe name="kuaidi100" src="https://www.kuaidi100.com/frame/730px.html"
										width="100%" height="380" marginwidth="0" marginheight="0" hspace="0" vspace="0"
										frameborder="0" scrolling="yes"></iframe>

							</div>
						</div>
					</li>
				</transition-group>
			</draggable>
		</div>

		<!---------右侧---------->
		<div class="right-area">
			<draggable v-model="layouts[1].panels" @start="dragstart()" @end="dragend()">
				<transition-group name="list-complete">
					<li v-for="(panel, index) in layouts[1].panels" class="list-complete-item"
						v-bind:key="panel.panelName">
						<!-------------公告---------------->
						<div v-if="panel.panelName=='公告'">
							<div class="notice">
								<div class="box1" style="cursor: move;">
									<div class="box11">
										<i class="iconfont uce-remind-circle"></i>
									</div>
									<p>{{panel.panelName}}</p>
									<div class="box12">
										<a @click="noticeList()">
											<i class="iconfont uce-turnright"></i>
										</a>
									</div>
								</div>
								<div class="noticeMenu">
									<div class="swiper-container">
										<div class="swiper-wrapper">
											<div class="swiper-slide" v-for="(item,index) in noticeInfo"
												 :key="index">
													<span v-text="item.typeName" :class="{'active':item.selected}"
														  @click="menuSwitch(item)"></span>
											</div>
										</div>
										<!-- 如果需要导航按钮 -->
										<div class="sw-prev">
											<div class="swiper-button-prev"></div>
										</div>
										<div class="sw-next">
											<div class="swiper-button-next"></div>
										</div>
									</div>
								</div>

								<div class="box3 new-layout">
									<ul v-if="notices.length!=0">
										<li v-for="(notice, index) in notices" @click="getNoticeDetail(notice)">
											<i class="notice-type"
											   :class="{'notice-type-old':notice.viewFlag==true,'notice-type-new':notice.viewFlag==false}"></i>
											<span>{{notice.createTime}}</span>
											<a class="box3-context">
												<i style="font-style:normal;margin-right:5px;"
												   v-text="viewTitle(notice.oneName)"></i>
												<em style="font-style: normal" :title="notice.title">{{notice.title}}</em>
												<img v-if="notice.setTop == true"
													 src="/omg-sso-main/images/portal/setTop.gif">
											</a>
										</li>

									</ul>
									<p v-else class="no-tice">暂无此类公告</p>
								</div>
							</div>
						</div>
						<!-------------日历---------------->
						<!--解决向iframe里拖动失效问题-->
						<!-- <div v-if="panel.panelName=='日历'" style="cursor: move;">
								<div class="calendar">
									<div class="layer" v-if="layer"></div>
									<iframe 
										src="${pageContext.request.contextPath}/scripts/plugins/jquery-lunar-calendar/index.html"
										width="485px" height="430px" style="margin: 0 0 0 auto;"></iframe>
								</div>
							</div> -->
						<div v-if="panel.panelName=='集团制度'">
							<div class="notice file-list">
								<div class="box1" style="cursor: move;">
									<div class="box11">
										<i class="iconfont uce-openfile"></i>
									</div>
									<p>{{panel.panelName}}</p>
									<div class="box12">
										<a @click="toFilelist()">
											<i class="iconfont uce-turnright"></i>
										</a>
									</div>
								</div>
								<div class="noticeMenu">
									<div class="swiper-container file-type">
										<div class="swiper-wrapper">
											<div class="swiper-slide" v-for="(item,index) in file.fileType"
												 :key="index">
													<span v-text="item.twoTypeName" :class="{'active':item.selected}"
														  @click="fileTypeChange(item)"></span>
											</div>
										</div>
										<!-- 如果需要导航按钮 -->
										<div class="sw-prev">
											<div class="swiper-button-prev"></div>
										</div>
										<div class="sw-next">
											<div class="swiper-button-next"></div>
										</div>
									</div>
								</div>

								<div class="box3 new-layout">
									<ul v-if="file.fileList.length!=0">
										<li v-for="(notice, index) in file.fileList">
											<i class="notice-type"
											   :class="{'notice-type-old':notice.hasRead==1,'notice-type-new':notice.hasRead==0}"></i>
											<span>{{notice.fileTime}}</span>
											<a class="box3-context" @click="viewFile(notice)">
												<i style="font-style:normal;margin-right:5px;"
												   v-if="notice.edition" v-text="'【'+notice.edition+'】'"></i>
												<em style="font-style: normal" :title="notice.fileName">{{notice.fileName}}</em>
											</a>
										</li>
									</ul>
									<p v-else class="no-tice">暂无文件数据</p>
								</div>
							</div>
						</div>
					</li>
				</transition-group>
			</draggable>
		</div>
	</div>
	<!--快捷菜单弹窗start-->
	<div id="shortcutMenu" class="easyui-dialog" data-options="closed:true,title:'常用菜单设置',constrain:true,top:100"
		 style=" width:700px;height:500px;padding:5px 15px;display:none;" buttons="#saveBtn" v-cloak>
		<div class="flex-style menu-dlg">
			<div class="treeboxmenu" style="overflow: auto;">
				<el-input placeholder="输入关键字进行过滤" v-model="filterText" size="mini" clearable></el-input>
				<el-tree ref="tree" :data="treeData" :show-checkbox="true" :default-expand-all="false" node-key="id"
						 :filter-node-method="filterNode" @check="handleCheck" :highlight-current="true"
						 :props="defaultProps">
				</el-tree>

			</div>
			<div style="width: 20px;"></div>
			<div class="menu-dlg-right">
				<div class="selected">快捷菜单（上下拖动排序）</div>
				<ul>
					<el-checkbox-group v-model="delFastMenu" @change="handleCheckedItemChange">
						<draggable v-model="setFastMenu">
							<transition-group name="list-complete">
								<el-checkbox v-for="(item, index) in setFastMenu" :label="item" :key="item.text"
											 style="margin-left: 20px;cursor: move;">{{item.text}}</el-checkbox>
							</transition-group>
						</draggable>
					</el-checkbox-group>
				</ul>
				<div class="selected">
					<el-checkbox :indeterminate="indeterminate" v-model="delAll" @change="handleCheckAllChange">全选 |
						{{delFastMenu.length}}/{{setFastMenu.length}}</el-checkbox>
					<el-button @click="removeMenu" type="danger" size="mini" icon="el-icon-delete"
							   style="position: absolute;right: 30px;margin-top: 2px;">移除</el-button>
				</div>
			</div>
		</div>
	</div>
	<div id="saveBtn" style="text-align:center;display:none">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-success-circle"
		   id="saveBtn_save" @click="saveMenu();" style="width:90px">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-cancel" @click="dialogClose"
		   style="width:90px">取消</a>
	</div>
	<!--快捷菜单弹窗end-->
	<!-----公告详情-------->
	<div id="noticeDetail" class="easyui-dialog" data-options="closed:true,title:'公告详情',top:50,resizable:true"
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

<script type="text/javascript">
    var ucScheme = window.location.protocol;//http:或者https:
    if (ucScheme == "https:") {
        ucScheme = "https";
    } else {
        ucScheme = "http";
    }
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


    var domain = window.location.protocol + "//" + window.location.host;
    let countTime; //待办事项10分钟倒计时
    var oldUserMenu = [];
    var baseLayout = [{ name: "left", id: 1, panels: [{ panelName: "常用菜单", id: 1 },{ panelName: "待办事项", id: 2 },{ panelName: "快速入口", id: 3 }] }, { name: "right", id: 2, panels: [{ panelName: "公告", id: 3 }, { panelName: "集团制度", id: 4 }] }];
    if (localStorage.lay == undefined) {
        localStorage.lay = JSON.stringify(baseLayout)
    }else{
        let lay=JSON.parse(localStorage.lay),strHtml="";
        lay[0].panels.forEach(val=>{
            strHtml+=val.panelName;
		})
		if(strHtml.indexOf("待办事项")==-1){
			localStorage.removeItem(lay);
			localStorage.lay = JSON.stringify(baseLayout);
		}
		strRightHtml = "";
		lay[1].panels.forEach(val=>{
            strRightHtml+=val.panelName;
		})
		if(strRightHtml	.indexOf("集团制度")==-1){
			localStorage.removeItem(lay);
			localStorage.lay = JSON.stringify(baseLayout);
		}
		
		
    }
    /*监听用户是否操作，定时锁屏
    var x, y;
    document.onmousemove = function (event) {
        var x1 = event.clientX;
        var y1 = event.clientY;
        if (x != x1 || y != y1) {
            window.top.postMessage({fn:'lockScreen'}, '*');
        }
        x = x1;
        y = y1;
    };
    document.onkeydown = function () {
        window.top.postMessage({fn:'lockScreen'}, '*');
    }; */
    //小优拖动事件
    var key = false; //设置了一个标志 false为点击事件 ture为鼠标移动事件
    var firstTime = 0;
    var lastTime = 0;
    var x,y;
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
            y =  document.getElementById("robotDrag").clientHeight;
            //movemove事件必须绑定到$(document)上，鼠标移动是在整个屏幕上的
            $(document).bind("mousemove", move);
            //此处的$(document)可以改为obj
            $(document).bind("mouseup", stop);
            return false;//阻止默认事件或冒泡
        }
        function move(ev) {
            let oEvent = ev || event;
            //当前屏幕的宽度减去元素的宽度
            let	l = oEvent.clientX - x/2 ;
            let	t  = oEvent.clientY - y/2;
            var width=oEvent.clientX - x/2;
            var top= oEvent.clientY - y/2;
            if(width<-20){
                $(document).unbind("mouseup", stop);
                $(document).unbind("mousemove", move);
                l=0;
            }
            if(width>document.body.clientWidth-50){
                l=document.body.clientWidth-50;
                $(document).unbind("mousemove", move);
                $(document).unbind("mouseup", stop);
            }
            if(top<-20){
                t=0;
                $(document).unbind("mouseup", stop);
                $(document).unbind("mousemove", move);
            }
            if(top>document.body.clientHeight-220 ){
                t=document.body.clientHeight-220;
                $(document).unbind("mouseup", stop);
                $(document).unbind("mousemove", move);
            }
            obj.css({
                "left": l + "px",
                "top": t+ "px"
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
    //IT服务台跳转方法
    function ITtiaozhuan() {
        if(key){
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
                        if(shFlag) {
                            break;
                        }
                    }
                }
            }
            if (shFlag) {
                window.top.vm.openTab("IT小助手", aiUrl + "/view/webchat/robot.jsp?source=20&ucScheme="+ucScheme+"&anonymous=1&consult=-1&linkITUrl=" +  portalUrl + "/jira/createIssue.do", true, 1000009400, "PORTAL", true, 4);
            } else {
                window.top.vm.openTab("上报IT服务台", /* aiUrl + "/view/webchat/robot.jsp?source=20&anonymous=1&consult=-1&linkITUrl=" + */ portalUrl + "/jira/createIssue.do", true, 1000009400, "PORTAL", true, 4);
            }

        }
        key=false;

    }
    //清除缓存
    sessionStorage.removeItem("defaultfastMenu");
    //菜单收藏
    function menuAdd() {
        let storage = window.sessionStorage, temp, setCheck = [];
        if (vm.isShow) {
            if (storage.showfastMenu) {
                temp = JSON.parse(storage.showfastMenu);
                //数组去重
                let obj = {};
                temp = temp.reduce(function (item, next) {
                    obj[next.text] ? '' : obj[next.text] = true && item.push(next);
                    return item;
                }, []);
                sessionStorage.setItem("defaultfastMenu", JSON.stringify(temp));
                vm.showfastMenu = temp;
                vm.setFastMenu = temp;
                temp.forEach((item, index) => {
                    setCheck.push(item.id);
            })
                vm.$refs.tree.setCheckedKeys(setCheck) // 初始化
            }
        }
    }
    var later = function () {
        let checkedNodes = vm.$refs.tree.getCheckedNodes().slice();
        vm.showfastMenuresult.forEach((sortId, sortIndex) => {
            let userSortId = parseInt(sortId);
        checkedNodes.forEach((item, index) => {
            if (item.id == userSortId && item.children == null) {
            vm.showfastMenu.push(item);
            vm.setFastMenu.push(item);
        }
    })
    })
        vm.isShow = true;
        sessionStorage.setItem("showfastMenu", JSON.stringify(vm.showfastMenu));
    };
    /*监听用户是否操作，定时锁屏  */

    /*--------实例化---------*/
    var vm = new Vue({
        el: '#app',
        name: 'home',
        data: {
            layer: false,
            layouts: JSON.parse(localStorage.lay),
            tasks: [1, 2, 3, 4, 5, 6, 7],//任务
            messages: [1, 2, 3, 4, 5],//消息
            notices: [],//公告
            NoticeDetail: "",//公告详情
            notice_content: "",//公告内容
            homePages: [],//首页导航
            showfastMenu: [],//用于展示快捷菜单
            setFastMenu: [],//快捷菜单设置
            delFastMenu: [],//准备移除的菜单
            delAll: false,//全选删除
            filterText: "",//过滤关键字段
            indeterminate: true,
            treeData: [],//目录
            showfastMenuresult: [],
            Announcement: [],//公告值
            defaultProps: {
                children: 'children',
                label: 'text'
            },
            IMGBASEURL: '${pageContext.request.contextPath}/images/portal/',
            isShow: false,
            countDown: 6,//倒计时
            datashow: false,
            noticeInfo: [  //公告类型
                { typeName: '最新公告', selected: true, typeId: "" },
                { typeName: '必读公告', selected: false, typeId: -100 }
            ],
            file:{
                fileType:[],//文件类型
                fileList:[] //文件列表
            },
            todo:{
                orderList:[],
                todoList:[],
                isShow:1,
                countDown:60,
                showTime:false,
            },
        },
        mounted() {
            var vueThis = this;
            //vueThis.checkBrowser();
            vueThis.getUserMenus();
            vueThis.getNoticeType();
            vueThis.getNotice();
            vueThis.getHomePage();
            vueThis.getAlertNotice();
            //小优拖拽
            obj = $("#robotDrag");
            robotDrag(obj);//传入的必须是jQuery对象，否则不能调用jQuery的自定义函数
            $("#robotDrag").on('click', function () {
                ITtiaozhuan();
            })
            //常用菜单树结构关闭事件
            $("#shortcutMenu").dialog({
                onClose() {
                    vueThis.dialogClose(1);
                }
            });
            this.getFileType();
            this.getFileList();
            this.getTodoList();
        },
        methods: {
            //查询公告类型
            getNoticeType() {
                let vueThis = this;
                $.ajax({
                    url: 'findNoticeType.action',
                    data: {},
                    async: true,
                    success: function (result) {
                        if (Array.isArray(result) && result.length != 0) {
                            let obj = {};
                            result.forEach(res => {
                                obj = {};
                            obj.typeId = res.parentId;
                            obj.typeName = res.typeName;
                            obj.selected = false;
                            vueThis.noticeInfo.push(obj);
                        })
                            //配置轮播图
                            setTimeout(res => {
                                var swiper = new Swiper('.swiper-container', {
                                    slidesPerView: 7,
                                    spaceBetween: 0,
                                    pagination: {
                                        el: '.swiper-pagination',
                                        clickable: true,
                                    },
                                    navigation: {
                                        nextEl: '.swiper-button-next',
                                        prevEl: '.swiper-button-prev',
                                    },
                                });
                        }, 0)
                        } else {
                            setTimeout(res => {
                                var swiper = new Swiper('.swiper-container', {
                                    slidesPerView: 2,
                                    spaceBetween: 0,
                                    pagination: {
                                        el: '.swiper-pagination',
                                        clickable: true,
                                    },
                                    navigation: {
                                        nextEl: '.swiper-button-next',
                                        prevEl: '.swiper-button-prev',
                                    },
                                });
                        }, 0)
                        }
                    },
                    error: (function (err) {
                        //配置轮播图
                        setTimeout(res => {
                            var swiper = new Swiper('.swiper-container', {
                                slidesPerView: 2,
                                spaceBetween: 0,
                                pagination: {
                                    el: '.swiper-pagination',
                                    clickable: true,
                                },
                                navigation: {
                                    nextEl: '.swiper-button-next',
                                    prevEl: '.swiper-button-prev',
                                },
                            });
                    }, 0)
                    })
                })
            },
            //公告类型切换
            menuSwitch(item) {
                this.noticeInfo.forEach(res => {
                    res.selected = false;
            })
                item.selected = true;
                this.getNotice(item.typeId);
            },
            dragstart() {
                this.layer = true;
            },
            dragend() {
                localStorage.lay = JSON.stringify(this.layouts);
                this.layer = false;
            },
            getNotice: function (typeId) {
                var vueThis = this;
                $.ajax({
                    url: 'noticeTopicTen.action',
                    data: { typeId: typeId, orgId: window.top.vm.user.userInfo.orgId },
                    async: true,
                    success: function (result) {
                        if (result.length > 0) {
                            for (var i = 0; i < result.length; i++) {
                                result[i].createTime = vueThis.timestampToTime(result[i].createTime);
                            }
                        }
                        //时间去重
                        var temp = new Array();

                        for (var i = 0; i < result.length; i++) {
                            if (temp.indexOf(result[i].createTime) == -1) {
                                temp.push(result[i].createTime);
                            }
                        }
                        let arr = [];
                        temp.forEach(function (item, index) {
                            let obj = new Object; obj.data = [];
                            result.forEach(function (res, resIndex) {
                                if (item == res.createTime) {
                                    obj.time = item;
                                    obj.data.push(res);
                                    arr[index] = obj;
                                }
                            })
                        })

                        vueThis.Announcement = arr;
                        //日期整合
                        vueThis.notices = result;
                    }
                })
            },
            //日期格式化
            timestampToTime(timestamp) {
                var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                Y = date.getFullYear() + '-';
                M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();

                return Y + M + D;
            },
            getAlertNotice: function () {
                var vueThis = this;
                $.ajax({
                    url: 'findNoticeAlert.action',
                    data: { orgId: window.top.vm.user.userInfo.orgId },
                    async: true,
                    success: function (result) {
                        if (result.length > 0) {
                            vm.getNoticeDetail(result[0], 1);
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
                            let ht = $(window).height(); //浏览器当前窗口可视区域高度
                            let menuHt = $("#noticeDetail").parent(".panel").height();
                            let scrollTop=$(document).scrollTop();
                            $('#noticeDetail').dialog({
                                top: ((ht - menuHt) / 2)+scrollTop
                            });
                            $("#noticeDetail").dialog({
                                onClose: function () {
                                    $("body").css({
                                        'overflow':"auto"
                                    })
                                }
                            });
                            $('#noticeDetail').dialog({ modal: true }).dialog('open');
                            if(!$("#noticeDetail").parent().is(":hidden")){
                                $("body").css({
                                    'overflow':"hidden"
                                })
                            }
                            $("#noticeDetail").scrollTop(0);
                            setTimeout(res=>{
                                $(".msgdtl img").on('click',function(){
                                $('.msgdtl').viewer('update');
                                $(".msgdtl").viewer();
                            })
                        })
                            vueThis.NoticeDetail = result;
                            if (idx == 1) {
                                if (vueThis.countDown == 6) {
                                    $("#noticeDetail").siblings(".panel-header").find(".panel-tool").hide();
                                    $("#noticeDetailBtn").find("a").hide();
                                    $("#noticeDetailBtn").css("margin-bottom", "10px");
                                    vueThis.datashow = true;
                                    vueThis.settime(1);
                                }
                            }
                        }

                    }
                })
            },
            //5秒倒计时
            settime: function (idx) {
                //idx 1 为弹框强弹5秒  2 待办事项刷新60秒倒计时
                var vueThis = this;
                let time=idx==1?vueThis.countDown:vueThis.todo.countDown;
                if (time == 0) {
                    if(idx==2){
                        vueThis.todo.showTime=false;
                        vueThis.todo.countDown=60;
                    }else if(idx==1){
                        $("#noticeDetail").siblings(".panel-header").find(".panel-tool").show();
                        $("#noticeDetailBtn").find("a").show();
                        $("#noticeDetailBtn").css("margin-bottom", "0px");
                        vueThis.datashow = false;
                        vueThis.countDown = 6;
                    }
                    //图标显示，倒计时结束
                } else {
                    if(idx==1){
                        vueThis.countDown--;
                    }else if(idx==2){
                        vueThis.todo.countDown--;
                        vueThis.todo.showTime=true;
                    }
                    setTimeout(function () {
                        vueThis.settime(idx)
                    }, 1000)
                    //图标隐藏，倒计时开始
                }
            },

            /**getMenus:function(){
				  var vueThis = this;
				  $.ajax({
					  url:'getMenuTree.action',
					  data:{},
					  async:true, 
					  success:function(result){
						  console.log(window.top.vm.dataMenu);
						  vueThis.treeData = result;
						  vueThis.getUserMenus();
					  }
				  })
			  },*/
            getUserMenus: function () {
                var vueThis = this;
                vueThis.treeData = window.top.vm.dataMenu;
                var menudatasLeft = [];
                var menudatasRight = [];
                $.ajax({
                    url: 'getUserMenu.action',
                    data: {},
                    async: true,
                    success: function (result) {
                        vueThis.$refs.tree.setCheckedKeys(result);//初始化选中数据
                        vueThis.showfastMenuresult = result;
                        setTimeout("later()", 1000);
                    },
                    error: function (err) {

                    }
                })
            },
            getHomePage() {
                var vueThis = this;
                $.ajax({
                    url: 'findHomePageInfo.action',
                    data: {},
                    async: true,
                    success: function (result) {
                        $.ajax({
                            url: '../portal/getCurrentUser.action',
                            data: {},
                            async: true,
                            success: function (data) {
                                var array = [];
                                var orgTypeT = data.orgType;
                                for (var i = 0; i < result.length; i++) {
                                    var arrayLevels = result[i].sysLevels.split(',');
                                    for (var j = 0; j < arrayLevels.length; j++) {
                                        if (arrayLevels[j] == orgTypeT) {
                                            array.push(result[i]);
                                        }
                                    }

                                }
                                vueThis.homePages = array;

                            }
                        });
                    }
                })
            },
            noticeList() {
                var info = window.top.vm.portalInfo;
                var portalUrl;
                for (var i = 0; i < info.length; i++) {
                    if (info[i].typeName == "PORTAL_URL") {
                        portalUrl = info[i].typeCode + "/notice/noticeDetail.do";
                        break;
                    }
                }
                window.top.vm.openTab("公告列表", portalUrl, true, null, 'PORTAL', false, '4');
            },
            openHomepage(page) {
                if (page.sysType) {
                    //备战域名时修改子菜单中的域名 haungting 2019/11/09
                    href = window.top.vm.updateHost(page.sysUrl);
                    window.top.vm.openTab(page.sysName, href, true, page.id, 'PORTAL', false, '4');
                } else {
                    if (page.sysName.indexOf('优速家选') != -1) {
                        $.ajax({
                            url: '../portal/getCurrentUserToken.action',
                            data: {},
                            async: false,
                            success: function (result) {
                                var url = page.sysUrl + "?loginId=" + encodeURIComponent(result.empCode) + "&institutionId=" + encodeURIComponent(result.cmsOrgId) + "&systemCode=PORTAL&token=" + encodeURIComponent(result.token);
                                window.open(url);
                            }
                        })
                    }else if (page.sysName.indexOf('壹起购') != -1) {
                        $.ajax({
                            url: '../portal/getLoginUserInfo.action',
                            data: {},
                            async: false,
                            success: function (result) {
                                var userInfo = window.top.vm.user.userInfo;
                                var url = page.sysUrl + "?empId=" + encodeURIComponent(userInfo.empId)
									+ "&orgName=" + encodeURIComponent(userInfo.orgName)
									+ "&tokenId=" + encodeURIComponent(userInfo.token)
                                    + "&otherOrgIdStr=" + encodeURIComponent(result.otherOrgIdStr)
									+ "&principalFlag=" + result.principalFlag
									+ "&userName=" + encodeURIComponent(userInfo.empCode)
									+ "&orgId=" + encodeURIComponent(userInfo.orgId)
									+ "&otherOrgId=" + encodeURIComponent(userInfo.cmsOrgId)
									+ "&lastLoginTime=" + encodeURIComponent(result.lastLoginTime)
									+ "&orgType=" + encodeURIComponent(userInfo.orgType)
									+ "&expireTime=" + encodeURIComponent(result.expireTime)
									+ "&empCode=" + encodeURIComponent(userInfo.empCode)
									+ "&empName=" + encodeURIComponent(userInfo.empName)
                                window.open(url);
                            }
                        })
                    } else if (page.sysUrl.indexOf('authentication/bridgeLogin') != -1) {
                        var token = BASE64(window.top.vm.user.userInfo.token);
                        var yhEmpCode = encodeURIComponent(window.top.vm.user.userInfo.yhEmpCode);
                        var compCode = encodeURIComponent(window.top.vm.user.userInfo.compCode);
                        window.open(page.sysUrl + "&fromSystemToken="+ token +"&fromSystemId=Portal&userCode="+yhEmpCode +"&userAccountType="+ compCode +"&bridgeReturnType=bridgeReturnRedirect");
                    } else {
                        //备战域名时修改子菜单中的域名 haungting 2019/11/09
                        href = window.top.vm.updateHost(page.sysUrl);
                        window.open(href);
                    }
                }
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.text.indexOf(value) !== -1;
            },
            handleCheck(node, treeNodes) {
                let vueThis = this;
                let getCheckedNodes = this.$refs.tree.getCheckedNodes().slice();
                vueThis.delFastMenu = [];
                vueThis.delAll = false;
                //let checkedNodes = treeNodes.checkedNodes.slice();
                // checkedNodes.forEach((item,index)=>{
                // 	if(item.children==null){
                // 		var isAdd = true;
                // 		//判断选中的节点如果存在右侧菜单栏中,将不在添加
                // 		for(var i=0;i<vueThis.setFastMenu.length;i++){
                // 			if(vueThis.setFastMenu[i].id==item.id){
                // 				isAdd = false;
                // 				break;
                // 			}
                // 		}
                // 		if(isAdd){
                // 			vueThis.setFastMenu.push(item);
                // 		}

                // 	}
                // })
                //获取选中的节点重新给右侧菜单栏赋值
                if (getCheckedNodes.length != 0) {
                    vueThis.setFastMenu = [];
                    getCheckedNodes.forEach(item => {
                        if (item.children == null) {
                        vueThis.setFastMenu.push(item)
                    }
                })
                } else {
                    vueThis.setFastMenu = [];
                }
                let storage = window.sessionStorage;
                if (storage.defaultfastMenu) {
                    let dt = JSON.parse(storage.defaultfastMenu);
                    vueThis.showfastMenu = dt;
                }
            },
            //全选
            handleCheckAllChange(val) {
                this.delFastMenu = val ? this.setFastMenu : [];
                this.indeterminate = false;
            },
            //右侧菜单栏勾选状态
            handleCheckedItemChange(value) {
                let checkedCount = value.length;
                this.delAll = checkedCount === this.setFastMenu.length;
                this.isIndeterminate = checkedCount > 0 && checkedCount < this.setFastMenu.length;
                this.delFastMenu = value.length > 0 ? value : [];
            },
            removeMenu() {
                var vueThis = this;
                if (this.delFastMenu.length === 0) {
                    return;
                }
                let delMenu = this.delFastMenu.slice();
                let fastMenu = this.setFastMenu.slice();
                for (var i = 0; i < delMenu.length; i++) {
                    var id = delMenu[i].id;
                    for (var j in fastMenu) {
                        if (fastMenu[j].id === id) {
                            fastMenu.splice(j, 1);
                        }
                    }
                }
                this.delFastMenu = [];
                this.setFastMenu = fastMenu;
                this.$refs.tree.setCheckedNodes(this.setFastMenu);
            },
            saveMenu: function () {
                var vueThis = this;
                var rightInfo = vueThis.setFastMenu.slice(0);
                var ids = [];
                for (var i = 0; i < rightInfo.length; i++) {
                    ids.push(rightInfo[i].id);
                }
                $('#saveBtn_save').linkbutton('disable');
                $.ajax({
                    url: 'saveUserMenu.action',
                    method: "post",
                    data: { 'ids': ids.join(','), 'empCode': window.top.vm.user.userInfo.empCode, 'cmsBaseOrgCode': window.top.vm.user.userInfo.cmsBaseOrgCode, 'oldUserMenu': oldUserMenu.join(',') },
                    success: function (data) {
                        vueThis.showfastMenu = rightInfo;
                        sessionStorage.setItem("showfastMenu", JSON.stringify(vueThis.showfastMenu))
                        $('#shortcutMenu').dialog('close');
                    }
                });
            },
            //公告一级标题拼接展示
            viewTitle(val) {
                if (val) {
                    return "【" + val + "】";
                } else {
                    return ""
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
            //树结构关闭
            dialogClose(val) {
                if (val != 1) {
                    $('#shortcutMenu').dialog('close');
                }
                if (this.showfastMenu.length == 0) {
                    this.$refs.tree.setCheckedNodes(this.showfastMenu);
                    this.setFastMenu = [];
                } else {
                    this.$refs.tree.setCheckedNodes(this.showfastMenu);
                    this.setFastMenu = this.showfastMenu;
                }
            },
            //dialog 添加
            dialogShow() {
                let ht = $(window).height(); //浏览器当前窗口可视区域高度
                let menuHt = $("#shortcutMenu").parent(".panel").height();
                $('#shortcutMenu').dialog({
                    top: ((ht - menuHt) / 2)
                });
                $('#shortcutMenu').dialog({ modal: true }).dialog('open'); $('#saveBtn_save').linkbutton('enable');
                if (this.showfastMenu.length == 0) {
                    this.$refs.tree.setCheckedNodes(this.showfastMenu);
                    this.setFastMenu = [];
                } else {
                    this.$refs.tree.setCheckedNodes(this.showfastMenu);
                    this.setFastMenu = this.showfastMenu;
                }
            },
            //获取文件类型
            getFileType(){
                let vueThis=this;
                let portalUrl = window.top.vm.portalUrl;
                let obj={
                    id: 1,
                    twoTypeName: "最新文件",
                    selected:true
                }
                vueThis.file.fileType.push(obj);
                $.ajax({
                    url: portalUrl+"file/getFileType.do",
                    xhrFields: {withCredentials: true},
                    method: "post",
                    data: {},
                    success: function (res) {
                        if(Array.isArray(res)&&res.length>0){
                            if(Array.isArray(res[0].fileTypeVoList)&&res[0].fileTypeVoList.length>0){
                                res[0].fileTypeVoList.forEach(val=>{
                                    vueThis.$set(val,"selected",false)
                                vueThis.file.fileType.push(val)
                            })
                                setTimeout(res => {
                                    var swiper = new Swiper('.file-type', {
                                        slidesPerView: 7,
                                        spaceBetween: 0,
                                        pagination: {
                                            el: '.swiper-pagination',
                                            clickable: true,
                                        },
                                        navigation: {
                                            nextEl: '.swiper-button-next',
                                            prevEl: '.swiper-button-prev',
                                        },
                                    });
                            }, 0)
                            }
                        }
                    }
                });
            },
            //文件列表
            getFileList(val){
                let vueThis=this;
                let portalUrl = window.top.vm.portalUrl;
                let params={};
                if(val){
                    params.twoTypeName=val.twoTypeName;
                    params.typeId=val.id;
                }
                $.ajax({
                    url: portalUrl+"file/getFileListByWeb.do",
                    xhrFields: {withCredentials: true},
                    method: "post",
                    data: params,
                    success: function (data) {
                        if(data.success){
                            if(Array.isArray(data.data)&&data.data.length>0){
                                data.data.forEach(res=>{
                                    res.fileTime=vueThis.timestampToTime(res.fileTime);
                                res.startTime=vueThis.timestampToTime(res.startTime);
                            })
                                vueThis.file.fileList = data.data;
                                $("#empty").hide();
                            }else{
                                vueThis.file.fileList=[];
                                $("#empty").show();
                            }
                        }else{
                            vueThis.file.fileList=[];
                            $("#empty").show();
                        }
                    },
                    error:function(err){
                        vueThis.file.fileList=[];
                    }
                });
            },
            //跳转文件列表页
            toFilelist(){
                var portalUrl=window.top.vm.portalUrl+"/file/forward.do";
                window.top.vm.openTab("制度列表", portalUrl, true, null, 'PORTAL', false, '4');
            },
            //切换文件类型
            fileTypeChange(item){
                this.file.fileType.forEach(res => {
                    res.selected = false;
            })
                item.selected = true;
                this.getFileList(item);
            },
            //预览附件
            viewFile(item) {
                //1.已读，2.打开附件
                var portalUrl=window.top.vm.portalUrl;
                var pdfUrl = item.fileUrl ;
                let strIndex = pdfUrl.lastIndexOf(".");
                let length=pdfUrl.length;
                let str=pdfUrl.substring(strIndex+1,length);
                str=str.toLowerCase();
                if(str!='pdf'){
                    this.$message({
                        showClose: true,
                        message: "暂时只支持pdf格式的文件预览!",
                        type: 'error',
                    });
                    return false;
                }
                var watermark = encodeURI(window.top.vm.user.userInfo.empCode + " " + window.top.vm.user.userInfo.empName);
                if(item.hasRead===0){
                    this.updateBrowse(item)
                }
                var url = portalUrl + "/scripts/portal/pdf/web/viewer.html?fileUrl="+pdfUrl+"&watermark=" + watermark ;
                window.top.vm.openTab("文件预览", url, true, null, 'PORTAL', false, '4');
            },
            //更新阅读量
            updateBrowse(item){
                let params={
                    primaryKey:item.id
                }
                let that=this;
                let portalUrl = window.top.vm.portalUrl;
                $.ajax({
                    url: portalUrl+"file/updateBrowse.do",
                    xhrFields: {withCredentials: true},
                    method: "post",
                    data: params,
                    success: function (res) {
                        item.hasRead=1; //已读
                    }
                });
            },
            //设置待办事项列表
            getTodoList(idx){
                //十分钟调一次0
                window.clearInterval(countTime); //初始清除倒计时
                let count=600;
                countTime=window.setInterval("countDown()",1000)
                countDown=()=>{
                    if(count==0){
                        window.clearInterval(countTime);
                        this.getTodoList();
                    }else{
                        count--;
                    }
                }
                //设置订单类，流程类列表初始数据
                let orderArr=[{name:'订单类',num:0,lists:[{name:'今日待接单订单',num:0,selected:false,childStatus:1},{name:'今日待揽件订单',num:0,selected:false,childStatus:2}]}]
                let todoArr=[{name:'流程类',num:0,lists:[{name:'待审流程',num:0,selected:false,childStatus:1},{name:'待阅流程',num:0,selected:false,childStatus:2}]}]
                let user=window.top.vm.user.userInfo;
                //order_accept  网点接单
                //order_got 网点揽件
                //to_do_list  待办事宜
                //read_list   待阅已阅
                //"UFE"  OMS menuItem.systemCode
                let codeRefMenu=window.top.vm.codeRefMenu,orderAccept,orderGot,todoList,readList;
                if(Object.keys(codeRefMenu).length==0){
                    var power=setTimeout(res=>{
                        let num=idx?idx:0;
                    num++;
                    if(num<6){
                        this.getTodoList(num)
                    }else{
                        window.clearTimeout(power)
                    }
                },5000)
                    return;
                }else{
                    orderAccept = window.top.vm.codeRefMenu["order_accept"];
                    orderGot = window.top.vm.codeRefMenu["order_got"];
                    todoList = window.top.vm.codeRefMenu["to_do_list"];
                    readList = window.top.vm.codeRefMenu["read_list"];
                }
                //拥有OMS系统“网点接单”、“网点揽件”且当前登录机构类型是网点、承包区
                if((user.orgType==30||user.orgType==40)&&((orderAccept&&orderAccept.systemCode=='OMS')||(orderGot&&orderGot.systemCode=='OMS'))){ //当前登录机构类型是不是网点或承包区
                    if(orderAccept&&orderAccept.systemCode=='OMS'){   //有网点接单权限，展示且为omg系统code
                        orderArr.forEach(res=>{
                            res.lists.forEach(val=>{
                            if(val.childStatus==1){
                            val.selected=true;
                        }
                    })
                    })
                    }
                    if(orderGot&&orderGot.systemCode=='OMS'){  //网点揽件权限 展示
                        orderArr.forEach(res=>{
                            res.lists.forEach(val=>{
                            if(val.childStatus==2){
                            val.selected=true;
                        }
                    })
                    })
                    }
                    let obj={
                        orderAccept:orderAccept,
                        orderGot:orderGot
                    }
                    this.todo.orderList=orderArr;
                    this.todayPlanOrderQuery(orderArr,user,obj);
                }else{
                    this.todo.orderList=[];
                    this.todo.isShow=2;
                }
                //流程判断
                if(!todoList&&!readList){
                    this.todo.todoList=[];
                    this.todo.isShow=1;
                }else{
                    if(todoList&&todoList.systemCode=='UFE'){   //有网点接单权限，且为omg系统code
                        todoArr.forEach(res=>{
                            res.lists.forEach(val=>{
                            if(val.childStatus==1){
                            val.selected=true;
                        }
                    })
                    })
                    }
                    if(readList&&readList.systemCode=='UFE'){  //网点揽件权限 展示
                        todoArr.forEach(res=>{
                            res.lists.forEach(val=>{
                            if(val.childStatus==2){
                            val.selected=true;
                        }
                    })
                    })
                    }
                    let obj={
                        todoList:todoList,
                        readList:readList
                    }
                    this.todo.todoList=todoArr;
                    this.getTaskCount(todoArr,obj);
                }
            },
            //请求ufe流程
            getTaskCount(todoArr,obj){
                let ufeUrl=window.top.vm.ufeUrl;
                let vueThis=this;
                if(ufeUrl){
                    if(obj.todoList||obj.readList){
                        $.ajax({
                            url: ufeUrl+"/mySasWork/getTaskCount.do?empCode="+window.top.vm.user.userInfo.empCode,
                            xhrFields: {withCredentials: true},
                            method: "GET",
                            data: {},
                            success: function (res) {
                                if(res.success){
                                    todoArr.forEach(val=>{
                                        if(!isNaN(Number(res.toDoCount))&&!isNaN(Number(res.toReadCount))){
                                        val.num=Number(res.toDoCount)+Number(res.toReadCount);
                                    }
                                    val.lists.forEach(item=>{
                                        if(item.childStatus==1){
                                        item.num=Number(res.toDoCount);
                                    }
                                    if(item.childStatus==2){
                                        item.num=Number(res.toReadCount);
                                    }
                                })
                                })
                                    vueThis.todo.todoList=todoArr;
                                }else{
                                    todoArr.forEach(val=>{
                                        val.num=0;
                                    val.lists.forEach(item=>{
                                        if(item.childStatus==1){
                                        item.num=0;
                                    }
                                    if(item.childStatus==2){
                                        item.num=0;
                                    }
                                })
                                })
                                    vueThis.todo.todoList=todoArr;
                                }
                            },
                            error:function(err){
                                todoArr.forEach(val=>{
                                    val.num='?';
                                val.lists.forEach(item=>{
                                    if(item.childStatus==1){
                                    item.num='?';
                                }
                                if(item.childStatus==2){
                                    item.num='?';
                                }
                            })
                            })
                                vueThis.todo.todoList=todoArr;
                            }
                        })
                    }
                }
            },
            //请求omg订单数量
            todayPlanOrderQuery(orderArr,user,Limit){
                //arr 原始数组  user用户信息 Limit 权限对象
                let timestamp=this.formatDate(new Date().getTime()).split(" ")[0];
                if(timestamp.indexOf('/')!=-1){
                    timestamp=timestamp.replace(/\//g,"-")
                }
                let postObj={
                    orderTimeBegin:timestamp+' '+'00:00:01',
                    orderTimeEnd:timestamp+' '+'23:59:59',
                }
                if(Limit.orderAccept){
                    postObj.acceptStatuses="1";
                    postObj.acceptSiteMay=user.cmsBaseOrgCode;
                }
                if(Limit.orderGot){
                    postObj.gotStatuses="2";
                    postObj.acceptSite=user.cmsBaseOrgCode;
                }
                if(Limit.orderAccept||Limit.orderGot){ //有网点接单或网点揽件权限，调接口查询数量
                    let vueThis=this;
                    this.loadDataDict(omsUrl=>{
                        $.ajax({
                        type: "POST",
                        url: omsUrl+"/oms-order-remind/common/todayPlanOrderQuery.do",
                        xhrFields: {
                            withCredentials: true
                        },
                        data:postObj,
                        success:function(res){
                            //todayAcceptCount 待接单数，todayGotCount 待揽件数
                            if(res.success){
                                let total="",num1=0,num2=0;
                                orderArr.forEach(val=>{
                                    val.lists.forEach(item=>{
                                    if(item.childStatus==1&&Limit.orderAccept){
                                    let count=res.data.todayAcceptCount;
                                    //有值且为数字，取当前值，否则为0
                                    num1=count&&!isNaN(Number(count))?Number(count):0;
                                    item.num=num1;
                                    item.selected=true;
                                }
                                if(item.childStatus==2&&Limit.orderGot){
                                    let count=res.data.todayGotCount;
                                    num2=count&&!isNaN(Number(count))?Number(count):0;
                                    item.num=num2;
                                    item.selected=true;
                                }
                            })
                                if((num1||num1==0)||(num2||num2==0)){
                                    val.num=num1+num2;
                                }
                            })
                                vueThis.todo.orderList=orderArr;
                            }else{
                                orderArr.forEach(val=>{
                                    val.lists.forEach(item=>{
                                    if(item.childStatus==1&&Limit.orderAccept){
                                    item.num=0;
                                    item.selected=true;
                                }
                                if(item.childStatus==2&&Limit.orderGot){
                                    item.num=0;
                                    item.selected=true;
                                }
                            })
                                val.num=0;
                            })
                                vueThis.todo.orderList=orderArr;
                            }
                        },
                        error:function(){
                            orderArr.forEach(val=>{
                                val.lists.forEach(item=>{
                                if(item.childStatus==1&&Limit.orderAccept){
                                item.num='?';
                                item.selected=true;
                            }
                            if(item.childStatus==2&&Limit.orderGot){
                                item.num='?';
                                item.selected=true;
                            }
                        })
                            val.num='?';
                        })
                            vueThis.todo.orderList=orderArr;
                        }
                    })
                })
                }
            },
            //获取omsUrl
            loadDataDict(callback){
                $.ajax({
                    url: "${pageContext.request.contextPath}"+'/portal/getDictDataByTypeClassCode.action',
                    async:false,
                    data: {typeClassCode: 'MSG_REMIND_SORCE_ADDRESS'},
                    success: function(result){
                        if(!result)return;
                        for (var i = 0; i < result.length; i++) {
                            if (result[i].typeName == "OMS_MESSAGE_PATH") {
                                callback(result[i].typeCode)
                            }
                        }
                    }
                })
            },
            //刷新待办列表
            todoRefresh(){
                this.getTodoList();
                this.settime(2)
            },
            //切换订单类，流程类页面
            todoNav(idx){
                if(idx==1){
                    this.todo.isShow=1;
                }else if(idx==2){
                    this.todo.isShow=2;
                }
            },
            //跳转待办事项页面
            toPage(val){
                if(val.selected){
                    this.loadDataDict(omsUrl=>{
                        if(val.name=="今日待接单订单"){
                        window.top.vm.openTab("网点接单", omsUrl+'/oms-order-main/orderAccept/forward.do?acceptFlag=true', true, null, 'OMS', true, '5');
                    }
                    if(val.name=="今日待揽件订单"){
                        window.top.vm.openTab("网点揽件", omsUrl+'/oms-order-main/orderGot/forward.do?gotFlag=true', true, null, 'OMS', true, '5');
                    }
                })
                    let ufeUrl=window.top.vm.ufeUrl;
                    if(ufeUrl){
                        if(val.name=="待审流程"){
                            window.top.vm.openTab("待办事宜", ufeUrl+"/mySasWork/forward.do", true, null, 'UFE', true, '5');
                        }
                        if(val.name=="待阅流程"){
                            window.top.vm.openTab("待阅已阅", ufeUrl+'/myRead/forward.do', true, null, 'UFE', true, '5');
                        }
                    }
                }
            }
        },
        watch: {
            filterText: function (val, old) {
                this.$refs.tree.filter(val);//elm-tree
            },
        }
    })
</script>
</body>

</html>