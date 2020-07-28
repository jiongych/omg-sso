/**
 * id:table id,如：<table id="test" class="easyui-datagrid">
 * columns:显示的列,如:[[{field:'name',title: '属性名称',align: 'center'},{field:'age',title: '年龄',width: 50}]]
 * url:后台请求url
 * pageSize,默认为10,可选参数
 * toolbar,工具条
 */
function loadGrid(id,columns,url,pageSize,toolbar, singleSelect,callback){ 
	if(id ==null || columns==null || url==null){
		console.error("datagrid id、colums、url不能为空！");
		return;
	};
	var pagination = false;
	var pageList = null;
	if (pageSize!=null && pageSize != undefined) {
		if(!isNaN(pageSize)){
			pageList=[pageSize,pageSize*2,pageSize*3,pageSize*4,pageSize*5]
			pagination = true;
		}else{
			console.error("pageSize必须要为数字！");
		    return;
		}
	} 
	if (singleSelect == null || singleSelect == undefined) {
		singleSelect = true;
	}
	$('#'+id).datagrid({  
	    striped: true ,  
	    singleSelect : singleSelect,  
	    fitColumns:true,
	    url : url,  
	    loadMsg:'数据加载中请稍后……',  
	    pagination: pagination,  
	    rownumbers: true,    
	    pageNumber : 1,
	    pageSize: pageSize,
	    pageList: pageList,
	    columns:columns,
	    onLoadSuccess:callback,
	    toolbar:toolbar,
	    pagePosition:'bottom'
	}); 
}

/**
 * id:table id,如：<table id="test" class="easyui-datagrid">
 * columns:显示的列配置项,如:[[{field:'name',title: '属性名称',align: 'center'},{field:'age',title: '年龄',width: 50}]]
 * dataGridParams:datagrid相关的参数配置项，详见easyuiAPI-datagrid;
 */
function newloadGrid(id,columns,dataGridParams){
	/*var dg_storageKey = window.location.pathname + "_datagrid_"+id+(dataGridParams && dataGridParams.storageKey ? "_"+dataGridParams.storageKey : "")+"_"+parent.currentUser.empCode+"_"+parent.currentUser.cmsBaseOrgCode
	$('#'+id).data('storageKey',dg_storageKey);*/
	if(id ==null || columns==null || dataGridParams.url==null){
		console.error("datagrid id、colums、url不能为空！");
		return;
	};
	if (dataGridParams.pageSize!=null && dataGridParams.pageSize != undefined) {
		if(!isNaN(dataGridParams.pageSize)){
			dataGridParams.pageList=[dataGridParams.pageSize,dataGridParams.pageSize*2,dataGridParams.pageSize*3,dataGridParams.pageSize*4,dataGridParams.pageSize*5,dataGridParams.pageSize*10]
			dataGridParams.pagination = 'true';
		}else{
			console.error("pageSize必须要为数字！");
		    return;
		}
	} 
	
	$('#'+id).datagrid({
		remoteSort : dataGridParams.remoteSort ? dataGridParams.remoteSort : false,
		title: dataGridParams.title ? dataGridParams.title : '',
/*		width: dataGridParams.width ? dataGridParams.width : 'auto',
		height: dataGridParams.height ? dataGridParams.height : 'auto',*/
		url: dataGridParams.url,
		method: dataGridParams.method =='get' ? 'get' : 'post',
		queryParams: dataGridParams.queryParams ? dataGridParams.queryParams : {},//请求参数对象{a:'1'}
		columns:columns, //列配置项
	    striped: true , //是否显示斑马线效果。
	    collapsible:false,//表格是否可折叠
	    singleSelect : dataGridParams.singleSelect == 'true' ? true : false,  //允许单行选择true/false
	    fitColumns: dataGridParams.fitColumns == 'true' ? true : false, //真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。
	    loadMsg:'数据加载中请稍后……',  
	    pagination: dataGridParams.pagination =='true' ? true : false,  //如果为true，则在DataGrid控件底部显示分页工具栏。
    	pagePosition:'bottom',//定义分页工具栏的位置。可用的值有：'top','bottom','both'。
	    rownumbers: dataGridParams.rownumbers=='false' ? false : true, //如果为true，则显示一个行号列。
	    pageNumber : 1, //在设置分页属性的时候初始化页码。
	    pageSize: dataGridParams.pageSize ? dataGridParams.pageSize : 10,//在设置分页属性的时候初始化页面大小。
	    pageList: dataGridParams.pageList ? dataGridParams.pageList : [10,20,30,40,50,100],//在设置分页属性的时候 初始化页面大小选择列表。
	    nowrap: true, //如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
	    resizable: true, //如果为true，允许列改变大小。
	    rownumberWidth : !isNaN(dataGridParams.rownumberWidth) ? dataGridParams.rownumberWidth : 50,
	    toolbar: dataGridParams.toolbar ? dataGridParams.toolbar : [], //顶部工具栏的DataGrid面板
	    onBeforeLoad: dataGridParams.onBeforeLoad ? dataGridParams.onBeforeLoad : function(param){
	    	//在载入请求数据数据之前触发，如果返回false可终止载入数据操作。
	    	console.log('onBeforeLoad');
	    },
	    onLoadSuccess: dataGridParams.onLoadSuccess ? dataGridParams.onLoadSuccess : function(data){
	    	//在数据加载成功的时候触发
	    	console.log('onLoadSuccess');
	    },
	    onSelect:dataGridParams.onSelect ? dataGridParams.onSelect :function(){
	    	console.log('onSelect');
	    }
	});
	
	/*if(dataGridParams && dataGridParams.rownumbers != false && dataGridParams.customSet != false){
		if($('#dg-set-menu-'+id).length == 0){
			$('#'+id).after("<div id = 'dg-set-menu-"+id+"'><div  data-options='id:\"export-set\",iconCls:\"icon-set\"'  onclick='exportSetClick(\""+id+"\",\""+dg_storageKey+"\","+JSON.stringify(columns)+")'>导出设置</div></div>");
		}
		
		$('#'+id).parent().children(":first").find('.datagrid-header-rownumber').append("<div id= 'dg-set-"+id+"'> <a href='javascript:void(0)' class='easyui-menubutton' iconcls='iconfont uce-set' plain='true' data-options='menu:\"#dg-set-menu-"+id+"\"'></a></div>");
		$.parser.parse('#dg-set-'+id);
	}*/
}
/*function saveDatagridSetExport(id,key){
	function getCheckedNodes(nodes){
		function buildExportNode(node){
			node.key = node.field;
			node.name = node.title;
			if(node.columns){
				node.list = node.columns;
				delete node.key;
			}
			for(attr in node){
				if(attr != 'key' && attr != 'name' && attr != 'list'){
					delete node[attr];
				}
			}
		}
		var limitIndex = nodes.length;
		for(var i = 0 ; i < limitIndex ; i ++){
			if(nodes[i].checked){
				buildExportNode(nodes[i]);
				if(nodes[i].list){
					getCheckedNodes(nodes[i].list);
				}
			}else{
				nodes.splice(i,1);
				limitIndex--;
				i--;
			}
		}
		return nodes;
	}
	var treeObj = $.fn.zTree.getZTreeObj("dg_export_set_"+id+"_tree");
	var nodes = treeObj.getNodes();
	var columnsRequest =  getCheckedNodes(nodes);
	var data = {};
	data.key = key;
	data.value = JSON.stringify(columnsRequest);
	$.ajax({
		url:'../export/tableSet.do',
		data: data,
		success: function(result){
			closeDialog('dg_export_set_'+id+'_dlg')	;
		}
	})
}*/

/*function exportSetClick(id,key,columns){
	if($('#dg_export_set_'+id+'_container').length == 0){
		$('#dg-set-menu-'+id).after("<div  id = 'dg_export_set_"+id+"_container'><div class='easyui-dialog' id = 'dg_export_set_"+id+"_dlg' style='width:300px;height:500px' closed='true' buttons='#dg_export_set_"+id+"_dlg_btn'><div class='ztree' data-options='fit:true,rownumberWidth:50' id = 'dg_export_set_"+id+"_tree'></div></div><div id='dg_export_set_"+id+"_dlg_btn'><a href='javascript:void(0)' class='easyui-linkbutton save' onclick='saveDatagridSetExport(\""+id+"\",\""+key+"\")' >保存</a><a href='javascript:void(0)' class='easyui-linkbutton call' onclick='javascript:closeDialog(\"dg_export_set_"+id+"_dlg\")' >取消</a></div><div>");
		$.parser.parse('#dg_export_set_'+id+'_container');
	}
	var columnsOrigin = columns.slice();
	var columnsResult = [];
	for(var i = 0 ; i < columnsOrigin[0].length ; i ++){
		if(columnsOrigin[0][i].exportField != false){
			if(columnsOrigin[0][i].colspan){
				setChildColumns(columnsOrigin[0][i],1,columnsOrigin[0][i].colspan);
			}
			columnsResult.push(columnsOrigin[0][i]);
		}
	}
	
	function setChildColumns(column,level,colspan){
		var childColumns = [];
		var limitIndex = colspan;
		for(var i = 0 ; i < limitIndex ; i++){
			if(columnsOrigin[level][0].colspan){
				limitIndex = limitIndex - columnsOrigin[level][0].colspan + 1;
				setChildColumns(columnsOrigin[level][0],level+1,columnsOrigin[level][0].colspan);
			}
			childColumns.push(columnsOrigin[level].splice(0,1)[0]);
		}
		column.columns = childColumns;
	}
	var setting = {
		data:{
			key:{
				name:'title',
				children: 'columns'
			},
			simpleData:{
				idKey:'field'
			}
			
		},
		check:{
			enable:true
		}
	}
	$.fn.zTree.init($('#dg_export_set_'+id+'_tree'), setting, columnsResult);
	openDialog('dg_export_set_'+id+'_dlg','导出设置');
}*/

function uceDatagrid(id,columns,dataGridParams){
	if(id ==null || columns==null || dataGridParams.url==null){
		console.error("datagrid id、colums、url不能为空！");
		return;
	};
	if (dataGridParams.pageSize!=null && dataGridParams.pageSize != undefined) {
		if(!isNaN(dataGridParams.pageSize)){
			dataGridParams.pageList=[dataGridParams.pageSize,dataGridParams.pageSize*2,dataGridParams.pageSize*3,dataGridParams.pageSize*4,dataGridParams.pageSize*5,dataGridParams.pageSize*10]
			dataGridParams.pagination = 'true';
		}else{
			console.error("pageSize必须要为数字！");
		    return;
		}
	} 
	
	$('#'+id).datagrid({
		title: dataGridParams.title ? dataGridParams.title : '',
/*		width: dataGridParams.width ? dataGridParams.width : 'auto',
		height: dataGridParams.height ? dataGridParams.height : 'auto',*/
		url: dataGridParams.url,
		method: dataGridParams.method =='get' ? 'get' : 'post',
		queryParams: dataGridParams.queryParams ? dataGridParams.queryParams : {},//请求参数对象{a:'1'}
		columns:columns, //列配置项
	    striped: true , //是否显示斑马线效果。
	    collapsible:false,//表格是否可折叠
	    singleSelect : dataGridParams.singleSelect == 'true' ? true : false,  //允许单行选择true/false
	    fitColumns: dataGridParams.fitColumns == 'true' ? true : false, //真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。
	    loadMsg:'数据加载中请稍后……',  
	    pagination: dataGridParams.pagination =='true' ? true : false,  //如果为true，则在DataGrid控件底部显示分页工具栏。
    	pagePosition:'bottom',//定义分页工具栏的位置。可用的值有：'top','bottom','both'。
	    rownumbers: dataGridParams.rownumbers=='false' ? false : true, //如果为true，则显示一个行号列。
	    pageNumber : 1, //在设置分页属性的时候初始化页码。
	    pageSize: dataGridParams.pageSize ? dataGridParams.pageSize : 10,//在设置分页属性的时候初始化页面大小。
	    pageList: dataGridParams.pageList ? dataGridParams.pageList : [10,20,30,40,50,100],//在设置分页属性的时候 初始化页面大小选择列表。
	    nowrap: true, //如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
	    resizable: true, //如果为true，允许列改变大小。
	    toolbar: dataGridParams.toolbar ? dataGridParams.toolbar : [], //顶部工具栏的DataGrid面板
	    onBeforeLoad: dataGridParams.onBeforeLoad ? dataGridParams.onBeforeLoad : function(param){
	    	//在载入请求数据数据之前触发，如果返回false可终止载入数据操作。
	    	console.log('onBeforeLoad');
	    },
	    onLoadSuccess: dataGridParams.onLoadSuccess ? dataGridParams.onLoadSuccess : function(data){
	    	//在数据加载成功的时候触发
	    	console.log('onLoadSuccess');
	    },
	    onLoadError:dataGridParams.onLoadError ? dataGridParams.onLoadError : function(){
	    	//在载入远程数据产生错误的时候触发。
	    	console.log('onLoadError');
	    }
	});
}
/**
 * 获取datagrid选取的值
 * @tableid 表格dom元素id
 */
function getDatagridVal(tableid){
	var obj = $('#'+tableid).datagrid('getSelections');
	return obj;
}


/**
 * 序列化对象
 * @param formId
 * @returns {___anonymous1382_1383}
 */
function serializeFormObj(formId) {
	var paramArray = $("#" + formId).serializeArray();
	var paramObjs = {};
	for (var i = 0; i < paramArray.length; i++) {
		var paramObj = {};
		paramObj[paramArray[i].name] = paramArray[i].value;
		$.extend(paramObjs, paramObj);
	}
	return paramObjs;
}

/***
 * 根据字段名称获取combo tree value
 * @param el combotree元素
 * @param filedName 字段名称 
 * @returns {Array}
 */
function getComboTreeValue(comboTreeId, valueField) {
	var values = [];
	var checkedDataList = $("#" + comboTreeId).combotree('tree').tree('getChecked');
	$.each(checkedDataList,function(idx, data){
		$.each(data, function(fieldName, fieldValue){
			if (fieldName == valueField){
				values.push(fieldValue);
			}
		});
	});
	return values;
} 

/**
 * alert info消息功能
 * @param msg 消息内容
 * @param fn 回调方法(可选参数)
 * @param args 回调方法参数(可选参数)
 * @param title 消息标题(可选参数)
 */
function showInfoMsg(msg, fn ,args, title) {
	if (isEmptyObject(msg)) {
		return;
	}
	var	infoTitle = !title ? '信息提示' : title;
	var msg = "<p>"+msg+"</p>";
	if (!fn) {
		$.messager.alert(infoTitle, msg, 'info');
		return;
	} 
	$.messager.alert(infoTitle, msg, 'info', function(){
		fn.apply(this, args);
	});
} 


