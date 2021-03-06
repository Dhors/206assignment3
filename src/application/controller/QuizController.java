package application.controller;

import application.Main;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class QuizController {

    private String _quizTerm;
    private File _quizVideo;

    @FXML
    private ToggleButton _backgroundMusicButton;
    @FXML
    private ToggleButton _backgroundMusicButtonInPlayer;

    @FXML
    private Pane _quizPlayer;
    @FXML
    private TextField _playerAnswerTextField;
    @FXML
    private Button _startButton;
    @FXML
    private Button _skipButton;
    @FXML
    private Button _checkButton;
    @FXML
    private Button _pausePlayButton;
    @FXML
    private Button _manageQuizButton;
    @FXML
    private ListView<String> _listOfQuiz;


    @FXML
    private Button _deleteButton;
    @FXML
    private Button _returnButton;
    @FXML
    private Button _backButton;
    @FXML
    MediaView _mediaView;
    @FXML
    private Label selectPrompt;
    @FXML
    private Label _quizListLabel;
    @FXML
    private Label _currentScoreText;
    private int _currentScore;
    @FXML
    private ImageView _quizImage;

    private MediaPlayer _mediaPlayer;

    private static String _selectedQuiz;

    @FXML
    public void initialize() {
        Main.setCurrentScene("QuizScene");
        _currentScore = 0;
        _currentScoreText.setText("   Current Score: 0");
        _quizPlayer.setVisible(false);
        BooleanBinding noCreationSelected = _listOfQuiz.getSelectionModel().selectedItemProperty().isNull();
        _deleteButton.disableProperty().bind(noCreationSelected);

        String buttonText = Main.backgroundMusicPlayer().getButtonText();
        _backgroundMusicButton.setText(buttonText);
        _backgroundMusicButtonInPlayer.setText(buttonText);
        boolean buttonIsSelected = Main.backgroundMusicPlayer().getButtonIsSelected();
        _backgroundMusicButton.setSelected(buttonIsSelected);
        _backgroundMusicButtonInPlayer.setSelected(buttonIsSelected);

        _startButton.setVisible(true);
        _manageQuizButton.setVisible(true);


        _listOfQuiz.setVisible(false);
        _returnButton.setVisible(false);
        _pausePlayButton.setVisible(false);
        _checkButton.setVisible(false);
        _skipButton.setVisible(false);
        _deleteButton.setVisible(false);
        _playerAnswerTextField.setVisible(false);
        _backgroundMusicButtonInPlayer.setVisible(false);

        /**
         * Credit to user DVarga
         * Full credit in NewCreationController in method setUpBooleanBindings()
         */
        // Don't let the user check their answer until they enter an answer
        BooleanBinding textIsEmpty = Bindings.createBooleanBinding(() ->
                        _playerAnswerTextField.getText().trim().isEmpty(),
                _playerAnswerTextField.textProperty());
        _checkButton.disableProperty().bind(textIsEmpty);
    }


    @FXML
    private void handleStartButton() {
        _quizPlayer.setVisible(true);
        _quizImage.setVisible(false);

        _currentScoreText.setVisible(true);
        _startButton.setVisible(false);
        _manageQuizButton.setVisible(false);
        _pausePlayButton.setVisible(true);
        _checkButton.setVisible(true);
        _skipButton.setVisible(true);
        _playerAnswerTextField.setVisible(true);
        _backgroundMusicButtonInPlayer.setVisible(true);


        getRandomQuiz();

        Media video = new Media(_quizVideo.toURI().toString());
        _mediaPlayer = new MediaPlayer(video);
        _mediaPlayer.setAutoPlay(true);

        _mediaView.setMediaPlayer(_mediaPlayer);


        //Once the video is finished the video will replay from the start
        _mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                _mediaPlayer.seek(Duration.ZERO);
                _mediaPlayer.play();
            }
        });
    }

    @FXML
    private void handleCheckButton() {
        _mediaPlayer.pause();

        boolean answerIsCorrect = _playerAnswerTextField.getText().equalsIgnoreCase(_quizTerm);
        if (answerIsCorrect) {
            //updating the score when user gets answer correct
            _currentScore++;
            _currentScoreText.setText("   Current Score: " + _currentScore);
            _playerAnswerTextField.setText("");
            Alert correctAnswerPopup = new Alert(Alert.AlertType.INFORMATION);
            correctAnswerPopup.getDialogPane().getStylesheets().add(("Alert.css"));
            correctAnswerPopup.setTitle("Well done that was right.");
            correctAnswerPopup.setHeaderText(null);
            correctAnswerPopup.setContentText("Good luck in the next one.");
            correctAnswerPopup.showAndWait();
            _startButton.fire();
        } else {
            Alert incorrectAnswerPopup = new Alert(Alert.AlertType.INFORMATION);
            incorrectAnswerPopup.getDialogPane().getStylesheets().add(("Alert.css"));
            incorrectAnswerPopup.setTitle("Sorry that was wrong.");
            incorrectAnswerPopup.setHeaderText(null);
            incorrectAnswerPopup.setContentText("Please try again.");
            incorrectAnswerPopup.showAndWait();
            _mediaPlayer.play();
        }
    }

    @FXML
    private void handlePausePlayButton() {
        if (_mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            _mediaPlayer.pause();
            _pausePlayButton.setText("\u25B6");
        } else {
            _mediaPlayer.play();
            _pausePlayButton.setText("| |");
        }
    }

    // Return to main menu
    @FXML
    private void handleBackButton() throws IOException {
        if (_mediaPlayer != null) {
            _mediaPlayer.stop();
        }
        Main.changeScene("resources/MainScreenScene.fxml");
    }

    @FXML
    private void handleSkipButton() {
        _pausePlayButton.setText("| |");
        _startButton.fire();
    }

    // This method will retrieve a random quiz video from the current repository of quiz videos.
    private void getRandomQuiz() {
        File quizFolder = new File(System.getProperty("user.dir") + "/quiz/");
        File[] quizVideosArray = quizFolder.listFiles();

        List<File> quizVideosList = new ArrayList<File>(Arrays.asList(quizVideosArray));

        Collections.shuffle(quizVideosList);

        Random rand = new Random();
        _quizVideo = quizVideosList.get(rand.nextInt(quizVideosList.size()));

        _quizTerm = _quizVideo.getName().replace(".mp4", "");
    }

    @FXML
    public void handleManageQuizButton() {
        _quizImage.setVisible(false);

        selectPrompt.setVisible(true);
        _quizListLabel.setVisible(true);

        ListCurrentQuiz();
        _backButton.setVisible(false);
        _returnButton.setVisible(true);
        _listOfQuiz.setVisible(true);
        _manageQuizButton.setVisible(false);
        _deleteButton.setVisible(true);
        _startButton.setVisible(false);

    }

    @FXML
    public void handleSelectedQuiz() {

        _selectedQuiz = _listOfQuiz.getSelectionModel().getSelectedItem();
        if (!(_selectedQuiz == null)) {
            selectPrompt.setText("");
        }
    }

    private void ListCurrentQuiz() {
        // The quiz directory where all quiz videos are stored.
        final File quizFolder = new File(System.getProperty("user.dir") + "/quiz/");
        ArrayList<String> creationNamesList = new ArrayList<String>();

        // Will get every file in the quiz directory and create an indexed
        // list of file names.
        int indexCounter = 1;
        for (final File quiz : quizFolder.listFiles()) {
            String fileName = quiz.getName();
            if (fileName.endsWith(".mp4")) {
                creationNamesList.add("" + indexCounter + ". " + fileName.replace(".mp4", ""));
                indexCounter++;
            }
        }

        // Turning the list of quiz names into an listView<String> for the GUI.
        ObservableList<String> observableCreationNamesList = FXCollections.observableArrayList(creationNamesList);
        _listOfQuiz.setItems(observableCreationNamesList);
    }

    public static File getSelectedFile() {
        // Removal of the index on the quiz video name
        // and creating it as a file to be played or deleted.
        String fileName = getSelectedQuizName();
        File selectedQuiz = new File(System.getProperty("user.dir") + "/quiz/" + fileName + ".mp4");

        return selectedQuiz;
    }

    public static String getSelectedQuizName() {
        // Removal of the index on the quiz video name
        String fileName = ("" + _selectedQuiz.substring(_selectedQuiz.indexOf(".") + 2));
        return fileName;
    }

    @FXML
    private void handleDeleteButton() {
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.getDialogPane().getStylesheets().add(("Alert.css"));
        deleteConfirmation.setTitle("Confirm Deletion");
        deleteConfirmation.setHeaderText("Delete " + getSelectedQuizName() + "?");
        deleteConfirmation.setContentText("Are you sure you want to delete this quiz?");
        Optional<ButtonType> buttonClicked = deleteConfirmation.showAndWait();

        if (buttonClicked.get() == ButtonType.OK) {
            getSelectedFile().delete();
            ListCurrentQuiz();

            _selectedQuiz = null;
            selectPrompt.setText("                               " +
                    "Please select a quiz video to continue.");
        }
    }

    // Return back to quiz start screen.
    @FXML
    private void handleReturnButton() throws IOException {
        Main.changeScene("resources/QuizScene.fxml");
    }

    @FXML
    private void handleBackgroundMusic() {
        boolean buttonIsSelected = _backgroundMusicButton.isSelected();
        Main.backgroundMusicPlayer().handleBackgroundMusic(buttonIsSelected);
        _backgroundMusicButtonInPlayer.setSelected(buttonIsSelected);
        updateButtonTexts();
    }

    @FXML
    private void handleBackgroundMusicInPlayer() {
        boolean buttonIsSelected = _backgroundMusicButtonInPlayer.isSelected();
        Main.backgroundMusicPlayer().handleBackgroundMusic(buttonIsSelected);
        _backgroundMusicButton.setSelected(buttonIsSelected);
        updateButtonTexts();
    }

    private void updateButtonTexts() {
        String buttonText = Main.backgroundMusicPlayer().getButtonText();
        _backgroundMusicButton.setText(buttonText);
        _backgroundMusicButtonInPlayer.setText(buttonText);
    }
}
