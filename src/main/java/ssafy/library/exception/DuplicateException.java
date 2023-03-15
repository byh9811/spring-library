package ssafy.library.exception;

public class DuplicateException extends IllegalArgumentException {

    public DuplicateException() {
        super();
    }

    public DuplicateException(String msg) {
        super(msg);
    }

}
