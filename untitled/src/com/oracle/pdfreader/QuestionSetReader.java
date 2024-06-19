package com.oracle.pdfreader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.sql.*;
import java.io.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionSetReader {
    // JDBC URL, username and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "prabhat";

    public static void main(String[] args) {

        File pdfFile = new File("C://genai//questions.pdf");

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfText = stripper.getText(document);

            String regex =
                    "Subject:\\s*(?<subject>.+?)\\s*" +
                            "Chapter:\\s*(?<chapter>.+?)\\s*" +
                            "Question:\\s*(?<question>.+?)\\s*" +
                            "\\s*Answers:\\s*" +
                            "Option\\s+a\\)\\s*(?<optionA>.+?)\\s*" +
                            "Option\\s+b\\)\\s*(?<optionB>.+?)\\s*" +
                            "Option\\s+c\\)\\s*(?<optionC>.+?)\\s*";

            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
            Matcher matcher = pattern.matcher(pdfText);
            Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO questions(Subject, Chapter, Question,Answers) VALUES (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            while (matcher.find()) {
                String subject = matcher.group("subject");
                String chapter = matcher.group("chapter");
                String question = matcher.group("question");
                String optionA = matcher.group("optionA");
                String optionB = matcher.group("optionB");
                String optionC = matcher.group("optionC");

                String ans = optionA + " " + optionB + " " + optionC;

                pstmt.setString(1,subject);
                pstmt.setString(2,chapter);
                pstmt.setString(3,question);
                pstmt.setString(4,ans);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("A new row has been inserted.");
                } else {
                    System.out.println("Failed to insert a new row.");
                }

//                System.out.println("Subject: " + subject.trim());
//                System.out.println("Chapter: " + chapter.trim());
//                System.out.println("Question: " + question.trim());
//                System.out.println("Answers:");
//                System.out.println("Option a) " + optionA.trim());
//                System.out.println("Option b) " + optionB.trim());
//                System.out.println("Option c) " + optionC.trim());
//                System.out.println();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}

