package se.basketball.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
	//***** Variables
	StringProperty name, team, pos;
	
	//Default Constructor
	public Player(){
		this.name = new SimpleStringProperty("Unknown");
		this.team = new SimpleStringProperty("Unknown");
		this.pos = new SimpleStringProperty("Unknown");
	}
	
	//Overloaded Constructor
	public Player(String name, String team){
		this.name = new SimpleStringProperty(name);
		this.team = new SimpleStringProperty(team);
	}
	
	//Overloaded Constructor
	public Player(String name, String team, String pos){
		this.name = new SimpleStringProperty(name);
		this.team = new SimpleStringProperty(team);
		this.pos = new SimpleStringProperty(pos);
	}

	//***** Getters and Setters ****************
	public StringProperty NameProperty() { //For table/FXML, returns name as property
		return name;
	}
	
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty TeamProperty() {
		return team;
	}
	
	public String getTeam() {
		return team.get();
	}

	public void setTeam(String team) {
		this.team.set(team);
	}

	public StringProperty PositionProperty() {
		return pos;
	}
	
	public String getPosition() {
		return pos.get();
	}

	public void setPos(String pos) {
		this.pos.set(pos);
	}//***** End getters and setters ************
	
}
