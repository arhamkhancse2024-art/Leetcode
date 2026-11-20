import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public int maxSelectedElements(int[] nums) {
        int maxLen = 0;
        // Map to store: {value -> length of the longest consecutive sequence ending at 'value'}
        HashMap<Integer, Integer> dp = new HashMap<>();
        
        // Sort the array to build the sequence from smallest to largest
        Arrays.sort(nums);
        
        for (int num : nums) {
            // Update num + 1 first to prevent using the same instance of 'num' twice
            dp.put(num + 1, dp.getOrDefault(num, 0) + 1);
            dp.put(num, dp.getOrDefault(num - 1, 0) + 1);
            
            // Track the maximum sequence length seen so far
            maxLen = Math.max(maxLen, Math.max(dp.get(num), dp.get(num + 1)));
        }
        
        return maxLen;
    }
}
