
/** 定义组织机构常量*/
/** 总部 */
const ORG_TYPE_HQ = 10;
/** 财务中心 */
const ORG_TYPE_FINANCE_CENTER = 20;
/** 操作中心 */
const ORG_TYPE_OPERATE_CENTER = 21;
/** 网点 */
const ORG_TYPE_SITE = 30;
/** 承包区 */
const ORG_TYPE_CONTRACT = 40;
/** 总部 */
const HQ_BASE_ORG_CODE = 'UC000142';
const VAR_OMS_TREEID = 386;
const VAR_OMS_ORDER_ACCEPT_MENU = '网点接单';
var ICS_MESSAGE_PATH = "";
var OMS_MESSAGE_PATH = "";

var empCode = "";
var cmsOrgCode = "";
var cmsOrgType = "";
var ICS_SWITCH = false;
var OMS_SWITCH = false;
var ICS_INTERVAL_TIME = 60 * 1000;
var OMS_INTERVAL_TIME = 60 * 1000;
var ICS_SING_MSG_TIMEOUT = 0;
var ICS_OLD_INTERFACE_SWITCH = false;

//定时刷新消息 
$(function() {
    try{

        /**
         * 加载数据字典、当前用户
         */
        var pageContext = $("#pageContext").val();
        loadDataDict(pageContext);

        /**
         * -------------------------------- ICS消息提醒 --------------------------------
         */
        menuPermissionCheck('ICS');
        if(ICS_SWITCH){
            //初始化加载
            showMsgInterval();
            //定时抓取任务时间初始化
            setInterval(showMsgInterval,ICS_INTERVAL_TIME);
        };

        /**
         * -------------------------------- OMS消息提醒 --------------------------------
         */
        if(OMS_SWITCH){
            //网点信息为空 总部 不提醒
            if('' == cmsOrgCode || null == cmsOrgCode ||  HQ_BASE_ORG_CODE == cmsOrgCode){
                return;
            }
            if(ORG_TYPE_HQ == cmsOrgType || ORG_TYPE_FINANCE_CENTER == cmsOrgType || ORG_TYPE_OPERATE_CENTER == cmsOrgType || HQ_BASE_ORG_CODE == cmsOrgCode){
                return;
            }
            findRemindNotices();
            setInterval(function() {
                findRemindNotices();
            }, OMS_INTERVAL_TIME);
        }
    }catch(e){

    }
})
/**
 * 加载数据字典
 */
function loadDataDict(pageContext){
    $.ajax({
        url: pageContext+'/portal/getDictDataByTypeClassCode.action',
        async:false,
        data: {typeClassCode: 'MSG_REMIND_SORCE_ADDRESS'},
        success: function(result){
            if(!result)return;
            for (var i = 0; i < result.length; i++) {
                if (result[i].typeName == "ICS_MESSAGE_SWITCH" && result[i].typeCode == "ICS_OPEN") {
                    ICS_SWITCH = true;
                }
                if (result[i].typeName == "OMS_MESSAGE_SWITCH" && result[i].typeCode == "OMS_OPEN") {
                    OMS_SWITCH = true;
                }
                if (result[i].typeName == "ICS_OLD_INTERFACE_SWITCH" && result[i].typeCode == "OLD") {
                    ICS_OLD_INTERFACE_SWITCH = true;
                }
            }
            for (var i = 0; i < result.length; i++) {
                if (result[i].typeName == "ICS_MESSAGE_PATH" && ICS_SWITCH) {
                    //备战域名时修改子菜单中的域名 haungting 2019/11/09
                    ICS_MESSAGE_PATH = window.top.vm.updateHost(result[i].typeCode);
                }
                if (result[i].typeName == "OMS_MESSAGE_PATH" && OMS_SWITCH) {
                    //备战域名时修改子菜单中的域名 haungting 2019/11/09
                    OMS_MESSAGE_PATH =  window.top.vm.updateHost(result[i].typeCode);
                }
            }
        }
    });
    //加载当前用户信息
    $.ajax({
        url: '../portal/getCurrentUser.action',
        async:false,
        success: function(result) {
            empCode = result.empCode;
            cmsOrgCode = result.cmsBaseOrgCode;
            cmsOrgType = result.cmsOrgType;
        }
    });
    //加载ICS,OMS轮询间隔
    $.ajax({
        url: pageContext+'/portal/getDictDataByTypeClassCode.action',
        async:false,
        data: {typeClassCode: 'MSG_REMIND_INTERVAL_TIME'},
        success: function(result){
            if(!result)return;
            for (var i = 0; i < result.length; i++) {
                if (result[i].typeName == "ICS_INTERVAL_TIME") {
                    if (eval(result[i].typeCode)) {
                        ICS_INTERVAL_TIME = eval(result[i].typeCode);
                    }
                }
                if (result[i].typeName == "OMS_INTERVAL_TIME") {
                    if (eval(result[i].typeCode)) {
                        OMS_INTERVAL_TIME = eval(result[i].typeCode);
                    }
                }
                if (result[i].typeName == "ICS_SING_MSG_TIMEOUT") {
                    if (eval(result[i].typeCode)) {
                        ICS_SING_MSG_TIMEOUT = eval(result[i].typeCode);
                    }
                }
            }
        }
    });
}

