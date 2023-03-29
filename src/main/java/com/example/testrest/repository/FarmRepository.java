package com.example.testrest.repository;

import com.example.testrest.model.CadObject;
import com.example.testrest.model.Farm;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    @Transactional
    @Query(value = "select f from Farm f where f.archiveStatus= false")
    List<Farm> getAllFarm();
}
