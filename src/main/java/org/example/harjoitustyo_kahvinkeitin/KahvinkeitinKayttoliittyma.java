package org.example.harjoitustyo_kahvinkeitin;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Objects;

public class KahvinkeitinKayttoliittyma extends Application {

    /**
     * Koneesta valittavat kahvilaadut
     */
    private String[] arrKoneet = { "Tummapaahto", "Vaalea paahto", "Kofeiiniton"};

    /**
   * kahvityypin id
    */
    private  int id;

    /**
     luodaan pääpaneeli
     */
    private BorderPane paneeli = new BorderPane();

    /**
     * Luodaan Kahvinkeitin-luokan olio
     */

    private  Kahvinkeitin[] keitin = new Kahvinkeitin[3];

    /**
     Luodaan paneeli painikkeille ja teksteille
     */
    private BorderPane bpanePainikkeet = new BorderPane();

    /**
     määritetään valittavien kahvilaatujen määrä
     */
    private  int kahvilaatuja = arrKoneet.length;

    /**
     luodaan VBox painikkeille
     */
    private  VBox vboxPainikkeille = new VBox();


    /**
     alustetaan tekstit ja tekstikentät
     */
    private Text kahviaJaljella = new Text();

    private Label label_vesi = new Label("Valitse veden määrä: ");
    /**
     * tekstikenttä johon kirjoitetaan veden määrä
     */
    private  TextField txt_vesimaara = new TextField();


    private Label label_kahvi = new Label("Valitse kahvin määrä: ");
    /**
     * tekstikenttä johon kirjoitetaan kahvin määrä
     */
    private TextField txt_kahvimaara = new TextField();


    private  VBox lisaaPavutBox = new VBox();

    private Label label_lisaaPavut = new Label("Lisää papuja koneeseen: ");
    /**
     * tekstikenttä koneeseen lisättävien papujen määrälle
     */
    private  TextField txt_pavut = new TextField();
    /**
     * painike jolla lisätään koneeseen papuja
     */
    private   Button btn_pavut = new Button("Lisää pavut");


    /**
     painike kahvin keittämiselle
     */
    private  Button btnKahvi = new Button("Keitä kahvi");

    /**
     alustetaan infoteksti
     */
    private Text teksti = new Text();
    private Text aikateksti = new Text();

    /**
     painike ohjelman sulkemista varten
     */
    private Button btnLopeta = new Button("Sulje");

    /**
      kuva kahvinkeittimestä
     */
    private Image kone = new Image("kahvinkeitin.jpg");
    private ImageView imgView = new ImageView(kone);

    private StackPane kuvaPaneeli = new StackPane();

    /**
     * toinen kuva käynnissä olevasta keittimestä
     */
    private Image koneKaynnissa = new Image("kahvinkeitin_pour.jpg");
    private ImageView imgView2 = new ImageView(koneKaynnissa);

    /**
     Luodaan höyryanimaatiolle paneeli
     */

    private  Pane animaatiopaneeli = new Pane();


    /**
     lista kahvilaaduista
     */
    private ListView<String> listKeittimet = new ListView<>
            (FXCollections.observableArrayList(arrKoneet));


