package Model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class Fine {

    private double price = 10.0;

    public void getFine(String ID, String reg) throws SQLException,ExceptionFine {
        ResultSet resultSet;
        long totalDays = 0;
        String retd = null;
        Connection conn = DriverManager.getConnection("jdbc:sqlite:Issued Books.db");
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM IssuedBooks");
        resultSet = statement.getResultSet();
        while (resultSet.next()){
            if (resultSet.getString("r").equals(reg) && resultSet.getString("i").equals(ID)){
                retd = resultSet.getString("retd");
                break;
            }
        }

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat1.parse(retd);
            Date date2 = dateFormat2.parse(java.time.LocalDate.now().toString());
            long diff = date2.getTime() - date1.getTime();
            totalDays = diff/(1000*60*60*24);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        if (totalDays<0){
            totalDays = 0;
        }
        resultSet.close();
        statement.close();
        conn.close();
        double fine = totalDays*price;

        if (fine > 0){
            throw new ExceptionFine(fine);
        }

 }

 public void updateFine(String reg) throws SQLException, ExceptionFine{
     ResultSet resultSet;
     long totalDays = 0;
     String retd;
     Connection conn = DriverManager.getConnection("jdbc:sqlite:Issued Books.db");
     Statement statement = conn.createStatement();
     statement.execute("SELECT * FROM IssuedBooks");
     resultSet = statement.getResultSet();

     SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
     SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

     Date date2 = null;
     try {
         date2 = dateFormat2.parse(LocalDate.now().toString());
     } catch (ParseException e) {
         System.out.println(e.getMessage());
     }

     int expireBooks = 0;
     while (resultSet.next()){
         if (resultSet.getString("r").equals(reg)){
           retd = resultSet.getString("retd");
             try {
                 Date date1 = dateFormat1.parse(retd);
                 long diff = date2.getTime() - date1.getTime();
                 long days = diff/(1000*60*60*24);

                 if (days<=0){
                     days = 0;
                 }
                 else {
                     expireBooks++;
                 }
                 totalDays += days;
             }
             catch (ParseException e) {
                 System.out.println(e.getMessage());
             }

         }
     }

     resultSet.close();
     statement.close();
     conn.close();

     StudentsDatabase stdDB = new StudentsDatabase();
     double fine = totalDays*price;
     StringTokenizer tokenizer = new StringTokenizer(stdDB.getReturnBookFine(reg),",");

     while (tokenizer.hasMoreTokens()){
         fine += Double.parseDouble(tokenizer.nextToken());
     }

     stdDB.updateFine(reg,fine);

        if (fine > 0){
            ExceptionFine exceptionFine = new ExceptionFine(fine);
            exceptionFine.setExpiredBooks(expireBooks);
            throw exceptionFine;
        }

    }



    public void updateFineForView(String reg) throws SQLException{
        ResultSet resultSet;
        long totalDays = 0;
        String retd;
        Connection conn = DriverManager.getConnection("jdbc:sqlite:Issued Books.db");
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM IssuedBooks");
        resultSet = statement.getResultSet();

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

        Date date2 = null;
        try {
            date2 = dateFormat2.parse(LocalDate.now().toString());
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        while (resultSet.next()){
            if (resultSet.getString("r").equals(reg)){
                retd = resultSet.getString("retd");
                try {
                    Date date1 = dateFormat1.parse(retd);
                    long diff = date2.getTime() - date1.getTime();
                    long days = diff/(1000*60*60*24);

                    if (days<0){
                        days = 0;
                    }
                    totalDays += days;
                }
                catch (ParseException e) {
                    System.out.println(e.getMessage());
                }

            }
        }

        resultSet.close();
        statement.close();
        conn.close();

        StudentsDatabase stdDB = new StudentsDatabase();
        double fine = totalDays*price;
        StringTokenizer tokenizer = new StringTokenizer(stdDB.getReturnBookFine(reg),",");

        while (tokenizer.hasMoreTokens()){
            fine += Double.parseDouble(tokenizer.nextToken());
        }

        stdDB.updateFine(reg,fine);

    }



}
