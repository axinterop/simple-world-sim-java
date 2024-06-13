package project.gui;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TextAreaLogHandler extends Handler {
    private JTextArea textArea;

    public TextAreaLogHandler(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void publish(LogRecord record) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(record.getMessage() + "\n");
        });
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {}
}