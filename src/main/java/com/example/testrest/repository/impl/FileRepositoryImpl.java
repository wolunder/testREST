package com.example.testrest.repository.impl;

import com.example.testrest.model.xmlmodel.RegRecordOwner;
import com.example.testrest.parser.ParserXml;
import com.example.testrest.parser.ParserXmlV2;
import com.example.testrest.repository.FileRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class FileRepositoryImpl implements FileRepository {
    public static final Logger LOG = LoggerFactory.getLogger(FileRepositoryImpl.class);

    private final ParserXml parserXml = new ParserXml();
    private final ParserXmlV2 parserXmlV2 = new ParserXmlV2();

    public List<RegRecordOwner> addOwnerToBaseV1(File fileXML) throws ParserConfigurationException, IOException, SAXException {
        List<RegRecordOwner> listOwner = parserXml.parseXMLtoList(fileXML);
        return listOwner;
    }


    public List<RegRecordOwner> addOwnerToBaseV2(File fileXML) throws ParserConfigurationException, IOException, SAXException {
        List<RegRecordOwner> listOwner = parserXmlV2.parseXml_V2(fileXML);
        return listOwner;
    }



}
