package com.fede.alk.back.app.controllers;

import com.fede.alk.back.app.models.entity.Autoridad;
import com.fede.alk.back.app.models.entity.Usuario;
import com.fede.alk.back.app.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/auth")
public class InicioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/register")
    private ResponseEntity<?> register(@Valid @RequestBody Usuario usuario, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Usuario newUsuario;

        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors().stream()
                    .map(error -> "El campo " + error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores", errores);
            System.out.println("Hola");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


        if (usuarioService.existsByUsername(usuario.getUsername())) {
            response.put("mensaje", "Ya existe un usuario con este nombre");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            response.put("mensaje", "Ya existe un usuario con este email");
        }

        try {
            usuario.setAutoridades(Arrays.asList(new Autoridad(1, "USER")));
            newUsuario = usuarioService.save(usuario);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear el usuario");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido creado con exito");
        response.put("usuario", newUsuario);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    private ResponseEntity<?> login(@RequestParam(name = "username") String username, @RequestParam(value = "password") String password) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Ha iniciado session con exito", HttpStatus.OK);
    }
}
