package paint;

import javax.swing.*;
import java.awt.*;

public class PaintApp extends JFrame {
    private DrawPanel drawPanel;
    private Color color = Color.BLACK;
    private boolean eraserMode = false;
    private ImageIcon pencil, eraser, colors, lineIcon, rectIcon, circleIcon;

    private JPanel toolPanel;
    private JButton pencilButton, eraserButton, colorButton, lineButton, rectButton, circleButton, undoButton, redoButton;
    private JLabel statusBar;
    private JSlider thicknessSlider;
    private JLabel thicknessLabel;

    private char toolMode = 'P'; // Mặc định là vẽ tự do

    public PaintApp() {
        setTitle("Paint App");
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo thanh công cụ
        toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

//        Tạo thanh công cụ
        toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pencil = new ImageIcon(new ImageIcon("src\\img\\1047189-200.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        pencilButton = new JButton(pencil);

        eraser = new ImageIcon(new ImageIcon("src\\img\\eraser_732430.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        eraserButton = new JButton(eraser);

        colors = new ImageIcon(new ImageIcon("src\\img\\color_15109282.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        colorButton = new JButton(colors);

        lineIcon = new ImageIcon(new ImageIcon("src\\img\\duongThang.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        lineButton = new JButton(lineIcon);

        rectIcon = new ImageIcon(new ImageIcon("src\\img\\hinhChuNhat.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        rectButton = new JButton(rectIcon);

        circleIcon = new ImageIcon(new ImageIcon("src\\img\\hinhTron.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        circleButton = new JButton(circleIcon);

        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");

        thicknessLabel = new JLabel("Độ dày: 5");
        thicknessSlider = new JSlider(1, 20, 5);

        // Xử lý sự kiện thay đổi độ dày nét vẽ
        thicknessSlider.addChangeListener(e -> {
            int thickness = thicknessSlider.getValue();
            thicknessLabel.setText("Độ dày: " + thickness);
            drawPanel.setThickness(thickness);
        });

        // Thêm các nút vào thanh công cụ
        toolPanel.add(pencilButton);
        toolPanel.add(eraserButton);
        toolPanel.add(colorButton);
        toolPanel.add(lineButton);
        toolPanel.add(rectButton);
        toolPanel.add(circleButton);
        toolPanel.add(undoButton);
        toolPanel.add(redoButton);
        toolPanel.add(thicknessSlider);
        toolPanel.add(thicknessLabel);

        add(toolPanel, BorderLayout.NORTH);

        // Khu vực vẽ
        drawPanel = new DrawPanel(this);
        add(drawPanel, BorderLayout.CENTER);

        // Thanh trạng thái
        statusBar = new JLabel(" Ready");
        add(statusBar, BorderLayout.SOUTH);

        // Xử lý sự kiện cho Pencil (Vẽ tự do)
        pencilButton.addActionListener(e -> setToolMode('P', "Pencil mode"));

        // Xử lý sự kiện cho Eraser
        eraserButton.addActionListener(e -> setToolMode('E', "Eraser mode"));

        // Xử lý sự kiện chọn màu
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Color", color);
            if (newColor != null) {
                color = newColor;
                setToolMode('P', "Color selected");
            }
        });

        // Xử lý sự kiện chọn Line
        lineButton.addActionListener(e -> setToolMode('L', "Line mode"));

        // Xử lý sự kiện chọn Rectangle
        rectButton.addActionListener(e -> setToolMode('R', "Rectangle mode"));

        // Xử lý sự kiện chọn Circle
        circleButton.addActionListener(e -> setToolMode('C', "Circle mode"));

        // Xử lý sự kiện Undo
        undoButton.addActionListener(e -> drawPanel.undo());

        // Xử lý sự kiện Redo
        redoButton.addActionListener(e -> drawPanel.redo());
    }

    private void setToolMode(char mode, String text) {
        toolMode = mode;
        statusBar.setText(text);
    }

    public char getToolMode() {
        return toolMode;
    }

    public Color getCurrentColor() {
        return color;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaintApp().setVisible(true));
    }
}
