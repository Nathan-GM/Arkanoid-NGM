/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ies.pedro.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import ies.pedro.adapters.AdapterPoint2DJSON;
import ies.pedro.adapters.AdapterPoint2DXML;
import ies.pedro.utils.Size;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javafx.geometry.Point2D;


import java.io.*;


import java.util.Arrays;



@XmlRootElement
public class Level implements Serializable {

    private Size size;

    private Block[][] blocks;

    private double time;

    private String sound;

    private String name;

    private Point2D backgroundPosition;
    private static Size s= new Size(224 / 16, 240 / 8);
    public Level(String name) {
        this.name = name;
        this.setSize(s);
        this.init();

    }

    public Level() {
        this.setSize(s);
        this.init();
        this.name="";
    }

    public void init() {
        this.setBlocks(new Block[this.getSize().getHeight()][this.getSize().getWidth()]);
        for (int i = 0; i < this.getBlocks().length; i++) {
            for (int j = 0; j < this.getBlocks()[i].length; j++) {
                if (this.getBlocks()[i][j] == null)
                    this.getBlocks()[i][j] = new Block();

            }
        }
    }

    public void reset() {
        for (int i = 0; i < this.getBlocks().length; i++) {
            Arrays.fill(this.getBlocks()[i], null);
        }
        this.backgroundPosition = null;
        this.sound = null;
        this.init();

    }

    public static void save(File fichero, Level level) {
        System.out.println(fichero.getName());
        String extension = "";
        if (fichero.getName().endsWith(".xml")) {
            extension = "xml";
        } else if (fichero.getName().endsWith(".json")) {
            extension = "json";
        } else {
            extension = "";
        }

        switch (extension) {
            case "xml":
                saveXML(fichero, level);
                break;
            case "json":
                saveJSON(fichero, level);
                break;
            default:
                System.out.println("Error");
        }

    }

    private static void saveXML(File fichero, Level level) {
        try {
            JAXBContext contexto = JAXBContext.newInstance(Level.class);
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(level, fichero);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void saveJSON (File fichero, Level level) {
        GsonBuilder gb = new GsonBuilder();

        gb.registerTypeAdapter(Point2D.class, new AdapterPoint2DJSON());

        Gson gson = gb.create();
        String json = gson.toJson(level);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
            bw.write(json);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Level load(File fichero) {
        String extension = "";
        if (fichero.getName().endsWith(".xml")) {
            extension = "xml";
        } else if (fichero.getName().endsWith(".json")) {
            extension = "json";
        } else {
            extension = "";
        }

        switch (extension) {
            case "xml":
                return loadXML(fichero);
            case "json":
                return loadJSON(fichero);
            default:
                System.err.println("Error");
                return null;
        }
    }

    private static Level loadXML(File fichero){
        Level l;
        try {
            JAXBContext contexto = JAXBContext.newInstance(Level.class);
            Unmarshaller unmarshaller = contexto.createUnmarshaller();

            l = (Level) unmarshaller.unmarshal(fichero);
            return l;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Level loadJSON(File fichero) {
        Level l;
        try {
            FileReader fr = new FileReader(fichero);
            GsonBuilder gb = new GsonBuilder();

            gb.registerTypeAdapter(Point2D.class, new AdapterPoint2DJSON());

            Gson gson = gb.create();

            l = gson.fromJson(fr,Level.class);
            return l;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBlockValue(String value, int row, int col) {

        this.getBlocks()[row][col].setValue(value);
    }

    public String getBlockValue(int row, int col) {
        if (this.getBlocks()[row][col] == null || this.getBlocks()[row][col].getValue() == null)
            return "null";//null;
        else
            return this.getBlocks()[row][col].getValue();
    }
    //@XmlElement(name = "size")
    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    //@XmlElement(name = "tiempo")
    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    //@XmlElement (name = "sonido")
    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    //@XmlElement (name = "matrizBloque")
    public Block[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }


    //@XmlElement (name = "background")
    @XmlJavaTypeAdapter(AdapterPoint2DXML.class)
    public Point2D getBackgroundPosition() {
        return backgroundPosition;
    }

    public void setBackgroundPosition(Point2D backgroundPosition) {
        this.backgroundPosition = backgroundPosition;
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        String tempo;
        cadena.append("Nivel ").append(this.getName()).append("\n");
        cadena.append("Backgroud:").append(this.backgroundPosition).append("\n");
        for (int i = 0; i < this.getBlocks().length; i++) {
            for (int j = 0; j < this.getBlocks()[i].length; j++) {
                //cadena.append("["+i+","+j+"]"+ this.getBlocks()[i][j]!=null?this.getBlocks()[i][j].toString():""+" "+"");
                tempo = this.getBlocks()[i][j] != null ? this.getBlocks()[i][j].toString() : "null";
                cadena.append("[").append(i).append(",").append(j).append("]").append(tempo).append(" ");
            }
            cadena.append("\n");
        }
        return cadena.toString();
    }


    //@XmlElement (name = "nombre")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
