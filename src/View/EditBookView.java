package View;

import Controller.EditBookController;
import Model.*;
import Model.WindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditBookView extends JFrame {

    private JPanel verticalPanel;
    private JComboBox<String> comboBox;
    private String[] categories = {"Computer", "Electronics", "Electrical", "Civil", "Mechanical", "Architecture"};
    private CustomTextField bookName;
    private CustomTextField author;
    private CustomTextField quantity;
    private CustomTextField price;
    private CustomTextField rackNo;
    private JLabel bookNameLabel;
    private JLabel authorLabel;
    private JLabel quantityLabel;
    private JLabel priceLabel;
    private JLabel rackNoLabel;
    private JButton submit;
    private String id;
    private JButton mainmenu;

    public static void main(String[] args) {
        EditBookView frame = new EditBookView("1");
    }

    private void makeSpace(JPanel panel, int l){
        for (int i=0; i<l;i++){
            panel.add(new JLabel(" "));
        }
    }

    public EditBookView(String id) {
        Font f = new Font("Arial",Font.PLAIN,20);
        Color color = Color.WHITE;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener());

        this.id = id;
        this.setTitle("This Book ID is \"" + id + "\"");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener());

        JLabel title = new JLabel("Enter the Information Below");
        title.setFont(new Font("Arial",Font.PLAIN,30));
        title.setForeground(Color.BLUE);

        JPanel titlePanel = new JPanel();
        titlePanel.add(title);


        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();

        JLabel categoryLabel = new JLabel("Select New Category ");
        categoryLabel.setFont(f);
        comboBox = new JComboBox<>(categories);
        comboBox.setFont(f);
        panel1.add(categoryLabel);
        panel1.add(comboBox);


        bookNameLabel = new JLabel("Enter New Name    ");
        bookNameLabel.setFont(f);
        bookName = new CustomTextField(50,f);
        panel2.add(bookNameLabel);
        panel2.add(bookName);


        authorLabel = new JLabel("Enter New Author   ");
        authorLabel.setFont(f);
        author = new CustomTextField(10,f);
        panel3.add(authorLabel);
        panel3.add(author);


        quantityLabel = new JLabel("Enter New Quantity");
        quantityLabel.setFont(f);
        quantity = new CustomTextField(10,f);
        quantity.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    quantity.setEditable(true);
                } else {
                    quantity.setEditable(false);
                }
            }
        });
        panel4.add(quantityLabel);
        panel4.add(quantity);


        priceLabel = new JLabel("Enter New Price     ");
        priceLabel.setFont(f);
        price = new CustomTextField(10,f);
        price.setBounds(143, 180, 155, 29);
        price.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE || ke.getKeyChar() == '.') {
                    price.setEditable(true);
                } else {
                    price.setEditable(false);
                }
            }
        });
        panel5.add(priceLabel);
        panel5.add(price);


        rackNoLabel = new JLabel("Enter New Rack No");
        rackNoLabel.setFont(f);
        rackNo = new CustomTextField(10,f);
        rackNo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    rackNo.setEditable(true);
                } else {
                    rackNo.setEditable(false);
                }
            }
        });
        panel6.add(rackNoLabel);
        panel6.add(rackNo);

        submit = new JButton("Update Book");
        submit.setFont(new Font("Arial",Font.PLAIN,20));
        submit.setIcon(new ImageIcon(new ImageIcon("images/yes2.JPG").getImage().getScaledInstance(30,30,0)));



        verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel,BoxLayout.Y_AXIS));
        makeSpace(verticalPanel,10);
        verticalPanel.add(titlePanel);
        makeSpace(verticalPanel,2);
        verticalPanel.add(panel1);
        verticalPanel.add(panel2);
        verticalPanel.add(panel3);
        verticalPanel.add(panel4);
        verticalPanel.add(panel5);
        verticalPanel.add(panel6);
        makeSpace(verticalPanel,2);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submit);

        JPanel panelHoldsVerticalPanel = new JPanel(new BorderLayout());
        panelHoldsVerticalPanel.add(verticalPanel,BorderLayout.CENTER);

        panelHoldsVerticalPanel.add(bottomPanel,BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel();
        mainPanel.add(panelHoldsVerticalPanel);
        mainPanel.add(new JLabel(new ImageIcon(new ImageIcon("images/book.JPG").getImage().getScaledInstance(700,500,0))));


        JButton back = new JButton("Go Back");
        back.setFont(new Font("Arial",Font.PLAIN,20));
        back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(30,20,0)));




        back = new JButton();
        back.setFont(new Font("Arial",Font.PLAIN,20));
        back.setIcon(new ImageIcon(new ImageIcon("images/back3.JPG").getImage().getScaledInstance(40,40,0)));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        mainmenu = new JButton();
        mainmenu.setIcon(new ImageIcon(new ImageIcon("images/home1.JPG").getImage().getScaledInstance(40,40,0)));



        JPanel belowPanel = new JPanel();
        add(belowPanel, BorderLayout.SOUTH);
        belowPanel.setLayout(new BoxLayout(belowPanel,BoxLayout.X_AXIS));

        belowPanel.add(back);
        belowPanel.add(mainmenu);


        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        add(belowPanel,BorderLayout.SOUTH);



//      Set Background Color
        getContentPane().setBackground(color);
        mainPanel.setBackground(color);
        verticalPanel.setBackground(color);
        titlePanel.setBackground(color);
        panel1.setBackground(color);
        panel2.setBackground(color);
        panel3.setBackground(color);
        panel4.setBackground(color);
        panel5.setBackground(color);
        panel6.setBackground(color);
        bottomPanel.setBackground(color);
        belowPanel.setBackground(Color.white);

        //Fetch data from Books database

        try {
            BooksDatabase booksDatabase = new BooksDatabase();
            ResultSet resultSet = booksDatabase.getListofBooks();
            String cat = null;
            String name = null;
            String auth = null;
            String quan = null;
            String price = null;
            String rack = null;
            while (resultSet.next()) {
                String ID = resultSet.getString("ID");
                if (ID.equals(id)) {
                    cat = resultSet.getString("cat");
                    name = resultSet.getString("name");
                    auth = resultSet.getString("auth");
                    quan = String.valueOf(resultSet.getInt("quan"));
                    price = String.valueOf(resultSet.getDouble("price"));
                    rack = String.valueOf(resultSet.getInt("rack"));
                    break;
                }
            }
            resultSet.close();
            comboBox.setSelectedItem(cat);
            bookName.setPlaceholder(name);
            author.setPlaceholder(auth);
            quantity.setPlaceholder(quan);
            this.price.setPlaceholder(price);
            rackNo.setPlaceholder(rack);
        } catch (SQLException exp) {

            Object[] options = {"OK"};
            Object ans = JOptionPane.showOptionDialog(null,exp.getMessage(),"SQL Exception",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
        }

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    public String getCategory() {
        return categories[comboBox.getSelectedIndex()];
    }

    public String getBookName() {
        return bookName.getText();
    }

    public String getAuthor() {
        return author.getText();
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

    public void addMainListener(ActionListener e ){
        mainmenu.addActionListener(e);
    }

}
