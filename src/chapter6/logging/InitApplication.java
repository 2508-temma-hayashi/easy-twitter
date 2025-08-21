package chapter6.logging;
//ログの設定を読み込んで初期化するクラス。いらないものはポイする。
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class InitApplication {

    /**
     * シングルトン
     */
    private static InitApplication instance = null;

    /**
     * java.util.log.LogManagerに読み込ませるログプロパティファイル
     */
    public static final String LOG_PROPERTIES_NAME = "logging.properties";


    /**
     * InitApplicationのインスタンスを返す。
     * @return InitApplicationのインスタンス
     */
    public static InitApplication getInstance(){
        if( instance == null){
            instance = new InitApplication();
        }
        return instance;
    }

    /**
     * アプリケーションの初期化処理
     */
    public void init(){
        initLog();
    }

    /**
     * ログの初期化
     */
    private void initLog(){

        InputStream in = null;
        try {
        	//★"logging.properties"を取得して開いて読み込むための準備をしてる
            in = InitApplication.class.getClassLoader().getResourceAsStream(LOG_PROPERTIES_NAME);
            if (in == null) {
                System.err.println(LOG_PROPERTIES_NAME +"がクラスパスに存在しません。");
            }
            //★LogManagerクラスのオブジェクトを持ってきて、それにファイルを読ませている。
            LogManager.getLogManager().readConfiguration(in);

            //カスタムフォーマットをFileHandlerへ直接登録
            //①ファイルにログをかきこむオブジェクト準備（どこに書くのか）
            Handler handler = new FileHandler();
            //②ログを書くフォーマットを決めるためのメソッドを準備➡LogFormatterクラスのオブジェクトを引っ張ってくる
    		handler.setFormatter(new LogFormatter());

    		//①twitterというラベルが付いたログを出力するオブジェクト（ロガー）を作成
    		Logger root = Logger.getLogger("twitter");
    		//②他のログを書く仕組み（ロガー）を無効にする（？）
    		root.setUseParentHandlers(false);
    		//③ログを書く仕組み（ロガー）を一つずつ取り出す。
    		for (Handler h : root.getHandlers()) {
    			//④それがファイルを書くものだったらremove
    		    if (h instanceof FileHandler) {
    		      root.removeHandler(h);
    		    }
    		}
    		//さっき作ったやつをロガーの集まりに追加
    		root.addHandler(handler);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if( in != null ){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
