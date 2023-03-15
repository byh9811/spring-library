package ssafy.library.exception;

public class EmailFormatException extends IllegalArgumentException {

    public EmailFormatException() {
        super();
    }

    public EmailFormatException(String msg) {
        super(msg);
    }

}
