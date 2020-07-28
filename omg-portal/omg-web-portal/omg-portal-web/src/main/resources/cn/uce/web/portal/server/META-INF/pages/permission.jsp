<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>权限管理</title>
    <%@include file="../common/common.jsp" %>
</head>
<body  class="easyui-layout">
	<script type="text/javascript">
		/**
		 * 控制权限权操作区是否显示
		 * @param psColumn 操作列显示总控
		 * @param... psButtonXXX 单个按钮是否有权限码
		 * @PS:如果放至列内会多次调用权限码校验方法
		 */
		var psOptControl ={
			psColumn:dealPermission(['permission_edit','permission_remove']),
			psButtonEdit:(dealPermission(['permission_edit']) ? 'bolck' : 'bolck'),
			psButtonDelete:(dealPermission(['permission_remove']) ? 'bolck' : 'bolck')
		};
	
     	//页面加载  
       $(document).ready(function(){

       	//加载dataGrid
    	var columns=[[  
			{field:'id',title: 'id' ,hidden:true,align:'center'},
	        {field:'deal',title: '操作',
				/* hidden:psOptControl.psColumn, */
				align:'center',width: 50,formatter:function(value,rec,index){
	        	if(value){
	        		return value;
	        	}
	        	return '<span class="icon-line iconfont uce-edit" title="编辑" style="display:'+psOptControl.psButtonEdit +'" onclick="openUpdatePermission(\''+index+'\')" href="javascript:void(0)"></span>'+
					   '<span class="icon-line iconfont uce-delete" title="删除" style="display:'+psOptControl.psButtonDelete +'" onclick="deletePermission(\''+index+'\')" href="javascript:void(0)"></span>';
	        }},
	        {field:'permissionCode',title: '权限编码' ,width: 60,formatter: formatPermissionCode},
			{field:'permissionName',title: '权限名称', width: 50,formatter: formatTip},
			{field:'permissionUrl',title: '权限路径', width: 70,formatter: formatTip} ,
			{field:'sort',title: '序号',align:'right', width: 20,formatter: formatTip} ,
			{field:'isHide',title: '是否隐藏',align:'right', width: 20,formatter: formEnabledFlag} ,
			{field:'controlType',title: '组件类型', width: 30, formatter: function(value){
				return getTypeNameByCode("CONTROL_TYPE",value);
			}},
			{field:'sysUrl',title: '所属模块',width: 70,formatter: function(value){
				return getTypeNameByCode("SYS_PATH",value);
			}},
			{field:'systemCode',title: '所属系统',width: 50,formatter: function(value){
				return getTypeNameByCode("SYSTEM_CODE",value);
			}},
			/* {field:'category',title: '权限类型',width: 30,formatter: function(value){
				return getTypeNameByCode("CATEGORY", value, formatTip);
			}}, */
			{field:'permissionLevels',title: '权限级别',width: 80,formatter: function(value){
				return pLevelFormat(value,formatTip);
			}}
		]];
    	var dataGridParams = {
			url : 'findPermissionByPage.do',
			pageSize : 10,
			toolbar : '#tlbPermission',
			singleSelect : 'true',
			fitColumns : 'true'
		}
		newloadGrid('dgPermission', columns, dataGridParams);
    	//加载权限树
    	loadPermissionTree();
    	//菜单权限树搜索过滤器
       	$("#schPermissionName").searchbox({
      			onChange: function(value) {
       			$('#treePermission').tree("doFilter", value);	
       		}
       	})
       	//加载“组件类型”下拉列表
       	uceDictCombobox("cmbControlType","CONTROL_TYPE",{headerValue:false});
       	//加载“所属模块”下拉列表
       	uceDictCombobox("sysUrlCombobox","SYS_PATH",{headerValue:false});
		$("#sysUrlCombobox").combobox({panelHeight:300});
		//加载“所属系统”下拉列表
       	uceDictCombobox("systemCode","SYSTEM_CODE",{headerValue:false});
      //加载“所属类别”下拉列表
       	/* uceDictCombobox("category","CATEGORY",{headerValue:false}); */
		//加载搜索所属系统数据字典
		uceDictCombobox("searchSystemCode", "SYSTEM_CODE");
       	
		//加载搜索所属系统数据字典
		uceDictCombobox("isHide", "ENABLE",{
          headerValue: false
          
        });        
		$("#searchSystemCode").combobox({
			onLoadSuccess:function(value){
				var rows = $("#searchSystemCode").combobox('getData');
				var isHave = false;
				for (var i = 0; i < rows.length; i++) {
					if (rows[i].typeCode == 'MENU') {
						rows.splice(i, 1);
						isHave = true;
						break; 
					}
				}
				if(isHave){
					$("#searchSystemCode").combobox('loadData', rows).combobox('setValue','');
				}
			}
		});
		$("#systemCode").combobox({
			panelHeight:300,
			onLoadSuccess:function(value){
				var rows = $("#systemCode").combobox('getData');
				var isHave = false;
				for (var i = 0; i < rows.length; i++) {
					if (rows[i].typeCode == 'MENU') {
						rows.splice(i, 1);
						isHave = true;
						break; 
					}
				}
				if(isHave){
					$("#systemCode").combobox('loadData', rows).combobox('setValue','');
				}
			}
		});
		//加载搜索角色类型数据字典
		/* uceDictCombobox("searchCategory", "CATEGORY"); */
   });
     	
     	
    var url ="";
	//初始化数据字典
	initDictDatas("ROLE_LEVEL,CONTROL_TYPE,SYS_PATH,SYSTEM_CODE,CATEGORY,ENABLE");
	
	//格式化权限码
	function formatPermissionCode(value,row,index){
		return value == null ? "" : (row.roleCodeStr == null ? "<a title='"+value+"【未分配角色】'>" + value + "</a>" : "<a title='"+value+"【已分配角色】[" + row.roleCodeStr + "]'>" + value + "</a>");
	}
	
	//查询
    function findPermissionData(){
       	$('#dgPermission').datagrid('load', serializeFormObj("formPermissionRole"));
    }
	
       //重置
	function resetQuery(){
		$("#formPermissionRole").form('reset');
	}
       
       //加载权限树
	function loadPermissionTree(){
    	$.ajax({
    		url:'${pageContext.request.contextPath}/permission/findPermissionTree.do',
    		success:function(result){
    			//加载菜单树
    			$('#treePermission').tree({
            		data:result,
            		onSelect : function(node){
            			$(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);
            			$('#dgPermission').datagrid("load", {
               				'parentPermission' : node.id
               			});
            		}
            	});
    			//加载组合树
    			$("#cmbtreePermission").combotree({
            		data:result,
					editable:true,
    				onChange :function (newValue,oldValue){
    					$("#cmbboxPermissionLevels").uceCombobox('clear');
    					$("#cmbboxPermissionLevels").uceCombobox({
    						url:'../permission/findParentPermissionLevels.do',
    						queryParams:{id:newValue},
    						valueField:'roleLevelCode',
    						textField:'roleLevelName',
    						multiple:true,
    						editable:false
    					})
    				}
            	});
    		}
    	})
	}
       
       //打开新增dialog
	function openAddPermission() {
       	$("#formPermission").form('clear');
       	$("#cmbControlType").combobox('readonly', false);
		$("#sysNameCombobox").combobox('readonly', false);
		$('#cmbtreePermission').combotree('readonly', false);
     	$('#txtPermissionCode').textbox('readonly', false);
     	$('#systemCode').textbox('readonly', false);
     	$('#formPermission').form('enableValidation');
       	//获取权限菜单树选中的节点作为新增权限的父节点
       	var treePermission = $('#treePermission').tree("getSelected");
       	if (treePermission != null) {//有选中的赋值给父节点，触发onChange事件
       		$('#cmbtreePermission').combotree('setValue', treePermission.id);
       	}else{//表示新增模块，手动查询
       		$("#cmbboxPermissionLevels").uceCombobox({
           		url:'../permission/findParentPermissionLevels.do',
           		valueField:'roleLevelCode',
           		textField:'roleLevelName',
           		multiple:true,
           		editable:false,
				onLoadSuccess:function(value){
					var rows = $("#cmbboxPermissionLevels").combobox('getData');
					var isHave = false;
					for (var i = 0; i < rows.length; i++) {
						if (rows[i].roleLevelCode == '100') {
							rows.splice(i, 1);
							isHave = true;
							 break; 
						}
					}
					if(isHave){
						$("#cmbboxPermissionLevels").combobox('loadData', rows).combobox('setValue','');
					}
				}
           	})
       	}
  		url ="${pageContext.request.contextPath}/permission/addPermission.do";
  		openDialog('dlgPermission', '新增权限');
    }
       
	//打开修改dialog
	function openUpdatePermission(index) {
		var row=$('#dgPermission').datagrid('getRows')[index];
		$("#cmbboxPermissionLevels").uceCombobox({
			url:'../permission/findParentPermissionLevels.do',
			queryParams:{id:row.parentPermission},
			valueField:'roleLevelCode',
			textField:'roleLevelName',
			multiple:true,
			onLoadSuccess:function(value){
				var rows = $("#cmbboxPermissionLevels").combobox('getData');
				var isHave = false;
				for (var i = 0; i < rows.length; i++) {
					if (rows[i].roleLevelCode == '100') {
						rows.splice(i, 1);
						isHave = true;
						 break; 
					}
				}
				if(isHave){
					$("#cmbboxPermissionLevels").combobox('loadData', rows);
				}
			}
		})
		url = '${pageContext.request.contextPath}/permission/updatePermission.do?id='+row.id;
		openDialog("dlgPermission", '编辑权限');
		$('#formPermission').form('load',row);
		$('#txtOldPermissionLevels').val(row.permissionLevels);
		$('#txtPermissionCode').textbox('disableValidation');//将权限编号的校验去掉
		$('#txtPermissionCode').textbox('readonly');
		$('#systemCode').textbox('readonly');
		$("#cmbControlType").combobox('readonly');
		$("#sysNameCombobox").combobox('readonly');
		$('#cmbtreePermission').combotree('readonly');
		$('#cmbtreePermission').combotree('setValue', row.parentPermission);
	}
	
	//保存权限
    function savePermission(btn) {
		$(btn).linkbutton('disable');
   		$("#formPermission").form('submit',{
   			url : url,
   			task: function(result){
   				if (result.data == -100) {
					$.messager.alert('权限编码重复','权限编码己存在','info');
					$(btn).linkbutton('enable');
				} else if (result.data == -200) {
					$.messager.alert('权限编码重复','权限编码己存在或系统异常','info');
					$(btn).linkbutton('enable');
				} else {
					$('#txtPermissionCode').textbox('reduce');
					closeDialog('dlgPermission');
					reloadDatagrid('dgPermission');
					loadPermissionTree();
				}
   				
				//window.top.vm.getMenus();//局部刷新菜单数据
   			},
   			finalTask:function(){
   				$(btn).linkbutton('enable');
   			}
   		});
    }
	
	//删除权限
	function deletePermission(index){
    	var row=$('#dgPermission').datagrid('getRows')[index];
		confirmMsg('您确定要删除选中的权限吗？', function(row) {
			$.ajax({
				url:'${pageContext.request.contextPath}/permission/deletePermission.do?',
				data :{'permissionCode': row.permissionCode,'parentPermission' : row.parentPermission,'systemCode':row.systemCode,'id':row.id},
				task : function(data, statusText, xhr){
					reloadDatagrid('dgPermission');
					loadPermissionTree();
				}
			});
		}, [row]);
    }
    </script>
	<!-- 权限菜单树 -->
	<div data-options="region:'west',border:false" style="width:230px;">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',border:false" >
				<div style="margin-bottom:0px" data-options="region:'north',border:false" >
		            <input class="easyui-searchbox"  style="width:100%;" data-options="prompt:'请输入过滤内容...'" id="schPermissionName">
		        </div>
			</div>
			<div data-options="region:'center'">
				<ul id="treePermission" class="easyui-tree"></ul>
			</div>
		</div>
    </div>  
    <!-- 权限表格 -->
    <div data-options="region:'center',border:false,plain:true">
    	<div id="tlbPermission">
			<form action="#" id="formPermissionRole">
			   <div id="aa" class="easyui-accordion">   
					<div title="" data-options="selected:true">
						<ul class="search-form" > 
							<li><input class="easyui-textbox inputcss" label="权限编码："  name="permissionCode" data-options="prompt:'请输入权限编码'" ></li>
							<li><input class="easyui-textbox inputcss" name="permissionName" label="权限名称：" data-options="prompt:'请输入权限名称'"></li>
							<li>
								<input name="systemCode" class="easyui-combobox" label="所属系统：" style="width:100px;" id="searchSystemCode"/>
							</li>
							<li>
								<select name="parentPermission" class="easyui-combobox" label="一级菜单：" style="width:100px;" id="searchParentPermission">
									<option value ="">--请选择--</option>
									<option value ="0">一级菜单</option>
								</select>
							</li>
							<!-- <li>
								<select name="category" label="权限类型：" style="width:100px;" id="searchCategory"></select>
							</li> -->
							<div>
								<a href="#" class="easyui-linkbutton search" onclick="findPermissionData()">查询</a>
								<a href="#" class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
							</div>
						</ul>
					</div>
				</div>
			  <div class="toolbar-margin">
				  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-add" onclick="openAddPermission()" plain="true">新增</a>
			  </div>
			</form> 
		</div>
		<table id="dgPermission" data-options="fit:true"></table>
    </div>
	
	<!--新增编辑权限dialog-->
    <div id="dlgPermission" class="easyui-dialog" style="width:420px;height:95%; padding:10px 20px;" closed="true" buttons="#divPermissionBtn">
        <form id="formPermission" method="post" >
        	<table class="table" style="width: 100%; border:0px;margin: 0 auto;">
				<tbody>
					<tr>
						<td width="30%">权限编码:</td>
						<td width="70%">
							<input name="permissionCode" id="txtPermissionCode"  class="easyui-textbox" data-options="required:true,invalidMessage:'权限编码重复，请修改',validType:{speicalCharFilter:[],remote:['${pageContext.request.contextPath}/permission/findByPermissionCode.do','permissionCode'],length:[1,60]}" style="width:100%">
						</td>
					</tr>
					<tr>
						<td>权限名称:</td>
						<td>
							<input name="permissionName" class="easyui-textbox" required="true" style="width:100%" validtype="length[1,16]"/>
						</td>
					</tr>
					<tr>
						<td>所属系统:</td>
						<td>
		                    <input id="systemCode" name="systemCode" class="easyui-combobox" value=""  style="width:100%" required="true"/>
						</td>
					</tr>
					<tr>
						<td>所属模块:</td>
						<td>
		                    <input id="sysUrlCombobox" name="sysUrl" class="easyui-combobox" value=""  style="width:100%" required="true" />
						</td>
					</tr>
					<tr>
						<td>权限类型:</td>
						<td>
							<select name="controlType" id="cmbControlType"  required="true" style="width:100%"></select>
						</td>
					</tr>
					<tr>
						<td>是否隐藏:</td>
						<td>
							<select name="isHide" id="isHide"  required="true" style="width:100%"></select>
						</td>
					</tr>
					<!-- <tr>
						<td>权限类型:</td>
						<td>
		                    <input id="category" name="category" value=""  style="width:100%" />
						</td>
					</tr> -->
					<tr>
						<td>父级菜单:</td>
						<td>
		                    <select name="parentPermission" id="cmbtreePermission" class="easyui-combotree" style="width:100%"></select>
						</td>
					</tr>
					<tr>
						<td>权限路径:</td>
						<td>
							<input name="permissionUrl" class="easyui-textbox" style="width:100%"/>
						</td>
					</tr>
					<tr>
						<td>权限级别:</td>
						<td>
							<input name="permissionLevels" id="cmbboxPermissionLevels" class="easyui-combobox" data-options="required:true" style="width:100%"/>
							<input name="oldPermissionLevels" id="txtOldPermissionLevels"  style="width:100%;display:none"/>
						</td>
					</tr>
					<tr>
						<td>序号:</td>
						<td>
							 <input name="sort" class="easyui-numberbox" style="width:100%" data-options="required:true,validType:{length:[1,9]}">
						</td>
					</tr>
					<tr>
						<td>菜单图标:</td>
						<td>
							<input name="permissionIcon" class="easyui-textbox" data-options="prompt:'请输入图标样式名，如uce-file'," style="width:70%"/><a style="margin-left:5px;" href="http://static-file.uce.cn/uce-static/iconfont/demo_fontclass.html" target="view_window">查看图标库<a/>
						</td>
					</tr>
				</tbody>
			</table>
        </form>
    </div>
    <div id="divPermissionBtn">
        <a href="javascript:void(0)" class="easyui-linkbutton save" onclick="savePermission(this)" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('dlgPermission')" >取消</a>
    </div>
</body>
</html>