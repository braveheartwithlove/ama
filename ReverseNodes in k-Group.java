Reverse Nodes in k-Group

Solution
Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.

Follow up:

Could you solve the problem in O(1) extra memory space?
You may not alter the values in the list's nodes, only nodes itself may be changed.
 

Example 1:


Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]
Example 2:


Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]
Example 3:

Input: head = [1,2,3,4,5], k = 1
Output: [1,2,3,4,5]
Example 4:

Input: head = [1], k = 1
Output: [1]
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
   head
   tail             cur
    1   2   3   4    5   6
 
 0->root (prev)
 
                    tail       cur
					 5    6    null

             tail
root 0 4 3 2  1 
             prev
			 
			     prev. next = tail

O(NK)
O(1)
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode root = new ListNode(0,head);
		ListNode cur = head;
		ListNode prev = root;
		
		while (cur != null){
			ListNode tail = cur;
			int listindex = 0;
			
			while(cur != null && listIndex <k){
				cur = cur.next;
				listIndex++;
			}
			
			if(listIndex != k){
				prev.next = tail;
			} else {
				prev.next = reverse(tail, k);
				prev = tail;
			}
		}
		return root.next;
		
    }
	
	private ListNode reverse(ListNode head, int k){
		ListNode prev = null;
		ListNode current = head;
		ListNode next = null;
		
		while(current != null && k-->0){
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		head = prev;
		return head;
	}

}


Recursive

class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode curKTail = restCheck(head,k); 
        if (curKTail == null) {
            return head;
        }//no enough return
        if (curKTail.next != null) {//case: recursive dive in 
            ListNode rest = reverseKGroup(curKTail.next, k);
            reverseN(head,k);
            head.next = rest;
        } else {//case: no more nodes, just reverse current k
            reverseN(head,k);
        }
        return curKTail;
        
    }
    ListNode successor = null; // you may use it in problem NO.92 xD
    ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            successor = head.next;
            return head;
        }
        ListNode last = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = successor;
        return last;
    }
    ListNode restCheck(ListNode head, int k) {//recursive check, return the last in the group
        if (head.next == null) {
            return null;
        }
        if (k == 2) {
            return head.next;
        }
        return restCheck(head.next, k - 1);
    }
}


