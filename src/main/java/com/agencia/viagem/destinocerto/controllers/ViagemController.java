package com.agencia.viagem.destinocerto.controllers;

import com.agencia.viagem.destinocerto.exeption.ResourceNotFoundException;
import com.agencia.viagem.destinocerto.models.Viagem;
import com.agencia.viagem.destinocerto.repositories.UserRepository;
import com.agencia.viagem.destinocerto.repositories.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/marcar")
public class ViagemController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ViagemRepository viagemRepository;

    @GetMapping("/user/{userId}/viagens")
    public ResponseEntity<List<Viagem>> listarTodasViagensPorUserId(@PathVariable(value = "userId") Long userId) {
        if (!userRepository.existsById(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Viagem> viagens = viagemRepository.findByUserId(userId);
        return new ResponseEntity<>(viagens, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/viagens")
    public ResponseEntity<Viagem> criarComentario(@PathVariable(value = "userId") Long userId,
                                                      @RequestBody Viagem viagemRequest) {
        Viagem viagem = userRepository.findById(userId).map(user -> {
            viagemRequest.setUser(user);
            return viagemRepository.save(viagemRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Destino não encontrado com id = " + userId));
        return new ResponseEntity<>(viagem, HttpStatus.CREATED);
    }

    @DeleteMapping("/viagem/{id}")
    public ResponseEntity<HttpStatus> deletarViagem(@PathVariable("id") Long id) {
        viagemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