/**
 * ICS菜单权限校验
 */
function menuPermissionCheck(systemCode){
    $.ajax({
        url: '../portal/menuPermissionCheck.action',
        async:false,
        data:{empCode:empCode,systemCode:systemCode},
        success: function(result) {
            if(ICS_SWITCH){
                ICS_SWITCH = result;
            }
            if (!result) {
                $("#totalMsgSettingSpan").hide();
                $("#totalMsgSetting").hide();
            }
        }
    });
}

/**
 * ICS消息初始化
 */
function showMsgInterval(){
    var url = ICS_MESSAGE_PATH + 'ics-remind/cs/CsTaskMsg/findCurrentMsg.do';
    if(ICS_OLD_INTERFACE_SWITCH){
        url = ICS_MESSAGE_PATH + '/cs/CsMsg/findCurrentMsg.do';
    }
    var msgs=new Array();
    $.ajax({
        url : url,
        type : 'get',
        xhrFields: {withCredentials: true},
        success : function(result){
            if(ICS_OLD_INTERFACE_SWITCH){
                if(result && result.success && result.rows){
                    msgs=result.rows;
                    for(var i=0,len=result.rows.length;i<len;i++){
                        showMag(result.rows[len-(i+1)].msg,'消息提醒',null,'ICS',null);
                    }
                }
            }else{
                if(result && result.success && result.data){
                    var val = result.data;
                    //弹窗没有消失跳过处理
                    if($("#msg_"+val.woCode).length > 0){
                        return false;
                    }
                    showTaskMsg(val);
                }
            }
        }
    });
    //唤起统计dialog
    /**
     setTimeout(function(){
		totalMsg('timeout');
	},2000);
     */
}

/**
 * 查询工单类型名称
 * */
function showTaskMsg(val){
    var msgId = "msg_"+val.woCode;
    var url ='ics'+val.woTabUrl;
    var orderChilTypeName="";
    if(val.orderChilTypeName){
        orderChilTypeName = val.orderChilTypeName;
    }
    var msg = '<a href="javascript:linkToTab(\''
        + val.woTabName
        + '\',\''
        + msgId
        + '\',\''
        + url
        + '\',\''
        + val.woDealType
        + '\',\''
        + val.woCode
        + '\',\''
        + 'ICS'
        + '\')"><strong style="margin-left:40px;">'+val.woTypeName+'-'+formatWoDealTypeName(val.woDealType)+'' +
        '</strong></br>收到一条"' + val.woTypeName.split("-")[0]+ '"' + formatWoDealTypeName(val.woDealType) + '提醒' +
        '</br>' +
        '工单编号:' + val.woCode +
        "</br>" +
        orderChilTypeName +
        '</a>';
    showMag(msg,"消息提醒",msgId,'ICS',null);
}

function formatWoDealTypeName(woDealType){
    if(!woDealType){
        return "待认领";
    }else{
        return "待办";
    }
}

/**
 * 更新已读消息
 * */
function msgRead(msgId,woCode){
    $.ajax({
        url : ICS_MESSAGE_PATH + 'ics-remind/cs/CsTaskMsg/updateMsgRead/'+woCode+'.do',
        type : 'get',
        xhrFields: {withCredentials: true},
        success : function(result){
            if(result.success){
                $('#'+msgId).parent("div.messager-body").window("close");
            }
        }
    });
}

/**
 * OMS/ICS弹出普通消息
 */
function showMag(msg,title,msgId,systemCode,icon) {
    title = title?title:'消息提醒';
    title = '<span style="font-size:14px;font-weight:bold;margin-top:0;color:#ffffff;">' + title + '</span>'
    initNewObject(msg,title,"messageTypeCode",title,'permissionCode',systemCode,"url",null,msgId,icon);
}
/**
 * OMS/ICS弹出普通消息,备份
 */
