<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
	var crossDomainFlag = false;
	var portal_global_currentUser = {};
	var portal_global_permissionCode = [];
	
	try{
		portal_global_currentUser = parent.vm.user.userInfo;
		portal_global_permissionCode = parent.vm.user.permissionCode;
	}catch(e){
		crossDomainFlag = true;
		if(window.name) {
			var crossDomainParams = JSON.parse(window.name);
			portal_global_currentUser = crossDomainParams.currentUser;
			portal_global_permissionCode = crossDomainParams.permissionCode;
		}
	}
	
	window.addEventListener('message', function(e) {
		var msg = e.data;
		switch(msg.fn) {
			case 'settingThemes':
				var cssStyleDom = document.getElementById('themes-setting');
				var theme = msg.params.theme;
				if(cssStyleDom == null) {
					var css = document.createElement('style');
					css.type = 'text/css';
					css.id = "themes-setting";
					if(theme == 'default') {
						css.innerHTML = ".iconfont{color:#ff9372;}";
					} else if(theme == 'blue') {
						css.innerHTML = ".iconfont{color:#23b7e5;}";
					} else {
						css.innerHTML = ".iconfont{color:#464c5b;}";
					}
					frames[i].frameElement.contentDocument.head.appendChild(css);
				} else {
					if(theme == 'default') {
						cssStyleDom.innerHTML = ".iconfont{color:#ff9372;}";
					} else if(theme == 'blue') {
						cssStyleDom.innerHTML = ".iconfont{color:#23b7e5;}";
					} else {
						cssStyleDom.innerHTML = ".iconfont{color:#464c5b;}";
					}
				}				
				break;
			default:
				break;
		}

	})
</script>
<link rel="stylesheet" type="text/css" href="${applicationScope.STATIC_SERVERADDRESS}/easyui-themes/themes/insdep/easyui.css">
<link rel="stylesheet" type="text/css" href="${applicationScope.STATIC_SERVERADDRESS}/easyui-themes/themes/insdep/icon.css">
<script type="text/javascript" src="${applicationScope.STATIC_SERVERADDRESS}/jquery-easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="${applicationScope.STATIC_SERVERADDRESS}/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${applicationScope.STATIC_SERVERADDRESS}/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${applicationScope.STATIC_SERVERADDRESS}/easyui-themes/themes/insdep/jquery.insdep-extend.min.js"></script>
<script type="text/javascript" src="${applicationScope.STATIC_SERVERADDRESS}/easyui-themes/themes/insdep/expand/datagrid-filter/datagrid-filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/uce.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/uce.easyui.parser.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/uce.easyui.combobox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/uce.easyui.combogrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/datagrid-detailview.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/pako/pako.min.js"></script>
<!-- ztree -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/common/plugins/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/ztree/js/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/ztree/js/jquery.ztree.exhide.min.js"></script>
<!-- <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_qhlfeje0cp85xw29.css"> -->
<link rel="stylesheet" type="text/css" href="${applicationScope.STATIC_SERVERADDRESS}/iconfont/iconfont.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/common/common.css">
<div id="winErrorDetail" class="easyui-dialog" data-options="closed:true" style=" width:500px;height:200px"></div>
<style type="text/css">
	.layout-panel{
		overflow:initial;
	}
	/*ztree-panel*/
	.ztree-panel{
		display:none; 
		position:absolute;
		z-index:999;
		background-color:white;
		border:1px solid #ccc;
		overflow-y:auto;
		overflow-x:auto;
	}
	
	.datagrid-header-rownumber{
		margin: 0px
	}
</style>
