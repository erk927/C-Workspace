package se.project2.basketball;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.project2.basketball.model.Player;
import se.project2.basketball.model.Team;
import se.project2.basketball.view.PlayerDetailsDialogController;
import se.project2.basketball.view.PlayerOverviewController;
import se.project2.basketball.view.TeamOverviewController;

public class MainApp extends Application {

	/** Variables **************************/
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Player> playerData = FXCollections.observableArrayList();
    private ObservableList<Team> teamData = FXCollections.observableArrayList();
    private ObservableList<String> teamCity = FXCollections.observableArrayList(); //List of Team names
    
    /*****************************************************************************************************************
     * Where it all begins
    *****************************************************************************************************************/
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPlayerOverview();
    }
    
	/*******************************************************************************************************************
	 * Returns the main stage.
	 * @return
	 ******************************************************************************************************************/
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
    
//-----------------------------------------------------------------------------------------------------------------------------------------------
//													General Methods
//-----------------------------------------------------------------------------------------------------------------------------------------------
    
    /*******************************************************************************************************************
     * Constructor
     ******************************************************************************************************************/
    public MainApp() {
    	try {
    	//Scanner reads csv file with player stats
    	Scanner input = new Scanner(new File("data.csv"));
    	
    	populatePlayerData(input);
    	input.close();
    	}
    	catch (FileNotFoundException e) {
    		System.out.println("File not found");
    	}
    }
    
    /**************************************************************** 
     * Returns playerData list
     ***************************************************************/
    public ObservableList<Player> getPlayerData(){
    	return playerData;
    }
    
    /**************************************************************** 
     * Returns teamData list
     ***************************************************************/
    public ObservableList<Team> getTeamData(){
    	return teamData;
    }
    
    /**************************************************************** 
     * Returns teamCity list
     ***************************************************************/
    public ObservableList<String> getCityList(){
    	return this.teamCity;
    }
    
    /*******************************************************************************************************************
     * Populates playerData with player info from data.csv
     ******************************************************************************************************************/
    private void populatePlayerData(Scanner input) {
		//Gets player info and creates new player
		while (input.hasNextLine()) {
			String[] s1 = input.nextLine().split(",");
				playerData.add(new Player(s1[0],s1[1],s1[2],Double.valueOf(s1[3]),Double.valueOf(s1[4]),
						Double.valueOf(s1[5]),Double.valueOf(s1[6]),Double.valueOf(s1[7]),
						Double.valueOf(s1[8]),Double.valueOf(s1[9])));
			}
		//After playerData is filled, create teams and add players to their team
    	populateTeamData();
    }
    
    /*******************************************************************************************************************
     * Populates teamData with player info from data.csv
     ******************************************************************************************************************/
    private void populateTeamData() {
    	teamCity.add("All Players");//Add as first option in list
    	for (int i = 0; i < playerData.size(); i++) 
		{
			//If Team isn't on teamCity list, create team
			if (!(teamCity.contains(playerData.get(i).getTeam()))) 
			{
				teamCity.add(playerData.get(i).getTeam());
				Team team = new Team(playerData.get(i).getTeam());
				//team.setCity(playerData.get(i).getTeam());
				teamData.add(team);
			}
			//Adds player to the correct Team
			for(int j = 0; j < teamData.size(); j++) 
			{
				if (playerData.get(i).getTeam().matches(teamData.get(j).getCity())) {
					teamData.get(j).getRoster().add(playerData.get(i));
				}
			}
		}	
	}
    
    /*************************************************************************************************************************
     * Populates ChoiceBox with team names
     ************************************************************************************************************************/
    public ChoiceBox<String> populateTeamChoiceBox(ChoiceBox<String> b) {
    	b.setItems(teamCity);
    	return b;
    }
    
    /*************************************************************************************************************************
     * Return index of team selected in ChoiceBox in main view
     * @return Integer
     ************************************************************************************************************************/  
    public int choiceBoxSelection(String chosen) {
    	//Goes through list of cities ,returns index of city selected
    	for (int i = 1; i < teamCity.size(); i++) {
    		if (teamCity.get(i).matches(chosen)) {
    			return i-1;
    		}
    	}
    	//for displaying all players
    	return -1;
    }
    
//-----------------------------------------------------------------------------------------------------------------------------------------------
//															Views
//-----------------------------------------------------------------------------------------------------------------------------------------------
    /*******************************************************************************************************************
     * Initializes the root layout.
     ******************************************************************************************************************/
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*******************************************************************************************************************
     * Shows the person overview inside the root layout.
     ******************************************************************************************************************/
    public void showPlayerOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PlayerOverview.fxml"));
            //AnchorPane personOverview = (AnchorPane) loader.load();
            BorderPane personOverview = (BorderPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            // Give the controller access to the main app.
            PlayerOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*******************************************************************************************************************
     * Shows the team overview inside the root layout.
     ******************************************************************************************************************/
    public void showTeamOverview() {
        try {
            // Load team overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TeamOverview.fxml"));
            BorderPane teamOverview = (BorderPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(teamOverview);
            
            // Give the controller access to the main app.
            TeamOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*******************************************************************************************************************
     * Shows the details overview inside the root layout.
     ******************************************************************************************************************/
    public void showPlayerDetailsDialog(ObservableList<Player> selected) {
        try {
        	// Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PlayerDetailsDialog.fxml"));
            BorderPane page = (BorderPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Player Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // 
           
            // Set the person into the controller.
            PlayerDetailsDialogController controller = loader.getController();
            controller.playerDetails(selected, page);
            controller.setDialogStage(dialogStage);
            //controller.displayPlayerStats(player2);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
