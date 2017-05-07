import java.util.*;


public class Count_sort {
	public static void main(String[] args){
		Random r = new Random();
		int size=0; //Initializing size of the array
		int no_of_runs=0; // for the number of runs
		
		try {
			//reading the size of array
			Scanner scan_array_size = new Scanner(System.in); // scanner for array size
			Scanner scan_runs = new Scanner(System.in); //scanner for number of runs
			System.out.println("Enter the number of elements to sort(using count sort):");
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
		random_integers[i] = r.nextInt(3000000);
	}
	
	// count sort start
	long start = System.currentTimeMillis(); // starting timer
	int low = 0;
	int high = random_integers.length - 1;
	countSort(random_integers);
	long time = System.currentTimeMillis() - start; //ending timer
	// count sort end
	
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
			e.printStackTrace();
		} // end of catch
		
	} // end of main

	

	

// count sort algorithm


	private static void countSort(int[] arr) {
		
		int N = arr.length;
        if (N == 0)
            return;
        /** find max and min values **/
        int max = arr[0], min = arr[0];
        for (int i = 1; i < N; i++)
        {
            if (arr[i] > max)
                max = arr[i];
            if (arr[i] < min)
                min = arr[i];
        }
        //int MAX_RANGE = 1000000;
        int range = max - min + 1;
        
        
        int[] count = new int[range];
        /** make count/frequency array for each element **/
        for (int i = 0; i < N; i++)
            count[arr[i] - min]++;
        /** modify count so that positions in final array is obtained **/
        for (int i = 1; i < range; i++)
            count[i] += count[i - 1];
        /** modify original array **/
        int j = 0;
        for (int i = 0; i < range; i++)
            while (j < count[i])
                arr[j++] = i + min;
		
	}





	// this function prints the array
	private static void PrintArray(int[] random_integers) {
		for (int i = 0; i < random_integers.length; i++)
		{System.out.println(random_integers[i]);}
		
	}

	
} // end of count sort
