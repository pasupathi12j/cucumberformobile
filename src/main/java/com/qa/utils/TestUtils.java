package com.qa.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import static com.qa.engine.ProjectBase.logInfo;

public class TestUtils {
    public static final long WAIT = 10;

    public HashMap<String, String> parseStringXML(InputStream file) throws Exception {
        HashMap<String, String> stringMap = new HashMap<String, String>();
        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(file);

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Here comes the root node
        Element root = document.getDocumentElement();

        //Get all elements
        NodeList nList = document.getElementsByTagName("string");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                // Store each element key value in map
                stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
            }
        }
        return stringMap;
    }

    public String dateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void waitForElement(WebDriver driver, String xpath, long secondsToWait) {
        Instant timeout = Instant.now().plusSeconds(secondsToWait);

        while (driver.findElements(By.xpath(xpath)).size() == 0) {
            if (driver.findElements(By.xpath(xpath)).size() > 0)
                break;

            if (Instant.now().isAfter(timeout))
                throw new TimeoutException("Element '" + xpath + "' is not found in the App.");
        }
    }

    public void waitForElement(WebDriver driver, By xpath, long secondsToWait) {
        Instant timeout = Instant.now().plusSeconds(secondsToWait);

        while (driver.findElements(xpath).size() == 0) {
            if (driver.findElements(xpath).size() > 0)
                break;

            if (Instant.now().isAfter(timeout))
                throw new TimeoutException("Element '" + xpath + "' is not found in the App.");
        }
    }

    public String getRandomString(int lenght) {
        String AlphaNumericString = "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(lenght);

        for (int i = 0; i < lenght; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public String getRandomNumber(int lenght) {
        String numeric = "123456789";

        StringBuilder sb = new StringBuilder(lenght);

        for (int i = 0; i < lenght; i++) {
            int index = (int) (numeric.length() * Math.random());

            sb.append(numeric.charAt(index));
        }

        return sb.toString();
    }

    public String fetchPdfContent(String pdfUrl) throws IOException {
        PDDocument document = null;
        try {
            File file = new File(pdfUrl);
            document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            logInfo("pdf content is fetched -->" + text);
            return text;
        } finally {
            assert document != null;
            document.close();
        }
    }
}