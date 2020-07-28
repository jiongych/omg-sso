<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
	var crossDomainFlag = false;
</script>
<link rel="stylesheet" type="text/css" href="${applicationScope.STATIC_SERVERADDRESS}/easyui-themes/themes/insdep/easyui.css">
<link rel="stylesheet" type="text/css" href="${applicationScope.STATIC_SERVERADDRESS}/easyui-themes/themes/insdep/icon.css">
<script type="text/javascript" src="${applicationScope.STATIC_SERVERADDRESS}/jquery-easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="${applicationScope.STATIC_SERVERADDRESS}/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${applicationScope.STATIC_SERVERADDRESS}/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/uce.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/pako/pako.min.js"></script>
<!-- <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_qhlfeje0cp85xw29.css"> -->
<link rel="stylesheet" type="text/css" href="${applicationScope.STATIC_SERVERADDRESS}/iconfont/iconfont.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/common/common.css">
<div id="winErrorDetail" class="easyui-dialog" data-options="closed:true" style=" width:500px;height:200px"></div>
