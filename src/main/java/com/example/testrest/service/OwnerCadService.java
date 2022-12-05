package com.example.testrest.service;


import com.example.testrest.dto.OwnerCadDTO;
import com.example.testrest.model.CadObject;
import com.example.testrest.model.OwnerCad;
import com.example.testrest.model.xmlmodel.RegRecordOwner;
import com.example.testrest.repository.FarmRepository;
import com.example.testrest.repository.OwnerCadRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerCadService {

    public static final Logger LOG = LoggerFactory.getLogger(OwnerCadService.class);
    private final OwnerCadRepository ownerCadRepository;
    private final FileService fileService;

    @Autowired
    public OwnerCadService(OwnerCadRepository ownerCadRepository, FileService fileService) {
        this.ownerCadRepository = ownerCadRepository;
        this.fileService = fileService;
    }

    public void addOwnerToDB(File fileXml, boolean v1orV2, CadObject cadObject, String area) throws ParserConfigurationException, IOException, SAXException {

      List<RegRecordOwner> listRegRecord = fileService.addOwnerToList(fileXml, v1orV2);
      List<OwnerCad> listOwner =  fileService.regOwnerToOwnerCadList(listRegRecord,cadObject, area);
        LOG.info(String.valueOf(listOwner.size()));
        for (int i = 0; i < listOwner.size(); i++) {
            ownerCadRepository.save(listOwner.get(i));
        }
        LOG.info(fileXml.getName() + " импортирован!");
    }

    public OwnerCad addOwner(OwnerCad ownerCad, CadObject cadObject){
        ownerCad.setCadObject(cadObject);

        LOG.info(ownerCad.getOwner() + " сохранен в "+ cadObject.getCadNumber()+".");
        return ownerCadRepository.save(ownerCad);
    }

   public Optional<OwnerCad> getOwner(Long id){

        return ownerCadRepository.findById(id);
   }

   public void deleteOwner(Long id){

        ownerCadRepository.deleteById(id);
        LOG.info("Объект удален.");

   }

   public OwnerCad updateOwner(Long id, OwnerCad ownerCad){
     OwnerCad findOwner =  ownerCadRepository.findById(id).get();
     findOwner = ownerCad;
     return ownerCadRepository.save(findOwner);
   }


   public List<OwnerCad> getAllOwnerCad(String cadNumber){
        List<OwnerCad> listOwner = ownerCadRepository.findOwnerCadByCadNumber(cadNumber);

       return listOwner;
   }

   public OwnerCad cadOwnerDTOtoCadOwner(OwnerCadDTO cadOwnerDTO){
        OwnerCad ownerCad = new OwnerCad();

        ownerCad.setCadNumber(cadOwnerDTO.getCadNumber());
        ownerCad.setNumberReg(cadOwnerDTO.getNumberReg());
        ownerCad.setOwner(cadOwnerDTO.getOwner());
        ownerCad.setRegRecord(cadOwnerDTO.getRegRecord());
        ownerCad.setShare(cadOwnerDTO.getShare());
        ownerCad.setShareNumerator(cadOwnerDTO.getShareNumerator());
        ownerCad.setShareDenominator(cadOwnerDTO.getShareDenominator());
        ownerCad.setRegNumberEncumbrance(cadOwnerDTO.getRegNumberEncumbrance());
        ownerCad.setTypeEncumbrance(cadOwnerDTO.getTypeEncumbrance());
        ownerCad.setDuration(cadOwnerDTO.getDuration());
        ownerCad.setEncumbranceOwner(cadOwnerDTO.getEncumbranceOwner());
        ownerCad.setDocFound(cadOwnerDTO.getDocFound());

        return ownerCad;
   }


}