function showMagCopy(msg,title,msgId){
    var timeOut = 20000;
    if(msgId){
        timeOut = 0;
    }
    title = title?title:'消息提醒';
    var showOption={
        title:'<div style="font-size:15px;font-weight:bold;margin-top:0;color:#7747ab;">' + title + '</div>',
        msg:'',
        timeout: timeOut,
        showType:'show'
    }
    showOption.msg=
        '</div><div style="color:#777272;height:50px" class="msg_a" id="'+msgId+'">' + msg + '</div>';
    document.getElementById("audio").play();
    var $msgMag = $.messager.show(showOption);
    $('.msg_a').parentsUntil('.panel window').css('height','auto');
    $('.msg_a').parentsUntil('.panel window').css('right','5px');
    $('.msg_a').parentsUntil('.panel window').css('bottom','28px');
    $('.msg_a').parentsUntil('.panel window').css('border-radius','4px');
    if(msgId){
        $msgMag.parent().find(".panel-tool-close").bind("click",function(){
            var woCode = msgId.split("_")[1];
            msgRead(msgId,woCode);
        });
        setTimeout(function(){
            $msgMag.parent().find(".panel-tool-close").trigger("click");
        }, ICS_SING_MSG_TIMEOUT);
    }
}

//ICS：弹出未读数据
function openUnReadMsgdlg(){
    totalMsg('page');
}

//ICS：未读数据
function totalMsg(type){
    var url = ICS_MESSAGE_PATH + 'ics-remind/cs/CsTaskMsg/findCountCurrentMsg.do';
    if(ICS_OLD_INTERFACE_SWITCH){
        url = ICS_MESSAGE_PATH + '/cs/CsMsg/findCountCurrentMsg.do';
    }
    $.ajax({
        url : url,
        type : 'post',
        xhrFields: {withCredentials: true},
        success : function(result){
            if(result.success) {
                if(result.total >0 ){
                    showUnReadMsg();
                }else{
                    if(type == 'timeout'){
                        //不展示数据
                        return;
                    }else if(type == 'page'){
                        showInfoMsg("当前无未读数据");
                        return;
                    }
                }
            }
        }
    });
}
function showUnReadMsg(){
    var columns = [[
        {field: 'woTypeName_DealTypeName', title: '<div style="font-size:10px;font-weight:bold;margin-top:0;color:#7747ab;">工单类型</div>', align: 'center',width:140,formatter: function(value,row,index){
                var url ='ics'+row.woTabUrl;
                var msgId='all';
                return '<a href="javascript:linkToTab(\''
                    + row.woTabName
                    + '\',\''
                    + msgId
                    + '\',\''
                    + url
                    + '\',\''
                    + row.woType
                    + '\',\''
                    + row.woCode
                    + '\')">'+row.woTypeName+'-'+formatWoDealTypeName(row.woDealType)+'</a>';
            }},
        {field: 'count', title: '<div style="font-size:10px;font-weight:bold;margin-top:0;color:#7747ab;">未读总数</div>', align: 'center',width:90}
    ]];
    var dataGridParams = {
        //获取数据
        url: '',
        queryParams:{ },
        singleSelect : 'false',
        fitColumns : 'false',
        rownumbers: 'false',
        striped: false,
        pagination:false
    }
    newloadGrid('totalDataGridMsg', columns, dataGridParams);
    var url = ICS_MESSAGE_PATH + 'ics-remind/cs/CsTaskMsg/findCountCurrentMsg.do';
    if(ICS_OLD_INTERFACE_SWITCH){
        url = ICS_MESSAGE_PATH + '/cs/CsMsg/findCountCurrentMsg.do';
    }
    $.ajax({
        url : url,
        type : 'post',
        async:false,
        xhrFields: {withCredentials: true},
        success : function(result){
            $('#totalDataGridMsg').datagrid('loadData',result);
        }
    });
    showTotalDialog();
}

function linkToTab(tabName,msgId,tabUrl,woType,woCode,isIcs){
    if(msgId != "" & 'all' != msgId){
        //当前消息标记为已经读取
        openTabCrossDomainIcsSimple("message_notice",tabName,tabUrl+'?woMsgCode='+woCode,true,0,'ICS',true);
        //$('#'+msgId).parentsUntil(".messager-body").parentsUntil(".panel window").window('close');
        //--------------------去掉点击详情时重复调用ICS阅读方法----------------------
        /*if(isIcs){
            var msgId = "msg_"+woCode;
            msgRead(msgId,woCode);
        }*/
    }else if(msgId == 'all'){
        openTabCrossDomainIcsSimple("message_notice",tabName,tabUrl+'?totalMsgflag=1',true,0,'ICS',true);
        $('#totalDialog').dialog('close');
    }
}
/**
 *
 * @param perssion 权限Code
 * @param title 页面标题
 * @param url URL
 */
