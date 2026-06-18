package com.rajat.expense_tracker.repository;

import com.rajat.expense_tracker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
