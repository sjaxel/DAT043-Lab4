package gateSim;

public class GateException extends RuntimeException {
	/**
	 * Exception for Gate-errors.
	 * @param m The message to display when an error is thrown.
	 */
	public GateException(String m) {
		super(m);
	}
}
