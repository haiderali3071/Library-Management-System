package Controller;

import Model.BooksDatabase;
import Model.ExceptionNotFound;
import Model.ExceptionEmptyTextField;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class IssueBooksController {

    private IssueBooksView issueBooksView;
    private BookIDView bookIDView;
    private boolean isRecoedFound = false;
    private String id;


    private void bookNotAvailableGreeting(){
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(null,"Currently this Book isn't Available, Try Another!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
    }

    public IssueBooksController(IssueBooksView issueBooksView) {
        this.issueBooksView = issueBooksView;
        issueBooksView.addBtnIssueABookActionListener(new IssueABookActionListener());
        issueBooksView.addBtnViewIssueBookActionListener(new ViewIssueBookActionListener());
        issueBooksView.btnReturnABookActionListener(new ReturnABookActionListener());
    }

    private class ViewIssueBookActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            IssuedBooksView issuedBooksView = new IssuedBooksView();
            issuedBooksView.addMainMenuActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    issueBooksView.dispose();
                    issuedBooksView.dispose();
                }
            });
            issuedBooksView.addBackActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    issuedBooksView.dispose();
                }
            });
        }
    }

    private class IssueABookActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            bookIDView = new BookIDView("issue");
            bookIDView.setTitle("Issue a Book");
            bookIDView.addSearchIDListener(new SearchActionListener());
            bookIDView.addMainMenuListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    issueBooksView.dispose();
                    bookIDView.dispose();
                }
            });
        }

    }




    private class ReturnABookActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            RegNoView regNoView = new RegNoView(false);
            regNoView.changeButtonName("Return a Book");
            ReturnABookController.isCheckOnWhenAllBooksReturned = false;
            ReturnABookController rt = new ReturnABookController(regNoView,issueBooksView);
            regNoView.addMainMenuListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    issueBooksView.dispose();
                    regNoView.dispose();
                }
            });
        }

    }

    private class SearchActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                BooksDatabase booksDatabase = new BooksDatabase();
                if (booksDatabase.search(id = bookIDView.getBookID())) {
                    isRecoedFound = true;

                    Object[] options = {"Cancel", "Next"};
                    Object ans = JOptionPane.showOptionDialog(null," \nThere are " +
                            booksDatabase.getQuantityByID(id) + " unissued books in library. \n" +
                            "The Name of Book is \"" + booksDatabase.getNameByID(id)+"\"","The Book Record is available",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                    switch ((int)ans){

                        case 0:
                            break;
                        case 1:
                            RegNoView regNoView = new RegNoView(false);
                            regNoView.addMainMenuListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    regNoView.dispose();
                                    issueBooksView.dispose();
                                    bookIDView.dispose();
                                }
                            });
                            IssueABookController issueABookController = new IssueABookController(regNoView, id,bookIDView);
                            break;
                    }

                } else {
                    isRecoedFound = false;
                    bookNotAvailableGreeting();
                }
            }
            catch (SQLException exp) {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
            catch (ExceptionEmptyTextField exp) {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please Enter Book ID!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
            }
            catch (ExceptionNotFound exp){
                bookNotAvailableGreeting();
            }

        }
    }
}
