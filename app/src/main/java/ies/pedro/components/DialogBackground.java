package ies.pedro.components;



import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;


public class DialogBackground extends Dialog<Point2D> {
    private Image image;
    private ArrayList<Pair<Integer,Integer>> coordenadas;
    private Point2D selected;
    private ImageView imageViewSelected;
    public DialogBackground() {
        super();
        this.getDialogPane().setPrefSize(275, 250);
      this.createCoordenadas();
    }
    private void createCoordenadas(){
        this.coordenadas= new ArrayList<>();
        this.coordenadas.add(new Pair<>(0,0));
        this.coordenadas.add(new Pair<>(232,0));
        this.coordenadas.add(new Pair<>(466,0));
        this.coordenadas.add(new Pair<>(696,0));
        this.coordenadas.add(new Pair<>(928,0));
        this.coordenadas.add(new Pair<>(0,256));
        this.coordenadas.add(new Pair<>(232,256));
        this.coordenadas.add(new Pair<>(466,256));
        this.coordenadas.add(new Pair<>(696,256));
        //this.coordenadas.add(new Pair<>(928,256));
    }
    public void init() {
        int h=110;
        int w=(int)(h/1.071);

        this.setTitle("Seleccionar fondo");
        this.image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/fondos.png")));
        ButtonType acceptButtonType = new ButtonType("Aceptar", ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(acceptButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));


        for(int i=0;i<this.coordenadas.size();i++){
            ImageView imageView = new ImageView(this.image);
            Rectangle2D viewportRect = new Rectangle2D(this.coordenadas.get(i).getKey(),this.coordenadas.get(i).getValue(), 224, 240);
            imageView.setX(0);
            imageView.setY(0);
            imageView.setFitHeight(h);
            imageView.setFitWidth(w);
            imageView.setViewport(viewportRect);
            imageView.setCursor(Cursor.HAND);
            int finalI = i;
            imageView.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2){
                    selected = new Point2D(coordenadas.get(finalI).getKey(), coordenadas.get(finalI).getKey());

                    this.setResult(selected);
                    this.close();

                }else {
                    selected = new Point2D(coordenadas.get(finalI).getKey(), coordenadas.get(finalI).getKey());
                    if (this.imageViewSelected != null) {
                        this.imageViewSelected.setEffect(null);
                    }
                    this.imageViewSelected = imageView;
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setRadius(10.0);
                    dropShadow.setOffsetX(10.0);
                    dropShadow.setOffsetY(10.0);
                    dropShadow.setColor(Color.color(0, 0.25, 0.25));
                    this.imageViewSelected.setEffect(dropShadow);
                }
            });

            grid.add(imageView, i% 2, i%5);
        }


        this.getDialogPane().setContent(grid);
        this.setResultConverter(dialogButton -> {
            if (dialogButton == acceptButtonType) {

                return selected;
            }
            return null;
        });
    }
}
