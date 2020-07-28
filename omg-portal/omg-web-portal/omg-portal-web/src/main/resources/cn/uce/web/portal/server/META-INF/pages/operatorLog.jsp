<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>操作日志管理</title>
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
		</style>
	</head>
		
	<body class="easyui-layout">
		<script type="text/javascript">
		
		  	//页面加载  
		    $(document).ready(function(){
		    	/**
				 * 控制角色操作区是否显示
				 * @param psColumn 操作列显示总控
				 * @param... psButtonXXX 单个按钮是否有权限码
				 * @PS:如果放至列内会多次调用权限码校验方法
				 
				var psOptControl ={
					psColumn:dealPermission(['role_edit','role_remove']),
					psButtonEdit:(dealPermission(['role_edit']) ? 'none' : 'bolck'),
					psButtonDelete:(dealPermission(['role_remove']) ? 'none' : 'bolck')
				};*/
				//加载表格
                var columns = [[{field : 'id',title : '',hidden:true,},
                    {field:'operatorMenu',title:'操作模块',formatter: formatTip},
                    {field:'operatorType',title:'操作类型',align:'center',formatter: formatTip},
                    {field:'operatorEmp',title:'员工编号',formatter: formatTip},
                    {field:'operatorEmpName',title:'操作人员',formatter: formatTip},
                    {field:'createdOrgName',title:'机构名称',formatter: formatTip},
                    {field:'createdTime',title:'操作时间',align:'center', formatter: function(value){
                        return formatData(value)
                    }},
                    /* {field:'createdOrg',title:'操作机构',formatter: formatTip}, 
                    {field:'operatorBefore',title:'操作前内容',width:400,  formatter: formatTip},*/
                    {field:'operatorAfter',title:'操作后内容', width:400, formatter: formatTip},
					{field:'operatorTable',title:'操作表名',formatter: formatTip},
                    {field:'operatorIp',title:'操作人IP',formatter: formatTip}
                ]];
			   
		       var dataGridParams = {
					url : '${pageContext.request.contextPath}/log/findAllLogs.do',
					pageSize : 10,
					toolbar : '#tlbOperatorLog',
					singleSelect : 'true',
					queryParams : {},
					fitColumns : 'false',
					onSelect : function(index,row){
						
					}
				}
				newloadGrid('dgOperatorLog', columns, dataGridParams);
		        //初始化数据字典
		   	    initDictDatas("SYS_MOUDLE,SYS_OTYPE");
		   	    uceDictCombobox("operatorMenu","SYS_MOUDLE",{headerValue:false});
		   	    uceDictCombobox("operatorType","SYS_OTYPE",{headerValue:false});
		    });

		//查询
	    function findOperatorLog() {
			$('#dgOperatorLog').datagrid("load", serializeFormObj("formfindOperatorLog"));
		}
	  	//重置
		function resetQuery(){
		  $("#formfindOperatorLog").form('reset');
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
								    <table class="table" style="width: auto; border:0px; padding-left: 20px;" >
										<tbody>
											<tr>
												<td>操作模块：</td>
												<td>
													<select id="operatorMenu" name="operatorMenu" value="" style="width:100%" ></select>
												</td>
												<td>操作类型：</td>
												<td>
													<select id="operatorType" name="operatorType" value="" style="width:100%" ></select>
												</td>
												<td>员工编号：</td>
												<td>
													<input class="easyui-textbox" name="operatorEmp" data-options="prompt:' 请输入操作人员'">
												</td>
												<td>操作人员：</td>
												<td>
													<input class="easyui-textbox" name="operatorEmpName" data-options="prompt:' 请输入操作人员'">
												</td>
												<td>操作表名：</td>
												<td>
													<input class="easyui-textbox" name="operatorTable" data-options="prompt:' 请输入操作表名'">
												</td>
											</tr>
											<tr>
												<td>操作后内容：</td>
												<td>
													<input class="easyui-textbox" name="operatorAfter" data-options="prompt:' 请输入操作后的内容'">
												</td>
												<td>开始时间：</td>
												<td>
													<input class="easyui-datetimebox" data-options="editable:false" name='createdTime' id='createdTime'/>
												</td>
												<td>结束时间：</td>
												<td>
													<input class="easyui-datetimebox" data-options="editable:false" name='updatedTime' id='updatedTime'/>
												</td>
												<td colspan="2">
													<a href="#" class="easyui-linkbutton search" onclick="findOperatorLog()">查询</a> 
													<a href="#" class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
												</td>
											</tr>
										</tbody>
									</table>  
								</div>
						    </div>  
						</form>
					</div>
					<table id="dgOperatorLog" class="easyui-datagrid" data-options="fit:true"></table>
				</div>
	    	</div>
	    </div>
	</body>
</html>