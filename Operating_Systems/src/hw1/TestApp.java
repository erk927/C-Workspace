package hw1;

import java.util.ArrayList;
import java.util.Scanner;

public class TestApp {


	//***** Data *****
	static int numOfProcesses = 0;
	static int timeQuantum = 0;
	static double avgWait, avgTurn;
	static ArrayList<Process> processes = new ArrayList<Process>();
	
	/*******************************************************************************************\
     * Main
    /*******************************************************************************************/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		getNumOfProcesses(input);
		
		System.out.println("-------------------------------");
		
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
	
	//Finds shortest remaining job***************************************************************************************
	public static Process shortestP(Process p, ArrayList<Process> list, int time) {
		for (int j = 0; j < list.size(); j++)//Cycles through all Processes again
		{
			Process p2 = list.get(j);//Process compared against
			if ((p2.getArrivalTime() <= time) && (p2.getBurstTime() < p.getBurstTime()))//If shorter Process found
			{
				p = p2;
			}
		}
		
		return p;
	}
	
	//Checks if given Process has Arrived *******************************************************************************
	private static boolean hasArrived(Process p, int time) {
		if (p.getArrivalTime() <= time)
			return true;
		
		return false;
	}
	
	private static void doStuff() {
		/**
		 * does stuff in SRT
		 * */
	}
	
	/************************************************************************
	 * 
	 * **********************************************************************/
	public static void testSrt() {
		ArrayList<Process> list = new ArrayList<Process>(processes);
		int time = 0;
		
		int timeInCpu = 0;
		
		//Can ignore this while cause it runs until list is empty
		while (list.size() > 0) //While there are still processes in list
		{
			for (int i = 0; i < list.size(); i++) //Cycle through list once 
			{
				if (hasArrived(list.get(i), time))//Checks if current Process has Arrived
				{
					Process p = shortestP(list.get(i), list, time);//Sets p equal to SRJ
					//TODO : Thinking of having another method the does the logic for the Process
					//Need to increment time and make it accurate with time++ below (may use newTime)
					//Need to Print gant chart
				}
					
			}
			time++;
		}
	}
	
	/** Things to do 
	 *	-Decrease Burst time for every increment
	 *	-Increase time for every increment
	 *		-Make sure time is accurate, cause I'm increasing it inside a loop
	 *	-Print gant chart	
	 *
	 ** After you have the shortest Process
	 *	-track  
	 * */
	
	
}
































