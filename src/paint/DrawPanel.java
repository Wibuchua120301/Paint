package paint;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {
    public DrawPanel() {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#708090"), 5), // viền bên ngoài
                BorderFactory.createLineBorder(Color.white, 1))); // viền bên trong
        setBackground(Color.white);
    }

}
