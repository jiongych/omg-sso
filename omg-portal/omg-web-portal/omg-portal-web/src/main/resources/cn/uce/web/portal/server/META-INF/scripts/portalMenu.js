/**
 * Portal菜单树脚本
 */
var baseZTree, extZTree, shiftKey, extTreeChangeFlag, portalMenuTreeNodeAddMap, portalMenuTreeNodeSortMap, portalMenuTreeNodeDeleteMap, portalMenuTreeNodeUpdateMap;

var baseMenuTreeSetting = {
	view: {
		fontCss: getFont,
		nameIsHTML: true
	},
	edit : {
		drag : {
			autoExpandTrigger : true,
			isCopy : true,
			isMove : false
		},
		enable : true,
		showRemoveBtn : false,
		showRenameBtn : false
	},
	data : {
		simpleData : {// 是否为简单数据类型JSON
			enable : true,
			idKey : "id",// 使用简单必须标明的的节点对应字段
			pIdKey : "pid",// 使用简单必须标明的的父节点对应字段
			rootPId : null
			// 根
		}
	},
	async : {
		enable : true,
		dataType : "json",
		url : 'findBaseMenuForZtree.do',
		otherParam : ["systemCode", function() {
					return $('#searchSystemCode').combobox('getValue');
				}],
		dataFilter : function(treeId, parentNode, responseData) {
			return responseData.rows;
		}
	},
	callback : {
		beforeDrag : baseMenuTreeBeforeDrag,
		beforeDrop : baseMenuTreeBeforeDrop,
		onDrop : baseMenuTreeOnDrop,
		onAsyncSuccess : baseMenuTreeLoadSuccess,
		onAsyncError : zTreeOnAsyncError
	}
};

