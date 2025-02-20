package paint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColoredPath {
    public List<Point> point; // Lưu trữ từng đường riêng biệt
    public Color color;
    private int thickness;

    public ColoredPath(Color color, int thickness) {
        this.color = color;
        this.point = new ArrayList<>();
        this.thickness = thickness;
    }

    public int getThickness() {
return thickness;
    }
}
