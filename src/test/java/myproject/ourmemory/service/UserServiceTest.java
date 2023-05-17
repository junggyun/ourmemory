package myproject.ourmemory.service;

import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.user.CreateUserRequestDto;
import myproject.ourmemory.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 등록")
    public void 회원_등록() throws Exception {
        //given
        CreateUserRequestDto request = CreateUserRequestDto.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();
        //when
        Long userId = userService.join(request);

        //then
        User user = userRepository.findOne(userId);

        assertEquals(1, userRepository.findAll().size());
        assertEquals("박정균", user.getName());
        assertEquals("테란킹", user.getNickName());
    }

    @Test
    @DisplayName("특정 회원 조회")
    public void 특정_회원_조회() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();

        userRepository.save(user);

        //when
        User findUser = userService.findOneUser(user.getId());

        //then
        assertEquals("박정균", findUser.getName());
        assertEquals("테란킹", findUser.getNickName());
     }

     @Test
     @DisplayName("전체 회원 조회")
     public void 전체_회원_조회() throws Exception {
         //given
         User user1 = User.builder()
                 .name("박정균")
                 .nickName("테란킹")
                 .build();
         userRepository.save(user1);

         User user2 = User.builder()
                 .name("정한별")
                 .nickName("항뵬")
                 .build();
         userRepository.save(user2);

         //when
         List<User> findUsers = userService.findAllUsers();

         //then
         assertEquals(2L, findUsers.size());

      }

    @Test
    @DisplayName("전체 회원 1페이지 조회")
    public void 전체_회원_1페이지_조회() throws Exception {
        //given
        List<User> users = IntStream.range(0, 30)
                .mapToObj(i -> {
                    return User.builder()
                            .name("회원" + i)
                            .nickName("닉네임" + i)
                            .build();
                })
                .collect(Collectors.toList());
        userRepository.saveAll(users);

        //when
        List<User> findUsers = userService.findPagingUsers(0, 10);

        //then
        assertEquals(10L, findUsers.size());

    }

}