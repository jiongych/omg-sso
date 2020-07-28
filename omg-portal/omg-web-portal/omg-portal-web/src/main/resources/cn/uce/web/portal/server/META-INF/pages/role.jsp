<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>角色管理</title>
		<%@include file="../common/common-ztree.jsp"%>
		<style>
			.datagrid-header-expander{
				padding:0 11px!important;
			}
			.search-form .search-ztree-li li{
				list-style-type: none;
				float: left;
				margin: 5px 10px;
				height: 100%;
				line-height: 100%;
			}
		</style>
	</head>

	<body>
		<script type="text/javascript">
			//用户权限组合ztree
			function userPermissionComboZTree(treeId,orgParams,treeParams){
				if(!treeId){
					console.error("参数校验未通过！");
					return;
				}
				var zTreeObj;
				var zTreeSetting;
				zTreeSetting = treeParams && treeParams.selectType=='single' ? 
					{
						data:{simpleData:{enable:true,pIdKey: 'pid',idKey:'id'},key:{title:'id'}},
						callback:{onClick: zTreeOnClick},
						view:{selectedMulti: false}
					} :
					{
						data:{simpleData:{enable:true,pIdKey: 'pid', idKey:'id'},key:{title:'id'}},
						callback:{onCheck: zTreeOnCheck},
						check:{enable: true,chkboxType: treeParams && treeParams.chkboxType ? treeParams.chkboxType : { "Y": "ps", "N": "s" }}
					};
				var zTreeNodes;
				if(treeParams && treeParams.zTreeNodes){
					zTreeNodes=treeParams.zTreeNodes;
				}
				var clickNode;
				if(zTreeNodes && zTreeNodes != null){
					initZTree();
					if(treeParams && treeParams.callback && treeParams.callback != null){
						treeParams.callback();
					}
				}
				
				
				//点击事件
				function zTreeOnClick(e, treeId, treeNode,clickFlag){
					$("#"+treeId+"~input").remove(":hidden");
					clickNode=treeNode;
					var text = "";
					if(clickFlag==0){//取消选中
						$("#"+treeId+"Text").textbox('setValue',text);
					}else if(clickFlag==1){//选中
						text=treeNode.name;
						$("#"+treeId+"Text").textbox('setValue',text);
					}
					$("#"+treeId+"Panel").fadeOut("fast");
				}
				//选中事件
				function zTreeOnCheck(e, treeId, treeNode){
					$("#"+treeId+"~input").remove(":hidden");
					clickNode=treeNode;
					var nodes = zTreeObj.getNodesByParam("isHidden", true);
					zTreeObj.showNodes(nodes);
					var checkedNodes = zTreeObj.getCheckedNodes(true);
					zTreeObj.hideNodes(nodes);
					var text = "";
					var value = "";
					for(var i=0;i<checkedNodes.length;i++){
						value += checkedNodes[i].id+",";
						text += checkedNodes[i].name + ",";
					}
					if (text.length > 0 ) text = text.substring(0, text.length-1);
					if (value.length > 0 ) value = value.substring(0, value.length-1);
					$("#"+treeId+"Text").textbox('setValue',text);
				}
				//显示下拉树
				function showMenu(){
					$("#"+treeId+"Panel").slideDown("fast");    
				}
				//隐藏下拉树
				function hideMenu() {
					$("#"+treeId+"Panel").fadeOut("fast");
				}
				//给textbox加下拉箭头并赋点击事件
				$("#"+treeId+"Text").textbox({
					icons:[{iconCls:'combo-arrow',
						handler: function(e){
								 if($("#"+treeId+"Panel").css('display') == 'none'){
										showMenu();
									}
									else{
										hideMenu();
									}
							}}],
					editable:true
				});
				//输入框点击事件
				$("input",$("#"+treeId+"Text").next("span")).click(function(){
					 if($(this).attr("readonly") != "readonly" && $("#"+treeId+"Panel").css('display') == 'none'){
						showMenu();
					 }else{
						hideMenu();
					 }
				})
				//点击Section以外的其他地方隐藏panel
				$("body").bind("mousedown", 
					function(event){
						if (!(event.target.id == treeId+"Box" || $(event.target).parents("#"+treeId+"Box").length>0)) {
							hideMenu();
						}
				 });
				//初始化树，默认展开第一级
				function initZTree(){
					zTreeObj = $.fn.zTree.init($("#"+treeId), zTreeSetting, zTreeNodes);
					//var zTreeRoots = zTreeObj.getNodesByFilter(function (node) { return node.level == 0 });
					//for(var i=0;i<zTreeRoots.length;i++){
						//zTreeObj.expandNode(zTreeRoots[i],true,false,false);
					//}
				}
			}
			/**
			 * 控制角色操作区是否显示
			 * @param psColumn 操作列显示总控
			 * @param... psButtonXXX 单个按钮是否有权限码
			 * @PS:如果放至列内会多次调用权限码校验方法
			 */
			var psOptControl = {
				psColumn: dealPermission(['role_add','role_details','role_edit', 'role_remove']),
				psButtonAdd: (dealPermission(['role_add']) ? 'none' : 'bolck'),
				psButtonDetails: (dealPermission(['role_details']) ? 'none' : 'bolck'),
				psButtonEdit: (dealPermission(['role_edit']) ? 'none' : 'bolck'),
				psButtonDelete: (dealPermission(['role_remove']) ? 'none' : 'bolck'),
				psButtonUserDeRole: (dealPermission(['role_user_remove']) ? 'none' : 'bolck'),
				psButtonRoleDeAllUser: (dealPermission(['role_user_all_remove']) ? 'none' : 'bolck')
			};
            var updatePermissionId = [];
			//页面加载
			$(function() {
				$("#dlgRole").dialog({  
					onClose: function () {  
						$("input",$("#orgTreeText").next("span")).trigger("keyup");
						$('#formRole').form('clear');
						updatePermissionId = [];
						sysCode = null;
					}  
				});  
				//加载角色dataGrid
				var columns = [
					[{
							field: 'id',
							title: '',
							hidden: true,
						},
						{
							field: 'sysCode',
							title: '',
							hidden: true,
						},
						{
							field: 'deal',
							title: '操作',
							/* hidden: psOptControl.psColumn, */
							align: 'center',
							width: 200,
							formatter: function(value, rec, index) {
								if(value) {
									return value;
								}
								return '<a class="icon-line iconfont uce-ck-details" title="查看" style="display:' + psOptControl.psButtonDetails + '" onclick="openRoleDetails(\'' + index + '\')" href="javascript:void(0)"></a>' +
									'<a class="icon-line iconfont uce-edit" title="编辑" style="display:' + psOptControl.psButtonEdit + '" onclick="openUpdateRole(\'' + index + '\')" href="javascript:void(0)"></a>' +
									'<a class="icon-line iconfont uce-delete" title="删除" style="display:' + psOptControl.psButtonDelete + '" onclick="deleteRole(\'' + index + '\')" href="javascript:void(0)"></a>' +
									'<a class="icon-line iconfont uce-error-circle" title="清空该角色下的所有用户" style="display:' + psOptControl.psButtonRoleDeAllUser + '" onclick="deleteAllUserRole(\'' + rec.roleCode + '\',\'' + index + '\')" href="javascript:void(0)"></a>';
							}
						},
						{
							field: 'roleCode',
							title: '角色编号',
							width: 180
						},
						{
							field: 'roleName',
							title: '角色名称',
							width: 150,
							formatter: formatTip
						},
						{
							field: 'roleLevel',
							title: '角色级别',
							width: 200,
							formatter: function(value) {
								return getTypeNameByCode("ROLE_LEVEL", value, formatTip);
							}
						},
						{
							field: 'roleScopeName',
							title: '作用范围',
							width: 200,
							formatter: formatTip
						},
						{
							field: 'assignsOrgType',
							title: '管理机构',
							width: 200,
							formatter: function(value) {
								return getTypeNameByCode("ASSIGNS_ORG_TYPE", value, formatTip);
							}
						},
						{
							field: 'createEmpName',
							title: '创建人',
							width: 200,
							formatter: formatTip
						},
						{
							field: 'createOrgName',
							title: '创建机构',
							width: 200,
							formatter: formatTip
						},
						{
							field: 'createTime',
							title: '创建时间',
							width: 200,
							formatter: function(value) {
								return formatData(value);
							}
						},
						{
							field: 'remark',
							title: '备注',
							width: 200,
							formatter: formatTip
						}
					]
				];
				var dataGridParams = {
					url: 'findRoleByPage.do',
					pageSize: 10,
					toolbar: '#tlbRole',
					singleSelect: 'true',
					fitColumns: 'true'
				}
				newloadGrid('dgRole', columns, dataGridParams);
				// 创建子网格
				$('#dgRole').datagrid({
					view: detailview,
					detailFormatter:function(index,row){
						return '<div style="margin-botom:3px;"><table id="roleUserTab' +index+ '" class="ddv"></table></div>';
					},
					onExpandRow: function(index,row){
						var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
						var parentIndex = index;
						ddv.datagrid({
							url:'findUserByRole.do?roleCode='+row.roleCode,
							fitColumns:true,
							singleSelect:true,
							pagination: true,
							pageSize: 10,
							pageList: [10,20,50,100],
							rownumbers:true,
							loadMsg:'',
							height:'auto',
							columns:[[
								{field:'empCode',title:'员工编号',width:100},
								{field:'empName',title:'员工名称',width:100},
								{field:'positionName',title:'员工岗位',width:100},
								{field:'empOrg',title:'员工所属机构',width:100},
								{
									field: 'deal',
									title: '操作',
									align: 'center',
									width: 50,
									formatter: function(value, rec, index) {
										return '<a class="icon-line iconfont uce-delete" title="删除" style="display:' + psOptControl.psButtonUserDeRole + '" onclick="deleteUserRole(\'' + rec.roleCode + '\',\'' + rec.empCode + '\',\'' + parentIndex + '\')" href="javascript:void(0)"></a>';
									}
								}
							]],
							onResize:function(){
								$('#dgRole').datagrid('fixDetailRowHeight',index);
							},
							onLoadSuccess:function(){
								setTimeout(function(){
									$('#dgRole').datagrid('fixDetailRowHeight',index);
								},0);
							}
						});
						$('#dgRole').datagrid('fixDetailRowHeight',index);
					}
				});

				//请求全量用户机构树
				$.ajax({
					url: '../org/findAllSyncCmsOrgZTree.do',
					data: {},
					success: function(data) {
						orgComboZTree("orgTree", {}, {
							zTreeNodes: JSON.parse(unzip(data)),
							selectType: 'single'
						});
					}
				});
				//加载角色类型数据字典
				uceDictCombobox("cmbCategory", "CATEGORY");
				//加载分配机构类型数据字典管理机构
				uceDictCombobox("assignsOrgType", "ASSIGNS_ORG_TYPE");
				//加载搜索角色类型数据字典
				uceDictCombobox("searchCategory", "CATEGORY");
				//加载搜索角色级别数据字典
				uceDictCombobox("searchRoleLevel", "ROLE_LEVEL"/* ,{onChange: searchRoleLevelOnChange} */);
				//加载角色权限树
				$.ajax({
					url: '../permission/findPermissionTreeByLevelNew.do',
					success: function(data) {
						userPermissionComboZTree("searchPerssiomTree", {}, {
							zTreeNodes: data
						});
					}
				});
				//新增按钮添加权限
				$('#addRoleBtn').css('display',psOptControl.psButtonAdd);
			});
			//记录用户是查看还是编辑或新增角色。0是新增，1是编辑，2是查看
            var operatorRoleType = 0;
			var sysCode = null;
			function roleSysCodeOnChange(newValue, oldValue) {
				sysCode = newValue;
				var pzt = $.fn.zTree.getZTreeObj('perssiomTree');
				var nodes = pzt.getNodesByParam("isHidden", true);
				pzt.showNodes(nodes);
				var nodes = pzt.getNodes();
				handlerPermission(pzt,sysCode,nodes);
			}
			function handlerPermission(pzt,sysCode,nodes) {
				var dataFilter = [];
				if(sysCode!=null&&sysCode!=""){
					for(var i = 0;i<nodes.length;i++){
						if (nodes[i].children == null) {
							dataFilter.push(nodes[i]);
						} else {
							var twoNodes = 0;
							for(var j=0;j<nodes[i].children.length;j++) {
								if (nodes[i].children[j].children == null) {
									dataFilter.push(nodes[i].children[j]);
									twoNodes++;
								} else {
									var threeNode = 0;
									for (var k=0;k<nodes[i].children[j].children.length;k++) {
										if (nodes[i].children[j].children[k].systemCode!=sysCode) {
											dataFilter.push(nodes[i].children[j].children[k]);
											threeNode ++;
										}
									}
									if (nodes[i].children[j].children.length == threeNode) {
										dataFilter.push(nodes[i].children[j]);
										twoNodes++;
									}
								}
							}
							if (nodes[i].children.length == twoNodes) {
								dataFilter.push(nodes[i]);
							}
						}
					}
					if (dataFilter.length > 0) {
						pzt.hideNodes(dataFilter);
					}
				}
			}
			var url;
			//初始化ROLE_LEVEL数据字典
			initDictDatas("ROLE_LEVEL,SUPER_ADMIN,CATEGORY,SYSTEM_CODE,ASSIGNS_ORG_TYPE");
			//角色级别加载过滤器
			function roleLevelLoadFilter(data) {
				var currLevel = parseInt(portal_global_currentUser.cmsOrgType);
				var filterData = new Array();
				for(var i = 0; i < data.length; i++) {
					if(parseInt(data[i].typeCode) >= currLevel) {
						filterData.push(data[i]);
					}
				}
				return filterData;
			}

			//角色级别改变事件
			function roleLevelOnChange(newValue, oldValue) {
				$("#keyword").val("");
				if(newValue==null||newValue==""){
					userPermissionComboZTree("perssiomTree", {}, {
							zTreeNodes: []
						});
				}else{
					//请求全量权限树
					$.ajax({
						url: '../permission/findPermissionTreeByLevelNew.do',
						data: {roleLevel: newValue},
						success: function(data) {
							userPermissionComboZTree("perssiomTree", {}, {
								zTreeNodes: data
							});
							var text = "";
							var pzt = $.fn.zTree.getZTreeObj('perssiomTree');
							for(var i=0;i<updatePermissionId.length;i++){
								var node = pzt.getNodeByParam("id",updatePermissionId[i]);
								if (null != node) {
									pzt.checkNode(node, true, false);
									text = text+node.name+',';
								}
								
							}
							if (text.length > 0 ) text = text.substring(0, text.length-1);
							$("#perssiomTreeText").textbox('setValue',text);
							var nodes = pzt.getNodes();
							handlerPermission(pzt,sysCode,nodes);
						}
					});
				}
			}
			
			//查询模块角色级别改变事件
