package chapter6.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.service.MessageService;

@WebServlet(urlPatterns = {"/deleteMessage"})
public class DeleteMessageServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	    //削除対象のつぶやきIDをリクエストパラメータから取得
		int messageId = Integer.parseInt(request.getParameter("id"));

		//サービスクラスを呼び出して削除を実行
		//自分の投稿だけを消すためにログインしてる人のIDを渡す。
		MessageService service = new MessageService();
		service.deleteMessage(messageId);

		//リダイレクトでtopservletの一覧表示にとばす
		response.sendRedirect("./");
	}
}