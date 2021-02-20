package hw1;

import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {
	
	//***** Data *****
	static int numOfProcesses = 0;
	static int timeQuantum = 0;
	static double fcfsAvgWait, fcfsAvgTurn,
					sjfAvgWait, sjfAvgTurn,
					srtAvgWait, srtAvgTurn,
					rrAvgWait, rrAvgTurn;
	static ArrayList<Process> processes = new ArrayList<Process>();
	
	/*******************************************************************************************\
     * Main
    /*******************************************************************************************/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		getNumOfProcesses(input);
		
		System.out.println("-------------------------------");
		fcfs();
		sjf();
		srt();
		rr();
		findAvgWt();
		printTable();
	}

	/*******************************************************************************************\
     * Get number of processes
    /*******************************************************************************************/
	public static void getNumOfProcesses(Scanner input) {
		System.out.print("Enter Number of Processes: ");
		try {	
			numOfProcesses = input.nextInt();
			createProcess(input);
		}
		catch (NumberFormatException e){
			System.out.println("Not a number");
		}
	}
	
	/*******************************************************************************************\
     * Creates processes with arrival times and burst times
    /*******************************************************************************************/
	public static void createProcess(Scanner input) {
		int arTime = 0;
		int brTime = 0;
		
		for (int i = 0; i < numOfProcesses; i++) {
			System.out.println();
			
			//Get arrival time
			System.out.print("Enter Arrival Time for p" + (i+1) + ": ");
			arTime = input.nextInt();
			
			//Get burst time
			System.out.print("Enter Burst Time for p" + (i+1) + ": ");
			brTime = input.nextInt();
			
			//Create Process and add to list
			Process p = new Process(i, arTime, brTime);
			processes.add(p);
		}
		
		//Get time quantum
		System.out.print("\nEnter Time Quantum: ");
		timeQuantum = input.nextInt();
	}
	
	
	/*******************************************************************************************\
     * FCFS: First Come First Serve algorithm
    /*******************************************************************************************/
	public static void fcfs() {
		ArrayList<Process> list = new ArrayList<Process>(processes);
		int time = -1;//Tracks time
		System.out.println();
		
		while(list.size() > 0) {
			time++;
			int i = 0;
			while (i < list.size()) {
				Process p = list.get(i);
				if (p.getArrivalTime() <= time) {//If process has arrived
					System.out.print(p.getName() + " " + time + "-" + (time + p.getBurstTime()) + ", "); //Prints: P1 0-5, 
					fcfsWaitTime(p, time);
					time+= p.getBurstTime();//updates time
					fcfsTurnTime(p, time);
					list.remove(i);
					i = 0;//rechecks because time passed in other method
					continue;
				}
				i++;
			}
		}
	}
	
	//********** fcfsWaitTime ******************
	public static void fcfsWaitTime(Process p, int time) {
		if ((time - p.getArrivalTime()) <= 0){// if wait time is 0
			processes.get(p.getIndex()).setFcfsWait(0);
		}
		else {
			processes.get(p.getIndex()).setFcfsWait(time-p.getArrivalTime());
		}
	}
	
	//********** fcfsTurnTime ******************
	public static void fcfsTurnTime(Process p, int time) {
		if ((time - p.getArrivalTime()) <= 0){
			processes.get(p.getIndex()).setFcfsTurn(0);
		}
		else {
			processes.get(p.getIndex()).setFcfsTurn(time-p.getArrivalTime());
		}
	}
	
	/*******************************************************************************************\
     * SJF: Shortest Job First algorithm
    /*******************************************************************************************/
	public static void sjf() {
		ArrayList<Process> list = new ArrayList<Process>(processes);
		int time = -1;//Tracks time
		System.out.println();
		
		while(list.size() > 0) 
		{
			time++;
			int i = 0;
			while (i < list.size()) 
			{
				Process p = list.get(i);
				if (p.getArrivalTime() <= time) 
				{
					p = shortestP(p, list, time);
					System.out.print(p.getName() + " " + time + "-" + (time + p.getBurstTime()) + ", "); //Prints: P1 0-5,
					sjfWaitTime(p, time);
					time+= p.getBurstTime();//updates time
					sjfTurnTime(p, time);
					list.remove(list.indexOf(p));
					i = 0;//rechecks because time passed in other method
					continue;
				}
				i++;
			}
		}
	}
	
	//********** sjfWaitTime ******************
	public static void sjfWaitTime(Process p, int time) {
		if ((time - p.getArrivalTime()) <= 0){// if wait time is 0
			processes.get(p.getIndex()).setSjfWait(0);
		}
		else {
			processes.get(p.getIndex()).setSjfWait(time-p.getArrivalTime());
		}
	}
	
	//********** sjfTurnTime ******************
	public static void sjfTurnTime(Process p, int time) {
		if ((time - p.getArrivalTime()) <= 0){// if wait time is 0
			processes.get(p.getIndex()).setSjfTurn(0);
		}
		else {
			processes.get(p.getIndex()).setSjfTurn(time-p.getArrivalTime());
		}
	}
	
	/**Finds shortest remaining job ************/
	public static Process shortestP(Process p, ArrayList<Process> list, int time) {
		for (int j = 0; j < list.size(); j++) {//Cycles through all Processes
			Process p2 = list.get(j);//Process compared against
			if ((p2.getArrivalTime() <= time) && (p2.getBurstTime() < p.getBurstTime()))//If shorter Process found
				p = p2;
			
		}
		return p;
	}
	
	/**************************************************************************************************************\
	* SRT: Shortest Remaining Time Alogrithm
	/**************************************************************************************************************/
	public static void srt() {
		ArrayList<Process> list = new ArrayList<Process>(processes);
		System.out.println();
		
		//Creates a copy of Process Burst Times
		int burstTimes[] = new int[list.size()];
		for(int i = 0; i < burstTimes.length; i++) {
			burstTimes[i] = list.get(i).getBurstTime();
		}
			
		int time = 0;
		int timeInCpu = 0;//TiC
		
		Process psp = list.get(0);//Previous shortest Process
		boolean fl = true;//First loop
		
		
		while (list.size() > 0) //While there are still processes in list
		{
			if (hasArrived(list, time)) //If a Process has arrived
			{
				while (hasArrived(list, time)) //While there are arrived Processes
				{//WL2 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					Process sp = shortestP(list.get(0), list, time);//Sets p equal to SRJ
					if (fl == true) {psp = sp; fl = false;}//Maintains accuracy
					
					if (sp.getBurstTime() == 0) {//When process completes
//						System.out.print(" [~" + sp.getArrivalTime() + "|" + time + "]");/** For testing purposes**/
						System.out.print(sp.getName() + " " + (time-timeInCpu) + "-" + time + ", ");//Print Gant info
						sp.setSrtTurn(time - sp.getArrivalTime());//Set TurnAroundTime
						sp.setSrtWait(sp.getSrtTurn() - burstTimes[sp.getIndex()]);//Set ArrivalTime
						list.remove(list.indexOf(sp));//Remove Process from list
						timeInCpu = 0;//Reset timeInCpu
						fl = true;
					}
					else if (sp == psp) {//If current Process is still SRJ
						timeInCpu += sp.decrementBT();//Increase TiC by 1, decrease BT by 1
						time++;
					} 
					else {//If shorter Process arrives
//						System.out.print(" [*" + psp.getName() + "] ");/** For testing purposes**/
						System.out.print(psp.getName() + " " + (time-timeInCpu) + "-" + time + ", ");//Print Gant info
						timeInCpu = 0;//Reset timeInCpu
						fl = true;
					}
				}// End WL2 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			} 
			else { time++; }//If no Process has arrived
		}
		
		//Resets Burst Times
		for(int i = 0; i < burstTimes.length; i++) {
			processes.get(i).setBurstTime(burstTimes[i]);
		}
	}
	
	/**Checks if any Process has Arrived **************************/
	private static boolean hasArrived(ArrayList<Process> list, int time) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getArrivalTime() <= time)
				return true;
		}
		return false;
	}
	
	
	public static void rr() {
		System.out.println();
		ArrayList<Process> list = new ArrayList<Process>(processes);
		rrWaitTime(list.size(), list);
		
	}
	
	
	// rr~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static void rrWaitTime(int n, ArrayList<Process> list){
		int completion_time[] = new int[n];
		int rem_time[] = new int[n];

		for (int i = 0; i < n; i++) {
			rem_time[i] = list.get(i).getBurstTime();
		}
		
		int t = 0;
		int arrival = 0;

		while (true) 
		{
			boolean done = true;
			for (int i = 0; i < n; i++) 
			{
				if (rem_time[i] > 0) 
				{
					done = false;
					if (rem_time[i] > timeQuantum && list.get(i).getArrivalTime() <= arrival) {
						t += timeQuantum;
						rem_time[i] -= timeQuantum;
						arrival++;
					} 
					else 
					{
						if (list.get(i).getArrivalTime() <= arrival) {
							arrival++;
							t += rem_time[i];
							rem_time[i] = 0;
							completion_time[i] = t;
						}
					}
				}
			}

			if (done == true) {
				break;
			}
		}
		
		for (int i = 0; i < completion_time.length; i++) {
			list.get(i).setRrTurn(completion_time[i] - list.get(i).getArrivalTime());
			list.get(i).setRrWait(list.get(i).getRrTurn() - list.get(i).getBurstTime());
		}
	}
	
	/**********************************************************************************************************
	 * 
	\*********************************************************************************************************/
	public static void printTable() {
		System.out.println();
		
		System.out.println("Wait Times:");
		System.out.printf("%-10s%10s%10s%10s%10s\n", "Processes", "FCFS", "SJF", "SRT", "RR");
		for (Process p: processes) {
			System.out.printf("%5s%14d%10d%10d%10d\n", p.getName(), p.getFcfsWait(), p.getSjfWait(), p.getSrtWait(), p.getRrWait());
		}
		System.out.printf("%5s%9.2f%10.2f%10.2f%10.2f\n", "Average Wait", fcfsAvgWait, sjfAvgWait, srtAvgWait, rrAvgWait);
		
		System.out.println("---------------------------------------------------------------");

		System.out.println("Turnaround Times:");
		System.out.printf("%-10s%10s%10s%10s%10s\n", "Processes", "FCFS", "SJF", "SRT", "RR");
		for (Process p: processes) {
			System.out.printf("%5s%14d%10d%10d%10d\n", p.getName(), p.getFcfsTurn(), p.getSjfTurn(), p.getSrtTurn(), p.getRrTurn());
		}
		System.out.printf("%5s%9.2f%10.2f%10.2f%10.2f\n", "Average Turn", fcfsAvgTurn, sjfAvgTurn, srtAvgTurn, rrAvgTurn);
	}
	
	/********** find avg WT ********************/
	public static void findAvgWt(){
		double f_wt = 0;
		double f_tt = 0;
		
		double sjf_wt = 0;
		double sjf_tt = 0;
		
		double srt_wt = 0;
		double srt_tt = 0;
		
		double rr_wt = 0;
		double rr_tt = 0;
		
		for (Process p: processes) {
			f_wt += p.getFcfsWait();
			sjf_wt += p.getSjfWait();
			srt_wt += p.getSrtWait();
			rr_wt += p.getRrWait();
			
			f_tt += p.getFcfsTurn();
			sjf_tt += p.getSjfTurn();
			srt_tt += p.getSrtTurn();
			rr_tt += p.getRrTurn();
		}
		
		fcfsAvgWait = f_wt/processes.size();
		fcfsAvgTurn = f_tt/processes.size();
		
		sjfAvgWait = sjf_wt/processes.size();
		sjfAvgTurn = sjf_tt/processes.size();
		
		srtAvgWait = srt_wt/processes.size();
		srtAvgTurn = srt_tt/processes.size();
		
		rrAvgWait = rr_wt/processes.size();
		rrAvgTurn = rr_tt/processes.size();
	}
}




























