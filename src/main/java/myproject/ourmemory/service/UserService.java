package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.user.CreateUserRequestDto;
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
    public Long join(CreateUserRequestDto request) {

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
    public void updateNickName(Long userId, String nickName) {
        User findUser = userRepository.findOne(userId);
        findUser.updateNickName(nickName);
    }

    /**
     * 회원 조회
     */
    //특정 회원 조회
    public User findOneUser(Long userId) {
        return userRepository.findOne(userId);
    }

    //전체 회원 조회
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    //전체 회원 페이징 조회
    public List<User> findPagingUsers(int offset, int limit) {
        return userRepository.findPaging(offset, limit);
    }

}
