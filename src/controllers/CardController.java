/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Planning;
import entities.RendezVous;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.RendezVousService;

/**
 * FXML Controller class
 *
 * @author rouai
 */
public class CardController implements Initializable {
    @FXML
    private Circle circleImg;
    @FXML
    private ImageView imageLb;
    @FXML
    private Button confirmBt;
    @FXML
    private Button annuleBt;
    @FXML
    private Label nameLb;
    @FXML
    private Label dateLb;
    @FXML
    private Label heureLb;
    @FXML
    private Label symptomesLb;
    @FXML
    private Label etatLb;
    RendezVous rendezVous;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        circleImg.setStroke(Color.SEAGREEN);
        Image im = new Image("./utils/img/patient.jpg",false);
        circleImg.setFill(new ImagePattern(im));
        
       
    }  
    
    public void setData(RendezVous rdv){
        this.rendezVous = rdv;
        System.out.println(rendezVous.getIdRdv());
        int maxLength = 20; // set the maximum length of the text
        circleImg.setStroke(Color.SEAGREEN);
        Image im = new Image("./utils/img/patient.jpg",false);
        etatLb.setText(rdv.getEtat());
          if(rdv.getEtat().equals("en attente")){
            etatLb.setTextFill(Color.ORANGE); // set the text color to red    
          }else if(rdv.getEtat().equals("confirmé")){
            etatLb.setTextFill(Color.GREEN); // set the text color to red    
          }else{
            etatLb.setTextFill(Color.RED); // set the text color to red    

    }
        circleImg.setFill(new ImagePattern(im));
        nameLb.setText(rdv.getFullName());
        dateLb.setText(rdv.getDate().toString());
        heureLb.setText(rdv.getHeureDebut().toString()+"-" + rdv.getHeureFin().toString());
        symptomesLb.setText(rdv.getSymptomes().substring(0, Math.min(rdv.getSymptomes().length(), maxLength)));
        if (rdv.getSymptomes().length() > maxLength) {
        Tooltip tooltip = new Tooltip(rdv.getSymptomes()); // create a tooltip to show the full text
        symptomesLb.setTooltip(tooltip); // set the tooltip on the label
        System.out.println(rendezVous.getEtat());
     }
    }
    
    
    @FXML
    void annulerRdv(ActionEvent event) {

    }

    @FXML
    void confirmerRdv(ActionEvent event) {
        RendezVousService rdvService = new RendezVousService(); 
        try{
        rdvService.confirmerRdv(rendezVous);
        rendezVous.setEtat("confirmé");
        setData(rendezVous);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("Rendez vous confirmé avec succès");
        alert.showAndWait();
        }catch(RuntimeException ex){
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR,"erreur",ButtonType.CLOSE);
            alert.showAndWait();
        }
        
        
    }
    
    
    @FXML
    void archiverRdv(ActionEvent event) {
                RendezVousService rdvService = new RendezVousService(); 
        try{
        rdvService.archiverRdv(rendezVous);
        rendezVous.setEtat("archivé");
        setData(rendezVous);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("Rendez vous confirmé avec succès");
        alert.showAndWait();
        }catch(RuntimeException ex){
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR,"erreur",ButtonType.CLOSE);
            alert.showAndWait();
        }
        
    }
    
    @FXML
    void editerRdv(ActionEvent event) {
        
       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/EditerRdv.fxml"));
            Parent parent = loader.load();

            EditerRdvController controller = (EditerRdvController) loader.getController();
            controller.inflateUI(this.rendezVous);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Editer Rendez Vous");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding((e) -> {
                try {
                    RendezVousService rendezVousService = new RendezVousService();
                    RendezVous rdvModifie = rendezVousService.findRdvById(this.rendezVous.getIdRdv());
                    System.out.println(rdvModifie);
                    setData(rdvModifie);

                    
                }catch (SQLException ex) {
                    System.out.println(ex);
                   // Logger.getLogger("gg").log(Level.SEVERE, null, ex);
                }
            }
            );
            
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 
}
