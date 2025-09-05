package chapter6.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.User;
import chapter6.beans.UserComment;
import chapter6.beans.UserMessage;
import chapter6.logging.InitApplication;
import chapter6.service.CommentService;
import chapter6.service.MessageService;


//URLを指定
@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
    * ロガーインスタンスの生成
    */
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public TopServlet() {
        InitApplication application = InitApplication.getInstance();
        application.init();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());
	  //ログインしている人しか通さない初期化
      boolean isShowMessageForm = false;
      //(User)でキャストしている getsessionでセッション情報を取り出す。その中からxUserを取り出す
      User user = (User) request.getSession().getAttribute("loginUser");
      if (user != null) {
    	  isShowMessageForm = true;
       }
       /*
       * String型のuser_idの値をrequest.getParameter("user_id")で
       * JSPから受け取るように設定
       * MessageServiceのselectに引数としてString型のuser_idを追加
       */
       //ユーザーが入力したパラメーターからIDを取り出す
       String userId = request.getParameter("user_id");
       String start = request.getParameter("start");
       String end   = request.getParameter("end");
       //取り出したIDを使ってselectメソッドで本文を取り出す
       List<UserMessage> messages = new MessageService().select(userId, start, end);

       List<UserComment>comments = new CommentService().select();

       //リクエストの中につぶやきを入れる。JSP 側で ${messages}とかくと参照できる
       request.setAttribute("messages", messages);
       request.setAttribute("isShowMessageForm", isShowMessageForm);
       request.setAttribute("comments", comments);
       request.setAttribute("start", start);
       request.setAttribute("end", end);
       //リクエストをフォワード。セットした値をJSPが使える
       request.getRequestDispatcher("/top.jsp").forward(request, response);
    }
}

