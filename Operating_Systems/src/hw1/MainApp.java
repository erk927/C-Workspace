package hw1;

import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {
	
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
//		fcfs();
		sjf();
//		srt();
//		testSrt();
		
		System.out.println("\n-------------------------------");
		for(Process p: processes) {
//			System.out.print(p.getSjfWait() + " - ");
//			System.out.print(p.getSjfTurn() + "\n");
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
	
	
	/*******************************************************************************************\
     * FCFS: First Come First Serve algorithm
    /*******************************************************************************************/
	public static void fcfs() {
		ArrayList<Process> list = new ArrayList<Process>(processes);
		int time = -1;//Tracks time
		
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
		
		while(list.size() > 0) 
		{
			time++;
			int i = 0;
			while (i < list.size()) 
			{
				Process p = list.get(i);
				if (p.getArrivalTime() <= time) 
				{
//					for (int j = i+1; j < list.size(); j++) 
//					{
//						Process p2 = list.get(j);
//						int minB = p.getBurstTime();
//						if ((p2.getArrivalTime() <= time) && (p2.getBurstTime() < minB))//if p2 arrived and burst is <
//						{
//							p = p2;//p becomes the shortest process
//							minB = p2.getBurstTime();
//						}
//					}
					p = checkStatus(p, list, time);
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
	
	/*******************************************************************************************\
     * SRT: Shortest Remaining Job algorithm
    /*******************************************************************************************/
	public static void srt() {
		ArrayList<Process> list = new ArrayList<Process>(processes);
		int time = -1;//Tracks time
		
		int timeInCpu = 0;
		
		while(list.size() > 0) 
		{
			time++;
			int i = 0;
			while (i < list.size()) 
			{
				Process p = list.get(i);
				if (p.getArrivalTime() <= time) 
				{
					p = checkStatus(p, list, time);
					System.out.print(p.getName() + " " + time + "-");// P1 0-
					int ind = p.getIndex();
					for (int j = p.getBurstTime(); j > 0; j--) {
						timeInCpu++;
						time++;
//						p.setBurstTime(p.getBurstTime()-1);
						System.out.print("yyyyyyyyyyyy----- " + p.getBurstTime() + "------yyyyyyyyyyyyyyyyyyyyyyyyy");
						p = checkStatus(p, list, time);
						if (p.getIndex() != ind) {
							System.out.print((time+timeInCpu) + ", ");
						}break;
					}
					
					
//					System.out.print(p.getName() + " " + time + "-" + (time + p.getBurstTime()) + ", "); //Prints: P1 0-5,
//					srtWaitTime(p, time);
//					time+= p.getBurstTime();//updates time
////					sjfTurnTime(p, time);
//					list.remove(list.indexOf(p));
//					i = 0;//rechecks because time passed in other method
//					continue;
				}
				i++;
			}
		}
	}
	
	//***** check *****
	public static Process checkStatus(Process p, ArrayList<Process> list, int time) {
		for (int i = 0; i < list.size(); i++) {
			Process p2 = list.get(i);
			int minB = p.getBurstTime();
			if ((p2.getArrivalTime() <= time) && (p2.getBurstTime() < minB))//if p2 arrived and burst is <
			{
				p = p2;//p becomes the shortest process
				minB = p2.getBurstTime();
			}
		}
		return p;
	}
	
	//********** srtWaitTime ******************
	public static void srtWaitTime(Process p, int time) {
//		if ((time - p.getArrivalTime()) <= 0){// if wait time is 0
//			processes.get(p.getIndex()).setSrtWait(0);
//		}
//		else {
//			processes.get(p.getIndex()).setSrtWait(time-p.getArrivalTime());
//		}
	}
	
	
	
	
	
	
	/******************************************************************************************************************************************/
	
	public static void testSrt() {
		ArrayList<Process> list = new ArrayList<Process>(processes);
		int time = -1;
		
		int timeInCpu = 0;
		
		boolean mark = true;
		
		while (list.size() > 0) //While there are still processes in list
		{
			time++;
			int i = 0;
			while (i < list.size()) 
			{
				Process p = list.get(i);
				Process sp = p; //Shortest process
				
				if (p.getArrivalTime() <= time) 
				{
					int newTime = time;
					sp = checkStatus(p, list, time);//checks if any other p has arrived and is shorter
					
					while (checkStatus(sp, list, time) == sp)//while og job is the shortest
					{
						if (sp.getBurstTime() == 0) {
							processes.get(sp.getIndex()).setSrtTurn(newTime-sp.getArrivalTime());
							processes.get(sp.getIndex()).setSrtWait(sp.getSrtTurn()-sp.getBurstTime());
							break;
						}
						timeInCpu+= sp.decrementBT();//TiC++
						newTime++;
					}
					System.out.print(sp.getName() + " " + time + "-" + newTime + ", ");// P1 1-3, 
					if (sp.getBurstTime() == 0) {list.remove(sp.getIndex());}//if process is complete
					time = newTime -1;//cause times gonna increment by 1
					timeInCpu = 0;
					i = 0;
					continue;//skips i++, and rechecks list cause checkStatus while loop failed
				}
				i++;
			}
		}
	}
}











