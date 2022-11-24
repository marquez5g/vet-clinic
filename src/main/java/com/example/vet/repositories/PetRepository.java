package com.example.vet.repositories;

import com.example.vet.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Set<Pet> getAllByClinicName(String clinicName);

    Set<Pet> getPetsByClinicName(String clinicName);

    Set<Pet> getAllByTypeIsNot(String type);

    Set<Pet> getAllByType(String type);
}
