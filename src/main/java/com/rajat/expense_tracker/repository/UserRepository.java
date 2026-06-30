package com.rajat.expense_tracker.repository;

import com.rajat.expense_tracker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByName(String name);
    List<UserEntity> findByNameContaining(String keyword);
    List<UserEntity> findByNameAndEmail(String name,String email);

//    @Query(value = """
//            SELECT U FROM UserEntity u
//            WHERE LOWER(u.name)
//            LIKE LOWER(CONCAT('%', :keyword, '%'))
//           """)
//    List<UserEntity> searchUsers(@Param("keyword") String keyword);
}
