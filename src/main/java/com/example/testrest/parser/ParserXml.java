package com.example.testrest.parser;

import com.example.testrest.model.xmlmodel.RegRecordEncumbrance;
import com.example.testrest.model.xmlmodel.RegRecordOwner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserXml {

    public List<RegRecordOwner> parseXMLtoList(File fileXML) throws ParserConfigurationException, IOException, SAXException, IllegalArgumentException {
        List<RegRecordOwner> listRecord = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(fileXML);
        //******************************************************************************************

        NodeList listExtractObjectRight = document.getElementsByTagName("ExtractObjectRight");
        Node nodeExtractObjectRight = listExtractObjectRight.item(0);
        Element elementExtractObjectRight = (Element) nodeExtractObjectRight;

        NodeList nodeList = elementExtractObjectRight.getElementsByTagName("Right");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
//           System.out.println("\n Current Element - " + nNode.getNodeName() + " " + i);
            RegRecordOwner owner = new RegRecordOwner();
            RegRecordEncumbrance encumbrance = new RegRecordEncumbrance();
            int count = i+1;
            owner.setNumberReg("п. "+count);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                Node nodeNoOwner = element.getElementsByTagName("NoOwner").item(0);
                if(nodeNoOwner != null){
                    owner.setOwner(nodeNoOwner.getTextContent());
                }

                // правообладатель ***********************************************************************************
                Node nodeOwner = element.getElementsByTagName("Owner").item(0);
                Element elementOwner = (Element) nodeOwner;
                NodeList listOwnerChildNode = elementOwner.getChildNodes();

                for (int j = 0; j < listOwnerChildNode.getLength(); j++) {
                    Node childOwnerNode = listOwnerChildNode.item(j);
                    NodeList listChild = childOwnerNode.getChildNodes();

                    for (int k = 0; k < listChild.getLength(); k++) {
                        if (listOwnerChildNode.item(k) != null) {
                            Node childNode = listOwnerChildNode.item(k);
                            String nameOwner = childNode.getNodeName();
                            if (nameOwner.equals("Person") || nameOwner.equals("Organization")) {
                                if (owner.getOwner() == null) {
                                    String owner1 = childOwnerNode.getTextContent();
                                    String[] arrayOwner = owner1.split("\n");
                                    owner.setOwner(arrayOwner[2]);
                                }
                            }
                        }
                    }
                }

//          регистрация ***********************************************************************************************

                Node nodeNoRegistration = element.getElementsByTagName("NoRegistration").item(0);
                if(nodeNoRegistration != null){
                    owner.setRegRecord(nodeNoRegistration.getTextContent());
                }

                Node nodeRegistration = element.getElementsByTagName("Registration").item(0);
                if (nodeRegistration != null) {
                    Element elementRegistration = (Element) nodeRegistration;
                    String registration = elementRegistration.getNodeName();

                    NodeList listRegistrationNode = elementRegistration.getChildNodes();
                    for (int j = 0; j < listRegistrationNode.getLength(); j++) {
                        if (listRegistrationNode.item(j) != null && (owner.getRegRecord() ==null || owner.getShare() ==null)) {
                            if (listRegistrationNode.item(j).getNodeName().equals("Name")) {
                                owner.setRegRecord(listRegistrationNode.item(j).getTextContent());
                            }
                            //доля собственности
                            if (listRegistrationNode.item(j).getNodeName().equals("ShareText")) {
                                String text = listRegistrationNode.item(j).getTextContent().trim();
                                owner.setShare(text);

                                if(text.matches("\\d+\\S?\\d+")) {
                                    String[] share = listRegistrationNode.item(j).getTextContent().split("/");
                                    if (share.length > 1) {
                                        owner.setShareNumerator(Double.parseDouble(share[0]));
                                        owner.setShareDenominator(Double.parseDouble(share[1]));
                                    } else {
                                        owner.setShare(share[0]);
                                    }
                                }
                                else if(text.matches("\\d+\\S?\\d+.+")){
                                    String[] share =text.split(" ",2);
                                    owner.setShareNumerator(Double.parseDouble(share[0].replace(',','.')));
                                    owner.setShareDenominator(0.0);
                                }
                                else{
                                    owner.setShareNumerator(0.0);
                                    owner.setShareDenominator(0.0);
                                }
                            }
                        }
                    }
                }

                Node nodeNoEncumbrance= element.getElementsByTagName("NoEncumbrance").item(0);
                if(nodeNoEncumbrance != null){
                    encumbrance.setEncumbranceOwner(nodeNoEncumbrance.getTextContent());

                }

                //обременение ******************************************************************************************
                NodeList listEncumbranceChildNode = element.getElementsByTagName("Encumbrance");
                if (listEncumbranceChildNode!= null && (encumbrance.getEncumbranceOwner()==null
                        || encumbrance.getRegNumberEncumbrance() == null
                        ||encumbrance.getTypeEncumbrance() == null
                        || encumbrance.getDuration() == null
                        || encumbrance.getDocFound() == null)) {
                    for (int j = 0; j < listEncumbranceChildNode.getLength(); j++) {
                        Node nodeEncumbrance = listEncumbranceChildNode.item(j);
                        Element element1 = (Element) nodeEncumbrance;
                        NodeList listEncumbrance = nodeEncumbrance.getChildNodes();

                        for (int k = 0; k < listEncumbrance.getLength(); k++) {
                            if (listEncumbrance.item(k) != null) {

                                if (listEncumbrance.item(k).getNodeName().equals("Name")) {
                                    encumbrance.setTypeEncumbrance(listEncumbrance.item(k).getTextContent());
                                }

                                if (listEncumbrance.item(k).getNodeName().equals("RegNumber")) {
                                    encumbrance.setRegNumberEncumbrance(listEncumbrance.item(k).getTextContent());
                                }

                                if (listEncumbrance.item(k).getNodeName().equals("Duration")) {
                                    String duration = listEncumbrance.item(k).getTextContent().replaceAll("\n", " ");
                                    encumbrance.setDuration(duration);
                                }

                                if (listEncumbrance.item(k).getNodeName().equals("Owner")) {
                                    String owner1 = listEncumbrance.item(k).getTextContent();
                                    String[] splitOwner = owner1.split("\n");
                                    encumbrance.setEncumbranceOwner(listEncumbrance.item(k).getTextContent().split("\n")[5]);
                                }
                                if (listEncumbrance.item(k).getNodeName().equals("DocFound")) {
                                    encumbrance.setDocFound(listEncumbrance.item(k).getTextContent().split("\n")[2]);
                                }
                            }
                        }
                    }
                    owner.getRegRecordEncumbranceList().add(encumbrance);
                }
            }
            listRecord.add(owner);
        }
        return listRecord;
    }
}

