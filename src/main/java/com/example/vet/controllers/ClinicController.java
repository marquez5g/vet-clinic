package com.example.vet.controllers;

import com.example.vet.dto.ClinicDto;
import com.example.vet.dto.PetDto;
import com.example.vet.services.ClinicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/clinic")
public class ClinicController {
    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    /**
     * Crea una clínica.
     * @param clinic la clínica a crear.
     * @return la clínica que fue creada.
     */
    @PostMapping
    public ResponseEntity<ClinicDto> createClinic(@RequestBody ClinicDto clinic) {
        ClinicDto savedClinic = clinicService.save(clinic);
        return ResponseEntity.ok().body(savedClinic);
    }

    /**
     * Obtiene una clínica por su id de base de datos.
     * @param id el id de base de datos de la clínica.
     * @return la clínica.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClinicDto> getClinic(@PathVariable Long id) {
        ClinicDto clinicDto = clinicService.findById(id);
        return ResponseEntity.ok().body(clinicDto);
    }

    /**
     * Agrega mascotas a una clínica cuyo nombre se envía por parámetro.
     * @param pets una lista de mascotas.
     * @param clinicName el nombre de la clínica a la que se van a agregar las mascotas.
     * @return las mascotas que fueron añadidas.
     */
    @PostMapping("/pets")
    public ResponseEntity<String> addPetsToClinic(@RequestBody Set<PetDto> pets, @RequestParam String clinicName) {
        clinicService.addPets(clinicName, pets);
        return ResponseEntity.ok().body("All pets were saved successfully.");
    }

}
