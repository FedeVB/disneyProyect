package com.fede.alk.back.app.controllers;

import com.fede.alk.back.app.models.dtos.PersonajeDto;
import com.fede.alk.back.app.models.entity.Personaje;
import com.fede.alk.back.app.service.interfaces.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
@RestController
@RequestMapping(value = "/characters")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    @GetMapping
    public ResponseEntity<?> listar() {
        Map<String, Object> response = new HashMap<>();
        response.put("personajes", personajeService.findAll().stream()
                .map(personaje -> new PersonajeDto(personaje.getImagen(), personaje.getNombre()))
                .collect(Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> porNombre(@PathVariable(value = "name") String nombre) {
        Map<String, Object> response = new HashMap<>();
        Personaje personajeBusc;

        try {
            personajeBusc = personajeService.findByNombre(nombre).orElse(null);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar el personaje en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (personajeBusc == null) {
            response.put("mensaje", "El personaje con el nombre : " + nombre + " no se encuentra en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("personaje", personajeBusc);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/age/{age}")
    public ResponseEntity<?> porEdad(@PathVariable(value = "age") Integer age) {
        Map<String, Object> response = new HashMap<>();
        Personaje personajeBusc;

        try {
            personajeBusc = personajeService.findByAge(age).orElse(null);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar el personaje en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (personajeBusc == null) {
            response.put("mensaje", "El personaje con la edad : " + age + " no se encuentra en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("personaje", personajeBusc);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/idMovie/{id}")
    public ResponseEntity<?> porPelicula(@PathVariable(value = "id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        List<Personaje> personajes;

        try {
            personajes = personajeService.findAllPeliculasId(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Ha ocurrido un error al realizar la busqueda en la base de datos");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("personajes", personajes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/detalle/{id}")
    public ResponseEntity<?> detalle(@PathVariable(value = "id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        Personaje personajeId;

        try {
            personajeId = personajeService.findById(id).orElse(null);
        } catch (DataAccessException e) {
            response.put("mensaje", "Ha ocurrido un error al consultar la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (personajeId == null) {
            response.put("mensaje", "No se encontro el personaje con el id : " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("personaje", personajeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Personaje personaje, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Personaje newPersonaje;

        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors().stream()
                    .map(error -> "El campo " + error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores", errores);
            response.put("mensaje", "Error al intentar actualizar el personaje");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            newPersonaje = personajeService.save(personaje);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al intentar crear al personaje");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El personaje ha sido creado con exito");
        response.put("personaje", newPersonaje);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> editar(@Valid @RequestBody Personaje personaje, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Personaje personajeUpdate;

        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors().stream()
                    .map(error -> "El campo " + error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores", errores);
            response.put("mensaje", "Error al intentar actualizar el personaje");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        if (personaje == null || personaje.getId() == null) {
            response.put("mensaje", "Error al actualizar el personaje");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            personajeUpdate = personajeService.save(personaje);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el personaje");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Personaje actualizado con exito");
        response.put("personaje", personajeUpdate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/asignarImagen")
    public ResponseEntity<?> agregarImagen(@RequestParam(value = "id", name = "id") Integer id,
                                           @RequestParam(value = "foto", name = "foto") MultipartFile foto) {
        Map<String, Object> response = new HashMap<>();

        byte[] imagen = devolverImagen(foto);
        Personaje personaje = personajeService.findById(id).orElse(null);
        if (personaje == null) {
            response.put("mensaje", "No se encontro el personaje con el id : " + id + " en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            personaje.setImagen(imagen);
            personajeService.save(personaje);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al guardar la imagen");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La imagen ha sido cargada con exito");
        response.put("personaje", personaje);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable(value = "id") Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            personajeService.deleteById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al borrar al personaje de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El personaje ha sido eliminado con exito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public byte[] devolverImagen(MultipartFile imagen) {

        if (!imagen.isEmpty()) {
            System.out.println(imagen.getContentType());
            if (imagen.getContentType().endsWith("jpg") || imagen.getContentType().endsWith("png")
            ||imagen.getContentType().endsWith("jpeg")) {
                try {
                    return imagen.getBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new byte[0];
    }

    @GetMapping("/imagen/{nombre}")
    public ResponseEntity<?> imagenes(@PathVariable(value = "nombre")String nombre){
        Personaje personaje=personajeService.findByNombre(nombre).orElse(null);
        byte[] foto=personaje.getImagen();
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(foto, cabecera, HttpStatus.OK);
    }
}
