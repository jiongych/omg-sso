/**
 * 说明：自定义map工具对象，可以对map新增/删除/修改/清除元素触发事件
 * 作者：zhangRb
 * 日期：2018/01/22
 * @class MapUtil
 * @constructor
 * @method put
 * 		向map中添加元素、并触发onPut事件回调
 * @method remove
		移除map中指定的key对应的键值对、并触发onRemove事件回调
 * @method update
		修改map中指定的key对应的键值、并触发onUpdate事件回调
 * @method clear
		清除map中所有的键值对、并触发onClear事件回调
 * @method get
		map的get方法,获取指定key对应的value
 * @method hasKey
		map是否有某个键
 * @method isEmpty
		判断map是否为空
 * @method getAllElements
		获取map对象
 * @method size
		获取map的元素个数
 * @method eventListener
		添加事件监听的方法
 */

function MapUtil() {
	this.arrayObject = {};
	this.callback={};
	
	//map的put方法
	if(!MapUtil.prototype.put) {
		MapUtil.prototype.put = function(key, value) {
			if (key != undefined && key != '' && key != null) {
				this.arrayObject[key] = value;
				//如果callback.onPut存在，则执行里面的回调函数
	            if(this.callback.onPut){
	            	this.callback.onPut.forEach(function(fn, index){
	                    fn(key,value);
	                })
	            }
			}
		}
	}
	//map的remove方法
	if(!MapUtil.prototype.remove) {
		MapUtil.prototype.remove = function(key) {
			if (key != undefined && key != '' && key != null) {
				var value = this.arrayObject[key];
				delete this.arrayObject[key];
				//如果callback.onRemove存在，则执行里面的回调函数
	            if(this.callback.onRemove){
	            	this.callback.onRemove.forEach(function(fn, index){
	                    fn(key, value);
	                })
	            }
			}
		}
	}
	//map的update方法
	if(!MapUtil.prototype.update) {
		MapUtil.prototype.update = function(key, value) {
			if (key != undefined && key != '' && key != null) {
				this.arrayObject[key] = value;
				//如果callback.onUpdate存在，则执行里面的回调函数
	            if(this.callback.onUpdate){
	            	this.callback.onUpdate.forEach(function(fn, index){
	                    fn(key, value);
	                })
	            }
			}
		}
	}
	//map的clear方法
	if(!MapUtil.prototype.clear) {
		MapUtil.prototype.clear = function() {
			this.arrayObject = {};
			//如果callback.onClear存在，则执行里面的回调函数
	        if(this.callback.onClear){
	        	this.callback.onClear.forEach(function(fn, index){
	                fn();
	            })
	        }
		}
	}
	//map的get方法,获取指定key对应的value
	if(!MapUtil.prototype.get) {
		MapUtil.prototype.get = function(key) {
			if (key != undefined && key != '' && key != null) {
				return this.arrayObject[key];
			}else {
				return null;
			}
		}
	}
	//map是否有某个键
	if(!MapUtil.prototype.hasKey) {
		MapUtil.prototype.hasKey = function(key) {
			if (key != undefined && key != '' && key != null) {
				return this.arrayObject.hasOwnProperty(key);
			}else {
				return false;
			}
		}
	}
	//判断map是否为空
	if(!MapUtil.prototype.isEmpty) {
		MapUtil.prototype.isEmpty = function() {
			var t;
		    for (t in this.arrayObject)
		        return !1;
		    return !0;
		}
	}
	//获取map对象
	if(!MapUtil.prototype.getAllElements) {
		MapUtil.prototype.getAllElements = function() {
			return this.arrayObject;
		}
	}
	//获取map的元素个数
	if(!MapUtil.prototype.size) {
		MapUtil.prototype.size = function() {
			var t;
			var length = 0;
			for (t in this.arrayObject)
		        length = length + 1;
		    
			return length;
		}
	}
	//添加事件监听的方法
	if(!MapUtil.prototype.eventListener) {
		MapUtil.prototype.eventListener = function(eventType,fn){
	        //如果对象callback中不存在eventType属性，则初始化一个eventType属性；
	        this.callback[eventType] = this.callback[eventType] ? this.callback[eventType] : [];
	        //添加回调函数
	        this.callback[eventType].push(fn);
	    }
	}
}