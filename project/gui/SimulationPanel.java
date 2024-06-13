package project.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumSet;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import project.simulation.Animal;
import project.simulation.ORGANISM_E;
import project.simulation.Organism;
import project.simulation.PLAYER_ACTION;
import project.simulation.World;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

public class SimulationPanel extends JPanel {

    public World world;
    private Logger logger;

    private int grid_offset = 20;

    Action upAction;
    Action downAction;
    Action rightAction;
    Action leftAction;
    Action powerAction;
    Action noAction;

    JPopupMenu popupMenu = new JPopupMenu();

    private int menux = 0;
    private int menuy = 0;

    public SimulationPanel(Rectangle sim_area, boolean load) {
        if (load) {
            world = loadWorld();
            if (world == null)
                System.exit(1);
        } else {
            world = new World(sim_area);
        }

        JMenuItem item1 = new JMenuItem("Wolf");
        JMenuItem item2 = new JMenuItem("Sheep"); 
        JMenuItem item3 = new JMenuItem("Fox");
        JMenuItem item4 = new JMenuItem("Turtle");
        JMenuItem item5 = new JMenuItem("Antilope");
        JMenuItem item6 = new JMenuItem("Grass");
        JMenuItem item7 = new JMenuItem("Sonchus");
        JMenuItem item8 = new JMenuItem("Guarana");
        JMenuItem item9 = new JMenuItem("Belladonna");
        JMenuItem item10 = new JMenuItem("H_Sosnowskyi");
        popupMenu.add(item1);
        popupMenu.add(item2);
        popupMenu.add(item3);
        popupMenu.add(item4);
        popupMenu.add(item5);
        popupMenu.add(item6);
        popupMenu.add(item7);
        popupMenu.add(item8);
        popupMenu.add(item9);
        popupMenu.add(item10);
        
        setComponentPopupMenu(popupMenu);
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                menux = e.getX();
                menuy = e.getY();
            }

            public void mousePressed(MouseEvent e) {
                menux = e.getX();
                menuy = e.getY();
            }
        });

        ActionListener menuItemListener = e -> {
            JMenuItem selectedItem = (JMenuItem)e.getSource();
            String selectedText = selectedItem.getText();

            ORGANISM_E o_t = ORGANISM_E.valueOf(selectedText.toUpperCase());
            EnumSet<ORGANISM_E> animals = EnumSet.of(ORGANISM_E.WOLF, ORGANISM_E.SHEEP, ORGANISM_E.FOX, ORGANISM_E.TURTLE, ORGANISM_E.ANTILOPE);
            Point p = new Point(menux - (menux % 10), menuy - (menuy % 10));
            if (animals.contains(o_t)) {
                world.CreateOrganism(o_t, p);
            }
            else {
                world.CreatePlantChunk(o_t, p);
            }
            world.WListener.RecordEvent(selectedText + " has been created");
        };

        item1.addActionListener(menuItemListener);
        item2.addActionListener(menuItemListener);
        item3.addActionListener(menuItemListener);
        item4.addActionListener(menuItemListener);
        item5.addActionListener(menuItemListener);
        item6.addActionListener(menuItemListener);
        item7.addActionListener(menuItemListener);
        item8.addActionListener(menuItemListener);
        item9.addActionListener(menuItemListener);
        item10.addActionListener(menuItemListener);
    
    }

    

    public void setActions() {
        upAction = new UpAction();
        downAction = new DownAction();
        leftAction = new LeftAction();
        rightAction = new RightAction();
        powerAction = new PowerAction();
        noAction = new NoAction();

        JRootPane rootPane = SwingUtilities.getRootPane(this);
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("UP"), "upAction");
        actionMap.put("upAction", upAction);
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        actionMap.put("downAction", downAction);
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        actionMap.put("rightAction", rightAction);
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        actionMap.put("leftAction", leftAction);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "powerAction");
        actionMap.put("powerAction", powerAction);
        inputMap.put(KeyStroke.getKeyStroke("C"), "noAction");
        actionMap.put("noAction", noAction);
    }

    public void setLogger(Logger l) {
        logger = l;
    }

    public void run() {
        update();
        repaint();
    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("world.bin");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(world);
            
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        world.makeTurn();
        if (world.WListener.size() != 0) {
            logger.info("\n- Turn: " + Integer.toString(world.turnsNum));
            while (world.WListener.size() != 0) {
                logger.info(world.WListener.events.poll());
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(Color.red);

        for (int i = world.organisms.size() - 1; i >= 0; i--) {
            Organism this_o = world.organisms.get(i);
            if (this_o.isDead())
                continue;

            int x = this_o.pos.x;
            int y = this_o.pos.y;
            g2.setPaint(this_o.color);
            g2.fillRect(x, y, grid_offset, grid_offset);
            if (this_o instanceof Animal) {
                g2.setColor(Color.WHITE);
                g2.drawRect(x, y, grid_offset, grid_offset);
            }
        }
    }

    public World loadWorld() {
        try {
            FileInputStream fileIn = new FileInputStream("world.bin");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            World resultWorld = (World) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return resultWorld;
        } catch (IOException | ClassNotFoundException e) {
            // logger.log(Level.SEVERE, "world loading interrupted by exception", e);
            System.out.println(e);
            return null;
        }
    }

    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            world.human_action = PLAYER_ACTION.GO_UP;
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            world.human_action = PLAYER_ACTION.GO_DOWN;
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            world.human_action = PLAYER_ACTION.GO_LEFT;
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            world.human_action = PLAYER_ACTION.GO_RIGHT;
        }
    }

    public class PowerAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            world.human_action = PLAYER_ACTION.POWER;
        }
    }
    public class NoAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            world.human_action = PLAYER_ACTION.NO_ACTION;
        }
    }

}
