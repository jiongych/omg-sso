<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>浏览器使用日志</title>
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
				//加载表格
                var columns = [[{field : 'id',title : '',hidden:true,},
                    {field:'browserName',title:'浏览器名称',formatter: formatTip},
                    {field:'createEmp',title:'员工编号',formatter: formatTip},
                    {field:'empName',title:'员工名称',formatter: formatTip},
                    {field:'operatingIp',title:'操作IP',formatter: formatTip},
                    {field:'browserVersion',title:'版本',align:'center',formatter: formatTip},
                    {field:'browserOperatingSystems',title:'操作系统',formatter: formatTip},
                    {field:'browserScreen',title:'分辨率',formatter: formatTip},
                    {field:'createDate',title:'操作时间',align:'center', formatter: function(value){
                            return formatData(value)
                        }}
                ]];
			   
		       var dataGridParams = {
					url : '${pageContext.request.contextPath}/browserInfo/findAllBrowserInfo.do',
					pageSize : 10,
					toolbar : '#tlbOperatorLog',
					singleSelect : 'true',
					queryParams : {},
					fitColumns : 'false',
					onSelect : function(index,row){
						
					}
				}
		       newloadGrid('dgOperatorLog', columns, dataGridParams);
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
												<td>员工编号：</td>
												<td>
													<input class="easyui-textbox" id="createEmp" name="createEmp" value="" style="width:100%" />
												</td>
												<td>浏览器名称：</td>
												<td>
													<input class="easyui-textbox" id="browserName" name="browserName" value="" style="width:100%" />
												</td>
												<td>开始时间：</td>
												<td>
													<input class="easyui-datetimebox" data-options="editable:false" name='createDate' id='createDate'/>
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