package project.simulation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.gui.LogWindow;
import project.gui.SimulationPanel;

public class Simulation implements ActionListener {
    private JFrame main_window;
    private LogWindow log_window;
    private SimulationPanel simPanel;
    private JLabel organismsAmount = new JLabel();
    private JLabel turnsPassed = new JLabel();

    JButton nextTurnB = new JButton("Next turn");
    JButton saveSimB = new JButton("Save simulation");

    public Simulation(int w, int h, boolean load) {
        main_window = new JFrame();
        log_window = new LogWindow();

        main_window.setTitle("PG - Simulation in Java");
        main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_window.setLocationRelativeTo(null);
        main_window.setLayout(new BorderLayout());
        main_window.setResizable(false);

        nextTurnB.addActionListener(this);
        saveSimB.addActionListener(this);

        Rectangle sim_area = new Rectangle(0, 0, w, h);
        simPanel = new SimulationPanel(sim_area, load);

        if (load) {
            sim_area = simPanel.world.world_area;
        }

        simPanel.setPreferredSize(new Dimension(sim_area.width, sim_area.height));
        simPanel.setBounds(sim_area);
        simPanel.setBackground(Color.black);
        simPanel.setLayout(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(nextTurnB);
        topPanel.add(saveSimB);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(turnsPassed);
        bottomPanel.add(organismsAmount);

        main_window.add(topPanel, BorderLayout.NORTH);
        main_window.add(simPanel, BorderLayout.CENTER);
        main_window.add(bottomPanel, BorderLayout.SOUTH);

        simPanel.setActions();
        simPanel.setLogger(log_window.logger);

        loadWorldInfo();

        main_window.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - main_window.getWidth()) / 2;
        int y = (screenSize.height - main_window.getHeight()) / 2;
        main_window.setLocation(x, y);

        main_window.setVisible(true);
        log_window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedB = (JButton) e.getSource();
        if (clickedB == nextTurnB) {
            simPanel.run();
            loadWorldInfo();;
        }
        else if (clickedB == saveSimB) {
            System.out.println("save game");
            simPanel.save();
        }
    }

    public void loadWorldInfo() {
        turnsPassed.setText("Turns passed: " + Integer.toString(simPanel.world.turnsNum));
        organismsAmount.setText("Organisms alive: " + Integer.toString(simPanel.world.organisms.size()));
    }
}
