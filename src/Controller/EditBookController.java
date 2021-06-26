package Controller;

import Model.BooksDatabase;
import Model.ExceptionNotFound;
import Model.ExceptionEmptyTextField;
import View.BookIDView;
import View.EditBookView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditBookController {

    private BookIDView bookIDView;
    private String bookId;
    private EditBookView ed;
    private BooksDatabase booksDatabase;

    public EditBookController(BookIDView bookIDView){
        this.bookIDView = bookIDView;
        bookIDView.addSearchIDListener(new SearchBookIDActionListener());
    }

    private class SearchBookIDActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                bookId = bookIDView.getBookID();
                 booksDatabase = new BooksDatabase();
                if (booksDatabase.searchForEdit(bookId)){
                    ed = new EditBookView(bookId);
                    ed.addSubmitActionListener(new SubmitActionListener());
                    ed.addMainListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ed.dispose();
                            bookIDView.dispose();
                        }
                    });
                }
                else {
                    throw new ExceptionNotFound();
                }

            }
            catch (ExceptionEmptyTextField exp) {

                Object[] options = {"OK"};
                Object ans = JOptionPane.showOptionDialog(null,"Please Enter Book ID!!!","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

            }
            catch (SQLException exp) {
                Object[] options = {"OK"};
                Object ans = JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

            }
            catch (ExceptionNotFound exp) {

                Object[] options = {"OK"};
                Object ans = JOptionPane.showOptionDialog(null,"Currently this Book isn't Available, Try Another!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }

        }
    }


    private class SubmitActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           updateBook();
        }
    }

    private void updateBook(){

        try{
            booksDatabase.updateBook(ed.getCategory(),bookId,ed.getBookName(),ed.getAuthor(),ed.getQuantity(),ed.getPrice(),ed.getRackNo());

            Object[] options = { "No","Yes"};
            Object ans = JOptionPane.showOptionDialog(null,"Successfully Book's Record Updated!!! \nDo you want to Edit more Book's Records?","Notification",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/yes2.JPg").getImage().getScaledInstance(80,80,0)),options,options[0]);

            switch ((int)ans){
                case 0:
                    ed.dispose();
                    break;
                case 1:
                    ed.dispose();
                    BookIDView bookIDView = new BookIDView("\t Edit Book's Record");
                    bookIDView.setTitle("Edit Book");
                    EditBookController editBookController = new EditBookController(bookIDView);
                    break;

            }
        }
        catch (SQLException exp) {
            Object[] options = {"OK"};
            Object ans = JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

        }
    }

}
