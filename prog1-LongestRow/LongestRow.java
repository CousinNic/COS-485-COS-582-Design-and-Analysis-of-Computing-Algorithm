package student;

import tester.Matrix;

/* 
 * This class is meant to contain your algorithm.
 * You should implement the static method longestRow()
 * The input is:
 *   the size n of the matrix
 *   and the matrix which is n x n and consists of rows each with just 1's or 0's.
 *   All 1's come before any 0's in each row
 *   
 * Your task is to find the length of the row that contains the most 1's.
 * The efficiency goal is to look at only only O(N) values.
 * 
 * To get the value in any cell use:
 *   matrix.get(row,col);
 *     
 * Return the length of the longest row.
 */
public class LongestRow {
    public static int longestRow(int n, Matrix matrix) {
    	// starting code that just looks at 1 location and guesses.
    	int row = 0;
    	int col = 0;
    	
    	//scans current colum  
    	for(col = 0; col < n && row < n;) {
    		//if match increment col
    		if(matrix.get(row,col) == 1) {
        			col++;
        	//else increment row
    		}else {
    			row++;
    		}
    	}	    		
    	return col; // guess
    	
    }
}