var portalMenuTreeSetting = {
	view : {
		addHoverDom : portalMenuTreeAddHoverDom,
		removeHoverDom : portalMenuTreeRemoveHoverDom
	},
	edit : {
		drag : {
			autoExpandTrigger : true
		},
		enable : true,
		showRemoveBtn : false,
		showRenameBtn : false
	},
	data : {
		key : {
			name : "aName"
		},
		simpleData : {// 是否为简单数据类型JSON
			enable : true,
			idKey : "id",// 使用简单必须标明的的节点对应字段
			pIdKey : "pid",// 使用简单必须标明的的父节点对应字段
			rootPId : null // 根
		}
	},
	async : {
		enable : true,
		dataType : "json",
		url : 'findPortalMenuForZtree.do',
		dataFilter : function(treeId, parentNode, responseData) {
			return responseData.rows;
		}
	},
	callback : {
		beforeDrag : portalMenuTreeBeforeDrag,
		beforeDrop : portalMenuTreeBeforeDrop,
		onDrop : portalMenuTreeOnDrop,
		onClick : portalMenuTreeNodeClick,
		onRightClick : extTreeOnRightClick,
		onAsyncSuccess : portalMenuTreeLoadSuccess,
		afterInitNode : function(treeNode) {
			// 节点渲染前最后一次处理treeNode数据
			if (treeNode.aName == null || treeNode.aName == '') {
				treeNode.aName = treeNode.name;
			}
		},
		onAsyncError : zTreeOnAsyncError
	}
};
//loadEnd
jQuery(function($) {
	initDictDatas("SYSTEM_CODE");
	// 加载“所属系统”下拉列表
	uceDictCombobox("searchSystemCode", "SYSTEM_CODE");
	uceDictCombobox('systemCodeView', 'SYSTEM_CODE');
	// 设置系统类型值变更事件
	$('#searchSystemCode').combobox({
				onChange : function() {
					reloadBaseMenuTree();
				}
			});
	// 初始化加载右边结构
	$.fn.zTree.init($("#baseMenuzTree"), baseMenuTreeSetting, null);
	baseZTree = $.fn.zTree.getZTreeObj("baseMenuzTree");

	// 初始化加载右边结构
	$.fn.zTree.init($("#portalMenuzTree"), portalMenuTreeSetting, null);
	extZTree = $.fn.zTree.getZTreeObj("portalMenuzTree");
	// 键盘事件监听
	$(document).off('keydown').on('keydown', function(e) {
		if (!shiftKey && e.keyCode == 16) {
			var keyCode = e.keyCode || e.which; // 按键的keyCode
			var isShift = e.shiftKey || (keyCode == 16) || false; // shift键是否按住
			shiftKey = isShift;
		}
	});
	$(document).off('keyup').on('keyup', function(e) {
		if (e.keyCode == 16) {
			shiftKey = false;
		}
	});
	// Portal菜单树搜索框事件
	$('#schPortalMenuName').searchbox({
		searcher : function(value, name) {
			if (value == '' || value == null) {
				return;
			}
			portalMenuSearch(value);
			$('input', $('#schPortalMenuName').next('span')).focus();
		}
	});

	// 新增Portal菜单map添加元素事件
	portalMenuTreeNodeAddMap = new MapUtil();
	portalMenuTreeNodeAddMap.eventListener('onPut', function(key, value) {
		var systemName = '';
		for (var i = 0; i < DictDatas['SYSTEM_CODE'].length; i++) {
			if (DictDatas['SYSTEM_CODE'][i].typeCode == value.systemCode) {
				systemName = DictDatas['SYSTEM_CODE'][i].typeName;
				break;
			}
		}
		if (systemName == '')
			systemName = value.systemCode;
		var parentNode = value.getParentNode();
		var parentNodeName = '';
		if (parentNode != undefined) {
			parentNodeName = parentNode.aName;
		} else {
			parentNodeName = '--';
		}
		$('#addExtDataGrid').datagrid('appendRow', {
			id : value.id,
			name : value.aName,
			systemCodeText : systemName,
			parentName : parentNodeName
		});
		updatePanelTitle(1, portalMenuTreeNodeAddMap.size());
		//更新基础菜单树节点字体样式
		setBaseMenuNodeFontCss(value, true);
	});
	// 新增Portal菜单map更新元素事件
	portalMenuTreeNodeAddMap.eventListener('onUpdate', function(key, value) {
		if (value) {
			var systemName = '';
			for (var i = 0; i < DictDatas['SYSTEM_CODE'].length; i++) {
				if (DictDatas['SYSTEM_CODE'][i].typeCode == value.systemCode) {
					systemName = DictDatas['SYSTEM_CODE'][i].typeName;
					break;
				}
			}
			if (systemName == '')
				systemName = value.systemCode;
			var parentNode = value.getParentNode();
			var parentNodeName = '';
			if (parentNode != undefined) {
				parentNodeName = parentNode.aName;
			} else {
				parentNodeName = '--';
			}
			var index = $('#addExtDataGrid').datagrid('getRowIndex',
					value.id);
			if (index >= 0) {
				$('#addExtDataGrid').datagrid('updateRow', {
					index : index,
					row : {
						id : value.id,
						name : value.aName,
						systemCodeText : systemName,
						parentName : parentNodeName
					}
				});
				updatePanelTitle(1, portalMenuTreeNodeAddMap.size());
			}
		}
	});
	// 新增Portal菜单map移除元素事件
	portalMenuTreeNodeAddMap.eventListener('onRemove', function(key, value) {
		if (value) {
			var index = $('#addExtDataGrid').datagrid('getRowIndex',
					value.id);
			if (index >= 0) {
				$('#addExtDataGrid').datagrid('deleteRow', index);
				updatePanelTitle(1, portalMenuTreeNodeAddMap.size());
			}
			
			//更新基础菜单树节点字体样式
			setBaseMenuNodeFontCss(value, false);
		}
	});
	// 新增Portal菜单map清除全部元素事件
	portalMenuTreeNodeAddMap.eventListener('onClear', function() {
		mapClearCallBack('addExtDataGrid', 1);
	});

	// 更新Portal菜单map添加元素事件
	portalMenuTreeNodeUpdateMap = new MapUtil();
	portalMenuTreeNodeUpdateMap.eventListener('onPut', function(key, value) {
		var systemName = '';
		for (var i = 0; i < DictDatas['SYSTEM_CODE'].length; i++) {
			if (DictDatas['SYSTEM_CODE'][i].typeCode == value.systemCode) {
				systemName = DictDatas['SYSTEM_CODE'][i].typeName;
				break;
			}
		}
		if (systemName == '')
			systemName = value.systemCode;
		var parentNode = value.getParentNode();
		var parentNodeName = '';
		if (parentNode != undefined) {
			parentNodeName = parentNode.aName;
		} else {
			parentNodeName = '--';
		}
		$('#updateExtDataGrid').datagrid('appendRow', {
			id : value.id,
			name : value.aName,
			systemCodeText : systemName,
			parentName : parentNodeName
		});
		updatePanelTitle(2, portalMenuTreeNodeUpdateMap.size());
	});
	// 更新Portal菜单map更新元素事件
	portalMenuTreeNodeUpdateMap.eventListener('onUpdate', function(key, value) {
		if (value) {
			var systemName = '';
			for (var i = 0; i < DictDatas['SYSTEM_CODE'].length; i++) {
				if (DictDatas['SYSTEM_CODE'][i].typeCode == value.systemCode) {
					systemName = DictDatas['SYSTEM_CODE'][i].typeName;
					break;
				}
			}
			if (systemName == '')
				systemName = value.systemCode;
			var parentNode = value.getParentNode();
			var parentNodeName = '';
			if (parentNode != undefined) {
				parentNodeName = parentNode.aName;
			} else {
				parentNodeName = '--';
			}
			var index = $('#updateExtDataGrid').datagrid('getRowIndex',
					value.id);
			if (index >= 0) {
				$('#updateExtDataGrid').datagrid('updateRow', {
					index : index,
					row : {
						id : value.id,
						name : value.aName,
						systemCodeText : systemName,
						parentName : parentNodeName
					}
				});
				updatePanelTitle(2, portalMenuTreeNodeUpdateMap.size());
			}
		}
	});
	// 更新Portal菜单map移除元素事件
	portalMenuTreeNodeUpdateMap.eventListener('onRemove', function(key, value) {
		if (value) {
			var index = $('#updateExtDataGrid').datagrid('getRowIndex',
					value.id);
			if (index >= 0) {
				$('#updateExtDataGrid').datagrid('deleteRow', index);
				updatePanelTitle(2, portalMenuTreeNodeUpdateMap.size());
			}
		}
	});
	// 更新Portal菜单map清除全部元素事件
	portalMenuTreeNodeUpdateMap.eventListener('onClear', function() {
		mapClearCallBack('updateExtDataGrid', 2);
	});

	// 删除Portal菜单map添加元素事件
	portalMenuTreeNodeDeleteMap = new MapUtil();
	portalMenuTreeNodeDeleteMap.eventListener('onPut', function(key, value) {
		var idNode = value;
		var systemName = '';
		for (var i = 0; i < DictDatas['SYSTEM_CODE'].length; i++) {
			if (DictDatas['SYSTEM_CODE'][i].typeCode == idNode.systemCode) {
				systemName = DictDatas['SYSTEM_CODE'][i].typeName;
				break;
			}
		}
		if (systemName == '')
			systemName = idNode.systemCode;
		var parentNode = idNode.getParentNode();
		var parentNodeName = '';
		if (parentNode != undefined) {
			parentNodeName = parentNode.aName;
		} else {
			parentNodeName = '--';
		}
		$('#delExtDataGrid').datagrid('appendRow', {
			id : idNode.id,
			name : idNode.aName,
			systemCodeText : systemName,
			parentName : parentNodeName
		});
		updatePanelTitle(3, portalMenuTreeNodeDeleteMap.size());
		
		//更新基础菜单树节点字体样式
		setBaseMenuNodeFontCss(value, false);
	});
	// 删除Portal菜单map移除元素事件
	portalMenuTreeNodeDeleteMap.eventListener('onRemove', function(key, value) {
		if (key) {
			var index = $('#delExtDataGrid').datagrid('getRowIndex',
					key);
			if (index >= 0) {
				$('#delExtDataGrid').datagrid('deleteRow', index);
				updatePanelTitle(3, portalMenuTreeNodeDeleteMap.size());
			}
			//更新基础菜单树节点字体样式
			setBaseMenuNodeFontCss(value, true);
		}
	});
	// 删除Portal菜单map清除全部元素事件
	portalMenuTreeNodeDeleteMap.eventListener('onClear', function() {
		mapClearCallBack('delExtDataGrid', 3);
	});

	// 排序Portal菜单map添加元素事件
	portalMenuTreeNodeSortMap = new MapUtil();
	portalMenuTreeNodeSortMap.eventListener('onPut', function(key, value) {
		var id, name;
		var systemName = '';
		var parentNodeName = '';
		if (key == '00') {
			id = "00";
			name = '全部一级菜单';
			systemName = '--';
			parentNodeName = '--';
		} else {
			var idNode = extZTree.getNodeByParam("tId", key, null);
			id = idNode.tId;
			name = idNode.aName;
			for (var i = 0; i < DictDatas['SYSTEM_CODE'].length; i++) {
				if (DictDatas['SYSTEM_CODE'][i].typeCode == idNode.systemCode) {
					systemName = DictDatas['SYSTEM_CODE'][i].typeName;
					break;
				}
			}
			if (systemName == '')
				systemName = idNode.systemCode;
			var parentNode = idNode.getParentNode();
			if (parentNode != undefined) {
				parentNodeName = parentNode.aName;
			} else {
				parentNodeName = '--';
			}
		}

		$('#sortExtDataGrid').datagrid('appendRow', {
			id : id,
			name : name,
			systemCodeText : systemName,
			parentName : parentNodeName
		});
		updatePanelTitle(4, portalMenuTreeNodeSortMap.size());
	});
	// 排序Portal菜单map移除元素事件
	portalMenuTreeNodeSortMap.eventListener('onRemove', function(key, value) {
		if (key) {
			var index = $('#sortExtDataGrid').datagrid('getRowIndex',
					key);
			if (index >= 0) {
				$('#sortExtDataGrid').datagrid('deleteRow', index);
				updatePanelTitle(4, portalMenuTreeNodeSortMap.size());
			}
		}
	});
	// 排序Portal菜单map清除全部元素事件
	portalMenuTreeNodeSortMap.eventListener('onClear', function() {
		mapClearCallBack('sortExtDataGrid', 4);
	});

});

