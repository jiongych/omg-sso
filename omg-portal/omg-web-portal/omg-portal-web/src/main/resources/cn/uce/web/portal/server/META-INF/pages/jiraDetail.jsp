<%@ page language="java" pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>工单详情</title>
		<%@include file="../common/common-ztree.jsp" %>
		<link rel="stylesheet" type="text/css" href="//static-file.uce.cn/uce-static/iconfont/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="../scripts/portal/jiraOrder.css?v=20190426" />
		<link rel="stylesheet" type="text/css" href="../scripts/portal/viewer.min.css" />
		<script type="text/javascript" src="../scripts/portal/portalUtil.js"></script>
		<script type="text/javascript" src="../scripts/portal/vue-2.3.min.js"></script>
		<script type="text/javascript" src="../scripts/portal/iconfont.js?v=20190426"></script>
		<style type="text/css">
			.iconfont {
				font-size: 18px;
				color: #ff9372;
			}

			.flow-form-block .iconfont {
				font-size: 20px;
				color: #5c2083;
			}

			.file-list {
				overflow: hidden;
			}

			* {
				margin: 0;
				padding: 0;
			}

			.file-list div {
				width: 80px;
				float: left;
				text-align: center;
				margin-left: 25px;
				margin-bottom: 10px;
				line-height: initial;
				cursor: pointer;
			}

			.file-list div img {
				width: 50px;
				height: 50px;
			}

			.file-loadown {
				color: blue;
			}

			.file-list div p {
				color: rgb(101, 92, 185);
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap
			}

			.product {
				overflow: auto;
			}
			.icono {
				width: 1em;
				height: 1em;
				vertical-align: -0.15em;
				fill: currentColor;
				overflow: hidden;
			}
		</style>
	</head>

	<body class="easyui-layout">
		<div class="flow-detail-container" id="app">
			<div class="product flow-form show">
				<div class="flow">
					<ul>
						<li v-for="(item,index) in flowList" :key="index">
							<div :class="{'active':status==item}">
								<p class="num" v-text="index+1"></p>
								<p v-text="item"></p>
							</div>
						</li>
					</ul>
				</div>
				
				<div class="flow-form-block">
					<div class="flow-form-block-head">
						<i class="icon iconfont uce-mulu"></i>
						<label class="flow-form-block-name">工单信息</label>
					</div>
					<div class="flow-form-feild-content">
						<table class="table-form" style="table-layout: fixed;">
							<tr>
								<td width="8%" style="text-align:right">问题编号：</td><td width="10%"><label id="key"></label></td>
								<td width="8%" style="text-align:right">联系人：</td><td width="10%"><label id="empName"></label></td>
								<td width="8%" style="text-align:right">联系人电话：</td><td width="10%"><label id="empTel"></label></td>
							</tr>
							<tr>
								<td width="8%" style="text-align:right">上报人：</td><td width="10%"><label id="reportEmpName"></label></td>
								<td width="8%" style="text-align:right">上报人工号：</td><td width="10%"><label id="empCode"></label></td>
								<td width="8%" style="text-align:right">上报人电话：</td><td width="10%"><label id="reportTel"></label></td>
							</tr>
							<tr>
								<td width="8%" style="text-align:right">上报人部门：</td><td width="15%"><label id="orgName"></label></td>
								<td width="8%" style="text-align:right">上报时间：</td><td width="15%"><label id="createTimeStr"></label></td>
								<td width="8%" style="text-align:right">业务类型：</td><td width="15%"><label id="bizTypeName"></label></td>
							</tr>
							<tr>
								<td width="8%" style="text-align:right;vertical-align: text-top;">运单/流程编号：</td>
								<td colspan="5">
									<label id="title"></label>
								</td>
							</tr>
							<tr>
								<td width="8%" style="text-align:right;vertical-align: text-top;">详情描述：</td>
								<td colspan="5">
									<label id="description"></label>
								</td>
							</tr>
							<tr>
								<td width="8%" style="text-align:right;vertical-align: text-top;">附件：</td>
								<td colspan="5" width="10%" style="word-wrap:break-word;word-break:break-all;">
									<div id="divAppend" class="file-list"></div>
									<div id="loadDown" class="file-list"></div>
								</td>
							</tr>
							<tr>
								<td width="8%" style="text-align:right;vertical-align: text-top;">解决方案：</td>
								<td colspan="5">
									<label id="solution"></label>
								</td>
							</tr>
						</table>
					</div>
					<hr/>
					<div class="flow-form-block-head">
						<div class="flow-form-block-head mt10">
							<svg class="icono" aria-hidden="true">
								<use xlink:href="#nesicon-lishijilu"></use>
							</svg>
							<label class="flow-form-block-name">历史记录</label>
						</div>
						<div class="process-record-scroll">
							<div class="process-record-content">
								<ul>
									<li class="l1 end">
										<span class="flow-exam-time" v-if="historyList.length!=0" v-text="formatDate(historyList[0].created)"></span>
										<span class="flow-exam-time" v-else v-text="result.createTimeStr"></span>
										<div class="c-dot">
											<svg class="icono" aria-hidden="true">
												<use xlink:href="#nesicon-yuandian"></use>
											</svg>
											<svg class="array icono" aria-hidden="true">
												<use xlink:href="#nesicon-upArray"></use>
											</svg>
										</div>
										<div class="l1-text">
											<div class="fl">
												<p>处理人：
													<span v-text="result.displayName?result.displayName:''"></span>
												</p>
											</div>
											<div class="fr" v-html="statusTxt(result.status)"></div>
										</div>
										<span class="line half-down flow-end"></span>
									</li>
									<li class="l1" v-for="(item,index) in historyList" v-if="historyList.length!=0" :key="index">
										<span class="flow-exam-time" v-text="formatDate(item.created)"></span>
										<div class="c-dot">
											<svg class="icono green" aria-hidden="true">
												<use xlink:href="#nesicon-iconcorrect"></use>
											</svg>
										</div>
										<div class="l1-text">
											<div class="fl">
												<p>处理人：<span v-text="item.displayName?item.displayName:''"></span></p>
												<p class="orange" :title="item.body">处理记录：<span v-text="item.body"></span></p>
											</div>
											<div class="fr green" v-html="item.status"></div>
										</div>
										<span class="line"></span>
										<span class="line half-up  flow-end" v-if="index==0"></span>
									</li>
									<li class="l1">
										<span class="flow-exam-time" v-text="result.createTimeStr"></span>
										<div class="c-dot">
											<svg class="icono green" aria-hidden="true">
												<use xlink:href="#nesicon-iconcorrect"></use>
											</svg>
										</div>
										<div class="l1-text">
											<div class="fl">
												<p>提交人：
													<span v-text="result.reportEmpName?result.reportEmpName:''"></span>
												</p>
												<p class="orange">处理记录：
													<span>提交工单</span>
												</p>
											</div>
											<div class="fr" style="color:#6a54ba">提交</div>
										</div>
										<span class="line half-up" :class="{'flow-end':historyList.length==0}"></span>
									</li>
								</ul>
							</div>
						</div>
						<div class="flow-form-feild-content">

						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="../scripts/portal/viewer.min.js"></script>
		<script type="text/javascript">
			//页面加载  
			var issueId = "${issueId}";
			var vm = new Vue({
				el: "#app",
				data() {
					return {
						historyList: [],
						result:"",
						status:"",
						flowList:["提交","待处理","处理中","待确认","已完成"],
					}
				},
				mounted() {
					
				},
				methods:{
					formatDate(val) {
						if(val) {
							let time = new Date(val);
							let y = time.getFullYear();
							let m = time.getMonth() + 1 >= 10 ? time.getMonth() + 1 : "0" + (time.getMonth() + 1);
							let d = time.getDate() >= 10 ? time.getDate() : "0" + time.getDate();
							let h = time.getHours() >= 10 ? time.getHours() : "0" + time.getHours();
							let n = time.getMinutes() >= 10 ? time.getMinutes() : "0" + time.getMinutes();
							let s = time.getSeconds() >= 10 ? time.getSeconds() : "0" + time.getSeconds();
							return y + "/" + m + "/" + d + " " + h + ":" + n + ":" + s;
						}
					},
					statusTxt(val){
						if (val == '待受理') {
							return '<span style="color:#f00">待处理</span>';
						} else if (val == '处理完成') {
							return '<span class="orange">待确认</span>';
						} else if (val == '关闭') {
							return '<span style="color:#999999">己完成</span>';
						} else {
							return '<span class="green">处理中</span>';
						}
					},
					statusList(val){
						if (val == '待处理') {
							return '<span style="color:#f00">待处理</span>';
						} else if (val == '待确认') {
							return '<span class="green">处理中</span>';
						} else if (val == '己完成') {
							return '<span class="green">处理中</span>';
						} else {
							return '<span class="green">处理中</span>';
						}
					}
				},
			})		
			$(document).ready(function () {
				$.ajax({
					url: '${pageContext.request.contextPath}/itsm/getIssueById.do',
					type: "GET",
					data: { issueId: issueId },
					success: function (data) {
						var attachmentIdArr = "";
						var attachmentNameArr = "";
						var picFlag = false;
						if (data != undefined && data != null) {
							var resutlFlag = data.success;
							if (!resutlFlag) {
								showInfoMsg(data.message);
								return;
							}
							$("#key").text(data.data.key);
							$("#empName").text(handleParam(data.data.empName));
							$("#empTel").text(handleParam(data.data.empTel));
							$("#reportEmpName").text(handleParam(data.data.reportEmpName));
							$("#empCode").text(handleParam(data.data.empCode));
							$("#reportTel").text(handleParam(data.data.reportTel));
							$("#orgName").text(handleParam(data.data.orgName));
							$("#createTimeStr").text(handleParam(data.data.createTimeStr));
							$("#bizTypeName").text(handleParam(data.data.bizTypeName));
							$("#title").text(handleParam(data.data.title));
							$("#description").text(handleParam(data.data.description));
							$("#solution").text(handleParam(data.data.solution));
							if(data.data){
								vm.result = data.data;
								let nVal=data.data.status;
								if (nVal) {
									if (nVal == '待受理') {
										vm.status = "待处理";
									} else if (nVal == '处理完成') {
										vm.status = "待确认";
									} else if (nVal == '关闭') {
										vm.status = "已完成";
									} else {
										vm.status = "处理中";
									}
								}
							}
							var jiraHandles = data.data.jiraHandles;
							if(null != jiraHandles && jiraHandles.length > 0) {
								// $.each(jiraHandles, function(i, val){
								// 	var recordDiv = document.getElementById("historyRecord");
								// 	recordDiv.innerHTML = recordDiv.innerHTML + JSON.stringify(val);
								// });
								vm.historyList=jiraHandles;
							}
							
							var picFile = data.data.attachments;
							var filePath = "${pageContext.request.contextPath}/itsm/downJiraFile.do?filePath=";
							if (null != picFile && picFile.length > 0) {
								$.each(picFile, function (i, val) {
									var divB = document.getElementById("loadDown");
									let strIndex = val.content.lastIndexOf(".");
									let length = val.content.length;
									let str = val.content.substring(strIndex + 1, length);

									if (/(bmp|gif|jpg|jpeg|png|BMP|GIF|JPG|PNG)$/i.test(str)) {
										attachmentIdArr += val.id + ",";
										attachmentNameArr += val.content + ",";
										picFlag = true;
									} else {
										var imgUrl = '../scripts/portal/images/view_other.png';
										if ('xls' == str || 'xlsx' == str) {
											imgUrl = '../scripts/portal/images/view_excel.png';
										} else if ('doc' == str || 'docx' == str) {
											imgUrl = '../scripts/portal/images/view_word.png';
										} else if ('ppt' == str || 'pptx' == str) {
											imgUrl = '../scripts/portal/images/view_ppt.png';
										} else if ('txt' == str) {
											imgUrl = '../scripts/portal/images/view_text.png';
										} else if ('rar' == str) {
											imgUrl = '../scripts/portal/images/view_rar.png';
										} else if ('zip' == str) {
											imgUrl = '../scripts/portal/images/view_zip.png';
										} else if ('pdf' == str) {
											imgUrl = '../scripts/portal/images/view_pdf.png';
										}
										divB.innerHTML = divB.innerHTML + "<div  onclick='loading(\"" + val.id + "\",\"" +  val.fileName + "\")'><img src='" + imgUrl + "' title='" + val.fileName + "'/><p>" + val.fileName + "</p></div>";
									}
								});
								if (picFlag) {
									var divA = document.getElementById("divAppend");
									divA.innerHTML = "<img src='../scripts/portal/images/load_dh.gif'>";
									getPicture(attachmentIdArr,attachmentNameArr);
								}
							}
						}
					}
				});
			});
			function getPicture(attachmentIdArr,attachmentNameArr) {
				$.ajax({
					url: '${pageContext.request.contextPath}/itsm/getPicture.do',
					type: "GET",
					data: { files: attachmentIdArr,fileNames:attachmentNameArr },
					success: function (data) {
						$("#divAppend").empty();
						if (data != undefined && data != null) {
							var picFile = data.data;
							$.each(picFile, function (i, val) {
								var divA = document.getElementById("divAppend");
								divA.innerHTML = divA.innerHTML + "<div><img src='data:image/jpeg;base64," + val.content + "' title='" + val.fileName + "'/><p>" + val.fileName + "</p></div>";
							});
							$("#divAppend").viewer();
						}
					}
				});
			}
			function handleParam(param) {
				if (null == param || 'undefined' == param) {
					return "";
				}
				return param;
			}
			function loading(id,fileName) {
				location.href = "${pageContext.request.contextPath}/itsm/downJiraFile.do?id=" + id + "&fileName=" + fileName + "&issueId=" + issueId;
			}
		</script>
	</body>

	</html>