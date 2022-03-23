package com.fede.alk.back.app.controllers;

//import com.fede.alk.back.app.jwt.JwtProvider;

import com.fede.alk.back.app.jwt.JwtProvider;
import com.fede.alk.back.app.mail.SendEmail;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private SendEmail sendEmail;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Usuario newUsuario;

        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors().stream()
                    .map(error -> "El campo " + error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores", errores);
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
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            newUsuario = usuarioService.save(usuario);
            sendEmail.sendTextEmail(usuario.getEmail(),usuario.getUsername());
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear el usuario");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException ex) {
            response.put("mensaje", "Ha ocurrido un error al enviar el email");
            response.put("error", ex.getMessage().concat(": ").concat(ex.getCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("mensaje", "El usuario ha sido creado con exito");
        response.put("usuario", newUsuario);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> response = new HashMap<>();
        response.put("token", "Bearer " + jwt);
        response.put("nombre", userDetails.getUsername());
        response.put("roles", userDetails.getAuthorities());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
