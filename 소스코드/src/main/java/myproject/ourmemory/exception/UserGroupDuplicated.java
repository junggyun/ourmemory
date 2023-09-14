package myproject.ourmemory.exception;

import lombok.Getter;

@Getter
public class UserGroupDuplicated extends OurMemoryException {

    private static final String MESSAGE = "해당 리소스가 이미 존재합니다.";


    public UserGroupDuplicated() {
        super(MESSAGE);
    }

    public UserGroupDuplicated(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 409;
    }
}
