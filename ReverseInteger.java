package net.kenyang.algorithm;

/**
 * Reverse digits of an integer.
 * </br></br>
 * Example1: x = 123, return 321 </br>
 * Example2: x = -123, return -321
 * @author Ken Yang
 *
 */
 
public class ReverseInteger {
    public int reverse(int x){
        long result = 0;
        while (x!=0) {
            int remainder = x % 10;
            result = result*10 + remainder;
			if(result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) return 0; 
            x /=10;
        }
        return (int) result;
    }
}


public class ReverseInteger {
    public int reverse(int x){
        int newValue = 0;
        while (x!=0) {
            int mod = x % 10;
            newValue = newValue*10 + mod;
            x /=10;
        }
        return newValue;
    }
}
