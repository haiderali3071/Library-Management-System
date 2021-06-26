package Model;

import java.sql.*;

public class PasswordDatabase {

    private String URL = "jdbc:sqlite:Password.db";

    public PasswordDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Password(pass TEXT, SQAns TEXT)");
        statement.close();
        conn.close();
    }

    public void createPassword(String pass, String SQAns) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("INSERT INTO Password VALUES ('"+pass+"', '"+SQAns+"' )" );
        statement.close();
        conn.close();
    }

    public void changePassword(String oldPass, String newPass) throws SQLException{

        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute(" UPDATE Password SET pass = '" + newPass + "' WHERE pass = '"+oldPass +"' " );
        statement.close();
        conn.close();
    }

    public void changePassword(String SQAns, String newPass, String extra) throws SQLException{

        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute(" UPDATE Password SET pass = '" + newPass + "' WHERE SQAns = '"+SQAns +"' " );
        statement.close();
        conn.close();
    }


    public String getPassword() throws SQLException{
        String pass = null;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Password ");
        ResultSet resultSet = statement.getResultSet();
        if (resultSet.next()) {
            pass = resultSet.getString("pass");
        }
        resultSet.close();
        statement.close();
        conn.close();
        return pass;
    }

    public String getSecurityAnswer() throws SQLException{
        String SQAns = null;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Password ");
        ResultSet resultSet = statement.getResultSet();
        if (resultSet.next()) {
            SQAns = resultSet.getString("SQAns");
        }

        resultSet.close();
        statement.close();
        conn.close();
        return SQAns;
    }

    public void deletePassword(String pass) throws SQLException{
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("DELETE FROM Password WHERE pass = '" + pass + "' ");
        statement.close();
        conn.close();
    }

    public void removePassword(String ans) throws SQLException{
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("DELETE FROM Password WHERE SQAns = '" + ans + "' ");
        statement.close();
        conn.close();
    }
}
