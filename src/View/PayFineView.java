package View;
import Model.WindowListener;

import javax.swing.*;

public class PayFineView extends JFrame {

    public PayFineView(){
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener());
        setTitle("Pay Fine");

    }
}
