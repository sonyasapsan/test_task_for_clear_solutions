package com.test_task.project.repository;

import com.test_task.project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.birthDate BETWEEN :from AND :to")
    Page<User> getAllUsersByDateRange(@Param("from") LocalDate from, @Param("to") LocalDate to, Pageable pageable);
}
