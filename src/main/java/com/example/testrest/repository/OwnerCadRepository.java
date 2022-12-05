package com.example.testrest.repository;

import com.example.testrest.model.OwnerCad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerCadRepository extends JpaRepository<OwnerCad, Long> {

    List<OwnerCad> findOwnerCadByCadNumber(String number);
}
