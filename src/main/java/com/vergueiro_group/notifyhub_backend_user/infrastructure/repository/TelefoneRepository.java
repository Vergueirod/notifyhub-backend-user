package com.vergueiro_group.notifyhub_backend_user.infrastructure.repository;

import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
