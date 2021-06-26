package View;

import Controller.MainMenuController;
import Controller.PasswordController;
import Model.*;
import View.PasswordViews.PasswordView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainMenuView extends JFrame {

    private JPanel contentPane;
    private JLabel lbMainMenu;
    private JButton btnAddBooks;
    private JButton btnDeleteBooks;
    private JButton btnSearchBooks;
    private JButton btnIssueBooks;
    private JButton btnViewBookList;
    private JButton btnEditBooksRecord;
    private JButton btnAddStudents;
    private JButton btnViewStudentList;
    private JButton btnPayFine;
    private JButton changePass;
    private JButton turnPass;


    public static void main(String[] args) {
        MainMenuView frame = new MainMenuView();

    }

    public static void mainMenu(){
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(mainMenuView);
    }

    public MainMenuView() {

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener());
        setTitle("Main Menu");

        setLayout(new GridLayout(4,3));


        btnViewStudentList = new JButton("View Students List");
        btnViewStudentList.setFont(new Font("Arial",Font.PLAIN,25));
        btnPayFine = new JButton("Pay Fine");
        btnPayFine.setFont(new Font("Arial",Font.PLAIN,25));
        btnAddBooks = new JButton("Add Books");
        btnAddBooks.setFont(new Font("Arial",Font.PLAIN,25));
        btnAddStudents = new JButton("Add Students");
        btnAddStudents.setFont(new Font("Arial",Font.PLAIN,25));
        btnDeleteBooks = new JButton("Delete Books");
        btnDeleteBooks.setFont(new Font("Arial",Font.PLAIN,25));
        btnSearchBooks = new JButton("Search Books");
        btnSearchBooks.setFont(new Font("Arial",Font.PLAIN,25));
        btnIssueBooks = new JButton("Issue & Return Books");
        btnIssueBooks.setFont(new Font("Arial",Font.PLAIN,25));
        btnViewBookList = new JButton("View Books List");
        btnViewBookList.setFont(new Font("Arial",Font.PLAIN,25));
        btnEditBooksRecord = new JButton("Edit Book's Record");
        btnEditBooksRecord.setFont(new Font("Arial",Font.PLAIN,25));
        changePass = new JButton("Change Password");
        changePass.setFont(new Font("Arial",Font.PLAIN,25));
        turnPass = new JButton("Turn Password");
        turnPass.setFont(new Font("Arial",Font.PLAIN,25));


        btnPayFine.setIcon(new ImageIcon(new ImageIcon("images/fine.JPG").getImage().getScaledInstance(200,150,0)));
        btnViewStudentList.setIcon(new ImageIcon(new ImageIcon("images/student1.JPG").getImage().getScaledInstance(200,150,0)));
        btnViewBookList.setIcon(new ImageIcon(new ImageIcon("images/book.JPG").getImage().getScaledInstance(200,150,0)));
        btnSearchBooks.setIcon(new ImageIcon(new ImageIcon("images/search1.JPG").getImage().getScaledInstance(200,150,0)));
        btnEditBooksRecord.setIcon(new ImageIcon(new ImageIcon("images/edit3.JPG").getImage().getScaledInstance(200,150,0)));
        btnDeleteBooks.setIcon(new ImageIcon(new ImageIcon("images/delete.JPG").getImage().getScaledInstance(200,150,0)));
        btnIssueBooks.setIcon(new ImageIcon(new ImageIcon("images/laptop.JPG").getImage().getScaledInstance(200,150,0)));
        btnAddStudents.setIcon(new ImageIcon(new ImageIcon("images/students.JPG").getImage().getScaledInstance(200,150,0)));
        btnAddBooks.setIcon(new ImageIcon(new ImageIcon("images/book5.JPG").getImage().getScaledInstance(200,150,0)));
        changePass.setIcon(new ImageIcon(new ImageIcon("images/lock.JPG").getImage().getScaledInstance(200,150,0)));
        turnPass.setIcon(new ImageIcon(new ImageIcon("images/lock.JPG").getImage().getScaledInstance(200,150,0)));



        add(btnViewStudentList);
        add(btnPayFine);
        add(btnAddBooks);
        add(btnAddStudents);
        add(btnDeleteBooks);
        add(btnSearchBooks);
        add(btnIssueBooks);
        add(btnViewBookList);
        add(btnEditBooksRecord);
        add(turnPass);
        add(changePass);

        try {
            PasswordDatabase passDB = new PasswordDatabase();
            new IssueBooksDatabase();

            if (passDB.getPassword() == null){
                turnPass.setText("Turn Password On");
                changePass.setEnabled(false);
            }
            else {
                turnPass.setText("Turn Password OFF");
                changePass.setEnabled(true);
            }
        }
        catch (SQLException exp){
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
        }

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

    }


    public void addBtnAddBooksActionListener(ActionListener e) {
        this.btnAddBooks.addActionListener(e);
    }
    public void addBtnAddStudentsActionListener(ActionListener e) {
        this.btnAddStudents.addActionListener(e);
    }

    public void addBtnViewStudentsListActionListener(ActionListener e){
        btnViewStudentList.addActionListener(e);
    }


    public void addBtnPayFineStudentsListActionListener(ActionListener e){
        btnPayFine.addActionListener(e);
    }


    public void addBtnDeleteBooksActionListener(ActionListener e) {
        this.btnDeleteBooks.addActionListener(e);
    }

    public void addBtnSearchBooksActionListener(ActionListener e) {
        this.btnSearchBooks.addActionListener(e);
    }

    public void addBtnIssueBooksActionListener(ActionListener e) {
        this.btnIssueBooks.addActionListener(e);
    }

    public void addBtnViewBookListActionListener(ActionListener e) {
        this.btnViewBookList.addActionListener(e);
    }

    public void addBtnEditBooksRecordActionListener(ActionListener e) {
        this.btnEditBooksRecord.addActionListener(e);
    }

    public void addChangePasswordActionListener(ActionListener e){
        this.changePass.addActionListener(e);
    }

    public void addTurnPasswordActionListener(ActionListener e){
        this.turnPass.addActionListener(e);
    }

}

