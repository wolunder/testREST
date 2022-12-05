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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

// 558264000000 - код док решение суда
// 558301120000 - код док запрет
// 558401010209 - код док аренда

// 022006000000 - код аренды по собственникам
// 022002000000 - код ареста по собственникам
// 022003000000 - код запрещения регистрации по собственникам
// 022099000000 - код Иные ограничения (обременения) прав по собственникам
// 022007000000 - код ипотека по собтвенникам
// 022008000000 - код ипотека в силу закона по собcтвенникам
public class ParserXmlV2 {
    private static final Logger LOGGER = Logger.getLogger(ParserXmlV2.class.getName());

    public List<RegRecordOwner> parseXml_V2(File fileXML) throws ParserConfigurationException, IOException, SAXException{
        LOGGER.info("загрузка файла- "+ fileXML.getName());
        List<RegRecordOwner> listOwner = new ArrayList<>();
        List<RegRecordEncumbrance> listRecord = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(fileXML);

        listRecord = parseRegRecordEncumbrance(document);
        listOwner = parseRegOwner(document);


        RegRecordEncumbrance regRecord = null;
        for (int i = 0; i < listRecord.size(); i++) {
            for (int j = 0; j < listRecord.get(i).getCadRegRecordList().size(); j++) {
                for (int k = 0; k < listOwner.size(); k++) {
                    if(listOwner.get(k).getRegRecord().contains(listRecord.get(i).getCadRegRecordList().get(j))){
                        regRecord = listRecord.get(i);
                        listOwner.get(k).getRegRecordEncumbranceList().add(regRecord);
                        regRecord = null;
                    }
                }
            }
        }
        LOGGER.info("Операция выполнена");
        return listOwner;
    }

