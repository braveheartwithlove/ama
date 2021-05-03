1152. Analyze User Website Visit Pattern
Medium

We are given some website visits: the user with name username[i] visited the website website[i] at time timestamp[i].

A 3-sequence is a list of websites of length 3 sorted in ascending order by the time of their visits.  (The websites in a 3-sequence are not necessarily distinct.)

Find the 3-sequence visited by the largest number of users. If there is more than one solution, return the lexicographically smallest such 3-sequence.

 

Example 1:

Input: username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"], timestamp = [1,2,3,4,5,6,7,8,9,10], website = ["home","about","career","home","cart","maps","home","home","about","career"]
Output: ["home","about","career"]
Explanation: 
The tuples in this example are:
["joe", 1, "home"]
["joe", 2, "about"]
["joe", 3, "career"]
["james", 4, "home"]
["james", 5, "cart"]
["james", 6, "maps"]
["james", 7, "home"]
["mary", 8, "home"]
["mary", 9, "about"]
["mary", 10, "career"]
The 3-sequence ("home", "about", "career") was visited at least once by 2 users.
The 3-sequence ("home", "cart", "maps") was visited at least once by 1 user.
The 3-sequence ("home", "cart", "home") was visited at least once by 1 user.
The 3-sequence ("home", "maps", "home") was visited at least once by 1 user.
The 3-sequence ("cart", "maps", "home") was visited at least once by 1 user.

Try to get the website sequence visted by most different users. sequence here means chronological order.

First sort the data by timestamp.

And put user and corresponding websites into HashMap<String, List<String>>.

For each user, find all its combination of 3 websites.

And each sequence within this combination, check its count of different user. If count is higher or lexicographically smaller, update the max sequence.

The max sequence is the result.

Time Complexity: O(n ^ 3). n = username.length. sort takes O(nlogn). getCom takes O(n ^ 3).

Space: O(n ^ 3).

class Solution {
 2     public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
 3         int n = username.length;
 4         List<Pair> datas = new ArrayList<>();
 5         for(int i = 0; i < n; i++){
 6             datas.add(new Pair(username[i], timestamp[i], website[i]));
 7         }
 8         
 9         Collections.sort(datas, (a, b) -> a.time - b.time);
10         HashMap<String, List<String>> userToWebs = new HashMap<>();
11         for(Pair data : datas){
12             userToWebs.putIfAbsent(data.user, new ArrayList<String>());
13             userToWebs.get(data.user).add(data.web);
14         }
15         
16         HashMap<String, Integer> seqToCount = new HashMap<>();
17         
18         int maxCount = 0;
19         String maxSeq = "";
20         for(Map.Entry<String, List<String>> entry : userToWebs.entrySet()){
21             Set<String> seqCom = getCom(entry.getValue());
22             for(String seq : seqCom){
23                 seqToCount.put(seq, seqToCount.getOrDefault(seq, 0) + 1);
24                 if(seqToCount.get(seq) > maxCount){
25                     maxCount = seqToCount.get(seq);
26                     maxSeq = seq;
27                 }else if(seqToCount.get(seq) == maxCount && seq.compareTo(maxSeq) < 0){
28                     maxSeq = seq;
29                 }    
30             }
31         }
32         
33         List<String> res = new ArrayList<>();
34         String [] webs = maxSeq.split(",");
35         for(String w : webs){
36             res.add(w);
37         }
38         
39         return res;
40     }
41     
42     private HashSet<String> getCom(List<String> webs){
43         HashSet<String> res = new HashSet<>();
44         int n = webs.size();
45         for(int i = 0; i < n - 2; i++){
46             for(int j = i + 1; j < n - 1; j++){
47                 for(int k = j + 1; k < n; k++){
48                     res.add(webs.get(i) + "," + webs.get(j) + "," + webs.get(k));
49                 }
50             }
51         }
52         
53         return res;
54     }
55 }
56 
57 class Pair{
58     String user;
59     int time;
60     String web;
61     public Pair(String user, int time, String web){
62         this.user = user;
63         this.time = time;
64         this.web = web;
65     }
66 }

