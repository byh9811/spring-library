package ssafy.library.exception;

public class NullExistException extends IllegalArgumentException {

    public NullExistException() {
        super();
    }

    public NullExistException(String msg) {
        super(msg);
    }

}
