<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>文件列表</title>
    <%@include file="../common/common.jsp" %>
	<style> 
		.noticeAuthor{float:right;text-align:right;font-weight:bold}
	</style> 
    <script type="text/javascript">
	var urlHost = window.location.href;
	var index=urlHost.lastIndexOf("omg-portal-main");
	var urlP = urlHost.substring(0,index);
    function openFile() {
		
    	var pdfUrl =  $("#fileUrl").textbox('getValue');
    	var urlFlag =  $("#urlFlag").textbox('getValue');
		//var pdfUrl = "https://uct.sit.uc56.com/update/uct/sit/core/20190708/AE289D2ED5634FDA94245BACD4E08B03.pdf";
		if (null != pdfUrl && pdfUrl.length > 40) {
			var watermark = encodeURI(portal_global_currentUser.empCode + " " + portal_global_currentUser.empName);
			var url = urlP + "${pageContext.request.contextPath}/scripts/portal/pdf/web/viewer.html?fileUrl="+pdfUrl+"&watermark=" + watermark + "&urlFlag=" + urlFlag;
			openTabCrossDomain(null,{},{title:"文件预览",systemCode:"PORTAL",url:url});
		} else {
			alert("参数有误");
		}
		
	}
	</script>
</head>

<body class="easyui-layout">
	<input class="easyui-textbox" label="文件地址：" style="width: 1000px" labelPosition="left" id = "fileUrl" name="fileUrl" data-options="prompt:'请输入文件地址'"><br/>
	<input class="easyui-textbox" label="是否启用配置中的地址：" style="width: 500px" labelPosition="left" id = "urlFlag" name="urlFlag" data-options="prompt:'是否启用配置中的地址'"><br/>
	<a style="width: 50px" onclick="openFile()">查看文件</a>
</body>
</html>