The solution is hard to understand, just want to clarify it a little bit.
This question is to analysis the user visit pattern, their visit sequence, we want to get a sequence that most user visited, like we usually visited google.com then leetcode.com then stackoverflow.com, this is our pattern. Then let's look at the question .
There are three arrays, username, timestamp, website, let's look at the example:

username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"], 
timestamp = [1,2,3,4,5,6,7,8,9,10], 
website = ["home","about","career","home","cart","maps","home","home","about","career"]
we can get there is users' visit history:

"joe" -> ["home","about","career"]
"james" -> ["home","cart","maps","home"]
"mary" -> ["home","about","career"]
Base on requirements, their 3-sequence patterns are:

"joe" -> ["home","about","career"]
"james" -> ["home","cart","maps"], ["home","cart","home"], ["home","maps","home"],["cart","maps","home"]
"mary" -> ["home","about","career"]
We want to get the pattern match with most users' pattern

["home","about","career"] -> ["joe", "mary"]
["home","cart","maps"] -> ["james"]
["home","cart","home"] -> ["james"]
["home","maps","home"] -> ["james"]
["cart","maps","home"] -> ["james"]
In the begin I don't know why we need the timestamp array, I thought the website array are in order, but it's not, so we need to utilize timestamp to sort.
Here is my code, not enhanced yet.



    public static class Solution1 {
        public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
            Map<String, TreeMap<Integer, String>> userToSiteMap = new HashMap<>();
            for (int i = 0; i < username.length; i++) {
                if (!userToSiteMap.containsKey(username[i])) {
                    userToSiteMap.put(username[i], new TreeMap<>());
                }
                TreeMap<Integer, String> timeToSiteMap = userToSiteMap.get(username[i]);
                timeToSiteMap.put(timestamp[i], website[i]);
                userToSiteMap.put(username[i], timeToSiteMap);
            }
            Map<String, Integer> sequenceCountMap = new HashMap<>();
            for (String user : userToSiteMap.keySet()) {
                TreeMap<Integer, String> timeToSiteMap = userToSiteMap.get(user);
                if (timeToSiteMap.size() < 3) {
                    continue;
                } else {
                    List<Integer> times = new ArrayList<>();
                    for (int time : timeToSiteMap.keySet()) {
                        times.add(time);
                    }
                    List<String> allSequences = formAllSequences(times, timeToSiteMap);
                    Set<String> encounteredSequence = new HashSet<>();
                    for (String sequence : allSequences) {
                        if (encounteredSequence.add(sequence)) {
                            sequenceCountMap.put(sequence, sequenceCountMap.getOrDefault(sequence, 0) + 1);
                        }
                    }
                }
            }
            List<String> mostVisitedPattern = new ArrayList<>();
            int count = 0;
            String result = null;
            for (String sequence : sequenceCountMap.keySet()) {
                if (sequenceCountMap.get(sequence) > count) {
                    result = sequence;
                    count = sequenceCountMap.get(sequence);
                } else if (count == sequenceCountMap.get(sequence)) {
                    if (sequence.compareTo(result) < 0) {
                        result = sequence;
                    }
                }
            }
            for (String site : result.split("->")) {
                mostVisitedPattern.add(site);
            }
            return mostVisitedPattern;
        }

        private List<String> formAllSequences(List<Integer> times, TreeMap<Integer, String> timeToSiteMap) {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < times.size() - 2; i++) {
                for (int j = i + 1; j < times.size() - 1; j++) {
                    for (int k = j + 1; k < times.size(); k++) {
                        result.add(timeToSiteMap.get(times.get(i)) + "->" + timeToSiteMap.get(times.get(j)) + "->" + timeToSiteMap.get(times.get(k)));
                    }
                }
            }
            return result;
        }
    }
	
	
