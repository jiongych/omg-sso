<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>各系统首页跳转信息</title>
    <%@include file="../common/common.jsp" %>
     
    <script type="text/javascript">
    
	    //页面加载
		$(function() {
			
			//加载角色dataGrid
			var columns = [[{field : 'id',title : '',hidden:true,},
							{field:'deal',title: '操作',align:'center',width: 80,formatter:function(value,rec,index){
								if(value){
					        		return value;
					        	}
								return '<a class="icon-line iconfont uce-edit" title="编辑" onclick="openUpdateSysHome(\''+index+'\')" href="javascript:void(0)"></a>'+
					        		   '<a class="icon-line iconfont uce-delete" title="删除" onclick="deleteSysHome(\''+index+'\')" href="javascript:void(0)"></a>';
					        }},
					        {field : 'sysName',title : '系统名称',width : 100,formatter: formatTip},
					        {field : 'sysUrl',title : '跳转路径',width : 150,formatter: formatTip},
					        {field : 'sysType',title : '系统类型',width : 80,formatter: function(value) {
								return getTypeNameByCode("SYS_TYPE", value, formatTip);
							}}, 
					        {field : 'sort',title : '展示顺序',width : 80,formatter: formatTip}, 
							{field : 'deleteFlag',title : '是否启用',width : 80,formatter: function(value) {
								return getTypeNameByCode("ENABLE", value, formatTip);
							}}, 
					        {field : 'sysIcon',title : '系统图标',width : 80,formatter: formatTip}, 
					        {field : 'createEmp',title : '创建人',width : 80,formatter: formatTip},
					        {field : 'createTime',title : '创建时间',width : 100,formatter: function(value){
					        	return formatData(value);
					        }},
					        {field : 'updateEmp',title : '更新人',width : 80,formatter: formatTip},
					        {field : 'updateTime',title : '更新时间',width : 100,formatter: function(value){
					        	return formatData(value);
					        }},
					        {field : 'remark',title : '备注',width : 100,formatter: formatTip}
						]];
			var dataGridParams = {
				url : 'findHomeInfoByPage.do',
				pageSize : 10,
				toolbar : '#tlbSysHome',
				singleSelect : 'true',
				fitColumns : 'true'
			}
			newloadGrid('dgSysHome', columns, dataGridParams);
		
			uceDictCombobox("sysType","SYS_TYPE",{headerValue:false});//加载“系统类型”下拉列表
			uceDictCombobox("deleteFlag","ENABLE",{headerValue:false});//加载“是否启用”下拉列表
			initSysLevels();
		});
		//初始化数据字典
		initDictDatas("SYS_TYPE,ENABLE");
		//新增各系统首页跳转信息
		function openAddSysHome() {
			$('#formSysHome').form('clear');
			$('#formSysHome').form('enableValidation');
			openDialog("dlgSysHome", '新增跳转信息');
			url = 'insertHomePageInfo.do';
		}
		
		function initSysLevels() {
			// 弹出框的所属分类
			$("#sysLevels").combobox({
				url:'../dictData/findDictData.do?typeClassCode=ROLE_LEVEL',
				multiple:true,
				valueField:'typeCode',    
				textField:'typeName',
				editable:false,
				onLoadSuccess:function(value){
					var rows = $("#sysLevels").combobox('getData');
					var isHave = false;
					for (var i = 0; i < rows.length; i++) {
						if (rows[i].typeCode == '100') {
							rows.splice(i, 1);
							isHave = true;
							 break; 
						}
					}
					if(isHave){
						$("#sysLevels").combobox('loadData', rows).combobox('setValue','');
					}
				}
			})
		}
		//编辑各系统首页跳转信息
		function openUpdateSysHome(index) {
			var row=$('#dgSysHome').datagrid('getRows')[index];
			$('#formSysHome').form('clear');
			$('#formSysHome').form('load',row);
			$('#sysLevels').combobox('setValues',row.sysLevels.split(","));
			$('#formSysHome').form('enableValidation');
			openDialog("dlgSysHome", '编辑跳转信息');
			url = 'updateHomePageInfo.do';
		}

		//保存各系统首页跳转信息
		function saveSysHome(btn) {
			$(btn).linkbutton('disable');
			$("#formSysHome").form('submit',{
	  			url : url,
	  			task: function(result){
					closeDialog('dlgSysHome');
					reloadDatagrid('dgSysHome');
	  			},
	  			finalTask:function(){
	  				$(btn).linkbutton('enable');
	  			}
	  		});
			
		}
		
		//查询
		function findSysHomeData() {
			if ($("#formFindSysHome").form('validate')) {
				$('#dgSysHome').datagrid('load', serializeFormObj("formFindSysHome"));
			}
		}
		//重置
		function resetQuery() {
			$("#formFindSysHome").form('reset');
		}
		//删除
		function deleteSysHome(index) {
			var row=$('#dgSysHome').datagrid('getRows')[index];
			confirmMsg('您确定要删除选中的角色吗？', function(row) {
				$.ajax({
					url:'deleteHomePageInfo.do',
					data :{'id': row.id},
					task : function(data, statusText, xhr){
						reloadDatagrid('dgSysHome');
					}
				});
			}, [row]);
			
		}
	</script>
