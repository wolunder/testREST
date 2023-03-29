package com.example.testrest.repository;

import com.example.testrest.model.CadObject;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CadObjectRepository extends JpaRepository<CadObject, Long> {

    Optional<CadObject> findCadObjectByCadNumber(String cadNumber);

    Optional<CadObject> findCadObjectById(Long id);


    @Transactional
    @Query(value = "select c from CadObject c where c.archiveStatus = false")
    List<CadObject> getAllObject();


    @Query("select c from CadObject c where c.farm like :farm and c.archiveStatus = false")
    List<CadObject> findCadObjectByFarm(@Param("farm") String farm);

    @Transactional
    @Query(value = "select c from CadObject c where c.farm =: farm and c.typeOwn =: typeOwn and c.archiveStatus = false")
    List<CadObject> findCadObjectByFarmAndTypeOwn(@Param("farm") String farm, @Param("typeOwn") String typeOwn);


}
