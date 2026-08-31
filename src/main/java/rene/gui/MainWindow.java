package rene.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import rene.Rene;

/**
 * Controls the main JavaFX window and forwards user commands to Rene.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Rene rene;

    /**
     * Keeps the conversation scrolled to its newest dialog.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Supplies the application logic used to process commands.
     *
     * @param rene the Rene application instance.
     */
    public void setRene(Rene rene) {
        this.rene = rene;
        dialogContainer.getChildren().add(DialogBox.getReneDialog(rene.getWelcomeMessage()));
        userInput.requestFocus();
    }

    /**
     * Adds the user's command and Rene's response to the conversation.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) {
            return;
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getReneDialog(rene.getResponse(input)));
        userInput.clear();
    }
}
