package Controller;

import Model.PasswordDatabase;
import View.MainMenuView;
import View.PasswordViews.PasswordView;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Controller {

    public Controller(){
        try {
            PasswordDatabase passDB = new PasswordDatabase();
            if (passDB.getPassword() == null){
                MainMenuView view = new MainMenuView();
                MainMenuController controller = new MainMenuController(view);
            }
            else {
                PasswordView passwordView = new PasswordView();
                PasswordController controller = new PasswordController(passwordView,passDB.getPassword());
            }
        }
        catch (SQLException exp){
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
        }
    }
}
