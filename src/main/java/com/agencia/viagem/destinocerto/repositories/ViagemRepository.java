package com.agencia.viagem.destinocerto.repositories;

import com.agencia.viagem.destinocerto.dto.ViagemResponse;
import com.agencia.viagem.destinocerto.dto.ViagemResponseUser;
import com.agencia.viagem.destinocerto.models.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    @Query(value = "SELECT CONCAT(u.nome, ' ', u.sobrenome) as nomecompleto, v.dataviagem, d.nome " +
                    "FROM viagens v " +
                    "INNER JOIN users u ON v.user_id=u.id " +
                    "INNER JOIN destinos d ON v.destino_id=d.id " +
                    "WHERE u.id=?;", nativeQuery = true)
    List<ViagemResponseUser> getJoinByUserId(Long id);

    @Query(value = "SELECT u.username, v.dataviagem, d.nome FROM viagens v INNER JOIN users u ON v.user_id=u.id inner join destinos d on v.destino_id=d.id;", nativeQuery = true)
    List<ViagemResponse> getJoinTables();
}
