package com.example.testrest.service;

import com.example.testrest.model.CadObject;
import com.example.testrest.model.OwnerCad;
import com.example.testrest.model.xmlmodel.InterpretationRecords;
import com.example.testrest.model.xmlmodel.RegRecordEncumbrance;
import com.example.testrest.model.xmlmodel.RegRecordOwner;

import com.example.testrest.repository.impl.FileRepositoryImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class FileService {

    private static final Logger LOG = Logger.getLogger(FileService.class.getName());
    private final FileRepositoryImpl fileRepository;

    @Autowired
    public FileService(FileRepositoryImpl fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<RegRecordOwner> addOwnerToList(File fileXml, boolean v1orV2) throws ParserConfigurationException, IOException, SAXException {
        List<RegRecordOwner> listOwner = null;

        if (v1orV2 == false){
            listOwner = fileRepository.addOwnerToBaseV1(fileXml);
        }
        else {
            listOwner = fileRepository.addOwnerToBaseV2(fileXml);
        }
        return listOwner;
    }

    public List<OwnerCad> regOwnerToOwnerCadList(List<RegRecordOwner> listRegOwner, CadObject cadObject, String area){
        List<OwnerCad> listOwner = new ArrayList<>();
        if (listRegOwner.size() > 0){
            OwnerCad owner = null;
            for (int i = 0; i < listRegOwner.size(); i++) {
                owner = regOwnerToOwnerCad(listRegOwner.get(i), cadObject, area);
                listOwner.add(owner);
            }
        }
        return listOwner;
    }

    private OwnerCad regOwnerToOwnerCad(RegRecordOwner regOwner, CadObject cadObject, String area){
        OwnerCad ownerCad = new OwnerCad();

        ownerCad.setCadNumber(cadObject.getCadNumber());
        ownerCad.setNumberReg(regOwner.getNumberReg());
        ownerCad.setOwner(regOwner.getOwner());
        ownerCad.setRegRecord(regOwner.getRegRecord());
        ownerCad.setShare(regOwner.getShare());
        ownerCad.setShareNumerator(regOwner.getShareNumerator());
        ownerCad.setShareDenominator(regOwner.getShareDenominator());

        if (regOwner.getShare() != null && regOwner.getShareDenominator() !=0 &&  regOwner.getShare().contains("/")){
            ownerCad.setCalculatedShare(InterpretationRecords.calculationNumerator(regOwner.getShare(),area));
        }else {
            ownerCad.setCalculatedShare(0);
        }

        StringBuilder strRegNumberEncumbrance = new StringBuilder("");
        StringBuilder strTypeEncumbrance = new StringBuilder("");
        StringBuilder strDuration = new StringBuilder("");
        StringBuilder strEncumbranceOwner = new StringBuilder("");
        StringBuilder strDocFound = new StringBuilder("");

        SetContainer container = new SetContainer();
        for (RegRecordEncumbrance re: regOwner.getRegRecordEncumbranceList()) {

            container.regNumberEncumbranceSet.add("- "+ re.getRegNumberEncumbrance()+". \n");
            container.typeEncumbranceSet.add("- "+ re.getTypeEncumbrance()+". \n");
            container.durationSet.add("- "+ re.getDuration()+". \n");
            container.encumbranceOwnerSet.add("- "+ re.getEncumbranceOwner()+". \n");
            container.docFoundSet.add("- "+ re.getDocFound()+". \n");
        }

        ownerCad.setRegNumberEncumbrance(container.setStringToOwnerCad(container.getRegNumberEncumbranceSet(),strRegNumberEncumbrance));
        ownerCad.setTypeEncumbrance(container.setStringToOwnerCad(container.getTypeEncumbranceSet(), strTypeEncumbrance));
        ownerCad.setDuration(container.setStringToOwnerCad(container.getDurationSet(), strDuration));
        ownerCad.setEncumbranceOwner(container.setStringToOwnerCad(container.getEncumbranceOwnerSet(), strEncumbranceOwner));
        ownerCad.setDocFound(container.setStringToOwnerCad(container.getDocFoundSet(), strDocFound));

        ownerCad.setArchiveStatus(false);
        ownerCad.setCadObject(cadObject);

        return ownerCad;
    }

    @Data
    private class SetContainer  {

        private LinkedHashSet<String> regNumberEncumbranceSet = new LinkedHashSet<>();
        private LinkedHashSet<String> typeEncumbranceSet = new LinkedHashSet<>();
        private LinkedHashSet<String> durationSet = new LinkedHashSet<>();
        private LinkedHashSet<String> encumbranceOwnerSet = new LinkedHashSet<>();
        private LinkedHashSet<String> docFoundSet = new LinkedHashSet<>();

        private String setStringToOwnerCad(LinkedHashSet<String> container, StringBuilder stringBuilder){
            Iterator<String> iterator  = container.iterator();
            while (iterator.hasNext()){
                stringBuilder.append(iterator.next());
            }
            return stringBuilder.toString();
        }
    }
}
