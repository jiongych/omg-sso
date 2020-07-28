<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户管理</title>
		<%@include file="../common/common-ztree.jsp" %>
		<%@include file="../common/common-miniui.jsp" %>
		<style type="text/css">
			div#rMenu {
				position: absolute;
				visibility: hidden;
				top: 0;
				background-color: #555;
				text-align: left;
				padding: 2px;
			}
			
			div#rMenu ul li {
				margin: 1px 0;
				padding: 0 5px;
				cursor: pointer;
				list-style: none outside none;
				background-color: #DFDFDF;
			}
			
			/*#dlgUserRoleRel .datagrid-cell {
				padding: 0px!important
			}*/
			
			#dlgUserRoleRel .datagrid-header td,#dlgUserRoleRel .datagrid-body td,#dlgUserRoleRel .datagrid-footer td {
			    border-style: solid;
			    border-left-color: transparent;
			    border-right-color: transparent;
			    border-top-color: transparent;
			    border-bottom-color: transparent;
			}
		</style>
	</head>

	<body class="easyui-layout">
		<script type="text/javascript">
		//页面加载
		$(function() {
			$("#dlgUserOrg").dialog({  
				onClose: function () {  
						var treeObj = $.fn.zTree.getZTreeObj("orgTree");
						var nodes = treeObj.getCheckedNodes();
						for(var i=0;i<nodes.length;i++){
							treeObj.checkNode(nodes[i],false,false);
						}
				}  
			});  
			$("#dlgUserOrgManager").dialog({  
				onClose: function () {  
						var treeObj = $.fn.zTree.getZTreeObj("orgManagerTree");
						var nodes = treeObj.getCheckedNodes();
						for(var i=0;i<nodes.length;i++){
							treeObj.checkNode(nodes[i],false,false);
						}
				}  
			}); 
		});
			function zTreeOnClick(event, treeId, treeNode) {
				$('#dgUser').datagrid("load", {
					'orgCode': treeNode.orgCode
				});
			};
			var orgIdValue;
			//页面加载  
			$(document).ready(function() {
				var zTreeSettings = {
					callback: {
						onClick: zTreeOnClick
					}
				};
				var zTreeNodes = [];
				var zTreeobj = null;
				orgIdValue = portal_global_currentUser.orgId;
				if (portal_global_currentUser.cmsOrgType == 10) {
					orgIdValue = null;
				}
				var id = portal_global_currentUser.orgId;
				if(portal_global_currentUser && portal_global_currentUser.cmsOrgType == 10){
					id = undefined;
				}
				$.ajax({
					url: "../org/findChildSyncOrgZTreeNew.do",
					data: {
						id:id,
						empCode:portal_global_currentUser.empCode
					},
					success: function(result) {
						zTreeNodes = result;
						zTreeObj = $.fn.zTree.init($("#treeOrg"), zTreeSettings, JSON.parse(unzip(zTreeNodes)));
					}
				})

				//请求全量用户机构树
				$.ajax({
					url: '../org/findChildSyncOrgZTree.do',
					data: {},
					success: function(data) {
						orgComboZTree("orgManagerTree", {}, {
							zTreeNodes: JSON.parse(unzip(data)),
							chkboxType : { "Y": "", "N": "" }
						});
					}
				})
				
				//请求全量用户机构树
				$.ajax({
					url: '../org/findAllSyncNosOrgZTree.do',
					data: {},
					success: function(data) {
						orgComboZTree("orgTree", {}, {
							zTreeNodes: JSON.parse(unzip(data))
						});
					}
				})
				/* $("#treeOrg").tree({
	    		url:'../org/findCmsOrgTree.do',
	    		onBeforeLoad : function(node,param){
	            	if(node!=null){
	            		param.parentOrgCode=node.id;
	            	}else{
						param.baseOrgCode=parent.currentUser.cmsBaseOrgCode;
					}
	            },
	            onClick : function(node){
	            	$('#dgUser').datagrid("load", {
		   				'baseOrgCode' : node.id
		   			});
	            }
	    	}) */
				//加载树
				/*admOrgTree("treeOrg","txtOrgName",{onClick: function(node) {
				$('#dgUser').datagrid("load", {
	   				'orgId' : node.id
	   			});
			}}); */

				//加载表格
				var columns = [
					[{
							field: 'empCode',
							title: '员工编号',
							width: 120,
							sortable: true,
							formatter: formatTip
						},
						{
							field: 'empName',
							title: '姓名',
							width: 100,
							formatter: formatTip
						},
						{
							field: 'positionName',
							title: '所属岗位',
							width: 100,
							formatter: formatTip
						},
						{
							field: 'orgName',
							title: '所属主机构',
							width: 100,
							formatter: function(value, row) {
								return row.orgVo.orgName;
							}
						},
						/* {field:'mobile',title:'手机号',width:140,formatter: formatTip},
						{field:'email',title:'邮箱',width:140,formatter: formatTip} , */
						{
							field: 'enabled',
							title: '是否启用',
							width: 40,
							formatter: formEnabledFlag
						},
						{
							field: 'createTime',
							title: '创建时间',
							align: 'center',
							width: 140,
							formatter: function(value) {
								return formatData(value)
							}
						},
						{
							field: 'lastLoginTime',
							title: '最后登录时间',
							align: 'center',
							width: 140,
							formatter: function(value) {
								return formatData(value)
							}
						}
					]
				];
				$("#roleList").datagrid({
					columns:[[
										{field:'roleCode',title:'角色编号',width:'44%',align:'left',
											formatter: formatTip},
										{field:'roleName',title:'角色名称',width:'44%',align:'left',
											formatter: formatTip}
									]]
				});
				var dataGridParams = {
					url: '${pageContext.request.contextPath}/user/findUserByPageNew.do',
					pageSize: 10,
					toolbar: '#tlbUser',
					singleSelect: 'true',
					queryParams: {
						orgId: orgIdValue
					},
					fitColumns: 'true',
					onSelect: function(index, row) {
						$('#roleList').datagrid('loadData',{total:0,rows:[]});
						$.ajax({
							url:"../role/findRoleByUser.do",
							data:{empCode: row.empCode},
							success:function(data){
								$("#roleList").datalist('loadData',data);
							}
						});
					}
				}
				newloadGrid('dgUser', columns, dataGridParams);

				//加载是否启用数据字典
				uceDictCombobox("cmbEnabled", "ENABLE");

				//loadDialogDatagrid();

			});
			//初始化是否启用数据字典
			initDictDatas("ENABLE,ORG_FLAG,SUPER_ADMIN");
			//机构标识
			var orgFlag = DictDatas["ORG_FLAG"] && DictDatas["ORG_FLAG"].length > 0 ? DictDatas["ORG_FLAG"][0].typeCode : 'CMS';
			//zTree
			var zTree = new Object();
			//初始化ZTreeNodes
			//initUserOrgZTreeNodes({orgFlag:orgFlag},zTree);
			//查询
			function findUser() {
				var queryParams = serializeFormObj("formFindUser");
				queryParams.orgId = orgIdValue;
				//var selectNode = $("#treeOrg").tree("getSelected");
				/*if(selectNode == null) {
					queryParams.baseOrgCode = parent.loginUser.loginCmsorg.cmsBaseOrgCode;
				} else {
					queryParams.baseOrgCode = selectNode.id;
				}*/
				$('#dgUser').datagrid("load", queryParams);
			}

			//重置
			function resetQuery() {
				$("#formFindUser").form('reset');
			}

			//分配
			function openUserOrgDlg() {
				var row = $('#dgUser').datagrid('getSelected');
				if(row == null) {
					showInfoMsg("请选择记录.");
				} else {
					var flag = false;
					/* DictDatas['SUPER_ADMIN'].map(function(e) {
						if(e.typeCode == parent.currentUser.empCode) {
							flag = true;
						}
					}) */
					/*if(!flag && parseInt(row.orgType) < parseInt(parent.currentUser.cmsOrgType)){
						showInfoMsg("普通用户禁止给上级机构用户维护数据权限.");
						return;
					}*/
					//初始化orgTree树
					$("#orgTreeText").textbox('clear');
					//userOrgComboZTree("orgTree",{orgFlag:orgFlag,empCode:row.empCode},{zTreeNodes:zTree.nodes,chkboxType:{"Y": "s", "N": "s" }});
					$('#formRole').form('load', row);
					$.ajax({
						url:"../user/findDataAuthByEmpCode.do",
						data:{empCode: row.empCode},
						success:function(data){
							if(data && data.length > 0) {
								var array = data.split(',');
								zTreeSetValues('orgTree',array );
								var text = "";
							    var pzt = $.fn.zTree.getZTreeObj('orgTree');
								for(var i=0;i<array.length;i++){
									var node = pzt.getNodeByParam("id",array[i]);
									text = text+node.name+',';
								}
								if (text.length > 0 ) text = text.substring(0, text.length-1);
								$("#orgTreeText").textbox('setValue',text);
							}
						}
					});
					$('#inpEmpCode').textbox('readonly');
					$('#inpEmpName').textbox('readonly');
					openDialog('dlgUserOrg', '数据权限');
				}
			}
			
			
			//分配
			function openUserOrgManagerDlg() {
				var row = $('#dgUser').datagrid('getSelected');
				if(row == null) {
					showInfoMsg("请选择记录.");
				} else {
					var flag = false;
					/* DictDatas['SUPER_ADMIN'].map(function(e) {
						if(e.typeCode == parent.currentUser.empCode) {
							flag = true;
						}
					}) */
					/*if(!flag && parseInt(row.orgType) < parseInt(parent.currentUser.cmsOrgType)){
						showInfoMsg("普通用户禁止给上级机构用户维护数据权限.");
						return;
					}*/
					//初始化orgTree树
					$("#orgManagerTreeText").textbox('clear');
					//userOrgComboZTree("orgTree",{orgFlag:orgFlag,empCode:row.empCode},{zTreeNodes:zTree.nodes,chkboxType:{"Y": "s", "N": "s" }});
					$('#formManagerRole').form('load', row);
					$.ajax({
						url:"../user/findDataManagerByEmpCode.do",
						data:{empCode: row.empCode},
						success:function(data){
							if(data && data.length > 0) {
								var array = data.split(',');
								zTreeSetValues('orgManagerTree',array );
								var text = "";
							    var pzt = $.fn.zTree.getZTreeObj('orgManagerTree');
								for(var i=0;i<array.length;i++){
									var node = pzt.getNodeByParam("id",array[i]);
									text = text+node.name+',';
								}
								if (text.length > 0 ) text = text.substring(0, text.length-1);
								$("#orgManagerTreeText").textbox('setValue',text);
							}
						}
					});
					$('#inpManagerEmpCode').textbox('readonly');
					$('#inpManagerEmpName').textbox('readonly');
					openDialog('dlgUserOrgManager', '管理机构范围');
				}
			}


			function loadDialogDatagrid() {
				var roleColumn1 = [
					[{
							field: 'roleCode',
							title: '角色编号',
							align: 'center',
							width: 40
						},
						{
							field: 'roleName',
							title: '角色名称',
							align: 'center',
							width: 60
						}
					]
				];
				var dataGridParm1 = {
					url: '',
					fitColumns: 'true',
					queryParams: {
						empCode: '',
						cmsBaseOrgCode: ''
					},
					toolbar: '#tlbUserRole',
					pagination: false,
					rownumbers: 'false'
				}
				var roleColumn2 = [
					[{
							field: 'roleCode',
							title: '角色编号',
							align: 'center',
							width: 40
						},
						{
							field: 'roleName',
							title: '角色名称',
							align: 'center',
							width: 60
						}
					]
				];
				var dataGridParm2 = {
					url: '',
					fitColumns: 'true',
					queryParams: {
						empCode: ''
					},
					toolbar: '',
					pagination: false,
					rownumbers: 'false'

				}
				$('#dgNotAddedUserRole').datalist({

				})
				//newloadGrid('dgNotAddedUserRole', roleColumn1, dataGridParm1);
				newloadGrid('dgAddedUserRole', roleColumn2, dataGridParm2);
				$("#dgNotAddedUserRole").datagrid('enableFilter');
			}

			//分配
			function openUserRoleRelDlg() {
				var row = $('#dgUser').datagrid('getSelected');
				if(row == null) {
					showInfoMsg("请选择记录.");
				} else {
					var flag = false;
					/* DictDatas['SUPER_ADMIN'].map(function(e) {
						if(e.typeCode == parent.currentUser.empCode) {
							flag = true;
						}
					}) */
					/*if(!flag && parseInt(row.orgType) < parseInt(parent.currentUser.cmsOrgType)){
						showInfoMsg("普通用户禁止给上级机构用户分配角色.");
						return;
					}*/
					$("#txtOrgCode").uceCombobox("setValue", "");
					$('#txtEmpCode').textbox("setValue", row.empCode);
					$('#txtEmpName').textbox("setValue", row.empName);
					$("#dgNotAddedUserRole").datagrid('loadData', {
						total: 0,
						rows: []
					}).datagrid('enableFilter');
					
					$("#dgAddedUserRole").datagrid('loadData', {
						total: 0,
						rows: []
					}).datagrid('enableFilter');
					$('#dgNotAddedUserRoleFilter').searchbox({
						onChange:function(value){
							$('#dgNotAddedUserRole').datagrid('addFilterRule',{
								field: 'roleCode',
								op: 'contains',
								value: value
							}).datagrid('addFilterRule',{
								field: 'roleName',
								op: 'contains',
								value: value
							}).datagrid('doFilter');
						}
					})
			        
					openDialog('dlgUserRoleRel', '角色设置');
					$('#dgAddedUserRole').datagrid({
						url: '${pageContext.request.contextPath}/role/findRoleByUser.do',
						columns:[[
							{field:'roleCode',title:'角色编号',width:'40%',align:'left',
								formatter: formatTip},
							{field:'roleName',title:'角色名称',width:'50%',align:'left',
								formatter: formatTip},
						]],
						fitColumns:true,
						filterMatchingType:'any',
						queryParams: {
							empCode: row.empCode
						},

					})
					$("#txtOrgCode").uceCombobox({
						url: '${pageContext.request.contextPath}/user/findOtherOrgRelationByEmpCode.do',
						valueField: 'cmsBaseOrgCode',
						textField: 'cmsOrgName',
						queryParams: {
							empId: row.empId,
							empCode: row.empCode
						},
						limitToList: true,
						editable: false,
						onLoadSuccess: function() { //加载完成后,设置选中第一项  
							var val = $(this).combobox("getData");
							for(var item in val[0]) {
								if(item == "cmsBaseOrgCode") {
									$(this).combobox("select", val[0][item]);
								}
							}
						},
						onChange: function(newValue, oldValue) {
							var roleLevel;
							var data = $(this).combobox('getData');
							var value = $(this).combobox('getValue');
							if(data != null && data.length > 0) {
								$.each(data, function(index, item) {
									if(item.cmsBaseOrgCode == value){
										roleLevel = item.cmsOrgType;
									}
								});
							}
							if(newValue != null && newValue != "") {
								$("#dgNotAddedUserRole").datagrid({
									url: '${pageContext.request.contextPath}/role/findNotRoleByUser.do',
									columns:[[
										{field:'roleCode',title:'角色编号',width:'40%',align:'left',
											formatter: formatTip},
										{field:'roleName',title:'角色名称',width:'50%',align:'left',
											formatter: formatTip},
									]],
									fitColumns:true,
									filterMatchingType:'any',
									queryParams: {
										empCode: row.empCode,
										baseOrgCode: newValue,
										roleLevel:roleLevel
									},
									onLoadSuccess:function(data){   
										var rows=$('#dgNotAddedUserRole').datagrid("getRows");
										var rightRows = $('#dgAddedUserRole').datagrid("getRows");
										if (rows.length > 0&&rightRows.length > 0) {
											var repeat = Array();
											for(var i=0;i<rows.length;i++){
												var isEquel = false;
												for(var j=0;j<rightRows.length;j++){
													if(rows[i].roleCode==rightRows[j].roleCode){
														isEquel = true;
														break;
													}
												}
												if(isEquel){
													repeat.push(i);
												}
											}
											for(var i=0;i<repeat.length;i++){
												$('#dgNotAddedUserRole').datagrid('deleteRow', repeat[i]);
											}
											
										}
									}
								});
							}
						}
					})

				}
			}

			//给用户移入角色
			function moveUserRoleIn() {
				var checkRows = $('#dgNotAddedUserRole').datagrid("getSelections");
				if(checkRows != null && checkRows.length > 0) {
					for(var i = 0; i < checkRows.length; i++) {
						//添加到角色到已添加这边
						$('#dgAddedUserRole').datagrid('appendRow', checkRows[i]);
						//删除未添加这边的该角色
						var rowIndex = $('#dgNotAddedUserRole').datagrid('getRowIndex', checkRows[i])
						$('#dgNotAddedUserRole').datagrid('deleteRow', rowIndex);
					}
				}
			}

			//给用户移入全部角色
			function moveUserRoleAllIn() {
				var checkRows = $('#dgNotAddedUserRole').datagrid("getRows");
				if(checkRows != null && checkRows.length > 0) {
					var rowNum = checkRows.length;
					for(var i = 0; i < rowNum; i++) {
						//添加到角色到已添加这边
						$('#dgAddedUserRole').datagrid('appendRow', checkRows[0]);
						//删除未添加这边的该角色
						var rowIndex = $('#dgNotAddedUserRole').datagrid('getRowIndex', checkRows[0])
						$('#dgNotAddedUserRole').datagrid('deleteRow', rowIndex);
					}
				}
			}

			//给用户移除角色
			function moveUserRoleOut() {
				var checkRows = $('#dgAddedUserRole').datagrid("getSelections");
				if(checkRows != null && checkRows.length > 0) {
					for(var i = 0; i < checkRows.length; i++) {
						//把角色移回到未添加这边
						$('#dgNotAddedUserRole').datagrid('appendRow', checkRows[i]);
						//删除未添加这边的该角色
						var rowIndex = $('#dgAddedUserRole').datagrid('getRowIndex', checkRows[i])
						$('#dgAddedUserRole').datagrid('deleteRow', rowIndex);
					}
				}
			}

			//给用户移除角色
			function moveUserRoleAllOut() {
				var checkRows = $('#dgAddedUserRole').datagrid("getRows");
				if(checkRows != null && checkRows.length > 0) {
					var rowNum = checkRows.length;
					for(var i = 0; i < rowNum; i++) {
						//把角色移回到未添加这边
						$('#dgNotAddedUserRole').datagrid('appendRow', checkRows[0]);
						//删除未添加这边的该角色
						var rowIndex = $('#dgAddedUserRole').datagrid('getRowIndex', checkRows[0])
						$('#dgAddedUserRole').datagrid('deleteRow', rowIndex);
					}
				}
			}

			// 保存用户角色
			function saveUserRole(btn) {
				$(btn).linkbutton('disable');
				var roleRows = $('#dgAddedUserRole').datagrid('getData').rows;
				var roleCodeListStr = '';
				if(roleRows && roleRows.length > 0) {
					for(var i = 0; i < roleRows.length; i++) {
						roleCodeListStr = roleCodeListStr + roleRows[i].roleCode + ',';
					}
				}
				$.ajax({
					url: '${pageContext.request.contextPath}/user/saveUserRoleRel.do',
					data: {
						'empCode': $("#txtEmpCode").val(),
						'roleCodeListStr': roleCodeListStr
					},
					task: function(data, statusText, xhr) {
						$("#roleList").datagrid('loadData', {
							total: 0,
							rows: []
						});
						$("#roleList").datagrid('reload');
						$('#dlgUserRoleRel').dialog('close');
					},
					finalTask: function() {
						$(btn).linkbutton('enable');
					}
				});
			}

			//保存用户机构
			function saveUserOrg(btn) {
				$(btn).linkbutton('disable');
				$("#formRole").form('submit', {
					url: '../user/updateUserOrg.do',
					task: function(result) {
						$(btn).linkbutton('enable');
						closeDialog('dlgUserOrg');
						reloadDatagrid('dgUser');
					}
				});
			}
			
			//保存用户机构
			function saveUserOrgManager(btn) {
				$(btn).linkbutton('disable');
				$("#formManagerRole").form('submit', {
					url: '../user/updateUserOrgManager.do',
					task: function(result) {
						$(btn).linkbutton('enable');
						closeDialog('dlgUserOrgManager');
					}
				});
			}
			
		</script>
		<!--左侧树-->
		<div data-options="region:'west',border:false" style="width:200px;">
			<div class="easyui-layout" data-options="fit:true,border:false">
				<div data-options="region:'north',border:false">
					<div style="margin-bottom:0px" data-options="region:'north',border:false">
						<!--  <input class="easyui-searchbox" id="txtOrgName" style="width:100%;" > -->
					</div>
				</div>
				<div data-options="region:'center'">
					<ul id="treeOrg" class="ztree"></ul>
				</div>
			</div>
		</div>
		<!--列表-->
		<div data-options="region:'center',border:false" style="width:550px;">
			<div class="easyui-layout" data-options="fit:true,border:false">
				<div data-options="region:'center',border:true" style="width:230px;height:100px;border:5px;">
					<div id="tlbUser" style="padding:5px;height:auto">
						<form action="#" id="formFindUser">
							<div id="aa" class="easyui-accordion">
								<div title="" data-options="selected:true">
									<ul class="search-form">
										<li><input class="easyui-textbox" label="员工编号：" name="empCode" data-options="prompt:' 请输入员工编号'"></li>
										<li><input class="easyui-textbox" label="员工名称：" name="empName" data-options="prompt:' 请输入员工名称'"></li>
										<li>
											<select name="enabled" label="是否启用：" style="width:100px;" id="cmbEnabled"></select>
										</li>
										<div>
											<a class="easyui-linkbutton search" onclick="findUser()">查询</a>
											<a class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
										</div>

									</ul>
								</div>
							</div>
							<div class="toolbar-margin">
								<shiro:hasPermission name="user_allocate"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-status" onclick="openUserRoleRelDlg()" plain="true">分配角色</a></shiro:hasPermission>
								<shiro:hasPermission name="user_org"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-permission" onclick="openUserOrgDlg()" plain="true">数据权限</a></shiro:hasPermission>
								<shiro:hasPermission name="user_org_manager"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-permission" onclick="openUserOrgManagerDlg()" plain="true">管理机构范围</a></shiro:hasPermission>
							</div>
						</form>
					</div>
					<input id="dgUser" class="easyui-datagrid" data-options="fit:true">
				</div>
				<div data-options="region:'east'" style="width:250px;">
					<input id="roleList" class="easyui-datagrid" data-options="fit:true">
					<!--<ul class="easyui-datalist" id="roleList" data-options="border:false" class="easyui-datagrid"></ul>-->
				</div>
			</div>
		</div>
		<!--编辑-->
		<div id="dlgUserRoleRel" class="easyui-dialog" data-options="title:'角色设置',closed:true" style="padding:5px 5px;background:#fff;height:500px;width:730px" buttons="#dlgUserRoleRel-btn">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north'" style="height:60px;  padding:10px;">
					所属机构: <input class="easyui-combobox" id="txtOrgCode"> 员工编号: <input class="easyui-textbox" id="txtEmpCode" disabled="disabled"> 员工名称: <input class="easyui-textbox" id="txtEmpName" disabled="disabled">
				</div>

				<div data-options="region:'east',title:'已添加角色',collapsible:false" style="width:300px; border:false" data-options="fit:true">
					<ul id="dgAddedUserRole" class="easyui-datagrid" style="width:100%;height:90%" data-options="border:false,fitColumns:true"></ul>
				</div>
				<div data-options="region:'west',title:'可添加角色',collapsible:false" style="width:300px; border:false" data-options="fit:true">
					<input class="easyui-searchbox" id="dgNotAddedUserRoleFilter" style="width:100%;height:9%"/>
					<ul id="dgNotAddedUserRole" class="easyui-datagrid" style="width:100%;;height:90%" data-options="border:false,fitColumns:true"></ul>
				</div>

				<div data-options="region:'center',border:false" style="padding-top:120px; padding-left:5px; height:200px">
					<a href="javascript:void(0)" class="easyui-linkbutton c1" style="width:110px; margin-bottom:10px;" onclick="moveUserRoleAllIn()"> 全部移入》</a>
					<a href="javascript:void(0)" class="easyui-linkbutton c1" style="width:110px; margin-bottom:10px;" onclick="moveUserRoleIn()"> 移入》</a>

					<a href="javascript:void(0)" class="easyui-linkbutton c5" style="width:110px; margin-bottom:10px;" onclick="moveUserRoleOut()">《移出</a>
					<a href="javascript:void(0)" class="easyui-linkbutton c5" style="width:110px; margin-bottom:10px;" onclick="moveUserRoleAllOut()">《全部移出</a>
				</div>
			</div>

		</div>
		<div id="dlgUserRoleRel-btn" style="padding-top:20px; padding-left:400px; border:2px;">
			<a href="javascript:void(0)" class="easyui-linkbutton save" onclick="saveUserRole(this)">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="closeDialog('dlgUserRoleRel')">取消</a>
		</div>

		<!--用户机构dialog-->
		<div id="dlgUserOrg" class="easyui-dialog" data-options="title:'角色设置',closed:true" style="background:#fff;height:450px;width:500px;" buttons="#dlgUserOrg-btn">
			<form id="formRole" method="post">
				<div style="padding: 15px 20px; background: #fff;">
					<table class="table" style="border: 0px;text-align:right;">
						<tbody>
							<tr>
								<td width="60px">员工编号:</td>
								<td width="150px"><input name="empCode" id="inpEmpCode" class="easyui-textbox" style="width: 150px;"></input></td>
								<td width="60px">员工姓名:</td>
								<td width="150px"><input name="empName" id="inpEmpName" class="easyui-textbox" style="width: 150px;"></input></td>
							</tr>
							<tr>
								<td>机构:</td>
								<td colspan="3">
									<div id="orgTreeBox">
										<input id="orgTreeText" class="easyui-textbox" style="width: 100%;">
										<div id="orgTreePanel" class="ztree-panel" style="height:250px;width:366px;">
											<ul id="orgTree" class="ztree"></ul>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>备注:</td>
								<td colspan="3"><input name="remark" class="easyui-textbox" validtype="length[1,125]" multiline="true" style="width: 100%;height: 60px;"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>
		<div id="dlgUserOrg-btn" style=" margin-bottom:10px;margin-top:10px;border:2px;">
			<a href="javascript:void(0)" class="easyui-linkbutton save" onclick="saveUserOrg(this)">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="closeDialog('dlgUserOrg')">取消</a>
		</div>
		
		<!--用户机构dialog-->
		<div id="dlgUserOrgManager" class="easyui-dialog" data-options="title:'角色设置',closed:true" style="background:#fff;height:450px;width:500px;" buttons="#dlgUserOrgManager-btn">
			<form id="formManagerRole" method="post">
				<div style="padding: 15px 20px; background: #fff;">
					<table class="table" style="border: 0px;text-align:right;">
						<tbody>
							<tr>
								<td width="60px">员工编号:</td>
								<td width="150px"><input name="empCode" id="inpManagerEmpCode" class="easyui-textbox" style="width: 150px;"></input></td>
								<td width="60px">员工姓名:</td>
								<td width="150px"><input name="empName" id="inpManagerEmpName" class="easyui-textbox" style="width: 150px;"></input></td>
							</tr>
							<tr>
								<td>机构:</td>
								<td colspan="3">
									<div id="orgManagerTreeBox">
										<input id="orgManagerTreeText" class="easyui-textbox" style="width: 100%;">
										<div id="orgManagerTreePanel" class="ztree-panel" style="height:250px;width:366px;">
											<ul id="orgManagerTree" class="ztree"></ul>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>
		<div id="dlgUserOrgManager-btn" style=" margin-bottom:10px;margin-top:10px;border:2px;">
			<a href="javascript:void(0)" class="easyui-linkbutton save" onclick="saveUserOrgManager(this)">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="closeDialog('dlgUserOrgManager')">取消</a>
		</div>
	</body>
</html>