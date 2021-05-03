994. Rotting Oranges
Medium

You are given an m x n grid where each cell can have one of three values:

0 representing an empty cell,
1 representing a fresh orange, or
2 representing a rotten orange.
Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.

 

Example 1:


Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
Example 2:

Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
Example 3:

Input: grid = [[0,2]]
Output: 0
Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 10
grid[i][j] is 0, 1, or 2.

https://www.youtube.com/watch?v=TzoDDOj60zE  Kevin
class Solution {
    public int orangesRotting(int[][] grid) {
		//Define two Hashset for the Fresh and Rotten oranges
		Set<String> fresh = new HashSet<>();
		Set<String> rotten = new HashSet<>();
		for(int i =0; i < grid.length; i++){
			for (int j = 0; j< grid[i].length; j++){
				if(grid[i][j]==1){
					fresh.add("" + i + j);
				}
				if(grid[i][j]==2){
					rotten.add("" + i + j);
				}
			}
		}
		//BFS
		//Go through all the rotten ones, and generate the infected.
		int minutes =0;
		int[][] directions ={{0,1},{0,-1},{1,0},{-1,0} };
		while (fresh.size()>0){
			//Define a new Hashset for infected oran
			Set<String> infected = new HashSet<>();
			for (String s: rotten){
				int i = s.charAt(0) -'0';
				int j = s.charAt(1) -'0';
				
				for(int[] direction: directions){
					int nextI= i+direction[0];
					int nextJ= j+direction[1];
					
					if(fresh.contains("" + nextI + nextJ)){
						fresh.remove("" + nextI + nextJ);
						infected.add("" + nextI + nextJ)
					}
				}
				
			}
			
			if(infected.size==0){
				return -1;
			}
			rotten = infected;
			minutes++;
		}
		return minutes;
		
		
	}
}	
Approach 1: Breadth-First Search (BFS)
O(N) O(N)
class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<Pair<Integer, Integer>> queue = new ArrayDeque();

        // Step 1). build the initial set of rotten oranges
        int freshOranges = 0;
        int ROWS = grid.length, COLS = grid[0].length;

        for (int r = 0; r < ROWS; ++r)
            for (int c = 0; c < COLS; ++c)
                if (grid[r][c] == 2)
                    queue.offer(new Pair(r, c));
                else if (grid[r][c] == 1)
                    freshOranges++;

        // Mark the round / level, _i.e_ the ticker of timestamp
        queue.offer(new Pair(-1, -1));

        // Step 2). start the rotting process via BFS
        int minutesElapsed = -1;
        int[][] directions = { {-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> p = queue.poll();
            int row = p.getKey();
            int col = p.getValue();
            if (row == -1) {
                // We finish one round of processing
                minutesElapsed++;
                // to avoid the endless loop
                if (!queue.isEmpty())
                    queue.offer(new Pair(-1, -1));
            } else {
                // this is a rotten orange
                // then it would contaminate its neighbors
                for (int[] d : directions) {
                    int neighborRow = row + d[0];
                    int neighborCol = col + d[1];
                    if (neighborRow >= 0 && neighborRow < ROWS && 
                        neighborCol >= 0 && neighborCol < COLS) {
                        if (grid[neighborRow][neighborCol] == 1) {
                            // this orange would be contaminated
                            grid[neighborRow][neighborCol] = 2;
                            freshOranges--;
                            // this orange would then contaminate other oranges
                            queue.offer(new Pair(neighborRow, neighborCol));
                        }
                    }
                }
            }
        }

        // return elapsed minutes if no fresh orange left
        return freshOranges == 0 ? minutesElapsed : -1;
    }
}

from collections import deque
class Solution:
    def orangesRotting(self, grid: List[List[int]]) -> int:
        queue = deque()

        # Step 1). build the initial set of rotten oranges
        fresh_oranges = 0
        ROWS, COLS = len(grid), len(grid[0])
        for r in range(ROWS):
            for c in range(COLS):
                if grid[r][c] == 2:
                    queue.append((r, c))
                elif grid[r][c] == 1:
                    fresh_oranges += 1

        # Mark the round / level, _i.e_ the ticker of timestamp
        queue.append((-1, -1))

        # Step 2). start the rotting process via BFS
        minutes_elapsed = -1
        directions = [(-1, 0), (0, 1), (1, 0), (0, -1)]
        while queue:
            row, col = queue.popleft()
            if row == -1:
                # We finish one round of processing
                minutes_elapsed += 1
                if queue:  # to avoid the endless loop
                    queue.append((-1, -1))
            else:
                # this is a rotten orange
                # then it would contaminate its neighbors
                for d in directions:
                    neighbor_row, neighbor_col = row + d[0], col + d[1]
                    if ROWS > neighbor_row >= 0 and COLS > neighbor_col >= 0:
                        if grid[neighbor_row][neighbor_col] == 1:
                            # this orange would be contaminated
                            grid[neighbor_row][neighbor_col] = 2
                            fresh_oranges -= 1
                            # this orange would then contaminate other oranges
                            queue.append((neighbor_row, neighbor_col))

        # return elapsed minutes if no fresh orange left
        return minutes_elapsed if fresh_oranges == 0 else -1
		
		
		
		
The idea is to find all the rotten arranges first and then start the rotting process by stepping through every minute and rotting all oranges that are fresh and adjacent to rotting orange. We achieve this by stepping through BFS.

In the end, after we have rotten all the fresh oranges that lie adjacent to rotten ones, we check if there are still fresh oranges left. We can achieve this by counting the number the fresh oranges in the first step of the solution and then decrementing the freshCount everytime we rot an orange.

Code below:

class Solution {
    public class Point{
        int x;
        int y;
        
        public Point(int a, int b) {
            x = a;
            y = b;
        }
    }
    
    public int orangesRotting(int[][] grid) {
        int freshCount = 0;
        int M = grid.length;
        int N = grid[0].length;
        Queue<Point> rotten = new LinkedList<>();
        
        for (int i=0; i<M; i++) {
            for (int j=0; j<N; j++) {
                if (grid[i][j] == 1) freshCount++;
                if (grid[i][j] == 2) {
                    rotten.offer(new Point(i, j));
                }
            }
        }

        int time=0;
        while (!rotten.isEmpty() && freshCount > 0) {
            int size = rotten.size();
            time++;
            while (size-- > 0) {
                Point r = rotten.poll();
                if (r.x > 0 && grid[r.x-1][r.y] == 1) {
                    grid[r.x-1][r.y] = 2;
                    freshCount--;
                    rotten.offer(new Point(r.x-1, r.y));
                }
                if (r.y > 0 && grid[r.x][r.y-1] == 1) {
                    grid[r.x][r.y-1] = 2;
                    freshCount--;
                    rotten.offer(new Point(r.x, r.y-1));
                }
                if (r.x < M-1 && grid[r.x+1][r.y] == 1) {
                    grid[r.x+1][r.y] = 2;
                    freshCount--;
                    rotten.offer(new Point(r.x+1, r.y));
                }
                if (r.y < N-1 && grid[r.x][r.y+1] == 1) {
                    grid[r.x][r.y+1] = 2;
                    freshCount--;
                    rotten.offer(new Point(r.x, r.y+1));
                }
            }
        }
        
        return freshCount==0? time : -1;
    }
}