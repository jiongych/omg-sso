var _contextPath, _curUser, _pageSize = 50, _allSysTypes, _orgSysTypes, _cmsAreas, _allOrgs;
var _rights = {
    'find': 1,
    'add': 2,
    'update': 3,
    'save': 4,
    'copy': 5,
    'del': 6,
    'audit': 7,
    'unaudit': 8,
    'imp': 9,
    'exp': 10,
    'upload': 11,
    'download': 12,
    'execute': 13
};
var _rightsName = {
    'find': '查询',
    'add': '新增',
    'update': '修改',
    'save': '编辑',
    'copy': '复制',
    'del': '删除',
    'audit': '审核',
    'unaudit': '弃审',
    'imp': '导入',
    'exp': '导出',
    'upload': '上传',
    'download': '下载',
    'execute': '执行'
};
var _rightsData = [[1, '查询'], [2, '新增'], [3, '修改'], [4, '编辑'], [5, '复制'], [6, '删除'], [7, '审核'], [8, '弃审'], [9, '导入'], [10, '导出'], [11, '上传'], [12, '下载'], [13, '执行']];
$.extend($.fn.datagrid.defaults, {
    pageSize: _pageSize,
    pagination: true,
    rownumbers: true,
    rowStyler: handRowStyle,
    loadMsg: '',
    onLoadError: function (data, status, error) {
        //606:cookies失效
        if (data.status == 606) {
            window.location.href = data.getResponseHeader("Location");
        } else if (status == 'timeout' || !error) {
            errorTip('请求超时，请检查网络是否正常');
        } else if (status == 'parsererror') {
            errorTip('数据解析异常');
        } else {
            var rs = eval('(' + data.responseText + ')');
            var title = $(this).attr('title');
            if (rs.exception.message.indexOf('操作权限') == -1) {
                errorMsg(rs.exception.message);
            } else {
                $(this).datagrid("getPanel").panel('setTitle', title + '&nbsp;&nbsp;<font color="red">' + rs.exception.message + '</font>');
            }
        }
    },
    onLoadSuccess: function (data) {
        var title = $(this).attr('title');
        if (data.rows && title) {
            var pos = title.indexOf('&nbsp;');
            if (pos != -1)
                title = title.substr(0, pos);
            if (data.rows.length == 0) {
                $(this).datagrid("getPanel").panel('setTitle', title + '&nbsp;&nbsp;<font color="green">没有查询到数据！</font>');
            } else {
                $(this).datagrid("getPanel").panel('setTitle', title + ($(this).datagrid("options").pagination == true ? '' : '&nbsp;&nbsp;<font color="green">共' + data.rows.length + '记录</font>'));
            }
        }
        var opt = $(this).datagrid("options");
        if (opt.task)
            opt.task.call(opt, data);
    }
});
$.extend($.fn.treegrid.defaults, {
    pageSize: _pageSize,
    pagination: true,
    rownumbers: true,
    rowStyler: handRowStyle,
    loadMsg: '',
    onLoadError: function (data, status, error) {
        //606:cookies失效
        if (data.status == 606) {
            window.location.href = data.getResponseHeader("Location");
        } else if (status == 'timeout' || !error) {
            errorTip('请求超时，请检查网络是否正常');
        } else if (status == 'parsererror') {
            errorTip('数据解析异常');
        } else {
            var rs = eval('(' + data.responseText + ')');
            var title = $(this).attr('title');
            if (rs.exception.message.indexOf('操作权限') == -1) {
                errorMsg(rs.exception.message);
            } else {
                $(this).datagrid("getPanel").panel('setTitle', title + '&nbsp;&nbsp;<font color="red">' + rs.exception.message + '</font>');
            }
        }
    },
    onLoadSuccess: function (row, data) {
        var title = $(this).attr('title');
        if (data && title) {
            var pos = title.indexOf('&nbsp;');
            if (pos != -1)
                title = title.substr(0, pos);
            if (data.length == 0) {
                $(this).datagrid("getPanel").panel('setTitle', title + '&nbsp;&nbsp;<font color="green">没有查询到数据！</font>');
            } else {
                $(this).datagrid("getPanel").panel('setTitle', title);
            }
        }
        var opt = $(this).datagrid("options");
        if (opt.task)
            opt.task.call(opt, row, data);
    }
});
//为树形结构添加过滤功能（从easyui1.5.3copy） S 2017-03-22
$.easyui = {
    forEach: function (_6, _7, _8) {
        var _9 = [];
        for (var i = 0; i < _6.length; i++) {
            _9.push(_6[i]);
        }
        while (_9.length) {
            var _a = _9.shift();
            if (_8(_a) == false) {
                return;
            }
            if (_7 && _a.children) {
                for (var i = _a.children.length - 1; i >= 0; i--) {
                    _9.unshift(_a.children[i]);
                }
            }
        }
    }
};
//为树形结构添加过滤功能（从easyui1.5.3copy） E 2017-03-22
$.extend($.fn.tree.defaults, {
    onLoadError: function (data, status, error) {
        //606:cookies失效
        if (data.status == 606) {
            window.location.href = data.getResponseHeader("Location");
        } else if (status == 'timeout' || !error) {
            errorTip('请求超时，请检查网络是否正常');
        } else if (status == 'parsererror') {
            errorTip('数据解析异常');
        } else {
            var rs = eval('(' + data.responseText + ')');
            if (!$(this).tree('getRoot')) {
                if (rs.exception.message.indexOf('操作权限') == -1) {
                    errorMsg(rs.exception.message);
                } else {
                    var node = {};
                    node.id = 'root';
                    node.iconCls = 'tree-dnd-no';
                    node.text = '<font color="red"><strong>' + rs.exception.message + '</strong></font>';
                    node.children = [];
                    $(this).tree('options').onContextMenu = undefined;
                    $(this).tree('options').onClick = undefined;
                    $(this).tree('options').onDblClick = undefined;
                    $(this).tree('options').onCheck = undefined;
                    $(this).tree('loadData', node);
                    var els = $($(this).tree('getRoot').target).find('.tree-checkbox');
                    if (els && els.length >= 1)
                        for (var i = 0; i < els.length; i++)
                            $(els[i]).remove();
                }
            }
        }
    },
    //为树形结构添加过滤功能（从easyui1.5.3copy） E 2017-03-22
    filter: function (q, node) {
        var filterFields = $(this).tree('options').filterFields;
        if (filterFields == undefined || filterFields == null) {
            filterFields = 'text';
        }
        var qq = [];
        $.map($.isArray(q) ? q : [q], function (q) {
            q = $.trim(q);
            if (q) {
                qq.push(q);
            }
        });
        for (var i = 0; i < qq.length; i++) {
            var matchField = filterWithMultiFields(node, filterFields, qq[i]);
            if (matchField) {
                return true;
            }
        }
        return !qq.length;
    },
    //为树形结构添加过滤功能（从easyui1.5.3copy） E 2017-03-22
    //为树形结构添加扩展功能 E 2017-04-08
    onExpand: function (node) {
        var tree = $(this);
        var remoteFilter = $(this).tree('options').remoteFilter;
        if (remoteFilter != undefined && remoteFilter != null) {
            var relSchBoxId = remoteFilter.relSchBoxId;
            var schUrl = remoteFilter.schUrl;
            if (relSchBoxId == undefined || relSchBoxId == null || schUrl == null || schUrl == undefined) {
                return;
            }
            var relSchBox = $("#" + relSchBoxId);
            if (relSchBox.length == 0) {
                return;
            }
            //为树形结构新增异步查询功能
            //查询机构树时，一级级展开父级节点，不是<所查询机构>的父级，则隐藏不显示
            var parentList = relSchBox.data("parentList");
            var schOrgCodeOrName = relSchBox.data("value");//查询机构是在用户点击查询按钮才触发，所以不能直接获取searchbox的值，而是从$(..).data()获取
            if (parentList != undefined && parentList != null && schOrgCodeOrName != null && schOrgCodeOrName != '') {
                var nodes = tree.tree('getChildren');//获取机构树所有节点
                $.each(nodes, function (i, node) {
                    if ($.inArray(node.id, parentList) != -1) {
                        tree.tree("expand", node.target);
                        $("#" + node.domId).removeClass("tree-node-hidden");
                        node.hidden = false;
                    } else {
                        $("#" + node.domId).addClass("tree-node-hidden");
                        node.hidden = true;
                    }
                });
            }
        }

    }
    //为树形结构添加扩展功能 S 2017-04-08
});

