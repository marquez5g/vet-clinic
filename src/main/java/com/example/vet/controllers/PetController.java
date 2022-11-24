package com.example.vet.controllers;

import com.example.vet.dto.PetDto;
import com.example.vet.services.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    /**
     * Este método gstiona la obtención de mascotas. Recibetres parámetros opcionales para definir la lista de
     * mascotas a devolver. Si se recibe el nombre de la clínica, se enviarán todas las mascotas de esa clínica. Si se
     * recibe el tipo de mascota, se traerán todas las de ese tipo sin importar la clínica. Pero si además del tipo
     * se recibe el parámetro exlucir, se traerán mascotas que no sean del tipo recibido.
     * @param type el tipo de mascota.
     * @param exclude si se traerán mascotas que no son del tipo recibido.
     * @param clinicName el nombre de la clínica.
     * @return una lista de mascotas filtrada.
     */
    @GetMapping
    public ResponseEntity<Set<PetDto>> getPets(@RequestParam(required = false) String type,
                                               @RequestParam(required = false) Boolean exclude,
                                               @RequestParam(required = false) String clinicName) {
        ResponseEntity<Set<PetDto>> response = ResponseEntity.badRequest().build();
        if (clinicName != null) {
            Set<PetDto> pets = petService.getPetsByClinicName(clinicName);
            response = ResponseEntity.ok().body(pets);
        } else if (type != null) {
            Set<PetDto> petDtos = petService.getPetsByType(type, exclude);
            response = ResponseEntity.ok().body(petDtos);
        }
        return response;
    }
}
