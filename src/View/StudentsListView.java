package View;

import Model.StudentsDatabase;
import Model.WindowListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentsListView extends JFrame{


    private JButton back;

    public StudentsListView() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener());
        setTitle("All Students");
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        add(menuBar);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable();
        table.setFont(new Font("Arial",Font.PLAIN,20));
        table.setRowHeight(40);

        model.addColumn("Registration No");
        model.addColumn("Student Name");
        model.addColumn("Fine");
        model.addColumn("Joining Date");


        try {

            StudentsDatabase stdDB = new StudentsDatabase();
            stdDB.updateFineOfAllStudents();
            ResultSet resultSet = stdDB.getListOfStudents();

            while (resultSet.next()) {

                String reg = resultSet.getString("r");
                String name = resultSet.getString("n");
                String date = resultSet.getString("d");

                double fine = resultSet.getDouble("f");

                model.addRow(new Object[]{reg,name,fine,date});
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

    public static void main(String[] uhj){
        new StudentsListView();
    }
}