/**
 * 过滤节点
 * @param node 节点
 * @param filterFields 过滤字段
 * @param keyword 搜索关键字
 * @returns {Boolean} true-节点显示，false-节点隐藏
 */
function filterWithMultiFields(node, filterFields, keyword) {
    if (typeof filterFields == "string") {
        return (node[filterFields] + "").toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
    } else {
        var matchField = $.grep(filterFields, function (filterField) {
            return (node[filterField] + "" + "").toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
        });
        if (matchField && matchField.length > 0) {
            return true;
        }
        return false;
    }
}

//为树形结构添加过滤功能（从easyui1.5.3copy） E 2017-03-22
$.extend($.fn.tree.methods, {
    doFilter: function (jq, q) {
        return jq.each(function () {
            doFilterTree(this, q);
        });
    },
    remoteFilter: function (jq) {
        jq.each(function () {
            var tree = $(this);
            var remoteFilter = $(this).tree('options').remoteFilter;
            if (remoteFilter != undefined && remoteFilter != null) {
                var relSchBoxId = remoteFilter.relSchBoxId;
                var schUrl = remoteFilter.schUrl;
                var dataParamName = remoteFilter.dataParamName;
                if (relSchBoxId == undefined || relSchBoxId == null || schUrl == null || schUrl == undefined) {
                    return;
                }
                var relSchBox = $("#" + relSchBoxId);
                if (relSchBox.length == 0) {
                    return;
                }
                if (dataParamName == null || dataParamName == undefined) {
                    dataParamName = "keyword";
                }
                doRemoteSchTree(tree, relSchBox, schUrl, dataParamName);
            }
        });
    },
    /**
     * 禁用复选框
     * @param {Object} jq
     * @param {Object} target
     */
    disableCheck: function (jq, target) {
        return jq.each(function () {
            var realTarget;
            var that = this;
            var state = $.data(this, 'tree');
            var opts = state.options;
            if (typeof target == "string" || typeof target == "number") {
                realTarget = $(this).tree("find", target).target;
            } else {
                realTarget = target;
            }
            var ckSpan = $(realTarget).find(">span.tree-checkbox");
            ckSpan.removeClass("tree-checkbox-disabled0").removeClass("tree-checkbox-disabled1").removeClass("tree-checkbox-disabled2");
            if (ckSpan.hasClass('tree-checkbox0')) {
                ckSpan.addClass('tree-checkbox-disabled0');
            } else if (ckSpan.hasClass('tree-checkbox1')) {
                ckSpan.addClass('tree-checkbox-disabled1');
            } else {
                ckSpan.addClass('tree-checkbox-disabled2')
            }
            if (!state.resetClick) {
                $(this).unbind('click').bind('click', function (e) {
                    var tt = $(e.target);
                    var node = tt.closest('div.tree-node');
                    if (!node.length) {
                        return;
                    }
                    if (tt.hasClass('tree-hit')) {
                        $(this).tree("toggle", node[0]);
                        return false;
                    } else if (tt.hasClass('tree-checkbox')) {
                        if (tt.hasClass('tree-checkbox-disabled0') || tt.hasClass('tree-checkbox-disabled1') || tt.hasClass('tree-checkbox-disabled2')) {
                            $(this).tree("select", node[0]);
                        } else {
                            if (tt.hasClass('tree-checkbox1')) {
                                $(this).tree('uncheck', node[0]);
                            } else {
                                $(this).tree('check', node[0]);
                            }
                            return false;
                        }
                    } else {
                        $(this).tree("select", node[0]);
                        opts.onClick.call(this, $(this).tree("getNode", node[0]));
                    }
                    e.stopPropagation();
                });
            }

        });
    }
});

