package chapter6.exception;
//SQL使ってるときに発生したエラーを投げなおすクラス
import java.sql.SQLException;

public class SQLRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SQLRuntimeException(SQLException cause) {
		super(cause);
	}

}
