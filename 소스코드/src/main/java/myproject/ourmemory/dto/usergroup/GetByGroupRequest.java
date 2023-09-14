package myproject.ourmemory.dto.usergroup;

import lombok.Builder;
import lombok.Getter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Builder
public class GetByGroupRequest {

    private static final int MAX_SIZE = 2000;

    private Long groupId;

    @Builder.Default
    private Integer size = 10;

    @Builder.Default
    private Integer page = 1;

    public Long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}
