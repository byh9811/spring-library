package ssafy.library.exception;

public class MaxLoanException extends IllegalArgumentException {

    public MaxLoanException() {
        super();
    }

    public MaxLoanException(String msg) {
        super(msg);
    }

}
