package View;

import Model.BooksDatabase;
import Model.WindowListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksListView extends JFrame{

    private JButton back;

    public BooksListView() {

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener());
        setTitle("All Books");
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        add(menuBar);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable();

        table.setFont(new Font("Arial",Font.PLAIN,20));
        table.setRowHeight(40);


        model.addColumn("Category");
        model.addColumn("Book ID");
        model.addColumn("Book Name");
        model.addColumn("Author Name");
        model.addColumn("Quantity");
        model.addColumn("Book Price");
        model.addColumn("Rack No");

        try {
            BooksDatabase booksDatabase = new BooksDatabase();
            ResultSet resultSet = booksDatabase.getListofBooks();

            while (resultSet.next()) {
                String cat = resultSet.getString("cat");
                String ID = resultSet.getString("ID");
                String name = resultSet.getString("name");
                String auth = resultSet.getString("auth");
                String quan = String.valueOf(resultSet.getInt("quan"));
                String price = String.valueOf(resultSet.getDouble("price"));
                String rack = String.valueOf(resultSet.getInt("rack"));
                model.addRow(new Object[]{cat,ID,name,auth,quan,price,rack});
            }
            resultSet.close();
            table.setModel(model);

            JScrollPane sp = new JScrollPane(table);
            add(sp, BorderLayout.CENTER);
            back = new JButton("Go Back");
            back.setFont(new Font("Arial",Font.PLAIN,20));
            back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(30,20,0)));

            JPanel belowPanel = new JPanel();
            add(belowPanel, BorderLayout.SOUTH);
            belowPanel.setLayout(new BoxLayout(belowPanel,BoxLayout.X_AXIS));
            belowPanel.add(back);

            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
        }
        catch (SQLException exp) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
        }


    }


  public void addBackActionListener(ActionListener e){
        back.addActionListener(e);
    }

}
