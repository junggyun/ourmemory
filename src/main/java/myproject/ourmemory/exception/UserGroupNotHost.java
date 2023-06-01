package myproject.ourmemory.exception;

import lombok.Getter;

@Getter
public class UserGroupNotHost extends OurMemoryException {

    private static final String MESSAGE = "잘못된 요청입니다.";


    public UserGroupNotHost() {
        super(MESSAGE);
    }

    public UserGroupNotHost(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
