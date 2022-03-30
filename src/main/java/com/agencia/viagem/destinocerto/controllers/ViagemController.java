package com.agencia.viagem.destinocerto.controllers;

import com.agencia.viagem.destinocerto.dto.ViagemResponse;
import com.agencia.viagem.destinocerto.dto.ViagemResponseUser;
import com.agencia.viagem.destinocerto.models.Destino;
import com.agencia.viagem.destinocerto.models.User;
import com.agencia.viagem.destinocerto.models.Viagem;
import com.agencia.viagem.destinocerto.repositories.DestinoRepository;
import com.agencia.viagem.destinocerto.repositories.UserRepository;
import com.agencia.viagem.destinocerto.repositories.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/marcar")
public class ViagemController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DestinoRepository destinoRepository;

    @Autowired
    ViagemRepository viagemRepository;

    @GetMapping("/user/{userId}/viagens")
    public ResponseEntity<List<ViagemResponseUser>> listarTodasViagensPorUserId(@PathVariable(value = "userId") Long userId) {
        if (!userRepository.existsById(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<ViagemResponseUser> viagens = viagemRepository.getJoinByUserId(userId);
        return new ResponseEntity<>(viagens, HttpStatus.OK);
    }

    @GetMapping("/user/")
    public ResponseEntity<List<ViagemResponse>> listarTodasViagens() {

        List<ViagemResponse> viagens = viagemRepository.getJoinTables();
        System.out.println(viagens);
        return new ResponseEntity<>(viagens, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/viagens/{destinoId}")
    public ResponseEntity<Object> criarViagem(@PathVariable(value = "userId") Long userId,
                                              @PathVariable(value = "destinoId") Long destinoId,
                                                      @RequestBody Viagem viagemRequest) {

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        Optional<Destino> destinoOptional = destinoRepository.findById(destinoId);
        if (!destinoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destino not found!");
        }

        viagemRequest.setUser(userOptional.get());
        viagemRequest.setDestino(destinoOptional.get());

        viagemRepository.save(viagemRequest);

        return ResponseEntity.status(HttpStatus.OK).body(viagemRequest);
    }

    @DeleteMapping("/viagem/{id}")
    public ResponseEntity<HttpStatus> deletarViagem(@PathVariable("id") Long id) {
        viagemRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.NO_CONTENT);
    }


}
