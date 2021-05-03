1041. Robot Bounded In Circle
Medium

On an infinite plane, a robot initially stands at (0, 0) and faces north. The robot can receive one of three instructions:

"G": go straight 1 unit;
"L": turn 90 degrees to the left;
"R": turn 90 degrees to the right.
The robot performs the instructions given in order, and repeats them forever.

Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.

Example 1:

Input: instructions = "GGLLGG"
Output: true
Explanation: The robot moves from (0,0) to (0,2), turns 180 degrees, and then returns to (0,0).
When repeating these instructions, the robot remains in the circle of radius 2 centered at the origin.
Example 2:

Input: instructions = "GG"
Output: false
Explanation: The robot moves north indefinitely.
Example 3:

Input: instructions = "GL"
Output: true
Explanation: The robot moves from (0, 0) -> (0, 1) -> (-1, 1) -> (-1, 0) -> (0, 0) -> ...

Approach 1: One Pass
class Solution {
    public boolean isRobotBounded(String instructions) {
        // north = 0, east = 1, south = 2, west = 3
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        // Initial position is in the center
        int x = 0, y = 0;
        // facing north
        int idx = 0;
        
        for (char i : instructions.toCharArray()) {
            if (i == 'L')
                idx = (idx + 3) % 4;
            else if (i == 'R')
                idx = (idx + 1) % 4;
            else {
                x += directions[idx][0];
                y += directions[idx][1];   
            }    
        }
        
        // after one cycle:
		
        // robot returns into initial position
        // or robot doesn't face north
        return (x == 0 && y == 0) || (idx != 0);
    }
}


class Solution:
    def isRobotBounded(self, instructions: str) -> bool:
        # north = 0, east = 1, south = 2, west = 3
        directions = [[0, 1], [1, 0], [0, -1], [-1, 0]]
        # Initial position is in the center
        x = y = 0
        # facing north
        idx = 0
        
        for i in instructions:
            if i == "L":
                idx = (idx + 3) % 4
            elif i == "R":
                idx = (idx + 1) % 4
            else:
                x += directions[idx][0]
                y += directions[idx][1]
        
        # after one cycle:
        # robot returns into initial position
        # or robot doesn't face north
        return (x == 0 and y == 0) or idx != 0
		
		
Complexity Analysis

Time complexity: \mathcal{O}(N)O(N), where NN is a number of instructions to parse.

Space complexity: \mathcal{O}(1)O(1) because the array directions contains only 4 elements.



Approach2: Map
Steps:

Create a map of instructions and directions
Start from origin (0, 0)
Read instructions one by one and update the current position
Repeat the whole process 4 times to see if we are back to the origin
class Solution:
    dir_map = {
        ('L', 'N'): 'W',
        ('L', 'W'): 'S',
        ('L', 'S'): 'E',
        ('L', 'E'): 'N',
        ('R', 'N'): 'E',
        ('R', 'W'): 'N',
        ('R', 'S'): 'W',
        ('R', 'E'): 'S',
    }
        
    def isRobotBounded(self, instructions: str) -> bool:
        curr = (0, 0)
        direction = 'N'
        n = 4
        i = 0
        while n > 0:
            direction, curr = self.move(instructions, direction, curr, i)
            n -= 1
            i = 0
        return curr == (0, 0)
    
    def move(self, instructions, direction, curr, i):
        while i < len(instructions):
            inst = instructions[i]
            if inst == 'G':
                if direction == 'N':
                    x, y = curr
                    curr = (x, y + 1)
                elif direction == 'S':
                    x, y = curr
                    curr = (x, y - 1)
                elif direction == 'E':
                    x, y = curr
                    curr = (x + 1, y)
                elif direction == 'W':
                    x, y = curr
                    curr = (x - 1, y)
            else:
                direction = self.dir_map[(inst, direction)]
            i += 1
        return (direction, curr)
		
		

Approach3: Dict
Python Using a Dictionary, Easy to understand, beats 94%, O(n) time.

from collections import defaultdict  #I perfer default dict

class Solution:
    def isRobotBounded(self, instructions: str) -> bool:
        move={('N', 'L') : 'W', #Created a lookup direction table
              ('N', 'R') : 'E', #N=North, S=South, E=East, W=West
             ('S', 'L') : 'E',
             ('S', 'R') : 'W',
             ('W', 'L') : 'S',
             ('W', 'R') : 'N',
             ('E', 'L') : 'N',
             ('E', 'R') : 'S'}
        
        instructions=instructions*4 #deals with cases like  "GL" or "GR"
        d = defaultdict(int)
        direction='N'
        
        for char in instructions:
            if char=='G':
                d[direction]+=1
            else:
                direction = move[(direction, char)]
                
         #In the end all north and south directional moves should equal
		 #As well as all East and West Directional Moves
        return True if d['S']==d['N'] and d['W']==d['E'] else False 