package hw1;

public class Process {
	
	//***** Data *****
	private String name;
	private int arrivalTime, burstTime, index;
	private int fcfsWait, fcfsTurn;
	private int sjfWait, sjfTurn;
	private int srtWait, srtTurn;
	private int rrWait, rrTurn;

	/*************************************************************************\
	 *Default Constructor 
	/*************************************************************************/
	Process(){
		this.name = "Unknown";
		this.arrivalTime = 0;
	}

	/*************************************************************************\
	 *Constructor: Name, ArrivalTime, BurstTime 
	/*************************************************************************/
	Process(int num, int arrivalTime, int burstTime){
		this.name = "P" + (num+1);
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		index = num;
	}

	

	//********** Getters and Setters ******************************************************
	public String getName() { return name; }

	public int getArrivalTime() { return arrivalTime; }

	public int getBurstTime() { return burstTime; }
	
	public int getIndex() { return index; }

	//***** FCFS
	public int getFcfsWait() { return fcfsWait; }
	public void setFcfsWait(int fcfsWait) { this.fcfsWait = fcfsWait; }
	
	public int getFcfsTurn() { return fcfsTurn; }
	public void setFcfsTurn(int fcfsTurn) { this.fcfsTurn = fcfsTurn; }

	//***** SJF
	public int getSjfWait() { return sjfWait; }
	public void setSjfWait(int sjfWait) { this.sjfWait = sjfWait; }

	public int getSjfTurn() { return sjfTurn; } 
	public void setSjfTurn(int sjfTurn) { this.sjfTurn = sjfTurn; }

	//***** SRT
	public int getSrtWait() { return srtWait; }
	public void setSrtWait(int srtWait) { this.srtWait = srtWait; }

	public int getSrtTurn() { return srtTurn; }
	public void setSrtTurn(int srtTurn) { this.srtTurn = srtTurn; }

	//***** RR
	public int getRrWait() { return rrWait; }
	public void setRrWait(int rrWait) { this.rrWait = rrWait; }

	public int getRrTurn() { return rrTurn; }
	public void setRrTurn(int rrTurn) { this.rrTurn = rrTurn; }

	//****************************************************************************
}