</head>

<body class="easyui-layout">
	<!--各系统首页跳转信息表格 -->
	<div id="tlbSysHome">
		<form action="#" id="formFindSysHome">
			<div id="aa" class="easyui-accordion">   
			    <div title="" data-options="selected:true">
					<ul class="search-form" >
				    	<li><input class="easyui-textbox" label="系统名称：" labelPosition="left" name="sysName" data-options="prompt:'请输入系统名称'"></li>
						
						<div>
							<a href="#" class="easyui-linkbutton search" onclick="findSysHomeData()">查询</a>
							<a href="#" class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
		    			</div>
					</ul>
				</div>
			</div>
			<div class="toolbar-margin">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-add" onclick="openAddSysHome()" plain="true">新增</a>
			</div>

		</form>
	</div>
	<table id="dgSysHome" class="easyui-datagrid" data-options="fit:true"></table>
	
	<!--新增修改dialog -->
	<div id="dlgSysHome" class="easyui-dialog" style="width: 700px; height: 330px;  padding:10px" closed="true" buttons="#divSysHomeBtn">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding: 15px 20px; background: #fff;">
				<form id="formSysHome" method="post">
					<input type="hidden" name="id" />
					<table class="table" style="width: 100%; border: 0px;">
						<tbody>
							<tr>
								<td width="15%">系统名称:</td>
								<td width="30%">
									<input name="sysName" id="sysName" class="easyui-textbox" style="width:100%" required="true">
								</td>
								<td width="15%">跳转路径:</td>
								<td width="30%">
									<input name="sysUrl" id="sysUrl" class="easyui-textbox" style="width:100%" required="true">
								</td>
							</tr>
							
							<tr>
								<td>系统类型:</td>
								<td>
									
									<input name="sysType" id="sysType" class="easyui-numberbox" required="true" style="width:100%"/>
								</td>
								<td>展示顺序:</td>
								<td>
									<input name="sort" id="sort" class="easyui-numberbox" style="width:100%" data-options="required:true,validType:{length:[1,9]}">
								</td>
							</tr>
							
							<tr>
								<td>显示级别:</td>
								<td>
									<input name="sysLevels" id="sysLevels" class="easyui-combobox" data-options="required:true" style="width:100%"/>
								</td>
								<td>是否启用:</td>
								<td>
									<select name="deleteFlag" id="deleteFlag"  required="true" style="width:100%"></select>
								</td>
							</tr>
							<tr>
								<td>系统图标:</td>
								<td>
									<input name="sysIcon" id="sysIcon" class="easyui-textbox" data-options="prompt:'请输入图标样式名'," style="width:100%">
								</td>
								<td>备注:</td>
								<td>
									<input name="remark" id="remark" class="easyui-textbox" 
									validType="length[1,100]"  invalidMessage="有效长度1-100"
									data-options="prompt:'有效长度1-100',multiline:true" style="width: 100%">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div id="divSysHomeBtn">
		<a href="javascript:void(0)" class="easyui-linkbutton  save" onclick="saveSysHome(this)" >保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('dlgSysHome')">取消</a>
	</div>
		
</body>
</html>