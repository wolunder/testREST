package com.example.testrest.service;


import com.example.testrest.model.Users;
import com.example.testrest.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    public static final Logger LOG = LoggerFactory.getLogger(UsersService.class);

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users addUsers(Users user){

        LOG.info(user.getUsername() + " создан и добавлен в Db.");
        return usersRepository.save(user);
    }

    public Optional<Users> getUsersId(Long id){
        return usersRepository.findById(id);
    }
}
