package Controller;

import Model.Fine;
import Model.PasswordDatabase;
import View.*;
import View.PasswordViews.ChangePasswordView;
import View.PasswordViews.PasswordSetupView;
import View.PasswordViews.TurnPasswordOffView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainMenuController {

    private MainMenuView mainMenuView;

    public MainMenuController(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
        mainMenuView.addBtnAddBooksActionListener(new AddBooksActionListener());
        mainMenuView.addBtnIssueBooksActionListener(new IssueBooksActionListener());
        mainMenuView.addBtnSearchBooksActionListener(new SearchBooksActionListener());
        mainMenuView.addBtnDeleteBooksActionListener(new DeleteBooksActionListener());
        mainMenuView.addBtnEditBooksRecordActionListener(new EditBookRecordActionListener());
        mainMenuView.addBtnViewBookListActionListener(new ViewBookListActionListener());
        mainMenuView.addBtnViewStudentsListActionListener(new ViewStudentListActionListener());
        mainMenuView.addBtnAddStudentsActionListener(new AddStudentsActionListener());
        mainMenuView.addBtnPayFineStudentsListActionListener(new PayFineActionListener());
        mainMenuView.addTurnPasswordActionListener(new TurnPasswordActionListener());
        mainMenuView.addChangePasswordActionListener(new ChangePasswordActionListener());
    }

    private class ChangePasswordActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ChangePasswordView changePasswordView = new ChangePasswordView();
            PasswordController controller = new PasswordController(changePasswordView);
        }
    }

    private class TurnPasswordActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PasswordDatabase passDB = new PasswordDatabase();
                if (passDB.getPassword() == null){
                    mainMenuView.dispose();
                    PasswordSetupView setupView = new PasswordSetupView();
                    PasswordController controller = new PasswordController(setupView);
                }
                else {
                    mainMenuView.dispose();
                    TurnPasswordOffView turnPasswordOffView = new TurnPasswordOffView();
                    PasswordController controller = new PasswordController(turnPasswordOffView);
                }
            }
            catch (SQLException exp){
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
            }
        }
    }

    private class PayFineActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            RegNoView view = new RegNoView(true);
            view.changeButtonName("Pay Fine");
            PayFineController controller = new PayFineController(view);
        }
    }
    private class AddBooksActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddBooksView addBooksView = new AddBooksView();
            AddBooksController addBooksController = new AddBooksController(addBooksView);
        }
    }

    private class AddStudentsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddStudentsView addStudentsView = new AddStudentsView();
            AddStudentsController adStd = new AddStudentsController(addStudentsView);
        }
    }

    private class IssueBooksActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            IssueBooksView issueBooksView = new IssueBooksView();
            IssueBooksController issueBooksController = new IssueBooksController(issueBooksView);
        }
    }

    private class DeleteBooksActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            BookIDView bookIDView = new BookIDView("images/cross3.JPG");
            bookIDView.setTitle("Delete Books");
            DeleteBooksController deleteBooksController = new DeleteBooksController(bookIDView);

        }
    }

        private class SearchBooksActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] options = {"Search Book By Name","Search Book By ID"};
                Object ans = JOptionPane.showOptionDialog(null,"Select your desired option?","Search Book", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);

                switch ((int) ans){
                    case 0:
                        BookNameView bookNameView = new BookNameView();
                        SearchBookController searchBookController = new SearchBookController(bookNameView);
                        break;
                    case 1:
                        BookIDView bookIDView = new BookIDView("images/search1.JPG");
                        bookIDView.setTitle("Search Book");
                        bookIDView.setButtonName("Search");
                        SearchBookController controller = new SearchBookController(bookIDView);
                        break;

        }
            }
        }

        private class EditBookRecordActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                BookIDView bookIDView = new BookIDView("\t Edit Book's Record");
                bookIDView.setTitle("Edit Book");
                EditBookController editBookController = new EditBookController(bookIDView);
            }
        }

        private class ViewBookListActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                BooksListView booksListView = new BooksListView();
                BooksListController booksListController = new BooksListController(booksListView);
            }
        }

        private class ViewStudentListActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                StudentsListView studentsListView = new StudentsListView();
                StudentsListController stdListCont = new StudentsListController(studentsListView);
            }
        }

}