class Solution {
    
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        Map<String, List<Node>> map = new HashMap<>();
        for(int i = 0; i < username.length; i++){
            if(!map.containsKey(username[i])){
                map.put(username[i], new ArrayList<>());
            }
            List<Node> list = map.get(username[i]);
            Node node = new Node(website[i], timestamp[i]);
            list.add(node);
        } 
        int max = 0;
        Map<String, Integer> count = new HashMap<>();
        for(Map.Entry<String, List<Node>> entry : map.entrySet()){
            Set<String> set = new HashSet<>();
            List<Node> nodes = entry.getValue();
            Collections.sort(nodes, (a, b) -> (a.timestamp - b.timestamp));
            dfs(nodes, new ArrayList<>(), 0, set);
            for(String string : set){
                count.put(string, count.getOrDefault(string, 0) + 1);
                max = Math.max(max, count.get(string));
            }
            
        }
        
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> (a.compareTo(b)));
        for(Map.Entry<String, Integer> entry : count.entrySet()){
            if(entry.getValue() == max){
                pq.offer(entry.getKey());
            }
        }
        
        String temp = pq.poll();
        String[] array = temp.split(" ");
        List<String> result = Arrays.asList(array);
        return result;
        
    }
    
    public void dfs(List<Node> nodes, List<String> result, int index, Set<String> set){
        if(result.size() == 3){
            StringBuilder sb = new StringBuilder();
            for(String s : result)
                sb.append(s).append(" ");
            
            set.add(sb.toString().trim());
            return;
        }
        
        for(int i = index; i < nodes.size(); i++){
            result.add(nodes.get(i).website);
            dfs(nodes, result, i + 1, set);
            result.remove(result.size() - 1);
        }
    }
    
    class Node{
        String website;
        int timestamp;
        
        public Node(String w, int t){
            website = w;
            timestamp = t;
        }
    }
}


from collections import defaultdict
from itertools import combinations
import heapq
class Solution:
    def mostVisitedPattern(self, username: List[str], timestamp: List[int], website: List[str]) -> List[str]:
        """
        """
        # step 1) find each of user and website timestamp
        # joe: [(1, home)]..
        user_time_site = dict()
        for user, time, site in zip(username, timestamp, website):
            if user not in user_time_site:
                user_time_site[user] = [(time, site)]
            else:
                user_time_site[user].append((time, site))
                
        # step 2) sorted site by time
        # joe: [(1, home)] => joe : [home, about]
        for user , time_site in user_time_site.items():
            time_site.sort(key = lambda x : x[0])
            user_time_site[user] = [site for time,site in time_site]
        
        user_site = user_time_site
        site_sequence_count = defaultdict(int)
        
        # step 3) find all 3 sequence cobinations of site for all users
        # ("home", "about","career"): 2 
        for user , sites in user_site.items():
            combs = set(combinations(sites,3))
            for comb in combs:
                site_sequence_count[comb] += 1
            
        three_seq_count = []
        # step 4) get the max site seq visit using heap
        for site,seq_count in site_sequence_count.items():
            heapq.heappush(three_seq_count, (-seq_count, site))
        
        return heapq.heappop(three_seq_count)[1]            
        
		
[Python3] hashmap implementation
		
Few pitfulls include:

the timestamps are not sorted,
duplicates of sequences in the same user
return the frequency with lexicographical order
def mostVisitedPattern(self, username: List[str], timestamp: List[int], website: List[str]) -> List[str]:
        
        ts_idx = sorted(range(len(timestamp)), key=lambda k: timestamp[k])
        
        usrSeqMap = dict()
        for i in ts_idx:
            usr, st, web = username[i], timestamp[i], website[i]
            usrSeqMap[usr] = usrSeqMap.get(usr, []) + [web]
        
        
        def combination(webs, i, k, curr):                    
            if len(curr) == k:                
                visited.append(curr[::])
                return 
                        
            for j in range(i, len(webs)):
                curr.append(webs[j])
                combination(webs, j+1, k, curr)
                curr.pop()
            
        combMap = {}        
        for u, seq in usrSeqMap.items():
            if len(seq) > 3:              
                visited = []
                combination(seq, 0, 3, [])
                for seq in visited:                                    
                    combMap[tuple([u]+seq)] = 1
            elif len(seq) == 3:
                combMap[tuple([u]+seq)] = 1
                
        
        seqItemsFreq = {}
        for k, v in combMap.items():
            u, item1, item2, item3 = k
            seqItemsFreq[(item1, item2, item3)] = seqItemsFreq.get((item1, item2, item3), 0) + 1
        
        seq, c = sorted(seqItemsFreq.items(), key=lambda x: (-x[1], x[0]))[0]
        return (list(seq))


