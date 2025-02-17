package ui;

import javax.swing.*;
import java.awt.*;

//Represent a splash screen
public class SplashScreen extends JFrame {

    //Constructor
    //EFFECTS: create splash screen
    public SplashScreen() {
        super("Welcome~");
        JLabel label = new JLabel(new ImageIcon(
                "/Users/yingmingsha/Desktop/CPSC210/project_r0c5s/src/main/ui/splash.gif"));
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        setSize(700, 467);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //EFFECTS: show the splash screen by giving duration
    public void showSplashScreen(int duration) {
        setVisible(true);

        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setVisible(false);
        dispose();
    }
}
