<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>公司公告</title>
    <%@include file="../common/common.jsp" %>
    <script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/kindeditor/kindeditor-all.js"></script>
	<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/kindeditor/lang/zh-CN.js"></script>
     
    <script type="text/javascript">
    
	    //页面加载
		$(function() {
			KindEditor.ready(function(K) {
				window.editor = K.create('#content', {
					allowImageUpload: true, //上传图片框本地上传的功能，false为隐藏，默认为true
					allowImageRemote : true, //上传图片框网络图片的功能，false为隐藏，默认为true
					allowFileManager : true, //浏览图片空间
					filterMode : false, //HTML特殊代码过滤
					showRemote : false,
					urlType : 'domain',
	                uploadJson : 'uploadFile.do',
	                fileManagerJson : '../demo/file_manager_json.do',
					items : [
						'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'cut', 'copy', 'paste',
						'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
						'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
						'superscript', 'clearhtml', 'quickformat', 'selectall', '|',
						'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
						'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
						'table', 'hr', 'emoticons',  'pagebreak', 'link', 'unlink', 'image','insertfile'
                    ]	//, 'image' 
	            });
			});
			//加载角色dataGrid
			var columns = [[{field : 'id',title : '',hidden:true,},
							{field:'deal',title: '操作',align:'center',width: 80,formatter:function(value,rec,index){
								if(value){
					        		return value;
					        	}
								return '<a class="icon-line iconfont uce-edit" title="编辑" onclick="openUpdateCompanyNotice(\''+index+'\')" href="javascript:void(0)"></a>'+
					        		   '<a class="icon-line iconfont uce-delete" title="删除" onclick="deleteCompanyNotice(\''+index+'\')" href="javascript:void(0)"></a>';
					        }},
					        {field : 'title',title : '公告标题',width : 200,formatter: formatTip},
					        {field : 'author',title : '发布部门',width : 100,formatter: formatTip},
					        {field : 'content',title : '公告内容',width : 250,formatter: function(data){
					        	return data.replace(/<[^>]+>/g,'').replace(/&nbsp;/ig,'').replace(/\s+/g, '');
							}}, 
							{field : 'setTop',title : '是否置顶',width : 80,align:'center',formatter: function(value) {
								return getTypeNameByCode("ENABLE", value, formatTip);
							}}, 
							{field : 'setAlert',title : '是否弹出',width : 80,align:'center',formatter: function(value) {
								return getTypeNameByCode("ENABLE", value, formatTip);
							}}, 
							{field : 'sort',title : '序号',width : 60,align:'center',formatter: formatTip},
					        {field : 'createEmp',title : '创建人',width : 80,formatter: formatTip},
					        {field : 'createTime',title : '创建时间',width : 120,formatter: function(value){
					        	return formatData(value);
					        }},
					        {field : 'updateEmp',title : '更新人',width : 80,formatter: formatTip},
					        {field : 'updateTime',title : '更新时间',width : 120,formatter: function(value){
					        	return formatData(value);
					        }},
					        {field : 'viewCount',title : '阅读数',width : 80,formatter: formatTip},
					        {field : 'remark',title : '备注',width : 100,formatter: formatTip}
						]];
			var dataGridParams = {
				url : 'findCompanyNoticeByPage.do',
				pageSize : 10,
				toolbar : '#tlbCompanyNotice',
				singleSelect : 'true',
				fitColumns : 'true'
			}
			newloadGrid('dgCompanyNotice', columns, dataGridParams);
			//加载“是否置顶”下拉列表
			uceDictCombobox("setTop","ENABLE",{headerValue:false});
			//加载“是否置顶”下拉列表
			uceDictCombobox("setAlert","ENABLE",{headerValue:false});
		});
		//初始化数据字典
		initDictDatas("ENABLE");
		//新增公告
		function openAddCompanyNotice() {
			$('#formCompanyNotice').form('clear');
			$('#formCompanyNotice').form('enableValidation');
			openDialog("dlgCompanyNotice", '新增公告');
			$("#setTop").combobox('setValue',0);
			$("#setAlert").combobox('setValue',0);
			$("#sort").textbox('setValue',1);
			editor.html("");
			noticeId=null;
			url = 'insertCompanyNotice.do';
		}
		
		//编辑公司公告
		function openUpdateCompanyNotice(index) {
			var row=$('#dgCompanyNotice').datagrid('getRows')[index];
			noticeId=row.id;
			$('#formCompanyNotice').form('clear');
			
			openDialog("dlgCompanyNotice", '编辑公告');
			$("#setTop").combobox('setValue',initEnable(row.setTop));
			$("#setAlert").combobox('setValue',initEnable(row.setAlert));
			$("#title").textbox('setValue',row.title);
			$("#remark").textbox('setValue',row.remark);
			$("#author").textbox('setValue',row.author);
			$("#sort").textbox('setValue',row.sort);
			$('#formCompanyNotice').form('enableValidation');
			editor.html(row.content);
			url = 'updateCompanyNotice.do';
		}
		
		function initEnable(value) {
			if (value == true) {
				return 1;
			} else {
				return 0;
			}
		}
		//保存公司公告
		function saveCompanyNotice(btn) {
			if ($("#formCompanyNotice").form('validate')) {
				editor.sync();
				var noticeContent = $("#content").val();
				if (noticeContent.replace(/<[^>]+>/g,"").replace(/&nbsp;/ig,"").replace(/\s+/g, "").length == 0) {
					showInfoMsg("公告内容不能为空");
					return false;
				}
				var infoValue = {
					content:$("#content").val(),
					title:$("#title").val(),
					remark:$("#remark").val(),
					author:$("#author").val(),
					setTop:$("#setTop").val(),
					setAlert:$("#setAlert").val(),
					sort:$("#sort").val(),
					id:noticeId
				};
				
				$.ajax({
					type : "POST",
					url : url,
					data: infoValue,
					success : function(data, textStatus) {
						closeDialog("dlgCompanyNotice");
						$('#dgCompanyNotice').datagrid('reload'); // reload the CompanyNotice data
					}
				});
			} else {
				
				return false;
			}

			
		}
		
		//查询
		function findCompanyNoticeData() {
			if ($("#formFindCompanyNotice").form('validate')) {
				$('#dgCompanyNotice').datagrid('load', serializeFormObj("formFindCompanyNotice"));
			}
		}
		//重置
		function resetQuery() {
			$("#formFindCompanyNotice").form('reset');
		}
		//删除
		function deleteCompanyNotice(index) {
			var row=$('#dgCompanyNotice').datagrid('getRows')[index];
			confirmMsg('您确定要删除选中的公告吗？', function(row) {
				$.ajax({
					url:'deleteCompanyNotice.do',
					data :{'id': row.id},
					task : function(data, statusText, xhr){
						reloadDatagrid('dgCompanyNotice');
					}
				});
			}, [row]);
			
		}
	</script>
