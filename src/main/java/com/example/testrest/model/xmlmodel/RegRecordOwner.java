package com.example.testrest.model.xmlmodel;

import java.util.ArrayList;
import java.util.List;

public class RegRecordOwner {
    private String cadNumber;
    private String numberReg;
    private String owner;
    private String regRecord;
    private String share;
    private double shareNumerator;
    private double shareDenominator;
    List<RegRecordEncumbrance> regRecordEncumbranceList = new ArrayList<>();


    public String getNumberReg() {
        return numberReg;
    }

    public void setNumberReg(String numberReg) {
        this.numberReg = numberReg;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public String getRegRecord() {
        return regRecord;
    }

    public void setRegRecord(String regRecord) {
        this.regRecord = regRecord;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public double getShareNumerator() {
        return shareNumerator;
    }

    public void setShareNumerator(double shareNumerator) {
        this.shareNumerator = shareNumerator;
    }

    public double getShareDenominator() {
        return shareDenominator;
    }

    public void setShareDenominator(double shareDenominator) {
        this.shareDenominator = shareDenominator;
    }

    public List<RegRecordEncumbrance> getRegRecordEncumbranceList() {
        return regRecordEncumbranceList;
    }

    public void setRegRecordEncumbranceList(List<RegRecordEncumbrance> regRecordEncumbranceList) {
        this.regRecordEncumbranceList = regRecordEncumbranceList;
    }

    public String getCadNumber() {
        return cadNumber;
    }

    public void setCadNumber(String cadNumber) {
        this.cadNumber = cadNumber;
    }

    @Override
    public String toString() {
        return "RegRecordOwner{" +
                "cadNumber='" + cadNumber + '\'' +
                ", numberReg='" + numberReg + '\'' +
                ", owner='" + owner + '\'' +
                ", regRecord='" + regRecord + '\'' +
                ", share='" + share + '\'' +
                ", shareNumerator=" + shareNumerator +
                ", shareDenominator=" + shareDenominator +
                ", regRecordEncumbranceList=" + regRecordEncumbranceList +
                '}';
    }
}
