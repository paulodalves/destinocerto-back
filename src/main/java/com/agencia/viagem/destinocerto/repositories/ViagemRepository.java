package com.agencia.viagem.destinocerto.repositories;

import com.agencia.viagem.destinocerto.models.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    List<Viagem> findByUserId(Long userId);
}
