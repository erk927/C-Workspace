package hw1;

import java.util.ArrayList;
import java.util.Scanner;

public class AlgorithmsWork {
	//***** Data 
	static int numOfProcesses = 0;
	static int timeQuantum = 0;
	static ArrayList<Integer> arrivalTime = new ArrayList<Integer>();
	static ArrayList<Integer> burstTime = new ArrayList<Integer>();

    /*************************************************************************************************************************
     * main
     ************************************************************************************************************************/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		getNumOfProcesses(input);
		
//		for (int i = 0; i < numOfProcesses; i++) {
//			System.out.print(p1.get(i));
//		}
	}
	
	/*********************************************************************************/
	//Implement algorithms, it's easier than I think
	//*************************************

    /*************************************************************************************************************************
     * Read number of Processes from keyboard
     ************************************************************************************************************************/
	public static void getNumOfProcesses(Scanner input) {
		System.out.print("Enter Number of Processes: ");
		String str = input.nextLine();
		
		try {	
			numOfProcesses = Integer.valueOf(str);
			getArrivalTime(input);
		}
		catch (NumberFormatException e){
			System.out.println("Not a number");
		}
	}
	
    /*************************************************************************************************************************
     * Read number of ArrivalTime from keyboard
     ************************************************************************************************************************/
	public static void getArrivalTime(Scanner input) {
		for (int i = 0; i < numOfProcesses; i++) {
			System.out.print("Enter Arrival Time for p" + (i+1) + ": ");
			arrivalTime.add(Integer.valueOf(input.next()));
		}
		getBurstTime(input);
	}
	
    /*************************************************************************************************************************
     * Read number of BurstTime from keyboard
     ************************************************************************************************************************/
	public static void getBurstTime(Scanner input) {
		for (int i = 0; i < numOfProcesses; i++) {
			System.out.print("Enter Burst Time for p" + (i+1) + ": ");
			burstTime.add(Integer.valueOf(input.next()));
		}
		getTimeQuantum(input);
	}
	
    /*************************************************************************************************************************
     * Read number of Time Quantum from keyboard
     ************************************************************************************************************************/
	public static void getTimeQuantum(Scanner input) {
		String str = input.nextLine();
		timeQuantum = Integer.valueOf(str);
	}
}

















