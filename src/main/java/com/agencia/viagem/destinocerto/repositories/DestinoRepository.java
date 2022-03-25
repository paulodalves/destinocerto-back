package com.agencia.viagem.destinocerto.repositories;

import com.agencia.viagem.destinocerto.models.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
}
