package View;

import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IssuedBooksView extends JFrame{

    private JButton back;
    private JButton mainmenu;

    public IssuedBooksView() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener());

        setTitle("Issued Books List");
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        add(menuBar);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable();
        table.setFont(new Font("Arial",Font.PLAIN,20));
        table.setRowHeight(40);

        model.addColumn("Registration No");
        model.addColumn("STUDENT NAME");
        model.addColumn("CATEGORY");
        model.addColumn("BOOK ID");
        model.addColumn("BOOK NAME");
        model.addColumn("ISSUED DATE");
        model.addColumn("RETURN DATE");

        try {
            IssueBooksDatabase is = new IssueBooksDatabase();
            BooksDatabase bd = new BooksDatabase();
            ResultSet listOfIssuedBooks = is.getListOfIssuedBooks();



            while (listOfIssuedBooks.next()) {
                String ID = listOfIssuedBooks.getString("i");
                String name = null;
                String cat = null;
                if (bd.search(ID)){
                     name = bd.getNameByID(ID);
                     cat = bd.getCategoryByID(ID);
                }
                String reg = listOfIssuedBooks.getString("r");
                StudentsDatabase studentsDatabase = new StudentsDatabase();
                String std = studentsDatabase.getStdNameByReg(reg);
                String issueDate = listOfIssuedBooks.getString("isd");
                String returnDate = listOfIssuedBooks.getString("retd");

                model.addRow(new Object[]{reg,std,cat,ID,name,issueDate,returnDate});
            }
            listOfIssuedBooks.close();

            table.setModel(model);
            table.setBounds(30, 40, 200, 300);
            JScrollPane sp = new JScrollPane(table);
            add(sp, BorderLayout.CENTER);
            back = new JButton("Go To Main Menu");
            add(back, BorderLayout.SOUTH);
            setBounds(240,120,1000,600);

            back = new JButton();
            back.setFont(new Font("Arial",Font.PLAIN,20));
            back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(40,40,0)));

            mainmenu = new JButton();
            mainmenu.setIcon(new ImageIcon(new ImageIcon("images/home1.JPG").getImage().getScaledInstance(40,40,0)));

            JPanel belowPanel = new JPanel();
            add(belowPanel, BorderLayout.SOUTH);
            belowPanel.setLayout(new BoxLayout(belowPanel,BoxLayout.X_AXIS));

            belowPanel.add(back);
            belowPanel.add(mainmenu);

            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
        }
        catch (SQLException | ExceptionNotFound exp){
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

        }

    }


    public void addBackActionListener(ActionListener e){
        back.addActionListener(e);
    }

    public void addMainMenuActionListener(ActionListener e){
        mainmenu.addActionListener(e);
    }
    public static void main(String []j){
        IssuedBooksView i = new IssuedBooksView();
    }
}
