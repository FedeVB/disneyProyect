package com.fede.alk.back.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/auth")
public class InicioController {

    @GetMapping(value = "/register")
    private ResponseEntity<?> register(){
        return null;
    }

    @GetMapping(value = "/login")
    private ResponseEntity<?> login(){
        return null;
    }
}
