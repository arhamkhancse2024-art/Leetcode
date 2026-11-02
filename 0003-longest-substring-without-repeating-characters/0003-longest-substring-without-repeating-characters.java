import java.util.HashMap;

public class Solution {
    public int lengthOfLongestSubstring(String s) {
        // Map to store the character and its most recent index
        HashMap<Character, Integer> charMap = new HashMap<>();
        int left = 0;
        int maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If the character is a duplicate and inside the current window, move left
            if (charMap.containsKey(currentChar) && charMap.get(currentChar) >= left) {
                left = charMap.get(currentChar) + 1;
            }
            
            // Update the character's last seen position
            charMap.put(currentChar, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
}
