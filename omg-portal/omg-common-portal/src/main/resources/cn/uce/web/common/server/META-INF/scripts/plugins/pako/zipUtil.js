var pako = window.pako;

function unzip(b64Data){  
	//Base64解码
    var strData     = window.atob(b64Data);  
    
    //将二进制字符串转换为字符数组
    var charData    = strData.split('').map(function(x){return x.charCodeAt(0);});  
    //将字符数据放入byte数组  
    var binData     = new Uint8Array(charData);  
    //解压
    var data        = pako.inflate(binData);  
    //根据解压的ByteArray返回ASCII字符串
    //strData     = String.fromCharCode.apply(null, new Uint16Array(data));
    if (!("TextDecoder" in window)){
    	strData = arrayBufferToString(data);
    }else {
		var decoder = new TextDecoder("utf-8");
	    //var binData = new Uint8Array(data);
	    var buffer = decoder.decode(data).replace(/\+/g, " ");
	    strData = decodeURIComponent(buffer).replace(/\\/g, '\\\\');
    }
    
    return strData;  
}

function arrayBufferToString(buffer){

    var bufView = new Uint16Array(buffer);
    
    var length = bufView.length;
    var result = '';
    
    //每次转换的长度
    var addition = Math.pow(2,16);
    for(var i = 0;i<length;i+=addition){

        if(i + addition > length){
            addition = length - i;
        }
        result += String.fromCharCode.apply(null, bufView.subarray(i,i+addition));
    }
    result = decodeURIComponent(result.replace(/\+/g, " ")).replace(/\\/g, '\\\\');

    return result;
}

function zip(str){  
	//先进行url编码
	str = encodeURI(str);
	//压缩数据
    var binaryString = pako.gzip(str, { to: 'string' });
    //进行Base64转码
    return btoa(binaryString);  
}