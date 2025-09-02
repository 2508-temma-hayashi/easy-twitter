package chapter6.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.Message;
import chapter6.service.MessageService;
///edit というURLをこのサーブレットに割り当てる
@WebServlet(urlPatterns = {"/edit"})
public class EditServlet extends HttpServlet{
	@Override
	//★idからtextを持ってきてedit.jspにフォワードするDOGET★
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//textのIDをリクエストのid属性から抽出してint型に変換
		int messageId = Integer.parseInt(request.getParameter("id"));
		//MessageServiveオブジェクトを作ってその中のtext取得する(それはmessageへ)
		MessageService service = new MessageService();
		Message message = service.selectByTextId(messageId);


		//★URLのつぶやきのIDを数字以外に変更したときのエラーメッセージ★
		//テキストのIDを持ってくる
		String id = request.getParameter("id");

		//idに何も入らなかった時、テキストのIDが数字でない時、TEXTIDから取得したTEXTが空の時。
		if (id == null || !id.matches("^[0-9]+$") || message == null) {
			//sessionにリクエストを入れて変換
			HttpSession session = request.getSession();
			//リクエストの箱の中にエラーメッセージを詰めてる。（setAttributeメソッド）
			session.setAttribute("errorMessages",java.util.Arrays.asList("不正なパラメータが入力されました"));
			//getRequestDispatcherでどこに渡すか準備してforwardでrequest と responseを渡す
			response.sendRedirect(request.getContextPath() + "/top.jsp");
			return;
		}

		//★DOGETに戻る★
		//リクエストの中にメッセージ属性でtextを入れておく
		request.setAttribute("message", message);
		//それを"/edit.jsp"に渡す。リクエストを引き継いで。
		request.getRequestDispatcher("/edit.jsp").forward(request, response);
	}

	@Override
	//変更したtextをserviceに渡して変更後の画面をtop.jspにリダイレクトするDOPOST
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		//textのIDをリクエストのid属性から抽出してint型に変換（getParameterメソッド）
		int messageId = Integer.parseInt(request.getParameter("id"));
		String messageText = request.getParameter("text");
		//サービスクラスを呼び出して削除を実行
	    //自分の投稿だけ更新消すためにログインしてる人のIDを渡す。
		MessageService service = new MessageService();
		service.updateMessage(messageId, messageText);

		//リダイレクトでtopservletの一覧表示にとばす(sendRedirectメソッド）
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
}

