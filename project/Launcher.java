package project;
import javax.swing.SwingUtilities;

import project.gui.StartupWindow;

public class Launcher {
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter the width (W) and height (H) of the world: ");
        // int W = scanner.nextInt();
        // int H = scanner.nextInt();
        // scanner.close();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartupWindow();
                // new Simulation(W, H);
            }
        });
    }
}
