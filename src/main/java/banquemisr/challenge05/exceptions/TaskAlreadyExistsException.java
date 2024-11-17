package banquemisr.challenge05.exceptions;

public class TaskAlreadyExistsException extends RuntimeException {
    private String message;

    public TaskAlreadyExistsException() {
    }

    public TaskAlreadyExistsException(String msg) {
        super(msg);
        this.message = msg;
    }
}