/**
 * alert error消息功能
 * @param msg 消息内容
 * @param fn 回调方法(可选参数)
 * @param args 回调方法参数(可选参数)
 * @param title 消息标题(可选参数)
 */
function showErrorMsg(msg, fn ,args, title) {
	if (isEmptyObject(msg)) {
		return;
	}
	var	infoTitle = !title ? '错误提示' : title;
	var msg = "<p>"+msg+"</p>";
	if (!fn) {
		$.messager.alert(infoTitle, msg, 'error');
		return;
	} 
	$.messager.alert(infoTitle, msg, 'error', function(){
		fn.apply(this, args);
	});
} 

/**
 * alert warn消息功能
 * @param msg 消息内容
 * @param fn 回调方法(可选参数)
 * @param args 回调方法参数(可选参数)
 * @param title 消息标题(可选参数)
 */
function showWarnMsg(msg, fn ,args, title) {
	if (isEmptyObject(msg)) {
		return;
	}
	var	infoTitle = !title ? '警告提示' : title;
	var msg = "<p>"+msg+"</p>";
	if (!fn) {
		$.messager.alert(infoTitle, msg, 'warning');
		return;
	} 
	$.messager.alert(infoTitle, msg, 'warning', function(){
		fn.apply(this, args);
	});
}

/**
 * show消息功能(从右下角弹出)
 * @param msg 消息内容
 * @param title 消息标题,默认'信息提示'(可选参数)
 * @param timeout 超时时间,默认3000毫秒(可选参数)
 */
function showInfoTip(msg, title, timeout) {
	if (isEmptyObject(msg)) {
		return;
	}
	var	infoTitle = !title ? '信息提示' : title;
	var infoTimeout = !timeout ? 3000 : timeout;
	var msgtip = $.messager.show({
		msg: '<div class="messager-icon messager-info" ></div><div style="font-size:15px;font-weight:bold;margin-top:0;color:#464c5b;">'+infoTitle+'</div><div style="margin-top:5px;color:#777272;">' + msg + '</div><div class="panel-tool" id="uce-msg-info" style="top:15px;right:5px;color: #657180;"><a class="panel-tool-close" ></a></div>',
		timeout: infoTimeout,
		showType: 'fade'
	});
	$('.messager-body').css('height','auto');
	$('.messager-body').parents('.panel').css('right','5px');
	$('.messager-body').parents('.panel').css('bottom','100px');
	$('.messager-body').parents('.panel').css('border-radius','4px');
	$('#uce-msg-info').bind('click',function(){
		msgtip.window('close');
	})
}

/**
 * show info消息功能
 * @param msg 消息内容
 * @param title 消息标题,默认'信息提示'(可选参数)
 * @param timeout 超时时间,默认3000毫秒(可选参数)
 */
function showErrorTip(msg, title, timeout) {
	if (isEmptyObject(msg)) {
		return;
	}
	var	infoTitle = !title ? '消息' : title;
	var infoTimeout = !timeout ? 3000 : timeout;
	var msgtip = $.messager.show({
		msg: '<div class="messager-icon messager-error" ></div><div style="font-size:15px;font-weight:bold;margin-top:0;color:#464c5b;">'+infoTitle+'</div><div style="margin-top:5px;color:#777272;">' + msg + '</div><div id="uce-msg-error" class="panel-tool" style="top:15px;right:5px;color: #657180;"><a class="panel-tool-close" ></a></div>',
		timeout: infoTimeout,
		showType: 'fade'
	});
	$('.messager-body').css('height','auto');
	$('.messager-body').parents('.panel').css('right','5px');
	$('.messager-body').parents('.panel').css('bottom','100px');
	$('.messager-body').parents('.panel').css('border-radius','4px');
	$('#uce-msg-error').bind('click',function(){
		msgtip.window('close');
	})
}

	/**
	 * 消息功能(右下角)
	 * @param msg 消息内容
	 * @param type 消息类型 success(成功)、error（错误）、warning（警告）、info（普通消息）
	 * @param timeout 超时时间,默认3000毫秒(可选参数)
	 */
	function showTips(msg, type, timeout) {
		if (isEmptyObject(msg)) {
			return;
		}
		var	infoTitle = type=='success'?'成功':(type=='error'?'错误':(type=='warning'?'警告':'消息'));
		var infoDom = '<div style="font-size:15px;font-weight:bold;margin-top:0;color:#464c5b;">'+infoTitle+'</div><div style="margin-top:5px;color:#777272;">' + msg + '</div><div id="uce-msg" class="panel-tool" style="top:15px;right:5px;color: #657180;"><a class="panel-tool-close" ></a></div>';
		var msg;
		if(type=='success'){
			msg = '<div class="messager-icon messager-success"></div>'+infoDom;
		}else if(type=='error'){
			msg = '<div class="messager-icon messager-error"></div>'+infoDom;
		}else if(type=='warning'){
			msg = '<div class="messager-icon messager-warning"></div>'+infoDom;
		}else{
			msg = '<div class="messager-icon messager-info" ></div>'+infoDom;
		}
		var infoTimeout = !timeout ? 3000 : timeout;
		var msgtip = $.messager.show({
			msg: msg,
			timeout: infoTimeout,
			showType: 'fade',
			height:'83'
		});
		$('.messager-body').parents('.panel').css('right','5px');
		$('.messager-body').parents('.panel').css('bottom','100px');
		$('.messager-body').parents('.panel').css('border-radius','4px');
		$('#uce-msg').bind('click',function(){
			msgtip.window('close');
		})
	}


/**
 * 
 * @param msg 消息内容
 * @param fn 回调方法
 * @param args 回调方法参数
 */
function confirmMsg(msg, fn, args, title) {
	var	infoTitle = !title ? '确认操作' : title;
	var msg = "<p>"+msg+"</p>";
	$.messager.confirm(infoTitle, msg, function(r) {
		if (r) {
			fn.apply(this, args);
		}
	});
}

/**
 * 判断对象是否为null
 * @param obj
 * @returns {Boolean}
 */
function isEmptyObject(obj) {
  for (var key in obj) {
    return false;
  }
  return true;
}

/**
 * jQuery ajax请求
 * @param url 请求路径
 * @param data 请求参数
 * @param callback 回调方法
 * @param type 请求方式,POST/GET,默认Post
 */
function uceAjax(url, data, callback, type) {
	if (!type) {
		type = 'POST';
	}
	$.ajax({
		type: type,
		url: url,
		data: data,
		success : function(data){
			var result = data;
			if (typeof data == 'string') {
				result = eval('('+ data +')');
			}
			if (result.message) {
				result.success ? showTips(result.message,'success') : showError(result);
			}
			if(callback){
				callback.apply(this, [result]);
			} 
		}
	});
}

/**
 * 展示错误信息
 * @param result
 */
function showError(data){
	console.log(data);
	if (data && isEmptyObject(data.stackTrace)) {
		showErrorTip(data.message);
		return;
	}
	openWindow({
        id: 'winErrorDetail',
        title: '错误信息',
        width: 400,
        height:300,
        url: '../error/toErrorDetail.do',
        onLoad : function() {
        	$('#divErrorTitle').html(data.message);
        	$('#divErrorDetail').html(data.stackTrace);
        },
        onSave: function () {}
    })
}

/**
 * 打开dialog
 * @param param
 */
function openWindow(param) {
	//add by xj crossDomain
	var winWidth = 800;
	var winHeight = 400;
	if(crossDomainFlag == true){
		winWidth = window.outerWidth;
		winHeight = window.outerHeight;
	}else{
		winWidth = $('.tabs-panels', parent.document).width();
		winHeight = $('.tabs-panels', parent.document).height();
	}
	//add by xj crossDomain
	//var winWidth = $('.tabs-panels',parent.document).width();
	//var winHeight = $('.tabs-panels',parent.document).height();
	$('#' + param.id).dialog({
		title : param.title,
		width : param.width<winWidth ? param.width : winWidth-50,
		height : param.height<winHeight ? param.height : winHeight-20,
		href : param.url,
		modal:true,
		constrain:true,
		onSave : function(d) {
			if (param.onSave) {
				param.onSave.call(this, d);
			}
		},
		onLoad : function(d) {
			if (param.onLoad) {
				param.onLoad.call(this, d);
			}
		}
	});
	$('#' + param.id).dialog('open');
	$('#' + param.id).parents('.panel').css('position','fixed');
}

/**
 * 打开dialog 
 * @param id 
 * @param title 
 */
function openDialog(id, title) {
	$('#' + id).dialog({
		title : title,
		iconCls:'icon-title',
		cache : false,
		modal : true,
		constrain:true,
		resizable:true,
		top:50,
		onLoad : function(){
		},
		onOpen : function() {
		},
		onBeforeOpen: function() {
			var formId = id.substr(4, id.length - 4);
			if ($('#form' + formId)) {
				$('#form' + formId).form('clear');
			}
		}
	});
	
	var dlgWidth = $('#' + id).dialog('options').width;
	var dlgHeight = $('#' + id).dialog('options').height;
	//add by xj crossDomain
	var winWidth = 800;
	var winHeight = 400;
	if(crossDomainFlag == true){
		winWidth = window.outerWidth;
		winHeight = window.outerHeight;
	}else{
		winWidth = $('.tabs-panels', parent.document).width();
		winHeight = $('.tabs-panels', parent.document).height();
	}
	//add by xj crossDomain
	var top =  $('#' + id).dialog('options').top;
	var left =  $('#' + id).dialog('options').left;
	if(dlgHeight >= winHeight){
		$('#' + id).dialog('resize',{height:winHeight-100,top:20})
	}
	if(dlgWidth >= winWidth){
		$('#' + id).dialog('resize',{width:winWidth-50,left:20})
	}
	
	$('#' + id).dialog('open');
	$('#' + id).parents('.panel').css('position','fixed');
}

/**
 * 关闭dialog
 * @param id
 */
function closeDialog(id) {
	$('#'+id).dialog('close');
}

/**
 * 重写加载datagrid
 * @param id
 */
function reloadDatagrid(id) {
	$('#'+id).datagrid('reload');
}

/**
 * 清除form
 * @param id
 */
function clearForm(id) {
	$('#'+id).form("clear");
}

/**
 * 多行文本控件
 * @param id 控件id
 * @param regex 文本值正则表达式校验规则
 * @param maxNumber 最大输入个数
 * @param toUpper 输入字母是否自动转大写 
 */
function loadMultilineInput(id, regex, maxNumber, toUpper) {
	if (toUpper == null || toUpper == undefined) {
		toUpper = false;
	}
	if (maxNumber == null || maxNumber == undefined) {
		maxNumber = 100;
	}
	var idStr = '#' + id;
	$(idStr).textbox({
		inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
			keyup : function(event){
				if (regex) {
					if (regex.test(this.value)){
						this.value = this.value.replace(regex, '');
					}
				}
				if (toUpper) {
					this.value = this.value.toUpperCase();
				}
				if (event.keyCode == 13){
	    			var txtCodes = $(idStr).textbox('getValue').split('\n');
	    			var i =0;
	    			var txtCodeStr = '';
	    			var convertReg = new RegExp('\\s*','g');
	    			for(var j in txtCodes ) {
	    				var code = txtCodes[j].replace(convertReg,'');
	    				if (code != '' && code != null) {
	    					if (i < maxNumber) {
	    						txtCodeStr += code +'\n';
	    					}
	    					i++;
	        				if(i == maxNumber) {
	        					$(idStr).textbox('setValue',txtCodeStr );
	        					showInfoMsg('最多只能输入' + maxNumber + '个');
	        					break;
	        				}
	    				}
	    			}
				}
			} 
		})
	});
}

/**
 * 运单编号多行文本控件
 * @param id
 * @param maxNumber
 */
function loadMutilineBillCodeInput(id, maxNumber) {
	loadMultilineInput(id, new RegExp(/[^a-zA-Z0-9\n-]/g,'g'), maxNumber, true);
}

/**
 * 将多行文本框的值以符号分割
 * @param id 
 * @param splitChar 分隔符，默认为“,”
 * @returns {String}例如{abc,ef}
 */
function getMultilineValues(id, splitChar){
	var txtCode = $('#' + id).textbox('getValue');
	if (txtCode == '' || txtCode == null){
		return '';
	}
	if (!splitChar) {
		splitChar = ',';
	}
	var txtCodes = txtCode.trim().split('\n');
	var txtCodeStr = '';
	var re = new RegExp('\\s*','g');
	for(var j in txtCodes ) {
		var code = txtCodes[j].replace(re,'');
		if (code != '' && code != null) {
			if (j < txtCodes.length - 1) {
				txtCodeStr += (code + splitChar);
			} else {
				txtCodeStr += code;
			}
		}
	}
	return txtCodeStr;
}

/**
 * 将多行文本框的值根据\n分割
 * @param id
 * @returns 返回数组,例如["abc", "def"]
 */
function getMultilineValueArray(id){
	var txtCode = $('#' + id).textbox('getValue');
	if (txtCode == '' || txtCode == null){
		return '';
	}
	return txtCode.trim().split('\n');
}


//是否删除格式
function formDeleteFlag(value, rec, rowIndex) {
    if (!value) {
        return "<input type=\"checkbox\"  name=\"ckEnabled\"  disabled=\"disabled\" >";
    }
    return "<input type=\"checkbox\"  name=\"ckEnabled\" checked=\"checked\"  disabled=\"disabled\" >";
}

//是否启用格式
function formEnabledFlag(value, rec, rowIndex) {
    if (value) {
        return "<input type=\"checkbox\"  name=\"ckEnabled\" checked=\"checked\"  disabled=\"disabled\" >";
    }
    return "<input type=\"checkbox\"  name=\"ckEnabled\"  disabled=\"disabled\" >";
}

//鼠标悬停显示内容
function formatTip(value,row,index){
	return value == null ? "" : "<a title='" + value + "'>" + value + "</a>";
}

/** 日期格式化为yyyy-mm-dd hh:mm:ss */
function formatTime(value) {
	if (value == null) {
		return '';
	}
	var val = new Date(value);
	var year = parseInt(val.getYear()) + 1900;
	var month = parseInt(val.getMonth()) + 1;
	month = month > 9 ? month : ('0' + month);
	var date = parseInt(val.getDate());
	date = date > 9 ? date : ('0' + date);
	var hours = parseInt(val.getHours());
	hours = hours > 9 ? hours : ('0' + hours);
	var minutes = parseInt(val.getMinutes());
	minutes = minutes > 9 ? minutes : ('0' + minutes);
	var seconds = parseInt(val.getSeconds());
	seconds = seconds > 9 ? seconds : ('0' + seconds);
	var time = year + '-' + month + '-' + date + ' ' + hours + ':' + minutes + ':' + seconds;
	return time;
}

