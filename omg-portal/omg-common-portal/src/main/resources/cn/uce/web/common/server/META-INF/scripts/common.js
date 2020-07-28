/**
 * 通用js调用
 */

/**
datagrid增加ToolTip
*/
var datagridMethod = $.fn.datagrid.methods;
$.fn.datagrid.methods = $.extend({}, $.fn.datagrid.methods, {
    /**
    * 开打提示功能
    */
    doCellTip: function (jq, params) {
        function showTip(data, td, e) {
            if ($(td).text() == "")
                return;
            data.tooltip.text($(td).text()).css({
                top: (e.pageY + 10) + 'px',
                left: (e.pageX + 20) + 'px',
                'z-index': $.fn.window.defaults.zIndex,
                display: 'block'
            });
        };
        return jq.each(function () {
            var grid = $(this);
            var options = $(this).data('datagrid');
            if (!options.tooltip) {
                var panel = grid.datagrid('getPanel').panel('panel');
                var defaultCls = {
                    'border': '1px solid #333',
                    'padding': '2px',
                    'color': '#333',
                    'background': '#f7f5d1',
                    'position': 'absolute',
                    'max-width': '200px',
                    'border-radius': '4px',
                    '-moz-border-radius': '4px',
                    '-webkit-border-radius': '4px',
                    'display': 'none'
                }
                var tooltip = $("<div id='celltip'></div>").appendTo('body');
                tooltip.css($.extend({}, defaultCls, params.cls));
                options.tooltip = tooltip;
                panel.find('.datagrid-body').each(function () {
                    var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td', {
                        'mouseover': function (e) {
                            if (params.delay) {
                                if (options.tipDelayTime)
                                    clearTimeout(options.tipDelayTime);
                                var that = this;
                                options.tipDelayTime = setTimeout(function () {
                                    showTip(options, that, e);
                                }, params.delay);
                            }
                            else {
                                showTip(options, this, e);
                            }
                        },
                        'mouseout': function (e) {
                            if (options.tipDelayTime)
                                clearTimeout(options.tipDelayTime);
	                            options.tooltip.css({
	                                'display': 'none'
	                            });
                        },
                        'mousemove': function (e) {
                            var that = this;
                            if (options.tipDelayTime)
                                clearTimeout(options.tipDelayTime);
                            	//showTip(options, this, e);
                            	options.tipDelayTime = setTimeout(function () {
                                showTip(options, that, e);
                            }, params.delay);
                        }
                    });
                });

            }

        });
    },
    /**
    * 关闭消息提示功能
    */
    cancelCellTip: function (jq) {
        return jq.each(function () {
            var data = $(this).data('datagrid');
            if (data.tooltip) {
                data.tooltip.remove();
                data.tooltip = null;
                var panel = $(this).datagrid('getPanel').panel('panel');
                panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')
            }
            if (data.tipDelayTime) {
                clearTimeout(data.tipDelayTime);
                data.tipDelayTime = null;
            }
        });
    },
    /**
     * 加载数据异常处理
     */
    loadData: function(jq, data) {
		if (data && !data.length && !data.rows) {
			if (data.message) {
				showError(data);
			}
			return [];
		}
		return datagridMethod.loadData.call(this, jq, data);
	}
});

/**
 * 扩展datagrid事件
 * 1.载入远程数据产生错误异常处理
 */
$.extend($.fn.datagrid.defaults,{
	onLoadError:function(data, status, xhr) {
		if (data.status == 606) {
			showInfoMsg('登录超时..',function(){
				window.location.href = data.getResponseHeader("Location");
			});
			return;
		}
		if (status == 'timeout') {
			showErrorMsg('请求超时，请检查网络是否正常');
			return;
		}
		if (status == 'parsererror') {
			showErrorMsg('数据解析异常');
			return;
		} 
		if (status == 'error') {
			if (data.readyState == '0') {
				showErrorMsg('服务器端异常');
			}
			if (data.readyState == '4') {
				showErrorMsg('系统异常');
			}
			return;
		} 
	    if (!isEmptyObject(data.responseText)) {
			var rs = eval('(' + data.responseText + ')');
			showErrorMsg(rs.exception.message);
			return;
	    } 
	    showErrorMsg("请求异常");
	}
});


