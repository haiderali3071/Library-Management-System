package Controller;

import Model.*;
import View.RegNoView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

public class PayFineController {

    private RegNoView view;
    private int NoOFExpireIssuedBooks;
    private String  reg;

    public PayFineController(RegNoView view){
        this.view = view;
        view.addBtnIssueBookActionListener(new getRegistrationActionListener());
    }

    private class getRegistrationActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                reg = view.getRegistrationNo();
                StudentsDatabase stdDB = new StudentsDatabase();
                IssueBooksDatabase isdDB = new IssueBooksDatabase();
                if (!stdDB.isStdExist(reg)){
                    throw new NoSuchElementException();
                }


                Fine fine = new Fine();
                fine.updateFine(reg);

                Object[] options = {"OK"};
                Object ans = JOptionPane.showOptionDialog(null,"This Student has No any Fine","Notification",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);

                switch ((int)ans){
                    case 0:
                        view.makeAllFieldsEmpty();
                        break;
                }

            }
            catch (NoSuchElementException exp){

                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"This Student does not exist!!!","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

            }
            catch (SQLException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (ExceptionEmptyTextField exp){

                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please Enter Registration No!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }
            catch (ExceptionFine exp){

                NoOFExpireIssuedBooks = exp.getExpiredBooks();

                if (exp.getExpiredBooks() == 0){

                    Object[] options = {"Cancel","Pay Fine"};
                    Object ans = JOptionPane.showOptionDialog(null,"This Student has Remaining Rs. "+exp.getFine()+" Fine","Fine detail",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                    switch ((int)ans){
                        case 0:
                            view.makeAllFieldsEmpty();
                            break;
                        case 1:
                            try {
                                Connection conn = DriverManager.getConnection("jdbc:sqlite:Students.db");
                                Statement statement = conn.createStatement();
                                statement.execute("UPDATE Students SET rb = '" + "0" + "'  WHERE r = '"+reg +"' ");

                                Object[] options1 = {"OK"};
                                JOptionPane.showOptionDialog(null,"Successfully, Fine has been received!!! ","Notification",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(80,80,20)),options1,options1[0]);

                            }
                            catch (SQLException ex){
                                Object[] option1 = {"OK"};
                                JOptionPane.showOptionDialog(null,ex.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,option1,option1[0]);

                            }
                            break;
                    }
                }
                else if (exp.getExpiredBooks() == 1){

                    Object[] options = {"Cancel","Return Book"};
                    Object ans = JOptionPane.showOptionDialog(null,"This Student has "+exp.getExpiredBooks()+" Issued Book Which Return's Date has Expired!!! \nPlease, First Return This Book, And then Pay Rs. "+exp.getFine()+" Fine to Issue new Books!!! ","Fine detail",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                    switch ((int)ans){
                        case 0:
                            view.makeAllFieldsEmpty();
                            break;
                        case 1:
                            ReturnABookController.isCheckOnWhenAllBooksReturned = true;
                            ReturnABookController controller = new ReturnABookController(reg,NoOFExpireIssuedBooks);
                            break;

                    }


                }
                else if (exp.getExpiredBooks() > 1){

                    Object[] options = {"Cancel","Return Books"};
                    Object ans = JOptionPane.showOptionDialog(null,"This Student has "+exp.getExpiredBooks()+" Issued Books Which Return's Date has Expired!!! \nPlease, First Return These Books, And then Pay Rs. "+exp.getFine()+" Fine to Issue new Books!!! ","Fine detail",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                    switch ((int)ans){
                        case 0:
                            view.makeAllFieldsEmpty();
                            break;
                        case 1:
                            ReturnABookController.isCheckOnWhenAllBooksReturned = true;
                            ReturnABookController controller = new ReturnABookController(reg,NoOFExpireIssuedBooks);
                            break;

                    }
                }
                else {
                    Object[] option1 = {"OK"};
                    JOptionPane.showOptionDialog(null," Error in PayFineController while caching ExceptionFine","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,option1,option1[0]);
                }

            }
        }
    }



}
