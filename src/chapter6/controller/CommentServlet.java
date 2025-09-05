	package chapter6.controller;

	import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Comment;
import chapter6.beans.User;
import chapter6.logging.InitApplication;
import chapter6.service.CommentService;


@WebServlet(urlPatterns = {"/comment"})
public class CommentServlet extends HttpServlet {
	Logger log = Logger.getLogger("twitter");
	public CommentServlet() {
        InitApplication application = InitApplication.getInstance();
        application.init();

    }

	//DOPOSTで送る
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		        " : " + new Object(){}.getClass().getEnclosingMethod().getName());


	    HttpSession session = request.getSession();
	    List<String> errorMessages = new ArrayList<String>();
	    //後でいろいろと使うので、JSPのリクエストの中から返信と返信先のつぶやきのIDを取得する
	    String text = request.getParameter("text");
	    int messageId = Integer.parseInt(request.getParameter("id"));

	    if (!isValid(text, errorMessages)) {
	        session.setAttribute("errorMessages", errorMessages);
	        response.sendRedirect("./");
	        return;
	    }
	    //後で使うinsertメソッドに引数として渡すためにオブジェクトを作る。
	    Comment comment = new Comment();
	    //useridを登録するためにセッションから情報を取りだす
	    User user = (User) session.getAttribute("loginUser");

	    comment.setText(text);
	    comment.setMessageId(messageId);
	    comment.setUserId(user.getId());

	    //insertメソッドにコメントの情報を送ってDBに登録してもらう
	    new CommentService().insert(comment);
	    //登録はお願いしたので、TOPSERVLRTに戻ってもらって表示する。
	    response.sendRedirect("./");


	}

	private boolean isValid(String text, List<String> errorMessages) {
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		if (StringUtils.isBlank(text)) {
			errorMessages.add("メッセージを入力してください");
		}else if (140 < text.length()) {
	        errorMessages.add("140文字以下で入力してください");
	    }
		if (errorMessages.size() != 0) {
		    return false;
		}
		return true;
	}
}


