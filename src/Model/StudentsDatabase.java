package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class StudentsDatabase {

    private String URL = "jdbc:sqlite:Students.db";

    public StudentsDatabase() throws SQLException {
        Connection conn1 = DriverManager.getConnection(URL);
        Statement statement1 = conn1.createStatement();
        statement1.execute("CREATE TABLE IF NOT EXISTS Students(r TEXT, n TEXT, d TEXT, f REAL, rb TEXT)");
        statement1.close();
        conn1.close();
    }

    public void addStudent(String name, String reg) throws SQLException, ExceptionDuplicate {
        if (isStdExist(reg)) {
            throw new ExceptionDuplicate();
        }
        StringTokenizer stringTokenizer = new StringTokenizer(java.time.LocalDate.now().toString(),"-");
        int year = Integer.parseInt(stringTokenizer.nextToken());
        int mon = Integer.parseInt(stringTokenizer.nextToken());
        int day = Integer.parseInt(stringTokenizer.nextToken());
        String date = day+"-"+mon+"-"+year;
        Connection conn1 = DriverManager.getConnection(URL);
        Statement statement1 = conn1.createStatement();
        statement1.execute("INSERT INTO Students VALUES('"+reg+"', '"+name+"' ,  '"+date+"' , 0, 0)");
        statement1.close();
        conn1.close();
    }

    public boolean isStdExist(String reg) throws SQLException {

        boolean isFound = false;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Students");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            if (resultSet.getString("r").compareTo(reg) == 0) {
                isFound = true;
            }
        }
        statement.close();
        conn.close();
        return isFound;
    }

    public ResultSet getListOfStudents() throws SQLException{
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Students");
        return statement.getResultSet();
    }


    public String getStdNameByReg(String reg) throws SQLException{
        String std = null;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Students");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()){
            if (resultSet.getString("r").equals(reg)){
                std = resultSet.getString("n");
                break;
            }
        }
        resultSet.close();
        if (std == null){
            throw new NullPointerException();
        }
        else {
            statement.close();
            conn.close();
            return std;
        }
    }

    public double getFineByReg(String reg) throws SQLException {
        double fine = 0;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Students ");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            if (resultSet.getString("r").compareTo(reg) == 0) {
                fine = resultSet.getDouble("f");
                break;
            }
        }
        resultSet.close();
        statement.close();;
        conn.close();
        return fine;
    }

    public void updateFine(String reg, double fine) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("UPDATE Students SET f = '" + fine + "' WHERE r = '"+reg +"' ");
        statement.close();
        conn.close();
    }

    public String getReturnBookFine(String reg) throws SQLException {
        String check = null;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Students");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            if (resultSet.getString("r").compareTo(reg) == 0) {
                check = resultSet.getString("rb");
                break;
            }
        }
        resultSet.close();
        statement.close();;
        conn.close();
        return check;
    }

    public void setReturnBookFine(String reg, String s) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("UPDATE Students SET rb = '" + s + "' WHERE r = '"+reg +"' ");
        statement.close();
        conn.close();
    }

    public void updateFineOfAllStudents() throws SQLException{
        ArrayList<String> reg =  new ArrayList<String>();
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Students ");
        ResultSet resultSet = statement.getResultSet();
        int i =0;
        while (resultSet.next()) {
             reg.add(i,resultSet.getString("r"));
        }
        resultSet.close();
        statement.close();;
        conn.close();
        Fine fine = new Fine();
        for (String r:reg){
            fine.updateFineForView(r);
        }
    }
}
