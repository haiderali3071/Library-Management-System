package View.PasswordViews;

import Model.WindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PasswordView extends JFrame {

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel;
    private JButton unlock;
    private JButton forget;
    private JPasswordField passwordField;

    public PasswordView()  {

        getContentPane().add(new JLabel(new ImageIcon(new ImageIcon("images/passwordBack.JPG").getImage().getScaledInstance(700,500,0))));
        setTitle("Library Management System");
        Color color = Color.white;
        getContentPane().setBackground(color);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener());
        panel1 = new JPanel();
        panel1.add(new JLabel("Enter Password"));
        passwordField = new JPasswordField(20);
        unlock = new JButton("Unlock");
        ImageIcon imageIcon = new ImageIcon("images/lock.JPG");
        unlock.setIcon(new ImageIcon(new ImageIcon("images/lock.JPG").getImage().getScaledInstance(20,20,0)));
        panel1.add(passwordField);
        panel1.add(unlock);

        panel2 = new JPanel();
        forget = new JButton("Forget Password");
        forget.setIcon(new ImageIcon(new ImageIcon("images/key.JPG").getImage().getScaledInstance(20,20,0)));
        panel2.add(forget);
        panel1.setBackground(color);
        panel2.setBackground(color);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(panel1);
        panel.add(panel2);

        add(panel,BorderLayout.NORTH);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }


    public void addUnlockActionListener(ActionListener e){
        this.unlock.addActionListener(e);
    }

    public void addForgetActionListener(ActionListener e){
        this.forget.addActionListener(e);
    }
    public char[] getPassword(){
        return passwordField.getPassword();
    }
}
