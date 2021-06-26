package View.PasswordViews;

import Model.ExceptionEmptyTextField;
import Model.WindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChangePasswordView extends JFrame {


    private JPasswordField oldPass;
    private JPasswordField newPass1;
    private JPasswordField newPass2;
    private JButton changePass;
    private JButton back;


    private void makeRoom(JPanel contentPane, int l){
        for (int i =0; i<l; i++){
            contentPane.add(new JLabel(" "));
        }
    }

    public ChangePasswordView(){
        Color color = Color.white;
        Font f = new Font("Arial",Font.PLAIN,20);

        this.setTitle("Change Password");
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



        JLabel title1 = new JLabel("Enter your old Password ");
        title1.setFont(new Font("Arial",Font.PLAIN,25));

        JLabel title2 = new JLabel("Enter your new Password ");
        title2.setFont(new Font("Arial",Font.PLAIN,25));

        JLabel title3 = new JLabel("Verify your new Password ");
        title3.setFont(new Font("Arial",Font.PLAIN,25));

        panel.add(contentPane,BorderLayout.CENTER);

        oldPass = new JPasswordField(10);
        oldPass.setFont(f);

        newPass1 = new JPasswordField(10);
        newPass1.setFont(f);

        newPass2 = new JPasswordField(10);
        newPass2.setFont(f);

        makeRoom(contentPane,8);

        contentPane.add(title1);
        makeRoom(contentPane,1);
        contentPane.add(oldPass);

        makeRoom(contentPane,2);

        contentPane.add(title2);
        makeRoom(contentPane,1);
        contentPane.add(newPass1);

        makeRoom(contentPane,2);

        contentPane.add(title3);
        makeRoom(contentPane,1);
        contentPane.add(newPass2);
        makeRoom(contentPane,3);

        changePass = new JButton("Change Password");
        changePass.setFont(f);
        changePass.setIcon(new ImageIcon(new ImageIcon("images/lock.JPG").getImage().getScaledInstance(30,30,0)));


        JPanel bottomPanel = new JPanel();
        bottomPanel.add(changePass);
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



    public void addBackBtnActionListener(ActionListener e){
        back.addActionListener(e);
    }

    public void addChangePasswordBtnActionListener(ActionListener e){
        changePass.addActionListener(e);
    }



    public char[] getOldPassword() throws ExceptionEmptyTextField {
        if (oldPass.getPassword().length != 0){
            return oldPass.getPassword();
        }
        else {
            throw new ExceptionEmptyTextField();
        }
    }


    public char[] getNewPassword1() throws ExceptionEmptyTextField {
        if (newPass1.getPassword().length != 0){
            return newPass1.getPassword();
        }
        else {
            throw new ExceptionEmptyTextField();
        }
    }

    public char[] getNewPassword2() throws ExceptionEmptyTextField{
        if (newPass2.getPassword().length != 0){
            return newPass2.getPassword();
        }
        else {
            throw new ExceptionEmptyTextField();
        }
    }


    public static void main(String[] args){
        new ChangePasswordView();
    }
}
