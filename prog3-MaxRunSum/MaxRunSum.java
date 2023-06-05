package student;

/* 
 * This Student class is meant to contain your algorithm.
 * You should implement the static method:
 *   maxRunSum - which finds the largest sum among all possible runs in the array
 *   
 * input:
 *   an array of values
 * returns:
 *   an array of three integers {start, end, sum}
 *     start is the index of the start of the run
 *     end is the index after the last value in the run
 *     sum is the sum of the run
 */
public class MaxRunSum {
    public static int[] maxRunSum(int[] values)	{
        int start = 0; // a guess
        int end = 0;   // another guess
        int sum = 0;
        int tempSum = 0;

        //loops through all values
        for (int index = 0; index < values.length; index++) {
        	tempSum = 0;
        	//loops through values beginning at the current value of the previous loop
        	for (int subSize = 0; subSize < values.length-index; subSize++) {
        		//adds current index to previous calculations
        		tempSum += values[subSize+index];
        		//if tempsum is larger than sum, update values
        		if (tempSum > sum) {
        			start = index; 
        	        end = subSize+index+1;   
        	        sum = tempSum;
        		}
        	}
        }
        
        int[] resultVector = {start, end, sum};  // make an array with the 3 return values
        return resultVector;
    }
}
