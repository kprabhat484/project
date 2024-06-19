package com.connect.db;
import java.sql.*;

public class MySQLConnect {

    public static void main(String[] args) {

        try {


            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila","root","prabhat");

            Statement myStmt = myConn.createStatement();

            ResultSet myRS = myStmt.executeQuery("select * from actor");
            while(myRS.next()){
                System.out.println(myRS.getString("last_name"));
            }

            myConn.close();

        }catch(Exception e){
               e.printStackTrace();;
        }

    }
}
