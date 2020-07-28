/////////////////////////////////////////////////
// Excel互操作插件：与Excel互拷贝、粘帖
/////////////////////////////////////////////////




var CopyExcel = function (grid) {

    var me = this;
    me.grid = grid;
    var id = grid.id;
    var html = "<div id='" + id + "-left' class='select-line select-left'></div>";
    html += "<div id='" + id + "-top' class='select-line select-top'></div>";
    html += "<div id='" + id + "-right' class='select-line select-right'></div>";
    html += "<div id='" + id + "-bottom' class='select-line select-bottom'></div>";
    //$(grid.el).append(html);
    $(grid.el).find(".mini-grid-rows-view").append(html);

    function getValueArrayFromClipboard(text) {

        var textArray = text.split("\n");
        if (textArray[textArray.length - 1].length == 0) {
            textArray.splice(textArray.length - 1, 1);
        }
        var array = [];
        for (var i = 0; i < textArray.length; i++) {
            array.push(textArray[i].split('\t'));
        }
        return array;
    }

    function copyClipboardToGrid(grid, text) {

        var valArray = getValueArrayFromClipboard(text);

        if (valArray.length > 0) {

            var columns = grid.getVisibleColumns();
            
            var cell = grid.getCurrentCell();
            var rowIndex = -1, columnIndex = -1;
            if (cell) {
                rowIndex = grid.indexOf(cell[0])
                columnIndex = columns.indexOf(cell[1]);
            }

            function setRowValues(row, columnIndex, values) {
                var valMap = {};
                for (var i = 0, l = values.length; i < l; i++) {
                      
                    var column = columns[columnIndex + i];
                    if (!column) continue;
                    valMap[column.field] = values[i];
                }
                grid.updateRow(row, valMap);
            }

            for (var i = 0; i < valArray.length; i++) {
                var values = valArray[i];

                var row = grid.getAt(rowIndex + i);
                if (!row) {
                    grid.addRow({});
                    row = grid.getAt(rowIndex + i);
                }

                setRowValues(row, columnIndex, values);

            }
        }

    }

    /////////////////////////////////////////////////////////////////////////

    

    /////////////////////////////////////////////////////////////////////////

    if (!mini.isIE) {

        var input = $('<input style="position:absolute;left:10px;top:10px;width:0px;height:0px;z-index:1000;overflow:hidden;padding:0;border:0;"/>').appendTo(grid.el);
        me._input = input;

        $(grid.el).bind('focusin', function (e) {

            if ($(e.target).is('input')) {
            } else {
                input[0].focus();
            }

            //alert(e.target.className);
            //input[0].focus();
            //debugger
            //alert(1);
            //e.preventDefault();
        });

        $(input).bind('paste', function (e) {

            var text = mini.getClipboard(e.originalEvent);

            me.copyClipboardToGrid(grid, text);

            e.preventDefault();

        });

    }



    $(grid.el).bind("keydown", function (e) {

        if (mini.isIE) {
            if (e.ctrlKey && e.keyCode == 86) {   //ctrl + v

                var text = mini.getClipboard();
                me.copyClipboardToGrid(grid, text);
            }
        }

        if (e.ctrlKey && e.keyCode == 67) {   //ctrl + c

            me.copyGridToClipboard(grid);
        }



    });



    /////////////////////////////////////////////////////////////////////////

    this.initSelection();

}




