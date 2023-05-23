package myproject.ourmemory.exception;

public class UserNotFound extends OurMemoryException {

    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
