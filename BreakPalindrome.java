1328. Break a Palindrome
Medium

Given a palindromic string of lowercase English letters palindrome, replace exactly one character with any lowercase English letter so that the resulting string is not a palindrome and that it is the lexicographically smallest one possible.

Return the resulting string. If there is no way to replace a character to make it not a palindrome, return an empty string.

A string a is lexicographically smaller than a string b (of the same length) if in the first position where a and b differ, a has a character strictly smaller than the corresponding character in b. For example, "abcc" is lexicographically smaller than "abcd" because the first position they differ is at the fourth character, and 'c' is smaller than 'd'.

 

Example 1:

Input: palindrome = "abccba"
Output: "aaccba"
Explanation: There are many ways to make "abccba" not a palindrome, such as "zbccba", "aaccba", and "abacba".
Of all the ways, "aaccba" is the lexicographically smallest.
Example 2:

Input: palindrome = "a"
Output: ""
Explanation: There is no way to replace a single character to make "a" not a palindrome, so return an empty string.
Example 3:

Input: palindrome = "aa"
Output: "ab"
Example 4:

Input: palindrome = "aba"
Output: "abb"

find the first letter before floor(len(palindrome)/2) that is not equal to "a"
if no such position extis, it means we have "aaaa", "aba",...... just change the last letter to "b"
in no way can we make it if len(palindrome) == 1


from math import floor
class Solution:

def breakPalindrome(self, palindrome: str) -> str:
    for i in range(floor(len(palindrome)/2)):
        if palindrome[i]!="a":
            return palindrome[:i] + "a" + palindrome[i+1:]
    if len(palindrome) == 1:
        return ""
    return palindrome[:-1] + "b"
	
	
class Solution:
    def breakPalindrome(self, palindrome: str) -> str:
        ln = len(palindrome)
        if ln == 1: return ''
        for i in range(ln//2):
            if ord(palindrome[i]) > 97:
                temp = palindrome[:i] + 'a' + palindrome[i+1:]
                if temp[i] != temp[ln - i - 1]:
                    return temp
        return palindrome[:ln - 1] + 'b'	