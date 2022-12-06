package com.example.testrest.repository;

import com.example.testrest.model.FileCad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
public interface FileCadRepository extends JpaRepository<FileCad, Long> {
}
