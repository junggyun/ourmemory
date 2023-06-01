package myproject.ourmemory.exception;

public class UserGroupNotFound extends OurMemoryException {

    private static final String MESSAGE = "존재하지 않는 유저그룹입니다.";

    public UserGroupNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
