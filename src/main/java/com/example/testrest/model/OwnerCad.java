package com.example.testrest.model;


import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
public class OwnerCad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String cadNumber;

    private String numberReg;

    private String owner;

    private String regRecord;

    private String share;

    private double shareNumerator;

    private double shareDenominator;

    private double calculatedShare;
    @Column(columnDefinition = "text")
    private String regNumberEncumbrance;
    @Column(columnDefinition = "text")
    private String typeEncumbrance;
    @Column(columnDefinition = "text")
    private String duration;
    @Column(columnDefinition = "text")
    private String encumbranceOwner;
    @Column(columnDefinition = "text")
    private String docFound;
    @Column(columnDefinition = "text")
    private boolean archiveStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cad_object_id")
    private CadObject cadObject;

}
