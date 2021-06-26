package Controller;

import View.MainMenuView;
import View.StudentsListView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentsListController {

    private StudentsListView studentsListView;

    public StudentsListController(StudentsListView studentsListView) {
        this.studentsListView = studentsListView;
        studentsListView.addBackActionListener(new BackActionListener());
    }


    private class BackActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            studentsListView.dispose();
        }
    }

}
