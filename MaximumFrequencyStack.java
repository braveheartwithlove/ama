Maximum Frequency Stack

Design a stack-like data structure to push elements to the stack and pop the most frequent element from the stack.

Implement the FreqStack class:

FreqStack() constructs an empty frequency stack.
void push(int val) pushes an integer val onto the top of the stack.
int pop() removes and returns the most frequent element in the stack.
If there is a tie for the most frequent element, the element closest to the stack's top is removed and returned.
 

Example 1:

Input
["FreqStack", "push", "push", "push", "push", "push", "push", "pop", "pop", "pop", "pop"]
[[], [5], [7], [5], [7], [4], [5], [], [], [], []]
Output
[null, null, null, null, null, null, null, 5, 7, 5, 4]

Explanation
FreqStack freqStack = new FreqStack();
freqStack.push(5); // The stack is [5]
freqStack.push(7); // The stack is [5,7]
freqStack.push(5); // The stack is [5,7,5]
freqStack.push(7); // The stack is [5,7,5,7]
freqStack.push(4); // The stack is [5,7,5,7,4]
freqStack.push(5); // The stack is [5,7,5,7,4,5]
freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,5,7,4].
freqStack.pop();   // return 7, as 5 and 7 is the most frequent, but 7 is closest to the top. The stack becomes [5,7,5,4].
freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,4].
freqStack.pop();   // return 4, as 4, 5 and 7 is the most frequent, but 4 is closest to the top. The stack becomes [5,7].

class FreqStack:
	#counter {1:11, 2:5}
	#stack default (10:[1], 11:[1])
	#maxfreq 11-1=10
	def __init__(self):
		self.c = Counter()
		self.stack = defaultdict(list)
		self.maxfreq =0
		
	def push(self, x: int) -> None:
		self.c[x]+=1 
		self.maxfreq = max(self.maxfreq, self.c[x])
		self.stack[self.c[x]].append(x)
		
	def pop(self) -> int:
		candidate = self.stack[self.maxfreq].pop()
		self.c[candidate] -= 1
		if not self.stack[self.maxfreq]: //not empty
			self.maxfreq -= 1
		return candidate
		


Approach 1: Stack of Stacks

class FreqStack {
    Map<Integer, Integer> freq;
    Map<Integer, Stack<Integer>> group;
    int maxfreq;

    public FreqStack() {
        freq = new HashMap();
        group = new HashMap();
        maxfreq = 0;
    }

    public void push(int x) {
        int f = freq.getOrDefault(x, 0) + 1;
        freq.put(x, f);
        if (f > maxfreq)
            maxfreq = f;

        group.computeIfAbsent(f, z-> new Stack()).push(x);
    }

    public int pop() {
        int x = group.get(maxfreq).pop();
        freq.put(x, freq.get(x) - 1);
        if (group.get(maxfreq).size() == 0)
            maxfreq--;
        return x;
    }
}

[JAVA] | Clean & Concise Code | HashMap + Stack Data Structure | O(1) Time | 100% Faster Solution

class FreqStack {
    
    int maxFrequency;
    List<Stack<Integer>> list;
    HashMap<Integer, Integer> frequency;

    public FreqStack() {
        
        maxFrequency = 0;
        list = new ArrayList <>();
        frequency = new HashMap <>();
    }
    
    public void push(int x) {
        
        int freq = frequency.getOrDefault (x, 0) + 1;
        frequency.put (x, freq);
        maxFrequency = Math.max (maxFrequency, freq);
        
        if (freq - 1 == list.size ()) {
            list.add (new Stack <>());
        }
        
        list.get (freq - 1).push (x);
    }
    
    public int pop() {
        
        int x = list.get (maxFrequency - 1).pop ();
        frequency.put (x, frequency.get (x) - 1);
        maxFrequency = list.get (maxFrequency - 1).size () == 0 ? --maxFrequency : maxFrequency;
        return x;
    }
}


class FreqStack(object):

    def __init__(self):
        self.freq = collections.Counter()
        self.group = collections.defaultdict(list)
        self.maxfreq = 0

    def push(self, x):
        f = self.freq[x] + 1
        self.freq[x] = f
        if f > self.maxfreq:
            self.maxfreq = f
        self.group[f].append(x)

    def pop(self):
        x = self.group[self.maxfreq].pop()
        self.freq[x] -= 1
        if not self.group[self.maxfreq]:
            self.maxfreq -= 1

        return x
		
Time Complexity: O(1)O(1) for both push and pop operations.

Space Complexity: O(N)O(N), where N is the number of elements in the FreqStack.