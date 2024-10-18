package ies.pedro.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.geometry.Point2D;

import java.io.IOException;

public class AdapterPoint2DJSON extends TypeAdapter<Point2D> {

    @Override
    public void write(JsonWriter jsonWriter, Point2D point2D) throws IOException {
        if (point2D == null) {
            jsonWriter.nullValue();
            return;
        }
        String xy = point2D.getX() + "," + point2D.getY();
        jsonWriter.value(xy);
    }

    @Override
    public Point2D read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        String xy = jsonReader.nextString();
        String[] partes = xy.split(",");
        double x = Double.parseDouble(partes[0]);
        double y = Double.parseDouble(partes[1]);
        return new Point2D(x,y);
    }
}
