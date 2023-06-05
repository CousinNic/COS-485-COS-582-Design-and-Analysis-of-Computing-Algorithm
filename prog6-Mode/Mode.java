package student;
import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

/*
  Find the MODE of an unordered array of integers in AVERAGE CASE linear time.
*/
public class Mode {
    public static int mode(int[] scores) {

    	HashMap<Integer, Integer> intCount = new HashMap<Integer, Integer>();
    	int modeCount = 0;
    	int currentMode = 0;
    	
    	//iterates through scores array
    	for(int i: scores) {
    		//checks if hashmap contains i, if so the cound is incremented += 1
    		if (intCount.get(i) != null) {
    			intCount.put(i, intCount.get(i) + 1);
    		//else i is added to the hashmap
    		}else {
    			intCount.put(i, 1);
    		}
    		//compare count to the current mode, if the hashmap i has more occurences, the saved mode is updated
    		if(intCount.get(i) > modeCount) {
    			modeCount = intCount.get(i);
    			currentMode = i;
    		}
    	}
        return currentMode;
    }
}