// ********baseMenu**********************基础树拖拽处理部分**********************************

/**
 * 基础菜单树拖拽事件处理
 * 
 * 1、节点拖拽操作结束之前的事件回调函数	:	baseMenuTreeBeforeDrop 
 * 2、节点拖拽操作结束的事件回调函数		:	baseMenuTreeOnDrop 
 * 3、基础菜单搜索事件函数				:	baseMenuSearch 
 * 4、基础菜单加载成功回调函数			:	baseMenuTreeLoadSuccess
 * 5、基础菜单树节点获取自定义字体样式函数	:	getFont
 * 6、捕获节点被拖拽之前的事件回调函数	：	baseMenuTreeBeforeDrag
 * 7、设置基础菜单树节点字体			:	setBaseMenuNodeFontCss
 */
// 1、节点拖拽操作结束之前的事件回调函数
function baseMenuTreeBeforeDrop(treeId, treeNodes, targetNode, moveType) {
	// 不能移动到自己的任何节点
	if (baseZTree.setting.treeId == treeId) {
		return false;
	}
	//只能对存在url的节点进行拖动
	for(var i = 0; i < treeNodes.length; i++) {
		if (treeNodes[i].permissionUrl == null || treeNodes[i].permissionUrl == "" || treeNodes[i].children != null) {
			showInfoMsg("不能拖动非菜单节点!");
			return false;
		}
	}
	
	//不能拖动节点到有效菜单节点上
	if (moveType == 'inner' && targetNode != null 
			&& targetNode.permissionUrl != null && targetNode.permissionUrl != ""){
		showInfoMsg("不能拖动到有效菜单节点上!");
		return false;
	}else if ((moveType == 'inner' && targetNode == null) //不能拖动到顶级节点校验
			|| (moveType != 'inner' && targetNode != null && targetNode.pid == null)) {
		showInfoMsg("不能拖动到顶级节点!");
		return false;
	}
	
	var targetZtree = $.fn.zTree.getZTreeObj(treeId);
	// 判断拖拽的节点中是否在目标树上存在
	for (i = 0; i < treeNodes.length; i++) {
		var node = targetZtree.getNodeByParam("id", treeNodes[i].id);
		if (node) {
			showErrorMsg('Portal菜单中已存在【' + treeNodes[i].name + '】菜单！');
			return false;
		}
	}

	return true;
}
// 2、节点拖拽操作结束的事件回调函数
function baseMenuTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
		if (baseZTree.setting.treeId != treeId
			&& (targetNode != null || moveType != null)) {
		/*if (shiftKey) {
			treeNodes.forEach(function(treeNode, index) {
				extZTree.removeChildNodes(treeNode);
			});
		}*/
		// 显示第一个选中的菜单信息
		showPortalMenuInfo(treeNodes[0]);
		var childNodeArrays = [];

		treeNodes.forEach(function(treeNode, index) {
			childNodeArrays = getAllChildrenNodes(treeNode,
					childNodeArrays);
			if (treeNode.pid != null && treeNode.pid != 0) {
				if (!portalMenuTreeNodeSortMap
						.hasKey(treeNode.parentTId)) {
					portalMenuTreeNodeSortMap.put(treeNode.parentTId,
							treeNode.parentTId);
				}
			} else {
				if (!portalMenuTreeNodeSortMap.hasKey("00")) {
					portalMenuTreeNodeSortMap.put("00", "00");
				}
			}
		});
		childNodeArrays.forEach(function(childNode) {
			if (portalMenuTreeNodeDeleteMap.hasKey(childNode.id)) {
				portalMenuTreeNodeDeleteMap.remove(childNode.id);
				portalMenuTreeNodeUpdateMap.put(childNode.id, childNode);
			} else {
				portalMenuTreeNodeAddMap.put(childNode.tId, childNode);

			}
			if (childNode.children) {
				if (!portalMenuTreeNodeSortMap.hasKey(childNode.tId)) {
					portalMenuTreeNodeSortMap.put(childNode.tId, childNode.tId);
				}
			}
		});
	}
}

