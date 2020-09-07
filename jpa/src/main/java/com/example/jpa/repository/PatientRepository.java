package com.example.jpa.repository;

import com.example.jpa.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer>, JpaSpecificationExecutor<Patient> {

    List<Patient> getByFirstName(String firstName);

    Patient findByFirstNameAndLastName(String firstName, String lastName);

    List<Patient> readByHeightGreaterThan(BigDecimal height);
}