    //собственники фильтрация
    private List<RegRecordOwner> parseRegOwner(Document document){
        NodeList listRightRecordOwner = document.getElementsByTagName("right_records");
        Node nodeExtractRightRecords = listRightRecordOwner.item(0);
        List<RegRecordOwner> listOwner = new ArrayList<>();
        Element elementExtractRightRecords = (Element) nodeExtractRightRecords;
        NodeList nodeExtractRightRecordsList = elementExtractRightRecords.getElementsByTagName("right_record");

        for (int i = 0; i < nodeExtractRightRecordsList.getLength(); i++) {
            Node ownerRegNode = nodeExtractRightRecordsList.item(i);
            RegRecordOwner owner = new RegRecordOwner();

            int count = i+1;
            owner.setNumberReg("п."+count);

            if (ownerRegNode.getNodeType() == Node.ELEMENT_NODE){
                Element elementOwner = (Element) ownerRegNode;
                NodeList listChildOwner = elementOwner.getChildNodes();
                List<String> regRecord = new ArrayList<>();

                for (int j = 0; j < listChildOwner.getLength(); j++) {
                    NodeList list = listChildOwner.item(j).getChildNodes();

                    for (int k = 0; k < list.getLength(); k++) {
                        String nodeName = list.item(k).getNodeName();

                        switch (nodeName){
                            case ("registration_date"):{
                                regRecord.add(list.item(k).getTextContent().split("T")[0]);
                                break;
                            }
                            case("right_type"):{
                                int index = list.item(k).getTextContent().lastIndexOf('0');
                                regRecord.add(list.item(k).getTextContent().substring(index+1).replace("\n","").trim()+", ");
                                break;
                            }
                            case("right_number"):{
                                regRecord.add(list.item(k).getTextContent()
                                        .replace(" ","")
                                        .replace('\n',Character.MIN_VALUE)
                                        .replace(Character.MIN_VALUE,' ').trim());
                                break;
                            }
                            case("shares"):{
                                String shares = list.item(k).getTextContent()
                                        .replace(" ","")
                                        .replace('\n',Character.MIN_VALUE)
                                        .replace(Character.MIN_VALUE,' ').trim().replace(' ','/');
                                regRecord.add(shares);

                                owner.setShare(regRecord.get(3));
                                String[] strShare = regRecord.get(3).split("/");

                                if(strShare.length>1) {
                                    owner.setShareNumerator(Double.parseDouble(strShare[0]));
                                    owner.setShareDenominator(Double.parseDouble(strShare[1]));
                                    owner.setRegRecord(regRecord.get(1)+"№ "+regRecord.get(2)+" от "+regRecord.get(0)+", "+regRecord.get(3));
                                }
                                else {
                                    Node nodeShare = list.item(k);
                                    Element elementSh = (Element) nodeShare;
                                    StringBuilder shareBuilder = new StringBuilder("");
                                    NodeList listShare = elementSh.getElementsByTagName("numerator");
                                    shareBuilder.append((listShare.item(0).getTextContent()) +"/");
                                    owner.setShareNumerator(Double.parseDouble(listShare.item(0).getTextContent()));
                                    listShare = elementSh.getElementsByTagName("denominator");
                                    owner.setShareDenominator(Double.parseDouble(listShare.item(0).getTextContent()));
                                    shareBuilder.append((listShare.item(0).getTextContent()));
                                    owner.setShare(shareBuilder.toString());
                                    owner.setRegRecord(regRecord.get(1)+"№ "+regRecord.get(2)+" от "+regRecord.get(0)+", "+shareBuilder.toString());
                                }
                                regRecord.clear();
                                break;
                            }
                            case ("right_holder"):{
                                String[] strHolders = list.item(k).getTextContent().split(" ");
                                if (strHolders.length >1) {
                                    StringBuilder stringBuilder = new StringBuilder("");
                                    Arrays.stream(strHolders).map(s -> s.replace("\n", "")).filter(s -> !s.isEmpty()).forEach(s -> stringBuilder.append(s + " "));
                                    String str = stringBuilder.toString();
                                    owner.setOwner(stringBuilder.toString());
                                }else {
                                    Node nodeShare = list.item(k);
                                    Element elementSh = (Element) nodeShare;
                                    StringBuilder nameBuilder = new StringBuilder("");
                                    NodeList listShare = elementSh.getElementsByTagName("surname");
                                    nameBuilder.append((listShare.item(0).getTextContent()) +" ");
                                    listShare = elementSh.getElementsByTagName("name");
                                    nameBuilder.append((listShare.item(0).getTextContent()) +" ");
                                    listShare = elementSh.getElementsByTagName("patronymic");
                                    nameBuilder.append((listShare.item(0).getTextContent()) +" ");
                                    owner.setOwner(nameBuilder.toString());
                                }


                                break;
                            }
                        }

                    }
                }
            }
            listOwner.add(owner);
        }
        LOGGER.info("Собственники извлечены");
        return listOwner;
    }

