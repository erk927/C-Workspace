package se.project2.basketball.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
	
	//***** Variables
	StringProperty name, team, pos;
	DoubleProperty age, ppg, rpg, apg, spg, bpg, tpg;
	
	//Default Constructor
	public Player(){
		this.name = new SimpleStringProperty("Unknown");
		this.team = new SimpleStringProperty("Unknown");
		this.pos = new SimpleStringProperty("Unknown");
		this.age = new SimpleDoubleProperty(0.0);
		this.ppg = new SimpleDoubleProperty(0.0); //points per game
		this.rpg = new SimpleDoubleProperty(0.0); //rebounds per game
		this.apg = new SimpleDoubleProperty(0.0); //assists per game
		this.spg = new SimpleDoubleProperty(0.0); //steals per game
		this.bpg = new SimpleDoubleProperty(0.0); //block per game
		this.tpg = new SimpleDoubleProperty(0.0); //turnovers per game
	}
	
	//Overloaded Constructor
	public Player(String name, String team){
		this.name = new SimpleStringProperty(name);
		this.team = new SimpleStringProperty(team);
	}
	
	//Overloaded Constructor
	public Player(String name, String team, String pos, double age, double ppg, double rpg, double apg, double spg, double bpg, double tpg){
		this.name = new SimpleStringProperty(name);
		this.team = new SimpleStringProperty(team);
		this.pos = new SimpleStringProperty(pos);
		this.age = new SimpleDoubleProperty(age);
		this.ppg = new SimpleDoubleProperty(ppg);
		this.rpg = new SimpleDoubleProperty(rpg);
		this.apg = new SimpleDoubleProperty(apg);
		this.spg = new SimpleDoubleProperty(spg);
		this.bpg = new SimpleDoubleProperty(bpg);
		this.tpg = new SimpleDoubleProperty(tpg);
	}

	// Getters~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public String getName() { return name.get(); }

	public String getTeam() { return team.get(); }

	public String getPos() { return pos.get(); }

	public double getAge() { return age.get();}

	public double getPpg() { return ppg.get(); }

	public double getRpg() { return rpg.get(); }

	public double getApg() { return apg.get(); }

	public double getSpg() { return spg.get(); }

	public double getBpg() { return bpg.get(); }

	public double getTpg() { return tpg.get(); }
	   /************ Property Getters **************/
	public StringProperty NameProperty() { return name; }
	
	public StringProperty TeamProperty() { return team; }
	
	public StringProperty PosProperty() { return pos; }
	
	public DoubleProperty AgeProperty() { return age;}
	
	public DoubleProperty PpgProperty() { return ppg; }
	
	public DoubleProperty RpgProperty() { return rpg; }
	
	public DoubleProperty ApgProperty() { return apg; }
	
	public DoubleProperty SpgProperty() { return spg; }
	
	public DoubleProperty BpgProperty() { return bpg; }
	
	public DoubleProperty TpgProperty() { return tpg; }
	//End Getters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//ToString
	public String toString() {
		System.out.printf("%-25s%-19s%-19s", this.name.get(), this.team.get(), this.pos.get());
		return "";
	}
}
