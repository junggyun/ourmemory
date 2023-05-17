package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.user.CreateUserRequestDto;
import myproject.ourmemory.dto.user.CreateUserResponseDto;
import myproject.ourmemory.dto.user.UserDto;
import myproject.ourmemory.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public CreateUserResponseDto createUser(@Valid @RequestBody CreateUserRequestDto request, BindingResult result) {

        Long userId = userService.join(request);

        return new CreateUserResponseDto(userId);
    }

    @GetMapping("/users/{userId}")
    public UserDto user(@PathVariable Long userId) {
        User findUser = userService.findOneUser(userId);

        return new UserDto(findUser);
    }

    @GetMapping("/users")
    public List<UserDto> users() {
        List<User> findUsers = userService.findAllUsers();
        List<UserDto> result = findUsers.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return result;
    }
}
