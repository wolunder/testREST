package com.example.testrest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    //уникальный и нельзя изменить
    @Column(unique = true, updatable = false)
    private String username;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "text")
    //увеличиваем объем хранения стринга больше 50
    private String bio;

    @Column(length = 3000)
    private String password;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createDate;
}
