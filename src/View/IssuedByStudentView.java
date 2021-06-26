package View;

import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IssuedByStudentView extends JFrame {

    private JButton btnReturn;
    private JTable table;
    private ArrayList<String> id = new ArrayList<String>();

    public static void main(String []dsf){
        new IssuedByStudentView("1");
    }

    public IssuedByStudentView(String regNo) {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener());


        setTitle("Issued Books List");
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        add(menuBar);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable();
        table.setFont(new Font("Arial",Font.PLAIN,20));
        table.setRowHeight(40);


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
                String reg = listOfIssuedBooks.getString("r");
                StudentsDatabase studentsDatabase = new StudentsDatabase();
                String std = studentsDatabase.getStdNameByReg(reg);

                if (regNo.equals(reg)){
                    setTitle(reg+"   "+std);
                    String ID = listOfIssuedBooks.getString("i");
                    String name = null;
                    String cat = null;
                    if (bd.search(ID)){
                        name = bd.getNameByID(ID);
                        cat = bd.getCategoryByID(ID);
                    }

                    String issueDate = listOfIssuedBooks.getString("isd");
                    String returnDate = listOfIssuedBooks.getString("retd");

                    this.id.add(ID);

                    model.addRow(new Object[]{cat,ID,name,issueDate,returnDate,new JCheckBox("RETURN")});
                }
            }
            listOfIssuedBooks.close();

            table.setModel(model);
            JScrollPane sp = new JScrollPane(table);
            add(sp, BorderLayout.CENTER);


            JPanel panel1 = new JPanel();
            panel1.add(new JLabel("Select One Row Only"));
            add(panel1,BorderLayout.NORTH);


            JButton back = new JButton();
            back.setFont(new Font("Arial",Font.PLAIN,20));
            back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(40,40,0)));

            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            btnReturn = new JButton("     RETURN     ");
            btnReturn.setFont(new Font("Arial",Font.PLAIN,20));
            btnReturn.setForeground(Color.blue);
            btnReturn.setIcon(new ImageIcon(new ImageIcon("images/ret.jpeg").getImage().getScaledInstance(40,40,0)));

            JPanel leftBottomPanel = new JPanel();
            leftBottomPanel.setLayout(new BoxLayout(leftBottomPanel,BoxLayout.X_AXIS));

            leftBottomPanel.add(back);
            JPanel bottomPanel = new JPanel();
            bottomPanel.add(leftBottomPanel);
            bottomPanel.add(new JLabel("               "));
            bottomPanel.add(btnReturn);

            add(bottomPanel,BorderLayout.SOUTH);

            Color color = Color.white;

            bottomPanel.setBackground(color);
            leftBottomPanel.setBackground(color);


            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);

        }
        catch (SQLException | ExceptionNotFound exp){
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);

        }


    }


    public void addBtnReturnuActionListener(ActionListener e){
        btnReturn.addActionListener(e);
    }
    public String getBookId(){
        return id.get(table.getSelectedRow());
    }

}
