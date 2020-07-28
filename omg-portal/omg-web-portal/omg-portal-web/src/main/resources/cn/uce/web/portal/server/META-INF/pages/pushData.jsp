<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>重推管理</title>
    <%@include file="../common/common.jsp" %>
     <style>
	    .dictType-menu {
		    position: relative;
		    margin: 5px 0;
		    padding: 0;
		    white-space: nowrap;
		    cursor: pointer;
		    height: 33px!important;
		}
    </style>
    <script type="text/javascript">
    //页面加载
	$(function() {
		//加载搜索所属系统数据字典
		uceDictCombobox("searchSystemCode", "REPAIR_RECEIVE_SYSTEM");
		//加载数据类型
		uceDictCombobox("loadDataType", "SYS_DATA_SYNC_TYPE");
		//加载数据范围
		/** $('#loadDataRange').combogrid({
			panelWidth: 320,
			url: 'loadDataRange.do',
			mode: 'remote',
			idField: 'id',
			textField: 'name',
			fitColumns: true,  
			striped: true,  
			editable: true,  
			pagination: false,           //是否分页  
			rownumbers: true,           //序号  
			collapsible: false,         //是否可折叠的  
			fit: true,  
			onBeforeLoad: function(param){         		
                var value = $("#loadDataType").combogrid('getValue');  
                if(value!=null && value!=undefined && value!=''){  
                    param.dataType = value;  
                    return true;  
                }else{  
                    return false;     
                }         
            },
			multiple: true,
			columns: [[
				{field:'id',title:'编号',width:120,sortable:true},
				{field:'name',title:'名称',width:80,sortable:true}
			]]
		}); */
		//关联查询推送范围数据
		/** $("#loadDataType").combobox({    
			onChange: function (newValue, oldValue) {  
				$.ajax({
					type : "POST",
					url : "loadDataRange.do",
					data: {
						"dataType" : $("#loadDataType").combobox('getValue')
					},
					success : function(data, textStatus) {
						$("#loadDataRange").combogrid("clear");
                        $("#loadDataRange").combogrid("grid").datagrid("loadData", data);
					}
				});
			}
	    });*/
    });
	//初始化数据字典
	initDictDatas("SYS_DATA_SYNC_TYPE,REPAIR_RECEIVE_SYSTEM");
	// 开始推送数据
	function startPush(){
		var dataType = $("#loadDataType").combobox('getValue');
		var values = $('#loadDataRange').textbox('getValue');
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var receiver = $("#searchSystemCode").combobox('getValues');
		var receivers = '';
		for(var i = 0; i < receiver.length; i++){
			receivers += receiver[i] + ',';
		}
		receivers = receivers.substring(0,receivers.length - 1);
		$('#formPushData').form('submit',{
			type: 'post',
			url : "startPush.do",
			onSubmit:function(){
				if((startTime == null || startTime == '' || startTime == undefined) 
					&& (values == null || values == '' || values == undefined)){
				    $.messager.alert('提示：','请输入数据类型编码或者选择推送日期，谢谢！');
					return false;
				}
				if(dataType == null || dataType == '' || dataType == undefined){
				    $.messager.alert('提示：','请输入数据类型,它为必填项，谢谢！');
					return false;
				}
				if(receivers == null || receivers == '' || receivers == undefined){
				    $.messager.alert('提示：','请输入数据接收系统,它为必填项，谢谢！');
					return false;
				}
				return $(this).form('enableValidation').form('validate');
			},
			success : function(data, textStatus) {
			   $.messager.alert('提示：','推送结果数据如下，请检查，谢谢！');
			   $("#pushContent").html(data);
			}
		});
		/** if(flag){
			$.messager.confirm('推送确认框', '你确定推送数据吗？', function(r){
				if (r){
					$.ajax({
						type : "POST",
						url : "startPush.do",
						data: {
							"dataType" : dataType,
							"startTime" : startTime,
							"endTime" : endTime,
							"pushRange" : values,
							"receiver" : receivers
						},
						success : function(data, textStatus) {
						}
					});
				}
			});
		}*/
	}
	
	//重置
	function queryData(){
		$("#formPushData").form('reset');
	}
	</script>
</head>

<body class="easyui-layout">
    <div data-options="region:'center'" >
    	<!--列表-->
		<div id="tlbDictData" style="padding:5px;height:auto">  
			<form action="#" id="formPushData">
				<div id="aa" class="easyui-accordion">  
                    <table cellpadding="5">
						<tr>
							<td>数据类型：</td>
							<td><select name="dataType" style="width:240px;" id="loadDataType" required="true"></select></td>
						    <td>数据类型编码:</td>
							<td><input class="easyui-textbox" style="width:240px;" id="loadDataRange" name="pushRange" data-options="prompt:'请输入数据类型编码'"></td>
						    <td>开始时间:</td>
							<td><input class="easyui-datetimebox" style="width:240px;" data-options="editable:false" name='startTime' id='startTime' data-options="prompt:'请输入开始时间'"></td>
						    <td>结束时间:</td>
							<td><input class="easyui-datetimebox" style="width:240px;" data-options="editable:false" name='endTime' id='endTime' data-options="prompt:'请输入结束时间'"></td>
						</tr>
						<tr>
							<td>接收系统:</td>
							<td><select name="receiver" style="width:240px;" id="searchSystemCode" required="true"></select></td>
							<td><a href="#" class="easyui-linkbutton search" onclick="startPush()">开始推送</a></td>
						</tr>
					</table>	
				</div>
			</form>
		</div>
		<div id="pushContent"></div>
	</div>
</body>
</html>