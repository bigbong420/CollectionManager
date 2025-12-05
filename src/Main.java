/*
 * Main.java
 * purpose: entry point for the music collection manager application
 *          launches the swing gui on the event dispatch thread
 * author: phin
 */

import gui.CollectionManagerGUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// main class - kicks off the whole application
public class Main {

    public static void main(String[] args) {
        // try to set a nicer look and feel
        try {
            // use system look and feel for native appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // if that fails, just use default
            System.out.println("couldn't set system look and feel, using default");
        }

        // launch the gui on the event dispatch thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // create and show the main window
                new CollectionManagerGUI();
            }
        });
    }
}
