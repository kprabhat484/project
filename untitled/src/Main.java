import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args)throws Exception, IOException
    {
        System.out.println("Hello world!");

        File dir = new File("C://genai//content");
        showFiles(dir.listFiles());

    }
    public static void showFiles(File[] files)throws Exception, IOException
    {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getAbsolutePath());
                showFiles(file.listFiles()); // Calls same method again.
            } else {
                System.out.println("File: " + file.getAbsolutePath());

                if(file.getAbsolutePath().endsWith(".pdf")){
                    pdfReader(file.getAbsolutePath());
                }

            }
        }
    }

    public static void pdfReader(String filePath)throws Exception, IOException
    {


        File file = new File(filePath);
        String directory = file.getParent();
        System.out.println("directory name : " + directory);
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
          String outPutFilePathName = directory + "//output.txt";


        // Convert ArrayList to the text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outPutFilePathName)))
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