/***
 * 首页用户向导
 * @param next
 */
function nextStep(next){
	$(".tipbox").css({"visibility":"hidden","display":"none"});
	$(".tipbar").hide();
	$("#step" + next).css({"visibility":"visible","display":"block"});
	$("#tipbar" + (next -1)).show();
	$('.notip').hide();
    var windowW = $(window).width();
	if(next == 2) {

	}else if(next == 3) {

	}else if(next==4){
		if(windowW>1400){
			$('#tipbar3').css('right', '41px');
		}else{
			$('#tipbar3').css('right', '26px');
			$('#tipbar3').css('top', '414px');
		}
	}
	if(next == 5){
		$(".tipSwitchAnimate").css("top","539px");
		$('.notip').show();
	}else if(next == 6){
		$(".tipSwitchAnimate").css("top","357px");
	}else {
		$(".tipSwitchAnimate").css("top","352px");	
	}
	$(".tipSwitchAnimate").css("left","410px");
}

//关闭提示框
function hideTip(){
    $("#searchTipBg").fadeOut({
        duration: 500
    });
	$("#searchTip").hide();
	$(".tipbar").hide();
	$(".tipbox").css({"visibility":"hidden","display":"none"});
	$("#step1").css({"visibility":"visible","display":"block"});
	SetCookie("tipVisible","no");
}

function setSearchTip(){
	var windowW = $(window).width()+16,
		windowH = $(window).height(),
		width = $("#searchTip").width(),
		ml = width/2;
	if($("#searchTip").length > 0 && $("#searchTipBg").length > 0){
		if(/msie/.test(navigator.userAgent.toLowerCase()) && $.browser.version == '6.0' && !$.support.style){
		  	var scrollT = $(window).scrollTop(),
			  	scrollL = $(window).scrollLeft();
			$("#searchTipBg").css({"width":windowW + scrollL,"height":windowH + scrollT});
		}else {
			$("#searchTipBg").css({"width":windowW,"height":windowH});
		}
		$("#searchTip").css({"margin-left":-ml});
	}
}
function noShow(){
	if(document.getElementById("notip").checked){
		SetCookie(emp_code_cookie+"neverShow","no",{expires:37230});
	}	
}

function showSearchTip(){
	var position = /msie/.test(navigator.userAgent.toLowerCase()) && $.browser.version == '6.0' && !$.support.style ? "absolute" : "fixed";
	//展示内容
	var searchTipBar = "<div class='tipbarwrap'><div class='tipbardiv'>"; 
	  	searchTipBar += "<div class='tipbar' id='tipbar1'><div class='tipbarInner'><div class='arrow'></div><div class='tipBarword'></div></div></div>";
		searchTipBar += "<div class='tipbar' id='tipbar2'><div class='tipbarInner'><div class='arrow'></div><div class='tipBarword'></div></div></div>";
		searchTipBar += "<div class='tipbar' id='tipbar3'><div class='tipbarInner'><div class='arrow'></div><div class='tipBarword'></div></div></div>";
		searchTipBar += "<div class='tipbar' id='tipbar4'><div class='tipbarInner'><div class='arrow'></div><div class='tipBarword'></div></div></div>";
		searchTipBar += "</div></div>";
	//弹框下一步
	var searchTipInner = "<div class='tipbox' id='step1'><div class='tipword'></div><span class='tipboxBtn' onclick='hideTip()'></span><span class='tipboxNextbtn' onclick='nextStep(2)'></span><ol class='progress'><li class='on'></li><li></li><li></li><li></li><li></li></ol></div>";
		searchTipInner += "<div class='tipbox' id='step2'><div class='tipword'></div><span class='tipboxBtn' onclick='hideTip()'></span><a href='#stepflow03' class='tipboxNextbtn' onclick='nextStep(3)'></a><ol class='progress'><li></li><li class='on'></li><li></li><li></li><li></li></ol></div>";
		searchTipInner += "<div class='tipbox' id='step3'><div class='tipword'></div><span class='tipboxBtn' onclick='hideTip()'></span><a href='#stepflow04' class='tipboxNextbtn' onclick='nextStep(4)'></a><ol class='progress'><li></li><li></li><li class='on'></li><li></li><li></li></ol></div>";
		searchTipInner += "<div class='tipbox' id='step4'><div class='tipword'></div><span class='tipboxBtn' onclick='hideTip()'></span><a href='#stepflow05' class='tipboxNextbtn' onclick='nextStep(5)'></a><ol class='progress'><li></li><li></li><li></li><li class='on'></li><li></li></ol></div>";
		searchTipInner += "<div class='tipbox' id='step5'><div class='tipword'></div><span class='tipboxBtn' onclick='hideTip();noShow()'></span><span class='tipboxNextbtn' onclick='hideTip();noShow()'></span><div class='notip'><input type='checkbox' id='notip' checked='checked'/><label for='notip'>不再提示</label></div></div>";
	var switchBtn = "<div class='tipSwitchAnimate tipSwitch' style='display:none; left:410px; top:353px;'></div>";	
	if($("#searchTip").length == 0){
		$("#guide-step").before("<div id='searchTipBg' style='width:100%; height:100%; left:0px; top:0px; z-index:999; background-color:#000; opacity:0.55; filter:alpha(opacity=55);position:"+ position +"'></div>"); //背景遮罩
		$("#guide-step").before("<div id='searchTip'>"+ searchTipInner +"</div>");
		$("#guide-step").before(searchTipBar);
		$(switchBtn).appendTo($(".tipbardiv"));
		$("#step1").css({"visibility":"visible","display":"block"});
		if(GetCookie("tipVisible") == "no" || GetCookie("neverShow") == "no"){
			$("#step5 .notip").hide();
		}
	}
	if($("#searchTip").css("display") == "none"){
		$("#searchTip").css("top","270px").show();
		$("#searchTipBg").show();
		$(".tipbox").css({"visibility":"hidden","display":"none"});
		$("#step1").css({"visibility":"visible","display":"block"});
	}
	if($(".tipbarwrap").css("display") == "none"){
		$(".tipbarwrap").show();
	}
}

function GetCookie(name){
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null) return decodeURIComponent(arr[2]); return null;
}

function SetCookie(name,value,options){
    var expires = '', path = '', domain = '', secure = ''; 
    if(options)
    {
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var exp;
            if (typeof options.expires == 'number') {
                exp = new Date();
                exp.setTime(exp.getTime() + options.expires*24*60*60*1000);
            }
            else{
                exp = options.expires;
            }
            expires = ';expires=' + exp.toUTCString();
        }
        path = options.path ? '; path=' + options.path : ''; 
        domain = options.domain ? ';domain=' + options.domain : ''; 
        secure = options.secure ? ';secure' : ''; 
    }
    document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
}