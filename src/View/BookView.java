package View;

import Model.BooksDatabase;
import Model.ExceptionNotFound;
import Model.WindowListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookView extends JFrame{

    private JButton back;
    private JButton mainmenu;

    public BookView(String id) throws ExceptionNotFound {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener());

        BooksDatabase booksDatabase;
        setTitle("Searched Book is Below");
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        add(menuBar);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable();
        table.setFont(new Font("Arial",Font.ITALIC,30));
        table.setRowHeight(50);
        table.setForeground(Color.magenta);

        try {
            booksDatabase = new BooksDatabase();
            if (!booksDatabase.search(id)){
                throw new ExceptionNotFound();
            }
            ResultSet resultSet = booksDatabase.getListofBooks();
            String cat = null;
            String name = null;
            String auth = null;
            String quan = null;
            String price = null;
            String rack = null;
            while (resultSet.next()) {
                String ID = resultSet.getString("ID");
                if (ID.equals(id)){
                     cat = resultSet.getString("cat");
                     name = resultSet.getString("name");
                     auth = resultSet.getString("auth");
                     quan = String.valueOf(resultSet.getInt("quan"));
                     price = String.valueOf(resultSet.getDouble("price"));
                     rack = String.valueOf(resultSet.getInt("rack"));
                    break;
                }
            }

            model.addColumn(" ");
            model.addColumn(" ");
            model.addRow(new Object[]{"Category",cat});
            model.addRow(new Object[]{"Book ID",id});
            model.addRow(new Object[]{"Book Name",name});
            model.addRow(new Object[]{"Author Name",auth});
            model.addRow(new Object[]{"Quantity",quan});
            model.addRow(new Object[]{"Book Price",price});
            model.addRow(new Object[]{"Rack No",rack});

            table.setModel(model);
            JScrollPane sp = new JScrollPane(table);
            add(sp, BorderLayout.CENTER);

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

        }  catch (SQLException exp){
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


    public static void main(String [] a) throws ExceptionNotFound {
        new BookView("4");
    }
}
