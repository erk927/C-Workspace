package se.project2.basketball.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import se.project2.basketball.MainApp;
import se.project2.basketball.model.Player;

public class PlayerDetailsDialogController {
	
	/** Variable **/
	private Stage dialogStage;
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
    
    @FXML private Label name1;
    @FXML private Label tm1;
    @FXML private Label pos1;
    @FXML private Label age1;
    @FXML private Label p1;
    @FXML private Label r1;
    @FXML private Label a1;
    @FXML private Label s1;
    @FXML private Label b1;
    @FXML private Label t1;
    
    // Reference to the main application.
    private MainApp mainApp;

    /*************************************************************************************************************************
     * Display selected players information
     ************************************************************************************************************************/
    public void displayPlayerStats(Player player){
    	System.out.println(player.getName());
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
    	//Testing to make sure setup has gone as planned
    }
    
    /*************************************************************************************************************************
     * Display selected players information
     ************************************************************************************************************************/
    public void displaySecondPlayerStats(Player player){
    	System.out.println(player.getName());
    	name1.setText(player.getName());
    	tm1.setText(player.getTeam());
    	pos1.setText(player.getPos());
    	age1.setText(String.valueOf(player.getAge()));
    	p1.setText(String.valueOf(player.getPpg()));
    	r1.setText(String.valueOf(player.getRpg()));
    	a1.setText(String.valueOf(player.getApg()));
    	s1.setText(String.valueOf(player.getSpg()));
    	b1.setText(String.valueOf(player.getBpg()));
    	t1.setText(String.valueOf(player.getTpg()));
    }
    
    /*************************************************************************************************************************
     * Displays 1 or 2 players details 
     ************************************************************************************************************************/
    public void playerDetails(ObservableList<Player> selected, BorderPane page) {
    	if (selected.size() == 1) {
    		displayPlayerStats(selected.get(0));
    		hideView();
    		page.setRight(null);
    		page.setCenter(null);
    	}
    	else {
    		displayPlayerStats(selected.get(0));
    		displaySecondPlayerStats(selected.get(1));
    	}
    		
    }
    
    /*************************************************************************************************************************
     * Hides 2cd players details if only one person is selected
     ************************************************************************************************************************/
    public void hideView() {
    	name1.setVisible(false);
    	tm1.setVisible(false);
    	pos1.setVisible(false);
    	age1.setVisible(false);
    	p1.setVisible(false);
    	r1.setVisible(false);
    	a1.setVisible(false);
    	s1.setVisible(false);
    	b1.setVisible(false);
    	t1.setVisible(false);
    }
    
    /****************************************************************************************
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     ***************************************************************************************/
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /*************************************************************************************************************************
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
     ************************************************************************************************************************/
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
}