// 3、基础菜单搜索事件函数 : baseMenuSearch
var searchBOValue = '';
var searchBONodes = [];
var searchBOIndex = 0;
function baseMenuSearch(searchCondition) {
	if (searchCondition == "") {
		return;
	}
	// 由于异步加载如果打开的时候tree还没初始化好，此时treeObj为null
	if (baseZTree && baseZTree != null) {
		if (searchBOValue != searchCondition) {
			var idNode = baseZTree.getNodeByParam("id", searchCondition, null);
			searchBONodes = baseZTree.getNodesByParamFuzzy('name',
					searchCondition, null);
			if (idNode && idNode != null) {
				searchBONodes.push(idNode);
			}
			searchBOIndex = 0;
			searchBOValue = searchCondition;
		}
		baseZTree.selectNode(searchBONodes[searchBOIndex]);
		searchBOIndex = searchBOIndex == searchBONodes.length - 1
				? 0
				: (searchBOIndex + 1);
	}
}

//4、基础菜单加载成功回调函数			:	baseMenuTreeLoadSuccess
function baseMenuTreeLoadSuccess(event, treeId, treeNode, msg) {
	menuTreeLoadAfterDeal();
}

//5、基础菜单树自定义字体样式回调函数	:	getFont
function getFont(treeId, node) {
	return node.font ? node.font : {};
}

//6、捕获节点被拖拽之前的事件回调函数	：	baseMenuTreeBeforeDrag
function baseMenuTreeBeforeDrag(treeId, treeNodes) {
	for (var i = 0; i < treeNodes.length; i++) {
		if (treeNodes[i].atPortalMenu) {
			return  false;
		}
	}
}

//7、设置基础菜单树节点字体			:	setBaseMenuNodeFontCss
function setBaseMenuNodeFontCss(node, flag){
	if (baseZTree == null) return;
	var nodes = baseZTree.getNodesByParam("id", node.id, null);
	if (nodes != null && nodes.length > 0) {
		if (flag) {
			nodes[0].font = {'color':'red'};
			nodes[0].atPortalMenu = true;
			baseZTree.updateNode(nodes[0]);
		}else {
			nodes[0].font = {'color':'#333'};
			nodes[0].atPortalMenu = null;
			baseZTree.updateNode(nodes[0]);
		}
	}
}

// *******portalMenuBtn***********************扩展树处理部分******************************************//
/**
 * 菜单按钮事件
 * 
 * 1、删除按钮事件 				:	portalMenuRemoveClick 
 * 2、新增虚拟菜单按钮事件			:	portalMenuTreeAddClick
 * 3、新增虚拟菜单窗口确定按钮事件	:	addExtPortalMenuConfirm 
 * 4、更新菜单按钮事件				:	portalMenuTreeUpdateClick 
 * 5、更新窗口确认按钮确认事件		:	updatePortalMenuConfirm 
 * 
 */
// 1、删除按钮事件
function portalMenuRemoveClick(e) {
	$('#extTreeMenu').menu('hide');
	var nodes = extZTree.getSelectedNodes();
	for (var i = 0; i < nodes.length; i++) {
		if (nodes[i].systemCode == "MENU") {
			showInfoMsg("菜单【" + nodes[i].aName + "】是Portal菜单项，不能删除！");
			return;
		}
	}
	confirmMsg('是否要移除选中的菜单？', function() {
		removePortalMenuTreeNode(nodes);
	});
}

// 2、新增虚拟菜单按钮事件
function portalMenuTreeAddClick(e) {
	/*
	 * $('#extTreeMenu').menu('hide'); //初使化虚拟菜单类型 var treeNode =
	 * e.data.treeNode; //控件可编辑状态设定 $('#portalMenuName').textbox('enable');
	 * $('#portalMenuAliasName').textbox('disable');
	 * $('#extRemark').textbox('enable');
	 * 
	 * $('#formUpdateWin').form('clear');
	 * $('#confirmAddNode').off('click').on('click', {treeNode : treeNode},
	 * addExtPortalMenuConfirm); if (treeNode) {
	 * $('#portalMenuParent').textbox('setValue', treeNode.name); }
	 * $("input",$("#portalMenuName").next("span")).blur(function(){ var
	 * portalMenuName = $('#portalMenuName').textbox('getValue');
	 * $('#portalMenuAliasName').textbox('setValue', portalMenuName); });
	 * openDialog('menuUpdateWin', '新增虚拟菜单节点');
	 * $('#portalMenuName').textbox('textbox').focus();
	 */
}

// 3、新增虚拟菜单窗口确定按钮事件回调函数
function addExtPortalMenuConfirm(e) {
	/*
	 * if($("#formUpdateWin").form('enableValidation').form('validate')){ var
	 * portalMenuName = $('#portalMenuName').textbox('getValue'); var
	 * portalMenuAliasName = $('#portalMenuAliasName').textbox('getValue'); var
	 * extRemark = $('#extRemark').textbox('getValue'); var newNode = {name:
	 * portalMenuName, aName: portalMenuAliasName, systemCode: systemCode,
	 * remark: extRemark, vof: true}; newNode =
	 * extZTree.addNodes(e.data.treeNode, newNode); newNode =
	 * extZTree.getNodeByTId(newNode[0].tId); newNode.boc = newNode.tId; if
	 * (!portalMenuTreeNodeAddMap.hasKey(newNode.tId)) {
	 * portalMenuTreeNodeAddMap.put(newNode.tId, newNode); } if
	 * (newNode.parentTId != null &&
	 * !portalMenuTreeNodeSortMap.hasKey(newNode.parentTId)) {
	 * portalMenuTreeNodeSortMap.put(newNode.parentTId, newNode.parentTId); }
	 * 
	 * closeDialog('menuUpdateWin'); }
	 */
}

