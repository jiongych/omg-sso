<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录信息统计管理</title>
<%@include file="../common/common-ztree.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/echarts.min.js"></script>
<%-- 
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/Chart.js"></script> 
--%>
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
	    var leftBrowserInfo;
	    var myChart;
		var topLoginPeople;
		var loginChart;
		var rightResolutionRatio;
		var ratioChart;
		//页面加载
		$(function() {
			leftBrowserInfo = document.getElementById("leftContainer");
			topLoginPeople = document.getElementById("topContainer");
			rightResolutionRatio = document.getElementById("rightContainer");
			myChart = echarts.init(leftBrowserInfo);
			loginChart = echarts.init(topLoginPeople);
			ratioChart = echarts.init(rightResolutionRatio);
			loadBrowserInfo();// 加载浏览器使用情况统计信息
			loadLoginPepoleInfo(); // 加载每天登录人数信息
			loadResolutionRatio();//构建分辨率统计图
			loadRankingData(0,'login7');
			//加载每周或每月排名数据
			var tabs = $('#rankings').tabs().tabs('tabs');
            for(var i=0; i<tabs.length; i++){
                tabs[i].panel('options').tab.unbind().bind('mouseenter',{index:i},function(e){
					if(e.data.index == 0){
						loadRankingData(e.data.index,'login7');
					}else {
						loadRankingData(e.data.index,'login30');
					}
                    $('#rankings').tabs('select', e.data.index);
                });
            }
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
		
		function loadBrowserInfo(){
			$.ajax({
				url:'${pageContext.request.contextPath}/browser/loadBrowserInfo.do',
				type:"POST",
				data:{},
				success:function (data) {
					// 判断数据是否存在，存在构建echarts需要的数据格式
					if(data != undefined && data != null) {
						var loginNums = data.loginNums;
						var browserInfo = data.browserInfo;
						var mdata = new Array();
						var series = new Array();
						var loginCounts = new Array();
						for(var i in loginNums) {
							if(loginNums[i].monthNum == undefined)continue;
							mdata.push(loginNums[i].monthNum + "月");
							// loginCounts.push(loginNums[i].loginNums);
						}
						var colors = ['#1d9c30','#ff1d1d','#61a0a8','#d48265','#2f4554','#ca8622','#541b86','#1b8cf2'];
						var browserNames = new Array();
						var index = 0;
						for (var key in browserInfo) {  
						    //if(index > 5) break;
						    var subseries = {
								name:key,
								type:'bar',
								itemStyle : {
									normal : {
										color : colors[index],
										barBorderRadius : 0,
										label : {
											show : true,
											position : "top",
											formatter : function(p) {
												return p.value > 0 ? (p.value) : '';
											}
										}
									}
								},
								data:browserInfo[key]
							}
							series.push(subseries);
							browserNames.push(key);
							index++;
						}
						//series.push(buildCount(loginCounts,colors,"浏览器每月次数"));
					    // 局部刷新
						//var backgroundColor = "#f5f5f5";
						//var title = "浏览器使用情况统计";
						//flushBrowserStaticsInfo(20,myChart,title,backgroundColor,mdata,browserNames,series);
						browserUsageStatistics(mdata,browserNames,series);
					} 
				}
			});
		}
		function browserUsageStatistics(mdata,browserNames,series){
			var option = {
				title : {
					text: '浏览器月使用统计',
					subtext: '详情请查看登录信息'
				},
				tooltip : {
					trigger: 'axis'
				},
				legend: {
					data:browserNames
				},
				toolbox: {
			        show : true,
			        orient: 'vertical',
			        x: 'right',
			        y: 'center',
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
				calculable : true,
				xAxis : [
					{
						type : 'category',
						data : mdata
					}
				],
				yAxis : [
					{
						type : 'value'
					}
				],
				dataZoom : [
						{
							"show" : true,
							"height" : 30,
							"xAxisIndex" : [ 0 ],
							bottom : 0,
							"start" : 80,
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
						} 
				],
				series : series
			};    
            if (option && typeof option === "object") {
				myChart.setOption(option, true);
			}			
		}
		
		
		function buildSeries(key,info,colors){
			var color = colors[10*Math.random()];
			var series = {
				"name" : key,
				"type" : "bar",
				"stack" : "总量",
				"barMaxWidth" : 35,
				"barGap" : "10%",
				"itemStyle" : {
					"normal" : {
						"color" : color,
						"label" : {
							"show" : true,
							"textStyle" : {
								"color" : "#1d9c30"
							},
							"position" : "insideTop",
							formatter : function(p) {
								return p.value > 0 ? (p.value) : '';
							}
						}
					}
				},
				"data" : info // browserLoginNums,
			}
			return series;
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
		
		// 加载每周或每月排名数据
		function loadRankingData(value,id){
		   $.ajax({
				url:'${pageContext.request.contextPath}/browser/loadLoginRankingInfo.do',
				type:"POST",
				data:{"value":value},
				success:function (data) {
					// 判断数据是否存在，存在构建echarts需要的数据格式
					if(data != undefined && data != null) {
						$("#"+id).html('');
						var rankings = data.result;
						var result = '<li style="font-size:20px;color:red;">TOP排名:</li>';
						for(var i = 0; i < rankings.length; i++){
							result += '<li>' + (i+1) + ', 员工名称：' + rankings[i].empName + ', 员工编号：' + rankings[i].createEmp + ', 登录次数：' + rankings[i].ranking + '</li>'
						}
						$("#"+id).append(result);
					}
				}
			});
		}
	</script>
	<div id="browserInfo" style="height: 100%; width: 100%;padding-top:15px;">
		<div id="north" style="height: 45%;">
		    <div id="topContainer" style="height: 100%;margin-left: 15px;width:55%;float:left;"></div>
			<div id="topContainer" style="height: 100%;margin-left: 10px;width:40%;float:left;">
				<div id="rankings" class="easyui-tabs" style="45%;height:100%;    background: #f8f8f8;">
				    <div title="最近7天" style="padding:10px">
						<ul id="login7" style="margin-left:40px;font-size:15px">
						</ul>
					</div>
					<div title="最近30天" style="padding:10px">
						<ul id="login30" style="margin-left:40px;font-size:15px">
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="south" style="height: 45%;margin-top:1px;">
			<div id="leftContainer" style="height: 100%;margin-left: 15px;width:55%;float:left;"></div>
			<div id="rightContainer" style="height: 100%;margin-left: 15px;width:40%;float:left;"></div>
		</div>
	</div>
</body>
</html>