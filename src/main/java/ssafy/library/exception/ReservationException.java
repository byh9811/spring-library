package ssafy.library.exception;

public class ReservationException extends IllegalArgumentException {

    public ReservationException() {
        super();
    }

    public ReservationException(String msg) {
        super(msg);
    }

}
