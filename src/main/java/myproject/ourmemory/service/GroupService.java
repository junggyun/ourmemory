package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;

    /**
     * 그룹 등록
     */
    @Transactional
    public Long createGroup(Group group) {
        groupRepository.save(group);

        return group.getId();
    }

    /**
     * 그룹 수정
     */
    @Transactional
    public void updateName(Long groupId, String name) {
        Group findGroup = groupRepository.findOne(groupId);
        findGroup.updateName(name);
    }

    /**
     * 그룹 조회
     */
    //특정 그룹 조회
    public Group findOneGroup(Long groupId) {
        return groupRepository.findOne(groupId);
    }

    //전체 그룹 조회
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }
}
