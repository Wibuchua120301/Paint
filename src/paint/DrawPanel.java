package paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class DrawPanel extends JPanel {
    private List<ColoredPath> shapes = new ArrayList<>(); // Lưu trữ từng đường riêng biệt
    private ColoredPath currentPath;  // Đường đang vẽ với màu sắc hiện tại
    private PaintApp parent;
    private List<List<ColoredPath>> allPaths;
    private List<List<ColoredPath>> undoList = new ArrayList<>();
    private List<List<ColoredPath>> redoList = new ArrayList<>();
    private ArrayList<Integer> thicknesses = new ArrayList<>();
    public int currentThickness = 5;

    public DrawPanel(PaintApp parent) {
        this.parent = parent;
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#708090"), 5),
                BorderFactory.createLineBorder(Color.white, 1)));
        setBackground(Color.WHITE);
        allPaths = new ArrayList<>(25);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (parent.getToolMode() == 'P') {
                    currentPath = new ColoredPath(parent.getCurrentColor(), currentThickness);// Tạo đường mới
                } else if (parent.getToolMode() == 'E') {
                    currentPath = new ColoredPath(Color.white, currentThickness); // màu cảu cục tẩy
                }
                currentPath.point.add(e.getPoint());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentPath != null && !currentPath.point.isEmpty()) {
                    shapes.add((currentPath)); // Lưu đường đã hoàn thành
                    undoList.add(new ArrayList<>(shapes));
                    redoList.clear();
                }
                currentPath = null;
                repaint();
            }


        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentPath != null) {
                    thicknesses.add(currentThickness);
                    currentPath.point.add(e.getPoint());
                    repaint();
                }
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Vẽ tất cả các đường đã lưu
        for (ColoredPath shape : shapes) {
            g2d.setColor(shape.color);
            g2d.setStroke(new BasicStroke(shape.getThickness()));
            drawShape(g2d, shape.point);

        }

        // Vẽ đường đang vẽ dở
        if (currentPath != null) {
            if (parent.getToolMode() == 'P') {
                g2d.setColor(currentPath.color);
                g2d.setStroke(new BasicStroke(currentPath.getThickness())); // Màu của đường vẽ dở
            } else if (parent.getToolMode() == 'E') {
                g2d.setColor(Color.white);
                g2d.setStroke(new BasicStroke(currentThickness));
            }
            drawShape(g2d, currentPath.point);

        }
    }

    private void drawShape(Graphics2D g2d, List<Point> points) {
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            redoList.add(new ArrayList<>(shapes));
            shapes.remove(shapes.size() - 1);
            repaint();
        }
    }

    public void redo() {
        if (!redoList.isEmpty()) {
            undoList.add(new ArrayList<>(shapes));
            shapes = redoList.remove(redoList.size() - 1);
            repaint();
        }
    }

    public void setThickness(int thickness) {
         this.currentThickness = thickness;
    }
}