package myproject.ourmemory.repository;

import myproject.ourmemory.domain.Group;

import java.util.List;

public interface CustomGroupRepository {

    List<Group> findAllWithUser();
}
