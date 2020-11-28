package se.basketball;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.basketball.model.Player;
import se.basketball.view.PlayerEditDialogController;
import se.basketball.view.PlayerOverviewController;

public class MainApp extends Application {

	//These two are needed from the jump
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    //An ObservableArrayList of players. Just an ArrayList for fxml
    private ObservableList<Player> playerData = FXCollections.observableArrayList();
    
    //********************************************************************************************************
    //Constructor: just adds players to list
    //********************************************************************************************************
    public MainApp() {
    	playerData.add(new Player("Marcus White", "Raptors", "Foward Guard"));
    	playerData.add(new Player("Jason Black", "Bears"));
    	playerData.add(new Player("Terrance Howards", "Hawks"));
    	playerData.add(new Player("Tony Stark", "Gladiators"));
    	playerData.add(new Player("Steve Rogers", "Patriots"));
    	playerData.add(new Player("Bruce Banner", "Green Meanies"));
    	playerData.add(new Player("Steven Strange", "Mages"));
    	playerData.add(new Player("Natasha Romanov", "Assassins"));
    	playerData.add(new Player("Sam Coleson", "Shields", "Point Guard"));
    	playerData.add(new Player("Nick Fury", "Agents"));
    }
    
    // getPlayerData: Returns PLayer data list
    public ObservableList<Player> getPlayerData() {
		return playerData;
	}
    

    //********************************************************************************************************
    // start: this is where everything begins. Called automatically when the programs ran
    //********************************************************************************************************
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("BasketballApp");//text that shows in bar
        
        this.primaryStage.getIcons().add(new Image("file:resources/images/book.png"));

        initRootLayout();//Calls method

        showPlayerOverview();//Calls method
    }
    
    //********************************************************************************************************
    // initRootLayout
    //********************************************************************************************************
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();//created up top
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //********************************************************************************************************
    // showPersonOverview: Displays people in table by using PlayerOverview Controller
    //********************************************************************************************************
    public void showPlayerOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PlayerOverview.fxml"));
            AnchorPane playerOverview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(playerOverview);//because this is a Borderpane, you can use .setCenter method
            
            //Added in Part 2 of Tutorial
            // Give the controller access to the main app.
            PlayerOverviewController controller = loader.getController();
            controller.setMainApp(this);// calls method in Player Controller
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**********************************************************************************************************
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     *********************************************************************************************************/
    public boolean showPersonEditDialog(Player player) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PlayerEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setPlScene(scene);

            // Set the person into the controller.
            PlayerEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(player);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}