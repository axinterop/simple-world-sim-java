package project.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.simulation.Simulation;

public class StartupWindow extends JFrame implements ActionListener {

    JPanel panel;

    JButton startSimB;
    JButton loadSimB;
    JButton endProgramB;;

    JTextField widthTF;
    JTextField heightTF;

    public StartupWindow() {
        setTitle("PG - Simulation in Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 400);
        setLocationRelativeTo(null);

        preparePanel();
        prepareTextFields();
        prepareButtons();

        JLabel labelW = new JLabel("Width:");
        JLabel labelH = new JLabel("Height:");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelW, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(widthTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelH, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(heightTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(startSimB, gbc);

        gbc.gridy = 3;
        panel.add(loadSimB, gbc);

        gbc.gridy = 4;
        panel.add(endProgramB, gbc);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void preparePanel() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(200, 350));
    }

    private void prepareTextFields() {
        widthTF = new JTextField(15);
        heightTF = new JTextField(15);

        KeyListener kl = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        };

        widthTF.addKeyListener(kl);
        heightTF.addKeyListener(kl);
    }

    private void prepareButtons() {
        startSimB = new JButton("Start simulation");
        startSimB.addActionListener(this);

        loadSimB = new JButton("Load simulation");
        loadSimB.addActionListener(this);

        endProgramB = new JButton("Exit");
        endProgramB.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedB = (JButton) e.getSource();

        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // int x = (screenSize.width - .getWidth()) / 2;
        // int y = (screenSize.height - newFrame.getHeight()) / 2;
        // newFrame.setLocation(x, y);

        if (clickedB == startSimB) {
            int W = Integer.parseInt(widthTF.getText());
            int H = Integer.parseInt(heightTF.getText());
            W *= 20;
            H *= 20;
            new Simulation(W, H, false);
            dispose();
        } else if (clickedB == loadSimB) {
            new Simulation(0, 0, true);
            dispose();
        } else if (clickedB == endProgramB) {
            System.exit(0);
        }
    }

}
