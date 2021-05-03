295. Find Median from Data Stream
Hard
The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value and the median is the mean of the two middle values.

For example, for arr = [2,3,4], the median is 3.
For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
Implement the MedianFinder class:

MedianFinder() initializes the MedianFinder object.
void addNum(int num) adds the integer num from the data stream to the data structure.
double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.
 

Example 1:

Input
["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
[[], [1], [2], [], [3], []]
Output
[null, null, null, 1.5, null, 2.0]

Explanation
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
medianFinder.addNum(3);    // arr[1, 2, 3]
medianFinder.findMedian(); // return 2.0
 

Constraints:

-105 <= num <= 105
There will be at least one element in the data structure before calling findMedian.
At most 5 * 104 calls will be made to addNum and findMedian.
 

Follow up:

If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?


class MedianFinder {

    PriorityQueue<Integer> maxQ;
    PriorityQueue<Integer> minQ;

    public MedianFinder() {
        maxQ=new PriorityQueue<>((a,b) -> b-a);
        minQ=new PriorityQueue<>();
    }

//O(logN)
    public void addNum(int num) {
        maxQ.offer(num);
        minQ.offer(maxQ.poll());
        if(maxQ.size()<minQ.size()) maxQ.offer(minQ.poll());
    }
//O(1)
    public double findMedian() {
        if(maxQ.size()==0) return 0;
        return maxQ.size()>minQ.size()?maxQ.peek():(double)(maxQ.peek()+minQ.peek())/2;

    }
}



Min and Max Heap- 10 lines code

class MedianFinder {
 private Queue<Integer> maxHeap = new PriorityQueue<Integer>((a,b) -> a.compareTo(b));
 private Queue<Integer> minHeap = new PriorityQueue<Integer>((a,b) -> b.compareTo(a)); 
                                                           
    public void addNum(int num) {
        minHeap.add(num);
        maxHeap.add(minHeap.poll());
        if (minHeap.size() < maxHeap.size())
            minHeap.add(maxHeap.poll());
    }

    public double findMedian() {
        return minHeap.size() > maxHeap.size()
                ? minHeap.peek()
                : (minHeap.peek()/2.0 + maxHeap.peek()/2.0);
    } 
}

Python using min-max heap, faster than 98.6%
import heapq
class MedianFinder:

    def __init__(self):
        """
        initialize your data structure here.
        """
        # max heap
        self.left = []
        # min heap
        self.right = []
        self.median = None
      
    def addNum(self, num: int) -> None:
		# when number of elements are odd
        if self.median != None:
            l, r = min(self.median, num), max(self.median, num)
            heapq.heappush(self.left, -l)
            heapq.heappush(self.right, r)
            self.median = None
		# when number of elements are even
        else:
			# corner case first element
            if self.left:
                l = -self.left[0]
                r = self.right[0]
                if  r >= num >= (l + r) /2 or l <= num <= (l+r)/2:
                    self.median = num
                elif num > r:
                    temp = heapq.heappop(self.right)
                    self.median = temp
                    heapq.heappush(self.right, num)
                else:
                    temp = -heapq.heappop(self.left)
                    heapq.heappush(self.left, -num)
                    self.median = temp
            else:
                self.median = num

    def findMedian(self) -> float:
        if self.median != None:
            return self.median
        else:
            l, r = -self.left[0], self.right[0]
            return (l+r)/2
			
			
Approach 1: Simple Sorting
Intuition

Do what the question says.

Algorithm

Store the numbers in a resize-able container. Every time you need to output the median, 
sort the container and output the median.

class MedianFinder {
    vector<int> store;

public:
    // Adds a number into the data structure.
    void addNum(int num)
    {
        store.push_back(num);
    }

    // Returns the median of current data stream
    double findMedian()
    {
        sort(store.begin(), store.end());

        int n = store.size();
        return (n & 1 ? store[n / 2] : ((double) store[n / 2 - 1] + store[n / 2]) * 0.5);
    }
};
Time complexity: O(n\log n) + O(1) \simeq O(n\log n)O(nlogn)+O(1)≃O(nlogn).

Adding a number takes amortized O(1)O(1) time for a container with an efficient resizing scheme.
Finding the median is primarily dependent on the sorting that takes place. This takes O(n\log n)O(nlogn) time for a standard comparative sort.
Space complexity: O(n)O(n) linear space to hold input in a container. No extra space other than that needed (since sorting can usually be done in-place).

Approach 2: Insertion Sort
Intuition

Keeping our input container always sorted (i.e. maintaining the sorted nature of the container as an invariant).

Algorithm

Which algorithm allows a number to be added to a sorted list of numbers and yet keeps the entire list sorted? Well, for one, insertion sort!

We assume that the current list is already sorted. When a new number comes, we have to add it to the list while maintaining the sorted nature of the list. This is achieved easily by finding the correct place to insert the incoming number, using a binary search (remember, the list is always sorted). Once the position is found, we need to shift all higher elements by one space to make room for the incoming number.

This method would work well when the amount of insertion queries is lesser or about the same as the amount of median finding queries.

class MedianFinder {
    vector<int> store; // resize-able container

public:
    // Adds a number into the data structure.
    void addNum(int num)
    {
        if (store.empty())
            store.push_back(num);
        else
            store.insert(lower_bound(store.begin(), store.end(), num), num);     // binary search and insertion combined
    }

    // Returns the median of current data stream
    double findMedian()
    {
        int n = store.size();
        return n & 1 ? store[n / 2] : ((double) store[n / 2 - 1] + store[n / 2]) * 0.5;
    }
};
Time complexity: O(n) + O(\log n) \approx O(n)O(n)+O(logn)≈O(n).

Binary Search takes O(\log n)O(logn) time to find correct insertion position.
Insertion can take up to O(n)O(n) time since elements have to be shifted inside the container to make room for the new element.
Space complexity: O(n)O(n) linear space to hold input in a container.

