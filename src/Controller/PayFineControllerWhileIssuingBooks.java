package Controller;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PayFineControllerWhileIssuingBooks {


    public PayFineControllerWhileIssuingBooks(String reg){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Students.db");
            Statement statement = conn.createStatement();
            statement.execute("UPDATE Students SET rb = '" + "0" + "'  WHERE r = '"+reg +"' ");

            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,"Successfully, Fine has been Received","Notification",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(100,100,0)),options,options[0]);

        }
        catch (SQLException ex){
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,ex.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
        }
    }


}
