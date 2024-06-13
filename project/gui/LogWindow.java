package project.gui;

import java.awt.Font;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogWindow extends JFrame {
    public final Logger logger = Logger.getLogger(LogWindow.class.getName());

    public LogWindow() {
        JTextArea textArea = new JTextArea();
        Font currentFont = textArea.getFont();
        float newFontSize = 16f;
        Font newFont = currentFont.deriveFont(newFontSize); 
        textArea.setFont(newFont);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        TextAreaLogHandler handler = new TextAreaLogHandler(textArea);
        logger.addHandler(handler);

        
        setTitle("Logger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 900);
    }
}
