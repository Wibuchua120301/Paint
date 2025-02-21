package paint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColoredPath {
    public List<Point> point;
    public Color color;
    private int thickness;
    public char shapeType; // Kiểu hình vẽ ('F' - tự do, 'L' - đường thẳng, 'R' - chữ nhật, 'C' - hình tròn)
    public Point start, end;

    public ColoredPath(Color color, int thickness, char shapeType) {
        this.color = color;
        this.point = new ArrayList<>();
        this.thickness = thickness;
        this.shapeType = shapeType;
    }

    public void setStartEnd(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public int getThickness() {
        return thickness;
    }
}
