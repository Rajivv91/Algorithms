import java.util.*;

public class Quick_sort {
	public static void main(String[] args){
		Random r = new Random();
		int size=0; //Initializing size of the array
		int no_of_runs=0; // for the number of runs
		
		
		try {
			//reading the size of array
			Scanner scan_array_size = new Scanner(System.in); // scanner for array size
			Scanner scan_runs = new Scanner(System.in); //scanner for number of runs
			System.out.println("Enter the number of elements to sort (using quick sort):");
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
	
	//  quick sort start
	int low = 0;
	int high = random_integers.length - 1;
	long start = System.currentTimeMillis(); // starting timer
	QuickSort(random_integers,low,high);
	long time = System.currentTimeMillis() - start; //ending timer
	// quick sort end
	
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



    // quick sort algorithm starts here
	private static void QuickSort(int[] arr, int low, int high) {
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}
 
			while (arr[j] > pivot) {
				j--;
			}
 
			if (i <= j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			QuickSort(arr, low, j);
 
		if (high > i)
			QuickSort(arr, i, high);
	}

	




	// this function prints the array
	private static void PrintArray(int[] random_integers) {
		for (int i = 0; i < random_integers.length; i++)
		{System.out.println(random_integers[i]);}
		
	}

	

} // end of Quick sort