/*日期格式*/
// formatTime(value);//输出默认样式
// formatTime(value,'ss');输出的日期是/的形式
// formatTime(value,'dd');//输出加日期是.的形式
// formatTime(value,'data');// 只输出日期格式
// formatTime(value,'time');//只输出时分秒格式
// formatTime(value,'data','ss');// 只输出日期 加 / 形式
// formatTime(value,'data','dd');//只输出日期 加 . 形式

function formatData(value, dataOrTime, ssOrdd) {
    var val = new Date(value);
    var year = parseInt(val.getYear()) + 1900;
    var month = parseInt(val.getMonth()) + 1;
    month = month > 9 ? month : ('0' + month);
    var date = parseInt(val.getDate());
    date = date > 9 ? date : ('0' + date);
    var hours = parseInt(val.getHours());
    hours = hours > 9 ? hours : ('0' + hours);
    var minutes = parseInt(val.getMinutes());
    minutes = minutes > 9 ? minutes : ('0' + minutes);
    var seconds = parseInt(val.getSeconds());
    seconds = seconds > 9 ? seconds : ('0' + seconds);
    if(value==null){
        return ''
    }else if (arguments.length == 1) {
    	var data=year + '-' + month + '-' + date + ' ' + hours + ':' + minutes + ':' + seconds ;
    	return "<a title='" +data + "'>" + data + "</a>"
    } else if (arguments.length == 2) {
        if (arguments[1] == 'data') {
        	var data=year + '-' + month + '-' + date
        	return "<a title='" +data + "'>" + data + "</a>"
        } else if(arguments[1] == 'Time'){
        	var data=hours + ':' + minutes + ':' + seconds
        	return "<a title='" +data + "'>" + data + "</a>"
        }else if(arguments[1] == '/'){
        	var data= year + '/' + month + '/' + date + ' ' + hours + ':' + minutes + ':' + secondse
        	return "<a title='" +data + "'>" + data + "</a>"
        }else{
        	var data=year + '.' + month + '.' + date + ' ' + hours + ':' + minutes + ':' + seconds
        	return "<a title='" +data + "'>" + data + "</a>"
        }
    } else {
        if(arguments[1] == 'data'&&arguments[2] == 'ss'){
        	var data=year + '/' + month + '/' + date
        	return "<a title='" +data + "'>" + data + "</a>"
        }else if(arguments[1] == 'data'&&arguments[2]=='dd'){
           var data= year + '.' + month + '.' + date
           return "<a title='" +data + "'>" + data + "</a>"
        }
    }
}
/** 格式化字典下拉列表为typeCode-typeName */
function formatDicShowTypeCode(row) {
	if (row.typeCode) {
		return row.typeCode + "-" + row.typeName;
	}
	return row.typeName;
}

/**
 * 删除确认框
 * @param dgId 控件id
 * @param callback 回调函数
 */
function deleteCheckAndConfirm(dgId, callback) {
	var selectedRows = $('#' + dgId).datagrid('getSelections');
    if(selectedRows.length <= 0){
    	showInfoMsg("请先选择要删除的数据！");
    	return;
    }
    var deletedNum = 0;
    var undeleteRows =[];
    var msg = "";
    var map = {}; 
    for(var i in selectedRows){
    	if (selectedRows[i].deleteFlag) {
    		deletedNum ++; 
        }else{
        	undeleteRows.push(selectedRows[i]);
        }
    }
    
    if (deletedNum == selectedRows.length) {
    	showErrorMsg("选中的数据已经删除了，不能重复删除！");
 		return;
    } else {
    	if (deletedNum == 0) {
    		msg = "确认删除吗？";
    	} else {
    		msg = "您所要删除的数据有【"+ deletedNum +"】条是已删除的，不能重复删除; 你确定要删除其他【" + (selectedRows.length-deletedNum) + "】条数据吗？";
    	}
		confirmMsg(msg , function() {
			callback.apply(this, [undeleteRows]);
    	})
    }
}

/***
 * 日期控件限制
 * @param beginid 开始时间id
 * @param endid 结束时间id
 */
function dateRestrict(beginid, endid) {
	datetimeboxRestrict(beginid, endid, 'datebox');
}
/**
 * 日期时间控件限制
 * @param beginid 开始时间id
 * @param endid 结束时间id
 */
function dateTimeRestrict(beginid, endid) {
	datetimeboxRestrict(beginid, endid, 'datetimebox');
}
/**
 * 时间控件限制时间范围
 * @param beginid 开始时间id
 * @param endid 结束时间id
 * @param controlType 组件类型
 */
function datetimeboxRestrict(beginid, endid, controlType) {
	if (!controlType) {
		controlType = 'datetimebox';
	}
	$("#" + beginid)[controlType]({
		onHidePanel : function () {
			var options = $(this)[controlType]('options');
			if (options.onHidePanelTimer) {
				clearTimeout(options.onHidePanelTimer);
			}
			options.onHidePanelTimer = setTimeout(function() {
				var beginTimeStr =  $("#" + beginid)[controlType]('getValue');
    			if (beginTimeStr == null) {
    				return null;
    			}
    			var endTimeStr = $("#" + endid)[controlType]('getValue');
    			datetimeValidator(endid, stringToDatetime(beginTimeStr), null, controlType);
    			//validateBeginEndTime(beginid, endid);
			}, 300);
			
		}
	});

	$("#" + endid)[controlType]({
		onHidePanel : function () {
			var options = $(this)[controlType]('options');
			if (options.onHidePanelTimer) {
				clearTimeout(options.onHidePanelTimer);
			}
			options.onHidePanelTimer = setTimeout(function() {
				var endTimeStr = $("#" + endid)[controlType]('getValue');
    			if (endTimeStr == null) {
    				return null;
    			}
    			var beginTimeStr =  $("#" + beginid)[controlType]('getValue');
    			datetimeValidator(beginid, null , stringToDatetime(endTimeStr), controlType);
    			//validateBeginEndTime(beginid, endid);
			}, 50);
		}
	});
}

/**
 * 校验开始结束日期
 * @param beginid	开始日期id
 * @param endid		结束日期id
 * @returns {Boolean}
 */
function validateBeginEndDate(beginid, endid) {
	return validateBeginEndDateBox(beginid, endid, 'datebox');
}

/**
 * 校验开始结束时间
 * @param beginid	开始时间id
 * @param endid		结束时间id
 * @returns {Boolean}
 */
function validateBeginEndTime(beginid, endid) {
	return validateBeginEndDateBox(beginid, endid, 'datetimebox');
}

/**
 * 校验开始结束日期box
 * @param beginid	开始时间id
 * @param endid		结束时间id
 * @param controlType 组件类型
 * @returns {Boolean}
 */
function validateBeginEndDateBox(beginid, endid, controlType) {
	if (!controlType) {
		controlType = 'datetimebox';
	}
	var endTimeStr = $("#" + endid)[controlType]('getValue');
	var beginTimeStr =  $("#" + beginid)[controlType]('getValue');
	if (beginTimeStr != null && beginTimeStr != '' && endTimeStr != null && endTimeStr != '' && beginTimeStr > endTimeStr) {
		return false;
	}
	return true;
}

/**
 * 
 * @param id
 * @param minDate 开始日期
 * @param maxDate 结束日期
 * @param controlType 控件类型
 */
function datetimeValidator(id, minDate, maxDate, controlType) {
	if (!controlType) {
		controlType = 'datetimebox';
	}
	$("#" + id)[controlType]('calendar').calendar({
		validator : function (date) {
			if (minDate == null && maxDate == null) {
				return true;
			}
			if (minDate == null) {
				return date <= maxDate;
			}
			if (maxDate == null) {
				return date >= minDate;
			}
			return date >= minDate && date <= maxDate;
		}
	});
}

/**
 * 将字符日期转换为Date类型日期
 * @param str
 * @returns {Date}
 */
function stringToDatetime(str) {
    var reg=/[\s,\-,\:]/
    var ss = (str.split(reg));

    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (ss.length > 3) {
    	var h = parseInt(ss[3],10);
        var min = parseInt(ss[4],10);
        var sec = parseInt(ss[5],10);
        return new Date(y,m-1,d,h,min,sec);
    } else {
    	return new Date(y,m-1,d);
    }
}

/**
 * 加载机构控件
 */
function loadOrgCombotreegrid(id, options) {
	$("#" + id).combotreegrid($.extend({
        panelWidth:500,
        url:'../org/findOrgByParentId.do',
        idField:'orgId',
        treeField:'orgName',
        columns:[[
            {field:'orgName',title:'机构名称',width:200},
            {field:'orgCode',title:'机构编码',width:100}
        ]]}, options));
}

/**
 * tab右键关闭菜单
 * @maintabid:tab id eg:<div id="mainTab" class="easyui-tabs" fit="true"></div>
 * @menuid:菜单id 
 * eg:<div id="mm" class="easyui-menu" style="width:120px;">
		<div data-options="iconCls:'icon-cancel'" id="closecur">
			关闭
		</div>
	</div>
 */
function tabRightMenu(maintabid,menuid){
	//监听右键事件，创建右键菜单
    var indexId = 0;
    $('#'+maintabid).tabs({
        onContextMenu:function(e, title,index){
            e.preventDefault();
            indexId = index;
            if(index>0){
                $('#'+menuid).menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            }
        }
    });
  	//关闭当前标签页
	$("#closecur").bind("click",function(){
// 		var tab = $('#mainTab').tabs('getSelected');
// 		var indexId = $('#mainTab').tabs('getTabIndex',tab);
		$('#mainTab').tabs('close',indexId);
	});
	//关闭所有标签页
	$("#closeall").bind("click",function(){
		var tablist = $('#mainTab').tabs('tabs');
		for(var i=tablist.length-1;i>=0;i--){
			if(i!=0){
				$('#mainTab').tabs('close',i);
			}
		}
	});
	//关闭非当前标签页（先关闭右侧，再关闭左侧）
	$("#closeother").bind("click",function(){
		var tablist = $('#mainTab').tabs('tabs');
// 		var tab = $('#mainTab').tabs('getSelected');
// 		var indexId = $('#mainTab').tabs('getTabIndex',tab);
		for(var i=tablist.length-1;i>indexId;i--){
			if(i!=0){
				$('#mainTab').tabs('close',i);
			}
		}
		var num = indexId-1;
		for(var i=num;i>=0;i--){
			if(i!=0){
				$('#mainTab').tabs('close',i);
			}
		}
		$('#mainTab').tabs('select',1);
	});
	//关闭当前标签页右侧标签页
	$("#closeright").bind("click",function(){
		var tablist = $('#mainTab').tabs('tabs');
// 		var tab = $('#mainTab').tabs('getSelected');
// 		var indexId = $('#mainTab').tabs('getTabIndex',tab);
		for(var i=tablist.length-1;i>indexId;i--){
			if(i!=0){
				$('#mainTab').tabs('close',i);
			}
		}
		$('#mainTab').tabs('select',indexId);
	});
	//关闭当前标签页左侧标签页
	$("#closeleft").bind("click",function(){
// 		var tab = $('#mainTab').tabs('getSelected');
// 		var indexId = $('#mainTab').tabs('getTabIndex',tab);
		var num = indexId-1;
		for(var i=0;i<=num;i++){
			if(i!=0){
				$('#mainTab').tabs('close',i);
			}
		}
		$('#mainTab').tabs('select',1);
	});
}

/**
 *combobox下拉列表
 *@domId input id,如：<input id="datadic" name="datadic" value="" data-options="prompt:'请选择查询条件'"/>
 *@ combobox配置参数对象
 *@url 请求地址
 *@method 请求方式get/post
 *@valueField 基础数据 值名称 绑定到该下拉列表框。
 *@textField  基础数据 字段名称 绑定到该下拉列表框。 
 *@queryParams 请求参数，可选值
 */
function fspCombobox(domId,url,method,valueField,textField,queryParams){
	if(!domId||!url || !valueField || !textField){
		console.error("domId url、valueField、textField不能为空！");
		return;
	};
	$('#'+domId).combobox({
	    url:url, 
	    method: method?method:'post',
	    queryParams: queryParams?queryParams:{},
	    valueField:valueField,
	    textField:textField,
	    editable:true,
		panelHeight:'auto',
		panelMaxHeight:'200'
	});
}

/**
 *数据字典下拉列表
 *@domId input id,如：<input id="datadic" name="datadic" value="" data-options="prompt:'请选择查询条件'"/>
 *@typeClassCode 请求参数
 */
function dicCombobox(domId,typeClassCode,params){
	/*'../dictData/findDictData.do'*/
	if(!domId || !typeClassCode){
		console.error("domId,typeClassCode不能为空！");
		return;
	};
	$('#'+domId).uceCombobox({
		headerValue : params && params.headerValue == false ? undefined : '',
	    url : params && params.url ? params.url : '../dictData/findDictData.do', 
	    method:'post',
	    queryParams: {"typeClassCode":typeClassCode},
	    valueField:'typeCode',
	    textField:'typeName',
	    value : params && params.value ? params.value : undefined,
	    limitToList : params && params.limitToList==false ? false : true,
	    editable: params && params.editable == true ? true : false,
		panelHeight : params && !isNaN(params.panelHeight) ? params.panelHeight : 200,
		multiple : params && params.multiple ? true : false,
		formatter : params && params.formatter ? params.formatter : undefined
	});
}

/**
 * getComboboxVal 获取combobox选择的值
 * @domId input id,如：<input id="datadic" name="datadic" value="" data-options="prompt:'请选择查询条件'"/>
 */
function getComboboxVal(domId){
	var val = $("#"+domId).combobox('getValue');
	return val;
}

/**
 * searchbox弹出选择列表
 * @id input id eg:<input  type="text" name="key" id="searchTable"/>
 * @url 请求接口地址
 * @columns 表头定义数据
 * @Mulchoice 是否支持多选 true/false
 */
function searchWinTable(id,url,columns,Mulchoice,callback){
	if(!id || !url || !columns){
		console.error("id、colums、url不能为空！");
		return;
	};
	$('#'+id).searchbox({
		searcher:function(value,name){ 
			var dataGridParams = new Object();
			dataGridParams.url = url;
			dataGridParams.singleSelect = Mulchoice?Mulchoice:'false';
			dataGridParams.queryParams = new Object();
			dataGridParams.queryParams[name] = value;

			$('#w').window({
				constrain:true,
			    onOpen:function(){
			    	//调用datagrid组件
			    	newloadGrid('table', columns, dataGridParams);
			    },
			    onResize:function(newwidth, newheight){
		    		$('#table').datagrid('resize',{width:(newwidth-30),height:(newheight-125)});
			    }
			}); 
		}
	});
	//回调方法
	$("#ok").bind("click",function(){
		if(callback){
			callback();
		}else{
			console.warn('无回调函数')
		}
	});
}