function doFilterTree(treeEle, q) {
    var tree = $.data(treeEle, "tree");
    var opts = tree.options;
    var ids = {};
    $.easyui.forEach(tree.data, true, function (node) {
        if (opts.filter.call(treeEle, q, node)) {
            $("#" + node.domId).removeClass("tree-node-hidden");
            ids[node.domId] = 1;
            node.hidden = false;
        } else {
            $("#" + node.domId).addClass("tree-node-hidden");
            node.hidden = true;
        }
    });
    for (var id in ids) {
        showParent(id);
    }

    function showParent(domId) {
        var p = $(treeEle).tree("getParent", $("#" + domId)[0]);
        while (p) {
            $(p.target).removeClass("tree-node-hidden");
            p.hidden = false;
            p = $(treeEle).tree("getParent", p.target);
        }
    };
}
//为树形结构添加过滤功能（从easyui1.5.3copy） E 2017-03-22

//为树形结构添加远程过滤功能 S 2017-04-08
function doRemoteSchTree(tree, relSchBox, schUrl, dataParamName) {
    relSchBox.searchbox({
        searcher: function (value, name) {
            //值为null，则显示所有节点
            if (value == null || value == '') {
                var nodes = tree.tree('getChildren');
                relSchBox.data("parentList", null);
                relSchBox.data("value", null);
                $.each(nodes, function (i, node) {
                    $("#" + node.domId).removeClass("tree-node-hidden");
                    node.hidden = false;
                });
            } else {
                var schDataStr = "{\"" + dataParamName + "\":\"" + value + "\"}";
                var schData = headerData = eval('(' + schDataStr + ')');
                //根据orgName or orgCode精确查询，往上获取所有父级节点
                $.ajax({
                    url: schUrl,
                    data: schData,
                    success: function (parentList, statusText, xhr) {
                        //一级一级展开父节点，展开的同时获取机构下的子节点
                        var nodes = tree.tree('getChildren');
                        //保存值，以备在onExpand方法扩展到所查询节点
                        relSchBox.data("parentList", parentList);
                        relSchBox.data("value", value);
                        $.each(nodes, function (i, node) {
                            if ($.inArray(node.id, parentList) != -1) {
                                tree.tree("expand", node.target);
                                $("#" + node.domId).removeClass("tree-node-hidden");
                                node.hidden = false;
                            } else {
                                $("#" + node.domId).addClass("tree-node-hidden");
                                node.hidden = true;
                            }
                        });
                    }
                });
            }
        }
    });
}
//为树形结构添加远程过滤功能 E 2017-04-08

