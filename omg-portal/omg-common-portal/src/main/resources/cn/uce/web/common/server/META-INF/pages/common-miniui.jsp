<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/boot.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/miniui/CopyExcel.js?v=<%=System.currentTimeMillis() %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/plugins/miniui/ColumnStateful.js?v=<%=System.currentTimeMillis() %>"></script>
<style>
	.excel-cell-selected
	{
	    background: #ADDFFE!important;
	}
	
	.select-line {
		position: absolute;
		display: none;
		background: #2E8B57;
	}
	
	.select-left,
	.select-right {
		width: 2px;
	}
	
	.select-top,
	.select-bottom {
		height: 2px;
	}
</style>
<script>
	function minigrid(id, columns, datagridParams) {
		$('#'+id).after("<div ><ul id='miniui-dg-contextmenu-"+id+"' class='mini-contextmenu'><li id='miniui-dg-contextmenu-copy-"+id+"' iconCls='icon-print'>复制</li></ul></div>");
		
		//导出设置按钮
		if($('#dg-export-set-button-' + id).length > 0) {
			$('#' + id).after("<div id = 'dg-export-set-div-" + id + "'><div id = 'dg-export-set-dlg-" + id + "' class='easyui-dialog' data-options = 'title:&apos;导出设置&apos;,closed:true,buttons:&apos;#dg-export-set-dlg-btn-"+id+"&apos;'><div class='easyui-layout' data-options='border:false' style='width:501px;height:300px'><div data-options='region:&apos;west&apos;,border:false'style='width:200px;'><div id='dg-export-set-datalist-west-" + id + "' ></div></div><div data-options='region:&apos;center&apos;,border:false' style='padding:50px 1px'><a class='easyui-linkbutton' id = 'dg-export-set-button-moveInAll-"+id+"'  data-options='width:99'>全部移入\>\></a><a id = 'dg-export-set-button-moveIn-"+id+"' class='easyui-linkbutton' data-options='width:99' style='width:&apos;100px&apos;,border:false'>移入\></a><a id = 'dg-export-set-button-moveOut-"+id+"' class='easyui-linkbutton' data-options='width:99'>移出\<</a><a id = 'dg-export-set-button-moveOutAll-"+id+"' class='easyui-linkbutton' data-options='width:99'>全部移出\<\<</a></div><div data-options='region:&apos;east&apos;' style='width:200px;'><div id='dg-export-set-datalist-east-" + id + "' ></div></div><div data-options='region:&apos;south&apos;' style='height:40px'><form id='dg-export-set-form-"+id+"' method='post'><input class='easyui-textbox' name='fileNameXls' data-options='prompt:&apos;请输入自定义文件名...&apos;' style='height:39px;width:499px'/></form></div></div></div><div id = 'dg-export-set-dlg-btn-" + id + "' style='padding-top:20px;'><a href='javascript:void(0)' id='dg-export-set-dlg-btn-save-"+id+"' class='easyui-linkbutton save'  >导出</a><a href='javascript:void(0)' id='dg-export-set-dlg-btn-cancel-"+id+"' class='easyui-linkbutton call' >取消</a></div></div>")
			$.parser.parse('#dg-export-set-div-'+id);
			
			function buildColumnList(columns, columnsFlat) {
				columns.map(function(item) {
					if(!item.columns) {
						if(item.type != 'indexcolumn' && item.type != 'checkcolumn' && item.isExportColumn != false) {
							columnsFlat.push({
								header: item.header,
								field: item.field
							})
						}
					} else {
						buildColumnList(item.columns, columnsFlat);
					}
				})
				return columnsFlat;
			}

			var columnsFlat = [];
			buildColumnList(columns, columnsFlat);
			$('#dg-export-set-datalist-west-' + id).datalist({
				height:'100%',
				data: columnsFlat,
				textField: 'header',
				valueField: 'field',
				checkbox: true,
				singleSelect:false
			})
			$('#dg-export-set-datalist-east-' + id).datalist({
				height:'100%',
				data: [],
				textField: 'header',
				valueField: 'field',
				checkbox: true,
				singleSelect:false
			})
			
			//导出按钮点击事件
			$('#dg-export-set-button-' + id).linkbutton({
				onClick: function() {
					$('#dg-export-set-dlg-' + id).dialog('open')
				}
			})
			
			//dialog内按钮点击实际
			$('#dg-export-set-button-moveInAll-' + id).linkbutton({
				onClick : function(){
					var chooseItems = $('#dg-export-set-datalist-west-' + id).datalist('getRows');
					if(chooseItems != null){
						var limit = chooseItems.length;
						for(var i = 0 ; i < limit ; i++){
							$('#dg-export-set-datalist-east-' + id).datalist('appendRow',chooseItems[0]);
							$('#dg-export-set-datalist-west-' + id).datalist('deleteRow',$('#dg-export-set-datalist-west-' + id).datalist('getRowIndex',chooseItems[0]));
						}
					}
					
				}
			})
			$('#dg-export-set-button-moveIn-' + id).linkbutton({
				onClick : function(){
					var chooseItems = $('#dg-export-set-datalist-west-' + id).datalist('getChecked');
					if(chooseItems != null){
						var limit = chooseItems.length;
						for(var i = 0 ; i < limit ; i++){
							$('#dg-export-set-datalist-east-' + id).datalist('appendRow',chooseItems[i]);
							$('#dg-export-set-datalist-west-' + id).datalist('deleteRow',$('#dg-export-set-datalist-west-' + id).datalist('getRowIndex',chooseItems[i]));
						}
					}
				}
			})
			$('#dg-export-set-button-moveOutAll-' + id).linkbutton({
				onClick : function(){
					var chooseItems = $('#dg-export-set-datalist-east-' + id).datalist('getRows');
					if(chooseItems != null){
						var limit = chooseItems.length;
						for(var i = 0 ; i < limit ; i++){
							$('#dg-export-set-datalist-west-' + id).datalist('appendRow',chooseItems[0]);
							$('#dg-export-set-datalist-east-' + id).datalist('deleteRow',$('#dg-export-set-datalist-east-' + id).datalist('getRowIndex',chooseItems[0]));
						}
					}
				}
			})
			$('#dg-export-set-button-moveOut-' + id).linkbutton({
				onClick : function(){
					var chooseItems = $('#dg-export-set-datalist-east-' + id).datalist('getChecked');
					if(chooseItems != null){
						var limit = chooseItems.length;
						for(var i = 0 ; i < limit ; i++){
							$('#dg-export-set-datalist-west-' + id).datalist('appendRow',chooseItems[i]);
							$('#dg-export-set-datalist-east-' + id).datalist('deleteRow',$('#dg-export-set-datalist-east-' + id).datalist('getRowIndex',chooseItems[i]));
						}
					}
				}
			})
			$('#dg-export-set-dlg-btn-save-'+id).linkbutton({
				onClick: function(){
					if($('#dg-export-set-button-' + id).data('bfclick')){
						var bfclick = eval($('#dg-export-set-button-' + id).data('bfclick')+"()")
						if(bfclick == false){
							return false;
						}
					}
					var queryParams = {};
					if($('#dg-export-set-button-' + id).data('params')){
						queryParams = eval($('#dg-export-set-button-' + id).data('params')+'()');
					}
					
					var exclusions = $('#dg-export-set-datalist-west-' + id).datalist('getRows');
					var exclusionTitles = [];
					exclusions.map(function(item){
						exclusionTitles.push(item.header);
					})
					
					queryParams['exclusionTitles'] = JSON.stringify(exclusionTitles);
					
					$('#dg-export-set-form-'+id).form('submit',{
						url:$('#dg-export-set-button-' + id).data('url'),
						queryParams:queryParams
					})
					if($('#dg-export-set-button-' + id).data('afclick')){
						eval($('#dg-export-set-button-' + id).data('afclick')+'()')
					}
					$('#dg-export-set-dlg-' + id).dialog('close');
				}
				
			})
			$('#dg-export-set-dlg-btn-cancel-'+id).linkbutton({
				onClick: function(){
					$('#dg-export-set-dlg-' + id).dialog('close');
				}
			})
		}
		
		mini.parse();
    	var grid = mini.get(id);
    	
    	function dealColumns(columns){
    		if(columns && columns.length > 0){
    			columns.map(function(item){
		    		item.headerAlign = 'center';
		    		if(item.columns){
		    			dealColumns(item.columns);
		    		}
		    	})
    		}
    	}
    	dealColumns(columns);
    	
    	/**
		 * ========================================propertie================================================
		 */
		grid.set({
			columns: columns,
			url: datagridParams && datagridParams.url ? datagridParams.url : null,
			dataField: datagridParams && datagridParams.dataField ? datagridParams.dataField : 'rows',
			
			style: datagridParams && datagridParams.style ? datagridParams.style : "height:100%;",
			enableHotTrack: datagridParams && datagridParams.enableHotTrack == true ? true : false,//移动到行时高亮显示
			defaultRowHeight: 30,
			allowAlternating: true,
			
			allowCellSelect : datagridParams && datagridParams.selectType == 'row' ? false : true,
			//allowRowSelect : datagridParams && datagridParams.selectType == 'row' ? true : false,
			multiSelect: datagridParams && datagridParams.multiSelect == false ? false : true,
			
  			//pageIndex: 1,
  			//pageIndexField: 'page',
  			pageSizeField: 'rows',
  			pageSize: datagridParams && datagridParams.pageSize ? datagridParams.pageSize : 50,
  			sizeList: datagridParams && datagridParams.sizeList ? datagridParams.sizeList : [50,100,150,200,250,500],
  			
  			allowSortColumn: datagridParams && datagridParams.allowSortColumn == false ? false : true,
  			sortMode: datagridParams && datagridParams.sortMode ? datagridParams.sortMode : 'client',
			showSortIcon: datagridParams && datagridParams.showSortIcon == true ? true : false,
			sortDblClick: datagridParams && datagridParams.sortDblClick == true ? true : false,
			allowCancelSort: datagridParams && datagridParams.allowCancelSort == false ? false : true,
			
			virtualColumns : datagridParams && datagridParams.virtualColumns == false ? false : true,
  			virtualScroll : datagridParams && datagridParams.virtualScroll == false ? false : true,
  			
  			showFilterRow: datagridParams && datagridParams.showFilterRow == true ? true : false,
  			showSummaryRow: datagridParams && datagridParams.showSummaryRow == true ? true : false,
  			showGroupSummary: datagridParams && datagridParams.showGroupSummary == true ? true : false,
  			showColumnsMenu: datagridParams && datagridParams.showColumnsMenu == true ? true : false,
  			
  			onDrawSummaryCell: onDrawSummaryCell
  			
		})
		/**
		 * ========================================propertie================================================
		 */
		
		/**
		 * ======================================event==============================
		 */
		grid.on('beforeload',function( param ){
			if(!param.url || param.url == null || param.url == ''){
				param.cancel = true;
			}
			param.params.page = param.params.pageIndex+1;
    		if(datagridParams && datagridParams.beforeload){
    			datagridParams.beforeload(param);
    		}
    	})
    	grid.on('update',function(param){
			var el = $(grid.getEl()).find(".mini-pager-right");
            var pageSize = grid.getPageSize();
            var pageIndex = grid.getPageIndex();
            var t = grid.getTotalCount();
            var s = 0;
            var e = 0;
            if(t != 0){
            	var s = pageIndex * pageSize+1;
           	 	var e = t < pageSize ? t : (pageIndex + 1) * pageSize ;
            }
            var text = "显示 " + s + " 到 " + e + ",共 " + t + " 记录";
            el.text(text);
			if(datagridParams && datagridParams.update){
				datagridParams.update(param);
    		}
		})
		grid.on('loaderror', function(e) {
			if(datagridParams && datagridParams.loaderror) {
				datagridParams.loaderror(e);
			}
			if(e.xhr.status == 606) {
				showInfoMsg('登录超时..', function() {
					window.location.href = e.xhr.getResponseHeader("Location");
				});
				return;
			}
		})
		grid.on('columnschanged',datagridParams && datagridParams.columnschanged ? datagridParams.columnschanged : null);
		grid.on('rowclick',datagridParams && datagridParams.rowclick ? datagridParams.rowclick : null);
		grid.on('rowdblclick',datagridParams && datagridParams.rowdblclick ? datagridParams.rowdblclick : null);
		grid.on('rowmousedown',datagridParams && datagridParams.rowmousedown ? datagridParams.rowmousedown : null);
		grid.on('cellclick',datagridParams && datagridParams.cellclick ? datagridParams.cellclick : null);
		grid.on('cellmousedown',datagridParams && datagridParams.cellmousedown ? datagridParams.cellmousedown : null);
		grid.on('headercellclick',datagridParams && datagridParams.headercellclick ? datagridParams.headercellclick : null);
		grid.on('headercellmousedown',datagridParams && datagridParams.headercellmousedown ? datagridParams.headercellmousedown : null);
		grid.on('headercellcontextmenu',datagridParams && datagridParams.headercellcontextmenu ? datagridParams.headercellcontextmenu : null);
		grid.on('preload',datagridParams && datagridParams.preload ? datagridParams.preload : null);
		//grid.on('loaderror',datagridParams && datagridParams.loaderror ? datagridParams.loaderror : null);
		grid.on('load',datagridParams && datagridParams.load ? datagridParams.load : null);
		//grid.on('update',datagridParams && datagridParams.update ? datagridParams.update : null);
		grid.on('drawcell',datagridParams && datagridParams.drawcell ? datagridParams.drawcell : null);
		grid.on('cellbeginedit',datagridParams && datagridParams.cellbeginedit ? datagridParams.cellbeginedit : null);
		grid.on('cellcommitedit',datagridParams && datagridParams.cellcommitedit ? datagridParams.cellcommitedit : null);
		grid.on('cellendedit',datagridParams && datagridParams.cellendedit ? datagridParams.cellendedit : null);
		grid.on('celleditenter',datagridParams && datagridParams.celleditenter ? datagridParams.celleditenter : null);
		grid.on('selectionchanged',datagridParams && datagridParams.selectionchanged ? datagridParams.selectionchanged : null);
		grid.on('beforeselect',datagridParams && datagridParams.beforeselect ? datagridParams.beforeselect : null);
		grid.on('beforedeselect',datagridParams && datagridParams.beforedeselect ? datagridParams.beforedeselect : null);
		grid.on('select',datagridParams && datagridParams.select ? datagridParams.select : null);
		grid.on('deselect',datagridParams && datagridParams.deselect ? datagridParams.deselect : null);
		grid.on('cellvalidation',datagridParams && datagridParams.cellvalidation ? datagridParams.cellvalidation : null);
		grid.on('drawsummarycell',datagridParams && datagridParams.drawsummarycell ? datagridParams.drawsummarycell : null);
		grid.on('resize',datagridParams && datagridParams.resize ? datagridParams.resize : null);
		/**
		 * ======================================event==============================
		 */
		
		if(grid.getUrl() && grid.getUrl() != ''){
			grid.load( datagridParams && datagridParams.queryParams ? datagridParams.queryParams : {});
		}
		
		/**
		 * ========================================stateful================================================
		 */
       	new ColumnStateful(grid, {      //扩展组件：列状态保持
            stateId: window.location.pathname+"_minigrid_"+id + (datagridParams && datagridParams.storageKey ? datagridParams.storageKey : '')
        });
        /**
		 * ========================================stateful================================================
		 */
		
		/**
		 * ========================================copyable================================================
		 */
        new CopyExcel(grid);
        /**
		 * ========================================copyable================================================
		 */
		
		function onDrawSummaryCell(e) {
			if(e.column.field == 'rowNumber'){
				e.cellHtml = "<div style='margin:5px; text-align:center'><b>合计:</b><div>"
			}
            //客户端汇总计算
            if (e.column.summaryType == "sum") {
                e.cellHtml = "<div style='margin:5px; text-align:center'><b>" + e.cellHtml+"</b><div>";
            }
        }
	}
</script>