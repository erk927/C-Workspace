package se.basketball.view;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import se.basketball.model.Player;

public class PlayerEditDialogController {
	
		@FXML
	    private TextField nameField;
	    @FXML
	    private TextField teamField;
	    @FXML
	    private TextField positionField;

	    private Stage dialogStage;//Don't have this in Player Controller. Maybe cause this a new window?
	    private Player player;
	    private boolean okClicked = false;

	    /****************************************************************************************
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     ***************************************************************************************/
	    @FXML
	    private void initialize() {
	    }

	    /****************************************************************************************
	     * Sets the stage of this dialog.
	     * 
	     * @param dialogStage
	     ***************************************************************************************/
	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }

	    /****************************************************************************************
	     * Sets the person to be edited in the dialog.
	     * 
	     * @param person
	     ***************************************************************************************/
	    public void setPerson(Player player) {
	        this.player = player;
	        //when the edit window comes up, the text boxes have this filled in
	        nameField.setText(player.getName());
	        teamField.setText(player.getTeam());
	        positionField.setText(player.getPosition());

	    }

	    /****************************************************************************************
	     * Returns true if the user clicked OK, false otherwise.
	     * 
	     * @return
	     ***************************************************************************************/
	    public boolean isOkClicked() {
	        return okClicked;
	    }

	    /****************************************************************************************
	     * Called when the user clicks ok.
	     ***************************************************************************************/
	    @FXML
	    private void handleOk() {
	        if (isInputValid()) {
	        	player.setName(nameField.getText());
	        	player.setTeam(teamField.getText());
	        	player.setPos(positionField.getText());
	            okClicked = true;
	            dialogStage.close();//closes window
	        }
	    }

	    /****************************************************************************************
	     * Called when the user clicks cancel.
	     ***************************************************************************************/
	    @FXML
	    private void handleCancel() {
	        dialogStage.close();//closes window
	    }

	    /****************************************************************************************
	     * Validates the user input in the text fields.
	     * 
	     * @return true if the input is valid
	     ***************************************************************************************/
	    private boolean isInputValid() {
	        String errorMessage = "";

	        if (nameField.getText() == null || nameField.getText().length() == 0) {
	            errorMessage += "No valid name!\n"; 
	        }
	        if (teamField.getText() == null || teamField.getText().length() == 0) {
	            errorMessage += "No valid team name!\n"; 
	        }
	        if (positionField.getText() == null || positionField.getText().length() == 0) {
	            errorMessage += "No valid position!\n"; 
	        }

	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Invalid Fields");
	            alert.setHeaderText("Please correct invalid fields");
	            alert.setContentText(errorMessage);
	            
	            alert.showAndWait();
	            
	            return false;
	        }
	    }
}