/*
 * 设置临时存储的值
 */
function setSessionStorage(key,value){
	if(typeof(value) == 'string' || typeof(value) == 'number'){
		sessionStorage.setItem(key,value)
	}
	if(typeof(value) == 'object'){
		sessionStorage.setItem(key, JSON.stringify(value));
	}
}

/*
 * 获取临时存储的值
 */
function getSessionStorage(key){
	var values = sessionStorage.getItem(key);
	try {
        return JSON.parse(values);;
    } catch (e) {
        return values;
    }
}

/*
 * 根据key清除临时存储的值
 */
function remSessionStorage(key){
	sessionStorage.removeItem(key);
}

/*
 * 清除所有临时存储的值
 */
function clearSessionStorage(){
	sessionStorage.clear();
}

/*
 * 设置永久存储的值
 */
function setLocalStorage(key,value){
	if(typeof(value) == 'string' || typeof(value) == 'number'){
		localStorage.setItem(key,value)
	}
	if(typeof(value) == 'object'){
		localStorage.setItem(key, JSON.stringify(value));
	}
}

/*
 * 获取永久存储的值
 */
function getLocalStorage(key){
	var values = localStorage.getItem(key);
	try {
        return JSON.parse(values);;
    } catch (e) {
        return values;
    }
}

/*
 * 根据key删除永久存储的值
 */
function remLocalStorage(key){
	localStorage.removeItem(key);
}

/*
 * 清除所有永久存储的值
 */
function clearLocalStorage(){
	localStorage.clear();
}

/*
 * 查询营运机构树
 */
function nosOrgTree(treeId ,searchId ,params){
	if(!treeId){
		console.error("treeId不能为空！");
		return;
	}
	$("#"+treeId).tree({
	    url : params && params.url ? params.url : '../org/getNosOrgTree.do',
	    method : params && params.method == 'get' ? 'get' : 'post',
	    animate : params && params.animate == true ? true : false,
	    checkbox : params && params.checkbox ? params.checkbox : false,
	    cascadeCheck : params && params.cascadeCheck == false ? false : true,
	    onlyLeafCheck : params && params.onlyLeafCheck == true ? true : false,
	    lines : params && params.lines == true ? true : false,
	    dnd : params && params.dnd == true ? true : false,
	    data : params && params.data ? params.data : null,
	    queryParams : params && params.queryParams ? params.queryParams : {},
	    formatter : params && params.formatter ? params.formatter : undefined,
	    filter : params && params.filter ? params.filter : undefined,
	    loader : params && params.loader ? params.loader : undefined,
	    onSelect : function(node) { 
        	$(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);
        },
       	onBeforeLoad : function(node,param){
        	if(node!=null){
        		param.id=node.id;
	        	param.orgType=node.orgType;
        	}
        },
        onLoadSuccess : params && params.onLoadSucess ? params.onLoadSucess : undefined,
        onClick: params && params.onClick ? params.onClick : undefined,
        onDblClick : params && params.onDblClick ? params.onDblClick : undefined,
        onLoadError : params && params.onLoadError ? params.onLoadError : undefined,
        onBeforeExpand : params && params.onBeforeExpand ? params.onBeforeExpand : undefined
	});
	if(searchId){
		$("#"+searchId).searchbox({
			onChange: function(value) {
				$("#"+treeId).tree("doFilter", value);	
			}
		})
	}
}

/*
 * 行政机构树
 */
function admOrgTree(treeId ,searchId ,params){
	if(!treeId){
		console.error("treeId不能为空！");
		return;
	}
	$("#"+treeId).tree({
	    url : params && params.url ? params.url : '../org/findAdmOrgTree.do',
	    method : params && params.method == 'get' ? 'get' : 'post',
	    animate : params && params.animate == true ? true : false,
	    checkbox : params && params.checkbox ? params.checkbox : false,
	    cascadeCheck : params && params.cascadeCheck == false ? false : true,
	    onlyLeafCheck : params && params.onlyLeafCheck == true ? true : false,
	    lines : params && params.lines == true ? true : false,
	    dnd : params && params.dnd == true ? true : false,
	    data : params && params.data ? params.data : null,
	    queryParams : params && params.queryParams ? params.queryParams : {},
	    formatter : params && params.formatter ? params.formatter : undefined,
	    filter : params && params.filter ? params.filter : undefined,
	    loader : params && params.loader ? params.loader : undefined,
	    onSelect : function(node) { 
        	$(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);
        },
       	onBeforeLoad : function(node,param){
        	if(node!=null){
        		param.orgId=node.id;
	        	param.orgType=node.orgType;
        	}
        },
        onClick:params && params.onClick ? params.onClick :undefined,
        onLoadSuccess : params && params.onLoadSuccess ? params.onLoadSuccess : undefined,
        onDblClick : params && params.onDblClick ? params.onDblClick : undefined,
        onLoadError : params && params.onLoadError ? params.onLoadError : undefined,
        onBeforeExpand : params && params.onBeforeExpand ? params.onBeforeExpand : undefined
	});
	
	if(searchId){
		$("#"+searchId).searchbox({
			onChange: function(value) {
				$("#"+treeId).tree("doFilter", value);	
			}
		})
	}
}
//营运机构组合树
function nosOrgComboTree(domId,orgTreeParam){
	if(!domId){
		console.error("domId不能为空！");
		return;
	}
	$("#"+domId).combotree({
		url:orgTreeParam && orgTreeParam.url ?  orgTreeParam.url : '../org/getNosOrgTree.do',
		multiple : orgTreeParam && orgTreeParam.multiple == false ? false : true,
		cascadeCheck : orgTreeParam && orgTreeParam.cascadeCheck == true ? true : false,
		panelHeight : orgTreeParam && orgTreeParam.panelHeight && !isNaN(orgTreeParam.panelHeight) ? orgTreeParam.panelHeight : undefined,
		width:orgTreeParam && orgTreeParam.width && !isNaN(orgTreeParam.width) ? orgTreeParam.width : undefined,
		height:orgTreeParam && orgTreeParam.height && !isNaN(orgTreeParam.height) ? orgTreeParam.height : undefined,
		editable:orgTreeParam && orgTreeParam.editable == true ? true : false,
		value:orgTreeParam && orgTreeParam.value ? orgTreeParam.value : undefined,
		onBeforeLoad:function(node,param){
        	if(node!=null){
        		param.id=node.id;
	        	param.orgType=node.orgType;
        	}
        },
        onLoadSuccess:orgTreeParam && orgTreeParam.onLoadSucess ? orgTreeParam.onLoadSucess : undefined,
        onClick: orgTreeParam && orgTreeParam.onClick ? orgTreeParam.onClick : undefined
	})
}

//营运机构组合树形表格
function nosOrgCombotreegrid(domId,params){
	if(!domId){
		console.error("domId不能为空！");
		return;
	}
	$("#"+domId).combotreegrid({
		url : params && params.url ?  params.url : '../org/getNosOrgTree.do',
		idField : 'id',
        treeField : 'text',
        fitColumns : true,
        limitToGrid : true,
        columns : [[
            {field:'text',title:'机构名称',width:200},
            {field:'id',title:'机构编码',width:100}
        ]],
        onBeforeLoad : function(node,param){
        	if(node!=null){
        		param.id=node.id;
	        	param.orgType=node.orgType;
        	}
        }
	})
}

//员工组合表格
function empCombogrid(domId,params){
	if(!domId){
		console.error("domId不能为空！");
		return;
	}
	if (params && params.pageSize!=null && params.pageSize != undefined) {
		if(!isNaN(params.pageSize)){
			params.pageList=[params.pageSize,params.pageSize*2,params.pageSize*3,params.pageSize*4,params.pageSize*5,params.pageSize*10]
			params.pagination = true;
		}else{
			console.error("pageSize必须要为数字！");
		    return;
		}
	} 
	$("#"+domId).combogrid({
		url : params && params.url ? params.url : '../emp/findEmpByPage.do' ,
		columns : params && params.columns ? params.columns : [[
		          	{field:'empId',title:'员工id',width:100, hidden:true},
		    	    {field:'empCode',title:'员工编号',width:100},
		    	    {field:'empName',title:'员工名称',width:100}
	    	    ]],
   	    pagination : params && params.pagination == false ? false : true,
   	    pageNumber : 1,
   	    pageSize : params && params.pageSize && !isNaN(params.pageSize) ? params.pageSize : undefined,
   	    pageList: params && params.pageList ? params.pageList : undefined,
   		idField : params && params.idField ? params.idField : 'empId',	
   		textField : params && params.textField ? params.textField : 'empName',
   		mode : params && params.mode=='local' ? 'local' : 'remote',
   		loadMsg : '数据加载中请稍后……',
   		width : params && params.width && !isNaN(params.width) ? params.width : undefined,
   		height : params && params.height && !isNaN(params.height) ? params.height : undefined,
   		panelHeight : params && params.panelHeight && !isNaN(params.panelHeight) ? params.panelHeight : undefined,
   		singleSelect : params && params.singleSelect==true ? true : false,
		fitColumns : true,
		onClick : params && params.onClick ? params.onClick : undefined,
		onLoadSuccess : params && params.onLoadSuccess ? params.onLoadSuccess : undefined,
		onBeforeLoad : params && params.onBeforeLoad ? params.onBeforeLoad : undefined,
		onLoadError : params && params.onLoadError ? params.onLoadError :undefined
	})
}

//机构组合表格
function nosOrgCombogrid(domId,params){
	if(!domId){
		console.error("domId不能为空！");
		return;
	}
	if (params && params.pageSize!=null && params.pageSize != undefined) {
		if(!isNaN(params.pageSize)){
			params.pageList=[params.pageSize,params.pageSize*2,params.pageSize*3,params.pageSize*4,params.pageSize*5,params.pageSize*10]
			params.pagination = true;
		}else{
			console.error("pageSize必须要为数字！");
		    return;
		}
	} 
	$("#"+domId).combogrid({
		url : params && params.url ? params.url : '../org/findNosOrgByPage.do' ,
		columns : params && params.columns ? params.columns : [[
					{field:'orgType',title:'机构类型',width:100},
		          	{field:'orgCode',title:'机构编号',width:100},
		    	    {field:'orgName',title:'机构名称',width:100}
	    	    ]],
   	    pagination : params && params.pagination == false ? false : true,
   	    pageNumber : 1,
   	    pageSize : params && params.pageSize && !isNaN(params.pageSize) ? params.pageSize : undefined,
   	    pageList: params && params.pageList ? params.pageList : undefined,
   		idField : params && params.idField ? params.idField : 'orgCode',	
   		textField : params && params.textField ? params.textField : 'orgName',
   		mode : params && params.mode=='local' ? 'local' : 'remote',
   		loadMsg : '数据加载中请稍后……',
   		width : params && params.width && !isNaN(params.width) ? params.width : undefined,
   		height : params && params.height && !isNaN(params.height) ? params.height : undefined,
   		panelHeight : params && params.panelHeight && !isNaN(params.panelHeight) ? params.panelHeight : undefined,
   		singleSelect : params && params.singleSelect==true ? true : false,
		fitColumns : true,
		onClick : params && params.onClick ? params.onClick : undefined,
		onLoadSuccess : params && params.onLoadSuccess ? params.onLoadSuccess : undefined,
		onBeforeLoad : params && params.onBeforeLoad ? params.onBeforeLoad : undefined,
		onLoadError : params && params.onLoadError ? params.onLoadError :undefined
	})
}

//地区组合表格
function areaCombogrid(domId,params){
	if(!domId){
		console.error("domId不能为空！");
		return;
	}
	if (params && params.pageSize!=null && params.pageSize != undefined) {
		if(!isNaN(params.pageSize)){
			params.pageList=[params.pageSize,params.pageSize*2,params.pageSize*3,params.pageSize*4,params.pageSize*5,params.pageSize*10]
			params.pagination = true;
		}else{
			console.error("pageSize必须要为数字！");
		    return;
		}
	} 
	$("#"+domId).combogrid({
		url : params && params.url ? params.url : '../area/findAreaByPage.do' ,
		columns : params && params.columns ? params.columns : [[
					{field:'areaType',title:'地区类型',width:100},
		          	{field:'areaCode',title:'地区编号',width:100},
		    	    {field:'areaName',title:'地区名称',width:100}
	    	    ]],
   	    pagination : params && params.pagination == false ? false : true,
   	    pageNumber : 1,
   	    pageSize : params && params.pageSize && !isNaN(params.pageSize) ? params.pageSize : undefined,
   	    pageList: params && params.pageList ? params.pageList : undefined,
   		idField : params && params.idField ? params.idField : 'areaCode',	
   		textField : params && params.textField ? params.textField : 'areaName',
   		mode : params && params.mode=='local' ? 'local' : 'remote',
   		loadMsg : '数据加载中请稍后……',
   		width : params && params.width && !isNaN(params.width) ? params.width : undefined,
   		height : params && params.height && !isNaN(params.height) ? params.height : undefined,
   		panelHeight : params && params.panelHeight && !isNaN(params.panelHeight) ? params.panelHeight : undefined,
   		singleSelect : params && params.singleSelect==true ? true : false,
		fitColumns : true,
		onClick : params && params.onClick ? params.onClick : undefined,
		onLoadSuccess : params && params.onLoadSuccess ? params.onLoadSuccess : undefined,
		onBeforeLoad : params && params.onBeforeLoad ? params.onBeforeLoad : undefined,
		onLoadError : params && params.onLoadError ? params.onLoadError :undefined
	})
}

//省市区联动（三个输入框）
function areaCascade(provinceId,cityId,regionId,params){
	if(!provinceId){
		console.error("provinceId不能为空");
		return;
	}
	$("#"+provinceId).combobox({
		width : params && !isNaN(params.width) ? params.width : undefined,
		height : params && !isNaN(params.height) ? params.height : undefined,
		panelWidth : params && !isNaN(params.panelWidth) ? params.panelWidth : undefined,
		panelHeight : params && !isNaN(params.panelHeight) ? params.panelHeight : undefined,
		valueField : params && params.valueField ? params.valueField : 'areaCode',
		textField : params && params.textField ? params.textField : 'areaName',
		url : params && params.url ? params.url : '../area/findListByAreaType.do',
		editable : params && params.editable == false ? false : true,
		onBeforeLoad:function(param){
			param.areaType=2;
		},
		onSelect : !cityId || cityId == null ? function(){} : function(record){
			console.log(record);
			if(regionId && regionId != null){
				$("#"+regionId).combobox('clear');
			}
			$("#"+cityId).combobox({
				valueField : params && params.valueField ? params.valueField : 'areaCode',
				textField : params && params.textField ? params.textField : 'areaName',
				url : params && params.url ? params.url : '../area/findListByAreaType.do',
				editable : params && params.editable == true ? true : false,
				onBeforeLoad:function(param){
					param.areaType=3;
					param.parentAreaCode=record.areaCode;
				},
				onSelect: !regionId || regionId == null ? function(){} : function(record){
					$("#"+regionId).combobox({
						valueField : params && params.valueField ? params.valueField : 'areaCode',
						textField : params && params.textField ? params.textField : 'areaName',
						url : params && params.url ? params.url : '../area/findListByAreaType.do',
						editable : params && params.editable == true ? true : false,
						onBeforeLoad:function(param){
							param.areaType=4;
							param.parentAreaCode=record.areaCode;
						},
					})
				}
			})
		}
	})
}