// 4、更新菜单按钮事件
function portalMenuTreeUpdateClick(e) {
	var treeNode = e.data.treeNode;
	$('#extTreeMenu').menu('hide');
	openDialog('menuUpdateWin', '更新Portal菜单');
	// 初使化系统类型
	uceDictCombobox("systemTypeWin", "SYSTEM_CODE");

	// 控件可编辑状态设定
	$('#portalMenuNameWin').textbox('disable');
	$('#portalMenuAliasNameWin').textbox('enable');
	$('#systemTypeWin').combobox('disable');
	$('#portalMenuParentWin').textbox('disable');

	// 设置值
	var parent = treeNode.getParentNode();
	$('#portalMenuNameWin').textbox('setValue', treeNode.name);
	$('#portalMenuAliasNameWin').textbox('setValue', treeNode.aName);
	$('#systemTypeWin').combobox('setValue', treeNode.systemCode);

	$('#confirmAddNode').off('click').on('click', {
				treeNode : treeNode
			}, updatePortalMenuConfirm);
	if (treeNode) {
		if (parent != null && parent != undefined) {
			$('#portalMenuParentWin').textbox('setValue', parent.aName);
		}
	}
}

// 5、更新窗口确认按钮确认事件回调函数
function updatePortalMenuConfirm(e) {
	if ($("#formUpdateWin").form('enableValidation').form('validate')) {
		var treeNode = e.data.treeNode;
		var portalMenuAliasName = $('#portalMenuAliasNameWin')
				.textbox('getValue');
		treeNode.aName = portalMenuAliasName;
		// 更新菜单节点
		extZTree.updateNode(treeNode);
		// 记录修改节点
		if (!portalMenuTreeNodeAddMap.hasKey(treeNode.tId)
				&& !portalMenuTreeNodeUpdateMap.hasKey(treeNode.id)) {
			portalMenuTreeNodeUpdateMap.put(treeNode.id, treeNode);
		} else if (!portalMenuTreeNodeAddMap.hasKey(treeNode.tId)
				&& portalMenuTreeNodeUpdateMap.hasKey(treeNode.id)) {
			portalMenuTreeNodeUpdateMap.update(treeNode.id, treeNode);
		} else if (portalMenuTreeNodeAddMap.hasKey(treeNode.tId)) {
			portalMenuTreeNodeAddMap.update(treeNode.tId, treeNode);
		}

		showPortalMenuInfo(treeNode);
	}

	closeDialog('menuUpdateWin');
}

/**
 * 工具栏按钮事件
 * 
 * 1、保存Portal菜单树按钮 : savePortalMenuTreeClick 
 * 2、重置Portal菜单树按钮 : reloadPortalMenuTreeClick
 * 
 */
// 1、保存Portal菜单树按钮
function savePortalMenuTreeClick() {
	var result = saveportalMenuTree();
	if (result) {
		// $.fn.zTree.destroy("portalMenuzTree");
		// extZTree = $.fn.zTree.init($("#portalMenuzTree"),
		// portalMenuTreeSetting, null);
		reloadPortalMenuTree();
	}
}

// 2、重置Portal菜单树按钮 : reloadPortalMenuTreeClick
function reloadPortalMenuTreeClick() {
	confirmMsg("是否要新加载Portal菜单？", function() {
		// 清空MAP
		portalMenuTreeNodeAddMap.clear();
		portalMenuTreeNodeSortMap.clear();
		portalMenuTreeNodeDeleteMap.clear();
		portalMenuTreeNodeUpdateMap.clear();
		// 重新加载Portal菜单
		// $.fn.zTree.destroy("portalMenuzTree");
		// extZTree = $.fn.zTree.init($("#portalMenuzTree"),
		// portalMenuTreeSetting, null);
		reloadPortalMenuTree();
	});

}

/*****************portalMenuTree********************************************************
 * 扩展树事件处理
 * 
 * 1、鼠标移入添加用户自定义删除控件		：	portalMenuTreeAddHoverDom 
 * 2、鼠标移出隐藏用户自定义删除控件		：	portalMenuTreeRemoveHoverDom 
 * 3、用户自定义删除控件事件回调函数		：	portalMenuDeleteCallBack
 * 4、Portal菜单树右键事件回调函数		:	extTreeOnRightClick 
 * 5、捕获节点被拖拽之前的事件回调函数	：	portalMenuTreeBeforeDrag 
 * 6、节点拖拽操作结束之前的事件回调函数	：	portalMenuTreeBeforeDrop
 * 7、节点拖拽操作结束的事件回调函数		：	portalMenuTreeOnDrop 
 * 8、Portal菜单树节点点击回调函数		：	portalMenuTreeNodeClick 
 * 9、Portal菜单搜索事件函数 			:	portalMenuSearch
 * 10、portal菜单加载成功后的回调函数	:	portalMenuTreeLoadSuccess
 * 
 */

// 1、扩展树添加删除按钮
var IDMark_A = "_a";
function portalMenuTreeAddHoverDom(treeId, treeNode) {
	if (treeNode.systemCode == "MENU") return;
	var aObj = $("#" + treeNode.tId + IDMark_A);
	if ($("#diyBtn_" + treeNode.id).length > 0)
		return;
	var editStr = '<span id="diyBtn_'
			+ treeNode.id
			+ '" class="button remove" onfocus="this.blur();"></span>';
	aObj.after(editStr);
	var btn = $("#diyBtn_" + treeNode.id);
	if (btn)
		btn.on("click", {
					treeId : treeId,
					treeNode : treeNode
				}, portalMenuDeleteCallBack);
}

// 2、扩展树鼠标移开隐藏删除按钮
function portalMenuTreeRemoveHoverDom(treeId, treeNode) {
	$("#diyBtn_" + treeNode.id).unbind().remove();
	$("#diyBtn_space_" + treeNode.id).unbind().remove();
}

