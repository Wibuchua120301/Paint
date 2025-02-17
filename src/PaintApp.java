import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class PaintApp extends JFrame {
    private DrawPanel drawPanel;
    private Color color = Color.BLACK;
    private boolean eraserMode = false;
    private ImageIcon pencil;
    private ImageIcon eraser;
    private ImageIcon colors;
    private ImageIcon shapes;



    public PaintApp(){
        setTitle("Paint App");
        setSize(1200,650);
        setLocation(50,50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

//        Tạo thanh công cụ
        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pencil= new ImageIcon(new ImageIcon("C:\\Users\\admin\\Paint\\src\\1047189-200.png").getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH));
        JButton pencilButton = new JButton(pencil);
//        pencilButton.setSize(20,20);

        eraser = new ImageIcon(new ImageIcon("C:\\Users\\admin\\Paint\\src\\eraser_732430.png").getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH));
        JButton eraserButton = new JButton(eraser);

        colors =new ImageIcon( new ImageIcon("C:\\Users\\admin\\Paint\\src\\color_15109282.png").getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH));
        JButton colorButton = new JButton(colors);

        shapes = new ImageIcon( new ImageIcon("C:\\Users\\admin\\Paint\\src\\shapes_10335711.png").getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH));
        JButton shapeButton = new JButton(shapes);

        toolPanel.add(pencilButton);
        toolPanel.add(eraserButton);
        toolPanel.add(colorButton);
        toolPanel.add(shapeButton);

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
