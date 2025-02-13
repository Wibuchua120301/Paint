import javax.swing.*;
import java.awt.*;

public class PaintApp extends JFrame {
    private DrawPanel drawPanel;
    private Color color = Color.BLACK;
    private boolean eraserMode = false;

    public PaintApp(){
        setTitle("Paint App");
        setSize(1200,650);
        setLocation(50,50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
//        setVisible(true);

//        Tạo thanh công cụ
        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton pencilButton = new JButton("Pencil");
        JButton eraserButton = new JButton("Eraser");
        JButton colorButton = new JButton("Color");

        toolPanel.add(pencilButton);
        toolPanel.add(eraserButton);
        toolPanel.add(colorButton);

        add(toolPanel, BorderLayout.NORTH);

        //  Khu vực vẽ
        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        //  Thanh trạng thái
        JLabel statusBar = new JLabel(" Ready");
        add(statusBar, BorderLayout.SOUTH);

        // Xử lý sự kiện cho Pencil
        pencilButton.addActionListener(e -> {
            eraserMode = false;
            statusBar.setText(" Pencil mode");
        });

        // Xử lý sự kiện cho Eraser
        eraserButton.addActionListener(e -> {
            eraserMode = true;
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaintApp().setVisible(true));

    }
}
