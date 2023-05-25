package myproject.ourmemory.repository;

import myproject.ourmemory.domain.Group;
import myproject.ourmemory.dto.group.GetGroupRequest;

import java.util.List;

public interface CustomGroupRepository {

    List<Group> findGroups(GetGroupRequest request);
}
