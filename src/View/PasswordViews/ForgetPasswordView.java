package View.PasswordViews;

import Model.ExceptionEmptyTextField;
import Model.WindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ForgetPasswordView extends JFrame {



    private JButton back;
    private JButton next;
    private JTextField SQAns;


    private void makeRoom(JPanel contentPane, int l){
        for (int i =0; i<l; i++){
            contentPane.add(new JLabel(" "));
        }
    }

    public ForgetPasswordView(){
        Color color = Color.white;
        Font f = new Font("Arial",Font.PLAIN,20);

        this.setTitle("Forget Password");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener());
        setLayout(new BorderLayout());
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
        JPanel panel = new JPanel(new BorderLayout());

        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(color);
        add(mainPanel);

        mainPanel.add(panel);
        //It is to make GUI better. It is used to create space.
        mainPanel.add(new JLabel("                                                  "));
        mainPanel.add(new JLabel(new ImageIcon(new ImageIcon("images/lock.JPG").getImage().getScaledInstance(400,450,0))));



        JLabel title = new JLabel("What is your favourite pet name? ");
        title.setFont(new Font("Arial",Font.PLAIN,25));

        JLabel SQLabel = new JLabel("Security Question to recover forget Password");
        SQLabel.setFont(new Font("Arial",Font.PLAIN,30));

        SQAns = new JTextField();
        SQAns.setFont(f);

        panel.add(contentPane,BorderLayout.CENTER);


        makeRoom(contentPane,8);
        contentPane.add(SQLabel);
        makeRoom(contentPane,1);
        contentPane.add(title);
        makeRoom(contentPane,1);
        contentPane.add(SQAns);

        makeRoom(contentPane,3);

        next = new JButton("next");
        next.setForeground(Color.BLUE);
        next.setFont(new Font("Arial",Font.PLAIN,25));



        JPanel bottomPanel = new JPanel();
        bottomPanel.add(next);
        panel.add(bottomPanel,BorderLayout.SOUTH);

        getContentPane().setBackground(color);
        contentPane.setBackground(color);
        panel.setBackground(color);
        bottomPanel.setBackground(color);

        back = new JButton("Go Back");
        back.setFont(new Font("Arial",Font.PLAIN,20));
        back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(30,20,0)));

        JPanel belowPanel = new JPanel();
        add(belowPanel, BorderLayout.SOUTH);
        belowPanel.setLayout(new BoxLayout(belowPanel,BoxLayout.X_AXIS));
        belowPanel.add(back);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }



    public String getSecurityAnswer() throws ExceptionEmptyTextField {
        if (!SQAns.getText().equals("")){
            return SQAns.getText();
        }
        else {
            throw new ExceptionEmptyTextField();
        }
    }
    public void addBackBtnActionListener(ActionListener e){
        back.addActionListener(e);
    }


    public void addNextBtnActionListener(ActionListener e){
        next.addActionListener(e);
    }


    public static void main(String[] args){
        new ForgetPasswordView();
    }
}