CopyExcel.prototype = {

    currentCell: null,      //{row, col}
    cellRange: null,        //{startRow, endRow, startCol, endCol }

    isInCellRange: function (cell) {
        var range = this.cellRange;
        if (!range) return false;

        if (cell.row < range.startRow || cell.row > range.endRow) return false;
        if (cell.col < range.startCol || cell.col > range.endCol) return false;

        return true;
    },

    initSelection: function () {

        var me = this,
            grid = this.grid,
            columns = grid.getVisibleColumns();


        function selectCellRange(cell1, cell2) {
        	$(grid.el).find(".select-line").hide();
            var range = {};


            range.startRow = Math.min(cell1.row, cell2.row);
            range.endRow = Math.max(cell1.row, cell2.row);
            range.startCol = Math.min(cell1.col, cell2.col);
            range.endCol = Math.max(cell1.col, cell2.col);

            //document.title = cell2.row + ":" + cell2.col;

            me.cellRange = range;

            $(grid.el).find(".excel-cell-selected").removeClass("excel-cell-selected");

            for (var i = range.startRow, l = range.endRow; i <= l; i++) {
                for (var j = range.startCol, k = range.endCol; j <= k; j++) {
                    var columns = grid.getVisibleColumns();
                    var col = columns[j];


                    var cellEl = grid.getCellEl(i, col);

                    $(cellEl).addClass("excel-cell-selected");
                }
            }
			me.selectArea();
            //cell.addClass("cell-selected");
        }

        function getCellAddress(e) {
            var cell = e.length == 2 ? e : grid.getCellFromEvent(e);
            var rowIndex = -1;
            var colIndex = -1;
            if (cell) {
                rowIndex = grid.indexOf(cell[0]);
                var columns = grid.getVisibleColumns();
                colIndex = columns.indexOf(cell[1]);

            }
            return { row: rowIndex, col: colIndex };
        }


        var isMove = false, moveCell;

        var contentBox;

        function handleMove(e) {

            if (!isMove) {
                isMove = true;
            }

            var cell = $(e.target).closest('.mini-grid-cell')[0];
            if (cell) {
                moveCell = getCellAddress(e);
            }

            selectCellRange(me.currentCell, moveCell || me.currentCell);

            e.preventDefault();

            //调整滚动条
            var x = e.pageX, y = e.pageY;

            var scrollTop = grid.getScrollTop(),
                scrollLeft = grid.getScrollLeft();
            if (y < contentBox.top) {
                grid.setScrollTop(scrollTop - 10);
            } else if (y > contentBox.bottom) {
                grid.setScrollTop(scrollTop + 10);
            }

            if (x < contentBox.left) {
                grid.setScrollLeft(scrollLeft - 10);
            } else if (x > contentBox.right) {
                grid.setScrollLeft(scrollLeft + 10);
            }

        }

        function handleEnd(e) {

            isMove = false;
            moveCell = null;
            $(document).unbind(".excel-selection");
        }

        $(grid.el).bind("mousedown", function (e) {

            var cell = $(e.target).closest('.mini-grid-cell')[0];
            if (cell) {
                //if (e.button == 2) return;

                var currentCell = getCellAddress(e);

                //if(e.button == 2) debugger
                if (e.button == 2 && me.isInCellRange(currentCell)) {
                	var cmenu = mini.get('miniui-dg-contextmenu-'+grid.id);
                	cmenu.on('itemclick',function(e){
                		if(e.item.id == 'miniui-dg-contextmenu-copy-'+grid.id){
                			me.copyGridToClipboard(grid);
                		}
                	})
                	cmenu.showAtPos(e.pageX, e.pageY);
                    return false;
                }

                me.currentCell = currentCell;

                selectCellRange(me.currentCell, me.currentCell);
                //alert(me.currentCell.row+":"+me.currentCell.col);
                $(document).bind("mousemove.excel-selection", handleMove);
                $(document).bind("mouseup.excel-selection", handleEnd);
                e.preventDefault();

                contentBox = mini.getBox($(grid.el).find(".mini-grid-rows-view")[0]);
            }

        });

        ////////////////////////////////////////////////////////////////////
        grid.on('currentcellchanged', function (e) {
            //document.title = new Date().getTime();
            //$(grid.el).find(".excel-cell-selected").removeClass("excel-cell-selected");

            //alert(e.htmlEvent);

            var cell = grid.getCurrentCell();
            //            me.currentCell = getCellAddress(cell);
            //            selectCellRange(me.currentCell, me.currentCell);

            if (me._input) {
                var box = grid.getCellBox(cell[0], cell[1]);
                mini.setXY(me._input, box.left, box.top);
            }
            $(grid.el).find(".mini-grid-rows-view").scroll(function () {

            me.selectArea();
        })
        });

    },

    getCurrentCell: function () {
        return this.currentCell;
    },

    getCellRange: function () {
        return this.cellRange;
    },
    
    getValueArrayFromGrid: function (grid) {
        var range = this.cellRange;
        var array = [];

        if (range) {
            var columns = grid.getVisibleColumns();

            for (var i = range.startRow, l = range.endRow; i <= l; i++) {
                var textArray = [];
                array.push(textArray);

                var row = grid.getAt(i);
                for (var j = range.startCol, k = range.endCol; j <= k; j++) {
                    var column = columns[j];
                    var val = row[column.field];
                    var renderer = 	column.type != 'indexcolumn' &&
                    				column.type != 'indexColumn' &&
                    				column.type != 'checkcolumn' && 
                    				column.type != 'checkboxcolumn' && 
                    				column.type != 'comboboxcolumn' && 
                    				column.type != 'treeselectcolumn' &&
                    				column.copyRenderer != false && 
                    				column.renderer;
                    var cellValue=(renderer ? renderer({value:val,row:row}) : val);
                    do{
                    	if(/>(.*)</.test(cellValue)){
                			cellValue = cellValue.match(/>(.*)</)[1];
                		}
                    }while(/>(.*)</.test(cellValue))
                	
                    if (mini.isNull(cellValue)) cellValue = "";
                    textArray.push(cellValue);
                }
            }
        }

        return array;
    },

    copyGridToClipboard: function (grid) {

        var valArray = this.getValueArrayFromGrid(grid);
        var textArray = [];
        for (var i = 0, l = valArray.length; i < l; i++) {
            textArray.push(valArray[i].join('\t'));
        }
        this.copyToClipboard(textArray.join('\n'));

    },

    copyToClipboard: function (text) {

        mini.setClipboard(text);

    },
    selectArea: function () {
        var grid = this.grid;
        var range = this.getCellRange()

        //  console.log(range.startRow + ":" + range.endRow + ":" + range.startCol + ":" + range.endCol)
        //var selectArea = [range.startRow, range.endRow, range.startCol, range.endCol];

        var startCell = grid.getCellEl(range.startRow, range.startCol);
        var endCell = grid.getCellEl(range.endRow, range.endCol);

        if (grid.getVirtualScroll() == true) {
            if (mini.isNull(startCell) || mini.isNull(endCell)) {

                $(grid.el).find(".mini-grid-rows-view").find(".select-line").hide();
                this.cellRange = [];
                return
            }
        }

        var h0 = $(startCell).height() + 1;
        //var w0 = $(startCell).width();

        var viewArea = $(grid.el).find(".mini-grid-rows-view");

        var top = parseInt(viewArea.offset().top);
        var left = parseInt(viewArea.offset().left);

        var top1 = parseInt($(startCell).offset().top)
        var left1 = parseInt($(startCell).offset().left)

        var sTop = $(viewArea)[0].scrollTop;
        var sLeft = $(viewArea)[0].scrollLeft;

        console.log(top+":"+top1+":"+sTop + ":" + sLeft);

        var top2 = 0, left2 = 0;
        if (endCell) {
            var top2 = parseInt($(endCell).offset().top)
            var left2 = parseInt($(endCell).offset().left)
        }
        var h = top2 - top1 + $(endCell).height() + 1;
        var w = left2 - left1 + $(endCell).width() + 9;


        $(grid.el).find(".mini-grid-rows-view").find(".select-line").show();

        $(grid.el).find(".mini-grid-rows-view").find(".select-left").height(h).css({ "left": left1 - left + sLeft, "top": top1 - top + sTop })
        $(grid.el).find(".mini-grid-rows-view").find(".select-top").width(w).css({ "left": left1 - left + sLeft, "top": top1 - top + sTop })
        $(grid.el).find(".mini-grid-rows-view").find(".select-right").height(h).css({ "left": left1 + w - left + sLeft, "top": top1 - top + sTop })
        $(grid.el).find(".mini-grid-rows-view").find(".select-bottom").width(w).css({ "left": left1 - left + sLeft, "top": h + top1 - top + sTop })
    }

}
