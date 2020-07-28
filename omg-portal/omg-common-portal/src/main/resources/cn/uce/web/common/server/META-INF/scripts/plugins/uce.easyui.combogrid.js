(function($){
		var combogridPlugin = $.fn.combogrid;
		$.fn.uceCombogrid = function(options, param){
	    	if(typeof options == "string"){
		    	return $.fn.combogrid.apply(this,arguments);
	    	}
	    	options = options||{};
	    	return this.each(function(){
	    		var uceOpts = $.extend({},$.fn.uceCombogrid.defaults,$.fn.uceCombogrid.parseOptions(this), options);
	    		if (!uceOpts.filterFields) {
	    			uceOpts.filterFields = uceOpts.textField;
	    		}
	    		if (typeof uceOpts.filterFields == "string") {
	    			uceOpts.filterFields = [uceOpts.filterFields];
	    		}
	    		$.fn.combogrid.call($(this), uceOpts);
	    	});
    	};
	    $.fn.uceCombogrid.methods = combogridPlugin.methods;
	    $.fn.uceCombogrid.parseData = combogridPlugin.parseData;
	  //解析自定义属性：queryParamName-当mode = 'remote'时，用户搜索数据时，grid重新请求数据的参数名称
	    //<!-- filterFields : 输入时，根据什么字段过滤 -->
	    //<!-- limitToList : 限制只能选择列表内的数据，默认true -->
	    // <!-- frontPage : 在前端分页，后台load data时加载所有数据，只在mode != 'remote'时有效 -->
	    //queryMinCharLength: 当输入字符长度达到最小值时，才发起后台查询，默认为3
	    $.fn.uceCombogrid.parseOptions = function(options){
			 return $.extend({}, combogridPlugin.parseOptions(options), $.parser.parseOptions(options,["queryParamName", "limitToList", "filterFields", "frontPage", 'queryMinCharLength']));
		}
	   
		$.fn.uceCombogrid.defaults = $.extend({}, $.fn.combogrid.defaults,{
			queryParamName : "keyword", //设置默认请求参数名称：keyword
			limitToList : true, //默认设置limitToList = true， 只允许选择列表内的数据
			frontPage : true,
			queryMinCharLength : 1,
			//当mode = 'local'时，在前端分页，只显示当页数据
			loadFilter : function(data) {
				var dg = $(this);
				return pageData(dg, data);
			},
			keyHandler : $.extend({}, combogridPlugin.defaults.keyHandler, {
				//用户输入时，过滤数据
				query : function(keyword, e) {
					var target = e.data.target;
					var combo = $(target);
					var mode = combo.uceCombogrid("options").mode;// 是否远程请求数据
					query(combo, keyword);
					if (mode != 'remote') {
						if (combogridPlugin.defaults.keyHandler && combogridPlugin.defaults.keyHandler.query) {
							combogridPlugin.defaults.keyHandler.query.call(this, keyword, e);
						}
					}
				}
			}) ,
			filter : function(keyword, row) {
				return filter(this, keyword, row);
			},
			//panel隐藏时，判断选择的数据是否是在列表内，如果不在列表内，清空文本框
			onHidePanel : function() {
				limitValueInCombogridList(this);
				if (combogridPlugin.onHidePanel) {
					combogridPlugin.onHidePanel.call(this);
				}
			}
		});
		
		function pageData(dg, data) {
			var options = dg.datagrid("options");
			var mode = options.mode;// 是否远程请求数据
            var frontPage = options.frontPage;// 是否在前端实现分页功能
			if (mode != 'remote' && frontPage) {
				if (typeof data.length == 'number' && typeof data.splice == 'function') {
	                data = {
	                    total: data.length,
	                    rows: data
	                }
	            }
				var pager = dg.datagrid('getPager');
				//设置分页数、每页显示记录条数
	            pager.pagination({
	                onSelectPage:function(pageNum, pageSize){
	                	options.pageNumber = pageNum;
	                    pager.pagination('refresh',{
	                        pageNumber:pageNum,
	                        pageSize:pageSize
	                    });
	                    dg.datagrid('loadData',data);
	                }
	            });
	            if (!$.data(dg[0], 'allRows')) {//datagrid 所有数据
	            	$.data(dg[0], 'allRows', data.rows);
	            }
	            if (!$.data(dg[0], 'originalRows')) {//当前查询条件下所有数据
	            	$.data(dg[0], 'originalRows', data.rows);
	            }
				//获取当页数据
				var start = (options.pageNumber - 1) * parseInt(options.pageSize);
	            var end = start + parseInt(options.pageSize);
	            data.rows = $.data(dg[0], 'originalRows').slice(start, end);
	            return data;
			}
			return data;
		}
		
		function query(combo, keyword) {
			var comgrid = combo.uceCombogrid("grid");
			var mode = combo.uceCombogrid("options").mode;// 是否远程请求数据
            //remote模式，修改发送请求的参数名称
            if (mode == 'remote') {
            	queryRemote(combo, keyword);
            } else {
            //local模式，在前端过滤数据
				queryLocal(combo, keyword);
            }
		}
		
		function queryRemote(combo, keyword) {
			var comgrid = combo.uceCombogrid("grid");
			var options = combo.uceCombogrid("options");
			var queryMinCharLength = options.queryMinCharLength;
        	var keywordName = options.queryParamName;// 获取参数请求名称
            var queryParams = comgrid.datagrid('options').queryParams; 
        	if (keyword && keyword.length >= queryMinCharLength) {
        		var queryParamStr = "{" + keywordName + " : \"" + keyword + "\"}";
	                var queryParamJson =  eval('(' + queryParamStr + ')');
					$.extend(queryParams, queryParamJson);
	                comgrid.datagrid('options').queryParams = queryParams;
	                comgrid.datagrid("reload");    //重新加载
	                combo.uceCombogrid("setValue", keyword);
        	}
		}
		
		function queryLocal(combo, keyword) {
			var comgrid = combo.uceCombogrid("grid");
			var options = combo.uceCombogrid("options");
			var filterFields = options.filterFields;// 过滤字段名称
			var rows = $.data(comgrid[0],"allRows");
			var keywords = options.multiple ? keyword.split(options.separator) : [keyword];
			var filterRows = [];
			$.each(rows, function(i, row){
				if (likeInCombogridList(row, filterFields, keywords)) {
					filterRows.push(row);
				}
			});
			$.data(comgrid[0], 'originalRows', null);//清空查询条件下所有rows
			comgrid.datagrid('loadData', filterRows);//重新设置查询条件下所有rows
			combo.uceCombogrid("setValue", keyword);
		}
		
		function filter(target, keyword, row) {
			var combo = $(target);
			var comgrid = combo.uceCombogrid("grid");
			var options = combo.uceCombogrid("options");
			var filterFields = options.filterFields;// 过滤字段名称
			var keywords = options.multiple ? keyword.split(options.separator) : [keyword];
			if (likeInCombogridList(row, filterFields, keywords)) {
				return true;
			}
			return false;
		}
		
		function limitValueInCombogridList(target) {
			var combo = $(target);
			var options = combo.uceCombogrid('options');
			var limitToList = options.limitToList;
			if (limitToList) {
				var existInList = false;
				var filterFields = options.filterFields;// 过滤字段名称
				var textField = options.textField;// 过滤字段名称
				var idField = options.idField;//id 字段
				var textbox = combo.uceCombogrid('textbox').parent().find('.textbox-text');
				var keyword = $(textbox[0]).val();
				var keywords = options.multiple ? keyword.split(options.separator) : [keyword];
				var rows = combo.uceCombogrid('grid').datagrid('getData').rows;
				var values = [];
				for (var i = 0; i < rows.length; i++) {
					if (matchFullInCombogridList(rows[i], filterFields, keywords)) {
						existInList = true;
						values.push(rows[i][idField]);
					}
				}
//				if (values.length == 0) {
//					var valuestr = "";
//					for (var i = 0; i < values.length ; i++) {
//						valuestr += values[i];
//					}
//					console.log("hide combogrid setvalue null,text = " + valuestr + "," + keyword + ",.....");
//				}
				combo.uceCombogrid('setValues', values);
			}
		}
		
		function likeInCombogridList(row, filterFields, keywords) {
			for (var i = 0; i < filterFields.length; i++) {
				for (var j = 0; j < keywords.length; j++) {
					if ((row[filterFields[i]] + "").toLowerCase().indexOf(keywords[j].toLowerCase()) >= 0) {
						return true;
					}
				}
			}
			return false;
		}
		
		function matchFullInCombogridList(row, filterFields, keywords) {
			var lowerKeywords = [];
			$.each(keywords, function(i, keyword) {
				lowerKeywords.push(keyword);
			});
			var matchField = $.grep(filterFields, function(filterField) {
				return $.inArray((row[filterField] + "").toLowerCase(), lowerKeywords) >= 0;
			});
			if (matchField && matchField.length > 0) {
				return true;
			}
			return false;
		}
	})(jQuery);

function loadUceCombogrid(id, options) {
	if (!options.pageSize) {
		options.pageSize = 50;
	}
	if (!options.pageList) {
		options.pageList = [options.pageSize];
	}
	$("#" + id).uceCombogrid(options);
	$("#" + id).uceCombogrid('grid').datagrid('getPager').
	pagination({pageSize:options.pageSize,pageNumber:1,showPageList : false});
}

function loadEmpCombogrid(id, options) {
	loadUceCombogrid(id, $.extend({panelWidth:400,
	    pagination: true,
	    idField:'empId',	
	    textField:'empName', 
	    filterFields : ['empCode', 'empName'],//搜索条件
	    mode : 'remote',//当输入文本时，从后台拉取数据
	    //limitToList : true,//限制在列表范围内
	    queryParamName : 'keyword',
	    columns:[[
        {field:'empId',title:'员工id',width:100, hidden:true},
	    {field:'empCode',title:'员工编号',width:100},
	    {field:'empName',title:'员工名称',width:100}
	    ]]} , options))
}