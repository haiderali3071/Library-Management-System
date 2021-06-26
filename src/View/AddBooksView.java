package View;

import Model.CustomTextField;
import Model.ExceptionEmptyTextField;
import Model.WindowListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.*;

public class AddBooksView extends JFrame {

    private JPanel contentPane;
    private JComboBox<String> comboBox;
    private String[] categories = {"Computer", "Electronics", "Electrical", "Civil", "Mechanical","Architecture"};
    private JLabel title;
    private JLabel label;
    private CustomTextField bookID;
    private CustomTextField bookName;
    private CustomTextField author;
    private CustomTextField quantity;
    private CustomTextField price;
    private CustomTextField rackNo;
    private JButton submit;

    public static void main(String[] args) {
        AddBooksView frame = new AddBooksView();

    }

    public void makeRoom(JPanel contentPane, int l){
        for (int i =0; i<l; i++){
            contentPane.add(new JLabel(" "));
        }
    }

    public AddBooksView() {
        Color color = Color.white;

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener());
        setTitle("Add Books");
        this.setBounds(400,200,430,330);
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
        setLayout(new FlowLayout());
        JPanel panel = new JPanel(new BorderLayout());

        add(panel);
        getContentPane().add(new JLabel(new ImageIcon(new ImageIcon("images/book.JPG").getImage().getScaledInstance(700,500,0))));


        Font f = new Font("Arial",Font.PLAIN,20);

        title = new JLabel("Enter The Information Below\t\t");
        title.setFont(new Font("Arial",Font.PLAIN,30));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
        makeRoom(titlePanel,8);
        titlePanel.add(title);


        panel.add(titlePanel,BorderLayout.NORTH);
        makeRoom(contentPane,3);
        panel.add(contentPane,BorderLayout.CENTER);


        JPanel categoryPanel = new JPanel();
        label = new JLabel("Select Book Category");
        label.setFont(new Font("Arial",Font.PLAIN,25));
        comboBox = new JComboBox<>(categories);
        comboBox.setFont(new Font("Arial",Font.PLAIN,20));
        categoryPanel.add(label);
        categoryPanel.add(comboBox);
        contentPane.add(categoryPanel);


        bookID = new CustomTextField(f);
        bookID.setPlaceholder("Enter Book ID");
        contentPane.add(bookID);

        bookName = new CustomTextField(f);
        bookName.setPlaceholder("Enter Book Name");
        bookName.setFont(new Font("Arial",Font.PLAIN,20));
        contentPane.add(bookName);


        author = new CustomTextField(f);
        author.setPlaceholder("Enter Author Name");
        author.setFont(new Font("Arial",Font.PLAIN,20));
        contentPane.add(author);

        quantity = new CustomTextField(f);
        quantity.setPlaceholder("Enter Quantity");
        quantity.setFont(new Font("Arial",Font.PLAIN,20));

        quantity.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    quantity.setEditable(true);
                } else {
                    quantity.setEditable(false);
                }
            }
        });
        contentPane.add(quantity);

        price = new CustomTextField(f);
        price.setPlaceholder("Enter Price of Book");
        price.setFont(new Font("Arial",Font.PLAIN,20));

        price.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE || ke.getKeyChar() == '.') {
                    price.setEditable(true);
                } else {
                    price.setEditable(false);
                }
            }
        });
        contentPane.add(price);

        rackNo = new CustomTextField(f);
        rackNo.setPlaceholder("Enter Rack No");
        rackNo.setFont(new Font("Arial",Font.PLAIN,20));

        rackNo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    rackNo.setEditable(true);
                } else {
                    rackNo.setEditable(false);
                }
            }
        });
        contentPane.add(rackNo);

        submit = new JButton("Add Book");
        submit.setFont(new Font("Arial",Font.PLAIN,20));
        submit.setIcon(new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(30,30,0)));
        JPanel submitPanel = new JPanel();
        makeRoom(contentPane,5);

        JButton mainMenu = new JButton(" Back ");
        mainMenu.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(50,30,0)));
        mainMenu.setFont(f);
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        submitPanel.add(mainMenu);
        submitPanel.add(submit);
        panel.add(submitPanel,BorderLayout.SOUTH);


        getContentPane().setBackground(color);
        categoryPanel.setBackground(color);
        contentPane.setBackground(color);
        panel.setBackground(color);
        titlePanel.setBackground(color);
        submitPanel.setBackground(color);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    public String getCategory() {
        return categories[comboBox.getSelectedIndex()];
    }

    public String getBookID() throws ExceptionEmptyTextField {
        if (bookID.getText().equals(bookID.getPlaceholder()))
        {
            throw new ExceptionEmptyTextField();
        }
        else {
            return bookID.getText();
        }
    }

    public String getBookName() throws ExceptionEmptyTextField {
        if (bookName.getText().equals(bookName.getPlaceholder()))
        {
            throw new ExceptionEmptyTextField();
        }
        else {
            return bookName.getText();
        }
    }

    public String getAuthor() throws ExceptionEmptyTextField {
        if (author.getText().equals(author.getPlaceholder()))
        {
            throw new ExceptionEmptyTextField();
        }
        else {
            return author.getText();
        }
    }

    public void makeEmptyAllFields(){
        bookName.setPlaceholder("Enter Book Name");
        bookID.setPlaceholder("Enter Book ID");
        author.setPlaceholder("Enter Author Name");
        quantity.setPlaceholder("Enter Quantity");
        price.setPlaceholder("Enter Price f Book");
        rackNo.setPlaceholder("Enter Rack No");
    }

    public int getQuantity() {
        return Integer.parseInt(quantity.getText());
    }

    public double getPrice() {
        return Double.parseDouble(price.getText());
    }

    public int getRackNo() {
        return Integer.parseInt(rackNo.getText());
    }

    public void addSubmitActionListener(ActionListener e) {
        this.submit.addActionListener(e);
    }




}
