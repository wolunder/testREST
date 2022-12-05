package com.example.testrest.model.xmlmodel;

import java.util.ArrayList;
import java.util.List;

public class RegRecordEncumbrance {
    private String regNumberEncumbrance;
    private String typeEncumbrance;
    private String duration;
    private String encumbranceOwner;
    private String docFound;
    private List<String> cadRegRecordList = new ArrayList<>();

    public List<String> getCadRegRecordList() {
        return cadRegRecordList;
    }

    public void setCadRegRecordList(List<String> cadRegRecordList) {
        this.cadRegRecordList = cadRegRecordList;
    }

    public String getRegNumberEncumbrance() {
        return regNumberEncumbrance;
    }

    public void setRegNumberEncumbrance(String regNumberEncumbrance) {
        this.regNumberEncumbrance = regNumberEncumbrance;
    }

    public String getTypeEncumbrance() {
        return typeEncumbrance;
    }

    public void setTypeEncumbrance(String typeEncumbrance) {
        this.typeEncumbrance = typeEncumbrance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEncumbranceOwner() {
        return encumbranceOwner;
    }

    public void setEncumbranceOwner(String encumbranceOwner) {
        this.encumbranceOwner = encumbranceOwner;
    }

    public String getDocFound() {
        return docFound;
    }

    public void setDocFound(String docFound) {
        this.docFound = docFound;
    }

    @Override
    public String toString() {
        return "RegRecordEncumbrance{" +
                "regNumberEncumbrance='" + regNumberEncumbrance + '\'' +
                ", typeEncumbrance='" + typeEncumbrance + '\'' +
                ", duration='" + duration + '\'' +
                ", encumbranceOwner='" + encumbranceOwner + '\'' +
                ", docFound='" + docFound + '\'' +
                ", cadNumList=" + cadRegRecordList.size() +
                '}';
    }
}
