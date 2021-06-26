package Controller;

import Model.BooksDatabase;
import Model.ExceptionNotFound;
import Model.ExceptionEmptyTextField;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SearchBookController {

    private BookIDView bookIDView;
    private BookNameView bookNameView;
    private BookView bookView;

    public SearchBookController(BookIDView bookIDView) {
       this.bookIDView = bookIDView;
       this.bookIDView.addSearchIDListener(new SearchIDActionListener());
    }

    public SearchBookController(BookNameView bookNameView) {
        this.bookNameView = bookNameView;
        this.bookNameView.addSearchNameListener(new SearchNameActionListener());
    }

    private class SearchIDActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String book_id = bookIDView.getBookID();
                bookView = new BookView(book_id);
                bookView.addBackActionListener(new BackToIDView());
                bookView.addMainMenuActionListener(new MainMenuActionListener());
                bookIDView.dispose();
            }
            catch (ExceptionNotFound exp){

                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Currently this Book isn't Available, Try Another!!!","Notification",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
            }
            catch (ExceptionEmptyTextField exp) {

                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please Enter Book ID!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);

            }
        }
    }

    private class SearchNameActionListener implements ActionListener {

        String book_id;
        @Override
        public void actionPerformed(ActionEvent e){
            try {
                BooksDatabase booksDatabase = new BooksDatabase();
                book_id = booksDatabase.getIDByName(bookNameView.getBookName());
                bookView = new BookView(book_id);
                bookView.addBackActionListener(new BackToNameView());
                bookView.addMainMenuActionListener(new MainMenuActionListener());
                bookNameView.dispose();
            }
            catch (SQLException exp) {

                Object[] options = {"OK"};
                Object ans = JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL EXCEPTION",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

            }
            catch (ExceptionNotFound exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Currently this Book isn't Available, Try Another!!!","Notification",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);

            }
            catch (ExceptionEmptyTextField exp) {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please Enter Book Name!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);

            }
        }
    }

    private class BackToIDView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            bookView.dispose();
            BookIDView bookIDView = new BookIDView("images/search1.JPG");
            bookIDView.setTitle("Search Book");
            bookIDView.setButtonName("Search");
            SearchBookController controller = new SearchBookController(bookIDView);
        }
    }

    private class BackToNameView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            bookView.dispose();
            BookNameView bookNameView = new BookNameView();
            SearchBookController controller = new SearchBookController(bookNameView);
        }
    }

    private class MainMenuActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            bookView.dispose();
            MainMenuView.mainMenu();
        }
    }


}

