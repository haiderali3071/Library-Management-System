package Model;

import java.sql.*;

public class IssueBooksDatabase {

    private String URL1 = "jdbc:sqlite:Students.db";
    private String URL2 = "jdbc:sqlite:Issued Books.db";

    public IssueBooksDatabase() throws SQLException{
        Connection conn = DriverManager.getConnection(URL2);
        Statement statement = conn.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS IssuedBooks(r TEXT, i TEXT, isd TEXT, retd TEXT)");
        statement.close();
        conn.close();
    }

    public void issueBook(String id, String reg, String isd, String retd) throws SQLException {
           int quantity;
           BooksDatabase booksDatabase = new BooksDatabase();
           quantity = booksDatabase.getQuantityByID(id);
           quantity--;
           booksDatabase.updateBookQuantity(quantity,id);

           Connection conn = DriverManager.getConnection(URL2);
           Statement statement = conn.createStatement();
           statement.execute("INSERT INTO IssuedBooks VALUES('"+reg+"', '"+id+"', '"+isd+"', '"+retd+"' )");
           statement.close();
           conn.close();
    }

    public boolean isAlreadyIssued(String reg, String id) throws SQLException {

        boolean isFound = false;
        Connection conn = DriverManager.getConnection(URL2);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM IssuedBooks ");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            if (resultSet.getString("r").compareTo(reg) == 0) {
                if (resultSet.getString("i").equals(id)) {
                    isFound = true;
                }
            }
        }
        statement.close();
        conn.close();
        return isFound;
    }


    public ResultSet getListOfIssuedBooks() throws SQLException{
        ResultSet resultSet;
        Connection conn = DriverManager.getConnection(URL2);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM IssuedBooks");
        return statement.getResultSet();
    }

    public void deleteIssuedBook(String ID) throws SQLException {
        Connection conn = DriverManager.getConnection(URL2);
        Statement statement = conn.createStatement();
        statement.execute("DELETE FROM IssuedBooks WHERE i = '" + ID + "' ");
        statement.close();
        conn.close();
    }

    public void returnIssuedBook(String ID, String reg) throws SQLException {

        Connection conn = DriverManager.getConnection(URL2);
        Statement statement = conn.createStatement();
        statement.execute("DELETE FROM IssuedBooks WHERE i = '" + ID + "' AND r = '" + reg + "' ");
        BooksDatabase booksDatabase = new BooksDatabase();
        int quan = booksDatabase.getQuantityByID(ID);
        quan++;
        booksDatabase.updateBookQuantity(quan,ID);
        statement.close();
        conn.close();
    }


}
