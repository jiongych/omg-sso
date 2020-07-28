<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="../scripts/portal/jiraOrder.css"/>
		<link rel="stylesheet" type="text/css" href="../scripts/portal/viewer.min.css" />
	    <title>上报IT服务台</title>
	    <%@include file="../common/common-ztree.jsp" %>
	    <style type="text/css">
			#tipsDiv {
				position: fixed;
				left: 0;
				right: 0;
				top: 0;
				bottom: 0;
				background-color:rgba(212, 209, 209, 0.2);
				z-index: 99999;
			}
			#tipsDiv .tipsClass {
				position: absolute;
				left: 50%;
				top: 50%;
				width: 330px;
				height: 100px;
				margin: -100px 0px 0px -150px;
				text-align: center;
			}
			.uploaddiv{
				width: 20%;
			    float: left;
			}
			.uploaddiv img {
			    width: 80px;
				height: 80px;
				vertical-align: middle;
				cursor: pointer;
				margin-bottom: 5px;
			}
			.uploaddiv p {
			    height: 18px;
				line-height: 18px;
			    overflow: hidden;
			    display: block;
			    margin-top: 0;
			}
			.imgList{
				position:relative;
				text-align: center;
				width: 80px;
				margin-left: 5px;
				margin-top: 5px;
			}
			.imgList .delect{
				position: absolute;
				top: -5px;
				right: -7px;
				z-index: 2;
				color: #9c9c9c;
			    font-size: 20px;
			    background: #fff;
			    border-radius: 50%;
			}
			.icono {
				width: 1em;
				height: 1em;
				vertical-align: -0.15em;
				fill: currentColor;
				overflow: hidden;
			}
			.it-list{
				margin-left: 115px;
				text-decoration: underline;
				cursor: pointer;
				color: #6a54ba;
			}
			.flow-form-block::-webkit-scrollbar{
				display:none;
			}
			.save{
				background: #6a54ba;
			}
			.save:hover{
				background: #5c6cbc;
			}
			.issueTypeChecked{
				background-color:#6a54ba;
				color:#fff;
			}
			.issueTypeUnCheck{
				background-color:#fff;
				color:#000000;
			}
		</style>
	</head>
		
	<body class="easyui-layout">
		<script type="text/javascript" src="../scripts/portal/viewer.min.js"></script>
		<script type="text/javascript" src="../scripts/portal/iconfont.js"></script>
		<script type="text/javascript">
		var limitSize=20; //图片最大限制(M)
		//页面加载  
		$(document).ready(function(){
			initDictDatas('PROMPT');
			$("#vilidTime").hide();
			var	fileOptions=[];	//上传图片列表	
			$("#statusParam").next().hide();
			/* $("#description2").next().hide();
			$('#description2').textbox('setValue','无'); */
			var maxlength = 255;
			$('#description1').textbox({
				inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
					keyup:function(event){
						var value = event.currentTarget.value;
						var rem = maxlength - value.length; 
						if (rem < 0) {
							rem =0;
						}
						$("#cost_tpl_title_length").html(rem); 
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
						$("#cost_tpl_title_length").html(num); 
					}
						 
				})
			});
			/* $('#description2').textbox({
				inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
					keyup:function(event){
						var value = event.currentTarget.value;
						var rem = maxlength - value.length; 
						if (rem < 0) {
							rem =0;
						}
						$("#cost_tpl_title_length").html(rem); 
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
						$("#cost_tpl_title_length").html(num); 
					}
						 
				})
			}); */
			ITServiceReport();
			findCurrentUserInfo();
		});

		var empName = "";
		var userTel = "";
		var imageTotal = 0
		var ttFlag = 0;
		
		function changeIssueType(issueType,obj) {
			/* if (ttFlag == issueType) {
				return;
			} */
			$(".issueType").removeClass("issueTypeChecked");
			$(".issueType").addClass("issueTypeUnCheck");
			$(obj).removeClass("issueTypeUnCheck");
			$(obj).addClass("issueTypeChecked");
			var issueTypeId = $('#issueTypeId').textbox('getValue');
			if (issueTypeId == "") {
				if (issueType == 0) {
					$('#issueTypeId').textbox('setValue','100');
				} else if (issueType == 1){
					$('#issueTypeId').textbox('setValue','110');
				} else if (issueType == 2){
					$('#issueTypeId').textbox('setValue','120');
				}
				return;
			}
			var checkCon = true;
			var description = "";
			description = $("#description1").textbox('getValue');
			var title = $("#title").textbox('getValue');
			/* if (issueType == 0) {
				description = $("#description2").textbox('getValue');
			} else {
				description = $("#description1").textbox('getValue');
			} */
			var file = $("#img_preview").text();
			if (description == "" && file == "" && title == "") {
				checkCon = false;
			}
			if(!checkCon && imageTotal <= 0) {
				checkCon = false;
			}
			if (checkCon) {
				$.messager.confirm('切换上报问题类型','切换会清除内容，确认切换吗？',function(r){
					if (r){
						$('#img_preview').empty();
						//$("#saveJiraIssueForm").form('clear');
						initInfo();
						$("#cost_tpl_title_length").html(255); 
						$('#description1').textbox('setValue','');
						$('#title').textbox('setValue','');
						//$('#description2').textbox('setValue','');
						imageTotal = 0;
						changeTypeColor(issueType,obj);
						ttFlag = issueType;
					}
				});
			} else {
				changeTypeColor(issueType,obj);
				ttFlag = issueType;
			}
		}
		var description1 ="";
		var description2 ="";
		function changeTypeColor(issueType,obj) {
			$('#description1').textbox('setValue','');
			var des;
			if (issueType == 0) {
				/* $("#description1").next().show();
				$('#description1').textbox('setValue','');
				$("#description2").next().hide(); */
				des = DictDatas['PROMPT'][0].typeName;
				$("#description1").textbox({prompt:des});
				$('#issueTypeId').textbox('setValue','100');
				/* $('#issueTypeA1').css({'background-color':'#6a54ba'});
				$('#issueTypeA1').css({'color':'#fff'});
				$('#issueTypeA2').css({'background-color':'#fff'});
				$('#issueTypeA2').css({'color':'#000000'}); */
			} else if (issueType == 1){
				/* $("#description1").next().hide();
				$('#description1').textbox('setValue','无');
				$("#description2").next().show(); */
				des = DictDatas['PROMPT'][1].typeName;
				$("#description1").textbox({prompt:des});
				$('#issueTypeId').textbox('setValue','110');
			/* 	$('#issueTypeA2').css({'background-color':'#6a54ba'});
				$('#issueTypeA2').css({'color':'#fff'});
				$('#issueTypeA1').css({'background-color':'#fff'});
				$('#issueTypeA1').css({'color':'#000000'}); */
			} else if (issueType == 2){
				des = DictDatas['PROMPT'][4].typeName
				$("#description1").textbox({prompt:des});
				$('#issueTypeId').textbox('setValue','120');
			}
		}
	
		function findCurrentUserInfo() {
			$.ajax({
				url:'${pageContext.request.contextPath}/jira/findCurrentUserInfo.do',
				type:"POST",
				data:{},
				success:function (data) {
					if(data != undefined && data != null) {
						empName = data.empName;
						userTel = data.userTel;
						initInfo();
					}
				}
			});	
			
		}

		function initInfo() {
			$('#empName1').textbox('setValue',empName);
			$('#telephone1').textbox('setValue',userTel);
			$('#empName2').textbox('setValue',empName);
			$('#telephone2').textbox('setValue',userTel);
			$('#issueTypeId').textbox('setValue','');
			$("#bizTypeYS").prop("checked",false);
			$("#bizTypeYM").prop("checked",false);
		}
		//初始化IT服务台上报页面
		function ITServiceReport() {
			description1 = DictDatas['PROMPT'][0];
			description2 = DictDatas['PROMPT'][1];
			var imageNum = 0;
			imageTotal = 0;
			initInfo();
			$("#issueTypeId").next().hide();
		    //$('#issueTypeId').textbox('setValue','100');
		    $('#img_preview').empty();
			$("#cost_tpl_title_length").html(255); 
			$('#file').filebox({
					buttonText: '选择文件',
					buttonAlign: 'left',
					//accept:'.JPEG,.jpg,.png,.jpeg,.JPG,.PNG',
					onClickButton:function(){
						$("#file").filebox('clear');
					},
					onChange:function(nVal,oVal){
						 if(nVal){
							$('#file').filebox('setText','');
							let fileObjs=$("input[name='file[]']")[0].files;
							if(!fileCheck(fileObjs)){
								return;
							}
							var fileArr = Array.from(fileObjs);
							//$('#img_preview1').empty();
							
							fileArr.forEach((item,index) => {
							    if(item){
									if (imageTotal == 0 || imageTotal == 5) {
										var container = $('#scrolCreateIssue');
										container.scrollTop(500);//滚动到div 100px
									}
									var imgUrl = this.result
									var fileType = item.name.substring(item.name.lastIndexOf('.') + 1)
									if (fileType != "png" && fileType != "jpg" && fileType != "JPEG" && fileType != "PNG" && fileType != "JPG" && fileType != "jpeg") {
										imgUrl = '../scripts/portal/images/view_other.png';
										if ('xls' == fileType || 'xlsx' == fileType) {
											imgUrl = '../scripts/portal/images/view_excel.png';
										} else if ('doc' == fileType || 'docx' == fileType) {
											imgUrl = '../scripts/portal/images/view_word.png';
										} else if ('ppt' == fileType || 'pptx' == fileType) {
											imgUrl = '../scripts/portal/images/view_ppt.png';
										} else if ('txt' == fileType) {
											imgUrl = '../scripts/portal/images/view_text.png';
										} else if ('rar' == fileType) {
											imgUrl = '../scripts/portal/images/view_rar.png';
										} else if ('zip' == fileType) {
											imgUrl = '../scripts/portal/images/view_zip.png';
										} else if ('pdf' == fileType) {
											imgUrl = '../scripts/portal/images/view_pdf.png';
										}
									}
							    	imageTotal++;
									imageNum++;
							        //读取本地图片，以base64编码输出
						            $('#img_preview').append('<div class="uploaddiv" id="uploaddiv_'+imageNum+'">'+
						            		'<div class="imgList"><img style="height:80px;" src="'+imgUrl+'" id="uploadimg_'+imageNum+'" alt="'+item.name+'" title="'+item.name+'"><svg class="icono delect" aria-hidden="true" onclick="deleteImage(\'uploaddiv_'+ imageNum +'\','+imageNum+')"><use xlink:href="#nesicon-guanbi"></use></svg>'+
						            		'<p style="text-align:center;">'+item.name+'</div>' +
						            		'<input class="easyui-textbox" id="imgBase'+imageNum+'" name="imageBase" style="width:180px"/>'+
						            		'<input class="easyui-textbox" id="imgBaseName'+imageNum+'" name="imageBaseName" style="width:180px"/></div>');
									$.parser.parse($('#imgBase'+imageNum).parent());
									$.parser.parse($('#imgBaseName'+imageNum).parent());
									$("#imgBase"+imageNum).next().hide();
									$("#imgBaseName"+imageNum).next().hide();
									var reader = new FileReader();
							        reader.readAsDataURL(item);
							        reader.onloadend = function(){
							            //读取完毕后输出结果
										//arrList.push(this.result);
							            if (fileType == "png" || fileType == "jpg" || fileType == "JPEG" || fileType == "PNG" || fileType == "JPG" || fileType == "jpeg") {
							            	$('#uploadimg_'+imageNum).attr("src",this.result);
							            }
										var task = function () {
											$('#imgBase'+imageNum).textbox('setValue',reader.result);
											$('#imgBaseName'+imageNum).textbox('setValue',item.name);
										}
										setTimeout(task, 200);
							        }
									$('#img_preview').viewer();
							    }
							});
						}
						
					}
			});
			
			if (description1 == undefined) {
				description1 = "示范：异常系统：OA 异常描述：密码遗忘登陆不了OA，点击忘记密码没反应，请帮我解决。";
			} else {
				description1 = DictDatas['PROMPT'][0].typeName;
			}
			
			if (description2 == undefined) {
				description2 = "示范：建议系统：OA 建议描述：OA没地方看公司组织架构，每次发文的架构都不能体现出来。";
			} else {
				description2 = DictDatas['PROMPT'][1].typeName;
			}
			$("#description1").textbox({prompt:description1}); 
			//$("#description2").textbox({prompt:description2}); 
		}
		function viewLoadImg() {
			//$('#img_preview').update();
			$('#img_preview').viewer('update');
		}
		function deleteImage(divId, num) {
			imageTotal--;
			$("#" + divId).remove();
		}
		//保存问题上报信息
		function saveJiraIssue(btn) {
			var issueTypeId = $("#issueTypeId").textbox("getValue");
			if (issueTypeId == "") {
				showInfoMsg("请选择你要上报的问题类型");
				return;
			}
			var bizTypeFlag = false;
			$('input[name="bizType"]:checked').each(function(){  
				bizTypeFlag = true;
			});  
			if (bizTypeFlag == false) {
				showInfoMsg("请选择业务类型");
				return;
			} 
			if ($("#saveJiraIssueForm").form('validate')) {
				$(btn).linkbutton('disable');
				var url = "${pageContext.request.contextPath}/itsm/createIssue.do";
				$("#saveJiraIssueForm").form('submit',{
					url : url,
					task: function(result){
						
					},
					finalTask:function(){
						$("#tipsP").html("提交成功。");
						//$(btn).linkbutton('enable');
					},
					onSubmit:function() {
						document.getElementById("tipsDiv").style.display = "";
					},
					success:function(res){
						var result = JSON.parse(res);
						if(result.success == true){
							$("#tipsP").html("提交成功。正在打开上报问题列表。");
							setTimeout(function() {
								closeCurrentPage();
								openTabCrossDomain('jira_order',{},{title:'我上报的问题',systemCode:"PORTAL"});
							},2000);
						} else {//错误处理
							$("#tipsP").html(result.message);
							setTimeout(function() {
								$(btn).linkbutton('enable');
								$("#tipsP").html("正在发送中......");
								document.getElementById("tipsDiv").style.display = "none";
							},2000);
							
						}
					}
				});
			}
		}
		
		function fileCheck(fileObjs){
			var flag = true;
			var size = 5;
			if(imageTotal >= size || (fileObjs && fileObjs.length > size)){
				$.messager.show({
					title:'提示',
					msg:'最多只能上传' + size +'个文件!',
					showType:'show',
					timeout:3000
				});
				flag = false;
			}
			var fileArr = Array.from(fileObjs);
			fileArr.forEach((item,index) => {
				var nameB = item.name;
				var fileType = nameB.substring(nameB.lastIndexOf('.') + 1)
				/* if (fileType != "png" && fileType != "jpg" && fileType != "JPEG" && fileType != "PNG" && fileType != "JPG" && fileType != "jpeg") {
					$.messager.show({
						title:'提示',
						msg:'请上传png、jpg、JPEG格式的图片',
						showType:'show',
						timeout:3000
					});
					flag = false;
				} */
				var fileSize = item.size/(1024*1024);
				if(fileSize > limitSize){
					$.messager.show({
						title:'提示',
						msg:'单个文件不可大于'+limitSize+'M!',
						showType:'show',
						timeout:3000
					});
					flag = false;
				}
			});
			return flag;
		}
		function toItlist(){
			var localHref = window.location.href.substring(0,window.location.href.indexOf("${pageContext.request.contextPath}"));
			var url = localHref + "${pageContext.request.contextPath}/jira/forward.do";
			openTabCrossDomain('jira_order',{},{title:'我上报的问题',systemCode:"PORTAL",url:url});	
		}
		
		</script>
		<!--列表-->
		<div class="flow-detail-container">
			<div class="product flow-form show" style="height:93%">
				<div class="flow-form-block" style="overflow-y:auto;width:80%;margin:15px auto;height:90%;border: none;
				box-shadow: none;" id="scrolCreateIssue">
				<form enctype="multipart/form-data" method="post" id="saveJiraIssueForm">
					<div style="margin-top:20px;margin-left:20px;height:535px">
						<table class="table" style="width: 100%; border:0px;border-spacing:0px 20px;padding-bottom: 60px;">
							<tbody>
								<tr>
									<td width="20%" align="right"><span style="color:red;">*</span>请选择你要上报的问题类型：</td>
									<td width="70%">
										<button type='button' id="issueTypeA1" onclick="changeIssueType(0,this)" class="issueType issueTypeUnCheck" style="border-radius: 6px;width:110px;height:30px;border:none;cursor:pointer;outline:none;border: 1px solid #ccc">IT系统问题</button>
										<button type='button' id="issueTypeA2" onclick="changeIssueType(1,this)" class="issueType issueTypeUnCheck" style="border-radius: 6px;width:110px;height:30px;border:none;margin-left:50px;cursor:pointer;outline:none;border: 1px solid #ccc">需求与建议</button>
										<button type='button' id="issueTypeA3" onclick="changeIssueType(2,this)" class="issueType issueTypeUnCheck"style="border-radius: 6px;width:110px;height:30px;border:none;margin-left:50px;cursor:pointer;outline:none;border: 1px solid #ccc">桌面支持</button>
										<span class="it-list" onclick="toItlist()">查看我上报的问题</span>
									</td>
								</tr>
								<tr>
									<td width="20%" align="right"><span style="color:red;">*</span>业务类型：</td>
									<td width="70%"><input id="bizTypeYS" type="radio" name="bizType" value="10"/>快递
													<input id="bizTypeYM" type="radio" name="bizType" value="20"/>快运
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">运单/流程编号：</td>
									<td width="70%">
										<input id="title" name="title" class="easyui-textbox" data-options="multiline:true,validType:'length[1,255]'" value="" style="width:70%;" >
									</td>
								</tr>
								<tr>
									<td width="20%" align="right"><span style="color:red;">*</span>详情描述：</td>
									<td width="70%">
										<input id="issueTypeId" class="easyui-textbox" name="issueTypeId" />
										<input id="description1" class="easyui-textbox" data-options="multiline:true,validType:'length[1,255]',required:true" value="" style="width:70%;height:150px" name="description">
										<!-- <input id="description2" class="easyui-textbox" data-options="multiline:true,validType:'length[1,255]',required:true" value="" style="width:70%;height:150px" name="description" >
										<input id="description3" class="easyui-textbox" data-options="multiline:true,validType:'length[1,255]',required:true" value="" style="width:70%;height:150px" name="description" > -->
										<br><span style="font-size:15px;float:right;margin-right:30%;">还可以输入<span id="cost_tpl_title_length" style="font-size:18px;color:red">255</span>字</span> 
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">联系人：</td>
									<td width="70%">
										<input id="empName1" name="empName"  class="easyui-textbox"  data-options="validType:'length[0,30]',required:true"  style="width:31%">
										<span class="icon-line iconfont uce-edit" href="javascript:void(0)" style="margin-left: -23px;position: relative;top: 2px;right:5px;color:#645fbb;"></span>
										<lable>联系电话：</lable>
										<input id="telephone1" name="telephone"  class="easyui-textbox"  data-options="validType:'length[0,30]',required:true"  style="width:31%">
										<span class="icon-line iconfont uce-edit" href="javascript:void(0)" style="margin-left: -23px;position: relative;top: 2px;right:5px;color:#645fbb;"></span><br>
										<span style="color:red">* 特别提示：如非本人上报请修改联系人及手机号码！防止处理人员联系不到上报人影响问题解决时效 ！</span>
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">附件：</td>
									<td width="70%">
										<input id="file" name="file[]" type="text" style="width:31%"><lable style="margin-left: 10px">请上传文件，最多5个</lable>
									</td>
								</tr>
								<tr>
									<td></td>	
									<td><div id="img_preview" onclick="viewLoadImg()" style="width:70%"></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					</form>
				</div>
			</div>
			<div>
				<a href="javascript:void(0)" style="margin-left:45%;border-radius: 8px;width:110px;" class="easyui-linkbutton save" onclick="saveJiraIssue(this)">提交</a>
			</div>
		</div>
		<div id="tipsDiv" style="display: none">
			<div class="tipsClass">
				<p id="tipsP">正在提交中......</p>
				
				<img src='../scripts/portal/images/saveIng.gif'>
			</div>
		</div>
	</body>
</html>