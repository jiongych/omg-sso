(function($){
    
	var combotreegridPlugin = $.fn.combotreegrid;
    $.fn.uceCombotreegrid = function(options, param){
        if (typeof options != 'string'){
            return this.each(function(){
            	var uceOpts = $.extend({},$.fn.uceCombotreegrid.defaults,$.fn.uceCombotreegrid.parseOptions(this), options);
            	if (!uceOpts.filterFields) {
	    			uceOpts.filterFields = [uceOpts.treeField, uceOpts.idField];
	    		} else if (typeof uceOpts.filterFields == "string") {
	    			uceOpts.filterFields = [uceOpts.filterFields, uceOpts.idField];
	    		} else {
	    			uceOpts.filterFields.push(uceOpts.idField);
	    		}
            	combotreegridPlugin.call($(this), uceOpts, param);
            });
        } else {
        	var func = $.fn.uceCombotreegrid.methods[options];
        	if(func){
        		return func(this, param);
        	} else {
        		return combotreegridPlugin.call(this, options, param);
        	}
        }
    };
	
    $.fn.uceCombotreegrid.methods = $.extend({}, combotreegridPlugin.methods,{
        getSelected: function(jq){
            var options = jq.uceCombotreegrid('options');
            var key = options.idField;
            var values = jq.uceCombotreegrid('getValues');
            var data = jq.uceCombotreegrid('getData');
            var items = [];
            for(var i=0; i<data.length; i++){
                var item = data[i];
                if($.inArray(item[key], values) >= 0){
                	items.push = item;
                }
            }
            return items;
        }
    });

    $.fn.uceCombotreegrid.defaults = $.extend({}, combotreegridPlugin.defaults, {
    	headerData : null,
    	headerText : null,
    	headerValue : null,
    	master : null , 
    	queryParamName : "keyword", //设置默认请求参数名称：keyword
//    	keyHandler : $.extend({}, combotreegridPlugin.defaults.keyHandler, {
//			//用户输入时，过滤数据
//			query : function(keyword, e) {
//				var target = e.data.target;
//				var combotreegrid = $(target);
//				var mode = combotreegrid.uceCombotreegrid("options").mode;// 是否远程请求数据
//				query(combotreegrid, keyword);
//				if (mode != 'remote') {
//					if (combotreegridPlugin.defaults.keyHandler && combotreegridPlugin.defaults.keyHandler.query) {
//						combotreegridPlugin.defaults.keyHandler.query.call(this, keyword, e);
//					}
//				}
//			}
//		}) ,
//    	filter : function(keyword, row){
//    		var opts = $(this).uceCombotreegrid("options");
//    		var filterFields = opts.filterFields;
//    		return filterInCombotreegridList(row, filterFields, keyword);
//    	},
    	onHidePanel : function() {
			limitValueInCombotreegridList(this);
			if (combotreegridPlugin.defaults.onHidePanel) {
				combotreegridPlugin.defaults.onHidePanel.call(this);
			}
    	},
    	inputEvents : $.extend({}, combotreegridPlugin.defaults.inputEvents, {
    		blur : function(e) {
    			var target = e.data.target;
    			limitValueInCombotreegridList(target);
    			if (combotreegridPlugin.defaults.inputEvents.blur) {
    				combotreegridPlugin.defaults.inputEvents.blur.call(this, e);
    			}
    		}
    	})
    });
    
    $.fn.uceCombotreegrid.parseOptions = function(options) {
    	return $.extend({}, combotreegridPlugin.parseOptions(options), $.parser.parseOptions(options,["filterFields", "queryParamName"]));
    };
    $.fn.uceCombotreegrid.parseData = combotreegridPlugin.parseData;
    
    function query(combotreegrid, keyword) {
		var mode = combotreegrid.uceCombotreegrid("options").mode;// 是否远程请求数据
        //remote模式，修改发送请求的参数名称
        if (mode == 'remote') {
        	queryRemote(combotreegrid, keyword);
        } else {
        //local模式，在前端过滤数据
			queryLocal(combotreegrid, keyword);
        }
	}
	
	function queryRemote(combotreegrid, keyword) {
		var dg = combotreegrid.uceCombotreegrid("grid");
		var options = combotreegrid.uceCombotreegrid("options");
		var queryMinCharLength = options.queryMinCharLength;
    	var keywordName = options.queryParamName;// 获取参数请求名称
        var queryParams = dg.treegrid('options').queryParams; 
    	if (keyword && keyword.length >= queryMinCharLength) {
    		var queryParamStr = "{" + keywordName + " : \"" + keyword + "\"}";
                var queryParamJson =  eval('(' + queryParamStr + ')');
				$.extend(queryParams, queryParamJson);
				dg.treegrid('options').queryParams = queryParams;
				dg.treegrid("reload");    //重新加载
				combotreegrid.uceCombotreegrid("setValue", keyword);
    	}
	}
	
	function queryLocal(combotreegrid, keyword) {
		var dg = combotreegrid.uceCombotreegrid("grid");
		var options = combotreegrid.uceCombotreegrid("options");
		var filterFields = options.filterFields;// 过滤字段名称
		var rows = $.data(dg[0], "originalRows");
		if (!rows) {
			rows = dg.treegrid('getData');
			 $.data(dg[0], "originalRows", rows);
		}
		var keywords = options.multiple ? keyword.split(options.separator) : [keyword];
		var filterRows = [];
		$.easyui.forEach(rows, true, function(row){
			if (likeInCombotreegridList(row, filterFields, keywords)) {
				filterRows.push(row);
			}
		});
		dg.treegrid('loadData', filterRows);//重新设置查询条件下所有rows
		combotreegrid.uceCombotreegrid("setValue", keyword);
	}
    
    function likeInCombotreegridList(row, filterFields, keywords) {
    	for (var i = 0; i < filterFields.length; i++) {
			for (var j = 0; j < keywords.length; j++) {
				if ((row[filterFields[i]] + "").toLowerCase().indexOf(keywords[j].toLowerCase()) >= 0) {
					return true;
				}
			}
		}
    	return false;
	}
    
    function matchFullInCombotreegridList(row, filterFields, keyword) {
		if (typeof filterFields == "string") {
			return (row[filterFields] + "").toLowerCase() == keyword.toLowerCase();
		} else {
			var matchField = $.grep(filterFields, function(filterField) {
				return (row[filterField] + "").toLowerCase() == keyword.toLowerCase();
			});
			if (matchField && matchField.length > 0) {
				return true;
			}
			return false;
		}
	}
    
    function limitValueInCombotreegridList(target) {
    	var combotreegrid = $(target);
		var options = combotreegrid.uceCombotreegrid('options');
		var limitToGrid = options.limitToGrid;
		if (limitToGrid) {
			if (options.onHidePanelTimer) {
				clearTimeout(options.onHidePanelTimer);
			}
			options.onHidePanelTimer = setTimeout(function() {
				var existInList = false;
				var filterFields = options.filterFields;// 过滤字段名称
				var idField = options.idField;
				var texts = combotreegrid.uceCombotreegrid('getValues');
				var rows = combotreegrid.uceCombotreegrid('getData');
				var values = [];
				$.each(rows, function(idx, row){
					$.each(texts, function(textIdx, text){
						if (matchFullInCombotreegridList(row, filterFields, text)) {
							values.push(row[idField]);
						}
					});
				});
//				if (values.length == 0) {
				var valuestr = "";
				for (var i = 0; i < values.length ; i++) {
					valuestr += values[i];
				}
					console.log("hide combotreegrid setvalue null,text = " + valuestr + "," + texts + ",.....");
//				}
				combotreegrid.uceCombotreegrid('setValues', values);
			}, 50);
		}
    }
	
    function fixRemoteFilterParam(target, queryParams) {
    	if (queryParams.q == undefined) {
    		return;
    	}
    	var combotreegrid = $(target);
		var options = combotreegrid.uceCombotreegrid('options');
		var queryParamName = options.queryParamName;
		var queryParamStr = "{" + queryParamName + " : \"" + queryParams.q + "\"}";
         var queryParamJson =  eval('(' + queryParamStr + ')');
		$.extend(queryParams, queryParamJson);
    }
    

})(jQuery);

function loadCombotreegrid(id, options) {
	
}