1629. Slowest Key
A newly designed keypad was tested, where a tester pressed a sequence of n keys, one at a time.

You are given a string keysPressed of length n, where keysPressed[i] was the ith key pressed in the testing sequence, and a sorted list releaseTimes, where releaseTimes[i] was the time the ith key was released. Both arrays are 0-indexed. The 0th key was pressed at the time 0, and every subsequent key was pressed at the exact time the previous key was released.

The tester wants to know the key of the keypress that had the longest duration. The ith keypress had a duration of releaseTimes[i] - releaseTimes[i - 1], and the 0th keypress had a duration of releaseTimes[0].

Note that the same key could have been pressed multiple times during the test, and these multiple presses of the same key may not have had the same duration.

Return the key of the keypress that had the longest duration. If there are multiple such keypresses, return the lexicographically largest key of the keypresses.

 
Example 1:

Input: releaseTimes = [9,29,49,50], keysPressed = "cbcd"
Output: "c"
Explanation: The keypresses were as follows:
Keypress for 'c' had a duration of 9 (pressed at time 0 and released at time 9).
Keypress for 'b' had a duration of 29 - 9 = 20 (pressed at time 9 right after the release of the previous character and released at time 29).
Keypress for 'c' had a duration of 49 - 29 = 20 (pressed at time 29 right after the release of the previous character and released at time 49).
Keypress for 'd' had a duration of 50 - 49 = 1 (pressed at time 49 right after the release of the previous character and released at time 50).
The longest of these was the keypress for 'b' and the second keypress for 'c', both with duration 20.
'c' is lexicographically larger than 'b', so the answer is 'c'.

Initialize the maxDifference and bestChar with the first character.
Start iterating from 2nd character to nth character.
Find the difference between every adjacent character (i and i-1th character)
If the current difference is greater than maxDifference calculated so far. Or the difference is same but the current character is greater than bestChar, update the difference and bestChar.
Return the bestChar.
class Solution {
    public char slowestKey(int[] releaseTimes, String keysPressed) {
        int n = releaseTimes.length;
        int maxDifference = releaseTimes[0];
        char bestChar = keysPressed.charAt(0);
        for (int i = 1; i < n; i++) {
            int difference = releaseTimes[i] - releaseTimes[i-1];

            if (difference > maxDifference || 
                (difference == maxDifference && keysPressed.charAt(i) > bestChar)) {
                maxDifference = difference;
                bestChar = keysPressed.charAt(i);
            }
        }
        return bestChar;
    }
}

Python single-pass, easy-to-understand solution

    def slowestKey(self, r, k):
        r = [0] + r
        n = len(k)
        idx = max(range(n), key = lambda i : (r[i+1] - r[i], k[i]))
        return k[idx]

class Solution:
    def slowestKey(self, releaseTimes: List[int], keysPressed: str) -> str:
        durations = [releaseTimes[0]]
        n = len(releaseTimes)
        maxDuration = releaseTimes[0]
        maxIndex = 0
        for i in range(1,n):
            curr = releaseTimes[i] - releaseTimes[i-1]
            durations.append(curr)
            if curr > maxDuration:
                maxDuration = curr
                maxIndex = i
            elif curr == maxDuration and keysPressed[i] > keysPressed[maxIndex]:
                maxIndex = i
        return keysPressed[maxIndex]
		
class Solution {
public:
    char slowestKey(vector<int>& releaseTimes, string keysPressed) {
        //not that hard, but ...
        int maxTime = releaseTimes[0];
        char key = keysPressed[0];
        
        for (int i = 1; i < releaseTimes.size(); i++) {
            int duration = releaseTimes[i] - releaseTimes[i - 1];
            char curKey = keysPressed[i];
            if (duration > maxTime || (duration == maxTime && curKey > key)) {
                maxTime = duration;
                key = curKey;
            }
        }
        
        return key;
    }
};


Python Solution with Time: O(n), Space: O(1)

class Solution:
    def slowestKey(self, releaseTimes: List[int], keysPressed: str) -> str:
        maximum, ans = releaseTimes[0], keysPressed[0]
        for i in range(1, len(releaseTimes)):
            diff = releaseTimes[i] - releaseTimes[i-1]
            word = keysPressed[i]
            if diff > maximum:
                maximum, ans = diff, word
            elif diff == maximum and word > ans:
                ans = word
        return ans