package org.hrms.repository;


import org.hrms.repository.entity.User;
import org.hrms.repository.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

    Optional<User> findOptionalByAuthId(Long authid);

    Optional<User> findOptionalByUsername(String username);

    List<User> findByCompanyId(String id);

    List<User> findByRole(ERole role);

}
