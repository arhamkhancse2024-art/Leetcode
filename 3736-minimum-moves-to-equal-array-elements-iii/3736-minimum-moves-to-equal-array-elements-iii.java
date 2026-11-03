class Solution {
    public int minMoves(int[] nums) {
        int n = nums.length;
        int maxVal = nums[0];
        int totalSum = 0;
        
        // Find the maximum value and total sum in a single pass
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
            totalSum += num;
        }
        
        // Formula: (target_value * count) - current_sum
        return (maxVal * n) - totalSum;
    }
}
