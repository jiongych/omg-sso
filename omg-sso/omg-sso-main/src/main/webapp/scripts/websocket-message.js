
var messageEmpCode = "";
var messageCmsOrgCode = "";
var messageCmsOrgType = "";
var socketUrl = "";
var socketHttpUrl = "";
var messageSwitch = "open";
var messageTimeOut = 60;
var messagePushTimeOut = 5 * 1000;
var messagePushType = "socket";
var idx=1;
var timer;
var timeOutChange;
var ignoreType = [];
//定时刷新消息 
$(function() {
    initDict();
    setTimeout(() => {
        pushMessage();
}, 5000);
})
/**
 * 获取当前用户
 */
function loadCurrentUser(){
    //加载当前用户信息
    $.ajax({
        url: '../portal/getCurrentUser.action',
        async:false,
        success: function(result) {
            messageEmpCode = result.empCode;
            messageCmsOrgCode = result.cmsBaseOrgCode;
            messageCmsOrgType = result.cmsOrgType;
            initSocket();
        }
    });
}
function initDict() {
    $.ajax({
        url: '../portal/getDictDataByTypeClassCode.action',
        data: { typeClassCode: 'MCS_PORTAL_INFO' },
        success: function (result) {
            result.forEach(element => {
                //socket连接地址
                if (element.typeName == "MCS_PORTAL_SOCKET_URL") {
                socketUrl = window.top.vm.updateHost(element.typeCode);
            }
            //socket开关
            if (element.typeName == "MCS_PORTAL_SWITCH") {
                messageSwitch = element.typeCode;
            }
            //弹框关闭时间
            if (element.typeName == "MCS_TIME_OUT") {
                messageTimeOut = element.typeCode;
            }
            //站内消息http地址
            if (element.typeName == "MCS_PORTAL_HTTP_URL") {
                socketHttpUrl =window.top.vm.updateHost(element.typeCode);
                getUserIgnoreType();
            }
            //消息获取方式socket或http
            if (element.typeName == "MCS_PUSH_TYPE") {
                messagePushType = element.typeCode;
            }
            //如果是http获取，获取相隔时间
            if (element.typeName == "MCS_PUSH_TIME_OUT") {
                messagePushTimeOut = element.typeCode*1000;
            }
        });
        }
    })
}
function getUserIgnoreType() {
    $.ajax({
        url: socketHttpUrl + 'gateway/findIgnoreTypeCodeByUser.do',
        xhrFields: {withCredentials: true},
        data: {},
        success: function (result) {
            ignoreType = result;
        }
    })
}
function pushMessage() {
    if (messageSwitch == 'close') {
        return;
    } else {
        if (messagePushType == "http") {
            initHttpMessage()
        } else {
            loadCurrentUser();
        }
        findUnReadMessage();
    }
}
/* 从站内消息系统中获取最新未读消息*/
var beforeDate;
function initHttpMessage() {
    $.ajax({
        url: socketHttpUrl + 'gateway/findNewMessage.do',
        xhrFields: {withCredentials: true},
        data: {beforeLongDate:beforeDate},
        success: function (result) {
            idx=1;
            timeOutChange=messagePushTimeOut;
            setTimeout("initHttpMessage()",timeOutChange);
            if (result == null || result.length == 0) {
                return;
            }
            for (var i = result.length - 1; i >= 0; i--) {
                var title = result[i].title;
                result[i].title = htmlRestore(title);
                var content = result[i].content;
                result[i].content = htmlRestore(content);
                if (result[i].status == '09') {
                    window.top.vm.removeMsg(result[i])
                } else if (null != title && (title.indexOf("网点的通知") != -1 || title.indexOf("网点转让") != -1 || title.indexOf("网点暂停") != -1)) {
                    sendMessageStatus(result[i].reqId, result[i].empCode, '02');
                } else {
                    var ignoreFlag = false;
                    //判断消息类型是否被忽略
                    if (null != ignoreType && ignoreType.length > 0 && result[i].portalMessageTypeVo && result[i].portalMessageTypeVo.sonLevel) {
                        var sonLevel = result[i].portalMessageTypeVo.sonLevel;
                        //遍历寻找被忽略的类型
                        ignoreType.forEach(function(val, index ) {
                            if (sonLevel == val) {
                                ignoreFlag = true;
                                return;
                            }
                        });
                    }
                    if (ignoreFlag) {
                        sendMessageStatus(result[i].reqId, result[i].empCode, '02');
                    } else {
                        var messageInfo = JSON.stringify(result[i]);
                        window.top.vm.msgTips(messageInfo,messageTimeOut);
                    }
                }
                beforeDate = result[i].beforeDate;
            }
            //当有新消息查询到时获取最新数据
            findUnReadMessage();
        },
        error:function(err){
            idx++;
            timeOutChange=messagePushTimeOut*idx;
            timer=setTimeout("initHttpMessage()",timeOutChange);
        }
    })
}

