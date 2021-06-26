package Model;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

final public class CustomTextField extends JTextField {

    private Font originalFont;
    private Color originalForeground;
    private Color placeholderForeground = new Color(160, 160, 160);
    private boolean textWrittenIn;
    private int limit;
    private String custText;
    private int x;

    public CustomTextField() {
        super();
        limit = 50;
    }
    public CustomTextField(int c) {
        super(c);
        limit = 50;
    }
    public CustomTextField(Font f) {
        super();
        originalFont = f;
        limit = 50;
    }

    public CustomTextField(int c, Font f) {
        super(c);
        originalFont = f;
        limit = 50;
    }

    public void setTextLimit(int i){
        this.limit = i;
    }

    private Color getPlaceholderForeground() {
        return placeholderForeground;
    }

    private boolean isTextWrittenIn() {
        return textWrittenIn;
    }

    private void setTextWrittenIn(boolean textWrittenIn) {
        this.textWrittenIn = textWrittenIn;
    }

    public String  getPlaceholder(){
        return custText;
    }
    public void setPlaceholder(final String text) {

        this.custText = text;
        this.customizeText(text);

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                if (getText().trim().length() != 0) {
                    setFont(originalFont);
                    setForeground(originalForeground);
                    setTextWrittenIn(true);
                }

            }
        });

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!isTextWrittenIn()) {
                    setText("");
                    setForeground(Color.black);
                    setFont(originalFont);
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().trim().length() == 0) {
                    customizeText(text);
                }
            }

        });

    }

    private void customizeText(String text) {
        setText(text);
        setFont(originalFont);
        setForeground(getPlaceholderForeground());
        setTextWrittenIn(false);
    }

    @Override
    protected Document createDefaultModel() {
        return new LimitDocument();
    }

    private class LimitDocument extends PlainDocument {

        @Override
        public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
            if (str == null) return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }

    }
}