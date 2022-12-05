package com.example.testrest.repository;

import com.example.testrest.model.OwnerCad;

import java.io.File;
import java.util.List;

public interface FileImportRepository {

    List<OwnerCad> addListOwnerXML_V1(File file);

    List<OwnerCad> addListOwnerXML_V2(File file);
}
