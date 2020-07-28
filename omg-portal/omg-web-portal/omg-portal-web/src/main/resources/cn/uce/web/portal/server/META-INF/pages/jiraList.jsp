<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>我的工单</title>
	    <%@include file="../common/common-ztree.jsp" %>
	    <style type="text/css">
			div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
			div#rMenu ul li{
				margin: 1px 0;
				padding: 0 5px;
				cursor: pointer;
				list-style: none outside none;
				background-color: #DFDFDF;
			}
			#dlgUserRoleRel .datagrid-cell{
				padding:0px!important
			}
			
			.uploaddiv{
				width: 20%;
			    float: left;
			}
			.uploaddiv img {
			    width: 78px;
			    vertical-align: middle;
			    margin-left: 15px;
			    margin-top: 5px;
			    cursor: pointer;
			}
			.uploaddiv p {
			    height: 15px;
			    line-height: 15px;
			    overflow: hidden;
			    display: block;
			    margin-top: 0;
			}
			.imgList{
				position:relative;
				text-align: center;
			}
			.imgList .delect{
				position: absolute;
				top: -9px;
				right: 6px;
				z-index: 2;
				width: 25px;
				height: 25px;
			}
		</style>
	</head>
		
	<body class="easyui-layout">
		<script type="text/javascript">
			var limitSize=20; //图片最大限制(M)
			var processValue = "";
			var localHref = window.location.href.substring(0,window.location.href.indexOf("${pageContext.request.contextPath}"));
		  	//页面加载  
		    $(document).ready(function(){
				initDictDatas('PROMPT');
				
				$("#vilidTime").hide();
				var	fileOptions=[];	//上传图片列表	
				let score=5;
			  	showScore(score);
				$("#score").on('click','img',function(){
						let index = $(this).index();  //获取索引下标 也从0开始
						let starOk="",star="";
						$("#score").html("");	
						for(var j=0;j<=index;j++){
							starOk+='<img src="../scripts/portal/images/star-ok.png">'
						}
						$("#score").append(starOk)
						for(var z=index+1;z<=4;z++){
							star+='<img src="../scripts/portal/images/star.png">'
						}
						$("#score").append(star);
						$("#scoreId").textbox('setValue', index+1);
				})
				//加载表格
                var columns = [[
                    {field:'key',title:'编号',align:'center',width: '6%',formatter: function(value,row,index){
						return '<a style="color:#6a54ba" href="javascript:void(0);" onclick="viewDetail(\'' + row.id + '\',\'' + row.key + '\')">'+ row.key +'</a>';
					}},
                    {field:'description',title:'详情描述',width: '23%',formatter: formatTip},
                    {field:'solution',title:'解决方案',width: '28%',formatter: formatTip},
                    {field:'displayName',title:'当前处理人',align:'center',width: '7%',formatter: function(value) {
						if (null == value || value == '' || value == '未分配') {
							return '';
						}
						return value;
					}},
                    /* {field:'issueType',title:'问题类型',align:'center',width: '7%',formatter: formatTip}, */
                    {field:'status',title:'状态',align:'center',width: '4%',formatter: function(value){
                    	if (value == '待受理') {
							return '<span style="color:red">待处理</span>';
						} else if (value == '处理完成') {
							return '<span style="color:#FF9900">待确认</span>';
						} else if (value == '关闭') {
							return '<span style="color:#999999">己完成</span>';
						} else {
							return '<span style="color:#00CC00">处理中</span>';
						}
						return value;
                    }},
                    {field:'createTimeStr',title:'上报时间',align:'center', width: '10%',formatter: function(value){
                    	return formatData(value);
                    }},
                    {field:'score',title:'处理结果确认',align:'center',width: '12%',formatter: function(value,row,index) {
						
						if (row.status == "处理完成") {
							return '<a onclick="openScore(\'' + row.id+ '\',2, \'处理完成\')"><img alt="" src="../scripts/portal/images/face_y.png" style="vertical-align: bottom;"><span style="margin-left: 1px"/>未解决</span></a><a onclick="openScore(\'' + row.id+ '\',1,\'处理完成\')" ><img style="margin-left:15px;" alt="" src="../scripts/portal/images/flower_y.png"/><span>已解决</span></a>';
						}
						var thirtyCheck = dataCompareThirty(row.endTime);
						if (!thirtyCheck && row.status == "关闭" && row.incSatisfaction == null) {
							return '<a ><img src="../scripts/portal/images/face_n.png" style="vertical-align: bottom;"/><span style="margin-left: 1px;color:#ada9a9">未解决</span></a><a onclick="openScore(\'' + row.id+ '\',1, \'关闭\')"><img style="margin-left:15px;" src="../scripts/portal/images/flower_y.png"/><span>已解决</span></a>';
						}
						return '<a><img src="../scripts/portal/images/face_n.png" style="vertical-align: bottom;"/><span style="margin-left: 1px;color:#ada9a9">未解决</span></a><a><img style="margin-left:15px;" src="../scripts/portal/images/flower_n.png"/><span style="color:#ada9a9">已解决</span></a>';
					}}
                ]];
		       var dataGridParams = {
					url : '${pageContext.request.contextPath}/itsm/getIssueList.do',
					pageSize : 20,
					toolbar : '#tlbOperatorLog',
					singleSelect : 'true',
					queryParams : {},
					fitColumns : 'false',
					onSelect : function(index,row){
						
					},
					onLoadSuccess : function(data) {
						statusParam = data.otherParam.searchIssueType;
						confirmStatus = data.otherParam.confirmStatus;//待确认
						$('#waitStatusA').css('color','#464c5b');
						$('#processStatusA').css('color','#464c5b');
						$('#confirmStatusA').css('color','#464c5b');
						$('#overStatusA').css('color','#464c5b');
						$('#allStatusA').css('color','#464c5b');
						if (statusParam == 15) {//待处理
							
							$('#waitStatusA').css('color','red');
						} else if (statusParam == 30) {//处理中
							
							$('#processStatusA').css('color','red');
						} else if (statusParam == 50) {//待确认
						
							$('#confirmStatusA').css('color','red');
						} else if (statusParam == 60) {//已完成
							
							$('#overStatusA').css('color','red');
						} else {
							$('#allStatusA').css('color','red');
						}
						$("#confirmStatus").text(confirmStatus);
					}
				}
				$("#statusParam").next().hide();
		       	
				newloadGrid('dgOperatorLog', columns, dataGridParams);
				$("#startDate").textbox('textbox').bind("click", function () {
					var startDate = $("#startDate").textbox('getValue');
					if ("" == startDate) {
						var myDate = new Date();
						var lw = new Date(myDate - 1000 * 60 * 60 * 24 * 30);//最后一个数字30可改，30天的意思
						var lastY = lw.getFullYear();
						var lastM = lw.getMonth()+1;
						var lastD = lw.getDate();
						var startdateValue =lastY+"-"+(lastM < 10 ? "0" + lastM : lastM)+"-"+(lastD < 10 ? "0"+ lastD : lastD) + " 00:00:00";//三十天之前日期
						$("#startDate").textbox('setValue', startdateValue);
					}
				})
				$("#endDate").textbox('textbox').bind("click", function () {
					var endDate = $("#endDate").textbox('getValue');
					if ("" == endDate) {
						var lw = new Date();//最后一个数字30可改，30天的意思
						var lastY = lw.getFullYear();
						var lastM = lw.getMonth()+1;
						var lastD = lw.getDate();
						var enddateValue =lastY+"-"+(lastM < 10 ? "0" + lastM : lastM)+"-"+(lastD < 10 ? "0"+ lastD : lastD) + " 23:59:59";
						$("#endDate").textbox('setValue', enddateValue);
					}
				})
				
				var maxlength = 255;
				var maxlengthScore = 100;
				$('#description2').textbox({
					inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
		                keyup:function(event){
							var value = event.currentTarget.value;
		                    var rem = maxlength - value.length; 
		                    if (rem < 0) {
		                    	rem =0;
		                    }
		                    $("#cost_tpl_title_length2").html(rem); 
		                }, 
		                paste:function(e){
		    				var pastedText = undefined;
		    				if (window.clipboardData && window.clipboardData.getData) { // IE
		    				    pastedText = window.clipboardData.getData('Text');
		    				} else {
		    				    pastedText = e.originalEvent.clipboardData.getData('Text');//e.clipboardData.getData('text/plain');
		    				}
		    				var selectText = window.getSelection?window.getSelection().toString():document.selection.createRange().text;
		    				var desc = e.currentTarget.value;
		    				var num = maxlength - (desc.length - selectText.length + pastedText.length);
		                    if (num < 0) {
		                    	num =0;
		                    }
		    				$("#cost_tpl_title_length2").html(num); 
		                }
					})
				});
				$('#description1').textbox({
					inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
		                keyup:function(event){
							var value = event.currentTarget.value;
		                    var rem = maxlength - value.length; 
		                    if (rem < 0) {
		                    	rem =0;
		                    }
		                    $("#cost_tpl_title_length1").html(rem); 
		                }, 
		                paste:function(e){
		    				var pastedText = undefined;
		    				if (window.clipboardData && window.clipboardData.getData) { // IE
		    				    pastedText = window.clipboardData.getData('Text');
		    				} else {
		    				    pastedText = e.originalEvent.clipboardData.getData('Text');//e.clipboardData.getData('text/plain');
		    				}
		    				var selectText = window.getSelection?window.getSelection().toString():document.selection.createRange().text;
		    				var desc = e.currentTarget.value;
		    				var num = maxlength - (desc.length - selectText.length + pastedText.length);
		                    if (num < 0) {
		                    	num =0;
		                    }
		    				$("#cost_tpl_title_length1").html(num); 
		                }
		                     
					})
				});
				
				$('#addScoreCommentId').textbox({
					inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
		                keyup:function(event){
							var value = event.currentTarget.value;
		                    var rem = maxlengthScore - value.length; 
		                    if (rem < 0) {
		                    	rem =0;
		                    }
		                    $("#cost_tpl_title_length3").html(rem); 
		                }, 
		                paste:function(e){
		    				var pastedText = undefined;
		    				if (window.clipboardData && window.clipboardData.getData) { // IE
		    				    pastedText = window.clipboardData.getData('Text');
		    				} else {
		    				    pastedText = e.originalEvent.clipboardData.getData('Text');//e.clipboardData.getData('text/plain');
		    				}
		    				var selectText = window.getSelection?window.getSelection().toString():document.selection.createRange().text;
		    				var desc = e.currentTarget.value;
		    				var num = maxlengthScore - (desc.length - selectText.length + pastedText.length);
		                    if (num < 0) {
		                    	num =0;
		                    }
		    				$("#cost_tpl_title_length3").html(num); 
		                }
		                     
					})
				});
				var ttFlag = 0;
				$('#tt').tabs({
					border:true,
					onSelect:function(title,index) {
						var checkCon = true;
						if (index == 0) {
							var description2 = $("#description2").textbox('getValue');
							var file2 = $("#file2").textbox('getValue');
							if (description2 == "" && file2 == "") {
								checkCon = false;
							} 
							if(imageTotal <= 0) {
								checkCon = false;
							}
						} else {
							var description1 = $("#description1").textbox('getValue');
							var file1 = $("#file1").textbox('getValue');
							if (description1 == "" && file1 == "") {
								checkCon = false;
							} 
							if(imageTotal <= 0) {
								checkCon = false;
							}
						}
						if(index == 0) {
							$("#issueTitleId1").css("color","red");
							$("#issueTitleId2").css("color","");
						} else {
							$("#issueTitleId2").css("color","red");
							$("#issueTitleId1").css("color","");
						}
						if (ttFlag == 0 && checkCon) {
							$.messager.confirm('切换上报问题类型','切换会清除内容，确认切换吗？',function(r){
							if (r){
								$('#img_preview1').empty();
								$('#img_preview2').empty();
								$("#saveJiraIssueForm1").form('clear');
								$("#saveJiraIssueForm2").form('clear');
								initInfo();
								$('#tt').tabs('select', index);
								$('#issueTypeId1').textbox('setValue','10306');
								$('#issueTypeId2').textbox('setValue','10308');
								if(index == 0) {
									$("#issueTitleId1").css("color","red");
								} else {
									$("#issueTitleId2").css("color","red");
								}
								$("#cost_tpl_title_length1").html(255); 
								$("#cost_tpl_title_length2").html(255); 
								$('#description1').textbox('setValue','');
								$('#description2').textbox('setValue','');
								imageTotal = 0;
							} else {
								ttFlag = 1;
								$('#tt').tabs('select', index == 1 ? 0 : 1);
								setTimeout(function(){
									ttFlag = 0;
								},100);
							}
						});
					}					
				}
			});
			var empName = "";
			var userTel = "";
			findCurrentUserInfo();
			replaceTab();
		});
		var imageTotal = 0
		initDictDatas("JIRA_TYPE");
		//过滤空格
		function replaceTab() {
			$("#descriptionSearch").textbox("textbox").blur(function(){
				var descriptionValue =  $("#descriptionSearch").textbox('getValue');
				descriptionValue = descriptionValue.replace(/\s+/g,'');
				$('#descriptionSearch').textbox('setValue',descriptionValue);
			});
			$("#issueId").textbox("textbox").blur(function(){
				var descriptionValue =  $("#issueId").textbox('getValue');
				descriptionValue = descriptionValue.replace(/\s+/g,'');
				$('#issueId').textbox('setValue',descriptionValue);
			});
		}
		function findCurrentUserInfo() {
			$.ajax({
				url:'${pageContext.request.contextPath}/itsm/findCurrentUserInfo.do',
				type:"POST",
				data:{},
				success:function (data) {
					// 判断数据是否存在，存在构建echarts需要的数据格式
					if(data != undefined && data != null) {
						empName = data.empName;
						userTel = data.userTel;
					}
				}
			});	
		}
		//查询
	    function findOperatorLog(typeNum) {
	    	statusParam = -1;
			if (typeNum != undefined) {
				statusParam = typeNum;
			}
			var startDate =  $("#startDate").textbox('getValue');
			var endDate =  $("#endDate").textbox('getValue');
			startDate = startDate.replace("-","/");
			endDate = endDate.replace("-","/");
			var startCom = new Date(Date.parse(startDate));  
			var endCom = new Date(Date.parse(endDate));  
			if (startCom >= endCom) {
				$("#vilidTime").show();
				setTimeout(function(){//两秒后跳转
					$("#vilidTime").hide();
				},3000);
				return;
			}
			
			$('#statusParam').textbox('setValue',statusParam);
			$('#dgOperatorLog').datagrid("load", serializeFormObj("formfindOperatorLog"));
		}
	  	//重置
		function resetQuery(){
		  $("#formfindOperatorLog").form('reset');
		}

		function viewDetail(id,key) {
			var title = "工单详情(" + key + ")" ;
			
			var url = localHref + "${pageContext.request.contextPath}/itsm/jiraDetail.do";
			
			openTabCrossDomain(null,{issueId:id},{title:title,systemCode:"PORTAL",url:url});
		}
		function initInfo() {
			$('#empName1').textbox('setValue',empName);
			$('#telephone1').textbox('setValue',userTel);
			$('#empName2').textbox('setValue',empName);
			$('#telephone2').textbox('setValue',userTel);
		}
		//打开IT服务台上报页面
		function ITServiceReport() {
			var url = localHref + "${pageContext.request.contextPath}/itsm/createIssue.do";
			openTabCrossDomain('create_issue',{},{title:'上报IT服务台',systemCode:"PORTAL",url:url});
		}
		
		function deleteImage(divId, num) {
			imageTotal--;
			$("#" + divId).remove();
		}
		//保存问题上报信息
		function saveJiraIssue(btn) {
			var tab = $('#tt').tabs('getSelected');
			var index = $('#tt').tabs('getTabIndex',tab);
			let fileObjs=$("input[name=file]")[index].files;
			if(!fileCheck(fileObjs)){
				return;
			}
			var saveJiraIssueForm = "saveJiraIssueForm1";
			if (1 == index) {
				saveJiraIssueForm = "saveJiraIssueForm2";
			}
			$(btn).linkbutton('disable');
			var url = "${pageContext.request.contextPath}/jira/createIssue.do";
			$("#" + saveJiraIssueForm).form('submit',{
	  			url : url,
	  			task: function(result){
					closeDialog('jiraAddInfo');
					reloadDatagrid('dgOperatorLog');
	  			},
	  			finalTask:function(){
	  				$(btn).linkbutton('enable');
	  			}
	  		});
			
		}
		
		function fileCheck(fileObjs){
			var flag = true;
			if(imageTotal >= 10 || (fileObjs && fileObjs.length > 10)){
				$.messager.show({
					title:'提示',
					msg:'最多只能上传10张图片!',
					showType:'show',
					timeout:3000
				});
				flag = false;
			}
			var fileArr = Array.from(fileObjs);
			fileArr.forEach((item,index) => {
				var nameB = item.name;
				var fileType = nameB.substring(nameB.lastIndexOf('.') + 1)
				if (fileType != "png" && fileType != "jpg" && fileType != "JPEG" && fileType != "PNG" && fileType != "JPG" && fileType != "jpeg") {
					$.messager.show({
						title:'提示',
						msg:'请上传png、jpg、JPEG格式的图片',
						showType:'show',
						timeout:3000
					});
					flag = false;
				}
				var fileSize = item.size/(1024*1024);
				if(fileSize > limitSize){
					$.messager.show({
						title:'提示',
						msg:'单张图片不可大于'+limitSize+'M!',
						showType:'show',
						timeout:3000
					});
					flag = false;
				}
			});
			return flag;
		}
		function addScore(btn) {
			$(btn).linkbutton('disable');
			var url = "${pageContext.request.contextPath}/itsm/addComment.do";
		    if (processValue == "关闭") {
				url = "${pageContext.request.contextPath}/itsm/insertComment.do";
			}
			$("#saveYJiraScoreForm").form('submit',{
	  			url : url,
	  			task: function(result){
					closeDialog('jiraAddYScore');
					reloadDatagrid('dgOperatorLog');
	  			},
	  			finalTask:function(){
	  				$(btn).linkbutton('enable');
	  			}
	  		});
		}
		function openScore(key, flag, process) {
			processValue = process;
			$("#saveYJiraScoreForm").form('clear');
			$('#jiraAddYId').textbox('setValue',key);
			$("#scoreId").next().hide();
			$("#jiraAddYId").next().hide();
			$("#isSuccessFlagId").next().hide();
			//问题未解决
			if (flag == 2) {
				$('#jiraAddYScore').dialog('options').title = '问题未解决';
				var addScoreComment = DictDatas['PROMPT'][2];
				if (addScoreComment == undefined) {
					addScoreComment = "请填写未解决原因，我们将极力为您处理！";
				} else {
					addScoreComment = DictDatas['PROMPT'][2].typeName;
				}
				$("#addScoreCommentId").textbox({prompt:addScoreComment}); 
				$("#addScoreSpanId").html("追加描述");
				$('#isSuccessFlagId').textbox('setValue',flag);
				$("#score").hide();
			} else {
				//问题己解决
				$('#jiraAddYScore').dialog('options').title = '己解决并评价';
				var addScoreComment = DictDatas['PROMPT'][3];
				if (addScoreComment == undefined) {
					addScoreComment = "请说出你对系统的其它建议......";
				} else {
					addScoreComment = DictDatas['PROMPT'][3].typeName;
				}
				$('#addScoreCommentId').textbox({prompt:addScoreComment});
				$("#addScoreSpanId").html("请大胆说出你的想法吧");
				$('#isSuccessFlagId').textbox('setValue',flag);
				$('#scoreId').textbox('setValue',5);
				$("#score").show();
				$("#score").html("");  //点击评价，初始化星级
				this.showScore(5)
			}
			
			$("#cost_tpl_title_length3").html(100); 
		    $('#addScoreCommentId').textbox('setValue','');
			$('#jiraAddYScore').dialog({modal:true}).dialog('open');
		}
		//星级展示
		function showScore(num){
			for(var i=0;i<num;i++){
				$("#score").append("<img src='../scripts/portal/images/star-ok.png'>");
			}
		}
		
		/**
		 * 计算是否超过30天
		 *
		*/
		function dataCompareThirty(beforeData) {
			var now = new Date();
			//30天前的日期
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate()-29, now.getHours(), now.getMinutes(), now.getSeconds());
			//问题上报时间
			var d2 = new Date(beforeData);
			return d1 > d2;
		}
		</script>
		<!--列表-->
	    <div data-options="region:'center',border:false" style="width:600px;">
	    	<div class="easyui-layout" data-options="fit:true,border:false">
				<div data-options="region:'center',border:true" style="width:230px;height:100px;border:5px;">
					<div id="tlbOperatorLog" style="padding:5px;height:auto">
						<form action="#" id="formfindOperatorLog">
							<div id="aa" class="easyui-accordion">   
				    			<div title="" data-options="selected:true">
								<input id="statusParam" class="easyui-textbox" name="statusParam" value=""/>
								    <table class="table" style="width: auto; border:0px; padding-left: 20px;" >
										<tbody>
											<tr>
												<td>详情描述：</td>
												<td>
													<input class="easyui-textbox" id="descriptionSearch" name="descriptionSearch" style="width:300px" />
												</td>
												<td style="width:30px;"></td>
												<td>问题编号：</td>
												<td>
													<input class="easyui-textbox" id="issueId" name="issueId"  style="width:100%" />
												</td>
												<!-- <td>问题类型：</td>
												<td>
													<input class="easyui-combobox" id="issuetype" name="issuetype" style="width:100%" />
												</td> -->
												<td style="width:30px;"></td>
												<td>上报时间：</td>
												<td>
													<input class="easyui-datetimebox"  data-options="editable:false" name='startDate' id='startDate'/>
												</td>
												<td>至</td>
												<td>
													<input class="easyui-datetimebox" data-options="editable:false" name='endDate' id='endDate'/>
												</td>
												<td style="width:30px;"></td>
												<td>
													<a href="#" class="easyui-linkbutton search" onclick="findOperatorLog()">查询</a> 
													<a href="#" class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
													<div id="vilidTime" style="color:red;display:inline;">查询上报的结束时间必须大于开始时间</div>
												</td>
											</tr>
										</tbody>
									</table>  
								</div>
						    </div>
							<div style="line-height:20px;font-size:14px;font-family:微软雅黑;margin-left:15px;">
								<p><a id="allStatusA" onclick="findOperatorLog(1)" style="margin-left:15px;margin-right:10px;">全部<!-- (<span id="allStatus"></span>) --></a>
								<span>|</span><a id="waitStatusA" onclick="findOperatorLog(15)" style="margin-left:15px;margin-right:10px;">待处理<!-- (<span id="waitStatus"></span>) --></a>
								<span>|</span><a id="processStatusA" onclick="findOperatorLog(30)" style="margin-left:15px;margin-right:10px;">处理中<!-- (<span id="processStatus"></span>) --></a>
								<span>|</span><a id="confirmStatusA" onclick="findOperatorLog(50)" style="margin-left:15px;margin-right:10px;">待确认(<span id="confirmStatus"></span>)</a>
								<span>|</span><a id="overStatusA" onclick="findOperatorLog(60)" style="margin-left:15px;margin-right:10px;">已完成<!-- (<span id="overStatus"></span>) --></a>
								<span>|</span>
								<img style="position: absolute;margin-top:3px;margin-left:5px;" src="../scripts/portal/images/kefu.png"/>
								<button type="button" style="border-radius: 5px;height:25px;background-color: #EE7800;color:#ffffff;border:none;margin-left:30px;cursor:pointer" onclick="ITServiceReport()">上报IT服务台</button>
							</div>							
						</form>
					</div>
					<table id="dgOperatorLog" class="easyui-datagrid" data-options="fit:true"></table>
				</div>
	    	</div>
	    </div>
		
		<div id="jiraAddInfo" class="easyui-dialog" data-options="closed:true,title:'IT服务台(IT热线021-31290988)',top:5" style="width:750px;height:600px;padding:0px 0px;display:none;" v-cloak buttons="#divJiraAddBtn">
			<div id="tt" class="easyui-tabs" style="width:100%;height:100%;" data-options="fit:true">
				<div title="<div id='issueTitleId1'>IT问题</div>" style="padding:20px;display:none;">
					<form enctype="multipart/form-data" method="post" id="saveJiraIssueForm1">
						<span style="color:red;">*</span><span>详情描述</span>
						<input id="issueTypeId1" class="easyui-textbox" name="issueTypeId" value="10306"/>
						<input id="description1" class="easyui-textbox" data-options="multiline:true,validType:'length[1,255]'" value="" style="width:550px;height:150px" name="description" required="true" >
						<br><span style="font-size:15px;float:right;margin-right:7%;">还可以输入<span id="cost_tpl_title_length1" style="font-size:18px;color:red">255</span>字</span> 
						<div style="margin-top:20px;">
							<span style="margin-right:5px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名</span>
							<input id="empName1" name="empName" class="easyui-textbox" style="width:180px" data-options="validType:'length[0,30]'" >
							<span style="margin-right:5px;margin-left:30px;">电话</span>
							<input id="telephone1" name="telephone" class="easyui-textbox" style="width:180px" data-options="validType:'length[0,30]'" >
						</div>
						<div style="margin-top:20px;"> 
							<span style="margin-right:5px;margin-left:20px">附件:</span>
							<input id="file1" name="file" type="text" style="width:300px">
							<br/><lable style="color:red">*</lable><lable>请上传png、jpg、JPEG格式的图片，最多10个</lable>
							<div id="img_preview1"></div>
						</div>
					</form>
				</div>
				<div title="<div id='issueTitleId2'>系统功能建议</div>" style="overflow:auto;padding:20px;display:none;">
					<form enctype="multipart/form-data" method="post" id="saveJiraIssueForm2">
						<span style="color:red;">*</span><span>详情描述</span>
						<input id="issueTypeId2" class="easyui-textbox" name="issueTypeId" value="10308"/>
						<input id="description2" class="easyui-textbox" data-options="multiline:true,validType:'length[1,255]'" value="" style="width:550px;height:150px" name="description" required="true" >
						<br><span style="font-size:15px;float:right;margin-right:7%;">还可以输入<span id="cost_tpl_title_length2" style="font-size:18px;color:red">255</span>字</span> 
						<div style="margin-top:20px;">
							<span style="margin-right:5px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名</span>
							<input id="empName2" name="empName" class="easyui-textbox" style="width:180px" data-options="validType:'length[0,30]'" >
							<span style="margin-right:5px;margin-left:30px;">电话</span>
							<input id="telephone2" name="telephone" class="easyui-textbox" style="width:180px" data-options="validType:'length[0,30]'" >
						</div>
						<div style="margin-top:20px;"> 
							<span style="margin-right:5px;margin-left:20px">附件:</span>
							<input id="file2" name="file" type="text" style="width:300px">
							<br/><lable style="color:red">*</lable><lable>请上传png、jpg、JPEG格式的图片，最多10个</lable>
							<div id="img_preview2"></div>
						</div> 
					</form>
				</div>
			</div>
		</div>
		<div id="divJiraAddBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton save" onclick="saveJiraIssue(this)">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('jiraAddInfo')" >取消</a>
		</div>
		
		<div id="jiraAddYScore" class="easyui-dialog" data-options="closed:true,title:'已解决并评价',top:50" style="width:600px;height:430px;padding:0px 0px;display:none;" v-cloak buttons="#divJiraAddScoreYBtn">
			<div style="padding:20px;">
				<form method="post" id="saveYJiraScoreForm">
					<div id="score" style="text-align: center;margin: 10px;"></div> 
					<input id="scoreId" class="easyui-textbox" name="score" value="5"  style="width: 300px"/>
					<span style="color:red;">*</span><span id="addScoreSpanId">请大胆说出你的想法吧</span>
					<br/><input id="jiraAddYId" class="easyui-textbox" name="key" value=""/>
					<br/><input id="isSuccessFlagId" class="easyui-textbox" name="isSuccessFlag" value=""/>
					<input id="addScoreCommentId" class="easyui-textbox" data-options="prompt:'请说出你对系统的其它建议......',multiline:true,validType:'length[1,100]'" value="" style="width:550px;height:200px" name="content" required="true" >
					<br><span style="font-size:15px;float:right;margin-right:2%;">还可以输入<span id="cost_tpl_title_length3" style="font-size:18px;color:red">100</span>字</span> 
				</form>
			</div>
		</div>
		<div id="divJiraAddScoreYBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton save" onclick="addScore(this)">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('jiraAddYScore')" >取消</a>
		</div>
	</body>
</html>