//省市区联动（标签页面板）
function areaCascadePanel(txtArea,sectionArea,tabArea,panelArea){
	if(!txtArea || !sectionArea || !tabArea || !panelArea ){
		console.error("txtArea,sectionArea,tabArea,panelArea不能为空！");
		return;
	}
	//文本框点击显示/隐藏切换
	$("#"+txtArea).click(function(){
		$("#"+sectionArea).toggle();
	})
	//点击其他地方隐藏sectionArea
	$("body").click(function(e){
		var target = $(e.target);
		if(!target.is($("#"+txtArea)) && !target.is($("#"+txtArea).find("*")) && !target.is($("#"+sectionArea)) && !target.is($("#"+sectionArea).find("*"))){
			$("#"+sectionArea).hide();
		}
	})
	//tab点击事件
	$("#"+tabArea).children().click(function(){
		//点击的当前tab深色
		$("#"+tabArea).children().css("background","white");
		$(this).css("background","#D3D3D3");
		//省显示省份面板
		if($(this).attr("id")==tabArea+"-province"){
			$("#"+panelArea).children().hide();
			$("#"+panelArea+"-province").show();
		//显示城市面板
		}else if($(this).attr("id")==tabArea+"-city"){
			$("#"+panelArea).children().hide();
			$("#"+panelArea+"-city").show();
		//显示地区面板
		}else if($(this).attr("id")==tabArea+"-region"){
			$("#"+panelArea).children().hide();
			$("#"+panelArea+"-region").show();
		}
		
	})
	//请求省份信息
	$.ajax({
		url:'../area/findListByAreaType.do',
		data:'areaType=2',
		success:function(result){
			$("#"+panelArea+"-province").children().children().remove();
			$.each(result,function(i,n){
				$("#"+panelArea+"-province").children().append("<li  id="+n.areaCode+" code="+n.areaCode+" style='padding:5px;cursor:pointer;list-style:none'>"+n.areaName+"</li>");
			})
		}
	})
	//省份点击事件
	$("#"+panelArea+"-province").delegate('li','click',function(){
		//把点击的一条高亮，其他白色
		$("#"+panelArea+"-province").children().children("li").css("background","white");
		$(this).css("background","#ccc");
		//把省份信息放到文本框中
		$("#"+txtArea+"-province").attr("code",$(this).attr("code"));
		$("#"+txtArea+"-province").html($(this).text());
		
		//城市面板存在
		if($("#"+txtArea+"-city").length>0){
			//清除城市文本框的值
			$("#"+txtArea+"-city").html("");
			$("#"+txtArea+"-city").attr("code",'');
			//城市tab加深
			$("#"+tabArea).children().css("background","white");
			$("#"+tabArea+"-city").css("background","#D3D3D3");	
			//展示城市面板
			$("#"+panelArea).children().hide();
			$("#"+panelArea+"-city").show();	
			//请求城市信息
			$.ajax({
				url:'../area/findListByAreaType.do',
				data:'areaType=3&&parentAreaCode='+$(this).attr('id'),
				success:function(result){
					$("#"+panelArea+"-city").children().children().remove();
					$.each(result,function(i,n){
						$("#"+panelArea+"-city").children().append("<li  id="+n.areaCode+" code="+n.areaCode+" style='padding:5px;cursor:pointer;list-style:none'>"+n.areaName+"</li>");
					})
				}
			});
			//城市点击事件
			$("#"+panelArea+"-city").delegate('li','click',function(){
				//把点击的一条高亮，其他白色
				$("#"+panelArea+"-city").children().children("li").css("background","white");
				$(this).css("background","#CCC");
				//把城市信息放到文本框中
				$("#"+txtArea+"-city").attr("code",$(this).attr("code"));
				$("#"+txtArea+"-city").html($(this).text());
				if($("#"+txtArea+"-region").length>0){
					//清除区域文本框的值
					$("#"+txtArea+"-region").html("");
					$("#"+txtArea+"-region").attr("code",'');
					//地区tab深色
					$("#"+tabArea).children().css("background","white");
					$("#"+tabArea+"-region").css("background","#D3D3D3");
					//显示区域面板
					$("#"+panelArea).children().hide();
					$("#"+panelArea+"-region").show();
					//请求区域信息
					$.ajax({
						url:'../area/findListByAreaType.do',
						data:'areaType=4&&parentAreaCode='+$(this).attr('id'),
						success:function(result){
							$("#"+panelArea+"-region").children().children().remove();
							$.each(result,function(i,n){
								$("#"+panelArea+"-region").children().append("<li  id="+n.areaCode+" code="+n.areaCode+" style='padding:5px;cursor:pointer;list-style:none'>"+n.areaName+"</li>");
							})
						}
					})
					//区域点击事件
					$("#"+panelArea+"-region").delegate('li','click',function(){
						$("#"+txtArea+"-region").attr("code",$(this).attr("code"));
						$("#"+txtArea+"-region").html($(this).text());
						$("#"+panelArea+"-region").children().children("li").css("background","white");
						$(this).css("background","#CCC");
						$("#"+sectionArea).hide();
					})
				}
			})
		}
		
		//区域面板存在
		if($("#"+txtArea+"-region").length>0){
			//清除区域信息的值
			$("#"+txtArea+"-region").html("");
			$("#"+txtArea+"-region").attr("code",'');
		}
	})
}

//地区组合树
function areaCombotree(domId,params){
	if(!domId){
		console.error("domId不能为空");
		return;
	}
	$("#"+domId).combotree({
		url: params && params.url ? params.url : '../area/findAreaTree.do',
		multiple : true,
		onBeforeLoad : function(node,param){
			if(node!=null){
	    		param.areaType=node.areaType;
	        	param.parentAreaCode=node.areaCode;
	    	}
		},
		cascadeCheck:false
	})
}

//地区组合树形表格
function areaCombotreegrid(domId,params){
	if(!domId){
		console.error("domId不能为空");
		return;
	}
	$("#"+domId).combotreegrid({
		url:params && params.url ? params.url : '../area/findAreaTree.do',
		idField:'id',
        treeField:'text',
        fitColumns : true,
        columns:[[
            {field:'text',title:'地区名称',width:200},
            {field:'id',title:'地区编码',width:100}
        ]],
        onBeforeLoad:function(node,param){
        	if(node!=null){
        		param.areaType=node.areaType;
	        	param.parentAreaCode=node.areaCode;
        	}
        }
		
	})
}

//扩展Array，判断元素是否在数组中
/*Array.prototype.inArray=function(e){
	for(i=0;i<this.length;i++){
	 if(this[i] == e)
	 return true;
	}
	 return false; 
}*/

//校验给出的权限码是否存在
function dealPermission(operateArray){
	var flag = true;
	$.each(operateArray,function(index,value,array){
		//add by xj crossDomain
		 if($.inArray(value,portal_global_permissionCode) != -1){
			 flag=false;
			 return;
		 }
		 //add by xj crossDomain
	 })
	 return flag;
 }

/**
 * 初始化数据字典
 */
var DictDatas = {};
function initDictDatas(typeClassCodes,params){
	if(typeClassCodes!=null && typeClassCodes!=undefined){
		$.ajax({
			type: "post",
			url : params && params.url ? params.url : '../dictData/findByTypesCodes.do',
			async : params && params.async ? params.async : false,
			data: {"typeClassCodes":typeClassCodes},
			success : function(data){
				if(data!=null && data!=undefined){
					if(data.length>0){
						for(var i=0;i<data.length;i++){
							DictDatas[data[i].dictType]=data[i].dictDataList;
						}
					}
				}
			}
		});
	}
}

/**
 * 根据type显示Name
 * @param typeClassCodes
 * @param typeCode
 * @returns
 */
function getTypeNameByCode(typeClassCodes,typeCode,showTip){
	if(DictDatas != null && DictDatas != undefined && DictDatas[typeClassCodes].length != 0){
		for(var i=0;i<DictDatas[typeClassCodes].length;i++){
			if(DictDatas[typeClassCodes][i].typeCode == typeCode){
				if(showTip!=null && showTip!=undefined){
					return "<a title='" + DictDatas[typeClassCodes][i].typeName + "'>" + DictDatas[typeClassCodes][i].typeName + "</a>"
				}
				return DictDatas[typeClassCodes][i].typeName;
			}
		}
	}
	return typeCode;
}

/**
 * 格式化权限级别
 * @param value
 * @param showTip
 * @returns
 */
function pLevelFormat(value,showTip){
	if(value && value!=null){
		var levelName;
		var levels = value.split(",");
		for(var i=0 ; i<levels.length ; i++){
			levelName=(levelName == undefined ? "" : levelName+",")+getTypeNameByCode("ROLE_LEVEL",levels[i]);
		}
		if(showTip!=null && showTip!=undefined){
			return "<a title='" + levelName + "'>" + levelName + "</a>"
		}
		return levelName;
	}else{
		return "";
	}
	
}

/**
 *数据字典下拉列表
 *@domId input id,如：<input id="datadic" name="datadic" value="" data-options="prompt:'请选择查询条件'"/>
 *@typeClassCode 请求参数
 */
function uceDictCombobox(domId,typeClassCode,params){
	if(!domId || !typeClassCode){
		console.error("domId,typeClassCode不能为空！");
		return;
	};
	$('#'+domId).uceCombobox({
		headerValue : params && params.headerValue == false ? undefined : '',
	    valueField:'typeCode',
	    textField:'typeName',
	    data:DictDatas[typeClassCode].slice(0),
	    value : params && params.value ? params.value : undefined,
	    limitToList : params && params.limitToList==false ? false : true,
	    editable: params && params.editable == true ? true : false,
		panelHeight : params && !isNaN(params.panelHeight) ? params.panelHeight : 200,
		multiple : params && params.multiple ? true : false,
		formatter : params && params.formatter ? params.formatter : undefined,
		loadFilter : params && params.loadFilter ? params.loadFilter : undefined,
		onChange : params && params.onChange ? params.onChange : undefined
	});
}


/**
 * loading框
 * uceLoading.show(text,type,time)
 * @param text 消息内容(可选)
 * @param type 消息类型,成功，失败，loading三种状态，可选，默认loading
 * @param time 显示时长，默认一直显示(可选参数)
 *
 *uceLoading.close(time)
 *@param time 延迟多长时间关闭，默认为立即关闭（可选参数）
 */
function uceLoading(){
	var element = document.createElement('div');
	element.addEventListener('touchmove',function(e){
		e.preventDefault();
	})
	
	uceLoading.show = function(text,type,time){
		element.setAttribute('style','position: absolute;top:0;width: 100%;height: 100%;background: rgba(0, 0, 0, 0.25);z-index:9999;')
		if(type == "success"){
			element.innerHTML = "<div class='uce-loading-me'><p class='iconfont uce-success-circle uce-loading-success'></p><p>"+(text?text:'成功')+"</p></div>"
		}else if(type == "error"){
			element.innerHTML = "<div class='uce-loading-me'><p class='iconfont uce-error-circle uce-loading-error'></p><p>"+(text?text:'失败')+"</p></div>"
		}else{
			element.innerHTML = "<div class='uce-loading-me'><p class='iconfont uce-loading-line uce-spinner'></p><p>"+(text?text:'加载中...')+"</p></div>"
		}
		var uceLoading = [element];
		document.body.appendChild(element);
		if(time){
			setTimeout(function(){
				element.setAttribute('style', 'opacity:0;display:none');
				return uceLoading;
			},time)
		}
		return uceLoading;
	}
	uceLoading.close = function(time){
		if(time){
			setTimeout(function(){
				element.setAttribute('style', 'opacity:0;display:none');
			},time)
		}else{
			element.setAttribute('style', 'opacity:0;display:none');
		}
		return uceLoading;
	}
	return uceLoading;
}
var uceLoading = uceLoading();

//用法
//ucedatetimebox('dd','time')日期类型为YYYY/MM/DD hh:mm:ss格式的类型
//ucedatetimebox('dd')日期类型为YYYY/MM/DD 格式的类型
function ucedatetimebox(id, time) {
  if (time) {
      $('#' + id).datetimebox({
          value: '3/4/2010',
          required: true,
          showSeconds: true,
          inputEvents: {
              keydown: down(19)
          },
      });
  } else {
      $('#' + id).datebox({
          value: '3/4/2010',
          required: true,
          inputEvents: {
              keydown: down(10)
          },
      });
  }

  function down(num) {
      return function (e) {
          var value = $(this).val();
          e = e || event;
          var keyCode = e.keyCode || e.which,
                  char = String.fromCharCode(keyCode);
          var reg = /^[0-9]*$/;
          if ((reg.test(char) || keyCode == 8 || (keyCode >= 97 && keyCode <= 105))) {
              if (keyCode == 8) {
                  return true;
              }
              if ((value.length < num)) {
                  return true;
              } else {
                  return false;
              }
          } else {
              return false;
          }
      }
  }
}

