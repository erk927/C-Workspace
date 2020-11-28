package se.project2.basketball.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.project2.basketball.MainApp;
import se.project2.basketball.model.Player;

public class PlayerOverviewController {
	@FXML private TableView<Player> playerTable;
    @FXML private TableColumn<Player, String> nameColumn;
    @FXML private TableColumn<Player, String> teamColumn;
    @FXML private TableColumn<Player, String> positionColumn;
    @FXML private TableColumn<Player, Double> ageColumn;
    @FXML private TableColumn<Player, Double> ppgColumn;
    @FXML private TableColumn<Player, Double> rpgColumn;
    @FXML private TableColumn<Player, Double> apgColumn;
    @FXML private TableColumn<Player, Double> spgColumn;
    @FXML private TableColumn<Player, Double> bpgColumn;
    @FXML private TableColumn<Player, Double> tpgColumn;
    @FXML private ChoiceBox<String> teamChoiceBox;
    @FXML private ImageView banner;
    @FXML private Label team;
    @FXML private Label name;
    @FXML private Label tm;
    @FXML private Label pos;
    @FXML private Label age;
    @FXML private Label p;
    @FXML private Label r;
    @FXML private Label a;
    @FXML private Label s;
    @FXML private Label b;
    @FXML private Label t;
    @FXML private Label test;
    @FXML private Button details;
    
    // Reference to the main application.
    private MainApp mainApp;
    
    /*************************************************************************************************************************
     * Constructor
     * The constructor is called before the initialize() method.
     ************************************************************************************************************************/
    public PlayerOverviewController (){
    	
    }
    
    /*************************************************************************************************************************
     * Changes to Team view when team button is clicked
     ************************************************************************************************************************/
    @FXML
    public void handleTeamButton() {
    	//mainApp.showTeamOverview();
    }
    
    /*************************************************************************************************************************
     * Display tables with chosen teams players
     ************************************************************************************************************************/
    public void display(String city, int index, MainApp app) {
    	if (index == -1) {
    		playerTable.setItems(mainApp.getPlayerData());
    		team.setText("All Players");
    	}
    	else {
    		playerTable.setItems(app.getTeamData().get(index).getRoster());
    		team.setText(city);
    		}	
    }
    
    /*************************************************************************************************************************
     * Display selected players information
     ************************************************************************************************************************/
    private void displayPlayerStats(Player player){
    	name.setText(player.getName());
    	tm.setText(player.getTeam());
    	pos.setText(player.getPos());
    	age.setText(String.valueOf(player.getAge()));
    	p.setText(String.valueOf(player.getPpg()));
    	r.setText(String.valueOf(player.getRpg()));
    	a.setText(String.valueOf(player.getApg()));
    	s.setText(String.valueOf(player.getSpg()));
    	b.setText(String.valueOf(player.getBpg()));
    	t.setText(String.valueOf(player.getTpg()));
    }
    
    /*************************************************************************************************************************
     * Handle details button
     ************************************************************************************************************************/
    @FXML
    private void handleDetailsButton() {
    	ObservableList<Player> selected =  playerTable.getSelectionModel().getSelectedItems();
    	if (selected.size() > 2)
    		System.out.println("Too many people selected");
//    	else if (selected.size() < 1)
//    		System.out.println("Must select at least one player");
    	else
    		mainApp.showPlayerDetailsDialog(selected);
    }
    
    /*************************************************************************************************************************
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     ************************************************************************************************************************/
    @FXML
    private void initialize() {
    	// Initialize the player table with all columns.
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
    	teamColumn.setCellValueFactory(cellData -> cellData.getValue().TeamProperty());
    	positionColumn.setCellValueFactory(cellData -> cellData.getValue().PosProperty());
    	ageColumn.setCellValueFactory(cellData -> cellData.getValue().AgeProperty().asObject());
    	ppgColumn.setCellValueFactory(cellData -> cellData.getValue().PpgProperty().asObject());
    	rpgColumn.setCellValueFactory(cellData -> cellData.getValue().RpgProperty().asObject());
    	apgColumn.setCellValueFactory(cellData -> cellData.getValue().ApgProperty().asObject());
    	spgColumn.setCellValueFactory(cellData -> cellData.getValue().SpgProperty().asObject());
    	bpgColumn.setCellValueFactory(cellData -> cellData.getValue().BpgProperty().asObject());
    	tpgColumn.setCellValueFactory(cellData -> cellData.getValue().TpgProperty().asObject());
    	
    	//String str = "ATL-Hawks-Banner.png";
    	//banner.setImage(new Image("file:resources/images/" + str));
    }
    
    /*************************************************************************************************************************
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
     ************************************************************************************************************************/
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        team.setText("All Players");
        
        // Add observable list data to the table and make it multi-select
        playerTable.setItems(mainApp.getPlayerData());
        playerTable.getSelectionModel().selectLast();
        playerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        //Add list of cities to ChoiceBox
        mainApp.populateTeamChoiceBox(teamChoiceBox);
        teamChoiceBox.getSelectionModel().selectFirst();
        
        //Changes table to display selected team or all players
        teamChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> display(newValue, mainApp.choiceBoxSelection(newValue), mainApp));
    }
}
