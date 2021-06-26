package View;

import Model.CustomTextField;
import Model.ExceptionEmptyTextField;
import Model.WindowListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class BookNameView extends JFrame{

    private CustomTextField bookName;
    private JButton search;

    public static void main(String[] args){
        BookNameView bookNameView = new BookNameView();
    }

    private void makeRoom(JPanel contentPane, int l){
        for (int i =0; i<l; i++){
            contentPane.add(new JLabel(" "));
        }
    }


    public BookNameView(){
        this.setTitle("Search Book");

        Color color = Color.white;
        Font f = new Font("Arial",Font.PLAIN,20);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener());
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
        JPanel panel = new JPanel(new BorderLayout());


        JLabel title = new JLabel(" Enter Book Name? ");
        title.setFont(new Font("Arial",Font.PLAIN,25));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
        makeRoom(titlePanel,10);
        titlePanel.add(title);

        panel.add(titlePanel,BorderLayout.NORTH);
        makeRoom(contentPane,1);
        panel.add(contentPane,BorderLayout.CENTER);

        bookName = new CustomTextField(f);
        bookName.setPlaceholder("Enter Book Name");
        contentPane.add(bookName);

        search = new JButton("Next");
        search.setFont(f);
        search.setIcon(new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(30,30,0)));


        JPanel btnPanel = new JPanel();
        btnPanel.add(search);
        panel.add(btnPanel,BorderLayout.SOUTH);



        JPanel mainPanel = new JPanel();

        mainPanel.add(panel);
        //It is to make GUI better. It is used to create space.
        mainPanel.add(new JLabel("                                                  "));
        mainPanel.add(new JLabel(new ImageIcon(new ImageIcon("images/search1.JPG").getImage().getScaledInstance(700,500,0))));


        JButton back = new JButton("Go Back");
        back.setFont(new Font("Arial",Font.PLAIN,20));
        back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(30,20,0)));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel belowPanel = new JPanel();
        belowPanel.setLayout(new BoxLayout(belowPanel,BoxLayout.X_AXIS));
        belowPanel.add(back);

        add(mainPanel,BorderLayout.CENTER);
        add(belowPanel, BorderLayout.SOUTH);


        getContentPane().setBackground(color);
        contentPane.setBackground(color);
        panel.setBackground(color);
        titlePanel.setBackground(color);
        btnPanel.setBackground(color);
        mainPanel.setBackground(color);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    public void addSearchNameListener(ActionListener e) {
        search.addActionListener(e);
    }

    public String getBookName() throws ExceptionEmptyTextField {
        if (bookName.getPlaceholder().equals(bookName.getText())){
            throw new ExceptionEmptyTextField();
        }
        else {
            return bookName.getText().toLowerCase();
        }
    }


}
