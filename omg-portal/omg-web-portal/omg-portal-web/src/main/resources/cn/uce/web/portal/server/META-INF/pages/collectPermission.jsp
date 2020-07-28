<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>菜单点击日志</title>
    <%@include file="../common/common.jsp" %>
	<style> 
		.noticeAuthor{float:right;text-align:right;font-weight:bold}
	</style> 
    <script type="text/javascript">
    
	    //页面加载
		$(function() {
			//加载角色dataGrid
			var columns = [[{field : 'id',title : '',hidden:true},
					        {field : 'permissionName',title : '菜单名称',width : 100,formatter: formatTip},
					        {field : 'permissionCode',title : '菜单编号',width : 100,formatter: formatTip},
					        {field : 'systemCode',title : '所属系统名称',width : 100,formatter: function(value){
								return getTypeNameByCode("SYSTEM_CODE",value) + '（' + value + '）';
							}},
							{field : 'empCode',title : '用户编号',width : 50,formatter: formatTip},
					        {field : 'empName',title : '用户姓名',width : 50,formatter: formatTip},
					        {field : 'source',title : '数据来源',width : 50,formatter: function(value){
								return getTypeNameByCode("COLLEC_SOURCE",value);
							}},
					        {field : 'createTime',title : '创建时间',width : 100,align:'center',formatter: function(value){
					        	return formatData(value);
					        }},
							{field : 'url',title : '跳转地址',width : 120,formatter: formatTip}
						]];
			var dataGridParams = {
				url : 'findCollectPermissionByPage.do',
				pageSize : 10,
				toolbar : '#tlbCollectPermission',
				singleSelect : 'true',
				fitColumns : 'true'
			}
			$("#init").next().hide();
			newloadGrid('dgCollectPermission', columns, dataGridParams);
			uceDictCombobox("systemCode","SYSTEM_CODE",{headerValue:true});
			uceDictCombobox("source","COLLEC_SOURCE",{headerValue:true});
		});
		$("#vilidTime").hide();
		//初始化数据字典
		initDictDatas("COLLEC_SOURCE,SYSTEM_CODE");
		//查询
		function findCollectPermissionData() {
			var createTime =  $("#createTime").textbox('getValue');
			var updateTime =  $("#updateTime").textbox('getValue');
			if (createTime != '' && updateTime != '') {
				createTime = createTime.replace("-","/");
				updateTime = updateTime.replace("-","/");
				var startCom = new Date(Date.parse(createTime));  
				var endCom = new Date(Date.parse(updateTime));  
				if (startCom >= endCom) {
					$("#vilidTime").show();
					setTimeout(function(){//两秒后跳转
						$("#vilidTime").hide();
					},3000);
					return;
				}
			}
			
			if ($("#formFindCollectPermission").form('validate')) {
				$('#dgCollectPermission').datagrid('load', serializeFormObj("formFindCollectPermission"));
			}
		}
		//重置
		function resetQuery() {
			$("#formFindCollectPermission").form('reset');
		}

	</script>
</head>

<body class="easyui-layout">
	<!--公告表格 -->
	<div id="tlbCollectPermission">
		<form action="#" id="formFindCollectPermission">
			<div id="aa" class="easyui-accordion">   
			    <div title="" data-options="selected:true">
					<ul class="search-form" >
						<div>
					    	<li><input class="easyui-textbox" label="菜单名称：" labelPosition="left" name="permissionName" data-options="prompt:'请输入菜单名称'">
							<input class="easyui-textbox" name="init" id="init" value="F"></li>
							<li><input class="easyui-textbox" label="菜单编号：" labelPosition="left" name="permissionCode" data-options="prompt:'请输入菜单编号'"></li>
							<li><input class="easyui-textbox" label="用户编号：" labelPosition="left" name="empCode" data-options="prompt:'请输入用户编号'"></li>
							<li><input class="easyui-textbox" label="用户姓名：" labelPosition="left" name="empName" data-options="prompt:'请输入用户姓名'"></li>
						</div>
						<div>
							<li>
								<select name="systemCode" id="systemCode" label="系统类型：" style="width:100px;"></select>
							</li>
							<li>
								<select name="source" id="source" label="数据来源：" style="width:100px;"></select>
							</li>
							<li><input class="easyui-datetimebox" label="开始时间：" data-options="editable:false" name='createTime' id='createTime' data-options="prompt:'请输入开始时间'"></li>
							<li><input class="easyui-datetimebox" label="结束时间：" data-options="editable:false" name='updateTime' id='updateTime' data-options="prompt:'请输入结束时间'"></li>
							<div>
								<a href="#" class="easyui-linkbutton search" onclick="findCollectPermissionData()">查询</a>
								<a href="#" class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
								
			    			</div>
							<li id="vilidTime" style="color:red;display:none;margin-left:10px;margin-top:20px;">查询结束时间必须大于开始时间</li>
						</div>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<table id="dgCollectPermission" class="easyui-datagrid" data-options="fit:true"></table>

</body>
</html>