$.extend($.fn.form.defaults, {
    onSubmit: function (param) {
        var valid = $(this).form('enableValidation').form('validate');
        if (valid)
            openRequestMask($(this).attr('id'));
        return valid;
    },
    success: function (data) {
        var formId = getRequestMaskValue();
        closeRequestMask();
        try {
            var json = eval('(' + data + ')');
            if (json.exception) {
                errorMsg(json.exception.message);
            } else {
                try {
                    if (this.complete) {
                        this.complete.call(this, json);
                    } else {
                        infoTip(this.info ? this.info : '操作成功！');
                        if (formId && formId.length > 3) {
                            var winId = formId.substr(3, formId.length - 3);
                            if (winId)
                                closeDialog('win' + winId);
                        }
                        if (this.task)
                            this.task.call(this, json);
                    }
                } catch (e) {
                    errorMsg(e.message);
                }
            }
        } catch (e) {
            if ($.isXMLDoc(data))
                errorTip('服务器请求地址无响应！');
        }
    }
});


//扩展easyui表单的验证  bug #2777 zhangfenghuang 2015-12-30 begin
$.extend($.fn.validatebox.defaults.rules, {
    //验证汉字  
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只能输入中文.'
    },
    /*passwordRule : {
     validator: function(value) {
     var regex = /^(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{8,}$/;
     return regex.test(value);
     },
     message: '密码由英文字母大写、小写，数字及特殊符号构成，必须满足3种类型，且不能少于8位！'
     },*/
    // add by BaoJingyu 2017-12-09 begin
    consecutiveRepeatCharacterRegex: {
        validator: function (value) {
            var regex = /(.)(\1){2,}/;
            return !regex.test(value);//false
        },
        message: '密码中不能包含三位以上重复的字符!'
    },
    consecutiveCharacterRegex: {
        validator: function (value) {
            var regex = /((9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){2}\d|(0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){2}\d|(a(?=b)|b(?=c)|c(?=d)|d(?=e)|e(?=f)|f(?=g)|g(?=h)|h(?=i)|i(?=j)|j(?=k)|k(?=l)|l(?=m)|m(?=n)|n(?=o)|o(?=p)|p(?=q)|q(?=r)|r(?=s)|s(?=t)|t(?=u)|u(?=v)|v(?=w)|w(?=x)|x(?=y)|y(?=z)){2}\w|(z(?=y)|y(?=x)|x(?=w)|w(?=v)|v(?=u)|u(?=t)|t(?=s)|s(?=r)|r(?=q)|q(?=p)|p(?=o)|o(?=n)|n(?=m)|m(?=l)|l(?=k)|k(?=j)|j(?=i)|i(?=h)|h(?=g)|g(?=f)|f(?=e)|e(?=d)|d(?=c)|c(?=b)|b(?=a)){2}\w|(A(?=B)|B(?=C)|C(?=D)|D(?=E)|E(?=F)|F(?=G)|G(?=H)|H(?=I)|I(?=J)|J(?=K)|K(?=L)|L(?=M)|M(?=N)|N(?=O)|O(?=P)|P(?=Q)|Q(?=R)|R(?=S)|S(?=T)|T(?=U)|U(?=V)|V(?=W)|W(?=X)|X(?=Y)|Y(?=Z)){2}\w|(Z(?=Y)|Y(?=X)|X(?=W)|W(?=V)|V(?=U)|U(?=T)|T(?=S)|S(?=R)|R(?=Q)|Q(?=P)|P(?=O)|O(?=N)|N(?=M)|M(?=L)|L(?=K)|K(?=J)|J(?=I)|I(?=H)|H(?=G)|G(?=F)|F(?=E)|E(?=D)|D(?=C)|C(?=B)|B(?=A)){2}\w)/;
            return !regex.test(value);//false
        },
        message: '密码中不能包含三位以上正序或倒序的连续数字或字母!'
    },
    passwordRule: {
        validator: function (value) {
            var regex = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,}$/;
            return regex.test(value);//false
        },
        message: '密码由英文字母大写、小写，数字及特殊符号构成，必须满足3种类型，且不能少于8位!'
    },
    // add by BaoJingyu 2017-12-09 end
    // BUG #2977 zhagnfenghuang 2016-01-22 begin
    orgNameRule: {
        validator: function (value) {
            var regex = /[-|"'!%*?]+/g;
            return !regex.test(value);
        },
        message: '机构名称不能包含特殊字符 - | " \' ! % * ?'
    },
    roleCodeRule: {
        validator: function (value) {
            var regex = /[\\\/\[\]{}()|"'!%;*?:+ =#$%^&*]+/g;
            return !regex.test(value);
        },
        message: '不能包含空格和特殊字符 [ ] { } ( ) : | " \\ / \' ! % ; * ? + = # $ % ^ & *'
    },
    // BUG #2977 zhagnfenghuang 2016-01-22 end
    letterOrNum: {
        validator: function (value) {
            return /^[a-zA-Z0-9]+$/.test(value);
        },
        message: '只能输入数字或字母'
    },
    roleLength: { // 长度
        validator: function (value, param) {
            if (value == null) {
                return true;
            }
            var len = $.trim(value).length;
            return len >= param[0] && len <= param[1];
        },
        message: "输入内容长度必须介于{0}和{1}之间"
    },
    noSpecialCharAndCHS: {
        validator: function (value) {
            var regex = /[(\,\.\\\/\[\]{}<>()|"'!%;*?:+ =#$%^&*) | (\u0391-\uFFE5)]+/g;
            return !regex.test(value);
        },
        message: '不能包含中文、空格和特殊字符< > [ ] { } ( ) : | " \\ / \' ! % ; * ? + = # $ % ^ & *'
    }
});
//扩展easyui表单的验证  bug #2777 zhangfenghuang 2015-12-30 end


//调整datagrid,序号列宽度 bug ##2801 zhangfenghuang 2016-01-05 begin
$.extend($.fn.datagrid.methods, {
    fixRownumber: function (jq) {
        return jq.each(function () {
            var panel = $(this).datagrid("getPanel");
            //获取最后一行的number容器,并拷贝一份
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
            clone.css({
                "position": "absolute",
                left: -1000
            }).appendTo("body");
            var width = clone.width("auto").width();
            //默认宽度是25,所以只有大于25的时候才进行fix
            if (width > 25) {
                //多加5个像素,保持一点边距
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                $(this).datagrid("resize");
                //一些清理工作
                clone.remove();
                clone = null;
            } else {
                //还原成默认状态
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
            }
        });
    }
});

//调整datagrid,序号列宽度 bug ##2801 zhangfenghuang 2016-01-05 end


//调整datagrid,序号列宽度 bug ##2801 zhangfenghuang 2016-01-05 begin
$.extend($.fn.datagrid.methods, {
    fixRownumber: function (jq) {
        return jq.each(function () {
            var panel = $(this).datagrid("getPanel");
            //获取最后一行的number容器,并拷贝一份
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
            clone.css({
                "position": "absolute",
                left: -1000
            }).appendTo("body");
            var width = clone.width("auto").width();
            //默认宽度是25,所以只有大于25的时候才进行fix
            if (width > 25) {
                //多加5个像素,保持一点边距
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                $(this).datagrid("resize");
                //一些清理工作
                clone.remove();
                clone = null;
            } else {
                //还原成默认状态
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
            }
        });
    }
});

//bug #344 用户输入null，传到后台为空对象，原因为web包使用了StringTrimmerEditor对stirng类型做了转换，
//对“null”字符串进行了转换，转换为空对象，但是后台修改影响全局，所以在前端控制不能输入“null”
$.extend($.fn.textbox.methods, {
    fixRownumber: function (jq) {

    }
});