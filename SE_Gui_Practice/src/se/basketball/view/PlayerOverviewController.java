package se.basketball.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import se.basketball.MainApp;
import se.basketball.model.Player;

public class PlayerOverviewController {


	    @FXML
	    private TableView<Player> playerTable;
	    @FXML
	    private TableColumn<Player, String> nameColumn;
	    @FXML
	    private TableColumn<Player, String> teamColumn;

	    @FXML
	    private Label nameLabel;
	    @FXML
	    private Label teamLabel;
	    @FXML
	    private Label positionLabel;
	    
	    // Reference to the main application.
	    private MainApp mainApp;
	    
	    
	    /************************************************************************************************
	     * Fills all text fields to show details about the person.
	     * If the specified person is null, all text fields are cleared.
	     * @param person the person or null
	     /**********************************************************************************************/
	    private void showPlayerDetails(Player player) {
	    	if (player != null) {
	            // Fill the labels with info from the player object. 
	        	// So this just uses the getters to get player info and put them into labels
	        	// Don't forget to assign label fx:id in scene builder
	        	nameLabel.setText(player.getName());
	        	teamLabel.setText(player.getTeam());
	        	positionLabel.setText("Baller");

	        } else {
	            // Player is null, remove all the text.
	        	nameLabel.setText("");
	        	teamLabel.setText("");
	        	positionLabel.setText("");
	        }
	    }
	    
	    /*************************************************************************************************
	    // Handles the delete button
	    //***********************************************************************************************/
	    @FXML
	    private void handleDeletePerson() {
	    	//I guess every row has an index number, so this gets that number
	        int selectedIndex = playerTable.getSelectionModel().getSelectedIndex();
	        
	        //this deletes that row
	        //playerTable.getItems().remove(selectedIndex);
	        
	        //Same as above but handles if no Player is selected in table
		    if (selectedIndex >= 0) {
		    	playerTable.getItems().remove(selectedIndex); 
		    } else {
		        // Nothing selected.
		        Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(mainApp.getPrimaryStage());
		        alert.setTitle("No Selection");
		        alert.setHeaderText("No Person Selected");
		        alert.setContentText("Please select a person in the table.");
	
		        alert.showAndWait();
		    }
	    }
	    
	    /**
	     * Called when the user clicks the new button. Opens a dialog to edit
	     * details for a new person.
	     */
	    @FXML
	    private void handleNewPerson() {
	        Player tempPerson = new Player();
	        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
	        if (okClicked) {
	            mainApp.getPlayerData().add(tempPerson);
	        }
	    }

	    /**
	     * Called when the user clicks the edit button. Opens a dialog to edit
	     * details for the selected person.
	     */
	    @FXML
	    private void handleEditPerson() {
	        Player selectedPerson = playerTable.getSelectionModel().getSelectedItem();
	        if (selectedPerson != null) {
	            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
	            if (okClicked) {
	                showPlayerDetails(selectedPerson);
	            }

	        } else {
	            // Nothing sel
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("No Selection");
	            alert.setHeaderText("No Person Selected");
	            alert.setContentText("Please select a person in the table.");

	            alert.showAndWait();
	        }
	    }

	    /*************************************************************************************************
	    // The constructor.
	    // The constructor is called before the initialize() method.
	    //***********************************************************************************************/
	    public PlayerOverviewController() {
	    }

	    /*************************************************************************************************
	    // Initializes the controller class. This method is automatically called
	    //  after the fxml file has been loaded.
	    //***********************************************************************************************/
	    @FXML
	    private void initialize() {
	    	// Initialize the person table with the two columns.
	    	// This fills the two columns in the table. I guess it acts like a for loop
	    	//  because it gets all players from the ObservableList in MainApp
	    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
	    	teamColumn.setCellValueFactory(cellData -> cellData.getValue().TeamProperty());
	    	
	    	
	    	// Clear person details.
	    	// Call method with null player
//	        showPlayerDetails(null);

	        // Listen for selection changes and show the person details when changed.
	        // When a player is picked in the table, show their details in the label: just copy and paste this line and change it
	        playerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPlayerDetails(newValue));
	    }

	    /*************************************************************************************************
	    // Is called by the main application to give a reference back to itself.
	    //***********************************************************************************************/
	    /** @param mainApp */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;

	        // Add observable list data to the table
	        // Populates table with player info
	        playerTable.setItems(mainApp.getPlayerData());
	    }
	}