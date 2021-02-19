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
		testSrt();
		System.out.println();
		for (Process p: processes) {
			System.out.println(p.getSrtWait());
		}
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
	
	//Finds shortest remaining job **************************************************************************************
	public static Process shortestP(Process p, ArrayList<Process> list, int time) {
		for (int j = 0; j < list.size(); j++) {//Cycles through all Processes
			Process p2 = list.get(j);//Process compared against
			if ((p2.getArrivalTime() <= time) && (p2.getBurstTime() < p.getBurstTime()))//If shorter Process found
				p = p2;
			
		}
		return p;
	}
	
	//Checks if any Process has Arrived **********************************************************************************
	private static boolean hasArrived(ArrayList<Process> list, int time) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getArrivalTime() <= time)
				return true;
		}
		return false;
	}
	
	/**************************************************************************************************************\
	* 
	\* ************************************************************************************************************/
	public static void testSrt() {
		ArrayList<Process> list = new ArrayList<Process>(processes);
		
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
						System.out.print(" [~" + sp.getArrivalTime() + "|" + time + "]");/** For testing purposes**/
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
						System.out.print(" [*" + psp.getName() + "] ");/** For testing purposes**/
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
}
