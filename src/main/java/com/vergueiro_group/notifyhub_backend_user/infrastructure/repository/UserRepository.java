package com.vergueiro_group.notifyhub_backend_user.infrastructure.repository;

import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

}