// 3、扩展树删除按钮事件回调函数
function portalMenuDeleteCallBack(e) {
	if (e.data.treeNode.systemCode == "MENU") {
		showInfoMsg("Portal菜单项不能删除！");
		return;
	}
	confirmMsg('是否要移除菜单【' + e.data.treeNode.name + '】', function() {
		var nodeArray = [];
		nodeArray.push(e.data.treeNode);
		removePortalMenuTreeNode(nodeArray);
	});
}

// 4、Portal菜单树右键，弹出快捷菜单
function extTreeOnRightClick(e, treeId, treeNode) {
	e.preventDefault();
	// 新增虚拟菜单事件绑定
	// $('#treeMenuAdd').off('click').on('click',{treeNode: treeNode},
	// portalMenuTreeAddClick);
	var selectFlag = false;
	var selectNodes = extZTree.getSelectedNodes();
	selectNodes.forEach(function(node, index) {
		if (node.tId == treeNode.tId) {
			selectFlag = true;
		}
	});
	if (!selectFlag)
		extZTree.cancelSelectedNode();
	if (!treeNode) {
		// $('#treeMenuUpdate').hide();
		// $('#treeMenuDel').hide();
		// 解除事件绑定,删除按钮事件已在JSP中添加
		// $('#treeMenuUpdate').off('click');
	} else {
		$('#treeMenuUpdate').show();
		$('#treeMenuDel').show();
		extZTree.selectNode(treeNode, true);
		// 显示第一个选中的菜单信息
		showPortalMenuInfo(treeNode);
		// 动态绑定事件，传递点击的treeNode
		$('#treeMenuUpdate').off('click').on('click', {
				treeNode : treeNode
			}, portalMenuTreeUpdateClick);
		$('#extTreeMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
	}
}

// 5、捕获节点被拖拽之前的事件回调函数
function portalMenuTreeBeforeDrag(treeId, treeNodes) {
	return true;
}

// 6、节点拖拽操作结束之前的事件回调函数
function portalMenuTreeBeforeDrop(treeId, treeNodes, targetNode, moveType) {
	// 判断是否拖拽到其它树上
	if (extZTree.setting.treeId != treeId) {
		return false;
	}
	for (var i = 0; i < treeNodes.length; i++) {
		if (treeNodes[i].systemCode == 'MENU'
				&& (moveType == 'inner' || (treeNodes[i].parentTId != targetNode.parentTId && moveType != 'inner'))) {
			showInfoMsg("Portal菜单项不能改变父亲节点！");
			return false;
		}
	}
	treeNodes.forEach(function(treeNode) {
		// 如果是成为目标节点的子节点
		if (moveType == 'inner') {
			if (!portalMenuTreeNodeSortMap.hasKey(targetNode.tId)) {
				portalMenuTreeNodeSortMap.put(targetNode.tId, targetNode.tId);
			}
			if (treeNode.parentTId != targetNode.tId) {
				if (treeNode.parentTId != null
						&& !portalMenuTreeNodeSortMap
								.hasKey(treeNode.parentTId)) {
					portalMenuTreeNodeSortMap.put(treeNode.parentTId,
							treeNode.parentTId);
				} else if (!portalMenuTreeNodeSortMap.hasKey('00')) {
					portalMenuTreeNodeSortMap.put("00", "00");
				}
				if (!portalMenuTreeNodeAddMap.hasKey(treeNode.tId)
						&& !portalMenuTreeNodeUpdateMap.hasKey(treeNode.id)) {
					portalMenuTreeNodeUpdateMap.put(treeNode.id, treeNode);
				}
			}
		} else {// 如果是拖动到某个节点的上方或下方
			if (targetNode.parentTId != null
					&& !portalMenuTreeNodeSortMap.hasKey(targetNode.parentTId)) {
				portalMenuTreeNodeSortMap.put(targetNode.parentTId,
						targetNode.parentTId);
			} else if (!portalMenuTreeNodeSortMap.hasKey('00')) {
				portalMenuTreeNodeSortMap.put("00", "00");
			}
			if (treeNode.parentTId != targetNode.parentTId) {
				if (treeNode.parentTId != null
						&& !portalMenuTreeNodeSortMap
								.hasKey(treeNode.parentTId)) {
					portalMenuTreeNodeSortMap.put(treeNode.parentTId,
							treeNode.parentTId);
				} else if (!portalMenuTreeNodeSortMap.hasKey('00')) {
					portalMenuTreeNodeSortMap.put("00", "00");
				}
				if (!portalMenuTreeNodeAddMap.hasKey(treeNode.tId)
						&& !portalMenuTreeNodeUpdateMap.hasKey(treeNode.id)) {
					portalMenuTreeNodeUpdateMap.put(treeNode.id, treeNode);
				}
			}
		}
	});

	return true;
}

// 7、节点拖拽操作结束的事件回调函数
function portalMenuTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
	// 显示拖拽节点中的第一个Portal菜单信息
	showPortalMenuInfo(treeNodes[0]);
	var childNodeArrays = [];
	treeNodes.forEach(function(treeNode) {
		childNodeArrays = getAllChildrenNodes(treeNode, childNodeArrays);
		childNodeArrays.forEach(function(childNode) {
			if (portalMenuTreeNodeAddMap.hasKey(childNode.tId)) {
				portalMenuTreeNodeAddMap.update(childNode.tId,
						childNode);
			} else if (portalMenuTreeNodeUpdateMap.hasKey(childNode.id)) {
				portalMenuTreeNodeUpdateMap.update(childNode.id,
						childNode)
			}
		});
	});
}

// 8、Portal菜单树节点点击回调函数
function portalMenuTreeNodeClick(event, treeId, treeNode) {
	showPortalMenuInfo(extZTree.getSelectedNodes()[0]);
}

