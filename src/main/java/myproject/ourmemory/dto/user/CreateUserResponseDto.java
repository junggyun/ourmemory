package myproject.ourmemory.dto.user;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.UserGroup;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateUserResponseDto {

    private Long id;

    public CreateUserResponseDto(Long id) {
        this.id = id;
    }
}