//机构表格显示
function treeDatagrid(treeId, url, params) {
	/*2017-12-05 update by xj*/
	var hideIndexs = new Array();
	var clickRowFlag = false;
	/*2017-12-05 update by xj*/
	$('#' + treeId + '_select_button').bind('click', function() {
		$('#' + treeId + '_datagrid').datagrid('reload'); // 重新加载待选机构
		var $v_j = localStorage.getItem(treeId + 'Values');
		var $values = JSON.parse($v_j);
		$('#' + treeId + '_selected').datagrid('loadData', {
			total: $values,
			rows: $values
		});
		var length = ($values == null || $values == undefined) ? 0 : $values.length;
		$('#' + treeId + '_title').html('共选中<span style="color:red;">' + length + '</span>条记录');
		$('#' + treeId + '_dialog').dialog('open');
	});
	$('#' + treeId + '_dialog').dialog({
		title: '<span style="margin-left:-10px;">选择操作</span>',
		width: 602,
		height: 393,
		closed: false,
		cache: false,
		modal: true
	});
	$('#' + treeId + '_dialog').dialog('close');
	var regx = "?orgFlag=" + params.orgFlag + "&orgType=" + params.orgType + "&orgStatus=" + params.orgStatus + "&isSelf=" + params.isSelf + "&type=10";
	$('#' + treeId + '_datagrid').datagrid({
		url: url,
		/*2017-12-11 update by xj*/
		title: '<span style="margin-left:-10px;">待选择部门</span>',
		/*2017-12-11 update by xj*/
		width: '100%',
		height: '100%',
		toolbar: '#tool',
		/*2017-12-11 update by xj*/
		checkOnSelect: true,
		selectOnCheck: true,
		/*2017-12-11 update by xj*/
		pagination: params.pagination,
		//iconCls:'icon-save',
		columns: [
			[{
				field: 'ck',
				checkbox: 'true'
			}, {
				field: 'id',
				width: '1',
				hidden: 'true'
			}, {
				field: 'iconField',
				width: '16',
				title: '<span style="margin-left: -10px;">全选</span>',
				formatter: function(val, row, index) {
					return '<span class="tree-icon tree-file"></span>';
				}
			}, {
				field: 'text',
				width: '145'
			}, {
				field: 'orgType',
				width: '1',
				hidden: 'true'
			}, {
				field: '_operate',
				width: '35',
				align: 'center',
				formatter: function(val, row, index) {
					if(row.parentId == null || row.parentId == undefined || row.parentId == '') {
						return '<a href="javascript:void(0)" id="' + row.id + '-Child" style="color:#c5c5c5;cursor:not-allowed;">下级</a>';
					} else {
						return '<a href="javascript:void(0)" id="' + row.id + '-Child">下级</a>';
					}
				}
			}]
		],
		onLoadSuccess: function(result) {},
		onCheckAll: function(rows) {
			if(params.multiple) {
				appendSelect(rows);
			} else {
				$('#' + treeId + '_datagrid').datagrid('uncheckAll');
			}
		},
		onCheck: function(index, row) {
			if(!params.multiple) {
				var s_0 = $('#' + treeId + '_selected').datalist('getRows');
				if(s_0 != null && s_0 != undefined && s_0.length != 0) {
					for(var i = 0; i < s_0.length; i++) {
						$('#' + treeId + '_selected').datagrid('deleteRow', i);
					}
					var s_1 = $('#' + treeId + '_selected').datalist('getRows');
					$('#' + treeId + '_title').html('共选中<span style="color:red;">' + s_1.length + '</span>条记录');
				}
				var d_0 = $('#' + treeId + '_datagrid').datalist('getRows');
				if(d_0 != null && d_0 != undefined && d_0.length != 0) {
					for(var i = 0; i < d_0.length; i++) {
						var d_index = $('#' + treeId + '_datagrid').datagrid('getRowIndex', d_0[i]);
						if(index != d_index) {
							$('#' + treeId + '_datagrid').datagrid('uncheckRow', d_index);
						}
					}
				}
			}
			var a = new Array();
			a.push(row);
			appendSelect(a);
		},
		onClickCell: function(index, field, value) {
			if(field == '_operate') {
				var rows = $('#' + treeId + '_datagrid').datagrid('getRows');
				var row = rows[index];
				var as = $('.' + treeId + '_navigation a:last');
				var $id = as[0].id.split('_')[0];
				if(row.id == $id || row.parentId == null) {
					$('#' + $id + '-Child').attr("disabled", true);
					var c = $('#' + $id + '-Child')[0];
					if(c != undefined && c != null) {
						c.style.color = '#c5c5c5';
						c.style.cursor = 'not-allowed';
					}
				} else {
					sessionStorage.setItem(treeId + "parentId", row.id);
					findOrgChild(row.id, row.orgType, row.text, row.parentId);
				}
				console.log(row.id + '-' + row.orgType);

				/*2017-12-05 update by xj*/
				clickRowFlag = false;
				/*2017-12-05 update by xj*/
			} else {
				/*2017-12-05 update by xj*/
				clickRowFlag = true;
				/*2017-12-05 update by xj*/
			}
		},
		/*2017-12-05 update by xj*/
		onBeforeSelect: function() {
			return clickRowFlag;
		},
		/*2017-12-05 update by xj*/
		onBeforeLoad: function(param) {
			var as = $('.' + treeId + '_navigation a:last');
			var name = $('#' + treeId + '_name').textbox('getValue');
			param.orgFlag = params.orgFlag;
			param.orgType = params.orgType;
			param.orgStatus = params.orgStatus;
			param.isSelf = params.isSelf;
			var $opt = $('#' + treeId + '_datagrid').datagrid('options');
			var page = $opt.pageNumber;
			var rows = $opt.pageSize;
			param.type = as[0].id.split('_')[1];
			param.page = page;
			param.rows = rows;
			param.id = sessionStorage.getItem(treeId + "parentId");
			if(param.id == null || param.id == '' || param.id == undefined) {
				param.name = name;
			}
		}
	});
	//设置分页控件 
	var p = $('#' + treeId + '_datagrid').datagrid('getPager');
	$(p).pagination({
		pageList: params.pageList,
		pageSize: params.pageSize,
		beforePageText: '第',
		afterPageText: '页 共{pages}页',
		showPageList: false,
		showRefresh: true,
		displayMsg: ''
	});
	$('#' + treeId + '_selected').datagrid({
		title: '<span style="margin-left:-10px;">已选择部门</span>',
		width: '100%',
		height: '100%',
		toolbar: '#' + treeId + '_tool_checked',
		checkOnSelect: true,
		selectOnCheck: true,
		//iconCls:'icon-save',
		columns: [
			[{
				field: 'ck',
				checkbox: 'true'
			}, {
				field: 'iconField',
				width: '20',
				title: '<span style="top:15px;margin-left: -10px;color:#ff9372;" id="' + treeId + '_delete">&nbsp;删除</span>',
				formatter: function(val, row, index) {
					return '<span class="tree-icon tree-file"></span>';
				}
			}, {
				field: 'id',
				hidden: 'true'
			}, {
				title: '<span id="' + treeId + '_title" style="margin-left:-70px;">共选中<span style="color:red;">0</span>条记录</span>',
				field: 'text',
				width: '160',

			}, {
				field: 'parentId',
				hidden: 'true'
			}, {
				field: '_operate',
				width: '15',
				align: 'center',
				formatter: function(val, row, index) {
					return '<a href="#">X</a>';
				}
			}]
		],
		onClickCell: function(index, field, value) {
			if(field == '_operate') {
				/*2017-12-05 update by xj*/
				/*var rows = $('#' + treeId + '_selected').datagrid('getRows');
				var row = rows[index];
				//console.log(row);
				deleteRowIndex(row.id);*/
				$('#' + treeId + '_selected').datagrid('deleteRow', index);
				/*2017-12-05 update by xj*/
			}
		},
		rowStyler: function(index, row) {
			if(hideIndexs.indexOf(index) >= 0) {
				return 'background:red; display:none';
			}
		}
	});
	// 查询按钮操作
	/*2017-12-05 update by xj*/
	/*$('.'+treeId+'_search'+' .searchbox-button').bind('click',function(){
		sessionStorage.removeItem(treeId+"parentId");
		var as = $('.'+treeId+'_navigation a:first');
		var id = as[0].id.split('_')[0];
		var sub = $('.'+treeId+'_navigation a');
		var f = 0;
		for(var i = 0; i < sub.length; i++){
			var $sub_id = sub[i].id.split('_')[0];
			if($sub_id != id){
				sub[i].remove();	
			}
		}
		var name = $('#'+treeId+'_name').textbox('getValue');
		if(name == '' || name == null || name == undefined)return;
		var $opt = $('#'+treeId+'_datagrid').datagrid('options');
		var rows = $opt.pageSize;
		loadData('','',name,'','',1,rows);	  
	});*/

	$('#' + treeId + '_name').searchbox({
		searcher: function(value, name) {
			sessionStorage.removeItem(treeId + "parentId");
			var as = $('.' + treeId + '_navigation a:first');
			var id = as[0].id.split('_')[0];
			var sub = $('.' + treeId + '_navigation a');
			var f = 0;
			for(var i = 0; i < sub.length; i++) {
				var $sub_id = sub[i].id.split('_')[0];
				if($sub_id != id) {
					sub[i].remove();
				}
			}
			var name = $('#' + treeId + '_name').textbox('getValue');
			if(value == '' || value == null || value == undefined) return;
			var $opt = $('#' + treeId + '_datagrid').datagrid('options');
			var rows = $opt.pageSize;
			loadData('', '', value, '', '', 1, rows);
		}
	});

	$('#' + treeId + '_name_checked').searchbox({
		searcher: function(value, name) {
			hideIndexs.length = 0;
			if(value == '请输入查询内容') {
				value = '';
			}
			//结束datagrid的编辑.
			//endEdit();
			var rows = $('#' + treeId + '_selected').datagrid('getRows');

			var cols = $('#' + treeId + '_selected').datagrid('options').columns[0];

			for(var i = rows.length - 1; i >= 0; i--) {
				var row = rows[i];
				var isMatch = false;
				for(var j = 0; j < cols.length; j++) {

					var pValue = row[cols[j].field];
					if(pValue == undefined) {
						continue;
					}
					if(pValue.toString().indexOf(value) >= 0) {
						isMatch = true;
						break;
					}
				}
				if(!isMatch)
					hideIndexs.push(i);　　　　　　　　　 //刷新行,否则不能看到效果.
				$('#' + treeId + '_selected').datagrid('refreshRow', i);
			}

		}
	});
	/*2017-12-05 update by xj*/

	// 删除操作
	$('#' + treeId + '_delete').bind('click', function() {
		var rows = $('#' + treeId + '_selected').datagrid('getChecked');
		if(rows != null && rows != undefined && rows != "") {
			for(var i = 0; i < rows.length; i++) {
				deleteRowIndex(rows[i].id);
			}
		}
		$('#' + treeId + '_selected').datagrid('uncheckAll');
	});
	// 格式化要显示的数据
	$('#' + treeId + '_datagrid').datagrid("options").view.onBeforeRender = function(target, rows) {
		$.each(rows, function(index, row) {
			row.text = formatText(row.text, row.parentName);
		});
	};
	// 单击下一级，切换导航
	function findOrgChild(id, orgType, text, parentId) {
		var issue = $('#' + id + '-Child').attr('disabled');
		if(issue == 'disabled') return;
		var $opt = $('#' + treeId + '_datagrid').datagrid('options');
		var rows = $opt.pageSize;
		loadData(id, orgType, '', text, parentId, 1, rows);
	}
	// 格式化细节
	function formatText(text, parentName) {
		if(parentName == '' || parentName == null || parentName == undefined) {
			return text;
		} else {
			var val = parentName + '-' + text;
			return val;
		}
	}
	// ajax加载数据集(查询，导航，下级共用此功能)
	function loadData(id, orgType, name, text, parentId, page, rows) {
		var $param = "?orgFlag=" + params.orgFlag + "&orgType=" + params.orgType + "&orgStatus=" + params.orgStatus + "&isSelf=" + params.isSelf;
		$.ajax({
			url: url + $param + "&id=" + id + "&name=" + name + "&page=" + page + "&rows=" + rows + "&type=" + orgType,
			dataType: 'json',
			type: 'post',
			success: function(data) {
				// 如果结果集为空,则把该条记录下级修改为禁用状态
				if(null == data || data == undefined || data.length == 0) {
					if(id == '') {
						$('#' + treeId + '_datagrid').datagrid('loadData', {
							total: 0,
							rows: []
						});
					} else {
						$('#' + id + '-Child').attr("disabled", true);
						var c = $('#' + id + '-Child')[0];
						if(c != undefined && c != null) {
							c.style.color = '#c5c5c5';
							c.style.cursor = 'not-allowed';
						}
					}
				} else {
					var result;
					// 如果是营运机构,则分页失效
					if(name != null && name != undefined && name != '' && params.orgFlag == 'NOS') {
						result = {
							'total': 1,
							'rows': data.rows
						};
					} else {
						result = data;
					}
					$('#' + treeId + '_datagrid').datagrid('loadData', result);
					if(text != null && text != '' && text != undefined) {
						navigatChange(id, orgType, text, parentId);
					}
				}
			}
		});
	}
	// 选中添加到右边组件
	function appendSelect(rows) {
		var s_0 = $('#' + treeId + '_selected').datalist('getRows');
		var flag = true;
		if(rows != null && rows != undefined && rows.length != 0) {
			for(var j = 0; j < rows.length; j++) {
				var flag = false;
				if(s_0 != null && s_0 != undefined && s_0.length != 0) {
					for(var i = 0; i < s_0.length; i++) {
						if(rows[j].id == s_0[i].id) {
							flag = true;
							break;
						}
					}
				}
				if(!flag) {
					$('#' + treeId + '_selected').datalist('appendRow', rows[j]);
				}
			}
		}
		var s_0 = $('#' + treeId + '_selected').datalist('getRows');
		$('#' + treeId + '_title').html('共选中<span style="color:red;">' + s_0.length + '</span>条记录');
	}
	// 导航操作
	$('.' + treeId + '_navigation').on('click', 'a', function() {
		var $id = this.id.split('_')[0];
		var $orgType = this.id.split('_')[1];
		sessionStorage.setItem(treeId + "parentId", $id);
		navigatLoad($id, $orgType); //此处的this是触发点击事件的a
	});
	// 导航切换	
	function navigatChange(id, orgType, text, parentId) {
		var val = text.split('-')[1];
		var $id = id + "_" + orgType;
		var html = '<a href="#" id=\'' + $id + '\'>' + val + '></a>';
		var as = $('.' + treeId + '_navigation a:last');
		if(as[0].id.split('_')[0] != '' && as[0].id.split('_')[0] != null && as[0].id.split('_')[0] != undefined && as[0].id.split('_')[0] != parentId) {
			as.remove();
		}
		if(as[0].id.split('_')[0] != id) {
			$('.' + treeId + '_navigation').append(html);
		}
	}
	// 单击导航加载数据
	function navigatLoad(id, orgType) {
		var sub = $('.' + treeId + '_navigation a');
		var f = 0;
		for(var i = 0; i < sub.length; i++) {
			var $sub_id = sub[i].id.split('_')[0];
			if($sub_id == id) {
				f = i + 1;
			}
			if(f != 0 && (i + 1) < sub.length) {
				sub[i + 1].remove();
			}
		}
		var $opt = $('#' + treeId + '_datagrid').datagrid('options');
		var rows = $opt.pageSize;
		var p = $('#' + treeId + '_datagrid').datagrid('getPager');
		p.pagination('select', 1);
		loadData(id, orgType, '', '', '', 1, rows);
	}
	// 右边删除操作
	function deleteRowIndex(id) {
		debugger
		var s_0 = $('#' + treeId + '_selected').datalist('getRows');
		if(s_0 != null && s_0 != undefined && s_0.length != 0) {
			for(var i = 0; i < s_0.length; i++) {
				if(id == s_0[i].id) {
					$('#' + treeId + '_selected').datagrid('deleteRow', i);
					break;
				}
			}
			var s_1 = $('#' + treeId + '_selected').datalist('getRows');
			$('#' + treeId + '_title').html('共选中<span style="color:red;">' + s_1.length + '</span>条记录');
		}
	}

	// 设置选中数据到文本框
	$('#' + treeId + '_checked').bind('click', function() {
		var s_0 = $('#' + treeId + '_selected').datagrid('getRows');
		var $text = '';
		if(s_0 != null && s_0 != undefined && s_0.length > 0) {
			var $values = new Array();
			for(var i = 0; i < s_0.length; i++) {
				var text = s_0[i].text;
				if(text.indexOf('-') != -1) {
					text = text.split('-')[1];
				}
				$values.push({
					id: s_0[i].id,
					text: text,
					orgType: s_0[i].orgType,
					parentId: s_0[i].parentId
				});
				$text += text + ",";
			}
			var $v = JSON.stringify($values);
			localStorage.setItem(treeId + 'Values', $v);
		} else {
			localStorage.removeItem(treeId + 'Values');
		}
		$('#' + treeId + '_textGrid').textbox('setValue', $text.substring(0, $text.length - 1));
		$('#' + treeId + '_name').textbox('clear');
		sessionStorage.removeItem(treeId + "parentId");
		refreshPlugin();
		$('#' + treeId + '_dialog').dialog('close');
	});
	// 取消按钮操作
	$('#' + treeId + '_cannel').bind('click', function() {
		clearChecked();
		refreshPlugin();
		$('#' + treeId + '_dialog').dialog('close');
	});
	// 清除已选中记录操作
	function clearChecked() {
		$('#' + treeId + '_selected').datagrid('loadData', {
			total: 0,
			rows: []
		});
		$('#' + treeId + '_name').textbox('clear');
		sessionStorage.removeItem(treeId + "parentId");
		$('#' + treeId + '_title').html('共选中<span style="color:red;">0</span>条记录');
	}
	$('a.panel-tool-close').bind('click', function() {
		refreshPlugin();
	});

	function refreshPlugin() {
		var as = $('.' + treeId + '_navigation a:first').click();
	}
}
// 获取机构表格选中数据
function getCheckValue(id) {
	var $v_j = localStorage.getItem(id + 'Values');
	var $values = JSON.parse($v_j);
	localStorage.removeItem(id + 'Values');
	$('#' + id + '_name').textbox('clear');
	$('#' + id + '_selected').datagrid('loadData', {
		total: 0,
		rows: []
	});
	$('#' + id + '_title').html('共选中<span style="color:red;">0</span>条记录');
	return $values;
}
// 设置机构表格选中数据
function setCheckValue(id, values) {
	var $text = '';
	if(values != undefined && values != null) {
		var $v = JSON.stringify(values);
		localStorage.setItem(id + 'Values', $v);
		for(var i = 0; i < values.length; i++) {
			$text += values[i].text + ',';
			if(values[i].parentName != null && values[i].parentName != undefined && values[i].parentName != '') {
				values[i].text = values[i].parentName + "-" + values[i].text;
			}
			$('#' + id + '_selected').datalist('appendRow', values[i]);
		}
		$('#' + id + '_textGrid').textbox('setValue', $text.substring(0, $text.length - 1));
		$('#selected_title').html('共选中<span style="color:red;">' + values.length + '</span>条记录');
	}
}
//用户组织组合ztree
function userOrgComboZTree(treeId,orgParams,treeParams){
	if(!treeId){
		console.error("参数校验未通过！");
		return;
	}
	var zTreeObj;
	var zTreeSetting;
	zTreeSetting = 
		{
			data:{simpleData:{enable:true,idKey:'i',pIdKey: 'p'},key:{title:'i',name:'t',checked:'c'}},
			callback:{onCheck: zTreeOnCheck,onClick:zTreeOnClick},
			check:{enable: true,chkboxType: treeParams && treeParams.chkboxType ? treeParams.chkboxType : { "Y": "ps", "N": "s" }}
		};
	var zTreeNodes;
	if(treeParams && treeParams.zTreeNodes){
		zTreeNodes=treeParams.zTreeNodes;
	}
	var clickNode;
	if(zTreeNodes && zTreeNodes != null){
		initZTree();
	}else{
		//请求树的数据
		var url;
		if(orgParams.orgFlag == 'NOS'){
			url='../org/findNosUserOrgTree.do';
		}else if(orgParams.orgFlag == 'CMS'){
			url='../org/findCmsUserOrgTree.do';
		}
		$.ajax({
			url:url,
			data:{empCode:orgParams.empCode},
			success:function(result){
				zTreeNodes=result;
				//子机构树
				initZTree();
			}
		})
	}
	//点击事件
	function zTreeOnClick(e,treeId,treeNode,clickFlag){
		var nodes = zTreeObj.getNodesByParam('nocheck',false,treeNode);
		for(var i = 0 ; i<nodes.length;i++){
			zTreeObj.checkNode(nodes[i]);
		}
		zTreeOnCheck(e, treeId, treeNode);
	}
	
	//选中事件
	function zTreeOnCheck(e, treeId, treeNode){
		$("#"+treeId+"~input").remove(":hidden");
		clickNode=treeNode;
		var checkedNodes = zTreeObj.getCheckedNodes(true);
		var text = "";
		var value = "";
		for(var i=0;i<checkedNodes.length;i++){
			value += checkedNodes[i].i+",";
			text += checkedNodes[i].t + ",";
		}
		if (text.length > 0 ) text = text.substring(0, text.length-1);
		if (value.length > 0 ) value = value.substring(0, value.length-1);
		$("#"+treeId).after("<input type='hidden' name='zTreeOrgCode' value="+value+">");
		$("#"+treeId+"Text").textbox('setValue',text);
	}
	//显示下拉树
	function showMenu(){
		$("#"+treeId+"Panel").slideDown("fast");    
	}
	//隐藏下拉树
	function hideMenu() {
		$("#"+treeId+"Panel").fadeOut("fast");
	}
	//给textbox加下拉箭头并赋点击事件
	$("#"+treeId+"Text").textbox({
		icons:[{iconCls:'combo-arrow',
			handler: function(e){
					 if($("#"+treeId+"Panel").css('display') == 'none'){
							showMenu();
						}
						else{
							hideMenu();
						}
				}}],
		editable:false
	})
	//键盘事件
	$("input",$("#"+treeId+"Text").next("span")).keyup(function(){
		clickNode=undefined;
		showMenu(); 
	})
	//输入框点击事件
	$("input",$("#"+treeId+"Text").next("span")).click(function(){
		 if($(this).attr("readonly") != "readonly" && $("#"+treeId+"Panel").css('display') == 'none'){
			showMenu();
		 }else{
			hideMenu();
		 }
	})
	//点击Section以外的其他地方隐藏panel
	$("body").bind("mousedown", 
		function(event){
			if (!(event.target.id == treeId+"Box" || $(event.target).parents("#"+treeId+"Box").length>0)) {
				hideMenu();
			}
	 });
	//初始化树，默认展开第一级
	function initZTree(){
		zTreeObj = $.fn.zTree.init($("#"+treeId), zTreeSetting, zTreeNodes);
		var zTreeRoots = zTreeObj.getNodesByFilter(function (node) { return node.level == 0 });
		for(var i=0;i<zTreeRoots.length;i++){
			zTreeObj.expandNode(zTreeRoots[i],true,false,false);
		}
	}
}
//选中节点
function userOrgZTreeSetValues(treeId,ids){
	var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
	for(var i=0;i<ids.length;i++){
		var node = zTreeObj.getNodeByParam('i',ids[i]);
		if(node && node != null){
			node.c=true;
			zTreeObj.updateNode(node);
		}
	}
	var text = "";
	var value = "";
	var checkedNodes = zTreeObj.getCheckedNodes(true);
	for(var i=0;i<checkedNodes.length;i++){
		value += checkedNodes[i].i+",";
		text += checkedNodes[i].t + ",";
	}
	if (text.length > 0 ) text = text.substring(0, text.length-1);
	if (value.length > 0 ) value = value.substring(0, value.length-1);
	$("#"+treeId).after("<input type='hidden' name='zTreeOrgCode' value="+value+">");
	$("#"+treeId+"Text").textbox('setValue',text);
}

