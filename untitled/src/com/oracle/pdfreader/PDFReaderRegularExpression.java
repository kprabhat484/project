package com.oracle.pdfreader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFReaderRegularExpression {

    public static void main(String[] args) throws IOException {
        Properties prop = readPropertiesFile("C://genai//project//untitled//src//com//oracle//pdfreader//config.properties");
        System.out.println("username: "+ prop.getProperty("regex"));

        String REGEX_PATTERN = prop.getProperty("regex");
        Pattern r = Pattern.compile(REGEX_PATTERN);

        File dir = new File("C://genai//content");

        File file = new File("C://genai//content//Python_Go_Java Project Requirements.pdf");
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        int noPages = document.getNumberOfPages();
        pdfStripper.setStartPage(1);
        pdfStripper.setEndPage(noPages);

        //load all lines into a string
        String pages = pdfStripper.getText(document);

        //split by detecting newline
        String[] lines = pages.split("\r\n|\r|\n");

//        int count = 1;   //Just to indicate line number
//        for (String temp : lines) {
//            System.out.println(count + " " + temp);
//            count++;
//        }


        String filePath = "C://genai//content//output.txt";

        // Convert ArrayList to the text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
        {
            for (String str : lines) {
                Matcher m = r.matcher(str);
                if (m.find()) {
                    writer.write(str);
                    writer.newLine();
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }
}
