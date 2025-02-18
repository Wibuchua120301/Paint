package paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class DrawPanel extends JPanel {
    private List<List<Point>> shapes = new ArrayList<>(); // Lưu trữ từng đường riêng biệt
    private List<Point> currentPath = new ArrayList<>();  // Đường đang vẽ
    private PaintApp parent;

    public DrawPanel(PaintApp parent) {
        this.parent = parent;
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#708090"), 5),
                BorderFactory.createLineBorder(Color.white, 1)));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (parent.getToolMode() == 'P') {
                    currentPath = new ArrayList<>(); // Tạo đường mới
                    currentPath.add(e.getPoint());
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (parent.getToolMode() == 'P' && !currentPath.isEmpty()) {
                    shapes.add(new ArrayList<>(currentPath)); // Lưu đường đã hoàn thành
                    currentPath.clear();
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (parent.getToolMode() == 'P') {
                    currentPath.add(e.getPoint());
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(parent.getCurrentColor());

        // Vẽ tất cả các đường đã lưu
        for (List<Point> shape : shapes) {
            drawShape(g, shape);
        }

        // Vẽ đường đang vẽ dở
        drawShape(g, currentPath);
    }

    private void drawShape(Graphics g, List<Point> points) {
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}