//初始化用户组织zTree
function initUserOrgZTreeNodes(orgParams,zTree){
	var url;
	if(orgParams.orgFlag == 'NOS'){
		url='../org/findNosUserOrgTree.do';
	}else if(orgParams.orgFlag == 'CMS'){
		url='../org/findCmsUserOrgTree.do';
	}
	$.ajax({
		url:url,
		data:{empCode:orgParams.empCode},
		success:function(result){
			zTree.nodes=result;
		}
	})
}

var zTreeSetValues; 
var zTreeClearValues;
//机构组合树
function orgComboZTree(treeId,orgParams,treeParams){
	if(!treeId){
		console.error("参数校验未通过！");
		return;
	}
	var searchNodes=[];
	var searchIndex=0;
	var searchValue="";
	var zTreeObj;
	var zTreeSetting = treeParams && treeParams.selectType=='single' ? 
		{
			data:{key:{title:'id',name:'name'}},
			callback:{onClick: zTreeOnClick},
			view:{selectedMulti: false}
		} :
		{
			data:{key:{title:'id',name:'name'}},
			callback:{onCheck: zTreeOnCheck},
			check:{enable: true,chkboxType: treeParams && treeParams.chkboxType ? treeParams.chkboxType : { "Y": "ps", "N": "s" }}
		};
	var zTreeNodes;
	if(treeParams && treeParams.zTreeNodes){
		zTreeNodes=treeParams.zTreeNodes;
	}
	var clickNode;
	if(zTreeNodes && zTreeNodes != null){
		initZTree();
	}else{
		//请求树的数据
		var url=orgParams && orgParams.url ? orgParams.url : '../org/findChildSyncOrgTree.do';
		if(orgParams && orgParams.orgTypeList && Object.prototype.toString.call(orgParams.orgTypeList)=='[object Array]'){
			for(var i =0 ;i<orgParams.orgTypeList.length ; i++){
				var regx = /\?/;
				regx.test(url) ? url=url+"&orgTypeList="+orgParams.orgTypeList[i] : url = url+"?orgTypeList="+orgParams.orgTypeList[i];
			}
		}
		if(orgParams && orgParams.orgStatusList && Object.prototype.toString.call(orgParams.orgStatusList)=='[object Array]'){
			for(var i =0 ;i<orgParams.orgStatusList.length ; i++){
				var regx = /\?/;
				regx.test(url) ? url=url+"&orgStatusList="+orgParams.orgStatusList[i] : url = url+"?orgStatusList="+orgParams.orgStatusList[i];
			}
		}
		var requestParams = {orgTreeType:orgParams.orgTreeType};
		if(orgParams && orgParams.baseOrgCode){
			requestParams.baseOrgCode = orgParams.baseOrgCode;
		}
		$.ajax({
			url:url,
			data:requestParams,
			success:function(result){
				zTreeNodes=result;
				//子机构树
				initZTree();
			}
		})
	}
	
	//点击事件
	function zTreeOnClick(e, treeId, treeNode,clickFlag){
		$("#"+treeId+"~input").remove(":hidden");
		clickNode=treeNode;
		var text = "";
		if(clickFlag==0){//取消选中
			$("#"+treeId+"Text").textbox('setValue',text);
		}else if(clickFlag==1){//选中
			text=treeNode.name;
			$("#"+treeId).after("<input type='hidden' name='zTreeOrgCode' value="+treeNode.id+">");
			$("#"+treeId+"Text").textbox('setValue',text);
		}
		$("#"+treeId+"Panel").fadeOut("fast");
	}
	//选中事件
	function zTreeOnCheck(e, treeId, treeNode){
		$("#"+treeId+"~input").remove(":hidden");
		clickNode=treeNode;
		var checkedNodes = zTreeObj.getCheckedNodes(true);
		var text = "";
		var value = "";
		var name = zTreeSetting.data.key.name;
		for(var i=0;i<checkedNodes.length;i++){
			value += checkedNodes[i].id+",";
			text += checkedNodes[i][name] + ",";
		}
		if (text.length > 0 ) text = text.substring(0, text.length-1);
		if (value.length > 0 ) value = value.substring(0, value.length-1);
		$("#"+treeId).after("<input type='hidden' name='zTreeOrgCode' value="+value+">");
		$("#"+treeId+"Text").textbox('setValue',text);
	}
	//搜索功能
	function search_ztree(searchCondition){
		if(searchCondition == ""){return;}
		//由于异步加载如果打开的时候tree还没初始化好，此时treeObj为null
		if(zTreeObj && zTreeObj != null){
			if(searchValue != searchCondition){
				var idNode = zTreeObj.getNodeByParam("id", searchCondition, null);
				searchNodes = zTreeObj.getNodesByParamFuzzy(zTreeSetting.data.key.name, searchCondition, null);
				if(idNode && idNode != null){
					searchNodes.push(idNode);
				}
				searchIndex=0;
				searchValue = searchCondition;
			}
			zTreeObj.selectNode(searchNodes[searchIndex]);
			searchIndex = searchIndex == searchNodes.length-1 ? 0 : searchIndex+1;
		}
	}
	/**
	 * 递归得到指定节点的父节点的父节点....直到根节点
	 */
	function getParentNodes_ztree(node,searchObj,searchNodes){
		if (node != null) {
			if(!searchObj[node[id]]){
				searchObj[node[id]]=node;
			}
			var parentNode = node.getParentNode();
			if(parentNode!=null ){//父节点不为空
				if(!parentNode.children){
					parentNode.children= [];
				}
				parentNode.children.push(node);
				if(!searchObj[parentNode[id]]){
					searchObj[parentNode[id]] = parentNode;
				}
				searchObj[parentNode[id]]=parentNode;
				searchNodes.push(parentNode);
				getParentNodes_ztree(parentNode,searchObj,searchNodes);
			}else{//父节点为空表示当前结点为根节点
				searchNodes.push(node);
			}
		} 
	}
	//显示下拉树
	function showMenu(){
		$("#"+treeId+"Panel").slideDown("fast");    
	}
	//隐藏下拉树
	function hideMenu() {
		$("#"+treeId+"Panel").fadeOut("fast");
	}
	//给textbox加下拉箭头并赋点击事件
	$("#"+treeId+"Text").textbox({
		icons:[{iconCls:'combo-arrow',
			handler: function(e){
					 if($("#"+treeId+"Panel").css('display') == 'none'){
							showMenu();
						}
						else{
							hideMenu();
						}
				}}],
		editable:true
	})
	//键盘事件
	$("input",$("#"+treeId+"Text").next("span")).keyup(function(e){
		if(e.keyCode ==13){
			search_ztree($(this).val());
		}
		clickNode=undefined;
		showMenu();
		
	})
	//输入框点击事件
	$("input",$("#"+treeId+"Text").next("span")).click(function(){
		 if($(this).attr("readonly") != "readonly" && $("#"+treeId+"Panel").css('display') == 'none'){
			showMenu();
		 }else{
			hideMenu();
		 }
	})
	//点击Section以外的其他地方隐藏panel
	$("body").bind("mousedown", 
		function(event){
			if (!(event.target.id == treeId+"Box" || $(event.target).parents("#"+treeId+"Box").length>0)) {
				hideMenu();
			}
	 });
	//初始化树，默认展开第一级
	function initZTree(){
		zTreeObj = $.fn.zTree.init($("#"+treeId), zTreeSetting, zTreeNodes);
		var zTreeRoots = zTreeObj.getNodesByFilter(function (node) { return node.level == 0 });
		for(var i=0;i<zTreeRoots.length;i++){
			zTreeObj.expandNode(zTreeRoots[i],true,false,false);
		}
		if(treeParams && treeParams.callback && treeParams.callback != null){
			treeParams.callback();
		}
	}
}
//选中节点
function zTreeSetValues(treeId,ids){
	var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
	if(zTreeObj && zTreeObj !=null){
		for(var i=0;i<ids.length;i++){
			var node = zTreeObj.getNodeByParam('id',ids[i]);
			if(node && node != null){
				zTreeObj.checkNode(zTreeObj.getNodeByParam('id',ids[i]),true,false,true);
			}
		}
	}
}
//清除选中
function zTreeClearValues(treeId){
	var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
	if(zTreeObj && zTreeObj !=null){
		var checkedNodes = zTreeObj.getCheckedNodes(true);
		for(var i=0;i<checkedNodes.length;i++){
			zTreeObj.checkNode(checkedNodes[i],false,false,true);
		}
	}
}