    /**
     Luetaan kahvilaatujen tiedot tiedostosta "kahvikone.dat" tai alustetaan tiedot jos tiedostoa ei ole. Virheenhallinta jos lukeminen
     tai kirjoittaminen epäonnistuu.
     */
    private void lueTiedosto() {
        try {
            File tiedosto = new File("kahvikone.dat");
            if (tiedosto.exists()) {
                FileInputStream kahvitiedosto = new FileInputStream("kahvikone.dat");
                ObjectInputStream kahvit = new ObjectInputStream(kahvitiedosto);
                for (int i = 0; i < kahvilaatuja; i++) {
                    keitin[i] = (Kahvinkeitin) kahvit.readObject();
                }
                kahvitiedosto.close();
            } else  // kirjoitetaan tilien tiedot jos niitä ei ole
            {
                for (int i = 0; i < kahvilaatuja; i++) {
                    keitin[i] = new Kahvinkeitin(i, 50);
                }
                try {
                    FileOutputStream kahvitiedosto = new FileOutputStream("kahvikone.dat");
                    ObjectOutputStream kahvit = new ObjectOutputStream(kahvitiedosto);
                    for (int i= 0; i < kahvilaatuja; i++) {
                        kahvit.writeObject(keitin[i]);
                    }
                    kahvitiedosto.close();
                    System.out.println("Kirjoitettiin tiedostoon onnistuneesti. ");
                } catch (Exception e ) {
                    System.out.println("virhe kirjoituksessa.");
                    System.out.println(e);

                }
            }
        } catch (Exception e) {
            System.out.println("virhe lukemisessa");
            System.out.println(e);
        }
    }

    /**
     * Tallennetaan kahvien uudet tiedot tiedostoon "kahvikone.dat". Virheenhallinta jos kirjoittaminen epäonnistuu.
     * Lopulta suljetaan ohjelma.
     */
    private void tallennaTiedostoon() {
        try {
            FileOutputStream kahvitiedosto = new FileOutputStream("kahvikone.dat");
            ObjectOutputStream kahvit = new ObjectOutputStream(kahvitiedosto);
            for (int i= 0; i < kahvilaatuja; i++) {
                kahvit.writeObject(keitin[i]);
            }
            kahvitiedosto.close();
            System.out.println("Kirjoitettiin tiedostoon onnistuneesti. ");
        } catch (Exception ex ) {
            System.out.println("virhe kirjoituksessa.");
            System.out.println(ex);
        } finally {
            Stage stage = (Stage) btnLopeta.getScene().getWindow();
            stage.close();
        }

    }

