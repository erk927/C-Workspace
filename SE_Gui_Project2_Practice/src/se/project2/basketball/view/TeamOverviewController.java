package se.project2.basketball.view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import se.project2.basketball.MainApp;
import se.project2.basketball.model.Player;
import se.project2.basketball.model.Team;

public class TeamOverviewController {
	@FXML private TableView<Player> playerTable;
    @FXML private TableColumn<Player, String> nameColumn;
    @FXML private TableColumn<Player, String> positionColumn;
    @FXML private TableColumn<Player, Double> ageColumn;
    @FXML private TableColumn<Player, Double> ppgColumn;
    @FXML private TableColumn<Player, Double> rpgColumn;
    @FXML private TableColumn<Player, Double> apgColumn;
    @FXML private TableColumn<Player, Double> spgColumn;
    @FXML private TableColumn<Player, Double> bpgColumn;
    @FXML private TableColumn<Player, Double> tpgColumn;
    @FXML private ChoiceBox<String> teamChoiceBox;
    @FXML private Label teamName;
    
 // Reference to the main application.
    private MainApp mainApp;
    
    /*************************************************************************************************************************
     * Constructor
     * The constructor is called before the initialize() method.
     ************************************************************************************************************************/
    public TeamOverviewController (){
    	
    }
    
    /*************************************************************************************************************************
     * Changes to Player view when player button is clicked
     ************************************************************************************************************************/
    @FXML
    public void handlePlayerButton() {
    	mainApp.showPlayerOverview();
    }
    
    public void setLabel(Team team) {
    	teamName.setText(team.getCity());
    }
    
    /*************************************************************************************************************************
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     ************************************************************************************************************************/
    @FXML
    private void initialize() {
    	// Initialize the player table with the all columns.
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
    	positionColumn.setCellValueFactory(cellData -> cellData.getValue().PosProperty());
    	ageColumn.setCellValueFactory(cellData -> cellData.getValue().AgeProperty().asObject());
    	ppgColumn.setCellValueFactory(cellData -> cellData.getValue().PpgProperty().asObject());
    	rpgColumn.setCellValueFactory(cellData -> cellData.getValue().RpgProperty().asObject());
    	apgColumn.setCellValueFactory(cellData -> cellData.getValue().ApgProperty().asObject());
    	spgColumn.setCellValueFactory(cellData -> cellData.getValue().SpgProperty().asObject());
    	bpgColumn.setCellValueFactory(cellData -> cellData.getValue().BpgProperty().asObject());
    	tpgColumn.setCellValueFactory(cellData -> cellData.getValue().TpgProperty().asObject());
    }
    
    /*************************************************************************************************************************
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
     ************************************************************************************************************************/
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        playerTable.setItems(mainApp.getTeamData().get(0).getRoster());
//        playerTable.setItems(mainApp.getTeamData().get(mainApp.choiceBoxSelection(teamChoiceBox.getSelectionModel().getSelectedItem())).getRoster());
        
        //Add list of cities to ChoiceBox
        mainApp.populateTeamChoiceBox(teamChoiceBox);
    }
}
