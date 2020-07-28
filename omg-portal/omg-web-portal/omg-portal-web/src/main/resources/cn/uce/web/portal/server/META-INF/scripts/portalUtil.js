/**
 * Portal常用工具类
 */
//获取url后面的参数，paramName:参数名字，返回的是值
function GetUrlParam(paramName) {
	var url = document.location.toString();
	var arrObj = url.split("?");
	if (arrObj.length > 1) {
	　　var arrPara = arrObj[1].split("&");
	　　var arr;
	　　for (var i = 0; i < arrPara.length; i++) {
			arr = arrPara[i].split("=");
			if (arr != null && arr[0] == paramName) {
			　　return arr[1];
			}
	　　}
	　　return "";
	} else {
	　　return "";
	}
}
