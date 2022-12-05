package com.example.testrest.facade;

import com.example.testrest.dto.CadObjectDTO;
import com.example.testrest.model.CadObject;

public class CadObjectFacade {

    public CadObjectDTO cadObjectToCadObjectDTO(CadObject cadObject){

        CadObjectDTO cadDTO = new CadObjectDTO();

        cadDTO.setId(cadObject.getId());
        cadDTO.setFarm(cadObject.getFarm());
        cadDTO.setCadNumber(cadObject.getCadNumber());
        cadDTO.setTypeOwn(cadObject.getTypeOwn());
        cadDTO.setTenancy(cadObject.getTenancy());
        cadDTO.setArea(cadObject.getArea());
        cadDTO.setCadCost(cadObject.getCadCost());
        cadDTO.setAgroArea(cadObject.getAgroArea());
        cadDTO.setShareOwn(cadObject.getShareOwn());
        cadDTO.setShareRent(cadObject.getShareRent());
        cadDTO.setFarmRent(cadObject.getFarmRent());
        cadDTO.setAfOwnerArea(cadObject.getAfOwnerArea());
        cadDTO.setNotCultivated(cadObject.getNotCultivated());
        cadDTO.setNotDocument(cadObject.getNotDocument());
        cadDTO.setCause(cadObject.getCause());
        cadDTO.setNote1(cadObject.getNote1());


        return cadDTO;
    }
}
