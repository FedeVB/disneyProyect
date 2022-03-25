package com.fede.alk.back.app.controllers;

import com.fede.alk.back.app.models.entity.Foto;
import com.fede.alk.back.app.models.entity.Genero;
import com.fede.alk.back.app.models.entity.Personaje;
import com.fede.alk.back.app.service.interfaces.FotoService;
import com.fede.alk.back.app.service.interfaces.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/genre")
public class GeneroController {

    @Autowired
    private GeneroService generoService;
    @Autowired
    private FotoService fotoService;

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

    @PostMapping(value = "/imagen")
    public ResponseEntity<?> agregarImagen(@RequestParam(value = "id", name = "id") Integer id,
                                           @RequestParam(value = "foto", name = "foto") MultipartFile foto) {
        Map<String, Object> response = new HashMap<>();

        Genero genero = generoService.findById(id).orElse(null);
        if (genero == null) {
            response.put("mensaje", "No se encontro el Genero con el id : " + id + " en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Foto imagen = new Foto();
        imagen.setContent(devolverImagen(foto));
        imagen = fotoService.save(imagen);
        try {
            if (genero.getUrlFoto() != null) {
                fotoService.deleteById(Integer.parseInt(genero.getUrlFoto()
                        .replace("http://localhost:8080/genre/imagen/", "")));
            }
            genero.setUrlFoto("http://localhost:8080/genre/imagen/" + imagen.getId());
            generoService.save(genero);
        } catch (DataAccessException e) {
            fotoService.deleteById(imagen.getId());
            response.put("mensaje", "Error al guardar la imagen");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La imagen ha sido cargada con exito");
        response.put("genero", genero);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public byte[] devolverImagen(MultipartFile imagen) {

        if (!imagen.isEmpty()) {
            if (imagen.getContentType().endsWith("jpg") || imagen.getContentType().endsWith("png")
                    || imagen.getContentType().endsWith("jpeg")) {
                try {
                    return imagen.getBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new byte[0];
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<?> imagenes(@PathVariable(value = "id") Integer id) {
        Foto imagen = fotoService.findById(id).orElse(null);
        byte[] foto = imagen.getContent();
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(foto, cabecera, HttpStatus.OK);
    }
}
