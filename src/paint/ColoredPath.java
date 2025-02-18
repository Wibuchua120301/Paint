package paint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColoredPath {
    public List<Point> point; // Lưu trữ từng đường riêng biệt
    public Color color;

    public ColoredPath(Color color) {
        this.color = color;
        this.point = new ArrayList<>();
    }
}