/* 			function searchRoleLevelOnChange(newValue, oldValue) {
				if(newValue==null||newValue==""){
					userPermissionComboZTree("searchPerssiomTree", {}, {
							zTreeNodes: []
						});
				}else{
					//请求全量权限树
					$.ajax({
						url: '../permission/findPermissionTreeByLevelNew.do',
						data: {roleLevel: newValue},
						success: function(data) {
							userPermissionComboZTree("searchPerssiomTree", {}, {
								zTreeNodes: data
							});
							var text = "";
							var pzt = $.fn.zTree.getZTreeObj('searchPerssiomTree');
							for(var i=0;i<updatePermissionId.length;i++){
								var node = pzt.getNodeByParam("id",updatePermissionId[i]);
								pzt.checkNode(node, true, false);
								text = text+node.name+',';
							}
							if (text.length > 0 ) text = text.substring(0, text.length-1);
							$("#searchPerssiomTreeText").textbox('setValue',text);
							var nodes = pzt.getNodes();
							handlerPermission(pzt,sysCode,nodes);
						}
					});
				}
			} */

			//查询
			function findRoleData() {
				if($("#formFindRole").form('validate')) {
					$("#divSearchPermissionCode").html("");
					var pzt = $.fn.zTree.getZTreeObj('searchPerssiomTree');
					var nodes = pzt.getNodesByParam("isHidden", true);
					pzt.showNodes(nodes);
					var selectNotes = pzt.getCheckedNodes();
					if(selectNotes.length > 0) {
						if(selectNotes.length > 20){
							showInfoMsg('仅支持最多查询20个权限，请重新选择');
							return;
						}
						var permissionCodeArray = [];
						var systemCodeArray = [];
						$.each(
						selectNotes,
						function(i, value) {
							permissionCodeArray[i]=value.permissionCode;
							systemCodeArray[i]=value.systemCode;
						}); 
						$("#divSearchPermissionCode").append("<input type='hidden' name='permissionCodeList' value='" + permissionCodeArray + "'/>");
						$("#divSearchSystemCode").append("<input type='hidden' name='systemCodeList' value='" + systemCodeArray + "'/>");
					}
					$('#dgRole').datagrid('options').url = 'findRoleByPage.do';
					$('#dgRole').datagrid('load', serializeFormObj("formFindRole"));
				}
			}

			//重置
			function resetQuery() {
				$("#formFindRole").form('reset');
				$("#divSearchPermissionCode").empty();
				//加载角色权限树
				$.ajax({
					url: '../permission/findPermissionTreeByLevelNew.do',
					success: function(data) {
						userPermissionComboZTree("searchPerssiomTree", {}, {
							zTreeNodes: data
						});
					}
				});
			}
			

			//新增角色dialog
			function openAddRole() {
				operatorRoleType=0;
				//加载角色级别数据字典
				uceDictCombobox("cmbRoleLevel", "ROLE_LEVEL", {
					headerValue: false,
					loadFilter: roleLevelLoadFilter,
					onChange: roleLevelOnChange
				});
				//所属系统
				uceDictCombobox("cmbSysCode", "SYSTEM_CODE", {
					onChange: roleSysCodeOnChange
				});
				$("#cmbSysCode").combobox({panelHeight:300});
				$('#formRole').form('clear');
				$('#formRole').form('enableValidation');
				openDialog("dlgRole", '新增角色');
				$("#inpRoleCode").textbox("readonly", false);
				$("#roleName").textbox("readonly", false);
				$("#cmbCategory").combobox("readonly", false);
				$("#assignsOrgType").combobox("readonly", false);
				$("#cmbRoleLevel").combobox("readonly", false);
				$("#cmbSysCode").textbox("readonly", false);
				$("#perssiomTreeText").textbox("readonly", false);
				$("#orgTreeText").textbox("readonly", false);
				$("#remark").textbox("readonly", false);
				$("#divRoleBtn").css('display','block');
				url = 'addRole.do';
			}
			
			//查看角色dialog
			function openRoleDetails(index){
				operatorRoleType=2;
				var row = $('#dgRole').datagrid('getRows')[index];
				//加载角色级别数据字典
				uceDictCombobox("cmbRoleLevel", "ROLE_LEVEL", {
					headerValue: false,
					onChange: roleLevelOnChange
				});
				//所属系统
				uceDictCombobox("cmbSysCode", "SYSTEM_CODE", {
					onChange: roleSysCodeOnChange
				});
				$("#cmbSysCode").combobox({panelHeight:300});
				url = 'updateRole.do';
				uceAjax('../permission/findPortalMenuByRoleCode.do', {
					roleCode: row.roleCode
				}, function(data) {
					var text = "";
					updatePermissionId = data.data;
					var pzt = $.fn.zTree.getZTreeObj('perssiomTree');
					for(var i=0;i<data.data.length;i++){
						var node = pzt.getNodeByParam("id",data.data[i]);
						if (null != node) {
							pzt.checkNode(node, true, false);
							text = text+node.name+',';
						}
					}
					if (text.length > 0 ) text = text.substring(0, text.length-1);
				    $("#perssiomTreeText").textbox('setValue',text);
					
				});
				$('#formRole').form('load', row);
				uceAjax('../org/findCmsOrgByBaseOrgCode.do', {
					baseOrgCode: row.roleScope
				}, function(data) {
					$('#orgTreeText').textbox('setText', data.orgName == null ? row.roleScope : data.orgName);
				});
				openDialog("dlgRole", '查看角色');
				$('#orgTreeText').textbox('setValue',row.roleScope);
				
				$("#inpRoleCode").textbox("readonly");
				$("#roleName").textbox("readonly");
				$("#cmbCategory").combobox("readonly");
				$("#assignsOrgType").combobox("readonly");
				$("#cmbRoleLevel").combobox("readonly");
				$("#cmbSysCode").combobox("readonly");
				//$("#perssiomTreeText").textbox("readonly");
				$("#orgTreeText").textbox("readonly");
				$("#remark").textbox("readonly");
				$("#divRoleBtn").css('display','none');
				
			}

			//修改角色dialog
			function openUpdateRole(index) {
				operatorRoleType=1;
				var row = $('#dgRole').datagrid('getRows')[index];
/* 				var flag = false;
				DictDatas['SUPER_ADMIN'].map(function(e) {
					if(e.typeCode == portal_global_currentUser.empCode) {
						flag = true;
					}
				})
				if(!flag) {
					if(row.createEmp != portal_global_currentUser.empCode) {
						showInfoMsg('请联系此角色创建人或管理员修改..');
						return;
					}
				} */

				//$('#formRole').form('disableValidation');
				//加载角色级别数据字典
				uceDictCombobox("cmbRoleLevel", "ROLE_LEVEL", {
					headerValue: false,
					onChange: roleLevelOnChange
				});
				//所属系统
				uceDictCombobox("cmbSysCode", "SYSTEM_CODE", {
					onChange: roleSysCodeOnChange
				});
				$("#cmbSysCode").combobox({panelHeight:300});
				url = 'updateRole.do';
				uceAjax('../permission/findPortalMenuByRoleCode.do', {
					roleCode: row.roleCode
				}, function(data) {
					var text = "";
					updatePermissionId = data.data;
					var pzt = $.fn.zTree.getZTreeObj('perssiomTree');
					for(var i=0;i<data.data.length;i++){
						var node = pzt.getNodeByParam("id",data.data[i]);
						if (null != node) {
							pzt.checkNode(node, true, false);
							text = text+node.name+',';
						}
						
					}
					if (text.length > 0 ) text = text.substring(0, text.length-1);
				    $("#perssiomTreeText").textbox('setValue',text);
					
				});
				$('#formRole').form('load', row);
				uceAjax('../org/findCmsOrgByBaseOrgCode.do', {
					baseOrgCode: row.roleScope
				}, function(data) {
					$('#orgTreeText').textbox('setText', data.orgName == null ? row.roleScope : data.orgName);
				});
				openDialog("dlgRole", '编辑角色');
				$('#orgTreeText').textbox('setValue',row.roleScope);

				$("#roleName").textbox("readonly", false);
				$("#cmbCategory").combobox("readonly", false);
				$("#assignsOrgType").combobox("readonly", false);
				$("#cmbSysCode").textbox("readonly", false);
				$("#perssiomTreeText").textbox("readonly", false);
				$("#remark").textbox("readonly", false);
				
				$("#inpRoleCode").textbox("readonly");
				$("#cmbRoleLevel").combobox("readonly");
				$("#orgTreeText").textbox("readonly");
				$("#divRoleBtn").css('display','block');
				
			}

			//保存角色
			function saveRole(btn) {
				$(btn).linkbutton('disable');
				$('#formRole').form('submit', {
					url: url,
					onSubmit: function() {
						if(!$("input[name='zTreeOrgCode']").length > 0 && url == 'addRole.do') {
							$("#orgTreeText").textbox('setValue', '');
						}
						if($(this).form('validate')) {
							$("#divPermissionId").html("");
							$("#divPermissionCode").html("");
							$("#divSystemCode").html("");
							var pzt = $.fn.zTree.getZTreeObj('perssiomTree');
							var nodes = pzt.getNodesByParam("isHidden", true);
							pzt.showNodes(nodes);
							var selectNotes = pzt.getCheckedNodes();
							if(selectNotes.length > 0) {
								$.each(
									selectNotes,
									function(i, value) {
										$("#divSystemCode").append("<input type='hidden' name='systemCodeList' value='" + value.systemCode + "'/>");
										$("#divPermissionCode").append("<input type='hidden' name='permissionCodeList' value='" + value.permissionCode + "'/>");
										$("#divPermissionId").append("<input type='hidden' name='permissionIdList' value='" + value.id + "'/>");
									});
							}
							return true;
						}else{
							$(btn).linkbutton('enable');
						}
						return false;
					},
					task: function(result) {
						$(btn).linkbutton('enable');
						closeDialog("dlgRole");
						$('#dgRole').datagrid({url: 'findRoleByPage.do'});
						$('#dgRole').datagrid('reload'); // reload the role data
					},
					finalTask: function(result) {
						$(btn).linkbutton('enable');
					}
				});
			}

			//删除角色
			function deleteRole(index) {
				var row = $('#dgRole').datagrid('getRows')[index];
/* 				var flag = false;
				DictDatas['SUPER_ADMIN'].map(function(e) {
					if(e.typeCode == portal_global_currentUser.empCode) {
						flag = true;
					}
				})
				if(!flag) {
					if(row.createEmp != portal_global_currentUser.empCode) {
						showInfoMsg('请联系此角色创建人或管理员删除..');
						return;
					}
				} */
				confirmMsg('您确定要删除选中的角色吗？', function(row) {
					$.ajax({
						url: '${pageContext.request.contextPath}/role/deleteRole.do',
						data: {
							'roleCode': row.roleCode
						},
						task: function(data, statusText, xhr) {
							reloadDatagrid('dgRole');
						}
					});
				}, [row]);
			}
			function deleteUserRole(roleCode,empCode,parentIndex) {
				confirmMsg('您确定要将该用户从该角色中移除吗？', function(row) {
					$.ajax({
						url: '${pageContext.request.contextPath}/role/deleteUserRole.do',
						data: {'roleCode':roleCode,'empCode':empCode},
						task: function(data, statusText, xhr) {
							reloadDatagrid('roleUserTab' + parentIndex);
						}
					});
				});
			}
			function deleteAllUserRole(roleCode,parentIndex) {
				confirmMsg('您确定要删除该角色下的全部用户吗？', function(row) {
					$.ajax({
						url: '${pageContext.request.contextPath}/role/deleteAllUserRole.do',
						data: {'roleCode':roleCode},
						task: function(data, statusText, xhr) {
							reloadDatagrid('roleUserTab' + parentIndex);
						}
					});
				});
			}
			function permissionCheck() {
				openDialog("premissionSelect", '选择权限');
				if (operatorRoleType==2) {
					$("#divPerSelect").css('display','none');
				} else {
					$("#divPerSelect").css('display','block');
				}
				fuzzySearch('perssiomTree','#keyword',null,true); //初始化模糊搜索方法
			}
			//ztree配置
			var setting = {
				check: {
					enable: true//checkbox
				},
				view: {
					nameIsHTML: true, //允许name支持html                
					selectedMulti: false
				},
				edit: {
					enable: false,
					editNameSelectAll: false
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
			/**
			 * 
			 * @param zTreeId ztree对象的id,不需要#
			 * @param searchField 输入框选择器
			 * @param isHighLight 是否高亮,默认高亮,传入false禁用
			 * @param isExpand 是否展开,默认合拢,传入true展开
			 * @returns
			 */ 
			 function fuzzySearch(zTreeId, searchField, isHighLight, isExpand){
				var zTreeObj = $.fn.zTree.getZTreeObj(zTreeId);//获取树对象
				if(!zTreeObj){
					alter("获取树对象失败");
				}
				var nameKey = zTreeObj.setting.data.key.name; //获取name属性的key
				isHighLight = isHighLight===false?false:true;//除直接输入false的情况外,都默认为高亮
				isExpand = isExpand?true:false;
				zTreeObj.setting.view.nameIsHTML = isHighLight;//允许在节点名称中使用html,用于处理高亮
				
				var metaChar = '[\\[\\]\\\\\^\\$\\.\\|\\?\\*\\+\\(\\)]'; //js正则表达式元字符集
				var rexMeta = new RegExp(metaChar, 'gi');//匹配元字符的正则表达式
				
				// 过滤ztree显示数据
				function ztreeFilter(zTreeObj,_keywords,callBackFunc) {
					if(!_keywords){
						_keywords =''; //如果为空，赋值空字符串
					}
					
					// 查找符合条件的叶子节点
					function filterFunc(node) {
						if(node && node.oldname && node.oldname.length>0){
							node[nameKey] = node.oldname; //如果存在原始名称则恢复原始名称
						}
						//node.highlight = false; //取消高亮
						zTreeObj.updateNode(node); //更新节点让之前对节点所做的修改生效
						if (_keywords.length == 0) { 
							//如果关键字为空,返回true,表示每个节点都显示
							zTreeObj.showNode(node);
							zTreeObj.expandNode(node,isExpand); //关键字为空时是否展开节点
							return true;
						}
						//节点名称和关键字都用toLowerCase()做小写处理
						if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase())!=-1) {
							if(isHighLight){ //如果高亮，对文字进行高亮处理
								//创建一个新变量newKeywords,不影响_keywords在下一个节点使用
								//对_keywords中的元字符进行处理,否则无法在replace中使用RegExp
								var newKeywords = _keywords.replace(rexMeta,function(matchStr){
									//对元字符做转义处理
									return '\\' + matchStr;
									
								});
								node.oldname = node[nameKey]; //缓存原有名称用于恢复
								//为处理过元字符的_keywords创建正则表达式,全局且不分大小写
								var rexGlobal = new RegExp(newKeywords, 'gi');//'g'代表全局匹配,'i'代表不区分大小写
								//无法直接使用replace(/substr/g,replacement)方法,所以使用RegExp
								node[nameKey] = node.oldname.replace(rexGlobal, function(originalText){
									//将所有匹配的子串加上高亮效果
									var highLightText =
										'<span style="color: whitesmoke;background-color: darkred;">'
										+ originalText
										+'</span>';
									return  originalText;                  
								});
								zTreeObj.updateNode(node); //update让更名和高亮生效
							}
							zTreeObj.showNode(node);//显示符合条件的节点
							return true; //带有关键字的节点不隐藏
						}
						
						zTreeObj.hideNode(node); // 隐藏不符合要求的节点
						return false; //不符合返回false
					}
					var nodesShow = zTreeObj.getNodesByFilter(filterFunc); //获取匹配关键字的节点
					processShowNodes(nodesShow, _keywords);//对获取的节点进行二次处理
				}
				
				/**
				 * 对符合条件的节点做二次处理
				 */
				function processShowNodes(nodesShow,_keywords){
					if(nodesShow && nodesShow.length>0){
						//关键字不为空时对关键字节点的祖先节点进行二次处理
						if(_keywords.length>0){ 
							$.each(nodesShow, function(n,obj){
								var pathOfOne = obj.getPath();//向上追溯,获取节点的所有祖先节点(包括自己)
								if(pathOfOne && pathOfOne.length>0){
									// i < pathOfOne.length-1, 对节点本身不再操作
									for(var i=0;i<pathOfOne.length-1;i++){
										zTreeObj.showNode(pathOfOne[i]); //显示节点
										zTreeObj.expandNode(pathOfOne[i],true); //展开节点
									}
								}
							});             
						}else{ //关键字为空则显示所有节点, 此时展开根节点
							var rootNodes = zTreeObj.getNodesByParam('level','0');//获得所有根节点
							$.each(rootNodes,function(n,obj){
								zTreeObj.expandNode(obj,true); //展开所有根节点
							});
						}
					}
				}
				
				//监听关键字input输入框文字变化事件
				$(searchField).bind('input propertychange', function() {
					var _keywords = $(this).val();
					searchNodeLazy(_keywords); //调用延时处理
				});

				var timeoutId = null;
				// 有输入后定时执行一次，如果上次的输入还没有被执行，那么就取消上一次的执行
				function searchNodeLazy(_keywords) {
					if (timeoutId) { //如果不为空,结束任务
						clearTimeout(timeoutId);
					}
					timeoutId = setTimeout(function() {
						ztreeFilter(zTreeObj,_keywords);    //延时执行筛选方法
						$(searchField).focus();//输入框重新获取焦点
					}, 100);
				}
			}
		</script>

		<!--角色表格 -->
		<div id="tlbRole">
			<form action="#" id="formFindRole">
				<div id="aa" class="easyui-accordion">
					<div title="" data-options="selected:true">
						<ul class="search-form">
							<li><input class="easyui-textbox" label="角色编号：" labelPosition="left" name="roleCode" data-options="prompt:'请输入角色编号'"></li>
							<li><input class="easyui-textbox" label="角色名称：" labelPosition="left" name="roleName" data-options="prompt:'请输入角色名称'"></li>
							<li>
								<select name="category" label="角色类型：" style="width:100px;" id="searchCategory"></select>
							</li>
							<li>
								<select name="roleLevel" label="角色级别：" style="width:100px;" id="searchRoleLevel"></select>
							</li>
							<li class="search-ztree-li">
								<div id="searchPerssiomTreeBox" class="search-ztree-div" style="margin:0px">
									<input id="searchPerssiomTreeText" class="easyui-textbox" label="权限：" labelPosition="left">
									<div id="searchPerssiomTreePanel" class="ztree-panel" style="height:200px;width:336px;position: fixed">
										<ul id="searchPerssiomTree" name="permissionCodeList" class="ztree"></ul>
									</div>
								</div>
								<div id="divSearchSystemCode"></div>
								<div id="divSearchPermissionCode"></div>
							</li>
							<div>
								<a href="#" id="searchBtn" class="easyui-linkbutton search" onclick="findRoleData()">查询</a>
								<a href="#" id="resetBtn" class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
							</div>
						</ul>
					</div>
				</div>
				<div class="toolbar-margin">
					<a href="javascript:void(0)" id="addRoleBtn" class="easyui-linkbutton" iconCls="iconfont uce-add" onclick="openAddRole()" plain="true">新增</a>
				</div>

			</form>
		</div>
		<table id="dgRole" class="easyui-datagrid" data-options="fit:true"></table>

		<!--新增修改dialog -->
		<div id="dlgRole" class="easyui-dialog" style="width: 800px; height: 450px; padding: 5px" closed="true" buttons="#divRoleBtn">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'center',border:false" style="padding: 15px 20px; background: #fff;">
					<form id="formRole" method="post">
						<input type="hidden" name="id" />
						<table class="table" style="border: 0px;text-align:right;">
							<tbody>
								<tr style="padding-left: 20px">
									<td width="80px">角色编号:</td>
									<td width="130px"><input name="roleCode" id="inpRoleCode" class="easyui-textbox" style="width:130px;" required="true" data-options="validType:['length[1,32]','roleCode']"></td>
									<td width="80px">角色名称:</td>
									<td width="130px"><input name="roleName" class="easyui-textbox" id="roleName" style="width:130px;" required="true" validtype="length[1,32]"></td>
									
									<td width="80px">角色类型:</td>
									<td width="130px"><input name="category" class="easyui-combobox" id="cmbCategory" style="width:130px;" validtype="length[1,32]"></td>
								</tr>
								<tr style="padding-left: 20px">
									<td width="80px">管理机构:</td>
									<td width="130px">
										<input name="assignsOrgType" class="easyui-combobox" id="assignsOrgType" style="width:130px;">
									</td>
									<td width="80px">角色级别:</td>
									<td width="130px"><input name="roleLevel" class="easyui-combobox" id="cmbRoleLevel" style="width:130px;" required="true" validtype="length[1,32]"></td>
									<td></td>
								</tr>
								<tr style="padding-left: 20px">
									<td>系统分类:</td>
									<td><input name="sysCode" class="easyui-combobox" id="cmbSysCode" style="width:100%"></td>
									<td width="60px">权限:</td>
									<td colspan="5">
										<div id="perssiomTreeBox"  onClick="permissionCheck()">
											<input id="perssiomTreeText" class="easyui-textbox" style="width:100%">
											
										</div>
										<div id="divSystemCode"></div>
										<div id="divPermissionCode"></div>	
										<div id="divPermissionId"></div>
									</td>
								</tr>
								<tr style="padding-left: 20px">
									<td>作用范围:</td>
									<td>
										<div id="orgTreeBox">
											<input id="orgTreeText" class="easyui-textbox" style="width:100%" required="true">
											<div id="orgTreePanel" class="ztree-panel" style="height:155px;width:182px">
												<ul id="orgTree" name="roleScope" class="ztree"></ul>
											</div>
										</div>
									</td>
									<td width="60px">备注:</td>
									<td colspan="5"><input name="remark" class="easyui-textbox" id="remark" validtype="length[1,125]" multiline="true" style="width:100%;height:60px;"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div id="divRoleBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton  save" onclick="saveRole(this)">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('dlgRole')">取消</a>
		</div>

		<div id="premissionSelect" class="easyui-dialog" style="width: 800px; height:100%; padding: 5px;top:2px;" closed="true" buttons="#divPerSelect">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'center',border:false" style="padding: 15px 20px; background: #fff;">
					<input id="keyword" style="padding-left:10px;height:25px;padding-right:10px;border-radius: 6px;border-style:inset;outline: none;width:97%;" placeholder="请输入需要搜索的菜单名称">
					<div class="ztree-panel" style="height:90%;width:95%;display: block;">
						<ul id="perssiomTree" name="permissionCodeList" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
		<div id="divPerSelect">
			<a href="javascript:void(0)" class="easyui-linkbutton  save" onclick="javascript:closeDialog('premissionSelect')">确定</a>
		</div>
	</body>

</html>