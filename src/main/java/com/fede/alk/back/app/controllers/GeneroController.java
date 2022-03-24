package com.fede.alk.back.app.controllers;

import com.fede.alk.back.app.models.entity.Genero;
import com.fede.alk.back.app.service.interfaces.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/genre")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Genero genero) {
        Map<String, Object> response = new HashMap<>();
        Genero newGenero;

        try {
            newGenero = generoService.save(genero);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear el genero");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El genero ha sido creado con exito");
        response.put("genero", newGenero);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
