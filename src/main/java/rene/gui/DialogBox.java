package rene.gui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Displays one user or Rene message in the conversation.
 */
public class DialogBox extends HBox {
    private static final String USER_NAME = "You";
    private static final String RENE_NAME = "R";

    @FXML
    private Label messageLabel;
    @FXML
    private Label avatarLabel;

    /**
     * Loads a reusable dialog box from FXML and supplies its text.
     *
     * @param message the message displayed in the dialog box.
     */
    private DialogBox(String message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load the dialog box view.", exception);
        }
        messageLabel.setText(message);
    }

    /**
     * Creates a right-aligned dialog for a command entered by the user.
     *
     * @param message the user's command.
     * @return the user dialog.
     */
    public static DialogBox getUserDialog(String message) {
        DialogBox dialogBox = new DialogBox(message);
        dialogBox.avatarLabel.setText(USER_NAME);
        dialogBox.getStyleClass().add("user-dialog");
        return dialogBox;
    }

    /**
     * Creates a left-aligned dialog for Rene's response.
     *
     * @param message Rene's response.
     * @return Rene's dialog.
     */
    public static DialogBox getReneDialog(String message) {
        DialogBox dialogBox = new DialogBox(message);
        dialogBox.avatarLabel.setText(RENE_NAME);
        dialogBox.getStyleClass().add("rene-dialog");
        dialogBox.flip();
        return dialogBox;
    }

    /**
     * Places the avatar on the left for Rene's messages.
     */
    private void flip() {
        setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> children = FXCollections.observableArrayList(getChildren());
        FXCollections.reverse(children);
        getChildren().setAll(children);
    }
}
