package com.example.testrest.repository;

import com.example.testrest.model.CadObject;
import com.example.testrest.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

}
