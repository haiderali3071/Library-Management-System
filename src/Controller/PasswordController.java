package Controller;

import Model.ExceptionEmptyTextField;
import Model.PasswordDatabase;
import View.MainMenuView;
import View.PasswordViews.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.InputMismatchException;

public class PasswordController {

    private String realPassword;
    private PasswordView passwordView;
    private PasswordSetupView setupView;
    private TurnPasswordOffView passwordOffView;
    private ForgetPasswordView forgetPasswordView;
    private ChangePasswordView changePasswordView;
    private String SQAns = null;


    private class BackActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (setupView != null && setupView.isActive()){
                setupView.dispose();
                if (SQAns == null){
                    MainMenuView.mainMenu();
                }
                else {
                    forgetPasswordView = new ForgetPasswordView();
                    forgetPasswordView.addNextBtnActionListener(new NextBtnPasswordActionListener());
                    forgetPasswordView.addBackBtnActionListener(new BackActionListener());
                }
            }
            else if (passwordOffView != null && passwordOffView.isActive()){
                passwordOffView.dispose();
                MainMenuView.mainMenu();
            }
            else if (forgetPasswordView != null && forgetPasswordView.isActive()){
                forgetPasswordView.dispose();
                PasswordView passwordView = new PasswordView();
                PasswordController controller = new PasswordController(passwordView,realPassword);
            }
            else if (changePasswordView != null && changePasswordView.isActive()){
                changePasswordView.dispose();
            }
        }
    }

    public PasswordController(PasswordView passwordView, String pass){
        this.passwordView = passwordView;
        realPassword = pass;
        passwordView.addUnlockActionListener(new UnlockActionListener());
        passwordView.addForgetActionListener(new ForgetPasswordActionListener());
    }


    public PasswordController(PasswordSetupView setupView){
        this.setupView = setupView;
        this.setupView.addCreatePasswordBtnActionListener(new CreatePasswordActionListener());
        this.setupView.addBackBtnActionListener(new BackActionListener());
    }

    public PasswordController(TurnPasswordOffView passwordOffView){
        this.passwordOffView = passwordOffView;
        passwordOffView.addDoneBtnActionListener(new DoneBtnActionListener());
        this.passwordOffView.addBackBtnActionListener(new BackActionListener());
    }

    public PasswordController(ChangePasswordView changePasswordView){
        this.changePasswordView = changePasswordView;
        this.changePasswordView.addBackBtnActionListener(new BackActionListener());
        this.changePasswordView.addChangePasswordBtnActionListener(new ChangePasswordBtnActionListener());
    }

    private class ChangePasswordBtnActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PasswordDatabase passDB = new PasswordDatabase();
                String pass = passDB.getPassword();
                char[] old = changePasswordView.getOldPassword();
                char[] new1 = changePasswordView.getNewPassword1();
                char[] new2 = changePasswordView.getNewPassword2();

                if (pass.equals(Arrays.toString(old))){
                    if (Arrays.equals(new1,new2)){
                        passDB.changePassword(pass,Arrays.toString(new1));
                        Object[] options = {"OK"};
                        Object answer = JOptionPane.showOptionDialog(null,"Successfully, Password Changed!! ","Success",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPg").getImage().getScaledInstance(80,80,20)),options,options[0]);
                        switch ((int)answer){
                            case 0:
                                changePasswordView.dispose();
                        }
                    }
                    else {
                        Object[] options = {"OK"};
                        JOptionPane.showOptionDialog(null,"Passwords did not match. Try again.","Password Mis-match",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
                    }
                }
                else {
                    Object[] options = {"OK"};
                    Object answer = JOptionPane.showOptionDialog(null,"Wrong Old Password Entered!!! ","Failed Password Attempt",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/cross.JPg").getImage().getScaledInstance(80,80,20)),options,options[0]);
                }
            }
            catch (ExceptionEmptyTextField exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please, fill all the fields","Fill All Fields",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (SQLException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
        }
    }


    private class NextBtnPasswordActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PasswordDatabase passDB = new PasswordDatabase();
                SQAns = forgetPasswordView.getSecurityAnswer();
                if (passDB.getSecurityAnswer().equals(SQAns)){
                    forgetPasswordView.dispose();
                    setupView = new PasswordSetupView();
                    setupView.setSQAnsText(SQAns);
                    setupView.addCreatePasswordBtnActionListener(new CreatePasswordActionListener());
                    setupView.addBackBtnActionListener(new BackActionListener());
                }
                else {
                    Object[] options = {"OK"};
                    Object answer = JOptionPane.showOptionDialog(null,"Wrong Security Answer!!!","Failed Attempt",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,new ImageIcon(new ImageIcon("images/cross.JPg").getImage().getScaledInstance(80,80,20)),options,options[0]);

                }
            }
            catch (ExceptionEmptyTextField exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please, Enter Answer!!!","Answer???",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (SQLException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
        }
    }

    private class ForgetPasswordActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
             passwordView.dispose();
             forgetPasswordView = new ForgetPasswordView();
             forgetPasswordView.addNextBtnActionListener(new NextBtnPasswordActionListener());
             forgetPasswordView.addBackBtnActionListener(new BackActionListener());
        }
    }

    private class DoneBtnActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                char[] p = passwordOffView.getPassword();
                PasswordDatabase passDB = new PasswordDatabase();

                if (passDB.getPassword().equals(Arrays.toString(p))){
                    passDB.deletePassword(Arrays.toString(p));
                    Object[] options = {"OK"};
                    Object answer = JOptionPane.showOptionDialog(null,"Successfully, Password Turn Off!!! ","Success",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPg").getImage().getScaledInstance(80,80,20)),options,options[0]);

                    switch ((int)answer){
                        case 0:
                            passwordOffView.dispose();
                            MainMenuView.mainMenu();
                            break;
                    }
                }
                else {
                    Object[] options = {"OK"};
                    Object answer = JOptionPane.showOptionDialog(null,"Please, Enter correct password!!! ","Failed Password Attempt",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/cross.JPg").getImage().getScaledInstance(80,80,20)),options,options[0]);
                }

            }
            catch (ExceptionEmptyTextField exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please, Enter Password!!!","Password???",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (SQLException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
        }
    }

    private class CreatePasswordActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String ans = setupView.getSecurityAnswer();
                char[] p1 = setupView.getNewPassword1();
                char[] p2 = setupView.getNewPassword2();

                if (Arrays.equals(p1,p2)){
                    PasswordDatabase passDB = new PasswordDatabase();
                    if (SQAns != null){
                        passDB.removePassword(SQAns);
                    }
                    passDB.createPassword(Arrays.toString(p1),ans);

                    Object[] options = {"OK"};
                    Object answer = JOptionPane.showOptionDialog(null,"Successfully, Password Turn On!!! ","Success",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPg").getImage().getScaledInstance(80,80,20)),options,options[0]);

                    switch ((int)answer){
                        case 0:
                            setupView.dispose();
                            MainMenuView.mainMenu();
                            break;
                    }
                }
                else {
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Passwords did not match. Try again.","Password Mis-match",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (ExceptionEmptyTextField exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please, fill all the fields","Fill All Fields",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (SQLException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }

        }
    }

    private class UnlockActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (realPassword.equals(Arrays.toString(passwordView.getPassword()))){
                passwordView.dispose();
                MainMenuView.mainMenu();
            }
            else {
                JOptionPane.showMessageDialog(null,"Wrong Password!!!","Password",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
