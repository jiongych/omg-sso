<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    
    <title>portal菜单管理</title>
    <%@include file="../common/common.jsp" %>
    <link rel="stylesheet" type="text/css" href="../scripts/portal/zTree_v3/css/zTreeStyle/zTreeStyle.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/portal/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/portal/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/portal/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/portal/zTree_v3/js/jquery.ztree.exhide-3.5.js"></script>
    <script type="text/javascript" src="../scripts/portal/portalMenu.js"></script>
    <script type="text/javascript" src="../scripts/portal/zTree_v3/util/mapUtil.js"></script>
    
</head>
<body  class="easyui-layout">
	<style >
		.orgTreePanelFirstCls div {
			height:30px;
		}
		
		.orgTreePanelFirstCls .panel-tool{
			margin-top: -12px;
			left: 5px;
		}
		
		.orgTreePanelFirstCls .panel-body {
			overflow: hidden;
		}
		
		.orgTreePanelFirstCls .panel-tool a{
			width: 70px;
			height: 25px;
			opacity: 1.0;
			vertical-align: middle;
		}
		
		.orgTreePanelFirstCls .panel-tool .datagrid-btn-separator {
			height: 25px !important;
		}
		
		#baseMenuTree-tools, #portalMenuTree-tools {
    		text-align: left;
		}
		#baseMenuTree-tools div, #portalMenuTree-tools div{
			display:inline;
			vertical-align: middle;
			padding-left: 10px;
		}
		
		.portalMenuInfoTable .qtl_tr {
			height:30px;
		}
		
		.portalMenuInfoTable .qtl_btn {
			text-align:right;
			width:100px;
		}
		
		.portalMenuInfoTable th {
		    font-weight: normal;
		    display: table-cell;
		    vertical-align: inherit;
		}
		
		.portalMenuInfoTable .qtl_table {
			/* width: 90px; */
			text-align: right;
			/* padding-left:30px; */
		}
		
		.portalMenuInfoTable input {
			width:150px;
		}
		
		.portalMenuInfoTable #emp_isShowDeleteFlag {
			width:15px;
			vertical-align: middle;
		}
		
		#baseMenuTreeDiv .ztree {
			padding: 0px !important;
		}
		
		#portalMenuTreeDiv .ztree {
			padding: 0px !important;
		}
		
		#menuUpdateWin .easyui-textbox,
		#menuUpdateWin .easyui-combobox {
			width : 200px;
		}
		
		.confimPanel {
			overflow: hidden;
		}
		
		#confirmButtons .easyui-linkbutton {
			margin: 0px 10px;
		}
		
		.empPanelCls div {
			height:20px;
		}
		
		.empPanelCls .panel-tool{
			margin-top: -12px;
		}
		
		.optionPanelCls div {
			height:20px;
		}
		
		.selectedEmpPanelCls div {
			height:20px;
		}
		.ztree li span.button.firstPage {
			/* float:right;  */
			margin-left:2px; 
			margin-right: 0; 
			background-position:-144px -16px; 
			vertical-align:top; 
			*vertical-align:middle
		}
		.ztree li span.button.prevPage {
			/* float:right;  */
			margin-left:2px; 
			margin-right: 0; 
			background-position:-144px -48px; 
			vertical-align:top; 
			*vertical-align:middle
		}
		.ztree li span.button.nextPage {
			/* float:right; */ 
			margin-left:2px; 
			margin-right: 0; 
			background-position:-144px -64px; 
			vertical-align:top; 
			*vertical-align:middle
		}
		.ztree li span.button.lastPage {
			/* float:right;  */
			margin-left:2px; 
			margin-right: 0; 
			background-position:-144px -32px; 
			vertical-align:top; 
			*vertical-align:middle
		}
		
		#portalMenuManage .panel-tool {
			height: 38px;
			margin-top: -20px;
		}
		
		#portalMenuManage .panel-tool .panel-tool-a {
			height: 38px;
			vertical-align: middle;
			display: table-cell;
		}
			
	</style>
	<script type="text/javascript">
	 
	</script>
	<div id="portalMenuManage" style="width:100%;height:100%;overflow:hidden;">
		<!-- 页面工具栏 Start -->
		<div id="portalMenuTree-ToolBar">
			<div>
				<a href="#" class="easyui-linkbutton" style="width:75px;font-size:14px;height: 30px;opacity: 1;" iconCls="icon-save" onclick="javascript:savePortalMenuTreeClick()" plain="true"> 保存</a>
				<span class="datagrid-btn-separator" style="height: 22px;display:inline-block;float:none"></span>
				<a href="#" class="easyui-linkbutton"  style="width:75px;font-size:14px;height: 30px;opacity: 1;" iconCls="iconfont uce-update"  onclick="javascript:reloadPortalMenuTreeClick()" plain="true">重置</a>
			</div>
		</div>
		<!-- 页面工具栏 End -->
		
		<div class="easyui-layout" style="width:100%;height:100%;">
	 		<!-- 基础菜单树 Start -->
	 		<div id="baseMenuTreePanel" class="easyui-panel" title="基础权限" style="width:30%;height:100%;overflow:hidden;" data-options="region:'west',collapsible:false">
		    	<table style="width:100%;height:100%;">
		    		<tr><td style="height:25px;">
			        	<div id="baseMenuTree-tools">
							 <select name="systemCode" label="所属系统：" style="width:300px;" id="searchSystemCode"/>
						</div>
					</td></tr>
					<tr><td>
			        	<div id="baseMenuTreeDiv" style="height:100%;border-style: ridge;overflow: auto;">
							<div id="baseMenuzTree" class="ztree"></div>
			        	</div>
			        </td></tr>
			    </table>
			</div>
			<!-- 基础菜单树 End -->
			<!-- Portal菜单树 Start -->
			<div id="portalMenuTreePanel" class="easyui-panel" title="Portal菜单" style="width:30%;height:100%;" data-options="region:'center',collapsible:false,tools:'#portalMenuTree-ToolBar'">
		    	<table style="width:100%;height:100%;">
		    		<tr><td style="height:25px;">
		        	<div id="portalMenuTree-tools">
								<div class="easyui-searchbox" data-options="prompt:'请输入菜单名称'" id="schPortalMenuName" name="schPortalMenuName" style="width:200px;"/>
							</div>
						</td></tr>
						<tr><td>
			        <div id="portalMenuTreeDiv" style="height:100%;border-style: ridge;overflow: auto;">
						<div id="portalMenuzTree" class="ztree" style="height:100%;"></div>
			        </div>
		        </td></tr>
		    	</table>
			</div>
			<!-- Portal菜单树 End -->
			<!-- Portal菜单信息面板Start -->
			<div id="portalMenuInfoPanel" class="easyui-panel" title="Portal菜单信息" style="width:40%;height:100%;overflow:hidden;" data-options="region:'east',collapsible:false">
				<div class="easyui-accordion" id="infoPanel" style="width:100%;height:100%;">
					<div title="Portal菜单信息" data-options="iconCls:'iconfont uce-tipinfo-circle',selected:true" style="overflow:auto;padding:10px;">
						<table class="portalMenuInfoTable" style="border-spacing: 15px 10px;float:left;width: 100%;">
							<tr class="qtl_tr">
								<th class="qtl_table">菜单名称:</th>
								<th class="qtl_term"><input class="easyui-textbox" readonly="readonly" id="portalMenuName"></th>
								<th class="qtl_table">菜单别名:</th>
								<th class="qtl_term"><input class="easyui-textbox" readonly="readonly" id="portalMenuAliasName"></th>
								
								<!-- <th rowspan="2" class="qtl_btn">
									<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="width: 80px;height: 30px" onclick="javascript:searchEmpData()">查询</a>
								</th> -->
							</tr>
							<tr class="qtl_tr">
								<th class="qtl_table">系统类型:</th>
								<th class="qtl_term">
									<input class="easyui-combobox" id="systemCodeView" readonly="readonly"/>
							   	</th>
							   	<th class="qtl_table">上级菜单:</th>
							   	<th class="qtl_term">
							   		<input class="easyui-textbox" readonly="readonly" id="parentMenu">
							   	</th>
							</tr>
							<tr class="qtl_tr">
								<th class="qtl_table">URL:</th>
								<th class="qtl_term" colspan=3>
									<input class="easyui-textbox" id="permissionUrlView" readonly="readonly" style="width:445px;"/>
							   	</th>
							</tr>
						</table>
					</div>
					<div title="新增的菜单 (0)个" data-options="iconCls:'iconfont uce-add'" style="padding:10px;">
						<table id="addExtDataGrid" class="easyui-datagrid" style="width:100%;height:100%;" data-options="
						rownumbers:true,
						singleSelect:true,
						fitColumns:false,
						pagination:false,
						idField:'id'">
						    <thead>
						        <tr>
						        	<th data-options="field:'id',width:80,hidden:true">ID</th>
						        	<th data-options="field:'name',width:80">菜单名称</th>
						        	<th data-options="field:'systemCodeText',width:100">系统类型</th>
						        	<th data-options="field:'parentName',width:100">上级菜单</th>
						        </tr>
						    </thead>
						</table>
					</div>
					<div title="更新的菜单 (0)个" data-options="iconCls:'iconfont uce-qudaoguanli'" style="padding:10px;">
						<table id="updateExtDataGrid" class="easyui-datagrid" style="width:100%;height:100%;" data-options="
						rownumbers:true,
						singleSelect:true,
						fitColumns:false,
						pagination:false,
						idField:'id'">
						    <thead>
						        <tr>
						        	<th data-options="field:'id',width:80,hidden:true">ID</th>
						        	<th data-options="field:'name',width:80">菜单名称</th>
						        	<th data-options="field:'systemCodeText',width:100">系统类型</th>
						        	<th data-options="field:'parentName',width:100">上级菜单</th>
						        </tr>
						    </thead>
						</table>
					</div>
					<div title="删除的菜单 (0)个" data-options="iconCls:'iconfont uce-error-circle'" style="padding:10px;">
						<table id="delExtDataGrid" class="easyui-datagrid" style="width:100%;height:100%;" data-options="
						rownumbers:true,
						singleSelect:true,
						fitColumns:false,
						pagination:false,
						idField:'id'">
						    <thead>
						        <tr>
						        	<th data-options="field:'id',width:80,hidden:true">ID</th>
						        	<th data-options="field:'name',width:80">菜单名称</th>
						        	<th data-options="field:'systemCodeText',width:100">系统类型</th>
						        	<th data-options="field:'parentName',width:100">上级菜单</th>
						        </tr>
						    </thead>
						</table>
					</div>
					<div title="需要重新排序的菜单 (0)个" data-options="iconCls:'icon-sort'" style="padding:10px;">
						<table id="sortExtDataGrid" class="easyui-datagrid" style="width:100%;height:100%;" data-options="
						rownumbers:true,
						singleSelect:true,
						fitColumns:false,
						pagination:false,
						idField:'id'">
						    <thead>
						        <tr>
						        	<th data-options="field:'id',width:80,hidden:true">ID</th>
						        	<th data-options="field:'name',width:80">菜单名称</th>
						        	<th data-options="field:'systemCodeText',width:100">系统类型</th>
						        	<th data-options="field:'parentName',width:100">上级菜单</th>
						        </tr>
						    </thead>
						</table>
					</div>
					<div title="Help" data-options="iconCls:'iconfont uce-help'" style="padding:10px;">
					</div>
				</div>
			</div>
			<!-- Portal菜单信息面板End -->
	 	</div>

		<!-- 右键菜单 Start -->
		<div id="extTreeMenu" class="easyui-menu" style="width:120px;">
			<!-- <div id="treeMenuAdd" data-options="iconCls:'icon-add'">新增虚拟机构</div> -->
			<div id="treeMenuUpdate" data-options="iconCls:'icon-edit'">修改菜单</div>
			<div id="treeMenuDel" onclick="portalMenuRemoveClick()" data-options="iconCls:'icon-remove'">移除菜单</div>
		</div>
		<!-- 右键菜单 End -->
		
		<!-- 添加虚拟机构/修改机构窗口  Start -->
		<div id="menuUpdateWin" class="easyui-dialog" data-options="closed:true" style="width:320px;height:290px;padding:5px">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'center',border:false" style="padding:10px 10px;background:#fff;border:1px solid #ccc;">
					<form method="post" class="form"  id="formUpdateWin">
						<table class="table" style="width: 100%;">
							<tbody>
								<tr>
									<th>菜单名称</th>
									<td>
										<input type="hidden" id="id" name="id"/>
										<input id="portalMenuNameWin" name="name" class="easyui-textbox" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<th>菜单别名</th>
									<td>
										<input id="portalMenuAliasNameWin" name="aName" class="easyui-textbox" data-options="required:true,prompt:'有效长度1-10'"></input>
									</td>
								</tr>
								<tr>
									<th>系统类型</th>
									<td>
										<input class="easyui-combobox" id="systemTypeWin" name="systemCode" data-options="required:false"/>
									</td>
								</tr>
								<tr>
									<th>上级机构</th>
									<td>
										<input id="portalMenuParentWin" class="easyui-textbox" readonly="readonly"></input>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				
				<div data-options="region:'south',border:false" style="height:45px;text-align:right;padding:5px 0;">
					<a id="confirmAddNode" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0)">确定</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="closeDialog('menuUpdateWin')">取消</a>
				</div>
			</div>
		</div>
		<!-- 添加虚拟机构/修改机构窗口  End -->
	
		<!-- Portal菜单切换时的提示窗口  Start-->
		<!-- <div id="extTreeSaveChangeConfirm" class="easyui-dialog" buttons="#confirmButtons" data-options="closed:true" style="width:330px;height:140px;padding:5px">  
		    <div class="confimPanel">
		    	<div>
		    		<div class="messager-question" style="width:32px;height:32px;position: absolute;"></div>
		    		<div id="confirmTitle" style="margin-left: 40px;"></div>
		    	</div>
		    </div>
		</div>
		<div id="confirmButtons"  style="text-align: center;">
	    	<a id="yesBtn" class="easyui-linkbutton" href="javascript:void(0)"><span class="green icon-ok-font bigger-180"></span>&nbsp;&nbsp;是</a>
		    <a id="noBtn" class="easyui-linkbutton" href="javascript:void(0)" onclick="btnNo()"><span class="red icon-remove-font bigger-180"></span>&nbsp;&nbsp;否</a> 
		    <a id="cancelBtn" class="easyui-linkbutton" href="javascript:void(0)"><span class="pink icon-remove-sign bigger-180"></span>&nbsp;&nbsp;取消</a> 
	   	</div> -->
	   	<!-- Portal菜单切换时的提示窗口 End -->
	</div>
</body>
</html>