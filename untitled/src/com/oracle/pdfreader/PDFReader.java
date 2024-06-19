package com.oracle.pdfreader;

import java.io.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader {
    public static void main(String[] args) throws Exception, IOException {
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
                writer.write(str);
                writer.newLine();
            }
            System.out.println("ArrayList written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