/**
 * jQuery中AJAX请求的默认设置选项,之后执行的所有AJAX请求，如果对应的选项参数没有设置，将使用更改后的默认设置。
 */
$.ajaxSetup({
	type:'POST',
	success : function(data, statusText, xhr){
		if (this.finalTask) {
			this.finalTask.call(this);
		}
		if (typeof data == 'boolean'){
			return;
		}
		var json = data;
		if (typeof data == 'string') {
			if(data == ''){
				return '';
			}
			try { 
				json = eval('('+ data +')');
			}catch (e) { 
				console.log(e);
			} 
		}
		if (json.success) {
			if(json.message!=null && json.message!=""){
				showTips(json.message,'success');
			}
			if (this.task) {
				this.task.call(this, json, statusText, xhr);
			}
		} else {
			showError(json);
			if (this.fail) {
				this.fail.call(this, json, statusText, xhr);
			}
		}
	},
	error:function(data, status, xhr) {
		if (this.finalTask) {
			this.finalTask.call(this);
		}
		if (data.status == 606) {
			showInfoMsg('登录超时..',function(){
				window.location.href = data.getResponseHeader("Location");
			});
			return;
		}
		if (status == 'timeout') {
			showErrorMsg('请求超时，请检查网络是否正常');
			return;
		}
		if (status == 'parsererror') {
			showErrorMsg('数据解析异常');
			return;
		} 
		if (status == 'error') {
			if (data.readyState == '0') {
				showErrorMsg('服务器端异常');
			}
			if (data.readyState == '4') {
				showErrorMsg('系统异常');
			}
			return;
		} 
	    if (!isEmptyObject(data.responseText)) {
			var rs = eval('(' + data.responseText + ')');
			showErrorMsg(rs.exception.message);
			return;
	    } 
	    showErrorMsg("请求异常");
	}
});


/**
 * 扩展form表单事件
 * 1.提交时表单校验
 * 2.加载数据异常处理
 */

$.extend($.fn.form.defaults,{
	onSubmit: function(param){
		var valid = $(this).form('validate');
		if(!valid && $(this).form('options').finalTask){
			$(this).form('options').finalTask.call(this);
		}
		return valid;
	},
	success: function(data) {
		if($(this).form('options').finalTask){
			$(this).form('options').finalTask.call(this);
		}
		if (isEmptyObject(data)) {
			showErrorMsg('服务器端异常');
			return;
		}
		var result = data;
		if (typeof data == 'string') {
			result = eval('('+ data +')');
		}
		if (result.success) {
			showTips(result.message,'success');
			if ($(this).form('options').task) {
				$(this).form('options').task.call(this, result);
			}
		} else {
			showError(result);
		}
	}
});


/**
 * 扩展easyui表单验证 
 */
