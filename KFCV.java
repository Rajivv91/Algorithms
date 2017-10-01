package cross_validation;
import java.io.*;
import java.util.*;



public class KFCV {

	public static void main(String[] args) throws IOException {
		
		//reading the CSV file
		List<String[]> rowList = new ArrayList<String[]>();
		int k = Integer.parseInt(args[1]);
		//try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Rajiv R\\Desktop\\Assignments\\fourth sem- fall\\machine learning_jesus\\assignment_1\\ecoli.csv"))) {
			try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {    
			String line;
		    while ((line = br.readLine()) != null) {
		        String[] lineItems = line.split(",");
		        rowList.add(lineItems);
		    }
		    br.close();
		}
		catch(Exception e){
		    // Handle any I/O problems
		}
		
		//converting CSV file to a 2D matrix
		String[][] matrix = new String[rowList.size()][];
		for (int i = 0; i < rowList.size(); i++) {
		    String[] row = rowList.get(i);
		    matrix[i] = row;
		}
		/*System.out.println(matrix.length); // number of rows
		System.out.println(matrix[0].length); // number of columns */
		int totalrows = matrix.length;
		int totalcols = matrix[0].length;
		
		/*System.out.println("array is");
		Print2dstring(matrix); // printing the matrix */
		
		// dividing the array into attribute values array,
		//class array, attribute name array
		
		//step1 : getting attribute names array
		String[] att_names = new String[totalcols];
		for(int j = 0; j < totalcols; j++){
			att_names[j] = matrix[0][j];
		}
		//Print1dstring(att_names);
		
		//step2 : getting class array
		String[] class_names = new String[totalrows-1];
		for(int z = 0; z < totalrows-1; z++){
			class_names[z] = matrix[z+1][totalcols-1];
		}
		//Print1dstring(class_names);
		
		//step3 : getting attribute values array 
		Double[][] att_values = new Double[totalrows-1][totalcols-1];
		
		for(int i = 0; i < totalrows-1; i++)
		{
			for(int j = 0; j < totalcols-1; j++)
				att_values[i][j]= Double.parseDouble(matrix[i+1][j]) ;
			
		}
		// Print2ddouble(att_values);
		
		// normalize the attribute values
		Double[][] att_values_normalized = new Double[totalrows-1][totalcols-1];
		att_values_normalized = normalize(att_values);
		//Print2ddouble(att_values_normalized);
		
		// partitioning the data
		Double[][] att_values_partioned = new Double[totalrows-1][totalcols];
		att_values_partioned = partition(att_values_normalized,k);
		//Print2ddouble(att_values_partioned);
		
		
		
		//Combining all the values into a single array
		int final_rows = matrix.length;
		int final_cols = matrix[0].length+1;
		String[][] Final_norm_partitioned = new String[final_rows][final_cols];
		
		Final_norm_partitioned = combine(Final_norm_partitioned,att_values_partioned,att_names,class_names,att_values_normalized);
		Print2dstring(Final_norm_partitioned);
		
		
		/* writing the final result to a CSV file
		 * The final CSV file is named output.csv */
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < Final_norm_partitioned.length; i++)//for each row
		{
		   for(int j = 0; j < Final_norm_partitioned[0].length; j++)//for each column
		   {
		      builder.append(Final_norm_partitioned[i][j]+"");//append to the output string
		      if(j < Final_norm_partitioned.length - 1)//if this is not the last row element
		         builder.append(",");//then add comma (if you don't like commas you can use spaces)
		   }
		   builder.append("\n");//append new line at the end of the row
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"));
		writer.write(builder.toString());//save the string representation of the board
		writer.close();
		
	} // end of main
	
	/* this function creates a final array by combining
	 * all the arrays */
	private static String[][] combine(String[][] final_norm_partitioned, Double[][] att_values_partioned,
			String[] att_names, String[] class_names, Double[][] att_values_normalized) {
		
		// first : inserting the attribute names 
		for(int j = 0; j < final_norm_partitioned[0].length-1; j++){
			 final_norm_partitioned[0][j]=att_names[j] ;
		}
		final_norm_partitioned[0][final_norm_partitioned[0].length-1] = "Partion_number";
		
		// second : inserting class names into the array
		for(int c = 0; c < class_names.length; c++){
			final_norm_partitioned[c+1][final_norm_partitioned[0].length-2]=class_names[c] ;
		}
		
		
		// third : adding the normalized values
		for(int a=0;a<att_values_normalized.length;a++){
			for(int b=0;b<att_values_normalized[0].length;b++){
				final_norm_partitioned[a+1][b]= String.valueOf(att_values_normalized[a][b]);	
			}
		}
		
		
		// last: Assigning the partition number
		for(int a=0;a<att_values_partioned.length;a++){
			final_norm_partitioned[a+1][final_norm_partitioned[0].length-1]=String.valueOf(att_values_partioned[a][att_values_partioned[0].length-1]);
		}
		//Print2dstring(final_norm_partitioned);
		
		
		return final_norm_partitioned;
	} // end of combine()


