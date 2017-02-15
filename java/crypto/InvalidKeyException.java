package crypto;

/**
 * This class object is thrown when some characteristic of input key is not
 * satisfied.
 * 
 * @author prince
 *
 */
public class InvalidKeyException extends Exception {

	public InvalidKeyException(String arg0) {
		super(arg0);
	}

}
