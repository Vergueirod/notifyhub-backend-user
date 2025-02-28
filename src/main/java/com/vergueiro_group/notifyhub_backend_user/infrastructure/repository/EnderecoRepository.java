package com.vergueiro_group.notifyhub_backend_user.infrastructure.repository;

import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
