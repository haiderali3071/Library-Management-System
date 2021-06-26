package Controller;

import Model.BooksDatabase;
import Model.ExceptionNotFound;
import Model.ExceptionEmptyTextField;
import Model.IssueBooksDatabase;
import View.BookIDView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DeleteBooksController {

    private BookIDView bookIDView;
    private String bookName;
    private String id;

    public DeleteBooksController(BookIDView view) {

        bookIDView = view;
        bookIDView.addSearchIDListener(new DeleteActionListener());
        bookIDView.setButtonName("Delete Book");
    }


    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                id = bookIDView.getBookID();
                BooksDatabase booksDatabase = new BooksDatabase();
                bookName = booksDatabase.getNameByID(id);


                Object[] options = {"Yes","No"};
                Object ans = JOptionPane.showOptionDialog(null,"This Book \"" + bookName + "\" is Available!!! \nAre you sure, you want to delete this book?","Notification",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/available.JPG").getImage().getScaledInstance(100,100,0)),options,options[1]);

        switch ((int) ans){
            case 0:
                    booksDatabase.deleteBook(id);
                    IssueBooksDatabase is = new IssueBooksDatabase();
                    is.deleteIssuedBook(id);

                    ans = JOptionPane.showOptionDialog(null,"The Book \"" + bookName + "\" is successfully deleted!!! \nDo you want to delete Any More Book?","Success",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(100,100,0)),options,options[0]);

                    switch ((int) ans){
                        case 0:
                            bookIDView.makeAllFieldsEmpty();
                            break;
                        case 1:
                            bookIDView.dispose();
                            break;
                    }

                break;
            case 1:
                break;
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
            catch (ExceptionNotFound exp) {

               Object[] options = {"OK"};
               JOptionPane.showOptionDialog(null,"Currently this Book isn't Available, Try Another!!!","Notification",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }

        }
    }

}

