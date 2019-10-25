package application.controller;

import application.Main;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ListController {
	@FXML
	private ListView<String> listViewCreations;

	@FXML
	private static String _selectedCreation;

	@FXML
	private Button playButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Text selectPrompt;
	
	@FXML
	private CheckBox backgroundMusicCheckBox;

	public void initialize(){
		ListCurrentFiles();
		
		backgroundMusicCheckBox.setSelected(Main.backgroundMusicPlayer().checkBoxesAreSelected());

		// Disable the buttons whenever there is no creation selected
		BooleanBinding noCreationSelected = listViewCreations.getSelectionModel().selectedItemProperty().isNull();
		playButton.disableProperty().bind(noCreationSelected);
		deleteButton.disableProperty().bind(noCreationSelected);
		selectPrompt.visibleProperty().bind(noCreationSelected);
	}

	@FXML
	private void handleNewCreationButton() throws IOException {
		Main.changeScene("resources/newCreationScene.fxml");
	}

	@FXML
	private void handleRefreshButton() {
		ListCurrentFiles();
	}

	@FXML
	private void handlePlayButton() throws IOException {
		Main.changeScene("resources/PlayerScene.fxml");
	}

	@FXML
	private void handleReturnButton() throws IOException {
		Main.changeScene("resources/MainScreenScene.fxml");
	}
	
	@FXML
	public void handleSelectedCreation() {
		_selectedCreation = listViewCreations.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	private void handleDeleteButton(){
		Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
		deleteConfirmation.setTitle("Confirm Deletion");
		deleteConfirmation.setHeaderText("Delete " + getSelectedCreationName() + "?");
		deleteConfirmation.setContentText("Are you sure you want to delete this creation?");
		Optional<ButtonType> buttonClicked = deleteConfirmation.showAndWait();

		if (buttonClicked.get() == ButtonType.OK) {
			getSelectedFile().delete();
			ListCurrentFiles();
		}
	}

	// This will return a list of all current creations in the creations directory.
	// This list will be displayed to the user in the view interface.
	private void ListCurrentFiles(){
		// The creations directory where all creations are stored.
		final File creationsFolder = new File(System.getProperty("user.dir")+"/creations/");
		ArrayList<String> creationNamesList = new ArrayList<String>();

		// Will get every file in the creations directory and create an indexed
		// list of file names.
		int indexCounter = 1;
		for (final File file : creationsFolder.listFiles()) {
			String fileName = file.getName();
			if (fileName.endsWith(".mp4")) {
				creationNamesList.add("" + indexCounter + ". " + fileName.replace(".mp4", ""));
				indexCounter++;
			}
		}
		
		// Turning the list of creation names into an listView<String> for the GUI.
		ObservableList<String> observableCreationNamesList = FXCollections.observableArrayList(creationNamesList);
		listViewCreations.setItems(observableCreationNamesList);
	}

	public static File getSelectedFile(){
		// Removal of the index on the creation name
		// and creating it as a file to be played or deleted.
		String fileName = getSelectedCreationName();
		File selectedfile = new File(System.getProperty("user.dir")+"/creations/"+ fileName +".mp4");

		return selectedfile;
	}

	public static String getSelectedCreationName(){
		// Removal of the index on the creation name
		String fileName = ( "" + _selectedCreation.substring(_selectedCreation.indexOf(".")+2) );
		return fileName;
	}
	
	@FXML
	private void handleBackgroundMusic() throws IOException {
		Main.backgroundMusicPlayer().handleBackgroundMusic(backgroundMusicCheckBox.isSelected());
	}
}
