(function($){

//	$.fn.dialog.defaults.onMove = function(e){
//		var movDom = this.parentNode;
//		console.log(movDom);
//		var _w = movDom.offsetWidth;
//		var _h = movDom.offsetHeight;
//		
//		var body = movDom.parentNode;
//		var W = body.offsetWidth;
//		var H = body.offsetHeight;
//		
//		var left = movDom.offsetLeft;
//		var top = movDom.offsetTop;
//		
//		if(left + _w > W){
//			movDom.style.left = W - _w + "px";
//		}
//		if(left<0){
//			movDom.style.left = 1 + "px";
//		}
//		if(top + _h > H){
//			movDom.style.top = H - _h + "px";
//		}
//		if(top < 0){
//			movDom.style.top = 1 + "px";
//		}
//	};

	$.messager.defaults.onMove = function(){
		console.log(this);
		var movMessage = this.parentNode;
		var _w = movMessage.offsetWidth;
		var _h = movMessage.offsetHeight;
		
		var body = movMessage.parentNode;
		var W = body.offsetWidth;
		var H = body.offsetHeight;
		
		var left = movMessage.offsetLeft;
		var top = movMessage.offsetTop;
		
		if(left + _w > W){
			movMessage.style.left = W - _w + "px";
		}
		if(left<0){
			movMessage.style.left = 1 + "px";
		}
		if(top + _h > H){
			movMessage.style.top = H - _h + "px";
		}
		if(top < 0){
			movMessage.style.top = 1 + "px";
		}		
	};
    
	var comboboxPlugin = $.fn.combobox;
    $.fn.uceCombobox = function(options, param){
        if (typeof options != 'string'){
            return this.each(function(){
            	var uceOpts = $.extend({},$.fn.uceCombobox.defaults,$.fn.uceCombobox.parseOptions(this), options);
            	uceOpts.panelHeight = 'auto';
            	uceOpts.panelMaxHeight = '200';
            	if (!uceOpts.filterFields) {
	    			uceOpts.filterFields = [uceOpts.textField, uceOpts.valueField];
	    		} else if (typeof uceOpts.filterFields == "string") {
	    			uceOpts.filterFields = [uceOpts.filterFields, uceOpts.valueField];
	    		} else {
	    			uceOpts.filterFields.push(uceOpts.valueField);
	    		}
            	comboboxPlugin.call($(this), uceOpts, param);
                //关联主节点
                masterHandle(this);
            });
        } else {
        	var func = $.fn.uceCombobox.methods[options];
        	if(func){
        		func(this, param).panelHeight = 'auto';
        		func(this, param).panelMaxHeight = '200';
        		return func(this, param);
        	} else {
        		return comboboxPlugin.call(this, options, param);
        	}
        }
    };
	
    $.fn.uceCombobox.methods = $.extend({}, comboboxPlugin.methods,{
        addEventListener: function(jq, param){
            return jq.each(function(){
                var eventList = $.isArray(param) ? param : [param];
                var target = this;
                var options = $(target).uceCombobox('options');
                $.each(eventList, function(i, event){
                    addEventListener(options, target, event.name, event.handler|| function(){}, event.override);
                });
            });
        },
        getSelected: function(jq){
            var options = jq.uceCombobox('options');
            var key = options.valueField;
            var values = jq.uceCombobox('getValues');
            var data = jq.uceCombobox('getData');
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

    $.fn.uceCombobox.defaults = $.extend({}, comboboxPlugin.defaults, {
    	headerData : null,
    	headerText : null,
    	headerValue : null,
    	master : null , 
    	queryParamName : "keyword", //设置默认请求参数名称：keyword
    	loadFilter : function(data) {
    		return addHeaderData(this, data); 
    	},
    	filter : function(keyword, row){
    		var opts = $(this).uceCombobox("options");
    		var filterFields = opts.filterFields;
    		return filterInComboboxList(row, filterFields, keyword);
    	},
    	onBeforeLoad : function(queryParams) {
    		fixRemoteFilterParam(this, queryParams);
    		return true;
    	},
    	onHidePanel : function() {
			limitValueInComboboxList(this);
			if (comboboxPlugin.defaults.onHidePanel) {
				comboboxPlugin.defaults.onHidePanel.call(this);
			}
    	},
    	inputEvents : $.extend({}, comboboxPlugin.defaults.inputEvents, {
    		blur : function(e) {
    			var target = e.data.target;
    			limitValueInComboboxList(target);
    			if (comboboxPlugin.defaults.inputEvents.blur) {
    				comboboxPlugin.defaults.inputEvents.blur.call(this, e);
    			}
    		}
    	})
    });
    
    //headerData : 头节点数据，可配置多个，例： headerData : [{'id':0, 'text':'--请选择--'}],
    //headerText ： 头节点text，如果配置了headerValue， headerText默认为：--请选择--
    //headerValue ： 头节点value，如果配置了headerText， headerValue默认为：0
    //masterId：联动的主控件id
    $.fn.uceCombobox.parseOptions = function(options) {
    	return $.extend({}, comboboxPlugin.parseOptions(options), $.parser.parseOptions(options,["headerData", "headerText", "headerValue", "master", "filterFields", "queryParamName"]));
    };
    $.fn.uceCombobox.parseData = comboboxPlugin.parseData;
    
    function masterHandle(slaveEle){
        var slaveOpts = $(slaveEle).uceCombobox('options');
        var master = slaveOpts.master;
        if (master == null || (typeof master.id == "undefined") || master.id == null || master.id == "") { 
        	return; 
    	}
        var masterId = master.id;
        if(!/^#/.test(masterId)){
        	masterId = '#' + masterId;
        }
        var materOpts = $(masterId).uceCombobox('options');

        if(!materOpts.multiple){
            $(masterId).uceCombobox('addEventListener', [{
                name: 'onSelect',
                handler: function(masterRecord){
                    loadSlaveData(slaveEle, masterRecord);
                }
            },{
                name: 'onChange',
                handler: function(newValue, oldValue){
                    if(newValue == null || newValue ==''){
                        $(slaveEle).uceCombobox('clear').combobox('loadData',[]);
                        $(masterId).uceCombobox('textbox').trigger('blur');
                    }
                }
            }]);
        }
    }

    function loadSlaveData(slaveEle, masterRecord){
    	var slaveOpts = $(slaveEle).uceCombobox('options');
    	var mode = 'remote';
    	if (slaveOpts.master.mode != null && slaveOpts.master.mode != undefined) {
    		mode = slaveOpts.master.mode;
    	}
        if(slaveOpts.master.mode == 'remote'){
        	var masterValue = masterRecord[slaveOpts.valueField];
        	if (masterValue == null || masterValue == undefined) {
        		$(slaveEle).uceCombobox('clear').uceCombobox('loadData', []);
        		return;
        	}
            var url = slaveOpts.originalUrl || slaveOpts.url;
            $(slaveEle).uceCombobox('options').originalUrl = url;
            url += masterValue; //追加查询参数
            $(slaveEle).uceCombobox('clear').uceCombobox('reload', url);
        }else{
        	var slaveDataField = 'data';
        	if (slaveOpts.master.slaveDataField != null && slaveOpts.master.slaveDataField != undefined) {
        		slaveDataField = slaveOpts.master.slaveDataField;
        	}
            $(slaveEle).uceCombobox('clear').uceCombobox('loadData', masterRecord[slaveDataField]);
        }
    }

    function addHeaderData(target, data){
        var options = $(target).uceCombobox('options');
        if(options.headerData == null && options.headerText == null  && options.headerValue == null) {
        	return data;
        }
        var headerData = options.headerData;
        if (!headerData) {
        	if (options.headerValue == null) {
        		options.headerValue = 0;
        	} 
        	if (options.headerText == null) {
        		options.headerText = '--请选择--';
        	}
        	if (typeof options.headerValue == 'string') {
        		headerDataStr = "{\"" + options.valueField + "\":\"" + options.headerValue + "\",\"" + options.textField + "\":\"" + options.headerText + "\"}";
        	} else {
        		headerDataStr = "{\"" + options.valueField + "\":" + options.headerValue + ",\"" + options.textField + "\":\"" + options.headerText + "\"}";
        	}
        	
        	headerData = eval('(' + headerDataStr + ')');
        	options.headerData = headerData;
        }
        if (typeof data == "undefined") {
        	data = [];
        }
    	if (headerData.length) {
    		$.each(headerData, function(index, headerRow) {
    			if ($.inArray(headerRow, data) < 0) {
    				data.splice(0,0, headerRow);
    			}
    		});
    	} else {
    		if ($.inArray(headerData, data) < 0) {
				data.splice(0,0, headerData);
			}
    	}
        return data;
    }
    
    function filterInComboboxList(row, filterFields, keyword) {
		if (typeof filterFields == "string") {
			return (row[filterFields] + "").toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
		} else {
			var matchField = $.grep(filterFields, function(filterField) {
				return (row[filterField] + "" + "").toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
			});
			if (matchField && matchField.length > 0) {
				return true;
			}
			return false;
		}
	}
    
    function matchFullInComboboxList(row, filterFields, keyword) {
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
    
    function limitValueInComboboxList(target) {
    	var combo = $(target);
		var options = combo.uceCombobox('options');
		var limitToList = options.limitToList;
		if (limitToList) {
			if (options.onHidePanelTimer) {
				clearTimeout(options.onHidePanelTimer);
			}
			options.onHidePanelTimer = setTimeout(function() {
				var existInList = false;
				var filterFields = options.filterFields;// 过滤字段名称
				var valueField = options.valueField;
				var texts = combo.uceCombobox('getValues');
				var rows = combo.uceCombobox('getData');
				var values = [];
				$.each(rows, function(idx, row){
					$.each(texts, function(textIdx, text){
						if (matchFullInComboboxList(row, filterFields, text)) {
							values.push(row[valueField]);
						}
					});
				});
//				if (values.length == 0) {
//					var valuestr = "";
//					for (var i = 0; i < values.length ; i++) {
//						valuestr += values[i];
//					}
//					console.log("hide combobox setvalue null,text = " + valuestr + "," + texts + ",.....");
//				}
				combo.uceCombobox('setValues', values);
			}, 50);
		}
    }
	
    function fixRemoteFilterParam(target, queryParams) {
    	if (queryParams.q == undefined) {
    		return;
    	}
    	var combo = $(target);
		var options = combo.uceCombobox('options');
		var queryParamName = options.queryParamName;
		var queryParamStr = "{" + queryParamName + " : \"" + queryParams.q + "\"}";
         var queryParamJson =  eval('(' + queryParamStr + ')');
		$.extend(queryParams, queryParamJson);
    }
    
	function addEventListener(options, target, eventName, handler, override){
	    var defaultActionEvent = options[eventName];
	    if(override) {
	        options[eventName] = handler;
	    } else {
	        options[eventName] = function(){
	            defaultActionEvent.apply(this, arguments);
	            handler.apply(this, arguments);
	        }
	    }
	    switch (eventName){
	        case 'onLoadSuccess':
	        case 'onSelect':
	        case 'onChange':
	        case 'onHidePanel':
	            if(override) {
	                options[eventName] = handler;
	            } else { 
	                options[eventName] = function(){
	                    defaultActionEvent.apply(this, arguments);
	                    handler.apply(this, arguments);
	                }
	            }
	            break;
	        case 'loadFilter' :
	        	if(override) {
	                options[eventName] = handler;
	            } else {
	                options[eventName] = function(){
	                    var data = defaultActionEvent.apply(this, arguments);
	                    return handler.call(this, data);
	                }
	            }
	            break;
	        default :
	            break;
	    }
	}

})(jQuery);

function loadDictCombobox(id, options) {
	$("#" + id).uceCombobox($.extend({
		valueField : 'typeId',
		textField : 'typeName',
		filterFields : ['typeCode', 'typeName'],//设置可根据typeCode、typeName查询
	    limitToList : true,//限制必须输入列表内的值
	    headerValue : 0, //设置头节点
	    formatter:formatDicShowTypeCode //格式化下拉列表数据，显示typeCode
	},options));
}