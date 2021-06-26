package View;

import Model.CustomTextField;
import Model.ExceptionEmptyTextField;
import Model.WindowListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddStudentsView extends JFrame{

    private JPanel contentPane;
    private CustomTextField stdName;
    private JComboBox<String> yearBox;
    private JComboBox<String> departmentBox;
    private String[] years = {"FA12", "SP13", "FA13", "SP14", "FA14", "SP15", "FA15", "SP16", "FA16", "SP17", "FA17", "SP18", "FA18", "SP19", "FA19"};
    private String[] departments = {"BAF", "BAR", "BSE", "BBA", "BCE", "BCS", "BDE", "BEC", "BEE", "BEL", "BET", "BFA", "BPH"};
    private JButton addStdBtn;
    private JLabel heading;
    private CustomTextField rollNo;

    public static void main(String[] args){
        new AddStudentsView();
    }

    private void makeSpace(JPanel panel, int l){
        for (int i=0; i<l;i++){
            panel.add(new JLabel(" "));
        }
    }

    public AddStudentsView() {



        Color color = Color.WHITE;
        Font f = new Font("Arial",Font.PLAIN,25);
        setTitle("Add Student");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener());
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel,BoxLayout.Y_AXIS));
        panel.add(verticalPanel,BorderLayout.CENTER);


        JLabel title = new JLabel("    Enter Student Name");
        title.setFont(f);

        panel.add(title,BorderLayout.NORTH);
        makeSpace(verticalPanel,1);

        JPanel mainPanel = new JPanel(new FlowLayout());
        add(mainPanel,BorderLayout.CENTER);

        mainPanel.add(panel);
        mainPanel.add(new JLabel("                                      "));
        mainPanel.add(new JLabel(new ImageIcon(new ImageIcon("images/student1.JPG").getImage().getScaledInstance(600,600,0))));

        stdName = new CustomTextField(13,f);
        verticalPanel.add(stdName);
        makeSpace(verticalPanel,1);
        stdName.setPlaceholder("Student Name");

        JPanel HorizontalPanel = new JPanel();
        HorizontalPanel.setLayout(new BoxLayout(HorizontalPanel,BoxLayout.X_AXIS));

        yearBox = new JComboBox<>(years);
        yearBox.setFont(new Font("Arial",Font.PLAIN,18));
        departmentBox = new JComboBox<>(departments);
        departmentBox.setFont(new Font("Arial",Font.PLAIN,18));
        rollNo = new CustomTextField(new Font("Arial",Font.PLAIN,20));
        rollNo.setPlaceholder("No");
        //set roll No Charactors Limit
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


        HorizontalPanel.add(yearBox);
        HorizontalPanel.add(departmentBox);
        HorizontalPanel.add(rollNo);

        verticalPanel.add(HorizontalPanel);
        makeSpace(verticalPanel,2);

        addStdBtn = new JButton("Add Student");
        addStdBtn.setFont(new Font("Arial",Font.PLAIN,20));
        addStdBtn.setIcon(new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(27,27,0)));
        JPanel belowPanel = new JPanel(new FlowLayout());
        belowPanel.add(addStdBtn);
        panel.add(belowPanel,BorderLayout.SOUTH);

        panel.setBackground(color);
        verticalPanel.setBackground(color);
        HorizontalPanel.setBackground(color);
        getContentPane().setBackground(color);
        belowPanel.setBackground(color);
        mainPanel.setBackground(color);

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



        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public String getStdName() throws ExceptionEmptyTextField {
        if (stdName.getText().equals(stdName.getPlaceholder()))
        {
            throw new ExceptionEmptyTextField();
        }
        else{
            return stdName.getText();
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
        this.addStdBtn.addActionListener(e);
    }

    public void makeEmptyAllFields(){
        stdName.setPlaceholder("Enter Student Name");
        rollNo.setPlaceholder("No");
    }
}
