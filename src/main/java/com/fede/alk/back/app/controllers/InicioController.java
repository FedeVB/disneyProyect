package com.fede.alk.back.app.controllers;

//import com.fede.alk.back.app.jwt.JwtProvider;
import com.fede.alk.back.app.jwt.JwtProvider;
import com.fede.alk.back.app.models.entity.Autoridad;
import com.fede.alk.back.app.models.entity.Usuario;
import com.fede.alk.back.app.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario, BindingResult result) {

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
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        System.out.println(usuario.getUsername());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        Map<String,Object> response=new HashMap<>();
        response.put("token","Bearer "+jwt);
        response.put("nombre",userDetails.getUsername());
        response.put("roles",userDetails.getAuthorities());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
