package com.example.testrest.controller;


import com.example.testrest.dto.CadObjectDTO;
import com.example.testrest.model.CadObject;
import com.example.testrest.model.OwnerCad;
import com.example.testrest.service.CadObjectService;
import com.example.testrest.service.OwnerCadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/cad")
public class CadController {
    public static final Logger LOG = LoggerFactory.getLogger(CadController.class);

    private OwnerCadService ownerCadService;

    private CadObjectService cadObjectService;
    @Autowired
    public CadController(CadObjectService cadObjectService, OwnerCadService ownerCadService) {
        this.cadObjectService = cadObjectService;
        this.ownerCadService = ownerCadService;
    }

    @PostMapping("/add-object")
    public ResponseEntity<CadObject> addObject(@RequestBody CadObjectDTO cadObjectDTO){
        Optional<CadObject> findObject = cadObjectService.findObjectByCadNumber(cadObjectDTO.getCadNumber());

        if(!findObject.isPresent()){
            CadObject cadObject = new CadObject();
            cadObject = cadObjectService.cadObjectDTOToCadObject(cadObject, cadObjectDTO);
            cadObjectService.addObject(cadObject);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }

       else {
            System.out.println(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getNumber/{idOrCadNum}")
    public ResponseEntity<CadObject> findObjectByCadNumber(@PathVariable ("idOrCadNum") String idOrCadNumber){
        String regex = "[0-9]";
        ResponseEntity<CadObject> response = null;
        if (idOrCadNumber.contains(":")) {
            Optional<CadObject> responseObject = cadObjectService.findObjectByCadNumber(idOrCadNumber);
            if (responseObject.isPresent()) {
                CadObject cadObject = responseObject.get();
                response =  new ResponseEntity<>(cadObject, HttpStatus.OK);
            }
            else response = new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return response;
        }
        else if(idOrCadNumber.matches(regex)){
            Optional<CadObject> responseObject = cadObjectService.findObjectById(Long.parseLong(idOrCadNumber));
            if(responseObject.isPresent()){
                CadObject cadObject = responseObject.get();
                response =  new ResponseEntity<>(cadObject, HttpStatus.OK);
            }
            else response = new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return response;
        }
        else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CadObject>> getAllCadObject(){

        List<CadObject> listObject = cadObjectService.getAllObject();

        return new ResponseEntity<>(listObject, HttpStatus.OK);
    }

    @PostMapping("/getNumber/{idOrNumCad}/update-cad")
    public ResponseEntity<CadObject> updateObject(@PathVariable ("idOrNumCad") String idOrNumCad, @RequestBody CadObjectDTO cadObjectDTO){
        LOG.info("rabotaet update");
        String regex = "[0-9]";
        ResponseEntity<CadObject> response = null;
        if(idOrNumCad.contains(":")){
            Optional<CadObject> findObject = cadObjectService.findObjectByCadNumber(idOrNumCad);
            if(findObject.isPresent()){
                CadObject updateObject = findObject.get();
                cadObjectService.addObject(cadObjectService.cadObjectDTOToCadObject(updateObject, cadObjectDTO));
                response = new ResponseEntity<>(HttpStatus.OK);

            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return response;
        }

        else if(idOrNumCad.matches(regex)) {

            Optional<CadObject> findObject = cadObjectService.findObjectById(Long.parseLong(idOrNumCad));
            if(findObject.isPresent()) {
                LOG.info("кадастровый id "+cadObjectDTO.getCadNumber());
                CadObject updateObject = findObject.get();
                cadObjectService.addObject(cadObjectService.cadObjectDTOToCadObject(updateObject, cadObjectDTO));
                response = new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return response;
        }
        else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        }
    }


    @PostMapping("/getNumber/{idCadNum}/add-owner")
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

    @GetMapping("/getNumber/{idCadNum}/get-all-owner")
    public ResponseEntity<List<OwnerCad>> getAllOwnerCadNum(@PathVariable ("idCadNum") String idCadNum){

        Optional<CadObject> findObject = cadObjectService.findObjectById(Long.parseLong(idCadNum));
        if(findObject.isPresent()) {
            List<OwnerCad> listOwner = ownerCadService.getAllOwnerCad(findObject.get().getCadNumber());
            return new ResponseEntity<>(listOwner, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    //загрузка документов
    @PostMapping("/files")
    public void addOwnerToCadBase(@RequestPart MultipartFile file){

    }

    @PostMapping("/getNumber/{id}/set-status")
    public ResponseEntity<String> setArchiveStatusToCadObject(@PathVariable ("id") String id){
      boolean setStatus =  cadObjectService.setArchiveStatusObj(Long.parseLong(id));
      if (setStatus == true){
          return new ResponseEntity<>("Объект удален!", HttpStatus.OK);
      }
      else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
