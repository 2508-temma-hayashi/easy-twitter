	package chapter6.filter;
	//リクエストレスポンスの文字コードを統一するクラス
	import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;

@WebFilter(urlPatterns = {"/edit","/setting"})
public class LoginFilter implements Filter {

	public static String INIT_PARAMETER_NAME_ENCODING = "encoding";

	public static String DEFAULT_ENCODING = "UTF-8";


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		//ServletRequest型からHttpServletRequestにキャストして型変換
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//エラーのリスト作成
		List<String> errorMessage = new ArrayList<String>();
		 errorMessage.add("ログインしてください");
		//HttpServletRequestに変換したのでセッション取得①なんで変換してから取得する必要があるの？➡親クラスだから。
		HttpSession session = httpRequest.getSession();


		//getAttributeでログインユーザーを取得。③なんでAttribute？Paramaterじゃなくて？➡JSPから持ってくるときはPara。それ以外はAttri
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			session.setAttribute("errorMessages",errorMessage);
		    // 未ログインなのでログイン画面へリダイレクト④これも親クラスだから変換したものを使う。
			httpResponse.sendRedirect("./login");
			return;
			}
			chain.doFilter(request, response); // サーブレットを実行
		}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}

}
