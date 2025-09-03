package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.service.MessageService;
///edit というURLをこのサーブレットに割り当てる
@WebServlet(urlPatterns = {"/edit"})
public class EditServlet extends HttpServlet{
	@Override
	//★idからtextを持ってきてedit.jspにフォワードするDOGET★
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//テキストのIDを持ってくる
		String id = request.getParameter("id");


		//★URLのつぶやきのIDを数字以外に変更したときのエラーメッセージ★
		//idに何も入らなかった時、テキストのIDが数字でない時、TEXTIDから取得したTEXTが空の時。
		if (id == null || !id.matches("^[0-9]+$")) {
			//sessionにリクエストを入れて変換
			HttpSession session = request.getSession();
			//リクエストの箱の中にエラーメッセージを詰めてる。（setAttributeメソッド）
			session.setAttribute("errorMessages", java.util.Arrays.asList("不正なパラメータが入力されました"));
			//getRequestDispatcherでどこに渡すか準備してforwardでrequest と responseを渡す
			response.sendRedirect("./");
			return;
		}

		//textのIDをリクエストのid属性から抽出してint型に変換
		int messageId = Integer.parseInt(request.getParameter("id"));
		//MessageServiveオブジェクトを作ってその中のtext取得する(それはmessageへ)
		Message message = new MessageService().select(messageId);
		if (message == null) {
			//sessionにリクエストを入れて変換
			HttpSession session = request.getSession();
			//リクエストの箱の中にエラーメッセージを詰めてる。（setAttributeメソッド）
			session.setAttribute("errorMessages", java.util.Arrays.asList("不正なパラメータが入力されました"));
			//getRequestDispatcherでどこに渡すか準備してforwardでrequest と responseを渡す
			response.sendRedirect("./");
			return;
		}


		//★DOGETに戻る★
		//リクエストの中にメッセージ属性でtextを入れておく
		request.setAttribute("message", message);
		//それを"/edit.jsp"に渡す。リクエストを引き継いで。
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}

	@Override
	//変更したtextをserviceに渡して変更後の画面をtop.jspにリダイレクトするDOPOST
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		//textのIDをリクエストのid属性から抽出してint型に変換（getParameterメソッド）
		int messageId = Integer.parseInt(request.getParameter("id"));
		String messageText = request.getParameter("text");

		//有効性確認の段階
		//{./}これは今のディレクトリ
		List<String> errorMessages = new ArrayList<String>();
		Message message = new Message();
		message.setText(messageText);
		message.setId(messageId);
		if (!isValid(messageText, errorMessages)) {
            request.setAttribute("errorMessages", errorMessages);
            request.setAttribute("message", message);
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
            return;
		}

		//サービスクラスを呼び出して削除を実行
	    //自分の投稿だけ更新消すためにログインしてる人のIDを渡す。
		new MessageService().update(message);

		//リダイレクトでtopservletの一覧表示にとばす(sendRedirectメソッド）
		response.sendRedirect("./");
	}


	//isvalidメソッド
	private boolean isValid(String text, List<String> errorMessages) {
		if (StringUtils.isBlank(text)) {
			errorMessages.add("入力してください");
		}else if (140 < text.length()) {
			errorMessages.add("140文字以下で入力してください");
		}
		if (errorMessages.size() != 0) {
			return false;
		}
		return true;
	}
}

