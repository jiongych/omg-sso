<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="mvc" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="../jquery-easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript"
	src="../jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../easyui-themes/themes/insdep/jquery.insdep-extend.min.js"></script>
<script type="text/javascript"
	src="../jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>
<title>中文国际化，欢迎来自全世界的朋友</title>
</head>
<body>
	Language:
	<a href="?lang=zh_CN"><spring:message code="language.cn" /></a> -
	<a href="?lang=en_US"><spring:message code="language.en" /></a>
	<h2>
		<spring:message code="welcome" />
	</h2>
	<h2>
	    <a href="color.do"><spring:message code="what.color" /></a> ${what_color }
	</h2>
	
	Locale: ${pageContext.response.locale }
</body>
<script type="text/javascript">
  </script>
</html>