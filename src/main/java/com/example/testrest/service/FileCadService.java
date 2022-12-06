package com.example.testrest.service;


import com.example.testrest.model.CadObject;
import com.example.testrest.model.FileCad;
import com.example.testrest.repository.FileCadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileCadService {

    public static final Logger LOG = LoggerFactory.getLogger(FileCadService.class);

    private final FileCadRepository fileCadRepository;

    @Autowired
    public FileCadService(FileCadRepository fileCadRepository) {
        this.fileCadRepository = fileCadRepository;
    }


    public FileCad addFileToCadObject(CadObject cadObject, String filePath, String typeDocument){
         FileCad fileCad = new FileCad();

         fileCad.setFilePath(filePath);
         fileCad.setTypeDocument(typeDocument);
         fileCad.setCadObject(cadObject);
        return fileCadRepository.save(fileCad);
    }
}
