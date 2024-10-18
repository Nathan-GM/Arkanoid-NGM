package ies.pedro.adapters;


import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javafx.geometry.Point2D;

public class AdapterPoint2DXML extends XmlAdapter<String, Point2D> {
    @Override
    public Point2D unmarshal(String s) throws Exception {
        if (s.isEmpty() || s == null) {
            return null;
        } else {
            String[] partes = s.split(",");
            double x = Double.parseDouble(partes[0]);
            double y = Double.parseDouble(partes[1]);
            return new Point2D(x, y);
        }
    }

    @Override
    public String marshal(Point2D point2D) throws Exception {
        return point2D.getX() + "," + point2D.getY();
    }
}
