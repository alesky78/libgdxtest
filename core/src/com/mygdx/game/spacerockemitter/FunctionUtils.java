package com.mygdx.game.spacerockemitter;


/**
 * Specific collection of utils that support interesting functions
 * 
 * 
 * @author id837836
 *
 */
public class FunctionUtils {

	
	/**
	 * this function has a value between 0 and 1 and is always continue in this range
	 * the shape of the courve can be consider has a half circle and its values are: 
	 * 0 where x = 0
	 * 1 where x = 0.5
	 * 0 where x = 1
	 * 
	 * TODO we could had the second field pow that change the curve
	 * for value > 1  the curve tend to become a spice in the 0.5  
	 * for value < 1  the curve tend to become a rectangle where the value is 0.5 wherever less that the margins where is always 0
	 * 
	 * @param x in the range of 0 and 1
	 * @return the specific value of the function
	 */
	public static float zeroOneZero(float x) {
		if(x<0 || x>1)
			throw new UnsupportedOperationException("the range of the input parameter must be between 0 and 1 included");
		
		return 4f*x*(1f-x);			
	}
	
}
