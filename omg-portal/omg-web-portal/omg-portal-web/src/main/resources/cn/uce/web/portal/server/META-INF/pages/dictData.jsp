<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>系统类型</title>
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
    
    /**
	 * 控制权限权操作区是否显示
	 * @param psColumn 操作列显示总控
	 * @param... psButtonXXX 单个按钮是否有权限码
	 * @PS:如果放至列内会多次调用权限码校验方法
	 */

    var typeClassCode = "";
    //页面加载
	$(function() {
		//加载树
    	$('#treeDictData').tree({
    	    url:'${pageContext.request.contextPath}/dictData/findDictDataTree.do',
    	    onSelect : function(node) { 
				typeClassCode = node.id;
	        	$(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);	        	
	        		$("#dgDictData").datagrid("load", {
	        			'typeClassCode':node.id
	        	})        	
	        },
			onContextMenu: function(e, node){
				e.preventDefault();
				$("#update").attr('dictTypeObj', JSON.stringify(node));
				$("#delete").attr('dictTypeObj', JSON.stringify(node));
				// 显示快捷菜单
				$('#mmm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			}
    	});
		
    	$("#txtDictDataName").textbox({
	    	onChange: function(value) {
	    		$('#treeDictData').tree('doFilter', value);
	    	}
		 });
    	
	 	//加载dataGrid
    	var columns=[[  
			{field:'id',title: 'id',hidden:true,align:'center'},
	        {field:'deal',title: '操作',align:'center',width: 50,formatter:function(value,rec,index)
	        {
        	if(value){
        		return value;
        	}
	        return '<span class="icon-line iconfont uce-edit" title="编辑" onclick="openUpdateDictData(\''+index+'\')" href="javascript:void(0)"></span>'+
	        		'<span class="icon-line iconfont uce-delete" title="删除" onclick="deleteDictData(\''+index+'\')" href="javascript:void(0)"></a>';
	        },width: 100},
			{field:'typeClassName',title: '所属分类' ,width: 130,formatter: formatTip},  
			{field:'typeCode',title: '类型编号',width: 100,formatter: formatTip},
			{field:'typeName',title: '类型名称',width: 150,formatter: formatTip},
			{field:'sourceType',title: '数据来源',width: 100,formatter: function(value){
				return getTypeNameByCode("SOURCE_TYPE",value);
			}},  
			{field:'createEmp',title: '创建人', width: 50,formatter: formatTip} ,
			{field:'createTime',title: '创建时间', width: 130,align:'center', formatter : function(value){
                return formatData(value)
            }},
			{field:'updateEmp',title: '更新人',width: 50,formatter: formatTip} ,
			{field:'updateTime',title: '更新时间',width: 130,align:'center',sortable:true,formatter : function(value){
                return formatData(value)
            }} ,
			{field:'remark',title: '备注',width: 140,formatter: formatTip},
		    ]];

			var dataGridParams = {
				url : 'findDictDataByPage.do',
				pageSize : 10,
				toolbar : '#tlbDictData',
				singleSelect : 'true',
				fitColumns : 'true',
				onBeforeLoad : function(param) {
				},
				onLoadSuccess : function(data) {
				},
				onLoadError : function() {
					//在载入远程数据产生错误的时候触发。
				}
			}
		newloadGrid('dgDictData', columns, dataGridParams);

		//var params={editable:true,value:'1'}; 如需加参数demo
    	uceDictCombobox("cmbSourceType","SOURCE_TYPE");

		// 所属分类
    	$("#searchTypeClassCode").combobox({
    		url:'../dictData/findAllDictType.do',
   			valueField:'typeClassCode',    
   			textField:'typeClassName',
   			editable:true,
   			limitToList:true,
   			panelHeight:200,
   			loadFilter:function(data){
       			var row = {typeClassCode:'',typeClassName:'-- 请选择 --'};
	            data.splice(0,0,row);
	            return data;
	     	}
    	});
    	
		// 弹出框的所属分类
    	$("#txtTypeClassCode").combobox({
    		url:'../dictData/findTypeClassCode.do',
   			valueField:'typeClassCode',    
   			textField:'typeClassName',
   			editable:true,
   			limitToList:true,
   			panelHeight:200
    	})
    	
    	// 弹出框的所属系统
    	$("#systemCode").combobox({
    		url:'../dictData/findPermissionByEmpCode.do',
   			valueField:'sysName',    
   			textField:'sysName',
   			editable:true,
   			limitToList:true,
   			panelHeight:200
    	})
	});
	
	initDictDatas("SOURCE_TYPE");
	/**
  	 * 右键新增类型分类弹出框
  	 */
	function openAddDictType() {
		$("#formDictType").form('clear');
		$("#inpCkVisible").prop("checked",true);
		$('#formDictType').form('enableValidation');
		url ="${pageContext.request.contextPath}/dictData/addDictType.do";
		openDialog('dlgDictType', '新增分类');
  		$('#inpTypeClassCode').textbox('readonly', false);
  		$('#systemCode').textbox('readonly', false);
  		/* $('#inpTypeClassCode').textbox({
  			validType: "remote['${pageContext.request.contextPath}/dictData/findByTypeClassCode.do','typeClassCode']"
  		}) */
	}
	
	//右键弹出框的保存
    function saveDictType(btn) {
    	$(btn).linkbutton('disable');
	    $("#inpVisibleHide").val($("#inpCkVisible").is(":checked"));
	    $("#formDictType").form('submit',{
	        url : url,
	        task: function(result){
		        closeDialog('dlgDictType');
		        $('#treeDictData').tree('reload');
		        $('#txtTypeClassCode').combobox('reload');
		        $('#searchTypeClassCode').combobox('reload');
	        },
	        finalTask:function(){
	        	$(btn).linkbutton('enable');
	        }
	    });
    }
  
  	/**
	* 右键修改
	*/
	function openUpdateDictType(dom) {
		var dictTypeObj=$(dom).attr("dictTypeObj");	
		var sourceType = JSON.parse(dictTypeObj).sourceType;
		if(parseInt(sourceType) != 2){
			showInfoMsg('OMG同步数据不能修改..');
			return;
		}
		var id = JSON.parse(dictTypeObj).id;
		var typeClassName=JSON.parse(dictTypeObj).typeClassName;
		var visible=JSON.parse(dictTypeObj).visible;
		var remark = JSON.parse(dictTypeObj).remark;
		var systemCode = JSON.parse(dictTypeObj).systemCode;
		url = '${pageContext.request.contextPath}/dictData/updateDictType.do?id='+id;
		openDialog("dlgDictType", '编辑分类');
		$('#formDictType').form('disableValidation');
		//分类编号
		$('#inpTypeClassCode').textbox('setValue',id);
		//分类名称
		$('#inpTypeClassNAme').textbox('setValue',typeClassName);
		//是否显示
		$('#inpVisibleHide').val(visible);
		//备注
		$('#inpRemark').textbox('setValue',remark);
		$('#inpTypeClassCode').textbox('readonly', true);
		//所属系统
		$('#systemCode').textbox('setValue',systemCode);
  		$('#systemCode').textbox('readonly', true);
		if(visible){
		   $("#inpCkVisible").prop("checked",true);
		}
	}

	//右键删除
    function deleteDictType(dom) {
	   	var sourceType = JSON.parse($(dom).attr("dictTypeObj")).sourceType;
	   	if(parseInt(sourceType) != 2){
			showInfoMsg('OMG同步数据不能删除..');
			return;
		}
	   	var id = JSON.parse($(dom).attr("dictTypeObj")).id;
		$.ajax({
		   url:'${pageContext.request.contextPath}/dictData/deleteDictType.do?',
		   data :{'typeClassCode': id},
		   task : function(data, statusText, xhr){
		     	$('#treeDictData').tree('reload');
		     	$('#txtTypeClassCode').combobox('reload');
	         	$('#searchTypeClassCode').combobox('reload');
		   }
		});
	}
	
	//查询系统类型
	function findDictDataData() {
		$("#dgDictData").datagrid("load", serializeFormObj("formFindDictData"));
	}
	var dataTypeV = "";
	
	/**
  	 * 打开新增系统类型弹出框
  	 */
	function openAddDictData() {
		dataTypeV = "";
		$("#formDictData").form('clear');
		$('#formDictData').form('enableValidation');
		url ="${pageContext.request.contextPath}/dictData/addDictData.do";
		openDialog('dlgDictData', '新增分类');
  		$('#txtTypeClassCode').textbox('readonly', false);
		$("#txtTypeClassCode").combobox('setValue',typeClassCode);
	}
	
	/**
	* 单行修改系统类型弹出框
	*/
	function openUpdateDictData(index) {
		var row = $('#dgDictData').datagrid('getRows')[index];
		if(row.sourceType != '2'){
			showInfoMsg('OMG同步数据不能修改..');
			return;
		}
		dataTypeV = row.typeCode;
		url = '${pageContext.request.contextPath}/dictData/updateDictData.do?id='+row.id;
		$('#formDictData').form('load',row);
		openDialog("dlgDictData", '编辑分类');
		$('#formDictData').form('disableValidation');
		$('#txtTypeClassCode').textbox('readonly', true);
	}
	
	/**
  	 * 保存系统类型
  	 */
	function saveDictData(btn) {
		$(btn).linkbutton('disable');
		$.ajax({
		   url:'${pageContext.request.contextPath}/dictData/findExitTypeCode.do?',
		   data :{'typeClassCode': $('#txtTypeClassCode').combobox('getValue'),typeCode:$("#txtTypeCode").textbox('getValue')},
		   success : function(data, textStatus) {
			   if (!data && dataTypeV != $("#txtTypeCode").textbox('getValue')) {
				   $.messager.alert('类型编号重复','类型编号己存在','info');
				   $(btn).linkbutton('enable');
			   } else {
				   	$("#formDictData").form('submit',{
						url : url,
						task: function(result){
							closeDialog('dlgDictData');
							reloadDatagrid('dgDictData');
						},
						finalTask:function(){
							$(btn).linkbutton('enable');
						}
					});
			   }
		   }
		});

	}
	
	//重置
	function resetQuery(){
		$("#formFindDictData").form('reset');
	}
	
	/**
	* 单行删除系统类型
	*/
	function deleteDictData(index) {
		var row = $('#dgDictData').datagrid('getRows')[index];
	    if (row.sourceType != '2') {
			showInfoMsg('OMG同步的数据不能删除..');
			return;
		}
	   	confirmMsg("您确定要删除吗？,删除后可能会影响到已使用该类型的数据！", function() {
			$.ajax({
			   url:'${pageContext.request.contextPath}/dictData/deleteDictData.do?',
			   data :{'id': row.id,'typeClassCode':row.typeClassCode,'typeCode':row.typeCode},
			   task : function(data, statusText, xhr){
			   		reloadDatagrid('dgDictData');
			   }
			});
		}, []);
	}
	</script>
</head>

<body class="easyui-layout">
	<div data-options="region:'west',border:false" style="width:200px;">
        <div class="easyui-layout" data-options="fit:true,border:false">
            <div data-options="region:'north',border:false" >
                <div style="margin-bottom:0px" data-options="region:'north',border:false" >
                    <input class="easyui-searchbox" id="txtDictDataName" style="width:100%;" ">
                </div>
            </div>
            <div data-options="region:'center'">
                <ul id="treeDictData"></ul>
            </div>
            <div id="mmm" class="easyui-menu" style="width:80px;height: 140px;">
						<div class = "dictType-menu" style="margin-left: -10px;">
							<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="iconfont uce-add" onclick="openAddDictType()" plain="true">新增</a>
					 	</div>
						<div class = "dictType-menu" style="margin-left: -10px;">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-edit" id="update"  onclick="openUpdateDictType(this)" plain="true">修改</a>
						</div>
						<div class = "dictType-menu" style="margin-left: -10px;">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-delete" id="delete" onclick="deleteDictType(this)" plain="true">删除</a>
						</div>
				</div>
        </div>
   </div>
    <div data-options="region:'center'" >
    	<!--列表-->
	<div id="tlbDictData" style="padding:5px;height:auto">  
		<form action="#" id="formFindDictData">
			<div id="aa" class="easyui-accordion">   
			    <div title="" data-options="selected:true">
					<ul class="search-form" >
						<li><input class="easyui-combobox" label="所属分类：" style="width:120px;" id="searchTypeClassCode" name="typeClassCode"></li>
						<li><input class="easyui-textbox" label="类型编号：" name="typeCode" data-options="prompt:'请输入类型编号'">  </li>	  
						<li><input class="easyui-textbox" label="类型名称：" name="typeName" data-options="prompt:'请输入类型名称'"></li>		
						<li><input class="easyui-combobox" label="数据来源：" name="sourceType"   id="cmbSourceType"></li>		
						<div>
							<a href="#" class="easyui-linkbutton search" onclick="findDictDataData()">查询</a>
							<a href="#" class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
						</div>							
					</ul>
			    </div>
			</div>
		        <div style="margin-top:5px;" class="toolbar-margin">
		          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-add" onclick="openAddDictData()" plain="true">新增</a>
		        </div>
	    </form>
	</div>
	
    <table id="dgDictData" class="easyui-datagrid" data-options="fit:true"></table>
    </div>
     <!--编辑--> 
    <div id="dlgDictData" class="easyui-dialog" style="width:400px;height:360px; padding:20px 20px" closed="true" buttons="#divDictDataBtn">
        <form id="formDictData" method="post" >
        	<input type="hidden" name="typeClassId" />
        	<input type="hidden" name="typeId" />
        	<table class="table" style="width: 100%; border:0px;">
				<tbody>
					<tr>
						<td width="30%">所属分类:</td>
						<td width="70%">
							<select name="typeClassCode" id="txtTypeClassCode"  class="easyui-combobox"  required="true" style="width:100%;"></select>
						</td>
					</tr>
					<tr>
						<td>类型编号:</td>
						<td>
							<input name="typeCode" id="txtTypeCode" class="easyui-textbox" style="width:100%;" data-options="required:true,validType:{typeCodeExit:['#txtTypeClassCode'],length:[1,60]}">
						</td>
					</tr>
					<tr>
						<td>类型名称:</td>
						<td>
							<input name="typeName" class="easyui-textbox" required="true" style="width:100%;"data-options="validType:'length[1,60]'">
						</td>
					</tr>
					<tr>
						<td>备注:</td>
						<td>
							<input class="easyui-textbox" id="remark" name="remark"  
							validType="length[1,100]"  invalidMessage="有效长度1-100"  
							data-options="prompt:'有效长度1-100',multiline:true" 
							style="height:70px; width: 100%">
						</td>
					</tr>
				</tbody>
			</table>
        </form>
    </div>
    <div id="divDictDataBtn">
        <a href="javascript:void(0)" class="easyui-linkbutton save" onclick="saveDictData(this)">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('dlgDictData')" >取消</a>
    </div>
 <!--右键弹框-->
	<div id="dlgDictType" class="easyui-dialog" style="width:400px;height:380px; padding:20px 20px" closed="true" buttons="#divDictTypeBtn">
        <form id="formDictType" method="post" >
        	<input type="hidden" name="typeClassId" />
        	<table class="table" style="width: 100%; border:0px;">
				<tbody>
					<tr>
						<td width="30%">所属系统:</td>
						<td width="70%">
							<select name="systemCode" id="systemCode"  class="easyui-combobox"  required="true" style="width:100%;"></select>
						</td>
					</tr>
					<tr>
						<td width="30%">分类编号:</td>
						<td width="70%">
							<input name="typeClassCode" id="inpTypeClassCode"  class="easyui-textbox" style="width:100%" data-options="required:true,validType:{remote:['${pageContext.request.contextPath}/dictData/findByTypeClassCode.do','typeClassCode'],length:[1,60]}">
						</td>
					</tr>
					<tr>
						<td>分类名称:</td>
						<td>
							<input name="typeClassName" id="inpTypeClassNAme" class="easyui-textbox" style="width:100%" required="true" data-options="validType:'length[1,60]'">
						</td>
					</tr>
					<tr>
						<td>显示界面:</td>
						<td>
							<input type="hidden" name="visible" id="inpVisibleHide"/>
							<input id="inpCkVisible"  type="checkbox"/>
						</td>
					</tr>
					<tr>
						<td>备注:</td>
						<td>
							<input class="easyui-textbox" id="inpRemark" name="remark"  
							invalidMessage="有效长度1-100"  
							data-options="prompt:'有效长度1-100',multiline:true,validType:'length[1,100]'" 
							style="height:70px; width: 100%">
						</td>
					</tr>
				</tbody>
			</table>
        </form>
    </div>
    <div id="divDictTypeBtn">
        <a href="javascript:void(0)" class="easyui-linkbutton save" onclick="saveDictType(this)" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('dlgDictType')" >取消</a>
    </div>
</body>
</html>