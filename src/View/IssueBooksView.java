package View;

import Model.WindowListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IssueBooksView extends JFrame {

    private JButton btnIssueABook;
    private JButton btnViewIssueBook;
    private JButton btnReturnBook;

    public static void main(String[] args) {
        IssueBooksView frame = new IssueBooksView();
        frame.setVisible(true);
    }

    public IssueBooksView() {
        Font f = new Font("Arial",Font.PLAIN,25);
        setTitle("Issue Books");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener());
        JPanel contentPane = new JPanel(new GridLayout(3, 1));
        add(contentPane);


        btnIssueABook = new JButton("Issue A Book");
        btnIssueABook.setFont(f);
        btnIssueABook.setIcon(new ImageIcon(new ImageIcon("images/book.JPG").getImage().getScaledInstance(300,250,0)));
        contentPane.add(btnIssueABook);

        btnReturnBook = new JButton("Return A Book");
        btnReturnBook.setFont(f);
        btnReturnBook.setIcon(new ImageIcon(new ImageIcon("images/book.JPG").getImage().getScaledInstance(300,250,0)));
        contentPane.add(btnReturnBook);

        btnViewIssueBook = new JButton("View Issued Book");
        btnViewIssueBook.setFont(f);
        btnViewIssueBook.setIcon(new ImageIcon(new ImageIcon("images/book.JPG").getImage().getScaledInstance(300,250,0)));

        contentPane.add(btnViewIssueBook);


        JButton back = new JButton("Go Back");
        back.setFont(new Font("Arial",Font.PLAIN,20));
        back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(30,20,0)));

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel backbtnPanel = new JPanel();
        backbtnPanel.setLayout(new BoxLayout(backbtnPanel,BoxLayout.X_AXIS));
        backbtnPanel.add(back);

        add(backbtnPanel,BorderLayout.SOUTH);
        backbtnPanel.setBackground(Color.white);



        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    public void addBtnIssueABookActionListener(ActionListener e) {
        btnIssueABook.addActionListener(e);
    }

    public void addBtnViewIssueBookActionListener(ActionListener e) {
        btnViewIssueBook.addActionListener(e);
    }

    public void btnReturnABookActionListener(ActionListener e) {
        btnReturnBook.addActionListener(e);
    }


}
