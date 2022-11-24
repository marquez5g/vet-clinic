package com.example.vet.repositories;

import com.example.vet.entities.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Clinic getClinicByName(String name);
}
