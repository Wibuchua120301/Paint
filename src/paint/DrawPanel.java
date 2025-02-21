package paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class DrawPanel extends JPanel {
    private List<ColoredPath> shapes = new ArrayList<>();
    private ColoredPath currentPath;
    private PaintApp parent;
    private List<List<ColoredPath>> undoList = new ArrayList<>();
    private List<List<ColoredPath>> redoList = new ArrayList<>();
    private Point startPoint, endPoint;
    public int currentThickness = 5;

    public DrawPanel(PaintApp parent) {
        this.parent = parent;
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                char mode = parent.getToolMode();

                if (mode == 'P' || mode == 'E') {
                    currentPath = new ColoredPath(
                            (mode == 'P') ? parent.getCurrentColor() : Color.WHITE,
                            currentThickness, 'F'
                    );
                    currentPath.point.add(e.getPoint());
                } else {
                    currentPath = new ColoredPath(parent.getCurrentColor(), currentThickness, mode);
                    currentPath.setStartEnd(startPoint, startPoint);
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentPath != null) {
                    if (parent.getToolMode() == 'P' || parent.getToolMode() == 'E') {
                        shapes.add(currentPath);
                    } else {
                        currentPath.setStartEnd(startPoint, e.getPoint());
                        shapes.add(currentPath);
                    }
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
                endPoint = e.getPoint();
                if (currentPath != null) {
                    if (parent.getToolMode() == 'P' || parent.getToolMode() == 'E') {
                        currentPath.point.add(e.getPoint());
                    } else {
                        currentPath.setStartEnd(startPoint, endPoint);
                    }
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (ColoredPath shape : shapes) {
            g2d.setColor(shape.color);
            g2d.setStroke(new BasicStroke(shape.getThickness()));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawShape(g2d, shape);
        }
        if (currentPath != null) {
            g2d.setColor(currentPath.color);
            g2d.setStroke(new BasicStroke(currentPath.getThickness()));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawShape(g2d, currentPath);
        }
    }

    private void drawShape(Graphics2D g2d, ColoredPath shape) {
        switch (shape.shapeType) {
            case 'F': // Vẽ tự do
                for (int i = 1; i < shape.point.size(); i++) {
                    Point p1 = shape.point.get(i - 1);
                    Point p2 = shape.point.get(i);
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
                break;
            case 'L': // Đường thẳng
                g2d.drawLine(shape.start.x, shape.start.y, shape.end.x, shape.end.y);
                break;
            case 'R': // Hình chữ nhật
                int x = Math.min(shape.start.x, shape.end.x);
                int y = Math.min(shape.start.y, shape.end.y);
                int width = Math.abs(shape.start.x - shape.end.x);
                int height = Math.abs(shape.start.y - shape.end.y);
                g2d.drawRect(x, y, width, height);
                break;
            case 'C': // Hình tròn
                int radius = (int) Math.sqrt(Math.pow(shape.end.x - shape.start.x, 2) + Math.pow(shape.end.y - shape.start.y, 2));
                g2d.drawOval(shape.start.x - radius, shape.start.y - radius, radius * 2, radius * 2);
                break;
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