$.extend($.fn.validatebox.defaults.rules,{
	//验证汉字
	CHS: {
		validator: function(value) {
			return /^[\u0391-\uFFE5]+$/.test(value);  
		},
		message: "请输入整数"
	},
	//验证只能输入字母和数字
	letterOrNum: {
		validator: function(value) {
			return /^[a-zA-Z0-9]+$/.test(value);
		},
		message: "请输入字母和数字"
	},
	//当前控件值需与 id 这个控件的值相同
	equals: {
		validator: function(value, param) {
			return value == $(param[0]).val();
		},
		message: "密码不一致"
	},
	/*2017-12-18 修改密码强度校验规则*/
	/*strong: {
		validator: function(value) {
			return /^(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{8,}$/.test(value);
		},
		message: "密码为8位以上数字字母组合"
	},*/
	consecutiveRepeatCharacterRegex: {
		validator: function(value) {
			var regex = /(.)(\1){2,}/;
			return !regex.test(value); //false
		},
		message: '密码中不能包含三位以上重复的字符!'
	},
	consecutiveCharacterRegex: {
		validator: function(value) {
			var regex = /((9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){2}\d|(0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){2}\d|(a(?=b)|b(?=c)|c(?=d)|d(?=e)|e(?=f)|f(?=g)|g(?=h)|h(?=i)|i(?=j)|j(?=k)|k(?=l)|l(?=m)|m(?=n)|n(?=o)|o(?=p)|p(?=q)|q(?=r)|r(?=s)|s(?=t)|t(?=u)|u(?=v)|v(?=w)|w(?=x)|x(?=y)|y(?=z)){2}\w|(z(?=y)|y(?=x)|x(?=w)|w(?=v)|v(?=u)|u(?=t)|t(?=s)|s(?=r)|r(?=q)|q(?=p)|p(?=o)|o(?=n)|n(?=m)|m(?=l)|l(?=k)|k(?=j)|j(?=i)|i(?=h)|h(?=g)|g(?=f)|f(?=e)|e(?=d)|d(?=c)|c(?=b)|b(?=a)){2}\w|(A(?=B)|B(?=C)|C(?=D)|D(?=E)|E(?=F)|F(?=G)|G(?=H)|H(?=I)|I(?=J)|J(?=K)|K(?=L)|L(?=M)|M(?=N)|N(?=O)|O(?=P)|P(?=Q)|Q(?=R)|R(?=S)|S(?=T)|T(?=U)|U(?=V)|V(?=W)|W(?=X)|X(?=Y)|Y(?=Z)){2}\w|(Z(?=Y)|Y(?=X)|X(?=W)|W(?=V)|V(?=U)|U(?=T)|T(?=S)|S(?=R)|R(?=Q)|Q(?=P)|P(?=O)|O(?=N)|N(?=M)|M(?=L)|L(?=K)|K(?=J)|J(?=I)|I(?=H)|H(?=G)|G(?=F)|F(?=E)|E(?=D)|D(?=C)|C(?=B)|B(?=A)){2}\w)/;
			return !regex.test(value); //false
		},
		message: '密码中不能包含三位以上正序或倒序的连续数字或字母!'
	},
	passwordRule: {
		validator: function(value) {
			var regex = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,}$/;
			return regex.test(value); //false
		},
		message: '密码由英文字母大写、小写，数字及特殊符号构成，必须满足3种类型，且不能少于8位!'
	},
	/*2017-12-18 修改密码强度校验规则*/
	billCode: {
		validator: function(value) {
			return /^[a-zA-Z0-9-]*$/.test(value);
		},
		message: "运单号格式不正确"
	},
	speicalCharFilter : {
   	    validator: function(value) {
   	    	var regex = /[\,\.\-\\\/\[\]{}<>()|"'!%;*?:+ =#$%^&*]+/g;
	        return !regex.test(value);
	     },
	    message: '不能包含空格和特殊字符'  
    },
	beginNotGreaterEndDate : {
		validator: function(value, dateIdArray) {
			return validateBeginEndTime(dateIdArray[0], dateIdArray[1], 'datebox');
		},
		message: "开始日期不能大于结束日期"
	},
	beginNotGreaterEndDateTime : {
		validator: function(value, dateIdArray) {
			return validateBeginEndTime(dateIdArray[0], dateIdArray[1], 'datetimebox');
		},
		message: "开始时间不能大于结束时间"
	},
	roleCode : {
		validator : function(value) {
			//var regex = /[\,\.\\\/\[\]{}<>()|"'!%;*?:+ =#$%^&*]+/g;
			var regex = /^[0-9a-zA-Z_]*$/g;
			return regex.test(value);
		},
		message : '只允许输入数字、字母和下划线'
	},
	typeCodeExit : {
		validator : function(value,param) {
			var typeClassCode = $(param[0]).combobox('getValue');
			if(!typeClassCode){
				return false;
			}
			var flag=true;
			$.ajax({
				async : false,
				url:'../dictData/findExitTypeCode.do',
				data :{'typeClassCode': typeClassCode,'typeCode':value},
				success : function(data, statusText, xhr){
					flag = data;
				}
			});
			return flag;
		},
		message : '该分类下类型编号已存在'
	}
});

