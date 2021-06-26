package Model;
import java.sql.*;

public class BooksDatabase {

    private String URL = "jdbc:sqlite:books.db";

    public BooksDatabase() throws SQLException{
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Books(cat TEXT, ID TEXT, name TEXT, auth TEXT, quan INTEGER, price REAL,rack INTEGER)");
        statement.close();
        conn.close();
    }
    public void addBook(String cat, String ID, String name, String auth, int quan, double price, int rack) throws SQLException, ExceptionDuplicate {

        if (search(ID)) {
            throw new ExceptionDuplicate();
        }
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("INSERT INTO Books(cat,ID,name,auth,quan,price,rack) VALUES('" + cat + "', '" + ID + "', '" + name + "', '" + auth + "', " + quan + ", " + price + ", " + rack + ")");
        statement.close();
        conn.close();
    }

    public void deleteBook(String ID) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("DELETE FROM Books WHERE ID = '" + ID + "' ");
        statement.close();
        conn.close();
    }

    public void updateBook(String cat, String ID, String name, String auth, int quan, double price, int rack) throws SQLException {
        updateBookCategory(cat, ID);
        updateBookName(name, ID);
        updateBookAuthor(auth, ID);
        updateBookQuantity(quan, ID);
        updateBookPrice(price, ID);
        updateBookRack(rack, ID);
    }

    public void updateBookName(String name, String ID) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("UPDATE Books SET name = '" + name + "' WHERE ID = '"+ID+"' ");
        statement.close();
        conn.close();
    }

    private void updateBookCategory(String cat, String ID) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("UPDATE Books SET cat = '" + cat + "' WHERE ID = '"+ID+"' ");
        statement.close();
        conn.close();
    }

    private void updateBookAuthor(String auth, String ID) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("UPDATE Books SET auth = '" + auth + "' WHERE ID = '"+ID+"' ");
        statement.close();
        conn.close();
    }

    void updateBookQuantity(int quan, String ID) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("UPDATE Books SET quan = '" + quan + "' WHERE ID = '"+ID +"' ");
        statement.close();
        conn.close();
    }

    private void updateBookRack(int rack, String ID) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("UPDATE Books SET rack = '" + rack + "' WHERE ID = '"+ID+"' ");
        statement.close();
        conn.close();
    }

    private void updateBookPrice(double price, String ID) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("UPDATE Books SET price = '" + price + "' WHERE ID = '"+ID+"' ");
        statement.close();
        conn.close();
    }

    public boolean search(String id) throws SQLException {

        if (getQuantityByID(id) == 0){
            return false;
        }
        boolean isFound = false;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Books ");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            if (resultSet.getString("ID").compareTo(id) == 0) {
                isFound = true;
            }
        }
        statement.close();
        conn.close();
        return isFound;
    }

    public boolean searchForEdit(String id) throws SQLException {

        boolean isFound = false;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Books ");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            if (resultSet.getString("ID").compareTo(id) == 0) {
                isFound = true;
            }
        }
        statement.close();
        conn.close();
        return isFound;
    }

    public ResultSet getListofBooks() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Books ");
        return statement.getResultSet();
    }

    public String getNameByID(String id) throws SQLException, ExceptionNotFound {
        String name = null;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Books ");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            if (resultSet.getString("ID").compareTo(id) == 0) {
                name = resultSet.getString("name");
            }
        }
        resultSet.close();
        if (name == null){
            throw new ExceptionNotFound();
        }
        else{
            return name;
        }
    }

    public String getIDByName(String name) throws SQLException, ExceptionNotFound {
        String id = null;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Books ");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            String n = resultSet.getString("name");
            n = n.toLowerCase();
            if (n.compareTo(name) == 0) {
                id = resultSet.getString("ID");
            }
        }
        resultSet.close();
        if (id == null){
            throw new ExceptionNotFound();
        }
        else{
            return id;
        }
    }

    public int getQuantityByID(String id) throws SQLException {
        int quan = 0;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Books ");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            if (resultSet.getString("ID").compareTo(id) == 0) {
                quan = resultSet.getInt("quan");
            }
        }
        resultSet.close();
        return quan;
    }

    public String getCategoryByID(String id) throws SQLException, ExceptionNotFound {
        String cat = null;
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Books ");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            if (resultSet.getString("ID").compareTo(id) == 0) {
                cat = resultSet.getString("cat");
            }
        }
        resultSet.close();
        if (cat == null){
            throw new ExceptionNotFound();
        }
        else{
            return cat;
        }
    }
}
