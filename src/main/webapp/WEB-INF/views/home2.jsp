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
</head>
<body>
  <a href="#0" id="kakaoLogin"><img src="${url}/img/kakao_login_large_narrow.png" alt="카카오계정 로그인" style="height: 100px;"/></a>

    <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
    <script>
        function saveToDos(token) { //item을 localStorage에 저장합니다. 
            typeof(Storage) !== 'undefined' && sessionStorage.setItem('AccessKEY', JSON.stringify(token)); 
        };

        window.Kakao.init('ad6f95c42016d0f207504edcfdb9716f');
        function checkUser(token){
        	var url = "${url}/login/kakao";
        	$.ajax({
        		url : url,
        		type : "POST",
        		data : {
        			token : token,
        		},success : function(data){
        			console.log(data);
        		},fail : function(error){
        			console.log(error);
        		}
        		
        	})
        }
        function kakaoLogin() {
            window.Kakao.Auth.login({
                scope: 'profile_nickname, account_email', //동의항목 페이지에 있는 개인정보 보호 테이블의 활성화된 ID값을 넣습니다.
                success: function(response) {
                    saveToDos(response.access_token); // 로그인 성공하면 사용자 엑세스 토큰 sessionStorage에 저장
                    checkUser(response.access_token)
                },
                fail: function(error) {
                    console.log(error);
                }
            });
        };

        const login = document.querySelector('#kakaoLogin');
        login.addEventListener('click', kakaoLogin);
    </script>
</body>
</html>