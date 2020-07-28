<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>biz</title>
		<%@include file="../../common/common-ztree.jsp" %>
	</head>

	<body>
		<script>
			$(function() {
				$("#xx").searchbox({
					searcher: function(value, name) {
						alert(value + "," + name)
					}
				})
				//初始化orgTree树
				orgComboZTree("orgTree", {
					orgFlag: 'NOS',
					orgCode: 'BUC',
					iself: true
				});
				//org datagrid NOS营运机构，CMS乾坤机构，ADM行政机构
				var params = {
					'orgFlag': 'CMS',
					'orgType': '',
					'orgStatus': '',
					'isSelf': true,
					'pagination': true,
					'pageList': [10],
					'pageSize': 10,
					'multiple': true
				};
				treeDatagrid('org', '../org/tree_show.do', params);
			})
		</script>
		<div class="easyui-panel" title="业务控件" style="width:100%;padding:30px 60px;" data-options="fit:true">
			<input class="easyui-searchbox" id='xx'></input>
			<!-- 机构树 -->
			<div id="orgTreeBox">
				<input id="orgTreeText" style="width:300px;height:40px">
				<div id="orgTreePanel" class="ztree-panel" style="height:200px;width:298px">
					<ul id="orgTree" class="ztree"></ul>
				</div>
			</div>
			<input id="org_textGrid" class="easyui-textbox" style="width:300px;" editable="false">
			<a href="#" class="easyui-linkbutton" id="org_select_button">选择</a>
			<div id="org_dialog">
				<div class="easyui-layout" style="width:600px;height:350px;" id="org-panel">
					<div data-options="region:'south',split:false" style="height:50px;text-align:center;padding-top:12px;">
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" id="org_checked">确定</a>
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" id="org_cannel">取消</a>
					</div>
					<div data-options="region:'east',split:true,collapsible:false" style="width:300px;overflow-y:visible;overflow-x:visible" id="right_selected">
						<table id="org_selected">
						</table>
					</div>
					<div data-options="region:'center',iconCls:'icon-ok'" style="padding:1px;overflow-y:visible;overflow-x:visible">
						<table id="org_datagrid">
						</table>
					</div>
					<div id="tool">
						<div class="org_search">
							<input id="org_name" class="easyui-searchbox" style="width:100%;height:22px;" data-options="iconAlign:'right',prompt:'请输入...'" >
						</div>
						<div class="org_navigation" style="height:25px;font-size: 13px;padding-top: 5px;">
							<a href="#" id='_10'>&nbsp;&nbsp;总部></a>
						</div>
					</div>
					<!--20171211 add by xj-->
					<div id="org_tool_checked">
						<div class="org_search">
							<input id="org_name_checked" class="easyui-searchbox" style="width:100%;height:22px;" data-options="iconAlign:'right',prompt:'请输入...'" >
						</div>
					</div>
					<!--20171211 add by xj-->
				</div>
			</div>
		</div>

	</body>

</html>