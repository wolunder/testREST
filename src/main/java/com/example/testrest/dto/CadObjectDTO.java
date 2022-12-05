package com.example.testrest.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CadObjectDTO {

    private Long id;

    private String farm;

    private String cadNumber;

    private String typeOwn;

    private LocalDate tenancy;

    private double area;

    private double cadCost;

    private double agroArea;

    private double shareOwn;

    private double shareRent;

    private double farmRent;

    private double afOwnerArea;

    private double notCultivated;

    private double notDocument;

    private String cause;

    private String note1;

    private boolean archiveStatus;

    private LocalDateTime createDate;
}