class Data implements Comparable<Data>{
    public String username;
    public int timestamp;
    public String website;
    
    public Data(String username, int timestamp, String website){
        this.username=username;
        this.timestamp=timestamp;
        this.website=website;
    }
    
    @Override
    public int compareTo(Data data){
        return this.timestamp-data.timestamp;
    }    
}

Java- Very clean and easy to understand


class Solution {
        
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        
        // Make the list of Data sorted by timestamp        
        PriorityQueue<Data> queue=buildListData(username, timestamp,website);        
        // build map of user and list of website visted
        Map<String, List<String>> userWebs= buildUserWebsMap(queue);                
        // build List of combination 3 websites from list of each user. Each user will have a Set of combination
        List<Set<String>> userCombines= builUserSetCombinationOfThreeWebs(userWebs);        
        // Count number of combination from the list of set
        Map<String, Integer> mapCombines= getMaxOfThreeCombinations(userCombines);        
        // get the max value and the first item of list after sorted
        return getResult(mapCombines);
    }
    
    PriorityQueue<Data> buildListData(String[] username, int[] timestamp, String[] website){
        PriorityQueue<Data> queue=new PriorityQueue<Data>();
        for (int i=0;i<username.length;i++){
            Data data=new Data(username[i], timestamp[i],website[i]);
            queue.add(data);
        }
        return queue;
    }    
    
    Map<String, List<String>> buildUserWebsMap(PriorityQueue<Data> queue){
        Map<String, List<String>> userWebs=new HashMap<>();        
        while(!queue.isEmpty()){
            Data data=queue.poll();
            userWebs.putIfAbsent(data.username,new ArrayList<String>());
            userWebs.get(data.username).add(data.website);
        }              
        return userWebs;
    }
    
    List<Set<String>> builUserSetCombinationOfThreeWebs(Map<String, List<String>> userWebs){
        List<Set<String>> userCombines=new ArrayList<>();   
        for(Map.Entry<String,List<String>> entry: userWebs.entrySet()){
            List<String> webList=entry.getValue();
            Set<String> setWeb=new HashSet<>();
            for(int i=0;i<webList.size()-2;i++){
                for(int j=i+1;j<webList.size()-1;j++){                
                    for(int k=j+1;k<webList.size();k++){
                        setWeb.add(webList.get(i)+" " + webList.get(j)+" " +webList.get(k));
                    }
                }    
            }
            userCombines.add(setWeb);
        }        
        return userCombines;
    }
    
    Map<String, Integer> getMaxOfThreeCombinations(List<Set<String>> userCombines){
        Map<String, Integer> mapCombine=new HashMap<>();        
        for(Set<String> userCombine: userCombines){            
            for(String combine:userCombine){
                mapCombine.put(combine, mapCombine.getOrDefault(combine,0) +1);    
            }            
        }        
        return mapCombine;
    }
    
    
    List<String> getResult(Map<String, Integer> mapCombine){
        int max=Collections.max(mapCombine.values());
        List<String> list=new ArrayList<>();
        for(Map.Entry<String,Integer> entry: mapCombine.entrySet()){
            if(entry.getValue()==max){
                list.add(entry.getKey());
            }
        }        
        Collections.sort(list);        
        String[] res=list.get(0).split(" ");        
        return Arrays.asList(res);        
    }
}
                		