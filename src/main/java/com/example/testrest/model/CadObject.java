package com.example.testrest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class CadObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String farm;

    @Column(nullable = false, unique = true)
    private String cadNumber;

    private String typeOwn;

    //    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate tenancy;

    @Column(nullable = false)
    private double area;

    @PositiveOrZero
    private double cadCost;

    @PositiveOrZero
    private double agroArea;

    @PositiveOrZero
    private double shareOwn;

    @PositiveOrZero
    private double shareRent;

    @PositiveOrZero
    private double farmRent;

    @PositiveOrZero
    private double afOwnerArea;

    @PositiveOrZero
    private double notCultivated;

    @PositiveOrZero
    private double notDocument;

    @Column(columnDefinition = "text")
    private String cause;

    @Column(columnDefinition = "text")
    private String note1;

    private boolean archiveStatus;

    //    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createDate;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cadObject", orphanRemoval = true)
    @JsonIgnore
    private List<OwnerCad> listOwner = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cadObject", orphanRemoval = true)
    @JsonIgnore
    private List<FileCad> listFile = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
        this.tenancy = LocalDate.now();
    }
}
