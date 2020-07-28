<%-- <%@include file="../common/common.jsp" %>
	<script type="text/javascript">
	 	var billCode = '<%=request.getAttribute("billCode")%>';
	 	$.ajax({
				url:'${pageContext.request.contextPath}/bill/queryQkBillInfo.do',
				type:"POST",
				data:{billCodes:billCode},
				success:function (data) {
					console.log(data);
					$("#content").html(data.message);
				}
			});
	</script> --%>
	
	<%=request.getAttribute("content")%>
	