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
//		testSrt();
//		System.out.println();
//		for (Process p: processes) {
//			System.out.println(p.getSrtWait());
//		}
		
		rr();
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
	
	//Checks if any Process has Arrived **********************************************************************************
	private static boolean hasArrived(ArrayList<Process> list, int time) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getArrivalTime() <= time)
				return true;
		}
		return false;
	}
	
	/********** Get next available Process **********/
	private static Process getProcess(ArrayList<Process> list, int time) {
		Process p = list.get(0);
		for (int i = 0; i < list.size(); i++) {
			p = list.get(i);
			if ((p.getArrivalTime() <= time) && (p.getBurstTime() > 0)) {
				return p;
			}
		}
		return p;
	}
	/***********************************************************************************************************************/
	public static void rr() {
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
		
	}
}

























/**********************************************************************************************************************************/
//Java program to implement Round Robin
//Scheduling with different arrival time
//import java.util.concurrent.CompletableFuture;
class RoundRobin1 {
	// rr~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static void findWaitingTime(int process[],int wt_time[],int n ,int brusttime[],int quantum,int completion_time[],int arrival_time[]){
		// copy the value of brusttime array into wt_time array.
		int rem_time[] = new int[n];

		for (int i = 0; i < wt_time.length; i++) {
			rem_time[i] = brusttime[i];
		}
		int t = 0;
		int arrival = 0;
		// processing until the value of element of rem_time array is 0
		while (true) {
			boolean done = true;
			for (int i = 0; i < n; i++) {
				if (rem_time[i] > 0) {
					done = false;
					if (rem_time[i] > quantum && arrival_time[i] <= arrival) {
						t += quantum;
						rem_time[i] -= quantum;
						arrival++;
					} else {
						if (arrival_time[i] <= arrival) {
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
	}
	
	//Find turnaround time ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static void findTurnAroundTime(int process[] ,int wt_time[],int n,int brusttime[],int tat_time[],int completion_time[],int arrival_time[]){
		for(int i=0;i<n;i++){
			tat_time[i]= completion_time[i]-arrival_time[i];
			wt_time[i] = tat_time[i]-brusttime[i];
			
			
		}
		
	}
	
	// find average ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static void findAvgTime(int process[],int n,int brusttime[],int quantum,int arrival_time[]){
	int wt_time[] = new int[n];
	int tat_time[] = new int[n];
	int completion_time[] = new int[n];
	findWaitingTime(process,wt_time,n,brusttime,quantum,completion_time,arrival_time); 
	findTurnAroundTime(process,wt_time,n,brusttime,tat_time,completion_time,arrival_time);
	int total_wt = 0, total_tat = 0; 
	
	System.out.println("Processes " +" Arrival Time\t"+ " Burst time " +" completion time"+ 
			" Turn Around Time " + " Waiting time");
	for (int i=0; i<n; i++) 
	{ 
		total_wt = total_wt + wt_time[i]; 
		total_tat = total_tat + tat_time[i]; 
		System.out.println(" " + (i+1) + "\t\t"+ arrival_time[i]+"\t\t"+ + brusttime[i] +"\t " +completion_time[i]+"\t\t"
							+tat_time[i] +"\t\t " + wt_time[i]); 
	} 
	
	System.out.println("Average waiting time = " + 
						(float)total_wt / (float)n); 
	System.out.println("Average turn around time = " + 
						(float)total_tat / (float)n); 
	}

	//Main ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static void main(String []agrs){
	RoundRobin1 object = new RoundRobin1();
	Scanner scan = new Scanner(System.in);
	
	int quantum = 2;
	int arrival_time[] = new int[]{0,1,2,3};
	int process[] = new int[]{1,2,3,4};
	int brusttime[] = new int[]{5,4,2,1};
	int n = process.length;
	
	findAvgTime(process,n,brusttime,quantum,arrival_time);
	
	scan.close();
		
	}
}
