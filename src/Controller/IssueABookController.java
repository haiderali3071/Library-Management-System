package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class IssueABookController {

    private RegNoView regNoView;
    private BookIDView bookIDView;
    private String  bookID;
    private boolean isRecoedFound = false;
    private String id;
    private String reg;
    private int NoOFExpireIssuedBooks = 0;

    private void bookNotAvailableGreeting(){

        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(null," Currently this Book isn't Available, Try Another!!! ","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

    }


    public IssueABookController(RegNoView regNoView, String bookID, BookIDView view){
        this(regNoView,  bookID);
        bookIDView = view;
    }

    public IssueABookController(RegNoView regNoView, String bookID) {
        this.regNoView = regNoView;
        this.bookID = bookID;
        regNoView.addBtnIssueBookActionListener(new IssueBookActionListener());
    }

    private class IssueBookActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                reg = regNoView.getRegistrationNo();
                StudentsDatabase stdDB = new StudentsDatabase();
                IssueBooksDatabase isdDB = new IssueBooksDatabase();
                if (!stdDB.isStdExist(reg)){
                    throw new NoSuchElementException();
                }
                if (isdDB.isAlreadyIssued(reg, bookID)) {
                    throw new ExceptionDuplicate();
                }

                Fine fine = new Fine();
                fine.updateFine(reg);

                StringTokenizer stringTokenizer = new StringTokenizer(java.time.LocalDate.now().toString(),"-");
                int year = Integer.parseInt(stringTokenizer.nextToken());
                int mon = Integer.parseInt(stringTokenizer.nextToken());
                int day = Integer.parseInt(stringTokenizer.nextToken());
                int d =day;
                int m = mon;
                int y = year;

                day+=30; // Issued for a month
                if (day > 30) {
                    mon++;
                    day-=30;
                }
                if (mon>12){
                    year++;
                    mon-=12;
                }
                String issuedDate = d+"-"+m+"-"+y;
                String returnDate = day+"-"+mon+"-"+year;

                IssueBooksDatabase is = new IssueBooksDatabase();

                // Following statement throws some exceptions
                is.issueBook(bookID,reg,issuedDate,returnDate);

                Object[] options = {"OK"};
                Object ans = JOptionPane.showOptionDialog(null," Issued Date: "+issuedDate+"\nTo Be Return: "+returnDate,"Successfully Book of ID \""+bookID+"\" is Issued",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(70,70,20)),options,options[0]);

                switch ((int)ans){
                    case 0:
                        Object[] options1 = {"Cancel","No","Yes"};
                        ans = JOptionPane.showOptionDialog(null," Do you want to Issue Any More Book?","Issue Books",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options1,options1[0]);

                        switch ((int)ans){
                            case 0:
                                break;
                            case 1:
                                bookIDView.dispose();
                                regNoView.dispose();

                                break;
                            case 2:
                                bookIDView.makeAllFieldsEmpty();
                                regNoView.dispose();
                                break;


                        }
                        break;
                }

            }
            catch (SQLException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (ExceptionEmptyTextField exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please Fill All the Fields!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }
            catch (ExceptionDuplicate exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Same Book Already Issued to this Student!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }
            catch (NoSuchElementException exp){

                Object[] options = {"Cancel","Add Student"};
                Object ans = JOptionPane.showOptionDialog(null,"This Student does not exist!!!","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

                switch ((int)ans){
                    case 0:
                        regNoView.makeAllFieldsEmpty();
                        break;
                    case 1:
                        AddStudentsView addStudentsView = new AddStudentsView();
                        AddStudentsController adStd = new AddStudentsController(addStudentsView);
                        break;
                }
            }

            catch (ExceptionFine exp){

                NoOFExpireIssuedBooks = exp.getExpiredBooks();

                if (exp.getExpiredBooks() == 0){

                    Object[] options = {"Cancel","Pay Fine"};
                    Object ans = JOptionPane.showOptionDialog(null,"Please, First Pay Fine Rs. "+exp.getFine()+" to Issue new Books!!! ","Fine detail",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                    switch ((int)ans){
                        case 0:
                            regNoView.makeAllFieldsEmpty();
                            break;
                        case 1:
                            PayFineControllerWhileIssuingBooks controller = new PayFineControllerWhileIssuingBooks(reg);
                            break;
                    }
                }
                else if (exp.getExpiredBooks() == 1){

                    Object[] options = {"Cancel","Return Book"};
                    Object ans = JOptionPane.showOptionDialog(null,"This Student has "+exp.getExpiredBooks()+" Issued Book Which Return's Date has Expired!!! \nPlease, First Return This Book, And then Pay Rs. "+exp.getFine()+" Fine to Issue new Books!!! ","Fine detail",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                    switch ((int)ans){
                        case 0:
                            regNoView.makeAllFieldsEmpty();
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
                            regNoView.makeAllFieldsEmpty();
                            break;
                        case 1:
                            ReturnABookController.isCheckOnWhenAllBooksReturned = true;
                            ReturnABookController controller = new ReturnABookController(reg,NoOFExpireIssuedBooks);
                            break;

                    }
                }
                else {
                    Object[] option1 = {"OK"};
                    JOptionPane.showOptionDialog(null," Error in IssueABookController while caching ExceptionFine","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,option1,option1[0]);
                }

            }
        }
    }

}
