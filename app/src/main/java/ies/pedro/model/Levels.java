package ies.pedro.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ies.pedro.adapters.AdapterPoint2DJSON;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.geometry.Point2D;

import java.io.*;
import java.util.ArrayList;

@XmlRootElement
public class Levels {
    private ArrayList<Level> listaLevels;

    //Constructores
    public Levels() {
        this.listaLevels = new ArrayList<Level>();
    }

    public Levels(ArrayList<Level> listaLevels) {
        this.listaLevels = listaLevels;
    }

    public void addLevel(Level level) {
        this.listaLevels.add(level);
    }

    public void deleteLevel(String nombreLevel) {
        for (int i = 0; i < this.listaLevels.size(); i++) {
            if (this.listaLevels.get(i).getName().equals(nombreLevel)) {
                System.out.println("Nivel: " + this.listaLevels.get(i).getName() + " eliminado");
                this.listaLevels.remove(i);
                break;
            }
        }
    }

    public Level changeLevel(String nombre) {
        for (int i = 0; i < this.listaLevels.size(); i++) {
            if(this.listaLevels.get(i).getName().equals(nombre)) {
                return this.listaLevels.get(i);
            }
        }
        return null;
    }


    public static void save(File fichero, Levels levels) {
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
                saveXML(fichero,levels);
                break;
            case "json":
                saveJSON(fichero,levels);
                break;
            default:
                System.out.println("Error");
        }
    }

    private static void saveXML(File fichero, Levels levels) {
        try {
            JAXBContext contexto = JAXBContext.newInstance(Levels.class);
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(levels, fichero);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void saveJSON(File fichero, Levels levels) {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Point2D.class, new AdapterPoint2DJSON());

        Gson gson = gb.create();
        String json = gson.toJson(levels);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
            bw.write(json);
            bw.close();
        } catch (IOException e) {
             e.printStackTrace();
        }
    }

    public static Levels load(File fichero) {
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


    private static Levels loadXML(File fichero) {
        Levels levels;
        try {
            JAXBContext contexto = JAXBContext.newInstance(Levels.class);
            Unmarshaller unmarshaller = contexto.createUnmarshaller();
            levels = (Levels) unmarshaller.unmarshal(fichero);
            return levels;
        } catch (JAXBException e) {
            return null;
        }
    }

    private static Levels loadJSON(File fichero) {
        Levels l;
        try {
            FileReader fr = new FileReader(fichero);
            GsonBuilder gb = new GsonBuilder();

            gb.registerTypeAdapter(Point2D.class, new AdapterPoint2DJSON());
            Gson gson = gb.create();

            l = gson.fromJson(fr, Levels.class);
            return l;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    //Getters y Setters
    public ArrayList<Level> getListaLevels() {
        return listaLevels;
    }

    public void setListaLevels(ArrayList<Level> listaLevels) {
        this.listaLevels = listaLevels;
    }

}
