package com.example.vet.services;

import com.example.vet.dto.ClinicDto;
import com.example.vet.dto.PetDto;
import com.example.vet.entities.Clinic;
import com.example.vet.entities.Pet;
import com.example.vet.repositories.ClinicRepository;
import com.example.vet.repositories.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ClinicService {
    private final ClinicRepository clinicRepository;

    private final PetRepository petRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClinicService(ClinicRepository clinicRepository, PetRepository petRepository) {
        this.clinicRepository = clinicRepository;
        this.petRepository = petRepository;
    }

    /**
     * Guarda una clínica luego de convertir su Dto a la entidad.
     * @param clinicDto la clínica de tipo Dto.
     * @return la clínica guardada.
     */
    public ClinicDto save(ClinicDto clinicDto) {
        Clinic clinic = objectMapper.convertValue(clinicDto, Clinic.class);
        Clinic savedClinic = clinicRepository.save(clinic);
        return objectMapper.convertValue(savedClinic, ClinicDto.class);
    }

    /**
     * Buscar una clínica por id usando su repositorio. Recibe un Optional de la base de datos, revisa si está presente
     * y lo obtiene en el caso que exista.
     * @param id el id de la clínica
     * @return la clínica.
     */
    public ClinicDto findById(Long id) {
        Optional<Clinic> clinicOptional = clinicRepository.findById(id);
        Clinic clinic = new Clinic();
        if (clinicOptional.isPresent()) {
            clinic = clinicOptional.get();
        }
        return objectMapper.convertValue(clinic, ClinicDto.class);
    }

    /**
     * Agrega mascotas a una clínica. Busca la clínica por su nombre, recibe una lista de mascotas de tipo PetDto,
     * los transforma a tipo Pet, los agrega a la clínica y guarda la clínica.
     * @param clinicName el nombre de la clínica.
     * @param pets la lista de mascotas a agregar.
     */
    public void addPets(String clinicName, Set<PetDto> pets) {
        Clinic clinic = clinicRepository.getClinicByName(clinicName);
        Set<Pet> mappedPets = new HashSet<>();
        for (PetDto pet :
                pets) {
            Pet mappedPet = objectMapper.convertValue(pet, Pet.class);
            mappedPet.setClinic(clinic);
            mappedPets.add(mappedPet);
        }
        petRepository.saveAll(mappedPets);
    }


}
