package com.fede.alk.back.app.controllers;

import com.fede.alk.back.app.models.dtos.PeliculaDto;
import com.fede.alk.back.app.models.entity.Pelicula;
import com.fede.alk.back.app.service.interfaces.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/movies")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<List<PeliculaDto>> listar() {
        return new ResponseEntity<List<PeliculaDto>>(peliculaService.findAll().stream()
                .map(pelicula -> new PeliculaDto(pelicula.getImagen(), pelicula.getTitulo(), pelicula.getFechaCreacion()))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/detalle/{id}")
    public ResponseEntity<?> detalle(@PathVariable(value = "id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        Pelicula pelicula;

        try {
            pelicula = peliculaService.findById(id).orElse(null);
        } catch (DataAccessException e) {
            response.put("mensaje", "Ha ocurrido un error al intentar buscar la pelicula");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pelicula == null) {
            response.put("mensaje", "La pelicula con el id " + id + " no se encuentra en la base de datos");
            return new ResponseEntity(response, HttpStatus.NO_CONTENT);
        }

        response.put("pelicula", pelicula);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> crear(@Valid @RequestBody Pelicula pelicula, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Pelicula newPelicula;

        if(result.hasErrors()){
            List<String> errores=result.getFieldErrors().stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList());
            response.put("errores",errores);
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }
        try {
            newPelicula = peliculaService.save(pelicula);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear la pelicula en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("pelicula", newPelicula);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> editar(@Valid @RequestBody Pelicula pelicula,BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Pelicula peliculaUpdate;

        if(result.hasErrors()){
            List<String> errores=result.getFieldErrors().stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList());
            response.put("errores",errores);
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }

        try {
            peliculaUpdate = peliculaService.save(pelicula);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al editar la pelicula en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("pelicula", peliculaUpdate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> eliminar(@PathVariable(value = "id") Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            peliculaService.deleteById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la pelicula en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La pelicula con el id " + id + " ha sido eliminada exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> porNombre(@PathVariable(value = "name") String name) {
        Map<String, Object> response = new HashMap<>();
        Pelicula peliculaBusc;

        try {
            peliculaBusc = peliculaService.findByNombre(name).orElse(null);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar la pelicula en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (peliculaBusc == null) {
            response.put("mensaje", "No se encontro la pelicula con el nombre : " + name + " en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("pelicula", peliculaBusc);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/genre/{idGenero}")
    public ResponseEntity<?> porGenero(@PathVariable(value = "idGenero") Integer idGenero) {
        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculasBusc;

        try {
            peliculasBusc = peliculaService.findAllByGeneroId(idGenero);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar las peliculas en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("peliculas", peliculasBusc);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/order/ASC")
    public ResponseEntity<?> ordernAsc() {
        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculasOrder;

        try {
            peliculasOrder = peliculaService.findAllByOrderByTituloAsc();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("peliculas", peliculasOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/order/DESC")
    public ResponseEntity<?> ordernDesc() {
        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculasOrder;

        try {
            peliculasOrder = peliculaService.findAllByOrderByTituloDesc();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("peliculas", peliculasOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