function openTabCrossDomainIcsSimple(perssion,titles,url,param1,param2,param3,param4){
    if(titles == null || titles == ' ' || titles == ''){
        titles = '';
    }

    var accessurl = ICS_MESSAGE_PATH + url;

    //openTabCrossDomain(perssion,param,{title:titles,systemCode:'ICS',url:accessurl});
    window.top.vm.openTab(titles,accessurl,true,null,'ICS',true,3);
}
//ICS： 弹出消息统计框
function showTotalDialog(){
    var dialogWidth = 250;		// dialog的宽度
    var dialogHeight = 350;		// dialog的高度
    var topPosition = $(window).height() - dialogHeight;
    var leftPosition = $(window).width() - dialogWidth;
    $('#totalDialog').dialog({
        width: dialogWidth,
        height: dialogHeight,
        top : topPosition,
        left : leftPosition-5,
        right:5,
        title:'<div style="font-size:15px;font-weight:bold;margin-top:0;color:#7747ab;">消息统计</div>'
    }).dialog('open');
    $('#totalDialog').parents('.panel').css('right','5px');
    $('#totalDialog').parents('.panel').css('bottom','150px');
    $('#totalDialog').parents('.panel').css('border-radius','4px');
    $('#totalDialog').parent().next('.window-shadow').hide();
}

/**
 * OMS消息提醒，弹出框js，刷新消息信息
 */
function findRemindNotices() {
    //var cmsOrgCode = decodeURI('${sessionScope.CURRENT_USER.cmsBaseOrgCode}');
    // 查询
    try {
        var visitUrl = OMS_MESSAGE_PATH + '/oms-order-remind/common/findRemindNotices.do';
        // 查询公告信息
        $.ajax({
            url : visitUrl,
            data : $.param({
                'acceptSiteMay' : cmsOrgCode
            }, true),
            xhrFields: {withCredentials: true},
            success : function(data) {
                if (!data.success) {
                    return;
                }
                if ("" == data.data
                    || typeof (data.data) == 'undefined') {
                    return;
                }
                if (window.Notification) {
                    if (Notification.permission == "granted") {
                        popNotice(data);
                    } else if (Notification.permission != "denied") {
                        Notification.requestPermission(function (permission) {
                            popNotice(data);
                        });
                    }else {
                        showInfoTipOms(data.data);
                    }
                } else {
                    showInfoTipOms(data.data);
                }
            },
            error : function(request) {}
        });
    } catch (e) {
        console.log("ERR_NETWORK_CHANGED");
    }
}
/**
 * show消息功能(从右下角弹出)
 * @param msg 消息内容
 * @param title 消息标题,默认'信息提示'(可选参数)
 * @param timeout 超时时间,默认3000毫秒(可选参数)
 */
function showInfoTipOms(messageNum) {
    showMag("<a href='#' style='margin-top:36px;display:inline-block;' id='" + messageNum + "' onclick='openOmsMessageTab("+messageNum+")'>您有" + messageNum + "条未读订单</a>",'新订单提醒',null,'OMS',null);

}
var popNotice = function(data) {
    if (Notification.permission == "granted") {
        var notification = new Notification("新订单提醒", {
            body: '您有'+ data.data + '条,未读订单！',
            icon: '../scripts/common/oms1.png'
        });

        notification.onclick = function() {
            openOmsMessageTab(data.data);
            notification.close();
        };
    }else{
        showInfoTipOms(data.data);
    }
};

function openOmsMessageTab(messageNum) {
    var title = VAR_OMS_ORDER_ACCEPT_MENU;
    var accessurl = OMS_MESSAGE_PATH + '/oms-order-main/orderAccept/forward.do?acceptFlag=' + true;
    treeid = VAR_OMS_TREEID;
    selectRemindNotices();
    //$($('#'+messageNum).parentsUntil(".messager-body").parentsUntil(".panel window")[0]).window('close');
    window.top.vm.openTab(title,accessurl,true,null,'OMS',true,3);

}
/**
 * 全部已读
 */
function selectRemindNotices() {
    uceAjax(OMS_MESSAGE_PATH + '/oms-order-remind/common/selectRemindNotices.do',
        $.param({
            'acceptSiteMay' : cmsOrgCode
        }, true), function(row) {
            console.log("今日消息已读")
        });
}

/**
 * 打开预付款充值页面,关闭消息弹框
 */
function rechargeMoney() {
    window.postMessage({ fn: 'openTab', permissionCode: 'recharge_management_code', params: {}, attr: {systemCode:'recharge_management_code'} }, '*');
    //$('#rechargeMoney').parentsUntil(".messager-body").parentsUntil(".panel window").window('close');
}