/**
 * 机构表格选择器控件
 * @param orgGridPluginId
 * @param orgParams
 * @param pluginParams
 */
function uceOrgGridPlugin(orgGridPluginId,orgParams,pluginParams) {
	if(!orgGridPluginId){
		console.error("控件ID不存在...");
		return;
	}
	//导航项模板
	var navigateTemplate = "<a href='javascript:void(0)' class='easyui-linkbutton' ></a>";
	//选择数据存储器
	var valueDataRef = {};

	//机构控件文本框
	$("#"+orgGridPluginId+"Text").textbox({
		editable : false,
		icons : [{iconCls : 'icon-more'}],
		onClickIcon: function(index) {
			openDialog(orgGridPluginId+"Dlg", pluginParams && pluginParams.title ? pluginParams.title : '机构选择器')
		}
	})

	/**
	 * ======================选项表格================
	 */

	//选项表格列
	var columns = [
		[{
				field: 'ck',
				checkbox: true
			},
			{
				field: 'basOrgCode',
				title: '基准编码',
				hidden: true
			},
			{
				field: 'orgAliasName',
				title: '全选',
				width: 50,
				align: 'left'
			}
		]
	];

	/**
	 * 选项表格初始化
	 */
	$("#"+orgGridPluginId+"List").datagrid({
		url: "../org/findOrgByParentBaseOrgCode.do",
		textField: 'orgAliasName',
		valueField: 'baseOrgCode',
		idField: "baseOrgCode",
		height: '100%',
		toolbar: '#'+orgGridPluginId+'Navigate',
		pagination: true,
		pagePosition: 'bottom',
		fitColumns: true,
		columns: columns,
		selectOnCheck: false,
		checkOnSelect: false,
		singleSelect: true,
		queryParams: {
			orgTreeType: orgParams && orgParams.orgTreeType ? orgParams.orgTreeType : null,
			parentBaseOrgCode: 'UC001'//TODO
		},
		onLoadSuccess: function(data) {
			//去除选项表格的表格线
			var panel = $(this).datagrid('getPanel');
			var tr = panel.find('div.datagrid-body tr');
			tr.each(function() {
				var td = $(this).children('td');
				td.css({
					"border-width": "0"
				});;
			});
			//根据保存的已选数据更新表格check状态
			$.each(data.rows, function(index, item) {
				if(valueDataRef[item.baseOrgCode]) {
					$("#"+orgGridPluginId+"List").datagrid('checkRow', $("#"+orgGridPluginId+"List").datagrid('getRowIndex', item));
				} else {
					$("#"+orgGridPluginId+"List").datagrid('uncheckRow', $("#"+orgGridPluginId+"List").datagrid('getRowIndex', item));
				}
			})

		},
		onCheck: function(index, row) {
			onCheck(row);
		},
		onUncheck: function(index, row) {
			onUncheck(row);
		},
		onCheckAll: function(rows) {
			onCheckAll(rows);
		},
		onUncheckAll: function(rows) {
			onUncheckAll(rows);
		},
		onClickRow: function(indes, row) {
			//点击总部无效
			if(row.baseOrgCode == row.parentBaseOrgCode) {
				return;
			}
			//加载子机构
			$(this).datagrid('load', {
				orgTreeType: 1,
				parentBaseOrgCode: row.baseOrgCode
			});
			//追加导航分隔符
			$(navigateTemplate).appendTo("#"+orgGridPluginId+"Navigate").linkbutton({
				text: '>',
				plain: true,
				disabled: true
			});
			//追加导航
			appendNavigate(row);
			//启用父级导航
			$('#' + row.parentBaseOrgCode).linkbutton('enable');
		}
	})

	/**
	 * 表格分页配置
	 */
	$("#"+orgGridPluginId+"List").datagrid('getPager').pagination({
		links: pluginParams && pluginParams.pageLinkNum ? pluginParams.pageLinkNum : 6,
		layout: ['first', 'links', 'last']
	})

	/*
	 * 初始化根导航器
	 */
	initRootNavigate();

	/**
	 * 解析导航器为easy控件
	 */
	$.parser.parse("#"+orgGridPluginId+"Navigate");

	//初始化根导航
	function initRootNavigate() {
		$.ajax({
			url: '../org/findOrgRoot.do',
			data: {
				orgTreeType: orgParams && orgParams.orgTreeType ? orgParams.orgTreeType : null
			},
			success: function(data) {
				appendNavigate(data);
			}
		})
	}

	/**
	 * 追加导航
	 * @param {Object} item
	 */
	function appendNavigate(item) {
		$(navigateTemplate).appendTo("#"+orgGridPluginId+"Navigate").linkbutton({
			id: item.baseOrgCode,
			text: item.orgAliasName,
			plain: true,
			disabled: true,
			onClick: function() {
				$("#"+orgGridPluginId+"List").datagrid('load', {
					orgTreeType: orgParams && orgParams.orgTreeType ? orgParams.orgTreeType : null,
					parentBaseOrgCode: item.baseOrgCode
				});
				$(this).nextAll('.easyui-linkbutton').remove();
				$(this).linkbutton('disable');
			}
		});
	}

	/**
	 * 选择一行
	 * @param {Object} row
	 */
	function onCheck(row) {
		if(valueDataRef[row.baseOrgCode]) {
			return;
		}
		appendSelect(row);
		reloadData();
		$("#"+orgGridPluginId+"Tag").tagbox('select', row.baseOrgCode);
	}

	/**
	 * 选择所有行
	 * @param {Object} rows
	 */
	function onCheckAll(rows) {
		$.each(rows, function(index, item) {
			appendSelect(item);
			$("#"+orgGridPluginId+"Tag").tagbox('select', item.baseOrgCode);
		});
		reloadData();
	}

	/**
	 * 反选所有行
	 * @param {Object} rows
	 */
	function onUncheckAll(rows) {
		$.each(rows, function(index, item) {
			reduceSelect(item);
			$("#"+orgGridPluginId+"Tag").tagbox('unselect', item.baseOrgCode);
		});
		reloadData();
	}

	/**
	 * 反选一行
	 * @param {Object} row
	 */
	function onUncheck(row) {
		reduceSelect(row);
		reloadData();
		$("#"+orgGridPluginId+"Tag").tagbox('unselect', row.baseOrgCode);
	}

	/**
	 * ======================选项表格================
	 */

	/**
	 * ======================标签选择框================
	 */
	/**
	 * 标签盒子初始化
	 */
	$("#"+orgGridPluginId+"Tag").tagbox({
		url: '../org/findOrgByOrgNameOrCode.do',
		valueField: 'baseOrgCode',
		textField: 'orgAliasName',
		queryParams: {
			orgTreeType: orgParams && orgParams.orgTreeType ? orgParams.orgTreeType : null,
		},
		limitToList: true,
		mode: 'remote',
		prompt: '搜索名称、基准编码...',
		delay: 1000,
		onBeforeLoad: function(param) {
			param.orgFullCode = $(this).combobox('getValues').join(',');;
		},
		onLoadSuccess: function(data) {
			var selectedItem = $(this).tagbox('panel').children(".combobox-item-selected");
			$.each(selectedItem, function(index, item) {
				$(item).hide();
			})
		},
		onRemoveTag: function(value) {
			//移除值存储器的值
			reduceValue(value);
			//如果此记录在选项表格的当前页，去除该行的check状态
			var checkedRows = $("#"+orgGridPluginId+"List").datagrid('getChecked');
			$.each(checkedRows, function(index, item) {
				if(item && item.baseOrgCode == value) {
					var rowIndex = $("#"+orgGridPluginId+"List").datagrid('getRowIndex', item);
					if(rowIndex >= 0) {
						$("#"+orgGridPluginId+"List").datagrid('uncheckRow', rowIndex);
					}

				}
			});

		},
		onSelect: function(record) {
			//存储器已存在，表示已选中，不处理
			if(valueDataRef[record.baseOrgCode]) {
				return;
			}
			//追加该记录到存储器
			appendSelect(record);
			//如果该记录在表格中已选中，直接返回，否则选中当前页对应的记录
			var checkedRows = $("#"+orgGridPluginId+"List").datagrid('getChecked');
			var flag = false;
			$.each(checkedRows, function(index, item) {
				if(item && item.baseOrgCode == record.baseOrgCode) {
					flag = true;
				}
			});
			if(flag == true) {
				return;
			}
			var rows = $("#"+orgGridPluginId+"List").datagrid('getRows');
			$.each(rows, function(index, item) {
				if(item && item.baseOrgCode == record.baseOrgCode) {
					$("#"+orgGridPluginId+"List").datagrid('checkRow', $("#"+orgGridPluginId+"List").datagrid('getRowIndex', item));
				}
			});

		},
		onUnselect: function(record) {
			//移除存储器的值
			reduceValue(record.baseOrgCode);
			//如果此记录在选项表格的当前页，去除该行的check状态
			var checkedRows = $("#"+orgGridPluginId+"List").datagrid('getChecked');
			$.each(checkedRows, function(index, item) {
				if(item && item.baseOrgCode == record.baseOrgCode) {
					var rowIndex = $("#"+orgGridPluginId+"List").datagrid('getRowIndex', item);
					if(rowIndex >= 0) {
						$("#"+orgGridPluginId+"List").datagrid('uncheckRow', rowIndex);
					}

				}
			});
		}
	})
	/**
	 * 去除标签盒子边框
	 */
	$("#"+orgGridPluginId+"Tag").next("span").css({
		"border-width": "0"
	})

	/**
	 * 根据存储器重新加载标签盒子的下拉数据
	 */
	function reloadData() {
		var data = [];
		$.each(valueDataRef, function(index, item) {
			data.push(item);
		})
		$("#"+orgGridPluginId+"Tag").tagbox('loadData', data);
	}
	/**
	 * 追加存储器的值
	 * @param {Object} item
	 */
	function appendSelect(item) {
		if(valueDataRef == null) {
			valueDataRef = {};
		}
		valueDataRef[item.baseOrgCode] = item;
	}

	/**
	 * 删除存储器的值
	 * @param {Object} item
	 */
	function reduceSelect(item) {
		if(valueDataRef == null) {
			return;
		}
		delete valueDataRef[item.baseOrgCode];
	}

	/**
	 * 根据key删除存储器的值
	 * @param {Object} value
	 */
	function reduceValue(value) {
		if(valueDataRef == null) {
			return;
		}
		delete valueDataRef[value];
	}
	/**
	 * ======================标签选择框================
	 */

	/**
	 * ======================按钮事件================
	 */
	//确认按钮
	$('#'+orgGridPluginId+'Ok').linkbutton({
		//把存储器的值拼接为字符串放入文本框中
		onClick: function() {
			var text = '';
			var value = '';
			$.each(valueDataRef, function(index, item) {
				text = text == '' ? item.orgAliasName : text + ',' + item.orgAliasName;
				value = value == '' ? index : value + ',' + index;
			});
			$("#"+orgGridPluginId+"Text").textbox('setValue', value);
			$("#"+orgGridPluginId+"Text").textbox('setText', text);
			closeDialog(orgGridPluginId+"Dlg");
		}
	})
	//取消按钮
	$("#"+orgGridPluginId+"Cancel").linkbutton({
		onClick: function() {
			closeDialog(orgGridPluginId+"Dlg");
		}
	})
	/**
	 * ======================按钮事件================
	 */
}

/**
 * pako解压
 * @param b64Data
 * @returns
 */
function unzip(b64Data){  
	//Base64解码
    var strData     = atob(b64Data);  
    //将二进制字符串转换为字符数组
    var charData    = strData.split('').map(function(x){return x.charCodeAt(0);});  
    //将字符数据放入byte数组  
    var binData     = new Uint8Array(charData);  
    //解压
    var data        = pako.inflate(binData);  
    //根据解压的ByteArray返回ASCII字符串
    //strData     = String.fromCharCode.apply(null, new Uint16Array(data));
    if (!("TextDecoder" in window)){
    	strData = arrayBufferToString(data);
    }else {
		var decoder = new TextDecoder("utf-8");
	    //var binData = new Uint8Array(data);
	    var buffer = decoder.decode(data).replace(/\+/g, " ");
	    strData = decodeURIComponent(buffer).replace(/\\/g, '\\\\');
    }
    return strData;  
}