/**
 *  转义字符还原成html字符
 * @param str
 * @returns {string}
 * @constructor
 */
function htmlRestore(str) {
    var s = "";
    if(!str){
        return "";
    }
    if (str.length === 0) {
        return "";
    }
    s = str.replaceAll(/&amp;/g, "&");
    s = s.replaceAll(/&lt;/g, "<");
    s = s.replaceAll(/&gt;/g, ">");
    s = s.replaceAll(/&nbsp;/g, " ");
    s = s.replaceAll(/&#39;/g, "\'");
    s = s.replaceAll(/&quot;/g, "\"");
    return s;
}
String.prototype.replaceAll=function(f,e){
    var reg = new RegExp(f,"g");
    return this.replace(reg,e);
}

/* 从站内消息系统中获取用户未读消息类型和数量*/
function findUnReadMessage(idx) {
    $.ajax({
        url: socketHttpUrl + 'gateway/findUnReadMessage.do',
        xhrFields: {withCredentials: true},
        data: {},
        success: function (result) {
            var messageTotal = 0;
            if (null != result) {
                messageTotal =  result.unReadTotal;
                if (null != result.unReadMessageList && result.unReadMessageList.length > 0) {
                    var divA = document.getElementById("unReadMessage");
                    var htmlCon = "<div style='padding: 0px 15px'><i class='iconfont uce-remind-circle' style='color:#645dba'></i>&nbsp;&nbsp;新消息提醒(<lable style='color:red' id='mustReadFlag'>"+ messageTotal+"</lable>)<a class='update-msg' style='float:right' onclick='updateMessageReadStatus(1)'>全部标记为己读</a></div><hr style='border:0.5px solid #eaeaea;'><div class='msg-content' style='height:200px;overflow:auto;padding:0 10%;'>";
                    let arr=result.unReadMessageList;
                    //标记已读，初始化数量为0
                    if(idx==1){
                        messageTotal=0;
                    }
                    for(let index of arr.keys()){
                        var typeName = arr[index].typeName;
                        if (null == typeName || typeName.length == 0) {
                            typeName = "其它";
                        }
                        let strHtml="";
                        //循环判断是否必读，添加必读标识，计算必读数量和
                        if(arr[index].mustReadFlag==1){
                            strHtml="<span style='color:#f00;font-size:12px;vertical-align: middle;display:inline-block;margin-left:-7px'>【必读】</span>";
                            if(idx==1&&arr[index].typeMessageTotal){
                                messageTotal+=arr[index].typeMessageTotal;
                            }
                        }
                        if(arr[index].mustReadFlag!=1 && arr[index].messageLevel==2 ){
                            strHtml="<span style='color:#f00;font-size:12px;vertical-align: middle;display:inline-block;margin-left:-7px'>【重要】</span>";

                        }
                        //标记已读，展示必读数据，跳出当前循环，非必读,正常展示数据。
                        if(idx==1){
                            if(arr[index].mustReadFlag==1){
                                var content = "<a onclick='openPermission(\"" + arr[index].typeCode + "\")'>"+strHtml+ typeName + "(<lable style='color:red'>" +arr[index].typeMessageTotal + "</lable>)</a>";
                                htmlCon += "<div style='width:50%;height:30px;text-align:left;float:left'>" + content + "</div>";
                                continue;
                            }
                        }else{
                            var content = "<a onclick='openPermission(\"" + arr[index].typeCode + "\")'>"+strHtml+ typeName + "(<lable style='color:red'>" +arr[index].typeMessageTotal + "</lable>)</a>";
                            htmlCon += "<div style='width:50%;height:30px;text-align:left;float:left'>" + content + "</div>";
                        }
                    }
                    htmlCon += "</div>";
                    htmlCon +="<hr style='border:0.5px solid #eaeaea;'><div><a style='float: left;color:blue;margin-left: 5%;' onclick='findMessageType()'><i title='消息提醒设置' class='iconfont uce-set' style='color:#645dba;font-size: 16px;'></i></a><a style='text-align: center;text-decoration:underline;color:#645dba;margin-left: 30%;' onclick='openPermission()'>查看全部未读</a><div>";
                    divA.innerHTML = htmlCon;
                } else {
                    var divA = document.getElementById("unReadMessage");
                    var htmlCon = "<div style='padding: 0px 15px'><i class='iconfont uce-remind-circle' style='color:#645dba'></i>&nbsp;&nbsp;新消息提醒(<a style='color:red'>0</a>)</div><hr style='border:0.5px solid #eaeaea;'>";
                    htmlCon += "<div><div style='text-align: center;height: 200px;'><p style='padding-top: 50px;height: 64px;'><img src='../images/empty.png'/></p>暂时没有未读消息。</div><div>";
                    htmlCon +="<div><hr style='border:0.5px solid #eaeaea;'><a style='float: left;color:blue;margin-left: 5%;' onclick='findMessageType()'><i title='消息提醒设置' class='iconfont uce-set' style='color:#645dba;font-size: 16px;'></i></a><a style='text-align: center;text-decoration:underline;color:#645dba;margin-left: 30%;' onclick='openPermission(-1)'>查看历史消息</a><div>";
                    divA.innerHTML = htmlCon;
                }
                //标记已读，提醒消息数量赋值，无非必读消息，显示空提示。
                if(idx==1){
                    document.getElementById("mustReadFlag").innerHTML=messageTotal;
                    if(messageTotal===0){
                        var divA = document.getElementById("unReadMessage");
                        var htmlCon = "<div style='padding: 0px 15px'><i class='iconfont uce-remind-circle' style='color:#645dba'></i>&nbsp;&nbsp;新消息提醒(<a style='color:red'>0</a>)</div><hr style='border:0.5px solid #eaeaea;'>";
                        htmlCon += "<div><div style='text-align: center;height: 200px;'><p style='padding-top: 50px;height: 64px;'><img src='../images/empty.png'/></p>暂时没有未读消息。</div><div>";
                        htmlCon +="<div><hr style='border:0.5px solid #eaeaea;'><a style='float: left;color:blue;margin-left: 5%;' onclick='findMessageType()'><i title='消息提醒设置' class='iconfont uce-set' style='color:#645dba;font-size: 16px;'></i></a><a style='text-align: center;text-decoration:underline;color:#645dba;margin-left: 30%;' onclick='openPermission(-1)'>查看历史消息</a><div>";
                        divA.innerHTML = htmlCon;
                    }
                }
                //数量赋值
                messageTotal>99?messageTotal="99+":messageTotal;
                window.top.vm.messageTotal=messageTotal;
            }
        },
        error: function(error) {
            var divEle = document.getElementById("unReadMessage");
            var htmlCon = "<div style='padding: 0px 15px'><i class='iconfont uce-remind-circle' style='color:#645dba'></i>&nbsp;&nbsp;新消息提醒(<lable style='color:red'>0</lable>)</div><hr style='border:0.5px solid #eaeaea;'>";
            htmlCon += "<div><lable style='display: block;text-align: center;height: 200px;line-height: 200px;'>网络异常，请稍后重试。或点击<a style='color:red' onclick='findUnReadMessage()'>刷新</a>。</lable><div>";
            htmlCon +="<div><hr style='border:0.5px solid #eaeaea;'><a style='float: left;color:blue;margin-left: 5%;' onclick='findMessageType()'><i title='消息提醒设置' class='iconfont uce-set' style='color:#645dba;font-size: 16px;'></i></a><a style='text-align: center;text-decoration:underline;color:#645dba;margin-left: 30%;' onclick='openPermission()'>查看全部未读</a><div>";
            divEle.innerHTML = htmlCon;
        }
    })
}

function initSocket() {
    var num = RandomNum(1000,9999);
    //服务器地址
    var url = socketUrl + "mcs-portal-socket/webSocket/" + messageEmpCode + "_" + num;
    //一些对浏览器的兼容已经在插件里面完成
    var websocket = new ReconnectingWebSocket(url);

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        var result = JSON.parse(event.data);
        setMessageInnerHTML(result.content);
        sendMessageStatus(result.reqId, result.empCode, '01');
        window.top.vm.msgTips(event.data,messageTimeOut);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(sendMessage) {
        //document.getElementById('message').innerHTML = sendMessage;
    }
    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

}
//取随机数，从Min到Max之间
function RandomNum(Min, Max) {
    var Range = Max - Min;
    var Rand = Math.random();
    var num = Min + Math.round(Rand * Range); //四舍五入
    return num;
}

//消息在web端接收后的回调函数（更新消息状态）
function sendMessageStatus(reqId, empCode, status) {
    $.ajax({
        url:'updateMessageStatus.action',
        async:false,
        data: {reqId: reqId, empCode:empCode, status: status},
        success: function(result){
            setTimeout(() => {
                findUnReadMessage();
        }, 3000);
        }
    });
}
//openType:打开方式 url:通过url打开，permissionCode或null：通过permissionCode打开
function initNewObject(content,title,messageTypeCode,sonLevelName,permissionCode,systemCode,openType,url,msgId,icon) {
    var portalMessageReceiverVo = new Object();
    var portalMessageTypeVo = new Object();
    portalMessageReceiverVo.content=content;
    portalMessageReceiverVo.title=title;
    portalMessageReceiverVo.messageTypeCode=messageTypeCode;
    portalMessageReceiverVo.sonLevelName=sonLevelName;
    portalMessageReceiverVo.reqId=systemCode+new Date().getTime();
    portalMessageReceiverVo.updateStatus="no";
    portalMessageReceiverVo.openType=openType;
    portalMessageReceiverVo.url=url;
    portalMessageReceiverVo.msgId=msgId;
    portalMessageTypeVo.icon=icon;
    portalMessageTypeVo.sonLevelName=sonLevelName;
    portalMessageTypeVo.permissionCode=permissionCode;
    portalMessageTypeVo.systemCode=systemCode;
    portalMessageReceiverVo.portalMessageTypeVo=portalMessageTypeVo;
    window.top.vm.msgTips(portalMessageReceiverVo,messageTimeOut);
}

//打开未读消息弹框
function openUnReadPortalMessage() {
    var divEle = document.getElementById("unReadMessage");
    var htmlCon = "<div style='padding: 0px 15px'><i class='iconfont uce-remind-circle' style='color:#645dba'></i>&nbsp;&nbsp;新消息提醒(<lable style='color:red'>0</lable>)</div><hr style='border:0.5px solid #eaeaea;'>";
    htmlCon += "<div><lable style='display: block;text-align: center;height: 200px;line-height: 200px;'>正在查询您的未读消息。</lable><div>";
    htmlCon +="<div><hr style='border:0.5px solid #eaeaea;'><a style='float: left;color:blue;margin-left: 5%;' onclick='findMessageType()'><i title='消息提醒设置' class='iconfont uce-set' style='color:#645dba;font-size: 16px;'></i></a><a style='text-align: center;text-decoration:underline;color:#645dba;margin-left: 30%;' onclick='openPermission()'>查看全部未读</a><div>";
    divEle.innerHTML = htmlCon;

    var checkUnReadDivStatus = $('#unReadMessage').is(':hidden');
    if (checkUnReadDivStatus) {
        findUnReadMessage();
        $("#unReadMessage").show();
    } else {
        $("#unReadMessage").hide();
    }

}

//关闭未读消息弹框
var closeUnReadPortalMessageValue = 0;
function closeUnReadPortalMessage(timeOut) {
    var id = setTimeout(() => {
        $("#unReadMessage").hide();
}, timeOut);
    closeUnReadPortalMessageValue = id;
}
//清除定时关闭消息弹框
function onUnReadPortalMessage() {
    clearTimeout(closeUnReadPortalMessageValue);
}

//根据消息打开我的消息列表：-1为查看己读
function openPermission(messageType) {
    $("#unReadMessage").hide();
    window.top.vm.openTab("消息列表", socketHttpUrl + "/message/forward.do?messageType=" + messageType, true, null,'MCS',true,5);
}
//全部更新己读接口
function updateMessageReadStatus(idx) {
    //$("#unReadMessage").hide();
    findUnReadMessage(idx);
    $.ajax({
        url: socketHttpUrl + 'gateway/updateMessageReadStatus.do',
        xhrFields: {withCredentials: true},
        data: {},
        success: function (result) {
            // setTimeout(() => {
            // 	findUnReadMessage(idx);
            // }, 3000);
            //window.top.vm.messageRemind("消息己读标记成功。");
        }
    })
}

//获取消息类型并构建页面
function findMessageType() {
    $('#messageSelfSet').dialog('options').title = "消息提醒设置";
    $('#messageSelfSet').dialog({ modal: true }).dialog('open');
    $.ajax({
        url: socketHttpUrl + 'gateway/findMessageType.do',
        xhrFields: {withCredentials: true},
        timeout : 5000,
        data: {},
        success: function (result) {
            if (null != result && result.length > 0) {
                var divA = document.getElementById("seltSetDetail");
                var htmlCon = "";
                result.forEach(function(val, index) {
                    htmlCon += "<div style='margin:10px 0px 0px 0px;'> <i class='iconfont uce-jiedan' style='color:#f5683d;font-size:16px;'></i><lable style='color:#645dba;font-size:16px;margin-left:5px;'>"
                    htmlCon += val.parentLevelName;
                    htmlCon += "</lable>";

                    var messageSonType = val.portalMessageTypeVoList;
                    if (null != messageSonType && messageSonType.length > 0) {
                        htmlCon += "<div style='margin: 10px 0px 0px 0px;overflow:hidden'>";
                        messageSonType.forEach(function(sonType, sonIndex) {
                            htmlCon += "<div style='margin: 5px 0px 0px 20px;width: 180px;height: 30px;display: inline;float: left;'>";
                            htmlCon += "<input style='margin-left:15px;' name='messageSonCheck' id='messageSonCheck' type='checkbox'";
                            if (sonType.ignoreFlag == 1) {
                                htmlCon += " disabled='disabled'";
                            }
                            var userIgnoreF = sonType.userIgnoreF;
                            if (null != userIgnoreF && userIgnoreF.length > 0) {
                                htmlCon += " checked='checked'";
                            }

                            htmlCon += " align='right' value='"+ sonType.sonLevel +"'/>";
                            htmlCon += "<lable style='font-size:14px;margin-left:10px;width:80px;";
                            if (sonType.ignoreFlag == 1) {
                                htmlCon += "color:#9c9191";
                            }
                            htmlCon += "'>";
                            htmlCon += sonType.sonLevelName;
                            htmlCon += "</lable>";
                            htmlCon += "</div>";
                        });
                        htmlCon += "</div>";
                    }
                    htmlCon += "</div>";
                });
                divA.innerHTML = htmlCon;
            }
        },
        error: function(error) {
            var divEle = document.getElementById("seltSetDetail");
            var htmlCon = "<div><lable style='display: block;text-align: center;margin-top:20%'>网络异常，请稍后重试。或点击<a style='color:red' onclick='findMessageType()'>刷新</a></lable><div>";
            divEle.innerHTML = htmlCon;
        }
    })
}

function saveMessageSelfSet() {

    var typeCodes = "";
    $("input[name='messageSonCheck']:checked").each(function(i){
        typeCodes += $(this).val() + ",";
    })
    $.ajax({
        url: socketHttpUrl + 'gateway/saveIgnoreTypeCode.do',
        xhrFields: {withCredentials: true},
        type: "post",
        timeout : 5000,
        data: {typeCodes:typeCodes},
        success: function (result) {
            if (result) {
                getUserIgnoreType();
                window.top.vm.messageRemind("消息提醒设置成功。");
            } else {
                window.top.vm.messageRemind("消息提醒设置失败，请稍后重试。");
            }
            closeDialog('messageSelfSet');
        },
        error: function(error) {
            var errorStatus = error.status;
            if (404 == errorStatus) {
                window.top.vm.messageRemind("网络异常，请稍后重试。");
            } else {
                window.top.vm.messageRemind("消息提醒设置失败，请稍后重试。");
            }

            closeDialog('messageSelfSet');
        }
    })
}