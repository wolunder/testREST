package com.example.testrest.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FileCad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cad_object_id")
    private CadObject cadObject;
}
