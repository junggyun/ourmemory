package myproject.ourmemory;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserGroupService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final initService initService;

    @PostConstruct
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

            User user1 = User.builder()
                    .name("테스터1")
                    .nickName("닉네임1")
                    .build();
            userRepository.save(user1);

            User user2 = User.builder()
                    .name("테스터2")
                    .nickName("닉네임2")
                    .build();
            userRepository.save(user2);

            CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                    .userId(user1.getId())
                    .groupName("그룹1")
                    .build();

            CreateUserGroupRequest request2 = CreateUserGroupRequest.builder()
                    .userId(user2.getId())
                    .groupName("그룹2")
                    .build();

            userGroupService.create(request1);
            userGroupService.create(request2);
        }
    }
}
