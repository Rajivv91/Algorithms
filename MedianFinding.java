import java.util.Random;
import java.util.Scanner;

public class Median_Finding {
	
	public static void main(String[] args){
		Random high = new Random();
		int size=0; //Initializing size of the array
		int no_of_runs=0; // for the number of runs
		int kth_smallest=0; // kth smallest element
		
		try {
			//reading the size of array
			Scanner scan_array_size = new Scanner(System.in); // scanner for array size
			Scanner scan_runs = new Scanner(System.in); //scanner for number of runs
			Scanner scan_ith_smallest = new Scanner(System.in); // scanner for array size
			
			System.out.println("Enter the number of elements to sort (using quick sort):");
			size = scan_array_size.nextInt();  
			
			System.out.println("\nsize how many times you want to run the algorithm??");
			no_of_runs = scan_runs.nextInt();
			
			System.out.println("\nsize Please enter the ith smallest element you want to find");
			kth_smallest = scan_ith_smallest.nextInt();
			
			float average=0;
	
			
for (int x = 1; x <= no_of_runs; x++) {
	//generating random numbers
	int[] random_integers = new int[size];

	random_integers[0] = high.nextInt(500000);

    for(int nextNumber = 1; nextNumber < random_integers.length; ++nextNumber) {
    	random_integers[nextNumber] = high.nextInt(20);

        for( int testNumber = 0; testNumber < nextNumber; ++testNumber ) {
            if( random_integers[testNumber] == random_integers[nextNumber] ) {
            	random_integers[nextNumber] = high.nextInt(500000);
                testNumber = 0;
            }
        }// end of for, test number
    }// end of for, next number
	
    /**
	// print the array before sorting
				System.out.println("before sorting");
				PrintArray(random_integers);
	*/
	
	int low = 0;
	int high1 = random_integers.length - 1;
	int smallest;
	long start = System.currentTimeMillis(); // starting timer
	smallest = kthSmallest(random_integers,low,high1,kth_smallest);
	long time = System.currentTimeMillis() - start; //ending timer
	
	// print the array after sorting
	System.out.println("after sorting");
	PrintArray(random_integers);
	
	System.out.println("\nrun "+ x +" time taken:" + time + " milli second");
	System.out.println("\nkth smallest element is:"+smallest);
	//average[x]=(int)time;
	average+=(float)time;
} // end of for loop for number of runs
			System.out.println("\naverage time taken: "+average/no_of_runs+ " milli second" );


		} // end of try 
		catch (Exception e) {
			System.out.println("\nsize Invalid Input");
			e.printStackTrace();
		} // end of catch
		
	} // end of main

	
	private static int medianFind(int[] tempArr, int size) {
		int i,j,min,temp;
		for ( i = 0; i < tempArr.length; i++){
			min=i;
			for(j=i+1; j < tempArr.length; j++){
				if(tempArr[j]<tempArr[min])
					min=j;
			}
			// now swap the elements
			temp=tempArr[min];
			tempArr[min]=tempArr[i];
			tempArr[i]=temp;
		}
		
		return tempArr[size/2];
	}// end of medianFind
	
	
	public static int kthSmallest(int[] arr, int low, int high, int k){
		if (k > 0 && k <= high-low+1){
			int size = high-low+1;
			int i;
			
			int [] median = new int[(size+4)/5];
			int [] temp = new int[5];
			
			for (i=0; i<size/5; i++){
				for (int j=0; j<5; j++)
					temp[j] = arr[i*5+j];
				
				median[i] = medianFind(temp, 5);
				
			}
			if (i*5 < size) //For last group with less than 5 elements
	        {
				for (int j=0; j < (i*5)%size; j++)
					temp[j] = arr[i*5+j];
				
				median[i] = medianFind(temp, (i*5)%size);
				
				i++;
	        }    
	 
	   	    int pivot;
	        if (i==1)
	        	pivot = median[i-1];
	        else
	        	pivot = kthSmallest(median, 0, i-1, i/2);
	 
	        // Partition the array around a random element and
	        // get position of pivot element in sorted array
	        int pos = partition(arr, low, high, pivot);
	 
	        // If position is same as k
	        if (pos-low == k-1)
	            return arr[pos];
	        if (pos-low > k-1)  // If position is more, recur for left
	            return kthSmallest(arr, low, pos-1, k);
	 
	        // Else recur for right subarray
	        return kthSmallest(arr, pos+1, high, k-pos+low-1);
	    }
	 
	    // If k is more than number of elements in array
	    return 9999999;
	}// end of method ith smallest
	
	static int partition(int arr[], int low, int high, int x)
	{
	    // Search for x in arr[low..high] and move it to end
	    int i;
	    for (i=low; i<high; i++)
	        if (arr[i] == x)
	           break;
	    swap(arr, i, high);
	 
	    // Standard partition algorithm
	    i = low;
	    for (int j = low; j <= high - 1; j++)
	    {
	        if (arr[j] <= x)
	        {
	            swap(arr, i, j);
	            i++;
	        }
	    }
	    swap(arr, i, high);
	    return i;
	}
	
	private static void swap (int [] arr, int i, int j){
		int temp = arr[i];
		arr [i] = arr [j];
		arr[j] = temp;
	}
	// this function prints the array
		private static void PrintArray(int[] random_integers) {
			for (int i = 0; i < random_integers.length; i++)
			{System.out.println(random_integers[i]);}
		}
	

}//end of class


