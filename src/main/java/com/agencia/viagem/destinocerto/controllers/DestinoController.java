package com.agencia.viagem.destinocerto.controllers;

import com.agencia.viagem.destinocerto.models.Destino;
import com.agencia.viagem.destinocerto.repositories.DestinoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/loc")
public class DestinoController {

    DestinoRepository destinoRepository;

    public DestinoController(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }

    @PostMapping("/destino")
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid Destino destino) {
        return ResponseEntity.status(HttpStatus.CREATED).body(destinoRepository.save(destino));
    }

    @GetMapping("/destino")
    public ResponseEntity<Object> getAllParkingSpots(){
        return ResponseEntity.status(HttpStatus.OK).body(destinoRepository.findAll());
    }

    @GetMapping("/destino/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id")Long id){
        Optional<Destino> destinoOptional = destinoRepository.findById(id);
        if (!destinoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(destinoOptional.get());
    }

    @DeleteMapping("/destino/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") Long id){
        Optional<Destino> destinoOptional = destinoRepository.findById(id);
        if (!destinoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found!");
        }
        destinoRepository.delete(destinoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking spot deleted successfully.");
    }

    @PutMapping("/destino/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") Long id,
                                                    @RequestBody @Valid Destino destinoRequest) {
        Optional<Destino> destinoOptional = destinoRepository.findById(id);
        if (!destinoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found!");
        }

        var destino = destinoOptional.get();

        destino.setNome(destinoRequest.getNome());
        destino.setValor(destinoRequest.getValor());
        destino.setDescricao(destinoRequest.getDescricao());

        return ResponseEntity.status(HttpStatus.OK).body(destinoRepository.save(destino));
    }

}
