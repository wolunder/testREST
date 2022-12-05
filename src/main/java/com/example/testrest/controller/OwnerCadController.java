package com.example.testrest.controller;
import com.example.testrest.model.CadObject;
import com.example.testrest.model.OwnerCad;
import com.example.testrest.service.CadObjectService;
import com.example.testrest.service.OwnerCadService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/owner")
public class OwnerCadController {
    private String API_OWNER = "/owner";

    public static final Logger LOG = LoggerFactory.getLogger(CadController.class);

    private OwnerCadService ownerCadService;
    private CadObjectService cadObjectService;

    @Autowired
    public OwnerCadController(OwnerCadService ownerCadService, CadObjectService cadObjectService) {
        this.ownerCadService = ownerCadService;
        this.cadObjectService = cadObjectService;
    }

    @PostMapping("/cad/{idCadNum}/add-owner")
    public ResponseEntity<OwnerCad> addOwner(@PathVariable ("idCadNum") String idNumCad, @RequestBody OwnerCad ownerCad){

        Optional<CadObject> findObject = cadObjectService.findObjectById(Long.parseLong(idNumCad));
        if(findObject.isPresent()) {
            CadObject cadObject = findObject.get();
            ownerCad.setCadNumber(cadObject.getCadNumber());
            ownerCadService.addOwner(ownerCad, cadObject);
            return new ResponseEntity<>(ownerCad, HttpStatus.CREATED);
        }

        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //сделать сущность передачи нескольких параметров
    @PostMapping("/cad/{idCadNum}/add-list-owner/upload")
    public ResponseEntity<?> addOwnerList(@PathVariable ("idCadNum") String idNumCad, @RequestParam("file") MultipartFile fileXml){

        Optional<CadObject> findObject = cadObjectService.findObjectById(Long.parseLong(idNumCad));
        if(findObject.isPresent()) {
            CadObject cadObject = findObject.get();
            try {
                File file = new File(fileXml.getOriginalFilename());
                FileUtils.copyInputStreamToFile(fileXml.getInputStream(), file);
                    ownerCadService.addOwnerToDB(file, true, cadObject, "7200");
            } catch (ParserConfigurationException e) {
                return new ResponseEntity<String>("Ошибка парсера",HttpStatus.BAD_REQUEST);
            } catch (IOException e) {
                return new ResponseEntity<String>("Ошибка ввода вывода",HttpStatus.BAD_REQUEST);
            } catch (SAXException e) {
                return new ResponseEntity<String>("Ошибка преобразования",HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<String>("Ошибка - " + e.getMessage(),HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<String>("File is converted!",HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
