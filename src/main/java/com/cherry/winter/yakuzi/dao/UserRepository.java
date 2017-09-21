package com.cherry.winter.yakuzi.dao;

import com.cherry.winter.yakuzi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByName(@Param("name") String name);

    Iterable<User> findAll(Specification<User> specification);
    Page<User> findAll(Pageable pageable);
}
