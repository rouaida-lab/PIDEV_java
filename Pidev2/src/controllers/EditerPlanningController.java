/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
import entities.Planning;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import services.PlanningService;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author rouai
 */
public class EditerPlanningController implements Initializable {

    @FXML
    private JFXDatePicker tf_date_debut;
    @FXML
    private JFXDatePicker tf_date_fin;
    @FXML
    private JFXTimePicker tf_heure_debut;
    @FXML
    private JFXTimePicker tf_heure_fin;
    @FXML
    private JFXTextArea tf_description;
    int idPlanning;
    Planning p;
    private final Connection cnx;
    private PreparedStatement ste;
    SimpleDateFormat heureFormatPicker = new SimpleDateFormat("HH:mm");
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy"); // Change the output format to match the format accepted by JFoenix DatePicker

    public EditerPlanningController() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    }
    
    ObservableList<Planning> ListPlanning=FXCollections.observableArrayList();
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void inflateUI(Planning p) {
       idPlanning= p.getIdPlanning();
       tf_date_debut.setValue(LocalDate.parse(outputFormat.format(p.getDateDebut()), DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Set the DatePicker value using the formatted date
       tf_date_fin.setValue(LocalDate.parse(outputFormat.format(p.getDateFin()), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
       tf_heure_debut.setValue(LocalTime.parse(heureFormatPicker.format(p.getHeureDebut()), DateTimeFormatter.ofPattern("HH:mm")));
       tf_heure_fin.setValue(LocalTime.parse(heureFormatPicker.format(p.getHeureFin()), DateTimeFormatter.ofPattern("HH:mm")));
       tf_description.setText(p.getDescription());
        
    }
    
    
    @FXML
    private void Valider(ActionEvent event) throws Exception {

        

try{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
        if(tf_description.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la description");
            alert.showAndWait();  
        }else if(tf_description.getText().length()<5){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("la description doit comporter au moins 5 caractères");
            alert.showAndWait();  
        }else if(tf_date_debut.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une date de debut");
            alert.showAndWait();  
        }else if(tf_date_fin.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une date de fin");
            alert.showAndWait();  
        }else if(tf_heure_debut.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une heure de debut");
            alert.showAndWait();  
        }else if(tf_heure_fin.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une heure de fin");
            alert.showAndWait();  
        }
        
        else{  
        Date dateDebut = dateFormat.parse(tf_date_debut.getValue().toString());
        Date dateFin = dateFormat.parse(tf_date_fin.getValue().toString());;
        Date heureDebut = heureFormat.parse(tf_heure_debut.getValue().toString());
        Date heureFin = heureFormat.parse(tf_heure_fin.getValue().toString());
        System.out.println("date controller"+dateDebut);
        
        Planning p = new Planning(idPlanning,tf_description.getText(),dateDebut,dateFin,heureDebut,heureFin);
        PlanningService planningService = new PlanningService();
        planningService.editerPlanning(p);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AJOUT AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("Planning modifié avec succès");
        alert.showAndWait();
        
        }}

        catch(RuntimeException ex)
        {
            System.out.println(ex);
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        }
        String title = "succes ";
        String message = "planning modifié avec succes";
     
    }
    @FXML
    private void AfficherPlanningAction(ActionEvent event) throws IOException {

      // Parent parentAfficherPlanning= FXMLLoader.load(getClass().getResource("AfficherPlanning.fxml"));
    //Scene scene = new Scene(parentAfficherPlanning);
    Stage stageEdit = (Stage)((Node)event.getSource()).getScene().getWindow();

    stageEdit.hide();
    }
}