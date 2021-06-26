package Controller;

import View.BooksListView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BooksListController {

    private BooksListView booksListView;

    public BooksListController(BooksListView booksListView) {
        this.booksListView = booksListView;
        booksListView.addBackActionListener(new BackActionListener());
    }


    private class BackActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            booksListView.dispose();
        }
    }

}