    //обременения фильтрация
    private List<RegRecordEncumbrance> parseRegRecordEncumbrance(Document document){
        NodeList listTest = document.getElementsByTagName("restrict_records");
        Node nodeRestrict = listTest.item(0);
        Element elementRestrict = (Element) nodeRestrict;
        NodeList restrictList = elementRestrict.getElementsByTagName("restrict_record");
        List<RegRecordEncumbrance> listRecordEncumbrance = new ArrayList<>();
        //аренда, аресты и прочее
        for (int i = 0; i < restrictList.getLength(); i++) {
            RegRecordEncumbrance recordDocument = new RegRecordEncumbrance();
            Node encumRecord = restrictList.item(i);
            if(encumRecord.getNodeType() == Node.ELEMENT_NODE){
                Element elementRecord = (Element) encumRecord;
                NodeList listChildRecord = elementRecord.getChildNodes();

                for (int j = 0; j < listChildRecord.getLength(); j++) {
                    String nodeName = listChildRecord.item(j).getNodeName();

                    if(nodeName.equals("restrictions_encumbrances_data")){
                        Node nodeRestrictRight = listChildRecord.item(j);
                        Element elementRestrictRight = (Element) nodeRestrictRight;
                        NodeList listRestrictRight = elementRestrictRight.getChildNodes();
                        for (int k = 0; k < listRestrictRight.getLength(); k++) {
                            String nameRestrict = listRestrictRight.item(k).getNodeName();

                            switch (nameRestrict){
                                //рег запись
                                case("restriction_encumbrance_number"):{
                                    recordDocument.setRegNumberEncumbrance(listRestrictRight.item(k).getTextContent().trim());
                                    break;
                                }
                                //тип и код
                                case("restriction_encumbrance_type"):{
                                    String[] arrStr = listRestrictRight.item(k).getTextContent().trim().split("\n");
                                    Stream<String> streamStrType =Arrays.stream(arrStr);
                                    arrStr = streamStrType.map(s -> s.trim()).toArray(String[]::new);
                                    if (arrStr.length >1) {
                                        recordDocument.setTypeEncumbrance(arrStr[0] + " " + arrStr[1]);
                                    }else {
                                        recordDocument.setTypeEncumbrance(arrStr[0]);
                                    }
                                    break;
                                }
                                //кад номера
                                case("restricting_rights"):{
                                    String[] strArr = listRestrictRight.item(k).getTextContent().trim().replace(" ","")
                                            .split("\n");
                                    if(strArr.length >1) {
                                        Stream<String> streamStr = Arrays.stream(strArr);
                                        streamStr.filter(s -> s.matches("\\d++\\S++")).filter(s -> !s.contains("egrppart"))
                                                            .distinct().forEach(s -> recordDocument.getCadRegRecordList().add(s));
                                    }else{
                                        Node nodeRestrictNumber = listRestrictRight.item(k);
                                        Element elementR = (Element) nodeRestrictNumber;
                                        NodeList listRestrict = elementR.getElementsByTagName("right_number");
                                        for (int l = 0; l < listRestrict.getLength(); l++) {
                                            recordDocument.getCadRegRecordList().add(listRestrict.item(l).getTextContent());
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    switch (nodeName){
                        case("right_holders"): {
                            String[] strArr = listChildRecord.item(j).getTextContent().split("\n");
                            Stream<String> stringStream = Arrays.stream(strArr);
                            strArr = stringStream.map(s -> s.trim()).filter(s -> !s.isEmpty()).toArray(String[]::new);
                                recordDocument.setEncumbranceOwner(strArr[0]);
                            break;
                        }
                        case("underlying_documents"): {
                            listChildRecord.item(j).getTextContent();
                            String[] arrString = listChildRecord.item(j).getTextContent().trim().split("\n");
                            StringBuilder strDoc = new StringBuilder("- ");
                            if (arrString.length >1) {
                                Stream<String> docStream = Arrays.stream(arrString);
                                docStream.map(s -> s.trim()).filter(s -> !s.isEmpty()).forEach(s -> strDoc.append(" " + s + " \n"));
                            }
                            else {
                                Node nodeRestrictNumber = listChildRecord.item(j);
                                Element elementR = (Element) nodeRestrictNumber;

                                NodeList listRestrict = elementR.getElementsByTagName("value");
                                strDoc.append(listRestrict.item(0).getTextContent()+" ");

                                listRestrict = elementR.getElementsByTagName("document_name");
                               strDoc.append(listRestrict.item(0).getTextContent()+" ");

                                listRestrict = elementR.getElementsByTagName("document_date");
                                strDoc.append(listRestrict.item(0).getTextContent()+" ");

                                listRestrict = elementR.getElementsByTagName("document_issuer");
                                if(listRestrict .getLength()>0) {
                                    strDoc.append(listRestrict.item(0).getTextContent() + " ");
                                }

                                listRestrict = elementR.getElementsByTagName("deal_registered_number");
                                if(listRestrict .getLength()>0) {
                                    strDoc.append(listRestrict.item(0).getTextContent());
                                }

                                listRestrict = elementR.getElementsByTagName("deal_registered_date");
                                if(listRestrict .getLength()>0) {
                                    strDoc.append(", от "+listRestrict.item(0).getTextContent().split("T")[0]);
                                }
                            }
                            recordDocument.setDocFound(strDoc.toString());
                            break;
                        }
                    }
                }
            }
            listRecordEncumbrance.add(recordDocument);
        }
        LOGGER.info("Обременения извлечены");
        return listRecordEncumbrance;
    }
}
