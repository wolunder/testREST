package com.example.testrest.repository;

import com.example.testrest.model.CadObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CadObjectRepository extends JpaRepository<CadObject, Long> {

    Optional<CadObject> findCadObjectByCadNumber(String cadNumber);


}
