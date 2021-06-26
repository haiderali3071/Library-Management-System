package View;

import Model.CustomTextField;
import Model.ExceptionEmptyTextField;
import Model.WindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegNoView extends JFrame{

    private JComboBox<String> yearBox;
    private JComboBox<String> departmentBox;
    private String[] years = {"FA12", "SP13", "FA13", "SP14", "FA14", "SP15", "FA15", "SP16", "FA16", "SP17", "FA17", "SP18", "FA18", "SP19", "FA19"};
    private String[] departments = {"BAF", "BAR", "BSE", "BBA", "BCE", "BCS", "BDE", "BEC", "BEE", "BEL", "BET", "BFA", "BPH"};
    private JButton btnIssueBook;
    private CustomTextField rollNo;
    private JButton mainmenu;


    public static void main(String[] args){
        RegNoView regNoView = new RegNoView(false);
    }



    public RegNoView(boolean isFineView) {

        Font f = new Font("Arial",Font.PLAIN, 18);
        Color color = Color.WHITE;

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener());

        JLabel heading = new JLabel("Enter Registration No");
        heading.setFont(new Font("Arial",Font.PLAIN, 25));
        JPanel headingPanel = new JPanel();
        headingPanel.add(heading);

        yearBox = new JComboBox<>(years);
        yearBox.setFont(f);

        departmentBox = new JComboBox<>(departments);
        departmentBox.setFont(f);

        rollNo = new CustomTextField(3,f);
        rollNo.setPlaceholder("No");
        rollNo.setTextLimit(3);
        rollNo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    rollNo.setEditable(true);
                } else {
                    rollNo.setEditable(false);
                }
            }
        });
        rollNo.setFont(f);

        btnIssueBook = new JButton("Issue Book");
        btnIssueBook.setFont(new Font("Arial",Font.PLAIN,15));
        btnIssueBook.setIcon(new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(25,25,0)));

        JPanel rollNoPanel = new JPanel();
        rollNoPanel.add(yearBox);
        rollNoPanel.add(departmentBox);
        rollNoPanel.add(rollNo);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));
        makeSpace(topPanel,5);
        topPanel.add(headingPanel);
        makeSpace(topPanel,1);
        topPanel.add(rollNoPanel);
        makeSpace(topPanel,2);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnIssueBook);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel,BorderLayout.CENTER);
        mainPanel.add(btnPanel,BorderLayout.SOUTH);

        setLayout(new BorderLayout());

        add(mainPanel,BorderLayout.NORTH);


        JButton back = new JButton();
        back.setFont(new Font("Arial",Font.PLAIN,20));
        back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(40,40,0)));


        mainmenu = new JButton();
        mainmenu.setIcon(new ImageIcon(new ImageIcon("images/home1.JPG").getImage().getScaledInstance(40,40,0)));

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel belowPanel = new JPanel();
        add(belowPanel, BorderLayout.SOUTH);
        belowPanel.setLayout(new BoxLayout(belowPanel,BoxLayout.X_AXIS));



        if (!isFineView){
            belowPanel.add(back);
            belowPanel.add(mainmenu);
            add(new JLabel(new ImageIcon(new ImageIcon("images/student1.JPG").getImage().getScaledInstance(600,600,0))),BorderLayout.EAST);
            add(new JLabel(new ImageIcon(new ImageIcon("images/book.JPG").getImage().getScaledInstance(700,500,0))),BorderLayout.WEST);
        }
        else {
            back.setText("Go Back");
            back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(30,20,0)));
            belowPanel.add(back);
            add(new JLabel(new ImageIcon(new ImageIcon("images/fine.JPG").getImage().getScaledInstance(600,600,0))),BorderLayout.WEST);
        }




        //setting Color
        getContentPane().setBackground(color);
        mainPanel.setBackground(color);
        btnPanel.setBackground(color);
        topPanel.setBackground(color);
        rollNoPanel.setBackground(color);
        headingPanel.setBackground(color);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private void makeSpace(JPanel p, int l){
        for (int i=0; i<l; i++){
            p.add(new JLabel(" "));
        }
    }

    public String getRegistrationNo() throws ExceptionEmptyTextField {
       if (rollNo.getText().equals(rollNo.getPlaceholder())){
           throw new ExceptionEmptyTextField();
       }
       else {
           return years[yearBox.getSelectedIndex()]+"-"+departments[departmentBox.getSelectedIndex()]+"-"+rollNo.getText();
       }
    }

    public void addBtnIssueBookActionListener(ActionListener e) {
        this.btnIssueBook.addActionListener(e);
    }

    public void addMainMenuListener(ActionListener e){
        mainmenu.addActionListener(e);
    }


   public void changeButtonName(String  s){
        btnIssueBook.setText(s);
   }

    public void makeAllFieldsEmpty(){
       rollNo.setPlaceholder("No");
    }

}
