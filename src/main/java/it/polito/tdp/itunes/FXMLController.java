/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.db.Adiacenze;
import it.polito.tdp.itunes.model.Genre;
import it.polito.tdp.itunes.model.Model;
import it.polito.tdp.itunes.model.Track;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="btnMassimo"
    private Button btnMassimo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCanzone"
    private ComboBox<Track> cmbCanzone; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGenere"
    private ComboBox<Genre> cmbGenere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void btnCreaLista(ActionEvent event) {
    	Track canzonePreferita=this.cmbCanzone.getValue();
    	if(canzonePreferita==null) {
    		this.txtResult.setText("Selezionare canzone!");
    		return;
    	}
    	
    	try {
    		Integer memoria=Integer.parseInt(this.txtMemoria.getText());
    		//ricorsione
    		List<Track> lista=this.model.creaLista(canzonePreferita, memoria);
    		for(Track c:lista)
        		this.txtResult.appendText("\n"+c.getName());

    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci memoria");
    		return;
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Genre g=this.cmbGenere.getValue();
    	if(g==null) {
    		this.txtResult.setText("Inserire genere!");
    		return;
    	}
    	String msg=this.model.creaGrafo(g);
		this.txtResult.setText(msg);
		this.btnCreaLista.setDisable(false);
		this.btnMassimo.setDisable(false);
		this.cmbCanzone.getItems().addAll(this.model.getAllSong());
    }

    @FXML
    void doDeltaMassimo(ActionEvent event) {
    	Genre g=this.cmbGenere.getValue();
    	if(g==null) {
    		this.txtResult.setText("Inserire genere!");
    		return;
    	}
    	List<Adiacenze> res=this.model.getDeltaMassimo(g);
    	for(Adiacenze a: res) {
    		String msg="\n"+a.getT1().getName()+"***"+a.getT2().getName()+" => "+a.getDelta();
    		this.txtResult.appendText(msg);
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMassimo != null : "fx:id=\"btnMassimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCanzone != null : "fx:id=\"cmbCanzone\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGenere != null : "fx:id=\"cmbGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbGenere.getItems().addAll(this.model.getAllGenre());
    	this.btnMassimo.setDisable(true);
    	this.btnCreaLista.setDisable(true);
    }

}
