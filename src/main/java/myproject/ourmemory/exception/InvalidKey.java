package myproject.ourmemory.exception;

import lombok.Getter;

@Getter
public class InvalidKey extends OurMemoryException {

    private static final String MESSAGE = "잘못된 요청입니다.";


    public InvalidKey() {
        super(MESSAGE);
    }

    public InvalidKey(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
