package com.sdet.auto.users.repository;

import com.sdet.auto.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // first value is model, the long is the primary key from the model.
public interface UserRepository extends JpaRepository<User, Long>{

}