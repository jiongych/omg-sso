<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>错误信息</title>
    <%@include file="common.jsp" %>
</head>
<body>

<script type="text/javascript">
	function showErrorDetail(){
		$("#fieErrorDetail").toggle();
	}
</script>
	<fieldset style="margin:10px 20px;">
	<legend>错误信息</legend>
		<div class="messager-icon messager-error" ></div>
		<div id="divErrorTitle" style="width: 60%;float: right;font-size:16px;"></div>
		<div style="width: 60%;float: right;">
		<a href="#" class="easyui-linkbutton" style="float: right;"  onclick="showErrorDetail()">展开详细信息</a>
		</div>
	</fieldset>  
	
	<fieldset id="fieErrorDetail" style="height: 60%;margin:10px 20px;display: none">
	<legend>详细信息</legend>
		<div id="" style="padding:10px;font-size:16px;">
		   错误堆栈信息
		</div>
		<div id="divErrorDetail" style="line-height: 19px">
		</div>
	</fieldset>
</body>
</html>