package com.example.vet.services;

import com.example.vet.dto.PetDto;
import com.example.vet.entities.Pet;
import com.example.vet.repositories.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PetService {

    private final PetRepository petRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * Filtra mascotas por tpo. Si exclude es verdadero llama a getPetsExcludeType para obtener las mascotas de tipo
     * Pet que no son de ese tipo, las mapea a PetDto usando el método mapToDto. De lo contrario llama a getPetsOfType
     * para obtener las que son de ese tipo y las mapea de la misma manera.
     * @param type el tipo de mascota.
     * @param exclude etermina si se ontendrá mascotas de ese tipo o del resto de tipos.
     * @return la lista de mascotas filtradas.
     */
    public Set<PetDto> getPetsByType(String type, Boolean exclude) {
        Set<PetDto> petDtos;
        Set<Pet> pets;
        if (exclude != null && exclude) {
            pets = getPetsExcludeType(type);
        } else {
            pets = getPetsOfType(type);
        }
        petDtos = mapToDto(pets);
        return petDtos;
    }

    /**
     * Busca todas las mascotas de una clínica usando el nombre de la clínica. Usa el repositorio de las mascotas.
     * @param clinicName el nombre de la clínica.
     * @return la lista de mascotas de la clínica buscada.
     */
    public Set<PetDto> getPetsByClinicName(String clinicName) {
        Set<Pet> pets = petRepository.getAllByClinicName(clinicName);
        Set<PetDto> petDtos = new HashSet<>();
        for (Pet pet :
                pets) {
            petDtos.add(objectMapper.convertValue(pet, PetDto.class));
        }
        return petDtos;
    }

    /**
     * Obtiene mascotas que no son del tipo pasado por parámetro.
     * @param type el tipo de mascota a excluir.
     * @return la lisat de mascotas tipo Pet.
     */
    private Set<Pet> getPetsExcludeType(String type) {
        return petRepository.getAllByTypeIsNot(type);
    }

    /**
     * Obtiene mascotas del tipo pasado por parámetro.
     * @param type el tipo de mascota.
     * @return la lisat de mascotas tipo Pet.
     */
    private Set<Pet> getPetsOfType(String type) {
        return petRepository.getAllByType(type);
    }

    /**
     * Mapea una lista de mascotas tipo Pet a PetDto.
     * @param pets la lista de mascotas tipo Pet.
     * @return una lista de mascotas tipo PetDto.
     */
    private Set<PetDto> mapToDto (Set<Pet> pets) {
        Set<PetDto> petDtos = new HashSet<>();
        for (Pet pet :
                pets) {
           petDtos.add(objectMapper.convertValue(pet, PetDto.class));
        }
        return petDtos;
    }
}
