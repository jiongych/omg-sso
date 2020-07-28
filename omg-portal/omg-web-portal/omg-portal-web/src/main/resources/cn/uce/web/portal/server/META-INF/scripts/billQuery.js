$(function() {
	$(".toggle").toggle(function() {
		$(this).parent().next("table").show();
		$(this).addClass("jian").removeClass("jia");
	}, function() {
		$(this).parent().next("table").hide();
		$(this).addClass("jia").removeClass("jian");
	});
	$(".jilu").toggle(function() {
		$(this).parent().next("table").hide();
		$(this).addClass("jia").removeClass("jian");
	}, function() {
		$(this).parent().next("table").show();
		$(this).addClass("jian").removeClass("jia");
	});
	/*
	 * var x = 0; var y = 0; $(".mouseover").mouseover( function(e) { e = e ||
	 * window.event; this.myTitle = this.title; this.title = ""; var tooltip = "<div
	 * id='tooltip' class='tooltip'>" + this.myTitle + "</div>";
	 * $("body").append(tooltip); $("#tooltip").css({ "top" : (e.pageY + y) +
	 * "px", "left" : (e.pageX + x) + "px" }).show();
	 * 
	 * }).mouseout(function() { this.title = this.myTitle;
	 * $("#tooltip").remove(); }).mousemove(function(e) { $("#tooltip").css({
	 * "top" : (e.pageY + y) + "px", "left" : (e.pageX + x) + "px" }); });
	 */
	// $(document).mousemove(function (e) {
	// $("#span").text("X: " + e.pageX + ", Y: " + e.pageY);
	// });
	$(".mouseover").hover(
			function() {
				title = $(this).attr("telephone"),
						tooltip = "<span id='tooltip' class='tooltip' style='width:250px'>" + title
								+ "</span>";
				if ($("#tooltip")) {
					$("#tooltip").remove();
				}
				$(this).append(tooltip);
				$("#tooltip").css({
					"top" : $(this).pageY + "px",
					"left" : $(this).pageX + "px"
				}).show();
			}, function() {
				$(this).find("span:first").remove();
			});

})