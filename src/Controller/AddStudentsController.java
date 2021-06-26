package Controller;

import Model.ExceptionDuplicate;
import Model.ExceptionEmptyTextField;
import Model.StudentsDatabase;
import View.AddStudentsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddStudentsController {

    private AddStudentsView view;

    public AddStudentsController(AddStudentsView issueOrReturnABookView){
        view = issueOrReturnABookView;
        view.addBtnIssueBookActionListener(new SubmitActionListener());
    }


    private class SubmitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                StudentsDatabase studentsDatabase = new StudentsDatabase();
                studentsDatabase.addStudent(view.getStdName(),view.getRegistrationNo());

                Object[] options = {"Yes","No"};
                Object ans = JOptionPane.showOptionDialog(null,"Successfully Student Added!!! \nDo you want to add more Students?","Success",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(80,80,0)),options,options[0]);

                switch ((int)ans){
                    case 0:
                        view.makeEmptyAllFields();
                        break;
                    case 1:
                        view.dispose();
                        break;
                }
            }
            catch (SQLException exp){
                Object[] options = {"OK"};
                Object ans = JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (ExceptionDuplicate exp){
                Object[] options = {"OK"};
                Object ans = JOptionPane.showOptionDialog(null,"Same Reg. No Already exist!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }
            catch (ExceptionEmptyTextField exp){
                Object[] options = {"OK"};
                Object ans = JOptionPane.showOptionDialog(null,"Please Fill All the Fields!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }

        }

    }





}
