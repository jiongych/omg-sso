//为树形结构添加过滤功能（从easyui1.5.3copy） E 2017-03-22
$.ajaxSetup({
	type:'POST',
	beforeSend:function(xhr, settings) {
		if (settings.loadMask == undefined || settings.loadMask) {
			openRequestMask();
		}
	},
	success:function(data, statusText, xhr) {
		if (this.loadMask == undefined || this.loadMask) {
			closeRequestMask();
			this.closeMask = true;
		}
		if (this.info) {
			infoTip(this.info);
		}
		if (this.task) {
			this.task.call(this, data, statusText, xhr);
		}
	},
	error:function(data, status, xhr) {
		if (this.loadMask == undefined || this.loadMask) {
			closeRequestMask();
			this.closeMask = true;
		}
		//606:cookies失效
		if (data.status == 606) {
			window.location.href = data.getResponseHeader("Location");
	    } else if (status == 'timeout') {
			errorTip('请求超时，请检查网络是否正常');
		} else if (status == 'parsererror') {
			errorTip('数据解析异常');
		} else {
			var rs = eval('(' + data.responseText + ')');
			errorMsg(rs.exception.message);
		}
	},
	complete:function(xhr, statusText) {
		if (this.loadMask == undefined || this.loadMask)
			closeRequestMask();
	}
});
function showDateCombo(index){
	var els = $('.date-combo');
	for (var i = 0; i < els.length; i++) {
		$(els[i]).css('width', 90);
		var options = $(els[i]).attr('data-options');
		if (options)
			options = eval('({' + options + '})');
		$(els[i]).combobox({
			editable:false,
			data:[{text:'今天',value:1},{text:'昨天',value:2},{text:'过去一周',value:3},{text:'过去一个月',value:4}],
			onSelect:function(record){
				if (options && options.start && options.end && $('#' + options.start) && $('#' + options.end)) {
					var curDate = new Date();
					if (record.value == 1) {
						$('#' + options.start).datebox('setValue', currentDateString(curDate));
						$('#' + options.end).datebox('setValue', currentDateString(curDate));
					} else if (record.value == 2) {
						var date = addDays(curDate, -1);
						$('#' + options.start).datebox('setValue', currentDateString(date));
						$('#' + options.end).datebox('setValue', currentDateString(date));
					} else if (record.value == 3) {
						var date = addDays(curDate, -7);
						var dayOfWeek = date.getDay() == 0 ? 7 : date.getDay() - 1;
						date = addDays(date, -dayOfWeek);
						$('#' + options.start).datebox('setValue', currentDateString(date));
						$('#' + options.end).datebox('setValue', currentDateString(addDays(date, 6)));
					} else if (record.value == 4) {
						var date = addMonths(curDate, -1);
						date = new Date(date.getFullYear(), date.getMonth(), 1);
						$('#' + options.start).datebox('setValue', currentDateString(date));
						$('#' + options.end).datebox('setValue', currentDateString(addDays(addMonths(date, 1), -1)));
					}
				}
			}
		});
		$(els[i]).combobox('select', index ? index : 1);
	}
}
function checkAccess(role, access) {
	if (role) {
		var value = Math.pow(2, access);
		return parseInt(role & value) == value;
	}
	return false;
}
function grantRights(ary) {
	var rs = 0;
	for(var i = 0; i < ary.length; i++) {
		rs = rs | parseInt(Math.pow(2, ary[i]));
	}
	return rs;
}
//打开窗口加载界面
function openWindow(param) {
	$('#'+param.id).dialog({
		title : param.title,
		width : param.width,
		height : param.height,
		href : param.url,
		onSave : function(d) {
			param.onSave.call(this, d);
			return false;
		},
		onLoad : function(d) {
			param.onLoad.call();
		}
	});
	$('#'+param.id).dialog('open');
}

