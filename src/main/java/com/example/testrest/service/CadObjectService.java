package com.example.testrest.service;

import com.example.testrest.dto.CadObjectDTO;
import com.example.testrest.model.CadObject;
import com.example.testrest.repository.CadObjectRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadObjectService {

    public static final Logger LOG = LoggerFactory.getLogger(CadObjectService.class);

    private final CadObjectRepository cadObjectRepository;

    @Autowired
    public CadObjectService(CadObjectRepository cadObjectRepository) {
        this.cadObjectRepository = cadObjectRepository;
    }

    public CadObject addObject(CadObject cadObject){

        LOG.info(cadObject.getCadNumber() + " сохранен");
        return cadObjectRepository.save(cadObject);
    }

    public Optional<CadObject> findObjectByCadNumber(String cadNumber){

        LOG.info(cadNumber);
        return cadObjectRepository.findCadObjectByCadNumber(cadNumber);
    }


    public List<CadObject> getAllObject(){

        LOG.info("Список кадастров извлечен");

        return cadObjectRepository.getAllObject();
    }

    public void deleteObject(Long id){
        Optional<CadObject> object = cadObjectRepository.findById(id);
            cadObjectRepository.delete(object.get());
    }

    public Boolean setArchiveStatusObj(Long id){
        Optional<CadObject> findOdj = cadObjectRepository.findCadObjectById(id);
        if(findOdj.isPresent()){
            CadObject cadObject = findOdj.get();
            cadObject.setArchiveStatus(true);
            cadObjectRepository.save(cadObject);
            return true;
        }
        return false;
    }


    public Optional<CadObject> findObjectById(Long id){
        return cadObjectRepository.findById(id);
    }

    public CadObject cadObjectDTOToCadObject(CadObject object,CadObjectDTO cadObjectDTO){

        object.setFarm(cadObjectDTO.getFarm());
        object.setCadNumber(cadObjectDTO.getCadNumber());
        object.setTypeOwn(cadObjectDTO.getTypeOwn());
        object.setTenancy(cadObjectDTO.getTenancy());
        object.setArea(cadObjectDTO.getArea());
        object.setCadCost(cadObjectDTO.getCadCost());
        object.setAgroArea(cadObjectDTO.getAgroArea());
        object.setShareOwn(cadObjectDTO.getShareOwn());
        object.setShareRent(cadObjectDTO.getShareRent());
        object.setFarmRent(cadObjectDTO.getFarmRent());
        object.setAfOwnerArea(cadObjectDTO.getAfOwnerArea());
        object.setNotCultivated(cadObjectDTO.getNotCultivated());
        object.setNotDocument(cadObjectDTO.getNotDocument());
        object.setCause(cadObjectDTO.getCause());
        object.setNote1(cadObjectDTO.getNote1());

        return object;
    }
}
