package Controller;

import Model.BooksDatabase;
import Model.ExceptionDuplicate;
import Model.ExceptionEmptyTextField;
import View.AddBooksView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddBooksController {

    private AddBooksView addBooksView;

    public AddBooksController(AddBooksView addBooksView) {
        this.addBooksView = addBooksView;
        addBooksView.addSubmitActionListener(new SubmitActionListener());
    }


    private class SubmitActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                BooksDatabase booksDatabase = new BooksDatabase();
                booksDatabase.addBook(addBooksView.getCategory(),addBooksView.getBookID(),
                addBooksView.getBookName(),addBooksView.getAuthor(),addBooksView.getQuantity(),
                addBooksView.getPrice(),addBooksView.getRackNo());

                Object[] options = {"Yes","No"};
                Object ans = JOptionPane.showOptionDialog(null," Do you want to add more books?","Successfully Book Added",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new ImageIcon("images/book.JPG").getImage().getScaledInstance(100,100,0)),options,options[0]);

                switch ((int) ans){
                    case 0:
                        addBooksView.makeEmptyAllFields();
                        break;
                    case 1:
                        addBooksView.dispose();
                }
            }
            catch (SQLException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

            }
            catch (ExceptionDuplicate exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Same Book ID Already exist!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }
            catch (ExceptionEmptyTextField exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,"Please Fill All the Fields!!!","WARNING",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);

            }
            catch (Exception exp){
                System.out.println(exp);
            }
        }

    }

}
