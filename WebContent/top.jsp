<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>簡易Twitter</title>
         <link href="./css/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="main-contents">
            <div class="header">
            	<c:if test="${ empty loginUser }">
                	<a href="login">ログイン</a>
                	<a href="signup">登録する</a>
                </c:if>
				<c:if test="${ not empty loginUser }">
			        <a href="./">ホーム</a>
			        <a href="setting">設定</a>
			        <a href="logout">ログアウト</a>
			    </c:if>
            </div>
				<c:if test="${ not empty loginUser }">
				    <div class="profile">
				        <div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
				        <div class="account">@<c:out value="${loginUser.account}" /></div>
				        <div class="description"><c:out value="${loginUser.description}" /></div>
				    </div>
				</c:if>
				<c:if test="${ not empty errorMessages }">
				    <div class="errorMessages">
				        <ul>
				            <c:forEach items="${errorMessages}" var="errorMessage">
				                <li><c:out value="${errorMessage}" />
				            </c:forEach>
				        </ul>
				    </div>
				    <c:remove var="errorMessages" scope="session" />
				</c:if>

				<div class="form-area">
				    <c:if test="${ isShowMessageForm }">
				        <form action="message" method="post">
				            いま、どうしてる？<br />
				            <textarea name="text" cols="100" rows="5" class="tweet-box"></textarea>
				            <br />
				            <input type="submit" value="つぶやく">（140文字まで）
				        </form>
				    </c:if>
				</div>

				<!-- 日付指定 -->
				<form method="get" action="./">
					日付
						<input type="date" name="start" value = "${start}">
					  	～
					 	<input type="date" name="end" value = "${end}">
					 <button type="submit">絞込</button>
				</form>

				<div class="messages">
					<!--c:forEachはループ処理-->
					<!-- itemsは繰り返す対象を指定する。今回はEL 式 のmessagesリストからvarに入れて繰り返す -->
				    <c:forEach items="${messages}" var="message">
				    	<!-- メッセージの塊大枠。範囲を指定するdiv、classは名前を付けている-->
				        <div class="message">
				            <div class="account-name">
				            	<!-- spanただのタグ -->
								<span class="account">
									<!-- aはリンクのタグクリックするとhrefに書いてるURLに飛ぶ -->
									<!-- ./ は「今いるページと同じ場所」を意味する相対パス。
									?user_id=15の？はここからパラメータの始まるよって意味
									user_idというキーに15という値をセットして、URLの後ろに渡している。 -->
								    <a href="./?user_id=<c:out value="${message.userId}"/> ">
								        <c:out value="${message.account}" />
								    </a>
								</span>
				                <span class="name"><c:out value="${message.name}" /></span>
				            </div>
				            <div class="text" style="white-space: pre-wrap;"><c:out value="${message.text}" /></div>
				            <div class="date"><fmt:formatDate value="${message.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>

						   <c:if test="${loginUser.id == message.userId}">
						        <form action="deleteMessage" method="post">
						            <input type="hidden" name="id" value="${message.id}">
						            <input type="submit" value="削除"/>
						         </form>
						         <!-- divクラスで分けるとわかりやすかった。devは目印なる。編集(CSS)の時にも使える。 -->
						         	<form action="edit" method="get" style="display:inline;">
        								<input type="hidden" name="id" value="${message.id}">
        								<input type="submit" value="編集"/>
   									</form>
						   </c:if>
				        </div>
				       <div class="form-area">
				   		 <c:if test="${ isShowMessageForm }">
				        			<form action="comment" method="post">
				       					<!-- どの投稿への返信か -->
       									 <input type="hidden" name="id" value="${message.id}"/>
				           			 		返信<br />
				           			 <textarea name="text" cols="100" rows="5" class="tweet-box"></textarea>
				           			 <br />
				           		 <input type="submit" value="送信">
				       		 </form>
				  		  </c:if>
						</div>
						<!--  -->
						<div class="replies">
							<c:forEach items="${comments}" var="comment">
								    <!-- この投稿に対する返信か？ -->
   								<c:if test="${comment.messageId eq message.id}">
									<div class = "account">
										<c:out value="${comment.account}" />
									<span class = "name">
										<c:out value="${comment.name}" />
									</span>
									</div>
									<div class="text">
    									<pre><c:out value="${comment.text}" /></pre>
									</div>
									<div class = "date">
									<fmt:formatDate value="${comment.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" />	
									</div>
								</c:if>
							</c:forEach>
						</div>
				    </c:forEach>
				</div>


            	<div class="copyright"> Copyright(c)Temma Hayashi</div>
        	</div>
    </body>
</html>