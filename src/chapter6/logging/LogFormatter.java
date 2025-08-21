package chapter6.logging;
//ログのフォーマットを整えるクラス
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter  {
	//日付を"yyyy/MM/dd HH:mm:ss.SSS"これで出力するオブジェクト（道具）を作る
    private static final DateFormat  formatter = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss.SSS" );
    //ログを重大さで分けてマップに格納してる。
    private static final Map<Level, String> levelMsgMap = Collections.unmodifiableMap(
            new HashMap<Level, String>() { {
                put( Level.SEVERE,  "ERROR" );
                put( Level.WARNING, "WARN" );
                put( Level.INFO,    "INFO" );
                put( Level.CONFIG,  "CONF" );
                put( Level.FINE,    "FINE" );
                put( Level.FINER,   "FINE" );
                put( Level.FINEST,  "FINE" );
            } } );


    @Override
    //デフォのメソッドを上書き。ログの情報を渡して出力の見た目調整
    public String format(LogRecord record) {

        StringBuilder sb = new StringBuilder(200);
        
        //時刻を取得してDate型に
        Date instant = new Date(record.getMillis());
        //それをさっき指定した形に整える
        sb.append( formatter.format ( instant ) );
        sb.append( " " );
        
        //重要度をわかりやすく変換
        sb.append( levelMsgMap.get( record.getLevel() ) );
        sb.append( " " );
        
        //メッセージを出力
        sb.append( formatMessage( record ) );
        
        //改行
        sb.append( System.lineSeparator() );
        
        //例外対策（recordにエラーがあれば）
        if ( record.getThrown() != null ) {
            try {
            	//文字列書き出し
                StringWriter sw = new StringWriter();
                //これがないと書けないっぽい？
                PrintWriter pw = new PrintWriter( sw );
                //実際に書き出し
                record.getThrown().printStackTrace( pw );
                pw.close();
                sb.append( sw.toString() );
            } catch ( Exception ex ) {
            }
        }

        return sb.toString();
    }


}
