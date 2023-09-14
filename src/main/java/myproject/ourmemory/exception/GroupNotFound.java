package myproject.ourmemory.exception;

public class GroupNotFound extends OurMemoryException {

    private static final String MESSAGE = "존재하지 않는 그룹입니다.";

    public GroupNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
