package Model;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowListener extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {


        Object[] options = {"No", "Yes"};
        Object ans = JOptionPane.showOptionDialog(null, "Are you sure, you want to exit?", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        switch ((int) ans) {
            case 0:
                break;
            case 1:
                System.exit(0);
        }
    }
}