// 9、基础菜单搜索事件函数 : portalMenuSearch
var searchEOValue = '';
var searchEONodes = [];
var searchEOIndex = 0;
function portalMenuSearch(searchCondition) {
	if (searchCondition == "") {
		return;
	}
	// 由于异步加载如果打开的时候tree还没初始化好，此时treeObj为null
	if (extZTree && extZTree != null) {
		if (searchEOValue != searchCondition) {
			var idNode = extZTree.getNodeByParam("id", searchCondition, null);
			searchEONodes = extZTree.getNodesByParamFuzzy('name',
					searchCondition, null);
			if (idNode && idNode != null) {
				searchEONodes.push(idNode);
			}
			searchEOIndex = 0;
			searchEOValue = searchCondition;
		}
		extZTree.selectNode(searchEONodes[searchEOIndex]);
		showPortalMenuInfo(searchEONodes[searchEOIndex]);
		searchEOIndex = searchEOIndex == (searchEONodes.length - 1)
				? 0
				: (searchEOIndex + 1);
	}
}

//10、portal菜单加载成功后的回调函数	:	portalMenuTreeLoadSuccess
function portalMenuTreeLoadSuccess(event, treeId, treeNode, msg) {
	menuTreeLoadAfterDeal();
}

//***********publicMethod******************公共方法**************************************//
/**
 * 1、保存Portal菜单树变更						:	saveportalMenuTree 
 * 2、删除菜单方法								:	removePortalMenuTreeNode
 * 3、获取某节点以及其所有子节点					:	getAllChildrenNodes 
 * 4、获取某节点的父节点以及下面所有子节点的父节点	：	getAllParentNodes 
 * 5、获取某节点的全称 						:	getMenuFullName 
 * 6、在右边显示区域显示指定Portal菜单信息		:	showPortalMenuInfo 
 * 7、判断一个对象是否为空						:	isEmptyObject 
 * 8、zTree异步加载数据时的错误处理				:	zTreeOnAsyncError 
 * 9、重新加载Portal菜单树						:	reloadportalMenuTree 
 * 10、重新加载基础菜单树						:	reloadBaseMenuTree 
 * 11、更新信息面板标题						:	updatePanelTitle 
 * 12、转换树形节点为后台可识别的JSON对象			:	portalMenu 
 * 13、保存各变更点MAP清除后的事件回调			:	mapClearCallBack
 * 14、菜单树加载成功后的处理					:	menuTreeLoadAfterDeal
 */

// 1、保存Portal菜单树变更
function saveportalMenuTree() {

	if (portalMenuTreeNodeAddMap.isEmpty()
			&& portalMenuTreeNodeSortMap.isEmpty()
			&& portalMenuTreeNodeUpdateMap.isEmpty()
			&& portalMenuTreeNodeDeleteMap.isEmpty()) {
		showInfoMsg("Portal菜单没有变更,无法保存！");
		return;
	}

	var result = false;
	var addStr = "";
	var sortStr = "";
	var deleteStr = "";
	var updateStr = "";
	var addArrays = [];
	var updateArrays = [];
	var delArrays = [];

	if (!portalMenuTreeNodeAddMap.isEmpty()) {
		var tempMap = portalMenuTreeNodeAddMap.getAllElements();
		for (var i in tempMap) {
			addArrays.push(new portalMenu(tempMap[i]));
		}
	}
	if (!portalMenuTreeNodeSortMap.isEmpty()) {
		var tempMap = portalMenuTreeNodeSortMap.getAllElements();
		for (var i in tempMap) {
			var nodes;
			if (i == '00') {
				nodes = extZTree.getNodesByParam("parentTId", null, null);
			} else {
				nodes = extZTree.getNodesByParam("parentTId", i, null);
			}
			nodes.forEach(function(node) {// 查询上级 所有的进行排序
						sortStr += node.permissionCode + "+" + node.systemCode
								+ ",";
					});
			sortStr += "A";
		}
	}
	if (!portalMenuTreeNodeUpdateMap.isEmpty()) {
		var tempMap = portalMenuTreeNodeUpdateMap.getAllElements();
		for (var i in tempMap) {
			updateArrays.push(new portalMenu(tempMap[i]));
		}
	}
	if (!portalMenuTreeNodeDeleteMap.isEmpty()) {
		var tempMap = portalMenuTreeNodeDeleteMap.getAllElements();
		for (var i in tempMap) {
			var node = tempMap[i];
			if (node != undefined) {
				delArrays.push(new portalMenu(node));
			}
		}
	}

	if (addArrays.length > 0) {
		addStr = JSON.stringify(addArrays);
	}
	if (updateArrays.length > 0) {
		updateStr = JSON.stringify(updateArrays);
	}
	if (delArrays.length > 0) {
		deleteStr = JSON.stringify(delArrays);
	}

	var data = {
		addStr : addStr,
		sortStr : sortStr,
		updateStr : updateStr,
		deleteStr : deleteStr
	};
	
	$.ajax({
		url : 'saveProtalMenuTree.do',
		data : {
			"addStr" : addStr,
			"sortStr" : sortStr,
			"updateStr" : updateStr,
			"deleteStr" : deleteStr
		},
		type : 'POST',
		async : false,
		success : function(pdata) {
			if(pdata != null && pdata.data != null){
				 if(pdata.data == 1) {
					showTips("保存成功",'success');
					result = true;
					//清空MAP
					portalMenuTreeNodeAddMap.clear();
					portalMenuTreeNodeSortMap.clear();
					portalMenuTreeNodeDeleteMap.clear();
					portalMenuTreeNodeUpdateMap.clear();
				 } else {
					 showErrorMsg("保存失败！");
				 }
			}
		}
	});

	return result;
}

// 2、删除菜单方法
function removePortalMenuTreeNode(nodes) {

	var success = true;
	if (nodes && nodes.length > 0) {
		$(nodes).each(function(i, node) {
			if (node.pid != null
					&& node.pid != "0"
					&& !portalMenuTreeNodeSortMap.hasKey(node.parentTId)
					&& !portalMenuTreeNodeDeleteMap
							.hasKey(node.getParentNode().id)) {
				portalMenuTreeNodeSortMap.put(node.parentTId, node.parentTId);
			}else if ((node.pid == null || node.pid == 0) && !portalMenuTreeNodeSortMap.hasKey("00")) {
				portalMenuTreeNodeSortMap.put('00', '00');
			}
			var nodeList = getAllChildrenNodes(node, []);
			nodeList.forEach(function(node) {
				if (portalMenuTreeNodeAddMap.hasKey(node.tId)) {
					portalMenuTreeNodeAddMap.remove(node.tId);
				} else {
					portalMenuTreeNodeDeleteMap.put(node.id, node);
				}

				if (portalMenuTreeNodeUpdateMap.hasKey(node.id)) {
					portalMenuTreeNodeUpdateMap.remove(node.id);
				}

				if (portalMenuTreeNodeSortMap.hasKey(node.tId)) {
					portalMenuTreeNodeSortMap.remove(node.tId);
				}
			});
		});

		$(nodes).each(function(i, val) {
			extZTree.removeNode(val);
		});
	}
}

