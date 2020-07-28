<%@ page language="java" pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公司公告</title>
		<link rel="stylesheet" type="text/css" href="../scripts/portal/viewer.min.css?v=01" />
		<%@include file="../common/common.jsp" %>
			<style>
				.noticeAuthor {
					float: right;
					text-align: right;
					font-weight: bold
				}
				.type-name{
					overflow: hidden;
					margin:15px 0 0px 15px;
				}
				.type-name li{
					float: left;
					border-right:1px solid #9c9c9c;
					padding:0 1em;
					font-size: 14px;
					cursor: pointer;
					color: #666;
				}
				.type-name li:last-of-type{
					border-right: none;
				}
				.type-name li.active{
					color: red;
				}
				#secondMenu li{
					border:none;
					color: #666;
					margin-bottom: 10px;
				}
				#secondMenu li:first-of-type.active{
					margin-left: 15px;
				}
				#secondMenu li.active{
					color: #fff;
					padding: 1px 10px;
					border-radius: 20px;
					background: -webkit-linear-gradient(right,#541b86,#7747ab); /* Safari 5.1 to 6.0 */
					background: -o-linear-gradient(right,#541b86,#7747ab); /* Opera 11.1 to 12.0 */
					background: -moz-linear-gradient(right,#541b86,#7747ab); /* Firefox 3.6 to 15 */
					background: linear-gradient(right,#541b86,#7747ab); /* 标准语法 (必须在最后) */
				}
				.notice-title{
					font-size: 16px;
					text-align: center;
					font-weight: bold;
				}
 				.notice-header {
					overflow: hidden; 
					color: #999999; 
					font-size:14px; margin:10px 0; 
				} 
 				.notice-header span{
					margin-right: 20px; 
				} 
 				.notice-header .fr{
					float: right; margin-right: 0; 
				}
				.view-flag{
					color:#999999;
				}
				.msgdtl {
					font-size: 14px;
					line-height: 20px;
				}
				/*公告详情图片预览*/
				.viewer-container{
					z-index: 9999 !important;
				}
			</style>
			<script type="text/javascript" src="../scripts/portal/viewer.min.js?v=02"></script>
			<script type="text/javascript">
				//页面加载
				$(function () {
					//加载角色dataGrid
					var columns = [[{ field: 'id', title: '', hidden: true },
					{
						field: 'deal', title: '操作', align: 'center', width: 60, formatter: function (value, rec, index) {
							if (value) {
								return value;
							}
							if(rec.viewFlag){
								return '<a class="icon-line iconfont uce-ck-details  view-flag  view-rows" title="查看详情" onclick="viewDetail(\'' + index + '\')"  href="javascript:void(0)"></a>';
							}else{
								return '<a class="icon-line iconfont uce-ck-details view-rows" title="查看详情" onclick="viewDetail(\'' + index + '\')" href="javascript:void(0)"></a>';
							}
							
						}
					},
					{ field: 'title', title: '公告标题', width: 200, formatter: function(value, rec, index) {
						return '<a style="color:#6a54ba" title="查看详情" onclick="viewDetail(\'' + index + '\')"  href="javascript:void(0)">'+ value +'</a>'
					} },
					{ field: 'type', title: '公告类别', width: 100, formatter: function(value, rec, index) {
						var oneName = rec.oneName;
						var towName = rec.twoName;
						var type = '';
						if (oneName != null && oneName != undefined) {
							type = oneName;
							if (towName != null && towName != undefined) {
								type = type + '-' + towName;
							}
						} else {
							if (towName != null && towName != undefined) {
								type = towName;
							}
						}
						return type;
					} },
					{ field: 'content', title: '公告内容', width: 300, formatter: function (data) {
							return data.replace(/<[^>]+>/g, '').replace(/&nbsp;/ig, '').replace(/\s+/g, '');
						}
					},
					{ field: 'author', title: '发文部门', width: 100, formatter: function (value, rec, index) {return value;} },
					
					/* {field : 'createEmp',title : '创建人',width : 80,align:'center',formatter: formatTip}, */
					{
						field: 'createTime', title: '发文时间', width: 120, align: 'center', formatter: function (value) {
							return formatData(value);
						}
					}
						/* ,
						{field : 'viewCount',title : '阅读数',width : 80,formatter: formatTip} */
					]];
					var dataGridParams = {
						url: 'findCompanyNoticeByPage.do',
						pageSize: 10,
						toolbar: '#tlbCompanyNotice',
						singleSelect: 'true',
						fitColumns: 'true'
					}
 					$("#parentId").next().hide();
 					$("#sonId").next().hide();
 					$("#mustReadFlag").next().hide();
					noticeTypeShow();
					newloadGrid('dgCompanyNotice', columns, dataGridParams);
					//加载“是否置顶”下拉列表
					uceDictCombobox("setTop", "ENABLE", { headerValue: false });
					uceDictCombobox("noticeType", "NOTICE_TYPE", { headerValue: true });
					var noticeId = getUrlParam('noticeId');
					if (null != noticeId && noticeId.length > 0) {
						viewDetailById(noticeId);
					}
				});
				//初始化数据字典
				initDictDatas("ENABLE,NOTICE_TYPE");
				var mustReadFlag = "";
				var parentId = "";
				var sonId = "";
				function getUrlParam(paraName) {
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
				}
				function viewDetailById(noticeId) {
					//var row = $('#dgCompanyNotice').datagrid('getRows')[index];
					var title = "";
					$.ajax({
						type: "POST",
						url: "findById.do",
						data: { id: noticeId },
						success: function (data, textStatus) {
							if (data == null || data.data == null) {
								return;
							}
							title = data.data.title;
							//$(".view-rows")[Number(index)].classList.add("view-flag");
							let oneName=data.data.oneName?data.data.oneName:"";
							let temp=data.data.oneName&&data.data.twoName?"-":"";
							let twoName=data.data.twoName?data.data.twoName:"";
							let createOrgName=data.data.createOrgName?data.data.createOrgName:"";
							let viewCount=(data.data.viewCount||data.data.viewCount==0)?data.data.viewCount:"";
 							let strHtml='<span>类型：'+oneName+temp+twoName+'</span>';
 								strHtml+='<span>发文部门:'+createOrgName+'</span>';
 								strHtml+='<span>发文时间:'+formatDate(data.data.createTime)+'</span>';
 								strHtml+='<span class="fr">阅读量:'+viewCount+'</span>';
 							$("#noticeHeader").html(strHtml);
							var noticeContent = data.data.content;
							noticeContent = noticeContent.replace(/data-original/g, "src").replace(/<img /g, "<img style='max-width:100%;max-height:100%'");
							$("#notice_content").html(noticeContent);
							$('#noticeDetail').dialog('options').title = "公告";
							$("#noticeTitle").text(title);
							let ht = $(window).height(); //浏览器当前窗口可视区域高度
							let menuHt = $("#noticeDetail").parent(".panel").height();
							$('#noticeDetail').dialog({
								top: ((ht - menuHt) / 2)
							});
							$('#noticeDetail').dialog({ modal: true }).dialog('open');
							$("#noticeDetail").scrollTop(0);
							setTimeout(res=>{
								$(".msgdtl img").on('click',function(){
									$('.msgdtl').viewer('update');
									$(".msgdtl").viewer();
								})
							})
						}
					});
				}
				function viewDetail(index) {
					var row = $('#dgCompanyNotice').datagrid('getRows')[index];
					
					$.ajax({
						type: "POST",
						url: "findById.do",
						data: { id: row.id },
						success: function (data, textStatus) {
							$(".view-rows")[Number(index)].classList.add("view-flag");
							let oneName=data.data.oneName?data.data.oneName:"";
							let temp=data.data.oneName&&data.data.twoName?"-":"";
							let twoName=data.data.twoName?data.data.twoName:"";
							let createOrgName=data.data.createOrgName?data.data.createOrgName:"";
							let viewCount=(data.data.viewCount||data.data.viewCount==0)?data.data.viewCount:"";
 							let strHtml='<span>类型：'+oneName+temp+twoName+'</span>';
 								strHtml+='<span>发文部门:'+createOrgName+'</span>';
 								strHtml+='<span>发文时间:'+formatDate(data.data.createTime)+'</span>';
 								strHtml+='<span class="fr">阅读量:'+viewCount+'</span>';
 							$("#noticeHeader").html(strHtml);
							var noticeContent = data.data.content;
							noticeContent = noticeContent.replace(/data-original/g, "src").replace(/<img /g, "<img style='max-width:100%;max-height:100%'");
							$("#notice_content").html(noticeContent);
							setTimeout(res=>{
								$(".msgdtl img").on('click',function(){
									$('.msgdtl').viewer('update');
									$(".msgdtl").viewer();
								})
							})
						}
					});

					$('#noticeDetail').dialog('options').title = "公告";
					$("#noticeTitle").text(row.title);
					let ht = $(window).height(); //浏览器当前窗口可视区域高度
					let menuHt = $("#noticeDetail").parent(".panel").height();
					$('#noticeDetail').dialog({
						top: ((ht - menuHt) / 2)
					});
					$('#noticeDetail').dialog({ modal: true }).dialog('open');
					$("#noticeDetail").scrollTop(0);
				}
				function formatDate(val) { 
					if(val){ 
						let time = new Date(val); 
						let y = time.getFullYear(); 
						let m = time.getMonth() + 1 >= 10 ? time.getMonth()+1 : "0" + (time.getMonth() + 1); 
						let d = time.getDate() >= 10 ? time.getDate() : "0" + time.getDate(); 
						let h = time.getHours()>=10 ? time.getHours() : "0" + time.getHours();
						let n = time.getMinutes() >= 10 ? time.getMinutes() : "0" + time.getMinutes();
						let s = time.getSeconds() >= 10 ? time.getSeconds() : "0" + time.getSeconds();
						return y + "/" + m + "/" + d + " " + h + ":"+n+":"+s; 
					}else{
						return ""
					}		
				}
				function checkEmpty(param) {
					return param == null ? "" : param
				}
				//查询
 				function findCompanyNoticeData() {
					$('#mustReadFlag').textbox('setValue',mustReadFlag); 
					$('#parentId').textbox('setValue',parentId);
					$('#sonId').textbox('setValue', sonId);
					if ($("#formFindCompanyNotice").form('validate')) {
						$('#dgCompanyNotice').datagrid('load', serializeFormObj("formFindCompanyNotice"));
					}
				}
				//重置
				function resetQuery() {
					$("#formFindCompanyNotice").form('reset');
				}
				//公告类型展示
				function noticeTypeShow() {
					$.ajax({
						url: '${pageContext.request.contextPath}/notice/findNoticeType.do',
						data: {},
						type: 'GET',
						success: function (data) {
							if(data.success){
								let noticeType=data.data;
								let addObj={
									typeName:"最新公告"
								}
								//模拟必读公告传参
								let addObj2 = {
									typeName: "必读公告",
 									mustReadFlag: 1
								}
								let strHtml ="",childHtml="";
								if(Array.isArray(noticeType)&& noticeType.length!=0){
									noticeType.unshift(addObj,addObj2);
									noticeType.forEach(item => {
										strHtml += '<li onclick="searchNotice(' + JSON.stringify(item).replace(/\"/g, "'") +')">' + item.typeName + '</li>';
									})
									$("#noticeTypeShow").append(strHtml);
									let li = document.getElementById("noticeTypeShow").getElementsByTagName("li");
									li[0].classList.add("active");
									$("#noticeTypeShow").on('click', 'li',function () {
										$(this).siblings('li').removeClass('active');
										$(this).addClass("active");
									})
									$("#secondMenu").on('click', 'li', function () {
										$(this).siblings('li').removeClass('active');
										$(this).addClass("active");
									})
								}
							}
						}
					});
				}
				//查找一级公告类型
				function searchNotice(item) {
					$("#secondMenu").html("");
					let childHtml="";
					if (Array.isArray(item.towTwoLeveInfoList) && item.towTwoLeveInfoList.length != 0) {
						item.towTwoLeveInfoList.forEach( val=> {
							childHtml += '<li onclick="searchSecond('+item.parentId+ ','+ val.twoLeveTypeId + ')">' + val.twoLeveTypeName +
    								'</li>'
						})
					}
					$("#secondMenu").html(childHtml);
 					if(item.typeName=='必读公告'){
						mustReadFlag=item.mustReadFlag;
						parentId="";
						sonId="";
 						findCompanyNoticeData();
					}else{
						mustReadFlag="";
						parentId=item.parentId;
						sonId="";
 						findCompanyNoticeData();
					}
				}
				//查找二级公告类型
 				function searchSecond(parentIdV,sonIdV){
					parentId=parentIdV;
					sonId=sonIdV;
 					findCompanyNoticeData(parentId,sonId);
				}
			</script>
	</head>

	<body class="easyui-layout">
		<!--公告表格 -->
		<div id="tlbCompanyNotice">
			<form action="#" id="formFindCompanyNotice">
				<div id="aa" class="easyui-accordion">
					<div title="" data-options="selected:true">
						<input id="parentId" class="easyui-textbox" name="parentId" style="display: none;" value="" />
						<input id="sonId" class="easyui-textbox" style="display: none;" name="sonId" value="" />
						<input id="mustReadFlag" class="easyui-textbox" style="display: none;" name="mustReadFlag" value="" />
						<ul class="search-form">
							<li>
								<input class="easyui-textbox" label="公告标题：" labelPosition="left" name="title" data-options="prompt:'请输入公告标题'">
							</li>
							<li>
								<input class="easyui-textbox" label="公告内容：" labelPosition="left" name="content" data-options="prompt:'请输入公告内容'">
							</li>
							<!-- <li>
							<select name="typeId" id="noticeType" label="公告类型：" style="width:100px;"></select>
						</li> -->
							<li>
								<input class="easyui-datetimebox" label="开始时间：" data-options="editable:false" name='createTime' id='createTime' data-options="prompt:'请输入开始时间'">
							</li>
							<li>
								<input class="easyui-datetimebox" label="结束时间：" data-options="editable:false" name='updateTime' id='updateTime' data-options="prompt:'请输入结束时间'">
							</li>
							<div>
								<a href="#" class="easyui-linkbutton search" onclick="findCompanyNoticeData()">查询</a>
								<a href="#" class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
							</div>
						</ul>
					</div>
				</div>
				<ul class="type-name" id="noticeTypeShow"></ul>
				<ul class="type-name" id="secondMenu"></ul>
			</form>
		</div>
		<table id="dgCompanyNotice" class="easyui-datagrid" data-options="fit:true"></table>

		<div id="noticeDetail" class="easyui-dialog" data-options="closed:true,title:'公告详情',top:50,resizable:true" style=" width:900px;height:500px;padding:20px 30px;display:none;"
		 v-cloak buttons="#divNoticeBtn">
		 	<div id="noticeTitle" class="notice-title"></div>
		 	<div id="noticeHeader" class="notice-header"></div>
			<div class="msgdtl" id="notice_content"></div>
			<div class="noticeAuthor" id="notice_author" style=""></div>
		</div>
		<div id="divNoticeBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="avascript:closeDialog('noticeDetail')">关闭</a>
		</div>
	</body>

	</html>