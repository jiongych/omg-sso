<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录信息统计管理</title>
<%@include file="../common/common-ztree.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/echarts.min.js"></script>
<style>
.datagrid-header-expander {
	padding: 0 11px !important;
}
.tabs li a.tabs-inner, .tabs-header, .tabs-tool {
    background-color: #1d9c30;
}
.tabs li a.tabs-inner:hover, .tabs li.tabs-selected a.tabs-inner {
    background-color: #ff0000;
}
.tabs-header, .tabs-scroller-left, .tabs-scroller-right, .tabs-tool, .tabs, .tabs-panels, .tabs li a.tabs-inner, .tabs li.tabs-selected a.tabs-inner, .tabs-header-bottom .tabs li.tabs-selected a.tabs-inner, .tabs-header-left .tabs li.tabs-selected a.tabs-inner, .tabs-header-right .tabs li.tabs-selected a.tabs-inner {
    border-color: #f8f8f8;
}
.panel-body {
    font-size: 12px;
    background-color: whitesmoke;
}
.tabs-title {
    font-size: 12px;
    color: white;
}
</style>
</head>

<body>
	<script type="text/javascript">
		var topLoginPeople;
		var loginChart;
		var rightResolutionRatio;
		var ratioChart;
		//页面加载
		$(function() {
			topLoginPeople = document.getElementById("topContainer");
			rightResolutionRatio = document.getElementById("rightContainer");
			loginChart = echarts.init(topLoginPeople);
			ratioChart = echarts.init(rightResolutionRatio);
	
			loadLoginPepoleInfo(); // 加载每天登录人数信息
			loadResolutionRatio();//构建分辨率统计图
			
		});
		function loadResolutionRatio(){
		    $.ajax({
				url:'${pageContext.request.contextPath}/browser/loadResolutionRatio.do',
				type:"POST",
				data:{},
				success:function (data) {
					// 判断数据是否存在，存在构建echarts需要的数据格式
					if(data != undefined && data != null) {
						var ratioName = new Array();
						var ratioData = new Array();
						for(var key in data) {
							ratioName.push(key);
							var ratio = {"value" : data[key],"name" : key};
							ratioData.push(ratio);
						}
						resolutionRatio(ratioName,ratioData);
					}
				}
			});	
		}
		
		function loadLoginPepoleInfo(){
			$.ajax({
				url:'${pageContext.request.contextPath}/browser/loadLoginPepoleInfo.do',
				type:"POST",
				data:{},
				success:function (data) {
					// 判断数据是否存在，存在构建echarts需要的数据格式
					if(data != undefined && data != null) {
						var days = data.days;
						var loginNums = data.loginNums;
						var loginTimes = data.loginTimes;
						var series = new Array();
						var names = new Array("人数","次数");
						series.push(buildCount(loginNums,'#1d9c30',"人数"));
						series.push(buildCount(loginTimes,'#ff0000',"次数"));
						var dates = new Array();
						for(var i = 0; i < days.length; i++){
							dates.push(days[i].substring(3,days[i].length));
						}
					    // 局部刷新
						var backgroundColor = "#f5f5f5";
						var title = "每日登录人数统计";
						flushBrowserStaticsInfo(80,loginChart,title,backgroundColor,dates,names,series);
					}
				}
			});
		}
		
		function buildCount(loginCounts,colors,name){
			var series = {
				"name" : name,
				"type" : "line",
				"stack" : "总量",
				symbolSize : 10,
				symbol : 'circle',
				"itemStyle" : {
					"normal" : {
						"color" : colors,
						"barBorderRadius" : 0,
						"label" : {
							"show" : true,
							"position" : "top",
							formatter : function(p) {
								return p.value > 0 ? (p.value) : '';
							}
						}
					}
				},
				"data" : loginCounts
			}
			return series;
		}
		// 构建分辨率统计图
		function resolutionRatio(ratioName,ratioData){
			var option = {
				title : {
					text: '分辨率使用情况',
					subtext: '最近三月使用情况',
					x:'center'
				},
				tooltip : {
					trigger: 'item',
					formatter: "{a} <br/>{b} 登录次数为 {c} (占比{d}%)"
				},
				legend: {
					orient: 'vertical',
					left: 'left',
					data: ratioName
				},
				series : [
					{
						name: '使用概率',
						type: 'pie',
						radius : '55%',
						center: ['50%', '60%'],
						data:ratioData,
						itemStyle: {
							emphasis: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}
				]
			};
			if (option && typeof option === "object") {
				ratioChart.setOption(option, true);
			}
		}
		// 344b58
		function flushBrowserStaticsInfo(start,chart,title,backgroundColor,mdata,browserNames,resultDetail) {
			var option = {
				backgroundColor : backgroundColor,
				"title" : {
					"text" : title,
					"subtext" : "",
					x : "4%",

					textStyle : {
						color : '#000000',
						fontSize : '22'
					},
					subtextStyle : {
						color : '#000000',
						fontSize : '16',

					},
				},
				"tooltip" : {
					"trigger" : "axis",
					"axisPointer" : {
						"type" : "shadow",
						textStyle : {
							color : "#fff"
						}

					},
				},
				"grid" : {
					"borderWidth" : 0,
					"top" : 110,
					"bottom" : 95,
					textStyle : {
						color : "#fff"
					}
				},
				"legend" : {
					x : '4%',
					top : '11%',
					textStyle : {
						color : '#90979c',
					},
					"data" : browserNames
				},

				"calculable" : true,
				"xAxis" : [ {
					"type" : "category",
					"axisLine" : {
						lineStyle : {
							color : '#90979c'
						}
					},
					"splitLine" : {
						"show" : false
					},
					"axisTick" : {
						"show" : false
					},
					"splitArea" : {
						"show" : false
					},
					"axisLabel" : {
						"interval" : 0,

					},
					"data" : mdata,
				} ],
				"yAxis" : [ {
					"type" : "value",
					"splitLine" : {
						"show" : false
					},
					"axisLine" : {
						lineStyle : {
							color : '#90979c'
						}
					},
					"axisTick" : {
						"show" : false
					},
					"axisLabel" : {
						"interval" : 0,

					},
					"splitArea" : {
						"show" : false
					},

				} ],
				"dataZoom" : [
						{
							"show" : true,
							"height" : 30,
							"xAxisIndex" : [ 0 ],
							bottom : 30,
							"start" : start,
							"end" : 100,
							handleIcon : 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
							handleSize : '110%',
							handleStyle : {
								color : "#1d9c30",

							},
							textStyle : {
								color : "black"
							},
							borderColor : "#1d9c30",
							backgroundColor : "#f5f5f5"

						}, {
							"type" : "inside",
							"show" : true,
							"height" : 15,
							"start" : 80,
							"end" : 100
						} ],
				"series" : resultDetail
			}
			if (option && typeof option === "object") {
				chart.setOption(option, true);
			}
		}

	</script>
	<div id="browserInfo" style="height: 90%; width: 100%;padding-top:15px;">
		<div id="north" style="height: 100%;">
		    <div id="topContainer" style="height: 100%;margin-left: 15px;width:55%;float:left;"></div>
			<div id="rightContainer" style="height: 100%;margin-left: 15px;width:40%;float:left;"></div>
		</div>
	</div>
</body>
</html>