package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class ReturnABookController {

    private RegNoView regNoView;
    private IssuedByStudentView issuedByStudentVIew;
    private String reg;
    private double fine;
    private int NoOfIssuedBooks = 0;
    private String bookID;
    private  BookIDView bookIDView;
    private boolean isRecoedFound;
    public static boolean isCheckOnWhenAllBooksReturned = false;

    // It is to manage main menu button
    private IssueBooksView issueBooksView;

    public ReturnABookController(RegNoView regNoView,IssueBooksView issuedBooksView){
        this(regNoView);
        this.issueBooksView = issuedBooksView;
    }



    public ReturnABookController(RegNoView regNoView){
        this.regNoView = regNoView;
        regNoView.addBtnIssueBookActionListener(new ReturnBookActionListener());
    }

    public ReturnABookController(String reg, int NoOfExpireIssuedBooks){
        this.reg = reg;
        this.NoOfIssuedBooks = NoOfExpireIssuedBooks;
        issuedByStudentVIew = new IssuedByStudentView(reg);
        issuedByStudentVIew.addBtnReturnuActionListener(new ReturnActionListener());
    }

    private class ReturnBookActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                reg = regNoView.getRegistrationNo();
                StudentsDatabase stdDB = new StudentsDatabase();
                if (!stdDB.isStdExist(reg)){
                    throw new NoSuchElementException();
                }

                IssueBooksDatabase is = new IssueBooksDatabase();
                ResultSet listOfIssuedBooks = is.getListOfIssuedBooks();

                while (listOfIssuedBooks.next()) {
                    String regNo = listOfIssuedBooks.getString("r");
                    if (reg.equals(regNo)){
                        NoOfIssuedBooks++;
                        break;
                    }
                }
                listOfIssuedBooks.close();
                if (NoOfIssuedBooks == 0){
                    throw new ExceptionNotFound();
                }

                issuedByStudentVIew = new IssuedByStudentView(reg);
                issuedByStudentVIew.addBtnReturnuActionListener(new ReturnActionListener());

            }
            catch (ExceptionEmptyTextField exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please Fill All the Fields!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }
            catch (NoSuchElementException exp){

                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"This Student does not exist!!!","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

            }

            catch (SQLException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (ExceptionNotFound exp) {

                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"This student did not issue any book!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
            }
        }
    }


    private class ReturnActionListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                bookID = issuedByStudentVIew.getBookId();

                Object[] options = {"No","Yes"};
                Object ans = JOptionPane.showOptionDialog(null,"Are you sure?","Asking?",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                switch ((int)ans){
                    case 0:

                        break;

                    case 1:

                        try {
                            Fine tax = new Fine();
                            tax.getFine(bookID,reg);
                            IssueBooksDatabase isd = new IssueBooksDatabase();
                            isd.returnIssuedBook(bookID,reg);
                            reloadData();
                        }
                        catch (ExceptionFine exp){


                            Object[] option = {"Pay Later", "Pay Now"};
                            ans = JOptionPane.showOptionDialog(null,"There is Rs. "+exp.getFine()+" Fine on this book.","Fine",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,option,option[0]);

                            switch ((int)ans){
                                case 0:
                                    try {
                                        IssueBooksDatabase isd = new IssueBooksDatabase();
                                        StudentsDatabase stdDB = new StudentsDatabase();
                                        String s = stdDB.getReturnBookFine(reg);
                                        s += ",";
                                        s += Double.toString(fine);
                                        stdDB.setReturnBookFine(reg,s);
                                        isd.returnIssuedBook(issuedByStudentVIew.getBookId(),reg);
                                        reloadData();
                                        NoOfIssuedBooks--;
                                        isAllExpiredBooksReturned();

                                    }
                                    catch (SQLException ex1){
                                        Object[] option1 = {"OK"};
                                        JOptionPane.showOptionDialog(null,ex1.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,option1,option1[0]);
                                    }
                                    break;

                                case 1:
                                    try{
                                        IssueBooksDatabase isd = new IssueBooksDatabase();
                                        isd.returnIssuedBook(issuedByStudentVIew.getBookId(),reg);
                                        reloadData();
                                        NoOfIssuedBooks--;
                                        isAllExpiredBooksReturned();

                                    }
                                    catch (SQLException ex2){
                                        Object[] option2 = {"OK"};
                                        JOptionPane.showOptionDialog(null,ex2.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,option2,option2[0]);
                                    }
                                    break;
                            }
                        }
                        catch (SQLException exp){
                            Object[] option = {"OK"};
                            JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,option,option[0]);
                        }
                        break;
                }
            }
            catch (IndexOutOfBoundsException exp){

                Object[] option = {"OK"};
                JOptionPane.showOptionDialog(null,"Please, Select any one row","SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,option,option[0]);

            }

        }
    }


    private void reloadData(){
        issuedByStudentVIew.dispose();
        issuedByStudentVIew = new IssuedByStudentView(reg);
        issuedByStudentVIew.addBtnReturnuActionListener(new ReturnActionListener());
    }

    private void isAllExpiredBooksReturned(){
        if(isCheckOnWhenAllBooksReturned){
            if (NoOfIssuedBooks == 0){
                Fine f = new Fine();
                try {
                    //Throw a Exception
                    f.updateFine(reg);

                    Object[] option = {"OK"};
                    JOptionPane.showOptionDialog(null,"You Have Returned All Books which Date has been Expired \nNow, You can Issue new Books","Notification",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(70,70,20)),option,option[0]);

                }
                catch (ExceptionFine exp){

                    Object[] option = {"Cancel", "Pay It"};
                    Object ans = JOptionPane.showOptionDialog(null,"You Have Returned All Books which Date has been Expired \nThis Student has Remaining Rs. "+exp.getFine()+" Fine","Fine detail",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);

                    switch ((int)ans){
                        case 0:
                            break;
                        case 1:
                            PayFineControllerWhileIssuingBooks controller = new PayFineControllerWhileIssuingBooks(reg);
                            break;
                    }
                }
                catch (SQLException exp){
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
                }
            }
        }
    }

}