/**
 * easyui validatebox的两个方法.移除验证和还原验证
 */
$.extend($.fn.validatebox.methods, {
	//移除验证
    remove: function (jq, newposition) {
        return jq.each(function () {
            $(this).removeClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');
        });
    },
    //恢复验证
    reduce: function (jq, newposition) {
        return jq.each(function () {
            var opt = $(this).data().validatebox.options;
            $(this).addClass("validatebox-text validatebox-invalid").validatebox(opt);
        });
    }
}); 

/**
 * easyui validate文件大小与数量校验
 */
$.extend($.fn.validatebox.defaults.rules, {
	// filebox验证文件大小的规则函数
	fileSize : {
		validator : function(value, array) {
			var size = array[0];
			var unit = array[1];
			if (!size || isNaN(size) || size == 0) {
				$.error('验证文件大小的值不能为 "' + size + '"');
			} else if (!unit) {
				$.error('请指定验证文件大小的单位');
			}
			var index = -1;
			var unitArr = new Array("bytes", "kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb");
			for (var i = 0; i < unitArr.length; i++) {
				if (unitArr[i] == unit.toLowerCase()) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				$.error('请指定正确的验证文件大小的单位：["bytes", "kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb"]');
			}
			// 转换为bytes公式
			var formula = 1;
			while (index > 0) {
				formula = formula * 1024;
				index--;
			}
			// this为页面上能看到文件名称的文本框，而非真实的file
			// $(this).next()是file元素
			var files = $(this).next().get(0).files;
			if(files != null && files != undefined){
				var flag;
				for(var i = 0; i < files.length; i++){
					flag = files[i].size < parseFloat(size) * formula;
					if(!flag){
						$.messager.alert('警告：',files[i].name + '文件大小必须小于' + size +' ' + unit);
						return flag;
					}
				}
				return flag;
			}
		},
		message : '文件大小必须小于 {0}{1}'
	},
	fileNum: { // 校验文件上传数量
		validator : function(value,array) {  
		    var fileNum = $(this).next().get(0).files.length;
			var flag = fileNum <= array[0];
			if(!flag){
				$.messager.alert('警告：','只能上传' + array[0] +'张图片');
			}
            return flag; 			
		},  
		message : '只能上传 {0} 张图片'
	}
});

//重新解析dom
function resizeParser(){
	$.parser.parse($('body').parent());
}

window.onload = function(){
	
	if (window.top == window.self) {
		//当前窗口为最顶层窗口
        return;
    }else{ 
    	//折叠消息面板
		document.body.onclick=function(event){
			//add by xj crossDomain
			if(crossDomainFlag == true){
				window.top.postMessage({fn:'click'}, '*');
			} else {
				if(window.top.vm.settings.type) {
					window.top.vm.openSetting(event);
				}
			}
			//add by xj crossDomain
		}
		
		//皮肤设置
		var settings = localStorage.settings;
		var themes = settings?JSON.parse(settings):{themes:"default"}
		var css = document.createElement('style');
		css.type='text/css';
		css.id="themes-setting";
		if(themes.themes=='default'){
			css.innerHTML =".iconfont{color:#ff9372;}";
		}else if(themes.themes =='blue'){
			css.innerHTML =".iconfont{color:#23b7e5;}";
		}else{
			css.innerHTML =".iconfont{color:#464c5b;}";
		}
		document.head.appendChild(css);
    } 
	
}