// 3、获取某节点以及其所有子节点
function getAllChildrenNodes(treeNode, nodeArrays) {
	nodeArrays.push(treeNode);
	if (treeNode.isParent) {
		var childrenNodes = treeNode.children;
		if (childrenNodes) {
			for (var i = 0; i < childrenNodes.length; i++) {
				nodeArrays = getAllChildrenNodes(childrenNodes[i], nodeArrays);
			}
		}
	}

	return nodeArrays;
}

// 4、获取某节点的父节点以及下面所有子节点的父节点
function getAllParentNodes(treeNode, nodeArrays) {
	if (treeNode.isParent) {
		var childrenNodes = treeNode.children;
		if (childrenNodes) {
			nodeArrays.push(treeNode);
			for (var i = 0; i < childrenNodes.length; i++) {
				nodeArrays = getAllParentNodes(childrenNodes[i], nodeArrays);
			}
		}
	}

	return nodeArrays;
}

// 5、获取某节点的全称
function getMenuFullName(treeNode, fullName) {
	if (treeNode == null || treeNode == undefined) {
		return fullName;
	}
	if (fullName == null || fullName == "") {
		fullName = treeNode.aName;
	} else {
		fullName = treeNode.aName + "-" + fullName;
	}
	if (treeNode.pid != null && treeNode.pid != "0") {
		fullName = getMenuFullName(treeNode.getParentNode(), fullName);
	}
	return fullName;
}

// 6、在右边显示区域显示指定Portal菜单信息
function showPortalMenuInfo(treeNode) {
	if (treeNode == null) {
		$("#portalMenuName").textbox("setValue", '');
		$("#portalMenuAliasName").textbox("setValue", '');
		$("#systemCodeView").combobox('setValue', '');
		$("#parentMenu").textbox("setValue", '');
		$("#permissionUrlView").textbox("setValue", '');
		return;
	}
	$("#portalMenuName").textbox("setValue", treeNode.name);
	if (treeNode.aName == '' || treeNode.aName == null
			|| treeNode.aName == undefined) {
		$("#portalMenuAliasName").textbox("setValue", treeNode.name);
	} else {
		$("#portalMenuAliasName").textbox("setValue", treeNode.aName);
	}

	$("#systemCodeView").combobox('setValue', treeNode.systemCode);
	if (treeNode.pid == null || treeNode.pid == "0") {
		$("#parentMenu").textbox("setValue", '');
	} else {
		$("#parentMenu").textbox("setValue", treeNode.getParentNode().aName);
	}
	
	$("#permissionUrlView").textbox("setValue", treeNode.permissionUrl);
}

// 7、判断一个对象是否为空
function isEmptyObject(e) {
	var t;
	for (var i = 0; i < e.length; i++) {
		return !1;
	}
	return !0
}

// 8、zTree异步加载数据时的错误处理
function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
		errorThrown) {
	if (XMLHttpRequest.status == 606) {
		window.location.href = XMLHttpRequest.getResponseHeader("Location");
	} else if (XMLHttpRequest.status == 'timeout') {
		errorTip('请求超时，请检查网络是否正常');
	} else if (XMLHttpRequest.status == 'parsererror') {
		errorTip('数据解析异常');
	}
}

// 9、重新加载Portal菜单树
function reloadPortalMenuTree() {
	$.fn.zTree.destroy("portalMenuzTree");
	extZTree = $.fn.zTree.init($("#portalMenuzTree"), portalMenuTreeSetting,
			null);
	showPortalMenuInfo(null);
	//重置搜索
	searchEOValue = '';
	searchEONodes = [];
	searchEOIndex = 0;
}

// 10、重新加载基础菜单树
function reloadBaseMenuTree() {
	$.fn.zTree.destroy("baseMenuzTree");
	baseZTree = $.fn.zTree.init($("#baseMenuzTree"), baseMenuTreeSetting, null);
}

// 11、更新信息面板标题
function updatePanelTitle(index, count) {
	var p = $('#infoPanel').accordion('getPanel', index);
	if (p) {
		var titleDom = p.parent().find('.panel-title');
		var titleStr = titleDom.html();
		var reg = new RegExp('\\((.+?)\\)', "g");
		titleDom.html(titleStr.replace(reg, '(' + count + ')'));
	}
}

// 12、转换树形节点为后台可识别的JSON对象
function portalMenu(node, portalMenuTreeType) {
	this.id = node.id;
	this.parentPermission = node.pid;
	this.permissionCode = node.permissionCode;
	this.systemCode = node.systemCode;
	if (node.name != node.aName) {
		this.permissionAliasName = node.aName;
	}else {
		this.permissionAliasName = null;
	}
}

// 13、保存各变更点MAP清除后的事件回调
function mapClearCallBack(tableName, accordionIndex) {
	$('#' + tableName).datagrid('loadData', {
		total : 0,
		rows : []
	});
	updatePanelTitle(accordionIndex, 0);
}

//14、菜单树加载成功后的处理					:	menuTreeLoadAfterDeal
function menuTreeLoadAfterDeal(){
	if (extZTree != null && baseZTree != null) {
		var nodes = extZTree.transformToArray(extZTree.getNodes());
		nodes.forEach(function(node, index) {
			if (node.systemCode != "MENU") {
				var baseTreeNode = baseZTree.getNodesByParam("id", node.id, null);
				if (baseTreeNode != null && baseTreeNode.length > 0) {
					setBaseMenuNodeFontCss(baseTreeNode[0], true);
				}
			}
		});
	}
}