</head>

<body class="easyui-layout">
	<!--公告表格 -->
	<div id="tlbCompanyNotice">
		<form action="#" id="formFindCompanyNotice">
			<div id="aa" class="easyui-accordion">   
			    <div title="" data-options="selected:true">
					<ul class="search-form" >
				    	<li><input class="easyui-textbox" label="公告标题：" labelPosition="left" name="title" data-options="prompt:'请输入公告标题'"></li>
						<li><input class="easyui-textbox" label="公告内容：" labelPosition="left" name="content" data-options="prompt:'请输入公告内容'"></li>
						<li><input class="easyui-datetimebox" label="开始时间：" data-options="editable:false" name='createTime' id='createTime' data-options="prompt:'请输入开始时间'"></li>
						<li><input class="easyui-datetimebox" label="结束时间：" data-options="editable:false" name='updateTime' id='updateTime' data-options="prompt:'请输入结束时间'"></li>
						<div>
							<a href="#" class="easyui-linkbutton search" onclick="findCompanyNoticeData()">查询</a>
							<a href="#" class="easyui-linkbutton reset" onclick="resetQuery()">重置</a>
		    			</div>
					</ul>
				</div>
			</div>
			<div class="toolbar-margin">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="iconfont uce-add" onclick="openAddCompanyNotice()" plain="true">新增</a>
			</div>

		</form>
	</div>
	<table id="dgCompanyNotice" class="easyui-datagrid" data-options="fit:true"></table>
	
	<!--新增修改dialog -->
	<div id="dlgCompanyNotice" class="easyui-dialog"
		style="width: 800px; height: 490px; padding: 5px" closed="true" buttons="#divCompanyNoticeBtn">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false"
				style="padding: 15px 20px; background: #fff;">
				<form id="formCompanyNotice" method="post">
					<input type="hidden" name="id" />
					<table class="table" style="width: 750px; border: 0px;">
						<tbody>
							<tr style="padding-left: 20px">
								<td width="80px">公告标题:</td>
								<td>
									<input name="title" id="title" class="easyui-textbox" style="width:280px;height: 40px" required="true"validtype="length[1,30]">
								</td>
								<td width="80px">发布部门:</td>
								<td>
									<input name="author" id="author" class="easyui-textbox" style="width:280px;height: 40px" validtype="length[1,20]">
								</td>
							</tr>
							<tr style="padding-left: 20px">
								<td width="80px">是否置顶:</td>
								<td>
									<input name="setTop" id="setTop" class="easyui-combobox" style="width:280px;height: 40px" required="true">
									
								</td>
								<td width="80px">是否弹出:</td>
								<td>
									<input name="setAlert" id="setAlert" class="easyui-combobox" style="width:280px;height: 40px" required="true">
									
								</td>
							</tr>
							<tr style="padding-left: 20px">
								<td width="80px">序号:</td>
								<td>
									<input name="sort" id="sort" class="easyui-numberbox" style="width:280px;height: 40px" validtype="length[1,9]">
								</td>
								<td width="80px">备注:</td>
								<td colspan=>
									<input name="remark" id="remark" class="easyui-textbox" validtype="length[1,125]" multiline="true" style="height: 40px; width:280px">
								</td>
							</tr>
							<tr style="padding-left: 20px">
								<td width="80px">公告内容:</td>
								<td colspan="3">
									<input name="content" id="content" style="width:500px;height: 208px" required="true">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div id="divCompanyNoticeBtn">
		<a href="javascript:void(0)" class="easyui-linkbutton  save" onclick="saveCompanyNotice(this)" >保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton call" onclick="javascript:closeDialog('dlgCompanyNotice')">取消</a>
	</div>
		
</body>
</html>