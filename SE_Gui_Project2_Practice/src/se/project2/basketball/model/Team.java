package se.project2.basketball.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Team {
	// Variables
	private StringProperty city;
	private IntegerProperty numOfPlayers;
	ObservableList<Player> roster = FXCollections.observableArrayList();;

	//Default Constructor
	public Team(){
		this.city = new SimpleStringProperty("Unknown");
		this.numOfPlayers = new SimpleIntegerProperty(0);
	}
	
	//Overloaded Constructor: Takes team Name
	public Team(String teamCity){
		this.city = new SimpleStringProperty(teamCity);
		this.numOfPlayers = new SimpleIntegerProperty(0);
	}
	
	//Overloaded Constructor: Takes Team name, and number of players
	public Team(String city, int numPlayers){
		this.city = new SimpleStringProperty(city);
		this.numOfPlayers = new SimpleIntegerProperty(numPlayers);
	}
	
	// Getters~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public String getCity() { return city.get(); }
	
	public int getNumOfPLayers() { return numOfPlayers.get(); }

	public void setCity(String city) { this.city.set(city); }

	public void setNumOfPLayers(int numPlayers) { this.numOfPlayers.set(numPlayers); }

	public ObservableList<Player> getRoster() { return roster; }

	public void setRoster(ObservableList<Player> roster) { this.roster = roster; }
	 /************ Property Getters **************/
	public StringProperty CityProperty() { return city; }
	
	public IntegerProperty NumOfPlayersProperty() { return numOfPlayers; }
	//End Getters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
