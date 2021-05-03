1010. Pairs of Songs With Total Durations Divisible by 60
Medium

You are given a list of songs where the ith song has a duration of time[i] seconds.

Return the number of pairs of songs for which their total duration in seconds is divisible by 60. Formally, we want the number of indices i, j such that i < j with (time[i] + time[j]) % 60 == 0.

 

Example 1:

Input: time = [30,20,150,100,40]
Output: 3
Explanation: Three pairs have a total duration divisible by 60:
(time[0] = 30, time[2] = 150): total duration 180
(time[1] = 20, time[3] = 100): total duration 120
(time[1] = 20, time[4] = 40): total duration 60
Example 2:

Input: time = [60,60,60]
Output: 3
Explanation: All three pairs have a total duration of 120, which is divisible by 60.

Approach 2: Use an Array to Store Frequencies
We would iterate through the input array time and for each element a, we want to know the number of elements b such that:


1. b%60 = 0, if a%60 = 0
2. b%60 = 60-a%60, if a%60 != 0

We can use Approach 1 to implement this logic by repeatedly examining the rest of time again and again for each element a.
 However, we are able to improve the time complexity by consuming more space - we can store the frequencies of the remainder 
 a % 60, so that we can find the number of the complements in \mathcal{O}(1)O(1) time.
We would initiate an array remainders with size 60 to record the frequencies of each remainder - as the range of remainders is [0,59][0,59]. Then we can loop through the array once and for each element a we would:

1. if a % 60=0, add remainders[0] to the result; 
else, add remainders[60 - t % 60] to the result;
2. update remainders[a % 60].

[60, 30, 150, 100, 40, 80, 120]
remainders
{ 0:[60], 30:[30] }
{ 0:[60], 30:[30, 150] }
{ 0:[60], 30:[30, 150], 40:[100,40],20:[80]  }
{ 0:[60,120], 30:[30, 150], 40:[100,40],20:[80]  }

qualified [30, 150] [80, 100] [80, 40] [60,120]
O(n), O(1)
class Solution {
    public int numPairsDivisibleBy60(int[] time) {
        int remainders[] = new int[60];
        int count = 0;
        for (int t: time) {
            if (t % 60 == 0) { // check if a%60==0 && b%60==0
                count += remainders[0];
            } else { // check if a%60+b%60==60
                count += remainders[60 - t % 60];
            }
            remainders[t % 60]++; // remember to update the remainders
        }
        return count;
    }
}

class Solution:
    def numPairsDivisibleBy60(self, time: List[int]) -> int:
        remainders = collections.defaultdict(int)
        ret = 0
        for t in time:
            if t % 60 == 0: # check if a%60==0 && b%60==0
                ret += remainders[0]
            else: # check if a%60+b%60==60
                ret += remainders[60-t%60]
            remainders[t % 60] += 1 # remember to update the remainders
        return ret		
		
		
Approach 1: Brute Force
O(N2) O(1)
class Solution {
    public int numPairsDivisibleBy60(int[] time) {
        int count = 0, n = time.length;
        for (int i = 0; i < n; i++) {
            // j starts with i+1 so that i is always to the left of j
            // to avoid repetitive counting
            for (int j = i + 1; j < n; j++) {
                if ((time[i] + time[j]) % 60 == 0) {
                    count++;
                }
            }
        }
        return count;
    }
}

class Solution:
    def numPairsDivisibleBy60(self, time: List[int]) -> int:
        ret, n = 0, len(time)
        for i in range(n):
            # j starts with i+1 so that i is always to the left of j
            # to avoid repetitive counting
            for j in range(i + 1, n):
                ret += (time[i] + time[j]) % 60==0
        return ret
		
		
		
