package paint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PaintApp extends JFrame {
    private DrawPanel drawPanel;
    private Color color = Color.BLACK;
    private boolean eraserMode = false;
    private ImageIcon pencil;
    private ImageIcon eraser;
    private ImageIcon colors;
    private ImageIcon shapes;

    private JPanel toolPanel;

    private JButton pencilButton, eraserButton, colorButton, shapeButton,undoButton,redoButton;

    private JLabel statusBar;

    private char toolMode = 'P';

    private JSlider thicknessSlider;
    private JLabel thicknessLabel;

    public PaintApp() {
        setTitle("Paint App");
        setSize(1200, 650);
        setLocation(50, 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

//        Tạo thanh công cụ
        toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pencil = new ImageIcon(new ImageIcon("src\\img\\1047189-200.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        pencilButton = new JButton(pencil);

        eraser = new ImageIcon(new ImageIcon("src\\img\\eraser_732430.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        eraserButton = new JButton(eraser);

        colors = new ImageIcon(new ImageIcon("src\\img\\color_15109282.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        colorButton = new JButton(colors);

        shapes = new ImageIcon(new ImageIcon("src\\img\\shapes_10335711.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        shapeButton = new JButton(shapes);

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e->drawPanel.undo() );

        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e->drawPanel.redo() );

        thicknessLabel = new JLabel("Độ dày: 5");
        thicknessSlider = new JSlider(1, 20, 5);

        // Thay đổi độ dày của nét vẽ
        thicknessSlider.addChangeListener(e -> {
            int thickness = thicknessSlider.getValue();
            thicknessLabel.setText("Độ dày: " + thickness);
            drawPanel.setThickness(thickness);
        });

        toolPanel.add(pencilButton);
        toolPanel.add(eraserButton);
        toolPanel.add(colorButton);
        toolPanel.add(shapeButton);
        toolPanel.add(undoButton);
        toolPanel.add(redoButton);
        toolPanel.add(thicknessSlider);
        toolPanel.add(thicknessLabel);

        add(toolPanel, BorderLayout.NORTH);

        //  Khu vực vẽ
        drawPanel = new DrawPanel(this);
        add(drawPanel, BorderLayout.CENTER);

        //  Thanh trạng thái
        statusBar = new JLabel(" Ready");

        add(statusBar, BorderLayout.SOUTH);

        // Xử lý sự kiện cho Pencil
        pencilButton.addActionListener(e -> {
            eraserMode = false;
            toolMode = 'P';
            statusBar.setText(" Pencil mode");
        });

        // Xử lý sự kiện cho Eraser
        eraserButton.addActionListener(e -> {
            eraserMode = true;
            toolMode = 'E';
            statusBar.setText(" Eraser mode");
        });

        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Color", color);
            if (newColor != null) {
                color = newColor;
                eraserMode = false;
                statusBar.setText(" Color selected: " + color);
            }
        });

    }

    public char getToolMode() {
        return toolMode;
    }

    public Color getCurrentColor() {
        return color;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()  -> new PaintApp().setVisible(true));

    }
}
