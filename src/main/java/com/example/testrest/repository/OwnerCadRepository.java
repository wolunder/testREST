package com.example.testrest.repository;

import com.example.testrest.model.OwnerCad;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OwnerCadRepository extends JpaRepository<OwnerCad, Long> {

    List<OwnerCad> findOwnerCadByCadNumber(String number);

    @Transactional
    @Query(value = "select o from OwnerCad o where o.cadNumber =: cadNumber and o.archiveStatus = false")
    List<OwnerCad> getAllOwnerByCadNumber(String cadNumber);

    @Transactional
    @Query( value = "select o from OwnerCad  o where o.cadNumber =: cadNumber and o.archiveStatus = true")
    List<OwnerCad> getArchiveOwnerByCadNumber(String cadNumber);
}
