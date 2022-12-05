package com.example.testrest.controller;



import com.example.testrest.model.CadObject;
import com.example.testrest.model.Farm;
import com.example.testrest.model.Users;
import com.example.testrest.service.CadObjectService;
import com.example.testrest.service.FarmService;
import com.example.testrest.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private UsersService usersService;
    private CadObjectService cadObjectService;
    private FarmService farmService;

    @Autowired
    public TestController(UsersService usersService, CadObjectService cadObjectService, FarmService farmService) {
        this.usersService = usersService;
        this.cadObjectService = cadObjectService;
        this.farmService = farmService;
    }

    @PostMapping("/add-users")
    public ResponseEntity<Users> addUsers(@RequestBody Users user){

        usersService.addUsers(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable("userId") String userId){

        Optional<Users> responseUser = usersService.getUsersId(Long.parseLong(userId));
        if(responseUser.isPresent()){
            Users user = responseUser.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @PostMapping("/add-farm")
    public ResponseEntity<Farm> addFarm(@RequestBody Farm farm){

        farmService.addFarm(farm);

        return new ResponseEntity<>(farm, HttpStatus.CREATED);
    }

    @GetMapping("/all-farm")
    public ResponseEntity<List<Farm>> getAllFarm(){

        List<Farm> farmList = farmService.getAllFarmList();

        return new ResponseEntity<>(farmList, HttpStatus.OK);
    }
}
