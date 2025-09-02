<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>つぶやき編集画面</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="header">
		<a href="../">ホーム</a>
		<a href="setting">設定</a>
		<a href="logout">ログアウト</a>
	</div>

	<div class="main-contents">
		<!-- 入力フォーム　送信するときに使うHTTPメソッドの種類を指定 -->
		<form action="edit" method="post">
     		<input type="hidden" name="id" value="${message.id}">

    		 <p>つぶやき</p>
     		<textarea name="text" rows="5" cols="40"><c:out value="${message.text}" /></textarea><br/>

     		<input type="submit" value="更新">
    	</form>
    		<a class="back" href="top">戻る</a>
    	<!-- 誰が作ったか書いてるだけ -->
    	<div class="copyright"> Copyright(c)Temma Hayashi</div>
   </div>
</body>
</html>