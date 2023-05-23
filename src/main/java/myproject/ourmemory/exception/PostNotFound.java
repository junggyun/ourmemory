package myproject.ourmemory.exception;

public class PostNotFound extends OurMemoryException {

    private static final String MESSAGE = "존재하지 않는 게시글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
