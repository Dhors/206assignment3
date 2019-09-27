package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class CreationController {

	@FXML
    private Text enterSearchTerm;
	@FXML
    private TextField enterSearchTermTextInput;
	@FXML
    private Button searchWikipediaButton;
	@FXML
    private Text searchInProgress;
	@FXML
    private Text termNotFound;
	
	
	
	@FXML
    private TextArea searchResultTextArea;
	@FXML
    private Button previewChunk;
	@FXML
    private Button saveChunk;
	@FXML
    private Text voiceLabel;
	@FXML
    private ChoiceBox voiceDropDownMenu;
	@FXML
    private ListView chunkList;
	@FXML
    private Slider numImagesSlider;
	@FXML
    private TextField creationNameTextField;
	@FXML
    private Button finalCreate;
	
    @FXML
    private void handleCreationCancelButton(ActionEvent event) throws IOException {

        Parent creationViewParent = FXMLLoader.load(Main.class.getResource("resources/home.fxml"));
        Scene creationViewScene = new Scene(creationViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(creationViewScene);
        window.show();
    }

	@FXML
    private void handleSearchWikipedia(ActionEvent event) throws IOException {
		displayChunkSelection();
	}

    private void displayChunkSelection() {
    	enterSearchTerm.setVisible(false);
    	enterSearchTermTextInput.setVisible(false);
    	searchWikipediaButton.setVisible(false);
    	searchInProgress.setVisible(false);
        termNotFound.setVisible(false);
        
        searchResultTextArea.setVisible(true); 
        previewChunk.setVisible(true);
    	saveChunk.setVisible(true);
    	voiceLabel.setVisible(true);
    	voiceDropDownMenu.setVisible(true);
    	chunkList.setVisible(true);
    	numImagesSlider.setVisible(true);
        creationNameTextField.setVisible(true);
        finalCreate.setVisible(true);
    }
}
