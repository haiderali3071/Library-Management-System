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

public class BookIDView extends JFrame{

    private CustomTextField bookID;
    private JButton search;
    private JButton mainmenu;

    public static void main(String[] args){
        BookIDView bookIDView = new BookIDView("head");
    }

    private void makeRoom(JPanel contentPane, int l){
        for (int i =0; i<l; i++){
            contentPane.add(new JLabel(" "));
        }
    }

    private boolean isImage(String s){
        StringTokenizer tokenizer = new StringTokenizer(s,".");
        while (tokenizer.hasMoreTokens()){
            if (tokenizer.nextToken().equals("JPG")){
                return true;
            }
        }
        return false;
    }

    public BookIDView(String imageName){
        Color color = Color.white;
        Font f = new Font("Arial",Font.PLAIN,20);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener());
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
        JPanel panel = new JPanel(new BorderLayout());


        JLabel title = new JLabel(" Enter Book ID? ");
        title.setFont(new Font("Arial",Font.PLAIN,25));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
        makeRoom(titlePanel,10);
        titlePanel.add(title);

        panel.add(titlePanel,BorderLayout.NORTH);
        makeRoom(contentPane,1);
        panel.add(contentPane,BorderLayout.CENTER);

        bookID = new CustomTextField(f);
        bookID.setPlaceholder("Enter Book ID");
        contentPane.add(bookID);

        search = new JButton("Next");
        search.setFont(f);
        search.setIcon(new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(30,30,0)));


        JPanel btnPanel = new JPanel();
        btnPanel.add(search);
        panel.add(btnPanel,BorderLayout.SOUTH);



        JButton back = new JButton();
        back.setFont(new Font("Arial",Font.PLAIN,20));
        back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(40,40,0)));

        mainmenu = new JButton();
        mainmenu.setIcon(new ImageIcon(new ImageIcon("images/home1.JPG").getImage().getScaledInstance(40,40,0)));

        JPanel belowPanel = new JPanel();
        add(belowPanel, BorderLayout.SOUTH);
        belowPanel.setLayout(new BoxLayout(belowPanel,BoxLayout.X_AXIS));

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        if (!imageName.equals("issue")){
            back.setText("Go Back");
            back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(30,20,0)));
            belowPanel.add(back);
        }
        else {
            belowPanel.add(back);
            belowPanel.add(mainmenu);
        }


        JPanel mainPanel = new JPanel();

        mainPanel.add(panel);
        //It is to make GUI better. It is used to create space.
        mainPanel.add(new JLabel("                                                  "));

        if (isImage(imageName)){
            mainPanel.add(new JLabel(new ImageIcon(new ImageIcon(imageName).getImage().getScaledInstance(700,500,0))));
        }
        else {
            mainPanel.add(new JLabel(new ImageIcon(new ImageIcon("images/search1.JPG").getImage().getScaledInstance(700,500,0))));
        }


        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        add(belowPanel,BorderLayout.SOUTH);


        getContentPane().setBackground(color);
        contentPane.setBackground(color);
        panel.setBackground(color);
        titlePanel.setBackground(color);
        btnPanel.setBackground(color);
        mainPanel.setBackground(color);
//        belowPanel.setBackground(color);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    public void addSearchIDListener(ActionListener e) {
        search.addActionListener(e);
    }
    public void addMainMenuListener(ActionListener e) {
        mainmenu.addActionListener(e);
    }

    public String  getBookID() throws ExceptionEmptyTextField {
       if (bookID.getPlaceholder().equals(bookID.getText())){
           throw new ExceptionEmptyTextField();
       }
       else {
           return bookID.getText();
       }
    }

    public void setButtonName(String text){
        search.setText(text);
    }

    public void makeAllFieldsEmpty(){
        bookID.setPlaceholder("Enter Book ID");
    }


}
