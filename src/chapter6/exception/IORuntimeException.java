package chapter6.exception;
//入出力処理の例外をラップして投げなおすクラス
import java.io.IOException;

public class IORuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IORuntimeException(IOException cause) {
		super(cause);
	}

}
