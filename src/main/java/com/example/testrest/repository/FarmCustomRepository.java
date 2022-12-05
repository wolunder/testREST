package com.example.testrest.repository;

import com.example.testrest.model.Farm;

import java.util.List;
import java.util.Optional;

public interface FarmCustomRepository {

    public List<Optional<Farm>> listAllFarm();
}
