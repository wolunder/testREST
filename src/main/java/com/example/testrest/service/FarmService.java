package com.example.testrest.service;

import com.example.testrest.model.CadObject;
import com.example.testrest.model.Farm;
import com.example.testrest.repository.FarmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmService {

    public static final Logger LOG = LoggerFactory.getLogger(FarmService.class);

    private final FarmRepository farmRepository;

    @Autowired
    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public Farm addFarm(Farm farm){

        LOG.info(farm.getFarm() + " добавлен в список хозяйств.");
        return farmRepository.save(farm);
    }

    public List<Farm> getAllFarmList(){

        return farmRepository.findAll();
    }
}
