
import java.util.*;
import java.io.*;

public class Selection_sort {
	public static void main(String[] args){
		Random r = new Random();
		int size=0; //Initializing size of the array
		int no_of_runs=0; // for the number of runs
		
		try {
			//reading the size of array
			Scanner scan_array_size = new Scanner(System.in); // scanner for array size
			Scanner scan_runs = new Scanner(System.in); //scanner for number of runs
			System.out.println("Enter the number of elements to sort(using selection sort):");
			size = scan_array_size.nextInt();  
			System.out.println("\n how many times you want to run the algorithm??");
			no_of_runs = scan_runs.nextInt();
			//int[] average = new int[size];
			float average=0;
			
		
 // print the array before sorting
			/*System.out.println("before sorting");
			PrintArray(random_integers);*/
			
for (int x = 1; x <= no_of_runs; x++) {
	//generating random numbers
	int[] random_integers = new int[size];
	for (int i = 0; i < random_integers.length; i++) {
		random_integers[i] = r.nextInt();
	}
	
	// selection sort start
	long start = System.currentTimeMillis(); // starting timer
	SelectionSort(random_integers);
	long time = System.currentTimeMillis() - start; //ending timer
	// selection sort end
	
	// print the array after sorting
	/*System.out.println("after sorting");
	PrintArray(random_integers);*/
	System.out.println("run "+ x +" time taken:" + time + " milli second");
	//average[x]=(int)time;
	average+=(float)time;
} // end of for loop for number of runs
			System.out.println("\n average time taken:"+average/no_of_runs+ " milli second" );


		} // end of try 
		catch (Exception e) {
			System.out.println("\n Invalid Input");
			//e.printStackTrace();
		} // end of catch
		
	} // end of main

	// The selection sort algorithm 
	private static void SelectionSort(int[] random_integers) {
		int i,j,min,temp;
		for ( i = 0; i < random_integers.length; i++){
			min=i;
			for(j=i+1;j < random_integers.length; j++){
				if(random_integers[j]<random_integers[min])
					min=j;
			}
			// now swap the elements
			temp=random_integers[min];
			random_integers[min]=random_integers[i];
			random_integers[i]=temp;
		}
		
	}


	// this function prints the array
	private static void PrintArray(int[] random_integers) {
		for (int i = 0; i < random_integers.length; i++)
		{System.out.println(random_integers[i]);}
		
	}

	

} // end of class Selection_sort