//打开窗口 添加回调事件OMG
function omgOpenDialog(param)
{
	$('#'+param.id).dialog({
		title:param.title,
	    iconCls:'icon-save',
	    cache:false,
	    modal:true,
	    onLoad:function(){
	    	if (param.onLoad) {
	    		param.onLoad.call(this);
	    	}
	    },
	    onOpen:function(){
	    	if (param.onOpen) {
				param.onOpen.call(this);
	    	}
	    },
	    onClose:function(){
	    	if (param.onClose) {
				param.onClose.call(this);
	    	}
	    },
	    onBeforeOpen:function(){
	    	if ($('#' +param. formId)) {
		    	$('#' + param.formId).form('clear');
		    	$('#' + param.formId).form('disableValidation');
	    	}
	    }
	}); 
	$('#'+param.id).dialog('open');
}
// 提交表单
function postForm(param) {
	if (param.url && param.form) {
		var elForm = $('#' + param.form);

		if (elForm.form('enableValidation').form('validate')) {
			openRequestMask();
			elForm.ajaxSubmit({
				url:param.url,
				success:function(data, statusText, xhr) {
					closeRequestMask();
					if (param.success) {
						param.success.call(this, data, statusText, xhr);
					} else {
						infoTip(param.info ? param.info : '操作成功！');
						var formId = elForm.attr('id');
						if (formId && formId.length > 3) {
							var winId = formId.substr(3, formId.length - 3);
							if (winId)
								closeDialog('win' + winId);
						}
						if (param.task)
							param.task.call(this, data);
					}
				},
				error:function(data, statusText, xhr) {
					//606:cookies失效 modify by zhangRb 20170607
					if (data.status == 606) {
						window.location.href = data.getResponseHeader("Location");
					}else{
						closeRequestMask();
						var rs = eval('('+data.responseText+')');
						
						errorMsg(rs.exception.message);
					}
				}
			});
		}
		else return false;
	}
}
function openRequestMask(formId) {
	if ($(parent.frameBody)) {
		var zIndex = $.fn.window.defaults.zIndex++;
		var maskIds = $(parent.frameBody).data('loadMaskId');
		if (maskIds) {
			maskIds.push('divLoadMask_' + zIndex);			
		} else {
			$(parent.frameBody).data('loadMaskId', ['divLoadMask_' + zIndex]);
		}
		var el = '<div id="divLoadMask_' + zIndex + '" formId="' + (formId ? formId : '') + '"><div class="window-mask" style="z-index:' + zIndex + ';opacity:0.3"></div><div class="mask-msg" style="z-index:';
		el = el + ($.fn.window.defaults.zIndex++) + ';" ondblclick="closeMask(this)">正在等待服务器响应，请稍候。。。</div></div>';
		$(el).appendTo($(parent.frameBody));
	}
}
function closeRequestMask() {
	if ($(parent.frameBody)) {
		var maskIds = $(parent.frameBody).data('loadMaskId');
		if (maskIds) {
			$('#' + maskIds[maskIds.length - 1], parent.document).remove();
			maskIds.pop();
			if (maskIds.length == 0) {
				$(parent.frameBody).removeData('loadMaskId');
			}
		}
	}
}
function closeMask(e) {
	$(e).parent().remove();
}
function clearRequestMask() {
	if ($(parent.frameBody)) {
		var maskIds = $(parent.frameBody).data('loadMaskId');
		if (maskIds) {
			for (var i = maskIds.length - 1; i >= 0; i--) {
				$('#' + maskIds[i], parent.document).remove();
				maskIds.pop();
			}
			$(parent.frameBody).removeData('loadMaskId');
		}
	}
}
function getRequestMaskValue() {
	if ($(parent.frameBody)) {
		var maskIds = $(parent.frameBody).data('loadMaskId');
		if (maskIds) {
			return $('#' + maskIds[maskIds.length - 1], parent.document).attr('formId');
		}
	}
	return undefined;
}
function openDialog(id, title) {
	$('#'+id).dialog({
		title:title,
	    iconCls:'icon-save',
	    cache:false,
	    modal:true,
	    onLoad:function(){
	    	
	    },
	    onOpen:function(){
	    	
	    },
	    onBeforeOpen:function(){

	    	var formId = id.substr(3, id.length - 3);
	    	if ($('#fom' + formId)) {
	    		$('#fom' + formId).form('clear');
	    		$('#fom' + formId).form('disableValidation');
	    	}
	    }
	}); 
	
	$('#'+id).dialog('open');
}
function closeDialog(id) {
	$('#'+id).dialog('close');
}
function resetForm(id) {
	$('#'+id).form('reset');
}
function infoMsg(msg) {
	$.messager.alert('信息提示', msg, 'info');
}
function errorMsg(msg) {
	$.messager.alert('错误提示', msg, 'error');
}
function infoTip(msg) {
	$.messager.show({title:'信息提示', msg:'<div class="messager-icon messager-info"></div><div>' + msg + '</div>', showType:'slide', style:{right:'',bottom:''}, top:document.body.scrollTop+document.documentElement.scrollTop, timeout:2000});
}
function errorTip(msg) {
	$.messager.show({title:'错误提示', msg:'<div class="messager-icon messager-error"></div><div>' + msg + '</div>', showType:'slide', style:{right:'',bottom:''}, top:document.body.scrollTop+document.documentElement.scrollTop, timeout:3000});
}
function confirmMsg(msg, fn, args) {
	$.messager.confirm('确认操作', msg, function(r) {
		if (r) {
			fn.apply(this, args);
		}
	});
}
function getComboboxText(id, value) {
	var options = $('#' + id).combobox('options');
	var ary = $('#' + id).combobox('getData');
	for (var i = 0; i < ary.length; i++) {
		if (ary[i][options.valueField] == value) {
			return ary[i][options.textField];
		}
	}
	return value;
}
function queryData(gridId, formId) {
	if (formId) {
		var param = $.extend($('#'+gridId).datagrid('options').queryParams, $('#'+formId).getValues());
		$('#'+gridId).datagrid('load', param);
	} else {
		$('#'+gridId).datagrid('load', $('#'+gridId).datagrid('options').queryParams);
	}
	$('#'+gridId).datagrid('clearSelections');
}
function handRowStyle(index, row) {
	return 'cursor:pointer';
}
function currentDateString(date) {
	if (!date) {
		date = new Date();
	}
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function numberField(e) {
	var k = window.event ? e.keyCode : e.which;
	if (!(k >= 48 && k <= 57)) {
		if (window.event) {
			window.event.returnValue = false;
		} else {//for firefox
			e.preventDefault();  
		}
	}
}
function addDays(d, n) {//复制并操作新对象，避免改动原对象
    var t = new Date(d);
    t.setDate(t.getDate() + n);
    return t;
}
function addMonths(d, n) {//日期+月。日对日，若目标月份不存在该日期，则置为最后一日
    var t = new Date(d);
    t.setMonth(t.getMonth() + n);
    if (t.getDate() != d.getDate()) { t.setDate(0); }
    return t;
}
function createPageBar(store,items) {
	if (items == undefined) {
		items = [];
	}
	return new Ext.toolbar.Paging({
		store:store,
		displayInfo:true,
		beforePageText:'Page',
		afterPageText:'of {0}',
		displayMsg:'Displaying topics {0} - {1} of {2}',
		emptyMsg:'No topics to display',
		items:items
	});
}
function addFloat(arg1, arg2) {
	var r1, r2, m; 
	try {r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;} 
	try {r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;} 
	m = Math.max(r1, r2);
	var v1, v2;
	if (r1 == 0) {
		v1 = arg1 * Math.pow(10, m);
	} else {
		v1 = arg1.toString().replace('.', '');
		if (r1 < m) {
			v1 = parseInt(v1) * Math.pow(10, m - r1);
		}
	}
	if (r2 == 0) {
		v2 = arg2 * Math.pow(10, m);
	} else {
		v2 = arg2.toString().replace('.', '');
		if (r2 < m) {
			v2 = parseInt(v2) * Math.pow(10, m - r2);
		}
	}
	r1 =  (parseInt(v1) + parseInt(v2)).toString();
	r2 = r1.length - m;
	if (r2 == 0 && m > 0) {
		return '0.' + r1;
	}
	if (r2 < 0 && m > 0) {
		r2 = Math.pow(10, Math.abs(r2));
		return '0.' + r2.toString().substr(1, r2.toString().length - 1) + r1;
	}
	return parseFloat(r1.substr(0, r2) + '.' + r1.substr(r2, m)); 
}
function subFloat(arg1, arg2) {
	var r1, r2, m; 
	try {r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;} 
	try {r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;} 
	m = Math.max(r1, r2);
	var v1, v2;
	if (r1 == 0) {
		v1 = arg1 * Math.pow(10, m);
	} else {
		v1 = arg1.toString().replace('.', '');
		if (r1 < m) {
			v1 = parseInt(v1) * Math.pow(10, m - r1);
		}
	}
	if (r2 == 0) {
		v2 = arg2 * Math.pow(10, m);
	} else {
		v2 = arg2.toString().replace('.', '');
		if (r2 < m) {
			v2 = parseInt(v2) * Math.pow(10, m - r2);
		}
	}
	r1 =  parseInt(v1) - parseInt(v2);
	return parseFloat((parseInt(v1) - parseInt(v2)) / Math.pow(10, m)); 
}
function mulFloat(arg1, arg2, precision) {
	var r1, r2, m; 
	try {r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;} 
	try {r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;} 
	var v1, v2;
	if (r1 == 0) {
		v1 = arg1;
	} else {
		v1 = arg1.toString().replace('.', '');
	}
	if (r2 == 0) {
		v2 = arg2;
	} else {
		v2 = arg2.toString().replace('.', '');
	}
	r1 =  parseInt(v1) * parseInt(v2);
	m = parseFloat(parseInt(v1) * parseInt(v2) / Math.pow(10, r1 + r2));
	if (isNaN(precision)) {
		return m;
	}
	return roundFloat(m, precision);
}
function divFloat(arg1, arg2, precision) {
	var r1, r2, m; 
	try {r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;} 
	try {r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;} 
	m = Math.max(r1, r2);
	var v1, v2;
	if (r1 == 0) {
		v1 = arg1 * Math.pow(10, m);
	} else {
		v1 = arg1.toString().replace('.', '');
		if (r1 < m) {
			v1 = parseInt(v1) * Math.pow(10, m - r1);
		}
	}
	if (r2 == 0) {
		v2 = arg2 * Math.pow(10, m);
	} else {
		v2 = arg2.toString().replace('.', '');
		if (r2 < m) {
			v2 = parseInt(v2) * Math.pow(10, m - r2);
		}
	}
	m = parseFloat(parseInt(v1) / parseInt(v2));
	if (isNaN(precision)) {
		return m;
	}
	return roundFloat(m, precision);
}
function roundFloat(value, precision) {
	if (!isNaN(precision)) {
		var r;
		try {r=value.toString().split(".")[1].length;}catch(e){r=0;}
		if (r != 0) {
			var v = value.toString().replace('.', '');
			v = parseInt(v) * Math.pow(10, precision);
			v = Math.round(parseInt(v) / Math.pow(10, r));
			return parseInt(v) / Math.pow(10, precision);
		}
	}
	return value;
}
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
function openTabPage(id, name, url) {
	if ($.isFunction(window.loadPage)) {
		loadPage(id, name, url, true);
	} else {
		parent.loadPage(id, name, url, true);
	}
}
function getContextPath() {
	return _contextPath == undefined ? parent._contextPath : _contextPath;
}
function getCurUser() {
	return _curUser == undefined ? parent._curUser : _curUser;
}
function getAllOrgSysTypes() {
	return _orgSysTypes == undefined ? parent._orgSysTypes : _orgSysTypes;
}
function getAllSysTypes() {
	return _allSysTypes == undefined ? parent._allSysTypes : _allSysTypes;
}
function getAllAreas() {
	return _cmsAreas == undefined ? parent._cmsAreas : _cmsAreas;
}
function getAllOrgs() {
	return _allOrgs == undefined ? parent._allOrgs : _allOrgs;
}
function getAllOrgSysTypeNameById(id) {
	var sysTypes = getAllOrgSysTypes();
	if (sysTypes)
		for (var i = 0; i < sysTypes.length; i++) {
			if (sysTypes[i].typeId == id) {
				return sysTypes[i].typeName;
			}
		}
	return undefined;
}
function getAreaNameByAreaId(id) {
	var cmsAreas = getAllAreas();
	if (cmsAreas)
		for (var i = 0; i < cmsAreas.length; i++) {
			if (cmsAreas[i].id == id) {
				return cmsAreas[i].areaName;
			}
		}
	return undefined;
}
function getOrgNameByOrgId(id) {
	var orgs = getAllOrgs();
	if (orgs) {
		for (var i = 0; i < orgs.length; i++) {
			if (orgs[i].id == id) {
				return orgs[i].orgName;
			}
		}
	}
	return undefined;
}
function getCmsOrgsForSelect() {
	var data = [], child = [];
	var orgs = getAllOrgs();
	var sysTypes = getAllOrgSysTypes();
	var childType = sysTypes[sysTypes.length - 1].typeId;
	var siteType = sysTypes[sysTypes.length - 2].typeId;
	for (var i = 0; i < orgs.length; i++) {
		if (orgs[i].orgType == childType) {
			if (getCurUser().orgType == siteType && getCurUser().orgId == orgs[i].parentOrg)
				child.push(orgs[i]);
		} else {
			data.push(orgs[i]);
		}
	}
	for (var i = 0; i < child.length; i++)
		data.push(child[i]);
	return data;
}
function openCmsOrgTreeDialog(id) {
	var el = '<div id="winSelectCmsOrgDialog" data-options="closed:true" style="width:400px;height:500px;padding:5px"><div id="divSelectCmsOrgDialog" data-options="fit:true">';
	el = el + '<div data-options="region:\'center\',border:false" style="padding:10px 10px;background:#fff;border:1px solid #ccc;"><ul id="selectCmsOrgTree" data-options="checkbox:true,animate:true"></ul></div>';
	el = el + '<div data-options="region:\'south\',border:false" style="height:38px;text-align:right;padding:5px 0;"><a id="btnCmsOrgDialogOK" href="javascript:void(0)" onclick="selectCmsOrgForDialog(\'' + id + '\')">选择</a>';
	el = el + '&nbsp;<a id="btnCmsOrgDialogCancel" href="javascript:void(0)" onclick="closeCmsOrgDialog()">取消</a></div></div></div>';
	$('body').append($(el));
	var url = (getContextPath() == '/' ? '' : getContextPath()) + '/session/org/findCmsOrgsByParentId.json';
	$('#selectCmsOrgTree').tree({  
		checkbox: false,
        url: url,
		loadFilter: function(data, parentNode){
			if (data.rows) {
				var nodes = [];
				for (var i = 0; i < data.rows.length; i++) {
					var node = {};
					node.id = data.rows[i].id;
					node.text = data.rows[i].orgCode + ' ' + data.rows[i].orgName;
					node.attributes = data.rows[i];
					node.state = 'closed';
					nodes.push(node);
				}
				return nodes;
			}
			return [data];
		},
		onDblClick:function(node) {
			selectCmsOrgForDialog(id);
		},
		onLoadSuccess:function(node, data) {
			if (node == null) {
				var rootNode = $('#selectCmsOrgTree').tree('getRoot');
				$('#selectCmsOrgTree').tree('expand', rootNode.target);
			}
		}
    });
	$('#btnCmsOrgDialogOK').linkbutton({iconCls:'icon-ok'});
	$('#btnCmsOrgDialogCancel').linkbutton({iconCls:'icon-cancel'});
	$('#divSelectCmsOrgDialog').layout();
	$('#winSelectCmsOrgDialog').dialog({
		title:'选择机构',
	    iconCls:'icon-search',
	    cache:false,
	    modal:true,
	    top:document.body.scrollTop + 60,
	    onClose:closeCmsOrgDialog
	});
	$('#winSelectCmsOrgDialog').dialog('open');
}

//打开选择员工窗口
function openOmgEmpDialog(id,limit){
		var el = '<div id="winSelectOmgEmpDialog" data-options="closed:true" style="width:500px;height:500px;padding:5px"><div id="divSelectOmgEmpDialog" data-options="fit:true">';
		el = el + '<div data-options="region:\'north\',border:false" style="padding:10px 0px 0px 10px;background:#fff;border:1px solid #ccc;border-bottom:0px;height:50px">';
		el = el + '编号：<input id="txtOmgEmpDialogCode" style="border:1px solid #95B8E7;padding:4px;display:inline-block;font-size:12px;width:100px">&nbsp;';
		el = el + '姓名：<input id="txtOmgEmpDialogName" style="border:1px solid #95B8E7;padding:4px;display:inline-block;font-size:12px;width:100px">&nbsp;';
		el = el + '<a id="btnOmgEmpDialogQuery" href="javascript:void(0)" onclick="javascript:searchDialogEmp()">查询</a></div>';
		el = el + '<div data-options="region:\'center\',border:false" style="padding:0px 10px 10px 10px;background:#fff;border:1px solid #ccc;border-top:0px;"><table id="selecOmgEmpDatagrid"  data-options=" iconCls:\'icon-ok\',rownumbers: true, animate: true, fit:true,collapsible: true,fitColumns:false,singleSelect:true,method: \'post\', idField: \'id\',treeField: \'name\'">';
		el = el + '<thead><th data-options="field:\'empId\',width:30,hidden:true"></th><th data-options="field:\'empCode\',width:100">编号</th><th data-options="field:\'empName\',width:150">姓名</th><th data-options="field:\'orgFullName\',formatter:fromOrgName,width:200">机构路径</th></thead></table></div>';
		el = el + '<div data-options="region:\'south\',border:false" style="height:38px;text-align:right;padding:5px 0;"><a id="btnOmgEmpDialogOK" href="javascript:void(0)" onclick="selectOmgEmpForDialog(\'' + id + '\')">选择</a>';
		el = el + '&nbsp;<a id="btnOmgEmpDialogCancel" href="javascript:void(0)" onclick="closeCmsOrgDialog()">取消</a></div></div></div>';
		$('body').append($(el));
		$('#btnOmgEmpDialogOK').linkbutton({iconCls:'icon-ok'});
		$('#btnOmgEmpDialogCancel').linkbutton({iconCls:'icon-cancel'});
		$('#btnOmgEmpDialogQuery').linkbutton({iconCls:'icon-search'});
		$('#divSelectOmgEmpDialog').layout();
		$('#winSelectOmgEmpDialog').dialog({
			title:'选择员工',
		    iconCls:'icon-search',
		    cache:false,
		    modal:true,
		    top:document.body.scrollTop + 60,
		    onClose:closeCmsOrgDialog
		});
		$('#winSelectOmgEmpDialog').dialog('open');
		$('#selecOmgEmpDatagrid').datagrid({
			loadMsg:'正在加载...',
			pagination:true,
			url:contextPath + '/emp/findEmp.json?filterLeaveOffice=true',
			onDblClickRow:function(index, row) {
				 selectOmgEmpForDialog(id, row);
			}
		})
	
}
function searchDialogEmp() {
	$('#selecOmgEmpDatagrid').datagrid("clearSelections");
	$('#selecOmgEmpDatagrid').datagrid('load', {empName:$("#txtOmgEmpDialogName").val(), empCode: $("#txtOmgEmpDialogCode").val()});
}

//选择员工后，点击“确认”按钮    
function selectOmgEmpForDialog(id, row) {
	if (!row) {
		row = $('#selecOmgEmpDatagrid').datagrid('getSelected');
	}
	if (row) {
		$('#' + id+'Name').searchbox('setValue', row.empName);
		$('#' + id+'Id').val(row.empId);
		$('#winSelectOmgEmpDialog').dialog('destroy');
	} else {
		infoMsg('请选择人员！');
	}
}
function fromOrgName(val){
	if (typeof(val)!="undefined") {
		return '<a title='+val+' href=\"javascript:void(0)\">'+val+'</a>';
	} else {
		return "";
	}
}

var selectTree;

//打开选择机构窗口
function openOmgOrgDialog(param) {
	var el = '<div id="winSelectOmgOrgDialog" data-options="closed:true" style="width:350px;height:500px;padding:5px">';
		el = el + '<div id="divSelectOmgOrgDialog" data-options="fit:true">';
	//el = el + '<div data-options="region:\'north\',border:false" style="padding:10px 0px 0px 10px;background:#fff;border:1px solid #ccc;border-bottom:0px;height:50px"><input id="txtCmsOrgDialogCode" style="border:1px solid #95B8E7;padding:4px;display:inline-block;font-size:12px;width:294px"></div>';
	el = el + '<div data-options="region:\'center\'" style="padding:0px 10px 10px 10px;background:#fff;border:1px solid #ccc;border-top:0px; height:400px">';
	el = el + '<div class=\"ztree\"  style="padding:10px 0px 0px 10px; height:400px;" id=\"selectTree\"></div>';
	el = el +'</div>';
	el = el + '<div data-options="region:\'south\',border:false" style="height:38px;text-align:right;padding:5px 0;"> <a id="btnCmsOrgDialogOK" href="javascript:void(0)">选择</a>';
	el = el + '&nbsp;<a id="btnCmsOrgDialogCancel" href="javascript:void(0)" onclick="closeOrgOrgDialog()">取消</a></div></div>';
	el = el +'</div>';
	$('body').append($(el));
	var gridData;// = getCmsOrgsForSelect();
/*	$('#txtCmsOrgDialogCode').keyup(function(event) {
	//查询
	});*/
	//bug #2829 zhangfenghuang 2016-01-08 begin
	$("#btnCmsOrgDialogOK").click(function(){
		selectOmgOrgForDialog(param);
	});
	//bug #2829 zhangfenghuang 2016-01-08 end
	$('#btnCmsOrgDialogOK').linkbutton({iconCls:'icon-ok'});
	$('#btnCmsOrgDialogCancel').linkbutton({iconCls:'icon-cancel'});
	$('#divSelectOmgOrgDialog').layout();
	$('#winSelectOmgOrgDialog').dialog({
		title:'选择机构',
	    iconCls:'icon-search',
	    cache:false,
	    modal:true,
	    top:document.body.scrollTop + 60,
	    onClose:closeCmsOrgDialog
	});
	$('#winSelectOmgOrgDialog').dialog('open');
	//查询机构
	 var selectTreesetting = {
				view: {
					dblClickExpand: false
				},
				 data: {  
			                simpleData: {//是否为简单数据类型JSON  
			                enable:true,  
			                idKey: "id",//使用简单必须标明的的节点对应字段  
			                pIdKey: "_parentId",//使用简单必须标明的的父节点对应字段  
			                rootPId:null//根  
			            }  
			        },  
				async: {
					enable: true,
					dataType:"json",
					url: contextPath +"/org/findAllFroZtree.json",
					autoParam: ["id", "name"],
					dataFilter: function ajaxDataFilter(treeId, parentNode, responseData) {
						if(param.dataFilter){
							return param.dataFilter.call(this,responseData);
						}
					    return responseData;
				    }
				}
			};
	  $.fn.zTree.init($("#selectTree"), selectTreesetting, null);
      selectTree = $.fn.zTree.getZTreeObj("selectTree");
}

//确认选择机构
function selectOmgOrgForDialog(param) {
	var id = param.id;
	var selectNode=selectTree.getSelectedNodes()[0];		
	if (selectNode) {
		//BUG #2829 zhangfenghuang 2016-01-06 begin
		if (param.onSelect){
			var flag = param.onSelect.call(this,selectNode);
			if(flag == false){
				return;
			}
		}
		$('#' + id+"Id").val(selectNode.id);
		$('#' + id+"Name").val(selectNode.name);
		//BUG #2829 zhangfenghuang 2016-01-06 end
		closeOrgOrgDialog();
	} else {
		infoMsg('请选择机构！');
	}
}


//打开 管理机构范围窗口
function openOmgOrgByUserDialog(param) {
	var el = '<div id="winSelectOmgOrgUserDialog" data-options="closed:true" style="width:350px;height:500px;padding:5px">';
	el = el + '<div id="divSelectOmgOrgUserDialog" data-options="fit:true">';
	el = el + '<div data-options="region:\'center\'" style="padding:0px 10px 10px 10px;background:#fff;border:1px solid #ccc;border-top:0px; height:400px">';
	el = el + '<div class=\"ztree\"  style="padding:10px 0px 0px 10px; height:400px;" id=\"selectTree\"></div>';
	el = el +'</div>';
	el = el + '<div data-options="region:\'south\',border:false" style="height:38px;text-align:right;padding:5px 0;"><span style=\"color:red\">选择机构包含其下级，最多能选择3个</span> <a id="btnOrgUserDialogOK" href="javascript:void(0)">确定</a>';
	el = el + '&nbsp;<a id="btnOrgDialogUserCancel" href="javascript:void(0)" onclick="closeOrgOrgUserDialog()">取消</a></div></div>';
	el = el +'</div>';
	$('body').append($(el));
	var gridData;// = getCmsOrgsForSelect();
	$('#btnOrgUserDialogOK').linkbutton({iconCls:'icon-ok'});
	$('#btnOrgDialogUserCancel').linkbutton({iconCls:'icon-cancel'});
	$('#divSelectOmgOrgUserDialog').layout();
	$('#winSelectOmgOrgUserDialog').dialog({
		title:'选择机构',
	    iconCls:'icon-search',
	    cache:false,
	    modal:true,
	    top:document.body.scrollTop + 60,
	    onClose:closeOrgOrgUserDialog
	});
	$('#winSelectOmgOrgUserDialog').dialog('open');
	//查询机构
	var selectTreesetting = {
	    view: {
	        dblClickExpand: false
	    },
	    check: {
	        enable: true,
	        chkStyle: "checkbox",
	        chkboxType: {
	            "Y": "",
	            "N": ""
	        }
	    },
	    data: {
	        simpleData: { //是否为简单数据类型JSON  
	            enable: true,
	            idKey: "id", //使用简单必须标明的的节点对应字段  
	            pIdKey: "_parentId", //使用简单必须标明的的父节点对应字段  
	            rootPId: null //根  
	        }
	    },
	    async: {
	        enable: true,
	        dataType: "json",
	        url: contextPath + "/org/findAllFroZtree.json",
	        autoParam: ["id", "name"],
	        dataFilter: function ajaxDataFilter(treeId, parentNode, responseData) {
	            if (param.dataFilter) {
	                return param.dataFilter.call(this, responseData);
	            }
	            return responseData;
	        }
	    },
	    callback: {
	        onCheck: onCheck,
	        onAsyncSuccess: zTreeOnAsyncSuccess
	    }
	};
	$.fn.zTree.init($("#selectTree"), selectTreesetting, null);
	selectTree = $.fn.zTree.getZTreeObj("selectTree");
	//BUG #3428 用户名自动转换成大写字母   mengwenqi  2016-01-05 begin
	function zTreeOnAsyncSuccess(event, treeId, treeNode, responseData) {
	        if ($('#roleOrgId').val() == "") {
	            return;
	        }
	        var responseData = selectTree.transformToArray(selectTree.getNodes());
	        var orgIds = $('#roleOrgId').val().split(",");
	        for (var i = 0; i < responseData.length; i++) {
	            for (var y = 0; y < orgIds.length; y++) {
	                if (orgIds[y] != "") {
	                    if (orgIds[y] == responseData[i].id) {
	                        var node = selectTree.getNodeByParam("id", responseData[i].id);
	                        selectTree.checkNode(node, true, true);
	                    }
	                }
	            }
	        }
	    }
	    //BUG #3428 用户名自动转换成大写字母   mengwenqi 2016-01-05 end
	var checkedId = "";
	var checkedName = "";

	function onCheck(e, treeId, treeNode) {
	    checkedId = "";
	    checkedName = "";
	    nodes = selectTree.getChangeCheckedNodes();
	    if (nodes.length > 3) {
	        infoMsg('最多能选择三个机构！');
	        selectTree.checkNode(treeNode, false, true, true);
	        return false;
	    }
	    for (var i = 0, l = nodes.length; i < l; i++) {
	        checkedId += nodes[i].id + ",";
	        checkedName += nodes[i].name + ",";
	    }
	}
	//选定机构范围后，点击确定，回写选择的机构值
	$("#btnOrgUserDialogOK").click(function () {
	    if (checkedId.length <= 0 || checkedName.length <= 0) {
	        checkedId = "";
	        checkedName = "";
	    }
	    $('#' + param.id + "Id").val(checkedId);
	    $('#' + param.id + "Name").textbox("setValue", checkedName);
	    closeOrgOrgUserDialog();
	});
}
function closeOrgOrgUserDialog() {
	$('#winSelectOmgOrgUserDialog').dialog('destroy');
}
function closeOrgOrgDialog() {
	$('#winSelectOmgOrgDialog').dialog('destroy');
}
function closeCmsOrgDialog() {
	$('#winSelectOmgEmpDialog').dialog('destroy');
}

function formatStatus (val,rec,index){
	if(val=="") {
		return val;
	}
	if(val) {
		return "<image src='"+contextPath+"/scripts/easyui/themes/icons/tick.png'></image>";	
	} else {
		return  "<image src='"+contextPath+"/scripts/easyui/themes/icons/01.png'></image>";
	}
}
//根据机构编号实例化 机构名称
function findOrgNameByOrgId(orgId) {
	var orgName=getName(orgId);
	console.log(orgName);
	return orgName;
}
function getName(orgId){
	$.post(contextPath + '/org/findOrgNameByOrgId.json', {
		orgId : orgId
	}, function(data) {
		return data;
	});
}
////根据类型ID实例化类型编号
//function findTypeNameById(id) {
//	$.post(contextPath + '/systype/findTypeNameById.json',
//			{
//				id : id
//			}, function(data) {
//				return data;
//			});
//}



//调整datagrid,序号列宽度 bug ##2801 zhangfenghuang 2016-01-05 end
//小写转大写 #3238 mengwenqi 
function upperCase(dom)   {
    $(dom).val($(dom).val().toUpperCase());
}
//小写转大写 #3238 mengwenqi end

/**
 * 查询机构
 * */
function searchOrgInZtree(options){
	$.post( contextPath + '/org/findParentOrgListByCondition.json',
			{orgName : options.orgName},
			function(data){
				var selectedNode;
				var nodes = options.zTree.getNodes();
				//如果未输入名称则展示
				if (options.orgName.trim() == '') {
					$.each(nodes, function(i, node){
						console.log(nodes[i]);
						options.zTree.showNode(nodes[i]);
					});
					selectedNode = options.zTree.getSelectedNodes();
					options.zTree.cancelSelectedNode(selectedNode[0]);
					return;
				}
				if (data.length > 0 ) {
					for (var i = 0; i < data.length; i++) {
						if (nodes == null || nodes.length == 0) {
							break;
						}
						for (var j = 0; j < nodes.length ; j++) {
							if (nodes[j].id == data[i].orgId) {
								options.zTree.expandNode(nodes[j], true, false, true);
								if (i == data.length - 1) {
									options.zTree.selectNode(nodes[j], false, false);
									selectedNode = nodes[j];
									return;
								}
								//bug #324 huangting 2017-06-07 add begin
								options.zTree.showNode(nodes[j]);
								//bug #324 huangting 2017-06-07 add end
								nodes = nodes[j].children;
								break;
							}
						}
					}
					if (options.callback) {
						options.callback.call(this, selectedNode);
					}
				} else {
					//bug #324 huangting 2017-06-07 add begin
					for (var j = 0; j < nodes.length ; j++) {
						 options.zTree.hideNode(nodes[j]);
					}
					//bug #324 huangting 2017-06-07 add end
				}
			});
}

/**
 * 初始化数据字典
 */
var DictDatas = {};
function initDictDatas(typeClassCodes,params){
  if(typeClassCodes!=null && typeClassCodes!=undefined){
    $.ajax({
      type: "post",
      url : params && params.url ? params.url : contextPath + '/sysType/findByTypeClassCodes.json',
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
    for(var i=0; i<DictDatas[typeClassCodes].length; i++){
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
 *数据字典下拉列表
 *@domId input id,如：<input id="datadic" name="datadic" value="" data-options="prompt:'请选择查询条件'"/>
 *@typeClassCode 请求参数
 */
function uceDictCombobox(id, typeClassCode) {
	$("#" + id).uceCombobox({
		valueField : 'typeId',
		textField : 'typeName',
		filterFields : ['typeCode', 'typeName'],//设置可根据typeCode、typeName查询
	    limitToList : true,//限制必须输入列表内的值
	    headerValue : '', //设置头节点
	    formatter:formatDicShowTypeCode, //格式化下拉列表数据，显示typeCode
	    data : DictDatas[typeClassCode].slice(0)
	});
}

/**
 * 格式化状态
 * @param value
 */
function formatDelStatus(value) {
	if (!value) {
    	return "正常";
    } else {
    	return "<font style='color:red'>已删除</font>";
    }
}
