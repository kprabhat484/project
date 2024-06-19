package com.oracle.pdfreader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class ReadFromDB {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "prabhat";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

       // try{

            String chapterName = scanner.nextLine();
            String outputFilePath = "C://genai//output.txt";
            String query = "SELECT * FROM questions WHERE Chapter = ?";

            try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(query);
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {





            pstmt.setString(1, chapterName);

            ResultSet rs = pstmt.executeQuery();
            // Write the results to the file
            while(rs.next()){

                String question = rs.getString("Question");
                System.out.println(question);
                writer.write(question + "");
                writer.newLine();

            }
            System.out.println("Data has been written to " + outputFilePath);

        }catch(SQLException | IOException e){
            e.printStackTrace();
        } finally{
            scanner.close();
        }

    }
}
