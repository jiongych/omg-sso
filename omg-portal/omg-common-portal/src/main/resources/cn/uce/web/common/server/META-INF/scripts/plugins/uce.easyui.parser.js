(function($){
		//插件名称转换为对应的函数名称  uce-combogrid -- > uceCombogrid
		function convertToFuncName(pluginName) {
			var pos = pluginName.indexOf("-");
			pluginName = pluginName.substring(0, pos) + pluginName.substr(pos + 1, 1).toUpperCase() + pluginName.substring(pos + 2);
			return pluginName;
		}
		//插件名称转换为css 类名   uce-combogrid -- > uce-combogrid
		function convertToClzName(pluginName) {
			return pluginName;
		}
		//继承parser解析器，解析uce自定义标签，查找自定义uce-easyui-*类名，生成对应的控件
		var _parser = $.parser;
		$.parser = $.extend({}, $.parser, {
				plugins :_parser.plugins.concat(["uce-combogrid", "uce-combobox"]),
				parse : function(param) {
					for (var i=0; i< $.parser.plugins.length; i++) {
						var plugin = $.parser.plugins[i];//uce-combogrid
						var pluginFuncName = convertToFuncName(plugin);
						var pluginCssClzName = convertToClzName(plugin);
						if (plugin.indexOf("uce-") == 0) {
							var pluginEles = $("." + pluginCssClzName, param);
							if (pluginEles.length) {
								if (pluginEles[pluginFuncName]){
									pluginEles.each(function(){
										$(this)[pluginFuncName]($.data(this,"options")||{});
									});
									continue;
								}
							}
						}
					}
					//调用原生parser方法
					_parser.parse.call(this, param);
				}
		});
	})(jQuery);