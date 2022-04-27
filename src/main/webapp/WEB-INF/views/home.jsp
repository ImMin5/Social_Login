<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="url" value="<%=request.getContextPath()%>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script>

	$(function(){
		$("#login_btn").on("click",function(){
			
			var url  ="https://kauth.kakao.com/oauth/authorize?client_id=f20eb18d7d37d79e45a5dff8cb9e3b9e&redirect_uri=http://localhost:8080/login/oauth&response_type=code";
			$.ajax({
				url : url,
				type : "GET",
				success:function(data){
					console.log(data);
					//window.open("${url}/WEB-INF/views/popup.html",data);
					//console.log(data);
				},error : function(error){
					console.log(error);
				}
				
			});
			-->
		})
	})
	
</script>

</head>
<body>
<a id="login_btn">
로그인
</a>
<br/>
</body>
</html>