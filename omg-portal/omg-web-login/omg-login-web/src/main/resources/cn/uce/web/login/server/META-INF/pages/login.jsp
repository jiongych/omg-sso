<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <%@ page pageEncoding="UTF-8"%>
    <%@include file="../common/common.jsp"%>
    <title>FSP</title>
	<script type="text/javascript">
	
		function doLogin() {
			$.ajax({
				type: "POST",
				url: "validateUser.do",
				data: {username :  $('#userName').val(),
						password :  $('#pwd').val()},
				success : function(data){
					var result = data;
					if (typeof data == 'string') {
						result = eval('('+ data +')');
					}
					if (result.success) {
						window.location.href = "loginIndex.do";
						return;
					}
					showErrorMsg(result.message);
				}
			});
		}
 	</script>
</head>

<body class="easyui-layout"> 
	<div data-options="region:'south',border:false" style="text-align:center;height:40px;">
		<p>Copyright(c)2017 优速物流有限公司版权所有</p>
	</div>
	
    <div data-options="region:'center',border:false" style="height:auto;margin-left:50px;">
     	<form method="post" id="login" action="#">
         	<div style="margin-bottom:10px">
         	用户名：<input class="easyui-textbox" id="userName" data-options="prompt:' 请输入用户名',required:true" />
         	</div>
        	<div style="margin-bottom:10px">
        	密&nbsp;&nbsp;&nbsp;码：<input type="password" class="easyui-textbox" id="pwd" data-options="prompt:' 请输入密码',required:true" />
        	</div> 
        	<div style="padding-left:80px; margin-top:10px;">
         		<input type="button"  class="easyui-linkbutton" id="login" onclick="doLogin()" value="登  陆" style="width:60px;" >
         		<input type="button"  class="easyui-linkbutton" id="reset" onclick="$('#login').form('clear')" value="重  置" style="width:60px;">
        	</div>
     	</form>
    </div>
	
</body>
</html>