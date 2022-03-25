package com.agencia.viagem.destinocerto.repositories;

import com.agencia.viagem.destinocerto.models.ERole;
import com.agencia.viagem.destinocerto.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(ERole name);
}
