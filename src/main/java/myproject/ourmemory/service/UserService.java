package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.user.CreateUserRequest;
import myproject.ourmemory.dto.user.UpdateUserRequest;
import myproject.ourmemory.dto.user.GetUserRequest;
import myproject.ourmemory.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 등록
     */
    @Transactional
    public Long join(CreateUserRequest request) {

        User user = User.builder()
                .name(request.getName())
                .nickName(request.getNickName())
                .build();

        userRepository.save(user);

        return user.getId();
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void updateNickName(Long userId, UpdateUserRequest request) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        findUser.updateUser(request);
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteUser(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        userRepository.delete(findUser);
    }

    /**
     * 회원 조회
     */
    //특정 회원 조회
    public User findOneUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    //전체 회원 조회
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    //전체 회원 페이징 조회
    public List<User> findUsers(GetUserRequest request) {
        return userRepository.findUsers(request);
    }

}
