package myproject.ourmemory;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.dto.usergroup.JoinUserGroupRequest;
import myproject.ourmemory.exception.UserGroupNotFound;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserGroupService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final initService initService;

//    @PostConstruct
    public void init() {
        initService.DBinit1();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class initService {

        private final UserRepository userRepository;
        private final GroupRepository groupRepository;
        private final UserGroupRepository userGroupRepository;
        private final UserGroupService userGroupService;
        private final PostRepository postRepository;

        public void DBinit1() {

            List<User> users = IntStream.range(1, 51)
                    .mapToObj(i -> {
                        return User.builder()
                                .name("회원" + i)
                                .nickName("닉네임" + i)
                                .build();
                    })
                    .collect(Collectors.toList());
            userRepository.saveAll(users);

            for (int i = 1; i <= 10; i++) {
                CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                        .userId(users.get(0).getId())
                        .groupName("컴공과" + i)
                        .build();
                Long userGroupId = userGroupService.create(request1);
                UserGroup userGroup = userGroupRepository.findById(userGroupId)
                        .orElseThrow(UserGroupNotFound::new);

                for (int j = 1; j <= 49; j++) {
                    JoinUserGroupRequest request2 = JoinUserGroupRequest.builder()
                            .userId(users.get(j).getId())
                            .groupId(userGroup.getGroup().getId())
                            .build();
                    userGroupService.join(request2);
                }
            }

        }
    }
}
