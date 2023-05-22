package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.user.*;
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
    public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request, BindingResult result) {

        Long userId = userService.join(request);

        return new CreateUserResponse(userId);
    }

    @GetMapping("/users/{userId}")
    public UserDto user(@PathVariable Long userId) {
        User findUser = userService.findOneUser(userId);

        return new UserDto(findUser);
    }

    @GetMapping("/users")
    public List<UserDto> users(@ModelAttribute GetUserRequest request) {
        List<User> findUsers = userService.findUsers(request);
        List<UserDto> result = findUsers.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return result;
    }

    @PostMapping("/users/{userId}")
    public UpdateUserResponse updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserRequest request) {
        userService.updateNickName(userId, request);

        return new UpdateUserResponse(userId);
    }

    @DeleteMapping("/users/{userId}")
    public DeleteUserResponse deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return new DeleteUserResponse(userId);
    }
}