	// this fuction partitions the normalized data
	private static Double[][] partition(Double[][] normalized,int k) {
		// getting number of rows and cols of normalized array
		int norm_rows = normalized.length;
		int norm_cols = normalized[0].length;
		
		
		
		//adding an extra column to store partition number
		Double[][] partioned = new Double[norm_rows][norm_cols+	1];
		//System.out.println(partioned.length+" "+partioned[0].length);
		int last_column = partioned[0].length;
		//System.out.println(last_column);
		
		//copy the norm array to partition array
		for(int a=0;a<norm_rows;a++){
			for(int b=0;b<norm_cols;b++){
				partioned[a][b] = normalized[a][b];
			}
		} //end of copy
		
		//initializing last cloumn to 0
		for(int f=0;f<partioned.length;f++){
			partioned[f][last_column-1] =  0.0;
		}
		//Print2ddouble(partioned);
		
		
		
		
		// assigning partition numbers
		int  j=0, Low =0,Rand_row =0, low1=1, high =norm_rows-1;
		Double Rand_part_num =0.0;
		Random r = new Random();
		
		int div = (norm_rows/k);
		for(int i=0;i<k;i++){
		while(j<div){
			 Rand_row = r.nextInt(high-Low) + Low;
			if(partioned[Rand_row][last_column-1]== 0.0){
				
				partioned[Rand_row][last_column-1] = (double) i + 1.0;
				j= j+1;
			} else continue;
			
		}	//end while
		j=0;
		//Print2ddouble(partioned);
		}//end for
		
		
		//Print2ddouble(partioned);
		
		
		// counting the number of excess rows in the partition
		int count =0;
		for(int f=0;f<partioned.length;f++){
			if(partioned[f][last_column-1] == 0.0)
			count++;
		}
		//System.out.println("count1="+count);
		
		/* now my logic for excess partition
		 * if the number if excess rows is greater than
		 *  or equal to k/2 keep the new partition
		 * else assign it to existing partition randomly */
		if(count >=(k/2)){
			for(int f=0;f<partioned.length;f++){
				if(partioned[f][last_column-1] == 0.0)
					{partioned[f][last_column-1] = k +1.0;}
			}
		} else {
			Random r1 = new Random();
			int low2=1;
			int Rand_partition = r.nextInt(k-low2) + low2;
			for(int f=0;f<partioned.length;f++){
				if(partioned[f][last_column-1] == 0.0)
					{partioned[f][last_column-1] = Rand_partition +1.0;}
			}
			
		}
		return partioned;
			
			//testing
			/*int count2 =0;
			for(int f=0;f<partioned.length;f++){
				if(partioned[f][last_column-1] == 4.0)
				count2++;
			}
		
		System.out.println("count2="+count2);*/
		//Print2ddouble(partioned);
	} //end of partition()



	// this function normalizes the attribute values
	private static Double[][] normalize(Double[][] att_values) {
		int attr_totalrows = att_values.length;
		int attr_totalcols = att_values[0].length;
		Double[] temp = new Double[attr_totalrows];
		Double min , max;
		
		//finding the max and min value in each coloumn
		for (int i = 0; i < attr_totalcols; i++){
			for(int j = 0; j < attr_totalrows; j++){
				temp [j] = att_values[j][i];
				
			}
			min = getMinValue(temp);
			max = getMaxValue(temp);
			//normalizing
			for(int v = 0; v < attr_totalrows; v++){
				att_values[v][i] = (att_values[v][i] - min) / (max - min);
			}
			
		}
		
		
		//Print1ddouble(temp);
		
		
		return att_values;
	} //end of normalize


	private static Double getMinValue(Double[] temp2) {
		Arrays.sort(temp2);
		Double min = temp2[0];
		return min;
	} // end of getMinValue()



	//get the max of the temp array 	
	private static Double getMaxValue(Double[] temp1) {
		Arrays.sort(temp1);
		Double max = temp1[temp1.length-1];
		return max;
	} //end of getMaxValue()



	// this function prints 1d string array
	private static void Print1dstring(String[] att_names) {
		for(int i = 0; i < att_names.length; i++) {
			System.out.println(att_names[i]);
		}
		
	} //end of Print1dstring

	private static void Print1ddouble(Double[] att_names) {
		for(int i = 0; i < att_names.length; i++) {
			System.out.println(att_names[i]);
		}
		
	} // end of Print1ddouble()


	// this function prints 2d string array
	private static void Print2dstring(String[][] matrix) {
		for(int i = 0; i < matrix.length; i++)
		{
		    for(int j = 0; j < matrix[i].length; j++)
		    {
		        System.out.print(matrix[i][j]);
		        if(j < matrix[i].length - 1) System.out.print(" ");
		    }
		    System.out.println();
		}
		
	} //end of Print2dstring
	
	
	// this function prints 2d string array
	private static void Print2ddouble(Double[][] tempvalues) {
		for(int i = 0; i < tempvalues.length; i++)
		{
		    for(int j = 0; j < tempvalues[i].length; j++)
		    {
		        System.out.print(tempvalues[i][j]);
		        if(j < tempvalues[i].length - 1) System.out.print(" ");
		    }
		    System.out.println();
		}
		
	} //end of Print2dstring
	
	

}// end of whole program