    /**
    Ohjelman käynnistys ja ikkuna
    */
    @Override
    public void start(Stage primaryStage) {

        // luetaan alkutiedot tiedostosta
        lueTiedosto();

        // asetetaan muotoilut ja sijoitetaan painikkeet ja tekstikentät paneeliin
        lisaaPavutBox.setPadding(new Insets(10));
        lisaaPavutBox.setSpacing(10);
        vboxPainikkeille.setPadding(new Insets(10));
        vboxPainikkeille.setSpacing(10);
        lisaaPavutBox.getChildren().addAll(label_lisaaPavut, txt_pavut, btn_pavut);
        btnKahvi.setMaxWidth(Double.MAX_VALUE);
        btnKahvi.setStyle("-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #d8a4fa; -fx-border-color: #62069f; -fx-border-width: 1px;");

       // piilotetaan tekstit
        lisaaPavutBox.setVisible(false);
        teksti.setVisible(false);
        aikateksti.setVisible(false);

        // lisätään kaikki komponentit VBoxiin
        vboxPainikkeille.getChildren().addAll(kahviaJaljella, label_vesi, txt_vesimaara, label_kahvi, txt_kahvimaara, btnKahvi, aikateksti, teksti, lisaaPavutBox);
        bpanePainikkeet.setTop(vboxPainikkeille);
        bpanePainikkeet.setPadding(new Insets(10,10,10,10));

        // muotoilut kuvalle
        imgView.setFitHeight(200);
        imgView.setFitWidth(150);
        kuvaPaneeli.getChildren().add(imgView);
        imgView2.setFitHeight(200);
        imgView2.setFitWidth(150);


        // sijoitetaan painikkeet ja kuva paikoilleen
        vboxPainikkeille.getChildren().add(btnLopeta);
        btnLopeta.setAlignment(Pos.BOTTOM_RIGHT);
        paneeli.setRight(vboxPainikkeille);
        paneeli.setCenter(kuvaPaneeli);

        // höyrypilvigrafiikka

        // eka höyrypilvi
        Rectangle piste1 = new Rectangle(185,49,1.5,4);
        Rectangle piste2 = new Rectangle(186,46,1.5,4);
        Rectangle piste3 = new Rectangle(185,43,1.5,4);
        Rectangle piste4 = new Rectangle(186,40, 1.5, 4);

        // toinen höyrypilvi
        Rectangle piste5 = new Rectangle(195,62,1.5,4);
        Rectangle piste6 = new Rectangle(194,59,1.5, 4);
        Rectangle piste7 = new Rectangle(195,56,1.5,4);
        Rectangle piste8 = new Rectangle(194,53,1.5,4);

        // kolmas höyrypilvi
        Rectangle piste9 = new Rectangle(178,56,1.5,4);
        Rectangle piste10 = new Rectangle(177,53,1.5,4);
        Rectangle piste11 = new Rectangle(178,50,1.5,4);
        Rectangle piste12 = new Rectangle(177,47,1.5,4);
        piste1.setFill(Color.DIMGREY);
        piste2.setFill(Color.DIMGREY);
        piste3.setFill(Color.DIMGREY);
        piste4.setFill(Color.DIMGREY);
        piste5.setFill(Color.DIMGREY);
        piste6.setFill(Color.DIMGREY);
        piste7.setFill(Color.DIMGREY);
        piste8.setFill(Color.DIMGREY);
        piste9.setFill(Color.DIMGREY);
        piste10.setFill(Color.DIMGREY);
        piste11.setFill(Color.DIMGREY);
        piste12.setFill(Color.DIMGREY);

        // lisätään animaation komponentit paikoilleen
        animaatiopaneeli.getChildren().addAll(piste1, piste2, piste3, piste4, piste5, piste6, piste7, piste8, piste9, piste10, piste11, piste12);

        // Animaation käsittelijä
        EventHandler<ActionEvent> kasittelija1 = e -> {
            animaatiopaneeli.setLayoutY(animaatiopaneeli.getLayoutY()-2);
        };

        //  Countdown- käsittelijä
        EventHandler<ActionEvent> kasittelija2 = e -> {
            int aikanyt= Integer.parseInt(aikateksti.getText());
            // kun aika loppuu, piilotetaan animaatio ja näytetään infoteksti
            if (aikanyt == 0){
                kuvaPaneeli.getChildren().setAll(imgView);
                animaatiopaneeli.setVisible(false);
                teksti.setVisible(true);
                aikateksti.setVisible(false);
                txt_kahvimaara.setText("");
                txt_vesimaara.setText("");
                btnKahvi.setDisable(false);
            } else {
                aikateksti.setText(String.valueOf(aikanyt-1));

            }
        };

       // asetetaan animaatioille nopeudet
        Timeline animaatio1 = new Timeline((new KeyFrame(Duration.millis(500), kasittelija1)));
        Timeline animaatio2 = new Timeline((new KeyFrame(Duration.millis(1000), kasittelija2)));
        paneeli.getChildren().add(animaatiopaneeli);
        animaatiopaneeli.setVisible(false);

        // asetetaan muotoilut kahvilaatuvalikolle
        listKeittimet.setPrefWidth(120);
        listKeittimet.setPrefHeight(250);
        listKeittimet.requestFocus();

        // sallitaan vain yksi valinta, oletuksena ensimmäinen
        listKeittimet.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listKeittimet.getSelectionModel().select(0);
        kahviaJaljella.setText(listKeittimet.getSelectionModel().getSelectedItem()+": kahvia jäljellä "+ Math.round(keitin[id].getKahvi()));

        //otetaan valitun kahvulaadun id ja haetaan sen avulla oikeat tiedot kahvin määrästä

        listKeittimet.getSelectionModel().selectedItemProperty().addListener(
                ov -> {
                    id = listKeittimet.getSelectionModel().getSelectedIndex();
                    System.out.println(listKeittimet.getSelectionModel().getSelectedItem());
                    kahviaJaljella.setText(listKeittimet.getSelectionModel().getSelectedItem()+": kahvia jäljellä "+ Math.round(keitin[id].getKahvi()));
                }
        );

        //  sijoitetaan kahvilaatujen lista vasemmalle puolelle
        paneeli.setLeft(listKeittimet);

       // Tapahtumankäsittelijä: kahvin keittäminen
        btnKahvi.setOnMouseClicked(e -> {
            Kahvinkeitin keitinNyt = keitin[id];
            // tarkistetaan että on annettu numeroita
            try{
                Double.parseDouble(txt_vesimaara.getText());
                Double.parseDouble(txt_kahvimaara.getText());
            } catch (NumberFormatException exception) {
                System.out.println("exception "+exception);
                teksti.setText("Ole hyvä ja anna numero");
                teksti.setVisible(true);
            } finally {
                keitinNyt.valittuVesimaara = Double.parseDouble(txt_vesimaara.getText());
                keitinNyt.valittuKahvimaara = Double.parseDouble(txt_kahvimaara.getText());
                keitinNyt.setTila();
                //jos yritetään keittää vähemmän kuin 1 kupillinen tai yli 100
                if (Double.parseDouble(txt_vesimaara.getText()) < 1 || ( Double.parseDouble(txt_kahvimaara.getText()) < 1) ||  Double.parseDouble(txt_kahvimaara.getText()) > 100) {
                    teksti.setText( keitinNyt.getTila());
                    teksti.setFill(Color.RED);
                    teksti.setVisible(true);
                } else {
                    if (keitinNyt.sopiva) {
                        //jos kahvia on tarpeeksi ja vesi-kahvisuhde on sopiva
                        if (teksti.isVisible()) teksti.setVisible(false);
                        aikateksti.setText("5");
                        aikateksti.setVisible(true);
                        teksti.setFill(Color.BLACK);
                        teksti.setText( keitinNyt.getTila());
                        kuvaPaneeli.getChildren().setAll(imgView2);
                        animaatiopaneeli.setLayoutY(21);
                        animaatiopaneeli.setLayoutX(44);
                        animaatiopaneeli.setVisible(true);
                        animaatio1.setCycleCount(Integer.parseInt(aikateksti.getText())*2);
                        animaatio1.play();
                        animaatio2.setCycleCount(Integer.parseInt(aikateksti.getText())+1);
                        animaatio2.play();
                        kahviaJaljella.setText(listKeittimet.getSelectionModel().getSelectedItem()+": kahvia jäljellä "+ Math.round(keitin[id].getKahvi()));
                        btnKahvi.setDisable(true);
                    } else if (keitinNyt.valittuKahvimaara >  keitinNyt.getKahvi()) {  // jos valittu kahvimääärä on enemmän kuin keittimessä on papuja
                        teksti.setText(keitinNyt.getTila());
                        lisaaPavutBox.setVisible(true);
                        btn_pavut.setOnMouseClicked(papuKasittelija -> {
                            keitinNyt.lisaaKahvipapuja(Double.parseDouble(txt_pavut.getText()));
                            lisaaPavutBox.setVisible(false);
                            teksti.setText("Pavut lisätty!");
                            teksti.setFill(Color.BLACK);
                            teksti.setVisible(true);
                            kahviaJaljella.setText(listKeittimet.getSelectionModel().getSelectedItem()+": kahvia jäljellä "+ Math.round(keitin[id].getKahvi()));
                        });
                    } else  {     // muussa tapauksessa, eli jos kahvista on tulossa liian vahvaa/laihaa, näytetään infoteksti
                        teksti.setText(keitinNyt.getTila());
                        teksti.setFill(Color.RED);
                        teksti.setVisible(true);
                    }
                }
            }
        });

     //   Tapahtumankäsittelijä: kun ohjelma lopetetaan (sulje-painikkeesta)
        btnLopeta.setOnMouseClicked(e -> {
            tallennaTiedostoon();
        });

        Scene scene = new Scene(paneeli, 560, 450);
        primaryStage.setTitle("Kahvinkeitin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}